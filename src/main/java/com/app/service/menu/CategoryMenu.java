package com.app.service.menu;

import com.app.model.Category;
import com.app.model.State;
import com.app.repository.category.CategoryRepository;
import com.app.repository.category.CategoryRepositoryImpl;

import java.util.Comparator;
import java.util.Scanner;

import static com.app.model.State.INIT;
import static com.app.service.tools.MenuTools.showAvailableOperations;

class CategoryMenu {

    private static final String TABLE_NAME = "category";

    private static Scanner scanner = new Scanner(System.in);
    private static State state;

    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    State printAvailableOperationsOnCategories() {
        showAvailableOperations(TABLE_NAME);

        switch (scanner.nextInt()) {
            case 1: {
                state = printAddCategory();
                break;
            }
            case 2: {
                state = printDeleteCategory();
                break;
            }
            case 3: {
                state = printUpdateCategory();
                break;
            }
            case 4: {
                state = printShowAllCategories();
                break;
            }
            case 5: {
                state = printChosenCategory();
                break;
            }
            case 0: {
                state = INIT;
                scanner.nextLine();
                break;
            }
            default: {
                System.out.println("Wrong answer!");
                state = State.CATEGORY;
                scanner.nextLine();
                break;
            }
        }
        return state;
    }

    private State printAddCategory() {
        System.out.println("Enter category name:");
        String name = scanner.nextLine();
        name = scanner.nextLine();
        categoryRepository.add(Category.builder().name(name).build());
        state = State.CATEGORY;
        return state;
    }

    private State printDeleteCategory() {
        System.out.println("Choose Category id from list to delete:");
        categoryRepository
            .findAll()
            .stream()
            .sorted(Comparator.comparing(Category::getId))
            .forEach(x -> System.out.println(x.getId() + ". " + x.getName()));
        System.out.println("0 - Go back");

        long choice = scanner.nextLong();
        scanner.nextLine();

        if (choice == 0) {
            state = State.CATEGORY;
            return state;
        } else {
            categoryRepository.delete(choice);
            state = State.CATEGORY;
            return state;
        }
    }

    private State printUpdateCategory() {
        categoryRepository
            .findAll()
            .forEach(System.out::println);

        System.out.println("Choose id:");
        long choice = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter new Category name:");
        String name = scanner.nextLine();

        categoryRepository.update(Category
            .builder()
            .id(choice)
            .name(name)
            .build()
        );
        state = State.CATEGORY;
        return state;
    }

    private State printShowAllCategories() {
        System.out.println("1 - show all Categories sorted by name");
        System.out.println("2 - show all Categories sorted by id");
        System.out.println("0 - go back");

        switch (scanner.nextInt()) {
            case 1: {
                printCategoriesSortedByName();
                break;
            }
            case 2: {
                printCategoriesSortedById();
                break;
            }
            case 0: {
                state = State.CATEGORY;
                break;
            }
            default: {
                System.out.println("Wrong answer");
                state = State.CATEGORY;
                break;
            }
        }

        return state;
    }

    private State printCategoriesSortedById() {
        categoryRepository
            .findAll()
            .stream()
            .sorted(Comparator.comparing(Category::getId))
            .forEach(System.out::println);
        System.out.println("0 - Go back");

        long choice = scanner.nextLong();
        scanner.nextLine();

        if (choice == 0) {
            state = State.CATEGORY;
            return state;
        } else {
            while (choice != 0) {
                System.out.println("Press 0 to go back");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            state = State.CATEGORY;
        }
        return state;
    }

    private State printCategoriesSortedByName() {
        categoryRepository
            .findAll()
            .stream()
            .sorted(Comparator.comparing(Category::getName))
            .forEach(System.out::println);
        System.out.println("0 - Go back");

        long choice = scanner.nextLong();
        scanner.nextLine();

        if (choice == 0) {
            state = State.CATEGORY;
            return state;
        } else {
            while (choice != 0) {
                System.out.println("Press 0 to go back ");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            state = State.CATEGORY;
        }
        return state;
    }

    private State printChosenCategory() {
        System.out.println("1 - show category with chosen id");
        System.out.println("2 - show category with chosen name");
        System.out.println("0 - bo back");

        switch (scanner.nextInt()) {
            case 1: {
                printCategoryWithChosenId();
                break;
            }
            case 2: {
                printCategoryWithChosenName();
                break;
            }
            case 0: {
                state = State.CATEGORY;
                break;
            }
            default: {
                System.out.println("Wrong answer");
                state = State.CATEGORY;
                break;
            }
        }

        return state;
    }

    private State printCategoryWithChosenId() {
        System.out.println("Enter id:");
        System.out.println(categoryRepository
            .findOneById(scanner.nextLong())
            .orElseThrow(() -> new NullPointerException("NO CATEGORY WITH CHOSEN ID"))
        );
        state = State.CATEGORY;
        return state;
    }

    private State printCategoryWithChosenName() {
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        long id = -1;
        for (Category c : categoryRepository.findAll()) {
            if (name.equals(c.getName())) {
                id = c.getId();
                break;
            }
        }
        System.out.println(categoryRepository
            .findOneById(id)
            .orElseThrow(() -> new NullPointerException("NO CATEGORY WITH CHOSEN NAME"))
        );
        state = State.CATEGORY;
        return state;
    }
}
