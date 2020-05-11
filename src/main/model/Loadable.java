package model;

import model.exception.ExistenceException;
import model.exception.InvalidInputException;
import model.exception.WrongTypeException;

import java.io.IOException;
import java.util.List;

public interface Loadable {

    List<String> load(String filename)
            throws IOException, ExistenceException, InvalidInputException, WrongTypeException;
}
