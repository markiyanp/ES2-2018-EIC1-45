package optimizationProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleBufferReader extends Thread {

	private static InputStreamReader isr = new InputStreamReader(System.in);
	private static BufferedReader br = new BufferedReader(isr);
	private static int RUNSNUMBER = 5;

	@Override
	public void run() {
		String line;
		while (true) {
			try {
				line = br.readLine();
				checkRunNumber(line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private synchronized void checkRunNumber(String line) {
		if (line.contains("run")) {
			double run = line.charAt(line.indexOf("run: ") + 5);
			if (run / RUNSNUMBER >= 0.25) {
				System.out.println("25% Complete");
				return;
			}
			if (run / RUNSNUMBER >= 0.5) {
				System.out.println("50% Complete");
				return;
			}
			if (run / RUNSNUMBER >= 0.75) {
				System.out.println("75% Complete");
				return;
			}
			if (run == RUNSNUMBER) {
				System.out.println("100% Complete");
				interrupt();
				return;
			}
		}
	}

	@Override
	public void interrupt() {
		try {
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.interrupt();
	}
}
