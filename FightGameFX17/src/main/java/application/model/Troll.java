package application.model;

public class Troll extends Fighter{

    public Troll(String nom) {
        super(nom, 23, 21, 24, 15, 18);
        this.victories = 0;
        this.defeats = 0;
    }

    @Override
    public boolean fight(GameCharacter opponent) {
        return this.getEndurance() > opponent.getEndurance();
    }
    @Override
    public String toString() {
        return String.format("Troll{%s}",super.toString());
    }
}
