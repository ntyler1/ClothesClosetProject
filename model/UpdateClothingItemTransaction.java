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

/** The class containing the UpdateClothingItemTransaction for the Professional Clothes Closet application */
//==============================================================
public class UpdateClothingItemTransaction extends Transaction
{

    private Inventory myInventory;
    private ArticleTypeCollection myArticleTypeList;
    private ColorCollection myColorList;


    // GUI Components

    private String transactionErrorMessage = "";

    /**
     * Constructor for this class.
     *
     */
    //----------------------------------------------------------
    public UpdateClothingItemTransaction() throws Exception
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
     * This method encapsulates all the logic of creating the clothing item collection and showing the view
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
            Scene newScene = createModifyClothingItemView();
            swapToView(newScene);
        }
        catch (Exception ex)
        {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating InventoryCollectionView", Event.ERROR);
        }
    }

    /**
     * Helper method for article type update
     */
    //--------------------------------------------------------------------------
    private void InventoryModificationHelper(Properties props)
    {
        String gender = props.getProperty("Gender");
        if (gender != null) {
            myInventory.stateChangeRequest("Gender", gender);
        }
        String size = props.getProperty("Size");
        if (size != null) {
            myInventory.stateChangeRequest("Size", size);
        }
        String ArticleType = props.getProperty("ArticleType");
        if (ArticleType != null) {
            myInventory.stateChangeRequest("ArticleType", ArticleType);
        }
        String Color1 = props.getProperty("Color1");
        if (Color1 != null) {
            myInventory.stateChangeRequest("Color1", Color1);
        }
        String Color2 = props.getProperty("Color2");
        if (Color2 != null) {
            myInventory.stateChangeRequest("Color2", Color2);
        }
        String Brand = props.getProperty("Brand");
        if (Brand.length() > 0) {
            myInventory.stateChangeRequest("Brand", Brand);
        }
        String Notes = props.getProperty("Notes");
        if (Notes.length() > 0) {
            myInventory.stateChangeRequest("Notes", Notes);
        }
        String DonorLastName = props.getProperty("DonorLastName");
        if (DonorLastName.length() > 0) {
            myInventory.stateChangeRequest("DonorLastName", DonorLastName);
        }
        String DonorFirstName = props.getProperty("DonorFirstName");
        if (DonorFirstName.length() > 0) {
            myInventory.stateChangeRequest("DonorFirstName", DonorFirstName);
        }
        String DonorPhone = props.getProperty("DonorPhone");
        if (DonorPhone.length() > 0) {
            myInventory.stateChangeRequest("DonorPhone", DonorPhone);
        }
        String DonorEmail = props.getProperty("DonorEmail");
        if (DonorEmail.length() > 0) {
            myInventory.stateChangeRequest("DonorEmail", DonorEmail);
        }
        myInventory.update(props.getProperty("BarcodeOG"));
        transactionErrorMessage = (String)myInventory.getState("UpdateStatusMessage");
    }

    /**
     * This method encapsulates all the logic of modifiying the inventory,
     * verifying the new barcode, etc.
     */
    //----------------------------------------------------------
    private void processInventoryRemove(Properties props)
    {
        if (props.getProperty("Barcode") != null)
        {
            String barcode = props.getProperty("Barcode");
            String originalBarcode = (String)myInventory.getState("Barcode");
            if (barcode.equals(originalBarcode) == false)
            {
                try
                {
                    Inventory oldInventory = new Inventory(barcode);
                    transactionErrorMessage = "ERROR: Barcode " + barcode
                            + " already exists!";
                    new Event(Event.getLeafLevelClassName(this), "processTransaction",
                            "Inventory with barcode : " + barcode + " already exists!",
                            Event.ERROR);
                }
                catch (InvalidPrimaryKeyException ex)
                {
                    // Barcode  does not exist, validate data
                    try
                    {
                        System.out.println(barcode);
                        int barcodeVal = Integer.parseInt(barcode);
                        // Barcode prefix ok, so set it
                        myInventory.stateChangeRequest("Barcode", barcode);
                        props.setProperty("BarcodeOG", originalBarcode);
                        System.out.println("bruh");
                        
                        // Process the rest (description, alpha code). Helper does all that
                        InventoryModificationHelper(props);
                        System.out.println("yay");
                    }
                    catch (Exception excep)
                    {
                        transactionErrorMessage = "ERROR: Invalid barcode: " + barcode
                                + "! Must be numerical.";
                        new Event(Event.getLeafLevelClassName(this), "processTransaction",
                                "Invalid barcode : " + barcode + "! Must be numerical.",
                                Event.ERROR);
                    }

                }
                catch (MultiplePrimaryKeysException ex2)
                {
                    transactionErrorMessage = "ERROR: Multiple article types with barcode prefix!";
                    new Event(Event.getLeafLevelClassName(this), "processTransaction",
                            "Found multiple article types with barcode prefix : " + barcode + ". Reason: " + ex2.toString(),
                            Event.ERROR);

                }
            }
            else
            {
                // No change in barcode , so just process the rest (description, alpha code). Helper does all that
                InventoryModificationHelper(props);
            }

        }

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
        // DEBUG System.out.println("UpdateInventoryTransaction.sCR: key: " + key);

        if ((key.equals("DoYourJob") == true) || (key.equals("CancelInventoryList") == true))
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
        }
        if (key.equals("SearchInventory") == true)
        {
            processTransaction((Properties)value);
        }
        else
        if (key.equals("ClothingItemData") == true)
        {
            processInventoryRemove((Properties)value);
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
     * Create the view using which data about selected article type can be modified
     */
    //------------------------------------------------------
    protected Scene createModifyClothingItemView()
    {
        View newView = ViewFactory.createView("ModifyClothingItemView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }

}

