import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

/**
 * Created by Dana on 30-Mar-16.
 */

public class Main extends Application {

    Scene scene;
    TableView<Drink> table;
    TextField nameInput;
    TextField priceInput;
    TextField quantityInput;
    TextField volumeInput;
    TextField percentageInput;
    TableColumn<Drink, Integer> quantity;
    Label aux;
    Drink drink;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        ObservableList<Drink> drinks = FXCollections.observableArrayList(getDrinks());

        //Label for Title
        Label title = new Label("KEA Friday Bar - Time to Party!");
        Label title2 = new Label("By KEA CS, Lygten 37, DK-2400, Copenhagen NV");
        title.setId("title-label");
        title2.setId("title-label2");

        //VBox for titles
        VBox titles = new VBox(10);
        titles.getChildren().addAll(title, title2);
        titles.setAlignment(Pos.CENTER);

        //TableView - is editable, after every edit the file is updated
        table = new TableView<>();

        table.itemsProperty().setValue(drinks);

        TableColumn<Drink, String> name = new TableColumn<>("Name");
        TableColumn<Drink, Integer> price = new TableColumn<>("Price");
        quantity = new TableColumn<>("Quantity");
        TableColumn<Drink, Integer> volume = new TableColumn<>("Volume(ml)");
        TableColumn<Drink, Double> percentage = new TableColumn<>("Alc. %");

        name.setCellValueFactory(e -> e.getValue().nameProperty());
        price.setCellValueFactory(e -> e.getValue().priceProperty().asObject());
        quantity.setCellValueFactory(e -> e.getValue().quantityProperty().asObject());
        volume.setCellValueFactory(e -> e.getValue().volumeProperty().asObject());
        percentage.setCellValueFactory(e -> e.getValue().percentageProperty().asObject());

        name.setCellFactory(TextFieldTableCell.forTableColumn());
        price.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        volume.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        percentage.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        name.setOnEditCommit((TableColumn.CellEditEvent<Drink, String> event)->{
            ((Drink)event.getTableView().getItems().get(event.getTablePosition().getRow())).setName
                    (event.getNewValue());
            try{
                PrintStream writer = new PrintStream(new File("drinks.txt"));
                for(int i = 0; i < drinks.size(); i++)
                    writer.println(drinks.get(i));
            } catch (FileNotFoundException exception){}
        });
        name.setMinWidth(100);

        price.setOnEditCommit((TableColumn.CellEditEvent<Drink, Integer> event) -> {
            ((Drink) event.getTableView().getItems().get(event.getTablePosition().getRow())).setPrice
                    (((Integer)event.getNewValue()));
            try{
                PrintStream writer = new PrintStream(new File("drinks.txt"));
                for(int i = 0; i < drinks.size(); i++)
                    writer.println(drinks.get(i));
            } catch (FileNotFoundException exception){}
        });
        price.setMinWidth(100);

        quantity.setOnEditCommit((TableColumn.CellEditEvent<Drink, Integer> event) -> {
            ((Drink) event.getTableView().getItems().get(event.getTablePosition().getRow())).setQuantity
                    (((Integer) event.getNewValue()));
            try{
                PrintStream writer = new PrintStream(new File("drinks.txt"));
                for(int i = 0; i < drinks.size(); i++)
                    writer.println(drinks.get(i));
            } catch (FileNotFoundException exception){}
        });
        quantity.setMinWidth(100);

        volume.setOnEditCommit((TableColumn.CellEditEvent<Drink, Integer> event) -> {
            ((Drink)event.getTableView().getItems().get(event.getTablePosition().getRow())).setVolume
                    (((Integer)event.getNewValue()));
            try{
                PrintStream writer = new PrintStream(new File("drinks.txt"));
                for(int i = 0; i < drinks.size(); i++)
                    writer.println(drinks.get(i));
            } catch (FileNotFoundException exception){}
        });
        volume.setMinWidth(100);

        percentage.setOnEditCommit((TableColumn.CellEditEvent<Drink, Double> event) -> {
            ((Drink) event.getTableView().getItems().get(event.getTablePosition().getRow())).setPercentage
                    (((Double) event.getNewValue()));
            try{
                PrintStream writer = new PrintStream(new File("drinks.txt"));
                for(int i = 0; i < drinks.size(); i++)
                    writer.println(drinks.get(i));
            } catch (FileNotFoundException exception){}
        });
        percentage.setMinWidth(98);

        table.setEditable(true);
        table.getColumns().addAll(name, price, quantity, volume, percentage);

        //Name input
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(60);
        nameInput.setAlignment(Pos.CENTER);

        //Price input
        priceInput = new TextField();
        priceInput.setPromptText("Price");
        priceInput.setMinWidth(60);
        priceInput.setAlignment(Pos.CENTER);

