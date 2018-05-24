package jMetal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ScriptGenerator {
	
	/**
	 * Generates HV.Boxplot.eps
	 * 
	 * @param r_path to RScript.exe (e.g: C:\\Program Files\\R\\R-3.4.2\\bin\\RScript.exe)
	 * @param experiment name (e.g.: ExperimentsInteger)
	 * @throws IOException
	 */
	public static void generateR(String r_path, String experiment) throws IOException {

		Process process = new ProcessBuilder(r_path,"HV.Boxplot.R")
				.directory(new File("experimentBaseDirectory\\"+experiment+"\\R")).start();		


		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line;

		while((line=br.readLine()) != null){
			System.out.println(line);
		}
	}
	
	/**
	 * Generates the latex PDF
	 * 
	 * @param latex_path to pdflatex.exe (e.g: C:\\Program Files\\MiKTeX 2.9\\miktex\\bin\\x64\\pdflatex.exe)
	 * @param experiment name (e.g.: ExperimentsInteger)
	 * @throws IOException
	 */
	public static void generatorLatex(String latex_path, String experiment) throws IOException {
		
		Process process = new ProcessBuilder(latex_path,experiment+".tex")
				.directory(new File("experimentBaseDirectory\\"+experiment+"\\latex")).start();

		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		String line;
		
		while((line=br.readLine()) != null){
			System.out.println(line);
		}
	}
	

}
