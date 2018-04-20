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

/** The class containing the UpdateArticleTypeTransaction for the Professional Clothes Closet application */
//==============================================================
public class ListAvailableInventoryTransaction extends Transaction
{

	private InventoryCollection myInventoryList;
	// GUI Components

	private String transactionErrorMessage = "";

	/**
	 * Constructor for this class.
	 *
	 */
	//----------------------------------------------------------
	public ListAvailableInventoryTransaction() throws Exception
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
		myInventoryList.findAvailableInventory();
		try
		{	    
			Scene newScene = createListInventoryAvailableView();	
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
		//		else
		//		if (key.equals("ArticleTypeSelected") == true)
		//		{
		//			mySelectedArticleType = myArticleTypeList.retrieve((String)value);
		//			try
		//			{
		//				
		//				Scene newScene = createModifyArticleTypeView();
		//			
		//				swapToView(newScene);
		//
		//			}
		//			catch (Exception ex)
		//			{
		//				new Event(Event.getLeafLevelClassName(this), "processTransaction",
		//					"Error in creating ModifyArticleTypeView", Event.ERROR);
		//			}
		//		}
		//		else
		//		if (key.equals("ArticleTypeData") == true)
		//		{
		//			processArticleTypeModification((Properties)value);
		//		}

		myRegistry.updateSubscribers(key, this);
	}

	/**
	 * Create the view of this class. And then the super-class calls
	 * swapToView() to display the view in the frame
	 */
	//------------------------------------------------------
	protected Scene createView()
	{
		Scene currentScene = myViews.get("ListInventoryAvailableView");
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("ListInventoryAvailableView", this);
			currentScene = new Scene(newView);
			myViews.put("ListInventoryAvailableView", currentScene);

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
	protected Scene createListInventoryAvailableView()
	{
		View newView = ViewFactory.createView("ListInventoryAvailableView", this);
		Scene currentScene = new Scene(newView);

		return currentScene;
	}
}

