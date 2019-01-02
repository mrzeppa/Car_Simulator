package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Controller {
    @FXML private Label speedLabel;
    @FXML private Label statusLabel;
    @FXML private Label gearLabel;
    @FXML private Label roundsLabel;
    @FXML private Button functionButton1;
    @FXML private Rectangle gasGauge;
    @FXML private Rectangle breakGauge;
    @FXML private Rectangle clutchGauge;
    @FXML private GridPane container;
    private Car car;
    private Thread th;
    public boolean clutch = false;
    String image1 = this.getClass().getResource("resources/interior.jpg").toExternalForm();
    String image2 = this.getClass().getResource("resources/interior2.jpg").toExternalForm();

    public Controller() {
        car = new Car(0.0, 0, false, 'N', 6000);
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SHIFT){
            clutchGauge.setFill(Color.rgb(144,238,144));
            clutch = true;
        }

        if (event.getCode() == KeyCode.F2){
            if (!car.isStarted() && car.getMaxRounds() > car.getRounds() && clutch){
                car.setIsStarted(true);
                container.setStyle(" -fx-background-image: url('" + image1 + "');  ");

                if (event.getCode() == KeyCode.F2) {
                    Task task = new Task<Void>() {
                        @Override
                        public Void call() throws Exception {
                            for (Double i = car.getRounds(); i < 1132; i = i + 3){
                                Platform.runLater(() -> {
                                    car.setRounds(car.getRounds() + 3);
                                    roundsLabel.setText(Double.toString(car.getRounds()) + " RPM");
                                });
                                Thread.sleep(1);
                            }
                            return null;
                        }
                    };

                    th = new Thread(task);
                    th.setDaemon(true);
                    th.start();
                }
                roundsLabel.setText(Double.toString(car.getRounds()));
                statusLabel.setText("Silnik włączony");
                roundsLabel.setStyle("-fx-text-fill: #90ee90;");
                gearLabel.setStyle("-fx-text-fill: #90ee90;");
                speedLabel.setStyle("-fx-text-fill: #90ee90;");


            }

            else if (car.isStarted() && car.getSpeed() == 0){

                container.setStyle(" -fx-background-image: url('" + image2 + "')");

                car.setIsStarted(false);
                statusLabel.setText("Silnik wyłączony");
                if (event.getCode() == KeyCode.F2) {
                    Task task = new Task<Void>() {
                        @Override
                        public Void call() throws Exception {
                            for (Double i = car.getRounds(); i > 0; i = i - 3){
                                Platform.runLater(() -> {
                                    car.setRounds(car.getRounds() - 3);
                                    roundsLabel.setText(Double.toString(car.getRounds()) + " RPM");
                                });
                                Thread.sleep(1);
                            }
                            return null;
                        }
                    };

                    th = new Thread(task);
                    th.setDaemon(true);
                    th.start();
                }
                roundsLabel.setStyle("");
                gearLabel.setStyle("");
                speedLabel.setStyle("");
            }
        }

        if (event.getCode() == KeyCode.UP && car.isStarted()){
            if (th != null){
                th.interrupt();
            }
            gasGauge.setFill(Color.rgb(144,238,144));
            if(car.getSpeed() < car.maxGearSpeed(car.getGear()))
                car.accelerateUp();
            roundsLabel.setText(((int)car.getRounds()) + " RPM");
//            if(car.getSpeed() > 0)
                speedLabel.setText(((int)car.getSpeed()) + " km/h");
//            else
//                speedLabel.setText("0 KM/h");


        }

        if (event.getCode() == KeyCode.DOWN && car.isStarted()){
            if (th != null){
                th.interrupt();
            }
            car.accelerateDown();
            breakGauge.setFill(Color.rgb(144,238,144));
            roundsLabel.setText(((int)car.getRounds()) + " RPM");
            speedLabel.setText(((int)car.getSpeed()) + " km/h");
        }

        if (event.getCode() == KeyCode.Q && car.isStarted() && clutch){
//            if ((th != null && event.getCode() == KeyCode.DOWN) ||(th != null && event.getCode() == KeyCode.UP)){
//                th.interrupt();
//            }
            car.gearUp();
            gearLabel.setText(Character.toString(car.getGear()));

        }
        if (event.getCode() == KeyCode.E && car.isStarted() && clutch){
//            if ((th != null && event.getCode() == KeyCode.DOWN) ||(th != null && event.getCode() == KeyCode.UP)){
//                th.interrupt();
//            }
            car.gearDown();
            gearLabel.setText(Character.toString(car.getGear()));

        }

    }
    // --------------------------------------------------------------------
    @FXML
    public void keyReleased(KeyEvent event) throws InterruptedException {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN ) {
            Task task = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    for (Double i = car.getSpeed(); i > 0; i--) {
                        Platform.runLater(() -> {
                            car.accelerateDown();

                            speedLabel.setText(((int) car.getSpeed()) + " km/h");
//                            car.setRounds(car.getRounds() - (car.getSpeed() * car.gearRates() * 30 / 100));
                            roundsLabel.setText(((int) car.getRounds()) + " RPM");


                        });
                        Thread.sleep(500);
                    }
//                    if(car.isStarted() && car.getSpeed() >= 0 && car.getSpeed() < 4){
//                        car.setGear('N');
//                        System.out.println(car.getGear());
//                        gearLabel.setText("N");
//                    }
                    return null;
                }
            };

            th = new Thread(task);
            th.setDaemon(true);
            th.start();

        }

        if (event.getCode() == KeyCode.SHIFT) {
            clutch = false;
        }
        if (event.getCode() == KeyCode.DOWN) {
            breakGauge.setFill(Color.rgb(107,107,107));
        }
        if (event.getCode() == KeyCode.UP) {
            gasGauge.setFill(Color.rgb(107,107,107));
        }
        if (event.getCode() == KeyCode.SHIFT) {
            clutchGauge.setFill(Color.rgb(107,107,107));
        }
    }
    @FXML
    public void closeButton(){
        Platform.exit();
    }
    @FXML
    public void minimizeButton(ActionEvent event){
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    public void maximizeButton(ActionEvent event){
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        if(functionButton1.getText().equals("Maksymalizuj")) {
            stage.setMaximized(true);
            functionButton1.setText("Zmniejsz");
        }
        else{
            stage.setMaximized(false);
            functionButton1.setText("Maksymalizuj");
        }
    }
}
