package Controllers;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class MainController {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
	
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setSize(500, 500);
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
