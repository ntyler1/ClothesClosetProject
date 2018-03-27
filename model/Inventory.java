// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


// project imports
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the Inventory for the Professional Clothes Closet 
 * application 
 */
//==============================================================
public class Inventory extends EntityBase implements IView
{
	private static final String myTableName = "Inventory";

	protected Properties dependencies;

	// GUI Components
	private String updateStatusMessage = "";

	// constructor for this class
	//----------------------------------------------------------
	public Inventory(String barcode) throws InvalidPrimaryKeyException, MultiplePrimaryKeysException
	{
		super(myTableName);

		barcode = barcode.trim();
		String query = "SELECT * FROM " + myTableName + " WHERE (Barcode = '" + barcode + "') AND (Status != 'Removed')";

		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		// You must get one article type at least
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();
			// if size = 0 throw the Invalid Primary Key Exception
			if (size == 0)
			{
				throw new InvalidPrimaryKeyException("No clothing item matching barcode : "
				+ barcode + " found.");
			}
			else
			// There should be EXACTLY one article type. More than that is an error
			if (size != 1)
			{
				
				throw new MultiplePrimaryKeysException("Multiple clothing items matching barcode : "
					+ barcode + " found.");
			}
			else
			{
				// copy all the retrieved data into persistent state
				Properties retrievedATData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedATData.propertyNames();
				while (allKeys.hasMoreElements() == true)
				{
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedATData.getProperty(nextKey);
					// accountNumber = Integer.parseInt(retrievedAccountData.getProperty("accountNumber"));

					if (nextValue != null)
					{
						persistentState.setProperty(nextKey, nextValue);
					}
				}

			}
		}
		// If no article type found for this barcode prefix, throw an Invalid Primary key exception
		else
		{
			throw new InvalidPrimaryKeyException("No cloting item matching barcode prefix : "
				+ barcode + " found.");
		}
	}
	/**
	 * Alternate constructor.
	 */
	//----------------------------------------------------------
	public Inventory(Properties props)
	{
		super(myTableName);

		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = props.propertyNames();
		while (allKeys.hasMoreElements() == true)
		{
			String nextKey = (String)allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);
			if (nextValue != "")
			{
				persistentState.setProperty(nextKey, nextValue);
			}
		}
                
	}

	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();
	
		myRegistry.setDependencies(dependencies);
	}

	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("UpdateStatusMessage") == true)
			return updateStatusMessage;

		return persistentState.getProperty(key);
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		if (persistentState.getProperty(key) != null)
		{
			persistentState.setProperty(key, (String)value);
		}
		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//-----------------------------------------------------------------------------------
	public void update(String ogBarcode)
	{
		updateStateInDatabase(ogBarcode);
	}

	public void remove()
	{
		removeStateInDatabase();
	}
        
        public void insert(){
            insertStateInDatabase();
        }

        private void insertStateInDatabase(){
            try{
            Integer iID =insertPersistentState(mySchema, persistentState);
            updateStatusMessage = "Inventory Record with barcode : " +  persistentState.getProperty("Barcode")
					+ " installed successfully!";
            }
            catch (SQLException ex)
		{
                    System.out.println(ex);
                    updateStatusMessage = "ERROR in installing inventory data in database!";
		}
        }
	//-----------------------------------------------------------------------------------
	private void updateStateInDatabase(String barcodeOG) 
	{
		try
		{
                    
			if (barcodeOG != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("Barcode", barcodeOG);
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Inventory Record with barcode : " + persistentState.getProperty("Barcode") + " updated successfully!";
			}
                        else if(persistentState.getProperty("Barcode") != null){
                                Properties whereClause = new Properties();
				whereClause.setProperty("Barcode", persistentState.getProperty("Barcode"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Inventory Record with barcode : " + persistentState.getProperty("Barcode") + " updated successfully!";
                        }
		}
		catch (SQLException ex)
		{
                    System.out.println(ex);
                    updateStatusMessage = "ERROR in installing inventory data in database!";
		}
		//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
	}

	private void removeStateInDatabase() {
		try {
			if (persistentState.getProperty("Barcode") != null) {
				Properties whereClause = new Properties();
				whereClause.setProperty("Barcode", persistentState.getProperty("Barcode"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Inventory Record with barcode : " + persistentState.getProperty("Barcode") + " removed successfully!";
			} else {
				Integer Id = insertPersistentState(mySchema, persistentState);
				updateStatusMessage = "Inventory Record with barcode : " + persistentState.getProperty("Barcode") + " installed successfully";
			}
		} catch (SQLException ex) {
			updateStatusMessage = "Error in removing clothing item data in database!";
		}
	}


	/**
	 * This method is needed solely to enable the information to be displayable in a table
	 *
	 */
	//--------------------------------------------------------------------------
	public Vector<String> getEntryListView()
	{
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("Barcode"));
		v.addElement(persistentState.getProperty("Gender"));
		v.addElement(persistentState.getProperty("Size"));
		v.addElement(persistentState.getProperty("ArticleType"));
                v.addElement(persistentState.getProperty("Color1"));
		v.addElement(persistentState.getProperty("Color2"));
		v.addElement(persistentState.getProperty("Brand"));
		v.addElement(persistentState.getProperty("Notes"));
                v.addElement(persistentState.getProperty("Status"));
		v.addElement(persistentState.getProperty("DonorLastName"));
		v.addElement(persistentState.getProperty("DonorFirstName"));
		v.addElement(persistentState.getProperty("DonorPhone"));
                v.addElement(persistentState.getProperty("DonorEmail"));
		v.addElement(persistentState.getProperty("ReceiverNetid"));
                v.addElement(persistentState.getProperty("ReceiverLastName"));
		v.addElement(persistentState.getProperty("ReceiverFirstName"));
		v.addElement(persistentState.getProperty("DateDonated"));
		v.addElement(persistentState.getProperty("DateTaken"));

		return v;
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

