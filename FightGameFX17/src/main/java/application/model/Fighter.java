package application.model;

public abstract class Fighter extends GameCharacter {
    int victories;
    int defeats;
    public abstract boolean fight(GameCharacter opponent);

    public Fighter(String nom, int strength, int agility, int endurance, int intellect, int spirit) {
        super(nom, strength, agility, endurance, intellect, spirit);
    }
    protected void win(){
        victories ++;
    }
    protected void lose(){
        loseLive();
        defeats++;
    }

    @Override
    public String toString() {
        return super.toString() +
                " victories=" + victories +
                ", defeats=" + defeats
                ;
    }
}
