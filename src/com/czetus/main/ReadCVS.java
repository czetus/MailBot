package com.czetus.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * Za³o¿enie klasy:
 * odczytuje podany plik w konstruktorze
 * dostarcza metod do zapisu w tablicy czy HashMapie
 * 
 */
public class ReadCVS {
	BufferedReader reader  = null;
	private File selectedFile;
	
	public ReadCVS(File wybranyPlik){
		this.selectedFile = wybranyPlik;
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}
	
	
	public String[] asStringArray (){
		String[] tablica = new String[ileWierszy()];
		String linia;
		int i = 0;
		try {
			reader = new BufferedReader(new FileReader(selectedFile));
				
		
			while((linia = reader.readLine()) != null){
				tablica[i] = linia;
				i++;	
			}
			
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch(IOException e){}
			}
		}
		return tablica;
	}
	
	public int ileWierszy(){
		int ile = 0;
		
		try {
			reader = new BufferedReader(new FileReader(selectedFile));
		
			while(reader.readLine() != null){
				ile++;
			}
		
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ile;
	}

}
