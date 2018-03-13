package visual;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;

public class EmailTestFrame {

	private JFrame frame;
	private JTextField toField;
	private JTextField ccField;
	private JLabel titleLabel;
	private JTextField titleField;
	private JTextArea textArea;
	private JPanel attachmentPanel;
	private JLabel attachmentLabel;
	private JTextField attachementField;
	private JButton chooseButton;
	private JLabel passwordLabel;
	private JTextField passwordField;

	/**
	 * Launch the application.
	 * V0.01 Made in 20m, do not expect much of it
	 * 
	 * There is no scroll in textArea too
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmailTestFrame window = new EmailTestFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmailTestFrame() {
		initialize();
	}
	
	private void changeAttachmentPath() {
		JFileChooser jc = new JFileChooser();
		int returnVal = jc.showOpenDialog(frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			attachementField.setText(jc.getSelectedFile().getAbsolutePath());
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 718, 458);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel infoPanel = new JPanel();
		frame.getContentPane().add(infoPanel, BorderLayout.NORTH);
		infoPanel.setLayout(new GridLayout(4, 2, 0, 0));
		
		JLabel toLabel = new JLabel("To:");
		infoPanel.add(toLabel);
		
		toField = new JTextField();
		infoPanel.add(toField);
		toField.setColumns(10);
		
		JLabel ccLabel = new JLabel("Cc:");
		infoPanel.add(ccLabel);
		
		ccField = new JTextField();
		infoPanel.add(ccField);
		ccField.setColumns(10);
		
		titleLabel = new JLabel("Title:");
		infoPanel.add(titleLabel);
		
		titleField = new JTextField();
		infoPanel.add(titleField);
		titleField.setColumns(10);
		
		passwordLabel = new JLabel("Password: (Temp)");
		infoPanel.add(passwordLabel);
		
		passwordField = new JTextField();
		infoPanel.add(passwordField);
		passwordField.setColumns(10);
		
		textArea = new JTextArea();
		frame.getContentPane().add(textArea, BorderLayout.CENTER);
		
		attachmentPanel = new JPanel();
		frame.getContentPane().add(attachmentPanel, BorderLayout.SOUTH);
		attachmentPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		attachmentLabel = new JLabel("Attachment:");
		attachmentPanel.add(attachmentLabel);
		
		attachementField = new JTextField();
		attachmentPanel.add(attachementField);
		attachementField.setColumns(10);
		
		chooseButton = new JButton("Choose");
		chooseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeAttachmentPath();
			}
		});
		attachmentPanel.add(chooseButton);
	
	}

	public JTextField getToField() {
		return toField;
	}

	public JTextField getCcField() {
		return ccField;
	}

	public JTextField getTitleField() {
		return titleField;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public JTextField getAttachementField() {
		return attachementField;
	}

	public JTextField getPasswordField() {
		return passwordField;
	}

}
