package application.model;

public class Huma extends GameCharacter {
    public Huma(String nom) {
            super(nom, 14, 19, 16, 25, 24);
        }

    @Override
    public String toString() {
        return String.format("Huma {%s}",super.toString());
    }
}
