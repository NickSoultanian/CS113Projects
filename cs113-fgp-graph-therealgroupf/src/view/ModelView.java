package view;

/**
 * ModelView.java : View for program display and buttons.
 *
 * @author Alex Phan
 * @version 1.0
 */

import java.util.HashSet;

import edu.miracosta.cs113.AvailableClasses;

import edu.miracosta.cs113.GetProfScore;
import edu.miracosta.cs113.NonConflictingSchedules;
import edu.miracosta.cs113.ParsePDFOutput;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import org.controlsfx.control.textfield.TextFields;

public class ModelView extends Application implements EventHandler<ActionEvent> {
    public static final String TITLE_OF_WINDOW = "Class Schedule Assistant";
    public static final int WINDOW_HEIGHT = 500;
    public static final int WINDOW_WIDTH = 500;
    public static final int LABEL_FONT_SIZE = 20;
    public static final int TEXT_FIELD_SIZE = 195;
    public static final int TEXT_FIELD_FONT = 15;

    private Button doneButton;
    NonConflictingSchedules classSchedule;

    public static void main(String[] args) {
        launch(args);
        new ParsePDFOutput("refinedClassOfferings.txt");
        new AvailableClasses();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Gets list of available classes that parses through a file list of classes
        new AvailableClasses();

        // Create a HashSet list that will store class name and number. Will parse out
        // unnecessary information about class. Use HashSet instead of Array to avoid
        // duplicated class names.
        HashSet<String> list = new HashSet<String>();
        for (int i = 0; i < AvailableClasses.array.size(); i++) {
            list.add(AvailableClasses.array.get(i).substring(0, AvailableClasses.array.get(i).indexOf(",")));
        }

        // Create stage (window)
        primaryStage.setTitle(TITLE_OF_WINDOW);

        // Create grid layout
        GridPane grid = new GridPane();

        // Add padding to border of window
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label title = new Label("\n\n\n\n");
        GridPane.setConstraints(title, 0, 0);

        // Class 1 label
        Label class1Label = new Label("Class 1:");
        class1Label.setFont(new Font(LABEL_FONT_SIZE));
        // class1Label.setFont(Font.font("Verdana", FontWeight.BOLD, LABEL_FONT_SIZE));
        class1Label.setTextFill(Color.YELLOW);
        GridPane.setConstraints(class1Label, 0, 1);

        // Class 1 input
        //  Auto CAPITALIZE
        TextField class1Input = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                super.replaceText(start, end, text.toUpperCase());
            }
        };
        class1Input.setTextFormatter(new TextFormatter<Object>(change -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
        // Auto generate options based on text
        TextFields.bindAutoCompletion(class1Input, list);
        class1Input.setPrefWidth(TEXT_FIELD_SIZE);
        class1Input.setFont(Font.font(TEXT_FIELD_FONT));
        class1Input.setPromptText("e.g. Course");
        GridPane.setConstraints(class1Input, 1, 1);

        // Class 2 label
        Label class2Label = new Label("Class 2:");
        class2Label.setFont(new Font(LABEL_FONT_SIZE));
        class2Label.setTextFill(Color.YELLOW);
        GridPane.setConstraints(class2Label, 0, 2);

        // Class 2 input
        // Auto CAPITALIZE
        TextField class2Input = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                super.replaceText(start, end, text.toUpperCase());
            }
        };
        class2Input.setTextFormatter(new TextFormatter<Object>(change -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
        // Auto generate based on text
        TextFields.bindAutoCompletion(class2Input, list);
        class2Input.setPrefWidth(TEXT_FIELD_SIZE);
        class2Input.setFont(Font.font(TEXT_FIELD_FONT));
        class2Input.setPromptText("e.g. Course");
        GridPane.setConstraints(class2Input, 1, 2);

        // Class 3 label
        Label class3Label = new Label("Class 3");
        class3Label.setFont(new Font(LABEL_FONT_SIZE));
        class3Label.setTextFill(Color.YELLOW);
        GridPane.setConstraints(class3Label, 0, 3);

        // Class 3 input
        // Auto CAPITALIZE
        TextField class3Input = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                super.replaceText(start, end, text.toUpperCase());
            }
        };
        class3Input.setTextFormatter(new TextFormatter<Object>(change -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
        // Auto generate based on text
        TextFields.bindAutoCompletion(class3Input, list);
        class3Input.setPrefWidth(TEXT_FIELD_SIZE);
        class3Input.setFont(Font.font(TEXT_FIELD_FONT));
        class3Input.setPromptText("e.g. Course");
        GridPane.setConstraints(class3Input, 1, 3);

        // Class 4 label
        Label class4Label = new Label("Class 4:");
        class4Label.setFont(new Font(LABEL_FONT_SIZE));
        class4Label.setTextFill(Color.YELLOW);
        GridPane.setConstraints(class4Label, 0, 4);

        // Class 4 input
        // Auto CAPITALIZE
        TextField class4Input = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                super.replaceText(start, end, text.toUpperCase());
            }
        };
        class4Input.setTextFormatter(new TextFormatter<Object>(change -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
        // Auto generate based on text
        TextFields.bindAutoCompletion(class4Input, list);
        class4Input.setPrefWidth(TEXT_FIELD_SIZE);
        class4Input.setFont(Font.font(TEXT_FIELD_FONT));
        class4Input.setPromptText("e.g. Course");
        GridPane.setConstraints(class4Input, 1, 4);

        // Class 5 label
        Label class5Label = new Label("Class 5:");
        class5Label.setFont(new Font(LABEL_FONT_SIZE));
        class5Label.setTextFill(Color.YELLOW);
        GridPane.setConstraints(class5Label, 0, 5);

        // Class 5 input
        // Auto CAPITALIZE
        TextField class5Input = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                super.replaceText(start, end, text.toUpperCase());
            }
        };
        class5Input.setTextFormatter(new TextFormatter<Object>(change -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
        // Auto generate based on text
        TextFields.bindAutoCompletion(class5Input, list);
        class5Input.setPrefWidth(TEXT_FIELD_SIZE);
        class5Input.setFont(Font.font(TEXT_FIELD_FONT));
        class5Input.setPromptText("e.g. Course");
        GridPane.setConstraints(class5Input, 1, 5);

        // Create done button
        doneButton = new Button();
        doneButton.setFont(Font.font(15));
        doneButton.setText("Generate!");

        // Adds action to button, gets text from each TextField and combines it into 1 string separated by commas
        doneButton.setOnAction(e -> {
            // Obtains the text from TextField
            String class1 = class1Input.getText();
            String class2 = class2Input.getText();
            String class3 = class3Input.getText();
            String class4 = class4Input.getText();
            String class5 = class5Input.getText();

            // Adds comma after TextField input if not empty
            if (!class1.isEmpty()) {
                class1 += ",";
            }
            if (!class2.isEmpty()) {
                class2 += ",";
            }
            if (!class3.isEmpty()) {
                class3 += ",";
            }
            if (!class4.isEmpty()) {
                class4 += ",";
            }
            if (!class5.isEmpty()) {
                class5 += ",";
            }

            // Concatenates classes and removes last instance of comma
            String schedule = class1 + class2 + class3 + class4 + class5;
            schedule = schedule.substring(0, schedule.lastIndexOf(","));

            classSchedule = new NonConflictingSchedules(schedule);


            Schedule.display("Generated Schedule", classSchedule.getFinalThree());

        });

        // Directs where to handle code, in handle method
        //doneButton.setOnAction(this);

        // Add button to grid
        GridPane.setConstraints(doneButton, 1, 6);

        // Add Background Image
        BackgroundImage nickFace = new BackgroundImage(
                new Image("File:images/NickCatalog.png", WINDOW_WIDTH, WINDOW_HEIGHT, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        grid.setBackground(new Background(nickFace));

        // Gets children components and adds to list of "container"
        grid.getChildren().addAll(title, class1Label, class1Input, class2Label, class2Input,
                class3Label, class3Input, class4Label, class4Input,
                class5Input, class5Label, doneButton);

        // Create scene (layout)
        Scene scene = new Scene(grid, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Handles event of an action being executed.
     *
     * @param event
     */
    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == doneButton) {
            // TODO add what happens when done button is pressed
        }
    }
}
