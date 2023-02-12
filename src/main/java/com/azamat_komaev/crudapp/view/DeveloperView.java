package com.azamat_komaev.crudapp.view;

import com.azamat_komaev.crudapp.controller.DeveloperController;
import com.azamat_komaev.crudapp.model.Developer;
import com.azamat_komaev.crudapp.model.Skill;
import com.azamat_komaev.crudapp.model.Specialty;
import com.azamat_komaev.crudapp.model.Status;
import com.azamat_komaev.crudapp.repository.SkillRepository;
import com.azamat_komaev.crudapp.repository.SpecialtyRepository;
import com.azamat_komaev.crudapp.repository.jdbc.JdbcSkillRepositoryImpl;
import com.azamat_komaev.crudapp.repository.jdbc.JdbcSpecialtyRepositoryImpl;

import java.util.*;

public class DeveloperView implements GenericView {
    public final DeveloperController developerController;
    private final Scanner scanner;

    public DeveloperView() {
        this.developerController = new DeveloperController();
        this.scanner = new Scanner(System.in);
    }

    private Integer readAndParseId() {
        System.out.print("Enter id: ");
        return Integer.parseInt(this.scanner.nextLine());
    }

    private String readAndParseFirstName() {
        System.out.print("Enter firstName: ");
        return scanner.nextLine();
    }

    private String readAndParseLastName() {
        System.out.print("Enter lastName: ");
        return scanner.nextLine();
    }

    private List<Skill> readAndParseSkillList() {
        SkillRepository skillRepository = new JdbcSkillRepositoryImpl();
        System.out.print("Enter list of skill ids seperated with spaces: ");
        String[] skillsIdsString = this.scanner.nextLine().split(" ");

        List<Integer> skillsIds = Arrays.stream(skillsIdsString)
            .mapToInt(Integer::parseInt)
            .boxed()
            .toList();

        List<Skill> skillList = new ArrayList<>();
        skillsIds.forEach(id -> {
            Skill skill = skillRepository.getById(id);
            if (skill != null) {
                skillList.add(skill);
            }
        });

        return skillList;
    }

    private Specialty readAndParseSpecialty() {
        System.out.print("Enter specialty id: ");
        Integer specialtyId = Integer.parseInt(this.scanner.nextLine());

        SpecialtyRepository repository = new JdbcSpecialtyRepositoryImpl();
        return repository.getById(specialtyId);
    }

    public void printAll() {
        System.out.println(this.developerController.getAll());
    }

    public void printOne() {
        Integer id = readAndParseId();
        Developer developerToPrint;

        try {
            developerToPrint = this.developerController.getOne(id);
            System.out.println(developerToPrint);
        } catch (NoSuchElementException e) {
            System.out.println("There is no any developer with such id!");
        }
    }

    public void saveAndPrint() {
        String firstName = readAndParseFirstName();
        String lastName = readAndParseLastName();
        List<Skill> skillList = readAndParseSkillList();
        Specialty specialty = readAndParseSpecialty();

        Developer newDeveloper = this.developerController.save(firstName, lastName, Status.ACTIVE, skillList, specialty);
        System.out.println(newDeveloper);
    }

    public void updateAndPrint() {
        Integer id = readAndParseId();
        String firstName = readAndParseFirstName();
        String lastName = readAndParseLastName();
        List<Skill> skillList = readAndParseSkillList();
        Specialty specialty = readAndParseSpecialty();

        try {
            Developer developerToPrint = this.developerController.update(id, firstName, lastName, Status.ACTIVE, skillList, specialty);
            System.out.println(developerToPrint);
        } catch (NoSuchElementException e) {
            System.out.println("There is no any developer with such id!");
        }
    }

    public void deleteAndPrintWasOperationSuccessful() {
        Integer id = readAndParseId();

        try {
            this.developerController.destroy(id);
            System.out.println("The developer was deleted successful!");
        } catch (NoSuchElementException e) {
            System.out.println("There is no any developer with such id!");
        }
    }
}
