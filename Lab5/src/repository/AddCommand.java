package repository;

import domain.Identifiable;
import exceptions.DuplicateItemException;
import exceptions.ItemNotFound;

public class AddCommand<T extends Identifiable, U> implements Command<T,U>{

    private IRepository<T,U> iRepository;

    private T addedItem;

    public AddCommand(IRepository<T, U> iRepository, T addedItem) {
        this.iRepository = iRepository;
        this.addedItem = addedItem;
    }

    @Override
    public void undo() throws ItemNotFound {
        if (iRepository != null) {
            iRepository.deleteItemById((U) addedItem);
        }
    }

    @Override
    public void redo() throws DuplicateItemException {
        iRepository.addItem(addedItem);
    }
}
