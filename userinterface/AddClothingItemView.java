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

/** The class containing the Add clothing item View  for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class AddClothingItemView extends View
{

	// GUI components
	protected TextField barcode;
	protected TextField brand;
	protected TextField notes;
	protected TextField donorFName;
	protected TextField donorLName;
	protected TextField donorPhone;
	protected TextField donorEmail;
	protected Text actionText;
	protected Text prompt;
	String SaveDonorFName;
	String SaveDonorLName;
	String SaveDonorPhone;
	String SaveDonorEmail;

	protected Button submitButton;
	protected Button cancelButton;
	protected Button donor;
	protected ComboBox<Gender> gender;
	protected TextField size;
	protected ComboBox<ArticleType> articleType;
	protected ComboBox<ColorX> color1;
	protected ComboBox<ColorX> color2;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public AddClothingItemView(IModel ac)
	{
		super(ac, "AddClothingItemView");

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
		return "** ADDING NEW CLOTHING ITEM **";
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

		Text blankText = new Text("  ");
		blankText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		blankText.setWrappingWidth(350);
		blankText.setTextAlignment(TextAlignment.CENTER);
		blankText.setFill(Color.WHITE);
		vbox.getChildren().add(blankText);

		prompt = new Text("Enter New Clothing Item Information:");
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
		barcode.setOnKeyReleased(event -> {
			clearOutlines();
			clearErrorMessage();
			if(barcode.getText().length() == 0){
				gender.getSelectionModel().select(null);
				articleType.getSelectionModel().select(null);
				color1.getSelectionModel().select(null);
			}
			else if(barcode.getText().length() < 3){
				articleType.getSelectionModel().select(null);
				color1.getSelectionModel().select(null);
			}
			else if(barcode.getText().length() < 5)
				color1.getSelectionModel().select(null);
			else if(barcode.getText().length() == 8 && event.getText().length() == 1){
				String bc = barcode.getText();
				String gen = bc.substring(0, 1);
				String atPrefix = bc.substring(1, 3);
				String color1Prefix = bc.substring(3, 5);
				int cnt = 0;

				try{
					gender.getSelectionModel().select(new Gender(Integer.parseInt(gen)));
					cnt++;
					myModel.stateChangeRequest("ArticleTypeSelection", atPrefix);
					ArticleType at = (ArticleType)myModel.getState("AtSelect");
					if(at != null){
						articleType.getSelectionModel().select(at);
						cnt++;
					}
					else 
						throw new Exception();
					myModel.stateChangeRequest("ColorSelection", color1Prefix);
					ColorX col = (ColorX)myModel.getState("ColorSelect");
					if(col != null){
						color1.getSelectionModel().select(col);
						cnt++;
					}
					else
						throw new Exception();
				}
				catch(Exception e){
					switch (cnt) {
					case 0:
						gender.setStyle("-fx-border-color: firebrick; -fx-background-color: white; -fx-selection-bar:green;");
						break;
					case 1:
						articleType.setStyle("-fx-border-color: firebrick; -fx-background-color: white; -fx-selection-bar:green;");
						break;
					default:
						color1.setStyle("-fx-border-color: firebrick; -fx-background-color: white; -fx-selection-bar:green;");
						break;
					}
					displayErrorMessage("ERROR: Entered Barcode Does Not Match Data Found In Database!");
				}
			}
		});

		Text genderLabel = new Text(" Gender : ");

		genderLabel.setFill(Color.GOLD);
		genderLabel.setFont(myFont);
		genderLabel.setWrappingWidth(145);
		genderLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(genderLabel, 0, 2);

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
			if(barcode.getText().length() < GlobalVariables.BARCODE_LENGTH)
				barcode.setText("00000000");
			barcode.setText(gender.getSelectionModel().getSelectedItem().getValue() + barcode.getText().substring(1));
			if(gender.getSelectionModel().getSelectedItem() != null && color1.getSelectionModel().getSelectedItem() != null && articleType.getSelectionModel().getSelectedItem() != null){
				String bc = barcode.getText();
				barcode.setText(bc.substring(0,5) + fillBarcode(bc.substring(0,5)));
			}
		});

		gender.setMaxWidth(180);
		grid.add(gender, 1, 2);

		Text atLabel = new Text(" Article Type : ");

		atLabel.setFill(Color.GOLD);
		atLabel.setFont(myFont);
		atLabel.setWrappingWidth(145);
		atLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(atLabel, 0, 3);

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
			if(barcode.getText().length() < GlobalVariables.BARCODE_LENGTH)
				barcode.setText("00000000");
			barcode.setText(barcode.getText().substring(0,1) + articleType.getSelectionModel().getSelectedItem().getBarcodePrefix() + barcode.getText().substring(3));
			if(gender.getSelectionModel().getSelectedItem() != null && color1.getSelectionModel().getSelectedItem() != null && articleType.getSelectionModel().getSelectedItem() != null){
				String bc = barcode.getText();
				barcode.setText(bc.substring(0,5) + fillBarcode(bc.substring(0,5)));
			}
		});
		articleType.setPromptText("Please Select Article Type");
		articleType.setMaxWidth(180);
		grid.add(articleType, 1, 3);

		Text c1Label = new Text(" Color 1 : ");

		c1Label.setFill(Color.GOLD);
		c1Label.setFont(myFont);
		c1Label.setWrappingWidth(145);
		c1Label.setTextAlignment(TextAlignment.RIGHT);
		grid.add(c1Label, 0, 4);

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
			if(barcode.getText().length() < GlobalVariables.BARCODE_LENGTH)
				barcode.setText("00000000");
			barcode.setText(barcode.getText().substring(0,3) + color1.getSelectionModel().getSelectedItem().getBarcodePrefix() + barcode.getText().substring(5));
			if(gender.getSelectionModel().getSelectedItem() != null && color1.getSelectionModel().getSelectedItem() != null && articleType.getSelectionModel().getSelectedItem() != null){
				String bc = barcode.getText();
				barcode.setText(bc.substring(0,5) + fillBarcode(bc.substring(0,5)));
			}
		});
		color1.setPromptText("Please Select Color 1");
		color1.setMaxWidth(185);
		grid.add(color1, 1, 4);

		Text c2Label = new Text(" Color 2 : ");

		c2Label.setFill(Color.GOLD);
		c2Label.setFont(myFont);
		c2Label.setWrappingWidth(145);
		c2Label.setTextAlignment(TextAlignment.RIGHT);
		grid.add(c2Label, 0, 5);

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
		grid.add(color2, 1, 5);

		fillColorComboBox();

		Text sizeLabel = new Text(" Size : ");

		sizeLabel.setFill(Color.GOLD);
		sizeLabel.setFont(myFont);
		sizeLabel.setWrappingWidth(145);
		sizeLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(sizeLabel, 0, 6);

		size = new TextField();
		size.setMinWidth(180);
		grid.add(size, 1, 6);

		Text brandLabel = new Text(" Brand : ");

		brandLabel.setFill(Color.GOLD);
		brandLabel.setFont(myFont);
		brandLabel.setWrappingWidth(145);
		brandLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(brandLabel, 2, 1);

		brand = new TextField();
		brand.setMinWidth(180);
		grid.add(brand, 3, 1);

		Text notesLabel = new Text(" Notes : ");

		notesLabel.setFill(Color.GOLD);
		notesLabel.setFont(myFont);
		notesLabel.setWrappingWidth(145);
		notesLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(notesLabel, 2, 2);

		notes = new TextField();
		notes.setMinWidth(180);
		grid.add(notes, 3, 2);

		Text donorFNameLabel = new Text(" Donor First Name : ");

		donorFNameLabel.setFill(Color.GOLD);
		donorFNameLabel.setFont(myFont);
		donorFNameLabel.setWrappingWidth(145);
		donorFNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(donorFNameLabel, 2, 3);

		donorFName = new TextField();
		donorFName.setMinWidth(180);
		grid.add(donorFName, 3, 3);

		Text donorLNameLabel = new Text(" Donor Last Name : ");

		donorLNameLabel.setFill(Color.GOLD);
		donorLNameLabel.setFont(myFont);
		donorLNameLabel.setWrappingWidth(145);
		donorLNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(donorLNameLabel, 2, 4);

		donorLName = new TextField();
		donorLName.setMinWidth(180);
		grid.add(donorLName, 3, 4);

		Text donorPhoneLabel = new Text(" Donor Phone # : ");

		donorPhoneLabel.setFill(Color.GOLD);
		donorPhoneLabel.setFont(myFont);
		donorPhoneLabel.setWrappingWidth(145);
		donorPhoneLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(donorPhoneLabel, 2, 5);

		donorPhone = new TextField();
		donorPhone.setMinWidth(180);
		grid.add(donorPhone, 3, 5);

		Text donorEmailLabel = new Text(" Donor Email : ");

		donorEmailLabel.setFill(Color.GOLD);
		donorEmailLabel.setFont(myFont);
		donorEmailLabel.setWrappingWidth(145);
		donorEmailLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(donorEmailLabel, 2, 6);

		donorEmail = new TextField();
		donorEmail.setMinWidth(180);
		grid.add(donorEmail, 3, 6);

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
			String bc = barcode.getText();
			if (bc.length() == GlobalVariables.BARCODE_LENGTH && bc.matches("[0-9]+")){
				if(gender.getSelectionModel().getSelectedItem() != null){
					if(articleType.getSelectionModel().getSelectedItem() != null){
						if(color1.getSelectionModel().getSelectedItem() != null){
							String sizeStr = size.getText();
							if(sizeStr.matches("[a-zA-Z0-9- ()]*") && sizeStr.length() <= GlobalVariables.SIZE_MAX_LENGTH){
								String brandStr = brand.getText();
								if(brandStr.matches("[a-zA-Z0-9- ()]*")){ 
									String notesStr = notes.getText();
									if(notesStr.matches("[a-zA-Z0-9- ()/.]*")){
										String donorFNameStr = donorFName.getText();
										if(donorFNameStr.matches("[a-zA-Z- /.]*")){
											String donorLNameStr = donorLName.getText();
											if(donorLNameStr.matches("[a-zA-Z- /.]*")){
												String donorPhoneStr = donorPhone.getText();
												if(donorPhoneStr.matches("[0-9-]*")){
                                                                                                    if(!donorPhoneStr.equals("")){
                                                                                                        donorPhoneStr = donorPhoneStr.replaceAll("\\D+","");
                                                                                                        donorPhoneStr = donorPhoneStr.substring(0, 3) + "-" + donorPhoneStr.substring(3, 6) + "-" + donorPhoneStr.substring(6);
                                                                                                    }	
                                                                                                        String donorEmailStr = donorEmail.getText();
													if((donorEmailStr.matches("[a-zA-Z0-9-@/.]*") && donorEmailStr.contains("@")) || donorEmailStr.equals("")){
														props.setProperty("Barcode", bc);
														props.setProperty("Gender", gender.getSelectionModel().getSelectedItem().getName());
														if(!sizeStr.equals(""))
                                                                                                                    props.setProperty("Size", sizeStr);
														props.setProperty("ArticleType", articleType.getSelectionModel().getSelectedItem().getBarcodePrefix());
														props.setProperty("Color1", color1.getSelectionModel().getSelectedItem().getBarcodePrefix());
														if(color2.getSelectionModel().getSelectedItem() != null)
															props.setProperty("Color2", color2.getSelectionModel().getSelectedItem().getBarcodePrefix());
														if(!brandStr.equals(""))
                                                                                                                    props.setProperty("Brand", brandStr);
														if(!notesStr.equals(""))
                                                                                                                    props.setProperty("Notes", notesStr);
														if(!donorFNameStr.equals(""))
                                                                                                                    props.setProperty("DonorFirstName", donorFNameStr);
														if(!donorLNameStr.equals(""))
                                                                                                                    props.setProperty("DonorLastName", donorLNameStr);
														if(!donorPhoneStr.equals(""))
                                                                                                                    props.setProperty("DonorPhone", donorPhoneStr);
														if(!donorEmailStr.equals(""))
                                                                                                                    props.setProperty("DonorEmail", donorEmailStr);
														props.setProperty("DateDonated", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
														myModel.stateChangeRequest("ClothingItemData", props);
													}
													else{
														donorEmail.setStyle("-fx-border-color: firebrick;");
														displayErrorMessage("ERROR: Please Enter a valid Donor Email!");
													}
												}
												else{
													donorPhone.setStyle("-fx-border-color: firebrick;");
													displayErrorMessage("ERROR: Please Enter a valid Donor Phone Number!");
												}
											}
											else{
												donorLName.setStyle("-fx-border-color: firebrick;");
												displayErrorMessage("ERROR: Please enter a valid Donor Last Name!");
											}
										}
										else{
											donorFName.setStyle("-fx-border-color: firebrick;");
											displayErrorMessage("ERROR: Please enter a valid Donor First Name!");
										}
									}
									else{
										notes.setStyle("-fx-border-color: firebrick;");
										displayErrorMessage("ERROR: Please enter valid Notes!");
									}
								}
								else{
									brand.setStyle("-fx-border-color: firebrick;");
									displayErrorMessage("ERROR: Please enter a valid Brand!");
								}
							}
							else{
								size.setStyle("-fx-border-color: firebrick;");
								displayErrorMessage("ERROR: Please enter a valid Size!");
							}
						}
						else{
							color1.setStyle("-fx-border-color: firebrick; -fx-background-color: white; -fx-selection-bar:green;");
							displayErrorMessage("ERROR: Please Select a Color 1!");
						}
					}
					else{
						articleType.setStyle("-fx-border-color: firebrick; -fx-background-color: white; -fx-selection-bar:green;");
						displayErrorMessage("ERROR: Please Select a Article Type!");
					}
				}
				else{
					gender.setStyle("-fx-border-color: firebrick; -fx-background-color: white; -fx-selection-bar:green;");
					displayErrorMessage("ERROR: Please Select a Gender!");
				}
			}
			else
			{
				barcode.setStyle("-fx-border-color: firebrick;");
				displayErrorMessage("ERROR: Please Enter a Valid Barcode!");
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
			myModel.stateChangeRequest("CancelAddCI", null);
		});
		cancelButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			cancelButton.setEffect(new DropShadow());
		});
		cancelButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			cancelButton.setEffect(null);
		});
		
		icon = new ImageView(new Image("/images/editcolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		donor = new Button("Use Last Info", icon);
		donor.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		donor.setOnAction((ActionEvent e) -> {
			donorFName.setText(SaveDonorFName);
			donorLName.setText(SaveDonorLName);
			donorPhone.setText(SaveDonorPhone);
			donorEmail.setText(SaveDonorEmail);
		});
		donor.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			donor.setEffect(new DropShadow());
		});
		donor.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			donor.setEffect(null);
		});

		doneCont.getChildren().add(cancelButton);
		doneCont.getChildren().add(donor);
		
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);
                vbox.setAlignment(Pos.CENTER);
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
	
	private String fillBarcode(String bar){
            Properties props = new Properties();
            props.setProperty("Barcode", bar);
            myModel.stateChangeRequest("fillBarcode", props);
            int barcode = (Integer)myModel.getState("BarcodeFill");
            barcode ++;
            StringBuilder returnString = new StringBuilder();
            String s = new String(Integer.toString(barcode));
            int x = s.length();
            while(x < GlobalVariables.MAX_BARCODE_EXTENSION_LENGTH){
                    returnString.append(0);
                    x++;
            }
            returnString.append(s);
            return returnString.toString();
        }

	public void clearValues(){
		barcode.clear();
		gender.getSelectionModel().select(null);
		articleType.getSelectionModel().select(null);
		color1.getSelectionModel().select(null);
		color2.getSelectionModel().select(null);
		size.clear();
		brand.clear();
		notes.clear();
		SaveDonorFName = donorFName.getText();
		SaveDonorLName = donorLName.getText();
		SaveDonorPhone = donorPhone.getText();
		SaveDonorEmail = donorEmail.getText();
		donorFName.clear();
		donorLName.clear();
		donorPhone.clear();
		donorEmail.clear();
	}

	private void clearOutlines(){
		barcode.setStyle("-fx-border-color: transparent; -fx-focus-color: green;");
		gender.setStyle("-fx-background-color: white; -fx-selection-bar:green;");
		articleType.setStyle("-fx-background-color: white; -fx-selection-bar:green;");
		color1.setStyle("-fx-background-color: white; -fx-selection-bar:green;");
		color2.setStyle("-fx-background-color: white; -fx-selection-bar:green;");
		size.setStyle("-fx-border-color: transparent;  -fx-focus-color: darkgreen;");
		brand.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		notes.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		donorFName.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		donorLName.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		donorPhone.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
		donorEmail.setStyle("-fx-border-color: transparent; -fx-focus-color: darkgreen;");
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
