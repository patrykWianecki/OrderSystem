package com.app.service.menu;

import com.app.model.Producer;
import com.app.model.State;
import com.app.repository.country.CountryRepository;
import com.app.repository.country.CountryRepositoryImpl;
import com.app.repository.producer.ProducerRepository;
import com.app.repository.producer.ProducerRepositoryImpl;

import java.util.Comparator;
import java.util.Scanner;

import static com.app.model.State.*;
import static com.app.service.tools.MenuTools.showAvailableOperations;

class ProducerMenu {

    private static final String TABLE_NAME = "producer";

    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private Scanner scanner = new Scanner(System.in);
    private State state;

    State printAvailableOperationsOnProducers() {
        showAvailableOperations(TABLE_NAME);

        switch (scanner.nextInt()) {
            case 1: {
                state = printAddProducer();
                break;
            }
            case 2: {
                state = printDeleteProducer();
                break;
            }
            case 3: {
                state = printUpdateProducer();
                break;
            }
            case 4: {
                state = printShowAllProducers();
                break;
            }
            case 5: {
                state = printChosenProducer();
                break;
            }
            case 0: {
                state = INIT;
                scanner.nextLine();
                break;
            }
            default: {
                System.out.println("Wrong answer!");
                state = PRODUCER;
                scanner.nextLine();
                break;
            }
        }
        return state;
    }

    private State printAddProducer() {
        System.out.println("Enter Producer name:");
        String name = scanner.nextLine();

        System.out.println("Available countries:");
        countryRepository.findAll()
                .forEach(s -> System.out.println(s.getId() + ". " + s.getName()));
        System.out.println("Choose country id");
        int countryId = scanner.nextInt();
        scanner.nextLine();

        producerRepository.add(Producer
                .builder()
                .name(name)
                .countryId(countryId)
                .build()
        );
        state = PRODUCER;
        return state;
    }

    private State printDeleteProducer() {
        System.out.println("Choose Producer id from list to delete:");
        producerRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Producer::getId))
                .forEach(x -> System.out.println(x.getId() + ". " + x.getName()));
        System.out.println("0 - Go back");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            state = PRODUCER;
            return state;
        } else {
            producerRepository.delete(choice);
            state = PRODUCER;
            return state;
        }
    }

    private State printUpdateProducer() {
        producerRepository
                .findAll()
                .forEach(System.out::println);

        System.out.println("Choose id:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter new Producer name:");
        String name = scanner.nextLine();

        System.out.println("Enter new Producer country id:");
        int countryId = scanner.nextInt();
        scanner.nextLine();

        producerRepository.update(Producer
                .builder()
                .id(choice)
                .name(name)
                .countryId(countryId)
                .build()
        );
        state = PRODUCER;
        return state;
    }

    private State printShowAllProducers() {
        System.out.println("1 - show all Producers sorted by name");
        System.out.println("2 - show all Producers sorted by id");
        System.out.println("0 - go back");

        switch (scanner.nextInt()) {
            case 1: {
                printProducersSortedByName();
                break;
            }
            case 2: {
                printProducersSortedById();
                break;
            }
            case 0: {
                state = PRODUCER;
                break;
            }
            default: {
                System.out.println("Wrong answer");
                state = PRODUCER;
                break;
            }
        }

        return state;
    }

    private State printProducersSortedById() {
        producerRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Producer::getId))
                .forEach(System.out::println);
        System.out.println("0 - Go back");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            state = PRODUCER;
            return state;
        } else {
            while (choice != 0) {
                System.out.println("Press 0 to go back");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            state = PRODUCER;
        }
        return state;
    }

    private State printProducersSortedByName() {
        producerRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Producer::getName))
                .forEach(System.out::println);
        System.out.println("0 - Go back");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            state = PRODUCER;
            return state;
        } else {
            while (choice != 0) {
                System.out.println("Press 0 to go back ");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            state = PRODUCER;
        }
        return state;
    }

    private State printChosenProducer() {
        System.out.println("1 - show Producer with chosen id");
        System.out.println("2 - show Producer with chosen name");
        System.out.println("0 - bo back");

        switch (scanner.nextInt()) {
            case 1: {
                printProducerWithChosenId();
                break;
            }
            case 2: {
                printProducerWithChosenName();
                break;
            }
            case 0: {
                state = PRODUCER;
                break;
            }
            default: {
                System.out.println("Wrong answer");
                state = PRODUCER;
                break;
            }
        }

        return state;
    }

    private State printProducerWithChosenId() {
        System.out.println("Enter id:");
        System.out.println(producerRepository
                .findOneById(scanner.nextInt())
                .orElseThrow(() -> new NullPointerException("NO PRODUCER WITH CHOSEN ID"))
        );
        state = PRODUCER;
        return state;
    }

    private State printProducerWithChosenName() {
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        int id = -1;
        for (Producer c : producerRepository.findAll()) {
            if (name.equals(c.getName())) {
                id = c.getId();
                break;
            }
        }
        System.out.println(producerRepository
                .findOneById(id)
                .orElseThrow(() -> new NullPointerException("NO PRODUCER WITH CHOSEN ID"))
        );
        state = PRODUCER;
        return state;
    }

}
