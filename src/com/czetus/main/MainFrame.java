package com.czetus.main;

import java.awt.EventQueue;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class MainFrame {

	private  static final Date DATE_EXPIRE = new Date(1519804711000L); //http://www.epochconverter.com/

	public static void main(String[] args) {
		
	    try {
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception evt) {}
		
		if( Calendar.getInstance().getTimeInMillis() > DATE_EXPIRE.getTime()){ //przekroczony ten czas

			JOptionPane.showMessageDialog(null, StringConsts.TIME_OUT, StringConsts.TIME_OUT, JOptionPane.WARNING_MESSAGE);
			System.exit(1337);
		}else{
			long act = Calendar.getInstance().getTimeInMillis();
			long diff = (DATE_EXPIRE.getTime() - act);
			
			int days = (int) (diff / (1000*60*60*24));
			
			JOptionPane.showMessageDialog(null, String.format(StringConsts.INFO_ABOUT_TIMEOUT,days),
					 "Do końca okresu próbnego pozostało ...", JOptionPane.WARNING_MESSAGE);
			
		}

		EventQueue.invokeLater(()->{
				JFrame frame = new MailBot();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Bot Mail Spammer - czetus");
			}
		);
		

	}

}
