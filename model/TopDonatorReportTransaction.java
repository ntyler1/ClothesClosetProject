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
import utilities.GlobalVariables;

/** The class containing the UpdateArticleTypeTransaction for the Professional Clothes Closet application */
//==============================================================
public class TopDonatorReportTransaction extends Transaction
{

	private InventoryCollection myInventoryList;
	// GUI Components

	private String transactionErrorMessage = "";

	/**
	 * Constructor for this class.
	 *
	 */
	//----------------------------------------------------------
	public TopDonatorReportTransaction() throws Exception
	{
		super();

	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("CancelInventoryList", "CancelTransaction");
		dependencies.setProperty("InventoryData", "TransactionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the article type collection and showing the view
	 */
	//----------------------------------------------------------
	public void processTransaction(Properties props)
	{
		myInventoryList = new InventoryCollection();
		myInventoryList.findDonators();
		try
		{	    
			Scene newScene = createUntillDateReportView();	
			swapToView(newScene);
		}
		catch (Exception ex)
		{
			new Event(Event.getLeafLevelClassName(this), "processTransaction",
					"Error in creating InventoryCollectionView", Event.ERROR);
		}
	}
	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		else if (key.equals("InventoryList") == true)
		{
			return myInventoryList;
		}

		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		//System.out.println("UpdateArticleTypeTransaction.sCR: key: " + key);

		if(key.equals("CancelInventoryList") == true){
			doYourJob();
		}
		else
			if (key.equals("DoYourJob") == true)
			{
				processTransaction(null);
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
		Scene currentScene = myViews.get("TopDonatorReportView");
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("TopDonatorReportView", this);
			currentScene = new Scene(newView);
			myViews.put("UntillDateReportView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}

	/**
	 * Create the view containing the table of all matching article types on the search criteria sents
	 */
	//------------------------------------------------------
	protected Scene createUntillDateReportView()
	{
		View newView = ViewFactory.createView("TopDonatorReportView", this);
		Scene currentScene = new Scene(newView);

		return currentScene;
	}
}