package visual;

import java.util.HashMap;

import javax.swing.JPanel;

/**
 * @author Markyian Pyekh
 *
 */
public class RightPanel extends JPanel {

	private static final long serialVersionUID = 6355942857665120138L;

	// ********************INSTANCES**********************************
	private HashMap<String, JPanel> panel_map = new HashMap<String, JPanel>();
	private JPanel panel = new JPanel();
	// ********************INSTANCES**********************************

	/**
	 * The construtor
	 * @param window
	 */
	public RightPanel(Window window) {
		setLayout(null);
		panel_map.put("optimization", new OptimizationTab(window));
		panel_map.put("graphics", new GraphicsTab(window));
		panel_map.put("help", new HelpTab(window));

		loadTab("optimization");
		add(panel);
		repaint();
	}

	/**
	 * Load the new tab
	 * @param key
	 */
	private void loadTab(String key) {
		if (panel_map.containsKey(key)) {
			this.remove(panel);
			panel = panel_map.get(key);
			panel.setBounds(0, 0, 838, 612);
			add(panel);
			repaint();
		}
	}

	/**
	 * Changing a tab according to tab name
	 * @param tab_name
	 */
	public void changeTab(String tab_name) {
		loadTab(tab_name);
	}

	/**
	 * Return the optimization tab
	 * @return optimization tab
	 */
	public OptimizationTab getOpt_tab() {
		return (OptimizationTab) this.panel_map.get("optimization");
	}
	
	/**
	 * Return the graphics tab
	 * @return graphics tab
	 */
	public GraphicsTab getGraphicTab() {
		return (GraphicsTab) this.panel_map.get("graphics");
	}
	
	/**
	 * Return the help tab
	 * @return help tab
	 */
	public HelpTab getHelpTab() {
		return (HelpTab) this.panel_map.get("help");
	}

}