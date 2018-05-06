package jMetal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class ConsoleOutputCapturer extends Thread {
	private ByteArrayOutputStream baos;
	private PrintStream previous;
	private boolean capturing;
	private static int RUNSNUMBER = 5;

	@Override
	public void run() {
		if (capturing) {
			return;
		}
		System.out.println("starting capture");
		capturing = true;
		previous = System.out;
		baos = new ByteArrayOutputStream();

		OutputStream outputStreamCombiner = new OutputStreamCombiner(Arrays.asList(previous, baos));
		PrintStream custom = new PrintStream(outputStreamCombiner);

		System.setOut(custom);
	}

	@Override
	public void interrupt() {
		System.out.println("interrupting");
		if (capturing) {

			System.setOut(previous);

			String capturedValue = baos.toString();

			baos = null;
			previous = null;
			capturing = false;
			System.out.println(capturedValue);
			super.interrupt();
		}
	}
	
	private synchronized void checkRunNumber(String line) {
		if (line.contains("run")) {
			// +1 because the first run it's "0"
			double run = line.charAt(line.indexOf("run: ") + 5)+1;
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

	private static class OutputStreamCombiner extends OutputStream {
		private List<OutputStream> outputStreams;

		public OutputStreamCombiner(List<OutputStream> outputStreams) {
			this.outputStreams = outputStreams;
		}

		public void write(int b) throws IOException {
			for (OutputStream os : outputStreams) {
				os.write(b);
			}
		}

		public void flush() throws IOException {
			for (OutputStream os : outputStreams) {
				os.flush();
			}
		}

		public void close() throws IOException {
			for (OutputStream os : outputStreams) {
				os.close();
			}
		}
	}
}