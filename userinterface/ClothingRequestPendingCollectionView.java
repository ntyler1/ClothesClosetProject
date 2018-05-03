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
import javafx.scene.effect.DropShadow;
import model.ClothingRequest;
import model.ClothingRequestCollection;

//==============================================================================
public class ClothingRequestPendingCollectionView extends View
{
	protected TableView<ClothingRequestTableModel> tableOfRequests;
	protected Button cancelButton;
	protected Button submitButton;
        protected Text actionText;
        protected Text foundText; 

	protected MessageView statusLog;


	//--------------------------------------------------------------------------
	public ClothingRequestPendingCollectionView(IModel icv)
	{
		super(icv, "ClothingRequestPendingCollectionView");

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
                tableOfRequests.getSelectionModel().select(0);
        }

	//--------------------------------------------------------------------------
	protected void populateFields()
	{
		getEntryTableModelValues();
	}

	//--------------------------------------------------------------------------
	protected void getEntryTableModelValues()
	{
		
		ObservableList<ClothingRequestTableModel> tableData = FXCollections.observableArrayList();
		try
		{
			ClothingRequestCollection requestCollection = 
				(ClothingRequestCollection)myModel.getState("PendingRequests");

	 		Vector entryList = (Vector)requestCollection.getState("ClothingRequest");
			
			if (entryList.size() > 0)
			{
				Enumeration entries = entryList.elements();

				while (entries.hasMoreElements() == true)
				{	
                                        ClothingRequest nextItem = (ClothingRequest)entries.nextElement();
					Vector<String> view = nextItem.getEntryListView();

					// add this list entry to the list
					ClothingRequestTableModel nextTableRowData = new ClothingRequestTableModel(view);
					tableData.add(nextTableRowData);
					
				}
                                if(entryList.size() == 1)
                                    foundText.setText(entryList.size()+" Clothing Request Found!");
                                else 
                                    foundText.setText(entryList.size()+" Clothing Requests Found!");
                                    
                               foundText.setFill(Color.LIGHTGREEN);
			}
			else
			{
				foundText.setText("No Clothing Requests Found!");
                                foundText.setFill(Color.FIREBRICK);
                        }
                                tableOfRequests.setItems(tableData);
                }	
		catch (Exception e) {//SQLException e) {
			System.out.println(e);
		}
	}

	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
        {
                HBox wrapper = new HBox(10);
                VBox imageWrapper = new VBox(10);
                ImageView bportIcon = new ImageView(new Image("/images/BPT_LOGO_All-In-One_Color.png",135,135,true,true));
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
		actionText.setFill(Color.BLACK);
		container.getChildren().add(actionText);
		container.setAlignment(Pos.CENTER);
                
                Text blankText2 = new Text("  ");
		blankText2.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		blankText2.setWrappingWidth(350);
		blankText2.setTextAlignment(TextAlignment.CENTER);
		blankText2.setFill(Color.WHITE);
		container.getChildren().add(blankText2);
                
		foundText = new Text(" ");//set later
		foundText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		foundText.setWrappingWidth(350);
		foundText.setTextAlignment(TextAlignment.CENTER);
		foundText.setFill(Color.BLACK);
		container.getChildren().add(foundText);
                container.setAlignment(Pos.CENTER);
                wrapper.getChildren().add(container);
                
                VBox imageWrapper2 = new VBox(10);
                ImageView bportIcon2 = new ImageView(new Image("/images/BPT_LOGO_All-In-One_Color.png",135,135,true,true));
                bportIcon2.setEffect(new DropShadow());
		imageWrapper2.getChildren().add(bportIcon2);
                imageWrapper2.setPadding(new Insets(10,10,10,30));
                imageWrapper2.setAlignment(Pos.CENTER);
                wrapper.getChildren().add(imageWrapper2);
                
		return wrapper;
	}
        
