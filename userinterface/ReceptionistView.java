// specify the package
package userinterface;

// system imports
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
import javafx.scene.control.TextField;
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

// project imports
import impresario.IModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Vector;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import model.ClothingRequest;
import model.ClothingRequestCollection;
import utilities.GlobalVariables;

/** The class containing the Transaction Choice View  for the ATM application */
//==============================================================
public class ReceptionistView extends View
{

	// other private data
	private final int labelWidth = 120;
	private final int labelHeight = 25;

	// GUI components

	private Button addArticleTypeButton;
	private Button updateArticleTypeButton;
	private Button removeArticleTypeButton;
	private Button addColorButton;
	private Button updateColorButton;
	private Button removeColorButton;
	private Button addClothingItemButton;
	private Button updateClothingItemButton;
	private Button removeClothingItemButton;
	private Button logRequestButton;
	private Button fulfillRequestButton;
	private Button removeRequestButton;

	private Button checkoutClothingItemButton;
	private MenuButton reportsButton;

	private Button cancelButton;

	private MessageView statusLog;
	private DropShadow shadow = new DropShadow();

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public ReceptionistView(IModel teller)
	{
		super(teller, "ReceptionistView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));
                container.setStyle("-fx-background-color: slategrey");

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// how do you add white space?
		//container.getChildren().add(new Label(" "));

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContents());

		container.getChildren().add(createStatusLog("             "));
               // container.setMinHeight(550);
                //container.setMinWidth(550);

		getChildren().add(container);

		populateFields();
                cancelButton.requestFocus();

		myModel.subscribe("TransactionError", this);
	}

	// Create the labels and fields
	//-------------------------------------------------------------
	private VBox createTitle()
	{
		VBox container = new VBox(10);

		Text clientText = new Text("OFFICE OF CAREER SERVICES");
		clientText.setFont(Font.font("Copperplate", FontWeight.EXTRA_BOLD, 36));
                clientText.setEffect(shadow);
		clientText.setTextAlignment(TextAlignment.CENTER);
		clientText.setFill(Color.WHITESMOKE);
		container.getChildren().add(clientText);

		Text titleText = new Text(" Professional Clothes Closet Management System ");
		titleText.setFont(Font.font("Copperplate", FontWeight.THIN, 28));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.GOLD);
		container.getChildren().add(titleText);

		//bport icon
		ImageView bportIcon = new ImageView(new Image("/images/BPT_LOGO_All-In-One_Color.png",225,225 ,true,true));
		bportIcon.setEffect(new DropShadow());
		container.getChildren().add(bportIcon);

		container.setAlignment(Pos.CENTER);
                
