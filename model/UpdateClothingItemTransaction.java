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

    private InventoryCollection myInventoryList;
    private Inventory mySelectedInventoy;


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
        dependencies.setProperty("CancelAddI", "CancelTransaction");
        dependencies.setProperty("InventoryData", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    /**
     * This method encapsulates all the logic of creating the clothing item collection and showing the view
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
        else
        {
//            String desc = props.getProperty("Description");
//            String alfaC = props.getProperty("AlphaCode");
 //           myInventoryList.findByCriteria(props);
        }
        try
        {
            Scene newScene = createInventoryCollectionView();
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
            mySelectedInventoy.stateChangeRequest("Gender", gender);
        }
        String size = props.getProperty("Size");
        if (size != null) {
            mySelectedInventoy.stateChangeRequest("Size", size);
        }
        String ArticleType = props.getProperty("ArticleType");
        if (ArticleType != null) {
            mySelectedInventoy.stateChangeRequest("ArticleType", ArticleType);
        }
        String Color1 = props.getProperty("Color1");
        if (Color1 != null) {
            mySelectedInventoy.stateChangeRequest("Color1", Color1);
        }
        String Color2 = props.getProperty("Color2");
        if (Color2 != null) {
            mySelectedInventoy.stateChangeRequest("Color2", Color2);
        }
        String Brand = props.getProperty("Brand");
        if (Brand.length() > 0) {
            mySelectedInventoy.stateChangeRequest("Brand", Brand);
        }
        String Notes = props.getProperty("Notes");
        if (Notes.length() > 0) {
            mySelectedInventoy.stateChangeRequest("Notes", Notes);
        }
        String Status = props.getProperty("Status");
        if (Status.length() > 0) {
            mySelectedInventoy.stateChangeRequest("Status", Status);
        }
        String DonorLastName = props.getProperty("DonorLastName");
        if (DonorLastName.length() > 0) {
            mySelectedInventoy.stateChangeRequest("DonorLastName", DonorLastName);
        }
        String DonorFirstName = props.getProperty("DonorFirstName");
        if (DonorFirstName.length() > 0) {
            mySelectedInventoy.stateChangeRequest("DonorFirstName", DonorFirstName);
        }
        String DonorPhone = props.getProperty("DonorPhone");
        if (DonorPhone.length() > 0) {
            mySelectedInventoy.stateChangeRequest("DonorPhone", DonorPhone);
        }
        String DonorEmail = props.getProperty("DonorEmail");
        if (DonorEmail.length() > 0) {
            mySelectedInventoy.stateChangeRequest("DonorEmail", DonorEmail);
        }
        String ReceiverNetid = props.getProperty("ReceiverNetid");
        if (ReceiverNetid.length() > 0) {
            mySelectedInventoy.stateChangeRequest("ReceiverNetid", ReceiverNetid);
        }
        String ReceiverLastName = props.getProperty("ReceiverLastName");
        if (ReceiverLastName.length() > 0) {
            mySelectedInventoy.stateChangeRequest("ReceiverLastName", ReceiverLastName);
        }
        String ReceiverFirstName = props.getProperty("ReceiverFirstName");
        if (ReceiverFirstName.length() > 0) {
            mySelectedInventoy.stateChangeRequest("ReceiverFirstName", ReceiverFirstName);
        }
        String DateDonated = props.getProperty("DateDonated");
        if (DateDonated.length() > 0) {
            mySelectedInventoy.stateChangeRequest("DateDonated", DateDonated);
        }
        String DateTaken = props.getProperty("DateTaken");
        if (DateTaken.length() > 0) {
            mySelectedInventoy.stateChangeRequest("DateTaken", DateTaken);
        }
        mySelectedInventoy.update();
        transactionErrorMessage = (String)mySelectedInventoy.getState("UpdateStatusMessage");
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
            String originalBarcode = (String)mySelectedInventoy.getState("Barcode");
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
                        int barcodeVal = Integer.parseInt(barcode);
                        // Barcode prefix ok, so set it
                        mySelectedInventoy.stateChangeRequest("BarcodePrefix", barcode);
                        // Process the rest (description, alpha code). Helper does all that
                        InventoryModificationHelper(props);
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
        if (key.equals("InventoryList") == true)
        {
            return myInventoryList;
        }
        else
        if (key.equals("Barcode") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("Barcode");
            else
                return "";
        }
        else
        if (key.equals("Gender") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("Gender");
            else
                return "";
        }
        else
        if (key.equals("Size") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("Size");
            else
                return "";
        }
        if (key.equals("Color1") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("Color1");
            else
                return "";
        }
        if (key.equals("Color2") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("Color2");
            else
                return "";
        }
        if (key.equals("Brand") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("Brand");
            else
                return "";
        }
        if (key.equals("Notes") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("Notes");
            else
                return "";
        }
        if (key.equals("Status") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("Status");
            else
                return "";
        }
        if (key.equals("DonorLastName") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("DonorLastName");
            else
                return "";
        }
        if (key.equals("DonorFirstName") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("DonorFirstName");
            else
                return "";
        }
        if (key.equals("DonorPhone") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("DonorPhone");
            else
                return "";
        }
        if (key.equals("DonorEmail") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("DonorEmail");
            else
                return "";
        }
        if (key.equals("ReceiverNetid") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("ReceiverNetid");
            else
                return "";
        }
        if (key.equals("ReceiverLastName") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("ReceiverLastName");
            else
                return "";
        }
        if (key.equals("ReceiverFirstName") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("ReceiverFirstName");
            else
                return "";
        }
        if (key.equals("DateDonated") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("DateDonated");
            else
                return "";
        }
        if (key.equals("DateTaken") == true)
        {
            if (mySelectedInventoy != null)
                return mySelectedInventoy.getState("DateTaken");
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
        else
        if (key.equals("SearchInventory") == true)
        {
            processTransaction((Properties)value);
        }
        else
        if (key.equals("InventorySelected") == true)
        {
            mySelectedInventoy = myInventoryList.retrieve((String)value);
            try
            {

                Scene newScene = createModifyClothingItemView();

                swapToView(newScene);

            }
            catch (Exception ex)
            {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating ModifyClothingItemView", Event.ERROR);
            }
        }
        else
        if (key.equals("InventoryData") == true)
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
     * Create the view containing the table of all matching article types on the search criteria sents
     */
    //------------------------------------------------------
    protected Scene createInventoryCollectionView()
    {
        View newView = ViewFactory.createView("InventoryCollectionView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

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

