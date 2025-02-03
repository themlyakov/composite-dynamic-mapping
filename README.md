# Стартер для тэгирования бинов
#### Данный стартер позволяет на этапе поднятия контекста просканировать аннотации TaggingMarker и возвращать значения этих аннотаций при вызове метода markers()

## Пример
### Для начала необходимо определить аннотации-обертки, содержащие аннотацию TaggingMarker
```java
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
```
### Далее объявить сам компонент, использую данные аннотации

```java
@MarkerTypes.First
@MarkerTypes.Second
@Component
public class TestMarkedElement implements TaggingHandler {
}
```
> Компонент всегда должен реализовывать интерфейc TaggingHandler, иначе на этапе поднятия контекста выбросится исключения
> ```java
> public interface TaggingHandler {
>    default Set<String> tags(){
>        return new HashSet<>();
>    }
> } 
> ```

### Далее протестируем полученный результат
```java
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
```
