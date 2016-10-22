package com.ecache.spring;

import com.ecache.bean.InjectorInterface;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * @author 谢俊权
 * @create 2016/8/1 17:15
 */
public class ECacheInjector implements InjectorInterface {

    private AutowireCapableBeanFactory beanFactory;

    public ECacheInjector(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public <T> void doInject(T bean) {
        beanFactory.autowireBeanProperties(bean, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
    }
}