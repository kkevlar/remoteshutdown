package com.flipturnapps.remoteshutdown.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.FlowLayout;

public class ClientFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField_ip;
	private JTextField textField_port;
	private JTextField textField;

	
	public ClientFrame() {
		setTitle("Remote Shutdown Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 373, 227);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_settings = new JPanel();
		contentPane.add(panel_settings, BorderLayout.CENTER);
		panel_settings.setLayout(new BoxLayout(panel_settings, BoxLayout.Y_AXIS));
		
		JPanel panel_ipconfig = new JPanel();
		FlowLayout fl_panel_ipconfig = (FlowLayout) panel_ipconfig.getLayout();
		fl_panel_ipconfig.setAlignment(FlowLayout.LEFT);
		panel_settings.add(panel_ipconfig);
		
		JLabel lblNewLabel = new JLabel("Server IP:");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 19));
		panel_ipconfig.add(lblNewLabel);
		
		textField_ip = new JTextField();
		textField_ip.setFont(new Font("Calibri", Font.PLAIN, 17));
		panel_ipconfig.add(textField_ip);
		textField_ip.setColumns(16);
		
		JPanel panel_portconfig = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_portconfig.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_settings.add(panel_portconfig);
		
		JLabel lblPort = new JLabel("Server Port:");
		lblPort.setFont(new Font("Calibri", Font.BOLD, 17));
		panel_portconfig.add(lblPort);
		
		textField_port = new JTextField();
		textField_port.setFont(new Font("Calibri", Font.PLAIN, 17));
		textField_port.setColumns(7);
		panel_portconfig.add(textField_port);
		
		JPanel panel_password = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_password.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		panel_settings.add(panel_password);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setFont(new Font("Calibri", Font.BOLD, 19));
		panel_password.add(lblPassword);
		
		textField = new JTextField();
		textField.setFont(new Font("Calibri", Font.PLAIN, 17));
		textField.setColumns(15);
		panel_password.add(textField);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);
		
		JLabel lblNewLabel_1 = new JLabel("Send Shutdown Command");
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 19));
		panel_3.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Go");
		panel_3.add(btnNewButton);
	}

}
