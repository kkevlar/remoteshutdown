package com.flipturnapps.remoteshutdown.server;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ServerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textfield_port;
	private JTextField textField_password;
	private JTextField textField_timescale;
	private JTextField textField_time;
	private JTextField txtRemoteShutdownIs;


	/**
	 * Create the frame.
	 */
	public ServerFrame() {
		setResizable(false);
		setTitle("Simple Remote Shutdown");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 637, 263);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_info = new JPanel();
		contentPane.add(panel_info, BorderLayout.WEST);
		panel_info.setLayout(new BoxLayout(panel_info, BoxLayout.Y_AXIS));
		
		JPanel panel_portinfo = new JPanel();
		FlowLayout fl_panel_portinfo = (FlowLayout) panel_portinfo.getLayout();
		fl_panel_portinfo.setAlignment(FlowLayout.LEFT);
		panel_info.add(panel_portinfo);
		
		JLabel lblPortLabel = new JLabel("Open Port:");
		lblPortLabel.setFont(new Font("Calibri", Font.BOLD, 19));
		panel_portinfo.add(lblPortLabel);
		
		textfield_port = new JTextField();
		textfield_port.setBorder(null);
		textfield_port.setEditable(false);
		textfield_port.setFont(new Font("Calibri", Font.PLAIN, 17));
		textfield_port.setText("<port>");
		textfield_port.setHorizontalAlignment(SwingConstants.LEFT);
		panel_portinfo.add(textfield_port);
		textfield_port.setColumns(7);
		
		JPanel panel_passwordinfo = new JPanel();
		panel_info.add(panel_passwordinfo);
		panel_passwordinfo.setLayout(new BoxLayout(panel_passwordinfo, BoxLayout.Y_AXIS));
		
		JPanel panel_passwordtoppanel = new JPanel();
		panel_passwordinfo.add(panel_passwordtoppanel);
		panel_passwordtoppanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblPassword = new JLabel("Password:");
		panel_passwordtoppanel.add(lblPassword);
		lblPassword.setFont(new Font("Calibri", Font.BOLD, 19));
		
		textField_password = new JTextField();
		panel_passwordtoppanel.add(textField_password);
		textField_password.setText("<password>");
		textField_password.setHorizontalAlignment(SwingConstants.LEFT);
		textField_password.setFont(new Font("Calibri", Font.PLAIN, 17));
		textField_password.setEditable(false);
		textField_password.setColumns(7);
		textField_password.setBorder(null);
		
		JPanel panel_ckbox = new JPanel();
		FlowLayout fl_panel_ckbox = (FlowLayout) panel_ckbox.getLayout();
		fl_panel_ckbox.setAlignment(FlowLayout.LEFT);
		panel_passwordinfo.add(panel_ckbox);
		
		JCheckBox chckbxShowPassword = new JCheckBox("Show Password?");
		panel_ckbox.add(chckbxShowPassword);
		chckbxShowPassword.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		JPanel panel_timescale = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_timescale.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_info.add(panel_timescale);
		
		JLabel lblPasswordValid = new JLabel("Password Valid:");
		lblPasswordValid.setFont(new Font("Calibri", Font.BOLD, 19));
		panel_timescale.add(lblPasswordValid);
		
		textField_timescale = new JTextField();
		textField_timescale.setText("<timescale>");
		textField_timescale.setHorizontalAlignment(SwingConstants.LEFT);
		textField_timescale.setFont(new Font("Calibri", Font.PLAIN, 15));
		textField_timescale.setEditable(false);
		textField_timescale.setColumns(7);
		textField_timescale.setBorder(null);
		panel_timescale.add(textField_timescale);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(SystemColor.control);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_time = new JPanel();
		contentPane.add(panel_time, BorderLayout.SOUTH);
		
		JLabel lblRemoteShutdownWill = new JLabel("Your device will shutdown automatically in ");
		lblRemoteShutdownWill.setFont(new Font("Calibri", Font.BOLD, 18));
		panel_time.add(lblRemoteShutdownWill);
		
		textField_time = new JTextField();
		textField_time.setFont(new Font("Calibri", Font.BOLD, 18));
		textField_time.setEditable(false);
		panel_time.add(textField_time);
		textField_time.setColumns(15);
		
		txtRemoteShutdownIs = new JTextField();
		txtRemoteShutdownIs.setBorder(null);
		txtRemoteShutdownIs.setText("Remote Shutdown Is Waiting to Shutdown Your Device");
		txtRemoteShutdownIs.setHorizontalAlignment(SwingConstants.CENTER);
		txtRemoteShutdownIs.setFont(new Font("Calibri", Font.BOLD, 22));
		txtRemoteShutdownIs.setEditable(false);
		txtRemoteShutdownIs.setColumns(13);
		contentPane.add(txtRemoteShutdownIs, BorderLayout.NORTH);
		
		
	}

}
