/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import com.lowagie.text.Font;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Leonardo
 */
public class CellRenderBackground extends DefaultTableCellRenderer {

    private ArrayList<Object> obj = new ArrayList<Object>();
    private Integer qtdEstoque = 0;
    private Integer qtdMinima = 0;

 {
//seto os números de indice da tabela 2 e 4 para teste.
    obj.add("2");
    obj.add("4");
}

 /** */
// private Font fontePadrao = new Font("monospaced", Font.BOLD, 12);

 /**
 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
 * java.lang.Object, boolean, boolean, int, int)
 */
 public Component getTableCellRendererComponent(JTable table,
 Object value,
 boolean isSelected,
 boolean hasFocus,
 int row,
 int column) {

 Component c = super.getTableCellRendererComponent(
 table,
 value,
 isSelected,
 hasFocus,
 row,
 column);

 //c.setFont(this.fontePadrao);

 if(qtdEstoque <= qtdMinima){
                   setForeground(Color.RED);
                   System.out.println("COR RED");
                } else{
                   setForeground(Color.BLACK);
                   System.out.println("COR BLACK");
                }


 return c;
 }


}
