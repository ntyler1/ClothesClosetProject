// specify the package
package model;

// system imports
import java.lang.reflect.InvocationHandler;
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;


/** The class containing the InventoryCollection for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class InventoryCollection  extends EntityBase implements IView
{
	private static final String myTableName = "Inventory";

	private Vector<Inventory> inventory;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public InventoryCollection( ) 
	{
		super(myTableName);

	}
	
	//-----------------------------------------------------------
	private void populateCollectionHelper(String query)
	{
		
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null)
		{
                        inventory = new Vector<Inventory>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextIData = allDataRetrieved.elementAt(cnt);
				Inventory i = new Inventory(nextIData);

				if (i != null)
				{      
                                    inventory.add(i);
                                    
				}
			}

		}
	}
	public void findByBarcode(String barcodePrefix)
	{
		String query = "SELECT * FROM " + myTableName + " WHERE ((Barcode = '" + barcodePrefix +
				"')";
		populateCollectionHelper(query);
	}
	//-----------------------------------------------------------
	public void findAll()
	{
		String query = "SELECT * FROM " + myTableName + " WHERE Status = 'Donated'";
		populateCollectionHelper(query);
	}
	/**
	 *
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
            if (key.equals("Inventory")){
                    return inventory;                     
                }
		else
		if (key.equals("InventoryList")){
                     return this;
                }
                
            return null;
	}

	public Inventory retrieve(String barcodePrefix)
	{
		Inventory retValue = null;
		for (int cnt = 0; cnt < inventory.size(); cnt++)
		{
			Inventory nextInventory = inventory.elementAt(cnt);
			String nextBarcodePrefix = (String)nextInventory.getState("BarcodePrefix");
			if (nextBarcodePrefix.equals(barcodePrefix) == true)
			{
				retValue = nextInventory;
				return retValue; // we should say 'break;' here
			}
		}

		return retValue;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//------------------------------------------------------
	protected void createAndShowView()
	{

		Scene localScene = myViews.get("InventoryCollectionView");

		if (localScene == null)
		{
				// create our new view
				View newView = ViewFactory.createView("InventoryCollectionView", this);
				localScene = new Scene(newView);
				myViews.put("InventoryCollectionView", localScene);
		}
		// make the view visible by installing it into the frame
		swapToView(localScene);
		
	}

	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
}
