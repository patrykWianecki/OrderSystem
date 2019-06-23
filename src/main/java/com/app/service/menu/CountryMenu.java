package com.app.service.menu;

import com.app.model.Country;
import com.app.model.State;
import com.app.repository.country.CountryRepository;
import com.app.repository.country.CountryRepositoryImpl;
import com.app.service.tools.MenuTools;

import java.util.Comparator;
import java.util.Scanner;

import static com.app.model.State.*;
import static com.app.service.tools.MenuTools.showAvailableOperations;

class CountryMenu {

    private final static String TABLE_NAME = "country";

    private static Scanner scanner = new Scanner(System.in);
    private static State state;

    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private MenuTools menuTools = new MenuTools();

    State printAvailableOperationsOnCountries() {
        showAvailableOperations(TABLE_NAME);

        switch (scanner.nextInt()) {
            case 1: {
                state = printAddCountry();
                break;
            }
            case 2: {
                state = printDeleteCountry();
                break;
            }
            case 3: {
                state = printUpdateCountry();
                break;
            }
            case 4: {
                state = printShowAllCountries();
                break;
            }
            case 5: {
                state = printChosenCountry();
                break;
            }
            case 0: {
                state = INIT;
                scanner.nextLine();
                break;
            }
            default: {
                System.out.println("Wrong answer!");
                state = COUNTRY;
                scanner.nextLine();
                break;
            }
        }

        return state;
    }

    private State printAddCountry() {
        System.out.println("Enter country name:");
        String name = scanner.nextLine();
        countryRepository.add(Country
            .builder()
            .name(name)
            .build()
        );
        state = COUNTRY;
        return state;
    }

    private State printDeleteCountry() {
        System.out.println("Choose Country id from list to delete:");
        menuTools.showCountriesSortedById();
        System.out.println("0 - Go back");

        long choice = scanner.nextLong();
        scanner.nextLine();

        if (choice == 0) {
            state = COUNTRY;
            return state;
        } else {
            countryRepository.delete(choice);
            state = COUNTRY;
            return state;
        }
    }

    private State printUpdateCountry() {
        countryRepository
            .findAll()
            .forEach(System.out::println);

        System.out.println("Choose id:");
        long choice = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter new Country name:");
        String name = scanner.nextLine();

        countryRepository.update(Country
            .builder()
            .id(choice)
            .name(name)
            .build()
        );
        state = COUNTRY;
        return state;
    }

    private State printShowAllCountries() {
        System.out.println("1 - show all Countries sorted by name");
        System.out.println("2 - show all Countries sorted by id");
        System.out.println("0 - go back");

        switch (scanner.nextInt()) {
            case 1: {
                printCountriesSortedByName();
                break;
            }
            case 2: {
                printCountriesSortedById();
                break;
            }
            case 0: {
                state = COUNTRY;
                break;
            }
            default: {
                System.out.println("Wrong answer");
                state = COUNTRY;
                break;
            }
        }

        return state;
    }

    private State printCountriesSortedById() {
        menuTools.showCountriesSortedById();
        System.out.println("0 - Go back");

        long choice = scanner.nextLong();
        scanner.nextLine();

        if (choice == 0) {
            state = COUNTRY;
            return state;
        } else {
            while (choice != 0) {
                System.out.println("Press 0 to go back");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            state = COUNTRY;
        }
        return state;
    }

    private State printCountriesSortedByName() {
        countryRepository
            .findAll()
            .stream()
            .sorted(Comparator.comparing(Country::getName))
            .forEach(System.out::println);
        System.out.println("0 - Go back");

        long choice = scanner.nextLong();
        scanner.nextLine();

        if (choice == 0) {
            state = COUNTRY;
            return state;
        } else {
            while (choice != 0) {
                System.out.println("Press 0 to go back ");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            state = COUNTRY;
        }
        return state;
    }

    private State printChosenCountry() {
        System.out.println("1 - show Country with chosen id");
        System.out.println("2 - show Country with chosen name");
        System.out.println("0 - bo back");

        switch (scanner.nextInt()) {
            case 1: {
                printCountryWithChosenId();
                break;
            }
            case 2: {
                printCountryWithChosenName();
                break;
            }
            case 0: {
                state = COUNTRY;
                break;
            }
            default: {
                System.out.println("Wrong answer");
                state = COUNTRY;
                break;
            }
        }

        return state;
    }

    private State printCountryWithChosenId() {
        System.out.println("Enter id:");
        System.out.println(countryRepository
            .findOneById(scanner.nextLong())
            .orElseThrow(() -> new NullPointerException("NO COUNTRY WITH CHOSEN ID"))
        );
        state = COUNTRY;
        return state;
    }

    private State printCountryWithChosenName() {
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        long id = -1;
        for (Country c : countryRepository.findAll()) {
            if (name.equals(c.getName())) {
                id = c.getId();
                break;
            }
        }
        System.out.println(countryRepository
            .findOneById(id)
            .orElseThrow(() -> new NullPointerException("NO COUNTRY WITH CHOSEN ID"))
        );
        state = COUNTRY;
        return state;
    }
}
