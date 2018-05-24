package visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LeftPanel extends JPanel {

	private static final long serialVersionUID = -8909301298283476974L;

	// *****************BUTTONS**************************
	private JButton optimization = new JButton();
	private JButton importExport = new JButton();
	private static JButton graphics = new JButton();
	private JButton help = new JButton();
	// *****************BUTTONS**************************

	// *******************OTHER**************************
	private ActionListener listener;
	private JLabel selector = new JLabel(new ImageIcon(LeftPanel.class.getResource("/selector.png")));
	private JLabel selector1 = new JLabel(new ImageIcon(LeftPanel.class.getResource("/selector.png")));
	private Object current = null;
	private static Window window;
	private static JProgressBar pb;
	private static int MAX = 100;
	// *******************OTHER**************************

	public LeftPanel(Window window, int WIDTH) {
		this.window = window;
		setLayout(null);
		
		listener = action_listener();

		this.optimization = optimization();
		this.graphics = graphics();
		this.help = help();

		selector.setBounds(0, 0, 6, 55);
		selector1.setBounds(289, 0, 6, 55);

		progressBar();
		setProgress(0);
		setOpaque(false);
		add(selector);
		add(selector1);
		add(optimization);
		add(importExport);
		add(graphics);
		add(help);
		
		graphics.setEnabled(false);
		repaint();
	}

	private void progressBar() {
		pb = new JProgressBar();
		pb.setMinimum(0);
		pb.setMaximum(MAX);
		pb.setStringPainted(true);

		pb.setBounds(10, 580, 275, 25);
		add(pb);
	}

	public static void setProgress(int progress) {
		if (progress <= MAX) {
			pb.setValue(progress);
		} else {
			System.out.println(progress);
		}
		
		if(progress == 100) {
			graphics.setEnabled(true);
		}
	}

	private ActionListener action_listener() {
		ActionListener a = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == optimization) {
					if (current != e.getSource()) {
						selector.setBounds(0, 0, 6, 55);
						selector1.setBounds(289, 0, 6, 55);
						window.getRight_panel().changeTab("optimization");
					}
					current = e.getSource();
				} else if (e.getSource() == graphics) {
					if (current != e.getSource()) {
						selector.setBounds(0, 111, 6, 55);
						selector1.setBounds(289, 111, 6, 55);
						selector.setBounds(0, 55, 6, 55);
						selector1.setBounds(289, 55, 6, 55);
						window.getRight_panel().changeTab("graphics");
					}
					current = e.getSource();
				} else if (e.getSource() == help) {
					if (current != e.getSource()) {
						selector.setBounds(0, 111, 6, 55);
						selector1.setBounds(289, 111, 6, 55);
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

	private JButton graphics() {
		JButton b = new JButton();
		b.setBounds(0, 55, 295, 54);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		b.addActionListener(this.listener);
		return b;
	}

	private JButton help() {
		JButton b = new JButton();
		b.setBounds(0, 111, 295, 54);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		b.addActionListener(this.listener);
		return b;
	}

}