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
import java.util.Properties;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;

// project imports
import impresario.IModel;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.effect.InnerShadow;
import model.Inventory;
import model.InventoryCollection;

//==============================================================================
public class EnterRecepientInfoView extends View
{
	protected TableView<InventoryTableModel> tableOfClothes;
	protected TextField barcode;
	protected TextField netId;
	protected TextField firstName;
	protected TextField lastName;
	protected Button cancelButton;
	protected Button submitButton;
	protected Button addBarcodeButton;
	protected MessageView statusLog;
	protected Text actionText; 
        
	//--------------------------------------------------------------------------
	public EnterRecepientInfoView(IModel matt)
	{
		super(matt, "EnterRecepientInfoView");

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
		tableOfClothes.getSelectionModel().select(0); //autoselect first element
	}
	
	protected String getActionText() {
		return "** CHECKOUT CLOTHING ITEMS **";
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
					Inventory nextItem = (Inventory)entries.nextElement();
					Vector<String> view = nextItem.getEntryListView();

					// add this list entry to the list
					InventoryTableModel nextTableRowData = new InventoryTableModel(view);
					tableData.add(nextTableRowData);

				}
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
		clientText.setFont(Font.font("Copperplate", FontWeight.EXTRA_BOLD, 36));
                clientText.setEffect(new DropShadow());
		clientText.setTextAlignment(TextAlignment.CENTER);
		clientText.setFill(Color.WHITESMOKE);
		container.getChildren().add(clientText);