        //Quantity input
        quantityInput = new TextField();
        quantityInput.setPromptText("Quantity");
        quantityInput.setMinWidth(60);
        quantityInput.setAlignment(Pos.CENTER);

        //Volume input
        volumeInput = new TextField();
        volumeInput.setPromptText("Volume");
        volumeInput.setMinWidth(60);
        volumeInput.setAlignment(Pos.CENTER);

        //Alcohol percentage input
        percentageInput = new TextField();
        percentageInput.setPromptText("Alc. percentage");
        percentageInput.setMinWidth(100);
        percentageInput.setAlignment(Pos.CENTER);

        //HBox for inputs - adding new drink
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, priceInput, quantityInput, volumeInput, percentageInput);

        //Add Button
        Button add = new Button("Add");
        add.setOnAction(e -> addButtonClicked());

        //Remove Button
        Button remove = new Button("Remove");
        remove.setOnAction(e -> removeButtonClicked());

        //Sell Button
        Button sell = new Button("Sell");
        sell.setOnAction(e -> sellButtonClicked(primaryStage));
        sell.setId("buttonSell");

        //HBox for buttons - add, remove, sell
        HBox buttonBox = new HBox(50);
        buttonBox.getChildren().addAll(add, remove, sell);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(15, 12, 15, 12));

        //VBox as main layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(titles, table, hBox, buttonBox);
        layout.getStylesheets().add("MyCSS.css");

        //Scene
        scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Returns all the drink objects - read from file
    public ArrayList<Drink> getDrinks()throws Exception{
        ArrayList<Drink> drinks = new ArrayList<>();
        Scanner input = new Scanner(new File("drinks.txt"));
        while (input.hasNextLine()){
            String line = input.nextLine();
            Scanner tokenScan = new Scanner(line);
            Drink drink = new Drink(tokenScan.next(), tokenScan.nextInt(),
                    tokenScan.nextInt(), tokenScan.nextInt(), tokenScan.nextDouble());
            drinks.add(drink);
        }
        return drinks;
    }

    //Add button clicked - adds drink object in the tableview and in file
    public void addButtonClicked(){
        try {
            //Checks if price, quantity, volume and percentage inputs are numeric
            if (!isNumeric(priceInput.getText()) || !isNumeric(quantityInput.getText()) || !isNumeric(volumeInput.getText())
                    || !isNumeric(percentageInput.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Please enter numeric value.");
                alert.showAndWait();
                priceInput.clear();
                quantityInput.clear();
                volumeInput.clear();
                percentageInput.clear();
            } else {
                //Checks if all fields are filled - if not, shows alert
                if (nameInput.getText().isEmpty() || priceInput.getText().isEmpty() || quantityInput.getText().isEmpty() ||
                        volumeInput.getText().isEmpty() || percentageInput.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("All fields must be filled.");
                    alert.showAndWait();
                } else { //else, add to the array and to the file
                    PrintWriter out = new PrintWriter(new FileWriter("drinks.txt", true));
                    out.println(nameInput.getText() + " " + priceInput.getText() + " " + quantityInput.getText() + " " +
                            volumeInput.getText() + " " + percentageInput.getText());
                    out.close();

                    Drink drink = new Drink(
                            nameInput.getText(), Integer.parseInt(priceInput.getText()), Integer.parseInt(quantityInput.getText()),
                            Integer.parseInt(volumeInput.getText()), Double.parseDouble(percentageInput.getText()));

                    table.getItems().add(drink);
                    nameInput.clear();
                    priceInput.clear();
                    quantityInput.clear();
                    volumeInput.clear();
                    percentageInput.clear();
                }
            }
        }catch (IOException exception){}
    }

    //Remove button clicked - removes drink object selected from tableview and file
    public void removeButtonClicked(){
        try {
            ObservableList<Drink> allDrinks = table.getItems();

            //Needs multirowselection is set to true
            ObservableList<Drink> readOnlyItems = table.getSelectionModel().getSelectedItems();

            //Removes all selected elements in the table
            readOnlyItems.stream().forEach((item) -> {
                allDrinks.remove(item);
            });

            //Clear the selection
            table.getSelectionModel().clearSelection();

            //Removes drink in the file
            PrintStream output = new PrintStream (new File("drinks.txt"));
            for(int i = 0; i < allDrinks.size(); i++)
                output.println(allDrinks.get(i));

        }catch (Exception exception){}
    }

    //Sell button clicked - creates new scene in which you are able to sell drinks
    public void sellButtonClicked(Stage primaryStage) {
        try {
            ObservableList<Drink> drinks = FXCollections.observableArrayList(getDrinks());
            drink = new Drink();

            //Auxiliary Label - helps to calculate the price
            aux = new Label();

            //ComboBox for the drinks
            ComboBox comboBox = new ComboBox();
            comboBox.setPromptText("Select...");
            for (int i = 0; i < drinks.size(); i++)
                comboBox.getItems().add(drinks.get(i).getName());

            comboBox.setOnAction(e -> {
                //finds the drink object corresponding to the ComboBox choice
                for(int i = 0; i < drinks.size(); i++){
                    if(comboBox.getValue().toString().equals(drinks.get(i).getName()))
                        drink = drinks.get(i);
                }
                //after the drink is found, auxiliary label's text is set to the price of that drink
                aux.setText(Integer.toString(drink.getPrice()));
            });

            //Quantity input
            TextField qInput = new TextField();
            qInput.setPromptText("Quantity");
            qInput.setMinWidth(60);

            //Labels for price
            Label price = new Label("Price: ");
            Label actualPrice = new Label();
            Label dkk = new Label("dkk");

            //Calculate actual price
            qInput.textProperty().addListener((observable, oldValue, newValue) -> {
                //checks if the ComboBox has a value - first you need to choose drink, and only after input quantity
                //if yes, show alert
                if(comboBox.getValue() == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select drink first.");
                    alert.showAndWait();
                    qInput.clear();
                }
                else {
                    //checks if the String input represents a numeric value
                    //if not, shows alert
                    if (!isNumeric(qInput.getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter numeric value.");
                        alert.showAndWait();
                        qInput.clear();
                    } else {
                            if (qInput.getText().isEmpty())
                                actualPrice.setText(" ");
                            else {
                                if (Integer.parseInt(qInput.getText()) > drink.getQuantity()) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("ERROR");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Sorry, not enough items.");
                                    alert.showAndWait();
                                } else {
                                    //calculates the price - price/drink (from auxiliary label) * quantity (from qInput TextField)
                                    int actPrice = getAuxText(aux) * Integer.parseInt(qInput.getText());
                                    newValue = Integer.toString(actPrice);
                                    actualPrice.setText(newValue);
                                }
                            }

                    }
                }
            });

            //HBox for ComboBox, TextField and Labels
            HBox upperBox = new HBox(20);
            upperBox.getChildren().addAll(comboBox, qInput, price, actualPrice, dkk);
            upperBox.setAlignment(Pos.CENTER);

            //Sell Button - shows & asks for confirmation for order, updates tableView, updates file
            Button sellItem = new Button("Sell");
            sellItem.setOnAction(e -> {
                //checks if there is a drink chosen and a quantity entered
                //if not, shows alert
                if(comboBox.getValue() == null || qInput.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("First, choose drink and quantity.");
                    alert.showAndWait();
                }
                else {
                    //if yes, shows drink, price/drink, quantity and total price
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Order");
                    alert.setHeaderText("Your order has been placed. Confirm purchase?");
                    alert.setContentText("Drink:            " + comboBox.getValue().toString() +
                            "\nPrice/drink:   " + aux.getText() +
                            "\nQuantity:       " + qInput.getText() +
                            "\nTotal:         " + actualPrice.getText() + " dkk");

                    Optional<ButtonType> result = alert.showAndWait();
                    //when "OK" button is pressed, table and file are updated
                    if (result.get() == ButtonType.OK) {
                        try {
                            int q = drink.getQuantity() - Integer.parseInt(qInput.getText());
                            drink.setQuantity(q);
                            table.getItems().clear();
                            table.getItems().addAll(drinks);
                            PrintStream output = new PrintStream(new File("drinks.txt"));
                            for (int i = 0; i < drinks.size(); i++)
                                output.println(drinks.get(i));
                            primaryStage.setScene(scene);
                        } catch (Exception exception) {}
                    } else {
                        alert.close();
                    }
                }
            });
            sellItem.setId("buttonSell");

            //Cancel Button - goes to the first scene
            Button cancel = new Button("Cancel");
            cancel.setOnAction(e -> {
                primaryStage.setScene(scene);
            });

            //Top Image
            Image kea = new Image("kea.jpg");
            ImageView image = new ImageView();
            image.setImage(kea);
            image.setFitHeight(200);
            image.setFitWidth(450);

            //HBox for buttons - sellItem, Cancel
            HBox buttonBox = new HBox(20);
            buttonBox.getChildren().addAll(sellItem, cancel);
            buttonBox.setAlignment(Pos.CENTER);

            //VBox as main layout
            VBox layout = new VBox(30);
            layout.setAlignment(Pos.TOP_CENTER);
            layout.getChildren().addAll(image, upperBox, buttonBox);
            layout.getStylesheets().add("MyCSS2.css");

            //Second Scene
            Scene scene2 = new Scene(layout, 500, 500);
            primaryStage.setScene(scene2);
            primaryStage.show();

        }catch (Exception exception){}
    }

    //String to int converter
    public int getAuxText(Label aux){
        int price = Integer.parseInt(aux.getText());
        return price;
    }

    //Checks if input from qInput TextField is numeric
    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
