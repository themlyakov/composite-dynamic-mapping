package ru.themlyakov.composite.dynamic.mapping.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.themlyakov.composite.dynamic.mapping.annotation.TaggingMarker;
import ru.themlyakov.composite.dynamic.mapping.util.ProcessingUtils;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TaggingMarkerBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Class> foundBeans = new HashMap<>();
    private final Map<String, Set<String>> markersForBeanName = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(ProcessingUtils.isTaggingMarkerAnnotationPresent(bean)){
            if(ProcessingUtils.isTaggingHandlerInstance(bean)){
                Set<String> mappingMarkers = getAllMappingMarkers(bean);
                foundBeans.putIfAbsent(beanName,bean.getClass());
                markersForBeanName.putIfAbsent(beanName,mappingMarkers);
            }else{
                throw new BeanCreationException(String.format("Bean with name '%s' not TaggingHandler instance",beanName));
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(foundBeans.containsKey(beanName)){
            Class clazz = foundBeans.get(beanName);
            return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), (proxy, method, args) -> {
                if(method.isDefault() && "tags".equals(method.getName())){
                    return markersForBeanName.get(beanName);
                }
                return method.invoke(proxy,args);
            });
        }
        return bean;
    }

    private Set<String> getAllMappingMarkers(Object bean) {
        return ProcessingUtils.findAnnotations(bean.getClass(), TaggingMarker.class).stream()
                .map(TaggingMarker::markers)
                .flatMap(Arrays::stream)
                .collect(Collectors.toSet());
    }
}
