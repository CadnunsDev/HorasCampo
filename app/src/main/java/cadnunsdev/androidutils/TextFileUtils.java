package cadnunsdev.androidutils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tiago Silva on 09/10/2016.
 */
public class TextFileUtils {
    public static String[] GetLinesFromFile(String filePath){

        ArrayList<String> linhas = new ArrayList<>();
         try {
            FileReader arq = new FileReader(filePath);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();
             while (linha != null){
                 linhas.add(linha);
                 linha = lerArq.readLine();
             }
        } catch (FileNotFoundException ex){
            ex.getStackTrace();
        } catch (IOException ex){
            ex.getStackTrace();
        }
        return (String[])linhas.toArray();
    }
}
