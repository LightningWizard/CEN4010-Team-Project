package TicTacToe;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;

public class BasicComputerPlayer extends Player {
	public BasicComputerPlayer(Game g) {
		super(g);
		isHuman = false;
	}
	@Override
	public void takeTurn() {
		Board grid = game.getGrid();
		int m = grid.getM();
		int n = grid.getN();
		
		Random rand = new Random();
		int x = rand.nextInt(m);
		int y = rand.nextInt(n);
		
		outerloop:
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < n; j++) {
				ActionListener[] B = grid.getButton(x % m, y % n).getActionListeners();
				if(B.length > 0) {
					B[0].actionPerformed(new ActionEvent(grid.getButton(x % m, y % n), ActionEvent.ACTION_PERFORMED, null));
					break outerloop;
				}
				y++;
			}
			x++;
		}
		
		if(game.getTurns() == 1) {
			System.out.println("Test");
			grid = game.getGrid();
			JButton button = grid.getButton(x % m, y % n);
			button.setText("O");
			button.setForeground(Color.BLUE);
			button.setFont(new Font("Arial", Font.BOLD, game.getWidth() / (4 * Math.max(m, n))));
		}
	}
}
