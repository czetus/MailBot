package com.czetus.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.naming.AuthenticationException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.inet.editor.HtmlConverter;
import com.inet.html.InetHtmlParser;
import com.inet.html.InetHtmlWriter;

public class MailBot extends JFrame {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 500;
	private final int HEIGHT = 150;
	private JButton wylisj_email ;
//	private JEditorPane htmlEdtior;
//	private Document document ;
//  private JFileChooser szablonChooser;
    private JFileChooser kontaktyChooser;
	private JTextField smtpAddr;
	private JTextField smtpPort;
	private JTextField userName;
	private JTextField passName;
	private JTextField temat;
	private JTextField nadawca;
	private String[] odbiorcy;
	private Editor edytor;
	private SendingActivity sendingActivity;
	private ProgressMonitor progresMonitor;
	private Timer cancel;
	
	
	
	
	static{
        // register JWebEngine for HTML content
        JEditorPane.registerEditorKitForContentType( "text/html", "com.inet.html.InetHtmlEditorKit" );
        JEditorPane.registerEditorKitForContentType( "text/plain", "javax.swing.text.StyledEditorKit" );
    }
    
    
    
    
    
    
	public MailBot(){
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout());
		centreWindow(this);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu file = new JMenu("Plik");
		menuBar.add(file);
		
		JMenuItem openFileCVS = new JMenuItem("Otwórz plik CVS ...");
		
		
		
		openFileCVS.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				kontaktyChooser = new JFileChooser(".");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".cvs .txt files", new String[]{"txt","cvs"});
				kontaktyChooser.setFileFilter(filter);
				kontaktyChooser.addChoosableFileFilter(filter);
				kontaktyChooser.setMultiSelectionEnabled(false);
				int wybor = kontaktyChooser.showOpenDialog(null);
				
				if(wybor == JFileChooser.APPROVE_OPTION){
					if(kontaktyChooser.getSelectedFile().getName().endsWith(".cvs")){
						
						ReadCVS reader = new ReadCVS(kontaktyChooser.getSelectedFile());
						odbiorcy = reader.asStringArray();
						System.out.println(reader.ileWierszy());
//					for(String s : odbiorcy){
//						System.out.println(s);
//					}
						
						
					}else if(kontaktyChooser.getSelectedFile().getName().endsWith(".txt")){
						
					}else{
						JOptionPane.showMessageDialog(null, "Nieobsługiwany format pliku","Błąd", JOptionPane.ERROR_MESSAGE);
					}
					
				}
				
				
			}
		});
		
		JMenuItem exit = new JMenuItem("Wyjdź");
		exit.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		
//		htmlEdtior = new JEditorPane();
//		htmlEdtior.setEditable(true);
//		htmlEdtior.setContentType("text/html");
//		
//		JScrollPane scrollPane = new JScrollPane(htmlEdtior);
//		scrollPane.setBorder(BorderFactory.createEmptyBorder());
//		scrollPane.getViewport().setOpaque(false);
//		scrollPane.setOpaque(false);
//		add(scrollPane,BorderLayout.CENTER);
//		document = htmlEdtior.getDocument();
		

//		try {
//			htmlEdtior.setPage("http://goodmajster.pl/images/allegro_v2/page_with_CSS_1_2.html");
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		
		
		JMenu szablonHTML = new JMenu("Szablon HTML");
		
		JMenuItem openSzablon = new JMenuItem("Otwórz edytor");
		
		
		
		
		openSzablon.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
