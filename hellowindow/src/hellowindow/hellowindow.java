package hellowindow;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class hellowindow {

	hellowindow() {
		init_window();
	}
	
	private void init_window() {
		JFrame window = new JFrame();
		window.setTitle("Hello Window!");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(400,400);
		window.setLayout(new FlowLayout());
		JLabel emptyLabel = new JLabel("Hello Window");
		window.getContentPane().add(emptyLabel);
		JButton clicker = new JButton("Click me!");
		clicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				binaryDist test = new binaryDist();
				Double result = test.calcBinaryDist(0.05, 1, 10);
				emptyLabel.setText(emptyLabel.getText() + "Calculation result: " + result.toString()+ "\n");
		       //emptyLabel.setText("Button was clicked");
			}
		});
		window.getContentPane().add(clicker);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		new hellowindow();

	}

}
