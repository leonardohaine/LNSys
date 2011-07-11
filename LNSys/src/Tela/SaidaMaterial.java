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
public class SaidaMaterial extends javax.swing.JInternalFrame {

    private Inicial inicial = null;
    private static SaidaMaterial saidaMaterial;
    private SessionFactory fabricaDeSessoes;
    public static int estoque = 0;
    public static int minimo = 0;

    SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");

    /** Creates new form SaidaMaterial */
    public SaidaMaterial(Inicial instancia) throws PropertyVetoException {
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

            Session sessao = HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
            Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            //Query consultaOrca = sessao.createQuery("select o, mo, c from Orcamentos as o, MateriaisOrcamentos as mo, Clientes as c where  o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' and mo.orcamentos = o.codorcamentos and o.clientes = c.codcliente order by o.codorcamentos, mo.codmateriais ");

            Query materiais = sessao.createQuery("from Materiais m order by m.descricao, m.codmateriais");
            List listMateriais = materiais.list();

            mostraLista(listMateriais);
 
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
            jTableMateriais.updateUI();
            transacao.commit();
            sessao.close();
            
    }

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
     // certifique-se da existencia da imagem "icon.gif" antes de executar
//System.out.println("Estoque: "+ estoque + "\n Minimo: "+ minimo);

int e = (Integer) table.getValueAt(row, 3);
int m = (Integer) table.getValueAt(row, 4);
      //System.out.println("Estoque "+ e+ " Minimo "+ m);
     if(e <= m && table.getColumnName(3).equals("Qtd Estoque") && table.getColumnName(4).equals("Qtd Miníma") ){
       setBackground(table.getBackground());
       setIcon(red);
     } else {
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

 private void mostraLista(List <Materiais> materiais){
             jTableMateriais.setModel(new javax.swing.table.DefaultTableModel(
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

        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMateriais.getModel();
        tableResultado.setRowCount(0);

         

            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator m = materiais.iterator();


            while(m.hasNext()){
                Materiais mat = (Materiais) m.next();

                //System.out.println("Estoque " + mat.getQtdestoque());

                TableCellRenderer tcr = new ImagemTable(mat.getQtdestoque(), mat.getQtdminima());
                TableColumn column = jTableMateriais.getColumnModel().getColumn(8);
                column.setCellRenderer(tcr);

                tableResultado.addRow(new Object[] {mat.getCodmateriais(), mat.getDescricao(), mat.getUnidade(), mat.getQtdestoque(), mat.getQtdminima(), moeda(mat.getValor()), mat.getFornecedor(), sfd.format(mat.getDatacadastro()),new ImageIcon(this.getClass().getClassLoader().getResource("imagens/Bvermelha.gif"))});
     }

    }

    public void montaTabela(){

        System.out.println("Montando Tabela");

        //jTableMaterial.setBackground(((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));

        //System.out.println("COR - >"+((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));

        jTableMateriais.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
//        int row = jTableMaterial.getRowCount();
//        jTableMaterial.setBackground(((row%2==0) ? Color.gray : Color.yellow));
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        jTableMateriais.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        jTableMateriais.getColumnModel().getColumn(1).setCellRenderer(esquerda);
        jTableMateriais.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        jTableMateriais.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        jTableMateriais.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        jTableMateriais.getColumnModel().getColumn(5).setCellRenderer(direita);
        jTableMateriais.getColumnModel().getColumn(6).setCellRenderer(esquerda);
        jTableMateriais.getColumnModel().getColumn(7).setCellRenderer(centralizado);
        jTableMateriais.getColumnModel().getColumn(8).setCellRenderer(centralizado);
        //jTableMaterial.getColumnModel().getColumn(6).setCellRenderer(direita);
        //jTableMaterial.getColumnModel().getColumn(7).setCellRenderer(esquerda);
        //jTableMaterial.getColumnModel().getColumn(8).setCellRenderer(centralizado);
        //jTableMaterial.getColumnModel().getColumn(9).setCellRenderer(centralizado);
        System.out.println("Ajustando tamanho das colunas");
        jTableMateriais.getColumnModel().getColumn(0).setPreferredWidth(60); //codigo
        jTableMateriais.getColumnModel().getColumn(0).setMinWidth(60);
        //jTableMateriais.getColumnModel().getColumn(0).setMaxWidth(30);
        jTableMateriais.getColumnModel().getColumn(1).setPreferredWidth(280);//descricao
        jTableMateriais.getColumnModel().getColumn(1).setMinWidth(280);
        jTableMateriais.getColumnModel().getColumn(2).setPreferredWidth(75);//85 Unid
        jTableMateriais.getColumnModel().getColumn(2).setMinWidth(75);
         jTableMateriais.getColumnModel().getColumn(3).setPreferredWidth(75);//85 QUANT estoque
        jTableMateriais.getColumnModel().getColumn(3).setMinWidth(75);
        jTableMateriais.getColumnModel().getColumn(4).setPreferredWidth(75);//65 quant minima
        jTableMateriais.getColumnModel().getColumn(4).setMinWidth(75);
        jTableMateriais.getColumnModel().getColumn(5).setPreferredWidth(85);//265 valor
        jTableMateriais.getColumnModel().getColumn(5).setMinWidth(85);
        jTableMateriais.getColumnModel().getColumn(6).setPreferredWidth(140);//105 fornecedor
        jTableMateriais.getColumnModel().getColumn(6).setMinWidth(140);
        jTableMateriais.getColumnModel().getColumn(7).setPreferredWidth(100);//85 data
        jTableMateriais.getColumnModel().getColumn(7).setMinWidth(100);

        jTableMateriais.getColumnModel().getColumn(8).setPreferredWidth(24);//21 status
        jTableMateriais.getColumnModel().getColumn(8).setMinWidth(24);
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

            jTableMateriais.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][][] {
                    {null, null, null, null, null, null, null, null, null},
                },
                new String [] {
                    "Código","Descrição","Unid.", "Qtd Estoque", "Qtd Miníma", "Valor", "Fornecedor", "Data Cadastro", "OK"
                }
            ){
            public boolean isCellEditable(int row, int col) {
                 
                     return false;
                 
            }
            });
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMateriais.getModel();
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

    public static SaidaMaterial getInstance(Inicial instancia) throws PropertyVetoException {
        if(saidaMaterial == null)
            saidaMaterial = new SaidaMaterial(instancia);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldCod = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldDesc = new javax.swing.JTextField();
        jButtonAtualizar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldFornecedor = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMateriais = new javax.swing.JTable();
        jLabelRetorno = new javax.swing.JLabel();

        setClosable(true);
        setTitle("LISTA DE MATERIAIS - ENTRADA E SAÍDA");
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PESQUISA", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Código:");

        jTextFieldCod.setFont(new java.awt.Font("Arial", 1, 12));
        jTextFieldCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldCodFocusLost(evt);
            }
        });
        jTextFieldCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCodKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldCodKeyReleased(evt);
            }
        });

        jLabel2.setText("Descrição:");

        jTextFieldDesc.setFont(new java.awt.Font("Arial", 1, 12));
        jTextFieldDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldDescFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldDescFocusLost(evt);
            }
        });
        jTextFieldDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldDescKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldDescKeyReleased(evt);
            }
        });

        jButtonAtualizar.setFont(new java.awt.Font("Arial", 1, 12));
        jButtonAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/btnRefreshDisplay.png"))); // NOI18N
        jButtonAtualizar.setText("Atualizar");
        jButtonAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtualizarActionPerformed(evt);
            }
        });

        jLabel3.setText("Fornecedor:");

        jTextFieldFornecedor.setFont(new java.awt.Font("Arial", 1, 12));
        jTextFieldFornecedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldFornecedorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldFornecedorFocusLost(evt);
            }
        });
        jTextFieldFornecedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldFornecedorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFornecedorKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCod, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jButtonAtualizar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAtualizar))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTableMateriais.setAutoCreateRowSorter(true);
        jTableMateriais.setBackground(new java.awt.Color(0, 121, 76));
        jTableMateriais.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jTableMateriais.setForeground(new java.awt.Color(255, 255, 255));
        jTableMateriais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Título 5", "Título 6", "Título 7", "Título 8", "Título 9"
            }
        ));
        jTableMateriais.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableMateriais.setFillsViewportHeight(true);
        jTableMateriais.setGridColor(new java.awt.Color(255, 255, 255));
        jTableMateriais.setRowHeight(20);
        jTableMateriais.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTableMateriais.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTableMateriais.getTableHeader().setResizingAllowed(false);
        jTableMateriais.getTableHeader().setReorderingAllowed(false);
        jTableMateriais.setUpdateSelectionOnSort(false);
        jTableMateriais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMateriaisMouseClicked(evt);
            }
        });
        jTableMateriais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableMateriaisKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableMateriaisKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableMateriais);

        jLabelRetorno.setForeground(new java.awt.Color(255, 0, 0));
        jLabelRetorno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(219, 219, 219)
                        .addComponent(jLabelRetorno, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelRetorno, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldCodFocusGained
//        jTextFieldCod.setBackground(Color.GREEN);
//        jTextFieldCod.setForeground(Color.WHITE);
        jTextFieldDesc.setText("");
}//GEN-LAST:event_jTextFieldCodFocusGained

    private void jTextFieldCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldCodFocusLost
