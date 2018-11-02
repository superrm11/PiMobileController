package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.Component;
import java.awt.Button;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JCheckBox;

public class Window
{

JFrame frame;

/**
 * Create the application.
 */
public Window()
{
	initialize();
}

boolean isSetup = false;

//View Tab
private JLabel imageContainer;
private ImageIcon image;
JCheckBox cameraEnable;

//Setup Tab
JTextField robotIPTxtbx;
JTextField robotPortTxtbx;
private JLabel lblPort;
private Button connectButton;

//Tab System
private Button setupBtn;
private Button cameraBtn;
private JPanel setupTab;
private JPanel viewTab;
private JPanel tabSystem;

/**
 * Initialize the contents of the frame.
 */
private void initialize()
{
	frame = new JFrame();
	frame.setBounds(100, 100, 450, 300);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);

	//===============TABS==================
	setupTab = new JPanel();
	setupTab.setBounds(0, 24, 432, 229);
	frame.getContentPane().add(setupTab);
	setupTab.setLayout(null);
	
	viewTab = new JPanel();
	viewTab.setBounds(0, 24, 432, 229);
	frame.getContentPane().add(viewTab);
	viewTab.setLayout(null);
	
	tabSystem = new JPanel();
	tabSystem.setBounds(0, 0, 432, 24);
	frame.getContentPane().add(tabSystem);
	tabSystem.setLayout(null);
	
	//============SETUP TAB================
	robotIPTxtbx = new JTextField();
	robotIPTxtbx.setBounds(12, 64, 116, 22);
	setupTab.add(robotIPTxtbx);
	robotIPTxtbx.setColumns(10);

	JLabel lblIp = new JLabel("Robot IP");
	lblIp.setBounds(12, 47, 48, 16);
	setupTab.add(lblIp);

	connectButton = new Button("Connect");
	connectButton.setBounds(199, 62, 79, 24);
	setupTab.add(connectButton);

	robotPortTxtbx = new JTextField();
	robotPortTxtbx.setBounds(133, 64, 54, 22);
	setupTab.add(robotPortTxtbx);
	robotPortTxtbx.setColumns(10);

	lblPort = new JLabel("Port");
	lblPort.setBounds(131, 47, 56, 16);
	setupTab.add(lblPort);
	
	cameraEnable = new JCheckBox("Enable Camera");
	cameraEnable.setBounds(308, 204, 124, 25);
	setupTab.add(cameraEnable);
	
	//===============VIEW TAB================
	image = new ImageIcon();
	
	imageContainer = new JLabel(image);
	imageContainer.setBounds(192, 17, -1, -1);
	viewTab.add(imageContainer);

	setupBtn = new Button("Setup");
	setupBtn.setBounds(91, 0, 48, 24);
	tabSystem.add(setupBtn);

	cameraBtn = new Button("Camera View");
	cameraBtn.setBounds(0, 0, 91, 24);
	tabSystem.add(cameraBtn);
	
	frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { frame.getContentPane() }));
	
	startListeners();
}

private void startListeners()
{
	//If the setup button is pressed, hide the camera, and show the setup options
	setupBtn.addActionListener(new ActionListener()
	{
	public void actionPerformed(ActionEvent e)
	{
		viewTab.setVisible(false);
		setupTab.setVisible(true);
		isSetup = true;
	}
	});
	
	//If the Camera button is pressed, hide the setup options and show the camera view.
	cameraBtn.addActionListener(new ActionListener()
	{
	public void actionPerformed(ActionEvent e)
	{
		setupTab.setVisible(false);
		viewTab.setVisible(true);
		isSetup = false;
	}
	});

}

/**
 * Sets the image in the View tab in the program, reserved for the camera view.
 * @param image An OpenCV matrix of the image.
 */
public void setImage(Mat image)
{
	//Convert a mat into a matOfByte (of type jpg), then into a buffered image.
	MatOfByte matOfByte = new MatOfByte();
	Imgcodecs.imencode(".jpg", image, matOfByte);
	byte[] byteArray = matOfByte.toArray(); 
	ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
	
	try
	{
		this.image.setImage(ImageIO.read(in));
		imageContainer.setIcon(this.image);
		frame.pack();

	} catch (IOException e)
	{
		e.printStackTrace();
	}
}
}
