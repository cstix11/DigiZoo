public class Dog extends VirtualPet implements Canine, Domesticated {

    protected boolean play;
    protected boolean walk;

    public Dog(String name) {
        super(name, "Dog");
        play = false;
        walk = false;
    }

    @Override
    public void play() {
        play = true;
    }

    public void walk() {
        walk = true;
    }

    @Override
    public void tick() {
        happiness = (int) (1.176 * (((hunger * 0.15) + (social * 0.3) +(energy * 0.2) + ((100 - waste) * 0.2))));

        if (!sleeping) {
            if (feed) {
                hunger = 100;
                waste += 20;
                feed = false;
            } else {
                hunger -= (10 + (0.3*(100 - happiness)));
            }
            if (play) {
                hunger -= 10;
                energy -= 10;
                social = 100;
                waste += 10;
                play = false;
            } else if (walk) {
                hunger -= 10;
                energy -= 10;
                social = 100;
                waste = 0;
                walk = false;
            } else {
                social -= (5 + (0.3*(100 - happiness)));
                waste += (10 + (0.3*(100 - happiness)));
            }
            energy -= (5 + (0.3*(100 - happiness)));
        } else {
            hunger -= 1;
            waste += 1;
            social -= 1;
            energy += (10 + (0.03*(100 - happiness)));
            if (energy >= 100) {
                sleeping = false;
            }
        }
    }
}
