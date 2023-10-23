package TicTacToe;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.text.BadLocationException;

import org.junit.jupiter.api.Test;

class TicTacToeTests {
	@Test
	void testLargeEntry() {
		Main testMain = new Main();
		testMain.getMinField().setText("2345");
		String s = testMain.getMinField().getText();
		testMain.dispose();
		assertEquals(true, s.equals("45"));
	}
	
	@Test
	void testSmallEntry() {
		Main testMain = new Main();
		testMain.getMinField().setText("1");
		String s = testMain.getMinField().getText();
		testMain.dispose();
		assertEquals(true, s.equals("01"));
	}
	
	@Test
	void testRemove() throws BadLocationException {
		Main testMain = new Main();
		testMain.getMinField().setText("24");
		testMain.getMinDocument().remove(0, 0);
		String s = testMain.getMinField().getText();
		testMain.dispose();
		assertEquals(true, s.equals("02"));
	}
	
	@Test
	void testCalcTime() {
		Main testMain = new Main();
		int time = testMain.calcTime("01", "30");
		testMain.dispose();
		assertEquals(true, time == 90);
	}
	
	@Test
	void testCheckWinDiagonal1() {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 60, true);
		JButton[][] A = testGame.getBtnArray();
		int i = 0;
		int j = 0;
		ActionListener[] B;
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
	
	@Test
	void testCheckWinDiagonal2() {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 60, true);
		JButton[][] A = testGame.getBtnArray();
		int i = 0;
		int j = 2;
		ActionListener[] B;
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
	
	@Test
	void testCheckWinHorizontal() {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 60, true);
		JButton[][] A = testGame.getBtnArray();
		int i = 0;
		int j = 0;
		ActionListener[] B;
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
	
	@Test
	void testCheckWinVertical() {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 60, true);
		JButton[][] A = testGame.getBtnArray();
		int i = 0;
		int j = 0;
		ActionListener[] B;
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
	
	@Test
	void testCreateGBL() {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 60, true);
		testGame.setBounds(0, 0, 500, 500);
		int sideWidth = testGame.createGBL(new GridBagLayout());
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, sideWidth == (500 / 4 -15));
	}
	
	@Test
	void testForfeit() {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 60, true);
		JButton button = testGame.getForfeitBtn();
		ActionListener[] B = button.getActionListeners();
		B[0].actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null));
		String s = testGame.getMSG().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("FORFEIT: Player O Wins!"));
	}
	
	@Test
	void testDraw() {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 60, true);
		JButton[][] A = testGame.getBtnArray();
		ActionListener[] B;
		int i = 0;
		int j = 0;
		int m = 2;
		int n = 0;
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
	
	@Test
	void testIterateTimer() throws InterruptedException {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 10, true);
		testGame.iterateTimer();
		String s = testGame.getXTimer().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("00:09"));
	}
	
	@Test
	void testInitiateGrid() {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 60, true);
		JButton[][] A = testGame.getBtnArray();
		boolean test = true;
		outerloop:
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
	
	@Test
	void testHighlight() {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 60, true);
		JButton[][] A = testGame.getBtnArray();
		testGame.highlight(3, 0, 0, 2);
		Color back = Color.BLACK;
		Color text = Color.WHITE;
		boolean test = true;
		int i = 0;
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
	
	@Test
	void testEnd() {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 60, true);
		testGame.end("Player ");
		String s = testGame.getMSG().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("Player O Wins!"));
	}
	
	@Test
	void testMark() {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 60, true);
		JButton[][] A = testGame.getBtnArray();
		testGame.mark(A[0][0]);
		String s = A[0][0].getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("X"));
	}
	
	@Test
	void testSwitchTurn() {
		Main testMain = new Main();
		Game testGame = new Game(testMain, 60, true);
		testGame.switchTurn();
		String s = testGame.getMSG().getText();
		testGame.dispose();
		testMain.dispose();
		assertEquals(true, s.equals("Player O's Turn"));
	}
}
