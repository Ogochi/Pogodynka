package pl.edu.mimuw.pogodynka.app;

public class Log {
	public static void info(String txt) {
		System.out.print("[INFO] ");
		
		System.out.println(txt);
	}
	
	public static void err(String txt) {
		System.out.print("[ERROR] ");
		
		System.out.println(txt);
	}
}
