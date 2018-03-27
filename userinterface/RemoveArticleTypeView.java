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
public class RemoveArticleTypeView extends AddArticleTypeView
{

	//

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public RemoveArticleTypeView(IModel at)
	{
		super(at);
	}

	//-------------------------------------------------------------
	protected String getActionText()
	{
		return "** REMOVING ARTICLE TYPE **";
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
                
                barcodePrefix.setDisable(true);
                alphaCode.setDisable(true);
                description.setDisable(true);
                actionText.setFill(Color.PALEVIOLETRED);
                prompt.setText("Selected Article Type Information:");
                submitButton.setText("Remove"); //fix submitbutton
                ImageView icon = new ImageView(new Image("/images/trashcolor.png"));
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


