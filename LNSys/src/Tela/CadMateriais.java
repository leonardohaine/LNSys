/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadProdutos.java
 *
 * Created on 07/05/2011, 18:50:46
 */

package Tela;

import LN.entity.Fornecedores;
import LN.entity.Materiais;
import Util.HibernateUtil;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Leonardo
 */
public class CadMateriais extends javax.swing.JInternalFrame {

    private static CadMateriais cadprodutos;
    private CadMateriais instancia = this;
    private HistoricoEntradaSaida historicoEntradaSaida;

    private SessionFactory fabricaDeSessoes;
    private int codigoMaterial;

    /** Creates new form CadProdutos */
    public CadMateriais(Inicial inicial) {
        initComponents();

        setFrameIcon(new ImageIcon(this.getClass().getClassLoader().getResource("imagens/IconeLN.png")));

        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        String dataBusca = sfd.format(new Date());

        Campos(false);
        LimpaCampos();
        java.lang.Character teste = 'a';
        jButtonSalvar.setEnabled(false);
        jButtonAlterar.setEnabled(false);
        jButtonDeletar.setEnabled(false);
//        jTextFieldPesqCodigo.setEditable(false);
//        jTextFieldPesqDescricao.setEditable(false);
        jTextFieldPesquisa.setVisible(false);

    Dimension centraliza = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((centraliza.width-this.getSize().width)/2,
                      (centraliza.height-this.getSize().height)/2-30);

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();

        Query consultaFornecedores = sessao.createQuery(" from Fornecedores f order by f.fornecedor, f.codfornecedor  ");
                      
        List fornecedoresList = consultaFornecedores.list();
        System.out.println("foram encontradas "+fornecedoresList.size()+" fornecedores");
        Iterator f =fornecedoresList.iterator();

            while (f.hasNext()) {
               Fornecedores fornecedorList = (Fornecedores) f.next();
               System.out.println(fornecedorList.getFornecedor());
                //System.out.println("NEXT"+cliList.getCodclientes());

               jComboBoxFornecedor.addItem(fornecedorList.getFornecedor());
               jComboBoxFornecedor.updateUI();
            }

        mostraTabelaVazia();
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMateriais.getModel();

        Query consultaMateriais = sessao.createQuery(" from Materiais m order by m.descricao ");
        List materiaisList = consultaMateriais.list();
        System.out.println("foram encontradas "+materiaisList.size()+" materiais");
        Iterator i = materiaisList.iterator();

        while (i.hasNext()) {
            Materiais materiais = (Materiais) i.next();

            tableResultado.addRow(new Object[] {materiais.getCodmateriais(), materiais.getDescricao()});
        }

        transacao.commit();
        sessao.close();



        //jTextFieldNOrcamentoBusca.requestFocus();
        //jPanelCompras.setVisible(false);
    }


     public void montaTabela(){

        System.out.println("Montando Tabela");

        //jTableClientes.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
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
        System.out.println("Ajustando tamanho das colunas");
        jTableMateriais.getColumnModel().getColumn(0).setPreferredWidth(50);//0TEM
        jTableMateriais.getColumnModel().getColumn(0).setMinWidth(50);
        jTableMateriais.getColumnModel().getColumn(1).setPreferredWidth(485);//85 QUANT
        jTableMateriais.getColumnModel().getColumn(1).setMinWidth(485);

    }


