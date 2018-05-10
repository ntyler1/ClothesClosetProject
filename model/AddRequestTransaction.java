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

/** The class containing the LogRequestTransaction for the Professional Clothes Closet application */
//==============================================================
public class AddRequestTransaction extends Transaction
{
        private ClothingRequest myClothingRequest;
        private ArticleType at;
	private ColorX color;
        private ArticleTypeCollection myArticleTypeList;
	private ColorCollection myColorList;

	// GUI Components

	private String transactionErrorMessage = "";

	/**
	 * Constructor for this class.
	 *
	 */
	//----------------------------------------------------------
	public AddRequestTransaction() throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("CancelAddRequest", "CancelTransaction");
		dependencies.setProperty("ClothingRequestData", "TransactionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the request,
	 * verifying its uniqueness, etc.
	 */
	//----------------------------------------------------------
	public void processTransaction(Properties props)
	{
		if (props.getProperty("RequesterNetId") != null)
		{
                    String requesterNetID = props.getProperty("RequesterNetId");
                    if (requesterNetID.length() > GlobalVariables.NETID_MAX_LENGTH)
                    {
                            transactionErrorMessage = "ERROR: NetID too long! ";
                    }
                    else
                        {
                            String requesterLastName = props.getProperty("RequesterLastName");
                            if (requesterLastName.length() > GlobalVariables.LASTNAME_MAX_LENGTH)
                            {
                                    transactionErrorMessage = "ERROR: Last name too long (max length = " 
                                            + GlobalVariables.LASTNAME_MAX_LENGTH + ")! ";
                            }
                                else
                                {
                                    String requesterFirstName = props.getProperty("RequesterFirstName");
                                    if (requesterFirstName.length() > GlobalVariables.FIRSTNAME_MAX_LENGTH)
                                    {
                                            transactionErrorMessage = "ERROR: First name too long (max length = " 
                                                    + GlobalVariables.FIRSTNAME_MAX_LENGTH + ")! ";
                                    }
                                    else
                                    {
                                        myClothingRequest = new ClothingRequest(props);
                                        myClothingRequest.insert();
                                        transactionErrorMessage = (String)myClothingRequest.getState("UpdateStatusMessage");
                                   }
                                }
                        }
                }
        }
        

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		else
			if (key.equals("ArticleTypeList") == true)
			{
				return myArticleTypeList;
			}
			else
				if (key.equals("ColorList") == true)
				{
					return myColorList;
				}
				else
					if (key.equals("AtSelect") == true)
					{
						return at;
					}
					else
						if (key.equals("ColorSelect") == true)
						{
							return color;
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
		} else if (key.equals("ArticleTypeSelection"))
		{
			try{
				at = new ArticleType((String)value);
			}
			catch(Exception e){
				at = null;
			}
		}
		else if (key.equals("ColorSelection"))
		{
			try{
				color = new ColorX((String)value);
			}
			catch(Exception e){
				color = null;
			}
		}
		else if (key.equals("ClothingRequestData") == true)
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
		Scene currentScene = myViews.get("AddRequestView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("AddRequestView", this);
			currentScene = new Scene(newView);
			myViews.put("AddRequestView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
}

