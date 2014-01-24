package org.rb.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Button;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.eclipse.swt.browser.Browser;
import org.rb.fb.FBFunctions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainApp {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		System.setProperty("http.proxySet", "false");

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEnterAnAccess = new JLabel("Enter an access token");
		lblEnterAnAccess.setBounds(12, 14, 140, 16);
		frame.getContentPane().add(lblEnterAnAccess);
		
		textField = new JTextField();
		textField.setBounds(155, 12, 267, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		final JLabel lblYourName = new JLabel("Your Name:");
		lblYourName.setBounds(104, 87, 81, 16);
		frame.getContentPane().add(lblYourName);
		
		final JLabel label = new JLabel("----");
		label.setBounds(176, 87, 246, 16);
		frame.getContentPane().add(label);
		
		JButton btnSubmitAccessToken = new JButton("Submit access token");
		btnSubmitAccessToken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String accessToken = textField.getText();
				String userName = FBFunctions.getUserName(accessToken);
				label.setText(userName);
			}
		});
		btnSubmitAccessToken.setBounds(125, 42, 195, 26);
		frame.getContentPane().add(btnSubmitAccessToken);
		

		JButton btnThankTheWorld = new JButton("Thank the world");
		btnThankTheWorld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String accessToken = textField.getText();
				FBFunctions.thankOnFB(accessToken);
			}
		});
		btnThankTheWorld.setBounds(128, 224, 159, 26);
		frame.getContentPane().add(btnThankTheWorld);
	}
}
