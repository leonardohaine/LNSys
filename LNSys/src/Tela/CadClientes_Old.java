/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadClientes.java
 *
 * Created on 07/05/2011, 17:30:00
 */

package Tela;

import LN.entity.Clientes;
import LN.entity.Orcamentos;
import Util.HibernateUtil;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class CadClientes_Old extends javax.swing.JInternalFrame {

    private static CadClientes_Old cadclientes;
    private Inicial inicial = null;
    private SessionFactory fabricaDeSessoes;
    private int codigoCliente;

    /** Creates new form CadClientes */
    public CadClientes_Old(Inicial inicial) {
        initComponents();

        setFrameIcon(new ImageIcon(this.getClass().getClassLoader().getResource("imagens/IconeLN.png")));

        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        String dataBusca = sfd.format(new Date());

        Campos(false);
        LimpaCampos();

        jButtonSalvar.setEnabled(false);
        jButtonAlterar.setEnabled(false);
        jButtonDeletar.setEnabled(false);
        jTextFieldPesqRazao.setEditable(false);
        jTextFieldPesqNome.setEditable(false);
        jTextFieldPesqCodigo.setEditable(false);

        jPanelPesquisa.setVisible(false);

    Dimension centraliza = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((centraliza.width-this.getSize().width)/2,
                      (centraliza.height-this.getSize().height)/2-30);

        //jTextFieldNOrcamentoBusca.requestFocus();
        mostraTabelaVazia();
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableClientes.getModel();
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();

        Query consultaClientes = sessao.createQuery(" from Clientes c order by c.codcliente ");
        List clienteList = consultaClientes.list();
        System.out.println("foram encontradas "+clienteList.size()+" clientes");
        Iterator i = clienteList.iterator();

        while (i.hasNext()) {
            Clientes clientes = (Clientes) i.next();

            tableResultado.addRow(new Object[] {clientes.getCodcliente(), clientes.getRazaosocial()});
        }

        transacao.commit();
        sessao.close();

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
        jTableClientes.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        jTableClientes.getColumnModel().getColumn(1).setCellRenderer(esquerda);
        System.out.println("Ajustando tamanho das colunas");
        jTableClientes.getColumnModel().getColumn(0).setPreferredWidth(10);//0TEM
        jTableClientes.getColumnModel().getColumn(0).setMinWidth(10);
        jTableClientes.getColumnModel().getColumn(1).setPreferredWidth(100);//85 QUANT
        jTableClientes.getColumnModel().getColumn(1).setMinWidth(100);


    }


    private void mostraTabelaVazia(){
           //montaTabela();

            jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][][] {
                    {null, null},
                },
                new String [] {
                    "Código","Razão Social"
                }
            ){
            public boolean isCellEditable(int row, int col) {

                     return false;

            }
            });
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableClientes.getModel();
        tableResultado.setRowCount(0);
        montaTabela();
