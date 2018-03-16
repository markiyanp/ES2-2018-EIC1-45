package visual;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import org.apache.commons.mail.EmailException;

import core.Article;
import core.User;
import email.EMail_Tools;

public class HelpTab extends JPanel{

	private static final long serialVersionUID = 1L;


	//*************************************GENERAL FIELDS****************************************
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	private TitledBorder faq_area_border = BorderFactory.createTitledBorder(blackline, "FAQ");
	private TitledBorder help_area_border = BorderFactory.createTitledBorder(blackline, "HELP");

	private final int faq_panel_width = 805;
	private final int faq_panel_height = 350;

	private final int help_panel_width = 805;
	private final int help_panel_height = 230;


	//************************************FAQ FIELDS********************************************
	private JList<String> list;
	private JTextPane textPane;
	private JScrollPane scrollpane1;
	private JScrollPane scrollpane2;
	private HashMap<String,Article> current_prev;
	private JPanel help_panel = new JPanel();
	private JPanel faq_panel = new JPanel();
	private final File dir = new File("Resources/faq_docs");
	private File[] files;
	private ArrayList<Article> articles;
	//************************************FAQ FIELDS********************************************



	//************************************HELP FIELDS********************************************
	private JLabel help_to_label = new JLabel("TO:");
	private JLabel help_subj_label = new JLabel("SUBJ:");
	private JLabel help_from_label = new JLabel("FROM:");
	private JLabel help_text_label = new JLabel("TEXT:");
	private JLabel help_atachment_label = new JLabel("ATTACH:");
	private JLabel help_passwd_label = new JLabel("PASS:");

	private JTextField help_to_field = new JTextField();
	private JTextField help_subj_field = new JTextField();
	private JTextField help_from_field = new JTextField();
	private JTextField help_attachment_field  = new JTextField();
	private JTextField help_text_field  = new JTextField();
	private JPasswordField help_passwd_field = new JPasswordField();
	private JButton help_choose_field = new JButton("...");
	private JButton help_send_field = new JButton("SEND QUESTION");
	private String[] possible_subj = {"General Issue","XML Issue","User registration Issue","Problem registration Issue",
			"Variable Issue", "Algorithm Issue", "Graphs Issue", "Other Issue", "Other Inquiry"};
	private JComboBox<String> help_possibleSubj_field = new JComboBox<String>(possible_subj);
	//************************************HELP FIELDS********************************************


	//*************************************GENERAL FIELDS****************************************

	public HelpTab() {
		setBackground(Color.LIGHT_GRAY);
		help();
		faq();
		try {loadArticles();} catch (InterruptedException e) {e.printStackTrace();}
	}


