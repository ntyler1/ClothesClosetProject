package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class ClothingRequestTableModel
{
	private final SimpleStringProperty id;
	private final SimpleStringProperty requesterNetId;
	private final SimpleStringProperty requesterPhone;
	private final SimpleStringProperty requesterLN;
        private final SimpleStringProperty requesterFN;
	private final SimpleStringProperty requestedGender;
	private final SimpleStringProperty requestedAT;
	private final SimpleStringProperty requestedC1;
        private final SimpleStringProperty requestedC2;
	private final SimpleStringProperty requestedSize;
	private final SimpleStringProperty requestedBrand;
	private final SimpleStringProperty status;
        private final SimpleStringProperty fulfillItemBarcode;
	private final SimpleStringProperty requestMadeDate;
	private final SimpleStringProperty requestFulfilledDate;

	//----------------------------------------------------------------------------
	public ClothingRequestTableModel(Vector<String> atData)
	{
		id =  new SimpleStringProperty(atData.elementAt(0));
		requesterNetId =  new SimpleStringProperty(atData.elementAt(1));
		requesterPhone =  new SimpleStringProperty(atData.elementAt(2));
		requesterLN =  new SimpleStringProperty(atData.elementAt(3));
                requesterFN =  new SimpleStringProperty(atData.elementAt(4));
		requestedGender =  new SimpleStringProperty(atData.elementAt(5));
		requestedAT =  new SimpleStringProperty(atData.elementAt(6));
		requestedC1 =  new SimpleStringProperty(atData.elementAt(7));
                requestedC2 =  new SimpleStringProperty(atData.elementAt(8));
                requestedSize =  new SimpleStringProperty(atData.elementAt(9));
		requestedBrand =  new SimpleStringProperty(atData.elementAt(10));
		status =  new SimpleStringProperty(atData.elementAt(11));
                fulfillItemBarcode =  new SimpleStringProperty(atData.elementAt(12));
		requestMadeDate =  new SimpleStringProperty(atData.elementAt(13));
		requestFulfilledDate =  new SimpleStringProperty(atData.elementAt(14));
	}

	//----------------------------------------------------------------------------
    public String getId() {
        return id.get();
    }

	//----------------------------------------------------------------------------
    public void setId(String pref) {
        id.set(pref);
    }

    public String getRequesterNetId() {
        return requesterNetId.get();
    }

	//----------------------------------------------------------------------------
    public void setRequesterNetId(String pref) {
        requesterNetId.set(pref);
    }
    
    public String getRequesterPhone() {
        return requesterPhone.get();
    }

	//----------------------------------------------------------------------------
    public void setRequesterPhone(String pref) {
        requesterPhone.set(pref);
    }
    
    public String getRequesterLN() {
        return requesterLN.get();
    }

	//----------------------------------------------------------------------------
    public void setRequesterLN(String pref) {
        requesterLN.set(pref);
    }
    
    public String getRequesterFN() {
        return requesterFN.get();
    }

	//----------------------------------------------------------------------------
    public void setRequesterFN(String pref) {
        requesterFN.set(pref);
    }
    
    //----------------------------------------------------------------------------
    public String getRequestedGender() {
        return requestedGender.get();
    }

	//----------------------------------------------------------------------------
    public void setRequestedGender(String pref) {
        requestedGender.set(pref);
    }

    public String getRequestedAT() {
        return requestedAT.get();
    }

	//----------------------------------------------------------------------------
    public void setRequestedAT(String pref) {
        requestedAT.set(pref);
    }
    
    public String getRequestedC1() {
        return requestedC1.get();
    }

	//----------------------------------------------------------------------------
    public void setRequestedC1(String pref) {
        requestedC1.set(pref);
    }
    
    public String getRequestedC2() {
        return requestedC2.get();
    }

	//----------------------------------------------------------------------------
    public void setRequestedC2(String pref) {
        requestedC2.set(pref);
    }
    
    public String getRequestedSize() {
        return requestedSize.get();
    }

	//----------------------------------------------------------------------------
    public void setRequestedSize(String pref) {
        requestedSize.set(pref);
    }
    
    //----------------------------------------------------------------------------
    public String getRequestedBrand() {
        return requestedBrand.get();
    }

	//----------------------------------------------------------------------------
    public void setRequestedBrand(String pref) {
        requestedBrand.set(pref);
    }

    public String getFulfillItemBarcode() {
        return fulfillItemBarcode.get();
    }

	//----------------------------------------------------------------------------
    public void setFulfillItemBarcode(String pref) {
        fulfillItemBarcode.set(pref);
    }
    
    public String getRequestMadeDate() {
        return requestMadeDate.get();
    }

	//----------------------------------------------------------------------------
    public void setRequestMadeDate(String pref) {
        requestMadeDate.set(pref);
    }
	//----------------------------------------------------------------------------
    public void setRequestFulfilledDate(String pref) {
        requestFulfilledDate.set(pref);
    }
    
    public String getRequestFulfilledDate() {
        return requestFulfilledDate.get();
    }

    public String getStatus() {
        return 	status.get();
    }

	//----------------------------------------------------------------------------
    public void setStatus(String pref) {
        status.set(pref);
    }
}