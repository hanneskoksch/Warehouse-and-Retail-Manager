package core.interfaces;

import core.exceptions.IdNotUniqueException;
import core.exceptions.ValuesNotValidException;

import java.io.IOException;

public interface IItemController {
    void switchToWindow() throws IOException;
    void initialize();
    void switchToPrimary();
    void editItem() throws IdNotUniqueException, ValuesNotValidException;

}
