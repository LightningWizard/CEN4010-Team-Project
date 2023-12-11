// Made by Logan Milbrandt, Jose Riquelme, and Viorel Ortiz
package TicTacToe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

//TicTacToe Game class that extends JFrame
public class Game extends JFrame {
 private JFrame frame; // Main frame of the game
 private int xTime; // Time for player X
 private int oTime; // Time for player O
 private boolean test; // Flag for testing purposes

 // Game variables
 private int player; // Current player (0 for X, 1 for O)
 private int m;
 private int n;
 private int min = 0; // Minutes on the timer
 private int sec = 0; // Seconds on the timer
 private int turns = 0; // Number of turns taken in the game

 // UI components
 private JLabel msg; // Message label indicating the current player's turn
 private BackgroundPanel contentPane; // Main content panel
 private Board grid; // 2D array of buttons representing the Tic-Tac-Toe grid

 private JButton forfeitBtn; // Button for forfeiting the game
 private JLabel xLabel; // Label for player X
 private JLabel oLabel; // Label for player O
 private JLabel xTimer; // Timer label for player X
 private JLabel oTimer; // Timer label for player O
 private JPanel timePanel; // Panel for displaying timers
 private Timer gameTimer; // Timer for game countdown

 private JOptionPane optPane; // Option pane for displaying messages
 private JDialog dialog; // Dialog for displaying messages
 
 private Player[] players = new Player[2];
 private int gameType;

