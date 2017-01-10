package ie.gmit.sw;

public class Runner {
	public static void main(String[] args) {
		//this was to run the application in the console
		//Calculate calc = new Calculate("C:/Users/garym/workspace/AssesmentII/bin/assesmentII.jar");
		
		//taken from sample on moodle
		//runs the GUI
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AppWindow();
			}
		});
	}
}
