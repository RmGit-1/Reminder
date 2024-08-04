package Reminder.src;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application{

    private Savedata savedata;
    private ArrayList<ScheduleData> sData;
    private int notificate = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        savedata = new Savedata(new File("Reminder/Data/schedules.csv"));
        sData = Utilities.sortSchedule(savedata.loadSchedules());
    }

    @Override
    public void start(Stage stage) {
        stage.setWidth(500);
        stage.setHeight(800);

        Button notifications;
        notifications = new Button("0");
        notifications.setPrefSize(100, 45);
        notifications.setFont(new Font("Menlo Regular", 20));

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            VBox scheduleBox = new VBox(20);
            scheduleBox.setAlignment(Pos.TOP_LEFT);
            scheduleBox.setPrefWidth(500);
            setScheduleData(scheduleBox);
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // ----------------------------------------------------------

            Label ymdLabel = new Label("0000年 00月 00日 (曜)");
            ymdLabel.setFont(new Font("Menlo Regular", 30));

            Label timeLabel = new Label("00:00");
            timeLabel.setFont(new Font("Menlo Regular", 50));

            VBox timeBox = new VBox(10, ymdLabel, timeLabel);
            timeBox.setAlignment(Pos.CENTER);

            Timeline refleshTime = new Timeline(new KeyFrame(Duration.millis(250), event -> {
                ymdLabel.setText(String.format("%04d年%2d月%2d日(%s)", 
                    Utilities.getTime(Utilities.YEAR),
                    Utilities.getTime(Utilities.MONTH),
                    Utilities.getTime(Utilities.DAY),
                    Utilities.getDow()
                ));

                timeLabel.setText(String.format("%02d%1s%02d", 
                    Utilities.getTime(Utilities.HOUR),
                    (Utilities.getTime(Utilities.SECOND)%2==0) ? " " : ":",
                    Utilities.getTime(Utilities.MINUTE)
                ));
            }));
            refleshTime.setCycleCount(Timeline.INDEFINITE);

            Paint defaultColor = notifications.getTextFill();
            Timeline refleshSchedule = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
                notificate = 0;
                setScheduleData(scheduleBox);
                notifications.setTextFill(notificate!=0 ? Color.RED : defaultColor);
                notifications.setText(String.valueOf(notificate));
            }));
            refleshSchedule.setCycleCount(Timeline.INDEFINITE);

        // ----------------------------------------------------------

            // ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

                Stage addPanel = new Stage();
                addPanel.setTitle("予定を追加");
                addPanel.setWidth(500);
                addPanel.setHeight(300);
                addPanel.setResizable(false);
                addPanel.setAlwaysOnTop(true);
                addPanel.initStyle(StageStyle.UTILITY);

                Spinner<Integer> day = new Spinner<>();
                Spinner<Integer> year = new Spinner<>();
                Spinner<Integer> month = new Spinner<>();
                Calendar c = Calendar.getInstance();

                year.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999, Utilities.getTime(Utilities.YEAR)));
                year.valueProperty().addListener(new ChangeListener<Integer>() {
                    @Override
                    public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year.getValue());
                        cal.set(Calendar.MONTH, month.getValue()-1);

                        LoopSpinnerValueFactory lsvf = new LoopSpinnerValueFactory(
                            1,
                            cal.getActualMaximum(Calendar.DAY_OF_MONTH),
                            day.getValue()
                        );
                        day.setValueFactory(lsvf);

                        while (day.getValue() > lsvf.getMax()) {
                            day.decrement();
                        }
                    }
                });
                year.setStyle("-fx-font-family: 'Menlo Regular'; -fx-font-size: 20px;");
                year.setPrefSize(115, 50);
                
                month.setValueFactory(new LoopSpinnerValueFactory(1, 12, Utilities.getTime(Utilities.MONTH)));
                month.valueProperty().addListener(new ChangeListener<Integer>() {
                    @Override
                    public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year.getValue());
                        cal.set(Calendar.MONTH, month.getValue()-1);

                        LoopSpinnerValueFactory lsvf = new LoopSpinnerValueFactory(
                            1,
                            cal.getActualMaximum(Calendar.DAY_OF_MONTH),
                            day.getValue()
                        );
                        day.setValueFactory(lsvf);

                        while (day.getValue() > lsvf.getMax()) {
                            day.decrement();
                        }
                    }
                });
                month.setStyle("-fx-font-family: 'Menlo Regular'; -fx-font-size: 20px;");
                month.setPrefSize(90, 50);

                c.set(Calendar.YEAR, year.getValue());
                c.set(Calendar.MONTH, month.getValue()-1);
                day.setValueFactory(
                    new LoopSpinnerValueFactory(
                        1, 
                        c.getActualMaximum(Calendar.DAY_OF_MONTH), 
                        Utilities.getTime(Utilities.DAY)
                    )
                );
                day.setStyle("-fx-font-family: 'Menlo Regular'; -fx-font-size: 20px;");
                day.setPrefSize(90, 50);

                Spinner<Integer> hour = new Spinner<>();
                hour.setValueFactory(new LoopSpinnerValueFactory(0, 23, Utilities.getTime(Utilities.HOUR)));
                hour.setStyle("-fx-font-family: 'Menlo Regular'; -fx-font-size: 20px;");
                hour.setPrefSize(90, 50);

                Spinner<Integer> minute = new Spinner<>();
                minute.setValueFactory(new LoopSpinnerValueFactory(0,50, 0, 10));
                minute.setStyle("-fx-font-family: 'Menlo Regular'; -fx-font-size: 20px;");
                minute.setPrefSize(90, 50);

                Label l1 = new Label("年");
                l1.setFont(new Font("Menlo Regular", 20));
                Label l2 = new Label("月");
                l2.setFont(new Font("Menlo Regular", 20));
                Label l3 = new Label("日");
                l3.setFont(new Font("Menlo Regular", 20));
                Label l4 = new Label("時");
                l4.setFont(new Font("Menlo Regular", 20));
                Label l5 = new Label("分");
                l5.setFont(new Font("Menlo Regular", 20));

                HBox addymdBox = new HBox(10, year, l1, month, l2, day, l3);
                addymdBox.setAlignment(Pos.CENTER);
                HBox addhmBox = new HBox(10, hour, l4, minute, l5);
                addhmBox.setAlignment(Pos.CENTER);
                VBox addtimeBox = new VBox(10, addymdBox, addhmBox);

                TextField title = new TextField();
                title.setPrefSize(380, 50);
                title.setFont(new Font("Menlo Regular", 15));
                title.setPromptText("スケジュール名");
                TextField detail = new TextField();
                detail.setPrefSize(380, 30);
                detail.setFont(new Font("Menlo Regular", 10));
                detail.setPromptText("詳細説明");

                Button create = new Button("予定を作成");
                create.setPrefSize(200, 50);
                create.setFont(new Font("Menlo Regular", 20));
                create.setOnAction(event -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year.getValue());
                    calendar.set(Calendar.MONTH, month.getValue());
                    calendar.set(Calendar.DAY_OF_MONTH, day.getValue());
                    calendar.set(Calendar.HOUR_OF_DAY, hour.getValue());
                    calendar.set(Calendar.MINUTE, minute.getValue());
                    savedata.saveSchedule(new ScheduleData(
                        calendar.getTime(),
                        (title.getText().equals("")) ? "タイトルなし" : title.getText(),
                        (detail.getText().equals("")) ? "詳細説明なし" : detail.getText()
                    ));
                    addPanel.close();
                    title.setText("");
                    detail.setText("");

                    sData = Utilities.sortSchedule(savedata.loadSchedules());
                    setScheduleData(scheduleBox);
                });

                VBox addRoot = new VBox(10, title, detail, addtimeBox, create);
                addRoot.setAlignment(Pos.CENTER);

                addPanel.setScene(new Scene(addRoot));
        
            // ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

            Button addSchedule = new Button("+");
            addSchedule.setPrefSize(100, 45);
            addSchedule.setFont(new Font("Menlo Regular", 20));
            addSchedule.setOnAction(event -> {
                title.setText("");
                detail.setText("");
                addPanel.show();
            });

            VBox buttonBox = new VBox(notifications, addSchedule);
            buttonBox.setAlignment(Pos.CENTER);

        // ----------------------------------------------------------

        HBox widgetBox = new HBox(50, buttonBox, timeBox);
        widgetBox.setMinHeight(150);
        widgetBox.setAlignment(Pos.TOP_CENTER);

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        VBox root = new VBox(10, widgetBox, scheduleBox);
        root.setAlignment(Pos.TOP_LEFT);

        stage.setScene(new Scene(root));
        refleshTime.play();
        refleshSchedule.play();
        stage.show();
    }

    private void setScheduleData(VBox scheduleBox) {
        scheduleBox.getChildren().clear();
        sData = Utilities.sortSchedule(savedata.loadSchedules());

        for (int i=0; i<sData.size(); i++) {
            SchedulePane pane = new SchedulePane(sData.get(i));
            scheduleBox.getChildren().add(pane);
            if (pane.isPast) notificate++;

            final int I = i;
            Timeline onChecked = new Timeline(new KeyFrame(Duration.millis(1), event -> {
                scheduleBox.getChildren().remove(pane);
                savedata.removeSchedule(sData.get(I));
            }));
            onChecked.setCycleCount(1);

            pane.checkBox.setOnAction(event -> {
                if (pane.checkBox.isSelected()) {
                    onChecked.play();
                } else {
                    onChecked.stop();
                }
            });
        }
    }
}