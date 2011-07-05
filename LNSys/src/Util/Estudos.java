package Util;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

public class Estudos extends JFrame{
  public Estudos(){
    super("Exemplo de uma tabela simples");

    final DefaultTableModel modelo = new DefaultTableModel();

    // constrói a tabela
    JTable tabela = new JTable(modelo){
      public boolean isCellEditable(int rowIndex, int vColIndex){
        return false;
      }
    };

    // Cria duas colunas
    modelo.addColumn("Nome");
    modelo.addColumn("Cor");

    // adiciona duas linhas
    modelo.addRow(new Object[]{"Azul", Color.BLUE});
    modelo.addRow(new Object[]{"Vermelho", Color.RED});
    modelo.addRow(new Object[]{"Verde", Color.GREEN});

    DefaultTableCellRenderer renderer = new CorTableCellRenderer();
    TableColumn column = tabela.getColumnModel().getColumn(1);
	column.setCellRenderer(renderer);

    tabela.setPreferredScrollableViewportSize(new Dimension(350, 50));

    Container c = getContentPane();
    c.setLayout(new FlowLayout());

    JScrollPane scrollPane = new JScrollPane(tabela);
    c.add(scrollPane);

    setSize(400, 300);
    setVisible(true);
  }

  public static void main(String args[]){
    Estudos app = new Estudos();
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}

class CorTableCellRenderer extends DefaultTableCellRenderer{
  public Component getTableCellRendererComponent
       (JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int column)
    {
        Component cell = super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);
//        if( value instanceof Integer )
//        {
            Integer amount = (Integer) value;
            if( amount.intValue() < 0 )
            {
                cell.setBackground( Color.red );
                // You can also customize the Font and Foreground this way
                // cell.setForeground();
                // cell.setFont();
            }
            else
            {
                cell.setBackground( Color.black );
            }
    //    }
        return cell;
    }
}