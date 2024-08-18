import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Management Housing");
        AppModel model = new AppModel();
        AppController controller = new AppController(model);
        AppView view = new AppView(controller, model, primaryStage);
        primaryStage.setScene(view.welcomePage());
        primaryStage.show();
    }
    
}