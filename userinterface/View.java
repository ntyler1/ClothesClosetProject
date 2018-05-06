// tabs=4
//************************************************************
//	COPYRIGHT 2009/2015 Sandeep Mitra and Michael Steves, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package
package userinterface;

// system imports
import java.util.Properties;
import java.util.Vector;
import java.util.EventObject;
import javafx.scene.Group;

// project imports
import common.StringList;
import impresario.IView;
import impresario.IModel;
import impresario.IControl;
import impresario.ControlRegistry;
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

//==============================================================
public abstract class View extends Group
	implements IView, IControl
{
	// private data
	protected IModel myModel;
	protected ControlRegistry myRegistry;
	
	
	// GUI components
	
		
	// Class constructor
	//----------------------------------------------------------
	public View(IModel model, String classname)
	{
		myModel = model;
		
		myRegistry = new ControlRegistry(classname);
	}
	
	
	//----------------------------------------------------------
	public void setRegistry(ControlRegistry registry)
	{
		myRegistry = registry;
	}
	
	// Allow models to register for state updates
	//----------------------------------------------------------
	public void subscribe(String key,  IModel subscriber)
	{
		myRegistry.subscribe(key, subscriber);
	}
		
		
	// Allow models to unregister for state updates
	//----------------------------------------------------------
	public void unSubscribe(String key, IModel subscriber)
	{
		myRegistry.unSubscribe(key, subscriber);
	}
	
        //-------------------------------------------------------------
    protected void writeToFile(String fName)
    {

    }

    //--------------------------------------------------------------------------
    protected void saveToExcelFile()
    {
               // Put up JFileChooser
        // Retrieve full path name of file user selects
        // Create the file appropriately if it does not exist
        String reportsPath = System.getProperty("user.dir") + "\\reports";
        File reportsDir = new File(reportsPath);

        // if the directory does not exist, create it
        if (!reportsDir.exists())
        {
            reportsDir.mkdir();
        }

        JFileChooser chooser = new JFileChooser(reportsDir);
        chooser.setDialogTitle("Saving report: ");

        //JFileChooser chooser = new JFileChooser("." + File.separator +
        //        "ReportFiles");

        CSVFileFilter filter = new CSVFileFilter();

        chooser.setFileFilter(filter);

        try
        {
            String fileName = "";

            int returnVal = chooser.showSaveDialog(null);

            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = chooser.getSelectedFile();
                //selectedFile.mkdirs();
                fileName = selectedFile.getCanonicalPath();

                String tempName = fileName.toLowerCase();

                // Check if user specified the file type. If not, add the type.
                if(tempName.endsWith(".csv"))
                {
                    writeToFile(fileName);
                }
                else
                {
                    fileName += ".csv";
                    writeToFile(fileName);
                }

                Desktop desktop = null;

                // Before more Desktop API is used, first check
                // whether the API is supported by this particular
                // virtual machine (VM) on this particular host.
                if(Desktop.isDesktopSupported())
                {
                    desktop = Desktop.getDesktop();

                    if(desktop.isSupported(Desktop.Action.OPEN))
                    {

                        // Custom button text
                        Object[] options = {"Open reports folder",
                                            "Open this report",
                                            "Cancel"};

                        // Ask user what (s)he wants to do
                        int n = JOptionPane.showOptionDialog(null,
                            "What do you want to do next?",
                            "Export to Excel",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[2]);

                        if(n == JOptionPane.YES_OPTION)
                        {
                            desktop.open(reportsDir);
                        }
                        else
                        if(n == JOptionPane.NO_OPTION)
                        {
                            File reportPath = new File(fileName);
                            desktop.open(reportPath);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error in saving to file: "
                    + ex.toString());
        }
    }
}

