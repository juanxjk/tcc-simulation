package cm_awt;

/*
	This simple extension of the java.awt.Frame class
	contains all the elements necessary to act as the
	main window of an application.
 */

import java.awt.*;

public class nbody_awt extends java.awt.Frame 
{
    globals g;
    trajectory_panel trajectory_panel1;
	options_nbody options_nbody;
    
    
	public nbody_awt()
	{
		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		
		//{{INIT_CONTROLS
		setLayout(new BorderLayout(0,0));
		setSize(497,399);
		setVisible(false);
		openFileDialog1.setMode(FileDialog.LOAD);
		openFileDialog1.setTitle("Open");
		//$$ openFileDialog1.move(180,408);
		SaveFileDialog.setMode(FileDialog.SAVE);
		SaveFileDialog.setTitle("Save");
		//$$ SaveFileDialog.move(132,420);
		setTitle("AWT Application");
		//}}
		
		//{{INIT_MENUS
		menu1.setLabel("File");
		menu1.add(newMenuItem);
		newMenuItem.setEnabled(false);
		newMenuItem.setLabel("New");
		newMenuItem.setShortcut(new MenuShortcut(java.awt.event.KeyEvent.VK_N,false));
		menu1.add(openMenuItem);
		openMenuItem.setLabel("Open...");
		openMenuItem.setShortcut(new MenuShortcut(java.awt.event.KeyEvent.VK_O,false));
		menu1.add(saveMenuItem);
		saveMenuItem.setEnabled(false);
		saveMenuItem.setLabel("Save");
		saveMenuItem.setShortcut(new MenuShortcut(java.awt.event.KeyEvent.VK_S,false));
		menu1.add(saveAsMenuItem);
		saveAsMenuItem.setLabel("Save As...");
		menu1.add(SaveasTextmenuItem);
		SaveasTextmenuItem.setLabel("Save as Text...");
		menu1.add(separatorMenuItem);
		separatorMenuItem.setLabel("-");
		menu1.add(exitMenuItem);
		exitMenuItem.setLabel("Exit");
		mainMenuBar.add(menu1);
		menu2.setLabel("Edit");
		menu2.add(cutMenuItem);
		cutMenuItem.setEnabled(false);
		cutMenuItem.setLabel("Cut");
		cutMenuItem.setShortcut(new MenuShortcut(java.awt.event.KeyEvent.VK_X,false));
		menu2.add(copyMenuItem);
		copyMenuItem.setEnabled(false);
		copyMenuItem.setLabel("Copy");
		copyMenuItem.setShortcut(new MenuShortcut(java.awt.event.KeyEvent.VK_C,false));
		menu2.add(pasteMenuItem);
		pasteMenuItem.setEnabled(false);
		pasteMenuItem.setLabel("Paste");
		pasteMenuItem.setShortcut(new MenuShortcut(java.awt.event.KeyEvent.VK_V,false));
		menu2.add(MenuItem_options);
		MenuItem_options.setLabel("Options");
		mainMenuBar.add(menu2);
		menu3.setLabel("Help");
		menu3.add(aboutMenuItem);
		aboutMenuItem.setLabel("About...");
		mainMenuBar.add(menu3);
		//$$ mainMenuBar.move(48,408);
		setMenuBar(mainMenuBar);
		//}}
		
		//{{REGISTER_LISTENERS
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		openMenuItem.addActionListener(lSymAction);
		exitMenuItem.addActionListener(lSymAction);
		aboutMenuItem.addActionListener(lSymAction);
		saveAsMenuItem.addActionListener(lSymAction);
		MenuItem_options.addActionListener(lSymAction);
		SaveasTextmenuItem.addActionListener(lSymAction);
		//}}

		// my stuff
		//g=new globals();
		trajectory_panel1 = new trajectory_panel();
		add(trajectory_panel1);
    	options_nbody = new options_nbody(this,true);

	}
	
