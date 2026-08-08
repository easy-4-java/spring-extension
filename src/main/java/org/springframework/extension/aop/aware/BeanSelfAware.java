package org.springframework.extension.aop.aware;

/**
 * BeanSelfAware.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see BeanSelfAware
 */
public interface BeanSelfAware {

    //实现BeanSelfAware接口
    public void setSelf(Object proxyBean);

}
