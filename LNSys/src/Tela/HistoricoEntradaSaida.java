/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * HistoricoEntradaSaida.java
 *
 * Created on 11/05/2011, 01:39:58
 */

package Tela;

import Util.CalendarComboBox;
import Util.HibernateUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Leonardo
 */
public class HistoricoEntradaSaida extends javax.swing.JInternalFrame {

    private CadMateriais cadMateriais = null;
    private static HistoricoEntradaSaida historicoEntradaSaida;
    private SessionFactory fabricaDeSessoes;

    SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");

    /** Creates new form HistoricoEntradaSaida */
    public HistoricoEntradaSaida(CadMateriais instancia) {
         initComponents();

         setFrameIcon(new ImageIcon(this.getClass().getClassLoader().getResource("imagens/IconeLN.png")));

         Dimension centraliza = Toolkit.getDefaultToolkit().getScreenSize();
         this.setLocation((centraliza.width-this.getSize().width)/2,
                      (centraliza.height-this.getSize().height)/2);

         mostraTabelaVazia();

         SimpleDateFormat sfd2 = new SimpleDateFormat("dd/MM/yyyy");
         String data = sfd2.format(new Date());

         Session sessao = HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
         Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            //Query consultaOrca = sessao.createQuery("select o, mo, c from Orcamentos as o, MateriaisOrcamentos as mo, Clientes as c where  o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' and mo.orcamentos = o.codorcamentos and o.clientes = c.codcliente order by o.codorcamentos, mo.codmateriais ");

            Query materiais = sessao.createQuery("from HistoricoEntrada h where h.dataentrada between '"+data+"' and '"+data+"' and h.status in ('E', 'S') order by h.codmateriais");

            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listHistorico = materiais.list();
            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator iHistorico = listHistorico.iterator();

            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistorico.getModel();
            //tableResultado.setRowCount(0);


            while(iHistorico.hasNext()){
                 LN.entity.HistoricoEntrada historico = (LN.entity.HistoricoEntrada) iHistorico.next();

                 tableResultado.addRow(new Object[] {historico.getCodmateriais(), historico.getDescricao(), sfd.format(historico.getDataentrada()), historico.getQuantidade(), moeda(historico.getValor()), historico.getStatus(), historico.getUsuario()});
                 //System.out.println("HIST: "+ historico.getDescricao());
                 //jTableMateriais.updateUI();
                 //corLinhasTabela();
            }

            transacao.commit();
            sessao.close();

    }

//    public static HistoricoEntradaSaida getInstance(Inicial instancia) throws PropertyVetoException, SQLException, ClassNotFoundException {
//        if(historicoEntradaSaida == null)
//            historicoEntradaSaida = new HistoricoEntradaSaida(instancia);
//        return historicoEntradaSaida;
//    }