//				// OBSŁUGA ZDARZENIA OTWARCIA 
//				// JFILEChooser
//				// wybór szablonu  w postaci .html .xhtml etc.
//				//załadowanie go do JEditorPane
//				//JEdtiorPane read funkcja
//				szablonChooser = new JFileChooser(".");
//				FileNameExtensionFilter filter = new FileNameExtensionFilter("HTML files only", "html","xhtml");
//				szablonChooser.setFileFilter(filter);
//				szablonChooser.setMultiSelectionEnabled(false);
//				int wybor = szablonChooser.showOpenDialog(null);
//				
//				if(wybor == JFileChooser.APPROVE_OPTION){
//				
//				try {
//					htmlEdtior.removeAll();
//					
//					htmlEdtior.read(new FileInputStream(szablonChooser.getSelectedFile()), "Wybrany szablon");
//				} catch (FileNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			
//				
//				document = htmlEdtior.getDocument();
//
//				}

				edytor = new Editor();
				//editor.getEditor().doc
				
				
			}
		});
		
		
		
		
		
		szablonHTML.add(openSzablon);
		menuBar.add(szablonHTML);
		
		

		
		
		JPanel pola = new JPanel(new GridLayout(0,4));
		wylisj_email = new JButton("Wyślij");
		
		

		
		pola.add(new JLabel("SMTP address:")); smtpAddr = new JTextField(15);
		pola.add(smtpAddr);
		pola.add(new JLabel("Port:")); smtpPort = new JTextField(3);
		pola.add(smtpPort);		
		pola.add(new JLabel("User:")); userName = new JTextField(10);
		pola.add(userName);
		pola.add(new JLabel("Password:")); passName = new JPasswordField();
		pola.add(passName);
		pola.add(new JLabel("Temat Wiadomości:")); temat = new JTextField(10);
		pola.add(temat);
		pola.add(new JLabel());
		pola.add(new JLabel());
		pola.add(new JLabel("Nadawca(jan@o2.pl):")); nadawca = new JTextField(10);
		pola.add(nadawca);
		pola.add(new JLabel());
		pola.add(wylisj_email);
		
	
		
		wylisj_email.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(smtpAddr.getText().trim().isEmpty()) 
						JOptionPane.showMessageDialog(null, "Podaj SMTP adres", "Błąd", JOptionPane.ERROR_MESSAGE);
						else if (smtpPort.getText().trim().isEmpty()) 
							JOptionPane.showMessageDialog(null, "Podaj SMTP port", "Błąd", JOptionPane.ERROR_MESSAGE);
							else if (userName.getText().trim().isEmpty())
								JOptionPane.showMessageDialog(null, "Podaj nazwę użytkownika", "Błąd", JOptionPane.ERROR_MESSAGE);
								else if ( passName.getText().trim().isEmpty())
									JOptionPane.showMessageDialog(null, "Podaj hasło", "Błąd", JOptionPane.ERROR_MESSAGE);
									else if (temat.getText().trim().isEmpty())
										JOptionPane.showMessageDialog(null, "Podaj temat wiadomości", "Błąd", JOptionPane.ERROR_MESSAGE);
									else if (nadawca.getText().trim().isEmpty())
										JOptionPane.showMessageDialog(null, "Podaj nadawcę wiadomości z tego samego serwera", "Błąd", JOptionPane.ERROR_MESSAGE);
										else if ( edytor == null || edytor.getEditor().getText().length() == 0)
											JOptionPane.showMessageDialog(null, "Dokument nie zawiera żadnej treści", "Błąd", JOptionPane.ERROR_MESSAGE);
										else if ( odbiorcy == null || odbiorcy.length == 0 )
											JOptionPane.showMessageDialog(null, "Brak wybranych odbiórców wiadomości\nPlik -> Otwórz cvs ...", "Błąd", JOptionPane.ERROR_MESSAGE);
				else{
					//System.out.println("wykona wyslanie maila");
			 		sendingActivity = new SendingActivity(0, odbiorcy.length);// zamiast (... 0, size) 'wielkosc' by nie zrywa³o po³¹czenia i zapisywa³o jednorazowo mniej danych
			 		sendingActivity.execute();
			 		
			 		progresMonitor = new ProgressMonitor(MailBot.this, "Wysyłanie ... Spokojnie\nZrób stażyście kawę!;)", null,
			 				0, odbiorcy.length); 
			 		
					cancel.start();
					
				}
				
				
				
				
			}
		});
		
		cancel = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (progresMonitor.isCanceled()) {
					sendingActivity.cancel(true);
				} else if (sendingActivity.isDone()) {
					progresMonitor.close();
				} else {
					progresMonitor.setProgress(sendingActivity.getProgress());
				}
			}
		});
		
		
		file.add(openFileCVS);
		file.addSeparator();
		file.add(exit);
		
		
		add(pola,BorderLayout.SOUTH);
		pack();
		setJMenuBar(menuBar);
	}//constructor
	
	
	
	class SendingActivity extends SwingWorker<Integer , Integer>{
		private int max;  // wielkosc listy w trakcie zapisu
		private int iter;
		private int begin;
		
		public SendingActivity(int begin ,int max) {
			this.max = max;
			this.begin = begin;
			iter = 0;
		}
		@Override
		protected Integer doInBackground() throws Exception {
			
			
			for(int i = begin; i < max;i++){
			//	Dlug d = dluznik.getLista().get(i);
			//	service.persistOrUpdate(d);
				check(userName.getText(),passName.getText(),smtpAddr.getText(),smtpPort.getText(),temat.getText(),edytor.getEditor().getText(),odbiorcy[i],nadawca.getText());
				iter++;
				setProgress(iter);
				Thread.sleep(3000);
			}
		/*		
			for(Dlug d : dluznik.getLista()){
				service.persistOrUpdate(d);
				iter++;
				setProgress(iter);
			}
			*/	
			
			
			return null;
		}
		
		
	}
	
	/*
	 * wiemy że wszystkie pola są wypełnione
	 * funkcja ta wysła spam do okreslonej przez tablice z kontaktami
	 * osobami etc.
	 */
	public void wyslij_spam(){
		
		for(String kontakt : odbiorcy){
			try {
				Thread.sleep(3000);
				
				
				//check(userName.getText(),passName.getText(),smtpAddr.getText(),smtpPort.getText(),temat.getText(),edytor.getEditor().getText(),kontakt);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		//check(userName.getText(),passName.getText(),smtpAddr.getText(),smtpPort.getText(),temat.getText(),htmlEdtior.getText(),odbiorca);
	}
	
	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth()) / 3);
	    int y = (int) ((dimension.getHeight()) / 3);
	    frame.setLocation(x, y);
	}

	
	//String username,String password, String smtpAdr,String smtpPort,String pop3Adr,String pop3Port
	public static void check(final String username,final String password,
			String smtpAdr,String smtpPort,String temat,
			Object edytor, String odbiorca,String nadawca)
		   {
		
        Properties props = System.getProperties();
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", smtpAdr);
        props.put("mail.smtp.ssl.enable", "true");


        javax.mail.Authenticator auth = null;
        auth = new javax.mail.Authenticator() {
            
            public javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        };


		// Get session
		Session session = Session.getInstance(props, auth);

		// Define message
		MimeMessage message = new MimeMessage(session);

		
		// Set the from address
		try {
			
			message.setFrom(new InternetAddress(nadawca));
		// Set the to address
			
			//message.addRecipient(Message.RecipientType.TO,new InternetAddress("czetus@wp.pl"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(odbiorca));
			
		// Set the subject
			message.setSubject(temat);
		// Set the content
			message.setContent(edytor,"text/html; charset=ISO-8859-1");
		//	message.addHeader("Content-Type", "text/html");
		//	message.setText(edytor.toString(), "ISO-8859-1");
		// Send message
			System.out.println("Wysyłam!");
			Transport.send(message);
		}catch (AuthenticationFailedException ex){
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd w trakcie logowania do poczty", JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
}
