package window;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MobileController
{

public static String IP = "localhost";
public static String port = "6000";
public static boolean showCamera = true;


/**
 * Launch the application.
 */
public static void main(String[] args)
{
	// Create the window
	Window window = new Window();
	window.frame.setVisible(true);

	// Either load a settings file, or create a new one and use defaults.
	File defaultSetup;
	if (System.getProperty("os.name").toLowerCase().contains("windows"))
		defaultSetup = new File("C:/Users/ramte/Documents/defaultSettings.cfg");
	else
		defaultSetup = new File("/home/pi/defaultSettings.cfg");
	boolean settingsLoaded = false;
	try
	{
		if (!defaultSetup.createNewFile())
		{
			System.out.println("Settings file loaded!");
			settingsLoaded = true;
		} else
		{
			System.out.println("Creating settings file... Using default settings.");
		}
	} catch (IOException e)
	{
		e.printStackTrace();
	}

	// Setting the default values in the settings fields
	try
	{
		if (settingsLoaded)
		{
			BufferedReader fileReader = new BufferedReader(new FileReader(defaultSetup));
			IP = fileReader.readLine().replace("Robot_IP=", "");
			port = fileReader.readLine().replace("Robot_Port=", "");
			showCamera = Boolean.valueOf(fileReader.readLine().replace("Show_Camera=", ""));

			fileReader.close();
		} else
		{
			saveSettings(defaultSetup);
		}
	} catch (IOException e)
	{
		e.printStackTrace();
	}
	
	//Set the settings before starting the loop
	window.robotIPTxtbx.setText(IP);
	window.robotPortTxtbx.setText(port);
	window.cameraEnable.setSelected(showCamera);

	while (true)
	{

	}
}

/**
 * Saves the settings of the program to a configuration file (readable in plain text)
 * @param file The file to be written to
 * @throws IOException
 */
private static void saveSettings(File file) throws IOException
{
	file.delete();
	file.createNewFile();
	
	BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
	fileWriter.write("Robot_IP=" + IP + "\r\n");
	fileWriter.write("Robot_Port=" + port + "\r\n");
	fileWriter.write("Show_Camera=" + showCamera + "\r\n");
	fileWriter.flush();
	fileWriter.close();

}
}
