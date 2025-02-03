package ru.themlyakov.composite.dynamic.mapping.util;

import org.springframework.core.annotation.AnnotationFilter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.lang.Nullable;
import ru.themlyakov.composite.dynamic.mapping.annotation.TaggingMarker;
import ru.themlyakov.composite.dynamic.mapping.marker.TaggingHandler;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Optional.ofNullable;

public class ProcessingUtils {


    public static <A extends Annotation> List<A> findAnnotations(Class<?> clazz, @Nullable Class<A> annotationType) {
        List<A> result = new ArrayList<>();
        enrichFindAnnotations(clazz, annotationType,result);
        return result;
    }

    public static <A extends Annotation> void enrichFindAnnotations(Class<?> clazz, @Nullable Class<A> annotationType, List<A> annotationList) {
        if (annotationType == null) {
            return;
        }

        if (AnnotationFilter.PLAIN.matches(annotationType)) {
            A annotation = clazz.getDeclaredAnnotation(annotationType);
            if (annotation != null) {
                annotationList.add(annotation);
            }
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null || superclass != Object.class) {
                enrichFindAnnotations(superclass, annotationType, annotationList);
            }
        }

        MergedAnnotations.search(MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).from(clazz).stream(annotationType)
                .filter(Objects::nonNull)
                .map(MergedAnnotation::synthesize)
                .forEach(annotationList::add);
    }

    public static boolean isTaggingMarkerAnnotationPresent(Object bean){
        return ofNullable(bean)
                .map(v-> AnnotationUtils.findAnnotation(v.getClass(), TaggingMarker.class))
                .isPresent();
    }

    public static boolean isTaggingHandlerInstance(Object bean){
        return bean instanceof TaggingHandler;
    }
}