        protected String getActionText()
	{
		return "** FULFILL CLOTHING REQUEST **";
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

		tableOfRequests = new TableView<ClothingRequestTableModel>();
                tableOfRequests.setEffect(new DropShadow());
                tableOfRequests.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-selection-bar:lightgreen;");
		tableOfRequests.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                
                TableColumn netIdColumn = new TableColumn("Net ID") ;
		netIdColumn.setMinWidth(40);
		netIdColumn.setCellValueFactory(
	                new PropertyValueFactory<ClothingRequestTableModel, String>("requesterNetId"));
		  
		TableColumn fnColumn = new TableColumn("First Name") ;
		fnColumn.setMinWidth(160);
		fnColumn.setCellValueFactory(
	                new PropertyValueFactory<ClothingRequestTableModel, String>("requesterFN"));
		
		TableColumn lnColumn = new TableColumn("Last Name") ;
		lnColumn.setMinWidth(160);
		lnColumn.setCellValueFactory(
	                new PropertyValueFactory<ClothingRequestTableModel, String>("requesterLN"));
                
                TableColumn phoneColumn = new TableColumn("Phone") ;
		phoneColumn.setMinWidth(50);
		phoneColumn.setCellValueFactory(
	                new PropertyValueFactory<ClothingRequestTableModel, String>("requesterPhone"));
		
		TableColumn genderColumn = new TableColumn("Gender") ;
		genderColumn.setMinWidth(50);
		genderColumn.setCellValueFactory(
	                new PropertyValueFactory<ClothingRequestTableModel, String>("requestedGender"));
		  
		TableColumn atColumn = new TableColumn("Article Type") ;
		atColumn.setMinWidth(85);
		atColumn.setCellValueFactory(
	                new PropertyValueFactory<ClothingRequestTableModel, String>("requestedAT"));
		
		TableColumn color1Column = new TableColumn("Color 1") ;
		color1Column.setMinWidth(50);
		color1Column.setCellValueFactory(
	                new PropertyValueFactory<ClothingRequestTableModel, String>("requestedC1"));
                
                TableColumn color2Column = new TableColumn("Color 2") ;
		color2Column.setMinWidth(50);
		color2Column.setCellValueFactory(
	                new PropertyValueFactory<ClothingRequestTableModel, String>("requestedC2"));
                
                TableColumn sizeColumn = new TableColumn("Size") ;
		sizeColumn.setMinWidth(50);
		sizeColumn.setCellValueFactory(
	                new PropertyValueFactory<ClothingRequestTableModel, String>("requestedSize"));
		
		TableColumn brandColumn = new TableColumn("Brand") ;
		brandColumn.setMinWidth(150);
		brandColumn.setCellValueFactory(
	                new PropertyValueFactory<ClothingRequestTableModel, String>("requestedBrand"));
                
                TableColumn dateColumn = new TableColumn("Request Date") ;
		dateColumn.setMinWidth(40);
		dateColumn.setCellValueFactory(
	                new PropertyValueFactory<ClothingRequestTableModel, String>("requestMadeDate"));

		tableOfRequests.getColumns().addAll(netIdColumn, fnColumn, lnColumn, phoneColumn, genderColumn, atColumn, 
			color1Column, color2Column, sizeColumn, brandColumn, dateColumn);

		tableOfRequests.setOnMousePressed((MouseEvent event) -> {
                    if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                        processClothingItemSelected();
                    }
                });
                ImageView icon = new ImageView(new Image("/images/check.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		submitButton = new Button("Select",icon);
		submitButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
 		submitButton.setOnAction((ActionEvent e) -> {
                    clearErrorMessage();
                    // do the inquiry
                    processClothingItemSelected();
                });
                submitButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    submitButton.setEffect(new DropShadow());
                });
                submitButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    submitButton.setEffect(null);
                });
                icon = new ImageView(new Image("/images/return.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		cancelButton = new Button("Return", icon);
		cancelButton.setFont(Font.font("Comic Sans", FontWeight.THIN, 14));
 		cancelButton.setOnAction((ActionEvent e) -> {
                    clearErrorMessage();
                    myModel.stateChangeRequest("CancelFulfillRequest", null);
                });
                cancelButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    cancelButton.setEffect(new DropShadow());
                });
                cancelButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    cancelButton.setEffect(null);
                });

		HBox btnContainer = new HBox(10);
		btnContainer.setAlignment(Pos.CENTER);
                btnContainer.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    btnContainer.setStyle("-fx-background-color: GOLD");
		});
                btnContainer.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    btnContainer.setStyle("-fx-background-color: SLATEGREY");
		});
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(cancelButton);
		
		vbox.getChildren().add(grid);
                tableOfRequests.setPrefHeight(250);
                tableOfRequests.setPrefWidth(865);
		vbox.getChildren().add(tableOfRequests);
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
	protected void processClothingItemSelected()
	{
		ClothingRequestTableModel selectedItem = tableOfRequests.getSelectionModel().getSelectedItem();
		
		if(selectedItem != null)
		{
                        String id = selectedItem.getId();
                        
			myModel.stateChangeRequest("ClothingRequestSelected", id);
		}
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
	
	
}
