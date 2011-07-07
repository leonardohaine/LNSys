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

import LN.entity.Clientes;
import LN.entity.Fornecedores;
import LN.entity.Materiais;
import LN.entity.Orcamentos;
import Util.CalendarComboBox;
import Util.HibernateUtil;
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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Leonardo
 */
public class CadMateriais1 extends javax.swing.JInternalFrame {

    private static CadMateriais1 cadprodutos;
    private CadMateriais1 instancia = this;
    private HistoricoEntradaSaida historicoEntradaSaida;

    private SessionFactory fabricaDeSessoes;
    private int codigoMaterial;

    /** Creates new form CadProdutos */
    public CadMateriais1(Inicial inicial) {
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
        jTextFieldPesqCodigo.setEditable(false);
        jTextFieldPesqDescricao.setEditable(false);

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
        transacao.commit();
        sessao.close();



        //jTextFieldNOrcamentoBusca.requestFocus();
        jPanelCompras.setVisible(false);
    }

    public static CadMateriais1 getInstance(Inicial instancia) throws PropertyVetoException, SQLException, ClassNotFoundException {
        if(cadprodutos == null)
            cadprodutos = new CadMateriais1(instancia);
        return cadprodutos;
    }

    public void Campos( boolean x){
        Component[] c = jPanelCliente.getComponents();
        Component[] p = jPanel2.getComponents();
        for(int j = 0; j < c.length; j++) {
           if(c[j] instanceof JTextComponent) {
              JTextComponent cmp = (JTextComponent)c[j];
              cmp.setEditable(x);
              jTextFieldValorcompra.setEditable(x);
              jTextFieldQtdcompra.setEditable(x);
              jComboBoxDataCompra.setEnabled(x);
              jComboBoxFornecedor.setEnabled(x);

          }
        }

        for(int i = 0; i < p.length; i++) {
           if(c[i] instanceof JTextComponent) {
              JTextComponent cmp = (JTextComponent)p[i];
              cmp.setEditable(x);
              

          }
        }
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
              jTextFieldValorcompra.setText("");
              jTextFieldQtdcompra.setText("");
              jComboBoxFornecedor.setEditable(true);
              jComboBoxFornecedor.setSelectedItem("");
              jComboBoxFornecedor.setEditable(false);
              jLabelRetorno.setText("");
              //jComboBoxDataCompra.setSelectedItem("");
          }
        }
    }
     public void LimpaCamposP(){

        Component[] p = jPanel2.getComponents();
        for(int i = 0; i < p.length; i++) {
           if(p[i] instanceof JTextComponent) {
              JTextComponent cmp = (JTextComponent)p[i];
              cmp.setText("");
              //DefaultTableModel  dtm =(DefaultTableModel)jTableHistoricoOrcamento.getModel();
              //dtm.setRowCount(0);
              //jTextFieldDataCompra.setText("");
              jTextFieldDescricao.requestFocus();
              jTextFieldValorcompra.setText("");
              jTextFieldQtdcompra.setText("");
              jComboBoxFornecedor.setEditable(true);
              jComboBoxFornecedor.setSelectedItem("");
              jComboBoxFornecedor.setEditable(false);
              jLabelRetorno.setText("");
              //jComboBoxDataCompra.setSelectedItem("");
          }
        }

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
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldPesqCodigo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldPesqDescricao = new javax.swing.JTextField();
        jButtonPesquisar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jButtonHistorico = new javax.swing.JButton();
        jPanelCompras = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldQtdcompra = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldValorcompra = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButtonOkCompra = new javax.swing.JButton();
        jButtonComprar = new javax.swing.JButton();
        jComboBoxDataCompra = new CalendarComboBox(true);
        jLabelRetorno = new javax.swing.JLabel();

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
                        .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                    .addGroup(jPanelClienteLayout.createSequentialGroup()
                        .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelClienteLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelClienteLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldQtdEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)))
                        .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelClienteLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldQtdMin, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldValor, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                            .addGroup(jPanelClienteLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldData, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))))
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
                    .addComponent(jLabel15)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisa", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel16.setText("Código:");

        jTextFieldPesqCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesqCodigoKeyReleased(evt);
            }
        });

        jLabel17.setText("Descrição:");

        jTextFieldPesqDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesqDescricaoKeyReleased(evt);
            }
        });

        jButtonPesquisar.setMnemonic('P');
        jButtonPesquisar.setText("Pesquisar");
        jButtonPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPesqCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPesqDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonPesquisar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldPesqCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPesqDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jButtonPesquisar))
                .addContainerGap(6, Short.MAX_VALUE))
        );

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

        jPanelCompras.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compras", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel6.setText("Qtd:");

        jTextFieldQtdcompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldQtdcompraKeyPressed(evt);
            }
        });

        jLabel7.setText("Valor:");

        jLabel8.setText("Data de compra:");

        jButtonOkCompra.setText("OK");
        jButtonOkCompra.setEnabled(false);
        jButtonOkCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkCompraActionPerformed(evt);
            }
        });

        jButtonComprar.setText("Comprar");
        jButtonComprar.setEnabled(false);
        jButtonComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComprarActionPerformed(evt);
            }
        });

        jComboBoxDataCompra.setSelectedItem(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        jComboBoxDataCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDataCompraActionPerformed(evt);
            }
        });
        jComboBoxDataCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxDataCompraKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanelComprasLayout = new javax.swing.GroupLayout(jPanelCompras);
        jPanelCompras.setLayout(jPanelComprasLayout);
        jPanelComprasLayout.setHorizontalGroup(
            jPanelComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComprasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldQtdcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldValorcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanelComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonOkCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonComprar))
                .addContainerGap())
        );
        jPanelComprasLayout.setVerticalGroup(
            jPanelComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel6)
                .addComponent(jTextFieldQtdcompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel7)
                .addComponent(jTextFieldValorcompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel8))
            .addGroup(jPanelComprasLayout.createSequentialGroup()
                .addGroup(jPanelComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonComprar)
                    .addComponent(jComboBoxDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonOkCompra))
        );

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelCompras, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonDeletar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonHistorico)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addComponent(jButtonSair))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabelRetorno, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonNovo)
                        .addComponent(jButtonSalvar)
                        .addComponent(jButtonAlterar)
                        .addComponent(jButtonDeletar)
                        .addComponent(jButtonHistorico))
                    .addComponent(jButtonSair))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelRetorno, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)
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
        if(jButtonAlterar.getLabel().equals("Atualizar")){
            jButtonAlterar.setLabel("Alterar");
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
                Logger.getLogger(CadMateriais1.class.getName()).log(Level.SEVERE, null, ex);
            }

        try {
            valor = dff.parse(jTextFieldValor.getText().toUpperCase()).doubleValue();
            //cidade = jTextFieldDataCompra.getText().toUpperCase();
        } catch (ParseException ex) {
            Logger.getLogger(CadMateriais1.class.getName()).log(Level.SEVERE, null, ex);
        }

            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();

            Materiais materiais = new Materiais(codigo, descricao, unidade, qtdestoque, qtdmin, valor, fornecedor, date);

            sessao.update(materiais);
            transacao.commit();
            sessao.close();

            Campos(false);
            System.out.println("COMMIT UPDATE");
            JOptionPane.showMessageDialog(null, "Material atualizado com sucesso");
        }else{
            jButtonAlterar.setText("Atualizar");
            Campos(true);
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
            Logger.getLogger(CadMateriais1.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null, "Errono native data: "+ ex);
        }

       try {
            valor = dff.parse(jTextFieldValor.getText().toUpperCase()).doubleValue();
            //cidade = jTextFieldDataCompra.getText().toUpperCase();
        } catch (ParseException ex) {
            Logger.getLogger(CadMateriais1.class.getName()).log(Level.SEVERE, null, ex);
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

        LimpaCampos();
        Campos(false);
        if(jButtonNovo.getText().equals("Cancelar")){
           jButtonNovo.setText("Novo");
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

    private void jTextFieldPesqCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesqCodigoKeyReleased
        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        String data = sfd.format(new Date());

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

        Query consultaMaterial = sessao.createQuery("from Materiais as m where m.codmateriais = '"+jTextFieldPesqCodigo.getText().toUpperCase()+"' ");

        //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
        List ListMateriais = consultaMaterial.list();
        //List materiais = consultaMat.list();
        //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
        Iterator i = ListMateriais.iterator();

               
        //javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
        //tableResultado.setRowCount(0);
        //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
        //System.out.println("MAT ->"+ matOrca.getProduto());
        if(jTextFieldPesqCodigo.getText().equals("")){
            //Consulta = false;
            LimpaCampos();
            
            jTextFieldPesqCodigo.requestFocus();
        }else{
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

                jTextFieldQtdcompra.setText(String.valueOf(materiais.getQtdestoque()));
                jTextFieldValorcompra.setText(String.valueOf(moeda(materiais.getValor())));
                //jTextFieldDataCompra.setText(clientes.getCidade());

                //tableResultado.addRow(new Object[] {orca.getCodorcamentos(), sfd.format(orca.getDatacadastro())});

            }
             if (ListMateriais.isEmpty()) {
                    LimpaCampos();
                    jLabelRetorno.setText("Material não encontrado!!");
                    jTextFieldPesqCodigo.requestFocus();
                    return ;
                }
        }

        transacao.commit();
        sessao.close();
}//GEN-LAST:event_jTextFieldPesqCodigoKeyReleased

    private void jTextFieldPesqDescricaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesqDescricaoKeyReleased
        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();

        Query consultaMaterial = sessao.createQuery(" from Materiais as m where m.descricao like '"+jTextFieldPesqDescricao.getText().toUpperCase()+"%'");

        //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
        List ListMateriais = consultaMaterial.list();
        //List materiais = consultaMat.list();
        //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
        Iterator i = ListMateriais.iterator();

        Orcamentos orca = null;
        Clientes clientes = null;
        //javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
        //tableResultado.setRowCount(0);
        //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
        //System.out.println("MAT ->"+ matOrca.getProduto());
        if(jTextFieldPesqDescricao.getText().equals("")){
            //Consulta = false;
            LimpaCampos();
            jTextFieldPesqDescricao.requestFocus();
        }else{
            while (i.hasNext()) {

                Materiais materiais = (Materiais) i.next();

                jTextFieldCodigo.setText(String.valueOf(materiais.getCodmateriais()));
                jTextFieldDescricao.setText(materiais.getDescricao());
                jTextFieldUnidade.setText(materiais.getUnidade());
                jTextFieldQtdEstoque.setText(String.valueOf(materiais.getQtdestoque()));
                jTextFieldQtdMin.setText(String.valueOf(materiais.getQtdminima()));
                jComboBoxFornecedor.setSelectedItem(materiais.getFornecedor());
                jTextFieldValor.setText(String.valueOf(moeda(materiais.getValor())));
                jTextFieldData.setText(sfd.format(materiais.getDatacadastro()));

                jTextFieldQtdcompra.setText(String.valueOf(materiais.getQtdestoque()));
                jTextFieldValorcompra.setText(String.valueOf(moeda(materiais.getValor())));

                //tableResultado.addRow(new Object[] {orca.getCodorcamentos(), orca.getDatacadastro()});

            }

            if (ListMateriais.isEmpty()) {
                    LimpaCampos();
                    jLabelRetorno.setText("Material não encontrado!!");
                    jTextFieldPesqDescricao.requestFocus();
                    return ;
                }
        }

        transacao.commit();
        sessao.close();
}//GEN-LAST:event_jTextFieldPesqDescricaoKeyReleased

    private void jButtonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisarActionPerformed
        jTextFieldPesqCodigo.setEditable(true);
        jTextFieldPesqDescricao.setEditable(true);
        jButtonAlterar.setEnabled(true);
        jButtonDeletar.setEnabled(true);
        jButtonSalvar.setEnabled(false);
        jButtonComprar.setEnabled(true);

        //jButtonLimpar.setEnabled(true);
        //jComboBoxDataCompra.setEnabled(true);
        jTextFieldPesqCodigo.requestFocus();
        //Pesquisa();
        if(jButtonNovo.getText().equals("Cancelar")){
            jButtonNovo.setText("Novo");
            LimpaCampos();

            //jComboBoxGrupo.setEditable(true);
        }

        if(jButtonAlterar.getText().equals("Cancelar")){
            jButtonAlterar.setText("Alterar");
        }

        if(jButtonPesquisar.getText().equals("Cancelar")){
            jButtonPesquisar.setText("Pesquisar");
            jTextFieldPesqCodigo.setEditable(false);
            jTextFieldPesqDescricao.setEditable(false);
            jTextFieldPesqCodigo.setText("");
            jTextFieldPesqDescricao.setText("");
            jButtonAlterar.setEnabled(false);
            jButtonDeletar.setEnabled(false);
            jButtonSalvar.setEnabled(false);
            //jButtonLimpar.setEnabled(false);
            jTextFieldPesqCodigo.requestFocus();
            jButtonComprar.setEnabled(false);
            LimpaCampos();
            //jComboBoxDataCompra.setEnabled(false);
        }else{
            jButtonPesquisar.setText("Cancelar");

        }
}//GEN-LAST:event_jButtonPesquisarActionPerformed

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        doDefaultCloseAction();
}//GEN-LAST:event_jButtonSairActionPerformed

    private void jButtonComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComprarActionPerformed
        jTextFieldQtdcompra.setEditable(true);
        jTextFieldValorcompra.setEditable(true);
        jTextFieldQtdcompra.requestFocus();
        jTextFieldQtdcompra.selectAll();
        jComboBoxDataCompra.setEnabled(true);
        jButtonComprar.setEnabled(false);
        jButtonOkCompra.setEnabled(true);
        //new CalendarComboBox(true);
    }//GEN-LAST:event_jButtonComprarActionPerformed

    private void jButtonOkCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkCompraActionPerformed
        String descricao, unidade, fornecedor, dataCompra, data;
        int  codigo, qtdCompra, qtdestoque, qtdmin = 0;
        double valor = 0;

        DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        codigo = Integer.valueOf(jTextFieldCodigo.getText());
        descricao = jTextFieldDescricao.getText();
        unidade = jTextFieldUnidade.getText();
        qtdCompra = Integer.valueOf(jTextFieldQtdcompra.getText());
        fornecedor = (String) jComboBoxFornecedor.getSelectedItem();
        qtdestoque = Integer.valueOf(jTextFieldQtdEstoque.getText());
        qtdmin = Integer.valueOf(jTextFieldQtdMin.getText());
        data = jTextFieldData.getText();

        Date date = null;

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro data: "+ ex);
            Logger.getLogger(CadMateriais1.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(jComboBoxDataCompra.getSelectedItem().toString().equals("")){
           jComboBoxDataCompra.setSelectedItem(sdf.format(new Date()));
            System.out.println("IF NULL data Compra");
        }

        dataCompra = jComboBoxDataCompra.getSelectedItem().toString();
        try {
            valor = dff.parse(jTextFieldValor.getText().toUpperCase()).doubleValue();
            //cidade = jTextFieldDataCompra.getText().toUpperCase();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro conversão de valor: "+ ex);
            Logger.getLogger(CadMateriais1.class.getName()).log(Level.SEVERE, null, ex);
        }
        //cidade = jTextFieldDataCompra.getText().toUpperCase();

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();

        jTextFieldQtdEstoque.setText(String.valueOf((qtdestoque+qtdCompra)));

        LN.entity.HistoricoEntrada historicoEntrada = new LN.entity.HistoricoEntrada(getNextvalHistorico(), codigo, descricao, qtdCompra, valor, new Date(getDataTelaToBD(dataCompra)), "E", 0.0, Inicial.USER);
        Materiais materiais = new Materiais(String.valueOf(codigo), descricao,unidade, (qtdestoque+qtdCompra), qtdmin, valor, fornecedor, date);
        
        sessao.save(historicoEntrada);
        sessao.merge(materiais);
        
        //HistoricoMaterial(intt codhistorico, int codmateriais, String descricao, int quantidade, Double valor, Date dataentrada) {
        
        sessao.flush();
        transacao.commit();
        sessao.close();

        jButtonComprar.setEnabled(true);
        jButtonOkCompra.setEnabled(false);
        jComboBoxDataCompra.setEnabled(true);

        JOptionPane.showMessageDialog(null, "Material comprado com sucesso");
    }//GEN-LAST:event_jButtonOkCompraActionPerformed

    private void jTextFieldQtdcompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQtdcompraKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
        jTextFieldValorcompra.requestFocus();
        jTextFieldValorcompra.selectAll();
    }
    }//GEN-LAST:event_jTextFieldQtdcompraKeyPressed

    private void jComboBoxDataCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDataCompraActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jComboBoxDataCompraActionPerformed

    private void jComboBoxDataCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxDataCompraKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            System.out.println("ENTER");
            //jButtonVisual.requestFocus();
        }
}//GEN-LAST:event_jComboBoxDataCompraKeyPressed

    private void jButtonHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHistoricoActionPerformed
