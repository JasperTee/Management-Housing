import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class AppController {
    AppModel model;

    AppController(AppModel model){
        this.model = model;
    }

    public String loginRequest(String username, String password){
        if (model.login(username,password) && model.isAdmin()){
            return "Admin";
        }  else if (model.login(username,password)){
            return "User";
        } else {
            return "Invalid";
        }
    }
    public SimpleStringProperty userProfile(){
        return new SimpleStringProperty(AppModel.getCurrentUser().toString());
    }
    public SimpleIntegerProperty numberOfMails(){
        return new SimpleIntegerProperty(AppModel.getCurrentUser().getMails().size());
    }
    public SimpleStringProperty nameCurrentUser(){
        return AppModel.getCurrentUser().getPropertyName();
    }
    public SimpleObjectProperty<Address> addressCurrentUser(){
        return AppModel.getCurrentUser().getPropertyAddress();
    }
    public SimpleStringProperty phoneNumberCurrentUser(){
        return AppModel.getCurrentUser().getPropertyPhoneNumber();
    }
    public SimpleStringProperty usernameCurrentUser(){return AppModel.getCurrentUser().getPropertyUsername();}
    public SimpleStringProperty passwordCurrentUser(){return AppModel.getCurrentUser().getPropertyPassword();}
    public void setNameCurrentUser(String name){
        AppModel.getCurrentUser().setName(name);
    }
    public List<String> signUpRequest(String name, String address,String phoneNumber,String username, String password, String confirmPassword, String role){
        return model.signUp(name,address,convertStringToInt(phoneNumber),username,password,confirmPassword,role);
    }
    private int convertStringToInt(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        if ("-".equals(s)) {
            return 0;
        }
        return Integer.parseInt(s); // Convert string into integer
    }

}
