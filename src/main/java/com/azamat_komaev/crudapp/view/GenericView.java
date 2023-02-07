package com.azamat_komaev.crudapp.view;

public interface GenericView {
    void printAll();

    void printOne();

    void saveAndPrint();

    void updateAndPrint();

    void deleteAndPrintWasOperationSuccessful();

    default void runCommand(String command) {
        switch (command) {
            case "get_all" -> printAll();
            case "get_one" -> printOne();
            case "create" -> saveAndPrint();
            case "update" -> updateAndPrint();
            case "delete" -> deleteAndPrintWasOperationSuccessful();
            default -> System.out.println("Invalid command, try again!");
        }
    }
}

