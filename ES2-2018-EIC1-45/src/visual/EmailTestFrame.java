package visual;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;

/**Frame that supports the GUI implementation for the E-Mail functionality.
 * 
 * @author afgos-iscteiulpt & pvmpa-iscteiulpt
 *
 */
public class EmailTestFrame {

	private JFrame frame;
	private JTextField toField;
	private JTextField ccField;
	private JLabel titleLabel;
	private JTextField subjectField;
	private JTextArea bodyText;
	private JPanel attachmentPanel;
	private JPanel infoPanel;
	private JLabel attachmentLabel;
	private JTextField attachementField;
	private JButton chooseButton;
	private JLabel passwordLabel;
	private JTextField passwordField;
	private JScrollPane bodyScroll;
	

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
		
		infoPanel = new JPanel();
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
		
		subjectField = new JTextField();
		infoPanel.add(subjectField);
		subjectField.setColumns(10);
		
		passwordLabel = new JLabel("Password: (Temp)");
		infoPanel.add(passwordLabel);
		
		passwordField = new JTextField();
		infoPanel.add(passwordField);
		passwordField.setColumns(10);
		
		bodyText = new JTextArea();
		bodyScroll = new JScrollPane(bodyText);
		frame.getContentPane().add(bodyScroll, BorderLayout.CENTER);
		
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

	public JTextField getSubjectField() {
		return subjectField;
	}

	public JTextArea getBodyText() {
		return bodyText;
	}

	public JTextField getAttachementField() {
		return attachementField;
	}

	public JTextField getPasswordField() {
		return passwordField;
	}

}
