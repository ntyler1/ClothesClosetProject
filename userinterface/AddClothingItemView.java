package userinterface;

import java.util.Properties;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class AddClothingItemView extends View
{

	protected ComboBox gender;
	protected ComboBox color1;
	protected ComboBox color2;
	protected TextField brand;
	protected TextField firstName;
	protected TextField lastName;
	protected TextField phoneNumber;
	protected TextField email;
	protected TextField notes;

	protected Button submit;
	protected Button done;

	protected MessageView statusLog;


	
	
	public AddArticleTypeView(IModel ac)
	{
		super(ac, "AddClothingItemView");

		// create a container for showing the contents
		VBox container = new VBox(10);
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

	
	
	
	protected String getActionText()
	{
		return "Adding the clothing item...";
	}

	
	
	
	private Node createTitle()
	{
		VBox container = new VBox(10);
		container.setPadding(new Insets(1, 1, 1, 30));

		Text clientText = new Text(" Office of Career Services ");
		clientText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		clientText.setWrappingWidth(350);
		clientText.setTextAlignment(TextAlignment.CENTER);
		clientText.setFill(Color.DARKGREEN);
		container.getChildren().add(clientText);

		Text collegeText = new Text(" THE COLLEGE AT BROCKPORT ");
		collegeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		collegeText.setWrappingWidth(350);
		collegeText.setTextAlignment(TextAlignment.CENTER);
		collegeText.setFill(Color.DARKGREEN);
		container.getChildren().add(collegeText);

		Text titleText = new Text(" Professional Clothes Closet Management System ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(350);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
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
		actionText.setFill(Color.BLACK);
		container.getChildren().add(actionText);

		return container;
	}

	
	
	
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);
		
		Text prompt = new Text("CLOTHING ITEM INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
		prompt.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		vbox.getChildren().add(prompt);
		

		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
       	grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 25, 10, 0));

		Text genderLabel = new Text(" Gender : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		genderLabel.setFont(myFont);
		genderLabel.setWrappingWidth(150);
		gender.setTextAlignment(TextAlignment.RIGHT);
		grid.add(genderLabel, 0, 1);

		gender = (ComboBox)createGenderComboBox();
		grid.add(gender, 1, 1);
		
		
		
		Text color1Label = new Text(" Color 1 : ");
		color1Label.setFont(myFont);
		color1Label.setWrappingWidth(150);
		color1.setTextAlignment(TextAlignment.RIGHT);
		grid.add(color1Label, 0, 2);

		gender = new TextField();
		grid.add(gender, 1, 2);

		
		
		Text color2Label = new Text(" Color 2 : ");
		color2Label.setFont(myFont);
		color2Label.setWrappingWidth(150);
		color2Label.setTextAlignment(TextAlignment.RIGHT);
		grid.add(color2Label, 0, 3);

		color2 = new TextField();
		grid.add(color2, 1, 2);
		
		
		
		Text brandLabel = new Text(" Brand : ");
		brandLabel.setFont(myFont);
		brandLabel.setWrappingWidth(150);
		brandLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(brandLabel, 0, 4);

		brand = new TextField();
		grid.add(brand, 1, 4);

		
		
		Text color2Label = new Text(" Color 2 : ");
		color2Label.setFont(myFont);
		color2Label.setWrappingWidth(150);
		color2Label.setTextAlignment(TextAlignment.RIGHT);
		grid.add(color2Label, 0, 3);

		color2 = new TextField();
		grid.add(color2, 1, 2);
		
		
		
		
		
		Text alphaCodeLabel = new Text(" Alpha Code : ");
		alphaCodeLabel.setFont(myFont);
		alphaCodeLabel.setWrappingWidth(150);
		alphaCodeLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(alphaCodeLabel, 0, 3);

		alphaCode = new TextField();
		grid.add(alphaCode, 1, 3);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button("Submit");
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
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
								myModel.stateChangeRequest("ArticleTypeData", props); 
							}
							else
							{
								displayErrorMessage("ERROR: Please enter a valid alpha code!");
							}
						}
						else
						{
							displayErrorMessage("ERROR: Please enter a valid description!");
						}
						
					}
					else
					{
						displayErrorMessage("ERROR: Please enter a barcode prefix!");
						
					}
       		    	  
            	  }
        	});
		doneCont.getChildren().add(submitButton);
		
		cancelButton = new Button("Return");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	myModel.stateChangeRequest("CancelAddAT", null);   
            	  }
        	});
		doneCont.getChildren().add(cancelButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}



public Node createGenderComboBox()
{
	final ComboBox<String> genderCombo = new ComboBox<String>();
	genderCombo.getItems().addAll("Male" , "Female");
	return genderCombo;
}

}
