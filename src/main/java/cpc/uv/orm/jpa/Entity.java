package cpc.uv.orm.jpa;

public interface Entity<T extends EntityId> {
    T getId();
}
