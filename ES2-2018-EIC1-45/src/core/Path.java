package core;

/**
 * @author Markiyan Pyekh
 *
 */
public class Path {

	private String name;
	private String url;
	
	/**
	 * The constructor
	 * 
	 * @param name
	 * @param url
	 */
	public Path(String name, String url) {
		this.name = name;
		this.url = url;
	}

	/**
	 * The constructor
	 */
	public Path() {
	}

	/**
	 * Returns the path name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set a path name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the url path
	 * 
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set a url path
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
