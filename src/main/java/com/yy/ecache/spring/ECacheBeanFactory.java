package com.yy.ecache.spring;

import com.ecache.bean.BeanFactoryInterface;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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
        return get(clazz, null);
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

    private String getBeanId(Class<?> clazz){
        Controller controller = clazz.getAnnotation(Controller.class);
        Service service = clazz.getAnnotation(Service.class);
        Repository repository = clazz.getAnnotation(Repository.class);
        Component component = clazz.getAnnotation(Component.class);
        String id = (controller != null && !"".equals(controller.value())) ? controller.value() : (
                (service != null && !"".equals(service.value())) ? service.value() : (
                        (repository != null && !"".equals(repository.value())) ? repository.value() : (
                                (component != null && !"".equals(component.value())) ? component.value() : null

                        )
                )
        );
        return (id == null) ? clazz.getName() : id;
    }
}
