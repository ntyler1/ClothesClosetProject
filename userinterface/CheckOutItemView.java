// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Properties;

// project imports
import impresario.IModel;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/** The class containing the Modify Clothing View  for the Professional Clothes
 *  Closet application 
 */
//==============================================================
public class CheckOutItemView extends SearchInventoryView
{

	//----------------------------------------------------------
	public CheckOutItemView(IModel ci)
	{
		super(ci);
	}

	//-------------------------------------------------------------
	protected String getActionText()
	{
		return "** CHECKOUT CLOTHING ITEM **";
	}

	// Create the title container
	//-------------------------------------------------------------

	// Create the status log field
	//-------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------

	/**
	 * Update method
	 */
	//---------------------------------------------------------
}

//---------------------------------------------------------------
//	Revision History:
//


