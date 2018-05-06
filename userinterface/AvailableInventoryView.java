package userinterface;

// system imports
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javax.swing.JOptionPane;
import model.Inventory;
import model.InventoryCollection;

//==============================================================================
public class AvailableInventoryView extends View
{
	protected TableView<InventoryTableModel> tableOfInventory;
        protected Text actionText;
        protected HBox btnContainer;
	protected Button cancelButton;
	protected Button submitButton;
        protected Button saveButton;
	protected MessageView statusLog;
	protected Text foundText; 

	//--------------------------------------------------------------------------
	public AvailableInventoryView(IModel icv)
	{
		super(icv, "AvailableInventoryView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setStyle("-fx-background-color: slategrey");
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                                            "));

		getChildren().add(container);
		populateFields();
		myModel.subscribe("TransactionError", this);
	}

	//--------------------------------------------------------------------------
	protected void populateFields()
	{
		getEntryTableModelValues();
	}

	//--------------------------------------------------------------------------
	protected void getEntryTableModelValues()
	{
		ObservableList<InventoryTableModel> tableData = FXCollections.observableArrayList();
		try
		{
			InventoryCollection inventoryCollection = 
					(InventoryCollection)myModel.getState("InventoryList");

			Vector entryList = (Vector)inventoryCollection.getState("Inventory");

			if (entryList.size() > 0)
			{
				Enumeration entries = entryList.elements();
				while (entries.hasMoreElements() == true)
				{
					Inventory nextI = (Inventory)entries.nextElement();
					Vector<String> view = nextI.getEntryListView();

					// add this list entry to the list
					InventoryTableModel nextTableRowData = new InventoryTableModel(view);
					tableData.add(nextTableRowData);

				}
				if(entryList.size() == 1)
					foundText.setText(entryList.size()+" Available Inventory Record Found!");
				else 
					foundText.setText(entryList.size()+" Available Inventory Records Found!");

				foundText.setFill(Color.LIGHTGREEN);
			}
			else
			{
				foundText.setText("No Available Inventory Records Found!");
				foundText.setFill(Color.FIREBRICK);
			}

			tableOfInventory.setItems(tableData);
		}
		catch (Exception e) {//SQLException e) {

		}

	}

	protected String getActionText()
	{
		return "** LISTING AVAILABLE INVENTORY **";
	}

	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
	{
		HBox wrapper = new HBox(10);
                VBox imageWrapper = new VBox(10);
                ImageView bportIcon = new ImageView(new Image("/images/BPT_LOGO_All-In-One_Color.png",200,200,true,true));
                bportIcon.setEffect(new DropShadow());
		imageWrapper.getChildren().add(bportIcon);
                imageWrapper.setPadding(new Insets(10,10,10,50));
                imageWrapper.setAlignment(Pos.CENTER);
                wrapper.getChildren().add(imageWrapper);
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
		actionText.setWrappingWidth(475);
		actionText.setTextAlignment(TextAlignment.CENTER);
		actionText.setFill(Color.DARKGREEN);
		container.getChildren().add(actionText);
		container.setAlignment(Pos.CENTER);

		Text blankText2 = new Text("  ");
		blankText2.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		blankText2.setWrappingWidth(350);
		blankText2.setTextAlignment(TextAlignment.CENTER);
		blankText2.setFill(Color.WHITE);
		container.getChildren().add(blankText2);

		foundText = new Text("");
		foundText.setFont(Font.font("Copperplate", FontWeight.BOLD, 20));
		foundText.setWrappingWidth(500);
		foundText.setTextAlignment(TextAlignment.CENTER);
		container.getChildren().add(foundText);
		container.setAlignment(Pos.CENTER);
                wrapper.getChildren().add(container);
                
                VBox imageWrapper2 = new VBox(10);
                ImageView bportIcon2 = new ImageView(new Image("/images/BPT_LOGO_All-In-One_Color.png",200,200,true,true));
                bportIcon2.setEffect(new DropShadow());
		imageWrapper2.getChildren().add(bportIcon2);
                imageWrapper2.setPadding(new Insets(10,10,10,30));
                imageWrapper2.setAlignment(Pos.CENTER);
                wrapper.getChildren().add(imageWrapper2); 

		return wrapper;
	}

	// Create the main form content
	//-------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 25, 10, 0));

		tableOfInventory = new TableView<InventoryTableModel>();
		tableOfInventory.setEffect(new DropShadow());
		tableOfInventory.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-selection-bar:lightgreen;");

		TableColumn barcodeColumn = new TableColumn("Barcode") ;
		barcodeColumn.setMinWidth(40);
		barcodeColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("barcode"));

