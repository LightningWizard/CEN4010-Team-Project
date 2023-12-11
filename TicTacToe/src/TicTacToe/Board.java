package TicTacToe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Board {
	private Game game;
	private ActionListener gridListener;
	private JButton[][] grid;
	private int[] nums;
	private int m;
	private int n;
	private int k;
	
	public Board(Game game, int m, int n, int k) {
		this.game = game;
		this.m = m;
		this.n = n;
		this.k = k;
		grid = new JButton[m][n];
		gridListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				int win = checkWin(button, m, n, k);
				game.switchTurn();
			}
		};
	}
	public int[] initialize() {
		int[] b = {0, 0, 0, 0, 0};
		int z = 0;
		if(m >= k) {
			z++;
		}
		if(n >= k) {
			z++;
		}
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < n; j++) {
				grid[i][j].putClientProperty("val", z);
				
				int x;
				int y;
				if(i > j) {
					x = i - j;
					y = 0;
				}
				else {
					x = 0;
					y = j - i;
				}
				if((k <= m - x) && (k <= n - y)) {
					grid[i][j].putClientProperty("val", (int) grid[i][j].getClientProperty("val") + 1);
				}
				if(i > n - j - 1) {
					x = i + j - n + 1;
					y = n - 1;
				}
				else {
					x = 0;
					y = j + i;
				}
				if((k <= m - x) && (k <= y + 1)) {
					grid[i][j].putClientProperty("val", (int) grid[i][j].getClientProperty("val") + 1);
				}
				b[(int) grid[i][j].getClientProperty("val")]++;
			}
		}
		return b;
	}
	public void initiateGrid(JPanel gridPane, int m, int n, int width) {
		GridBagConstraints gbcGridButton = new GridBagConstraints();

		int max = Math.max(m, n);
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < n; j++) {
				grid[i][j] = new JButton();
				grid[i][j].addActionListener(gridListener);
				grid[i][j].putClientProperty("x", i);
				grid[i][j].putClientProperty("y", j);
				grid[i][j].setMaximumSize(new Dimension(width / (2 * max), width / (2 * max)));
				grid[i][j].setPreferredSize(new Dimension(width / (2 * max), width / (2 * max)));
				gbcGridButton.gridx = i;
				gbcGridButton.gridy = j;
				gridPane.add(grid[i][j], gbcGridButton);
			}
		}
		nums = initialize();
	}
	// Method to recursively check for a winning condition
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
		
	// Method to check if a player has won the game
	public int checkWin(JButton button, int m, int n, int k) {
		mark(button);
		//turns++;
		game.iterateTurns();
		int player = game.getPlayer();
		int turns = game.getTurns();
		
		int x = (int) button.getClientProperty("x");
		int y = (int) button.getClientProperty("y");
		
		int win = 0;
		int val1 = check(grid, x, y, -1, -1, player);
		int val2 = check(grid, x, y, 1, 1, player);
		
		do {
			if(k <= val1 + val2 + 1) {
				win = 1;
				break;
			}
			//Perhaps you can use an else if statement to check for a near win. Return the node needed to make a win.
			val1 = check(grid, x, y, -1, 0, player);
			val2 = check(grid, x, y, 1, 0, player);
			if(k <= val1 + val2 + 1) {
				win = 2;
				break;
			}
			val1 = check(grid, x, y, 0, 1, player);
			val2 = check(grid, x, y, 0, -1, player);
			if(k <= val1 + val2 + 1) {
				win = 3;
				break;
			}
			val1 = check(grid, x, y, -1, 1, player);
			val2 = check(grid, x, y, 1, -1, player);
			if(k <= val1 + val2 + 1) {
				win = 4;
				break;
			}
			if(turns == m * n) {
				game.end(null);
				return win;
			}
		} while (false);
		if(0 < win) {
			highlight(win, x, y, val1, k);
			game.end("Player ");
		}
		return win;
	}
	
	public JButton getButton(int x, int y) {
		return grid[x][y];
	}
	public int getK() {
		return k;
	}
	public int getM() {
		return m;
	}
	public int getN() {
		return n;
	}
	public int[] getNums() {
		return nums;
	}
	// Method to highlight the winning combination on the grid
	public void highlight(int win, int x, int y, int val1, int k) {
		// Highlight the winning combination on the grid
		switch(win) {
		case 1:
			for(int i = 0; i < k; i++) {
				grid[x - val1 + i][y - val1 + i].setBackground(Color.BLACK);
				grid[x - val1 + i][y - val1 + i].setForeground(Color.WHITE);
			}
			break;
		case 2:
			for(int i = 0; i < k; i++) {
				grid[x - val1 + i][y].setBackground(Color.BLACK);
				grid[x - val1 + i][y].setForeground(Color.WHITE);
			}
			break;
		case 3:
			for(int i = 0; i < k; i++) {
				grid[x][y + val1 - i].setBackground(Color.BLACK);
				grid[x][y + val1 - i].setForeground(Color.WHITE);
			}
			break;
		case 4:
			for(int i = 0; i < k; i++) {
				grid[x - val1 + i][y + val1 - i].setBackground(Color.BLACK);
				grid[x - val1 + i][y + val1 - i].setForeground(Color.WHITE);
			}
			break;
		}
	}
	
	// Method to mark the selected button with the player's symbol
	public void mark(JButton button) {
		// Mark the selected button with the player's symbol (X or O)
		if (game.getPlayer() == 0) {
			button.setText("X");
			button.setForeground(Color.RED);
		}
		else {
			button.setText("O");
			button.setForeground(Color.BLUE);
		}
		button.setFont(new Font("Arial", Font.BOLD, button.getHeight() / 2));
		button.removeActionListener(gridListener);
	}
}
