package userinterface;

// system imports
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import javafx.scene.effect.DropShadow;
import model.*;
import model.ColorCollection;

//==============================================================================
public class InventoryCollectionView extends View
{
	protected TableView<InventoryTableModel> tableOfClothes;
	protected Button cancelButton;
	protected Button submitButton;
	protected Text actionText; 
	protected int entryCnt = 0;


	protected MessageView statusLog;


	//--------------------------------------------------------------------------
	public InventoryCollectionView(IModel icv)
	{
		super(icv, "InventoryCollectionView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setStyle("-fx-background-color: slategrey");
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                                            "));

		getChildren().add(container);

		populateFields();
		tableOfClothes.getSelectionModel().select(0);
	}

	//--------------------------------------------------------------------------
	protected void populateFields()
	{
		getEntryTableModelValues();
	}

	//--------------------------------------------------------------------------
	protected void getEntryTableModelValues()
	{

		ObservableList<InventoryTableModel> tableData = FXCollections.observableArrayList();
		try
		{
			InventoryCollection inventoryCollection = 
					(InventoryCollection)myModel.getState("InventoryList");

			Vector entryList = (Vector)inventoryCollection.getState("Inventory");

			if (entryList.size() > 0)
			{
				Enumeration entries = entryList.elements();

				while (entries.hasMoreElements() == true)
				{
					entryCnt++;
					Inventory nextItem = (Inventory)entries.nextElement();
					Vector<String> view = nextItem.getEntryListView();

					// add this list entry to the list
					InventoryTableModel nextTableRowData = new InventoryTableModel(view);
					tableData.add(nextTableRowData);

				}
				if(entryList.size() == 1)
					actionText.setText(entryCnt+" Inventory Found!");
				else 
					actionText.setText(entryCnt+" Inventory Found!");

				actionText.setFill(Color.LIGHTGREEN);
			}
			else
			{
				actionText.setText("No Inventory Found!");
				actionText.setFill(Color.FIREBRICK);
			}
			tableOfClothes.setItems(tableData);
		}	
		catch (Exception e) {//SQLException e) {
			// Need to handle this exception
		}
	}

	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
	{
		VBox container = new VBox(10);
		container.setPadding(new Insets(1, 1, 1, 30));

		Text clientText = new Text("OFFICE OF CAREER SERVICES");
		clientText.setFont(Font.font("Copperplate", FontWeight.EXTRA_BOLD, 25));
		clientText.setWrappingWidth(350);
		clientText.setTextAlignment(TextAlignment.CENTER);
		clientText.setFill(Color.DARKGREEN);
		container.getChildren().add(clientText);

		Text titleText = new Text(" Professional Clothes Closet Management System ");
		titleText.setFont(Font.font("Comic Sans", FontWeight.THIN, 25));
		titleText.setWrappingWidth(350);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.GOLD);
		container.getChildren().add(titleText);

		Text blankText = new Text("  ");
		blankText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		blankText.setWrappingWidth(350);
		blankText.setTextAlignment(TextAlignment.CENTER);
		blankText.setFill(Color.WHITE);
		container.getChildren().add(blankText);

		actionText = new Text(" ");//set later
		actionText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		actionText.setWrappingWidth(350);
		actionText.setTextAlignment(TextAlignment.CENTER);
		actionText.setFill(Color.BLACK);
		container.getChildren().add(actionText);

		return container;
	}

	// Create the main form content
	//-------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 25, 10, 0));