     public void montaTabela(){

        System.out.println("Montando Tabela");

        //jTableMaterial.setBackground(((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));

        //System.out.println("COR - >"+((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));

        jTableHistorico.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
//        int row = jTableMaterial.getRowCount();
//        jTableMaterial.setBackground(((row%2==0) ? Color.gray : Color.yellow));
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        jTableHistorico.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        jTableHistorico.getColumnModel().getColumn(1).setCellRenderer(esquerda);
        jTableHistorico.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        jTableHistorico.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        jTableHistorico.getColumnModel().getColumn(4).setCellRenderer(direita);
        jTableHistorico.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        jTableHistorico.getColumnModel().getColumn(6).setCellRenderer(centralizado);
        //jTableHistorico.getColumnModel().getColumn(6).setCellRenderer(centralizado);
        //jTableMateriais.getColumnModel().getColumn(7).setCellRenderer(direita);
        //jTableMaterial.getColumnModel().getColumn(6).setCellRenderer(direita);
        //jTableMaterial.getColumnModel().getColumn(7).setCellRenderer(esquerda);
        //jTableMaterial.getColumnModel().getColumn(8).setCellRenderer(centralizado);
        //jTableMaterial.getColumnModel().getColumn(9).setCellRenderer(centralizado);
        System.out.println("Ajustando tamanho das colunas");
        jTableHistorico.getColumnModel().getColumn(0).setPreferredWidth(55); //codigo
        jTableHistorico.getColumnModel().getColumn(0).setMinWidth(55);
        //jTableMateriais.getColumnModel().getColumn(0).setMaxWidth(30);
        jTableHistorico.getColumnModel().getColumn(1).setPreferredWidth(255);//descricao
        jTableHistorico.getColumnModel().getColumn(1).setMinWidth(255);
        jTableHistorico.getColumnModel().getColumn(2).setPreferredWidth(110);//85 QUANT estoque
        jTableHistorico.getColumnModel().getColumn(2).setMinWidth(110);
        jTableHistorico.getColumnModel().getColumn(3).setPreferredWidth(85);//65 quant minima
        jTableHistorico.getColumnModel().getColumn(3).setMinWidth(85);
        jTableHistorico.getColumnModel().getColumn(4).setPreferredWidth(75);//265 valor
        jTableHistorico.getColumnModel().getColumn(4).setMinWidth(75);
        jTableHistorico.getColumnModel().getColumn(5).setPreferredWidth(40);//105 fornecedor
        jTableHistorico.getColumnModel().getColumn(5).setMinWidth(40);
        jTableHistorico.getColumnModel().getColumn(6).setPreferredWidth(85);
        jTableHistorico.getColumnModel().getColumn(6).setMinWidth(85);
        //jTableHistorico.getColumnModel().getColumn(6).setPreferredWidth(100);//85 data
        //jTableHistorico.getColumnModel().getColumn(6).setMinWidth(100);
        //jTableMaterial.getColumnModel().getColumn(5).setPreferredWidth(0);//COR
        //jTableResultado.getColumnModel().getColumn(6).setPreferredWidth(60);//DINHEIRO
        //jTableMaterial.getColumnModel().getColumn(6).setPreferredWidth(45);//QTD
        //jTableMaterial.getColumnModel().getColumn(7).setPreferredWidth(70);//LOCAL
//        jTableMaterial.getColumnModel().getColumn(5).setPreferredWidth(0);
//        jTableMaterial.getColumnModel().getColumn(5).setMinWidth(0);
//        jTableMaterial.getColumnModel().getColumn(5).setMaxWidth(0);
        //jTableMaterial.getColumnModel().getColumn(9).setPreferredWidth(0);
        //jTableMaterial.getColumnModel().getColumn(9).setMinWidth(0);
        //jTableMaterial.getColumnModel().getColumn(9).setMaxWidth(0);


    }


    private void corLinhasTabela(){

        System.out.println("Metodo COR LINHAS TABELA");
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistorico.getModel();
        jTableHistorico.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {


            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
               super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         //A primeira coluna é 0
                 int rows = jTableHistorico.getSelectedRow();
                 System.out.println("LINHAS: "+ rows);
                Object ref = table.getValueAt(row, 0);
                int estoque = (Integer) jTableHistorico.getValueAt(rows, 2);
                int minimo = (Integer) jTableHistorico.getValueAt(rows, 3);
                System.out.println("MINIMO: "+ minimo);
       //Coloca cor em todas as linhas,COLUNA(0) que tem o nome "EMPRESTADO"
                if(estoque <= minimo){
                System.out.println("eh menor COR LINHAS");
                    setForeground(Color.RED);
                    setBackground(Color.white);
                }else{
                    System.out.println("COR BLACK");
                    setForeground(Color.BLACK);
                }
                return this;
            }
        });
    }

    private void mostraTabelaVazia(){
           //montaTabela();

            jTableHistorico.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][][] {
                    {null, null, null, null, null, null, null},
                },
                new String [] {
                    "Código","Descrição","Data Entrada/Saída", "Quantidade", "Valor", "E/S", "Usuário"
                }
            ){
            public boolean isCellEditable(int row, int col) {

                     return false;

            }
            });
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistorico.getModel();
        tableResultado.setRowCount(0);
        montaTabela();
