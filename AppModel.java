import java.util.*;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

enum Address {
    TOWN_HALL,
    WYNYARD,
    CIRCULAR_QUAY,
    ST_JAMES,
    MUSEUM,
    CENTRAL,
    REDFERN,
    NEWTOWN,
    STANMORE,
    PETERSHAM,
    LEWISHAM,
    SUMMER_HILL,
    ASHFIELD,
    CROYDON,
    BURWOOD,
    STRATHFIELD,
    NOT_PREFER,
    INIDENTIFY;
}
enum reportFields {
    SCAM,
    WRONG_INFORMATION,
    SPAM,
    OTHERS;
}
enum FurnitureOptions {
    NONE_FURNITURE,
    FULL_FURNITURE(true);
 
    boolean goodCondition;
    FurnitureOptions(boolean goodCondition){
    this.goodCondition = goodCondition;
    }
    FurnitureOptions(){}
 }

interface systemCommunication {
    void sendmessage();
    void report();
}
interface accountManagement {
    void updatePassword();
    void updateProfile();

}

public class AppModel {
    private static User currentUser;
    private static Admin admin;
    private static Map<String,User> users;
    private static Map<String,Property> properties;
    private static Map<String, Address> area;
    private static Map<String, reportFields> reports;

    public AppModel(){
        users = new HashMap<>();
        properties = new HashMap<>();
        area = new HashMap<>();
        reports = new HashMap<>();

        List<User> listUsers = new ArrayList<>(Arrays.asList(
                        // Agents
                new Agent("Agent name", Address.CENTRAL, "0400000000", "a", "a"),
                new Agent("Adam West", Address.TOWN_HALL, "0412345671", "adamwest", "adampass123"),
                new Agent("Betty Young", Address.CIRCULAR_QUAY, "0412345672", "bettyyoung", "bettypass789"),
                new Agent("Carl Zane", Address.WYNYARD, "0412345673", "carlzane", "carlpass456"),
                new Agent("Diana Brown", Address.ST_JAMES, "0412345674", "dianabrown", "dianapass123"),
                new Agent("Evan Clark", Address.MUSEUM, "0412345675", "evanclark", "evanpass456"),
                new Agent("Fiona Davis", Address.CENTRAL, "0412345676", "fionadavis", "fionapass123"),
                new Agent("George Evans", Address.REDFERN, "0412345677", "georgeevans", "georgepass456"),
                new Agent("Hannah Fox", Address.NEWTOWN, "0412345678", "hannahfox", "hannahpass789"),
                new Agent("Ian Green", Address.STANMORE, "0412345679", "iangreen", "iangreen123"),
                new Agent("Jane Hall", Address.PETERSHAM, "0412345680", "janehall", "janepass456"),
                new Agent("Kyle Iron", Address.LEWISHAM, "0412345681", "kyleiron", "kylepass123"),
                // Real Estate Clients
                new Client("Tenant name", Address.CENTRAL, "0411111111", "b", "b"),
                new Client("Kathy Lewis", Address.LEWISHAM, "0412345691", "kathylewis", "kathypass123"),
                new Client("Larry Martin", Address.SUMMER_HILL, "0412345692", "larrymartin", "larrypass789"),
                new Client("Mona Nelson", Address.ASHFIELD, "0412345693", "monanelson", "monapass456"),
                new Client("Nina O'Brien", Address.CROYDON, "0412345694", "ninaobrien", "ninapass123"),
                new Client("Oscar Price", Address.BURWOOD, "0412345695", "oscarprice", "oscarpass456"),
                new Client("Paul Quinn", Address.STRATHFIELD, "0412345696", "paulquinn", "paulpass123"),
                new Client("Quincy Roberts", Address.NOT_PREFER, "0412345697", "quincyroberts", "quincy123"),
                new Client("Rachel Smith", Address.TOWN_HALL, "0412345698", "rachelsmith", "rachelpass456"),
                new Client("Steve Thomas", Address.CIRCULAR_QUAY, "0412345699", "stevethomas", "stevepass789"),
                new Client("Harry Phung", Address.CENTRAL, "0412345700", "harry", "123456789"),
                new Client("Tina Upton", Address.WYNYARD, "0412345701", "tinaupton", "tinapass456")
        ));
        List<Property> listProperties = new ArrayList<>(Arrays.asList(
                new House(300.5, Address.ASHFIELD,3, 2, 2, 3, 1, new Furniture(FurnitureOptions.FULL_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.NONE_FURNITURE, FurnitureOptions.NONE_FURNITURE), 3,true, 950),
                new Apartment(300.5, Address.ASHFIELD,2, 2, 2, 3, 1, new Furniture(FurnitureOptions.FULL_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.NONE_FURNITURE, FurnitureOptions.NONE_FURNITURE),12,304,50, 920),
                new House(450.0, Address.BURWOOD,4, 3, 3, 4, 2, new Furniture(FurnitureOptions.NONE_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.NONE_FURNITURE), 2,true, 1800),
                new Apartment(375.0, Address.CROYDON,3, 2, 1, 2, 1, new Furniture(FurnitureOptions.FULL_FURNITURE, FurnitureOptions.NONE_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.FULL_FURNITURE),1,23,30, 1650),
                new House(320.0, Address.STRATHFIELD,2, 1, 1, 2, 0, new Furniture(FurnitureOptions.FULL_FURNITURE, FurnitureOptions.NONE_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.NONE_FURNITURE), 1,false,  1400),
                new Apartment(500.0, Address.NEWTOWN,5, 4, 3, 4, 2, new Furniture(FurnitureOptions.FULL_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.NONE_FURNITURE, FurnitureOptions.FULL_FURNITURE),3,123,60, 1900),
                new House(285.0, Address.LEWISHAM,2, 1, 1, 1, 1, new Furniture(FurnitureOptions.NONE_FURNITURE, FurnitureOptions.NONE_FURNITURE, FurnitureOptions.NONE_FURNITURE, FurnitureOptions.NONE_FURNITURE),  0,false, 900),
                new Apartment(330.0, Address.SUMMER_HILL,2, 2, 2, 3, 1, new Furniture(FurnitureOptions.FULL_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.NONE_FURNITURE, FurnitureOptions.FULL_FURNITURE),1,356,45,1200),
                new House(460.0, Address.STANMORE,5, 3, 2, 4, 2, new Furniture(FurnitureOptions.FULL_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.NONE_FURNITURE), 2,false, 1750),
                new Apartment(410.0, Address.PETERSHAM,3, 2, 2, 2, 1, new Furniture(FurnitureOptions.FULL_FURNITURE, FurnitureOptions.NONE_FURNITURE, FurnitureOptions.NONE_FURNITURE, FurnitureOptions.FULL_FURNITURE),20,234,56,1600),
                new House(385.0, Address.CENTRAL,5, 3, 1, 3, 2, new Furniture(FurnitureOptions.FULL_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.NONE_FURNITURE),  1,false, 1950),
                new Apartment(470.0, Address.MUSEUM,4, 3, 2, 3, 1, new Furniture(FurnitureOptions.NONE_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.FULL_FURNITURE, FurnitureOptions.FULL_FURNITURE),34,563,67, 1850)
        ));

        area.put("town hall", Address.TOWN_HALL);
        area.put("wynyard", Address.WYNYARD);
        area.put("circular quay", Address.CIRCULAR_QUAY);
        area.put("st james", Address.ST_JAMES);
        area.put("museum", Address.MUSEUM);
        area.put("central", Address.CENTRAL);
        area.put("redfern", Address.REDFERN);
        area.put("newtown", Address.NEWTOWN);
        area.put("stanmore", Address.STANMORE);
        area.put("petersham", Address.PETERSHAM);
        area.put("lewisham", Address.LEWISHAM);
        area.put("summer hill", Address.SUMMER_HILL);
        area.put("ashfield", Address.ASHFIELD);
        area.put("croydon", Address.CROYDON);
        area.put("burwood", Address.BURWOOD);
        area.put("strathfield", Address.STRATHFIELD);
        area.put("not prefer", Address.NOT_PREFER);

        for (User user : listUsers){
            users.put(user.getUserID(), user);
        }
        for (Property property : listProperties) {
            properties.put(property.getPropertyID(), property);
        }
        for (int i = 0; i < listProperties.size() && i < listUsers.size(); i ++){
            if (listUsers.get(i) instanceof Agent){
                Agent user = (Agent) listUsers.get(i);
                properties.get(listProperties.get(i).getPropertyID()).setOwner(user);
                user.getInventory().put(listProperties.get(i).getPropertyID(), listProperties.get(i));
            }
        }
    }
    public static Admin getAdmin() {
        return admin;
    }
    public static Map<String, Address> getArea() {
        return area;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
    public static Map<String, Property> getProperties() {
        return properties;
    }
    public static Map<String, reportFields> getReports() {
        return reports;
    }
    public static Map<String, User> getUsers() {
        return users;
    }

    public boolean login(String username, String password){
        if (username.equals(Admin.getUsername())){
            if (password.equals(Admin.getPassword())){
                admin = new Admin();
                return true;
            }
        } else {
            for (User user : AppModel.getUsers().values()){
                if (username.equals(user.getUsername())){
                    if (password.equals(user.getPassword())){
                        currentUser = user;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public List<String> signUp(String name, String address, int phoneNumber, String username, String password,String confirmPassword, String role){
        List<String> errors = new ArrayList<>();
        if(checkPhoneNumber(phoneNumber).equals("Valid") && checkAddress(address) && checkUsername(username) && checkPassword(password,confirmPassword)){
            if (role.equals("Agent")){
                new Agent(name,area.get(address),"0" + phoneNumber,username,password);
            } else {
                new Agent(name,area.get(address),"0" + phoneNumber,username,password);
            }
            return errors;
        }
        if (!checkPhoneNumber(phoneNumber).equals("Valid")) {
            errors.add("Phone Number is " + checkPhoneNumber(phoneNumber) + "!!!");
        }
        if (!checkAddress(address)){
            errors.add("INVALID Address!!!");
        }
        if(!checkUsername(username)){
            errors.add("This username has been EXISTED!!!");
        }
        if (!checkPassword(password,confirmPassword)){
            errors.add("Password does not match");
        }
        return errors;
    }
    public boolean checkAddress(String address){
        for (String s : area.keySet()){
            if (address.equals(s)){
                return true;
            }
        }
        return false;
    }
    public String checkPhoneNumber(int phoneNumber){
        if(phoneNumber > 400000000  && phoneNumber < 500000000) {
            boolean isFound = false;
            String phoneNumberString = "0" + phoneNumber;
            for (User user : users.values()) {
                if (phoneNumberString.equals(user.getPhoneNumber())) {
                    isFound = true;
                    return "Existed";
                }
            }
            return "Valid";
        }
        return "Invalid";
    }
    public boolean checkUsername(String username){
        for (User user : users.values()){
            if (username.equals(user.getUsername())){
                return false;
            }
        }
        return true;
    }
    public boolean checkPassword(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }
    public boolean isAdmin(){
        return admin != null;
    }
}
class Furniture {
    private SimpleObjectProperty<FurnitureOptions> tivi;
    private SimpleObjectProperty<FurnitureOptions> desks;
    private SimpleObjectProperty<FurnitureOptions> sofa;
    private SimpleObjectProperty<FurnitureOptions> fridges;
    private  Map<String, FurnitureOptions> furnitureFULL;

    public Furniture(FurnitureOptions tivi, FurnitureOptions desks, FurnitureOptions sofa, FurnitureOptions fridges){
        this.tivi = new SimpleObjectProperty<>(tivi);
        this.desks = new SimpleObjectProperty<>(desks);
        this.sofa = new SimpleObjectProperty<>(sofa);
        this.fridges =new SimpleObjectProperty<>(fridges);

        furnitureFULL = new HashMap<>();
        if(tivi == FurnitureOptions.FULL_FURNITURE){
            furnitureFULL.put("Tivi", FurnitureOptions.FULL_FURNITURE);
        }
        if(desks == FurnitureOptions.FULL_FURNITURE){
            furnitureFULL.put("Desks", FurnitureOptions.FULL_FURNITURE);
        }
        if(sofa == FurnitureOptions.FULL_FURNITURE){
            furnitureFULL.put("Sofa", FurnitureOptions.FULL_FURNITURE);
        }
        if(fridges == FurnitureOptions.FULL_FURNITURE){
            furnitureFULL.put("Fridges", FurnitureOptions.FULL_FURNITURE);
        }
    }

    public Map<String, FurnitureOptions> getFurnitureFULL() {
        return furnitureFULL;
    }
    public String toString() {
        return "Furniture \n" + "Tivi=" + tivi + "\nDesks=" + desks + "\nSofa=" + sofa + "\nFridges=" + fridges;
    }
}
abstract class Property {
    private User owner;
    private Client[] tenants;
    private SimpleIntegerProperty numberOfTenants;
    private SimpleIntegerProperty countTenants;
    private SimpleIntegerProperty price;
    private final SimpleStringProperty propertyID;
    private SimpleDoubleProperty sizeOfProperty;
    private SimpleObjectProperty<Address> address;
    private SimpleIntegerProperty numberOfBedRooms;
    private SimpleIntegerProperty numberOfKitchens;
    private SimpleIntegerProperty numberOfBathRooms;
    private SimpleIntegerProperty carParkSlot;
    private Furniture furniture;

    // Comparators for sorting
    public static final Comparator<Property> comparatorSize = Comparator.comparing(Property::getSizeOfProperty);
    public static final Comparator<Property> comparatorBedrooms = Comparator.comparing(Property::getNumberOfBedRooms);
    public static final Comparator<Property> comparatorBathrooms = Comparator.comparing(Property::getNumberOfBathRooms);
    public static final Comparator<Property> comparatorKitchens = Comparator.comparing(Property::getNumberOfKitchens);
    public static final Comparator<Property> comparatorCarParkSlot = Comparator.comparing(Property::getCarParkSlot);
    public static final Comparator<Property> comparatorPrice = Comparator.comparing(Property::getPrice);

    //Constructors
    public Property(User owner, double sizeOfProperty, Address address, int numberOfTenants, int numberOfBedRooms, int numberOfKitchens, int numberOfBathRooms, int carParkSlot, Furniture furniture, int price) {
        this.owner = owner;
        this.sizeOfProperty = new SimpleDoubleProperty(sizeOfProperty);
        this.address = new SimpleObjectProperty<>(address);
        this.tenants = new Client[numberOfTenants];
        this.countTenants = new SimpleIntegerProperty(0);
        this.numberOfTenants = new SimpleIntegerProperty(numberOfTenants);
        this.numberOfBedRooms = new SimpleIntegerProperty(numberOfBedRooms);
        this.numberOfKitchens = new SimpleIntegerProperty(numberOfKitchens);
        this.numberOfBathRooms = new SimpleIntegerProperty(numberOfBathRooms);
        this.carParkSlot = new SimpleIntegerProperty(carParkSlot);
        this.furniture = furniture;
        this.price = new SimpleIntegerProperty(price);
        this.propertyID = new SimpleStringProperty(setPropertyID());
        AppModel.getProperties().put(this.propertyID.get(), this);
    }

    public Property(double sizeOfProperty, Address address, int numberOfTenants, int numberOfBedRooms, int numberOfKitchens, int numberOfBathRooms, int carParkSlot, Furniture furniture, int price) {
        this.sizeOfProperty = new SimpleDoubleProperty(sizeOfProperty);
        this.address = new SimpleObjectProperty<>(address);
        this.tenants = new Client[numberOfTenants];
        this.countTenants = new SimpleIntegerProperty(0);
        this.numberOfTenants = new SimpleIntegerProperty(numberOfTenants);
        this.numberOfBedRooms = new SimpleIntegerProperty(numberOfBedRooms);
        this.numberOfKitchens = new SimpleIntegerProperty(numberOfKitchens);
        this.numberOfBathRooms = new SimpleIntegerProperty(numberOfBathRooms);
        this.carParkSlot = new SimpleIntegerProperty(carParkSlot);
        this.furniture = furniture;
        this.price = new SimpleIntegerProperty(price);
        this.propertyID = new SimpleStringProperty(setPropertyID());
        AppModel.getProperties().put(this.propertyID.get(), this);
    }
    // Getter methods for properties
    public SimpleDoubleProperty getPropertySizeOfProperty() {
        return sizeOfProperty;
    }
    public SimpleObjectProperty<Address> getAddress() {
        return address;
    }
    public User getOwner() {
        return owner;
    }
    public SimpleIntegerProperty getPropertyCarParkSlot() {
        return carParkSlot;
    }
    public SimpleIntegerProperty getPropertyNumberOfBathRooms() {
        return numberOfBathRooms;
    }
    public SimpleIntegerProperty getPropertyNumberOfBedRooms() {
        return numberOfBedRooms;
    }
    public SimpleIntegerProperty getPropertyNumberOfKitchens() {
        return numberOfKitchens;
    }
    public SimpleIntegerProperty getPropertyNumberOfTenants() {
        return numberOfTenants;
    }
    public SimpleIntegerProperty getPropertyPrice() {
        return price;
    }
    public SimpleStringProperty getPropertyPropertyID() {
        return propertyID;
    }
    public Client[] getTenants() {
        return tenants;
    }
    public SimpleIntegerProperty getPropertyCountTenants() {
        return countTenants;
    }
    // Setter methods for properties
    public void setSizeOfProperty(double size) {
        this.sizeOfProperty.set(size);
    }
    public void setAddress(Address address) {
        this.address = new SimpleObjectProperty<>(address);
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public void setPropertyCarParkSlot(int carParkSlot) {
        this.carParkSlot.set(carParkSlot);
    }
    public void setPropertyNumberOfBathRooms(int numberOfBathRooms) {
        this.numberOfBathRooms.set(numberOfBathRooms);
    }
    public void setPropertyNumberOfBedRooms(int numberOfBedRooms) {
        this.numberOfBedRooms.set(numberOfBedRooms);
    }
    public void setPropertyNumberOfKitchens(int numberOfKitchens) {
        this.numberOfKitchens.set(numberOfKitchens);
    }
    public void setPropertyNumberOfTenants(int numberOfTenants) {
        this.numberOfTenants.set(numberOfTenants);
    }
    public void setPropertyPrice(int price) {
        this.price.set(price);
    }
    public void setPropertyCountTenants(int countTenants) {
        this.countTenants.set(countTenants);
    }
    public String setPropertyID() {
        String idsetup;
        while (true) {
            Random rand = new Random();
            int num1 = rand.nextInt(10);
            int num2 = rand.nextInt(10);
            int num3 = rand.nextInt(10);
            int num4 = rand.nextInt(10);
            idsetup = "PR" + num1 + num2 + num3 + num4;
            boolean isFound = false;
            for (String id : AppModel.getProperties().keySet()){
                if (idsetup.equals(id)){
                    isFound = true;
                    break;
                }
            }
            if (isFound){
                continue;
            }
            break;
        }
        return idsetup;
    }
    // Methods to retrieve actual values from properties
    public double getSizeOfProperty() {
        return getPropertySizeOfProperty().get();
    }
    public String getPropertyID() {
        return propertyID.get();
    }
    public int getCarParkSlot() {
        return getPropertyCarParkSlot().get();
    }
    public int getNumberOfBathRooms() {
        return getPropertyNumberOfBathRooms().get();
    }
    public int getNumberOfBedRooms() {
        return getPropertyNumberOfBedRooms().get();
    }
    public int getNumberOfKitchens() {
        return getPropertyNumberOfKitchens().get();
    }
    public int getNumberOfTenants() {
        return getPropertyNumberOfTenants().get();
    }
    public int getPrice() {
        return getPropertyPrice().get();
    }
}
class Apartment extends Property {
    private SimpleIntegerProperty floorNumber;
    private SimpleIntegerProperty roomNumber;
    private SimpleDoubleProperty extraFee; // Strata levies
    public Apartment(User owner, double sizeOfProperty, Address address,int numberOfTenants, int numberOfBedRooms, int numberOfKitchens, int numberOfBathRooms, int carParkSlot,Furniture furniture,int floorNumber, int roomNumber, int extraFee, int price){
        super(owner, sizeOfProperty, address,numberOfTenants , numberOfBedRooms, numberOfKitchens, numberOfBathRooms, carParkSlot, furniture,price);
        this.floorNumber = new SimpleIntegerProperty(floorNumber);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.extraFee = new SimpleDoubleProperty(extraFee);
    }
    public Apartment(double sizeOfProperty, Address address,int numberOfTenants, int numberOfBedRooms, int numberOfKitchens, int numberOfBathRooms, int carParkSlot,Furniture furniture,int floorNumber, int roomNumber, int extraFee, int price){
        super(sizeOfProperty, address,numberOfTenants , numberOfBedRooms, numberOfKitchens, numberOfBathRooms, carParkSlot, furniture,price);
        this.floorNumber = new SimpleIntegerProperty(floorNumber);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.extraFee = new SimpleDoubleProperty(extraFee);
    }
}
class House extends Property {
    private SimpleIntegerProperty numberOfFloor;
    private SimpleBooleanProperty garden;

    public House(User owner, double sizeOfProperty, Address address, int numberOfTenants, int numberOfBedRooms, int numberOfKitchens, int numberOfBathRooms, int carParkSlot, Furniture furniture, int numberOfFloor, boolean garden, int price) {
        super(owner, sizeOfProperty, address, numberOfTenants, numberOfBedRooms, numberOfKitchens, numberOfBathRooms, carParkSlot, furniture, price);
        this.numberOfFloor = new SimpleIntegerProperty(numberOfFloor);
        this.garden = new SimpleBooleanProperty(garden);
    }

    public House(double sizeOfProperty, Address address, int numberOfTenants, int numberOfBedRooms, int numberOfKitchens, int numberOfBathRooms, int carParkSlot, Furniture furniture, int numberOfFloor, boolean garden, int price) {
        super(sizeOfProperty, address, numberOfTenants, numberOfBedRooms, numberOfKitchens, numberOfBathRooms, carParkSlot, furniture, price);
        this.numberOfFloor = new SimpleIntegerProperty(numberOfFloor);
        this.garden = new SimpleBooleanProperty(garden);
    }
}
abstract class User implements systemCommunication, accountManagement {
    private SimpleStringProperty name;
    private SimpleObjectProperty<Address> address;
    private SimpleStringProperty phoneNumber;
    private final SimpleStringProperty userID;
    private List<SimpleStringProperty> mails;
    private SimpleStringProperty username;
    private SimpleStringProperty password;

    // Constructor
    User(String name, Address address, String phoneNumber, String username, String password){
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleObjectProperty<>(address);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.userID = new SimpleStringProperty(setUserID());
        this.mails = new ArrayList<>();
        AppModel.getUsers().put(getUserID(), this);
    }

    // Getter methods for properties
    public SimpleStringProperty getPropertyName() {
        return name;
    }
    public SimpleObjectProperty<Address> getPropertyAddress() {
        return address;
    }
    public SimpleStringProperty getPropertyPhoneNumber() {
        return phoneNumber;
    }
    public SimpleStringProperty getPropertyUserID() {
        return userID;
    }
    public SimpleStringProperty getPropertyUsername() {
        return username;
    }
    public SimpleStringProperty getPropertyPassword() {
        return password;
    }
    // Setter methods for properties
    public void setName(String name) {
        this.name.set(name);
    }
    public void setAddress(Address address) {
        this.address.set(address);
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }
    public void setUsername(String username) {
        this.username.set(username);
    }
    public void setPassword(String password) {
        this.password.set(password);
    }
    // Methods to retrieve actual values from properties
    public String getName() {
        return getPropertyName().get();
    }
    public Address getAddress() {
        return getPropertyAddress().get();
    }
    public String getPhoneNumber() {
        return getPropertyPhoneNumber().get();
    }
    public String getUserID() {
        return getPropertyUserID().get();
    }
    public List<SimpleStringProperty> getMails() {
        return mails;
    }
    public String getUsername() {
        return getPropertyUsername().get();
    }
    public String getPassword() {
        return getPropertyPassword().get();
    }

    abstract String setUserID();
    public String toString(){
        return "\nName: " + getName() + "\nAddress: " + getAddress() + "\nPhone Number: " + getPhoneNumber() + "\nUserID: " + getUserID();
    }
}
class Agent extends User {
    private Map<String, Property> inventory;

    public Agent(String name, Address address, String phoneNumber, String username, String password) {
        super(name, address, phoneNumber, username, password);
        inventory = new HashMap<>();
    }

    public Map<String, Property> getInventory() {
        return inventory;
    }
    @Override
    protected String setUserID() {
        String idsetup;
        while (true) {
            Random rand = new Random();
            int num1 = rand.nextInt(10);
            int num2 = rand.nextInt(10);
            int num3 = rand.nextInt(10);
            int num4 = rand.nextInt(10);
            idsetup = "AG" + num1 + num2 + num3 + num4;
            boolean isFound = false;
            for (String id : AppModel.getUsers().keySet()){
                if (idsetup.equals(id)){
                    isFound = true;
                    break;
                }
            }
            if (isFound){
                continue;
            }
            break;
        }
        return idsetup;
    }
    @Override
    public void updatePassword() {
    }
    @Override
    public void updateProfile() {

    }
    @Override
    public void sendmessage() {

    }
    @Override
    public void report() {

    }
}
class Client extends User {
    private Map<String, Property> wishList;
    private Map<String, Property> leases;

    public Client(String name, Address address, String phoneNumber, String username, String password) {
        super(name, address, phoneNumber, username, password);
        wishList = new HashMap<>();
        leases = new HashMap<>();
    }
    @Override
    protected String setUserID() {
        String idsetup;
        while (true) {
            Random rand = new Random();
            int num1 = rand.nextInt(10);
            int num2 = rand.nextInt(10);
            int num3 = rand.nextInt(10);
            int num4 = rand.nextInt(10);
            idsetup = "EC" + num1 + num2 + num3 + num4;
            boolean isFound = false;
            for (String id : AppModel.getUsers().keySet()){
                if (idsetup.equals(id)){
                    isFound = true;
                    break;
                }
            }
            if (isFound){
                continue;
            }
            break;
        }
        return idsetup;
    }
    public Map<String, Property> getWishList() {
        return wishList;
    }
    public Map<String, Property> getLeases() {
        return leases;
    }

    @Override
    public void updatePassword() {

    }
    @Override
    public void updateProfile() {

    }
    @Override
    public void sendmessage() {

    }
    @Override
    public void report() {

    }
}
class Admin implements systemCommunication, accountManagement {
    private static SimpleStringProperty username = new SimpleStringProperty("Admin");
    private static SimpleStringProperty password = new SimpleStringProperty("123");


    public static String getUsername() {
        return username.get();
    }

    public static String getPassword() {
        return password.get();
    }

    @Override
    public void updatePassword() {

    }
    @Override
    public void updateProfile() {

    }

    @Override
    public void sendmessage() {

    }
    @Override
    public void report() {

    }
}




