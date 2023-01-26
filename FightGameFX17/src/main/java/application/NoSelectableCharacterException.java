package application;

public class NoSelectableCharacterException extends Exception{
    public NoSelectableCharacterException() {
        super("Can't select the same character in both sides!!");
    }
}
