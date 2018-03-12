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
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;

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
	private Button listAvailableInventoryButton;

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
                container.setStyle("-fx-background-color: slategrey");
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());
		
		// how do you add white space?
		//container.getChildren().add(new Label(" "));

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContents());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		populateFields();

		myModel.subscribe("TransactionError", this);
	}

	// Create the labels and fields
	//-------------------------------------------------------------
	private VBox createTitle()
	{
		VBox container = new VBox(10);

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

                //bport icon
                ImageView bportIcon = new ImageView(new Image("/images/bporteagle.png",250,175,false,true));
                bportIcon.setEffect(new Glow(.5));
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
                icon.setFitHeight(20);
                icon.setFitWidth(20);
		checkoutClothingItemButton = new Button("Checkout Clothing Item", icon);
                checkoutClothingItemButton.setStyle("-fx-background-color: gold; ");
		checkoutClothingItemButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		checkoutClothingItemButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("CheckoutClothingItem", null);
                });
                checkoutClothingItemButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    checkoutClothingItemButton.setEffect(shadow);
                });
                checkoutClothingItemButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    checkoutClothingItemButton.setEffect(null);
                });
		checkoutCont.getChildren().add(checkoutClothingItemButton);
		
		container.getChildren().add(checkoutCont);
		
		HBox articleTypeCont = new HBox(10);
		articleTypeCont.setAlignment(Pos.CENTER_LEFT);
		Label atLabel = new Label("  Article Types: ");
		atLabel.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
                atLabel.setTextFill(Color.GOLD);
		articleTypeCont.getChildren().add(atLabel);
                icon = new ImageView(new Image("/images/pluscolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		addArticleTypeButton = new Button("Add",icon);
                addArticleTypeButton.setStyle("-fx-background-color: lightgreen; ");
		addArticleTypeButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		addArticleTypeButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("AddArticleType", null);
                });
                addArticleTypeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    addArticleTypeButton.setEffect(shadow);
                });
                addArticleTypeButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    addArticleTypeButton.setEffect(null);
                });
		articleTypeCont.getChildren().add(addArticleTypeButton);
		
                icon = new ImageView(new Image("/images/editcolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		updateArticleTypeButton = new Button("Update", icon);
                updateArticleTypeButton.setStyle("-fx-background-color: lightblue; ");
		updateArticleTypeButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		updateArticleTypeButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("UpdateArticleType", null);
                });
                updateArticleTypeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    updateArticleTypeButton.setEffect(shadow);
                });
                updateArticleTypeButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    updateArticleTypeButton.setEffect(null);
                });
		articleTypeCont.getChildren().add(updateArticleTypeButton);
                icon = new ImageView(new Image("/images/trashcolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		removeArticleTypeButton = new Button("Remove", icon);
                removeArticleTypeButton.setStyle("-fx-background-color: PALEVIOLETRED; ");
		removeArticleTypeButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		removeArticleTypeButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("RemoveArticleType", null);
                });
                removeArticleTypeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    removeArticleTypeButton.setEffect(shadow);
                });
                removeArticleTypeButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    removeArticleTypeButton.setEffect(null);
                });
		articleTypeCont.getChildren().add(removeArticleTypeButton);

		container.getChildren().add(articleTypeCont);
		
		HBox colorCont = new HBox(10);
		colorCont.setAlignment(Pos.CENTER_LEFT);
		Label colorLabel = new Label("            Colors: ");
		colorLabel.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
                colorLabel.setTextFill(Color.GOLD);
		colorCont.getChildren().add(colorLabel);
                icon = new ImageView(new Image("/images/pluscolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		addColorButton = new Button("Add", icon);
                addColorButton.setStyle("-fx-background-color: lightgreen; ");
		addColorButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		addColorButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("AddColor", null);
                });
                addColorButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    addColorButton.setEffect(shadow);
                });
                addColorButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    addColorButton.setEffect(null);
                });
		colorCont.getChildren().add(addColorButton);
		icon = new ImageView(new Image("/images/editcolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		updateColorButton = new Button("Update", icon);
                updateColorButton.setStyle("-fx-background-color: lightblue; ");
		updateColorButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		updateColorButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("UpdateColor", null);
                });
                updateColorButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    updateColorButton.setEffect(shadow);
                });
                updateColorButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    updateColorButton.setEffect(null);
                });
		colorCont.getChildren().add(updateColorButton);
                icon = new ImageView(new Image("/images/trashcolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		removeColorButton = new Button("Remove",icon);
                removeColorButton.setStyle("-fx-background-color: PALEVIOLETRED; ");
		removeColorButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		removeColorButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("RemoveColor", null);
                });
                removeColorButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    removeColorButton.setEffect(shadow);
                });
                removeColorButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    removeColorButton.setEffect(null);
                });
		colorCont.getChildren().add(removeColorButton);
		
		container.getChildren().add(colorCont);
		
		HBox clothingItemCont = new HBox(10);
		clothingItemCont.setAlignment(Pos.CENTER_LEFT);
		Label ciLabel = new Label("Clothing Items:");
		ciLabel.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
                ciLabel.setTextFill(Color.GOLD);
		clothingItemCont.getChildren().add(ciLabel);
                icon = new ImageView(new Image("/images/pluscolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		addClothingItemButton = new Button("Add", icon);
                addClothingItemButton.setStyle("-fx-background-color: lightgreen; ");
		addClothingItemButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		addClothingItemButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("addClothingItem", null);
                });
                addClothingItemButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    addClothingItemButton.setEffect(shadow);
                });
                addClothingItemButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    addClothingItemButton.setEffect(null);
                });
		clothingItemCont.getChildren().add(addClothingItemButton);
		icon = new ImageView(new Image("/images/editcolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		updateClothingItemButton = new Button("Update",icon);
                updateClothingItemButton.setStyle("-fx-background-color: lightblue; ");
		updateClothingItemButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		updateClothingItemButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("UpdateClothingItem", null);
                });
                updateClothingItemButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    updateClothingItemButton.setEffect(shadow);
                });
                updateClothingItemButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    updateClothingItemButton.setEffect(null);
                });
		clothingItemCont.getChildren().add(updateClothingItemButton);
                icon = new ImageView(new Image("/images/trashcolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		removeClothingItemButton = new Button("Remove", icon);
		removeClothingItemButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
                removeClothingItemButton.setStyle("-fx-background-color: PALEVIOLETRED; ");
		removeClothingItemButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("RemoveClothingItem", null);
                });
                removeClothingItemButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    removeClothingItemButton.setEffect(shadow);
                });
                removeClothingItemButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    removeClothingItemButton.setEffect(null);
                });
		clothingItemCont.getChildren().add(removeClothingItemButton);
		
		container.getChildren().add(clothingItemCont);
		
		HBox requestCont = new HBox(10);
		requestCont.setAlignment(Pos.CENTER_LEFT);
		Label reqLabel = new Label("        Requests: ");
		reqLabel.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
                reqLabel.setTextFill(Color.GOLD);
		requestCont.getChildren().add(reqLabel);
                icon = new ImageView(new Image("/images/logcolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		logRequestButton = new Button("Log", icon);
                logRequestButton.setStyle("-fx-background-color: lightgreen; ");
                logRequestButton.setMinWidth(65);
		logRequestButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		logRequestButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("LogRequest", null);
                });
                logRequestButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    logRequestButton.setEffect(shadow);
                });
                logRequestButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    logRequestButton.setEffect(null);
                });
		requestCont.getChildren().add(logRequestButton);
		icon = new ImageView(new Image("/images/entercolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		fulfillRequestButton = new Button(" Fulfill ", icon);
                fulfillRequestButton.setStyle("-fx-background-color: lightblue; ");
                fulfillRequestButton.setMinWidth(85);
		fulfillRequestButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		fulfillRequestButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("FulfillRequest", null);
                });
                fulfillRequestButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    fulfillRequestButton.setEffect(shadow);
                });
                fulfillRequestButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    fulfillRequestButton.setEffect(null);
                });
		requestCont.getChildren().add(fulfillRequestButton);
                icon = new ImageView(new Image("/images/trashcolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		removeRequestButton = new Button("Remove",icon);
                removeRequestButton.setStyle("-fx-background-color: PALEVIOLETRED; ");
		removeRequestButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		removeRequestButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("RemoveRequest", null);
                });
                removeRequestButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    removeRequestButton.setEffect(shadow);
                });
                removeRequestButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    removeRequestButton.setEffect(null);
                });
		requestCont.getChildren().add(removeRequestButton);
		
		container.getChildren().add(requestCont);
		
		HBox listAvailCont = new HBox(10);
		listAvailCont.setAlignment(Pos.CENTER);
                icon = new ImageView(new Image("/images/listcolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		listAvailableInventoryButton = new Button("List Available Inventory",icon);
                listAvailableInventoryButton.setStyle("-fx-background-color: thistle; ");
		listAvailableInventoryButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		listAvailableInventoryButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("ListAvailableInventory", null);
                });
                listAvailableInventoryButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    listAvailableInventoryButton.setEffect(shadow);
                });
                listAvailableInventoryButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    listAvailableInventoryButton.setEffect(null);
                });
		listAvailCont.getChildren().add(listAvailableInventoryButton);
		
		container.getChildren().add(listAvailCont);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
                icon = new ImageView(new Image("/images/exitcolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		cancelButton = new Button("Exit System",icon);
                cancelButton.setStyle("-fx-background-color: lightgrey; ");
		cancelButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		cancelButton.setOnAction((ActionEvent e) -> {
                    myModel.stateChangeRequest("ExitSystem", null);
                });
                cancelButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    cancelButton.setEffect(shadow);
                });
                cancelButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    cancelButton.setEffect(null);
                });
		doneCont.getChildren().add(cancelButton);

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


