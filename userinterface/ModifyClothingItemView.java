// specify the package
package userinterface;

// system imports
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// project imports
import impresario.IModel;
import java.util.Enumeration;
import java.util.Vector;
import model.Gender;
import model.ArticleType;
import model.ColorX;
import model.ArticleTypeCollection;
import model.ColorCollection;

/** The class containing the Modify Clothing Item View for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class ModifyClothingItemView extends AddClothingItemView
{
	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public ModifyClothingItemView(IModel ac)
	{
		super(ac);
	}

	//-------------------------------------------------------------
	protected String getActionText()
	{
		return "** UPDATING CLOTHING ITEM **";
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
		String brandStr = (String)myModel.getState("Brand");
		if (brandStr != null)
		{
			brand.setText(brandStr);
		}
		String notesStr = (String)myModel.getState("Notes");
		if (notesStr != null)
		{
			notes.setText(notesStr);
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
		String donorPhoneStr = (String)myModel.getState("DonorPhone");
		if (donorPhoneStr != null)
		{
			donorPhone.setText(donorPhoneStr);
		}
		String donorEmailStr = (String)myModel.getState("DonorEmail");
		if (donorEmailStr != null)
		{
			donorEmail.setText(donorEmailStr);
		}

		actionText.setFill(Color.LIGHTBLUE);
		submitButton.setText("Update"); //fix submitbutton
		ImageView icon = new ImageView(new Image("/images/savecolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		submitButton.setGraphic(icon);
	}

	public void clearValues(){

	}
}
