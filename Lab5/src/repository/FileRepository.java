package repository;

import domain.Identifiable;
import exceptions.DuplicateItemException;
import exceptions.ItemNotFound;

public abstract class FileRepository<T extends Identifiable<U>, U> extends MemoryRepository<T,U> {
    protected String fileName;

    public FileRepository(String filename) {
        this.fileName = filename;
        this.readFromFile(); //comment this when bin is empty
    }

    protected abstract void readFromFile();
    protected abstract void writeFile();

    @Override
    public void addItem(T elem) throws DuplicateItemException
    {
        try {
            super.addItem(elem);
            writeFile();
        }catch (DuplicateItemException e)
        {
            System.out.println("Item already exists.");
        }
    }

    @Override
    public void deleteItemById(U id) throws ItemNotFound {
        super.deleteItemById(id);
        writeFile();
    }


    @Override
    public void updateItemById(U id, T newItem) throws ItemNotFound {
        super.updateItemById(id, newItem);
        writeFile();
    }



}
