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
public class ClothingRequest extends EntityBase implements IView
{
	private static final String myTableName = "ClothingRequest";

	protected Properties dependencies;

	// GUI Components
	private String updateStatusMessage = "";

	// constructor for this class
	//----------------------------------------------------------
	public ClothingRequest(String id) throws InvalidPrimaryKeyException, MultiplePrimaryKeysException
	{
		super(myTableName);
		String query = "SELECT * FROM " + myTableName + " WHERE (ID = '" + id + "')";

		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		// You must get one article type at least
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();
			// if size = 0 throw the Invalid Primary Key Exception
			if (size == 0)
			{
				throw new InvalidPrimaryKeyException("No clothing request record matching id : "
				+ id + " found.");
			}
			else
			// There should be EXACTLY one article type. More than that is an error
			if (size != 1)
			{
				
				throw new MultiplePrimaryKeysException("Multiple Clothing Request records matching id : "
					+ id + " found.");
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
			throw new InvalidPrimaryKeyException("No clothing request record matching id : "
				+ id + " found.");
		}
	}
	/**
	 * Alternate constructor.
	 */
	//----------------------------------------------------------
	public ClothingRequest(Properties props)
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
		persistentState.setProperty(key, (String)value);
		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//-----------------------------------------------------------------------------------
	public void update()
	{
		updateStateInDatabase();
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
                updateStatusMessage = "Clothing Request Record with Requester Net ID : " +  persistentState.getProperty("RequesterNetId")
                                            + " installed successfully!";
            }
            catch (SQLException ex)
		{
                    System.out.println(ex);
                    updateStatusMessage = "ERROR in installing clothing request data in database!";
		}
        }
	//-----------------------------------------------------------------------------------
	private void updateStateInDatabase() 
	{
		try
		{
                         if(persistentState.getProperty("ID") != null){
                                Properties whereClause = new Properties();
				whereClause.setProperty("ID", persistentState.getProperty("ID"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Clothing Request Record with Requester Net ID : " + persistentState.getProperty("RequesterNetId") + " updated successfully!";
                        }
		}
		catch (SQLException ex)
		{
                    System.out.println(ex);
                    updateStatusMessage = "ERROR in update Clothing Request Record in database!";
		}
		//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
	}

	private void removeStateInDatabase() {
		try {
			if (persistentState.getProperty("ID") != null) {
				Properties whereClause = new Properties();
				whereClause.setProperty("ID", persistentState.getProperty("ID"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Clothing Request Record with Requester Net ID: " + persistentState.getProperty("RequesterNetId") + " removed successfully!";
			} else {
				Integer Id = insertPersistentState(mySchema, persistentState);
				updateStatusMessage = "Clothing Request Record with Requester Net ID: " + persistentState.getProperty("RequesterNetId") + " installed successfully";
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
		v.addElement(persistentState.getProperty("ID"));
		v.addElement(persistentState.getProperty("RequesterNetId"));
		v.addElement(persistentState.getProperty("RequesterPhone"));
		v.addElement(persistentState.getProperty("RequesterLastName"));
                v.addElement(persistentState.getProperty("RequesterFirstName"));
		v.addElement(persistentState.getProperty("RequestedGender"));
		v.addElement(persistentState.getProperty("RequestedArticleType"));
		v.addElement(persistentState.getProperty("RequestedColor1"));
                v.addElement(persistentState.getProperty("RequestedColor2"));
		v.addElement(persistentState.getProperty("RequestedSize"));
		v.addElement(persistentState.getProperty("RequestedBrand"));
		v.addElement(persistentState.getProperty("Status"));
                v.addElement(persistentState.getProperty("FulfilItemBarcode"));
		v.addElement(persistentState.getProperty("RequestMadeDate"));
                v.addElement(persistentState.getProperty("RequestFulfilledDate"));

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

