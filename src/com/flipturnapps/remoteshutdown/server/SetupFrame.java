package com.flipturnapps.remoteshutdown.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.flipturnapps.kevinLibrary.helper.TextFileHelper;
import com.flipturnapps.remoteshutdown.common.PasswordCreator;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class SetupFrame extends JFrame implements ActionListener, Runnable
{

	private JPanel contentPane;
	private JTextField textField_port;
	private JTextField textField_password;
	private JButton btnNewButton;
	private JCheckBox chckbxHidePassword;
	private JTextField textField_timeout;
	private JTextField textField_timeoutPreview;
	private JRadioButton rdbtnClose;
	private JRadioButton rdbtnShutdown;
	private final ButtonGroup buttonGroup = new ButtonGroup();


	public SetupFrame(String ip, String port) 
	{
		setTitle("Remote Shutdown Setup");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 466, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel_settings = new JPanel();
		contentPane.add(panel_settings, BorderLayout.CENTER);
		panel_settings.setLayout(new BoxLayout(panel_settings, BoxLayout.Y_AXIS));
		

		JPanel panel_portconfig = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_portconfig.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_settings.add(panel_portconfig);

		JLabel lblPort = new JLabel("Server Port:");
		lblPort.setFont(new Font("Calibri", Font.BOLD, 17));
		panel_portconfig.add(lblPort);

		textField_port = new JTextField();
		textField_port.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) 
			{
				e.getComponent().setForeground(Color.BLACK);
			}
		});
		textField_port.setFont(new Font("Calibri", Font.PLAIN, 17));
		textField_port.setColumns(7);
		textField_port.addKeyListener(this);
		panel_portconfig.add(textField_port);
		if(port != null)
			textField_port.setText(port);

		JPanel panel_password = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_password.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		panel_settings.add(panel_password);

		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setFont(new Font("Calibri", Font.BOLD, 19));
		panel_password.add(lblPassword);

		textField_password = new JTextField();
		textField_password.setFont(new Font("Calibri", Font.PLAIN, 17));
		textField_password.setColumns(12);
		textField_password.addKeyListener(this);
		panel_password.add(textField_password);
		
		chckbxHidePassword = new JCheckBox("Hide On Startup");
		panel_password.add(chckbxHidePassword);
		chckbxHidePassword.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxHidePassword.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		JPanel panel_timeout = new JPanel();
		FlowLayout fl_panel_timeout = (FlowLayout) panel_timeout.getLayout();
		fl_panel_timeout.setAlignment(FlowLayout.LEFT);
		panel_settings.add(panel_timeout);
		
		JLabel lblTimeout = new JLabel("Timeout (ms):");
		lblTimeout.setFont(new Font("Calibri", Font.BOLD, 17));
		panel_timeout.add(lblTimeout);
		
		JCheckBox chckbxEnabled = new JCheckBox("Enabled");
		chckbxEnabled.setFont(new Font("Calibri", Font.PLAIN, 16));
		panel_timeout.add(chckbxEnabled);
		
		textField_timeout = new JTextField();
		textField_timeout.setFont(new Font("Calibri", Font.PLAIN, 17));
		textField_timeout.setColumns(7);
		panel_timeout.add(textField_timeout);
		
		textField_timeoutPreview = new JTextField();
		textField_timeoutPreview.setEditable(false);
		textField_timeoutPreview.setFont(new Font("Calibri", Font.PLAIN, 17));
		textField_timeoutPreview.setColumns(7);
		panel_timeout.add(textField_timeoutPreview);
		
		JPanel panel_aftertimeout = new JPanel();
		FlowLayout fl_panel_aftertimeout = (FlowLayout) panel_aftertimeout.getLayout();
		fl_panel_aftertimeout.setAlignment(FlowLayout.LEFT);
		panel_settings.add(panel_aftertimeout);
		
		JLabel lblAfterTimeout = new JLabel("After Timeout:");
		lblAfterTimeout.setFont(new Font("Calibri", Font.BOLD, 18));
		panel_aftertimeout.add(lblAfterTimeout);
		
		rdbtnClose = new JRadioButton("Close Server");
		buttonGroup.add(rdbtnClose);
		rdbtnClose.setFont(new Font("Calibri", Font.PLAIN, 15));
		panel_aftertimeout.add(rdbtnClose);
		
		rdbtnShutdown = new JRadioButton("Shutdown PC");
		buttonGroup.add(rdbtnShutdown);
		rdbtnShutdown.setFont(new Font("Calibri", Font.PLAIN, 15));
		panel_aftertimeout.add(rdbtnShutdown);

		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);

		JLabel lblNewLabel_1 = new JLabel("Start RS Server");
		lblNewLabel_1.setForeground(new Color(0, 0, 102));
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 21));
		panel_3.add(lblNewLabel_1);

		btnNewButton = new JButton("Go");
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnNewButton.addActionListener(this);
		panel_3.add(btnNewButton);
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		buttonPressed();

	}


	@Override
	public void run() 
	{
		File saveFile = ServerMain.getSaveFile();
		try {
			System.out.println(saveFile.getAbsolutePath());
			saveFile.createNewFile();
			TextFileHelper.writeTextToFile(saveFile, ClientMain.IP_PREFIX + textField_ip.getText(),ClientMain.PORT_PREFIX + textField_port.getText());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PasswordCreator creator = new PasswordCreator();
		String pass  = creator.createPassword(textField_password.getText());
		RSClient client = null;
		try {
			client = new RSClient(textField_ip.getText(), Integer.parseInt(textField_port.getText()));

		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		client.sendMessage(pass);

	}


	@Override
	public void keyPressed(KeyEvent e) 
	{
		e.getComponent().setForeground(Color.BLACK);
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			buttonPressed();
	

	}


	private void buttonPressed() 
	{
		boolean ip = textField_ip.getText().length() > 0;
		boolean port = textField_port.getText().length() > 0;
		boolean password = textField_password.getText().length() > 0;
		if(!ip)
		{
			textField_ip.requestFocus();
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		if(!port)
		{
			textField_port.requestFocus();
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		if(!password)
		{
			textField_password.requestFocus();
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		String portText = textField_port.getText();
		try
		{
			int test = Integer.parseInt(portText);
		}
		catch(Exception ex)
		{
			textField_port.setForeground(Color.RED);
			Toolkit.getDefaultToolkit().beep();
			port = false;
		}
		if(ip && port && password)
		{
			btnNewButton.setText("Sent!");
			btnNewButton.setEnabled(false);
			new Thread(this).start();	
		}
	}


	


	private class




}
