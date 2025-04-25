package cpc.uv.orm.jpa;

public interface UniqueIdGenerator<T> {
    T getNextUniqueId();
}