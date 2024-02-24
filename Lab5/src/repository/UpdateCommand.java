package repository;

import domain.Identifiable;
import exceptions.DuplicateItemException;
import exceptions.ItemNotFound;

public class UpdateCommand <T extends Identifiable, U> implements Command<T,U>{

    private IRepository<T,U> iRepository;

    private T oldItem;

    private T newItem;

    public UpdateCommand(IRepository<T, U> iRepository, T oldItem, T newItem) {
        this.iRepository = iRepository;
        this.oldItem = oldItem;
        this.newItem = newItem;
    }

    @Override
    public void undo() throws ItemNotFound, DuplicateItemException {
        iRepository.updateItemById((U) oldItem.getId(),oldItem);
    }

    @Override
    public void redo() throws DuplicateItemException, ItemNotFound {
        iRepository.updateItemById((U) newItem.getId(),newItem);

    }
}
