package ru.vkokourov.goodish.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ru.vkokourov.goodish.GoodishApp;
import ru.vkokourov.goodish.mapper.DishMapper;
import ru.vkokourov.goodish.model.Dish;
import ru.vkokourov.goodish.repository.DishRepository;
import ru.vkokourov.goodish.service.DishService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final File file;
    private final DishMapper dishMapper;
    private final DishRepository dishRepository;
    private final DishService dishService;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField caloriesTextField;
    @FXML
    private TextField proteinsTextField;
    @FXML
    private TextField fatsTextField;
    @FXML
    private TextField carbsTextField;

    @FXML
    private TableView<Dish> dishTable;

    @FXML
    private VBox addDishPanel;

    public MainController() {
        file = new File("dish.csv");
        try {
            if (file.exists()) {
                System.out.println("File exists");
            }
            if ((file.createNewFile())) {
                System.out.println("File created");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        dishMapper = new DishMapper();
        dishRepository = new DishRepository(file, dishMapper);
        dishService = new DishService(dishRepository);
    }

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

        updateTable();
    }

    private void updateTable() {
        dishTable.getItems().clear();
        dishTable.getItems().addAll(dishService.findAll());
    }

    private String getSelectedNameOfDish() {
        return dishTable.getSelectionModel().getSelectedItem().getName();
    }

    private void clearTextFields() {
        nameTextField.clear();
        caloriesTextField.clear();
        proteinsTextField.clear();
        fatsTextField.clear();
        carbsTextField.clear();
    }

    public void btnExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void clickBtnAddDish(ActionEvent actionEvent) {
        addDishPanel.setVisible(true);
        addDishPanel.setManaged(true);
    }

    public void btnSaveDish(ActionEvent actionEvent) {
        dishRepository.save(new Dish(
                nameTextField.getText(),
                Double.parseDouble(caloriesTextField.getText()),
                Double.parseDouble(proteinsTextField.getText()),
                Double.parseDouble(fatsTextField.getText()),
                Double.parseDouble(carbsTextField.getText())
        ));

        addDishPanel.setVisible(false);
        addDishPanel.setManaged(false);

        clearTextFields();
        updateTable();
    }

    public void btnCancelDish(ActionEvent actionEvent) {
        addDishPanel.setVisible(false);
        addDishPanel.setManaged(false);

        clearTextFields();
    }

    public void clickBtnEditDish(ActionEvent actionEvent) {
        addDishPanel.setVisible(true);
        addDishPanel.setManaged(true);

        String name = dishTable.getSelectionModel().getSelectedItem().getName();
        Dish dish = dishService.findByName(name);

        nameTextField.setText(dish.getName());
        caloriesTextField.setText(String.valueOf(dish.getCalories()));
        proteinsTextField.setText(String.valueOf(dish.getProteins()));
        fatsTextField.setText(String.valueOf(dish.getFats()));
        carbsTextField.setText(String.valueOf(dish.getCarbs()));
    }

    public void clickBtnDeleteDish(ActionEvent actionEvent) {
        String name = dishTable.getSelectionModel().getSelectedItem().getName();
        dishService.deleteByName(name);
        updateTable();
    }
}