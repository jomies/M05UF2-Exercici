package application.model;

public class Orc extends Fighter{
    public Orc(String nom) {
        super(nom, 24, 22, 23, 16, 21);
        this.victories = 0;
        this.defeats = 0;
    }

    @Override
    public boolean fight(GameCharacter opponent) {
        return this.getStrength() > opponent.getStrength();
    }
    @Override
    public String toString() {
        return String.format("Orc  {%s}",super.toString());
    }}
