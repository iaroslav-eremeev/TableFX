package com.iaroslaveremeev.tablefx;

import com.iaroslaveremeev.tablefx.model.User;
import com.iaroslaveremeev.tablefx.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

public class MainController {

    public TableView<User> table;

    @FXML
    public void openUsers(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\tirsb\\IdeaProjects\\JavaEE\\TableFX"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "JSON files", "*.json", "*.JSON"));
        File file = fileChooser.showOpenDialog(null);
        try  {
            if(file != null){
                table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

                TableColumn<User, Boolean> isSentCol = new TableColumn<User, Boolean>("Sent");
                TableColumn<User, Integer> idCol = new TableColumn<User, Integer>("User ID");
                TableColumn<User, String> nameCol = new TableColumn<User, String>("Name");
                TableColumn<User, Date> regDateCol = new TableColumn<User, Date>("Registration date");
                TableColumn<User, String> mailCol = new TableColumn<User, String>("E-mail");
                TableColumn<User, Integer> ageCol = new TableColumn<User, Integer>("Age");
                TableColumn<User, String> countryCol = new TableColumn<User, String>("Country");
                TableColumn<User, Button> buttonCol = new TableColumn<User, Button>("Action");

                isSentCol.setCellValueFactory(new PropertyValueFactory<User, Boolean>("isSent"));
                idCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
                nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
                regDateCol.setCellValueFactory(new PropertyValueFactory<User, Date>("regDate"));
                mailCol.setCellValueFactory(new PropertyValueFactory<User, String>("mail"));
                ageCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));
                countryCol.setCellValueFactory(new PropertyValueFactory<User, String>("country"));
                buttonCol.setCellValueFactory(new PropertyValueFactory<User, Button>("action"));

                UserRepository userRepository = new UserRepository(file);
                table.setItems(FXCollections.observableArrayList(userRepository.getUsers()));

                Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactoryIsSent =
                        new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                            @Override
                            public TableCell call(final TableColumn<User, Boolean> param) {
                                final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
                                    CheckBox btn = new CheckBox("Sent");
                                    @Override
                                    public void updateItem(Boolean item, boolean empty) {
                                        super.updateItem(item, empty);
                                        if (empty) {
                                            setGraphic(null);
                                            setText(null);
                                        } else {
                                            btn.setDisable(true);
                                            btn.setStyle("-fx-opacity: 1;");
                                            setGraphic(btn);
                                            setText(null);
                                        }
                                    }
                                };
                                return cell;
                            }
                        };


                //TODO 2 коллбека - за галочки и за отправку
                table.getColumns().addAll(idCol, nameCol, regDateCol, mailCol, ageCol, countryCol);
            }
            else throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            App.showAlertWithoutHeaderText("Error!", "You didn't chose any file", Alert.AlertType.ERROR);
        }

    }
}