//        historicoEntradaSaida = null;
//
//    try {
//
//            historicoEntradaSaida = HistoricoEntradaSaida.getInstance(instancia);
//            if (!historicoEntradaSaida.isVisible()) {
//                historicoEntradaSaida.setVisible(true);
//                cadprodutos.getParent().add(historicoEntradaSaida, javax.swing.JLayeredPane.POPUP_LAYER);
//                historicoEntradaSaida.show();
//                historicoEntradaSaida.toFront();
//                historicoEntradaSaida.setSelected(true);
//                //telaVendas.setLocation(20, 10);
//
//            }
//            historicoEntradaSaida.setSelected(true);
//
//        } catch (PropertyVetoException ex) {
//            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(null, "Erro ao abrir tela Historico de Entrada e Saída: "+ ex);
//        }
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonComprar;
    private javax.swing.JButton jButtonDeletar;
    private javax.swing.JButton jButtonHistorico;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonOkCompra;
    private javax.swing.JButton jButtonPesquisar;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JComboBox jComboBoxDataCompra;
    private javax.swing.JComboBox jComboBoxFornecedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelRetorno;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelCliente;
    private javax.swing.JPanel jPanelCompras;
    private javax.swing.JTextField jTextFieldCodigo;
    private javax.swing.JTextField jTextFieldData;
    private javax.swing.JTextField jTextFieldDescricao;
    private javax.swing.JTextField jTextFieldPesqCodigo;
    private javax.swing.JTextField jTextFieldPesqDescricao;
    private javax.swing.JTextField jTextFieldQtdEstoque;
    private javax.swing.JTextField jTextFieldQtdMin;
    private javax.swing.JTextField jTextFieldQtdcompra;
    private javax.swing.JTextField jTextFieldUnidade;
    private javax.swing.JTextField jTextFieldValor;
    private javax.swing.JTextField jTextFieldValorcompra;
    // End of variables declaration//GEN-END:variables

}
