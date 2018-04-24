// specify the package
package userinterface;

// system imports
import javafx.scene.paint.Color;

// project imports
import impresario.IModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** The class containing the Modify Article Type View  for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class ModifyArticleTypeView extends AddArticleTypeView
{

	//

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public ModifyArticleTypeView(IModel at)
	{
		super(at);
	}

	//-------------------------------------------------------------
	protected String getActionText()
	{
		return "** UPDATING ARTICLE TYPE **";
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		String bcPrefix = (String)myModel.getState("BarcodePrefix");
		if (bcPrefix != null)
		{
			barcodePrefix.setText(bcPrefix);
		}
		String desc = (String)myModel.getState("Description");
		if (desc != null)
		{
			description.setText(desc);
		}
		String alfaC = (String)myModel.getState("AlphaCode");
		if (alfaC != null)
		{
			alphaCode.setText(alfaC);
		}
		submitButton.setText("Update"); //fix submitbutton
		ImageView icon = new ImageView(new Image("/images/savecolor.png"));
		icon.setFitHeight(15);
		icon.setFitWidth(15);
		submitButton.setGraphic(icon);
	}

	public void clearValues(){

	}
}

//---------------------------------------------------------------
//	Revision History:
//


