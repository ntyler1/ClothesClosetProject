// specify the package
package userinterface;

// system imports
import utilities.GlobalVariables;
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

import java.util.Properties;

// project imports
import impresario.IModel;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/** The class containing the Search Color View  for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class SearchColorView extends View
{

	// GUI components
	protected TextField barcodePrefix;
	protected TextField description;
	protected TextField alphaCode;

	protected Button submitButton;
	protected Button cancelButton;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public SearchColorView(IModel at)
	{
		super(at, "SearchColorView");

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

	//-------------------------------------------------------------
	protected String getActionText()
	{
		return "** SEARCHING FOR COLOR **";
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

		Text actionText = new Text("     " + getActionText() + "       ");
		actionText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		actionText.setWrappingWidth(350);
		actionText.setTextAlignment(TextAlignment.CENTER);
		actionText.setFill(Color.DARKGREEN);
		container.getChildren().add(actionText);
                
                Text blankText2 = new Text("  ");
		blankText2.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		blankText2.setWrappingWidth(350);
		blankText2.setTextAlignment(TextAlignment.CENTER);
		blankText2.setFill(Color.WHITE);
		container.getChildren().add(blankText2);
                container.setAlignment(Pos.CENTER);
	
		return container;
	}

	// Create the main form content
	//-------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);
		
		Text prompt1 = new Text(" Enter Color Barcode Prefix: ");
                prompt1.setWrappingWidth(400);
                prompt1.setTextAlignment(TextAlignment.CENTER);
                prompt1.setFill(Color.BLACK);
		prompt1.setFont(Font.font("Copperplate", FontWeight.THIN, 18));
		vbox.getChildren().add(prompt1);
		
		GridPane grid0 = new GridPane();
		grid0.setAlignment(Pos.CENTER);
                grid0.setHgap(10);
                grid0.setVgap(10);
                grid0.setPadding(new Insets(0, 25, 10, 0));
		
		Text barcodePrefixLabel = new Text(" Barcode Prefix : ");
                barcodePrefixLabel.setFill(Color.GOLD);
		Font myFont = Font.font("copperplate", FontWeight.THIN, 16);
		barcodePrefixLabel.setFont(myFont);
		barcodePrefixLabel.setWrappingWidth(150);
		barcodePrefixLabel.setTextAlignment(TextAlignment.RIGHT);
		grid0.add(barcodePrefixLabel, 0, 1);

		barcodePrefix = new TextField();
                barcodePrefix.setStyle("-fx-focus-color: darkgreen;");
		barcodePrefix.setOnAction((ActionEvent e) -> {
                    clearErrorMessage();
                    Properties props = new Properties();
                    String bcPrfx = barcodePrefix.getText();
                    if (bcPrfx.length() > GlobalVariables.BCPRFX_MIN_LENGTH)
                    {
                        props.setProperty("BarcodePrefix", bcPrfx);
                        myModel.stateChangeRequest("SearchColor", props);
                    }
                });
		grid0.add(barcodePrefix, 1, 1);
		
		vbox.getChildren().add(grid0);
		
		Text prompt2 = new Text(" Enter Other Color Search Criteria: ");
                prompt2.setWrappingWidth(400);
                prompt2.setTextAlignment(TextAlignment.CENTER);
                prompt2.setFill(Color.BLACK);
		prompt2.setFont(Font.font("Copperplate", FontWeight.THIN, 18));
		vbox.getChildren().add(prompt2);

		GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(0, 25, 10, 0));

		Text descripLabel = new Text(" Description : ");
                descripLabel.setFill(Color.GOLD);
		descripLabel.setFont(myFont);
		descripLabel.setWrappingWidth(150);
		descripLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(descripLabel, 0, 1);

		description = new TextField();
                description.setStyle("-fx-focus-color: darkgreen;");
		description.setOnAction((ActionEvent e) -> {
                    clearErrorMessage();
                    Properties props = new Properties();
                    
                    String descrip = description.getText();
                    props.setProperty("Description", descrip);
                    String alfaC = alphaCode.getText();
                    props.setProperty("AlphaCode", alfaC);
                    myModel.stateChangeRequest("SearchColor", props);
                });
		grid.add(description, 1, 1);

		Text alphaCodeLabel = new Text(" Alpha Code : ");
                alphaCodeLabel.setFill(Color.GOLD);
		alphaCodeLabel.setFont(myFont);
		alphaCodeLabel.setWrappingWidth(150);
		alphaCodeLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(alphaCodeLabel, 0, 2);

		alphaCode = new TextField();
                alphaCode.setStyle("-fx-focus-color: darkgreen;");
		grid.add(alphaCode, 1, 2);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
                 doneCont.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    doneCont.setStyle("-fx-background-color: GOLD");
		});
                doneCont.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    doneCont.setStyle("-fx-background-color: SLATEGREY");
		});
                ImageView icon = new ImageView(new Image("/images/searchcolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		submitButton = new Button("Search",icon);
		submitButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		submitButton.setOnAction((ActionEvent e) -> {
                    clearErrorMessage();
                    Properties props = new Properties();
                    String bcPrfx = barcodePrefix.getText();
                    if (bcPrfx.length() > GlobalVariables.BCPRFX_MIN_LENGTH)
                    {
                        props.setProperty("BarcodePrefix", bcPrfx);
                        myModel.stateChangeRequest("SearchColor", props);
                    }
                    else
                    {
                        String descrip = description.getText();
                        props.setProperty("Description", descrip);
                        String alfaC = alphaCode.getText();
                        props.setProperty("AlphaCode", alfaC);
                        myModel.stateChangeRequest("SearchColor", props);
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
                    myModel.stateChangeRequest("CancelSearchColor", null);
                });
                cancelButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    cancelButton.setEffect(new DropShadow());
                });
                cancelButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    cancelButton.setEffect(null);
                });
		doneCont.getChildren().add(cancelButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);
                
                vbox.setAlignment(Pos.CENTER);

		return vbox;
	}


	// Create the status log field
	//-------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		clearErrorMessage();
                clearValues();
	}
        
        public void clearValues()
        {
            barcodePrefix.clear();
            description.clear();
            alphaCode.clear();
        }

	/**
	 * Update method
	 */
	//---------------------------------------------------------
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

//---------------------------------------------------------------
//	Revision History:
//


