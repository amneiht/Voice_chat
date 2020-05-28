package application.radiocell;

import java.util.EnumSet;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class CreateTable {
	public static void makeReQuestTable(TableView<UserRq> tb) {
		// TableView<User> tb = new TableView<User>();
		TableColumn<UserRq, String> name = new TableColumn<UserRq, String>("Ten user");
		name.setCellValueFactory(new PropertyValueFactory<UserRq, String>("name"));
		name.setMinWidth(200);
		TableColumn<UserRq, Chose> act = new TableColumn<UserRq, Chose>("Chap nhan");
		act.setCellValueFactory(new PropertyValueFactory<UserRq, Chose>("action"));
		act.setCellFactory((param) -> new RadioButtonCell<UserRq, Chose>(EnumSet.allOf(Chose.class)));
		act.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UserRq, Chose>>() {

			@Override
			public void handle(CellEditEvent<UserRq, Chose> t) {
				int l = t.getTablePosition().getRow();
				UserRq us = (UserRq) t.getTableView().getItems().get(l);
				us.setAction(t.getNewValue());
			}
		});
		act.setMinWidth(200);
		tb.getColumns().add(name);
		tb.getColumns().add(act);
		tb.setEditable(true);
		// tb.setIte
	}

	public static void makeChoiseFrendTable(TableView<GFriend> tb) {
		TableColumn<GFriend, String> name = new TableColumn<GFriend, String>("Ten user");
		name.setCellValueFactory(new PropertyValueFactory<GFriend, String>("name"));
		name.setMinWidth(200);
		TableColumn<GFriend, AddF> act = new TableColumn<GFriend, AddF>("Chon");
		act.setMinWidth(200);
		act.setCellValueFactory(new PropertyValueFactory<GFriend, AddF>("action"));
		act.setCellFactory((param) -> new RadioButtonCell<GFriend, AddF>(EnumSet.allOf(AddF.class)));
		act.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GFriend, AddF>>() {

			@Override
			public void handle(CellEditEvent<GFriend, AddF> t) {
				int l = t.getTablePosition().getRow();
				GFriend us = (GFriend) t.getTableView().getItems().get(l);
				us.setAction(t.getNewValue());
			}
		});
		tb.getColumns().add(name);
		tb.getColumns().add(act);
		tb.setEditable(true);
	}

	public static void setUser(TableView<UserRq> tb, List<UserRq> lp) {
		try {
			ObservableList<UserRq> data = FXCollections.observableArrayList(lp);
			tb.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setGFriend(TableView<GFriend> tb, List<GFriend> lp) {
		try {
			ObservableList<GFriend> data = FXCollections.observableArrayList(lp);
			tb.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class RadioButtonCell<S, T extends Enum<T>> extends TableCell<S, T> {

	private EnumSet<T> enumeration;

	public RadioButtonCell(EnumSet<T> enumeration) {
		this.enumeration = enumeration;
	}

	@Override
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty) {
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
				public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
					getTableView().edit(getIndex(), getTableColumn());
					RadioButtonCell.this.commitEdit((T) newValue.getUserData());
				}
			});
			setGraphic(hb);
		}
	}
}
