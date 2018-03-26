// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.MultiplePrimaryKeysException;
import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the RemoveColorTransaction for the Professional Clothes Closet application */
//==============================================================
public class RemoveClothingItemTransaction extends Transaction
{

    private Inventory mySelectedInventory;
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
        dependencies.setProperty("CancelRemoveI", "CancelTransaction");
        dependencies.setProperty("InventoryData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the color collection and showing the view
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        if (props.getProperty("Barcode") != null)
        {
            String barcode = props.getProperty("Barcode");
            try{
                myInventory = new Inventory(barcode);
            }
            catch(Exception e){
                System.out.println("cant make a inventory with the barcode");
            }
        }
        try
        {
            Scene newScene = createRemoveClothingItemView();
            swapToView(newScene);
        }
        catch (Exception ex)
        {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ColorCollectionView", Event.ERROR);
        }
    }

    //----------------------------------------------------------
    private void processInventoryRemoval(Properties props)
    {
        myInventory.stateChangeRequest("Status", "Inactive");
        myInventory.remove();
        transactionErrorMessage = (String)myInventory.getState("UpdateStatusMessage");
    }

    //-----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("Inventory") == true)
        {
            return myInventory;
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
        if (key.equals("SearchInventory") == true)
        {
            processTransaction((Properties)value);
        }
        else
        if (key.equals("InventoryData") == true)
        {
            processInventoryRemoval((Properties)value);
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
        Scene currentScene = myViews.get("SearchInventoryView");

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView("SearchInventoryView", this);
            currentScene = new Scene(newView);
            myViews.put("SearchInventoryView", currentScene);

            return currentScene;
        }
        else
        {
            return currentScene;
        }
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