		tableOfClothes = new TableView<InventoryTableModel>();
		tableOfClothes.setEffect(new DropShadow());
		tableOfClothes.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-selection-bar:lightgreen;");
		tableOfClothes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		TableColumn barcodeColumn = new TableColumn("Barcode") ;
		barcodeColumn.setMinWidth(50);
		barcodeColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("barcode"));

		TableColumn genderColumn = new TableColumn("Gender") ;
		genderColumn.setMinWidth(35);
		genderColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("gender"));

		TableColumn sizeColumn = new TableColumn("Size") ;
		sizeColumn.setMinWidth(20);
		sizeColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("size"));

		TableColumn articleTypeColumn = new TableColumn("Article Type") ;
		articleTypeColumn.setMinWidth(50);
		articleTypeColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("articleType"));

		TableColumn color1Column = new TableColumn("Color 1") ;
		color1Column.setMinWidth(50);
		color1Column.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("color1"));

		TableColumn color2Column = new TableColumn("Color 2") ;
		color2Column.setMinWidth(50);
		color2Column.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("color2"));

		TableColumn brandColumn = new TableColumn("Brand") ;
		brandColumn.setMinWidth(50);
		brandColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("brand"));

		TableColumn notesColumn = new TableColumn("Notes") ;
		notesColumn.setMinWidth(100);
		notesColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("notes"));

		TableColumn statusColumn = new TableColumn("Status") ;
		statusColumn.setMinWidth(25);
		statusColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("status"));

		TableColumn donorLastNameColumn = new TableColumn("Donor Last Name") ;
		donorLastNameColumn.setMinWidth(50);
		donorLastNameColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("donorLastName"));

		TableColumn donorFirstNameColumn = new TableColumn("Donor First Name") ;
		donorFirstNameColumn.setMinWidth(50);
		donorFirstNameColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("donorFirstName"));

		TableColumn donorPhoneColumn = new TableColumn("Donor Phone") ;
		donorPhoneColumn.setMinWidth(50);
		donorPhoneColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("donorPhone"));

		TableColumn donorEmailColumn = new TableColumn("Donor Email") ;
		donorEmailColumn.setMinWidth(60);
		donorEmailColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("donorEmail"));

		TableColumn dateDonatedColumn = new TableColumn("Date Donated") ;
		dateDonatedColumn.setMinWidth(20);
		dateDonatedColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("dateDonated"));

		tableOfClothes.getColumns().addAll(barcodeColumn, genderColumn, sizeColumn, articleTypeColumn, color1Column, color2Column, brandColumn, 
				notesColumn, statusColumn, donorLastNameColumn, donorFirstNameColumn, donorPhoneColumn, donorEmailColumn, dateDonatedColumn);

		tableOfClothes.setOnMousePressed((MouseEvent event) -> {
			if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
				processInventorySelected();
			}
		});
		ImageView icon = new ImageView(new Image("/images/check.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		submitButton = new Button("Select",icon);
		submitButton.setStyle("-fx-background-color: lightgreen; ");
		submitButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		submitButton.setOnAction((ActionEvent e) -> {
			clearErrorMessage();
			// do the inquiry
			processInventorySelected();
		});
		submitButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			submitButton.setEffect(new DropShadow());
		});
		submitButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			submitButton.setEffect(null);
		});
		icon = new ImageView(new Image("/images/return.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		cancelButton = new Button("Return", icon);
		cancelButton.setStyle("-fx-background-color: PALEVIOLETRED; ");
		cancelButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		cancelButton.setOnAction((ActionEvent e) -> {
			clearErrorMessage();
			myModel.stateChangeRequest("CancelInventoryList", null);
		});
		cancelButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			cancelButton.setEffect(new DropShadow());
		});
		cancelButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			cancelButton.setEffect(null);
		});

		HBox btnContainer = new HBox(10);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(cancelButton);

		vbox.getChildren().add(grid);
		tableOfClothes.setPrefHeight(250);
		vbox.getChildren().add(tableOfClothes);
		vbox.getChildren().add(btnContainer);
		vbox.setPadding(new Insets(10,10,10,10));

		return vbox;
	}

	//--------------------------------------------------------------------------
	public void updateState(String key, Object value)
	{
	}

	//--------------------------------------------------------------------------
	protected void processInventorySelected()
	{
		InventoryTableModel selectedItem = tableOfClothes.getSelectionModel().getSelectedItem();

		if(selectedItem != null)
		{
			String selectedBarcode = selectedItem.getBarcode();

			myModel.stateChangeRequest("InventorySelected", selectedBarcode);
		}
	}

	//--------------------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}


	/**
	 * Display info message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}


}
