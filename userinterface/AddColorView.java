// specify the package
package userinterface;

// system imports
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Properties;

// project imports
import impresario.IModel;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/** The class containing the Add Color View  for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class AddColorView extends View
{

	// GUI components
	protected TextField barcodePrefix;
	protected TextField description;
	protected TextField alphaCode;
        protected Text actionText;
        protected Text prompt;

	protected Button submitButton;
	protected Button cancelButton;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public AddColorView(IModel at)
	{
		super(at, "AddColorView");

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
		return "** ADDING NEW COLOR **";
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

		actionText = new Text("     " + getActionText() + "       ");
		actionText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		actionText.setWrappingWidth(350);
		actionText.setTextAlignment(TextAlignment.CENTER);
		actionText.setFill(Color.LIGHTGREEN);
		container.getChildren().add(actionText);
                        
                Text blankText2 = new Text("  ");
		blankText2.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		blankText2.setWrappingWidth(350);
		blankText2.setTextAlignment(TextAlignment.CENTER);
		blankText2.setFill(Color.WHITE);
		container.getChildren().add(blankText2);
	
		return container;
	}

	// Create the main form content
	//-------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);
		
		prompt = new Text("Enter New Color Information:");
                prompt.setWrappingWidth(400);
                prompt.setTextAlignment(TextAlignment.CENTER);
                prompt.setFill(Color.BLACK);
                prompt.setFont(Font.font("Copperplate", FontWeight.THIN, 18));
                vbox.getChildren().add(prompt);
		

		GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(0, 25, 10, 0));

		Text barcodePrefixLabel = new Text(" Barcode Prefix : ");
                barcodePrefixLabel.setFill(Color.GOLD);
		Font myFont = Font.font("Comic Sans", FontWeight.THIN, 16);
		barcodePrefixLabel.setFont(myFont);
		barcodePrefixLabel.setWrappingWidth(150);
		barcodePrefixLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(barcodePrefixLabel, 0, 1);

		barcodePrefix = new TextField();
		grid.add(barcodePrefix, 1, 1);

		Text descripLabel = new Text(" Description : ");
                descripLabel.setFill(Color.GOLD);
		descripLabel.setFont(myFont);
		descripLabel.setWrappingWidth(150);
		descripLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(descripLabel, 0, 2);

		description = new TextField();
		grid.add(description, 1, 2);

		Text alphaCodeLabel = new Text(" Alpha Code : ");
                alphaCodeLabel.setFill(Color.GOLD);
		alphaCodeLabel.setFont(myFont);
		alphaCodeLabel.setWrappingWidth(150);
		alphaCodeLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(alphaCodeLabel, 0, 3);

		alphaCode = new TextField();
		grid.add(alphaCode, 1, 3);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
                ImageView icon = new ImageView(new Image("/images/pluscolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		submitButton = new Button("Add", icon);
                submitButton.setStyle("-fx-background-color: lightgreen; ");
		submitButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		submitButton.setOnAction((ActionEvent e) -> {
                    clearErrorMessage();
                    clearOutlines();
                    Properties props = new Properties();
                    String bcPrfx = barcodePrefix.getText();
                    if (bcPrfx.length() > 0)
                    {
                        props.setProperty("BarcodePrefix", bcPrfx);
                        String descrip = description.getText();
                        if (descrip.length() > 0)
                        {
                            props.setProperty("Description", descrip);
                            String alfaC = alphaCode.getText();
                            if (alfaC.length() > 0)
                            {
                                props.setProperty("AlphaCode", alfaC);
                                myModel.stateChangeRequest("ColorData", props);
                            }
                            else
                            {
                                alphaCode.setStyle("-fx-border-color: firebrick;");
                                displayErrorMessage("ERROR: Please enter a valid alpha code!");
                            }
                        }
                        else
                        {
                            description.setStyle("-fx-border-color: firebrick;");
                            displayErrorMessage("ERROR: Please enter a valid description!");
                        }
                        
                    }
                    else
                    {
                        barcodePrefix.setStyle("-fx-border-color: firebrick;");
                        displayErrorMessage("ERROR: Please enter a barcode prefix!");      
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
                cancelButton.setStyle("-fx-background-color: palevioletred; ");
		cancelButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		cancelButton.setOnAction((ActionEvent e) -> {
                    clearErrorMessage();
                    myModel.stateChangeRequest("CancelAddC", null);
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
	//-------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}
        
        private void clearOutlines(){
            barcodePrefix.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
            description.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
            alphaCode.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
        }

	//-------------------------------------------------------------
	public void populateFields()
	{
		
	}
        
        public void clearValues(){
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


