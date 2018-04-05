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
public class RemoveArticleTypeTransaction extends Transaction
{

	private ArticleTypeCollection myArticleTypeList;
	private ArticleType mySelectedArticleType;


	// GUI Components

	private String transactionErrorMessage = "";

	/**
	 * Constructor for this class.
	 *
	 */
	//----------------------------------------------------------
	public RemoveArticleTypeTransaction() throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("CancelSearchArticleType", "CancelTransaction");
		dependencies.setProperty("CancelAddAT", "CancelTransaction");
		dependencies.setProperty("ArticleTypeData", "TransactionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the article type collection and showing the view
	 */
	//----------------------------------------------------------
	public void processTransaction(Properties props)
	{
		myArticleTypeList = new ArticleTypeCollection();
		if (props.getProperty("BarcodePrefix") != null)
		{
			String barcodePrefix = props.getProperty("BarcodePrefix");
			myArticleTypeList.findByBarcodePrefix(barcodePrefix);
		}
		else
		{
			String desc = props.getProperty("Description");
			String alfaC = props.getProperty("AlphaCode");
			myArticleTypeList.findByCriteria(desc, alfaC);
		}
		try
		{	
			Scene newScene = createArticleTypeCollectionView();	
			swapToView(newScene);
		}
		catch (Exception ex)
		{
			new Event(Event.getLeafLevelClassName(this), "processTransaction",
					"Error in creating ArticleTypeCollectionView", Event.ERROR);
		}
	}

	/**
	 * This method encapsulates all the logic of removing the article type,
	 */
	//----------------------------------------------------------
	private void processArticleTypeRemoval(Properties props)
	{
		mySelectedArticleType.stateChangeRequest("Status", "Inactive");
		mySelectedArticleType.remove();
		transactionErrorMessage = (String)mySelectedArticleType.getState("UpdateStatusMessage");
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ArticleTypeList") == true)
		{
			return myArticleTypeList;
		}
		else
			if (key.equals("BarcodePrefix") == true)
			{
				if (mySelectedArticleType != null)
					return mySelectedArticleType.getState("BarcodePrefix");
				else
					return "";
			}
			else
				if (key.equals("Description") == true)
				{
					if (mySelectedArticleType != null)
						return mySelectedArticleType.getState("Description");
					else
						return "";
				}
				else
					if (key.equals("AlphaCode") == true)
					{
						if (mySelectedArticleType != null)
							return mySelectedArticleType.getState("AlphaCode");
						else
							return "";
					}
					else
						if (key.equals("TransactionError") == true)
						{
							return transactionErrorMessage;
						}

		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// DEBUG System.out.println("UpdateArticleTypeTransaction.sCR: key: " + key);

		if ((key.equals("DoYourJob") == true) || (key.equals("CancelArticleTypeList") == true))
		{
			doYourJob();
		}
		else
			if (key.equals("SearchArticleType") == true)
			{
				processTransaction((Properties)value);
			}
			else
				if (key.equals("ArticleTypeSelected") == true)
				{
					mySelectedArticleType = myArticleTypeList.retrieve((String)value);
					try
					{

						Scene newScene = createRemoveArticleTypeView();

						swapToView(newScene);

					}
					catch (Exception ex)
					{
						new Event(Event.getLeafLevelClassName(this), "processTransaction",
								"Error in creating ModifyArticleTypeView", Event.ERROR);
					}
				}
				else
					if (key.equals("ArticleTypeData") == true)
					{
						processArticleTypeRemoval((Properties)value);
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
		Scene currentScene = myViews.get("SearchArticleTypeView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchArticleTypeView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchArticleTypeView", currentScene);

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
	protected Scene createArticleTypeCollectionView()
	{
		View newView = ViewFactory.createView("ArticleTypeCollectionView", this);
		Scene currentScene = new Scene(newView);

		return currentScene;

	}

	/**
	 * Create the view using which data about selected article type can be modified
	 */
	//------------------------------------------------------
	protected Scene createRemoveArticleTypeView()
	{
		View newView = ViewFactory.createView("RemoveArticleTypeView", this);
		Scene currentScene = new Scene(newView);

		return currentScene;

	}

}

