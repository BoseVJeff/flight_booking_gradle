package io.github.bosev.flight_booking_gradle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserDump implements Initializable {
	private Database database;
	private static final ObservableList<User> userListEmpty=FXCollections.observableArrayList();
	private final ObservableList<User> userList=FXCollections.observableArrayList();

	public UserDump() {
		try {
			this.database=Database.getInstance();
		} catch (SQLException e) {
			System.err.println("Error getting database instance!");
//			this.errorLabel.setText("Error getting database instance!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
			return;
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		System.out.println("Init!");
		this.tableView.setItems(this.userList);

		this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		this.phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		this.isAdminColumn.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
		CheckBoxTableCell<User,Boolean> checkBoxTableCell=new CheckBoxTableCell<User,Boolean>(new Callback<Integer, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(Integer index) {
				return new SimpleBooleanProperty(userList.get(index).isAdmin);
			}
		});
////		CheckBoxTableCell<User,Boolean> checkBoxTableCell=CheckBoxTableCell.forTableColumn(this.isAdminColumn);
////		This is a two-way binding to the value returned by the `call` method.
//		checkBoxTableCell.setSelectedStateCallback(new Callback<Integer, ObservableValue<Boolean>>() {
//			@Override
//			public ObservableValue<Boolean> call(Integer index) {
////				`index` is index of
//				System.out.println(index);
//				return new SimpleBooleanProperty();
//			}
//		});
//		this.isAdminColumn.setCellFactory(new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
//			private CheckBox checkBox=new CheckBox();
//
//			@Override
//			public TableCell<User, Boolean> call(TableColumn<User, Boolean> param) {
//				return null;
//			}
//		});

		this.isAdminColumn.setCellFactory(new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
			@Override
			public TableCell<User, Boolean> call(TableColumn<User, Boolean> value) {
				return new CheckBoxTableCell<User,Boolean>(new Callback<Integer, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(Integer z) {
						User user=userList.get(z);
						SimpleBooleanProperty isAdminProperty=new SimpleBooleanProperty(user.isAdmin);
						isAdminProperty.addListener(new ChangeListener<Boolean>() {
							@Override
							public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
								if(oldValue) {
//									User is currently an admin, remove him from this position
									try {
										database.removeAdmin(user.id);
									} catch (SQLException e) {
										System.err.println("SQL error unsetting users as admin!");
										System.err.println(e.getMessage());
										for (int i = 0; i < e.getStackTrace().length; i++) {
											System.err.println(e.getStackTrace()[i].toString());
										}
									}
								} else {
//									User is currently not an admin, make 'em one
									try {
										database.makeAdmin(user.id);
									} catch (SQLException e) {
										System.err.println("SQL error unsetting users as admin!");
										System.err.println(e.getMessage());
										for (int i = 0; i < e.getStackTrace().length; i++) {
											System.err.println(e.getStackTrace()[i].toString());
										}
									}
								}
								System.out.println("Changed value of id "+user.id+" from "+oldValue+" to "+newValue);

							}
						});
						return isAdminProperty;
					}
				});
			}
		});

		this.refresh();
	}



	public static Scene getScene() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(UserDump.class.getResource("UserDump.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		return scene;
	}

	@FXML
	protected void refresh() {
		this.userList.clear();
		ArrayList<User> userArrayList;
		try {
			System.out.println("Getting all users...");
			userArrayList=this.database.getAllUsers();
		} catch (SQLException e) {
			System.err.println("SQL Error getting users!");
			this.errorLabel.setText("SQL Error getting users!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
			return;
		}
		for (User user : userArrayList) {
			System.out.println("Adding to table: "+user.name);
			this.userList.add(user);
		}
		System.out.println("Added all users!");
		System.out.println(this.userList.size());
	}

	@FXML
	private Label errorLabel;

	@FXML
	private TableView<User> tableView;

	@FXML
	private TableColumn<User, Integer> idColumn;

	@FXML
	private TableColumn<User,String> nameColumn;

	@FXML
	private TableColumn<User,String> emailColumn;

	@FXML
	private TableColumn<User,String> phoneColumn;

	@FXML
	private TableColumn<User, Boolean> isAdminColumn;

	@FXML
	private Button button;
}

//class CheckBoxTableCell<S,T> extends TableCell<S,Boolean> {
//	private final CheckBox checkBox;
//
//	public CheckBoxTableCell(final ObservableValue<Boolean> selectedProperty) {
//		this.checkBox=new CheckBox();
//		this.checkBox.selectedProperty().bindBidirectional(selectedProperty);
//	}
//}