	public nbody_awt(String title)
	{
		this();
		setTitle(title);
	}
	
    /**
     * Shows or hides the component depending on the boolean flag b.
     * @param b  if true, show the component; otherwise, hide the component.
     * @see java.awt.Component#isVisible
     */
    public void setVisible(boolean b)
	{
		if(b)
		{
			setLocation(50, 50);
		}	
		super.setVisible(b);
	}
	
	static public void main(String args[])
	{
		try
		{
			//Create a new instance of our application's frame, and make it visible.
    		(new nbody_awt()).setVisible(true);
		}
		catch (Throwable t)
		{
			System.err.println(t);
			t.printStackTrace();
			//Ensure the application exits with an error condition.
			System.exit(1);
		}
	}
	
	public void addNotify()
	{
		// Record the size of the window prior to calling parents addNotify.
		Dimension d = getSize();
		
		super.addNotify();
	
		if (fComponentsAdjusted)
			return;
	
		// Adjust components according to the insets
		setSize(getInsets().left + getInsets().right + d.width, getInsets().top + getInsets().bottom + d.height);
		Component components[] = getComponents();
		for (int i = 0; i < components.length; i++)
		{
			Point p = components[i].getLocation();
			p.translate(getInsets().left, getInsets().top);
			components[i].setLocation(p);
		}
		fComponentsAdjusted = true;
	}
	
	// Used for addNotify check.
	boolean fComponentsAdjusted = false;
	
	//{{DECLARE_CONTROLS
	java.awt.FileDialog openFileDialog1 = new java.awt.FileDialog(this);
	java.awt.FileDialog SaveFileDialog = new java.awt.FileDialog(this);
	//}}
	
