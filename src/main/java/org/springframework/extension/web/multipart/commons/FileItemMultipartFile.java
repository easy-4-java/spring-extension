package org.springframework.extension.web.multipart.commons;

import org.apache.commons.fileupload2.core.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/** 将 Commons FileUpload 2 的磁盘条目适配为 Spring MultipartFile。 */
final class FileItemMultipartFile implements MultipartFile {

    private final DiskFileItem item;

    FileItemMultipartFile(DiskFileItem item) {
        this.item = Objects.requireNonNull(item, "item");
    }

    @Override
    public String getName() {
        return item.getFieldName();
    }

    @Override
    public String getOriginalFilename() {
        return item.getName();
    }

    @Override
    public String getContentType() {
        return item.getContentType();
    }

    @Override
    public boolean isEmpty() {
        return item.getSize() == 0;
    }

    @Override
    public long getSize() {
        return item.getSize();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return item.get();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return item.getInputStream();
    }

    @Override
    public void transferTo(File destination) throws IOException {
        try (InputStream source = item.getInputStream()) {
            Files.copy(source, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