	//TODO make list scrollable
	private void faq() {
		faq_panel.setBorder(faq_area_border);
		faq_panel.setBounds(15, 250, faq_panel_width, faq_panel_height);
		faq_panel.setOpaque(false);
		faq_panel.setLayout(null);

		this.list = new JList<String>();
		this.scrollpane1 = new JScrollPane(list);
		this.scrollpane1.setOpaque(false);

		this.textPane = new JTextPane();
		this.textPane.setEditable(false);
		this.scrollpane2 = new JScrollPane(textPane);

		this.scrollpane2.setBounds(320, 25, 470,310);
		this.list.setBounds(15, 25, 300, 310);

		faq_panel.add(scrollpane2);
		faq_panel.add(list);

		list.addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent ev){
				if (!ev.getValueIsAdjusting()) {
					try {
						if(list.getSelectedValue()!= null){
							loadSelectedArticle(list.getSelectedValue());
						}
					} catch (IOException | BadLocationException  e) {
						e.printStackTrace();
					}
				}
			}
		});
		add(faq_panel);
	}


	private void help() {
		help_panel.setBorder(help_area_border);
		help_panel.setBounds(15, 10, help_panel_width, help_panel_height);
		help_panel.setOpaque(false);
		help_panel.setLayout(null);

		help_to_label.setBounds(30, 20, 40, 25);
		help_subj_label.setBounds(18, 90, 40, 25);
		help_from_label.setBounds(15, 55, 40, 25);
		help_text_label.setBounds(360, 20, 50, 25);

		help_possibleSubj_field.setBounds(60, 90, 270, 25);
		help_to_field.setBounds(60, 20, 270, 25);
		help_subj_field.setBounds(1, 1, 1, 1);
		help_from_field.setBounds(60, 55, 270, 25);
		help_text_field.setBounds(400, 25, 380, 145);

		help_send_field.setBounds(90, 170, 200, 30);
		help_send_field.addActionListener(listener());

		help_atachment_label.setBounds(340, 185, 70, 25);
		help_passwd_label.setBounds(17, 125, 270, 25);
		help_attachment_field.setBounds(400, 185, 340, 25);
		help_passwd_field.setBounds(60, 125, 270, 25);

		help_choose_field.setBounds(748, 185, 30, 25);
		help_choose_field.addActionListener(listener());

		//TODO This should refer to the Admin E-Mail on config.xml instead of EMail_Tools.getAdminEmail().
		help_to_field.setText(EMail_Tools.getAdminEmail());
		help_to_field.setEditable(false);

		//TODO This should refer to the User E-Mail on OptimizationTab instead of User.getEmailAddr().
		User u = new User("default", "group45.dummy.user.1@gmail.com");
		help_from_field.setText(u.getEmailAddr());
		help_from_field.setEditable(false);

		help_panel.add(help_choose_field);
		help_panel.add(help_atachment_label);
		help_panel.add(help_passwd_label);
		help_panel.add(help_attachment_field);
		help_panel.add(help_passwd_field);
		help_panel.add(help_to_label);
		help_panel.add(help_subj_label);
		help_panel.add(help_from_label);
		help_panel.add(help_text_label);
		help_panel.add(help_possibleSubj_field);
		help_panel.add(help_to_field);
		help_panel.add(help_subj_field);
		help_panel.add(help_from_field);
		help_panel.add(help_text_field);
		help_panel.add(help_send_field);

		add(help_panel);

	}


	private ActionListener listener(){
		ActionListener list = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == help_choose_field){
					JFileChooser jc = new JFileChooser();
					int returnVal = jc.showOpenDialog(help_panel);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						help_attachment_field.setText(jc.getSelectedFile().getAbsolutePath());
					}
				//TODO IMPLEMENTAR A FUNCIONALIDADE DE ADICIONAR ATTACHMENT!!!

				}else if(e.getSource() == help_send_field){
					try {
						//TODO !!! SE O EMAIL NAO FOR ENVIADO POR CAUSA DA PASSE, O ERRO PODE ESTAR AQUI!!!  help....to STRING()
						User u = new User("default", "group45.dummy.user.1@gmail.com");
	
						EMail_Tools.sendMail(u.getEmailAddr(),
								String.copyValueOf(help_passwd_field.getPassword()), 
								EMail_Tools.getAdminEmail(), 
								null,
								help_possibleSubj_field.getSelectedItem().toString(), 
								help_text_field.getText(), 
								help_attachment_field.getText());
					} catch (EmailException e1) {
						e1.printStackTrace();
					}

				}

			}
		};
		return list;
	}



	private void loadSelectedArticle(String selectedValue) throws IOException, BadLocationException{
		if(this.current_prev.containsKey(selectedValue)){
			textPane.setText("");
			Article art = this.current_prev.get(selectedValue);
			textPane.setContentType("text/html");
			textPane.setText("<html><b>" +art.getHeader() + "</b></html>");
			appendString("\n");

			for(String s : art.getArticle()){
				if(art.getArticle().indexOf(s) != 0)
					appendString(s + "\n");
			}
		}
		textPane.setCaretPosition(0);
	}


	public void appendString(String str) throws BadLocationException{
		StyledDocument document = (StyledDocument) textPane.getDocument();
		document.insertString(document.getLength(), str, null);
	}


	private void loadArticles() throws InterruptedException{
		this.current_prev = new HashMap<>();
		this.articles = new ArrayList<>();
		this.files = dir.listFiles();
		for(File f : files){
			articles.add(new Article(f));
		}
		String[] cat_array = new String[this.articles.size()];
		for(Article art : this.articles){
			cat_array[this.articles.indexOf(art)] = art.getHeader();
			this.current_prev.put(art.getHeader(), art);
		}
		this.scrollpane1.getViewport().repaint();
		this.list.setListData(cat_array);
	}

}