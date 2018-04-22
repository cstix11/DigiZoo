import java.util.Scanner;

public class DigiZooApp {

    public static void main(String[] args) {
        Zoo zoo = new Zoo(); // This is our zoo

        Scanner input = new Scanner(System.in); // User input

        String in; // Temp for storing user input
        String name; // Temp for storing pet name
        String type; // Temp for storing pet type

        String level = ""; // Controls which menu options are displayed

        printInstructions(); // What it says
        System.out.println("Ready? (y/n)");
        in = input.nextLine();
        quitChecker(in);

        if (in.equalsIgnoreCase("y")) { // If ready - play (level = "main")
            System.out.println("Then let's go...\n\n\n");
            level = "main";
        } else {                                    // Otherwise, end the program
            System.out.println("Ok, just restart the program when you're ready");
            System.exit(0);
        }

        game: while(true) { // Labeled infinite loop - can be broken by typing "Quit" (hard stop) or selecting Quit (option 9 - soft stop)
            switch (level) {
                case "main": // Main menu options text
                    System.out.println(zoo.toString());
                    System.out.println("\nMain Menu:" +
                            "\n\t1) Add a pet" +
                            "\n\t2) Remove a pet" +
                            "\n\t3) Display pets" +
                            "\n\t4) Select a pet" +
                            "\n\t5) Quick-view a pet" +
                            "\n\t6) Clean up the zoo" +
                            "\n\t7) Next turn" +
                            "\n\t8) Display the instructions" +
                            "\n\t9) Quit");
                    in = input.nextLine();
                    quitChecker(in);
                    switch (in) { // Checking which option was selected
                        case "1": level = "add";
                            break;
                        case "2": level = "remove";
                            break;
                        case "3": level = "display";
                            break;
                        case "4": level = "select";
                            break;
                        case "5": level = "view";
                            break;
                        case "6":
                            if (zoo.doTask()) {
                                System.out.println("Zoo will be cleaned on next tick");
                                zoo.clean();
                            } else {
                                System.out.println("No tasks remain for this turn");
                            }
                            break;
                        case "7": zoo.tick();
                            break;
                        case "8": printInstructions();
                            break;
                        case "9": System.out.println("\nThanks for playing!");
                            break game; // Breaks from game loop
                    }
                    break;
                case "add":
                    System.out.println("What type of animal would you like to add? (Cat, Dog, Lion, Tiger, Wolf)");
                    in = input.nextLine();
                    quitChecker(in);
                    if (in.equalsIgnoreCase("cat") ||       // Must add one of available pets
                            in.equalsIgnoreCase("dog") ||
                            in.equalsIgnoreCase("lion") ||
                            in.equalsIgnoreCase("tiger") ||
                            in.equalsIgnoreCase("wolf")) {
                        type = in.toLowerCase();
                    } else {
                        System.out.println("Sorry, that's not a valid pet...");
                        break;
                    }
                    if (zoo.doTask()) { // Must use doTask() here in case invalid pet entered
                        System.out.println("What would you like to name your " + type + "?");
                        name = input.nextLine();
                        quitChecker(name);
                        switch (type) {
                            case "cat":
                                zoo.addPet(new Cat(name));
                                break;
                            case "dog":
                                zoo.addPet(new Dog(name));
                                break;
                            case "lion":
                                zoo.addPet(new Lion(name));
                                break;
                            case "tiger":
                                zoo.addPet(new Tiger(name));
                                break;
                            case "wolf":
                                zoo.addPet(new Wolf(name));
                                break;
                        }
                        System.out.println("Add another? (y/n)");
                        in = input.nextLine();
                        quitChecker(in);
                        if (!in.equalsIgnoreCase("y")) {
                            level = "main";
                        }
                    } else {
                        System.out.println("No tasks remain for this turn");
                        level = "main";
                    }
                    break;
                case "remove":
                    if (zoo.doTask()) {
                        System.out.print("Enter the name of the animal you would like to remove: ");
                        in = input.nextLine();
                        quitChecker(in);
                        zoo.removePet(in);
                        System.out.println("Remove another? (y/n)");
                        in = input.nextLine();
                        quitChecker(in);
                        if (!in.equalsIgnoreCase("y")) {
                            level = "main";
                        }
                    } else {
                        System.out.println("No tasks remain for this turn");
                        level = "main";
                    }
                    break;
                case "display":
                    System.out.println("What would you like to display?" +
                            "\n\t1) All pets" +
                            "\n\t2) All domesticated" +
                            "\n\t3) All feral" +
                            "\n\t4) All canines" +
                            "\n\t5) All felines");
                    in = input.nextLine();
                    quitChecker(in);
                    switch (in) {
                        case "1": zoo.displayAll();
                            break;
                        case "2": zoo.displayDomestic();
                            break;
                        case "3": zoo.displayFeral();
                            break;
                        case "4": zoo.displayCanines();
                            break;
                        case "5": zoo.displayFelines();
                            break;
                    }
                    System.out.println("Done? (y/n)");
                    in = input.nextLine();
                    quitChecker(in);
                    if (in.equals("y")) {
                        level = "main"; // Return to main menu after displaying
                    }
                    break;
                case "select":
                    System.out.println("Which pet?");
                    in = input.nextLine();
                    quitChecker(in);
                    name = in;
                    if (zoo.getPet(name) == null) {
                        System.out.println("That pet could not be found");
                        level = "main";
                        break;
                    }
                    type = zoo.getPet(name).getType();
                    while (true) { // Loop through pet's options - broken by typing "Quit" (hard STOP) or selecting Exit (soft BREAK)
                        System.out.println(zoo.getPetInfo(name));
                        System.out.println("\t1) Exit" +
                                "\n\t2) Feed");
                        if (type.equals("Cat")) { // Only cats and dogs can be played with
                            System.out.println("\t3) Play");
                        } else if (type.equals("Dog")) { // Only dogs can be walked
                            System.out.println("\t3) Play");
                            System.out.println("\t4) Walk");
                        }
                        in = input.nextLine();
                        quitChecker(in);
                        if (in.equals("1")) { // If exit selected, go back to main menu
                            level = "main";
                            break;
                        } else if (zoo.doTask()) { // If task available
                            if (zoo.getPet(name).sleeping) {
                                System.out.println(name + " is sleeping. Try again later.");
                            } else {
                                switch (in) {
                                    case "2":
                                        zoo.getPet(name).feed();
                                        System.out.println(name + " will be fed on the next turn");
                                        break;
                                    case "3":
                                        try {                                           // Because the zoo class refers to
                                            if (type.equals("Cat")) {                   // all pets as VirtualPet objects,
                                                Cat cat = (Cat) zoo.getPet(name);       // cats and dogs must be downcast
                                                cat.play();                             // in order to do their specific
                                            } else if (type.equals("Dog")) {            // tasks.
                                                Dog dog = (Dog) zoo.getPet(name);
                                                dog.play();
                                            }
                                            System.out.println(name + " will be played with next turn");
                                        } catch (ClassCastException e) {
                                            e.printStackTrace();
                                            System.exit(1);
                                        }
                                        break;
                                    case "4":
                                        try {
                                            Dog dog = (Dog) zoo.getPet(name);
                                            dog.walk();
                                            System.out.println(name + " will be walked next turn");
                                        } catch (ClassCastException e) {
                                            e.printStackTrace();
                                            System.exit(1);
                                        }
                                        break;
                                }
                            }
                        } else {
                            System.out.println("No tasks remain for this turn");
                            level = "main";
                        }
                    }
                    break;
                case "view":
                    System.out.println("Which pet?");
                    in = input.nextLine();
                    quitChecker(in);
                    if (zoo.getPetInfo(in) == null) {
                        System.out.println("That pet could not be found");
                    } else {
                        System.out.println(zoo.getPetInfo(in));
                    }
                    level = "main";
                    break;
            }
        }
    }

    private static void printInstructions() {
        System.out.println("\nWelcome to the DigiZoo!\n\nHere's some info:" +
                "\n\tYou are in charge of taking care of every animal in the zoo." +
                "\n\tEach game tick, you have a certain number of game tasks you can perform." +
                "\n\tThe number of tasks increases every 10 game ticks UNLESS the amount of waste in the facility is" +
                "\n\tgreater than 5 times the amount of 10 ticks passed." +
                "\n\tYou can add as many pets as you want but remember: you have to take care of them." +
                "\n\tOh and you can quit anytime by typing \"Quit\"\n");
    }

    private static void quitChecker(String input) {
        if (input.equals("Quit")) {
            System.exit(0);
        }
    }
}
