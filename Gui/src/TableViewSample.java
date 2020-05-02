import java.util.EnumSet;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TableViewSample extends Application {

    private final TableView<Person> table = new TableView<>();
    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "jacob.smith@example.com",Participation.MAYBE),
            new Person("Isabella", "Johnson", "isabella.johnson@example.com",Participation.MAYBE),
            new Person("Ethan", "Williams", "ethan.williams@example.com",Participation.MAYBE),
            new Person("Emma", "Jones", "emma.jones@example.com",Participation.MAYBE),
            new Person("Michael", "Brown", "michael.brown@example.com",Participation.MAYBE));
    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(650);
        stage.setHeight(550);

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        table.setMinWidth(640);

        TableColumn<Person,String> firstNameCol = new TableColumn<Person,String>("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        TableColumn<Person,String>lastNameCol = new TableColumn<Person,String>("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        TableColumn<Person,String> emailCol = new TableColumn<Person,String>("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("email"));

        TableColumn<Person,Participation> participationColumn = new TableColumn<Person,Participation>("Participation");
        participationColumn.setCellFactory((param) -> new RadioButtonCell<Person, Participation>(EnumSet.allOf(Participation.class)));
        participationColumn.setCellValueFactory(new PropertyValueFactory<Person, Participation>("participation"));
        participationColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, Participation>>() {
                    @Override
                    public void handle(CellEditEvent<Person, Participation> t) {
                    	System.out.println("edit ccell");
                        ((Person) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setParticipation(t.getNewValue());
                    }
                }
            );


        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol, participationColumn );

        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Last Name");
        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(emailCol.getPrefWidth());
        addEmail.setPromptText("Email");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            data.add(new Person(
                    addFirstName.getText(),
                    addLastName.getText(),
                    addEmail.getText(),
                    Participation.NO
                    ));
            addFirstName.clear();
            addLastName.clear();
            addEmail.clear();
        });

        hb.getChildren().addAll(addFirstName, addLastName, addEmail, addButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }


    public static enum Participation {
        YES,
        NO,
        MAYBE;

        public String toString() {
            return super.toString().toLowerCase();
        };
    }

    public static class Person {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleObjectProperty<Participation> participation = new SimpleObjectProperty<Participation>();

        private Person(String fName, String lName, String email, Participation p ) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
            this.participation.setValue(p);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fName) {
            email.set(fName);
        }

        public void setParticipation(Participation p){
            participation.set(p);
        }

        public Participation getParticipation(){
            return participation.get();
        }
    }





    public static class RadioButtonCell<S,T extends Enum<T>> extends TableCell<S,T>{

        private EnumSet<T> enumeration;

        public RadioButtonCell(EnumSet<T> enumeration) {
            this.enumeration = enumeration;
        }

        @Override
        protected void updateItem(T item, boolean empty)
        {
            super.updateItem(item, empty);
            if (!empty) 
            {
                // gui setup
                HBox hb = new HBox(7);
                hb.setAlignment(Pos.CENTER);
                final ToggleGroup group = new ToggleGroup();

                // create a radio button for each 'element' of the enumeration
                for (Enum<T> enumElement : enumeration) {
                    RadioButton radioButton = new RadioButton(enumElement.toString());
                    radioButton.setUserData(enumElement);
                    radioButton.setToggleGroup(group);
                    hb.getChildren().add(radioButton);
                    if (enumElement.equals(item)) {
                        radioButton.setSelected(true);
                    }
                }

                // issue events on change of the selected radio button
                group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void changed(ObservableValue<? extends Toggle> observable,
                            Toggle oldValue, Toggle newValue) {
                        getTableView().edit(getIndex(), getTableColumn());
                        RadioButtonCell.this.commitEdit((T) newValue.getUserData());
                    }
                });
                setGraphic(hb);
            } 
        }
    }
} 