package com.ecache.spring;

import com.ecache.bean.BeanFactoryInterface;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author 谢俊权
 * @create 2016/8/1 17:13
 */
public class ECacheBeanFactory implements BeanFactoryInterface{

    private DefaultListableBeanFactory beanFactory;

    public ECacheBeanFactory(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public <T> void set(Class<?> clazz, T object) {
        set(clazz, null, object);
    }

    @Override
    public <T> void set(Class<?> clazz, String id, T object) {
        String beanId = id;
        String[] beanNames = beanFactory.getBeanNamesForType(clazz);
        if(beanNames != null && beanNames.length > 0){
            beanId = beanNames[0];
        }
        if(beanId == null){
            throw new RuntimeException("not found beanName of class " + clazz);
        }
        beanFactory.registerSingleton(beanId, object);
    }

    @Override
    public <T> T get(Class<?> clazz) {
        return get(clazz, clazz.getName());
    }

    @Override
    public <T> T get(Class<?> clazz, String id) {
        String beanId = id;
        String[] beanNames = beanFactory.getBeanNamesForType(clazz);
        if(beanNames != null && beanNames.length > 0){
            beanId = beanNames[0];
        }
        if(beanId == null){
            throw new RuntimeException("not found beanName of class " + clazz);
        }
        T bean = (T) beanFactory.getSingleton(beanId);
        return (bean == null) ? (T)beanFactory.getBean(clazz) : bean;
    }
}
