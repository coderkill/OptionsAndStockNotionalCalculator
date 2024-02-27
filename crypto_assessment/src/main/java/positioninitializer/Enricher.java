package positioninitializer;

public interface Enricher<T> {

    T enrich(T t);
}
