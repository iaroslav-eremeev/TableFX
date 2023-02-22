package com.iaroslaveremeev.tablefx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.tablefx.model.User;
import com.iaroslaveremeev.tablefx.repository.UserRepository;
import com.iaroslaveremeev.tablefx.util.MailSender;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
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
                isSentCol.setStyle( "-fx-alignment: CENTER;");
                isSentCol.setMinWidth(50);
                TableColumn<User, Integer> idCol = new TableColumn<User, Integer>("User ID");
                idCol.setMinWidth(50);
                TableColumn<User, String> nameCol = new TableColumn<User, String>("Name");
                nameCol.setMinWidth(125);
                TableColumn<User, Date> regDateCol = new TableColumn<User, Date>("Registration date");
                regDateCol.setMinWidth(125);
                TableColumn<User, String> mailCol = new TableColumn<User, String>("E-mail");
                mailCol.setMinWidth(150);
                TableColumn<User, Integer> ageCol = new TableColumn<User, Integer>("Age");
                ageCol.setMinWidth(50);
                TableColumn<User, String> countryCol = new TableColumn<User, String>("Country");
                countryCol.setMinWidth(100);
                TableColumn<User, String> buttonCol = new TableColumn<User, String>("Action");
                buttonCol.setMinWidth(150);
                buttonCol.setStyle( "-fx-alignment: CENTER;");

                idCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
                nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));

                // Filling columns with dates in a certain format
                regDateCol.setCellFactory(column -> {
                    TableCell<User, Date> cell = new TableCell<User, Date>(){
                        private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                        @Override
                        protected void updateItem(Date item, boolean empty){
                            super.updateItem(item, empty);
                            if (empty){
                                setText(null);
                            }
                            else {
                                setText(format.format(table.getItems().get(getIndex()).getRegDate()));
                            }
                        }
                    };
                    return cell;
                });


                mailCol.setCellValueFactory(new PropertyValueFactory<User, String>("mail"));
                ageCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));
                countryCol.setCellValueFactory(new PropertyValueFactory<User, String>("country"));

                UserRepository userRepository = new UserRepository(file);
                table.setItems(FXCollections.observableArrayList(userRepository.getUsers()));

                // Cell Factory for columns with check boxes
                Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactoryIsSent =
                        new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                            @Override
                            public TableCell call(final TableColumn<User, Boolean> param) {
                                final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
                                    CheckBox btn = new CheckBox();
                                    @Override
                                    public void updateItem(Boolean item, boolean empty) {
                                        super.updateItem(item, empty);
                                        if (empty) {
                                            setGraphic(null);
                                            setText(null);
                                        } else {
                                            btn.setSelected(table.getItems().get(getIndex()).isSent());
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

                isSentCol.setCellFactory(cellFactoryIsSent);

                // Cell factory for column with buttons
                Callback<TableColumn<User, String>, TableCell<User, String>> cellFactoryButton =
                        new Callback<TableColumn<User, String>, TableCell<User, String>>() {
                            @Override
                            public TableCell call(final TableColumn<User, String> param) {
                                final TableCell<User, String> cell = new TableCell<User, String>() {
                                    final Button btn = new Button("Send message");
                                    {
                                        btn.getStyleClass().add("primary");
                                    }
                                    @Override
                                    public void updateItem(String item, boolean empty) {
                                        super.updateItem(item, empty);
                                        if (empty) {
                                            setGraphic(null);
                                            setText(null);
                                        } else {
                                            btn.setOnAction(event -> {
                                                try {
                                                    Stage sendMailStage = App.openWindow("mail.fxml", null);
                                                    assert sendMailStage != null;
                                                    sendMailStage.show();
                                                } catch (IOException ignored) {}
                                            });
                                            setGraphic(btn);
                                            setText(null);
                                        }
                                    }
                                };
                                return cell;
                            }
                        };
                buttonCol.setCellFactory(cellFactoryButton);

                table.getColumns().addAll(isSentCol, idCol, nameCol, regDateCol, mailCol, ageCol, countryCol, buttonCol);
            }
            else throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            App.showAlertWithoutHeaderText("Error!", "You didn't chose any file", Alert.AlertType.ERROR);
        }

    }

    public void generateUsers(ActionEvent actionEvent) {
        UserRepository userRepository = new UserRepository(new File("send.json"));
        userRepository.fill(10);
        System.out.println(userRepository.getUsers());
        ObjectMapper objectMapper = new ObjectMapper();
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("users.json"))){
            objectMapper.writeValue(bufferedWriter, userRepository.getUsers());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}