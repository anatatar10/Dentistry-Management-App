package repository;

import domain.Identifiable;
import exceptions.DuplicateItemException;
import exceptions.ItemNotFound;

public class DeleteCommand<T extends Identifiable, U> implements Command<T,U>{

    private IRepository<T,U> iRepository;

    private T deletedItem;

    public DeleteCommand(IRepository<T, U> iRepository, T deletedItem) {
        this.iRepository = iRepository;
        this.deletedItem = deletedItem;
    }

    @Override
    public void undo() throws ItemNotFound, DuplicateItemException {
        iRepository.addItem(deletedItem);
    }

    @Override
    public void redo() throws DuplicateItemException, ItemNotFound {
        iRepository.deleteItemById((U) deletedItem.getId());
    }
}
