package com.iaroslaveremeev.tablefx.conrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.tablefx.App;
import com.iaroslaveremeev.tablefx.model.User;
import com.iaroslaveremeev.tablefx.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;

public class MainController {

    public TableView<User> table;
    public Preferences prefs;

    @FXML
    public void openUsers(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\tirsb\\IdeaProjects\\JavaEE\\TableFX"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "JSON files", "*.json", "*.JSON"));
        File file = fileChooser.showOpenDialog(null);
        try  {
            if(file != null){
                UserRepository userRepository = new UserRepository(file);
                initializeTable(userRepository);
                Stage passwordInput = App.openWindow("password.fxml", null);
                assert passwordInput != null;
                passwordInput.setTitle("Password input");
                passwordInput.showAndWait();
            }
            else throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            App.showAlertWithoutHeaderText("Error!", "You didn't chose any file", Alert.AlertType.ERROR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeTable(UserRepository userRepository){
        table.setItems(FXCollections.observableArrayList(userRepository.getUsers()));

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // Column with check boxes showing if any message is already sent
        TableColumn<User, Boolean> isSentCol = new TableColumn<User, Boolean>("Sent");
        isSentCol.setStyle( "-fx-alignment: CENTER;");
        isSentCol.setMinWidth(50);
        // User id column
        TableColumn<User, Integer> idCol = new TableColumn<User, Integer>("User ID");
        idCol.setStyle( "-fx-alignment: CENTER;");
        idCol.setMinWidth(50);
        idCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        // Cell Factory for check boxes column
        Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactoryIsSent =
                new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                    @Override
                    public TableCell call(final TableColumn<User, Boolean> param) {
                        final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
                            final CheckBox btn = new CheckBox();
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
        // User name column
        TableColumn<User, String> nameCol = new TableColumn<User, String>("Name");
        nameCol.setStyle( "-fx-alignment: CENTER-LEFT;");
        nameCol.setMinWidth(125);
        nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        // User registration date column
        TableColumn<User, Date> regDateCol = new TableColumn<User, Date>("Registration date");
        regDateCol.setStyle( "-fx-alignment: CENTER-LEFT;");
        regDateCol.setMinWidth(125);
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
        // User e-mail column
        TableColumn<User, String> mailCol = new TableColumn<User, String>("E-mail");
        mailCol.setStyle( "-fx-alignment: CENTER-LEFT;");
        mailCol.setMinWidth(150);
        mailCol.setCellValueFactory(new PropertyValueFactory<User, String>("mail"));
        // User age column
        TableColumn<User, Integer> ageCol = new TableColumn<User, Integer>("Age");
        ageCol.setStyle( "-fx-alignment: CENTER;");
        ageCol.setMinWidth(50);
        ageCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));
        // User country column
        TableColumn<User, String> countryCol = new TableColumn<User, String>("Country");
        countryCol.setStyle( "-fx-alignment: CENTER-LEFT;");
        countryCol.setMinWidth(100);
        countryCol.setCellValueFactory(new PropertyValueFactory<User, String>("country"));
        // Column with "Send message" buttons for each user
        TableColumn<User, String> buttonCol = new TableColumn<User, String>("Action");
        buttonCol.setStyle( "-fx-alignment: CENTER;");
        buttonCol.setMinWidth(150);
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
                                            prefs = Preferences.userRoot().node("mail");
                                            prefs.put("mail", getTableRow().getItem().getMail());
                                            prefs.putBoolean("sent", getTableRow().getItem().isSent());
                                            Stage sendMailStage = App.openWindow("mail.fxml", null);
                                            assert sendMailStage != null;
                                            sendMailStage.setTitle("Send mail to the chosen user");
                                            sendMailStage.showAndWait();
                                            // Updating sent status in user repository
                                            if (Preferences.userRoot().node("mail").getBoolean("sent", true)){
                                                userRepository.getUsers().get(getIndex()).setSent(true);
                                            }
                                            initializeTable(userRepository);
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
        // Adding all data in columns
        table.getColumns().addAll(isSentCol, idCol, nameCol, regDateCol, mailCol, ageCol, countryCol, buttonCol);
    }

    // Method for random users generation for testing table format purposes
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