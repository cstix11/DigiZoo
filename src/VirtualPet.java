public abstract class VirtualPet {
    protected int hunger;
    protected int waste;
    protected int social;
    protected int energy;
    protected int happiness; // Changes depending on stats - dictates how fast stats passively decay

    protected boolean feed; // Will add hunger if true (if instructed to eat)
    protected boolean sleeping; // Won't do anything while sleeping - sleeps until energy >= 100

    private String name;
    private String type;

    public VirtualPet(String name, String type) {
        hunger = 100;
        waste = 0;
        social = 100;
        energy = 100;

        feed = false;
        sleeping = false;

        this.name = name;
        this.type = type;
    }

    public int getHunger() {
        return hunger;
    }

    public int getWaste() {
        return waste;
    }

    public void setWaste(int waste) {
        this.waste = waste;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void feed() {
        feed = true;
    }

    public abstract void tick();

    @Override
    public String toString() { // Displays stats for pet
        return "\n" + name + " the " + type + ":" +
                "\t\tHunger: " + hunger + "/100" +
                "\t\tWaste: " + waste + "/100" +
                "\t\tSocial: " + social + "/100" +
                "\t\tEnergy: " + energy + "/100";
    }
}