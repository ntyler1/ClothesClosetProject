// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import java.text.SimpleDateFormat;
import java.util.Date;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the UpdateArticleTypeTransaction for the Professional Clothes Closet application */
//==============================================================
public class FulfillRequestTransaction extends Transaction
{
        private ClothingRequestCollection requestList;
        private InventoryCollection matchingInventory;
        private ClothingRequest mySelectedRequest;
        private Inventory mySelectedInventory;
	// GUI Components

	private String transactionErrorMessage = "";

	/**
	 * Constructor for this class.
	 *
	 */
	//----------------------------------------------------------
	public FulfillRequestTransaction() throws Exception
	{
		super();

	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("CancelFulfillRequest", "CancelTransaction");
                dependencies.setProperty("CancelMatchingInventory", "DoYourJob");
                dependencies.setProperty("InventorySelected", "TransactionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the article type collection and showing the view
	 */
	//----------------------------------------------------------
	public void processTransaction(Properties props)
	{
            
	}
	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TransactionError") == true)
		{
                    return transactionErrorMessage;
		}
                else if(key.equals("InventoryList")){
                    return matchingInventory;
                }
                else if (key.equals("PendingRequests") == true)
		{
                        requestList = new ClothingRequestCollection();
                        requestList.findAllPending();
			return requestList;
		}
		
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		//System.out.println("UpdateArticleTypeTransaction.sCR: key: " + key);
                if (key.equals("DoYourJob") == true)
		{
			doYourJob();
		}
                else
                    if(key.equals("CancelMatchingInventory") == true){
                        swapToView(createView());
                    }
		else
		if (key.equals("ClothingRequestSelected") == true)
		{
			findMatchingInventory((String) value);
			try
			{
				
				Scene newScene = createMatchingInventoryView();
			
				swapToView(newScene);

			}
			catch (Exception ex)
			{
				new Event(Event.getLeafLevelClassName(this), "processTransaction",
					"Error in creating ModifyArticleTypeView", Event.ERROR);
			}
		}
		else
		if (key.equals("InventorySelected") == true)
		{
                    try{
                        fulfillRequest((String)value);
                        transactionErrorMessage = "Request Has Been Successfully Fulfilled!";
                    }
                    catch(Exception e){
                        transactionErrorMessage = "ERROR Occured When Fulfilling Request!";
                    }
		}

		myRegistry.updateSubscribers(key, this);
	}
        
        protected void findMatchingInventory(String value){
            mySelectedRequest = requestList.retrieve((String)value);
            String gender = (String)mySelectedRequest.getState("RequestedGender");
            String size = (String)mySelectedRequest.getState("RequestedSize");
            String articleType = (String)mySelectedRequest.getState("RequestedArticleType");
            matchingInventory = new InventoryCollection();
            matchingInventory.findMatchingInventory(gender, articleType, size);
        }
        
        protected void fulfillRequest(String value) throws Exception{
            mySelectedInventory = matchingInventory.retrieve((String)value);
            mySelectedRequest.stateChangeRequest("FulfilItemBarcode", value);
            mySelectedRequest.stateChangeRequest("Status", "Fulfilled");
            mySelectedRequest.stateChangeRequest("RequestFulfilledDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            String netId = (String)mySelectedRequest.getState("RequesterNetId");
            String firstName = (String)mySelectedRequest.getState("RequesterFirstName");
            String lastName = (String)mySelectedRequest.getState("RequesterLastName");
            mySelectedInventory.stateChangeRequest("ReceiverNetid", netId);
            mySelectedInventory.stateChangeRequest("Status", "Received");
            mySelectedInventory.stateChangeRequest("DateTaken", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            mySelectedInventory.stateChangeRequest("ReceiverFirstName", firstName);
            mySelectedInventory.stateChangeRequest("ReceiverLastName", lastName);
            ClothingRequest tempRequest = new ClothingRequest((String)mySelectedRequest.getState("ID"));
            Inventory tempInventory = new Inventory((String)mySelectedInventory.getState("Barcode"));
            mySelectedRequest.stateChangeRequest("RequestedArticleType", (String)tempRequest.getState("RequestedArticleType"));
            mySelectedRequest.stateChangeRequest("RequestedColor1", (String)tempRequest.getState("RequestedColor1"));
            mySelectedRequest.stateChangeRequest("RequestedColor2", (String)tempRequest.getState("RequestedColor2"));
            mySelectedInventory.stateChangeRequest("ArticleType", (String)tempInventory.getState("ArticleType"));
            mySelectedInventory.stateChangeRequest("Color1", (String)tempInventory.getState("Color1"));
            mySelectedInventory.stateChangeRequest("Color2", (String)tempInventory.getState("Color2"));
            mySelectedRequest.update();
            mySelectedInventory.update(null);
       }

	/**
	 * Create the view of this class. And then the super-class calls
	 * swapToView() to display the view in the frame
	 */
	//------------------------------------------------------
	protected Scene createView()
	{
            Scene currentScene = myViews.get("ClothingRequestPendingCollectionView");
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("ClothingRequestPendingCollectionView", this);
			currentScene = new Scene(newView);
			myViews.put("ClothingRequestPendingCollectionView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
        
	protected Scene createMatchingInventoryView()
	{
		View newView = ViewFactory.createView("MatchingInventoryView", this);
		Scene currentScene = new Scene(newView);

		return currentScene;

	}
        
        protected Scene createFulfilledRequestView()
	{
		View newView = ViewFactory.createView("FulfilledRequestView", this);
		Scene currentScene = new Scene(newView);

		return currentScene;

	}
}

