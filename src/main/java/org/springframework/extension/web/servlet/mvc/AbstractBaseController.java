package org.springframework.extension.web.servlet.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractBaseController.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see AbstractBaseController
 */
public class AbstractBaseController {

    protected static final transient Logger LOG = LoggerFactory.getLogger(AbstractBaseController.class);

    protected void logException(Exception ex) {
        if (LOG.isErrorEnabled()){
            LOG.error(ex.getMessage(), ex);
        }
    }

}
