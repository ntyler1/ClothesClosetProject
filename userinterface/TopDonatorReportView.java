// specify the package
package userinterface;

// system imports
import javafx.scene.paint.Color;

// project imports
import impresario.IModel;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Properties;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Inventory;
import model.InventoryCollection;
import utilities.GlobalVariables;

//==============================================================
public class TopDonatorReportView extends AvailableInventoryView
{


	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public TopDonatorReportView(IModel td)
	{
		super(td);
	}

	//-------------------------------------------------------------
	protected String getActionText()
	{
		return "** TOP DONATORS REPORT **";
	}

	//-------------------------------------------------------------
	protected void populateFields()
        {
            tableOfInventory.getColumns().clear();
            
            TableColumn donorLastNameColumn = new TableColumn("Donor Last Name") ;
		donorLastNameColumn.setMinWidth(50);
		donorLastNameColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("donorLastName"));

		TableColumn donorFirstNameColumn = new TableColumn("Donor First Name") ;
		donorFirstNameColumn.setMinWidth(60);
		donorFirstNameColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("donorFirstName"));

		TableColumn donorEmailColumn = new TableColumn("Donor Email") ;
		donorEmailColumn.setMaxWidth(200);
		donorEmailColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("donorEmail"));
                
		TableColumn donorPhoneColumn = new TableColumn("Donor Phone") ;
		donorPhoneColumn.setMinWidth(60);
		donorPhoneColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("donorPhone"));
                
                TableColumn itemsColumn = new TableColumn("Items Donated") ;
		itemsColumn.setMinWidth(60);
		itemsColumn.setCellValueFactory(
				new PropertyValueFactory<InventoryTableModel, String>("status"));
                
            tableOfInventory.getColumns().addAll(donorFirstNameColumn, donorLastNameColumn, donorPhoneColumn, donorEmailColumn,  itemsColumn);
            tableOfInventory.setMaxHeight(575);
            tableOfInventory.setMaxWidth(575);
            getEntryTableModelValues();
	}
        
        protected void getEntryTableModelValues()
	{
		ObservableList<InventoryTableModel> tableData = FXCollections.observableArrayList();
		try
		{
			InventoryCollection inventoryCollection = 
					(InventoryCollection)myModel.getState("InventoryList");

			Vector entryList = (Vector)inventoryCollection.getState("Inventory");

			if (entryList.size() > 0)
			{
				Enumeration entries = entryList.elements();
				while (entries.hasMoreElements() == true)
				{
					Inventory nextAT = (Inventory)entries.nextElement();
					Vector<String> view = nextAT.getEntryListView();

					// add this list entry to the list
					InventoryTableModel nextTableRowData = new InventoryTableModel(view);
					tableData.add(nextTableRowData);

				}
				if(entryList.size() == 1)
					foundText.setText(entryList.size()+" Donator Record Found!");
				else 
					foundText.setText(entryList.size()+" Donator Records Found!");

				foundText.setFill(Color.LIGHTGREEN);
			}
			else
			{
				foundText.setText("No Donator Records Found!");
				foundText.setFill(Color.FIREBRICK);
			}

			tableOfInventory.setItems(tableData);
		}
		catch (Exception e) {//SQLException e) {

		}
	}
        
        protected void writeToFile(String fName)
        {
            Vector allColumnNames = new Vector();

            try
            {
                FileWriter outFile = new FileWriter(fName);
                PrintWriter out = new PrintWriter(outFile);
                InventoryCollection inventoryCollection = (InventoryCollection)myModel.getState("InventoryList");
                Vector entryList = (Vector)inventoryCollection.getState("Inventory");

                if ((entryList == null) || (entryList.size() == 0))
                    return;

                allColumnNames.add("Barcode");
		allColumnNames.add("Gender");
		allColumnNames.add("Size");
		allColumnNames.add("ArticleType");
		allColumnNames.add("Color1");
		allColumnNames.add("Color2");
		allColumnNames.add("Brand");
		allColumnNames.add("Notes");
		allColumnNames.add("Status");
		allColumnNames.add("DonorLastName");
		allColumnNames.add("DonorFirstName");
		allColumnNames.add("DonorPhone");
		allColumnNames.add("DonorEmail");
		allColumnNames.add("ReceiverNetid");
		allColumnNames.add("ReceiverLastName");
		allColumnNames.add("ReceiverFirstName");
		allColumnNames.add("DateDonated");
		allColumnNames.add("DateTaken");

                String line = "Items Donated, Donor Last Name, Donor First Name, Donor Phone, Donor Email";

                out.println(line);

                for (int k = 0; k < entryList.size(); k++)
                {
                    String valuesLine = "";
                    Inventory nextI = (Inventory)entryList.elementAt(k);
                    Vector<String> nextRow = nextI.getEntryListView();

                    for (int j = 0; j < allColumnNames.size(); j++)
                    {
                            String nextValue = nextRow.elementAt(j);
                            System.out.println(nextValue);
                            if(nextValue != null)
                                valuesLine += nextValue + ", ";
                    }
                    System.out.println(valuesLine);
                    out.println(valuesLine);
                }

                // Also print the shift count and filter type
                out.println("\nTotal number of donator records: " + entryList.size());

                // Finally, print the time-stamp
                DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                DateFormat timeFormat = new SimpleDateFormat("hh:mm aaa");
                Date date = new Date();
                String timeStamp = dateFormat.format(date) + " " +
                                   timeFormat.format(date);

                out.println("Top Donator Report created on " + timeStamp);

                out.close();

                // Acknowledge successful completion to user with JOptionPane
                JOptionPane.showMessageDialog(null,
                        "Report data saved successfully to selected file");
            }
            catch (FileNotFoundException e)
            {
                JOptionPane.showMessageDialog(null, "Could not access file to save: "
                        + fName, "Save Error", JOptionPane.ERROR_MESSAGE );
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Error in saving to file: "
                        + e.toString(), "Save Error", JOptionPane.ERROR_MESSAGE );

            }
    }
        
        public void updateState(String key, Object value)
	{
		clearErrorMessage();

		if (key.equals("TransactionError") == true)
		{
			String val = (String)value;
			if (val.startsWith("ERR") == true)
			{
				displayErrorMessage(val);
			}
			else
			{
				displayMessage(val);
			}
		}
	}
        
        public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}
}
