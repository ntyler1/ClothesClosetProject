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
    //----------------------------------------------------------
    private void processClothingRequestRemoval()
    {
        mySelectedRequest.stateChangeRequest("Status", "Removed");
        try{
            ClothingRequest tempRequest = new ClothingRequest((String)mySelectedRequest.getState("ID"));
            mySelectedRequest.stateChangeRequest("RequestedArticleType", (String)tempRequest.getState("RequestedArticleType"));
            mySelectedRequest.stateChangeRequest("RequestedColor1", (String)tempRequest.getState("RequestedColor1"));
            mySelectedRequest.stateChangeRequest("RequestedColor2", (String)tempRequest.getState("RequestedColor2"));
        }
        catch(Exception e){
            
        }
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
        if (key.equals("PendingRequests") == true)
            {
                requestList = new ClothingRequestCollection();
                requestList.findAllPending();
                return requestList;
            }
        else
            if (key.equals("TransactionError") == true)
            {
                    return transactionErrorMessage;
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
        if (key.equals("ClothingRequestSelected") == true)
        {
            mySelectedRequest = requestList.retrieve((String)value);
            processClothingRequestRemoval();
        }

        myRegistry.updateSubscribers(key, this);
    }
}