//        jTextFieldCod.setBackground(Color.green);
//        jTextFieldCod.setForeground(Color.black);
}//GEN-LAST:event_jTextFieldCodFocusLost

    private void jTextFieldCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCodKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            jTableMateriais.requestFocus();
            evt.setKeyCode(java.awt.event.KeyEvent.VK_ENTER);

        }
}//GEN-LAST:event_jTextFieldCodKeyPressed

    private void jTextFieldCodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCodKeyReleased
        boolean Consulta = true;

        //javax.swing.table.DefaultTableModel tableResultado = (javax.swing.table.DefaultTableModel)jTableMateriais.getModel();

        if(jTextFieldCod.getText().equals("")){
            
            //tableResultado.setRowCount(0);
            Consulta = false;
        }else{
            String codMaterial = jTextFieldCod.getText();

            Session sessao = HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
            Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            //Query consultaOrca = sessao.createQuery("select o, mo, c from Orcamentos as o, MateriaisOrcamentos as mo, Clientes as c where  o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' and mo.orcamentos = o.codorcamentos and o.clientes = c.codcliente order by o.codorcamentos, mo.codmateriais ");

            Query materiais = sessao.createQuery("from Materiais m where m.codmateriais like '"+codMaterial+"%' order by m.descricao, m.codmateriais");

            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listMateriais = materiais.list();
            Iterator m = listMateriais.iterator();

            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMateriais.getModel();
            tableResultado.setRowCount(0);
            jLabelRetorno.setText("");

            while(m.hasNext()){
                 Materiais mat = (Materiais) m.next();

                 tableResultado.addRow(new Object[] {mat.getCodmateriais(), mat.getDescricao(),mat.getUnidade(), mat.getQtdestoque(), mat.getQtdminima(), moeda(mat.getValor()), mat.getFornecedor(), sfd.format(mat.getDatacadastro()), ""});
                 System.out.println("COD: "+ mat.getDescricao());
                 //jTableMateriais.updateUI();
            }

            if (listMateriais.isEmpty()) {

                jLabelRetorno.setText("Nenhum material encontrado com este código.!!");
                jTextFieldCod.requestFocus();
                return ;
            }

            transacao.commit();
            sessao.close();
        }
}//GEN-LAST:event_jTextFieldCodKeyReleased

    private void jTextFieldDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldDescFocusGained
