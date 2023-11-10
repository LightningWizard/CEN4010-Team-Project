// Made by Logan Milbrandt, Jose Riquelme, and Viorel Ortiz
package TicTacToe;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

//MainMenu class for the TicTacToe game menu
public class MainMenu extends JFrame {
	private JPanel contentPane; // Main content panel
    private JTextField minField; // Text field for entering minutes
    private JTextField secField; // Text field for entering seconds
    private AbstractDocument minDocument; // Document for minField to apply document filter
    private AbstractDocument secDocument; // Document for secField to apply document filter

    // Main method to launch the application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu(); // Create MainMenu frame
					frame.setVisible(true); // Set frame visibility to true
				} catch (Exception e) {
					e.printStackTrace(); // Print stack trace if an exception occurs
				}
			}
		});
	}
	
	// Constructor for the MainMenu class
	public MainMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        setBounds(0, 0, 750, 750); // Set frame dimensions
        setLocationRelativeTo(null); // Center the frame on the screen
        setTitle("TicTacToe - Menu"); // Set frame title
        contentPane = new JPanel(); // Initialize main content panel
		
		setContentPane(contentPane);
		GridBagLayout gblContentPane = new GridBagLayout();
		int[] height = {getHeight() / 20, getHeight() / 5, getHeight() / 20, getHeight() / 5, getHeight() / 5};
		int[] width = {getWidth() / 4, getWidth() / 4, getWidth() / 4};
		gblContentPane.columnWidths = width;
		gblContentPane.rowHeights = height;
		contentPane.setLayout(gblContentPane);
		
		// Labels for minutes and seconds
		
		JLabel minLabel = new JLabel("MINUTES");
		GridBagConstraints constraints = new GridBagConstraints();
		minLabel.setHorizontalAlignment(JLabel.CENTER);
		minLabel.setVerticalAlignment(JLabel.BOTTOM);
		minLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		constraints.insets = new Insets(0, 0, 10, 5);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		contentPane.add(minLabel, constraints);
		
		JLabel secLabel = new JLabel("SECONDS");
		secLabel.setHorizontalAlignment(JLabel.CENTER);
		secLabel.setVerticalAlignment(JLabel.BOTTOM);
		secLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		constraints.gridx = 1;
		contentPane.add(secLabel, constraints);
		
		// Document filter for text fields to allow only valid input
		DocumentFilter filter = new DocumentFilter() {
			public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
				String text = fb.getDocument().getText(0, fb.getDocument().getLength());
				text += str;
				text = text.substring(text.length() - 2, text.length());
				if(text.matches("^[0-9]?[0-9]$")) {
					super.replace(fb, 0, text.length(), text, a);
				}
				else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
			public void remove(FilterBypass fb, int offs, int length) throws BadLocationException {
				String text = fb.getDocument().getText(0, 1);
				if(length < 2) {
					super.replace(fb, 0, 2, "0" + text, null);
				}
				else {
					super.replace(fb, 0, 2, "00", null);
				}
			}
		};
		
		// Text fields for entering minutes and seconds
		minField = new JTextField("00");
		minDocument = (AbstractDocument) minField.getDocument();
		minDocument.setDocumentFilter(filter);
		minField.setHorizontalAlignment(JTextField.CENTER);
		minField.setFont(new Font("Arial", Font.BOLD, getHeight() / 8));
		constraints.gridx = 0;
		constraints.gridy = 1;
		contentPane.add(minField, constraints);
		
		secField = new JTextField("00");
		secDocument = (AbstractDocument) secField.getDocument();
		secDocument.setDocumentFilter(filter);
		secField.setHorizontalAlignment(JTextField.CENTER);
		secField.setFont(new Font("Arial", Font.BOLD, getHeight() / 8));
		constraints.gridx = 1;
		contentPane.add(secField, constraints);
		
		JLabel mLabel = new JLabel("ROWS");
		mLabel.setHorizontalAlignment(JLabel.CENTER);
		mLabel.setVerticalAlignment(JLabel.BOTTOM);
		mLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		constraints.gridx = 0;
		constraints.gridy = 2;
		contentPane.add(mLabel, constraints);
		
		JLabel nLabel = new JLabel("COLUMNS");
		nLabel.setHorizontalAlignment(JLabel.CENTER);
		nLabel.setVerticalAlignment(JLabel.BOTTOM);
		nLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		constraints.gridx = 1;
		contentPane.add(nLabel, constraints);
		
		JLabel kLabel = new JLabel("WIN");
		kLabel.setHorizontalAlignment(JLabel.CENTER);
		kLabel.setVerticalAlignment(JLabel.BOTTOM);
		kLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		constraints.gridx = 2;
		contentPane.add(kLabel, constraints);
		
		JTextField mField = new JTextField("03");
		AbstractDocument mDocument = (AbstractDocument) mField.getDocument();
		mDocument.setDocumentFilter(filter);
		mField.setHorizontalAlignment(JTextField.CENTER);
		mField.setFont(new Font("Arial", Font.BOLD, getHeight() / 8));
		constraints.gridx = 0;
		constraints.gridy = 3;
		contentPane.add(mField, constraints);
		
		JTextField nField = new JTextField("03");
		AbstractDocument nDocument = (AbstractDocument) nField.getDocument();
		nDocument.setDocumentFilter(filter);
		nField.setHorizontalAlignment(JTextField.CENTER);
		nField.setFont(new Font("Arial", Font.BOLD, getHeight() / 8));
		constraints.gridx = 1;
		contentPane.add(nField, constraints);
		
		JTextField kField = new JTextField("03");
		AbstractDocument kDocument = (AbstractDocument) kField.getDocument();
		kDocument.setDocumentFilter(filter);
		kField.setHorizontalAlignment(JTextField.CENTER);
		kField.setFont(new Font("Arial", Font.BOLD, getHeight() / 8));
		constraints.gridx = 2;
		contentPane.add(kField, constraints);
		
		// Button for starting the game
		JButton button = new JButton("START");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				int time = calcTime(minField.getText(), secField.getText());
				int m = Integer.parseInt(mField.getText());
				int n = Integer.parseInt(nField.getText());
				int k = Integer.parseInt(kField.getText());
				Game gFrame = new Game((JFrame) SwingUtilities.getRoot(button), time, m, n, k, false);
				gFrame.setVisible(true);
			}
		});
		button.setFont(new Font("Arial", Font.BOLD, getHeight() / 8));
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		contentPane.add(button, constraints);
	}
	
	// Method to calculate total time in seconds from minutes and seconds input
	public int calcTime(String min, String sec) {
		return 60 * Integer.parseInt(min) + Integer.parseInt(sec);
	}
	
	// Getter methods for accessing private variables
	
	public JTextField getMinField() {
		return minField;
	}
	
	public JTextField getSecField() {
		return secField;
	}
	
	public AbstractDocument getMinDocument() {
		return minDocument;
	}
	
	public AbstractDocument getSecDocument() {
		return secDocument;
	}
}