// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;

// project imports
import event.Event;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the RemoveColorTransaction for the Professional Clothes Closet application */
//==============================================================
public class RemoveClothingItemTransaction extends Transaction
{

    private InventoryCollection myInventoryList;
    private Inventory myInventory;

    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     */
    //----------------------------------------------------------
    public RemoveClothingItemTransaction() throws Exception
    {
        super();
    }

    //----------------------------------------------------------
    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("CancelSearchInventory", "CancelTransaction");
        dependencies.setProperty("CancelAddI", "CancelTransaction");
        dependencies.setProperty("InventoryData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the color collection and showing the view
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        myInventoryList = new InventoryCollection();
        if (props.getProperty("Barcode") != null)
        {
            String barcode = props.getProperty("Barcode");
            myInventoryList.findByBarcode(barcode);
        }
        try
        {
            Scene newScene = createInventoryCollectionView();
            swapToView(newScene);
        }
        catch (Exception ex)
        {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ColorCollectionView", Event.ERROR);
        }
    }

    //----------------------------------------------------------
    private void processColorRemoval(Properties props)
    {
        myInventory.stateChangeRequest("Status", "Inactive");
        myInventory.remove();
        transactionErrorMessage = (String)myInventory.getState("UpdateStatusMessage");
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("InventoryList") == true)
        {
            return myInventoryList;
        }
        else
        if (key.equals("BarcodePrefix") == true)
        {
            if (myInventory != null)
                return myInventory.getState("BarcodePrefix");
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
        // DEBUG System.out.println("RemoveColorTransaction.sCR: key: " + key);

        if ((key.equals("DoYourJob") == true))
        {
            doYourJob();
        }
        else
        if (key.equals("SearchColor") == true)
        {
            processTransaction((Properties)value);
        }
        else
        if (key.equals("InventorySelected") == true)
        {
            myInventory = myInventoryList.retrieve((String)value);
            try
            {

                Scene newScene = createRemoveClothingItemView();

                swapToView(newScene);

            }
            catch (Exception ex)
            {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating RemoveClothingItemView", Event.ERROR);
            }
        }
        else
        if (key.equals("InventoryData") == true)
        {
            processColorRemoval((Properties)value);
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
     * Create the view containing the table of all matching colors on the search criteria sents
     */
    //------------------------------------------------------
    protected Scene createInventoryCollectionView() {
        View newView = ViewFactory.createView("InventoryClothingItem", this);
        Scene currentScene = new Scene(newView);

        return currentScene;
    }

    /**
     * Create the view using which data about selected color can be removed
     */
    //------------------------------------------------------
    protected Scene createRemoveClothingItemView()
    {
        View newView = ViewFactory.createView("RemoveClothingItem", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

}

