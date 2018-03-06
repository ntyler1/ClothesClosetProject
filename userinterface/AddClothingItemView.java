package userinterface;
import javafx.event.Event;
import java.util.Properties;

import com.sun.xml.internal.bind.v2.runtime.Name;

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

	protected Button submitButton;
	protected Button returnButton;

	protected MessageView statusLog;




	public AddClothingItemView(IModel ac)
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
		return "*** Adding a Clothing Item ***";
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
		genderLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(genderLabel, 0, 1);

		gender = (ComboBox)createGenderComboBox();
		//gender.setPrefWidth(value);
		grid.add(gender, 1, 1);



		Text color1Label = new Text(" Color 1 : ");
		color1Label.setFont(myFont);
		color1Label.setWrappingWidth(150);
		color1Label.setTextAlignment(TextAlignment.RIGHT);
		grid.add(color1Label, 0, 2);

		color1  = (ComboBox)createColor1ComboBox();
		//color1.setPrefWidth(value);
		grid.add(color1, 1, 2);



		Text color2Label = new Text(" Color 2 : ");
		color2Label.setFont(myFont);
		color2Label.setWrappingWidth(150);
		color2Label.setTextAlignment(TextAlignment.RIGHT);
		grid.add(color2Label, 0, 3);

		color2 = (ComboBox)createColor2ComboBox();
		//color2.setPrefWidth(value);
		grid.add(color2, 1, 3);



		Text brandLabel = new Text(" Brand : ");
		brandLabel.setFont(myFont);
		brandLabel.setWrappingWidth(150);
		brandLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(brandLabel, 0, 4);

		brand = new TextField("Type brand...");
		//brand.setPrefWidth(value);
		grid.add(brand, 1, 4);



		Text notesLabel = new Text(" Notes : ");
		notesLabel.setFont(myFont);
		notesLabel.setWrappingWidth(150);
		notesLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(notesLabel, 0, 5);

		notes = new TextField("Type description...");
		//notes.setPrefWidth();
		//notes.setPrefHeight(value);
		grid.add(notes, 1, 5);


		Text firstNameLabel = new Text(" First Name : ");
		firstNameLabel.setFont(myFont);
		firstNameLabel.setWrappingWidth(150);
		firstNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(firstNameLabel, 3, 0);

		firstName = new TextField("Type first name...");
		//firstName.setPrefWidth(value);
		grid.add(firstName, 4, 0);



		Text lastNameLabel = new Text(" Last Name : ");
		lastNameLabel.setFont(myFont);
		lastNameLabel.setWrappingWidth(150);
		lastNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(lastNameLabel, 3, 1);

		lastName = new TextField("Type last name...");
		//lastName.setPrefWidth(value);
		grid.add(lastName, 4, 1);



		Text phoneNumberLabel = new Text(" Phone Number : ");
		phoneNumberLabel.setFont(myFont);
		phoneNumberLabel.setWrappingWidth(150);
		phoneNumberLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(phoneNumberLabel, 4, 2);

		phoneNumber = new TextField("Type phone number...");
		//phoneNumber.setPrefWidth(value);
		grid.add(phoneNumber, 3, 2);



		Text emailLabel = new Text(" Email Address : ");
		emailLabel.setFont(myFont);
		emailLabel.setWrappingWidth(150);
		emailLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(emailLabel, 3, 3);

		email = new TextField("Type email address...");
		//email.setPrefWidth(value);
		grid.add(email, 4, 3);





		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button("Submit");
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				Properties props = new Properties();
				String gndr = (String) gender.getValue();
				if (gndr.length() > 0)
				{
					props.setProperty("Gender", gndr);
					String clr1 = (String)color1.getValue();
					if (clr1.length() > 0)
					{
						props.setProperty("Color1", clr1);
						String clr2 = (String)color2.getValue();
						if (clr2.length() > 0)
						{
							props.setProperty("Color2", clr2);
							String brnd = brand.getText();
							if (brnd.length() > 0)
							{
								props.setProperty("Brand", brnd);
								String nts = notes.getText();
								if (nts.length() > 0)
								{
									props.setProperty("Notes", nts);
									String frstName = firstName.getText();
									if (frstName.length() > 0)
									{
										props.setProperty("FirstName", frstName);
										String lstName = lastName.getText();
										if (lstName.length() > 0)
										{
											props.setProperty("LastName", lstName);
											String phoneNum = phoneNumber.getText();
											if (phoneNum.length() > 0)
											{
												props.setProperty("PhoneNumber", phoneNum);
												String em = email.getText();
												if (em.length() > 0)
												{
													props.setProperty("Emial", em);
													myModel.stateChangeRequest("InventoryData", props); 
												}
												else
												{
													displayErrorMessage("ERROR: Please enter a valid alpha code!");
												}
											}
											else
											{
												displayErrorMessage("ERROR: Please enter a valid alpha code!");
											}
										}
										else
										{
											displayErrorMessage("ERROR: Please enter a valid alpha code!");
										}
									}
									else
									{
										displayErrorMessage("ERROR: Please enter a valid alpha code!");
									}
								}
								else
								{
									displayErrorMessage("ERROR: Please enter a valid alpha code!");
								}
							}
							else
							{
								displayErrorMessage("ERROR: Please enter a valid alpha code!");
							}
						}
						else
						{
							displayErrorMessage("ERROR: Please enter a valid alpha code!");
						}
					}
					else
					{
						displayErrorMessage("ERROR: Please enter a valid alpha code!");
					}
				}
				else
				{
					displayErrorMessage("ERROR: Please enter a valid alpha code!");
				}

			}

		});
		doneCont.getChildren().add(submitButton);

		returnButton = new Button("Return");
		returnButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		returnButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				myModel.stateChangeRequest("CancelAddCI", null);   
			}
		});
		doneCont.getChildren().add(returnButton);

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

	public Node createColor1ComboBox()
	{
		final ComboBox<String> color1Combo = new ComboBox<String>();
		color1Combo.getItems().addAll("Male" , "Female");
		return color1Combo;
	}

	public Node createColor2ComboBox()
	{
		final ComboBox<String> color2Combo = new ComboBox<String>();
		color2Combo.getItems().addAll("Male" , "Female");
		return color2Combo;
	}

	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	public void populateFields()
	{

	}

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

	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}


	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}

}
