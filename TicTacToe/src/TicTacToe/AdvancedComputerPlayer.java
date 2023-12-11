package TicTacToe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;

public class AdvancedComputerPlayer extends Player {
	private int[] seq = new int[4];
	
	public AdvancedComputerPlayer(Game g) {
		super(g);
		isHuman = false;
		
		for(int i = 0; i < 4; i++) {
			seq[i] = -1;
		}
	}
	public void pickSpace() {
		int max = 0;
		Board grid = game.getGrid();
		int m = grid.getM();
		int n = grid.getN();
		int k = grid.getK();
		int[] nums = grid.getNums();
		for(int i = 4; i >= 0; i--) {
			if(nums[i] != 0) {
				max = i;
				break;
			}
		}
		Random rand = new Random();
		int u = 0;
		int v = 0;
		int y = 0;
		int x = rand.nextInt(nums[max]) + 1;
		while(x > 0) {
			JButton button = grid.getButton(y % m, y / n);
			if((int) button.getClientProperty("val") == max) {
				u = y % m;
				v = y / n;
				x--;
			}
			y++;
		}
		seq = buildSequence(grid, m, n, k, u, v);
		ActionListener[] B = grid.getButton(u, v).getActionListeners();
		B[0].actionPerformed(new ActionEvent(grid.getButton(u, v), ActionEvent.ACTION_PERFORMED, null));
		nums[(int) grid.getButton(u, v).getClientProperty("val")]--;
		grid.getButton(u, v).putClientProperty("val", -1);
	}
	
	@Override
	public void takeTurn() {
		Board grid = game.getGrid();
		boolean intact = true;
		int i;
		int j;
		if(seq[2] > seq[0]) {
			i = 1;
		}
		else {
			i = -1;
		}
		if(seq[3] > seq[1]) {
			j = 1;
		}
		else {
			j = -1;
		}
		if(seq[0] == -1) {
			pickSpace();
			return;
		}
		else {
			int x = seq[0];
			int y = seq[1];
			boolean atEnd = false;
			while(!atEnd) {
				if(grid.getButton(x, y).getText().equals("X")) {
					intact = false;
					break;
				}
				if(x == seq[2] && y == seq[3]) {
					atEnd = true;
				}
				x += i;
				y += j; 
			}
		}
		if(!intact) {
			pickSpace();
			return;
		}
		else {	
			for(int u = 0; u < grid.getK(); u++) {
				JButton button = grid.getButton(seq[0] + i * u, seq[1] + j * u);
				ActionListener[] B = button.getActionListeners();
				if(B.length > 0) {
					int[] nums = grid.getNums();
					B[0].actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
					nums[(int) button.getClientProperty("val")]--;
					button.putClientProperty("val", -1);
				}
			}
		}
	}
	public int[] buildCheck(Board grid, int m, int n, int k, int x, int y, int i, int j) {
		int x1 = -1;
		int x2 = -1;
		int y1 = -1;
		int y2 = -1;
		int u = 1;
		while(u <= k) {
			if(0 <= x + u * i && x + u * i <= m - 1 && u != k) {
				if (0 <= y + u * j && y + u * j <= n - 1) {
					if((int) grid.getButton(x + u * i, y + u * j).getClientProperty("val") != 0) {
						u++;
						continue;
					}
				}
			}
			x1 = x + u * i - i;
			y1 = y + u * j - j;
			break;
		}
		for(int v = 1; v <= k - u + 1; v++) {
			if(0 <= x + v * -i && x + v * -i <= m - 1 && v != k - u + 1) {
				if(0 <= y + v * -j && y + v * -j <= n - 1) {
					if((int) grid.getButton(x + v * -i, y + v * -j).getClientProperty("val") != 0) {
						continue;
					}
				}
			}
			x2 = x + v * -i + i;
			y2 = y + v * -j + j;
			break;
		}
		if(Math.abs(x1 - x2) + 1 >= k || Math.abs(y1 - y2) + 1 >= k) {
			int[] b = {x1, x2, y1, y2};
			return b;
		}
		int[] c = {-1};
		return c;
	}
	
	public int[] buildSequence(Board grid, int m, int n, int k, int x, int y) {
		int[] b = buildCheck(grid, m, n, k, x, y, 1, 0);
		if(b[0] > -1) {
			return b;
		}
		b = buildCheck(grid, m, n, k, x, y, 0, 1);
		if(b[0] > -1) {
			return b;
		}
		b = buildCheck(grid, m, n, k, x, y, 1, 1);
		if(b[0] > -1) {
			return b;
		}
		b = buildCheck(grid, m, n, k, x, y, 1, -1);
		if(b[0] > -1) {
			return b;
		}
		int[] c = {-1, -1, -1, -1};
		return c;
	}
}
