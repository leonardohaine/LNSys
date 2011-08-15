/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SaidaMaterial.java
 *
 * Created on 26/05/2011, 12:07:35
 */

package Tela;

import LN.entity.Materiais;
import Util.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Leonardo
 */
public class Saida extends javax.swing.JInternalFrame {

    private Inicial inicial = null;
    private static Saida saidaMaterial;
    private SessionFactory fabricaDeSessoes;
    public static int estoque = 0;
    public static int minimo = 0;

    SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");

    /** Creates new form SaidaMaterial */
    public Saida(Inicial instancia) throws PropertyVetoException {
        initComponents();
        this.setSelected(true);
        this.requestFocus();
        this.setClosable(true);
        this.setIconifiable(true);
        setResizable(false);

        setFrameIcon(new ImageIcon(this.getClass().getClassLoader().getResource("imagens/IconeLN.png")));

         Dimension centraliza = Toolkit.getDefaultToolkit().getScreenSize();
         this.setLocation((centraliza.width-this.getSize().width)/2,
                      (centraliza.height-this.getSize().height)/2-30);

            //fabricaDeSessoes = new Configuration().configure().buildSessionFactory();
            mostraTabelaVazia();
//           TableCellRenderer tcr = new ImageIconCellRenderer(new ImageIcon(this.getClass().getClassLoader().getResource("imagens/Bvermelha.gif")));
//        TableColumn column = jTableMateriais.getColumnModel().getColumn(8);
//	column.setCellRenderer(tcr);
//
            //String data = sfd.format(new Date());

            //Session sessao = HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
            //Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            //Query consultaOrca = sessao.createQuery("select o, mo, c from Orcamentos as o, MateriaisOrcamentos as mo, Clientes as c where  o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' and mo.orcamentos = o.codorcamentos and o.clientes = c.codcliente order by o.codorcamentos, mo.codmateriais ");

            //Query materiais = sessao.createQuery("from Materiais m order by m.descricao, m.codmateriais");
            //List listMateriais = materiais.list();

            //mostraLista(listMateriais);
 
            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
//            List listMateriais = materiais.list();
//            //List materiais = consultaMat.list();
//            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
//            Iterator m = listMateriais.iterator();
//        TableCellRenderer tcr1 = new ImagemTable();
//        TableColumn column1 = jTableMateriais.getColumnModel().getColumn(8);
//	column1.setCellRenderer(tcr1);
//
//            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMateriais.getModel();
//            //tableResultado.setRowCount(0);
//
//
//            while(m.hasNext()){
//                 Materiais mat = (Materiais) m.next();
//
//                 tableResultado.addRow(new Object[] {mat.getCodmateriais(), mat.getDescricao(), mat.getUnidade(), mat.getQtdestoque(), mat.getQtdminima(), moeda(mat.getValor()), mat.getFornecedor(), sfd.format(mat.getDatacadastro()), ""});
//
//            }
//
            jTableCaixa.updateUI();
            //transacao.commit();
            //sessao.close();
            
    }
/*
    public class ImagemTable extends JLabel implements TableCellRenderer{

  public ImagemTable(int est, int min){
  	setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table,
     Object value, boolean isSelected, boolean hasFocus, int row,
     int column){

     DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
//        int row = jTableMaterial.getRowCount();
//        jTableMaterial.setBackground(((row%2==0) ? Color.gray : Color.yellow));
     centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        //table.getColumnModel().getColumn(8).setCellRenderer(centralizado);

     Icon green = new ImageIcon(this.getClass().getClassLoader().getResource("imagens/ledgreen22.png"));
     Icon red = new ImageIcon(this.getClass().getClassLoader().getResource("imagens/ledred22.png"));
     Icon yellow = new ImageIcon(this.getClass().getClassLoader().getResource("imagens/ledyellow22.png"));
     // certifique-se da existencia da imagem "icon.gif" antes de executar
//System.out.println("Estoque: "+ estoque + "\n Minimo: "+ minimo);

     int e = (Integer) table.getValueAt(row, 3);
     int m = (Integer) table.getValueAt(row, 4);
      //System.out.println("Estoque "+ e+ " Minimo "+ m);
     if(e == m && table.getColumnName(3).equals("Qtd Estoque") && table.getColumnName(4).equals("Qtd Miníma") ){
       setBackground(table.getBackground());
       setIcon(yellow);
     } else if(e < m && table.getColumnName(3).equals("Qtd Estoque") && table.getColumnName(4).equals("Qtd Miníma")){
       setBackground(table.getBackground());
       setIcon(red);
     }else if (e > m && table.getColumnName(3).equals("Qtd Estoque") && table.getColumnName(4).equals("Qtd Miníma")) {
       setBackground(table.getBackground());
       setIcon(green);
     }
     setText(null);

     return this;
  }

  public void validate() {}
  public void revalidate() {}
  protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
  public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
}
*/
 private void mostraLista(List <Materiais> materiais){
             jTableCaixa.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][][] {
                    {null, null, null, null, null, null, null, null, null},
                },
                new String [] {
                    "Código","Descrição","Unid.", "Qtd Estoque", "Qtd Miníma", "Valor", "Fornecedor", "Data Cadastro", "OK"
                }
            ));

            mostraTabelaVazia();


 // Create the dummy data (a few rows of names)