		TableColumn genderColumn = new TableColumn("Gender") ;
		genderColumn.setMinWidth(35);
		genderColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("gender"));

		TableColumn sizeColumn = new TableColumn("Size") ;
		sizeColumn.setMinWidth(20);
		sizeColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("size"));

		TableColumn articleTypeColumn = new TableColumn("Article Type") ;
		articleTypeColumn.setMinWidth(50);
		articleTypeColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("articleType"));

		TableColumn color1Column = new TableColumn("Color 1") ;
		color1Column.setMinWidth(60);
		color1Column.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("color1"));

		TableColumn color2Column = new TableColumn("Color 2") ;
		color2Column.setMinWidth(60);
		color2Column.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("color2"));

		TableColumn brandColumn = new TableColumn("Brand") ;
		brandColumn.setMinWidth(100);
		brandColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("brand"));

		TableColumn notesColumn = new TableColumn("Notes") ;
		notesColumn.setMinWidth(150);
		notesColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("notes"));

		TableColumn donorLastNameColumn = new TableColumn("Donor Last Name") ;
		donorLastNameColumn.setMinWidth(50);
		donorLastNameColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("donorLastName"));

		TableColumn donorFirstNameColumn = new TableColumn("Donor First Name") ;
		donorFirstNameColumn.setMinWidth(60);
		donorFirstNameColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("donorFirstName"));

		TableColumn donorPhoneColumn = new TableColumn("Donor Phone") ;
		donorPhoneColumn.setMinWidth(60);
		donorPhoneColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("donorPhone"));

		TableColumn donorEmailColumn = new TableColumn("Donor Email") ;
		donorEmailColumn.setMaxWidth(200);
		donorEmailColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("donorEmail"));

		TableColumn dateDonatedColumn = new TableColumn("Date Donated") ;
		dateDonatedColumn.setMinWidth(20);
		dateDonatedColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("dateDonated"));

		tableOfInventory.getColumns().addAll(barcodeColumn, genderColumn, sizeColumn, articleTypeColumn, color1Column, color2Column, brandColumn, 
				notesColumn, donorLastNameColumn, donorFirstNameColumn, donorPhoneColumn, donorEmailColumn,
				dateDonatedColumn);
		ImageView icon = new ImageView(new Image("/images/return.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		cancelButton = new Button("Return", icon);
		cancelButton.setGraphic(icon);
                cancelButton.setPadding(new Insets(5,5,5,5));
		cancelButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				myModel.stateChangeRequest("CancelInventoryList", null); 
			}
		});
		cancelButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			cancelButton.setEffect(new DropShadow());
		});
		cancelButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			cancelButton.setEffect(null);
		});
                
                icon = new ImageView(new Image("/images/savecolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		saveButton = new Button("Save to File", icon);
		saveButton.setGraphic(icon);
                saveButton.setPadding(new Insets(5,5,5,5));
		saveButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
                                saveToExcelFile();
			}
		});
		saveButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			saveButton.setEffect(new DropShadow());
		});
		saveButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			saveButton.setEffect(null);
		});

                btnContainer = new HBox(10);
                btnContainer.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    btnContainer.setStyle("-fx-background-color: GOLD");
		});
                btnContainer.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    btnContainer.setStyle("-fx-background-color: SLATEGREY");
		});
		btnContainer.setAlignment(Pos.CENTER);
                btnContainer.getChildren().add(saveButton);
		btnContainer.getChildren().add(cancelButton);

		vbox.getChildren().add(grid);
		tableOfInventory.setPrefHeight(300);
		tableOfInventory.setPrefWidth(1100);
		vbox.getChildren().add(tableOfInventory);
		vbox.getChildren().add(btnContainer);
		vbox.setPadding(new Insets(10,10,10,10));
		vbox.setAlignment(Pos.CENTER);

		return vbox;
	}

	//--------------------------------------------------------------------------
	public void updateState(String key, Object value)
	{
	}

	//--------------------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
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
        
       protected void writeToFile(String fName)
        {
            Vector allColumnNames = new Vector();

            try
            {
                FileWriter outFile = new FileWriter(fName);
                PrintWriter out = new PrintWriter(outFile);
                InventoryCollection inventoryCollection = (InventoryCollection)myModel.getState("InventoryList");
                Vector entryList = (Vector)inventoryCollection.getState("Inventory");

                if ((entryList == null) || (entryList.size() == 0))
                    return;

                allColumnNames.addElement("ScoutName");
                allColumnNames.addElement("Gender");
                allColumnNames.addElement("Size");
                allColumnNames.addElement("ArticleType");
                allColumnNames.addElement("Color1");
                allColumnNames.addElement("Color2");
                allColumnNames.addElement("Brand");
                allColumnNames.addElement("Notes");
                allColumnNames.addElement("DonorLastName");
                allColumnNames.addElement("DonorFirstName");
                allColumnNames.addElement("DonorPhone");
                allColumnNames.addElement("DonorEmail");
                allColumnNames.addElement("Status");
                allColumnNames.addElement("RecevierNetid");
                allColumnNames.addElement("RecevierLastName");
                allColumnNames.addElement("RecevierFirstName");
                allColumnNames.addElement("DateDonated");
                allColumnNames.addElement("DateTaken");

                String line = "Barcode, Gender, Size, Article Type, Color 1, Color 2, Brand, Notes, Donor Last Name, "
                        + "Donor First Name, Donor Phone, Donor Email, Date Donated";

                out.println(line);

                for (int k = 0; k < entryList.size(); k++)
                {
                    String valuesLine = "";
                    Inventory nextI = (Inventory)entryList.elementAt(k);
                    Vector<String> nextRow = nextI.getEntryListView();

                    for (int j = 0; j < allColumnNames.size()-1; j++)
                    {
                            String nextValue = nextRow.elementAt(j);
                            System.out.println(nextValue);
                            if(nextValue != null)
                                valuesLine += nextValue + ", ";
                    }
                    out.println(valuesLine);
                }

                // Also print the shift count and filter type
                out.println("\nTotal number of Inventory Records: " + entryList.size());

                // Finally, print the time-stamp
                DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                DateFormat timeFormat = new SimpleDateFormat("hh:mm aaa");
                Date date = new Date();
                String timeStamp = dateFormat.format(date) + " " +
                                   timeFormat.format(date);

                out.println("Inventory Report created on " + timeStamp);

                out.close();

                // Acknowledge successful completion to user with JOptionPane
                JOptionPane.showMessageDialog(null,
                        "Report data saved successfully to selected file");
            }
            catch (FileNotFoundException e)
            {
                JOptionPane.showMessageDialog(null, "Could not access file to save: "
                        + fName, "Save Error", JOptionPane.ERROR_MESSAGE );
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Error in saving to file: "
                        + e.toString(), "Save Error", JOptionPane.ERROR_MESSAGE );

            }
    }
}
