package userinterface;

// system imports
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Properties;

// project imports
import impresario.IModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Vector;

import javafx.beans.property.SetProperty;
import javafx.collections.FXCollections;
import model.Gender;
import model.ArticleType;
import model.ColorX;
import model.ArticleTypeCollection;
import model.ColorCollection;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class EnterRecepientInfoView extends View {

	protected TextField netId;
	protected TextField firstName;
	protected TextField lastName;
	protected Text actionText;
	protected Text prompt;

	protected Button submitButton;
	protected Button cancelButton;

	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	// -------------------------------------------------------------

	public EnterRecepientInfoView(IModel ri) {
		super(ri, "EnterRecepientInfoView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setStyle("-fx-background-color: slategrey");

		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		populateFields();

		myModel.subscribe("TransactionError", this);
	}

	protected String getActionText() {
		return "** CHECKOUT CLOTHING ITEM **";
	}

	// -------------------------------------------------------------
	public void populateFields() {

	}

	// create the title container
	// ------------------------------------------------------------
	private Node createTitle() {
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
		actionText.setFill(Color.LIGHTGREEN);
		container.getChildren().add(actionText);
		container.setAlignment(Pos.CENTER);

		return container;
	}

	// Create the main form content
	// -------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);

		Text blankText = new Text("  ");
		blankText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		blankText.setWrappingWidth(350);
		blankText.setTextAlignment(TextAlignment.CENTER);
		blankText.setFill(Color.WHITE);
		vbox.getChildren().add(blankText);

		prompt = new Text("Enter Recepient Information:");
		prompt.setWrappingWidth(400);
		prompt.setTextAlignment(TextAlignment.CENTER);
		prompt.setFill(Color.BLACK);
		prompt.setFont(Font.font("Copperplate", FontWeight.THIN, 20));
		vbox.getChildren().add(prompt);
		vbox.setAlignment(Pos.CENTER);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 25, 10, 0));

		Text netIdLabel = new Text(" Net ID : ");
		Font myFont = Font.font("Copperplate", FontWeight.THIN, 16);

		netIdLabel.setFill(Color.GOLD);
		netIdLabel.setFont(myFont);
		netIdLabel.setWrappingWidth(145);
		netIdLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(netIdLabel, 0, 1);

		netId = new TextField();
		netId.setMinWidth(180);
		grid.add(netId, 1, 1);

		Text firstNameLabel = new Text(" First Name : ");
		firstNameLabel.setFill(Color.GOLD);
		firstNameLabel.setFont(myFont);
		firstNameLabel.setWrappingWidth(145);
		firstNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(firstNameLabel, 0, 2);

		firstName = new TextField();
		firstName.setMinWidth(180);
		grid.add(firstName, 1, 2);

		Text lastNameLabel = new Text(" Last Name : ");
		lastNameLabel.setFill(Color.GOLD);
		lastNameLabel.setFont(myFont);
		lastNameLabel.setWrappingWidth(145);
		lastNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(lastNameLabel, 0, 3);

		lastName = new TextField();
		lastName.setMinWidth(180);
		grid.add(lastName, 1, 3);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
                doneCont.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    doneCont.setStyle("-fx-background-color: GOLD");
		});
                doneCont.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    doneCont.setStyle("-fx-background-color: SLATEGREY");
		});
		ImageView icon = new ImageView(new Image("/images/pluscolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		submitButton = new Button("Add", icon);
		submitButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		submitButton.setOnAction((ActionEvent e) -> {
			clearOutlines();
			clearErrorMessage();
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
						props.setProperty("RecieverNetid", recepientNetID);
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
							}
							else
								myModel.stateChangeRequest("CancelCheckOutCI", null);
						}
						else
							myModel.stateChangeRequest("RecepientData", props);
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
		doneCont.getChildren().add(submitButton);
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
		doneCont.getChildren().add(cancelButton);

		vbox.getChildren().add(grid);
		Text blankText2 = new Text("  ");
		blankText2.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		blankText2.setWrappingWidth(350);
		blankText2.setTextAlignment(TextAlignment.CENTER);
		blankText2.setFill(Color.WHITE);
		vbox.getChildren().add(blankText2);
		vbox.getChildren().add(doneCont);
		clearOutlines();
		return vbox;
	}

	// Create the status log field
	//---------------------------------------------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//------------------------------------------------------------------------------------------
	public void clearValues(){
		netId.clear();
		firstName.clear();
		lastName.clear();
	}

	private void clearOutlines(){
		netId.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		firstName.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		lastName.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
	}

	//----------------------------------------------------------------------------------------------------------------------
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

	/**

	 * Display error message
	 */
	//----------------------------------------------------------
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

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}

}
