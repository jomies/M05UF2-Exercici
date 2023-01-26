package application.controller;

import application.NoSelectableCharacterException;
import application.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Set;

public class combatController {
    @FXML
    Button bt_fight;
    @FXML
    ComboBox cb_fighter;
    @FXML
    ComboBox cb_opponent;
    @FXML
    ImageView img_fighter;
    @FXML
    ImageView img_opponent;
    @FXML
    Label lb_combatResult;
    String selectedFighter = "";
    String selectedOpponent = "";

    HashMap<String, GameCharacter> charactersMap;

    @FXML
    protected void initialize() {
        charactersMap = new HashMap<>();
        charactersMap.put("Charles", new Huma("Charles"));
        charactersMap.put("Eric", new Huma("Eric"));
        charactersMap.put("Frederic", new Troll("Frederic"));
        charactersMap.put("Alexander", new Orc("Alexander"));
        charactersMap.put("Joane", new Orc("Joane"));
        charactersMap.put("Lara", new Troll("Lara"));
        charactersMap.put("Sebastian", new Nan("Sebastian"));
        charactersMap.put("Henry", new Nan("Henry"));
        reset();
    }


    private void fillOpponentsCombo(Set<String> characters) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                cb_opponent.getItems().clear();
                for (String nom : characters) {
                    cb_opponent.getItems().add(nom);
                }
            }
        });
    }

    private void fillFightersCombo(Set<String> characters) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                cb_fighter.getItems().clear();
                for (String nom : characters) {
                    if ((charactersMap.get(nom) instanceof Fighter))
                        cb_fighter.getItems().add(nom);
                }
            }
        });
    }


    public void fight() {
        if (((Fighter) charactersMap.get(selectedFighter)).fight(charactersMap.get(selectedOpponent))) {
            lb_combatResult.setText(String.format("%s wins / %s loses", selectedFighter, selectedOpponent));
            img_opponent.setOpacity(0.5);
        } else {
            lb_combatResult.setText(String.format("%s loses / %s wins", selectedFighter, selectedOpponent));
            img_fighter.setOpacity(0.5);
        }
        writeFight(lb_combatResult.getText());
    }

    private void writeFight(String text) {
        Path path = Paths.get("src/main/resources/application/files/register.txt");
        BufferedWriter bufferWriter = null;
        try {
            if (new File(String.valueOf(path)).exists())
                bufferWriter = Files.newBufferedWriter(path, java.nio.charset.StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            else
                bufferWriter = Files.newBufferedWriter(path, java.nio.charset.StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
            bufferWriter.write(text);
            bufferWriter.newLine();
            bufferWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void resetComboOpponent() {
        selectedOpponent = "";
        fillOpponentsCombo(charactersMap.keySet());
        Image missingImage = new Image(new File(String.format("src/main/resources/application/images/noImage.png")).toURI().toString());
        img_opponent.setImage(missingImage);
        selectedOpponent = "";
    }

    private void resetComboFighter() {
        selectedFighter = "";
        fillFightersCombo(charactersMap.keySet());
        Image missingImage = new Image(new File(String.format("src/main/resources/application/images/noImage.png")).toURI().toString());
        img_fighter.setImage(missingImage);

    }

    public void reset() {
        resetComboOpponent();
        resetComboFighter();
        check();
    }


    public void fighterSelection() {
        if (cb_fighter.getValue() != null) {
            try {
                if (cb_fighter.getValue().toString().equals(selectedOpponent)) {
                    throw new NoSelectableCharacterException();
                } else {
                    selectedFighter = (String) cb_fighter.getValue();
                }
                Image image = new Image(new File(String.format("src/main/resources/application/images/%s.png", selectedFighter)).toURI().toString());
                if (!image.isError()) {
                    img_fighter.setImage(image);
                } else {
                    Image missingImage = new Image(new File(String.format("src/main/resources/application/images/noImage.png")).toURI().toString());
                    img_fighter.setImage(missingImage);
                }
            } catch (NoSelectableCharacterException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
                resetComboFighter();
            } finally {
                check();
            }
        }
    }

    public void opponentSelection() {
        if (cb_opponent.getValue() != null) {
            try {
                if (cb_opponent.getValue().toString().equals(selectedFighter)) {
                    throw new NoSelectableCharacterException();
                } else {
                    selectedOpponent = (String) cb_opponent.getValue();
                }
                Image image = new Image(new File(String.format("src/main/resources/application/images/%s.png", selectedOpponent)).toURI().toString());
                if (!image.isError()) {
                    img_opponent.setImage(image);
                } else {
                    Image missingImage = new Image(new File(String.format("src/main/resources/application/images/noImage.png")).toURI().toString());
                    img_opponent.setImage(missingImage);
                }

            } catch (NoSelectableCharacterException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
                resetComboOpponent();
            } finally {
                check();
            }
        }
    }

    public void check() {
        img_fighter.setOpacity(1);
        img_opponent.setOpacity(1);
        lb_combatResult.setText("   /   ");
        if (selectedOpponent.equals("") || selectedFighter.equals(""))
            bt_fight.setDisable(true);
        else
            bt_fight.setDisable(false);
    }


}
