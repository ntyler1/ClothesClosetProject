// specify the package.
package model;

// system imports.


import javafx.scene.Scene;

import java.util.Properties;

public class RemoveRequestTransaction extends Transaction
{

    private ClothingRequestCollection requestList;
    private ClothingRequest request;

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

    }

    @Override
    //----------------------------------------------------------
    protected Scene createView()
    {
        return null;
    }

    @Override
    //----------------------------------------------------------
    public Object getState(String key)
    {
        return null;
    }

    @Override
    //----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

    }
}
