package TicTacToe;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
	Image img;
	public BackgroundPanel(String image) {
		img = Toolkit.getDefaultToolkit().createImage(image);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}
}
