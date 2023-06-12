package Interface.InfosSommetPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AfficherCheminPanel extends JPanel {
    private DefaultTableModel infoChemin;
    private JTable tableInfoChemin;

    public AfficherCheminPanel(){
        setLayout(new BorderLayout());
        tableInfoChemin = new JTable();//table qui stocke les informations des chemins
        infoChemin = new DefaultTableModel();
        infoChemin.setColumnCount(0);
        infoChemin.addColumn("Dispensaires");
        infoChemin.addColumn("");
        // Réinitialisez le nombre de lignes à zéro pour supprimer toutes les données existantes
        infoChemin.setRowCount(0);
        tableInfoChemin.setModel(infoChemin);
        add(new JScrollPane(tableInfoChemin), BorderLayout.CENTER);
       
    }

    public void updateColonne(String typeChemin){
        removeAll();
        infoChemin.setColumnCount(0);
        infoChemin.addColumn("Dispensaires");
        infoChemin.addColumn(typeChemin);
        // Réinitialisez le nombre de lignes à zéro pour supprimer toutes les données existantes
        infoChemin.setRowCount(0);
        tableInfoChemin.setModel(infoChemin);
        add(new JScrollPane(tableInfoChemin), BorderLayout.CENTER);
        updateUI();
    }

    public void resetTable(){
        // Réinitialisez le nombre de lignes à zéro pour supprimer toutes les données existantes
        infoChemin.setRowCount(0);
    }
    public void addDataInTable(String nomSommet, String donnee){
        infoChemin.addRow(new String[]{nomSommet, donnee});
    }
}
