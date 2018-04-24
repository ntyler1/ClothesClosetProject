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

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the RemoveColorTransaction for the Professional Clothes Closet application */
//==============================================================
public class RemoveColorTransaction extends Transaction
{

	private ColorCollection myColorList;
	private ColorX mySelectedColor;
        private Properties searchCritera;
        private boolean alert = true;


	// GUI Components

	private String transactionErrorMessage = "";

	/**
	 * Constructor for this class.
	 *
	 */
	//----------------------------------------------------------
	public RemoveColorTransaction() throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("CancelSearchColor", "CancelTransaction");
		dependencies.setProperty("CancelAddC", "CancelTransaction");
		dependencies.setProperty("ColorSelected", "TransactionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the color collection and showing the view
	 */
	//----------------------------------------------------------
	public void processTransaction(Properties props)
	{
		myColorList = new ColorCollection();
                if(searchCritera == null)
                    searchCritera = props;
		if (props.getProperty("BarcodePrefix") != null)
		{
			String barcodePrefix = props.getProperty("BarcodePrefix");
			myColorList.findByBarcodePrefix(barcodePrefix);
		}
		else
		{
			String desc = props.getProperty("Description");
			String alfaC = props.getProperty("AlphaCode");
			myColorList.findByCriteria(desc, alfaC);
		}
		try
		{	
			Scene newScene = createColorCollectionView();	
			swapToView(newScene);
		}
		catch (Exception ex)
		{
			new Event(Event.getLeafLevelClassName(this), "processTransaction",
					"Error in creating ColorCollectionView", Event.ERROR);
		}
	}

	//----------------------------------------------------------
	private void processColorRemoval()
	{

		mySelectedColor.stateChangeRequest("Status", "Inactive");
		mySelectedColor.remove();
                refreshTable();
		transactionErrorMessage = (String)mySelectedColor.getState("UpdateStatusMessage");	

	}
        
        public void refreshTable(){
                if (searchCritera.getProperty("BarcodePrefix") != null)
		{
			String barcodePrefix = searchCritera.getProperty("BarcodePrefix");
			myColorList.findByBarcodePrefix(barcodePrefix);
		}
		else
		{
			String desc = searchCritera.getProperty("Description");
			String alfaC = searchCritera.getProperty("AlphaCode");
			myColorList.findByCriteria(desc, alfaC);
		}
        }

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ColorList") == true)
		{
			return myColorList;
		}
		else
                if (key.equals("TransactionError") == true)
                {
                        return transactionErrorMessage;
                }
                else
                if (key.equals("Alert") == true)
                {
                        return alert;
                }

		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// DEBUG System.out.println("RemoveColorTransaction.sCR: key: " + key);

		if ((key.equals("DoYourJob") == true) || (key.equals("CancelColorList") == true))
		{
			doYourJob();
		}
		else
			if (key.equals("SearchColor") == true)
			{
				processTransaction((Properties)value);
			}
			else
				if (key.equals("ColorSelected") == true)
				{
					mySelectedColor = myColorList.retrieve((String)value);
                                        processColorRemoval();
				}

		myRegistry.updateSubscribers(key, this);
	}

	/**
	 * Create the view of this class. And then the super-class calls
	 * swapToView() to display the view in the frame
	 */
	//------------------------------------------------------
	protected Scene createView()                                 //chooses color
	{
		Scene currentScene = myViews.get("SearchColorView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchColorView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchColorView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}

	/**
	 * Create the view containing the table of all matching colors on the search criteria sents
	 */
	//------------------------------------------------------
	protected Scene createColorCollectionView()                           
	{
		View newView = ViewFactory.createView("ColorCollectionView", this);
		Scene currentScene = new Scene(newView);

		return currentScene;

	}
}

