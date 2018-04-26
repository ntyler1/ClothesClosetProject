 // specify the package
package userinterface;

// system imports
import utilities.GlobalVariables;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Properties;

// project imports
import impresario.IModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
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

/** The class containing the Add request View  for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class AddRequestView extends View
{

	// GUI components
	
	
	protected TextField brand;
	protected TextField recipientFName;
	protected TextField recipientLName;
	protected TextField recipientPhone;
	protected TextField recipientNet;
	protected Text actionText;
	protected Text prompt;

	protected Button submitButton;
	protected Button cancelButton;
	protected ComboBox<Gender> gender;
	protected TextField size;
	protected ComboBox<ArticleType> articleType;
	protected ComboBox<ColorX> color1;
	protected ComboBox<ColorX> color2;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public AddRequestView(IModel ar)
	{
		super(ar, "AddRequestView");

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
		return "** ADDING NEW REQUEST **";
	}

	public void populateFields(){

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
		Font myFont = Font.font("Comic Sans", FontWeight.THIN, 16);
		
		Text blankText = new Text("  ");
		blankText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		blankText.setWrappingWidth(350);
		blankText.setTextAlignment(TextAlignment.CENTER);
		blankText.setFill(Color.WHITE);
		vbox.getChildren().add(blankText);

		prompt = new Text("Enter Requested Item Information:");
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

		Text genderLabel = new Text(" Gender : ");

		genderLabel.setFill(Color.GOLD);
		genderLabel.setFont(myFont);
		genderLabel.setWrappingWidth(145);
		genderLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(genderLabel, 0, 1);

		gender = new ComboBox<Gender>();
		gender.setItems(FXCollections.observableArrayList(
				new Gender("Male"), new Gender("Female"), new Gender("Unisex")));
		gender.setPromptText("Please Select Gender");
		gender.setConverter(new StringConverter<Gender>() {
			@Override
			public String toString(Gender object) {
				return object.getName();
			}
			@Override
			public Gender fromString(String string) {
				return null;
			}
		});
		gender.setOnAction((event) -> {
			if(gender.getSelectionModel().getSelectedItem() == null)
				return;
		});

		gender.setMaxWidth(180);
		grid.add(gender, 1, 1);

		Text atLabel = new Text(" Article Type : ");

		atLabel.setFill(Color.GOLD);
		atLabel.setFont(myFont);
		atLabel.setWrappingWidth(145);
		atLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(atLabel, 0, 2);

		articleType = new ComboBox<ArticleType>();
		fillArticleTypeComboBox();
		articleType.setConverter(new StringConverter<ArticleType>() {
			@Override
			public String toString(ArticleType object) {
				return object.getDescription();
			}
			@Override
			public ArticleType fromString(String string) {
				return null;
			}
		});
		articleType.setOnAction((event) -> {
			clearOutlines();
			clearErrorMessage();
			if(articleType.getSelectionModel().getSelectedItem() == null)
				return;
			
		});
		articleType.setPromptText("Please Select Article Type");
		articleType.setMaxWidth(180);
		grid.add(articleType, 1, 2);

		Text c1Label = new Text(" Color 1 : ");

		c1Label.setFill(Color.GOLD);
		c1Label.setFont(myFont);
		c1Label.setWrappingWidth(145);
		c1Label.setTextAlignment(TextAlignment.RIGHT);
		grid.add(c1Label, 0, 3);

		color1 = new ComboBox<ColorX>();
		color1.setConverter(new StringConverter<ColorX>() {
			@Override
			public String toString(ColorX object) {
				return object.getDescription();
			}
			@Override
			public ColorX fromString(String string) {
				return null;
			}
		});
		color1.setOnAction((event) -> {
			clearOutlines();
			clearErrorMessage();
			if(color1.getSelectionModel().getSelectedItem() == null)
				return;
			
		});
		color1.setPromptText("Please Select Color 1");
		color1.setMaxWidth(185);
		grid.add(color1, 1, 3);

		Text c2Label = new Text(" Color 2 : ");

		c2Label.setFill(Color.GOLD);
		c2Label.setFont(myFont);
		c2Label.setWrappingWidth(145);
		c2Label.setTextAlignment(TextAlignment.RIGHT);
		grid.add(c2Label, 0, 4);

		color2 = new ComboBox<ColorX>();
		color2.setConverter(new StringConverter<ColorX>() {
			@Override
			public String toString(ColorX object) {
				return object.getDescription();
			}
			@Override
			public ColorX fromString(String string) {
				return null;
			}
		});
		color2.setPromptText("Please Select Color 2");
		color2.setMaxWidth(185);
		grid.add(color2, 1, 4);

		fillColorComboBox();

		Text sizeLabel = new Text(" Size : ");

		sizeLabel.setFill(Color.GOLD);
		sizeLabel.setFont(myFont);
		sizeLabel.setWrappingWidth(145);
		sizeLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(sizeLabel, 0, 5);

		size = new TextField();
		size.setMinWidth(180);
		grid.add(size, 1, 5);

		Text brandLabel = new Text(" Brand : ");

		brandLabel.setFill(Color.GOLD);
		brandLabel.setFont(myFont);
                brandLabel.setWrappingWidth(165);
		brandLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(brandLabel, 2, 1);

		brand = new TextField();
		brand.setMinWidth(180);
		grid.add(brand, 3, 1);

		

		Text recipientFNameLabel = new Text(" Recipient First Name : ");

		recipientFNameLabel.setFill(Color.GOLD);
		recipientFNameLabel.setFont(myFont);
		recipientFNameLabel.setWrappingWidth(165);
		recipientFNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(recipientFNameLabel, 2, 2);

		recipientFName = new TextField();
		recipientFName.setMinWidth(180);
		grid.add(recipientFName, 3, 2);

		Text recipientLNameLabel = new Text(" Recipient Last Name : ");

		recipientLNameLabel.setFill(Color.GOLD);
		recipientLNameLabel.setFont(myFont);
		recipientLNameLabel.setWrappingWidth(165);
		recipientLNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(recipientLNameLabel, 2, 3);

		recipientLName = new TextField();
		recipientLName.setMinWidth(180);
		grid.add(recipientLName, 3, 3);

		Text recipientPhoneLabel = new Text(" Recipient Phone # : ");

		recipientPhoneLabel.setFill(Color.GOLD);
		recipientPhoneLabel.setFont(myFont);
                recipientPhoneLabel.setWrappingWidth(165);
		recipientPhoneLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(recipientPhoneLabel, 2, 4);

		recipientPhone = new TextField();
		recipientPhone.setMinWidth(180);
		grid.add(recipientPhone, 3, 4);

		Text recipientNetLabel = new Text(" Recipient Net ID : ");

		recipientNetLabel.setFill(Color.GOLD);
		recipientNetLabel.setFont(myFont);
                recipientNetLabel.setWrappingWidth(165);
		recipientNetLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(recipientNetLabel, 2, 5);

		recipientNet = new TextField();
		recipientNet.setMinWidth(180);
		grid.add(recipientNet, 3, 5);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
                doneCont.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    doneCont.setStyle("-fx-background-color: GOLD");
		});
                doneCont.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    doneCont.setStyle("-fx-background-color: SLATEGREY");
		});
		ImageView icon = new ImageView(new Image("/images/logcolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		submitButton = new Button("Log", icon);
		submitButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		submitButton.setOnAction((ActionEvent e) -> {
			clearOutlines();
			clearErrorMessage();
			Properties props = new Properties();
				if(gender.getSelectionModel().getSelectedItem() != null){
					if(articleType.getSelectionModel().getSelectedItem() != null){
						if(color1.getSelectionModel().getSelectedItem() != null){
							String sizeStr = size.getText();
							if(sizeStr.length() > GlobalVariables.MIN_SIZE_LENGTH && sizeStr.matches("[a-zA-Z0-9- ()]+")){
								String brandStr = brand.getText();
								if(brandStr.length() > GlobalVariables.MIN_BRAND_LENGTH && brandStr.matches("[a-zA-Z0-9- ()]+")){ 
										String recipientFNameStr = recipientFName.getText();
										if(recipientFNameStr.length() > GlobalVariables.MIN_FNAME_LENGTH && recipientFNameStr.matches("[a-zA-Z- /.]+")){
											String recipientLNameStr = recipientLName.getText();
											if(recipientLNameStr.length() > GlobalVariables.MIN_LNAME_LENGTH && recipientLNameStr.matches("[a-zA-Z- /.]+")){
												String recipientPhoneStr = recipientPhone.getText();
												if(recipientPhoneStr.length() >= GlobalVariables.MIN_PHONENUM_LENGTH && recipientPhoneStr.matches("[0-9-]+")){
													String recipientNetStr = recipientNet.getText();
													if(recipientNetStr.length() > GlobalVariables.MIN_NETID_LENGTH && recipientNetStr.matches("[a-zA-Z0-9-]+")) {
														props.setProperty("RequestedGender", gender.getSelectionModel().getSelectedItem().getName());
														props.setProperty("RequestedSize", sizeStr);
														props.setProperty("RequestedArticleType", articleType.getSelectionModel().getSelectedItem().getBarcodePrefix());
														props.setProperty("RequestedColor1", color1.getSelectionModel().getSelectedItem().getBarcodePrefix());
														if(color2.getSelectionModel().getSelectedItem() != null)
                                                                                                                    props.setProperty("RequestedColor2", color2.getSelectionModel().getSelectedItem().getBarcodePrefix());
														props.setProperty("RequestedBrand", brandStr);
														props.setProperty("RequesterFirstName", recipientFNameStr);
														props.setProperty("RequesterLastName", recipientLNameStr);
														props.setProperty("RequesterPhone", recipientPhoneStr);
														props.setProperty("RequesterNetId", recipientNetStr);
														props.setProperty("RequestMadeDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
														myModel.stateChangeRequest("ClothingRequestData", props);
													}
													else{
														recipientNet.setStyle("-fx-border-color: firebrick;");
														displayErrorMessage("ERROR: Please Enter a valid Recipient Net ID!");
													}
												}
												else{
													recipientPhone.setStyle("-fx-border-color: firebrick;");
													displayErrorMessage("ERROR: Please Enter a valid Recipient Phone Number!");
												}
											}
											else{
												recipientLName.setStyle("-fx-border-color: firebrick;");
												displayErrorMessage("ERROR: Please Enter a valid Recipient Last Name!");
											}
										}
										else{
											recipientFName.setStyle("-fx-border-color: firebrick;");
											displayErrorMessage("ERROR: Please Enter a valid Recipient First Name!");
										}
									}
									
								else{
									brand.setStyle("-fx-border-color: firebrick;");
									displayErrorMessage("ERROR: Please enter a valid Brand!");
								}
							}
							else{
								size.setStyle("-fx-border-color: firebrick; -fx-background-color: white; -fx-selection-bar:lightgreen;");
								displayErrorMessage("ERROR: Please enter a valid Size!");
							}
						}
						else{
							color1.setStyle("-fx-border-color: firebrick; -fx-background-color: white; -fx-selection-bar:lightgreen;");
							displayErrorMessage("ERROR: Please Select a Color 1!");
						}
					}
					else{
						articleType.setStyle("-fx-border-color: firebrick; -fx-background-color: white; -fx-selection-bar:lightgreen;");
						displayErrorMessage("ERROR: Please Select a Article Type!");
					}
				}
				else{
					gender.setStyle("-fx-border-color: firebrick; -fx-background-color: white; -fx-selection-bar:lightgreen;");
					displayErrorMessage("ERROR: Please Select a Gender!");
				}
			}
		);
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
			myModel.stateChangeRequest("CancelAddRequest", null);
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

	private void fillArticleTypeComboBox(){
		myModel.stateChangeRequest("atComboBox", null);
		try
		{
			ArticleTypeCollection articleTypeCollection = 
					(ArticleTypeCollection)myModel.getState("ArticleTypeList");

			Vector entryList = (Vector)articleTypeCollection.getState("ArticleTypes");

			if (entryList.size() > 0)
			{
				Enumeration entries = entryList.elements();
				while (entries.hasMoreElements() == true)
				{
					ArticleType nextAT = (ArticleType)entries.nextElement();
					articleType.getItems().add(nextAT);				
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void fillColorComboBox(){
		myModel.stateChangeRequest("cComboBox", null);
		try
		{
			ColorCollection colorCollection = 
					(ColorCollection)myModel.getState("ColorList");

			Vector entryList = (Vector)colorCollection.getState("Colors");

			if (entryList.size() > 0)
			{
				Enumeration entries = entryList.elements();
				while (entries.hasMoreElements() == true)
				{
					ColorX nextColor = (ColorX)entries.nextElement();
					color1.getItems().add(nextColor);
					color2.getItems().add(nextColor);
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	

	public void clearValues(){
		gender.getSelectionModel().select(null);
		articleType.getSelectionModel().select(null);
		color1.getSelectionModel().select(null);
		color2.getSelectionModel().select(null);
		size.clear();
		brand.clear();
		recipientFName.clear();
		recipientLName.clear();
		recipientPhone.clear();
		recipientNet.clear();
	}

	private void clearOutlines(){
		
		gender.setStyle("-fx-background-color: white; -fx-selection-bar:lightgreen;");
		articleType.setStyle("-fx-background-color: white; -fx-selection-bar:lightgreen;");
		color1.setStyle("-fx-background-color: white; -fx-selection-bar:lightgreen;");
		color2.setStyle("-fx-background-color: white; -fx-selection-bar:lightgreen;");
		size.setStyle("-fx-background-color: white; -fx-selection-bar:lightgreen;");
		brand.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		
		recipientFName.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		recipientLName.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		recipientPhone.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		recipientNet.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
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

//---------------------------------------------------------------
//	Revision History:
//
