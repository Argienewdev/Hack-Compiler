package main;

import java.awt.EventQueue;

public class Launcher {
	
	public static void main(String[] a) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Compiler compiler = new Compiler();
					CompilerWindow window = new CompilerWindow(compiler);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
