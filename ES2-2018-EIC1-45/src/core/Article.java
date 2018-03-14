package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Article implements Serializable {
	
	private static final long serialVersionUID = -2097845286374685074L;
	private String header;
	private ArrayList<String> article;
	private File f;
	private transient  BufferedReader in;
	
	public Article(File f){
		this.setF(f);
		this.article = new ArrayList<>();
		try {
			this.in = new BufferedReader(new InputStreamReader( new FileInputStream(f), "UTF8"));
		} catch (UnsupportedEncodingException e) {
			System.err.println(e);
		} catch (FileNotFoundException e) {
			System.err.println(e);
		}
		boolean first_line = true;
		String line;
			try {
				while((line = this.in.readLine()) != null){
						if(first_line){
							this.header = line;
							this.article.add(line);
							first_line = false;
							}else {this.article.add(line);}
				}
				this.in.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		
	}
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public ArrayList<String> getArticle() {
		return article;
	}

	public void setArticle(ArrayList<String> article) {
		this.article = article;
	}


	public File getF() {
		return f;
	}


	public void setF(File f) {
		this.f = f;
	}
	
}
