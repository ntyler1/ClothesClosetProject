// specify the package
package userinterface;

// system imports
import javafx.scene.paint.Color;

// project imports
import impresario.IModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** The class containing the Remove Color View  for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class RemoveColorView extends AddColorView
{

	//

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public RemoveColorView(IModel c)
	{
		super(c);
	}

	//-------------------------------------------------------------
	protected String getActionText()
	{
		return "** REMOVING COLOR **";
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		String code = (String)myModel.getState("BarcodePrefix");
		if (code != null)
		{
			barcodePrefix.setText(code);
                        barcodePrefix.setDisable(true);
                }
                String desc = (String)myModel.getState("Description");
		if (desc != null)
		{
			description.setText(desc);
                        description.setDisable(true);

		}
		String alfaC = (String)myModel.getState("AlphaCode");
		if (alfaC != null)
		{
			alphaCode.setText(alfaC);
                        alphaCode.setDisable(true);
		}
                actionText.setFill(Color.PALEVIOLETRED);
                prompt.setText("Selected Color Information:");
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
