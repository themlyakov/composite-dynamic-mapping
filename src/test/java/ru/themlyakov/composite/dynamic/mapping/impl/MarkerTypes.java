package ru.themlyakov.composite.dynamic.mapping.impl;

import ru.themlyakov.composite.dynamic.mapping.annotation.TaggingMarker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MarkerTypes {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @TaggingMarker(markers = "first")
    public @interface First {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @TaggingMarker(markers = "second")
    public @interface Second {}
}
