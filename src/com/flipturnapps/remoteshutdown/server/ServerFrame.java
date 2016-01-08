package com.flipturnapps.remoteshutdown.server;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ServerFrame extends JFrame implements Runnable, ItemListener
{

	private static final String AUTOMATIC_SHUTDOWN = "Your device will shutdown automatically in ";
	private static final String AUTOMATIC_TIMEOUT = "Remote shutdown will timeout in ";
	private JPanel contentPane;
	private JTextField textfield_port;
	private JTextField textField_password;
	private JTextField textField_timescale;
	private JTextField textField_time;
	private JTextField txtRemoteShutdownIs;
	private long timeoutTime;
	private boolean shutdown;
	private JCheckBox chckbxShowPassword;
	private String pass;
	private int length;
	private String stars;


	/**
	 * Create the frame.
	 * @param dateAllowance 
	 */
	public ServerFrame(String password, boolean timeoutIsShutdown, long timeoutInMillis, int port, int passLength, int dateAllowance) 
	{
		pass = password;
		length = passLength;
		timeoutTime = timeoutInMillis;
		shutdown = timeoutIsShutdown;
		setResizable(false);
		setTitle("Simple Remote Shutdown");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 637, 263);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		stars = "";
		for(int i = 0; i < passLength; i++)
		{
			stars += "*";
		}
		String timescaleText = "";
		if(dateAllowance == 0)
			timescaleText = "Today only";
		else if(dateAllowance == 1)
			timescaleText = "Tomorrow only";
		else if(dateAllowance == 2)
			timescaleText = "Today and Tomorrow";

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
		textfield_port.setText(port + "");
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
		textField_password.setText(stars);
		textField_password.setHorizontalAlignment(SwingConstants.LEFT);
		textField_password.setFont(new Font("Calibri", Font.PLAIN, 17));
		textField_password.setEditable(false);
		textField_password.setColumns(10);
		textField_password.setBorder(null);

		JPanel panel_ckbox = new JPanel();
		FlowLayout fl_panel_ckbox = (FlowLayout) panel_ckbox.getLayout();
		fl_panel_ckbox.setAlignment(FlowLayout.LEFT);
		panel_passwordinfo.add(panel_ckbox);

		chckbxShowPassword = new JCheckBox("Show Password?");
		chckbxShowPassword.addItemListener(this);
		chckbxShowPassword.setEnabled(password != null);
		panel_ckbox.add(chckbxShowPassword);
		chckbxShowPassword.setFont(new Font("Calibri", Font.PLAIN, 16));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_info.add(panel);

		JPanel panel_timescale = new JPanel();
		panel.add(panel_timescale);
		panel_timescale.setVisible(timeoutInMillis > 0);
		panel_timescale.setLayout(new BoxLayout(panel_timescale, BoxLayout.Y_AXIS));

		JLabel lblPasswordValid = new JLabel("Password Valid:");
		lblPasswordValid.setFont(new Font("Calibri", Font.BOLD, 19));
		panel_timescale.add(lblPasswordValid);

		textField_timescale = new JTextField();
		textField_timescale.setHorizontalAlignment(SwingConstants.LEFT);
		textField_timescale.setFont(new Font("Calibri", Font.PLAIN, 15));
		textField_timescale.setEditable(false);
		textField_timescale.setColumns(7);
		textField_timescale.setBorder(null);
		textField_timescale.setText(timescaleText);
		panel_timescale.add(textField_timescale);

		JTextArea textArea = new JTextArea();
		textArea.setBackground(SystemColor.control);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel panel_time = new JPanel();
		contentPane.add(panel_time, BorderLayout.SOUTH);

		String temp = AUTOMATIC_TIMEOUT;
		if(timeoutIsShutdown)
			temp = AUTOMATIC_SHUTDOWN;
		JLabel lblRemoteShutdownWill = new JLabel(temp);
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
		if(timeoutInMillis > 0)
			new Thread(this).start();

	}


	@Override
	public void run() 
	{
		long startTime = System.currentTimeMillis();
		while (true)
		{
			if (startTime + timeoutTime < System.currentTimeMillis())
			{
				if (shutdown)
					try {
						RSServer.shutDown();
					} catch (RuntimeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else
					System.exit(0);
			}
			else
			{
				long millis = (startTime + timeoutTime) - System.currentTimeMillis();
				String textUpdate = String.format("%02dh %02dm %02ds", 
						TimeUnit.MILLISECONDS.toHours(millis),
						TimeUnit.MILLISECONDS.toMinutes(millis) -  
						TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
						TimeUnit.MILLISECONDS.toSeconds(millis) - 
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));  
				textField_time.setText(textUpdate);

			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if (chckbxShowPassword.isSelected())
			textField_password.setText(pass);
		else
			textField_password.setText(stars);
	}

}
