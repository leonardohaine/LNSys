/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import java.awt.Color;
import java.awt.Color;
import java.awt.Component;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Leonardo
 */
   public class ColorirLinha extends     JLabel  implements  TableCellRenderer {

        private Integer qtdEstoque = 0;
        private Integer qtdMinima = 0;

        public ColorirLinha(Integer qtdEstoque, Integer qtdMinima){
            this.qtdEstoque = qtdEstoque;
            this.qtdMinima = qtdMinima;
        }

        public ColorirLinha(){
            
        }


        public Component       getTableCellRendererComponent
                    (
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column
                    )
        {

                if(qtdEstoque <= qtdMinima){
                   setForeground(Color.RED);
                   System.out.println("COR RED");
                } else{
                   setForeground(Color.BLACK);
                   System.out.println("COR BLACK");
                }

                setText(value.toString());

            return this;
        }
    }