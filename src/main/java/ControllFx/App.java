package ControllFx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import core.*;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Logger log = LogManager.getLogger(App.class);

    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Main.fxml"));
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            log.warn(e);
        }
        stage.setScene(scene);
        stage.setTitle("The Shop");
        stage.setResizable(false);
        stage.getIcons().add(new Image("icon.png"));
        stage.show();
        log.debug("Application started");

        // Start Background Thread for autosafe-function
        Thread backgroundThread = new Thread(new BackupRunnable());
        // Set Deamon which closes thread on application exit
        backgroundThread.setDaemon(true);
        backgroundThread.start();
        log.debug("Autosafe-Thread started");
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception{
        // Safe on Exit
        Stock stock = new Stock();
        stock.writeList();
        log.debug("Safe on Exit");
        super.stop();
        log.debug("Application closed");
    }
}