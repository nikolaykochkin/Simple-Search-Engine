package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Program {

    private SearchEngine searchEngine;

    public Program(String[] args) {

        String fileName = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--data")) fileName = args[i + 1];
        }

        searchEngine = new SearchEngine(readFile(fileName));
    }

    private List<String> readFile(String fileName) {
        List<String> result = new ArrayList<>();
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                result.add(scanner.nextLine());
            }
        } catch (RuntimeException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void startProgram() {

        String menu =
                "\n=== Menu ===\n"
                        + "1. Find a person\n"
                        + "2. Print all people\n"
                        + "0. Exit";

        boolean handle = true;

        Scanner scanner = new Scanner(System.in);

        while (handle) {
            System.out.println(menu);
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    findPersons(scanner);
                    break;
                case 2:
                    System.out.println(searchEngine.toString());
                    break;
                case 0:
                    handle = false;
                    break;
                default:
                    System.out.println("\nIncorrect option! Try again.");
            }
        }
        System.out.println("Bye!");
        scanner.close();
    }

    private void findPersons(Scanner scanner) {
        System.out.println("\nSelect a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.nextLine();
        switch (strategy) {
            case "ALL":
                searchEngine.setSearchStrategy(new SearchAll());
                break;
            case "ANY":
                searchEngine.setSearchStrategy(new SearchAny());
                break;
            case "NONE":
                searchEngine.setSearchStrategy(new SearchNone());
                break;
            default:
                System.out.println("\nMatching strategy not found.");
                return;
        }
        System.out.println("\nEnter a name or email to search all suitable people.");
        String query = scanner.nextLine();
        System.out.println(searchEngine.find(query));
    }

}
