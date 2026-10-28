package org.springframework.extension.web.multipart.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload2.core.DiskFileItem;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileUploadSizeException;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletDiskFileUpload;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 使用 Jakarta Commons FileUpload 解析请求并将上传进度写入 Session。 */
@Slf4j
public class MultipartStatusResolver implements MultipartResolver {

    private static final String ITEMS_ATTRIBUTE = MultipartStatusResolver.class.getName() + ".items";

    private long maxUploadSize = -1;
    private long maxUploadSizePerFile = -1;
    private Charset defaultEncoding = StandardCharsets.UTF_8;
    private boolean resolveLazily;

    public void setMaxUploadSize(long maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
    }

    public void setMaxUploadSizePerFile(long maxUploadSizePerFile) {
        this.maxUploadSizePerFile = maxUploadSizePerFile;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = Charset.forName(defaultEncoding);
    }

    public void setResolveLazily(boolean resolveLazily) {
        this.resolveLazily = resolveLazily;
    }

    @Override
    public boolean isMultipart(HttpServletRequest request) {
        return JakartaServletFileUpload.isMultipartContent(request);
    }

    @Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        if (resolveLazily) {
            return new DefaultMultipartHttpServletRequest(request) {
                @Override
                protected void initializeMultipart() {
                    ParsedRequest parsed = parseRequest(request);
                    setMultipartFiles(parsed.files);
                    setMultipartParameters(parsed.parameters);
                    setMultipartParameterContentTypes(parsed.contentTypes);
                }
            };
        }
        ParsedRequest parsed = parseRequest(request);
        return new DefaultMultipartHttpServletRequest(request, parsed.files, parsed.parameters, parsed.contentTypes);
    }

    private ParsedRequest parseRequest(HttpServletRequest request) {
        DiskFileItemFactory factory = DiskFileItemFactory.builder().get();
        JakartaServletDiskFileUpload upload = new JakartaServletDiskFileUpload(factory);
        upload.setMaxSize(maxUploadSize);
        upload.setMaxFileSize(maxUploadSizePerFile);
        upload.setHeaderCharset(defaultEncoding);
        HttpSession session = request.getSession();
        upload.setProgressListener((bytesRead, contentLength, items) -> {
            if (contentLength > 0) {
                long percent = Math.min(100, bytesRead * 100 / contentLength);
                session.setAttribute("percent", percent + "%");
            }
        });
        try {
            List<DiskFileItem> items = upload.parseRequest(request);
            request.setAttribute(ITEMS_ATTRIBUTE, items);
            MultiValueMap<String, MultipartFile> files = new LinkedMultiValueMap<>();
            Map<String, List<String>> fieldValues = new HashMap<>();
            Map<String, String> contentTypes = new HashMap<>();
            for (DiskFileItem item : items) {
                if (item.isFormField()) {
                    fieldValues.computeIfAbsent(item.getFieldName(), key -> new ArrayList<>())
                            .add(item.getString(defaultEncoding));
                    contentTypes.put(item.getFieldName(), item.getContentType());
                } else {
                    files.add(item.getFieldName(), new FileItemMultipartFile(item));
                }
            }
            Map<String, String[]> parameters = new HashMap<>();
            fieldValues.forEach((name, values) -> parameters.put(name, values.toArray(new String[0])));
            return new ParsedRequest(files, parameters, contentTypes);
        } catch (FileUploadSizeException exception) {
            throw new MaxUploadSizeExceededException(exception.getPermitted(), exception);
        } catch (IOException exception) {
            throw new MultipartException("Could not parse multipart servlet request", exception);
        }
    }

    @Override
    public void cleanupMultipart(MultipartHttpServletRequest request) {
        Object stored = request.getAttribute(ITEMS_ATTRIBUTE);
        if (stored instanceof List<?> items) {
            for (Object value : items) {
                if (value instanceof DiskFileItem item) {
                    try {
                        item.delete();
                    } catch (IOException exception) {
                        log.warn("Failed to delete multipart temporary file", exception);
                    }
                }
            }
            request.removeAttribute(ITEMS_ATTRIBUTE);
        }
    }

    private static final class ParsedRequest {
        private final MultiValueMap<String, MultipartFile> files;
        private final Map<String, String[]> parameters;
        private final Map<String, String> contentTypes;

        private ParsedRequest(MultiValueMap<String, MultipartFile> files,
                              Map<String, String[]> parameters,
                              Map<String, String> contentTypes) {
            this.files = files;
            this.parameters = parameters;
            this.contentTypes = contentTypes;
        }
    }
}
