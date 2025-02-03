package ru.themlyakov.composite.dynamic.mapping.marker;

import java.util.HashSet;
import java.util.Set;

public interface TaggingHandler {
    default Set<String> tags(){
        return new HashSet<>();
    }
}
