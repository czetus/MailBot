package com.czetus.main;

import java.awt.EventQueue;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class MainFrame {

	private final static Date DATE_EXPIRE = new Date(1461164126000L); //http://www.epochconverter.com/
	
	public static void main(String[] args) {
		
	    try {
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception evt) {}
		
		if( Calendar.getInstance().getTimeInMillis() > DATE_EXPIRE.getTime()){ //przekroczy³ ten czas

			JOptionPane.showMessageDialog(null, "Czas u¿ytkownia aplikacji dobieg³ koñca.\n"+
												"Skontaktuj siê z developerem:"+
												"Jan Danes (czetus@wp.pl)", "Koniec okresu próbnego", JOptionPane.WARNING_MESSAGE);
			System.exit(1337);
		}else{
			long act = Calendar.getInstance().getTimeInMillis();
			long diff = (DATE_EXPIRE.getTime() - act);
			
			int days = (int) (diff / (1000*60*60*24));
			
			JOptionPane.showMessageDialog(null, "Czas u¿ytkownia aplikacji dni "+days,
					 "Do koñca", JOptionPane.WARNING_MESSAGE);
			
		}
		
		//1000 ms * 60(s)*60(min) = 1 godzina
		// dzien = 24 godziny
		//1000 ms * 60(s)*60(min)*24(godz) = 1 dzien
		// a 20 dni
		// 1000 ms * 60(s)*60(min)*24(godz)*20(dni) = 
		EventQueue.invokeLater(new Runnable() {
			
			
			public void run() {
				JFrame frame = new MailBot();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Bot Mail Spammer - czetus");
				
			}
		});
		

	}

}