		return container;
	}


	// Create the navigation buttons
	//-------------------------------------------------------------
	private VBox createFormContents()
	{

		VBox container = new VBox(15);
		// create the buttons, listen for events, add them to the container
		HBox checkoutCont = new HBox(10);
		checkoutCont.setAlignment(Pos.CENTER);
		ImageView icon = new ImageView(new Image("/images/buyingcolor.png"));
		icon.setFitHeight(25);
		icon.setFitWidth(25);
		checkoutClothingItemButton = new Button("Check Out Item", icon);
		checkoutClothingItemButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		checkoutClothingItemButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("CheckoutClothingItem", null);
		});
		checkoutClothingItemButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			checkoutClothingItemButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Check Out a Clothing Item to a Student");
		});
		checkoutClothingItemButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			checkoutClothingItemButton.setEffect(null);
                        clearErrorMessage();
		});
		checkoutCont.getChildren().add(checkoutClothingItemButton);
                checkoutCont.setAlignment(Pos.CENTER);

   
                icon = new ImageView(new Image("/images/listcolor.png"));
                icon.setFitHeight(20);
                icon.setFitWidth(20);
                MenuItem availInventory = new MenuItem("Available Inventory", icon );
                icon = new ImageView(new Image("/images/datecolor.png"));
                icon.setFitHeight(25);
                icon.setFitWidth(25);
                MenuItem itemCheckedOutTillDate = new MenuItem("Checked Out Items", icon);
                icon = new ImageView(new Image("/images/medalcolor.png"));
                icon.setFitHeight(25);
                icon.setFitWidth(25);
                MenuItem topDonators = new MenuItem("Top Donor", icon);

                icon = new ImageView(new Image("/images/reportcolor.png"));
		icon.setFitHeight(25);
		icon.setFitWidth(25);
                MenuButton reportsButton = new MenuButton("    Reports    ", icon, availInventory, itemCheckedOutTillDate, topDonators);
		reportsButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
                reportsButton.setStyle("-fx-selection-bar:gold");
		availInventory.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("ListAvailableInventory", null);
		});
                itemCheckedOutTillDate.setOnAction((ActionEvent e) -> {
			Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Checked Out Items Report");
                        alert.setHeaderText("Enter a Date for the Checked Out Items Report!");
                        alert.getDialogPane().setMinHeight(175);
                        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/BPT_LOGO_All-In-One_Color.png"));

                        DatePicker datePicker = new DatePicker();

                        datePicker.setShowWeekNumbers(false);
                        String pattern = "MM-dd-yyyy";

                        datePicker.setConverter(new StringConverter<LocalDate>() {
                             DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                             @Override 
                             public String toString(LocalDate date) {
                                 if (date != null) {
                                     return dateFormatter.format(date);
                                 } else {
                                     return "";
                                 }
                             }

                             @Override 
                             public LocalDate fromString(String string) {
                                 if (string != null && !string.isEmpty()) {
                                     return LocalDate.parse(string, dateFormatter);
                                 } else {
                                     return null;
                                 }
                             }
                         });
                        GridPane grid = new GridPane();
                        Text label = new Text("           Date : ");
                        grid.add(label, 0, 0);
                        grid.add(datePicker, 1, 0);
                        alert.getDialogPane().setContent(grid);
                        
                         final Button btOk = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                            btOk.addEventFilter(ActionEvent.ACTION, event -> {
                                if(datePicker.getValue() != null){  
                                    GlobalVariables.UNTILL_DATE = datePicker.getValue().format(DateTimeFormatter.ofPattern(pattern));
                                    myModel.stateChangeRequest("UntillDateReport", null); 
                                }
                                else{
                                    Text error = new Text("Enter a date!");
                                    error.setFill(Color.FIREBRICK);
                                    grid.add(error, 1, 1);
                                    alert.getDialogPane().setContent(grid);
                                    event.consume();
                                }
                            });
                        alert.showAndWait();
		});
                topDonators.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("TopDonatorReport", null);
		});
		reportsButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			reportsButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Displays List of Reports to Choose From");
		});
		reportsButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			reportsButton.setEffect(null);
                        clearErrorMessage();
		});
                checkoutCont.getChildren().add(reportsButton);
		container.getChildren().add(checkoutCont);

		HBox articleTypeCont = new HBox(10);
		articleTypeCont.setAlignment(Pos.CENTER);
		Label atLabel = new Label("ARTICLE TYPES      : ");
                articleTypeCont.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    articleTypeCont.setStyle("-fx-background-color: DARKGREEN");
		});
                articleTypeCont.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    articleTypeCont.setStyle("-fx-background-color: SLATEGREY");
		});
                atLabel.setAlignment(Pos.CENTER_LEFT);
		atLabel.setFont(Font.font("Copperplate", FontWeight.BOLD, 18));
		atLabel.setTextFill(Color.GOLD);
		articleTypeCont.getChildren().add(atLabel);
		icon = new ImageView(new Image("/images/pluscolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		addArticleTypeButton = new Button("Add",icon);
		addArticleTypeButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		addArticleTypeButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("AddArticleType", null);
		});
		addArticleTypeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			addArticleTypeButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Add new Article Types to the records");
                        
		});
		addArticleTypeButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			addArticleTypeButton.setEffect(null);
                        clearErrorMessage();
		});
		articleTypeCont.getChildren().add(addArticleTypeButton);

		icon = new ImageView(new Image("/images/editcolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		updateArticleTypeButton = new Button("Update", icon);
		updateArticleTypeButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		updateArticleTypeButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("UpdateArticleType", null);
		});
		updateArticleTypeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			updateArticleTypeButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Update Article Types in the records");
		});
		updateArticleTypeButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			updateArticleTypeButton.setEffect(null);
                        clearErrorMessage();
		});
		articleTypeCont.getChildren().add(updateArticleTypeButton);
		icon = new ImageView(new Image("/images/trashcolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		removeArticleTypeButton = new Button("Remove", icon);
		removeArticleTypeButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		removeArticleTypeButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("RemoveArticleType", null);
		});
		removeArticleTypeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			removeArticleTypeButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Remove Article Types from the records");
		});
		removeArticleTypeButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			removeArticleTypeButton.setEffect(null);
                        clearErrorMessage();
		});
		articleTypeCont.getChildren().add(removeArticleTypeButton);
                articleTypeCont.setAlignment(Pos.CENTER);

		container.getChildren().add(articleTypeCont);

		HBox colorCont = new HBox(10);
		colorCont.setAlignment(Pos.CENTER_LEFT);
		Label colorLabel = new Label("COLORS                 : ");
                colorCont.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    colorCont.setStyle("-fx-background-color: DARKGREEN");
		});
                colorCont.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    colorCont.setStyle("-fx-background-color: SLATEGREY");
		});
		colorLabel.setFont(Font.font("Copperplate", FontWeight.BOLD, 18));
                colorLabel.setTextFill(Color.GOLD);
		colorCont.getChildren().add(colorLabel);
		icon = new ImageView(new Image("/images/pluscolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		addColorButton = new Button("Add", icon);
		addColorButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		addColorButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("AddColor", null);
		});
		addColorButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			addColorButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Add new Colors to the records");
		});
		addColorButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			addColorButton.setEffect(null);
                        clearErrorMessage();
		});
		colorCont.getChildren().add(addColorButton);
		icon = new ImageView(new Image("/images/editcolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		updateColorButton = new Button("Update", icon);
		updateColorButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		updateColorButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("UpdateColor", null);
		});
		updateColorButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			updateColorButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Update Colors in the records");
		});
		updateColorButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			updateColorButton.setEffect(null);
                        clearErrorMessage();
		});
		colorCont.getChildren().add(updateColorButton);
		icon = new ImageView(new Image("/images/trashcolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		removeColorButton = new Button("Remove",icon);
		removeColorButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		removeColorButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("RemoveColor", null);
		});
		removeColorButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			removeColorButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Remove Colors from the records");
		});
		removeColorButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			removeColorButton.setEffect(null);
                        clearErrorMessage();
		});
		colorCont.getChildren().add(removeColorButton);
                colorCont.setAlignment(Pos.CENTER);

		container.getChildren().add(colorCont);

		HBox clothingItemCont = new HBox(10);
		clothingItemCont.setAlignment(Pos.CENTER_LEFT);
		Label ciLabel = new Label("CLOTHING ITEMS : ");
                clothingItemCont.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    clothingItemCont.setStyle("-fx-background-color: DARKGREEN");
		});
                clothingItemCont.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    clothingItemCont.setStyle("-fx-background-color: SLATEGREY");
		});
		ciLabel.setFont(Font.font("Copperplate", FontWeight.BOLD, 18));
                ciLabel.setTextFill(Color.GOLD);
		clothingItemCont.getChildren().add(ciLabel);
		icon = new ImageView(new Image("/images/pluscolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		addClothingItemButton = new Button("Add", icon);
		addClothingItemButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		addClothingItemButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("AddClothingItem", null);
		});
		addClothingItemButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			addClothingItemButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Add new Clothing Items to the records");
		});
		addClothingItemButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			addClothingItemButton.setEffect(null);
                        clearErrorMessage();
		});
		clothingItemCont.getChildren().add(addClothingItemButton);
		icon = new ImageView(new Image("/images/editcolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		updateClothingItemButton = new Button("Update",icon);
		updateClothingItemButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		updateClothingItemButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("UpdateClothingItem", null);
		});
		updateClothingItemButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			updateClothingItemButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Update Clothing Items in the records");
		});
		updateClothingItemButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			updateClothingItemButton.setEffect(null);
                        clearErrorMessage();
		});
		clothingItemCont.getChildren().add(updateClothingItemButton);
		icon = new ImageView(new Image("/images/trashcolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		removeClothingItemButton = new Button("Remove", icon);
		removeClothingItemButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		removeClothingItemButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("RemoveClothingItem", null);
		});
		removeClothingItemButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			removeClothingItemButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Remove Clothing Items from the records");
		});
		removeClothingItemButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			removeClothingItemButton.setEffect(null);
                        clearErrorMessage();
		});
		clothingItemCont.getChildren().add(removeClothingItemButton);
                clothingItemCont.setAlignment(Pos.CENTER);
		container.getChildren().add(clothingItemCont);

		HBox requestCont = new HBox(10);
		requestCont.setAlignment(Pos.CENTER_LEFT);
		Label reqLabel = new Label("REQUESTS             : ");
                requestCont.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    requestCont.setStyle("-fx-background-color: DARKGREEN");
		});
                requestCont.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    requestCont.setStyle("-fx-background-color: SLATEGREY");
		});
		reqLabel.setFont(Font.font("Copperplate", FontWeight.BOLD, 18));
                reqLabel.setTextFill(Color.GOLD);
		requestCont.getChildren().add(reqLabel);
		icon = new ImageView(new Image("/images/logcolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		logRequestButton = new Button("Log", icon);
		logRequestButton.setMinWidth(65);
		logRequestButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		logRequestButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("AddRequest", null);
		});
		logRequestButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			logRequestButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Log new Requests to the records");
		});
		logRequestButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			logRequestButton.setEffect(null);
                        clearErrorMessage();
		});
		requestCont.getChildren().add(logRequestButton);
		icon = new ImageView(new Image("/images/entercolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		fulfillRequestButton = new Button(" Fulfill ", icon);
		fulfillRequestButton.setMinWidth(85);
		fulfillRequestButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		fulfillRequestButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("FulfillRequest", null);
		});
		fulfillRequestButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			fulfillRequestButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Fulfill Requests in the records");
		});
		fulfillRequestButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			fulfillRequestButton.setEffect(null);
                        clearErrorMessage();
		});
		requestCont.getChildren().add(fulfillRequestButton);
		icon = new ImageView(new Image("/images/trashcolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		removeRequestButton = new Button("Remove",icon);
		removeRequestButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		removeRequestButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("RemoveRequest", null);
		});
		removeRequestButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			removeRequestButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Remove Requests from the records");
		});
		removeRequestButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			removeRequestButton.setEffect(null);
                        clearErrorMessage();
		});
		requestCont.getChildren().add(removeRequestButton);
                requestCont.setAlignment(Pos.CENTER);

		container.getChildren().add(requestCont);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		icon = new ImageView(new Image("/images/exitcolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		cancelButton = new Button("Exit System",icon);
		cancelButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		cancelButton.setOnAction((ActionEvent e) -> {
			myModel.stateChangeRequest("ExitSystem", null);
		});
		cancelButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			cancelButton.setEffect(shadow);
                        statusLog.displayInfoMessage("Close Application");
		});
		cancelButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			cancelButton.setEffect(null);
                        clearErrorMessage();
		});
		doneCont.getChildren().add(cancelButton);
                doneCont.setAlignment(Pos.CENTER);

		container.getChildren().add(doneCont);

		return container;
	}

	// Create the status log field
	//-------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage)
	{

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
            try{
                ClothingRequestCollection requestCollection = 
                        (ClothingRequestCollection)myModel.getState("MatchingRequests");

                Vector entryList = (Vector)requestCollection.getState("ClothingRequest");
                if(entryList.size() > 0)
                    fulfillRequestButton.setText("Fulfill ("+entryList.size()+")");
                    
            }	
		catch (Exception e) {//SQLException e)
			System.out.println(e);
		}
	}
        


	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		if (key.equals("TransactionError") == true)
		{
			// display the passed text
			displayErrorMessage((String)value);
		}
	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
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


