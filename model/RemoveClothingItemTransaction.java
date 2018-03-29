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

/** The class containing the RemoveClothingItemTransaction for the Professional Clothes Closet application */
//==============================================================
public class RemoveClothingItemTransaction extends Transaction
{
    private ArticleType at;
    private ColorX color;
    private ArticleTypeCollection myArticleTypeList;
    private ColorCollection myColorList;
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
        dependencies.setProperty("CancelAddCI", "CancelTransaction");
        dependencies.setProperty("ClothingItemData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the inventory object and showing the view
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
            catch(InvalidPrimaryKeyException e){
                transactionErrorMessage = "ERROR: No Clothing Item Found With Entered Barcode";
            }
            catch(MultiplePrimaryKeysException e){
                transactionErrorMessage = "ERROR: Multiple Clothing Item Found With Entered Barcode";
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
        myInventory.stateChangeRequest("Status", "Removed");
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
        else
        if (key.equals("Barcode") == true)
        {
            if (myInventory != null)
                return myInventory.getState("Barcode");
            else
                return "";
        }
        else
        if (key.equals("Gender") == true)
        {
            if (myInventory != null)
                return myInventory.getState("Gender");
            else
                return "";
        }
        else
        if (key.equals("ArticleType") == true)
        {
            if (myInventory != null)
                return myInventory.getState("ArticleType");
            else
                return "";
        }
        if (key.equals("Size") == true)
        {
            if (myInventory != null)
                return myInventory.getState("Size");
            else
                return "";
        }
        if (key.equals("Color1") == true)
        {
            if (myInventory != null)
                return myInventory.getState("Color1");
            else
                return "";
        }
        if (key.equals("Color2") == true)
        {
            if (myInventory != null)
                return myInventory.getState("Color2");
            else
                return "";
        }
        if (key.equals("Brand") == true)
        {
            if (myInventory != null)
                return myInventory.getState("Brand");
            else
                return "";
        }
        if (key.equals("Notes") == true)
        {
            if (myInventory != null)
                return myInventory.getState("Notes");
            else
                return "";
        }
        if (key.equals("Status") == true)
        {
            if (myInventory != null)
                return myInventory.getState("Status");
            else
                return "";
        }
        if (key.equals("DonorLastName") == true)
        {
            if (myInventory != null)
                return myInventory.getState("DonorLastName");
            else
                return "";
        }
        if (key.equals("DonorFirstName") == true)
        {
            if (myInventory != null)
                return myInventory.getState("DonorFirstName");
            else
                return "";
        }
        if (key.equals("DonorPhone") == true)
        {
            if (myInventory != null)
                return myInventory.getState("DonorPhone");
            else
                return "";
        }
        if (key.equals("DonorEmail") == true)
        {
            if (myInventory != null)
                return myInventory.getState("DonorEmail");
            else
                return "";
        }
        if (key.equals("ReceiverNetid") == true)
        {
            if (myInventory != null)
                return myInventory.getState("ReceiverNetid");
            else
                return "";
        }
        if (key.equals("ReceiverLastName") == true)
        {
            if (myInventory != null)
                return myInventory.getState("ReceiverLastName");
            else
                return "";
        }
        if (key.equals("ReceiverFirstName") == true)
        {
            if (myInventory != null)
                return myInventory.getState("ReceiverFirstName");
            else
                return "";
        }
        if (key.equals("DateDonated") == true)
        {
            if (myInventory != null)
                return myInventory.getState("DateDonated");
            else
                return "";
        }
        if (key.equals("DateTaken") == true)
        {
            if (myInventory != null)
                return myInventory.getState("DateTaken");
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
        if (key.equals("atComboBox") == true)
        {
                myArticleTypeList = new ArticleTypeCollection();
                myArticleTypeList.findAll();
        }else if (key.equals("cComboBox") == true)
        {
                myColorList = new ColorCollection();
                myColorList.findAll();
        }
        else if (key.equals("ArticleTypeSelection"))
        {
            try{
                at = new ArticleType((String)value);
            }
            catch(Exception e){
                at = null;
            }
        }
        else 
            if (key.equals("ColorSelection"))
        {
            try{
                color = new ColorX((String)value);
            }
            catch(Exception e){
                color = null;
            }
        }
        else
            if (key.equals("SearchInventory") == true)
        {
            processTransaction((Properties)value);
        }
        else
        if (key.equals("ClothingItemData") == true)
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
        View newView = ViewFactory.createView("RemoveClothingItemView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

}