    private void mostraTabelaVazia(){
           //montaTabela();

            jTableMateriais.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][][] {
                    {null, null},
                },
                new String [] {
                    "Código","Descrição"
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

    public static CadMateriais getInstance(Inicial instancia) throws PropertyVetoException, SQLException, ClassNotFoundException {
        if(cadprodutos == null)
            cadprodutos = new CadMateriais(instancia);
        return cadprodutos;
    }

    public void Campos( boolean x){
        Component[] c = jPanelCliente.getComponents();
        //Component[] p = jPanel2.getComponents();
        for(int j = 0; j < c.length; j++) {
           if(c[j] instanceof JTextComponent) {
              JTextComponent cmp = (JTextComponent)c[j];
              cmp.setEditable(x);
//              jTextFieldValorcompra.setEditable(x);
//              jTextFieldQtdcompra.setEditable(x);
//              jComboBoxDataCompra.setEnabled(x);
              jComboBoxFornecedor.setEnabled(x);

          }
        }

//        for(int i = 0; i < p.length; i++) {
//           if(c[i] instanceof JTextComponent) {
//              JTextComponent cmp = (JTextComponent)p[i];
//              cmp.setEditable(x);
//
//
//          }
//        }
    }

    public void LimpaCampos(){
        Component[] c = jPanelCliente.getComponents();
        for(int j = 0; j < c.length; j++) {
           if(c[j] instanceof JTextComponent) {
              JTextComponent cmp = (JTextComponent)c[j];
              cmp.setText("");
              //DefaultTableModel  dtm =(DefaultTableModel)jTableHistoricoOrcamento.getModel();
              //dtm.setRowCount(0);
              //jTextFieldDataCompra.setText("");
              jTextFieldDescricao.requestFocus();
//              jTextFieldValorcompra.setText("");
//              jTextFieldQtdcompra.setText("");
              jComboBoxFornecedor.setEditable(true);
              jComboBoxFornecedor.setSelectedItem("");
              jComboBoxFornecedor.setEditable(false);
              //jLabelRetorno.setText("");
              //jComboBoxDataCompra.setSelectedItem("");
          }
        }
    }
     public void LimpaCamposP(){

        //Component[] p = jPanel2.getComponents();
//        for(int i = 0; i < p.length; i++) {
//           if(p[i] instanceof JTextComponent) {
//              JTextComponent cmp = (JTextComponent)p[i];
//              cmp.setText("");
//              //DefaultTableModel  dtm =(DefaultTableModel)jTableHistoricoOrcamento.getModel();
//              //dtm.setRowCount(0);
//              //jTextFieldDataCompra.setText("");
//              jTextFieldDescricao.requestFocus();
//              //jTextFieldValorcompra.setText("");
//              //jTextFieldQtdcompra.setText("");
//              jComboBoxFornecedor.setEditable(true);
//              jComboBoxFornecedor.setSelectedItem("");
//              jComboBoxFornecedor.setEditable(false);
//              jLabelRetorno.setText("");
//              //jComboBoxDataCompra.setSelectedItem("");
//          }
//        }

    }

    public static String getDataTelaToBD(String x) {
        java.lang.String dia, mes, ano = "";
        java.lang.String data = "";
        dia = x.substring(0,2);
       //System.out.println("DIA " + dia);
        mes = x.substring(3,5);
        ano = x.substring(6, 10);
        data = mes +"/"+ dia + "/" + ano;
        //System.out.println("D  " + data );
        return data;
    }

    public static String moeda(double vlr){
            java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
            return df.format( vlr );
     }

    public int getNextvalHistorico(){

        int nextVal = 0;
    try{
       Session sessao = HibernateUtil.getSessionFactory().openSession();
       Transaction transacao = sessao.beginTransaction();

        SQLQuery query = sessao.createSQLQuery("select nextval('seq_historico')");
           Object object = query.uniqueResult();
           nextVal = ( (java.math.BigInteger)object ).intValue();
           System.out.println("NEXTVAL HISTORICO "+nextVal);


        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Errono gerador de sequencia: "+ e);
        }
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

        jPanelCliente = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldDescricao = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldQtdEstoque = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldValor = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldQtdMin = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldCodigo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldData = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldUnidade = new javax.swing.JTextField();
        jComboBoxFornecedor = new javax.swing.JComboBox();
        jButtonDeletar = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonSalvar = new javax.swing.JButton();
        jButtonNovo = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jButtonHistorico = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableMateriais = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldPesquisa = new javax.swing.JTextField();

        setIconifiable(true);
        setTitle("Cadastro de Materiais");
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
            }
        });

        jPanelCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro de materiais", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("Descrição:");

        jLabel2.setText("Qtd estoque:");

        jLabel3.setText("Fornecedor:");

        jLabel4.setText("Valor:");

        jLabel14.setText("Qtd minima:");

        jLabel19.setText("Código:");

        jTextFieldCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setText("Data:");

        jTextFieldData.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel15.setText("Unidade:");

        jComboBoxFornecedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SELECIONE" }));

        javax.swing.GroupLayout jPanelClienteLayout = new javax.swing.GroupLayout(jPanelCliente);
        jPanelCliente.setLayout(jPanelClienteLayout);
        jPanelClienteLayout.setHorizontalGroup(
            jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelClienteLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(15, 15, 15)
                        .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
                    .addGroup(jPanelClienteLayout.createSequentialGroup()
                        .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelClienteLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelClienteLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldQtdEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelClienteLayout.createSequentialGroup()
                                .addComponent(jTextFieldQtdMin, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldValor, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelClienteLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanelClienteLayout.setVerticalGroup(
            jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClienteLayout.createSequentialGroup()
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldQtdEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldQtdMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonDeletar.setMnemonic('D');
        jButtonDeletar.setText("Deletar");
        jButtonDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeletarActionPerformed(evt);
            }
        });

        jButtonAlterar.setMnemonic('A');
        jButtonAlterar.setText("Alterar");
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

        jButtonSalvar.setMnemonic('S');
        jButtonSalvar.setText("Salvar");
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });

        jButtonNovo.setMnemonic('N');
        jButtonNovo.setText("Novo");
        jButtonNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoActionPerformed(evt);
            }
        });

        jButtonSair.setMnemonic('R');
        jButtonSair.setText("Sair");
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });

        jButtonHistorico.setText("Histórico");
        jButtonHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHistoricoActionPerformed(evt);
            }
        });

        jTableMateriais.setBackground(new java.awt.Color(0, 121, 76));
        jTableMateriais.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jTableMateriais.setForeground(new java.awt.Color(255, 255, 255));
        jTableMateriais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Código", "Descrição"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMateriais.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableMateriais.setFillsViewportHeight(true);
        jTableMateriais.setGridColor(new java.awt.Color(255, 255, 255));
        jTableMateriais.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTableMateriais.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTableMateriais.getTableHeader().setResizingAllowed(false);
        jTableMateriais.getTableHeader().setReorderingAllowed(false);
        jTableMateriais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMateriaisMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableMateriaisMousePressed(evt);
            }
        });
        jTableMateriais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableMateriaisKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableMateriais);

        jLabel6.setText("Pesquisa:");

        jTextFieldPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDeletar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonHistorico)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(jButtonSair))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSair)
                    .addComponent(jButtonNovo)
                    .addComponent(jButtonSalvar)
                    .addComponent(jButtonAlterar)
                    .addComponent(jButtonDeletar)
                    .addComponent(jButtonHistorico))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeletarActionPerformed
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();

        if(jTextFieldCodigo.getText().equals("") ){
            JOptionPane.showMessageDialog(null,"Pesquise algum material para deletar", "Atenção", JOptionPane.INFORMATION_MESSAGE );
            return;
        }else{
            Object[] options = { "Sim", "Não" };
            int a = JOptionPane.showOptionDialog(this, "Deseja realmente apagar este material?","Atenção",
                    JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if(a == JOptionPane.YES_OPTION){
                Materiais newFornecedor = (Materiais) sessao.load(Materiais.class,jTextFieldCodigo.getText());
                //System.out.println("DATA "+ new Date("dd/MM/yyyy"));
                sessao.delete(newFornecedor);
                sessao.flush();
                transacao.commit();
                sessao.close();

                DefaultTableModel tableResultado =(DefaultTableModel)jTableMateriais.getModel();
                tableResultado.removeRow(jTableMateriais.getSelectedRow());
                jTableMateriais.updateUI();

                LimpaCampos();
                JOptionPane.showMessageDialog(null, "Material "+jTextFieldCodigo.getText()+" deletado com sucesso");

                System.out.println("Material <html><b>"+jTextFieldCodigo.getText()+"</b></html> deletado com sucesso!");
            }else{
                System.out.println("clicou em NÂO");
                return;
            }
        }
}//GEN-LAST:event_jButtonDeletarActionPerformed

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        if(jButtonAlterar.getText().equals("Atualizar")){
            jButtonAlterar.setText("Alterar");
            jTableMateriais.setEnabled(true);
            Campos(false);
            System.out.println("ATUALIZANDO Material");


        String codigo, descricao, unidade, fornecedor, data;
        int  qtdestoque, qtdmin = 0;
        double valor = 0;

        DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();

        codigo = jTextFieldCodigo.getText();
        descricao = jTextFieldDescricao.getText();
        unidade = jTextFieldUnidade.getText();
        qtdestoque = Integer.valueOf(jTextFieldQtdEstoque.getText());
        qtdmin = Integer.valueOf(jTextFieldQtdMin.getText());
        fornecedor = (String)jComboBoxFornecedor.getSelectedItem();
        data = jTextFieldData.getText();

        Date date = null;
            try {
               date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao converter data!", "Erro data", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(CadMateriais.class.getName()).log(Level.SEVERE, null, ex);
            }

        try {
            valor = dff.parse(jTextFieldValor.getText().toUpperCase()).doubleValue();
            //cidade = jTextFieldDataCompra.getText().toUpperCase();
        } catch (ParseException ex) {
            Logger.getLogger(CadMateriais.class.getName()).log(Level.SEVERE, null, ex);
        }

            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();

            Materiais materiais = new Materiais(codigo, descricao, unidade, qtdestoque, qtdmin, valor, fornecedor, date);

            sessao.update(materiais);
            transacao.commit();
            sessao.close();

            jTableMateriais.setValueAt(descricao, jTableMateriais.getSelectedRow(), 1);
            jTableMateriais.updateUI();

            Campos(false);
            System.out.println("COMMIT UPDATE");
            JOptionPane.showMessageDialog(null, "Material atualizado com sucesso");
        }else{
            jButtonAlterar.setText("Atualizar");
            Campos(true);
            jTableMateriais.setEnabled(false);
            jTextFieldCodigo.setEditable(false);

        }
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        String codigo, descricao, unidade, fornecedor, data;
        int  qtdestoque, qtdmin = 0;
        double valor = 0;

        if (jComboBoxFornecedor.getSelectedItem().equals("SELECIONE")) {
            JOptionPane.showMessageDialog(null, "Selecione um fornecedor", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            return ;
        }

        DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();

        codigo = jTextFieldCodigo.getText();
        descricao = jTextFieldDescricao.getText();
        unidade = jTextFieldUnidade.getText();
        qtdestoque = Integer.valueOf(jTextFieldQtdEstoque.getText());
        qtdmin = Integer.valueOf(jTextFieldQtdMin.getText());
        fornecedor = (String)jComboBoxFornecedor.getSelectedItem();
        data = jTextFieldData.getText();

        Date date = null;

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (ParseException ex) {
            Logger.getLogger(CadMateriais.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null, "Errono native data: "+ ex);
        }

       try {
            valor = dff.parse(jTextFieldValor.getText().toUpperCase()).doubleValue();
            //cidade = jTextFieldDataCompra.getText().toUpperCase();
        } catch (ParseException ex) {
            Logger.getLogger(CadMateriais.class.getName()).log(Level.SEVERE, null, ex);
        }
            //valor = Double.valueOf(jTextFieldValor.getText().replaceAll(",", "."));
            //valor = dff.parse(jTextFieldValor.getText().replaceAll(",", ".").toUpperCase()).doubleValue();
            System.out.println("Valor: "+ valor);
            //cidade = jTextFielble.dDataCompra.getText().toUpperCase();
       
        //cidade = jTextFieldDataCompra.getText().toUpperCase();

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();

        Materiais materiais = new Materiais(codigo, descricao, unidade, qtdestoque, qtdmin, valor, fornecedor, date);
                                       //(codmateriais, descricao, qtdestoque, qtdminima,valor, fornecedor, datacadastro) {
        sessao.save(materiais);
        transacao.commit();
        sessao.close();

        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMateriais.getModel();
        tableResultado.addRow(new Object[] {codigo, descricao});
        jTableMateriais.updateUI();


        LimpaCampos();
        Campos(false);
        if(jButtonNovo.getText().equals("Cancelar")){
           jButtonNovo.setText("Novo");
           jTableMateriais.setEnabled(true);
           jButtonSalvar.setEnabled(false);
           JOptionPane.showMessageDialog(null, "Material incluído com sucesso");
        }
}//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jButtonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoActionPerformed
        if(jButtonNovo.getText().equals("Cancelar")){
            jButtonNovo.setText("Novo");
            jButtonSalvar.setEnabled(false);
            Campos(false);
            LimpaCampos();
        }else{
            Campos(true);
            jButtonNovo.setText("Cancelar");
            jButtonAlterar.setEnabled(false);
            jTableMateriais.setEnabled(false);
            jButtonDeletar.setEnabled(false);
            jButtonSalvar.setEnabled(true);
            //jButtonPesquisar.setEnabled(false);

            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();

            SQLQuery query = sessao.createSQLQuery("select nextval('seq_materiais')");
            Object object = query.uniqueResult();
            System.out.println("NEXTVAL NOVO MATERIAL "+( (java.math.BigInteger)object ).longValue());
            codigoMaterial = (int) ((java.math.BigInteger)object).longValue();
            LimpaCampos();

            jTextFieldCodigo.setText(((java.math.BigInteger)object ).toString());
            jTextFieldCodigo.setEditable(false);

            SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
            String data = sfd.format(new Date());

            transacao.commit();
            sessao.close();
            jTextFieldData.setText(data);

            //
        }
}//GEN-LAST:event_jButtonNovoActionPerformed

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        doDefaultCloseAction();
}//GEN-LAST:event_jButtonSairActionPerformed

    private void jButtonHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHistoricoActionPerformed
        historicoEntradaSaida = null;

    try {

            historicoEntradaSaida = HistoricoEntradaSaida.getInstance(instancia);
            if (!historicoEntradaSaida.isVisible()) {
                historicoEntradaSaida.setVisible(true);
                cadprodutos.getParent().add(historicoEntradaSaida, javax.swing.JLayeredPane.POPUP_LAYER);
                historicoEntradaSaida.show();
                historicoEntradaSaida.toFront();
                historicoEntradaSaida.setSelected(true);
                //telaVendas.setLocation(20, 10);

            }
            historicoEntradaSaida.setSelected(true);

        } catch (PropertyVetoException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao abrir tela Historico de Entrada e Saída: "+ ex);
        }
    }//GEN-LAST:event_jButtonHistoricoActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
         LimpaCampos();
         LimpaCamposP();
         Campos(false);



        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();

        Query consultaFornecedores = sessao.createQuery(" from Fornecedores f order by f.fornecedor, f.codfornecedor  ");

        List fornecedoresList = consultaFornecedores.list();
        System.out.println("foram encontradas "+fornecedoresList.size()+" fornecedores");
        Iterator f =fornecedoresList.iterator();

            while (f.hasNext()) {
               Fornecedores fornecedorList = (Fornecedores) f.next();
               System.out.println(fornecedorList.getFornecedor());
                //System.out.println("NEXT"+cliList.getCodclientes());


               jComboBoxFornecedor.addItem(fornecedorList.getFornecedor());
               jComboBoxFornecedor.updateUI();
            }
        transacao.commit();
        sessao.close();
    }//GEN-LAST:event_formInternalFrameClosed

    private void jTableMateriaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMateriaisMouseClicked
        if (evt.getClickCount() == 1 || evt.getClickCount() == 2) {


            SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();

            javax.swing.table.DefaultTableModel table =(javax.swing.table.DefaultTableModel)jTableMateriais.getModel();

            int line = jTableMateriais.getSelectedRow();

            //            if(jTableClientes.getSelectedRowCount() <= 0){
            //                jButtonAlterar.setEnabled(false);
            //                jButtonDeletar.setEnabled(false);
            //                System.out.println("FALSE");
            //            }else{

            // }

            String codigo = (String) table.getValueAt(line, 0);
            System.out.println("Codigo "+ codigo);

            ////if(jTextFieldPesqCodigo.getText().equals("")){
            //Consulta = false;
            //LimpaCampos();
            //jTextFieldPesqCodigo.requestFocus();
            //}else{

            Query consultaMaterial = sessao.createQuery("from Materiais as m where m.codmateriais = '"+codigo+"' ");

        //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List ListMateriais = consultaMaterial.list();
        //List materiais = consultaMat.list();
        //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator i = ListMateriais.iterator();


            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
            //System.out.println("MAT ->"+ matOrca.getProduto());

            while (i.hasNext()) {

                Materiais materiais = (Materiais) i.next();


                jTextFieldCodigo.setText(String.valueOf(materiais.getCodmateriais()));
                jTextFieldDescricao.setText(materiais.getDescricao());
                jTextFieldUnidade.setText(materiais.getUnidade());
                jTextFieldQtdEstoque.setText(String.valueOf(materiais.getQtdestoque()));
                jTextFieldQtdMin.setText(String.valueOf(materiais.getQtdminima()));
                jComboBoxFornecedor.setEditable(true);
                jComboBoxFornecedor.setSelectedItem(materiais.getFornecedor());
                jComboBoxFornecedor.setEditable(false);
                System.out.println("FOrn "+ materiais.getFornecedor());
                jTextFieldValor.setText(String.valueOf(moeda(materiais.getValor())));
                jTextFieldData.setText(sfd.format(materiais.getDatacadastro()));
                //jLabelRetorno.setText("");
                //                            if (listClientes.isEmpty()) {
                //                                LimpaCampos();
                //                                jLabelRetorno.setText("Cliente não encontrado!!");
                //                                jTextFieldPesqCodigo.requestFocus();
                //                                return ;
                //                            }

                jButtonAlterar.setEnabled(true);
                jButtonDeletar.setEnabled(true);
                jButtonSalvar.setEnabled(false);
                //System.out.println("TRUE");
            }



//            if(listClientes.isEmpty()){
//                //System.out.println("FALSEEEEEE");
//                Query consultaOrcaNew = sessao.createQuery(" from Clientes as c where c.codcliente = '"+codigo+"' order by c.codcliente");
//                List ListClientesNew = consultaOrcaNew.list();
//                Iterator iNew = ListClientesNew.iterator();
//
//
//                //Orcamentos orca = null;
//                Clientes  clientesNew = null;
//                javax.swing.table.DefaultTableModel tableResultadoNew =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
//                tableResultadoNew.setRowCount(0);
//                //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
//                //System.out.println("MAT ->"+ matOrca.getProduto());
//
//                while (iNew.hasNext()) {
//
//                    //Object[] objeto = (Object[]) iNew.next();
//                    //orcaNew = (Orcamentos) objeto[0];
//                    //clientesNew = (Clientes) objeto[1];
//
//                    clientesNew = (Clientes) iNew.next();
//
//                    jTextFieldCodigo.setText(String.valueOf(clientesNew.getCodcliente()));
//                    jTextFieldRazaoSocial.setText(clientesNew.getRazaosocial());
//                    jTextFieldFantasia.setText(clientesNew.getFanatasia());
//                    jTextFieldNomeContato.setText(clientesNew.getNomecontato());
//                    jTextFieldEndereco.setText(clientesNew.getEndereco());
//                    jTextFieldBairro.setText(clientesNew.getBairro());
//                    jTextFieldCidade.setText(clientesNew.getCidade());
//                    jFormattedTextFieldCep.setText(clientesNew.getCep());
//                    jFormattedTextFieldTelefone.setText(clientesNew.getTelefone());
//                    jFormattedTextFieldCelular.setText(clientesNew.getCelular());
//
//                    if(clientesNew.getDatanascimento() == null){
//                        jFormattedTextFieldDataNasc.setText("  /  /    ");
//                    }else{
//                        jFormattedTextFieldDataNasc.setText(sfd.format(clientesNew.getDatanascimento()));
//                    }
//                    jTextFieldRG.setText(clientesNew.getRg());
//                    jFormattedTextFieldCPF.setText(clientesNew.getCpf());
//                    jTextFieldCNPJ.setText(clientesNew.getCnpj());
//                    jTextFieldInscricaoEstadual.setText(clientesNew.getInscricaoestadual());
//                    jTextFieldObservacao.setText(clientesNew.getObservacao());
//                    jTextFieldDataCadastro.setText(sfd.format(clientesNew.getDatacadastro()));
//                    jLabelRetorno.setText("");
//                    //tableResultado.addRow(new Object[] {orca.getCodorcamentos(), orca.getDatacadastro()});
//
//                }
//
//                if (ListClientesNew.isEmpty()) {
//                    //LimpaCampos();
//                    jLabelRetorno.setText("Cliente não encontrado!!");
//                    //jTextFieldPesqCodigo.requestFocus();
//                    return ;
//                }
//
//            }

            //}
        }
}//GEN-LAST:event_jTableMateriaisMouseClicked

    private void jTableMateriaisMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMateriaisMousePressed
        if (evt.getButton() == 1 || evt.getButton() == 2) {

            SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();

            javax.swing.table.DefaultTableModel table =(javax.swing.table.DefaultTableModel)jTableMateriais.getModel();

            int line = jTableMateriais.getSelectedRow();

            String codigo = (String) table.getValueAt(line, 0);
            System.out.println("CODIGO "+ codigo);
            jButtonAlterar.setEnabled(true);
            jButtonDeletar.setEnabled(true);
            jButtonSalvar.setEnabled(false);
            //            if(jTextFieldPesqCodigo.getText().equals("")){
            //                //Consulta = false;
            //                LimpaCampos();
            //                jTextFieldPesqCodigo.requestFocus();
            //            }else{

            Query consultaMaterial = sessao.createQuery("from Materiais as m where m.codmateriais = '"+codigo+"' ");

        //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List ListMateriais = consultaMaterial.list();
        //List materiais = consultaMat.list();
        //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator i = ListMateriais.iterator();


            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
            //System.out.println("MAT ->"+ matOrca.getProduto());

            while (i.hasNext()) {

                Materiais materiais = (Materiais) i.next();


                jTextFieldCodigo.setText(String.valueOf(materiais.getCodmateriais()));
                jTextFieldDescricao.setText(materiais.getDescricao());
                jTextFieldUnidade.setText(materiais.getUnidade());
                jTextFieldQtdEstoque.setText(String.valueOf(materiais.getQtdestoque()));
                jTextFieldQtdMin.setText(String.valueOf(materiais.getQtdminima()));
                jComboBoxFornecedor.setEditable(true);
                jComboBoxFornecedor.setSelectedItem(materiais.getFornecedor());
                jComboBoxFornecedor.setEditable(false);
                System.out.println("FOrn "+ materiais.getFornecedor());
                jTextFieldValor.setText(String.valueOf(moeda(materiais.getValor())));
                jTextFieldData.setText(sfd.format(materiais.getDatacadastro()));
                //jLabelRetorno.setText("");
                //                            if (listClientes.isEmpty()) {
                //                                LimpaCampos();
                //                                jLabelRetorno.setText("Cliente não encontrado!!");
                //                                jTextFieldPesqCodigo.requestFocus();
                //                                return ;
                //                            }

                jButtonAlterar.setEnabled(true);
                jButtonDeletar.setEnabled(true);
                jButtonSalvar.setEnabled(false);
                //System.out.println("TRUE");
            }


//            if(listClientes.isEmpty()){
//                System.out.println("FALSEEEEEE");
//                Query consultaOrcaNew = sessao.createQuery(" from Clientes as c where c.codcliente = '"+codigo+"' order by c.codcliente");
//                List ListClientesNew = consultaOrcaNew.list();
//                Iterator iNew = ListClientesNew.iterator();
//
//
//                //Orcamentos orca = null;
//                Clientes  clientesNew = null;
//                javax.swing.table.DefaultTableModel tableResultadoNew =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
//                tableResultadoNew.setRowCount(0);
//                //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
//                //System.out.println("MAT ->"+ matOrca.getProduto());
//
//                while (iNew.hasNext()) {
//
//                    //Object[] objeto = (Object[]) iNew.next();
//                    //orcaNew = (Orcamentos) objeto[0];
//                    //clientesNew = (Clientes) objeto[1];
//
//                    clientesNew = (Clientes) iNew.next();
//
//                    jTextFieldCodigo.setText(String.valueOf(clientesNew.getCodcliente()));
//                    jTextFieldRazaoSocial.setText(clientesNew.getRazaosocial());
//                    jTextFieldFantasia.setText(clientesNew.getFanatasia());
//                    jTextFieldNomeContato.setText(clientesNew.getNomecontato());
//                    jTextFieldEndereco.setText(clientesNew.getEndereco());
//                    jTextFieldBairro.setText(clientesNew.getBairro());
//                    jTextFieldCidade.setText(clientesNew.getCidade());
//                    jFormattedTextFieldCep.setText(clientesNew.getCep());
//                    jFormattedTextFieldTelefone.setText(clientesNew.getTelefone());
//                    jFormattedTextFieldCelular.setText(clientesNew.getCelular());
//                    jFormattedTextFieldCelular.setText(clientesNew.getCelular());
//                    String nasc = "";
//
//                    if(clientesNew.getDatanascimento() == null){
//                        jFormattedTextFieldDataNasc.setText("  /  /    ");
//                    }else{
//                        jFormattedTextFieldDataNasc.setText(sfd.format(clientesNew.getDatanascimento()));
//                    }
//                    jTextFieldRG.setText(clientesNew.getRg());
//                    jFormattedTextFieldCPF.setText(clientesNew.getCpf());
//                    jTextFieldCNPJ.setText(clientesNew.getCnpj());
//                    jTextFieldInscricaoEstadual.setText(clientesNew.getInscricaoestadual());
//                    jTextFieldObservacao.setText(clientesNew.getObservacao());
//                    jTextFieldDataCadastro.setText(sfd.format(clientesNew.getDatacadastro()));
//                    jLabelRetorno.setText("");
//                    //tableResultado.addRow(new Object[] {orca.getCodorcamentos(), orca.getDatacadastro()});
//
//                }
//
//                if (ListClientesNew.isEmpty()) {
//                    LimpaCampos();
//                    jLabelRetorno.setText("Cliente não encontrado!!");
//                    //jTextFieldPesqCodigo.requestFocus();
//                    return ;
//                }
//
//            }

            //}
        }
}//GEN-LAST:event_jTableMateriaisMousePressed

    private void jTextFieldPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisaKeyReleased
        Runnable runner = new Runnable() {
            public void run() {

                javax.swing.table.DefaultTableModel table =(javax.swing.table.DefaultTableModel)jTableMateriais.getModel();

                //System.out.println("Codigo RUN"+ table.getValueAt(jTableMateriais.getSelectedRow(), 0));

                if(table.getRowCount() > 0 || jTextFieldPesquisa.getText().length() > 0){

                    TableRowSorter sorter = new TableRowSorter(table);
                    jTableMateriais.setRowSorter(sorter);                                         //coluna
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)"+ jTextFieldPesquisa.getText(), 1));
                    try{
                        sorter.convertRowIndexToView(table.getRowCount());
                    }catch(Exception e){
                        System.out.println("Erro Index: "+ e);
                    }

                    System.out.println("PESQUISANDO Indice "+ jTableMateriais.getSelectedRow());
                    //jTableMateriais.updateUI();
                }
            }
       };
       EventQueue.invokeLater(runner);

        //jTableMateriais.getRowSorter(sorter);
    }//GEN-LAST:event_jTextFieldPesquisaKeyReleased

    private void jTableMateriaisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableMateriaisKeyReleased
        if (evt.getKeyCode() == evt.VK_DOWN || evt.getKeyCode() == evt.VK_UP) {
            SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();

            javax.swing.table.DefaultTableModel table =(javax.swing.table.DefaultTableModel)jTableMateriais.getModel();

            int line = jTableMateriais.getSelectedRow();

            String codigo = (String) table.getValueAt(line, 0);

            jButtonAlterar.setEnabled(true);
            jButtonDeletar.setEnabled(true);
            jButtonSalvar.setEnabled(false);
            //            if(jTextFieldPesqCodigo.getText().equals("")){
            //                //Consulta = false;
            //                LimpaCampos();
            //                jTextFieldPesqCodigo.requestFocus();
            //            }else{

            Query consultaMaterial = sessao.createQuery("from Materiais as m where m.codmateriais = '"+codigo+"' ");

        //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List ListMateriais = consultaMaterial.list();
        //List materiais = consultaMat.list();
        //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator i = ListMateriais.iterator();


            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
            //System.out.println("MAT ->"+ matOrca.getProduto());

            while (i.hasNext()) {

                Materiais materiais = (Materiais) i.next();


                jTextFieldCodigo.setText(String.valueOf(materiais.getCodmateriais()));
                jTextFieldDescricao.setText(materiais.getDescricao());
                jTextFieldUnidade.setText(materiais.getUnidade());
                jTextFieldQtdEstoque.setText(String.valueOf(materiais.getQtdestoque()));
                jTextFieldQtdMin.setText(String.valueOf(materiais.getQtdminima()));
                jComboBoxFornecedor.setEditable(true);
                jComboBoxFornecedor.setSelectedItem(materiais.getFornecedor());
                jComboBoxFornecedor.setEditable(false);
                System.out.println("FOrn "+ materiais.getFornecedor());
                jTextFieldValor.setText(String.valueOf(moeda(materiais.getValor())));
                jTextFieldData.setText(sfd.format(materiais.getDatacadastro()));
                //jLabelRetorno.setText("");
                //                            if (listClientes.isEmpty()) {
                //                                LimpaCampos();
                //                                jLabelRetorno.setText("Cliente não encontrado!!");
                //                                jTextFieldPesqCodigo.requestFocus();
                //                                return ;
                //                            }

                jButtonAlterar.setEnabled(true);
                jButtonDeletar.setEnabled(true);
                jButtonSalvar.setEnabled(false);
                //System.out.println("TRUE");
            }
        }
    }//GEN-LAST:event_jTableMateriaisKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonDeletar;
    private javax.swing.JButton jButtonHistorico;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JComboBox jComboBoxFornecedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanelCliente;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableMateriais;
    private javax.swing.JTextField jTextFieldCodigo;
    private javax.swing.JTextField jTextFieldData;
    private javax.swing.JTextField jTextFieldDescricao;
    private javax.swing.JTextField jTextFieldPesquisa;
    private javax.swing.JTextField jTextFieldQtdEstoque;
    private javax.swing.JTextField jTextFieldQtdMin;
    private javax.swing.JTextField jTextFieldUnidade;
    private javax.swing.JTextField jTextFieldValor;
    // End of variables declaration//GEN-END:variables

}
