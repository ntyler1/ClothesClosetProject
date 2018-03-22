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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Properties;

// project imports
import impresario.IModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Gender;
import model.ArticleType;
import model.ColorX;
import model.ArticleTypeCollection;
import model.ColorCollection;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

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
		String bar = (String)myModel.getState("Barcode");
		if (bar != null)
		{
			barcode.setText(bar);
		}
		Gender gen = (Gender)myModel.getState("Gender");
		if (gen != null)
		{
			gender.setValue(gen);
		}
		ArticleType article = (ArticleType) myModel.getState("ArticleType");
		if (article != null)
		{
			articleType.setValue(article);
		}
		ColorX col1 = (ColorX)myModel.getState("Color1");
		if (col1 != null)
		{
			color1.setValue(col1);
		}
		ColorX col2 = (ColorX)myModel.getState("Color2");
		if (col2 != null)
		{
			color2.setValue(col2);
		}
		String sz = (String)myModel.getState("Size");
		if (sz != null)
		{
			size.setValue(sz);
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
		String donorP = (String)myModel.getState("DonorPhoneNumber");
		if (donorP != null)
		{
			donorPhone.setText(donorP);
		}
		String donorMail = (String)myModel.getState("DonorEmail");
		if (donorMail != null)
		{
			donorEmail.setText(donorMail);
		}
		
                actionText.setFill(Color.LIGHTBLUE);
                submitButton.setText("Update"); //fix submitbutton
                ImageView icon = new ImageView(new Image("/images/savecolor.png"));
                icon.setFitHeight(15);
                icon.setFitWidth(15);
                submitButton.setGraphic(icon);
	}

}