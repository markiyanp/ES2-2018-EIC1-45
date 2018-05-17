package visual;

import java.util.HashMap;

import javax.swing.JPanel;

public class RightPanel extends JPanel {
	
	private static final long serialVersionUID = 6355942857665120138L;
	
	//********************INSTANCES**********************************
	private HashMap<String, JPanel> panel_map = new HashMap<String, JPanel>();
	private JPanel panel = new JPanel();
	private Window window;
	private OptimizationTab opt_tab = new OptimizationTab(window);
	//********************INSTANCES**********************************
	
	
	public RightPanel(Window window){
		setLayout(null);
		this.window = window;
		panel_map.put("optimization", opt_tab);
		panel_map.put("graphics", new GraphicsTab());
		panel_map.put("help", new HelpTab());
		
		loadTab("optimization");
		add(panel);
		repaint();
	}
	
	
	private void loadTab(String key) {
		if(panel_map.containsKey(key)){
			this.remove(panel);
			panel = panel_map.get(key);
			panel.setBounds(0, 0, 838, 612);
			add(panel);
			repaint();
		}
	}

	public void changeTab(String tab_name){
		loadTab(tab_name);
	}


	public OptimizationTab getOpt_tab() {
		return opt_tab;
	}
	
	
	
}