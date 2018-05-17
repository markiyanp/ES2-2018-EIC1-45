package visual;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TopPanel extends JPanel {
	private static final long serialVersionUID = -9196884174401501068L;
	private Window window;

	public TopPanel(Window window, int TOP_PANEL_HEIGHT) {
		setLayout(null);
		this.window = window;
		setOpaque(false);
		add(new JButton());
	}
	
	
}