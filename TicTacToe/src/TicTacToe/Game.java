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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Game extends JFrame {
	private int player = 0;
	private JPanel contentPane;
	
	private ActionListener gridListener;
	private int turns = 0;
	
	private Timer t;
	private int xTime = 0;
	private int oTime = 0;
	private int min = 0;
	private int sec = 0;
	
	public Game(JFrame frame, int inTime) {
		xTime = inTime;
		oTime = inTime;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1000, 750);
		setLocationRelativeTo(null);
		setTitle("TicTacToe - Game");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		GridBagLayout gblContentPane = new GridBagLayout();
		int sideWidth = getWidth() / 4 - 15;
		int gridWidth = getWidth() / 6;
		gblContentPane.columnWidths = new int[]{sideWidth, gridWidth, gridWidth, gridWidth, sideWidth};
		int[] height = new int[4];
		Arrays.fill(height, gridWidth);
		gblContentPane.rowHeights = height;
		contentPane.setLayout(gblContentPane);
		
		JLabel msg = new JLabel("Player X's Turn");
		msg.setFont(new Font("Arial", Font.BOLD, getHeight() / 20));
		GridBagConstraints gbcMessage = new GridBagConstraints();
		gbcMessage.gridx = 1;
		gbcMessage.gridwidth = 3;
		gbcMessage.gridy = 3;
		contentPane.add(msg, gbcMessage);
		
		JButton[][] A = new JButton[3][3];
		GridBagConstraints gbcGridButton = new GridBagConstraints();
		gbcGridButton.fill = GridBagConstraints.BOTH;
		gridListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				turns++;
				if (player == 0) {
					button.setText("X");
					button.setForeground(Color.RED);
					msg.setText("Player O's Turn");
				}
				else {
					button.setText("O");
					button.setForeground(Color.BLUE);
					msg.setText("Player X's Turn");
				}
				button.setFont(new Font("Arial", Font.BOLD, button.getWidth() / 2));
				button.removeActionListener(this);
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
						msg.setText("DRAW!");
						t.stop();
						gameOver(frame, msg.getText());
					}
				} while (false);
				player = player * (-1) + 1;
				if(0 < win) {
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
					msg.setText("Player " + button.getText() + " Wins!");
					t.stop();
					gameOver(frame, msg.getText());
				}
			}
		};
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
		JPanel timePanel = new JPanel();
		timePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gblTime = new GridBagLayout();
		gblTime.columnWidths = new int[] {sideWidth};
		gblTime.rowHeights = new int[] {0, 0, 0, 0, 0};
		timePanel.setLayout(gblTime);
		
		GridBagConstraints gbcLabel = new GridBagConstraints();
		Font font = new Font("Arial", Font.BOLD, getHeight() / 20);
		JLabel xLabel = new JLabel("Player X");
		xLabel.setForeground(Color.RED);
		xLabel.setFont(font);
		gbcLabel.gridy = 0;
		gbcLabel.insets = new Insets(0, 0, 10, 0);
		timePanel.add(xLabel, gbcLabel);
		
		JLabel oLabel = new JLabel("Player O");
		oLabel.setForeground(Color.BLUE);
		oLabel.setFont(font);
		gbcLabel.gridy = 2;
		gbcLabel.insets = new Insets(getHeight() / 20, 0, 10, 0);
		timePanel.add(oLabel, gbcLabel);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		Font timerFont = new Font("Arial", Font.PLAIN, getHeight() / 24);
		JLabel xTimer = new JLabel(String.format("%02d:%02d", xTime / 60, xTime % 60));
		xTimer.setHorizontalAlignment(JLabel.HORIZONTAL);
		xTimer.setFont(timerFont);
		xTimer.setBorder(border);
		
		JLabel oTimer = new JLabel(String.format("%02d:%02d", oTime / 60, oTime % 60));
		oTimer.setHorizontalAlignment(JLabel.HORIZONTAL);
		oTimer.setFont(timerFont);
		oTimer.setBorder(border);
		t = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							ActionListener[] B = A[i][j].getActionListeners();
							if(B.length > 0) {
								A[i][j].removeActionListener(B[0]);
							}
						}
					}
					String winner;
					if(player == 0) {
						winner = "O";
					}
					else {
						winner = "X";
					}
					msg.setText("TIME'S UP: Player " + winner + " wins!");
				}
			}
		});
		GridBagConstraints gbcTimer = new GridBagConstraints();
		gbcTimer.fill = GridBagConstraints.HORIZONTAL;
		gbcTimer.gridy = 1;
		timePanel.add(xTimer, gbcTimer);
		gbcTimer.gridy = 3;
		timePanel.add(oTimer, gbcTimer);
		t.start();
		
		JButton button = new JButton("FORFEIT");
		button.setFont(new Font("Arial", Font.BOLD, getHeight() / 26));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String winner = "X";
				if(player == 0) {
					winner = "O";
				}
				msg.setText("FORFEIT: Player " + winner + " wins!");
				t.stop();
				gameOver(frame, msg.getText());
			}
		});
		gbcTimer.gridy = 4;
		gbcTimer.insets = new Insets(getHeight() / 12, 0, 0, 0);
		timePanel.add(button, gbcTimer);
		
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.anchor = GridBagConstraints.NORTH;
		gbcPanel.gridx = 4;
		gbcPanel.gridy = 0;
		gbcPanel.gridheight = 4;
		contentPane.add(timePanel, gbcPanel);
	}
	public void gameOver(JFrame frame, String conclusion) {
		Object[] option={"Back to Menu","Close"};
        int n = JOptionPane.showOptionDialog(this, conclusion,"Game Over", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
        if(n == 0) {
            dispose();
            frame.setVisible(true);
        }
        else {
            dispose();
        }
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
}
