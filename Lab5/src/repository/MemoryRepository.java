package repository;

import domain.Identifiable;
import exceptions.DuplicateItemException;
import exceptions.ItemNotFound;


import java.util.*;

public class MemoryRepository<T extends Identifiable, U> implements IRepository<T,U> {

    public Map<U, T> listOfItems = new HashMap<>();

    @Override
    public void addItem(T item) throws DuplicateItemException {
        if(listOfItems.containsKey(item.getId()))
            throw new DuplicateItemException("An item with " + item.getId() + " already exists.");
        listOfItems.put((U) item.getId(), item);
    }

    @Override
    public void deleteItemById(U id) throws ItemNotFound {
        if(listOfItems.containsKey(id))
        {
            listOfItems.remove(id);
            return;
        }
        throw new ItemNotFound("Item " + id + " not found");
    }

    @Override
    public T getItemById(U id) throws ItemNotFound {
        if(!listOfItems.containsKey(id))
        {
            throw new ItemNotFound("Item with id " + id + " not found");
        }
        return listOfItems.get(id);
    }

    @Override
    public void updateItemById(U id, T newItem) throws ItemNotFound {
        if (!listOfItems.containsKey(id)) {
            throw new ItemNotFound("Item with id " + id + " not found");
        } else {
            listOfItems.replace(id, newItem);
        }
    }

    @Override
    public Iterable<T> getAllItems() {
        return listOfItems.values();
    }
}

