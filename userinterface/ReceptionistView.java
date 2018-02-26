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

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public ReceptionistView(IModel teller)
	{
		super(teller, "ReceptionistView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());
		
		// how do you add white space?
		container.getChildren().add(new Label(" "));

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
		
		Text clientText = new Text(" Office of Career Services ");
		clientText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		clientText.setWrappingWidth(350);
		clientText.setTextAlignment(TextAlignment.CENTER);
		clientText.setFill(Color.DARKGREEN);
		container.getChildren().add(clientText);

		Text titleText = new Text(" Professional Clothes Closet Management System ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(350);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);

                //bport icon
                ImageView bportIcon = new ImageView(new Image("/images/shield.JPG",160,160,true,true));
                container.getChildren().add(bportIcon);
                
		Text inquiryText = new Text("       MAIN MENU       ");
		inquiryText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		inquiryText.setWrappingWidth(350);
		inquiryText.setTextAlignment(TextAlignment.CENTER);
		inquiryText.setFill(Color.BLACK);
		container.getChildren().add(inquiryText);
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
                ImageView icon = new ImageView(new Image("/images/checkout.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		checkoutClothingItemButton = new Button("Checkout Clothing Item", icon);
		checkoutClothingItemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		checkoutClothingItemButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("CheckoutClothingItem", null);    
            	     }
        	});
		checkoutCont.getChildren().add(checkoutClothingItemButton);
		
		container.getChildren().add(checkoutCont);
		
		HBox articleTypeCont = new HBox(10);
		articleTypeCont.setAlignment(Pos.CENTER_LEFT);
		Label atLabel = new Label("  Article Types: ");
		atLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		articleTypeCont.getChildren().add(atLabel);
                icon = new ImageView(new Image("/images/plus.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		addArticleTypeButton = new Button("Add",icon);
		addArticleTypeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addArticleTypeButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("AddArticleType", null);    
            	     }
        	});
		articleTypeCont.getChildren().add(addArticleTypeButton);
		
                icon = new ImageView(new Image("/images/edit.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		updateArticleTypeButton = new Button("Update", icon);
		updateArticleTypeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateArticleTypeButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("UpdateArticleType", null);    
            	     }
        	});
		articleTypeCont.getChildren().add(updateArticleTypeButton);
                icon = new ImageView(new Image("/images/trash.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		removeArticleTypeButton = new Button("Remove", icon);
		removeArticleTypeButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeArticleTypeButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("RemoveArticleType", null);    
            	     }
        	});
		articleTypeCont.getChildren().add(removeArticleTypeButton);

		container.getChildren().add(articleTypeCont);
		
		HBox colorCont = new HBox(10);
		colorCont.setAlignment(Pos.CENTER_LEFT);
		Label colorLabel = new Label("             Colors: ");
		colorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		colorCont.getChildren().add(colorLabel);
                icon = new ImageView(new Image("/images/plus.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		addColorButton = new Button("Add", icon);
		addColorButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addColorButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("AddColor", null);    
            	     }
        	});
		colorCont.getChildren().add(addColorButton);
		icon = new ImageView(new Image("/images/edit.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		updateColorButton = new Button("Update", icon);
		updateColorButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateColorButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("UpdateColor", null);    
            	     }
        	});
		colorCont.getChildren().add(updateColorButton);
                icon = new ImageView(new Image("/images/trash.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		removeColorButton = new Button("Remove",icon);
		removeColorButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeColorButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("RemoveColor", null);    
            	     }
        	});
		colorCont.getChildren().add(removeColorButton);
		
		container.getChildren().add(colorCont);
		
		HBox clothingItemCont = new HBox(10);
		clothingItemCont.setAlignment(Pos.CENTER_LEFT);
		Label ciLabel = new Label("Clothing Items: ");
		ciLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		clothingItemCont.getChildren().add(ciLabel);
                icon = new ImageView(new Image("/images/plus.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		addClothingItemButton = new Button("Add", icon);
		addClothingItemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		addClothingItemButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("AddClothingItem", null);    
            	     }
        	});
		clothingItemCont.getChildren().add(addClothingItemButton);
		icon = new ImageView(new Image("/images/edit.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		updateClothingItemButton = new Button("Update",icon);
		updateClothingItemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateClothingItemButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("UpdateClothingItem", null);    
            	     }
        	});
		clothingItemCont.getChildren().add(updateClothingItemButton);
                icon = new ImageView(new Image("/images/trash.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		removeClothingItemButton = new Button("Remove", icon);
		removeClothingItemButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeClothingItemButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("RemoveClothingItem", null);    
            	     }
        	});
		clothingItemCont.getChildren().add(removeClothingItemButton);
		
		container.getChildren().add(clothingItemCont);
		
		HBox requestCont = new HBox(10);
		requestCont.setAlignment(Pos.CENTER_LEFT);
		Label reqLabel = new Label("         Requests: ");
		reqLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		requestCont.getChildren().add(reqLabel);
                icon = new ImageView(new Image("/images/log.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		logRequestButton = new Button("Log", icon);
		logRequestButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		logRequestButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("LogRequest", null);    
            	     }
        	});
		requestCont.getChildren().add(logRequestButton);
		icon = new ImageView(new Image("/images/enter.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		fulfillRequestButton = new Button(" Fulfill ", icon);
		fulfillRequestButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		fulfillRequestButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("FulfillRequest", null);    
            	     }
        	});
		requestCont.getChildren().add(fulfillRequestButton);
                icon = new ImageView(new Image("/images/trash.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		removeRequestButton = new Button("Remove",icon);
		removeRequestButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		removeRequestButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("RemoveRequest", null);    
            	     }
        	});
		requestCont.getChildren().add(removeRequestButton);
		
		container.getChildren().add(requestCont);
		
		HBox listAvailCont = new HBox(10);
		listAvailCont.setAlignment(Pos.CENTER);
                icon = new ImageView(new Image("/images/list.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		listAvailableInventoryButton = new Button("List Available Inventory",icon);
		listAvailableInventoryButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		listAvailableInventoryButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("ListAvailableInventory", null);    
            	     }
        	});
		listAvailCont.getChildren().add(listAvailableInventoryButton);
		
		container.getChildren().add(listAvailCont);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
                icon = new ImageView(new Image("/images/exit.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		cancelButton = new Button("Exit System",icon);
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	myModel.stateChangeRequest("ExitSystem", null);    
            	     }
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


