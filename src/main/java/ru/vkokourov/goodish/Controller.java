package ru.vkokourov.goodish;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ru.vkokourov.goodish.model.Dish;

import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TableView<Dish> dishTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Dish, String> dishNameColumn = new TableColumn<>("Название");
        dishNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        dishNameColumn.setMinWidth(318);

        TableColumn<Dish, Double> dishCaloriesColumn = new TableColumn<>("Калории");
        dishCaloriesColumn.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getCalories()));
        dishCaloriesColumn.setMinWidth(120);

        TableColumn<Dish, Double> dishProteinsColumn = new TableColumn<>("Протеины");
        dishProteinsColumn.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProteins()));
        dishProteinsColumn.setMinWidth(120);

        TableColumn<Dish, Double> dishFatsColumn = new TableColumn<>("Жиры");
        dishFatsColumn.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getFats()));
        dishFatsColumn.setMinWidth(120);

        TableColumn<Dish, Double> dishCarbsColumn = new TableColumn<>("Углеводы");
        dishCarbsColumn.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getCarbs()));
        dishCarbsColumn.setMinWidth(120);

        dishTable.getColumns().addAll(dishNameColumn, dishCaloriesColumn, dishProteinsColumn,
                dishFatsColumn, dishCarbsColumn);

        dishTable.getItems().clear();
        dishTable.getItems().addAll(new Dish("Яичница", 20.5, 12.5, 5, 2),
                new Dish("Суп с яйцами", 150, 15, 4, 3),
                new Dish("Яйца фаршированные", 200, 20, 8, 6)
                );
    }

    public void btnExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }
}