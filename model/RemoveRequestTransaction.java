// specify the package.
package model;

// system imports.
import event.Event;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;

public class RemoveRequestTransaction extends Transaction
{


    private ClothingRequestCollection requestList;
    private ClothingRequest mySelectedRequest;

    // GUI Components.
    private String transactionErrorMessage = "";


    public RemoveRequestTransaction() throws Exception
    {
        super();
    }


    @Override
    //----------------------------------------------------------
    protected void setDependencies()
    {
        // super.dependencies = new Properties();
        dependencies = new Properties();
        dependencies.setProperty("CancelFulfillRequest", "CancelTransaction");
        myRegistry.setDependencies(dependencies);

    }

    /**
     * This method encapsulates all the logic of creating the request collection and showing the view
     */
    //----------------------------------------------------------
    public void processTransaction(Properties props)
    {
        requestList = new ClothingRequestCollection();
        if (props.getProperty("Status") != null)
        {
            //String status = props.getProperty("Status");
            requestList.findAll();
        }
        try
        {
            Scene newScene = createClothingRequestCollectionView();
            swapToView(newScene);
        }
        catch (Exception ex)
        {
            new Event(Event.getLeafLevelClassName(this), "processTransaction",
                    "Error in creating ClothingRequestCollectionView", Event.ERROR);
        }
    }

    //----------------------------------------------------------
    private void processClothingRequestRemoval(Properties props)
    {

        mySelectedRequest.stateChangeRequest("Status", "Removed");
        mySelectedRequest.remove();
        transactionErrorMessage = (String)mySelectedRequest.getState("UpdateStatusMessage");

    }




    @Override
    //----------------------------------------------------------
    protected Scene createView()
    {
        Scene currentScene = myViews.get("RemoveRequestCollectionView");
            if (currentScene == null)
            {
                    // create our initial view
                    View newView = ViewFactory.createView("RemoveRequestCollectionView", this);
                    currentScene = new Scene(newView);
                    myViews.put("RemoveRequestCollectionView", currentScene);

                    return currentScene;
            }
            else
            {
                    return currentScene;
            }
    }

    @Override
    //----------------------------------------------------------
    public Object getState(String key)
    {

        if (key.equals("Status") == true)
        {
            if (mySelectedRequest != null)
                return mySelectedRequest.getState("Status");
            else
                return "";
        }
        else if (key.equals("PendingRequests") == true)
            {
                requestList = new ClothingRequestCollection();
                requestList.findAllPending();
                return requestList;
            }

        return null;
    }

    @Override
    //----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        if (key.equals("DoYourJob") == true)
        {
            doYourJob();
        }
        else
        if (key.equals("SearchClothingRequest") == true)
        {
            processTransaction((Properties)value);
        }
        else
        if (key.equals("RequestSelected") == true)
        {
            mySelectedRequest = requestList.retrieve((String)value);
            try
            {

                Scene newScene = createRemoveClothingRequestView();

                swapToView(newScene);

            }
            catch (Exception ex)
            {
                new Event(Event.getLeafLevelClassName(this), "processTransaction",
                        "Error in creating RemoveClothingRequestView", Event.ERROR);
            }
        }
        else
        if (key.equals("ClothingRequestData") == true)
        {
            processClothingRequestRemoval((Properties)value);
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Create the view containing the table of all matching request on the search criteria sent
     */
    //------------------------------------------------------
    protected Scene createClothingRequestCollectionView()
    {
        View newView = ViewFactory.createView("ClothingRequestCollectionView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }
    /**
     * Create the view using which data about selected request can be removed
     */
    //------------------------------------------------------
    protected Scene createRemoveClothingRequestView()
    {
        View newView = ViewFactory.createView("RemoveClothingRequestView", this);
        Scene currentScene = new Scene(newView);

        return currentScene;

    }
}
