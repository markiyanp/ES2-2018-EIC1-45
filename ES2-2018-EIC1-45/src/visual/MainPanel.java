package visual;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class MainPanel  extends JPanel{

	private static final long serialVersionUID = -5317356868846370620L;
	private Image background = Toolkit.getDefaultToolkit().createImage(Window.class.getResource("/background.png"));
	private Window window;

	
	public MainPanel(Window window) {
		this.window = window;
		setLayout(null);
		
		JPanel top_panel = window.getTop_panel();
		JPanel left_panel = window.getLeft_panel();
		JPanel right_panel = window.getRight_panel();
		JPanel bott_panel = window.getBott_panel();
		
		left_panel.setBounds(0, 58, 295, 612);
		top_panel.setBounds(0,0,1140,57);
		right_panel.setBounds(296, 58, 838,612);
		bott_panel.setBounds(0, 630, 295, 40);
		
		add(bott_panel);
		add(right_panel);
		add(left_panel);
		add(top_panel);
		
	}
	
	public void paintComponent(Graphics page){
	    super.paintComponent(page);
	    int h = background.getHeight(null);
	    int w = background.getWidth(null);

	    if ( w > this.getWidth() )
	    {
	        background = background.getScaledInstance( getWidth(), -1, Image.SCALE_DEFAULT );
	        h = background.getHeight(null);
	    }

	    if ( h > this.getHeight() )
	    {
	        background = background.getScaledInstance( -1, getHeight(), Image.SCALE_DEFAULT );
	    }
	    int x = (getWidth() - background.getWidth(null)) / 2;
	    int y = (getHeight() - background.getHeight(null)) / 2;

	    page.drawImage( background, x, y, null );
	    repaint();
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}
}