//        tableResultado.getRowCount();
//        jTableMaterial.setBackground(((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));
//
//        System.out.println("COR - >"+((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));

        //Color teste = ((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.BLUE);
        //jTableMaterial.setBackground(Color.WHITE);
    }



    public static String moeda(double vlr){
            java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
            return df.format( vlr );
    }

    public static HistoricoEntradaSaida getInstance(CadMateriais instancia) throws PropertyVetoException {
        if(historicoEntradaSaida == null)
            historicoEntradaSaida = new HistoricoEntradaSaida(instancia);
        return historicoEntradaSaida;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupEntradaSaida = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableHistorico = new javax.swing.JTable();
        jRadioButtonEntrada = new javax.swing.JRadioButton();
        jRadioButtonSaida = new javax.swing.JRadioButton();
        jRadioButtonTodos = new javax.swing.JRadioButton();
        jComboBoxDataFinal = new CalendarComboBox(true);
        jLabelDF = new javax.swing.JLabel();
        jComboBoxDataInicial = new CalendarComboBox(true);
        jLabelDI = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setIconifiable(true);
        setTitle("HISTÓRICO DE ENTRADA E SAÍDA DE MATERIAIS");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Histórico de Entrada e Saída de Materiais", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jTableHistorico.setBackground(new java.awt.Color(0, 121, 76));
        jTableHistorico.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jTableHistorico.setForeground(new java.awt.Color(255, 255, 255));
        jTableHistorico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Descrição", "Data de Entrada/Saída", "Quantidade", "Valor", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableHistorico.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableHistorico.setFillsViewportHeight(true);
        jTableHistorico.setGridColor(new java.awt.Color(255, 255, 255));
        jTableHistorico.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTableHistorico.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTableHistorico);
        jTableHistorico.getColumnModel().getColumn(0).setResizable(false);
        jTableHistorico.getColumnModel().getColumn(1).setResizable(false);
        jTableHistorico.getColumnModel().getColumn(2).setResizable(false);
        jTableHistorico.getColumnModel().getColumn(3).setResizable(false);
        jTableHistorico.getColumnModel().getColumn(4).setResizable(false);
        jTableHistorico.getColumnModel().getColumn(5).setResizable(false);

        buttonGroupEntradaSaida.add(jRadioButtonEntrada);
        jRadioButtonEntrada.setMnemonic('E');
        jRadioButtonEntrada.setText("Entrada");
        jRadioButtonEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButtonEntradaMouseClicked(evt);
            }
        });

        buttonGroupEntradaSaida.add(jRadioButtonSaida);
        jRadioButtonSaida.setMnemonic('S');
        jRadioButtonSaida.setText("Saída");
        jRadioButtonSaida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButtonSaidaMouseClicked(evt);
            }
        });

        buttonGroupEntradaSaida.add(jRadioButtonTodos);
        jRadioButtonTodos.setMnemonic('T');
        jRadioButtonTodos.setSelected(true);
        jRadioButtonTodos.setText("Todos");
        jRadioButtonTodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButtonTodosMouseClicked(evt);
            }
        });

        jComboBoxDataFinal.setSelectedItem(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        jComboBoxDataFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxDataFinalKeyPressed(evt);
            }
        });

        jLabelDF.setText("Data final:");

        jComboBoxDataInicial.setSelectedItem(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        jComboBoxDataInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDataInicialActionPerformed(evt);
            }
        });
        jComboBoxDataInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxDataInicialKeyPressed(evt);
            }
        });

        jLabelDI.setText("Data inicial:");

        jButton1.setText("Sair");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelDI)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelDF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jRadioButtonTodos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonEntrada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonSaida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDI)
                    .addComponent(jComboBoxDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDF)
                    .addComponent(jComboBoxDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButtonTodos)
                    .addComponent(jRadioButtonEntrada)
                    .addComponent(jRadioButtonSaida)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxDataFinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxDataFinalKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_jComboBoxDataFinalKeyPressed

    private void jComboBoxDataInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDataInicialActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jComboBoxDataInicialActionPerformed

    private void jComboBoxDataInicialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxDataInicialKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            System.out.println("ENTER");
            //jButtonVisual.requestFocus();
        }
}//GEN-LAST:event_jComboBoxDataInicialKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.doDefaultCloseAction();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButtonTodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButtonTodosMouseClicked
        if (evt.getClickCount() == 1) {
            mostraTabelaVazia();

         SimpleDateFormat sfd2 = new SimpleDateFormat("dd/MM/yyyy");
         String data = sfd2.format(new Date());

         Session sessao = HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
         Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            //Query consultaOrca = sessao.createQuery("select o, mo, c from Orcamentos as o, MateriaisOrcamentos as mo, Clientes as c where  o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' and mo.orcamentos = o.codorcamentos and o.clientes = c.codcliente order by o.codorcamentos, mo.codmateriais ");

            Query materiais = sessao.createQuery("from HistoricoEntrada h where h.dataentrada between '"+jComboBoxDataInicial.getSelectedItem()+"' and '"+jComboBoxDataFinal.getSelectedItem()+"' and h.status in ('E', 'S') order by h.codmateriais");

            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listHistorico = materiais.list();
            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator iHistorico = listHistorico.iterator();

            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistorico.getModel();
            //tableResultado.setRowCount(0);


            while(iHistorico.hasNext()){
                 LN.entity.HistoricoEntrada historico = (LN.entity.HistoricoEntrada) iHistorico.next();

                 tableResultado.addRow(new Object[] {historico.getCodmateriais(), historico.getDescricao(), sfd.format(historico.getDataentrada()), historico.getQuantidade(), moeda(historico.getValor()), historico.getStatus(), historico.getUsuario()});
                 //System.out.println("HIST: "+ historico.getDescricao());
                 //jTableMateriais.updateUI();
                 //corLinhasTabela();
            }

            transacao.commit();
            sessao.close();
        }
    }//GEN-LAST:event_jRadioButtonTodosMouseClicked

    private void jRadioButtonEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButtonEntradaMouseClicked
         if (evt.getClickCount() == 1) {
            mostraTabelaVazia();

         SimpleDateFormat sfd2 = new SimpleDateFormat("dd/MM/yyyy");
         String data = sfd2.format(new Date());

         Session sessao = HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
         Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            //Query consultaOrca = sessao.createQuery("select o, mo, c from Orcamentos as o, MateriaisOrcamentos as mo, Clientes as c where  o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' and mo.orcamentos = o.codorcamentos and o.clientes = c.codcliente order by o.codorcamentos, mo.codmateriais ");

            Query materiais = sessao.createQuery("from HistoricoEntrada h where h.dataentrada between '"+jComboBoxDataInicial.getSelectedItem()+"' and '"+jComboBoxDataFinal.getSelectedItem()+"' and h.status = 'E' order by h.codmateriais");

            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listHistorico = materiais.list();
            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator iHistorico = listHistorico.iterator();

            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistorico.getModel();
            //tableResultado.setRowCount(0);


            while(iHistorico.hasNext()){
                 LN.entity.HistoricoEntrada historico = (LN.entity.HistoricoEntrada) iHistorico.next();

                 tableResultado.addRow(new Object[] {historico.getCodmateriais(), historico.getDescricao(), sfd.format(historico.getDataentrada()), historico.getQuantidade(), moeda(historico.getValor()), historico.getStatus(), historico.getUsuario()});
                 //System.out.println("HIST: "+ historico.getDescricao());
                 //jTableMateriais.updateUI();
                 //corLinhasTabela();
            }

            transacao.commit();
            sessao.close();
        }
    }//GEN-LAST:event_jRadioButtonEntradaMouseClicked

    private void jRadioButtonSaidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButtonSaidaMouseClicked
         if (evt.getClickCount() == 1) {
            mostraTabelaVazia();

         SimpleDateFormat sfd2 = new SimpleDateFormat("dd/MM/yyyy");
         String data = sfd2.format(new Date());

         Session sessao = HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
         Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            //Query consultaOrca = sessao.createQuery("select o, mo, c from Orcamentos as o, MateriaisOrcamentos as mo, Clientes as c where  o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' and mo.orcamentos = o.codorcamentos and o.clientes = c.codcliente order by o.codorcamentos, mo.codmateriais ");

            Query materiais = sessao.createQuery("from HistoricoEntrada h where h.dataentrada between '"+jComboBoxDataInicial.getSelectedItem()+"' and '"+jComboBoxDataFinal.getSelectedItem()+"' and h.status = 'S' order by h.codmateriais");

            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listHistorico = materiais.list();
            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator iHistorico = listHistorico.iterator();

            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistorico.getModel();
            //tableResultado.setRowCount(0);


            while(iHistorico.hasNext()){
                 LN.entity.HistoricoEntrada historico = (LN.entity.HistoricoEntrada) iHistorico.next();

                 tableResultado.addRow(new Object[] {historico.getCodmateriais(), historico.getDescricao(), sfd.format(historico.getDataentrada()), historico.getQuantidade(), moeda(historico.getValor()), historico.getStatus(), historico.getUsuario()});
                 //System.out.println("HIST: "+ historico.getDescricao());
                 //jTableMateriais.updateUI();
                 //corLinhasTabela();
            }

            transacao.commit();
            sessao.close();
        }
    }//GEN-LAST:event_jRadioButtonSaidaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupEntradaSaida;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBoxDataFinal;
    private javax.swing.JComboBox jComboBoxDataInicial;
    private javax.swing.JLabel jLabelDF;
    private javax.swing.JLabel jLabelDI;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButtonEntrada;
    private javax.swing.JRadioButton jRadioButtonSaida;
    private javax.swing.JRadioButton jRadioButtonTodos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableHistorico;
    // End of variables declaration//GEN-END:variables

}
