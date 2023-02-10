package com.azamat_komaev.crudapp.view;

import com.azamat_komaev.crudapp.controller.SpecialtyController;
import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;

import java.util.Scanner;

public class SpecialtyView implements GenericView {
    private final SpecialtyController specialtyController;
    private final Scanner scanner;

    public SpecialtyView() {
        this.specialtyController = new SpecialtyController();
        this.scanner = new Scanner(System.in);
    }

    private Integer readAndParseId() {
        System.out.print("Enter id: ");
        return Integer.parseInt(this.scanner.nextLine());
    }

    private String readAndParseName() {
        System.out.print("Enter name: ");
        return scanner.nextLine();
    }

    public void printAll() {
        System.out.println(this.specialtyController.getAll());
    }

    public void printOne() {
        Integer id = readAndParseId();
        Specialty specialtyToPrint = this.specialtyController.getOne(id);

        if (specialtyToPrint == null) {
            System.out.println("There is no any specialty with such id!");
            return;
        }

        System.out.println(specialtyToPrint);
    }

    public void saveAndPrint() {
        String name = readAndParseName();
        Specialty newSpecialty = this.specialtyController.save(name, Status.ACTIVE);
        System.out.println(newSpecialty);
    }

    public void updateAndPrint() {
        Integer id = readAndParseId();
        String name = readAndParseName();
        Specialty specialtyToPrint = this.specialtyController.update(id, name, Status.ACTIVE);

        if (specialtyToPrint == null) {
            System.out.println("There is not any specialty with such id!");
            return;
        }

        System.out.println(specialtyToPrint);
    }

    public void deleteAndPrintWasOperationSuccessful() {
        Integer id = readAndParseId();
        this.specialtyController.destroy(id);
        System.out.println("The specialty was deleted successfully!");
    }
}
