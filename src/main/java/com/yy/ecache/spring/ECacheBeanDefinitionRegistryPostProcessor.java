package com.yy.ecache.spring;

import com.ecache.proxy.CacheInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * @author 谢俊权
 * @create 2016/10/18 19:49
 */
public class ECacheBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private String basePackage;

    public ECacheBeanDefinitionRegistryPostProcessor(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        beanFactory.setAllowBeanDefinitionOverriding(true);
        ECacheInjector cacheInjector = new ECacheInjector(applicationContext.getAutowireCapableBeanFactory());
        ECacheBeanFactory cacheBeanFactory = new ECacheBeanFactory(beanFactory);
        CacheInterceptor cacheInterceptor = new CacheInterceptor(cacheBeanFactory, cacheInjector);
        cacheInterceptor.run(basePackage);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