//         TableCellRenderer tcr = new ImageIconCellRenderer(new ImageIcon(this.getClass().getClassLoader().getResource("imagens/Bvermelha.gif")));
//        TableColumn column = jTableMateriais.getColumnModel().getColumn(2);
//	column.setCellRenderer(tcr);

        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableCaixa.getModel();
        tableResultado.setRowCount(0);

         

            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator m = materiais.iterator();


            while(m.hasNext()){
                Materiais mat = (Materiais) m.next();

                //System.out.println("Estoque " + mat.getQtdestoque());

                //TableCellRenderer tcr = new ImagemTable(mat.getQtdestoque(), mat.getQtdminima());
                //TableColumn column = jTableCaixa.getColumnModel().getColumn(8);
                //column.setCellRenderer(tcr);

                tableResultado.addRow(new Object[] {mat.getCodmateriais(), mat.getDescricao(), mat.getUnidade(), mat.getQtdestoque(), mat.getQtdminima(), moeda(mat.getValor()), mat.getFornecedor(), sfd.format(mat.getDatacadastro()),new ImageIcon(this.getClass().getClassLoader().getResource("imagens/Bvermelha.gif"))});
     }

    }

    public void montaTabela(){

        System.out.println("Montando Tabela");

        //jTableMaterial.setBackground(((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));

        //System.out.println("COR - >"+((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));

        jTableCaixa.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
//        int row = jTableMaterial.getRowCount();
//        jTableMaterial.setBackground(((row%2==0) ? Color.gray : Color.yellow));
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        jTableCaixa.getColumnModel().getColumn(0).setCellRenderer(centralizado);//cod
        jTableCaixa.getColumnModel().getColumn(1).setCellRenderer(esquerda);//desc
        jTableCaixa.getColumnModel().getColumn(2).setCellRenderer(centralizado);//unid
        jTableCaixa.getColumnModel().getColumn(3).setCellRenderer(centralizado);//qtd
        jTableCaixa.getColumnModel().getColumn(4).setCellRenderer(direita);//valor
        jTableCaixa.getColumnModel().getColumn(5).setCellRenderer(direita);//total
       // jTableCaixa.getColumnModel().getColumn(6).setCellRenderer(esquerda);
        //jTableCaixa.getColumnModel().getColumn(7).setCellRenderer(centralizado);
        //jTableCaixa.getColumnModel().getColumn(8).setCellRenderer(centralizado);
        //jTableMaterial.getColumnModel().getColumn(6).setCellRenderer(direita);
        //jTableMaterial.getColumnModel().getColumn(7).setCellRenderer(esquerda);
        //jTableMaterial.getColumnModel().getColumn(8).setCellRenderer(centralizado);
        //jTableMaterial.getColumnModel().getColumn(9).setCellRenderer(centralizado);
        System.out.println("Ajustando tamanho das colunas");
        jTableCaixa.getColumnModel().getColumn(0).setPreferredWidth(60); //codigo
        jTableCaixa.getColumnModel().getColumn(0).setMinWidth(60);
        //jTableMateriais.getColumnModel().getColumn(0).setMaxWidth(30);
        jTableCaixa.getColumnModel().getColumn(1).setPreferredWidth(180);//descricao
        jTableCaixa.getColumnModel().getColumn(1).setMinWidth(180);
        jTableCaixa.getColumnModel().getColumn(2).setPreferredWidth(45);//85 Unid
        jTableCaixa.getColumnModel().getColumn(2).setMinWidth(45);
         jTableCaixa.getColumnModel().getColumn(3).setPreferredWidth(45);//85 QUANT estoque
        jTableCaixa.getColumnModel().getColumn(3).setMinWidth(45);
        jTableCaixa.getColumnModel().getColumn(4).setPreferredWidth(75);//65 quant minima
        jTableCaixa.getColumnModel().getColumn(4).setMinWidth(75);
        jTableCaixa.getColumnModel().getColumn(5).setPreferredWidth(85);//265 valor
        jTableCaixa.getColumnModel().getColumn(5).setMinWidth(85);
        //jTableCaixa.getColumnModel().getColumn(6).setPreferredWidth(140);//105 fornecedor
        //jTableCaixa.getColumnModel().getColumn(6).setMinWidth(140);
        //jTableCaixa.getColumnModel().getColumn(7).setPreferredWidth(100);//85 data
        //jTableCaixa.getColumnModel().getColumn(7).setMinWidth(100);

        //jTableCaixa.getColumnModel().getColumn(8).setPreferredWidth(24);//21 status
        //jTableCaixa.getColumnModel().getColumn(8).setMinWidth(24);
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



    private void mostraTabelaVazia(){
           //montaTabela();

            jTableCaixa.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][][] {
                    {null, null, null, null, null, null, null, null, null},
                },
                new String [] {
                    "Código","Descrição","Unid.", "Qtd", "Valor", "Total"
                }
            ){
            public boolean isCellEditable(int row, int col) {
                 
                     return false;
                 
            }
            });
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableCaixa.getModel();
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

    public static Saida getInstance(Inicial instancia) throws PropertyVetoException {
        if(saidaMaterial == null)
            saidaMaterial = new Saida(instancia);
        return saidaMaterial;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCaixa = new javax.swing.JTable();
        jLabelTroco = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldQuant = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldCodProduto = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setClosable(true);
        setTitle("CAIXA -  EMPÓRIO JAGUARÉ");
        setPreferredSize(new java.awt.Dimension(800, 578));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jTableCaixa.setAutoCreateRowSorter(true);
        jTableCaixa.setBackground(new java.awt.Color(0, 121, 76));
        jTableCaixa.setFont(new java.awt.Font("Arial", 1, 16));
        jTableCaixa.setForeground(new java.awt.Color(255, 255, 255));
        jTableCaixa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Descrição", "Unid.", "Quantidade", "Valor Unitario", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCaixa.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableCaixa.setFillsViewportHeight(true);
        jTableCaixa.setGridColor(new java.awt.Color(255, 255, 255));
        jTableCaixa.setRowHeight(25);
        jTableCaixa.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTableCaixa.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTableCaixa.getTableHeader().setResizingAllowed(false);
        jTableCaixa.getTableHeader().setReorderingAllowed(false);
        jTableCaixa.setUpdateSelectionOnSort(false);
        jTableCaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCaixaMouseClicked(evt);
            }
        });
        jTableCaixa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableCaixaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableCaixaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableCaixa);

        jLabelTroco.setFont(new java.awt.Font("Arial Black", 0, 24));

        jLabel13.setText("Quantidade:");

        jTextFieldQuant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldQuant.setText("1");

        jLabel14.setText("Cód Produto:");

        jTextFieldCodProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCodProdutoKeyPressed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/search-32.png"))); // NOI18N
        jButton1.setText("CONSULTAR [F4]");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cancel-32.png"))); // NOI18N
        jButton2.setText("Excluir Item");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/door-32.png"))); // NOI18N
        jButton3.setText("FECHAR");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldCodProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(49, 49, 49)
                                .addComponent(jButton3)
                                .addGap(47, 47, 47)
                                .addComponent(jButton2)))
                        .addGap(1185, 1185, 1185)
                        .addComponent(jLabelTroco))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldCodProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2)
                            .addComponent(jButton3))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableCaixaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableCaixaKeyReleased
        int rows = jTableCaixa.getSelectedRow();

        // jTableMercadoria.getSelectionModel().addListSelectionListener(
        //            new ListSelectionListener(){
        //                public void valueChanged(ListSelectionEvent e){
        //                    JScrollBar vertBart = jScrollPane1.getVerticalScrollBar();
        //                    vertBart.setValue(jTableMercadoria.getSelectedRow() - 8);
        //                    //vertBart.setValue(jTableMercadoria.getRowHeight() * jTableMercadoria.getSelectedRow() - 8);
        //                }
        //            }
        //        );

        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN || evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP){

            DefaultTableModel modelo = (DefaultTableModel) jTableCaixa.getModel();

            int estoque = (Integer) modelo.getValueAt(rows, 3);
            int minimo = (Integer) modelo.getValueAt(rows, 4);
            //            String estoque = (String) modelo.getValueAt(rows, 6);
            //            String minimo = (String) modelo.getValueAt(rows,9);
            //            int e = Integer.valueOf(estoque);
            //            int m = Integer.valueOf(minimo);

            //System.out.println("EST: "+ estoque + "   MIN: " + minimo);

            if(estoque <= minimo){
                //System.out.println("eh menor");
                jTableCaixa.setSelectionForeground(Color.RED);
            }else{
                jTableCaixa.setSelectionForeground(Color.BLACK);
            }
        }
}//GEN-LAST:event_jTableCaixaKeyReleased

    private void jTableCaixaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableCaixaKeyPressed
        //    if (evt.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER){
        //        jTableResultado.getSelectedRow();
        //        evt.consume();
        //    }
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_F9){
            try {
                DefaultTableModel modelo = (DefaultTableModel) jTableCaixa.getModel();
                String cod = (String) modelo.getValueAt(jTableCaixa.getSelectedRow(), 0);
                new TelaAnalitica(inicial, true, cod).setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro: "+"\nCausa: "+ex.getCause());
            }
        }

        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            String cod = null;
            String desc = null;
            String unidade = null;
            Double dinheiro = null;
            double valor = 0;
            Date data = null;
            int qtd = 0;

            DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();



            try {



                DefaultTableModel modelo = (DefaultTableModel) jTableCaixa.getModel();

                //            Point p = evt.getPoint();
                //            int col1 = jTableResultado.columnAtPoint(p);
                //            int col1 = jTableResultado.columnAtPoint(p);
                //            System.out.printf("row: %d    col: %d\n", row1, col1);
                int[] l = jTableCaixa.getSelectedRows();
                int cow = jTableCaixa.getSelectedColumn();
                int row = jTableCaixa.getSelectedRow();

                int estoque = 0;
                int minimo = 0;

                try{

                for (int i = l.length - 1; i >= 0; i--) {
                    cod = (String) modelo.getValueAt(row, 0);
                    desc = (String) modelo.getValueAt(row, 1);
                    unidade = (String) modelo.getValueAt(row, 2);
                    estoque = (Integer) modelo.getValueAt(row, 3);
                    minimo = (Integer) modelo.getValueAt(row, 4);

                    String fornecedor = (String) modelo.getValueAt(row, 6);


                    dinheiro = dff.parse((String) modelo.getValueAt(row, 5)).doubleValue();
                    qtd = (Integer) modelo.getValueAt(row, 3);
                    System.out.println("dimdim "+dinheiro);
                    System.out.println("QTD Venda " + qtd);
                    System.out.println("table " + jTableCaixa.getSelectedRowCount() + " - " + row);
                }

                    cod = (String) modelo.getValueAt(row, 0);
                    desc = (String) modelo.getValueAt(row, 1);
                    unidade = (String) modelo.getValueAt(row, 2);
                     estoque = (Integer) modelo.getValueAt(row, 3);
                     minimo = (Integer) modelo.getValueAt(row, 4);

                        valor = dff.parse((String) modelo.getValueAt(row, 5)).doubleValue();
                    } catch (ParseException ex) {
                        Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro native conversão de valores: "+"\nCausa: "+ex.getCause());
                    }
                    String fornecedor = (String) modelo.getValueAt(row, 6);
                     try {
                        data = (Date) sfd.parse((String)modelo.getValueAt(row, 7));
                    } catch (ParseException ex) {
                        Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro native conversão de data: "+"\nCausa: "+ex.getCause());
                    }

                new ConfirmaSaida(inicial, true, cod, desc, dinheiro, qtd).setVisible(true);

                Session sessao = HibernateUtil.getSessionFactory().openSession();
                Transaction transacao = sessao.beginTransaction();
                modelo.setValueAt(ConfirmaSaida.qtdVenda, row, 3);
                modelo.setValueAt(moeda(ConfirmaSaida.valor), row, 5);
                jTableCaixa.updateUI();
                LN.entity.HistoricoEntrada historicoEntrada = new LN.entity.HistoricoEntrada(getNextvalMateriais(), Integer.parseInt(cod), desc, ConfirmaSaida.quantidade, ConfirmaSaida.valor, new Date(), ConfirmaSaida.status, ConfirmaSaida.total, Inicial.USER);
                Materiais mat = new Materiais(cod, desc, unidade, ConfirmaSaida.qtdVenda, minimo, ConfirmaSaida.valor, fornecedor, data);

                sessao.save(historicoEntrada);
                sessao.update(mat);
                transacao.commit();
                sessao.close();

            } catch (SQLException ex) {
                Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro SQL: "+"\nCausa: "+ex.getCause());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro classe não encontrada: "+ "\nCausa: "+ex.getCause());
            }
            jTableCaixa.getSelectedRow();
            evt.consume();

        }
}//GEN-LAST:event_jTableCaixaKeyPressed

    private void jTableCaixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCaixaMouseClicked
        String cod = null;
        String desc = null;
        String unidade = null;
        Double dinheiro = null;
        double valor = 0;
        Date data = null;
        int qtd = 0;

        DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();

        if(evt.getClickCount() == 1){
            DefaultTableModel modelo = (DefaultTableModel) jTableCaixa.getModel();
            int row = jTableCaixa.getSelectedRow();
            int estoque = (Integer) modelo.getValueAt(row, 3);
            int minimo = (Integer) modelo.getValueAt(row,4);
            //System.out.println("EST: "+ estoque + "   MIN: " + minimo);
            if(estoque <= minimo){
                //System.out.println("eh menor");
                jTableCaixa.setSelectionForeground(Color.RED);
            }else{
                jTableCaixa.setSelectionForeground(Color.BLACK);
            }

        }

        if (evt.getClickCount() == 2) {
            try {

                DefaultTableModel modelo = (DefaultTableModel) jTableCaixa.getModel();

                int[] l = jTableCaixa.getSelectedRows();
                int cow = jTableCaixa.getSelectedColumn();
                int row = jTableCaixa.getSelectedRow();

                int estoque = 0;
                int minimo = 0;

                try{

                for (int i = l.length - 1; i >= 0; i--) {
                    cod = (String) modelo.getValueAt(row, 0);
                    desc = (String) modelo.getValueAt(row, 1);
                    unidade = (String) modelo.getValueAt(row, 2);
                    estoque = (Integer) modelo.getValueAt(row, 3);
                    minimo = (Integer) modelo.getValueAt(row, 4);

                    String fornecedor = (String) modelo.getValueAt(row, 6);

                    
                    dinheiro = dff.parse((String) modelo.getValueAt(row, 5)).doubleValue();
                    qtd = (Integer) modelo.getValueAt(row, 3);
                    System.out.println("dimdim "+dinheiro);
                    System.out.println("QTD Venda " + qtd);
                    System.out.println("table " + jTableCaixa.getSelectedRowCount() + " - " + row);
                }

                    cod = (String) modelo.getValueAt(row, 0);
                    desc = (String) modelo.getValueAt(row, 1);
                    unidade = (String) modelo.getValueAt(row, 2);
                    estoque = (Integer) modelo.getValueAt(row, 3);
                    minimo = (Integer) modelo.getValueAt(row, 4);
                    
                    valor = dff.parse((String) modelo.getValueAt(row, 5)).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Erro native conversão de valores: "+ "\nCausa: "+ex.getCause());
                }

                String fornecedor = (String) modelo.getValueAt(row, 6);

                try {
                    data = (Date) sfd.parse((String)modelo.getValueAt(row, 7));
                } catch (ParseException ex) {
                    Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Erro native conversão de data: "+ "\nCausa: "+ex.getCause());
                }

                new ConfirmaSaida(inicial, true, cod, desc, dinheiro, qtd).setVisible(true);

                Session sessao = HibernateUtil.getSessionFactory().openSession();
                Transaction transacao = sessao.beginTransaction();

                modelo.setValueAt(ConfirmaSaida.qtdVenda, row, 3);
                modelo.setValueAt(moeda(ConfirmaSaida.valor), row, 5);
                jTableCaixa.updateUI();
                LN.entity.HistoricoEntrada historicoEntrada = new LN.entity.HistoricoEntrada(getNextvalMateriais(), Integer.parseInt(cod), desc, ConfirmaSaida.quantidade, ConfirmaSaida.valor, new Date(), ConfirmaSaida.status, ConfirmaSaida.total, Inicial.USER);
                Materiais mat = new Materiais(cod, desc, unidade, ConfirmaSaida.qtdVenda, minimo, ConfirmaSaida.valor, fornecedor, data);

                sessao.save(historicoEntrada);
                sessao.update(mat);
                transacao.commit();
                sessao.close();

//                if(ConfirmaSaida.status == 'S'){
//                    JOptionPane.showMessageDialog(this, "Saída confirmada com sucesso!!!.", "OK", JOptionPane.INFORMATION_MESSAGE);
//                }else if(ConfirmaSaida.status == 'E'){
//                    JOptionPane.showMessageDialog(this, "Entrada confirmada com sucesso!!!.", "OK", JOptionPane.INFORMATION_MESSAGE);
//                }

            } catch (SQLException ex) {
                Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro SQL: "+ "\nCausa: "+ex.getCause());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro classe não encontrada: "+ "\nCausa: "+ex.getCause());
            }

        }
}//GEN-LAST:event_jTableCaixaMouseClicked

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        //mostraTabelaVazia();


            //String data = sfd.format(new Date());

            Session sessao = HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
            Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            //Query consultaOrca = sessao.createQuery("select o, mo, c from Orcamentos as o, MateriaisOrcamentos as mo, Clientes as c where  o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' and mo.orcamentos = o.codorcamentos and o.clientes = c.codcliente order by o.codorcamentos, mo.codmateriais ");

            //Query materiais = sessao.createQuery("from Materiais m order by m.descricao, m.codmateriais");

            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            //List listMateriais = materiais.list();
            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            //Iterator m = listMateriais.iterator();

            //javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMateriais.getModel();
            //tableResultado.setRowCount(0);





