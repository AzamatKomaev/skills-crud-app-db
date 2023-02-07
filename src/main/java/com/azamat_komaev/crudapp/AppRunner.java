package com.azamat_komaev.crudapp;

import com.azamat_komaev.crudapp.util.ViewUtil;
import com.azamat_komaev.crudapp.view.GenericView;

public class AppRunner {
    public static void main(String[] args) {
        System.out.println("""
            Hello! It is console application to work with different entities.\s
            If you want to get full information about how to use it you can read README.md.\s
            """);

        while (true) {
            String input = ViewUtil.askForAction();
            String[] inputParts = ViewUtil.splitInputOnEntityNameAndCommand(input, "/");

            String entityName = inputParts[0];
            String command = inputParts[1];

            GenericView view = ViewUtil.getViewModelByEntityName(entityName);

            if (view == null) {
                continue;
            }

            view.runCommand(command);
        }
    }
}
