package com.azamat_komaev.crudapp.view;

import com.azamat_komaev.crudapp.controller.SkillController;
import com.azamat_komaev.crudapp.model.Skill;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class SkillView implements GenericView {
    private final SkillController skillController;
    private final Scanner scanner;

    public SkillView() {
        this.skillController = new SkillController();
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
        System.out.println(this.skillController.getAll());
    }

    public void printOne() {
        Integer id = readAndParseId();
        Skill skillToPrint = this.skillController.getOne(id);

        if (skillToPrint == null) {
            System.out.println("There is no any skill with such id!");
            return;
        }

        System.out.println(skillToPrint);
    }

    public void saveAndPrint() {
        String name = readAndParseName();
        Skill newSkill = this.skillController.save(name);
        System.out.println(newSkill);
    }

    public void updateAndPrint() {
        Integer id = readAndParseId();
        String name = readAndParseName();
        Skill skillToPrint = this.skillController.update(id, name);

        if (skillToPrint == null) {
            System.out.println("There is no any skill with such id!");
            return;
        }

        System.out.println(skillToPrint);
    }

    public void deleteAndPrintWasOperationSuccessful() {
        System.out.print("Enter skill id should be deleted: ");
        Integer id = Integer.parseInt(this.scanner.nextLine());

        try {
            this.skillController.destroy(id);
            System.out.println("The skill was deleted successful!");
        } catch (NoSuchElementException e) {
            System.out.println("There is no any skill with such id!");
        }
    }
}
