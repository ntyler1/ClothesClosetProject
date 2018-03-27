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

/** The class containing the AddArticleTypeTransaction for the Professional Clothes Closet application */
//==============================================================
public class AddClothingItemTransaction extends Transaction
{
	private ArticleTypeCollection myArticleTypeList;
        private ColorCollection myColorList;

	// GUI Components

	private String transactionErrorMessage = "";

	/**
	 * Constructor for this class.
	 *
	 */
	//----------------------------------------------------------
	public AddClothingItemTransaction() throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("CancelAddCI", "CancelTransaction");
		dependencies.setProperty("OK", "CancelTransaction");
		dependencies.setProperty("ClothingItemData", "TransactionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the article type,
	 * verifying its uniqueness, etc.
	 */
	//----------------------------------------------------------
	public void processTransaction(Properties props)
	{
		if (props.getProperty("Barcode") != null)
		{
			String barcode = props.getProperty("Barcode");
			try
			{

				Inventory oldInventory = new Inventory(barcode);
				transactionErrorMessage = "ERROR: Barcode " + barcode+ " already exists!";
				new Event(Event.getLeafLevelClassName(this), "processTransaction",
						"Inventory Record with barcode: " + barcode + " already exists!",
						Event.ERROR);
			}
			catch (InvalidPrimaryKeyException ex)
			{
				// Barcode does not exist, validate data
				try{
                                        Inventory newInventory = new Inventory(props);
                                        newInventory.update(null);
                                        transactionErrorMessage = (String)newInventory.getState("UpdateStatusMessage");
				}
				catch (Exception excep)
				{
					transactionErrorMessage = "ERROR: Invalid barcode : " + barcode
						+ "! Must be numerical.";
					new Event(Event.getLeafLevelClassName(this), "processTransaction",
						"Invalid barcode : " + barcode + "! Must be numerical.",
						Event.ERROR);
				}
			}
			catch (MultiplePrimaryKeysException ex2)
			{
				transactionErrorMessage = "ERROR: Multiple inventory records with barcode!";
				new Event(Event.getLeafLevelClassName(this), "processTransaction",
					"Found multiple invenotry records with barcode : " + barcode + ". Reason: " + ex2.toString(),
					Event.ERROR);

			}
		}
		
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}else
                    if (key.equals("ArticleTypeList") == true)
		{
			return myArticleTypeList;
		}
                else
                    if (key.equals("ColorList") == true)
		{
			return myColorList;
		}
             
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// DEBUG System.out.println("AddArticleTypeTransaction.sCR: key: " + key);

		if (key.equals("DoYourJob") == true)
		{
                    doYourJob();
		}
                else if (key.equals("atComboBox") == true)
		{
			myArticleTypeList = new ArticleTypeCollection();
                        myArticleTypeList.findAll();
		}else if (key.equals("cComboBox") == true)
		{
			myColorList = new ColorCollection();
                        myColorList.findAll();
		} else
		if (key.equals("ClothingItemData") == true)
		{
			processTransaction((Properties)value);
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
		Scene currentScene = myViews.get("AddClothingItemView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("AddClothingItemView", this);
			currentScene = new Scene(newView);
			myViews.put("AddClothingItemView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
}