//        jTextFieldDesc.setBackground(Color.gray);
//        jTextFieldDesc.setForeground(Color.WHITE);
        jTextFieldCod.setText("");
}//GEN-LAST:event_jTextFieldDescFocusGained

    private void jTextFieldDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldDescFocusLost
//        jTextFieldDesc.setBackground(Color.white);
//        jTextFieldDesc.setForeground(Color.black);
}//GEN-LAST:event_jTextFieldDescFocusLost

    private void jTextFieldDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDescKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            jTableMateriais.requestFocus();
            evt.setKeyCode(java.awt.event.KeyEvent.VK_ENTER);

        }
}//GEN-LAST:event_jTextFieldDescKeyPressed

    private void jTextFieldDescKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDescKeyReleased
        boolean Consulta = true;

        javax.swing.table.DefaultTableModel tableResultado = (javax.swing.table.DefaultTableModel)jTableMateriais.getModel();

        if(jTextFieldDesc.getText().equals("")){
            
            //tableResultado.setRowCount(0);
            Consulta = false;
            //return;
        }else{
            String descricaoMaterial = jTextFieldDesc.getText();

            //mostraTabelaVazia();
            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            Query materiais = sessao.createQuery("from Materiais m where m.descricao LIKE '"+descricaoMaterial+"%' order by m.descricao, m.codmateriais");

            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listMateriais = materiais.list();
            Iterator m = listMateriais.iterator();

            tableResultado.setRowCount(0);
            jLabelRetorno.setText("");

            while(m.hasNext()){
                 Materiais mat = (Materiais) m.next();

                 tableResultado.addRow(new Object[] {mat.getCodmateriais(), mat.getDescricao(), mat.getUnidade(), mat.getQtdestoque(), mat.getQtdminima(), moeda(mat.getValor()), mat.getFornecedor(), sfd.format(mat.getDatacadastro()), ""});
                 System.out.println("Desc: "+ mat.getDescricao());
                 jTableMateriais.updateUI();
            }

            if (listMateriais.isEmpty()) {

                jLabelRetorno.setText("Nenhum material encontrado com está descrição.!!");
                jTextFieldDesc.requestFocus();
                return ;
            }

            transacao.commit();
            sessao.close();


        }
}//GEN-LAST:event_jTextFieldDescKeyReleased

    private void jButtonAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtualizarActionPerformed
            Session sessao =HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
            Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            //Query consultaOrca = sessao.createQuery("select o, mo, c from Orcamentos as o, MateriaisOrcamentos as mo, Clientes as c where  o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' and mo.orcamentos = o.codorcamentos and o.clientes = c.codcliente order by o.codorcamentos, mo.codmateriais ");

            Query materiais = sessao.createQuery("from Materiais m order by m.descricao, m.codmateriais");

            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listMateriais = materiais.list();
            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator m = listMateriais.iterator();

            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMateriais.getModel();
            tableResultado.setRowCount(0);
            jLabelRetorno.setText("");


            while(m.hasNext()){
                 Materiais mat = (Materiais) m.next();

                 tableResultado.addRow(new Object[] {mat.getCodmateriais(), mat.getDescricao(), mat.getUnidade(), mat.getQtdestoque(), mat.getQtdminima(), moeda(mat.getValor()), mat.getFornecedor(), sfd.format(mat.getDatacadastro()), ""});
              
            }

            transacao.commit();
            sessao.close();
}//GEN-LAST:event_jButtonAtualizarActionPerformed

    private void jTableMateriaisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableMateriaisKeyReleased
        int rows = jTableMateriais.getSelectedRow();

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

            DefaultTableModel modelo = (DefaultTableModel) jTableMateriais.getModel();

            int estoque = (Integer) modelo.getValueAt(rows, 3);
            int minimo = (Integer) modelo.getValueAt(rows, 4);
            //            String estoque = (String) modelo.getValueAt(rows, 6);
            //            String minimo = (String) modelo.getValueAt(rows,9);
            //            int e = Integer.valueOf(estoque);
            //            int m = Integer.valueOf(minimo);

            //System.out.println("EST: "+ estoque + "   MIN: " + minimo);

            if(estoque <= minimo){
                //System.out.println("eh menor");
                jTableMateriais.setSelectionForeground(Color.RED);
            }else{
                jTableMateriais.setSelectionForeground(Color.BLACK);
            }
        }
}//GEN-LAST:event_jTableMateriaisKeyReleased

    private void jTableMateriaisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableMateriaisKeyPressed
        //    if (evt.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER){
        //        jTableResultado.getSelectedRow();
        //        evt.consume();
        //    }
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_F9){
            try {
                DefaultTableModel modelo = (DefaultTableModel) jTableMateriais.getModel();
                String cod = (String) modelo.getValueAt(jTableMateriais.getSelectedRow(), 0);
                new TelaAnalitica(inicial, true, cod).setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(SaidaMaterial.class.getName()).log(Level.SEVERE, null, ex);
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



                DefaultTableModel modelo = (DefaultTableModel) jTableMateriais.getModel();

                //            Point p = evt.getPoint();
                //            int col1 = jTableResultado.columnAtPoint(p);
                //            int col1 = jTableResultado.columnAtPoint(p);
                //            System.out.printf("row: %d    col: %d\n", row1, col1);
                int[] l = jTableMateriais.getSelectedRows();
                int cow = jTableMateriais.getSelectedColumn();
                int row = jTableMateriais.getSelectedRow();

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
                    System.out.println("table " + jTableMateriais.getSelectedRowCount() + " - " + row);
                }

                    cod = (String) modelo.getValueAt(row, 0);
                    desc = (String) modelo.getValueAt(row, 1);
                    unidade = (String) modelo.getValueAt(row, 2);
                     estoque = (Integer) modelo.getValueAt(row, 3);
                     minimo = (Integer) modelo.getValueAt(row, 4);

                        valor = dff.parse((String) modelo.getValueAt(row, 5)).doubleValue();
                    } catch (ParseException ex) {
                        Logger.getLogger(SaidaMaterial.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro native conversão de valores: "+"\nCausa: "+ex.getCause());
                    }
                    String fornecedor = (String) modelo.getValueAt(row, 6);
                     try {
                        data = (Date) sfd.parse((String)modelo.getValueAt(row, 7));
                    } catch (ParseException ex) {
                        Logger.getLogger(SaidaMaterial.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro native conversão de data: "+"\nCausa: "+ex.getCause());
                    }

                new ConfirmaSaida(inicial, true, cod, desc, dinheiro, qtd).setVisible(true);

                Session sessao = HibernateUtil.getSessionFactory().openSession();
                Transaction transacao = sessao.beginTransaction();
                modelo.setValueAt(ConfirmaSaida.qtdVenda, row, 3);
                modelo.setValueAt(moeda(ConfirmaSaida.valor), row, 5);
                jTableMateriais.updateUI();
                LN.entity.HistoricoEntrada historicoEntrada = new LN.entity.HistoricoEntrada(getNextvalMateriais(), Integer.parseInt(cod), desc, ConfirmaSaida.quantidade, ConfirmaSaida.valor, new Date(), ConfirmaSaida.status, ConfirmaSaida.total, Inicial.USER);
                Materiais mat = new Materiais(cod, desc, unidade, ConfirmaSaida.qtdVenda, minimo, ConfirmaSaida.valor, fornecedor, data);

                sessao.save(historicoEntrada);
                sessao.update(mat);
                transacao.commit();
                sessao.close();

            } catch (SQLException ex) {
                Logger.getLogger(SaidaMaterial.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro SQL: "+"\nCausa: "+ex.getCause());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SaidaMaterial.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro classe não encontrada: "+ "\nCausa: "+ex.getCause());
            }
            jTableMateriais.getSelectedRow();
            evt.consume();

        }
}//GEN-LAST:event_jTableMateriaisKeyPressed

    private void jTableMateriaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMateriaisMouseClicked
        String cod = null;
        String desc = null;
        String unidade = null;
        Double dinheiro = null;
        double valor = 0;
        Date data = null;
        int qtd = 0;

        DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();

        if(evt.getClickCount() == 1){
            DefaultTableModel modelo = (DefaultTableModel) jTableMateriais.getModel();
            int row = jTableMateriais.getSelectedRow();
            int estoque = (Integer) modelo.getValueAt(row, 3);
            int minimo = (Integer) modelo.getValueAt(row,4);
            //System.out.println("EST: "+ estoque + "   MIN: " + minimo);
            if(estoque <= minimo){
                //System.out.println("eh menor");
                jTableMateriais.setSelectionForeground(Color.RED);
            }else{
                jTableMateriais.setSelectionForeground(Color.BLACK);
            }

        }

        if (evt.getClickCount() == 2) {
            try {

                DefaultTableModel modelo = (DefaultTableModel) jTableMateriais.getModel();

                int[] l = jTableMateriais.getSelectedRows();
                int cow = jTableMateriais.getSelectedColumn();
                int row = jTableMateriais.getSelectedRow();

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
                    System.out.println("table " + jTableMateriais.getSelectedRowCount() + " - " + row);
                }

                    cod = (String) modelo.getValueAt(row, 0);
                    desc = (String) modelo.getValueAt(row, 1);
                    unidade = (String) modelo.getValueAt(row, 2);
                     estoque = (Integer) modelo.getValueAt(row, 3);
                     minimo = (Integer) modelo.getValueAt(row, 4);
                    
                        valor = dff.parse((String) modelo.getValueAt(row, 5)).doubleValue();
                    } catch (ParseException ex) {
                        Logger.getLogger(SaidaMaterial.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro native conversão de valores: "+ "\nCausa: "+ex.getCause());
                    }
                    String fornecedor = (String) modelo.getValueAt(row, 6);
                     try {
                        data = (Date) sfd.parse((String)modelo.getValueAt(row, 7));
                    } catch (ParseException ex) {
                        Logger.getLogger(SaidaMaterial.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro native conversão de data: "+ "\nCausa: "+ex.getCause());
                    }

                new ConfirmaSaida(inicial, true, cod, desc, dinheiro, qtd).setVisible(true);

                Session sessao = HibernateUtil.getSessionFactory().openSession();
                Transaction transacao = sessao.beginTransaction();
                modelo.setValueAt(ConfirmaSaida.qtdVenda, row, 3);
                modelo.setValueAt(moeda(ConfirmaSaida.valor), row, 5);
                jTableMateriais.updateUI();
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
                Logger.getLogger(SaidaMaterial.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro SQL: "+ "\nCausa: "+ex.getCause());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SaidaMaterial.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro classe não encontrada: "+ "\nCausa: "+ex.getCause());
            }

        }
}//GEN-LAST:event_jTableMateriaisMouseClicked

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

            Query materiais = sessao.createQuery("from Materiais m order by m.descricao, m.codmateriais");
            List listMateriais = materiais.list();

            mostraLista(listMateriais);

            transacao.commit();
            sessao.close();

            System.out.println("Open!!!");
    }//GEN-LAST:event_formInternalFrameOpened

    private void jTextFieldFornecedorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldFornecedorFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFornecedorFocusGained

    private void jTextFieldFornecedorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldFornecedorFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFornecedorFocusLost

    private void jTextFieldFornecedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFornecedorKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            jTableMateriais.requestFocus();
            evt.setKeyCode(java.awt.event.KeyEvent.VK_ENTER);

        }
    }//GEN-LAST:event_jTextFieldFornecedorKeyPressed

    private void jTextFieldFornecedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFornecedorKeyReleased
         boolean Consulta = true;

        javax.swing.table.DefaultTableModel tableResultado = (javax.swing.table.DefaultTableModel)jTableMateriais.getModel();

        if(jTextFieldFornecedor.getText().equals("")){

            //tableResultado.setRowCount(0);
            Consulta = false;
            //return;
        }else{
            String fornecedorMaterial = jTextFieldFornecedor.getText();

            //mostraTabelaVazia();
            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            Query materiais = sessao.createQuery("from Materiais m where m.fornecedor LIKE '"+fornecedorMaterial+"%' order by m.descricao, m.codmateriais");

            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listMateriais = materiais.list();
            Iterator m = listMateriais.iterator();

            tableResultado.setRowCount(0);
            jLabelRetorno.setText("");
            
            while(m.hasNext()){
                 Materiais mat = (Materiais) m.next();

                 tableResultado.addRow(new Object[] {mat.getCodmateriais(), mat.getDescricao(), mat.getUnidade(), mat.getQtdestoque(), mat.getQtdminima(), moeda(mat.getValor()), mat.getFornecedor(), sfd.format(mat.getDatacadastro()), ""});
                 System.out.println("Desc: "+ mat.getDescricao());
                 jTableMateriais.updateUI();
            }

            if (listMateriais.isEmpty()) {

                jLabelRetorno.setText("Nenhum material encontrado com este fornecedor.!!");
                jTextFieldFornecedor.requestFocus();
                return ;
            }

            transacao.commit();
            sessao.close();


        }
    }//GEN-LAST:event_jTextFieldFornecedorKeyReleased

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
            Session sessao = HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
            Transaction transacao = sessao.beginTransaction();

            Query materiais = sessao.createQuery("from Materiais m order by m.descricao, m.codmateriais");
            List listMateriais = materiais.list();

            mostraLista(listMateriais);

            transacao.commit();
            sessao.close();

            jTableMateriais.updateUI();
            jLabelRetorno.setText("");
            jTextFieldDesc.setText("");
            jTextFieldCod.setText("");
            jTextFieldFornecedor.setText("");

            System.out.println("Closed!!!");
    }//GEN-LAST:event_formInternalFrameClosed

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
    private javax.swing.JButton jButtonAtualizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelRetorno;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableMateriais;
    private javax.swing.JTextField jTextFieldCod;
    private javax.swing.JTextField jTextFieldDesc;
    private javax.swing.JTextField jTextFieldFornecedor;
    // End of variables declaration//GEN-END:variables

}
