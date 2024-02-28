package positioninitializer.enrichers;

public interface Enricher<T> {

    T enrich(T t);
}
