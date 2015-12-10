package Controllers;

import java.awt.EventQueue;

import javax.swing.JFrame;

import View.Ronde2;

public class MainController {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
	
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setSize(500, 500);
					frame.setVisible(true);
					frame.setResizable(false);
					
					frame.add(new Ronde2());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
