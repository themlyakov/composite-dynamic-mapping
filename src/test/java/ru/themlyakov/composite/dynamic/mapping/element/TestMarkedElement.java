package ru.themlyakov.composite.dynamic.mapping.element;

import org.springframework.stereotype.Component;
import ru.themlyakov.composite.dynamic.mapping.impl.MarkerTypes;
import ru.themlyakov.composite.dynamic.mapping.marker.TaggingHandler;

@MarkerTypes.First
@MarkerTypes.Second
@Component
public class TestMarkedElement implements TaggingHandler {
}
