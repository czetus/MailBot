package com.czetus.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * Za?o?enie klasy:
 * odczytuje podany plik w konstruktorze
 * dostarcza metod do zapisu w tablicy czy HashMapie
 *
 */
public class ReadCSV
{
    BufferedReader reader = null;
    private File selectedFile;

    public ReadCSV(File wybranyPlik)
    {
        this.selectedFile = wybranyPlik;
    }

    private File getSelectedFile()
    {
        return selectedFile;
    }

    private void setSelectedFile(File selectedFile)
    {
        this.selectedFile = selectedFile;
    }

    public String[] asStringArray()
    {
        String[] tablica = new String[ileWierszy()];
        String linia;
        int i = 0;
        try {
            reader = new BufferedReader(new FileReader(selectedFile));

            while ((linia = reader.readLine()) != null) {
                tablica[i] = linia;
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return tablica;
    }

    private int ileWierszy()
    {
        int ile = 0;

        try {
            reader = new BufferedReader(new FileReader(selectedFile));

            while (reader.readLine() != null) {
                ile++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ile;
    }

}
