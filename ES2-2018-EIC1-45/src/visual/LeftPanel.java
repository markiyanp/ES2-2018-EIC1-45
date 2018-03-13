package visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeftPanel extends JPanel{
	
	private static final long serialVersionUID = -8909301298283476974L;
	
	//*****************BUTTONS**************************
	private JButton optimization = new JButton();
	private JButton importExport = new JButton();
	private JButton graphics = new JButton();
	private JButton help = new JButton();
	//*****************BUTTONS**************************
	
	//*******************OTHER**************************
	private ActionListener listener;
	private JLabel selector = new JLabel(new ImageIcon(LeftPanel.class.getResource("/selector.png")));
	private JLabel selector1 = new JLabel(new ImageIcon(LeftPanel.class.getResource("/selector.png")));
	private Object current = null;
	private Window window;
	//*******************OTHER**************************
	
	
	public LeftPanel(Window window, int WIDTH) {
		this.window = window;
		setLayout(null);
		
		listener = action_listener();
		
		this.optimization = optimization();
		this.importExport = importExport();
		this.graphics = graphics();
		this.help = help();
		
		selector.setBounds(0, 0, 6, 55);
		selector1.setBounds(289, 0, 6, 55);
		
		setOpaque(false);
		add(selector);
		add(selector1);
		add(optimization);
		add(importExport);
		add(graphics);
		add(help);
		repaint();
	}

	private ActionListener action_listener() {
		ActionListener a = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == optimization){
					if(current != e.getSource()){
						selector.setBounds(0, 0, 6, 55);
						selector1.setBounds(289, 0, 6, 55);
						window.getRight_panel().changeTab("optimization");
					}
					current = e.getSource();
				}else if(e.getSource() == importExport){
					if(current != e.getSource()){
						selector.setBounds(0, 111, 6, 55);
						selector1.setBounds(289, 111, 6, 55);
						selector.setBounds(0, 55, 6, 55);
						selector1.setBounds(289, 55, 6, 55);
						window.getRight_panel().changeTab("importExport");
					}
					current = e.getSource();
				}else if(e.getSource() == graphics){
					if(current != e.getSource()){
						selector.setBounds(0, 111, 6, 55);
						selector1.setBounds(289, 111, 6, 55);
						window.getRight_panel().changeTab("graphics");
					}
					current = e.getSource();
				}else if(e.getSource() == help){
					if(current != e.getSource()){
						selector.setBounds(0, 167, 6, 55);
						selector1.setBounds(289, 167, 6, 55);
						window.getRight_panel().changeTab("help");
					}
					current = e.getSource();
				}
			}
		};
		
		return a;
	}

	
	private JButton optimization() {
		JButton b = new JButton();
		b.setBounds(0, 0, 295, 54);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		b.addActionListener(this.listener);
		return b;
	}

	private JButton importExport() {
		JButton b = new JButton();
		b.setBounds(0, 55, 295, 54);	
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		b.addActionListener(this.listener);
		return b;
	}

	private JButton graphics() {
		JButton b = new JButton();
		b.setBounds(0, 111, 295, 54);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		b.addActionListener(this.listener);
		return b;
	}
	
	private JButton help() {
		JButton b = new JButton();
		b.setBounds(0, 165, 295, 54);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		b.addActionListener(this.listener);
		return b;
	}
	
}