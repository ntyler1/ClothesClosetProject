// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.util.Date;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the UpdateClothingItemTransaction for the Professional Clothes Closet application */
//==============================================================
public class CheckoutClothingItemTransaction extends Transaction
{

    private Inventory myInventory;
	private InventoryCollection myInventoryList;
	private int inventoryCount = 0;
   


    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     */
    //----------------------------------------------------------
    public CheckoutClothingItemTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelSearchInventory", "CancelTransaction");
        dependencies.setProperty("CancelCheckOutCI", "CancelTransaction");
        dependencies.setProperty("RecepientData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the clothing item collection and showing the view
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
		if (props.getProperty("Barcode") != null)
		{
			String barcode = props.getProperty("Barcode");
			try{
				myInventory = new Inventory(barcode);
			}
			catch(InvalidPrimaryKeyException e){
				transactionErrorMessage = "ERROR: No Clothing Item Found With Entered Barcode";
			}
			catch(MultiplePrimaryKeysException e){
				transactionErrorMessage = "ERROR: Multiple Clothing Item Found With Entered Barcode";
			}
		}
		try
		{
			Scene newScene = createEnterRecepientInfoView();
			swapToView(newScene);
		}
		catch (Exception ex)
		{
			new Event(Event.getLeafLevelClassName(this), "processTransaction",
					"Error in creating InventoryCollectionView", Event.ERROR);
		}
    }

    /**
     * This method encapsulates all the logic of Checking out of the inventory
     */
    //----------------------------------------------------------
    private void processInventoryCheckout(Properties props)
    {
        String netId = props.getProperty("RecieverNetId");
        if (netId != null) {
            myInventory.setProperty("ReceiverNetid", netId);
        }
        String lastName = props.getProperty("ReceiverLastName");
        if (lastName != null) {
            myInventory.setProperty("ReceiverLastName", lastName);
        }
		String firstName = props.getProperty("ReceiverFirstName");
		if (firstName != null) {
            myInventory.setProperty("ReceiverFirstName", firstName);
        }
        myInventory.setProperty("DateTaken", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		myInventory.stateChangeRequest("Status", "Received");
		myInventory.update(null);
        transactionErrorMessage = (String)myInventory.getState("UpdateStatusMessage");
    }
	
	private void processNetId(Properties props)
	{
		if(props.getProperty("NetIdCheck") != null){
			String netId = props.getProperty("NetIdCheck");
			myInventoryList = new InventoryCollection();
			myInventoryList.findByDateAndNetId(netId);
			inventoryCount = myInventoryList.retrieveCount();
		}
	}

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		else if (key.equals("NetIdCheck") == true)
		{
			return (Integer)inventoryCount;
		}
        return null;
    }

    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // DEBUG System.out.println("UpdateInventoryTransaction.sCR: key: " + key);

        if ((key.equals("DoYourJob") == true) || (key.equals("CancelInventoryList") == true))
        {
            doYourJob();
        }
        else
        if (key.equals("SearchInventory") == true)
        {
            processTransaction((Properties)value);
        }
        else
        if (key.equals("RecepientData") == true)
        {
            processInventoryCheckout((Properties)value);
        }
		else
        if (key.equals("NetIdCheck") == true)
        {
            processNetId((Properties)value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view of this class. And then the super-class calls
     * swapToView() to display the view in the frame
     */
    //------------------------------------------------------
    protected Scene createView()
    {
        Scene currentScene = myViews.get("CheckOutItemView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("CheckOutItemView", this);
            currentScene = new Scene(newView);
            myViews.put("CheckOutItemView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }

    /**
     * Create the view using which data about selected article type can be modified
     */
    //------------------------------------------------------
    protected Scene createEnterRecepientInfoView()
    {
        View newView = ViewFactory.createView("EnterRecepientInfoView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

}

