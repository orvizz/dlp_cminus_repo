package ast.errorhandler;

import ast.types.ErrorType;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {

    private static ErrorHandler instance = null;
    private List<ErrorType> errors;

    private ErrorHandler() {
        errors = new ArrayList<>();
    }

    public static ErrorHandler getInstance() {
        if(instance == null) {
            instance = new ErrorHandler();
        }
        return instance;
    }

    public boolean anyErrors() {
        return !errors.isEmpty();
    }

    public void addError(ErrorType error) {
        errors.add(error);
    }

    public void showErrors(PrintStream out) {
        errors.forEach(err -> out.println(err.toString()));
    }
}
