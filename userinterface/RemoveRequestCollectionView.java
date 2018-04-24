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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.ClothingRequest;
import model.ClothingRequestCollection;

/** The class containing the Modify Color View  for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class RemoveRequestCollectionView extends ClothingRequestPendingCollectionView
{

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public RemoveRequestCollectionView(IModel rc)
	{
		super(rc);
	}

	//-------------------------------------------------------------
	protected String getActionText()	
	{
		return "** REMOVE CLOTHING REQUEST **";
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
            getEntryTableModelValues();
            actionText.setFill(Color.PALEVIOLETRED);
            submitButton.setOnAction((ActionEvent e) -> {
                    clearErrorMessage();
                    // do the inquiry
//                    processClothingItemSelected();
                    Alert alert = new Alert(AlertType.ERROR,"Request From Requester With Net ID: "+tableOfRequests.getSelectionModel().getSelectedItem().getRequesterNetId(), ButtonType.YES, ButtonType.NO);
                    alert.setHeaderText(null);
                    alert.setTitle("Remove Clothing Request");
                    alert.setHeaderText("Are you sure want to remove this clothing request?");
                    ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/BPT_LOGO_All-In-One_Color.png"));
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        //do stuff
                    }
            });
	}

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

}