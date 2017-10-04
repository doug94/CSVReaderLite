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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Douglas
 */
public class Dialog extends JPanel {

    public Dialog() {
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        fc.setFileFilter(filter);
        List<List<String>> mapa = null;
        int result = fc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) 
            mapa = readFile(fc.getSelectedFile());
//        for(List<String> l: mapa){
//            System.out.println("Coluna " + l.get(0) + ":");
//            for(int i = 1; i < l.size(); i++)
//                System.out.println(l.get(i));
//        }
        String[] colunas = new String[mapa.size()];
        for(int i = 0; i < mapa.size(); i++)
            colunas[i] = mapa.get(i).get(0);
        String[][] dados = new String[mapa.size()][mapa.get(0).size()];
        for(int i = 0; i < mapa.size(); i++)
            for(int j = 1; j < mapa.get(0).size(); j++)
                dados[i][j] = mapa.get(i).get(j);
        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);
        
    }

    private List<List<String>> readFile(File file) {
        //SortedMap<String, List<String>> mapa = new TreeMap<>();
        List<List<String>> mapa = new ArrayList<>();
        try (BufferedReader br
                = new BufferedReader(new FileReader(file))) {
            String str = br.readLine();
            //String tmp = "";
            // Ler primeira linha do arquivo e colocar as colunas do mapa
            List<String> lst = separarDados(str);
            List<String> aux = null;
            // Chaves estão desordenadas
            for (String s : lst) {
                aux = new ArrayList<>();
                aux.add(s);
                mapa.add(aux);
            }
            //Set colunas = mapa.keySet();
            // ler da segunda linha em diante e mapear os dados nas respectivas colunas
            str = br.readLine();
            while (str != null) {
                lst = separarDados(str);
                for(int i = 0; i < mapa.size(); i++){
                    mapa.get(i).add(lst.get(i));
                }
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
