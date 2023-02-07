package com.azamat_komaev.crudapp.util;

import com.azamat_komaev.crudapp.view.DeveloperView;
import com.azamat_komaev.crudapp.view.GenericView;
import com.azamat_komaev.crudapp.view.SkillView;
import com.azamat_komaev.crudapp.view.SpecialtyView;

import java.util.Scanner;

public class ViewUtil {
    public static String askForAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter action: ");
        return scanner.nextLine();
    }

    public static String[] splitInputOnEntityNameAndCommand(String input, String splitter) {
        return input.split(splitter);
    }

    public static GenericView getViewModelByEntityName(String entityName) {
        switch (entityName) {
            case "skill":
                return new SkillView();
            case "specialty":
                return new SpecialtyView();
            case "developer":
                return new DeveloperView();
            default:
                System.out.println("Invalid entity name!");
                return null;
        }
    }
}
