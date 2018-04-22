import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Zoo {

    private int waste; // How much waste is on cite
    private int tasks; // How many things you can do in a turn
    private int currTasks; // How many tasks you have now
    private int ticks; // How many game ticks have passed

    private boolean clean; // If the zoo is to be cleaned

    private HashMap<String, ArrayList<VirtualPet>> cages = new HashMap<>(); // Cages of animals
    private TreeMap<String, VirtualPet> petDB = new TreeMap<>(); // Database that associates a name with a pet

    public Zoo() {
        waste = 0;
        tasks = 1;
        currTasks = tasks;
        ticks = 0;

        cages.put("Domesticated", new ArrayList<>()); // Initializes cages
        cages.put("Lions", new ArrayList<>());
        cages.put("Tigers", new ArrayList<>());
        cages.put("Wolves", new ArrayList<>());
    }

    private void checkWaste() {
        for (ArrayList<VirtualPet> virtualPets : cages.values()) {  // Recalculates waste on every call
            for (VirtualPet virtualPet : virtualPets) {             // If any animals are over 100 waste,
                if (virtualPet.getWaste() >= 100) {                 // reset and add one to facility
                    waste++;                                        // waste (soiled self)
                    virtualPet.setWaste(0);
                }
            }
        }
    }

    private void checkHunger() { // If any pet's hunger drops below 0, they starve - remove them from petDB and their cage
        for (VirtualPet virtualPet : new ArrayList<>(cages.get("Domesticated"))) {
            if (virtualPet.getHunger() <= 0) {
                System.out.println(virtualPet.getName() + " the " + virtualPet.getType() + " has starved to death");
                petDB.remove(virtualPet.getName());
                cages.get("Domesticated").remove(virtualPet);
            }
        }
        for (VirtualPet virtualPet : new ArrayList<>(cages.get("Lions"))) {
            if (virtualPet.getHunger() <= 0) {
                System.out.println(virtualPet.getName() + " the " + virtualPet.getType() + " has starved to death");
                petDB.remove(virtualPet.getName());
                cages.get("Lions").remove(virtualPet);
            }
        }
        for (VirtualPet virtualPet : new ArrayList<>(cages.get("Tigers"))) {
            if (virtualPet.getHunger() <= 0) {
                System.out.println(virtualPet.getName() + " the " + virtualPet.getType() + " has starved to death");
                petDB.remove(virtualPet.getName());
                cages.get("Tigers").remove(virtualPet);
            }
        }
        for (VirtualPet virtualPet : new ArrayList<>(cages.get("Wolves"))) {
            if (virtualPet.getHunger() <= 0) {
                System.out.println(virtualPet.getName() + " the " + virtualPet.getType() + " has starved to death");
                petDB.remove(virtualPet.getName());
                cages.get("Wolves").remove(virtualPet);
            }
        }
    }

    public String getPetInfo(String name) { // Runs toString method of specified pet
        try {
            return petDB.get(name).toString();
        } catch (NullPointerException e) {
            System.out.println("That pet could not be found");
            return null;
        }
    }

    public VirtualPet getPet(String name) {
        try {
            return petDB.get(name);
        } catch (NullPointerException e) {
            System.out.println("That pet could not be found");
            return null;
        }
    }

    public void clean() {
        clean = true;
    } // Will clean zoo on next tick if called

    public boolean doTask() { // If you have tasks left use one
        if (currTasks <= 0) {
            return false;
        } else {
            currTasks--;
            return true;
        }
    }

    public void tick() {
        checkWaste();
        ticks++;
        for (ArrayList<VirtualPet> virtualPets : cages.values()) {  // Ticks every animal in the facility
            for (VirtualPet virtualPet : virtualPets) {
                virtualPet.tick();
            }
        }
        checkHunger();
        if (tasks % 10 == 0 && waste <= 5*(Math.floor(tasks / 10) + 1)) { // +1 available task every 10 ticks
            tasks++;
        }
        if (clean) {
            waste = 0;
            clean = false;
        }
        currTasks = tasks;
    }

    public void addPet(VirtualPet pet) {
        petDB.put(pet.getName(), pet);          // First, add pet to the database
        if (pet instanceof Domesticated) {      // Then, sort the pet into the proper cage
            cages.get("Domesticated").add(pet);
        } else if (pet instanceof Lion) {
            cages.get("Lions").add(pet);
        } else if (pet instanceof Tiger) {
            cages.get("Tigers").add(pet);
        } else if (pet instanceof Wolf) {
            cages.get("Wolves").add(pet);
        }
        System.out.println(pet.getName() + " the " + pet.getType() + " added");
    }

    public void removePet(String petName) {
        String type;
        try {
            type = petDB.remove(petName).getType();                              // Remove the pet from the database
        } catch (NullPointerException e) {
            System.out.println("That pet could not be found");
            return;
        }
        switch (type) {                                                          // Then remove from its cage
            case "Cat":
            case "Dog":
                for (int i = 0; i < cages.get("Domesticated").size(); i++) {
                    if (cages.get("Domesticated").get(i).getName().equals(petName)) {
                        cages.get("Domesticated").remove(i);
                    }
                }
                break;
            case "Lion":
                for (int i = 0; i < cages.get("Lions").size(); i++) {
                    if (cages.get("Lions").get(i).getName().equals(petName)) {
                        cages.get("Lions").remove(i);
                    }
                }
                break;
            case "Tiger":
                for (int i = 0; i < cages.get("Tigers").size(); i++) {
                    if (cages.get("Tigers").get(i).getName().equals(petName)) {
                        cages.get("Tigers").remove(i);
                    }
                }
                break;
            case "Wolf":
                for (int i = 0; i < cages.get("Wolves").size(); i++) {
                    if (cages.get("Wolves").get(i).getName().equals(petName)) {
                        cages.get("Wolves").remove(i);
                    }
                }
                break;
        }
        System.out.println(petName + " removed");
    }

    public void displayAll() {
        for (String name : petDB.descendingKeySet()) {      // Loops through every pet in the database
            System.out.println("\t" + name + " the " + petDB.get(name).getType());
        }
    }

    public void displayDomestic() {
        for (VirtualPet virtualPet : cages.get("Domesticated")) { // Loops through every pet in "Domesticated" cage
            System.out.println("\t" + virtualPet.getName() + " the " + virtualPet.getType());
        }
    }

    public void displayFeral() {
        for (VirtualPet virtualPet : cages.get("Lions")) {      // Loops through everything not in "Domesticated"
            System.out.println("\t" + virtualPet.getName() + " the " + virtualPet.getType());
        }
        for (VirtualPet virtualPet : cages.get("Tigers")) {
            System.out.println("\t" + virtualPet.getName() + " the " + virtualPet.getType());
        }
        for (VirtualPet virtualPet : cages.get("Wolves")) {
            System.out.println("\t" + virtualPet.getName() + " the " + virtualPet.getType());
        }
    }

    public void displayCanines() {
        for (VirtualPet virtualPet : cages.get("Domesticated")) {   // Any canines in the "Domesticated" cage
            if (virtualPet instanceof Canine) {
                System.out.println("\t" + virtualPet.getName() + " the " + virtualPet.getType());
            }
        }
        for (VirtualPet virtualPet : cages.get("Wolves")) {         // Also wolves
            System.out.println("\t" + virtualPet.getName() + " the " + virtualPet.getType());
        }
    }

    public void displayFelines() {
        for (VirtualPet virtualPet : cages.get("Domesticated")) {   // Any felines in the "Domesticated" cage
            if (virtualPet instanceof Feline) {
                System.out.println("\t" + virtualPet.getName() + " the " + virtualPet.getType());
            }
        }
        for (VirtualPet virtualPet : cages.get("Lions")) {          // Also lions and tigers
            System.out.println("\t" + virtualPet.getName() + " the " + virtualPet.getType());
        }
        for (VirtualPet virtualPet : cages.get("Tigers")) {
            System.out.println("\t" + virtualPet.getName() + " the " + virtualPet.getType());
        }
    }

    @Override
    public String toString() {  // General zoo info - for pet info use 'VirtualPet.toString()'
        return "\n\nTicks: " + ticks + "\tWaste on Cite: " + waste + "\tTasks: " + currTasks + "/" + tasks;
    }
}
