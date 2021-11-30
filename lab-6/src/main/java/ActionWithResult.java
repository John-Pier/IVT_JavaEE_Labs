public interface ActionWithResult<T, M> {
    M run(T resultSet);
}