		Text titleText = new Text(" Professional Clothes Closet Management System ");
		titleText.setFont(Font.font("Copperplate", FontWeight.THIN, 28));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.GOLD);
		container.getChildren().add(titleText);

		Text blankText = new Text("  ");
		blankText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		blankText.setWrappingWidth(350);
		blankText.setTextAlignment(TextAlignment.CENTER);
		blankText.setFill(Color.WHITE);
		container.getChildren().add(blankText);
				
		actionText = new Text("     " + getActionText() + "       ");
		actionText.setFont(Font.font("Copperplate", FontWeight.BOLD, 22));
		actionText.setWrappingWidth(450);
		actionText.setTextAlignment(TextAlignment.CENTER);
		actionText.setFill(Color.DARKGREEN);
		container.getChildren().add(actionText);
		container.setAlignment(Pos.CENTER);

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

		TableColumn articleTypeColumn = new TableColumn("Article Type") ;
		articleTypeColumn.setMinWidth(50);
		articleTypeColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("articleType"));

		
				
		TableColumn brandColumn = new TableColumn("Brand") ;
		brandColumn.setMinWidth(50);
		brandColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("brand"));

		tableOfClothes.getColumns().addAll(barcodeColumn, articleTypeColumn, brandColumn);
		
		Text barcodeLabel = new Text(" Barcode : ");
		Font myFont = Font.font("Copperplate", FontWeight.THIN, 16);
		barcodeLabel.setFill(Color.GOLD);
		barcodeLabel.setFont(myFont);
		barcodeLabel.setWrappingWidth(145);
		barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(barcodeLabel, 0, 1);

		barcode = new TextField();
		barcode.setMinWidth(180);
		grid.add(barcode, 1, 1);
		

		Text netIdLabel = new Text(" Net ID : ");
		netIdLabel.setFill(Color.GOLD);
		netIdLabel.setFont(myFont);
		netIdLabel.setWrappingWidth(145);
		netIdLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(netIdLabel, 0, 2);

		netId = new TextField();
		netId.setMinWidth(180);
		grid.add(netId, 1, 2);

		Text firstNameLabel = new Text(" First Name : ");
		firstNameLabel.setFill(Color.GOLD);
		firstNameLabel.setFont(myFont);
		firstNameLabel.setWrappingWidth(145);
		firstNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(firstNameLabel, 0, 3);

		firstName = new TextField();
		firstName.setMinWidth(180);
		grid.add(firstName, 1, 3);

		Text lastNameLabel = new Text(" Last Name : ");
		lastNameLabel.setFill(Color.GOLD);
		lastNameLabel.setFont(myFont);
		lastNameLabel.setWrappingWidth(145);
		lastNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(lastNameLabel, 0, 4);

		lastName = new TextField();
		lastName.setMinWidth(180);
		grid.add(lastName, 1, 4);
		
		ImageView icon = new ImageView(new Image("/images/pluscolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		addBarcodeButton = new Button("Add Item",icon);
		addBarcodeButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		addBarcodeButton.setOnAction((ActionEvent e) -> {
			clearErrorMessage();
			String myBarcode = barcode.getText();
			Properties props = new Properties();
			props.setProperty("BarcodeToAdd", myBarcode);
			try{
				myModel.stateChangeRequest("AddBarcode", props);
				String receiverNetid = (String)myModel.getState("PossibleReceiverNetid");
				String dateTaken = (String)myModel.getState("PossibleDateTaken");
				if(receiverNetid != null && !receiverNetid.equals(""))
				{
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Dialog");
					alert.setHeaderText("Item has already been checked out!");
					alert.setContentText("This item has been checkout in the past by: " + receiverNetid + " on this date: " + dateTaken +  " (yyyy,MM,dd). Are you sure you would like to continue?");
					Optional<ButtonType> result = alert.showAndWait();
			
					if (result.get() == ButtonType.OK)
					{
						//do Nothing
					}
					else
					{
						myModel.stateChangeRequest("CancelCheckOutCI", null);
					}
				}
				barcode.clear();
			}
			catch(Exception err){
				displayErrorMessage("ERROR: No Inventory Record Found!");
			}
			getEntryTableModelValues();
		});
		
		addBarcodeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			addBarcodeButton.setEffect(new DropShadow());
		});
		addBarcodeButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			addBarcodeButton.setEffect(null);
		});
		
		grid.add(addBarcodeButton, 2, 1);

		icon = new ImageView(new Image("/images/check.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		submitButton = new Button("Select",icon);
		submitButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		submitButton.setOnAction((ActionEvent e) -> {
			clearErrorMessage();
			clearOutlines();
			Properties props = new Properties();
			
			
			String recepientNetID = netId.getText();
			if (recepientNetID.length() > 0 )
			{
				String recepientFirstName = firstName.getText();
				if (recepientFirstName.length() > 0)
				{
					String recepientLastName = lastName.getText();
					if (recepientLastName.length() > 0)
					{
						
						props.setProperty("ReceiverNetid", recepientNetID);
						props.setProperty("ReceiverFirstName", recepientFirstName);
						props.setProperty("ReceiverLastName", recepientLastName);
						
						//Code here to check If the Date is within 6 months of last checkout
						Properties NetIdCheck = new Properties();
						NetIdCheck.setProperty("NetIdCheck", recepientNetID);
						myModel.stateChangeRequest("NetIdCheck", NetIdCheck);
						int netIdCount = (Integer)myModel.getState("NetIdCheck");
						if(netIdCount >= 1)
						{
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Confirmation Dialog");
							alert.setHeaderText("Too Many Checkouts!");
							alert.setContentText("This person has checked out " + netIdCount + " item(s) within the past 6 months. Are you sure you would like to continue?");
							Optional<ButtonType> result = alert.showAndWait();
							
							if (result.get() == ButtonType.OK)
							{
								myModel.stateChangeRequest("RecepientData", props);
								
								alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Confirmation Dialog");
								alert.setHeaderText("Done!");
								alert.setContentText("Items have been checked out");
								result = alert.showAndWait();
							}
							else
								myModel.stateChangeRequest("CancelCheckOutCI", null);
						}
						else
						{
							myModel.stateChangeRequest("RecepientData", props);                                                       
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Confirmation Dialog");
							alert.setHeaderText("Done!");
							alert.setContentText("Items have been checked out");
							Optional<ButtonType> result = alert.showAndWait();
                                                        myModel.stateChangeRequest("CancelCheckOutCI", null);
						}						
					}
					else {
						lastName.setStyle("-fx-border-color: firebrick;");
						displayErrorMessage("ERROR: Please Enter the Recepient's Last Name");
					}
				}
				else {
					firstName.setStyle("-fx-border-color: firebrick;");
					displayErrorMessage("ERROR: Please Enter the Recepient's First Name");
				}
			}
			else {
				netId.setStyle("-fx-border-color: firebrick;");
				displayErrorMessage("ERROR: Please Enter the Recepient's Net ID");
			}
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
		cancelButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		cancelButton.setOnAction((ActionEvent e) -> {
			clearErrorMessage();
			myModel.stateChangeRequest("CancelCheckOutCI", null);
		});
		
		cancelButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			cancelButton.setEffect(new DropShadow());
		});
		cancelButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			cancelButton.setEffect(null);
		});
		HBox btnContainer = new HBox(10);
		btnContainer.setAlignment(Pos.CENTER);
                btnContainer.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    btnContainer.setStyle("-fx-background-color: GOLD");
		});
                btnContainer.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    btnContainer.setStyle("-fx-background-color: SLATEGREY");
		});
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(cancelButton);

		
		tableOfClothes.setPrefHeight(150);
        tableOfClothes.setMaxWidth(250);
				
		Text blankText2 = new Text("  ");
		blankText2.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		blankText2.setWrappingWidth(200);
		blankText2.setTextAlignment(TextAlignment.CENTER);
		blankText2.setFill(Color.WHITE);
		
		Text blankText3 = new Text("  ");
		blankText2.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		blankText2.setWrappingWidth(200);
		blankText2.setTextAlignment(TextAlignment.CENTER);
		blankText2.setFill(Color.WHITE);
		
		vbox.getChildren().add(grid);
		vbox.getChildren().add(blankText2);
		vbox.getChildren().add(tableOfClothes);
		vbox.getChildren().add(blankText3);
		vbox.getChildren().add(btnContainer);
		vbox.setPadding(new Insets(10,10,10,10));
        vbox.setAlignment(Pos.CENTER);
		clearOutlines();

		return vbox;
	}

	//--------------------------------------------------------------------------
        
    public void updateState(String key, Object value)
	{
		clearErrorMessage();

		if (key.equals("TransactionError") == true)
		{
			String val = (String)value;
			if (val.startsWith("ERR") == true)
			{
				displayErrorMessage(val);
			}
			else
			{
				clearValues();
				displayMessage(val);
			}
		}
	}
	//--------------------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}
	
	public void clearValues(){
		netId.clear();
		firstName.clear();
		lastName.clear();
	}

	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}
	/**
	 * Display info message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}
	private void clearOutlines(){
		netId.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		firstName.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		lastName.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
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