	//{{DECLARE_MENUS
	java.awt.MenuBar mainMenuBar = new java.awt.MenuBar();
	java.awt.Menu menu1 = new java.awt.Menu();
	java.awt.MenuItem newMenuItem = new java.awt.MenuItem();
	java.awt.MenuItem openMenuItem = new java.awt.MenuItem();
	java.awt.MenuItem saveMenuItem = new java.awt.MenuItem();
	java.awt.MenuItem saveAsMenuItem = new java.awt.MenuItem();
	java.awt.MenuItem SaveasTextmenuItem = new java.awt.MenuItem();
	java.awt.MenuItem separatorMenuItem = new java.awt.MenuItem();
	java.awt.MenuItem exitMenuItem = new java.awt.MenuItem();
	java.awt.Menu menu2 = new java.awt.Menu();
	java.awt.MenuItem cutMenuItem = new java.awt.MenuItem();
	java.awt.MenuItem copyMenuItem = new java.awt.MenuItem();
	java.awt.MenuItem pasteMenuItem = new java.awt.MenuItem();
	java.awt.MenuItem MenuItem_options = new java.awt.MenuItem();
	java.awt.Menu menu3 = new java.awt.Menu();
	java.awt.MenuItem aboutMenuItem = new java.awt.MenuItem();
	//}}
	
	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowClosed(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == nbody_awt.this)
				nbodyAwt_WindowClosed(event);
		}

		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == nbody_awt.this)
				nbody_awt_WindowClosing(event);
		}
	}
	
	void nbody_awt_WindowClosing(java.awt.event.WindowEvent event)
	{
		// to do: code goes here.
			 
		nbody_awt_WindowClosing_Interaction1(event);
	}


	void nbody_awt_WindowClosing_Interaction1(java.awt.event.WindowEvent event)
	{
	    quit_immediately();
	    
		try {
			// QuitDialog Create and show as modal
			(new QuitDialog(this, true)).setVisible(true);
		} catch (Exception e) {
		}
	}

	
	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == openMenuItem)
				openMenuItem_ActionPerformed(event);
			else if (object == aboutMenuItem)
				aboutMenuItem_ActionPerformed(event);
			else if (object == exitMenuItem)
				exitMenuItem_ActionPerformed(event);
			else if (object == saveAsMenuItem)
				saveAsMenuItem_ActionPerformed(event);
			else if (object == MenuItem_options)
				MenuItemOptions_ActionPerformed(event);
			else if (object == SaveasTextmenuItem)
				SaveasTextmenuItem_ActionPerformed(event);
		}
	}
	
	void openMenuItem_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
			 
		openMenuItem_ActionPerformed_Interaction1(event);
	}


	void openMenuItem_ActionPerformed_Interaction1(java.awt.event.ActionEvent event)
	{
		try {
			// OpenFileDialog Create and show as modal
		    int		defMode         = openFileDialog1.getMode();
		    String	defTitle        = openFileDialog1.getTitle();
		    String defDirectory     = openFileDialog1.getDirectory();
		    String defFile          = openFileDialog1.getFile();

		    openFileDialog1 = new java.awt.FileDialog(this, defTitle, defMode);
		    openFileDialog1.setDirectory(defDirectory);
		    openFileDialog1.setFile(defFile);
		    openFileDialog1.setFile("*.dat"); // mine
		    openFileDialog1.setVisible(true);
            trajectory_panel1.openFileAction(openFileDialog1.getFile());
		    
		} catch (Exception e) {
		}
	}


	void aboutMenuItem_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
			 
		aboutMenuItem_ActionPerformed_Interaction1(event);
	}


	void aboutMenuItem_ActionPerformed_Interaction1(java.awt.event.ActionEvent event)
	{
		try {
			// AboutDialog Create and show as modal
			(new AboutDialog(this, true)).setVisible(true);
		} catch (Exception e) {
		}
	}
	
	
	void exitMenuItem_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
			 
		exitMenuItem_ActionPerformed_Interaction1(event);
	}


	void exitMenuItem_ActionPerformed_Interaction1(java.awt.event.ActionEvent event)
	{
		try {
			// QuitDialog Create and show as modal
		    (new QuitDialog(this, true)).setVisible(true);
		} catch (Exception e) {
		}
	}


	void nbodyAwt_WindowClosed(java.awt.event.WindowEvent event)
	{
		// to do: code goes here.
			 
	}
	
	void quit_immediately()
	{
		try {
	        setVisible(false);    // Hide the invoking frame
	        dispose();            // Free system resources
	        //this.dispose();                  // Free system resources
		    System.exit(0);             // close the application
		} catch (Exception e) {
		}
	}

	

	void saveAsMenuItem_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
			 
		saveAsMenuItem_ActionPerformed_Interaction1(event);
	}

	void saveAsMenuItem_ActionPerformed_Interaction1(java.awt.event.ActionEvent event)
	{
		try {
			// SaveFileDialog Show the FileDialog
		    SaveFileDialog.setFile("*.dat");
			SaveFileDialog.setVisible(true);
            trajectory_panel1.saveFileAction(SaveFileDialog.getFile());
			
		} catch (java.lang.Exception e) {
		}
	}

	void MenuItemOptions_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
			 
		MenuItem_options_ActionPerformed_Interaction1(event);
	}

	void MenuItem_options_ActionPerformed_Interaction1(java.awt.event.ActionEvent event)
	{
    	options_nbody.g=trajectory_panel1.g;
		try {
    		options_nbody.setVisible(true);
		} catch (java.lang.Exception e) {
		}
		trajectory_panel1.redraw();

	}

	void SaveasTextmenuItem_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
			 
		SaveasTextmenuItem_ActionPerformed_Interaction1(event);
	}

	void SaveasTextmenuItem_ActionPerformed_Interaction1(java.awt.event.ActionEvent event)
	{
		try {
			// SaveFileDialog Show the FileDialog
		    SaveFileDialog.setFile("*.txt");
			SaveFileDialog.setVisible(true);
            trajectory_panel1.saveTextFileAction(SaveFileDialog.getFile());
			
		} catch (java.lang.Exception e) {
		}
	}
}

