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
		System.out.println("Test 6: Initialized: " + seq[0] + ", " + seq[1] + ", " + seq[2] + ", " + seq[3]);
		ActionListener[] B = grid.getButton(u, v).getActionListeners();
		B[0].actionPerformed(new ActionEvent(grid.getButton(u, v), ActionEvent.ACTION_PERFORMED, null));
	}
	
	//public static void main(String[] args) {
		/*
		int max = 0;
		int size = 0;
		for(int i = 4; i >= 0; i--) {
			if(num[i] > max) {
				max = i + 1;
				size = num[i];
				break;
			}
		}
		int[] b = new int[size];
		int h = 0;
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < n; j++) {
				if(a[i][j] == max) {
					b[h] = max;
					h++;
				}
			}
		}
		*/
	//}
	@Override
	public void takeTurn() {
		System.out.println("Test 1: Beginning");
		Board grid = game.getGrid();
		boolean intact = true;
		int lowY;
		int highY;
		/*
		if(seq[1] < seq[3]) {
			lowY = 1;
			highY = 3;
		}
		else {
			lowY = 3;
			highY = 1;
		}
		*/
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
			System.out.println("Test 2: Not initialized");
			pickSpace();
			return;
		}
		else {
			System.out.println("Test 3: Initialized: " + seq[0] + ", " + seq[1] + ", " + seq[2] + ", " + seq[3]);
			/*
			for(int x = seq[0]; x <= seq[2]; x + i) {
				for(int j = seq[lowY]; j <= seq[highY]; j++) {
					if(grid.getButton(i, j).getText().equals("X")) {
						intact = false;
						break;
					}
				}
			}
			*/
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
			System.out.println("Test 4: Not intact");
			pickSpace();
			return;
		}
		else {	
			System.out.println("Test 5: Intact --> seq[0]: " + seq[0] + " seq[2]: " + seq[2]);
			/*
			outerloop:
			for(int i = seq[0]; i <= seq[2]; i++) {
				for(int j = seq[lowY]; j <= seq[highY]; j++) {
					ActionListener[] B = grid.getButton(i, j).getActionListeners();
					if(B.length > 0) {
						B[0].actionPerformed(new ActionEvent(grid.getButton(i, j), ActionEvent.ACTION_PERFORMED, null));
						break outerloop;
					}
				}
			}
			*/
			for(int u = 0; u < grid.getK(); u++) {
				ActionListener[] B = grid.getButton(seq[0] + i * u, seq[1] + j * u).getActionListeners();
				if(B.length > 0) {
					B[0].actionPerformed(new ActionEvent(grid.getButton(seq[0] + i * u, seq[1] + j * u), ActionEvent.ACTION_PERFORMED, null));
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
	
	//Pass type of iteration +1, 0 and -1, 0; +1, +1 and -1, -1; 0, +1 and 0, -1; +1, -1 and -1, +1
	//Pass if excuse exists
	//Iterate while keeping count unless you find a non mark while excuse is false, i == m, j == n, i == 0, or j == 0
	//count(int[] a
	public int[] masterCheck(int[][] a, int m, int n, int k, int x, int y) {
		//could use the value returned by the checks to mark the ends of a sequence, and then search within that sequence
		//for the position of the 1 remaining space.
		int[] pos = {-1, -1};
		int val1 = check(a, m, n, x, y, 1, 0, true);
		int val2 = check(a, m, n, x, y, -1, 0, false);
		//remainder is between x, y and x + val1, y
		if(k <= val1 + val2 + 1) {
			for(int i = 1; i <= val1; i++) {
				if(a[x + i][y] != 0) {
					pos[0] = x + i;
					pos[1] = y;
					return pos;
				}
			}
		}
		val1 = check(a, m, n, x, y, 1, 0, false);
		val2 = check(a, m, n, x, y, -1, 0, true);
		//remainder is between x, y and x - val2, y
		if(k <= val1 + val2 + 1) {
			for(int i = 1; i <= val2; i++) {
				if(a[x - i][y] != 0) {
					pos[0] = x - i;
					pos[1] = y;
					return pos;
				}
			}
		}
		val1 = check(a, m, n, x, y, 0, 1, true);
		val2 = check(a, m, n, x, y, 0, -1, false);
		//remainder is between x, y and x, y + val1
		if(k <= val1 + val2 + 1) {
			for(int i = 1; i <= val1; i++) {
				if(a[x][y + i] != 0) {
					pos[0] = x;
					pos[1] = y + i;
					return pos;
				}
			}
		}
		val1 = check(a, m, n, x, y, 0, 1, false);
		val2 = check(a, m, n, x, y, 0, -1, true);
		//remainder is between x, y and x, y - val2
		if(k <= val1 + val2 + 1) {
			for(int i = 1; i <= val2; i++) {
				if(a[x][y - i] != 0) {
					pos[0] = x;
					pos[1] = y - i;
					return pos;
				}
			}
		}
		val1 = check(a, m, n, x, y, 1, 1, true);
		val2 = check(a, m, n, x, y, -1, -1, false);
		//remainder is between x, y and x + val1, y + val1
		if(k <= val1 + val2 + 1) {
			for(int i = 1; i <= val1; i++) {
				if(a[x + i][y + i] != 0) {
					pos[0] = x + i;
					pos[1] = y + i;
					return pos;
				}
			}
		}
		val1 = check(a, m, n, x, y, 1, 1, false);
		val2 = check(a, m, n, x, y, -1, -1, true);
		//remainder is between x, y and x - val2, y - val2
		if(k <= val1 + val2 + 1) {
			for(int i = 1; i <= val2; i++) {
				if(a[x - i][y - i] != 0) {
					pos[0] = x - i;
					pos[1] = y - i;
					return pos;
				}
			}
		}
		val1 = check(a, m, n, x, y, 1, -1, true);
		val2 = check(a, m, n, x, y, -1, 1, false);
		//remainder is between x, y and x + val1, y - val1
		if(k <= val1 + val2 + 1) {
			for(int i = 1; i <= val1; i++) {
				if(a[x + i][y - i] != 0) {
					pos[0] = x + i;
					pos[1] = y - i;
					return pos;
				}
			}
		}
		val1 = check(a, m, n, x, y, 1, -1, false);
		val2 = check(a, m, n, x, y, -1, 1, true);
		//remainder is between x, y and x - val2, y + val2
		if(k <= val1 + val2 + 1) {
			for(int i = 1; i <= val2; i++) {
				if(a[x - i][y + i] != 0) {
					pos[0] = x - i;
					pos[1] = y + i;
					return pos;
				}
			}
		}
		return pos;
	}
	public static int check(int[][] a, int m, int n, int x, int y, int i, int j, boolean excuse) {
		if(0 <= x + i && x + i <= m - 1) {
			if (0 <= y + j && y + j <= n - 1) {
				if(a[x + i][y + j] == 0) {
					return 1 + check(a, m, n, x + i, y + j, i, j, excuse);
				}
				else if(excuse) {
					excuse = false;
					return check(a, m, n, x + i, y + j, i, j, excuse);
				}
			}
		}
		return 0;
	}
	//The modifications to values that must be made when a mark is made by the human player.
	public static void mark(int[][] a, int[] b, int m, int n, int k, int x, int y) {
		b[a[x][y]]--;
		a[x][y] = 0;
		b[a[x][y]]++;
		int stopXOne = x + k + 1;
		int stopXTwo = x - k - 1;
		for(int i = 1; i <= k; i++) {
			if(x + i == m || a[x + i][y] == 0) {
				stopXOne = x + i;
				break;
			}
		}
		for(int i = 1; i <= k; i++) {
			if(x - i == -1 || a[x - i][y] == 0) {
				stopXTwo = x - i;
				break;
			}
		}
		if((stopXOne - stopXTwo) > k) {
			if(stopXOne - x <= k) {
				int i = x + 1;
				while(i < stopXOne) {
					b[a[i][y]]--;
					a[i][y]--;
					b[a[i][y]]++;
					i++;
				}
			}
			if(x - stopXTwo <= k) {
				int i = x - 1;
				while(i > stopXTwo) {
					b[a[i][y]]--;
					a[i][y]--;
					b[a[i][y]]++;
					i--;
				}
			}
		}
		int stopYOne = y + k + 1;
		int stopYTwo = y - k - 1;
		for(int i = 1; i <= k; i++) {
			if(y + i == n || a[x][y + i] == 0) {
				stopYOne = y + i;
				break;
			}
		}
		for(int i = 1; i <= k; i++) {
			if(y - i == -1 || a[x][y - i] == 0) {
				stopYTwo = y - i;
				break;
			}
		}
		if((stopYOne - stopYTwo) > k) {
			if(stopYOne - y <= k) {
				int i = y + 1;
				while(i < stopYOne) {
					b[a[x][i]]--;
					a[x][i]--;
					b[a[x][i]]++;
			  		i++;
			  	}
			}
			if(y - stopYTwo <= k) {
				int i = y - 1;
				while(i > stopYTwo) {
					b[a[x][i]]--;
					a[x][i]--;
					b[a[x][i]]++;
			  		i--;
			 	}
			}
		}
		stopXOne = x + k + 1;
		stopXTwo = x - k - 1;
		stopYOne = y + k + 1;
		stopYTwo = y - k - 1;
		for(int i = 1; i <= k; i++) {
			if(x + i == m || y + i == n || a[x + i][y + i] == 0) {
				stopXOne = x + i;
				stopYOne = y + i;
				break;
			}
		}
		for(int i = 1; i <= k; i++) {
			if(x - i == -1 || y - i == -1 || a[x - i][y - i] == 0) {
				stopXTwo = x - i;
				stopYTwo = y - i;
				break;
			}
		}
		if((stopXOne - stopXTwo) > k && (stopYOne - stopYTwo) > k) {
			if(stopXOne - x <= k && stopYOne - y <= k) {
				int i = x + 1;
			  	int j = y + 1;
			  	//&& i < x + k && j < y + k && i > x && j > y
			  	while(i < stopXOne && j < stopYOne) {
			  		b[a[i][j]]--;
			  		a[i][j]--;
			  		b[a[i][j]]++;
			  		i++;
			  		j++;
			  	}
			}
			if(x - stopXTwo <= k && y - stopYTwo <= k) {
			  	int i = x - 1;
			  	int j = y - 1;
			  	//&& i > x - k && j > y - k && i < x && j < y
			  	while(i > stopXTwo && j > stopYTwo) {
			  		b[a[i][j]]--;
			  		a[i][j]--;
			  		b[a[i][j]]++;
			  		i--;
			  		j--;
			  	}
			}
		}
		stopXOne = x + k + 1;
		stopXTwo = x - k - 1;
		stopYOne = y + k + 1;
		stopYTwo = y - k - 1;
		for(int i = 1; i <= k; i++) {
			if(x + i == m || y - i == -1 || a[x + i][y - i] == 0) {
				stopXOne = x + i;
				stopYOne = y - i;
				break;
			}
		}
		for(int i = 1; i <= k; i++) {
			if(x - i == -1 || y + i == n || a[x - i][y + i] == 0) {
				stopXTwo = x - i;
				stopYTwo = y + i;
				break;
			}
		}
		if((stopXOne - stopXTwo) > k && (stopYTwo - stopYOne) > k) {
			if(stopXOne - x <= k && stopYTwo - y <= k) {
				int i = x + 1;
			  	int j = y - 1;
			  	while(i < stopXOne && j < stopYTwo) {
			  		b[a[i][j]]--;
			  		a[i][j]--;
			  		b[a[i][j]]++;
			  		i++;
			  		j--;
			  	}
			}
			if(x - stopXTwo <= k && y - stopYOne <= k) {
			  	int i = x - 1;
			  	int j = y + 1;
			  	while(i > stopXTwo && j > stopYOne) {
			  		b[a[i][j]]--;
			  		a[i][j]--;
			  		b[a[i][j]]++;
			  		i--;
			  		j++;
			  	}
			}
		}
	}
}
