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
import javax.swing.plaf.metal.MetalCheckBoxIcon;
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
		int[] height = {getHeight() / 4, getHeight() / 4, getHeight() / 4};
		int[] width = {3 * getWidth() / 4};
		gblContentPane.columnWidths = width;
		gblContentPane.rowHeights = height;
		contentPane.setLayout(gblContentPane);
		
		// Labels for minutes and seconds
		
		JPanel topPane = new JPanel();
		GridBagLayout gblTopPane = new GridBagLayout();
		gblTopPane.columnWidths = new int[] {getWidth() / 8, getWidth() / 4, getWidth() / 4, getWidth() / 8};
		gblTopPane.rowHeights = new int[] {getHeight() / 20, getHeight() / 5};
		topPane.setLayout(gblTopPane);
		GridBagConstraints gbcPanes = new GridBagConstraints();
		gbcPanes.gridx = 0;
		gbcPanes.gridy = 0;
		contentPane.add(topPane, gbcPanes);
		
		JLabel minLabel = new JLabel("MINUTES");
		GridBagConstraints constraints = new GridBagConstraints();
		minLabel.setHorizontalAlignment(JLabel.CENTER);
		minLabel.setVerticalAlignment(JLabel.BOTTOM);
		minLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		constraints.insets = new Insets(0, 0, 10, 5);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 0;
		topPane.add(minLabel, constraints);
		
		JLabel secLabel = new JLabel("SECONDS");
		secLabel.setHorizontalAlignment(JLabel.CENTER);
		secLabel.setVerticalAlignment(JLabel.BOTTOM);
		secLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		constraints.gridx = 2;
		topPane.add(secLabel, constraints);
		
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
		constraints.gridx = 1;
		constraints.gridy = 1;
		topPane.add(minField, constraints);
		
		secField = new JTextField("00");
		secDocument = (AbstractDocument) secField.getDocument();
		secDocument.setDocumentFilter(filter);
		secField.setHorizontalAlignment(JTextField.CENTER);
		secField.setFont(new Font("Arial", Font.BOLD, getHeight() / 8));
		constraints.gridx = 2;
		topPane.add(secField, constraints);
		
		JPanel midPane = new JPanel();
		GridBagLayout gblMidPane = new GridBagLayout();
		gblMidPane.columnWidths = new int[] {getWidth() / 4, getWidth() / 4, getWidth() / 4};
		gblMidPane.rowHeights = new int[] {getHeight() / 20, getHeight() / 5};
		midPane.setLayout(gblMidPane);
		gbcPanes.gridy = 1;
		contentPane.add(midPane, gbcPanes);
		
		JLabel mLabel = new JLabel("COLUMNS");
		mLabel.setHorizontalAlignment(JLabel.CENTER);
		mLabel.setVerticalAlignment(JLabel.BOTTOM);
		mLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		constraints.gridx = 0;
		constraints.gridy = 0;
		midPane.add(mLabel, constraints);
		
		JLabel nLabel = new JLabel("ROWS");
		nLabel.setHorizontalAlignment(JLabel.CENTER);
		nLabel.setVerticalAlignment(JLabel.BOTTOM);
		nLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		constraints.gridx = 1;
		midPane.add(nLabel, constraints);
		
		JLabel kLabel = new JLabel("WIN");
		kLabel.setHorizontalAlignment(JLabel.CENTER);
		kLabel.setVerticalAlignment(JLabel.BOTTOM);
		kLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		constraints.gridx = 2;
		midPane.add(kLabel, constraints);
		
		JTextField mField = new JTextField("03");
		AbstractDocument mDocument = (AbstractDocument) mField.getDocument();
		mDocument.setDocumentFilter(filter);
		mField.setHorizontalAlignment(JTextField.CENTER);
		mField.setFont(new Font("Arial", Font.BOLD, getHeight() / 8));
		constraints.gridx = 0;
		constraints.gridy = 1;
		midPane.add(mField, constraints);
		
		JTextField nField = new JTextField("03");
		AbstractDocument nDocument = (AbstractDocument) nField.getDocument();
		nDocument.setDocumentFilter(filter);
		nField.setHorizontalAlignment(JTextField.CENTER);
		nField.setFont(new Font("Arial", Font.BOLD, getHeight() / 8));
		constraints.gridx = 1;
		midPane.add(nField, constraints);
		
		JTextField kField = new JTextField("03");
		AbstractDocument kDocument = (AbstractDocument) kField.getDocument();
		kDocument.setDocumentFilter(filter);
		kField.setHorizontalAlignment(JTextField.CENTER);
		kField.setFont(new Font("Arial", Font.BOLD, getHeight() / 8));
		constraints.gridx = 2;
		midPane.add(kField, constraints);
		
		JPanel botPane = new JPanel();
		GridBagLayout gblBotPane = new GridBagLayout();
		gblBotPane.columnWidths = new int[] {getWidth() / 8, getWidth() / 4, getWidth() / 4, getWidth() / 8};
		gblBotPane.rowHeights = new int[] {getHeight() / 10, getHeight() / 10};
		botPane.setLayout(gblBotPane);
		gbcPanes.gridy = 2;
		contentPane.add(botPane, gbcPanes);
		
		JCheckBox compPlayer = new JCheckBox("2 Players?");
		compPlayer.setIcon(new MetalCheckBoxIcon() {
			protected int getControlSize() {return getWidth() / 24;}
		});
		compPlayer.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		constraints.gridx = 1;
		constraints.gridy = 0;
		botPane.add(compPlayer, constraints);
		
		JPanel subPane = new JPanel();
		GridBagLayout gblSubPane = new GridBagLayout();
		gblSubPane.columnWidths = new int[] {getWidth() / 24, 5 * getWidth() / 24};
		gblSubPane.rowHeights = new int[] {getHeight() / 10};
		subPane.setLayout(gblSubPane);
		constraints.gridy = 1;
		botPane.add(subPane, constraints);
		
		String[] players = {"X", "O"};
		JComboBox<String> firstPlayerBox = new JComboBox<String>(players);
		firstPlayerBox.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		firstPlayerBox.setRenderer(listRenderer);
		constraints.gridx = 0;
		constraints.gridy = 0;
		subPane.add(firstPlayerBox, constraints);
		
		JLabel firstPlayerLabel = new JLabel("1st Player");
		firstPlayerLabel.setFont(new Font("Arial", Font.BOLD, getHeight() / 24));
		constraints.gridx = 1;
		subPane.add(firstPlayerLabel, constraints);
		
		// Button for starting the game
		JButton button = new JButton("START");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int m = Integer.parseInt(mField.getText());
				int n = Integer.parseInt(nField.getText());
				int k = Integer.parseInt(kField.getText());
				if (m < 15 && n < 60 && (k <= m || k <= n)) {
					setVisible(false);
					int time = calcTime(minField.getText(), secField.getText());
					Game gFrame = new Game((JFrame) SwingUtilities.getRoot(button), time, m, n, k, compPlayer.isSelected(), (String) firstPlayerBox.getSelectedItem(), false);
					gFrame.setVisible(true);
				}
				else if (m >= 15){
					JOptionPane optPane = new JOptionPane("Number of columns must be less than 15.", JOptionPane.WARNING_MESSAGE, JOptionPane.NO_OPTION,  null, new Object[0], null);
					JDialog dialog = optPane.createDialog(button, "Warning");
					dialog.setVisible(true);
				}
				else if (n >= 60) {
					JOptionPane optPane = new JOptionPane("Number of rows must be less than 60.", JOptionPane.WARNING_MESSAGE, JOptionPane.NO_OPTION,  null, new Object[0], null);
					JDialog dialog = optPane.createDialog(button, "Warning");
					dialog.setVisible(true);
				}
				else {
					JOptionPane optPane = new JOptionPane("WIN value must be less than or equal to either the number of columns or rows.", JOptionPane.WARNING_MESSAGE, JOptionPane.NO_OPTION,  null, new Object[0], null);
					JDialog dialog = optPane.createDialog(button, "Warning");
					dialog.setVisible(true);
				}
			}
		});
		button.setFont(new Font("Arial", Font.BOLD, getHeight() / 8));
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridheight = 2;
		botPane.add(button, constraints);
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