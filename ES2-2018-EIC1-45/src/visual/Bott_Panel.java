package visual;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * @author Markiyan Pyekh
 *
 */
public class Bott_Panel extends JPanel {
	
	private static final long serialVersionUID = -5756900139966540449L;
	
	/**
	 * The constructor
	 * 
	 * @param window
	 * @param BOTT_PANEL_HEIGHT
	 */
	public Bott_Panel(Window window, int BOTT_PANEL_HEIGHT) {
		setPreferredSize(new Dimension(0,BOTT_PANEL_HEIGHT));	
		setOpaque(false);
	}

}
