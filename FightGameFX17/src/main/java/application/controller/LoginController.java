
package application.controller;
 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LoginController {
    @FXML private Text errorText;
    @FXML private TextField userField;
    @FXML private PasswordField passwordField;
    
    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        errorText.setText("");
        if(userField.getText().equals("") || passwordField.getText().equals("")){
            errorText.setText("Fields cannot be empty");
        }else{
            if(login())
            {
                try {
                    Stage stage = (Stage) errorText.getScene().getWindow();
                    Parent root = FXMLLoader.load(LoginController.class.getResource("combat.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                errorText.setText("Wrong user or password");
            }
        }
    }
    @FXML public void handleRegisterButtonAction(ActionEvent event) throws IOException{
        //Register Button
        String usernamee = userField.getText();
        String passwordd = passwordField.getText();
        errorText.setText("");

        Path pathName = Paths.get("/Users/jordimiroesteve/Downloads/FightGameFX17/src/main/resources/application/files/users.txt");
        BufferedWriter bufferWriter = null;

        try {
            if (new File(String.valueOf(pathName)).exists()) {
                bufferWriter = Files.newBufferedWriter(pathName, java.nio.charset.StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                bufferWriter.write(usernamee + ":" + passwordd);
                bufferWriter.newLine();
                bufferWriter.flush();

            }else if(usernamee.equals(part[0])&&passwordd.equals(part[1])){
                
            }
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader("/Users/jordimiroesteve/Downloads/FightGameFX17/src/main/resources/application/files/users.txt"));
             FileWriter fw = new FileWriter("/Users/jordimiroesteve/Downloads/FightGameFX17/src/main/resources/application/files/users.txt")) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (!parts[0].equals(parts[1])) {
                    fw.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }








    }





    public boolean login() throws IOException {
        String username = userField.getText();
        String password = passwordField.getText();
        Path path = Paths.get("src/main/resources/application/files/login");
        try {
            BufferedReader bufferedReader= Files.newBufferedReader(path, java.nio.charset.StandardCharsets.UTF_8);
            String linea;
            while ((linea=bufferedReader.readLine())!=null){
                String[] userData = linea.split(":");
                String uname =userData[0];
                String upswd = userData[1];
                System.out.println("uname");
                if (username.equals(uname) && password.equals(upswd)) {
                    return true;
                }else{
                //Avís dades introduïdes incorrectament
                    System.out.println("ERROR: Insert Correct values!");
                    System.out.println("Vuelve a introducir los datos");
                }
            }
        } catch (IOException ex) {
            System.err.println("I/O Error: "+ex);
        }
        userField.setText("");
        passwordField.setText("");
        return false;
    }
}