//            while(m.hasNext()){
                // Materiais mat = (Materiais) m.next();

//                 tableResultado.addRow(new Object[] {mat.getCodmateriais(), mat.getDescricao(), mat.getUnidade(), mat.getQtdestoque(), mat.getQtdminima(), moeda(mat.getValor()), mat.getFornecedor(), sfd.format(mat.getDatacadastro()), ""});
                 //System.out.println("MAT: "+ mat.getDescricao());
                 //jTableMateriais.updateUI();
//                 TableCellRenderer tcr = new ColorirLinha(mat.getQtdestoque(), mat.getQtdminima());
//                 TableColumn column =  jTableMateriais.getColumnModel().getColumn(1);
//                 column.setCellRenderer(tcr);
            //}

            ///Query materiais = sessao.createQuery("from Materiais m order by m.descricao, m.codmateriais");
            ///List listMateriais = materiais.list();

            ///mostraLista(listMateriais);

            transacao.commit();
            sessao.close();

            System.out.println("Open!!!");
    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
            Session sessao = HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
            Transaction transacao = sessao.beginTransaction();

            Query materiais = sessao.createQuery("from Materiais m order by m.descricao, m.codmateriais");
            List listMateriais = materiais.list();

            mostraLista(listMateriais);

            transacao.commit();
            sessao.close();

            jTableCaixa.updateUI();
            //jLabelRetorno.setText("");
            //jTextFieldDesc.setText("");
            //jTextFieldCod.setText("");
            //jTextFieldFornecedor.setText("");

            System.out.println("Closed!!!");
    }//GEN-LAST:event_formInternalFrameClosed

    private void jTextFieldCodProdutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCodProdutoKeyPressed
        Session sessao = HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
        Transaction transacao = sessao.beginTransaction();

        javax.swing.table.DefaultTableModel tableResultado = (javax.swing.table.DefaultTableModel)jTableCaixa.getModel();

        if (evt.getKeyCode() == evt.VK_ENTER) {

            Query materiais = sessao.createQuery("from Materiais m where m.codmateriais like '"+jTextFieldCodProduto.getText()+"%' order by m.descricao, m.codmateriais");
            List listMateriais = materiais.list();
            Iterator m = listMateriais.iterator();
            Materiais mat = (Materiais) m.next();
            double total =0;
            total = mat.getValor() * Double.valueOf(jTextFieldQuant.getText()).doubleValue();
            tableResultado.addRow(new Object[] {mat.getCodmateriais(), mat.getDescricao(), mat.getUnidade(), jTextFieldQuant.getText(), moeda(mat.getValor()), moeda(total)});
            //mostraLista(listMateriais);

            if(listMateriais.isEmpty()){
                //jLabelRetorno.setText("Produto nâo cadastro!!!");
            }

        }

        transacao.commit();
        sessao.close();
    }//GEN-LAST:event_jTextFieldCodProdutoKeyPressed

public int getNextvalMateriais(){

       //fabricaDeSessoes = new Configuration().configure().buildSessionFactory();
       Session sessao = HibernateUtil.getSessionFactory().openSession();
       Transaction transacao = sessao.beginTransaction();

        SQLQuery query = sessao.createSQLQuery("select nextval('seq_historico')");
           Object object = query.uniqueResult();
           int nextVal = ( (java.math.BigInteger)object ).intValue();
           System.out.println("NEXTVAL MATERIAIS "+nextVal);


           transacao.commit();
           sessao.close();


           return nextVal;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabelTroco;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableCaixa;
    private javax.swing.JTextField jTextFieldCodProduto;
    private javax.swing.JTextField jTextFieldQuant;
    // End of variables declaration//GEN-END:variables

}
