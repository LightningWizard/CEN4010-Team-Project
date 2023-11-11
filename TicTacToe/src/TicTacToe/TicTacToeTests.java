// Made by Logan Milbrandt, Jose Riquelme, and Viorel Ortiz
package TicTacToe;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.text.BadLocationException;

import org.junit.jupiter.api.Test;

//Class containing test methods for TicTacToe application
class TicTacToeTests {
	// Test case to check if large entry in minutes field is trimmed to last two digits
	@Test
	void testLargeEntry() {
		MainMenu testMain = new MainMenu();
		testMain.getMinField().setText("2345");
		String s = testMain.getMinField().getText();
		testMain.dispose();
		assertEquals(true, s.equals("45"));
	}
	
	// Test case to check if small entry in minutes field is padded with '0'
	@Test
	void testSmallEntry() {
		MainMenu testMain = new MainMenu();
		testMain.getMinField().setText("1");
		String s = testMain.getMinField().getText();
		testMain.dispose();
		assertEquals(true, s.equals("01"));
	}
	
	// Test case to check the remove method of DocumentFilter
	@Test
	void testRemove() throws BadLocationException {
		MainMenu testMain = new MainMenu();
		testMain.getMinField().setText("24");
		testMain.getMinDocument().remove(0, 0);
		String s = testMain.getMinField().getText();
		testMain.dispose();
		assertEquals(true, s.equals("02"));
	}
	
	// Test case to check the calcTime method in MainMenu class
	@Test
	void testCalcTime() {
		MainMenu testMain = new MainMenu();
		int time = testMain.calcTime("01", "30");
		testMain.dispose();
		assertEquals(true, time == 90);
	}
	
