// specify the package
package userinterface;

// system imports
import javafx.scene.paint.Color;

// project imports
import impresario.IModel;
import java.util.Enumeration;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Inventory;
import model.InventoryCollection;

//==============================================================
public class MatchingInventoryView extends AvailableInventoryView
{


	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public MatchingInventoryView(IModel at)
	{
		super(at);
	}

	//-------------------------------------------------------------
	protected String getActionText()
	{
		return "** FULFILL CLOTHING REQUEST **";
	}

	//-------------------------------------------------------------
	protected void populateFields()
	{
		actionText.setFill(Color.LIGHTBLUE);
                tableOfInventory.setOnMousePressed((MouseEvent event) -> {
                    if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                        processClothingItemSelected();
                    }
                });
                ImageView icon = new ImageView(new Image("/images/check.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
		submitButton = new Button("Select",icon);
                submitButton.setPadding(new Insets(10,10,10,10));
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
                cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				myModel.stateChangeRequest("CancelMatchingInventory", null); 
			}
		});
                btnContainer.getChildren().clear();
                btnContainer.getChildren().add(submitButton);
                btnContainer.getChildren().add(cancelButton);
                getEntryTableModelValues();
	}

	protected void clearValues(){

	}
        
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
					Inventory nextAT = (Inventory)entries.nextElement();
					Vector<String> view = nextAT.getEntryListView();

					// add this list entry to the list
					InventoryTableModel nextTableRowData = new InventoryTableModel(view);
					tableData.add(nextTableRowData);

				}
				if(entryList.size() == 1)
					foundText.setText(entryList.size()+" Matching Inventory Record Found!");
				else 
					foundText.setText(entryList.size()+" Matching Inventory Records Found!");

				foundText.setFill(Color.LIGHTGREEN);
			}
			else
			{
				foundText.setText("No Matching Inventory Records Found!");
				foundText.setFill(Color.FIREBRICK);
			}

			tableOfInventory.setItems(tableData);
		}
		catch (Exception e) {//SQLException e) {

		}

	}
        
        protected void processClothingItemSelected()
	{
		InventoryTableModel selectedItem = tableOfInventory.getSelectionModel().getSelectedItem();
		
		if(selectedItem != null)
		{
                        String barcode = selectedItem.getBarcode();
                        
			myModel.stateChangeRequest("InventorySelected", barcode);
                }
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
				clearValues();
				displayMessage(val);
			}
		}
	}
        
        public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}
}
