package repository;

import exceptions.DuplicateItemException;
import exceptions.ItemNotFound;

public interface Command<T,U>{
    void undo() throws ItemNotFound, DuplicateItemException;
    void redo() throws DuplicateItemException, ItemNotFound;
}
