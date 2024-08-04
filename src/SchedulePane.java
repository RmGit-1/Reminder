package Reminder.src;

import java.util.Calendar;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class SchedulePane extends VBox{

    private Label time;
    private Label title;
    private Label detail;

    public CheckBox checkBox;
    public boolean isPast;

    SchedulePane(ScheduleData sData) {
        Calendar c = Calendar.getInstance();
        c.setTime(sData.date);

        time = new Label(String.format("%4d/%2d/%2d  %02d:%02d", 
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH),
            c.get(Calendar.HOUR_OF_DAY),
            c.get(Calendar.MINUTE)
        ));
        time.setFont(new Font("Menlo Regular", 15));
        time.setTextAlignment(TextAlignment.LEFT);
        time.setPrefWidth(390);
        isPast = sData.isPast();
        if (isPast) time.setTextFill(Color.RED);

        title = new Label(sData.title);
        title.setFont(new Font("Menlo Regular", 20));
        title.setTextAlignment(TextAlignment.LEFT);
        title.setPrefWidth(390);

        detail = new Label(sData.detail);
        detail.setFont(new Font("Menlo Regular", 15));
        detail.setTextAlignment(TextAlignment.LEFT);
        detail.setPrefWidth(390);
        detail.setTextFill(Color.GRAY);

        VBox labelBox = new VBox(5, time, title, detail);
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setPrefWidth(390);


        checkBox = new CheckBox();
        checkBox.setPrefSize(30, 30);
        checkBox.setScaleX(1.5);
        checkBox.setScaleY(1.5);

        Line line = new Line();
        line.setStrokeWidth(2);
        line.setStartX(0); line.setEndX(0);
        line.setStartY(0); line.setEndY(70);

        HBox root = new HBox(10, checkBox, line, labelBox);
        root.setAlignment(Pos.CENTER);
        getChildren().add(root);
        setAlignment(Pos.CENTER);
    }
}
