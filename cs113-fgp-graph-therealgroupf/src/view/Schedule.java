package view;

/**
 * Schedule.java : Pop-up view that displays schedule.
 *
 * @author Alex Phan
 * @version 1.0
 */

import edu.miracosta.cs113.NonConflictingSchedules;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;



public class Schedule {
    public static final int ALERT_WINDOW_HEIGHT = 650;
    public static final int ALERT_WINDOW_WIDTH = 525;
    private static TableView<Catalog> table1;
    private static TableView<Catalog> table2;
    private static TableView<Catalog> table3;
    private static Stage stage;
    private static ObservableList<Catalog> catalog = FXCollections.observableArrayList();

    private static ObservableList<Catalog> cat2 = FXCollections.observableArrayList();
    private static ObservableList<Catalog> cat3 = FXCollections.observableArrayList();
    private static LinkedList<String> linkedList = new LinkedList<>();
    private static int counter = 0;

    public static void display(String title, ArrayList<LinkedList> ratedSchedule) {
        linkedList = new LinkedList<>();

        for (int i = 0; i < ratedSchedule.size(); i++) {
            System.out.println(ratedSchedule.get(i));
            linkedList.add(ratedSchedule.get(i).toString());
        }


        stage = new Stage();

        // Block user window events until this one is taken care of
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinHeight(ALERT_WINDOW_HEIGHT);
        stage.setMinWidth(ALERT_WINDOW_WIDTH);

        /*
        Label label = new Label();
        label.setText(message);
        */
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close());

        Label table1Title = new Label();
        table1Title.setText("Choices");

        Label table2Title = new Label();
        table2Title.setText("Option 2");

        Label table3Title = new Label();
        table3Title.setText("Option 3");

        // Course column
        TableColumn<Catalog, String> courseColumn = new TableColumn<>("Course");
        courseColumn.setMinWidth(75);
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));

        // Class section number column
        TableColumn<Catalog, String> numberColumn = new TableColumn<>("Class #");
        numberColumn.setMinWidth(50);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("classNumber"));

        // Time Column
        TableColumn<Catalog, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setMinWidth(100);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        // Instructor name column
        TableColumn<Catalog, String> instructorColumn = new TableColumn<>("Instructor");
        instructorColumn.setMinWidth(200);
        instructorColumn.setCellValueFactory(new PropertyValueFactory<>("instructor"));

        // Number of units column
        TableColumn<Catalog, String> unitsColumn = new TableColumn<>("Units");
        unitsColumn.setMinWidth(50);
        unitsColumn.setCellValueFactory(new PropertyValueFactory<>("units"));

        // Instructor rating
        TableColumn<Catalog, String> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setMinWidth(50);
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        // Assign tableColumns to TableView table1
        table1 = new TableView<>();
        table1.setItems(getCatalog(linkedList.get(0)));
        table1.getColumns().addAll(courseColumn, numberColumn, timeColumn, instructorColumn, ratingColumn);
        table1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);   // Fixes issue of extra column being generated
        table1.setFixedCellSize(25);    // Fixes issue of extra rows
        table1.prefHeightProperty().bind(Bindings.size(table1.getItems()).multiply(table1.getFixedCellSize()).add(125));

        // Assign tableColumns to TableView table2
        table2 = new TableView<>();
        table2.setItems(getCatalog(linkedList.get(1)));
        table2.getColumns().addAll(courseColumn, numberColumn, timeColumn, instructorColumn, ratingColumn);
        table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table2.setFixedCellSize(25);
        table2.prefHeightProperty().bind(Bindings.size(table1.getItems()).multiply(table1.getFixedCellSize()).add(125));

        // Assign tableColumns to TableView table3
        table3 = new TableView<>();
        table3.setItems(getCatalog(linkedList.get(2)));
        table3.getColumns().addAll(courseColumn, numberColumn, timeColumn, instructorColumn, ratingColumn);
        table3.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table3.setFixedCellSize(25);
        table3.prefHeightProperty().bind(Bindings.size(table1.getItems()).multiply(table1.getFixedCellSize()).add(125));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(table1Title, table1, closeButton);// table2Title, table2, table3Title, table3, closeButton);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static ObservableList<Catalog> getCatalog(String finalSchedule) {
        
        String firstPlace = finalSchedule.substring(1, finalSchedule.indexOf("]"));
        String[] blah = new String[40];
        int counter2 = 0;
        do {
            int place = firstPlace.indexOf(", ");
            blah[counter2] = firstPlace.substring(0, place);
            firstPlace = firstPlace.substring(place + 2);
            counter2++;
        } while (firstPlace.contains(", "));
        blah[counter2] = firstPlace;
        List<String> values2 = new ArrayList<String>();
        for (String data : blah) {
            if (data != null) {
                values2.add(data);
            }
        }
        String[] target = values2.toArray(new String[values2.size()]);
            for (int i = 0; i < target.length; i++) {
                String something = target[i];
                String[] newSections = new String[5];
                int counter3 = 0;
                do {
                    int place3 = something.indexOf(",");
                    newSections[counter3] = something.substring(0, place3);
                    something = something.substring(place3 + 1);
                    counter3++;
                } while (something.contains(","));
                newSections[counter3] = something;

                String course = newSections[0];
                String classNumber = newSections[1];
                String time = newSections[2];
                String instructor = newSections[3];
                String rating = newSections[4];
                catalog.add(new Catalog(course, classNumber, time, instructor, rating));
            }
        counter++;
        return catalog;
    }
}
