package ie.gmit.sw;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class AppWindow {
	private JFrame frame;
	
	public AppWindow(){
		//Create a window for the application
		frame = new JFrame();
		frame.setTitle("B.Sc. in Software Development - GMIT");
		frame.setSize(500, 300);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		
        //The file panel will contain the file chooser
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEADING));
        top.setBorder(new javax.swing.border.TitledBorder("Select Jar File to Analyse"));
        top.setPreferredSize(new java.awt.Dimension(400, 100));
        top.setMaximumSize(new java.awt.Dimension(400, 100));
        top.setMinimumSize(new java.awt.Dimension(400, 100));
        
        final JTextField txtFile =  new JTextField(20);
        txtFile.setPreferredSize(new java.awt.Dimension(100, 30));
        txtFile.setMaximumSize(new java.awt.Dimension(100, 30));
        txtFile.setMargin(new java.awt.Insets(2, 2, 2, 2));
        txtFile.setMinimumSize(new java.awt.Dimension(100, 30));
		
		JButton btnChooseFile = new JButton("Browse...");
		btnChooseFile.setToolTipText("Select Jar File to Analyse");
        btnChooseFile.setPreferredSize(new java.awt.Dimension(90, 30));
        btnChooseFile.setMaximumSize(new java.awt.Dimension(90, 30));
        btnChooseFile.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnChooseFile.setMinimumSize(new java.awt.Dimension(90, 30));
		btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
        		JFileChooser fc = new JFileChooser("./");
        		int returnVal = fc.showOpenDialog(frame);
            	if (returnVal == JFileChooser.APPROVE_OPTION) {
                	File file = fc.getSelectedFile().getAbsoluteFile();
                	String name = file.getAbsolutePath();
                	//check that its  jar file
                	if(name.endsWith(".jar") == true){
                		txtFile.setText(name);
                	}else{
                		System.out.println("Not a jar file");
                	}
            	}
			}
        });
		
        top.add(txtFile);
        top.add(btnChooseFile);
        frame.getContentPane().add(top); //Add the panel to the window
        
        
        //A separate panel for the programme output
        JPanel mid = new JPanel(new FlowLayout(FlowLayout.LEADING));
        mid.setBorder(new BevelBorder(BevelBorder.RAISED));
        mid.setPreferredSize(new java.awt.Dimension(500, 300));
        mid.setMaximumSize(new java.awt.Dimension(500, 300));
        mid.setMinimumSize(new java.awt.Dimension(500, 300));
        
		
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setPreferredSize(new java.awt.Dimension(400, 50));
        bottom.setMaximumSize(new java.awt.Dimension(400, 50));
        bottom.setMinimumSize(new java.awt.Dimension(400, 50));
        
        JButton btnDialog = new JButton("Analyse"); //Create Table button
        btnDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	//pass the jar to calculate
            	Calculate calc = new Calculate(txtFile.getText());

            	AppSummary as =  new AppSummary(frame, true);
                
            	// create a table model
                TypeSummaryTableModel tm = as.getTableModel();

                // add to table model to display it
                tm.setTableData(calc.getMetricData());

                //make it visible to the end user
                as.setVisible(true);
			}
        });
        
        JButton btnQuit = new JButton("Quit"); //Create Quit button
        btnQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.exit(0);
			}
        });
        bottom.add(btnDialog);
        bottom.add(btnQuit);

        frame.getContentPane().add(bottom);       
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new AppWindow();
	}
}