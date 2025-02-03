package ru.themlyakov.composite.dynamic.mapping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import ru.themlyakov.composite.dynamic.mapping.marker.TaggingHandler;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EnableAutoConfiguration
class TaggingTest {

    @Autowired
    List<TaggingHandler> handlers;

    @Test
    void contextLoads() {
        assertEquals(1,handlers.size());
        TaggingHandler markerHandler = handlers.get(0);
        assertEquals(2,markerHandler.tags().size());
        Set<String> markers = markerHandler.tags();
        assertEquals(Set.of("first","second"),markers);
    }
}
