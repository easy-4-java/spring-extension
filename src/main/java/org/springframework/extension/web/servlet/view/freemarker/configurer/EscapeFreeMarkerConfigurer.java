package org.springframework.extension.web.servlet.view.freemarker.configurer;

import freemarker.cache.TemplateLoader;
import org.springframework.extension.web.servlet.view.freemarker.cache.HtmlTemplateLoader;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.List;

/**
 * EscapeFreeMarkerConfigurer.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see EscapeFreeMarkerConfigurer
 */
public class EscapeFreeMarkerConfigurer extends FreeMarkerConfigurer{

  @Override
  protected TemplateLoader getAggregateTemplateLoader(List<TemplateLoader> templateLoaders) {
      return new HtmlTemplateLoader(super.getAggregateTemplateLoader(templateLoaders));
  }

}