 // Constructor for the Game class
	public Game(JFrame inFrame, int inTime, int m, int n, int k, String mode, String firstPlayer, String theme, boolean inTest) {
		frame = inFrame;
		xTime = inTime;
		oTime = inTime;
		this.m = m;
		this.n = n;
		test = inTest;
		
		players[0] = new HumanPlayer(this);
		if(mode.equals("Player VS Player")) {
			players[1] = new HumanPlayer(this);
			gameType = 0;
		}
		else if(mode.equals("Player VS AI (Basic)")) {
			players[1] = new BasicComputerPlayer(this);
			gameType = 1;
		}
		else {
			players[1] = new AdvancedComputerPlayer(this);
			gameType = 2;
		}
		if(firstPlayer.equals("Go First")) {
			player = 0;
		}
		else {
			player = 1;
		}
		
		String image = "Winter.jpg";
		if(theme.equals("Summer")) {
			image = "Summer.jpg";
		}
		
		// Set up the main frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1000, 750);
		setLocationRelativeTo(null);
		setTitle("TicTacToe - Game");
		contentPane = new BackgroundPanel(image);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// Set up the GridBagLayout for the main content pane
		setContentPane(contentPane);
		
		GridBagLayout gblContentPane = new GridBagLayout();
		
		gblContentPane.columnWidths = new int[] {getWidth() / 4 - 15, getWidth() / 2, getWidth() / 4 - 15};
		gblContentPane.rowHeights = new int[] {4 * getHeight() / 6, getHeight() / 6};
		contentPane.setLayout(gblContentPane);
		
		JPanel gridPane = new JPanel();
		gridPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gblGridPane = new GridBagLayout();
		int max = Math.max(m, n);
		int[] width = new int[m];
		Arrays.fill(width, getWidth() / (2 * max));
		gblGridPane.columnWidths = width;
		int[] height = new int[n];
		Arrays.fill(height, getWidth() / (2 * max));
		gblGridPane.rowHeights = height;
		gridPane.setPreferredSize(new Dimension(getWidth() / 2, getWidth() /2));
		gridPane.setMaximumSize(new Dimension(getWidth() / 2, getWidth() /2));
		gridPane.setLayout(gblGridPane);
		
		grid = new Board(this, m, n, k);
		grid.initiateGrid(gridPane, m, n, getWidth());
		
		GridBagConstraints gbcPanes = new GridBagConstraints();
		gbcPanes.gridx = 1;
		gbcPanes.gridy = 0;
		contentPane.add(gridPane, gbcPanes);
		
		// Initialize and add the message label
		msg = new JLabel("Player X's Turn");
		msg.setFont(new Font("Arial", Font.BOLD, getHeight() / 20));
		GridBagConstraints gbcMessage = new GridBagConstraints();
		gbcMessage.gridx = 1;
		gbcMessage.gridy = 1;
		contentPane.add(msg, gbcMessage);
		
		// Set up the panel for displaying timers
		timePanel = new JPanel();
		timePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gblTime = new GridBagLayout();
		gblTime.columnWidths = new int[] {getWidth() / 4 - 15};
		gblTime.rowHeights = new int[] {0, 0, 0, 0, 0};
		timePanel.setLayout(gblTime);
		
		// Initialize and add labels for player X and player O
		GridBagConstraints gbcLabel = new GridBagConstraints();
		Font font = new Font("Arial", Font.BOLD, getHeight() / 20);
		xLabel = new JLabel("Player X");
		xLabel.setForeground(Color.RED);
		xLabel.setFont(font);
		gbcLabel.gridy = 0;
		gbcLabel.insets = new Insets(0, 0, 10, 0);
		timePanel.add(xLabel, gbcLabel);
		
		oLabel = new JLabel("Player O");
		oLabel.setForeground(Color.BLUE);
		oLabel.setFont(font);
		gbcLabel.gridy = 2;
		gbcLabel.insets = new Insets(getHeight() / 20, 0, 10, 0);
		timePanel.add(oLabel, gbcLabel);
		
		// Set up timer labels for player X and player O
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		Font timerFont = new Font("Arial", Font.PLAIN, getHeight() / 24);
		xTimer = new JLabel(String.format("%02d:%02d", xTime / 60, xTime % 60));
		xTimer.setHorizontalAlignment(JLabel.HORIZONTAL);
		xTimer.setFont(timerFont);
		xTimer.setBorder(border);
		
		
		oTimer = new JLabel(String.format("%02d:%02d", oTime / 60, oTime % 60));
		oTimer.setHorizontalAlignment(JLabel.HORIZONTAL);
		oTimer.setFont(timerFont);
		oTimer.setBorder(border);
		gameTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iterateTimer();
			}
		});
		
		// Add timer labels to the time panel
		GridBagConstraints gbcTimer = new GridBagConstraints();
		gbcTimer.fill = GridBagConstraints.HORIZONTAL;
		gbcTimer.gridy = 1;
		timePanel.add(xTimer, gbcTimer);
		gbcTimer.gridy = 3;
		timePanel.add(oTimer, gbcTimer);
		gameTimer.start();
		
		// Set up forfeit button and add it to the time panel
		forfeitBtn = new JButton("FORFEIT");
		forfeitBtn.setFont(new Font("Arial", Font.BOLD, getHeight() / 26));
		forfeitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				end("FORFEIT: Player ");
			}
		});
		gbcTimer.gridy = 4;
		gbcTimer.insets = new Insets(getHeight() / 12, 0, 0, 0);
		timePanel.add(forfeitBtn, gbcTimer);
		
		// Add the time panel to the main content pane
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.anchor = GridBagConstraints.NORTH;
		gbcPanel.gridx = 2;
		gbcPanel.gridy = 0;
		gbcPanel.gridheight = 4;
		contentPane.add(timePanel, gbcPanel);

		System.out.println(player);
		players[player].takeTurn();
	}
	
	// Getters and setters for various properties
	
	public Board getGrid() {
		return grid;
	}
	public int getGameType() {
		return gameType;
	}
	public int getPlayer() {
		return player;
	}
	public Player[] getPlayers() {
		return players;
	}
	public int getTurns() {
		return turns;
	}
	public JPanel contentPane() {
		return contentPane;
	}
	public JLabel getMSG() {
		return msg;
	}
	public JLabel getXLabel() {
		return xLabel;
	}
	public JLabel getOLabel() {
		return oLabel;
	}
	public JLabel getXTimer() {
		return xTimer;
	}
	public JLabel getOTimer() {
		return oTimer;
	}
	public JButton getForfeitBtn() {
		return forfeitBtn;
	}
	public Timer getTimer() {
		return gameTimer;
	}
	public JOptionPane getOptPane() {
		return optPane;
	}
	public JDialog getDialog() {
		return dialog;
	}
	public void setPlayer(int p) {
		player = p;
	}
	
	// Method to end the game and display the result
	public void end(String s) {
		if(s != null) {
			String winner = "X";
			if(player == 0) {
				winner = "O";
			}
			s = s + winner + " Wins!";
		}
		else {
			s = "DRAW!";
		}
		// End the game and display the result (win, draw, or forfeit)
		msg.setText(s);
		gameTimer.stop();
		if(!test) {
			gameOver(frame, msg.getText());
		}
	}
	
	// Method to handle game over scenarios
	public void gameOver(JFrame frame, String conclusion) {
		Object[] option={"Back to Menu","Close"};
		optPane = new JOptionPane(conclusion, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION,  null, option, option[0]);
		dialog = optPane.createDialog(this, "Game Over");
		dialog.setVisible(true);
		String s = (String) optPane.getValue();
		// Display game over dialog with options to go back to the menu or close the game
		if(s != null) {
	        if(s.equals(option[0])) {
	            dispose();
	            frame.setVisible(true);
	        }
	        else {
	            dispose();
	            frame.dispose();
	        }
		}
		else {
			dispose();
            frame.dispose();
		}
	}
	
	// Method to iterate the game timer
	public void iterateTimer() {
		if(xTime > 0 && player == 0) {
			xTime -= 1;
			sec = xTime % 60;
			min = xTime / 60;
			xTimer.setText(String.format("%02d:%02d", min, sec));
		}
		else if(oTime > 0 && player == 1) {
			oTime -= 1;
			sec = oTime % 60;
			min = oTime / 60;
			oTimer.setText(String.format("%02d:%02d", min, sec));
		}
		else {
			end("TIME'S UP: Player ");
		}
	}
	
	public void iterateTurns() {
		turns++;
	}
	
	// Method to switch the turn between players
	public void switchTurn() {
		String s;
		if(player == 0) {
			s = "O";
		}
		else {
			s = "X";
		}
		msg.setText("Player " + s + "'s Turn");
		player = player * (-1) + 1;
		players[player].takeTurn();
	}
}
