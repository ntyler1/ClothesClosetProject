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
import model.ArticleType;
import model.ArticleTypeCollection;

//==============================================================================
public class ArticleTypeCollectionView extends View
{
	protected TableView<ArticleTypeTableModel> tableOfArticleTypes;
	protected Button cancelButton;
	protected Button submitButton;
        protected int entryCnt = 0;
	protected MessageView statusLog;
        protected Text actionText; 
        


	//--------------------------------------------------------------------------
	public ArticleTypeCollectionView(IModel matt)
	{
		super(matt, "ArticleTypeCollectionView");

		// create a container for showing the contents
		VBox container = new VBox(10);
                container.setStyle("-fx-background-color: gold");
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                                            "));

		getChildren().add(container);
		
		populateFields();
	}

	//--------------------------------------------------------------------------
	protected void populateFields()
	{
		getEntryTableModelValues();
	}

	//--------------------------------------------------------------------------
	protected void getEntryTableModelValues()
	{
		
		ObservableList<ArticleTypeTableModel> tableData = FXCollections.observableArrayList();
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
                                        entryCnt++;
                                        ArticleType nextAT = (ArticleType)entries.nextElement();
					Vector<String> view = nextAT.getEntryListView();

					// add this list entry to the list
					ArticleTypeTableModel nextTableRowData = new ArticleTypeTableModel(view);
					tableData.add(nextTableRowData);
					
				}
                                if(entryList.size() == 1)
                                    actionText.setText(entryCnt+" ARTICLE TYPE FOUND!");
                                else 
                                    actionText.setText(entryCnt+" ARTICLE TYPES FOUND!");
			}
			else
			{
				displayMessage("No matching entries found!");
			}
			
			tableOfArticleTypes.setItems(tableData);
		}
		catch (Exception e) {//SQLException e) {
			// Need to handle this exception
		}
               
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
		titleText.setFill(Color.BROWN);
		container.getChildren().add(titleText);
                		
                Text blankText = new Text("  ");
		blankText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		blankText.setWrappingWidth(350);
		blankText.setTextAlignment(TextAlignment.CENTER);
		blankText.setFill(Color.WHITE);
		container.getChildren().add(blankText);

                actionText = new Text(entryCnt+" ARTICLE TYPES FOUND!");
		actionText.setFont(Font.font("Copperplate", FontWeight.BOLD, 18));
		actionText.setWrappingWidth(350);
		actionText.setTextAlignment(TextAlignment.CENTER);
		actionText.setFill(Color.DARKGREY);
		container.getChildren().add(actionText);
	
		return container;
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

		tableOfArticleTypes = new TableView<ArticleTypeTableModel>();
                tableOfArticleTypes.setStyle("-fx-focus-color: transparent;");
		tableOfArticleTypes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	
		TableColumn barcodePrefixColumn = new TableColumn("Barcode Prefix") ;
		barcodePrefixColumn.setMinWidth(50);
		barcodePrefixColumn.setCellValueFactory(
	                new PropertyValueFactory<ArticleTypeTableModel, String>("barcodePrefix"));
		
		TableColumn descriptionColumn = new TableColumn("Description") ;
		descriptionColumn.setMinWidth(150);
		descriptionColumn.setCellValueFactory(
	                new PropertyValueFactory<ArticleTypeTableModel, String>("description"));
		  
		TableColumn alphaCodeColumn = new TableColumn("Alpha Code") ;
		alphaCodeColumn.setMinWidth(50);
		alphaCodeColumn.setCellValueFactory(
	                new PropertyValueFactory<ArticleTypeTableModel, String>("alphaCode"));
		
		TableColumn statusColumn = new TableColumn("Status") ;
		statusColumn.setMinWidth(50);
		statusColumn.setCellValueFactory(
	                new PropertyValueFactory<ArticleTypeTableModel, String>("status"));

		tableOfArticleTypes.getColumns().addAll(descriptionColumn, 
			barcodePrefixColumn, alphaCodeColumn, statusColumn);

		tableOfArticleTypes.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
					processArticleTypeSelected();
				}
			}
		});
                ImageView icon = new ImageView(new Image("/images/checkcolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		submitButton = new Button("Submit",icon);
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
 		submitButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	clearErrorMessage(); 
					// do the inquiry
					processArticleTypeSelected();
					
            	 }
        	});
                icon = new ImageView(new Image("/images/returncolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		cancelButton = new Button("Return", icon);
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
 		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
					/**
					 * Process the Cancel button.
					 * The ultimate result of this action is that the transaction will tell the Receptionist to
					 * to switch to the Receptionist view. BUT THAT IS NOT THIS VIEW'S CONCERN.
					 * It simply tells its model (controller) that the transaction was canceled, and leaves it
					 * to the model to decide to tell the Receptionist to do the switch back.
			 		*/
					//----------------------------------------------------------
       		     	clearErrorMessage();
       		     	myModel.stateChangeRequest("CancelArticleTypeList", null); 
            	  }
        	});

		HBox btnContainer = new HBox(10);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(cancelButton);
		
		vbox.getChildren().add(grid);
                tableOfArticleTypes.setPrefHeight(250);
		vbox.getChildren().add(tableOfArticleTypes);
		vbox.getChildren().add(btnContainer);
	
		return vbox;
	}

	//--------------------------------------------------------------------------
	public void updateState(String key, Object value)
	{
	}

	//--------------------------------------------------------------------------
	protected void processArticleTypeSelected()
	{
		ArticleTypeTableModel selectedItem = tableOfArticleTypes.getSelectionModel().getSelectedItem();
		
		if(selectedItem != null)
		{
			String selectedBarcodePrefix = selectedItem.getBarcodePrefix();

			myModel.stateChangeRequest("ArticleTypeSelected", selectedBarcodePrefix);
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