//        tableResultado.getRowCount();
//        jTableMaterial.setBackground(((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));
//
//        System.out.println("COR - >"+((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));

        //Color teste = ((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.BLUE);
        //jTableMaterial.setBackground(Color.WHITE);
    }

    public static CadClientes_Old getInstance(Inicial instancia) throws PropertyVetoException, SQLException, ClassNotFoundException {
        if(cadclientes == null)
            cadclientes = new CadClientes_Old(instancia);
        return cadclientes;
    }

    public void Campos( boolean x){
        Component[] c = jPanelCliente.getComponents();
        for(int j = 0; j < c.length; j++) {
           if(c[j] instanceof JTextComponent) {
              JTextComponent cmp = (JTextComponent)c[j];
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
              DefaultTableModel  dtm =(DefaultTableModel)jTableHistoricoOrcamento.getModel();
              dtm.setRowCount(0);
              jTextFieldDataCadastro.setText("");
              jTextFieldRazaoSocial.requestFocus();
              jLabelRetorno.setText("");
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
        System.out.println("D  " + data );
        return data;
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
        jTextFieldRazaoSocial = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldFantasia = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldEndereco = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldBairro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldCidade = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextFieldCep = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jFormattedTextFieldTelefone = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jFormattedTextFieldCelular = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jFormattedTextFieldDataNasc = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldRG = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jFormattedTextFieldCPF = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldCNPJ = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldInscricaoEstadual = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldNomeContato = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldObservacao = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldDataCadastro = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldCodigo = new javax.swing.JTextField();
        jButtonNovo = new javax.swing.JButton();
        jButtonSalvar = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonDeletar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jPanelPesquisa = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldPesqRazao = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldPesqNome = new javax.swing.JTextField();
        jButtonPesquisar = new javax.swing.JButton();
        jTextFieldPesqCodigo = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableHistoricoOrcamento = new javax.swing.JTable();
        jLabelRetorno = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableClientes = new javax.swing.JTable();

        setIconifiable(true);
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

        jPanelCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro de clientes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("Razão social:");

        jLabel2.setText("Fantasia:");

        jLabel3.setText("Endereço:");

        jLabel4.setText("Bairro:");

        jLabel5.setText("Cidade:");

        jLabel6.setText("CEP:");

        try {
            jFormattedTextFieldCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel7.setText("Telefone:");

        try {
            jFormattedTextFieldTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel8.setText("Celular:");

        try {
            jFormattedTextFieldCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel9.setText("Data Nascimento:");

        try {
            jFormattedTextFieldDataNasc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldDataNasc.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel10.setText("RG:");

        jLabel11.setText("CPF:");

        try {
            jFormattedTextFieldCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#########-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel12.setText("CNPJ:");

        jLabel13.setText("Inscrição estadual:");

        jLabel14.setText("Nome/Contato:");

        jLabel15.setText("Observação:");

        jLabel18.setText("Data cadastro:");

        jTextFieldDataCadastro.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel19.setText("Código:");

        javax.swing.GroupLayout jPanelClienteLayout = new javax.swing.GroupLayout(jPanelCliente);
        jPanelCliente.setLayout(jPanelClienteLayout);
        jPanelClienteLayout.setHorizontalGroup(
            jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelClienteLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
                    .addGroup(jPanelClienteLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCidade, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldCep, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelClienteLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldDataNasc, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                    .addGroup(jPanelClienteLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldObservacao, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE))
                    .addGroup(jPanelClienteLayout.createSequentialGroup()
                        .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelClienteLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldInscricaoEstadual))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelClienteLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldRG, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelClienteLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDataCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                            .addComponent(jTextFieldCNPJ, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelClienteLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFantasia, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNomeContato, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelClienteLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(15, 15, 15)
                        .addComponent(jTextFieldRazaoSocial, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelClienteLayout.setVerticalGroup(
            jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClienteLayout.createSequentialGroup()
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNomeContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jFormattedTextFieldCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jFormattedTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextFieldDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jFormattedTextFieldCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldRG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jFormattedTextFieldCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldInscricaoEstadual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jTextFieldDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonNovo.setMnemonic('N');
        jButtonNovo.setText("Novo");
        jButtonNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoActionPerformed(evt);
            }
        });

        jButtonSalvar.setMnemonic('S');
        jButtonSalvar.setText("Salvar");
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });

        jButtonAlterar.setMnemonic('A');
        jButtonAlterar.setText("Alterar");
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

        jButtonDeletar.setMnemonic('D');
        jButtonDeletar.setText("Deletar");
        jButtonDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeletarActionPerformed(evt);
            }
        });

        jButtonSair.setMnemonic('R');
        jButtonSair.setText("Sair");
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });

        jPanelPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisa", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel16.setText("Razão social:");

        jTextFieldPesqRazao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesqRazaoKeyReleased(evt);
            }
        });

        jLabel17.setText("Nome/Contato:");

        jTextFieldPesqNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesqNomeKeyReleased(evt);
            }
        });

        jButtonPesquisar.setMnemonic('P');
        jButtonPesquisar.setText("Pesquisar");
        jButtonPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPesquisarActionPerformed(evt);
            }
        });

        jTextFieldPesqCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesqCodigoKeyReleased(evt);
            }
        });

        jLabel20.setText("Código:");

        javax.swing.GroupLayout jPanelPesquisaLayout = new javax.swing.GroupLayout(jPanelPesquisa);
        jPanelPesquisa.setLayout(jPanelPesquisaLayout);
        jPanelPesquisaLayout.setHorizontalGroup(
            jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPesqCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPesqRazao, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPesqNome, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelPesquisaLayout.setVerticalGroup(
            jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPesquisaLayout.createSequentialGroup()
                .addGroup(jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPesquisar)
                    .addComponent(jTextFieldPesqNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldPesqRazao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jTextFieldPesqCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTableHistoricoOrcamento.setBackground(new java.awt.Color(0, 121, 76));
        jTableHistoricoOrcamento.setFont(new java.awt.Font("Arial", 1, 12));
        jTableHistoricoOrcamento.setForeground(new java.awt.Color(255, 255, 255));
        jTableHistoricoOrcamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "N° Orçamento", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableHistoricoOrcamento.setFillsViewportHeight(true);
        jTableHistoricoOrcamento.setGridColor(new java.awt.Color(255, 255, 255));
        jTableHistoricoOrcamento.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTableHistoricoOrcamento.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTableHistoricoOrcamento);
        jTableHistoricoOrcamento.getColumnModel().getColumn(0).setResizable(false);
        jTableHistoricoOrcamento.getColumnModel().getColumn(1).setResizable(false);

        jLabelRetorno.setForeground(new java.awt.Color(255, 0, 0));
        jLabelRetorno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jTableClientes.setBackground(new java.awt.Color(0, 121, 76));
        jTableClientes.setFont(new java.awt.Font("Arial", 1, 12));
        jTableClientes.setForeground(new java.awt.Color(255, 255, 255));
        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Código", "Razão Social"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableClientes.setFillsViewportHeight(true);
        jTableClientes.setGridColor(new java.awt.Color(255, 255, 255));
        jTableClientes.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTableClientes.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTableClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableClientesMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableClientesMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTableClientes);
        jTableClientes.getColumnModel().getColumn(0).setResizable(false);
        jTableClientes.getColumnModel().getColumn(1).setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonSalvar)
                                        .addGap(40, 40, 40)
                                        .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addComponent(jButtonDeletar)
                                        .addGap(32, 32, 32)
                                        .addComponent(jButtonSair))
                                    .addComponent(jPanelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanelPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabelRetorno, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(208, 208, 208))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonSair)
                            .addComponent(jButtonNovo)
                            .addComponent(jButtonSalvar)
                            .addComponent(jButtonAlterar)
                            .addComponent(jButtonDeletar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabelRetorno, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoActionPerformed
        if(jButtonNovo.getText().equals("Cancelar")){
           jButtonNovo.setText("Novo");
           jButtonSalvar.setEnabled(false);
           Campos(false);
           LimpaCampos();
        }else{

           try{
               Campos(true);
               jButtonNovo.setText("Cancelar");
               jButtonAlterar.setEnabled(false);
               jButtonDeletar.setEnabled(false);
               jButtonSalvar.setEnabled(true);

                Session sessao = HibernateUtil.getSessionFactory().openSession();
                Transaction transacao = sessao.beginTransaction();

                SQLQuery query = sessao.createSQLQuery("select nextval('seq_clientes')");
                Object object = query.uniqueResult();
                System.out.println("NEXTVAL NOVO CLIENTE "+( (java.math.BigInteger)object ).longValue());
                codigoCliente = (int) ((java.math.BigInteger)object).longValue();
                LimpaCampos();

                jTextFieldCodigo.setText(((java.math.BigInteger)object ).toString());

                SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
                String data = sfd.format(new Date());

                jTextFieldDataCadastro.setText(data);

            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Erro: "+ ex+"\n Causa: "+ ex.getCause(), "Atenção - Novo", JOptionPane.INFORMATION_MESSAGE );
            }
        }
    }//GEN-LAST:event_jButtonNovoActionPerformed

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        doDefaultCloseAction();
    }//GEN-LAST:event_jButtonSairActionPerformed

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        String razaoSocial, fantasia, nomeContato, endereco, bairro, cidade, cep,
               telefone, celular, dataNascimento, rg, cpf, cnpj, inscricaoEstadual,
               dataCadastro, observacao;

        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        //fabricaDeSessoes = new Configuration().configure().buildSessionFactory();
        Session sessao;
        Transaction transacao;

      try{

            int codigo = Integer.valueOf(jTextFieldCodigo.getText());
            razaoSocial = jTextFieldRazaoSocial.getText();
            fantasia =jTextFieldFantasia.getText();
            nomeContato = jTextFieldNomeContato.getText();
            endereco = jTextFieldEndereco.getText();
            bairro = jTextFieldBairro.getText();
            cidade = jTextFieldCidade.getText();
            cep = jFormattedTextFieldCep.getText();
            telefone = jFormattedTextFieldTelefone.getText();
            celular = jFormattedTextFieldCelular.getText();
            celular = jFormattedTextFieldCelular.getText();
            dataNascimento = jFormattedTextFieldDataNasc.getText();
            System.out.println("DATA NASC: "+ dataNascimento+".");
            rg = jTextFieldRG.getText();
            cpf = jFormattedTextFieldCPF.getText();
            cnpj = jTextFieldCNPJ.getText();
            inscricaoEstadual = jTextFieldInscricaoEstadual.getText();
            observacao = jTextFieldObservacao.getText();
            dataCadastro = jTextFieldDataCadastro.getText();

            sessao = HibernateUtil.getSessionFactory().openSession();

            transacao = sessao.beginTransaction();

            Date newDataN = null;

            if(dataNascimento.equals("  /  /    ")){
               newDataN = null;
                System.out.println("DATA NUL");
            }else{
               newDataN = sfd.parse(getDataTelaToBD(dataNascimento));
                System.out.println("DATA CHEIA");
            }

            Clientes cliente = new Clientes (codigo, razaoSocial, fantasia, nomeContato, endereco, bairro, cidade, cep, telefone, celular, newDataN, rg, cpf, cnpj, inscricaoEstadual,observacao, new Date(getDataTelaToBD(dataCadastro)));


           sessao.save(cliente);
           transacao.commit();
           sessao.close();

        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Erro: "+ ex.toString()+"\n Causa: "+ ex.getCause(), "Atenção - Salvando", JOptionPane.INFORMATION_MESSAGE );
            
            return ;
        }
       LimpaCampos();
       Campos(false);
       
       if(jButtonNovo.getText().equals("Cancelar")){
           jButtonNovo.setText("Novo");
           jButtonSalvar.setEnabled(false);
           JOptionPane.showMessageDialog(null, "Cliente incluído com sucesso");
       }
    }//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jButtonDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeletarActionPerformed
      try{

           Session sessao = HibernateUtil.getSessionFactory().openSession();
           Transaction transacao = sessao.beginTransaction();

           if(jTextFieldCodigo.getText().equals("")){
               JOptionPane.showMessageDialog(null,"Pesquise algum cliente para deletar", "Atenção", JOptionPane.INFORMATION_MESSAGE );
               return;
           }else{
               Object[] options = { "Sim", "Não" };
              int a = JOptionPane.showOptionDialog(this, "Deseja realmente apagar este cliente?","Atenção",
                  JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE, null,
                  options, options[0]);
              if(a == JOptionPane.YES_OPTION){
                   Clientes newCliente = (Clientes) sessao.load(Clientes.class,Integer.parseInt(jTextFieldCodigo.getText()));
                   //System.out.println("DATA "+ new Date("dd/MM/yyyy"));
                   sessao.delete(newCliente);
                   sessao.flush();
                   transacao.commit();
                   sessao.close();

                   LimpaCampos();
                   JOptionPane.showMessageDialog(null, "Cliente"+jTextFieldCodigo.getText()+"deletado com sucesso");

                  System.out.println("Cliente "+jTextFieldCodigo.getText()+" deletado com sucesso!");
               }else{
                  System.out.println("clicou em NÂO");
                  return;
               }
           }

        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Erro: "+ ex+"\n Causa: "+ ex.getCause(), "Atenção - Deletando", JOptionPane.INFORMATION_MESSAGE );
        }
    }//GEN-LAST:event_jButtonDeletarActionPerformed

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        if(jButtonAlterar.getText().equals("Atualizar")){

            try{
                jButtonAlterar.setText("Alterar");
                Campos(false);
                System.out.println("ATUALIZANDO CLIENTE");
                SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");

                String razaoSocial, fantasia, nomeContato, endereco, bairro, cidade, cep,
                   telefone, celular, dataNascimento, rg, cpf, cnpj, inscricaoEstadual,
                   dataCadastro, observacao;

                int codigo = Integer.valueOf(jTextFieldCodigo.getText());
                razaoSocial = jTextFieldRazaoSocial.getText();
                fantasia =jTextFieldFantasia.getText();
                nomeContato = jTextFieldNomeContato.getText();
                endereco = jTextFieldEndereco.getText();
                bairro = jTextFieldBairro.getText();
                cidade = jTextFieldCidade.getText();
                cep = jFormattedTextFieldCep.getText();
                telefone = jFormattedTextFieldTelefone.getText();
                celular = jFormattedTextFieldCelular.getText();
                celular = jFormattedTextFieldCelular.getText();
                dataNascimento = jFormattedTextFieldDataNasc.getText();
                rg = jTextFieldRG.getText();
                cpf = jFormattedTextFieldCPF.getText();
                cnpj = jTextFieldCNPJ.getText();
                inscricaoEstadual = jTextFieldInscricaoEstadual.getText();
                observacao = jTextFieldObservacao.getText();
                dataCadastro = jTextFieldDataCadastro.getText();

                Session sessao = HibernateUtil.getSessionFactory().openSession();
                Transaction transacao = sessao.beginTransaction();

                Date newDataN = null;

                if(dataNascimento.equals("  /  /    ")){
                    newDataN = null;
                    System.out.println("DATA NUL");
                }else{
                   newDataN = sfd.parse(getDataTelaToBD(dataNascimento));
                    System.out.println("DATA CHEIA");
                }

                Clientes cliente = new Clientes (codigo, razaoSocial, fantasia, nomeContato, endereco, bairro, cidade, cep, telefone, celular, newDataN, rg, cpf, cnpj, inscricaoEstadual,observacao, new Date(getDataTelaToBD(dataCadastro)));

                jTableClientes.setValueAt(razaoSocial, jTableClientes.getSelectedRow(), 1);
                jTableClientes.updateUI();
                sessao.update(cliente);
                transacao.commit();
                sessao.close();

            }catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"Erro: "+ ex.toString()+"\n Causa: "+ ex.getCause(), "Atenção - Atualizando", JOptionPane.INFORMATION_MESSAGE );

                return ;
            }

            Campos(false);
            System.out.println("COMMIT UPDATE");
            JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
       }else{
            jButtonAlterar.setText("Atualizar");
            Campos(true);
            jTextFieldCodigo.setEditable(false);
       }

    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jTextFieldPesqRazaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesqRazaoKeyReleased
        try{


        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
            String data = sfd.format(new Date());

            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            Query consultaCli = sessao.createQuery("select o,  c from Orcamentos as o, Clientes as c where c.razaosocial like '"+jTextFieldPesqRazao.getText()+"%' and  o.clientes = c.codcliente order by o.codorcamentos");

            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listClientes = consultaCli.list();
            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator i = listClientes.iterator();

            Orcamentos orca = null;
            Clientes clientes = null;
            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
            tableResultado.setRowCount(0);
            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
            //System.out.println("MAT ->"+ matOrca.getProduto());
            if(jTextFieldPesqRazao.getText().equals("")){
                //Consulta = false;
                LimpaCampos();
                jTextFieldPesqRazao.requestFocus();
            }else{
                while (i.hasNext()) {

                     Object[] objeto = (Object[]) i.next();
                     orca = (Orcamentos) objeto[0];
                     clientes = (Clientes) objeto[1];

                    jTextFieldCodigo.setText(String.valueOf(clientes.getCodcliente()));
                    jTextFieldRazaoSocial.setText(clientes.getRazaosocial());
                    jTextFieldFantasia.setText(clientes.getFanatasia());
                    jTextFieldNomeContato.setText(clientes.getNomecontato());
                    jTextFieldEndereco.setText(clientes.getEndereco());
                    jTextFieldBairro.setText(clientes.getBairro());
                    jTextFieldCidade.setText(clientes.getCidade());
                    jFormattedTextFieldCep.setText(clientes.getCep());
                    jFormattedTextFieldTelefone.setText(clientes.getTelefone());
                    jFormattedTextFieldCelular.setText(clientes.getCelular());
                    String nasc = "";

                        if(clientes.getDatanascimento() == null){
                            jFormattedTextFieldDataNasc.setText("  /  /    ");
                        }else{
                            jFormattedTextFieldDataNasc.setText(sfd.format(clientes.getDatanascimento()));
                        }
                    jTextFieldRG.setText(clientes.getRg());
                    jFormattedTextFieldCPF.setText(clientes.getCpf());
                    jTextFieldCNPJ.setText(clientes.getCnpj());
                    jTextFieldInscricaoEstadual.setText(clientes.getInscricaoestadual());
                    jTextFieldObservacao.setText(clientes.getObservacao());
                    jTextFieldDataCadastro.setText(sfd.format(clientes.getDatacadastro()));

                    tableResultado.addRow(new Object[] {orca.getCodorcamentos(), sfd.format(orca.getDatacadastro())});

                }

                if(listClientes.isEmpty()){
                      System.out.println("FALSEEEEEE");
                      Query consultaOrcaNew = sessao.createQuery(" from Clientes as c where c.razaosocial like '"+jTextFieldPesqRazao.getText()+"%' order by c.codcliente");
                      List ListClientesNew = consultaOrcaNew.list();
                      Iterator iNew = ListClientesNew.iterator();


                    //Orcamentos orca = null;
                    Clientes  clientesNew = null;
                    javax.swing.table.DefaultTableModel tableResultadoNew =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
                    tableResultadoNew.setRowCount(0);
                    //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
                    //System.out.println("MAT ->"+ matOrca.getProduto());

                   while (iNew.hasNext()) {

                         //Object[] objeto = (Object[]) iNew.next();
                         //orcaNew = (Orcamentos) objeto[0];
                         //clientesNew = (Clientes) objeto[1];

                        clientesNew = (Clientes) iNew.next();

                        jTextFieldCodigo.setText(String.valueOf(clientesNew.getCodcliente()));
                        jTextFieldRazaoSocial.setText(clientesNew.getRazaosocial());
                        jTextFieldFantasia.setText(clientesNew.getFanatasia());
                        jTextFieldNomeContato.setText(clientesNew.getNomecontato());
                        jTextFieldEndereco.setText(clientesNew.getEndereco());
                        jTextFieldBairro.setText(clientesNew.getBairro());
                        jTextFieldCidade.setText(clientesNew.getCidade());
                        jFormattedTextFieldCep.setText(clientesNew.getCep());
                        jFormattedTextFieldTelefone.setText(clientesNew.getTelefone());
                        jFormattedTextFieldCelular.setText(clientesNew.getCelular());
                        jFormattedTextFieldCelular.setText(clientesNew.getCelular());
                        String nasc = "";

                        if(clientesNew.getDatanascimento() == null){
                            jFormattedTextFieldDataNasc.setText("  /  /    ");
                        }else{
                            jFormattedTextFieldDataNasc.setText(sfd.format(clientesNew.getDatanascimento()));
                        }
                        jTextFieldRG.setText(clientesNew.getRg());
                        jFormattedTextFieldCPF.setText(clientesNew.getCpf());
                        jTextFieldCNPJ.setText(clientesNew.getCnpj());
                        jTextFieldInscricaoEstadual.setText(clientesNew.getInscricaoestadual());
                        jTextFieldObservacao.setText(clientesNew.getObservacao());
                        jTextFieldDataCadastro.setText(sfd.format(clientesNew.getDatacadastro()));
                        jLabelRetorno.setText("");
                        //tableResultado.addRow(new Object[] {orca.getCodorcamentos(), orca.getDatacadastro()});

                   }

                if (ListClientesNew.isEmpty()) {
                    LimpaCampos();
                    jLabelRetorno.setText("Cliente não encontrado!!");
                    jTextFieldPesqCodigo.requestFocus();
                    return ;
                }

            }

            }

            }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Erro: "+ ex+"\n Causa: "+ ex.getCause(), "Atenção - Pesquisando", JOptionPane.INFORMATION_MESSAGE );
           }
    }//GEN-LAST:event_jTextFieldPesqRazaoKeyReleased

    private void jButtonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisarActionPerformed
        jTextFieldPesqRazao.setEditable(true);
        jTextFieldPesqNome.setEditable(true);
        jTextFieldPesqCodigo.setEditable(true);
        jButtonAlterar.setEnabled(true);
        jButtonDeletar.setEnabled(true);
        jButtonSalvar.setEnabled(false);
        //jButtonLimpar.setEnabled(true);
        //jComboBoxGrupo.setEditable(true);
        jTextFieldPesqCodigo.requestFocus();
        //Pesquisa();
        if(jButtonNovo.getText().equals("Cancelar")){
            jButtonNovo.setText("Novo");
            LimpaCampos();
            Campos(false);

            //jComboBoxGrupo.setEditable(true);
        }

        if(jButtonAlterar.getText().equals("Cancelar")){
            jButtonAlterar.setText("Alterar");
        }

        if(jButtonPesquisar.getText().equals("Cancelar")){
            jButtonPesquisar.setText("Pesquisar");
            jTextFieldPesqRazao.setEditable(false);
            jTextFieldPesqNome.setEditable(false);
            jTextFieldPesqCodigo.setEditable(false);
            jTextFieldPesqRazao.setText("");
            jTextFieldPesqNome.setText("");
            jTextFieldPesqCodigo.setText("");
            jButtonAlterar.setEnabled(false);
            jButtonDeletar.setEnabled(false);
            jButtonSalvar.setEnabled(false);
            //jButtonLimpar.setEnabled(false);
            jTextFieldPesqCodigo.requestFocus();
            LimpaCampos();
            //jComboBoxGrupo.setEditable(true);
        }else{
            jButtonPesquisar.setText("Cancelar");

        }
    }//GEN-LAST:event_jButtonPesquisarActionPerformed

    private void jTextFieldPesqNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesqNomeKeyReleased
            SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();
            
            Query consultaCli = sessao.createQuery("select o,  c from Orcamentos as o, Clientes as c where c.nomecontato like '"+jTextFieldPesqNome.getText()+"%' and  o.clientes = c.codcliente order by o.codorcamentos");

            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listClientes = consultaCli.list();
            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator i = listClientes.iterator();

            Orcamentos orca = null;
            Clientes clientes = null;
            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
            tableResultado.setRowCount(0);
            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
            //System.out.println("MAT ->"+ matOrca.getProduto());
            if(jTextFieldPesqNome.getText().equals("")){
                //Consulta = false;
                LimpaCampos();
                jTextFieldPesqNome.requestFocus();
            }else{
                while (i.hasNext()) {

                     Object[] objeto = (Object[]) i.next();
                     orca = (Orcamentos) objeto[0];
                     clientes = (Clientes) objeto[1];

                    jTextFieldCodigo.setText(String.valueOf(clientes.getCodcliente()));
                    jTextFieldRazaoSocial.setText(clientes.getRazaosocial());
                    jTextFieldFantasia.setText(clientes.getFanatasia());
                    jTextFieldNomeContato.setText(clientes.getNomecontato());
                    jTextFieldEndereco.setText(clientes.getEndereco());
                    jTextFieldBairro.setText(clientes.getBairro());
                    jTextFieldCidade.setText(clientes.getCidade());
                    jFormattedTextFieldCep.setText(clientes.getCep());
                    jFormattedTextFieldTelefone.setText(clientes.getTelefone());
                    jFormattedTextFieldCelular.setText(clientes.getCelular());
                    String nasc = "";

                        if(clientes.getDatanascimento() == null){
                            jFormattedTextFieldDataNasc.setText("  /  /    ");
                        }else{
                            jFormattedTextFieldDataNasc.setText(sfd.format(clientes.getDatanascimento()));
                        }
                    jTextFieldRG.setText(clientes.getRg());
                    jFormattedTextFieldCPF.setText(clientes.getCpf());
                    jTextFieldCNPJ.setText(clientes.getCnpj());
                    jTextFieldInscricaoEstadual.setText(clientes.getInscricaoestadual());
                    jTextFieldObservacao.setText(clientes.getObservacao());
                    jTextFieldDataCadastro.setText(sfd.format(clientes.getDatacadastro()));

                    tableResultado.addRow(new Object[] {orca.getCodorcamentos(), orca.getDatacadastro()});

                }

                if(listClientes.isEmpty()){
                      System.out.println("FALSEEEEEE");
                      Query consultaOrcaNew = sessao.createQuery(" from Clientes as c where c.nomecontato like '"+jTextFieldPesqNome.getText()+"%' order by c.codcliente");
                      List ListClientesNew = consultaOrcaNew.list();
                      Iterator iNew = ListClientesNew.iterator();


                    //Orcamentos orca = null;
                    Clientes  clientesNew = null;
                    javax.swing.table.DefaultTableModel tableResultadoNew =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
                    tableResultadoNew.setRowCount(0);
                    //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
                    //System.out.println("MAT ->"+ matOrca.getProduto());

                   while (iNew.hasNext()) {

                         //Object[] objeto = (Object[]) iNew.next();
                         //orcaNew = (Orcamentos) objeto[0];
                         //clientesNew = (Clientes) objeto[1];

                        clientesNew = (Clientes) iNew.next();

                        jTextFieldCodigo.setText(String.valueOf(clientesNew.getCodcliente()));
                        jTextFieldRazaoSocial.setText(clientesNew.getRazaosocial());
                        jTextFieldFantasia.setText(clientesNew.getFanatasia());
                        jTextFieldNomeContato.setText(clientesNew.getNomecontato());
                        jTextFieldEndereco.setText(clientesNew.getEndereco());
                        jTextFieldBairro.setText(clientesNew.getBairro());
                        jTextFieldCidade.setText(clientesNew.getCidade());
                        jFormattedTextFieldCep.setText(clientesNew.getCep());
                        jFormattedTextFieldTelefone.setText(clientesNew.getTelefone());
                        jFormattedTextFieldCelular.setText(clientesNew.getCelular());
                        String nasc = "";

                        if(clientesNew.getDatanascimento() == null){
                            jFormattedTextFieldDataNasc.setText("  /  /    ");
                        }else{
                            jFormattedTextFieldDataNasc.setText(sfd.format(clientesNew.getDatanascimento()));
                        }
                        jTextFieldRG.setText(clientesNew.getRg());
                        jFormattedTextFieldCPF.setText(clientesNew.getCpf());
                        jTextFieldCNPJ.setText(clientesNew.getCnpj());
                        jTextFieldInscricaoEstadual.setText(clientesNew.getInscricaoestadual());
                        jTextFieldObservacao.setText(clientesNew.getObservacao());
                        jTextFieldDataCadastro.setText(sfd.format(clientesNew.getDatacadastro()));
                        jLabelRetorno.setText("");
                        //tableResultado.addRow(new Object[] {orca.getCodorcamentos(), orca.getDatacadastro()});

                   }

                if (ListClientesNew.isEmpty()) {
                    LimpaCampos();
                    jLabelRetorno.setText("Cliente não encontrado!!");
                    jTextFieldPesqCodigo.requestFocus();
                    return ;
                }

            }


        }
    }//GEN-LAST:event_jTextFieldPesqNomeKeyReleased

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        LimpaCampos();
        jTextFieldCodigo.setText("");
        jTextFieldDataCadastro.setText("");
        System.out.println("Abrindo Tela Cadastro Clientes... - Opened");
    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        LimpaCampos();
        jTextFieldCodigo.setText("");
        jTextFieldDataCadastro.setText("");
        jButtonSalvar.setEnabled(false);
        jButtonAlterar.setEnabled(false);
        jButtonDeletar.setEnabled(false);
        System.out.println("Fechando Tela Cadastro Clientes... - Closed");
    }//GEN-LAST:event_formInternalFrameClosed

    private void jTextFieldPesqCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesqCodigoKeyReleased
            SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();

            

            if(jTextFieldPesqCodigo.getText().equals("")){
                //Consulta = false;
                LimpaCampos();
                jTextFieldPesqCodigo.requestFocus();
            }else{

            Query consultaCliente = sessao.createQuery("select o, c from Orcamentos as o, Clientes as c where c.codcliente = '"+Integer.valueOf(jTextFieldPesqCodigo.getText())+"' and  o.clientes = c.codcliente order by o.codorcamentos");
            System.out.println("Query: "+consultaCliente);
            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listClientes = consultaCliente.list();
            System.out.println("List: "+listClientes);
            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator i = listClientes.iterator();

            Orcamentos orca = null;
            Clientes clientes = null;
            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
            tableResultado.setRowCount(0);
            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
            //System.out.println("MAT ->"+ matOrca.getProduto());
            
                while (i.hasNext()) {

                     Object[] objeto = (Object[]) i.next();
                     orca = (Orcamentos) objeto[0];
                     clientes = (Clientes) objeto[1];


                    jTextFieldCodigo.setText(String.valueOf(clientes.getCodcliente()));
                    jTextFieldRazaoSocial.setText(clientes.getRazaosocial());
                    jTextFieldFantasia.setText(clientes.getFanatasia());
                    jTextFieldNomeContato.setText(clientes.getNomecontato());
                    jTextFieldEndereco.setText(clientes.getEndereco());
                    jTextFieldBairro.setText(clientes.getBairro());
                    jTextFieldCidade.setText(clientes.getCidade());
                    jFormattedTextFieldCep.setText(clientes.getCep());
                    jFormattedTextFieldTelefone.setText(clientes.getTelefone());
                    jFormattedTextFieldCelular.setText(clientes.getCelular());
                    jFormattedTextFieldCelular.setText(clientes.getCelular());
                    jFormattedTextFieldDataNasc.setText(sfd.format(clientes.getDatanascimento()));
                    jTextFieldRG.setText(clientes.getRg());
                    jFormattedTextFieldCPF.setText(clientes.getCpf());
                    jTextFieldCNPJ.setText(clientes.getCnpj());
                    jTextFieldInscricaoEstadual.setText(clientes.getInscricaoestadual());
                    jTextFieldObservacao.setText(clientes.getObservacao());
                    jTextFieldDataCadastro.setText(sfd.format(clientes.getDatacadastro()));

                    tableResultado.addRow(new Object[] {orca.getCodorcamentos(), orca.getDatacadastro()});
                     jLabelRetorno.setText("");
//                            if (listClientes.isEmpty()) {
//                                LimpaCampos();
//                                jLabelRetorno.setText("Cliente não encontrado!!");
//                                jTextFieldPesqCodigo.requestFocus();
//                                return ;
//                            }
                }

            

            if(listClientes.isEmpty()){
                      System.out.println("FALSEEEEEE");
                      Query consultaOrcaNew = sessao.createQuery(" from Clientes as c where c.codcliente = '"+Integer.valueOf(jTextFieldPesqCodigo.getText())+"' order by c.codcliente");
                      List ListClientesNew = consultaOrcaNew.list();
                      Iterator iNew = ListClientesNew.iterator();


            //Orcamentos orca = null;
            Clientes  clientesNew = null;
            javax.swing.table.DefaultTableModel tableResultadoNew =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
            tableResultadoNew.setRowCount(0);
            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
            //System.out.println("MAT ->"+ matOrca.getProduto());

                while (iNew.hasNext()) {

                     //Object[] objeto = (Object[]) iNew.next();
                     //orcaNew = (Orcamentos) objeto[0];
                     //clientesNew = (Clientes) objeto[1];

                    clientesNew = (Clientes) iNew.next();

                    jTextFieldCodigo.setText(String.valueOf(clientesNew.getCodcliente()));
                    jTextFieldRazaoSocial.setText(clientesNew.getRazaosocial());
                    jTextFieldFantasia.setText(clientesNew.getFanatasia());
                    jTextFieldNomeContato.setText(clientesNew.getNomecontato());
                    jTextFieldEndereco.setText(clientesNew.getEndereco());
                    jTextFieldBairro.setText(clientesNew.getBairro());
                    jTextFieldCidade.setText(clientesNew.getCidade());
                    jFormattedTextFieldCep.setText(clientesNew.getCep());
                    jFormattedTextFieldTelefone.setText(clientesNew.getTelefone());
                    jFormattedTextFieldCelular.setText(clientesNew.getCelular());
                    jFormattedTextFieldCelular.setText(clientesNew.getCelular());
                    String nasc = "";

                    if(clientesNew.getDatanascimento() == null){
                        jFormattedTextFieldDataNasc.setText("  /  /    ");
                    }else{
                        jFormattedTextFieldDataNasc.setText(sfd.format(clientesNew.getDatanascimento()));
                    }
                    jTextFieldRG.setText(clientesNew.getRg());
                    jFormattedTextFieldCPF.setText(clientesNew.getCpf());
                    jTextFieldCNPJ.setText(clientesNew.getCnpj());
                    jTextFieldInscricaoEstadual.setText(clientesNew.getInscricaoestadual());
                    jTextFieldObservacao.setText(clientesNew.getObservacao());
                    jTextFieldDataCadastro.setText(sfd.format(clientesNew.getDatacadastro()));
                    jLabelRetorno.setText("");
                    //tableResultado.addRow(new Object[] {orca.getCodorcamentos(), orca.getDatacadastro()});

                }

                if (ListClientesNew.isEmpty()) {
                    LimpaCampos();
                    jLabelRetorno.setText("Cliente não encontrado!!");
                    jTextFieldPesqCodigo.requestFocus();
                    return ;
                }

            }

        }

    }//GEN-LAST:event_jTextFieldPesqCodigoKeyReleased

    private void jTableClientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClientesMousePressed
    if (evt.getButton() == 1 || evt.getButton() == 2) {

        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();

            javax.swing.table.DefaultTableModel table =(javax.swing.table.DefaultTableModel)jTableClientes.getModel();

            int line = jTableClientes.getSelectedRow();

            int codigo = (Integer) table.getValueAt(line, 0);

            jButtonAlterar.setEnabled(true);
            jButtonDeletar.setEnabled(true);
            jButtonSalvar.setEnabled(false);
//            if(jTextFieldPesqCodigo.getText().equals("")){
//                //Consulta = false;
//                LimpaCampos();
//                jTextFieldPesqCodigo.requestFocus();
//            }else{

            Query consultaCliente = sessao.createQuery("select o, c from Orcamentos as o, Clientes as c where c.codcliente = '"+codigo+"' and  o.clientes = c.codcliente order by o.codorcamentos");
            System.out.println("Query: "+consultaCliente);
            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listClientes = consultaCliente.list();
            System.out.println("List: "+listClientes);
            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator i = listClientes.iterator();

            Orcamentos orca = null;
            Clientes clientes = null;
            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
            tableResultado.setRowCount(0);
            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
            //System.out.println("MAT ->"+ matOrca.getProduto());

                while (i.hasNext()) {

                     Object[] objeto = (Object[]) i.next();
                     orca = (Orcamentos) objeto[0];
                     clientes = (Clientes) objeto[1];


                    jTextFieldCodigo.setText(String.valueOf(clientes.getCodcliente()));
                    jTextFieldRazaoSocial.setText(clientes.getRazaosocial());
                    jTextFieldFantasia.setText(clientes.getFanatasia());
                    jTextFieldNomeContato.setText(clientes.getNomecontato());
                    jTextFieldEndereco.setText(clientes.getEndereco());
                    jTextFieldBairro.setText(clientes.getBairro());
                    jTextFieldCidade.setText(clientes.getCidade());
                    jFormattedTextFieldCep.setText(clientes.getCep());
                    jFormattedTextFieldTelefone.setText(clientes.getTelefone());
                    jFormattedTextFieldCelular.setText(clientes.getCelular());
                    jFormattedTextFieldCelular.setText(clientes.getCelular());
                    jFormattedTextFieldDataNasc.setText(sfd.format(clientes.getDatanascimento()));
                    jTextFieldRG.setText(clientes.getRg());
                    jFormattedTextFieldCPF.setText(clientes.getCpf());
                    jTextFieldCNPJ.setText(clientes.getCnpj());
                    jTextFieldInscricaoEstadual.setText(clientes.getInscricaoestadual());
                    jTextFieldObservacao.setText(clientes.getObservacao());
                    jTextFieldDataCadastro.setText(sfd.format(clientes.getDatacadastro()));

                    tableResultado.addRow(new Object[] {orca.getCodorcamentos(), orca.getDatacadastro()});
                     jLabelRetorno.setText("");
//                            if (listClientes.isEmpty()) {
//                                LimpaCampos();
//                                jLabelRetorno.setText("Cliente não encontrado!!");
//                                jTextFieldPesqCodigo.requestFocus();
//                                return ;
//                            }
                }



            if(listClientes.isEmpty()){
                      System.out.println("FALSEEEEEE");
                      Query consultaOrcaNew = sessao.createQuery(" from Clientes as c where c.codcliente = '"+codigo+"' order by c.codcliente");
                      List ListClientesNew = consultaOrcaNew.list();
                      Iterator iNew = ListClientesNew.iterator();


            //Orcamentos orca = null;
            Clientes  clientesNew = null;
            javax.swing.table.DefaultTableModel tableResultadoNew =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
            tableResultadoNew.setRowCount(0);
            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
            //System.out.println("MAT ->"+ matOrca.getProduto());

                while (iNew.hasNext()) {

                     //Object[] objeto = (Object[]) iNew.next();
                     //orcaNew = (Orcamentos) objeto[0];
                     //clientesNew = (Clientes) objeto[1];

                    clientesNew = (Clientes) iNew.next();

                    jTextFieldCodigo.setText(String.valueOf(clientesNew.getCodcliente()));
                    jTextFieldRazaoSocial.setText(clientesNew.getRazaosocial());
                    jTextFieldFantasia.setText(clientesNew.getFanatasia());
                    jTextFieldNomeContato.setText(clientesNew.getNomecontato());
                    jTextFieldEndereco.setText(clientesNew.getEndereco());
                    jTextFieldBairro.setText(clientesNew.getBairro());
                    jTextFieldCidade.setText(clientesNew.getCidade());
                    jFormattedTextFieldCep.setText(clientesNew.getCep());
                    jFormattedTextFieldTelefone.setText(clientesNew.getTelefone());
                    jFormattedTextFieldCelular.setText(clientesNew.getCelular());
                    jFormattedTextFieldCelular.setText(clientesNew.getCelular());
                    String nasc = "";

                    if(clientesNew.getDatanascimento() == null){
                        jFormattedTextFieldDataNasc.setText("  /  /    ");
                    }else{
                        jFormattedTextFieldDataNasc.setText(sfd.format(clientesNew.getDatanascimento()));
                    }
                    jTextFieldRG.setText(clientesNew.getRg());
                    jFormattedTextFieldCPF.setText(clientesNew.getCpf());
                    jTextFieldCNPJ.setText(clientesNew.getCnpj());
                    jTextFieldInscricaoEstadual.setText(clientesNew.getInscricaoestadual());
                    jTextFieldObservacao.setText(clientesNew.getObservacao());
                    jTextFieldDataCadastro.setText(sfd.format(clientesNew.getDatacadastro()));
                    jLabelRetorno.setText("");
                    //tableResultado.addRow(new Object[] {orca.getCodorcamentos(), orca.getDatacadastro()});

                }

                if (ListClientesNew.isEmpty()) {
                    LimpaCampos();
                    jLabelRetorno.setText("Cliente não encontrado!!");
                    jTextFieldPesqCodigo.requestFocus();
                    return ;
                }

            }

        //}
    }
    }//GEN-LAST:event_jTableClientesMousePressed

    private void jTableClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClientesMouseClicked
    if (evt.getClickCount() == 1 || evt.getClickCount() == 2) {


            SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();

            javax.swing.table.DefaultTableModel table =(javax.swing.table.DefaultTableModel)jTableClientes.getModel();

            int line = jTableClientes.getSelectedRow();

//            if(jTableClientes.getSelectedRowCount() <= 0){
//                jButtonAlterar.setEnabled(false);
//                jButtonDeletar.setEnabled(false);
//                System.out.println("FALSE");
//            }else{
                
           // }

            int codigo = (Integer) table.getValueAt(line, 0);
            System.out.println("Codigo "+ codigo);

            ////if(jTextFieldPesqCodigo.getText().equals("")){
                //Consulta = false;
                //LimpaCampos();
                //jTextFieldPesqCodigo.requestFocus();
            //}else{

            Query consultaCliente = sessao.createQuery("select o, c from Orcamentos as o, Clientes as c where c.codcliente = '"+codigo+"' and  o.clientes = c.codcliente order by o.codorcamentos");
            System.out.println("Query: "+consultaCliente);
            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List listClientes = consultaCliente.list();
            System.out.println("List: "+listClientes);
            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator i = listClientes.iterator();

            Orcamentos orca = null;
            Clientes clientes = null;
            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
            tableResultado.setRowCount(0);
            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
            //System.out.println("MAT ->"+ matOrca.getProduto());

                while (i.hasNext()) {

                     Object[] objeto = (Object[]) i.next();
                     orca = (Orcamentos) objeto[0];
                     clientes = (Clientes) objeto[1];


                    jTextFieldCodigo.setText(String.valueOf(clientes.getCodcliente()));
                    jTextFieldRazaoSocial.setText(clientes.getRazaosocial());
                    jTextFieldFantasia.setText(clientes.getFanatasia());
                    jTextFieldNomeContato.setText(clientes.getNomecontato());
                    jTextFieldEndereco.setText(clientes.getEndereco());
                    jTextFieldBairro.setText(clientes.getBairro());
                    jTextFieldCidade.setText(clientes.getCidade());
                    jFormattedTextFieldCep.setText(clientes.getCep());
                    jFormattedTextFieldTelefone.setText(clientes.getTelefone());
                    jFormattedTextFieldCelular.setText(clientes.getCelular());
                    jFormattedTextFieldDataNasc.setText(sfd.format(clientes.getDatanascimento()));
                    jTextFieldRG.setText(clientes.getRg());
                    jFormattedTextFieldCPF.setText(clientes.getCpf());
                    jTextFieldCNPJ.setText(clientes.getCnpj());
                    jTextFieldInscricaoEstadual.setText(clientes.getInscricaoestadual());
                    jTextFieldObservacao.setText(clientes.getObservacao());
                    jTextFieldDataCadastro.setText(sfd.format(clientes.getDatacadastro()));

                    tableResultado.addRow(new Object[] {orca.getCodorcamentos(), sfd.format(orca.getDatacadastro())});
                    jLabelRetorno.setText("");
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



                if(listClientes.isEmpty()){
                  //System.out.println("FALSEEEEEE");
                  Query consultaOrcaNew = sessao.createQuery(" from Clientes as c where c.codcliente = '"+codigo+"' order by c.codcliente");
                  List ListClientesNew = consultaOrcaNew.list();
                  Iterator iNew = ListClientesNew.iterator();


                //Orcamentos orca = null;
                    Clientes  clientesNew = null;
                    javax.swing.table.DefaultTableModel tableResultadoNew =(javax.swing.table.DefaultTableModel)jTableHistoricoOrcamento.getModel();
                    tableResultadoNew.setRowCount(0);
                    //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
                    //System.out.println("MAT ->"+ matOrca.getProduto());

                    while (iNew.hasNext()) {

                         //Object[] objeto = (Object[]) iNew.next();
                         //orcaNew = (Orcamentos) objeto[0];
                         //clientesNew = (Clientes) objeto[1];

                        clientesNew = (Clientes) iNew.next();

                        jTextFieldCodigo.setText(String.valueOf(clientesNew.getCodcliente()));
                        jTextFieldRazaoSocial.setText(clientesNew.getRazaosocial());
                        jTextFieldFantasia.setText(clientesNew.getFanatasia());
                        jTextFieldNomeContato.setText(clientesNew.getNomecontato());
                        jTextFieldEndereco.setText(clientesNew.getEndereco());
                        jTextFieldBairro.setText(clientesNew.getBairro());
                        jTextFieldCidade.setText(clientesNew.getCidade());
                        jFormattedTextFieldCep.setText(clientesNew.getCep());
                        jFormattedTextFieldTelefone.setText(clientesNew.getTelefone());
                        jFormattedTextFieldCelular.setText(clientesNew.getCelular());

                        if(clientesNew.getDatanascimento() == null){
                            jFormattedTextFieldDataNasc.setText("  /  /    ");
                        }else{
                            jFormattedTextFieldDataNasc.setText(sfd.format(clientesNew.getDatanascimento()));
                        }
                        jTextFieldRG.setText(clientesNew.getRg());
                        jFormattedTextFieldCPF.setText(clientesNew.getCpf());
                        jTextFieldCNPJ.setText(clientesNew.getCnpj());
                        jTextFieldInscricaoEstadual.setText(clientesNew.getInscricaoestadual());
                        jTextFieldObservacao.setText(clientesNew.getObservacao());
                        jTextFieldDataCadastro.setText(sfd.format(clientesNew.getDatacadastro()));
                        jLabelRetorno.setText("");
                        //tableResultado.addRow(new Object[] {orca.getCodorcamentos(), orca.getDatacadastro()});

                    }

                    if (ListClientesNew.isEmpty()) {
                        //LimpaCampos();
                        jLabelRetorno.setText("Cliente não encontrado!!");
                        jTextFieldPesqCodigo.requestFocus();
                        return ;
                    }

                }

        //}
        }
    }//GEN-LAST:event_jTableClientesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonDeletar;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonPesquisar;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JFormattedTextField jFormattedTextFieldCPF;
    private javax.swing.JFormattedTextField jFormattedTextFieldCelular;
    private javax.swing.JFormattedTextField jFormattedTextFieldCep;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataNasc;
    private javax.swing.JFormattedTextField jFormattedTextFieldTelefone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelRetorno;
    private javax.swing.JPanel jPanelCliente;
    private javax.swing.JPanel jPanelPesquisa;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableClientes;
    private javax.swing.JTable jTableHistoricoOrcamento;
    private javax.swing.JTextField jTextFieldBairro;
    private javax.swing.JTextField jTextFieldCNPJ;
    private javax.swing.JTextField jTextFieldCidade;
    private javax.swing.JTextField jTextFieldCodigo;
    private javax.swing.JTextField jTextFieldDataCadastro;
    private javax.swing.JTextField jTextFieldEndereco;
    private javax.swing.JTextField jTextFieldFantasia;
    private javax.swing.JTextField jTextFieldInscricaoEstadual;
    private javax.swing.JTextField jTextFieldNomeContato;
    private javax.swing.JTextField jTextFieldObservacao;
    private javax.swing.JTextField jTextFieldPesqCodigo;
    private javax.swing.JTextField jTextFieldPesqNome;
    private javax.swing.JTextField jTextFieldPesqRazao;
    private javax.swing.JTextField jTextFieldRG;
    private javax.swing.JTextField jTextFieldRazaoSocial;
    // End of variables declaration//GEN-END:variables

}
