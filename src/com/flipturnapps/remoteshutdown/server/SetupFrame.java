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

	private static final int ID_CLOSE = 1;
	private static final int ID_SHUTDOWN = 2;
	private JPanel contentPane;
	private JTextField textField_port;
	private JTextField textField_password;
	private JButton btnGo;
	private JCheckBox chckbxHidePassword;
	private JTextField textField_timeout;
	private JTextField textField_timeoutPreview;
	private JRadioButton rdbtnClose;
	private JRadioButton rdbtnShutdown;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public int timeoutChoice;
	private JCheckBox chckboxTimoutEnabled;


	public SetupFrame(String port, boolean passHide, boolean timeoutEnabled, long timeout, int timeoutActionId) 
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
		panel_password.add(textField_password);
		
		chckbxHidePassword = new JCheckBox("Hide On Startup");
		panel_password.add(chckbxHidePassword);
		chckbxHidePassword.setSelected(passHide);
		chckbxHidePassword.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxHidePassword.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		JPanel panel_timeout = new JPanel();
		FlowLayout fl_panel_timeout = (FlowLayout) panel_timeout.getLayout();
		fl_panel_timeout.setAlignment(FlowLayout.LEFT);
		panel_settings.add(panel_timeout);
		
		JLabel lblTimeout = new JLabel("Timeout (ms):");
		lblTimeout.setFont(new Font("Calibri", Font.BOLD, 17));
		panel_timeout.add(lblTimeout);
		
		chckboxTimoutEnabled = new JCheckBox("Enabled");
		chckboxTimoutEnabled.setFont(new Font("Calibri", Font.PLAIN, 16));
		chckboxTimoutEnabled.setSelected(timeoutEnabled);
		chckboxTimoutEnabled.addItemListener(new TimeoutEnabledListener(chckboxTimoutEnabled));
		panel_timeout.add(chckboxTimoutEnabled);
		
		textField_timeout = new JTextField();
		textField_timeout.setFont(new Font("Calibri", Font.PLAIN, 17));
		textField_timeout.setColumns(7);
		textField_timeout.setEditable(timeoutEnabled);
		textField_timeout.addKeyListener(new SetupListener(null,0));
		if(timeout > 0)
			textField_timeout.setText(timeout + "");
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
		SetupListener closeListener = new SetupListener(rdbtnClose,ID_CLOSE);
		rdbtnClose.addItemListener(closeListener);
		if(timeoutActionId == ID_CLOSE)
			rdbtnClose.setSelected(true);
		panel_aftertimeout.add(rdbtnClose);
		
		rdbtnShutdown = new JRadioButton("Shutdown PC");
		buttonGroup.add(rdbtnShutdown);
		rdbtnShutdown.setFont(new Font("Calibri", Font.PLAIN, 15));
		SetupListener sdownListener = new SetupListener(rdbtnShutdown,ID_SHUTDOWN);
		rdbtnShutdown.addItemListener(sdownListener);
		if(timeoutActionId == ID_SHUTDOWN)
			rdbtnShutdown.setSelected(true);
		panel_aftertimeout.add(rdbtnShutdown);

		JPanel panel_start = new JPanel();
		contentPane.add(panel_start, BorderLayout.SOUTH);

		JLabel lblNewLabel_1 = new JLabel("Start RS Server");
		lblNewLabel_1.setForeground(new Color(0, 0, 102));
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 21));
		panel_start.add(lblNewLabel_1);

		btnGo = new JButton("Go");
		btnGo.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnGo.addActionListener(this);
		panel_start.add(btnGo);
	}

	public void updateTimeoutPreview()
	{
		long millis;
		try
		{
			String timeouttext = textField_timeout.getText();
			millis = Long.parseLong(timeouttext);
		}
		catch (Exception ex)
		{
			textField_timeoutPreview.setText("Bad Formatting");
			return;
		}
		String hms = ServerMain.millisToHMS(millis);
		textField_timeoutPreview.setText(hms);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		checkInputThenStartThread();

	}


	@Override
	public void run() 
	{
		File saveFile = ServerMain.getSaveFile();
		try {
			System.out.println(saveFile.getAbsolutePath());
			saveFile.createNewFile();
			TextFileHelper.writeTextToFile(saveFile, 
					ServerMain.PORT_PREFIX + textField_port.getText(),
					ServerMain.HIDEPASS_PREFIX + chckbxHidePassword.isSelected(),
					ServerMain.TIMEOUTENABLED_PREFIX + chckboxTimoutEnabled.isSelected(),
					ServerMain.TIMEOUT_PREFIX + textField_timeout.getText(),
					ServerMain.TIMEOUTACTION_PREFIX + timeoutChoice
					);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		int port = Integer.parseInt(textField_port.getText());
		String password = textField_password.getText();
		int passLength = password.length();
		boolean allowShowPassword = !chckbxHidePassword.isSelected();
		long timeout = Long.parseLong(textField_timeout.getText());
		boolean shutDownAfterTimeout = timeoutChoice == ID_SHUTDOWN;
		PasswordCreator creator = new PasswordCreator();
		String pass = creator.createPassword(password);
		
		if (!allowShowPassword)
			password = null;
		ServerFrame frame = new ServerFrame(password, shutDownAfterTimeout, timeout, port, passLength);
		RSServer server;
		try {
			server = new RSServer(port,pass,frame);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setVisible(true);
		this.setVisible(false);
		this.dispose();
	}




	private void checkInputThenStartThread() 
	{
		boolean port = textField_port.getText().length() > 0;
		boolean password = textField_password.getText().length() > 0;
		boolean timeoutDisabled = chckboxTimoutEnabled.isSelected() == false;
		boolean timeoutAction = timeoutChoice != 0;
		boolean timeoutOK = timeoutDisabled;
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
		if(!timeoutDisabled)
		{
			long timeout;
			try
			{
				timeout = Long.parseLong(textField_timeout.getText());
				if(timeout > 0)
					timeoutOK = true;
			}
			catch(Exception ex)
			{
				
			}
		}
		if(port && password && timeoutOK && timeoutAction)
		{
			btnGo.setText("Started");
			btnGo.setEnabled(false);
			new Thread(this).start();	
		}
	}


	
	private class TimeoutEnabledListener implements ItemListener
	{
		private JCheckBox chBox;
		public TimeoutEnabledListener(JCheckBox box)
		{
			this.chBox = box;
		}
		@Override
		public void itemStateChanged(ItemEvent arg0) 
		{
			if(chBox.isSelected())
				textField_timeout.setEditable(true);	
			else
				textField_timeout.setEditable(false);
		}
		
	}

	private class SetupListener implements ItemListener, KeyListener
	{
		private int id;
		private JRadioButton button;
		public SetupListener(JRadioButton button, int id)
		{
			this.button = button;
			this.id = id;
		}
		public void itemStateChanged(ItemEvent arg0)
		{
			if(button.isSelected())
				timeoutChoice = id;
		}
		@Override
		public void keyPressed(KeyEvent arg0) 
		{
			updateTimeoutPreview();			
		}
		@Override
		public void keyReleased(KeyEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method st
			
			
		}
		
	}




}
