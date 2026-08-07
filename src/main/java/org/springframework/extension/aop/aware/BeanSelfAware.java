package org.springframework.extension.aop.aware;

public interface BeanSelfAware {

    //实现BeanSelfAware接口
    public void setSelf(Object proxyBean);

}
