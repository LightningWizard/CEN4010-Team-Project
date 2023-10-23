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

public class Main extends JFrame {
	private JPanel contentPane;
	private JTextField minField;
	private JTextField secField;
	private AbstractDocument minDocument;
	private AbstractDocument secDocument;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 750, 750);
		setLocationRelativeTo(null);
		setTitle("TicTacToe - Menu");
		contentPane = new JPanel();
		
		setContentPane(contentPane);
		GridBagLayout gblContentPane = new GridBagLayout();
		int[] height = {getHeight() / 20, getHeight() / 3, getHeight() / 3};
		int[] width = {getWidth() / 3, getWidth() / 3};
		gblContentPane.columnWidths = width;
		gblContentPane.rowHeights = height;
		contentPane.setLayout(gblContentPane);
		
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
		
		JButton button = new JButton("START");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				int time = calcTime(minField.getText(), secField.getText());
				Game gFrame = new Game((JFrame) SwingUtilities.getRoot(button), time, false);
				gFrame.setVisible(true);
			}
		});
		button.setFont(new Font("Arial", Font.BOLD, getHeight() / 8));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		contentPane.add(button, constraints);
	}
	
	public int calcTime(String min, String sec) {
		return 60 * Integer.parseInt(min) + Integer.parseInt(sec);
	}
	
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
