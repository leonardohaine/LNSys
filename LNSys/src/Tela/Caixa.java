/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Caixa.java
 *
 * Created on 14/08/2011, 18:31:03
 */

package Tela;

import LN.entity.Materiais;
import Util.HibernateUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Leonardo
 */
public class Caixa extends javax.swing.JInternalFrame {

    private Inicial inicial = null;
    private static Caixa caixa;

    SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
    /** Creates new form Caixa */
    public Caixa(Inicial instancia) throws PropertyVetoException {
        initComponents();
        this.setSelected(true);
        this.requestFocus();
        this.setClosable(true);
        this.setIconifiable(true);
        setResizable(false);

        setFrameIcon(new ImageIcon(this.getClass().getClassLoader().getResource("imagens/IconeLN.png")));

         Dimension centraliza = Toolkit.getDefaultToolkit().getScreenSize();
         this.setLocation((centraliza.width-this.getSize().width)/2,
                      (centraliza.height-this.getSize().height)/2);

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

     public static String moeda(double vlr){
            java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
            return df.format( vlr );
    }

    public static Caixa getInstance(Inicial instancia) throws PropertyVetoException {
        if(caixa == null)
            caixa = new Caixa(instancia);
        return caixa;
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
                if(col == 4){
                    return true;
                }else{
                    return false;
                }
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel13 = new javax.swing.JLabel();
        jTextFieldCodProduto = new javax.swing.JTextField();
        jButtonConsulta = new javax.swing.JButton();
        jButtonExcluirItem = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldQuant = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCaixa = new javax.swing.JTable();
        jButtonFechar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldValorRecebido = new javax.swing.JTextField();
        jLabelTotal = new javax.swing.JLabel();
        jLabelTroco = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelQtdItens = new javax.swing.JLabel();
        jButtonConfirma = new javax.swing.JButton();
        jLabelRetorno = new javax.swing.JLabel();

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Quantidade:");

        jTextFieldCodProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCodProdutoKeyPressed(evt);
            }
        });

        jButtonConsulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/search-32.png"))); // NOI18N
        jButtonConsulta.setText("CONSULTAR [F4]");

        jButtonExcluirItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cancel-32.png"))); // NOI18N
        jButtonExcluirItem.setText("Excluir Item");
        jButtonExcluirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirItemActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Cód Produto:");

        jTextFieldQuant.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextFieldQuant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldQuant.setText("1");
        jTextFieldQuant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldQuantKeyPressed(evt);
            }
        });

        jTableCaixa.setAutoCreateRowSorter(true);
        jTableCaixa.setBackground(new java.awt.Color(0, 0, 0));
        jTableCaixa.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
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

        jButtonFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/door-32.png"))); // NOI18N
        jButtonFechar.setText("FECHAR");
        jButtonFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFecharActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("VALOR TOTAL:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("DINHEIRO:");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("TROCO:");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jTextFieldValorRecebido.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jTextFieldValorRecebido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldValorRecebido.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTextFieldValorRecebido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldValorRecebidoKeyPressed(evt);
            }
        });

        jLabelTotal.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabelTotal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabelTotal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabelTroco.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabelTroco.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabelTroco.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel6.setText("Quantidade de Itens:");

        jButtonConfirma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/check-32.png"))); // NOI18N
        jButtonConfirma.setText("CONFIRMA");
        jButtonConfirma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonConfirmaKeyPressed(evt);
            }
        });

        jLabelRetorno.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelRetorno.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jButtonConsulta)
                                .addGap(72, 72, 72)
                                .addComponent(jButtonExcluirItem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonFechar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelQtdItens, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTroco, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
                            .addComponent(jButtonConfirma)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldValorRecebido, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                                    .addComponent(jLabelTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(357, 357, 357)
                        .addComponent(jLabelRetorno, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCodProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelRetorno, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jTextFieldQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldCodProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel14)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldValorRecebido, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(84, 84, 84)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(111, 111, 111))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabelQtdItens, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonConfirma)
                            .addComponent(jButtonExcluirItem)
                            .addComponent(jButtonFechar))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCodProdutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCodProdutoKeyPressed
        Session sessao = HibernateUtil.getSessionFactory().openSession();// fabricaDeSessoes.openSession();
        Transaction transacao = sessao.beginTransaction();

        DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();

        javax.swing.table.DefaultTableModel tableResultado = (javax.swing.table.DefaultTableModel)jTableCaixa.getModel();
        double valorTotalGeral = 0;
        int x = 0;

        if (evt.getKeyCode() == evt.VK_ENTER) {

            Query materiais = sessao.createQuery("from Materiais m where m.codmateriais like '"+jTextFieldCodProduto.getText()+"%' order by m.descricao, m.codmateriais");
            List listMateriais = materiais.list();
            Iterator m = listMateriais.iterator();
            Materiais mat = (Materiais) m.next();
            double total =0;
            total = mat.getValor() * Double.valueOf(jTextFieldQuant.getText().replaceAll(",", ".")).doubleValue();
            tableResultado.addRow(new Object[] {mat.getCodmateriais(), mat.getDescricao(), mat.getUnidade(), jTextFieldQuant.getText(), moeda(mat.getValor()), moeda(total)});
            //mostraLista(listMateriais);

//            if(listMateriais.isEmpty()){
//                jLabelRetorno.setText("Produto nâo cadastro!!!");
//            }

            for(;x < tableResultado.getRowCount(); x++){
                if(tableResultado.getRowCount() > 0){
                    try {
                        valorTotalGeral += dff.parse((String) tableResultado.getValueAt(x, 5)).doubleValue();
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(Caixa.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                jLabelQtdItens.setText(String.valueOf(tableResultado.getRowCount()));
                jLabelTotal.setText(moeda(valorTotalGeral));
                jTextFieldCodProduto.selectAll();
           }
        }

        transacao.commit();
        sessao.close();
}//GEN-LAST:event_jTextFieldCodProdutoKeyPressed

    private void jTableCaixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCaixaMouseClicked
       /* String cod = null;
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

        */
}//GEN-LAST:event_jTableCaixaMouseClicked

    private void jTableCaixaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableCaixaKeyPressed
        //    if (evt.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER){
        //        jTableResultado.getSelectedRow();
        //        evt.consume();
        //    }
/*        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_F9){
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
 * /
 */
}//GEN-LAST:event_jTableCaixaKeyPressed

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
/*
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
 *
 */
}//GEN-LAST:event_jTableCaixaKeyReleased

    private void jButtonExcluirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirItemActionPerformed
        int[] selectLines = jTableCaixa.getSelectedRows();

        DefaultTableModel table = (DefaultTableModel)jTableCaixa.getModel();

        if(jTableCaixa.getSelectedRowCount() <= 0){
               JOptionPane.showMessageDialog(null,"Selecione algum item da tabela para deletar", "Atenção", JOptionPane.INFORMATION_MESSAGE );
               return;
        }else{
            Object[] options = { "Sim", "Não" };
            int a = JOptionPane.showOptionDialog(this, "Deseja realmente apagar este item","Atenção",
                JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);

            if(a == JOptionPane.YES_OPTION){
                for( int x = selectLines.length-1;x>=0; x-- ){
                    table.removeRow(selectLines[x]);
                }
                jLabelQtdItens.setText(String.valueOf(table.getRowCount()));
            }
        }
    }//GEN-LAST:event_jButtonExcluirItemActionPerformed

    private void jButtonFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFecharActionPerformed
        doDefaultCloseAction();
    }//GEN-LAST:event_jButtonFecharActionPerformed

    private void jTextFieldValorRecebidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldValorRecebidoKeyPressed
        double total = 0;
        double troco = 0;
        double recebido = 0;

        DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();

        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            try {
                total = dff.parse(jLabelTotal.getText()).doubleValue();
                recebido = dff.parse(jTextFieldValorRecebido.getText()).doubleValue();
                troco = recebido - total;
                jLabelTroco.setText(moeda(troco));
                jButtonConfirma.requestFocus();

            } catch (java.text.ParseException ex) {
                Logger.getLogger(Caixa.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jTextFieldValorRecebidoKeyPressed

    private void jTextFieldQuantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQuantKeyPressed
         if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
             jTextFieldCodProduto.requestFocus();
         }
    }//GEN-LAST:event_jTextFieldQuantKeyPressed

    private void jButtonConfirmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonConfirmaKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
             jButtonConfirma.doClick();
        }
    }//GEN-LAST:event_jButtonConfirmaKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConfirma;
    private javax.swing.JButton jButtonConsulta;
    private javax.swing.JButton jButtonExcluirItem;
    private javax.swing.JButton jButtonFechar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelQtdItens;
    private javax.swing.JLabel jLabelRetorno;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLabel jLabelTroco;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableCaixa;
    private javax.swing.JTextField jTextFieldCodProduto;
    private javax.swing.JTextField jTextFieldQuant;
    private javax.swing.JTextField jTextFieldValorRecebido;
    // End of variables declaration//GEN-END:variables

}
