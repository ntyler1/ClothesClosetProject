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
import utilities.GlobalVariables;


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
	//-----------------------------------------------------------
	public void findByBarcode(String barcode)
	{
		String query = "SELECT * FROM " + myTableName + " WHERE ((Barcode = '" + barcode +
				"')";
		populateCollectionHelper(query);
	}
	
	public void findByLikeBarcode(String barcode)
	{
		String query = "SELECT * FROM " + myTableName + " WHERE (Barcode LIKE '" + barcode + "%" +
				"')";
		populateCollectionHelper(query);
	}
	
	public void findByDateAndNetId(String netId)
	{
		String query = "SELECT * FROM " + myTableName + " WHERE (DateTaken > DATE_SUB(CURDATE(), INTERVAL 6 MONTH) AND ReceiverNetId = '" + netId + "')";
		populateCollectionHelper(query);
	}

	//-----------------------------------------------------------
	public void findAll()
	{
		String query = "SELECT * FROM " + myTableName + " WHERE Status = 'Donated'";
		populateCollectionHelper(query);
	}
        
        public void findMatchingInventory(String gender, String articleType){
            String query = "SELECT Barcode, Gender, Size, ArticleType.Description AS ArticleType, Color.Description AS Color1,"
                    + " Color2.Description AS Color2, Brand, Notes, Inventory.Status , DonorLastName, DonorFirstName, "
                    + "DonorPhone, DonorEmail, DateDonated FROM Inventory "
                    + "INNER JOIN ArticleType ON ArticleType.BarcodePrefix = ArticleType "
                    + "INNER JOIN Color on Color1 = Color.BarcodePrefix "
                    + "INNER JOIN Color AS Color2 on Color2 = Color2.BarcodePrefix "
                    + "WHERE Inventory.Status = 'Donated' AND Gender = '"+gender+"'"
                    + "AND ArticleType.Description = '"+articleType+"'";
		populateCollectionHelper(query);
        }
        
        public void findAvailableInventory(){
            String query = "SELECT Barcode, Gender, Size, ArticleType.Description AS ArticleType, Color.Description AS Color1, Color2.Description AS Color2, Brand, Notes, \n" +
                        "DonorLastName, DonorFirstName, DonorPhone, DonorEmail, DateDonated\n" +
                        "FROM "+myTableName+"\n" +
                        "INNER JOIN ArticleType ON ArticleType.BarcodePrefix = ArticleType\n" +
                        "INNER JOIN Color ON Color.BarcodePrefix = Color1\n" +
                        "INNER JOIN Color AS Color2 ON Color2.BarcodePrefix = Color2\n" +
                        "WHERE "+myTableName+".Status = 'Donated'";
		populateCollectionHelper(query);
        }
        
        public void findUntillDate(){
             String query = "SELECT Barcode, Gender, Size, ArticleType.Description AS ArticleType, Color.Description AS Color1, Color2.Description AS Color2, Brand, Notes, \n" +
                        "DonorLastName, DonorFirstName, DonorPhone, DonorEmail, DateDonated, ReceiverNetid, ReceiverLastName, ReceiverFirstName, DateTaken\n" +
                        "FROM "+myTableName+"\n" +
                        "INNER JOIN ArticleType ON ArticleType.BarcodePrefix = ArticleType\n" +
                        "INNER JOIN Color ON Color.BarcodePrefix = Color1\n" +
                        "INNER JOIN Color AS Color2 ON Color2.BarcodePrefix = Color2\n" +
                        "WHERE "+myTableName+".Status = 'Received' AND DateTaken <= '"+GlobalVariables.UNTILL_DATE+"'";
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
	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------
	public Inventory retrieve(String barcode)
	{
		Inventory retValue = null;
		for (int cnt = 0; cnt < inventory.size(); cnt++)
		{
			Inventory nextAT = inventory.elementAt(cnt);
			String nextBarcodePrefix = (String)nextAT.getState("Barcode");
			if (nextBarcodePrefix.equals(barcode) == true)
			{
				retValue = nextAT;
				return retValue; // we should say 'break;' here
			}
		}

		return retValue;
	}
	
	public int retrieveCount()
	{
		if(inventory != null)
			return inventory.size();
		else
			return 0;
	}
	public void addToBarcodeList(Vector<Inventory> inv)
	{
		if(inv != null)
		{
			inventory = new Vector<Inventory>();
			inventory = (Vector<Inventory>)inv.clone();
		}
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