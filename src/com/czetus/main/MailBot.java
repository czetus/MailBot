package com.czetus.main;

import java.awt.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MailBot extends JFrame
{

    private static final long serialVersionUID = 1L;
    private static Properties properties;
    private final int WIDTH = 600;
    private final int HEIGHT = 170;
    private JButton wylisj_email;
    private JFileChooser kontaktyChooser;
    //private JTextField smtpAddr;
    private JComboBox<String> smtpAddr;
    private JTextField userName;
    private JTextField passName;
    private JTextField temat;
    private JTextField nadawca;
    private String[] odbiorcy;
    private Editor edytor;
    private SendingActivity sendingActivity;
    private ProgressMonitor progresMonitor;
    private Timer cancel;

    static {
        // register JWebEngine for HTML content
        JEditorPane.registerEditorKitForContentType("text/html", "com.inet.html.InetHtmlEditorKit");
        JEditorPane.registerEditorKitForContentType("text/plain", "javax.swing.text.StyledEditorKit");
    }

    public MailBot()
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // setLayout(new BorderLayout());
        centreWindow(this);

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("Plik");
        menuBar.add(file);

        JMenuItem openFileCVS = new JMenuItem("Otwórz plik CSV...");

        openFileCVS.addActionListener(event ->
                {
                    kontaktyChooser = new JFileChooser(".");
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv .txt files", new String[]{"txt", "csv"});
                    kontaktyChooser.setFileFilter(filter);
                    kontaktyChooser.addChoosableFileFilter(filter);
                    kontaktyChooser.setMultiSelectionEnabled(false);
                    int wybor = kontaktyChooser.showOpenDialog(null);

                    if (wybor == JFileChooser.APPROVE_OPTION) {
                        if (kontaktyChooser.getSelectedFile().getName().endsWith(".csv")) {

                            ReadCSV reader = new ReadCSV(kontaktyChooser.getSelectedFile());
                            odbiorcy = reader.asStringArray();
                            //System.out.println(reader.ileWierszy());

                        } else if (kontaktyChooser.getSelectedFile().getName().endsWith(".txt")) {

                        } else {
                            JOptionPane.showMessageDialog(null, "Nieobsługiwany format pliku", "Błąd", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                }
        );

        JMenuItem exit = new JMenuItem("Wyjdź");
        exit.addActionListener(event -> System.exit(0));

        JMenu szablonHTML = new JMenu("Szablon HTML");

        JMenuItem openSzablon = new JMenuItem("Otwórz edytor");

        openSzablon.addActionListener(event -> edytor = new Editor());

        szablonHTML.add(openSzablon);
        menuBar.add(szablonHTML);

        JPanel centerPanel = new JPanel(new GridLayout(3, 4));

        centerPanel.add(new JLabel("Login:"));
        userName = new JTextField(10);
        centerPanel.add(userName);

        centerPanel.add(new JLabel("SMTP address:"));
        smtpAddr = new JComboBox<String>(new String[]{"smtp.wp.pl", "smtp.gmail.com",
                "smtp.poczta.onet.pl", "poczta.interia.pl",
                "smtp.mail.yahoo.com"});
        smtpAddr.setEditable(true);
        centerPanel.add(smtpAddr);

        centerPanel.add(new JLabel("Password:"));
        passName = new JPasswordField();
        centerPanel.add(passName);

        centerPanel.add(new JLabel("Temat Wiadomości:"));
        temat = new JTextField(10);
        centerPanel.add(temat);

        centerPanel.add(new JLabel("Nadawca(exmple@o2.pl):"));
        nadawca = new JTextField(10);
        centerPanel.add(nadawca);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        wylisj_email = new JButton("Wyślij");
        buttonPanel.add(wylisj_email);


        wylisj_email.addActionListener(evt ->
                {
                    if (anyInputIsEmpty()) {
                        JOptionPane.showMessageDialog(null, "Wszystkie Pola muszą być wypełnione, oraz wybrane kontakty \nPlik -> Otwórz cvs ...", "Błąd", JOptionPane.ERROR_MESSAGE);
                    } else {
                        sendingActivity = new SendingActivity(0, odbiorcy.length);
                        sendingActivity.execute();

                        progresMonitor = new ProgressMonitor(MailBot.this, "Wysyłanie wiadomości ... Spokojnie", null, 0, odbiorcy.length);

                        cancel.start();
                    }
                }
        );

        cancel = new Timer(500, evt ->
        {
            if (progresMonitor.isCanceled()) {
                sendingActivity.cancel(true);
            } else if (sendingActivity.isDone()) {
                progresMonitor.close();
            } else {
                progresMonitor.setProgress(sendingActivity.getProgress());
            }
        }
        );


        file.add(openFileCVS);
        file.addSeparator();
        file.add(exit);

        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel mainPanel = new JPanel(new BorderLayout());

        add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.NORTH);
        add(mainPanel);
        pack();
        setJMenuBar(menuBar);
        setResizable(false);
    }//constructor

    private boolean anyInputIsEmpty()
    {
        if (((String)smtpAddr.getSelectedItem()).isEmpty() || userName.getText().trim().isEmpty() ||
                passName.getText().trim().isEmpty() || temat.getText().trim().isEmpty() ||
                nadawca.getText().trim().isEmpty() || edytor == null ||
                edytor.getEditor().getText().length() == 0 ||
                odbiorcy == null || odbiorcy.length == 0
            )
            return true;

        return false;
    }

    class SendingActivity extends SwingWorker<Integer, Integer>
    {
        private int max;  // wielkosc listy w trakcie zapisu
        private int iter;
        private int begin;

        public SendingActivity(int begin, int max)
        {
            this.max = max;
            this.begin = begin;
            iter = 0;
        }

        @Override
        protected Integer doInBackground() throws Exception
        {
            for (int i = begin; i < max; i++) {
                //sendMessage(userName.getText(), passName.getText(), smtpAddr.getText(), smtpPort.getText(), temat.getText(), edytor.getEditor().getText(), odbiorcy[i], nadawca.getText());
                User newUser = new User(userName.getText(), passName.getText(), new EmailAccount((String) smtpAddr.getSelectedItem()));

                MyMessage myMessage = MyMessage
                        .builder()
                        .nadawca(nadawca.getText())
                        .odbiorca(odbiorcy[i])
                        .temat(temat.getText())
                        .build();

                prepareProperties(newUser);
                sendMessage(newUser, myMessage, edytor.getEditor().getText());

                iter++;
                setProgress(iter);
                Thread.sleep(3000);
            }
            return null;
        }

    }

    public static void centreWindow(Window frame)
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth()) / 3);
        int y = (int) ((dimension.getHeight()) / 3);
        frame.setLocation(x, y);
    }

    private static void prepareProperties(User userData)
    {
        properties = new Properties();
        properties.put("mail.smtp.user", userData.getName());
        properties.put("mail.smtp.password", userData.getPassword());
        properties.put("mail.smtp.auth", "true");
        System.out.println(userData.getEmailAccount().getSmtpAddres());
        properties.put("mail.smtp.host", userData.getEmailAccount().getSmtpAddres());
        properties.put("mail.smtp.ssl.enable", "true");
    }

    public static void sendMessage(User userData, MyMessage myMessageToSend, Object edytor)
    {
        Authenticator auth = getAuthentication(userData);

        Session session = Session.getInstance(properties, auth);
        MimeMessage message = new MimeMessage(session);

        // Set the from address
        try {
            message.setFrom(new InternetAddress(myMessageToSend.getNadawca()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(myMessageToSend.getOdbiorca()));
            message.setSubject(myMessageToSend.getTemat());

            MimeMultipart multipart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(edytor, "text/html");
            multipart.addBodyPart(bodyPart);

            message.setContent(multipart);
            Transport.send(message);
        } catch (AuthenticationFailedException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Błąd w trakcie logowania do poczty", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private static Authenticator getAuthentication(User user)
    {
        return new Authenticator()
        {
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(user.getName(), user.getPassword());
            }
        };
    }
}
