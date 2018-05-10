// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

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
	private Vector<Inventory> inv = new Vector<Inventory>();
	private String possibleNetId;
	private String possibleDateTaken;
   


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
		for(Inventory i: inv)
		{
			String netId = props.getProperty("ReceiverNetid");
			if (netId != null) {
				i.setProperty("ReceiverNetid", netId);
			}
			String lastName = props.getProperty("ReceiverLastName");
			if (lastName != null) {
				i.setProperty("ReceiverLastName", lastName);
			}
			String firstName = props.getProperty("ReceiverFirstName");
			if (firstName != null) {
				i.setProperty("ReceiverFirstName", firstName);
			}
			i.setProperty("DateTaken", new SimpleDateFormat("MM-dd-yyyy").format(new Date()));
			i.stateChangeRequest("Status", "Received");
			i.update(null);
			transactionErrorMessage = (String)i.getState("UpdateStatusMessage");
		}
    }

    /**
     * This method encapsulates all the logic of Checking out of the inventory
     */
    //----------------------------------------------------------
    private void processInventoryCheckout(Properties props)
    {
        String netId = props.getProperty("ReceiverNetid");
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
        myInventory.setProperty("DateTaken", new SimpleDateFormat("MM-dd-yyyy").format(new Date()));
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
			
			System.out.println(inventoryCount);
		}
	}
	
	private void addBarcodeToTable (Properties props)
	{
		if(props.getProperty("BarcodeToAdd") != null){
			String barcode = props.getProperty("BarcodeToAdd");
			boolean check = true;
			try{
				myInventory = new Inventory(barcode);
				for(Inventory i: inv)
				{
					if(i.getState("Barcode").equals(myInventory.getState("Barcode"))){     //this code makes sure that there are no duplicates in the table
						check = false;
					}
				}
				if(check)
				{
					checkForPastCheckout();
					inv.add(myInventory);
					myInventoryList = new InventoryCollection();
					myInventoryList.addToBarcodeList(inv);
				}
				myInventory = null;
			}
			catch(InvalidPrimaryKeyException e){
				transactionErrorMessage = "ERROR: No Clothing Item Found With Entered Barcode";
				myInventory = null;
				myInventory.getState("Barcode");
			}
			catch(MultiplePrimaryKeysException e){
				transactionErrorMessage = "ERROR: Multiple Clothing Item Found With Entered Barcode";
			}
			
		}	
	}
	private void checkForPastCheckout(){
		possibleDateTaken = (String)myInventory.getState("DateTaken");
		possibleNetId = (String)myInventory.getState("ReceiverNetid");
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
		if (key.equals("Barcode") == true)
		{
			if (myInventory != null)
				return myInventory.getState("Barcode");
			else
				return "";
		}
		if (key.equals("ReceiverNetid") == true)
		{
			if (myInventory != null)
				return myInventory.getState("ReceiverNetid");
			else
				return "";
		}
		if (key.equals("DateTaken") == true)
		{
			if (myInventory != null)
				return myInventory.getState("DateTaken");
			else
				return "";
		}
		if (key.equals("PossibleReceiverNetid") == true)
		{
			return possibleNetId;
		}
		if (key.equals("PossibleDateTaken") == true)
		{
			return possibleDateTaken;
		}
		if (key.equals("InventoryList") == true)
		{
			return myInventoryList;
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
        if (key.equals("RecepientData") == true)
        {
            processTransaction((Properties)value);
        }
		else
        if (key.equals("NetIdCheck") == true)
        {
            processNetId((Properties)value);
        }
		else
        if (key.equals("AddBarcode") == true)
        {
            addBarcodeToTable((Properties)value);
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
        Scene currentScene = myViews.get("EnterRecepientInfoView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("EnterRecepientInfoView", this);
            currentScene = new Scene(newView);
            myViews.put("EnterRecepientInfoView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
    }
    
}

