package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.LayerUI;

public class BlurTextExample extends JPanel {
	
	public BlurTextExample() {
		setBorder(new EmptyBorder(10, 10, 10, 10)); //add padding 
		JPanel answers[] = new JPanel[4];
		
		for(int i = 0; i < 4; i++) {
			JPanel panel = new JPanel();
			panel.add(new JLabel("antwoord " + i));
			panel.setBorder(new EmptyBorder(10, 10, 10, 10));
			panel.setBackground(Color.RED);
			answers[i] = panel;
			add(panel);
		}
		
		LayerUI<Component> layerUI = new BlurLayerUI();
		JLayer<Component> jlayer = new JLayer<Component>(answers[2], layerUI);
		add(jlayer);
		
	}
}


