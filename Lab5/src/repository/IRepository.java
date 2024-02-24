package repository;

import domain.Identifiable;
import exceptions.DuplicateItemException;
import exceptions.ItemNotFound;

public interface IRepository<T extends Identifiable, U> {
    public void addItem(T item) throws DuplicateItemException;
    public void deleteItemById(U id) throws ItemNotFound;

    public T getItemById(U id) throws ItemNotFound;

    public void updateItemById(U id, T newItem) throws ItemNotFound;
    public Iterable<T> getAllItems();
}