	// Test case to check if the game declares Player X as winner for diagonal line
	@Test
	void testCheckWinDiagonal1() {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 60, 3, 3, 3, true);
		JButton[][] A = testGame.getBtnArray();
		int i = 0;
		int j = 0;
		ActionListener[] B;
		// Iterate through diagonal line and perform actions on buttons
		while(i != 3 && j != 3) {
			B = A[i][j].getActionListeners();
			B[0].actionPerformed(new ActionEvent(A[i][j], ActionEvent.ACTION_PERFORMED, null));
			if(i != 2) {
				B = A[i + 1][j].getActionListeners();
				B[0].actionPerformed(new ActionEvent(A[i + 1][j], ActionEvent.ACTION_PERFORMED, null));
			}
			i++;
			j++;
		}
		String s = testGame.getMSG().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("Player X Wins!"));
	}
	
	// Test case to check if the game declares Player X as winner for another diagonal line
	@Test
	void testCheckWinDiagonal2() {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 60, 3, 3, 3, true);
		JButton[][] A = testGame.getBtnArray();
		int i = 0;
		int j = 2;
		ActionListener[] B;
		// Iterate through diagonal line and perform actions on buttons
		while(i != 3 && j != -1) {
			B = A[i][j].getActionListeners();
			B[0].actionPerformed(new ActionEvent(A[i][j], ActionEvent.ACTION_PERFORMED, null));
			if(i != 2) {
				B = A[i + 1][j].getActionListeners();
				B[0].actionPerformed(new ActionEvent(A[i + 1][j], ActionEvent.ACTION_PERFORMED, null));
			}
			i++;
			j--;
		}
		String s = testGame.getMSG().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("Player X Wins!"));
	}
	
	// Test case to check if the game declares Player X as winner for a horizontal line
	@Test
	void testCheckWinHorizontal() {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 60, 3, 3, 3, true);
		JButton[][] A = testGame.getBtnArray();
		int i = 0;
		int j = 0;
		ActionListener[] B;
		// Iterate through horizontal line and perform actions on buttons
		while(i != 3) {
			B = A[i][j].getActionListeners();
			B[0].actionPerformed(new ActionEvent(A[i][j], ActionEvent.ACTION_PERFORMED, null));
			if(i != 2) {
				B = A[i][j + 1].getActionListeners();
				B[0].actionPerformed(new ActionEvent(A[i][j + 1], ActionEvent.ACTION_PERFORMED, null));
			}
			i++;
		}
		String s = testGame.getMSG().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("Player X Wins!"));
	}
	
	// Test case to check if the game declares Player X as winner for a vertical line
	@Test
	void testCheckWinVertical() {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 60, 3, 3, 3, true);
		JButton[][] A = testGame.getBtnArray();
		int i = 0;
		int j = 0;
		ActionListener[] B;
		// Iterate through vertical line and perform actions on buttons
		while(j != 3) {
			B = A[i][j].getActionListeners();
			B[0].actionPerformed(new ActionEvent(A[i][j], ActionEvent.ACTION_PERFORMED, null));
			if(j != 2) {
				B = A[i + 1][j].getActionListeners();
				B[0].actionPerformed(new ActionEvent(A[i + 1][j], ActionEvent.ACTION_PERFORMED, null));
			}
			j++;
		}
		String s = testGame.getMSG().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("Player X Wins!"));
	}
	
	// Test case to check if the grid layout creation calculates side width correctly
	@Test
	void testCreateGBL() {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 60, 3, 3, 3, true);
		testGame.setBounds(0, 0, 500, 500);
		int sideWidth = testGame.createGBL(new GridBagLayout(), 3, 3);
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, sideWidth == (500 / 4 -15));
	}
	
	// Test case to check if the game declares Player O as winner in case of forfeit
	@Test
	void testForfeit() {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 60, 3, 3, 3, true);
		JButton button = testGame.getForfeitBtn();
		ActionListener[] B = button.getActionListeners();
		B[0].actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
		String s = testGame.getMSG().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("FORFEIT: Player O Wins!"));
	}
	
	// Test case to check if the game declares draw in case no one wins
	@Test
	void testDraw() {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 60, 3, 3, 3, true);
		JButton[][] A = testGame.getBtnArray();
		ActionListener[] B;
		int i = 0;
		int j = 0;
		int m = 2;
		int n = 0;
		// Iterate through the grid and perform actions on buttons
		for(int a = 0; a < 9; a++) {
			if(testGame.getPlayer() == 0) {
				B = A[i][j].getActionListeners();
				B[0].actionPerformed(new ActionEvent(A[i][j], ActionEvent.ACTION_PERFORMED, null));
				i++;
				if(i > 1) {
					j++;
				}
				if(i > 2) {
					i = 0;
				}
			}
			else {
				B = A[m][n].getActionListeners();
				B[0].actionPerformed(new ActionEvent(A[m][n], ActionEvent.ACTION_PERFORMED, null));
				m++;
				if(m > 1) {
					n++;
				}
				if(m > 2) {
					m = 0;
				}
			}
		}
		String s = testGame.getMSG().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("DRAW!"));
	}
	
	// Test case to check if the iterateTimer method decrements the timer correctly
	@Test
	void testIterateTimer() throws InterruptedException {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 10, 3, 3, 3, true);
		testGame.iterateTimer();
		String s = testGame.getXTimer().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("00:09"));
	}
	
	// Test case to check if the grid is initiated correctly in the Game class
	@Test
	void testInitiateGrid() {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 60, 3, 3, 3, true);
		JButton[][] A = testGame.getBtnArray();
		boolean test = true;
		outerloop:
		// Iterate through the grid and check the client properties
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				A[i][j].getClientProperty("x");
				A[i][j].getClientProperty("y");
				if(i != (int) A[i][j].getClientProperty("x") || j != (int) A[i][j].getClientProperty("y")) {
					test = false;
					break outerloop;
				}
			}
		}
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, test);
	}
	
	// Test case to check if the highlight method highlights buttons correctly
	@Test
	void testHighlight() {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 60, 3, 3, 3, true);
		JButton[][] A = testGame.getBtnArray();
		testGame.highlight(3, 0, 0, 2, 3);
		Color back = Color.BLACK;
		Color text = Color.WHITE;
		boolean test = true;
		int i = 0;
		// Iterate through the buttons and check if they are highlighted correctly
		for(int j = 0; j < 3; j++) {
			if(!back.equals(A[i][j].getBackground()) || !text.equals(A[i][j].getForeground())) {
				test = false;
				break;
			}
		}
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, test);
	}
	
	// Test case to check if the end method displays the correct message
	@Test
	void testEnd() {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 60, 3, 3, 3, true);
		testGame.end("Player ");
		String s = testGame.getMSG().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("Player O Wins!"));
	}
	
	@Test
	void testMark() {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 60, 3, 3, 3, true);
		JButton[][] A = testGame.getBtnArray();
		testGame.mark(A[0][0]);
		String s = A[0][0].getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("X"));
	}
	
	@Test
	void testSwitchTurn() {
		MainMenu testMain = new MainMenu();
		Game testGame = new Game(testMain, 60, 3, 3, 3, true);
		testGame.switchTurn();
		String s = testGame.getMSG().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("Player O's Turn"));
	}
}