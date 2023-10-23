package TicTacToe;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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

public class Game extends JFrame {
	private JFrame frame;
	private int xTime;
	private int oTime;
	private boolean test;
	
	private int player = 0;
	private int min = 0;
	private int sec = 0;
	private int turns = 0;
	
	private JLabel msg;
	private JPanel contentPane;
	private JButton[][] A;
	private ActionListener gridListener;
	
	private JButton forfeitBtn;
	private JLabel xLabel;
	private JLabel oLabel;
	private JLabel xTimer;
	private JLabel oTimer;
	private JPanel timePanel;
	private Timer t;
	
	private JOptionPane optPane;
	private JDialog dialog;
	
	public Game(JFrame inFrame, int inTime, boolean inTest) {
		frame = inFrame;
		xTime = inTime;
		oTime = inTime;
		test = inTest;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1000, 750);
		setLocationRelativeTo(null);
		setTitle("TicTacToe - Game");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		GridBagLayout gblContentPane = new GridBagLayout();
		int sideWidth = createGBL(gblContentPane);
		contentPane.setLayout(gblContentPane);
		
		msg = new JLabel("Player X's Turn");
		msg.setFont(new Font("Arial", Font.BOLD, getHeight() / 20));
		GridBagConstraints gbcMessage = new GridBagConstraints();
		gbcMessage.gridx = 1;
		gbcMessage.gridwidth = 3;
		gbcMessage.gridy = 3;
		contentPane.add(msg, gbcMessage);
		
		A = new JButton[3][3];
		gridListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				checkWin(button);
			}
		};
		initiateGrid();
		
		timePanel = new JPanel();
		timePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gblTime = new GridBagLayout();
		gblTime.columnWidths = new int[] {sideWidth};
		gblTime.rowHeights = new int[] {0, 0, 0, 0, 0};
		timePanel.setLayout(gblTime);
		
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
		t = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iterateTimer();
			}
		});
		GridBagConstraints gbcTimer = new GridBagConstraints();
		gbcTimer.fill = GridBagConstraints.HORIZONTAL;
		gbcTimer.gridy = 1;
		timePanel.add(xTimer, gbcTimer);
		gbcTimer.gridy = 3;
		timePanel.add(oTimer, gbcTimer);
		t.start();
		
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
		
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.anchor = GridBagConstraints.NORTH;
		gbcPanel.gridx = 4;
		gbcPanel.gridy = 0;
		gbcPanel.gridheight = 4;
		contentPane.add(timePanel, gbcPanel);
	}
	
	public int getPlayer() {
		return player;
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
	public JButton[][] getBtnArray() {
		return A;
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
		return t;
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
	
	public int check(JButton[][] A, int x, int y, int i, int j, int p) {
		if(0 <= x + i && x + i <= A.length - 1) {
			if (0 <= y + j && y + j <= A[x + i].length - 1) {
				String s;
				if(p == 0) {
					s = "X";
				}
				else {
					s = "O";
				}
				if(s.equals(A[x + i][y + j].getText())) {
					if (i > 0) { i++; }
					else if (i < 0) { i--; }
					if (j > 0) { j++; }
					else if (j < 0) { j--; }

					return 1 + check(A, x, y, i, j, p);
				}
			}
		}
		return 0;
	}
	
	public void checkWin(JButton button) {
		mark(button);
		turns++;
		
		int x = (int) button.getClientProperty("x");
		int y = (int) button.getClientProperty("y");
		
		int win = 0;
		int val1 = check(A, x, y, -1, -1, player);
		int val2 = check(A, x, y, 1, 1, player);
		
		do {
			if(3 <= val1 + val2 + 1) {
				win = 1;
				break;
			}
			val1 = check(A, x, y, -1, 0, player);
			val2 = check(A, x, y, 1, 0, player);
			if(3 <= val1 + val2 + 1) {
				win = 2;
				break;
			}
			val1 = check(A, x, y, 0, 1, player);
			val2 = check(A, x, y, 0, -1, player);
			if(3 <= val1 + val2 + 1) {
				win = 3;
				break;
			}
			val1 = check(A, x, y, -1, 1, player);
			val2 = check(A, x, y, 1, -1, player);
			if(3 <= val1 + val2 + 1) {
				win = 4;
				break;
			}
			if(turns == 9) {
				end(null);
				return;
			}
		} while (false);
		switchTurn();
		if(0 < win) {
			highlight(win, x, y, val1);
			end("Player ");
		}
	}
	public int createGBL(GridBagLayout gblContentPane) {
		int sideWidth = getWidth() / 4 - 15;
		int gridWidth = getWidth() / 6;
		
		gblContentPane.columnWidths = new int[]{sideWidth, gridWidth, gridWidth, gridWidth, sideWidth};
		
		int[] height = new int[4];
		Arrays.fill(height, gridWidth);
		gblContentPane.rowHeights = height;
		
		return sideWidth;
	}
	
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
		msg.setText(s);
		t.stop();
		if(!test) {
			gameOver(frame, msg.getText());
		}
	}
	
	public void gameOver(JFrame frame, String conclusion) {
		Object[] option={"Back to Menu","Close"};
		optPane = new JOptionPane(conclusion, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION,  null, option, option[0]);
		dialog = optPane.createDialog(this, "Game Over");
		dialog.setVisible(true);
		String s = (String) optPane.getValue();
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
	
	public void highlight(int win, int x, int y, int val1) {
		switch(win) {
		case 1:
			for(int i = 0; i < 3; i++) {
				A[x - val1 + i][y - val1 + i].setBackground(Color.BLACK);
				A[x - val1 + i][y - val1 + i].setForeground(Color.WHITE);
			}
			break;
		case 2:
			for(int i = 0; i < 3; i++) {
				A[x - val1 + i][y].setBackground(Color.BLACK);
				A[x - val1 + i][y].setForeground(Color.WHITE);
			}
			break;
		case 3:
			for(int i = 0; i < 3; i++) {
				A[x][y + val1 - i].setBackground(Color.BLACK);
				A[x][y + val1 - i].setForeground(Color.WHITE);
			}
			break;
		case 4:
			for(int i = 0; i < 3; i++) {
				A[x - val1 + i][y + val1 - i].setBackground(Color.BLACK);
				A[x - val1 + i][y + val1 - i].setForeground(Color.WHITE);
			}
			break;
		}
	}
	
	public void initiateGrid() {
		GridBagConstraints gbcGridButton = new GridBagConstraints();
		gbcGridButton.fill = GridBagConstraints.BOTH;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				A[i][j] = new JButton();
				A[i][j].putClientProperty("x", i);
				A[i][j].putClientProperty("y", j);
				A[i][j].addActionListener(gridListener);
				gbcGridButton.gridx = i + 1;
				gbcGridButton.gridy = j;
				contentPane.add(A[i][j], gbcGridButton);
			}
		}
	}
	
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
	
	public void mark(JButton button) {
		if (player == 0) {
			button.setText("X");
			button.setForeground(Color.RED);
		}
		else {
			button.setText("O");
			button.setForeground(Color.BLUE);
		}
		
		button.setFont(new Font("Arial", Font.BOLD, button.getWidth() / 2));
		button.removeActionListener(gridListener);
	}
	
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
	}
}
