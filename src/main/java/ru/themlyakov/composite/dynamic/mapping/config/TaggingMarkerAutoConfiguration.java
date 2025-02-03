package ru.themlyakov.composite.dynamic.mapping.config;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import ru.themlyakov.composite.dynamic.mapping.processor.TaggingMarkerBeanPostProcessor;

public class TaggingMarkerAutoConfiguration {

    @Bean
    public BeanPostProcessor mappingMarkerBeanPostProcessor(){
        return new TaggingMarkerBeanPostProcessor();
    }
}
