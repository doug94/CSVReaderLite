/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvreaderlite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Douglas
 */
public class Dialog {

    public Dialog() {
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        fc.setFileFilter(filter);
        SortedMap<String, List<String>> mapa = null;
        int result = fc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) 
            mapa = readFile(fc.getSelectedFile());
        for(String s: mapa.keySet())
            System.out.println(s);
    }

    private SortedMap<String, List<String>> readFile(File file) {
        SortedMap<String, List<String>> mapa = new TreeMap<>();
        try (BufferedReader br
                = new BufferedReader(new FileReader(file))) {
            String str = br.readLine();
            String tmp = "";
            // Ler primeira linha do arquivo e colocar as colunas do mapa
            List<String> lst = separarDados(str);
            // Chaves estão desordenadas
            for (String s : lst) {
                mapa.put(s, new ArrayList<>());
            }
            Set colunas = mapa.keySet();
            // ler da segunda linha em diante e mapear os dados nas respectivas colunas
            str = br.readLine();
            while (str != null) {
                lst = separarDados(str);
                Iterator it = colunas.iterator();
                for(String s: lst)
                    mapa.get(it.next()).add(s);
                str = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapa;
    }

    private List<String> separarDados(String str) {
        String tmp = "";
        List<String> lst = new ArrayList<>();
        if (str != null) {
            for(char c: str.toCharArray()){
                if(c == ';'){
                    lst.add(tmp);
                    tmp = "";
                }
                else
                    tmp += c;
            }
            lst.add(tmp);
        }
        return lst;
    }
}
