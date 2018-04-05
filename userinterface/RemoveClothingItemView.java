// specify the package
package userinterface;

// system imports
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// project imports
import impresario.IModel;
import model.Gender;
import model.ArticleType;
import model.ColorX;

/**
 * The class containing the Remove Clothing Item View for the Professional Clothes
 * Closet application
 */
// ==============================================================
public class RemoveClothingItemView extends AddClothingItemView {

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public RemoveClothingItemView(IModel ac)
	{
		super(ac);
	}

	//-------------------------------------------------------------
	protected String getActionText()
	{
		return "** REMOVING CLOTHING ITEM **";
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		String barcodeStr = (String)myModel.getState("Barcode");
		if (barcodeStr != null)
		{
			barcode.setText(barcodeStr);
		}
		String genderStr = (String)myModel.getState("Gender");
		if (genderStr != null)
		{
			try{
				gender.getSelectionModel().select(new Gender(genderStr));
			}
			catch(Exception e){
				gender.getSelectionModel().select(null);
			}
		}
		else
			gender.getSelectionModel().select(null);
		String article = (String)myModel.getState("ArticleType");
		if (article != null)
		{
			if(Integer.parseInt(article) < 10)
				article = "0"+article;
			myModel.stateChangeRequest("ArticleTypeSelection", article);
			ArticleType at = (ArticleType)myModel.getState("AtSelect");
			articleType.getSelectionModel().select(at);
		}
		else
			articleType.getSelectionModel().select(null);
		String color1Str = (String)myModel.getState("Color1");
		if (color1Str != null)
		{
			if(Integer.parseInt(color1Str) < 10)
				color1Str = "0"+color1Str;
			myModel.stateChangeRequest("ColorSelection", color1Str);
			ColorX col = (ColorX)myModel.getState("ColorSelect");
			color1.getSelectionModel().select(col);
		}
		else
			color1.getSelectionModel().select(null);
		String color2Str = (String)myModel.getState("Color2");
		if (color2Str != null)
		{
			if(Integer.parseInt(color2Str) < 10)
				color2Str = "0"+color2Str;
			myModel.stateChangeRequest("ColorSelection", color2Str);
			ColorX col = (ColorX)myModel.getState("ColorSelect");
			color2.getSelectionModel().select(col);
		}
		else
			color2.getSelectionModel().select(null);
		String sizeStr = (String)myModel.getState("Size");
		if (sizeStr != null)
		{
			size.setText(sizeStr);
		}
		String brnd = (String)myModel.getState("Brand");
		if (brnd != null)
		{
			brand.setText(brnd);
		}
		String nts = (String)myModel.getState("Notes");
		if (nts != null)
		{
			notes.setText(nts);
		}
		String donorFirst = (String)myModel.getState("DonorFirstName");
		if (donorFirst != null)
		{
			donorFName.setText(donorFirst);
		}
		String donorLast = (String)myModel.getState("DonorLastName");
		if (donorLast != null)
		{
			donorLName.setText(donorLast);
		}
		String donorP = (String)myModel.getState("DonorPhone");
		if (donorP != null)
		{
			donorPhone.setText(donorP);
		}
		String donorMail = (String)myModel.getState("DonorEmail");
		if (donorMail != null)
		{
			donorEmail.setText(donorMail);
		}

		barcode.setDisable(true);
		gender.setDisable(true);
		articleType.setDisable(true);
		color1.setDisable(true);
		color2.setDisable(true);
		size.setDisable(true);
		brand.setDisable(true);
		notes.setDisable(true);
		donorFName.setDisable(true);
		donorLName.setDisable(true);
		donorPhone.setDisable(true);
		donorEmail.setDisable(true);
		actionText.setFill(Color.PALEVIOLETRED);

		prompt.setText("Clothing Item Information:");
		submitButton.setText("Remove"); //fix submitbutton
		ImageView icon = new ImageView(new Image("/images/trashcolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		submitButton.setGraphic(icon);
	}

	public void clearValues(){

	}
}
