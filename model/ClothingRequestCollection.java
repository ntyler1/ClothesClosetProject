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
public class ClothingRequestCollection  extends EntityBase implements IView
{
	private static final String myTableName = "ClothingRequest";

	private Vector<ClothingRequest> request;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public ClothingRequestCollection( ) 
	{
		super(myTableName);

	}
	
	//-----------------------------------------------------------
	private void populateCollectionHelper(String query)
	{
		
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null)
		{
                        request = new Vector<ClothingRequest>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextIData = allDataRetrieved.elementAt(cnt);
				ClothingRequest r = new ClothingRequest(nextIData);

				if (r != null)
				{      
                                    request.add(r);  
				}
			}

		}
	}
	
	//-----------------------------------------------------------
	public void findAll()
	{
		String query = "SELECT * FROM " + myTableName + " WHERE Status = 'Pending'";
		populateCollectionHelper(query);
	}
	/**
	 *
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
            if (key.equals("ClothingRequest")){
                    return request;                     
                }
		else
		if (key.equals("ClothingRequestList")){
                     return this;
                }
                
            return null;
	}
	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------
//	public Inventory retrieve(String barcode)
//	{
//		Inventory retValue = null;
//		for (int cnt = 0; cnt < inventory.size(); cnt++)
//		{
//			Inventory nextAT = inventory.elementAt(cnt);
//			String nextBarcodePrefix = (String)nextAT.getState("Barcode");
//			if (nextBarcodePrefix.equals(barcode) == true)
//			{
//				retValue = nextAT;
//				return retValue; // we should say 'break;' here
//			}
//		}
//
//		return retValue;
//	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//------------------------------------------------------
	protected void createAndShowView()
	{

		Scene localScene = myViews.get("ClothingRequestCollectionView");

		if (localScene == null)
		{
				// create our new view
				View newView = ViewFactory.createView("ClothingRequestCollectionView", this);
				localScene = new Scene(newView);
				myViews.put("ClothingRequestCollectionView", localScene);
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
