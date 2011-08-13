/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TelaOrcamentos.java
 *
 * Created on 18/12/2010, 22:59:59
 */

package Tela;

import LN.entity.Clientes;
import LN.entity.MateriaisOrcamentos;
import LN.entity.Orcamentos;
import Util.CalendarComboBox;
import Util.Extenso;
import Util.HibernateUtil;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfFont;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
//import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.String;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
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
import org.hibernate.cfg.Configuration;


/**
 *
 * @author Leo
 */
public class TelaOrcamentos extends javax.swing.JInternalFrame {

    private static TelaOrcamentos orcamentos;
    private Inicial inicial = null;
    private SessionFactory fabricaDeSessoes;
    Vector vetorNOrcamento = new Vector();
    Vector vetorBuscaNOrcamento = new Vector();
    double totalGeral = 0;
    
    //private double quantidade;
    //private double valorUnitario;

    public void Campos( boolean x){
        Component[] c = jPanelDados.getComponents();

        for(int j = 0; j < c.length; j++) {
           if(c[j] instanceof JTextComponent) {
              JTextComponent cmp = (JTextComponent)c[j];
              cmp.setEditable(x);
              jTextAreaDescricao.setEditable(x);
              jTableMaterial.setEnabled(x);
              jRadioButtonAG.setEnabled(x);
              jRadioButtonAP.setEnabled(x);
              jRadioButtonM.setEnabled(x);
              jComboBoxClientes.setEnabled(x);
              jTextFieldNumOrcamento.setEditable(false);
              jButtonMaisLinha.setEnabled(x);
              jButtonMenosLinha.setEnabled(x);
          }
        }
    }

    public void LimpaCampos(){
        Component[] c = jPanelDados.getComponents();
        for(int j = 0; j < c.length; j++) {
           if(c[j] instanceof JTextComponent) {
              JTextComponent cmp = (JTextComponent)c[j];
              cmp.setText("");
              DefaultTableModel  dtm =(DefaultTableModel)jTableMaterial.getModel();
              dtm.setRowCount(0);
              jTextAreaDescricao.setText("");
              jTextFieldNumOrcamento.setText("");
              jTextFieldData.setText("");
              jLabelTotalGeral.setText("");
              jRadioButtonAP.setSelected(false);
              jRadioButtonAG.setSelected(false);
              jRadioButtonM.setSelected(false);
              jComboBoxClientes.setSelectedIndex(0);
              jTextFieldCliente.requestFocus();
          }
        }
    }

    public int getNextvalMateriaisOrcamentos(){

       Session sessao = HibernateUtil.getSessionFactory().openSession();
       Transaction transacao = sessao.beginTransaction();

           SQLQuery query = sessao.createSQLQuery("select nextval('seq_materiaisorcamentos')");
           Object object = query.uniqueResult();
           int nextVal = ( (java.math.BigInteger)object ).intValue();
           System.out.println("NEXTVAL MATERIAIS "+nextVal);

       return nextVal;
    }


    /** Creates new form TelaOrcamentos */
    public TelaOrcamentos(Inicial instancia) {
        initComponents();
        //jTableMaterial.setEnabled(false);
       
        jTextFieldCliente.setVisible(false);
        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        String dataBusca = sfd.format(new Date());

        jComboBoxDataFinal.addItem(dataBusca);

        Campos(false);
        LimpaCampos();

        jButtonSalvar.setEnabled(false);
        jButtonAlterar.setEnabled(false);
        jButtonDeletar.setEnabled(false);
        jButtonVisualizar.setEnabled(false);

        File config = new File("d:\\hibernate.cfg.xml");
        Transaction transacao = null;
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        try{
            transacao = sessao.beginTransaction();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Erro: "+ ex+"\n Causa: "+ ex.getCause(), "Atenção", JOptionPane.INFORMATION_MESSAGE );
        }
        System.out.println("URL 1-> "+new Configuration().getProperty("hibernate.connection.url"));
        new Configuration().setProperty("hibernate.connection.url", "testeURL");
        System.out.println("URL 2-> "+new Configuration().getProperty("hibernate.connection.url"));
        
       // int nextVal = (Integer) sessao.createQuery("select nextval(text) ('seq_materiais')").uniqueResult();      
             //   System.out.println("NEXTVAL = "+ nextVal);
//        FUNCIONANDO
//        SQLQuery query = sessao.createSQLQuery("select nextval('seq_materiais')");
//        Object object = query.uniqueResult();
//        System.out.println("NEXTVAL "+( (java.math.BigInteger)object ).longValue());

        SimpleDateFormat sfd2 = new SimpleDateFormat("dd/MM/yyyy");
        String data = sfd2.format(new Date());
        
        Query consultaOrcamentos = sessao.createQuery(" from Orcamentos o where o.datacadastro between '"+data+"' and '"+data+"' order by o.codorcamentos ");
        List orcamentosList = consultaOrcamentos.list();
        System.out.println("foram encontradas "+orcamentosList.size()+" orcamentos");
        Iterator i = orcamentosList.iterator();
        
        while (i.hasNext()) {
           Orcamentos orcaList = (Orcamentos) i.next();
           System.out.println(orcaList.getCodorcamentos());
            //System.out.println("NEXT"+ orca.getMateriaisOrcamentoses());
           vetorNOrcamento.add(orcaList.getCodorcamentos());
           jListOrcamento.setListData(vetorNOrcamento);
           jListOrcamento.updateUI();
        }
        //transacao.commit();
        //sessao.close();


        //UsuarioDAO usuarioDAO = new UsuarioDAO();
        //usuario = usuarioDAO.getUsuario();
        //jListOrcamento.setListData(usuarioDAO.getUsuario());
        jListOrcamento.updateUI();


         mostraTabelaVazia();
       // jTableMaterial.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //ImageIcon icone = new ImageIcon(this.getClass().getClassLoader().getResource("imagens/iconeLN.png"));
        //setIconImage(icone.getImage());
        System.out.println("XML "+ this.getClass().getClassLoader().getResource("hibernate.cfg.xml"));
        setFrameIcon(new ImageIcon(this.getClass().getClassLoader().getResource("imagens/IconeLN.png")));

        Dimension centraliza = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((centraliza.width-this.getSize().width)/2,
                      (centraliza.height-this.getSize().height)/2-30);

        jTextFieldNOrcamentoBusca.requestFocus();


//        fabricaDeSessoes = new Configuration().configure().buildSessionFactory();
//        Session sessaoCli = fabricaDeSessoes.openSession();
//        Transaction transacaoCli = sessaoCli.beginTransaction();

        Query consultaClientes = sessao.createQuery(" from Clientes c order by c.razaosocial, c.codcliente  ");
        List clientesList = consultaClientes.list();
        System.out.println("foram encontradas "+clientesList.size()+" clientes");
        Iterator c =clientesList.iterator();

            while (c.hasNext()) {
               Clientes cliList = (Clientes) c.next();
               System.out.println(cliList.getRazaosocial());
                //System.out.println("NEXT"+cliList.getCodclientes());

               jComboBoxClientes.addItem(cliList.getCodcliente()+" - "+ cliList.getRazaosocial());
               jComboBoxClientes.updateUI();
            }
        transacao.commit();
        sessao.close();

    }

    public static TelaOrcamentos getInstance(Inicial instancia) throws PropertyVetoException, SQLException, ClassNotFoundException {
        if(orcamentos == null)
            orcamentos = new TelaOrcamentos(instancia);
        return orcamentos;
    }

    
    private TelaOrcamentos() {

    }

     public static String moeda(double vlr){
            java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
            return df.format( vlr );
     }

     public void calculaTable(){
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
        int linhas = tableResultado.getRowCount();
        double item, quantidade = 0;
        double valorUnitario = 0;
        double valorTotal = 0;
        double valorTotalGeral = 0;
        int x = 0;

//        if((tableResultado.getValueAt(x, 2)) == null || (tableResultado.getValueAt(x, 5)) == null){
//           tableResultado.setValueAt("0",x, 2);
//           tableResultado.setValueAt("0",x, 5);
//           tableResultado.setValueAt("0",x, 6);
//            System.out.println("SETOU");
//        }

        DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();

        for(;x < linhas; x++){
           if(tableResultado.getRowCount() > 0){
               
               try{
                   quantidade = Double.valueOf(tableResultado.getValueAt(x, 2).toString()) ;//.replace(",", "."));
                        //System.out.println("I4 "+i);
                   valorUnitario = dff.parse((String)tableResultado.getValueAt(x, 5)).doubleValue();
                   System.out.println("Quantidade: "+ quantidade+"\nValor Unitario: "+ valorUnitario);

                   if (jTableMaterial.getSelectedColumn()== 0 || jTableMaterial.getSelectedColumn()== 1|| jTableMaterial.getSelectedColumn()== 2 || jTableMaterial.getSelectedColumn()== 3 || jTableMaterial.getSelectedColumn()== 4) {
                       valorTotal = quantidade*valorUnitario;
                       tableResultado.setValueAt(moeda(valorTotal), x, 6);

                   }

                   valorTotalGeral += dff.parse((String)tableResultado.getValueAt(x, 6)).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(TelaOrcamentos.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null,"Erro: "+ ex+"\n Causa: "+ ex.getCause(), "Atenção - Calculando valores", JOptionPane.INFORMATION_MESSAGE );
                }
                    System.out.println("TOTAL: "+ moeda(valorTotalGeral));
                    String desc = "TOTAL GERAL: R$ ";
                    totalGeral = valorTotalGeral;
                    //jLabelTotalGeral.setText("TOTAL GERAL: R$ "+String.valueOf(moeda(valorTotalGeral)));
                    jLabelTotalGeral.setText(desc+moeda(totalGeral));
           }
        }
     }

    public void montaTabela(){

        System.out.println("Montando Tabela");

        //jTableMaterial.setBackground(((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));

        //System.out.println("COR - >"+((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));

        jTableMaterial.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
//        int row = jTableMaterial.getRowCount();
//        jTableMaterial.setBackground(((row%2==0) ? Color.gray : Color.yellow));
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        jTableMaterial.getColumnModel().getColumn(0).setCellRenderer(direita);
        jTableMaterial.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        jTableMaterial.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        jTableMaterial.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        jTableMaterial.getColumnModel().getColumn(4).setCellRenderer(esquerda);
        jTableMaterial.getColumnModel().getColumn(5).setCellRenderer(direita);
        jTableMaterial.getColumnModel().getColumn(6).setCellRenderer(direita);
        //jTableMaterial.getColumnModel().getColumn(5).setCellRenderer(direita);
        //jTableMaterial.getColumnModel().getColumn(6).setCellRenderer(direita);
        //jTableMaterial.getColumnModel().getColumn(7).setCellRenderer(esquerda);
        //jTableMaterial.getColumnModel().getColumn(8).setCellRenderer(centralizado);
        //jTableMaterial.getColumnModel().getColumn(9).setCellRenderer(centralizado);
        System.out.println("Ajustando tamanho das colunas");
        jTableMaterial.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableMaterial.getColumnModel().getColumn(0).setMinWidth(0);
        jTableMaterial.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableMaterial.getColumnModel().getColumn(1).setPreferredWidth(30);//0TEM
        jTableMaterial.getColumnModel().getColumn(1).setMinWidth(30);
        jTableMaterial.getColumnModel().getColumn(2).setPreferredWidth(65);//85 QUANT
        jTableMaterial.getColumnModel().getColumn(2).setMinWidth(65);
        jTableMaterial.getColumnModel().getColumn(3).setPreferredWidth(65);//65 UNIDADE
        jTableMaterial.getColumnModel().getColumn(3).setMinWidth(65);
        jTableMaterial.getColumnModel().getColumn(4).setPreferredWidth(325);//265 DESC
        jTableMaterial.getColumnModel().getColumn(4).setMinWidth(325);
        jTableMaterial.getColumnModel().getColumn(5).setPreferredWidth(105);//105 V UNIT
        jTableMaterial.getColumnModel().getColumn(5).setMinWidth(105);
        jTableMaterial.getColumnModel().getColumn(6).setPreferredWidth(100);//85 VALOR TOTAL
        jTableMaterial.getColumnModel().getColumn(6).setMinWidth(100);
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

            jTableMaterial.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][][] {
                    {null, null, null, null, null, null},
                },
                new String [] {
                    "COD MAT","Item","Quant.", "Unidade", "Descrição do Material", "Valor Unitário", "Valor Total"
                }
            ){
            public boolean isCellEditable(int row, int col) {  
                 if(col == 6){
                     return false;
                 }else{    
                     return true; 
                 }
            } 
            });
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
        tableResultado.setRowCount(0);
        montaTabela();
//        tableResultado.getRowCount();
//        jTableMaterial.setBackground(((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));
//
//        System.out.println("COR - >"+((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.GREEN));

        //Color teste = ((tableResultado.getRowCount()%2==0) ? Color.BLACK : Color.BLUE);
        //jTableMaterial.setBackground(Color.WHITE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupPesquisa = new javax.swing.ButtonGroup();
        buttonGroupStatus = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabelDI = new javax.swing.JLabel();
        jComboBoxDataInicial = new CalendarComboBox(true);
        jLabelDF = new javax.swing.JLabel();
        jComboBoxDataFinal = new CalendarComboBox(true);
        jLabelNOrcamento = new javax.swing.JLabel();
        jTextFieldNOrcamentoBusca = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();
        Retorno = new javax.swing.JLabel();
        jCheckBoxAG = new javax.swing.JCheckBox();
        jCheckBoxAP = new javax.swing.JCheckBox();
        jCheckBoxM = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListOrcamento = new javax.swing.JList();
        jLabelNOrcamentos = new javax.swing.JLabel();
        jPanelDados = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldSolicitante = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldUnidade = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldPedidoN = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldLocal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldReferente = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldNumOrcamento = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDescricao = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldObs = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableMaterial = new javax.swing.JTable();
        jButtonNovo = new javax.swing.JButton();
        jButtonSalvar = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonDeletar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jButtonMaisLinha = new javax.swing.JButton();
        jButtonMenosLinha = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldAC = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldData = new javax.swing.JTextField();
        jLabelTotalGeral = new javax.swing.JLabel();
        jButtonVisualizar = new javax.swing.JButton();
        jRadioButtonAP = new javax.swing.JRadioButton();
        jRadioButtonAG = new javax.swing.JRadioButton();
        jRadioButtonM = new javax.swing.JRadioButton();
        jComboBoxClientes = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldPrazoentrega = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldCondicaopagamento = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldValidadeproposta = new javax.swing.JTextField();
        jButtonCopy = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("ORÇAMENTOS");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/IconeLN.png"))); // NOI18N
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

        jPanel1.setBackground(new java.awt.Color(0, 121, 76));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar"));

        jLabelDI.setText("Data inicial:");

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

        jLabelDF.setText("Data final:");

        jComboBoxDataFinal.setSelectedItem(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        jComboBoxDataFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxDataFinalKeyPressed(evt);
            }
        });

        jLabelNOrcamento.setText("Nº Orçamento:");

        jTextFieldNOrcamentoBusca.setNextFocusableComponent(jButtonBuscar);

        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/xmag16x16.png"))); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jCheckBoxAG.setBackground(new java.awt.Color(0, 121, 76));
        jCheckBoxAG.setSelected(true);
        jCheckBoxAG.setText("Aguardando");

        jCheckBoxAP.setBackground(new java.awt.Color(0, 121, 76));
        jCheckBoxAP.setSelected(true);
        jCheckBoxAP.setText("Aprovado");

        jCheckBoxM.setBackground(new java.awt.Color(0, 121, 76));
        jCheckBoxM.setSelected(true);
        jCheckBoxM.setText("Morto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelDI)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelDF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelNOrcamento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNOrcamentoBusca, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBuscar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBoxAP)
                        .addGap(207, 207, 207)
                        .addComponent(Retorno, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCheckBoxAG)
                    .addComponent(jCheckBoxM))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDI)
                            .addComponent(jComboBoxDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDF)
                            .addComponent(jComboBoxDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNOrcamentoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNOrcamento)
                            .addComponent(jButtonBuscar)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBoxAG, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBoxAP, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBoxM, javax.swing.GroupLayout.PREFERRED_SIZE, 21, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Retorno, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jListOrcamento.setBackground(new java.awt.Color(0, 121, 76));
        jListOrcamento.setForeground(new java.awt.Color(255, 255, 255));
        jListOrcamento.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListOrcamento.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jListOrcamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListOrcamentoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jListOrcamento);

        jLabelNOrcamentos.setText("Nº Orçamento:");

        jPanelDados.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));
        jPanelDados.setEnabled(false);

        jLabel1.setText("Cliente:");

        jTextFieldCliente.setForeground(new java.awt.Color(0, 176, 80));

        jLabel4.setText("Solicitante:");

        jLabel5.setText("Unidade:");

        jLabel6.setText("Nº Pedido / O.S:");

        jLabel7.setText("Local:");

        jLabel8.setText("Referente:");

        jTextFieldReferente.setText("Serviços De Marcenaria");

        jLabel9.setText("N° Orçamento:");

        jTextFieldNumOrcamento.setEditable(false);
        jTextFieldNumOrcamento.setFont(new java.awt.Font("Tahoma", 1, 12));
        jTextFieldNumOrcamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jScrollPane2.setAutoscrolls(true);

        jTextAreaDescricao.setColumns(20);
        jTextAreaDescricao.setFont(new java.awt.Font("Tahoma", 0, 11));
        jTextAreaDescricao.setLineWrap(true);
        jTextAreaDescricao.setRows(5);
        jTextAreaDescricao.setTabSize(0);
        jTextAreaDescricao.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextAreaDescricao);

        jLabel10.setText("Observações:");

        jTableMaterial.setBackground(new java.awt.Color(0, 121, 76));
        jTableMaterial.setFont(new java.awt.Font("Arial Black", 0, 12));
        jTableMaterial.setForeground(new java.awt.Color(255, 255, 255));
        jTableMaterial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Item", "Quantidade", "Unidade", "Descrição do Material", "Valor Unitário", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMaterial.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableMaterial.setColumnSelectionAllowed(true);
        jTableMaterial.setDoubleBuffered(true);
        jTableMaterial.setFillsViewportHeight(true);
        jTableMaterial.setGridColor(new java.awt.Color(255, 255, 255));
        jTableMaterial.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTableMaterial.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTableMaterial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMaterialMouseClicked(evt);
            }
        });
        jTableMaterial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableMaterialKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTableMaterial);
        jTableMaterial.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jTableMaterial.getColumnModel().getColumn(0).setResizable(false);
        jTableMaterial.getColumnModel().getColumn(1).setResizable(false);
        jTableMaterial.getColumnModel().getColumn(1).setPreferredWidth(80);
        jTableMaterial.getColumnModel().getColumn(2).setResizable(false);
        jTableMaterial.getColumnModel().getColumn(2).setPreferredWidth(60);
        jTableMaterial.getColumnModel().getColumn(3).setResizable(false);
        jTableMaterial.getColumnModel().getColumn(3).setPreferredWidth(245);
        jTableMaterial.getColumnModel().getColumn(4).setResizable(false);
        jTableMaterial.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTableMaterial.getColumnModel().getColumn(5).setResizable(false);
        jTableMaterial.getColumnModel().getColumn(5).setPreferredWidth(95);
        jTableMaterial.getAccessibleContext().setAccessibleParent(jScrollPane1);

        jButtonNovo.setText("Novo");
        jButtonNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoActionPerformed(evt);
            }
        });

        jButtonSalvar.setText("Salvar");
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });

        jButtonAlterar.setText("Alterar");
        jButtonAlterar.setMaximumSize(new java.awt.Dimension(71, 23));
        jButtonAlterar.setMinimumSize(new java.awt.Dimension(71, 23));
        jButtonAlterar.setPreferredSize(new java.awt.Dimension(71, 23));
        jButtonAlterar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/btnRefreshDisplay.png"))); // NOI18N
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

        jButtonDeletar.setText("Deletar");
        jButtonDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeletarActionPerformed(evt);
            }
        });

        jButtonSair.setText("Sair");
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });

        jButtonMaisLinha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit_add_22x22.png"))); // NOI18N
        jButtonMaisLinha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaisLinhaActionPerformed(evt);
            }
        });

        jButtonMenosLinha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit_remove_22x22.png"))); // NOI18N
        jButtonMenosLinha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMenosLinhaActionPerformed(evt);
            }
        });

        jLabel2.setText("A/C:");

        jLabel11.setText("Data cadastro:");

        jTextFieldData.setEditable(false);
        jTextFieldData.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldData.setAlignmentX(0.0F);
        jTextFieldData.setAlignmentY(0.0F);

        jLabelTotalGeral.setFont(new java.awt.Font("Tahoma", 1, 13));
        jLabelTotalGeral.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jButtonVisualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printer_16x16.png"))); // NOI18N
        jButtonVisualizar.setText("Visualizar Orçamento");
        jButtonVisualizar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonVisualizar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButtonVisualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVisualizarActionPerformed(evt);
            }
        });

        buttonGroupStatus.add(jRadioButtonAP);
        jRadioButtonAP.setText("Aprovado");

        buttonGroupStatus.add(jRadioButtonAG);
        jRadioButtonAG.setText("Aguardando");

        buttonGroupStatus.add(jRadioButtonM);
        jRadioButtonM.setText("Morto");

        jComboBoxClientes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SELECIONE O CLIENTE" }));

        jLabel3.setText("Prazo de entrega:");

        jTextFieldPrazoentrega.setText("Até 15 (quinze) dias após liberação.");

        jLabel12.setText("Condicões de pagamento:");

        jTextFieldCondicaopagamento.setText("Contra apresentação.");

        jLabel13.setText("Validade da proposta:");

        jTextFieldValidadeproposta.setText("Esta proposta é valida por 15 (quinze) dias.");

        jButtonCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/save.png"))); // NOI18N
        jButtonCopy.setEnabled(false);
        jButtonCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCopyActionPerformed(evt);
            }
        });

        jButton1.setText("Cancelar");

        javax.swing.GroupLayout jPanelDadosLayout = new javax.swing.GroupLayout(jPanelDados);
        jPanelDados.setLayout(jPanelDadosLayout);
        jPanelDadosLayout.setHorizontalGroup(
            jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDadosLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(4, 4, 4)
                        .addComponent(jTextFieldCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxClientes, 0, 595, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDadosLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(4, 4, 4)
                        .addComponent(jTextFieldUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSolicitante, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDadosLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPedidoN, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldAC, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDadosLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldObs, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDadosLayout.createSequentialGroup()
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(4, 4, 4)
                                .addComponent(jTextFieldReferente, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9))
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(4, 4, 4)
                                .addComponent(jTextFieldLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldData, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                            .addComponent(jTextFieldNumOrcamento, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)))
                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDadosLayout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldPrazoentrega))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDadosLayout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldCondicaopagamento))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDadosLayout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldValidadeproposta, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jButtonSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jButtonDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addComponent(jButtonVisualizar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonSair)
                                .addGap(10, 10, 10))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDadosLayout.createSequentialGroup()
                                .addComponent(jRadioButtonAP)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonAG)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonM))
                            .addComponent(jLabelTotalGeral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21)
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonCopy, 0, 0, Short.MAX_VALUE)
                    .addComponent(jButtonMenosLinha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jButtonMaisLinha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelDadosLayout.setVerticalGroup(
            jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosLayout.createSequentialGroup()
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                        .addComponent(jButtonMaisLinha, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonMenosLinha, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel1))
                            .addComponent(jTextFieldCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel5))
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jTextFieldUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel4))
                            .addComponent(jTextFieldSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel6))
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldPedidoN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)))
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel8))
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jTextFieldReferente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel9))
                            .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldNumOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonCopy)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel7))
                            .addComponent(jTextFieldLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel11))
                            .addComponent(jTextFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel10))
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jTextFieldObs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jTextFieldPrazoentrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelTotalGeral, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldCondicaopagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel13))
                    .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldValidadeproposta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRadioButtonAP)
                        .addComponent(jRadioButtonAG)
                        .addComponent(jRadioButtonM)))
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButtonDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1))))
                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonSair)
                            .addComponent(jButtonVisualizar))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNOrcamentos)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelNOrcamentos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(jPanelDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxDataInicialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxDataInicialKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            System.out.println("ENTER");
            //jButtonVisual.requestFocus();
        }
}//GEN-LAST:event_jComboBoxDataInicialKeyPressed

    private void jComboBoxDataFinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxDataFinalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDataFinalKeyPressed

    private void jComboBoxDataInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDataInicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDataInicialActionPerformed
    String item = "0";
    private void jButtonMaisLinhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMaisLinhaActionPerformed
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        jTextFieldNumOrcamento.getText();
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
        for(int i=0;i < tableResultado.getRowCount(); i++ ){
            item = String.valueOf(tableResultado.getValueAt(i, 1));
            
        }

        if(tableResultado.getRowCount() == 0){
                item = "0";
                //System.out.println("If Item:" + item);
        }

        System.out.println("Numero de linhas: "+tableResultado.getRowCount()+"\nItem: "+ item);

        //tableResultado.addRow(new Object[] {"", Integer.valueOf(item)+1, null, null, null, null, null});
        tableResultado.addRow(new Object[] {"", Integer.valueOf(item)+1, "0", "", "", "0", "0"});
        //tableResultado.addRow(new Object[] {2, 10, "KIT", "FORMICA", moeda(5.00d), moeda(50.00d)});
        montaTabela();
       
    }//GEN-LAST:event_jButtonMaisLinhaActionPerformed

    private void jButtonMenosLinhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMenosLinhaActionPerformed
           Session sessao = HibernateUtil.getSessionFactory().openSession();
           Transaction transacao = sessao.beginTransaction();


        int[] selectLines = jTableMaterial.getSelectedRows(); // captura as linhas selecionadas

        // pega o DefaultTableModel da tabela
        javax.swing.table.DefaultTableModel table = (javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
           if(jTableMaterial.getSelectedRowCount() <= 0){
               JOptionPane.showMessageDialog(null,"Selecione algum material da tabela para deletar", "Atenção", JOptionPane.INFORMATION_MESSAGE );
               return;
           }else{
               Object[] options = { "Sim", "Não" };
              int a = JOptionPane.showOptionDialog(this, "Deseja realmente apagar este material","Atenção",
                  JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE, null,
                  options, options[0]);
              if(a == JOptionPane.YES_OPTION){

                  Object value;
                for( int x = selectLines.length-1;x>=0; x-- ){
                    if(table.getValueAt(selectLines[x], 0).equals("")){
                        for( int k = selectLines.length-1;k>=0; k-- ){
                            table.removeRow(selectLines[k]-k);
                            System.out.println("Remove linha sem código");
                        }
                        return ;
                    }
                    System.out.println("cod linhas selected = "+ (Integer)table.getValueAt(selectLines[x], 0));
                    MateriaisOrcamentos materiais = (MateriaisOrcamentos) sessao.load(MateriaisOrcamentos.class,(Integer)table.getValueAt(selectLines[x], 0));
                    //System.out.println("DATA "+ new Date("dd/MM/yyyy"));
                    sessao.delete(materiais);
                    sessao.flush();
                    table.removeRow(selectLines[x]);
                    
               }
                transacao.commit();
                sessao.close();
            }else{
                  System.out.println("clicou em NÂO");
                  return;
               }
           }

        
    }//GEN-LAST:event_jButtonMenosLinhaActionPerformed

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        doDefaultCloseAction();
    }//GEN-LAST:event_jButtonSairActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed

        StringBuffer str = new StringBuffer();

        //fabricaDeSessoes = new Configuration().configure().buildSessionFactory();
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
  
        str.append("from Orcamentos o where");
        
        if (jTextFieldNOrcamentoBusca.getText().equals("") && jCheckBoxAG.isSelected() && jCheckBoxAP.isSelected() && jCheckBoxM.isSelected()) {
            str.append(" o.status in('AG', 'AP', 'M') and");
            System.out.println("1º");
        }else if(jTextFieldNOrcamentoBusca.getText().equals("") && jCheckBoxAG.isSelected() && jCheckBoxAP.isSelected()) {
            str.append(" o.status in('AG', 'AP') and");
            System.out.println("2º");
        }else if(jTextFieldNOrcamentoBusca.getText().equals("") && jCheckBoxAG.isSelected() && jCheckBoxM.isSelected()) {
            str.append(" o.status in('AG', 'M') and");
            System.out.println("3º");
        }else if(jTextFieldNOrcamentoBusca.getText().equals("") && jCheckBoxAP.isSelected() && jCheckBoxM.isSelected()) {
            str.append(" o.status in('AP', 'M') and");
            System.out.println("4º");
        }else if(jTextFieldNOrcamentoBusca.getText().equals("") && jCheckBoxAG.isSelected() && jCheckBoxAP.isSelected()) {
            str.append(" o.status in('AG', 'AP') and");
            System.out.println("5º");
        }else  if(jTextFieldNOrcamentoBusca.getText().equals("") && jCheckBoxAG.isSelected()) {
            str.append(" o.status in('AG') and");
            System.out.println("6º");
        }else  if(jTextFieldNOrcamentoBusca.getText().equals("") && jCheckBoxAP.isSelected()) {
            str.append(" o.status in('AP') and");
            System.out.println("7º");
        }else  if(jTextFieldNOrcamentoBusca.getText().equals("") && jCheckBoxM.isSelected()) {
            str.append(" o.status in('M') and");
            System.out.println("8º");
        }else  if(jTextFieldNOrcamentoBusca.getText().length() > 0 && jCheckBoxAG.isSelected() && jCheckBoxAP.isSelected() && jCheckBoxM.isSelected()) {
            str.append(" o.codorcamentos = '"+jTextFieldNOrcamentoBusca.getText()+"' and o.status in('AG', 'AP', 'M') and");
            System.out.println("9º");
        }else  if(jTextFieldNOrcamentoBusca.getText().length() > 0 && jCheckBoxAG.isSelected() && jCheckBoxAP.isSelected()) {
            str.append(" o.codorcamentos = '"+jTextFieldNOrcamentoBusca.getText()+"' and o.status in('AG', 'AP') and");
            System.out.println("10º");
        }else  if(jTextFieldNOrcamentoBusca.getText().length() > 0 && jCheckBoxAG.isSelected()) {
            str.append(" o.codorcamentos = '"+jTextFieldNOrcamentoBusca.getText()+"' and o.status in('AG') and");
            System.out.println("11º");
        }

        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        String dataBusca = sfd.format(new Date());
        if(jComboBoxDataFinal.getSelectedItem().toString() == null){
            JOptionPane.showMessageDialog(null, "Escolha a data final");
            //String dataFinal = jComboBoxDataFinal.getSelectedItem().toString() = dataBusca;
        }
        String dataInicial = jComboBoxDataInicial.getSelectedItem().toString();
        //String dataFinal = jComboBoxDataFinal.getSelectedItem().toString();

        
        //System.out.println("DATA FINAL "+ dataFinal);
        str.append(" o.datacadastro between '").append(dataInicial).append("' and '").append(jComboBoxDataFinal.getSelectedItem()).append("' order by o.codorcamentos");

        System.out.println("SQL = "+ str);
        //Query consultaOrcamentos = sessao.createQuery(" from Orcamentos o where o.codorcamentos = '"+jTextFieldNOrcamentoBusca.getText()+"' and o.datacadastro between '"+jComboBoxDataInicial.getSelectedItem()+"' and '"+jComboBoxDataFinal.getSelectedItem()+"' order by o.codorcamentos ");
        Query consultaOrcamentos = sessao.createQuery(str.toString());
        List orcamentosList = consultaOrcamentos.list();
        System.out.println("foram encontradas "+orcamentosList.size()+" orcamentos na consulta");
        jLabelNOrcamentos.setText("Nº Orçamento: "+orcamentosList.size());

        Iterator i = orcamentosList.iterator();

        //ListModel dlm = (ListModel) jListOrcamento.getModel();
        
        if(orcamentosList != null){
            vetorNOrcamento.clear();
            jListOrcamento.updateUI();
            while (i.hasNext()) {
               Orcamentos orcaList = (Orcamentos) i.next();
               System.out.println(orcaList.getCodorcamentos());
                //System.out.println("NEXT"+ orca.getMateriaisOrcamentoses());
               
               vetorNOrcamento.add(orcaList.getCodorcamentos());
               //jListOrcamento.removeAll();
               jListOrcamento.setListData(vetorNOrcamento);
               jListOrcamento.updateUI();
                System.out.println("retornou");
                jListOrcamento.setSelectedIndex(0);
            }
        }else{
            vetorNOrcamento.clear();
            jListOrcamento.updateUI();
            System.out.println("Limpando List");
            Retorno.setText("Foram encontrados "+ orcamentosList.size());
            LimpaCampos();
        }
        transacao.commit();
        sessao.close();

    }//GEN-LAST:event_jButtonBuscarActionPerformed


    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        
        System.out.println("SALVANDO ORÇAMENTO");



        if (jComboBoxClientes.getSelectedItem().equals("SELECIONE O CLIENTE")) {
            JOptionPane.showMessageDialog(null, "Selecione um cliente", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
//         Configuration cfg = new Configuration().configure();
//                //.addClass(Orcamentos.class);
//
//                TelaOrcamentos test = new TelaOrcamentos();
//
//                test.fabricaDeSessoes = cfg.buildSessionFactory();
try{
        String descricao, ac, cliente, local, numOrcamento, obs, pedidoN, referente, solicitante, unidadeCliente, prazoEntrega, condicaoPagamento, validadeProposta = "";
        String status = null;
        int codCliente = 0;
        String [] strCodCliente = jComboBoxClientes.getSelectedItem().toString().split("-");
        codCliente = Integer.valueOf(strCodCliente[0].trim());



        descricao = jTextAreaDescricao.getText().trim();
        ac = jTextFieldAC.getText().toUpperCase().trim();
        cliente =jTextFieldCliente.getText().trim();
        local = jTextFieldLocal.getText().trim();
        numOrcamento = jTextFieldNumOrcamento.getText().trim();
        obs = jTextFieldObs.getText().trim();
        pedidoN = jTextFieldPedidoN.getText().trim();
        referente = jTextFieldReferente.getText().trim();
        solicitante = jTextFieldSolicitante.getText().trim();
        unidadeCliente = jTextFieldUnidade.getText().trim();
        prazoEntrega = jTextFieldPrazoentrega.getText().trim();
        condicaoPagamento = jTextFieldCondicaopagamento.getText().trim();
        validadeProposta = jTextFieldValidadeproposta.getText().trim();

        if(jRadioButtonAG.isSelected()){
            status = "AG";
        }else if(jRadioButtonAP.isSelected()){
            status = "AP";
        }else if(jRadioButtonM.isSelected()){
            status = "M";
        }

        if(status == null){
            JOptionPane.showMessageDialog(null, "Selecione um status!!!");
            return;
        }

        System.out.println("STATUS INSERT = "+ status);
        
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
        int linhas = tableResultado.getRowCount();
        System.out.println("Linhas:" + linhas);

        String unidade = null;
        String produto="";
        double valorUnitario = 0;
        double valorTotal =0;
        int item, quantidade = 0;
        
       
        int fv;
//        for(fv=0; fv < this.vetorNOrcamento.size(); fv++){
//           if(this.vetorNOrcamento.get(fv).toString().equals(numOrcamento)){
//               jTextFieldNumOrcamento.selectAll();
//               JOptionPane.showMessageDialog(null,"Orçamento Nº: " + numOrcamento + "\nOrçamento já cadastrado", "Atenção", JOptionPane.INFORMATION_MESSAGE );
//               return;
//           }        
//        }


       SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
       String data = sfd.format(new Date());

       Session sessao = HibernateUtil.getSessionFactory().openSession();
       Transaction transacao = sessao.beginTransaction();

       Orcamentos newOrcamento = new Orcamentos(numOrcamento, new Clientes(codCliente), strCodCliente[1].trim(), ac, unidadeCliente,solicitante, referente, obs, status, new Date(),local, pedidoN, descricao, prazoEntrega, condicaoPagamento, validadeProposta);

       sessao.save(newOrcamento);

       DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();


       for(int i =0;i < linhas; i++){

           item = Integer.valueOf(tableResultado.getValueAt(i, 1).toString());
           
           quantidade = Integer.valueOf(tableResultado.getValueAt(i, 2).toString());
                //System.out.println("I1 "+i);
           unidade = tableResultado.getValueAt(i, 3).toString();
                //System.out.println("I2 "+i);
           produto = tableResultado.getValueAt(i, 4).toString();
                //System.out.println("I3 "+i);
           try {

           //valorUnitario = Double.valueOf(tableResultado.getValueAt(i, 5).toString().replace(",", "."));
            valorUnitario = dff.parse((String) tableResultado.getValueAt(i, 5)).doubleValue(); // Double.valueOf(tableResultado.getValueAt(i, 5).toString().replace(",", "."));
                //System.out.println("I4 "+i);
                valorTotal = dff.parse((String) tableResultado.getValueAt(i, 6)).doubleValue(); // Double.valueOf(tableResultado.getValueAt(i, 5).toString().replace(",", "."));
                // System.out.println("I5 "+i);
            } catch (ParseException ex) {
                Logger.getLogger(TelaOrcamentos.class.getName()).log(Level.SEVERE, null, ex);
            }
          
           int nextVal = getNextvalMateriaisOrcamentos();//( (java.math.BigInteger)object ).intValue();
           System.out.println("NEXTVAL MATERIAIS "+nextVal);

           System.out.println("SAIDA: \nlinha: "+ (i+1) + "\n"+quantidade+ " - "+ unidade+ " - "+ produto+ " - "+ valorUnitario+ " - "+ valorTotal);

           MateriaisOrcamentos materiais = new MateriaisOrcamentos(nextVal, newOrcamento, quantidade, unidade, produto, valorUnitario, valorTotal, item);

           materiais.setOrcamentos(newOrcamento);
           newOrcamento.getMateriaisOrcamentoses().add(materiais);
           sessao.save(materiais);
           //sessao.clear();
           sessao.flush();
           materiais = null;
           System.out.println("Materiais Incluídos!!!!!");

       }

           transacao.commit();
           System.out.println("COMMIT INSERT");
           sessao.close();
           vetorNOrcamento.add(newOrcamento.getCodorcamentos());
           jListOrcamento.setListData(vetorNOrcamento);
           jListOrcamento.updateUI();
           LimpaCampos();
           if(jButtonNovo.getText().equals("Cancelar") || jButtonCopy.isEnabled()){
               jButtonNovo.setText("Novo");
               jButtonNovo.setEnabled(true);
               jButtonSalvar.setEnabled(false);
               jButtonCopy.setEnabled(false);
               jButtonVisualizar.setEnabled(false);
               Campos(false);
           }
           JOptionPane.showMessageDialog(null, "Orçamento incluído com sucesso");

           }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Erro: "+ ex+"\n Causa: "+ ex.getCause(), "Atenção - Salvando", JOptionPane.INFORMATION_MESSAGE );
        }

    }//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jListOrcamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListOrcamentoMouseClicked
        if( vetorNOrcamento.size() > 0){
            jButtonDeletar.setEnabled(true);
            jButtonAlterar.setEnabled(true);
            jButtonVisualizar.setEnabled(true);
            System.out.println("If List "+vetorNOrcamento.size());
        }else{
            jButtonDeletar.setEnabled(false);
            jButtonAlterar.setEnabled(false);
            jButtonVisualizar.setEnabled(false);
            LimpaCampos();
            System.out.println("Else List "+ vetorNOrcamento.size());
        }
        //fabricaDeSessoes = new Configuration().configure().buildSessionFactory();

        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        String data = sfd.format(new Date());

        if(evt.getClickCount() == 1 && vetorNOrcamento.size() > 0){

            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();
            //Query consultaOrca = sessao.createQuery("from Orcamentos as o where o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' ");

            Query consultaOrca = sessao.createQuery("select o, mo, c from Orcamentos as o, MateriaisOrcamentos as mo, Clientes as c where  o.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"' and mo.orcamentos = o.codorcamentos and o.clientes = c.codcliente order by o.codorcamentos, mo.codmateriais ");

            //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
            List orcamentos = consultaOrca.list();
            //List materiais = consultaMat.list();
            //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
            Iterator i = orcamentos.iterator();
            // Iterator it = materiais.iterator();

            Orcamentos orca = null;
            MateriaisOrcamentos matOrca = null;
            Clientes cliente = null;
            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
            tableResultado.setRowCount(0);
            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
            //System.out.println("MAT ->"+ matOrca.getProduto());
            while (i.hasNext()) {

                 Object[] objeto = (Object[]) i.next();
                 orca = (Orcamentos) objeto[0];
                 matOrca = (MateriaisOrcamentos) objeto[1];
                 cliente = (Clientes) objeto[2];

                 tableResultado.addRow(new Object[] {matOrca.getCodmateriais(),matOrca.getItem(), matOrca.getQuantidade(), matOrca.getUnidade(), matOrca.getProduto(), moeda(matOrca.getValorunitario()), moeda(matOrca.getValortotal())});

            }
/*
            int linhas = tableResultado.getRowCount();
            double quantidade = 0;
            double valorUnitario = 0;
            double valorTotal = 0;
            double valorTotalGeral = 0;
            int x = 0;

            DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();

            for(;x < linhas; x++){

               quantidade = Double.valueOf(tableResultado.getValueAt(x, 2).toString().replace(",", "."));
               try {   //System.out.println("I4 "+i);
               valorUnitario = dff.parse((String)tableResultado.getValueAt(x, 5)).doubleValue();
               System.out.println("Quantidade: "+ quantidade+"\nValor Unitario: "+ valorUnitario);

               //if (jTableMaterial.getSelectedColumn()== 0|| jTableMaterial.getSelectedColumn()== 1|| jTableMaterial.getSelectedColumn()== 2 || jTableMaterial.getSelectedColumn()== 3 || jTableMaterial.getSelectedColumn()== 4 || jTableMaterial.getSelectedColumn()== 5 || jTableMaterial.getSelectedColumn()== 6) {
               if (jTableMaterial.getSelectedColumn() < 7){
                   valorTotal = quantidade*valorUnitario;
                   tableResultado.setValueAt(moeda(valorTotal), x, 6);
                   System.out.println("Coluna Selecionada = "+jTableMaterial.getSelectedColumn() );
               }

                    valorTotalGeral += dff.parse((String)tableResultado.getValueAt(x, 6)).doubleValue();
               } catch (ParseException ex) {
                    Logger.getLogger(TelaOrcamentos.class.getName()).log(Level.SEVERE, null, ex);
               }
                    System.out.println("TOTAL: "+ moeda(valorTotalGeral));
                    totalGeral = valorTotalGeral;
                    jLabelTotalGeral.setText("TOTAL GERAL: R$ "+moeda(totalGeral));
            }
**/
            calculaTable();

            if(orca!= null){
                //System.out.println("Classe "+ orca.toString());


                //System.out.println("CLicou "+orca.getCodorcamentos());
                jComboBoxClientes.setEditable(true);
                jComboBoxClientes.setSelectedItem(cliente.getCodcliente()+" - "+ cliente.getRazaosocial());
                jComboBoxClientes.setEditable(false);
                jTextFieldCliente.setText(orca.getCliente());
                jTextAreaDescricao.setText(orca.getDescricao());
                jTextFieldAC.setText(orca.getAc());
                jTextFieldLocal.setText(orca.getLocal());
                jTextFieldData.setText(sfd.format(orca.getDatacadastro()));
                jTextFieldNumOrcamento.setText(orca.getCodorcamentos());
                jTextFieldObs.setText(orca.getObservacoes());
                jTextFieldPedidoN.setText(orca.getPedido());
                jTextFieldReferente.setText(orca.getReferente());
                jTextFieldSolicitante.setText(orca.getSolicitante());
                jTextFieldUnidade.setText(orca.getUnidade());
                jTextFieldPrazoentrega.setText(orca.getPrazoentrega());
                jTextFieldCondicaopagamento.setText(orca.getCondicoespagamento());
                jTextFieldValidadeproposta.setText(orca.getValidadeproposta());

                if(orca.getStatus().equals("AG")){
                    jRadioButtonAG.setSelected(true);
                    //jRadioButtonAP2.setSelected(false);
                    //jCheckBoxMorto2.setSelected(false);
                }else if(orca.getStatus().equals("AP")){
                    jRadioButtonAP.setSelected(true);
                    //jCheckBoxAguardando2.setSelected(false);
                    //jCheckBoxMorto2.setSelected(false);
                }else if (orca.getStatus().equals("M")){
                    jRadioButtonM.setSelected(true);
                    //jCheckBoxAguardando2.setSelected(false);
                    //jCheckBoxAprovado2.setSelected(false);
                }
       
                transacao.commit();
                sessao.close();
        
            }
        }

    }//GEN-LAST:event_jListOrcamentoMouseClicked

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

            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();

            SQLQuery query = sessao.createSQLQuery("select nextval('seq_orcamentos')");
            Object object = query.uniqueResult();
            System.out.println("NEXTVAL NOVO "+( (java.math.BigInteger)object ).longValue());

            LimpaCampos();

            jTextFieldNumOrcamento.setText(((java.math.BigInteger)object ).toString());

            SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
            String data = sfd.format(new Date());

            jTextFieldData.setText(data);
            jTextFieldReferente.setText("Serviços De Marcenaria");
            jTextAreaDescricao.setText("1.  Objetivo: Fornecimento de material e mão de obra para confecção e instalação de mobiliários.");
            jTextFieldPrazoentrega.setText("Até 10 (dez) dias após liberação.");
            jTextFieldCondicaopagamento.setText("Contra apresentação.");
            jTextFieldValidadeproposta.setText("Esta proposta é valida por 15 (quinze) dias.");

            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
            //tableResultado.addRow(new Object[] {"", Integer.valueOf(item)+1, null, null, null, null, null});    //
            tableResultado.addRow(new Object[] {"", Integer.valueOf(item)+1, "0", "", "", "0", "0"});
        }
        
    }//GEN-LAST:event_jButtonNovoActionPerformed

    private void jButtonDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeletarActionPerformed
           //fabricaDeSessoes = new Configuration().configure().buildSessionFactory();
    try{
           Session sessao = HibernateUtil.getSessionFactory().openSession();
           Transaction transacao = sessao.beginTransaction();
           jTextFieldNumOrcamento.getText();

           if(jListOrcamento.getSelectedIndex() == -1){
               JOptionPane.showMessageDialog(null,"Selecione algum Nº de orçamento ao lado para deletar", "Atenção", JOptionPane.INFORMATION_MESSAGE );
               return;
           }else{
               Object[] options = { "Sim", "Não" };
              int a = JOptionPane.showOptionDialog(this, "Deseja realmente apagar este orçamento","Atenção",
                  JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE, null,
                  options, options[0]);
              if(a == JOptionPane.YES_OPTION){
                   Orcamentos newOrcamento = (Orcamentos) sessao.load(Orcamentos.class,jTextFieldNumOrcamento.getText());
                   //System.out.println("DATA "+ new Date("dd/MM/yyyy"));
                   sessao.delete(newOrcamento);
                   sessao.flush();
                   transacao.commit();
                   sessao.close();

                   vetorNOrcamento.remove(newOrcamento.getCodorcamentos());
                   jListOrcamento.setListData(vetorNOrcamento);
                   jListOrcamento.updateUI();
                   LimpaCampos();
                   JOptionPane.showMessageDialog(null, "Orçamento "+jTextFieldNumOrcamento.getText()+" deletado com sucesso");

                  System.out.println("Orçamento <html><b>"+jTextFieldNumOrcamento.getText()+"</b></html> deletado com sucesso!");
               }else{
                  System.out.println("clicou em NÂO");
                  return;
               }
           }

        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Erro: "+ ex+"\n Causa: "+ ex.getCause(), "Atenção - Atualizando", JOptionPane.INFORMATION_MESSAGE );
        }
    }//GEN-LAST:event_jButtonDeletarActionPerformed

    private void jTableMaterialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableMaterialKeyReleased
        
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
        int linhas = tableResultado.getRowCount();
        double item, quantidade = 0;
        double valorUnitario = 0;
        double valorTotal = 0;
        double valorTotalGeral = 0;
        int x = 0;

        DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();

        for(;x < linhas; x++){
            
           item = Double.valueOf(tableResultado.getValueAt(x, 1).toString().replace(",", ".")); 
            
           quantidade = Double.valueOf(tableResultado.getValueAt(x, 2).toString().replace(",", "."));
           try{   //System.out.println("I4 "+i);
           valorUnitario = dff.parse((String)tableResultado.getValueAt(x, 5)).doubleValue();
           //valorUnitario = Double.valueOf(tableResultado.getValueAt(x, 4).toString().replace(",", "."));
           System.out.println("Quantidade: "+ quantidade+"\nValor Unitario: "+ valorUnitario);
           
           if (jTableMaterial.getSelectedColumn()== 0|| jTableMaterial.getSelectedColumn()== 1|| jTableMaterial.getSelectedColumn()== 2 || jTableMaterial.getSelectedColumn()== 3 || jTableMaterial.getSelectedColumn()== 4 || jTableMaterial.getSelectedColumn()== 5 || jTableMaterial.getSelectedColumn()== 6) {
               valorTotal = quantidade*valorUnitario; 
               tableResultado.setValueAt(moeda(valorTotal), x, 6);
               System.out.println("Coluna selcionada = "+ jTableMaterial.getSelectedColumn());
                
            }
            //try {
                valorTotalGeral += dff.parse((String)tableResultado.getValueAt(x, 6)).doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(TelaOrcamentos.class.getName()).log(Level.SEVERE, null, ex);
            }
                System.out.println("TOTAL: "+ moeda(valorTotalGeral));
                jLabelTotalGeral.setText("TOTAL GERAL: R$ "+String.valueOf(moeda(valorTotalGeral)));
        }    
    }//GEN-LAST:event_jTableMaterialKeyReleased

    private void jTableMaterialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMaterialMouseClicked
if (evt.getClickCount() == 1 || evt.getClickCount() == 2) {
calculaTable();
/*
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
        int linhas = tableResultado.getRowCount();
        double item, quantidade = 0;
        double valorUnitario = 0;
        double valorTotal = 0;
        double valorTotalGeral = 0;
        int x = 0;

        DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();

        for(;x < linhas; x++){
            if(tableResultado.getRowCount() > 0){
               quantidade = Double.valueOf(tableResultado.getValueAt(x, 2).toString());//.replace(",", "."));
               try{     //System.out.println("I4 "+i);
               valorUnitario = dff.parse((String)tableResultado.getValueAt(x, 5)).doubleValue();
               System.out.println("Quantidade: "+ quantidade+"\nValor Unitario: "+ valorUnitario);

               if (jTableMaterial.getSelectedColumn()== 0|| jTableMaterial.getSelectedColumn()== 1|| jTableMaterial.getSelectedColumn()== 2 || jTableMaterial.getSelectedColumn()== 3 || jTableMaterial.getSelectedColumn()== 4) {
                   valorTotal = quantidade*valorUnitario;
                   tableResultado.setValueAt(moeda(valorTotal), x, 6);

                }

                    valorTotalGeral += dff.parse((String)tableResultado.getValueAt(x, 6)).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(TelaOrcamentos.class.getName()).log(Level.SEVERE, null, ex);
                }
                    System.out.println("TOTAL: "+ moeda(valorTotalGeral));
                    String desc = "TOTAL GERAL: R$ ";
                    totalGeral = valorTotalGeral;
                    //jLabelTotalGeral.setText("TOTAL GERAL: R$ "+String.valueOf(moeda(valorTotalGeral)));
                    jLabelTotalGeral.setText(desc+moeda(totalGeral));
            }
        }**/
    }

    }//GEN-LAST:event_jTableMaterialMouseClicked

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


    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed

    try{

        if(jButtonAlterar.getText().equals("Atualizar")){
            jButtonAlterar.setText("Alterar");
            Campos(false);
            jButtonCopy.setEnabled(false);
            System.out.println("ATUALIZANDO ORÇAMENTO");

            String descricao, ac, cliente, local, data, numOrcamento, obs, pedidoN, referente, solicitante, unidadeCliente, prazoEntrega, validadeProposta, condicaoPagamento = "";
            String status = null;

            int codCliente = 0;
            String [] strCodCliente = jComboBoxClientes.getSelectedItem().toString().split("-");
            codCliente = Integer.valueOf(strCodCliente[0].trim());


            descricao = jTextAreaDescricao.getText();
            ac = jTextFieldAC.getText();
            cliente =jTextFieldCliente.getText();
            local = jTextFieldLocal.getText();
            data = jTextFieldData.getText();
            numOrcamento = jTextFieldNumOrcamento.getText();
            obs = jTextFieldObs.getText();
            pedidoN = jTextFieldPedidoN.getText();
            referente = jTextFieldReferente.getText();
            solicitante = jTextFieldSolicitante.getText();
            unidadeCliente = jTextFieldUnidade.getText();
            prazoEntrega = jTextFieldPrazoentrega.getText();
            condicaoPagamento = jTextFieldCondicaopagamento.getText();
            validadeProposta = jTextFieldValidadeproposta.getText();

            if(jRadioButtonAG.isSelected()){
                status = "AG";
            }else if(jRadioButtonAP.isSelected()){
                status = "AP";
            }else if(jRadioButtonM.isSelected()){
                status = "M";
            }

            System.out.println("STATUS = "+ status);

            javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
            int linhas = tableResultado.getRowCount();
            System.out.println("Linhas:" + linhas);

            String unidade = null;
            String produto="";
            double valorUnitario = 0;
            double valorTotal =0;
            int item, quantidade = 0;

            int fv;
    //        for(fv=0; fv < this.vetorNOrcamento.size(); fv++){
    //           if(this.vetorNOrcamento.get(fv).toString().equals(numOrcamento)){
    //               jTextFieldNumOrcamento.selectAll();
    //               JOptionPane.showMessageDialog(null,"Orçamento Nº: " + numOrcamento + "\nOrçamento já cadastrado", "Atenção", JOptionPane.INFORMATION_MESSAGE );
    //               return;
    //           }
    //        }


           SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
           String dataa = sfd.format(new Date());
           
           Date date = null;
            try {
                date = sfd.parse(data);
                System.out.println("String data: "+ data);
                System.out.println("Date date: "+ date);
            } catch (ParseException ex) {
                Logger.getLogger(TelaOrcamentos.class.getName()).log(Level.SEVERE, null, ex);
            }

           Session sessao = HibernateUtil.getSessionFactory().openSession();
           Transaction transacao = sessao.beginTransaction();

           Orcamentos newOrcamento = new Orcamentos(numOrcamento, new Clientes(codCliente), strCodCliente[1].trim(), ac, unidadeCliente,solicitante, referente, obs, status, date,local, pedidoN, descricao, prazoEntrega, condicaoPagamento, validadeProposta);

           //Orcamentos newOrcamento = new Orcamentos(numOrcamento, strCodCliente[1], ac, unidadeCliente,solicitante, referente, obs, status, new Date(getDataTelaToBD(data)),local, pedidoN, descricao, codCliente);
           //System.out.println("DATA "+ new Date("dd/MM/yyyy"));
           sessao.update(newOrcamento);

           int x =0;
           DecimalFormat dff = (DecimalFormat) DecimalFormat.getInstance();
           for(;x < linhas; x++){

               item = Integer.valueOf(tableResultado.getValueAt(x, 1).toString());

               quantidade = Integer.valueOf(tableResultado.getValueAt(x, 2).toString());
                    //System.out.println("I1 "+i);
               unidade = tableResultado.getValueAt(x, 3).toString();
                    //System.out.println("I2 "+i);
               produto = tableResultado.getValueAt(x, 4).toString();
                    //System.out.println("I3 "+i);
               try{
                    valorUnitario = dff.parse((String)tableResultado.getValueAt(x, 5)).doubleValue();

                    //System.out.println("I4 "+i);
                    valorTotal = dff.parse((String) tableResultado.getValueAt(x, 6)).doubleValue();

               System.out.println("SAIDA UPDATE: \nlinha: "+ (x+1) + "\n"+quantidade+ " - "+ unidade+ " - "+ produto+ " - "+ valorUnitario+ " - "+ valorTotal);

               int codMateriais = 0;
               if(tableResultado.getValueAt(x, 0).equals("")){
                   codMateriais = getNextvalMateriaisOrcamentos();
               }else{
                   codMateriais = Integer.valueOf(tableResultado.getValueAt(x, 0).toString());
               }
                   System.out.println("CodMateriais = "+ codMateriais);

               MateriaisOrcamentos materiais = new MateriaisOrcamentos(codMateriais, newOrcamento, quantidade, unidade, produto, valorUnitario, valorTotal, item);
               System.out.println("COD MATERIAIS UPDATE " + codMateriais);
               //materiais.setOrcamentos(newOrcamento);
               ///newOrcamento.getMateriaisOrcamentoses().add(materiais);
               sessao.merge(materiais);
               //sessao.clear();

               //sessao.flush();
               //materiais = null;
               System.out.println("Materiais Atualizados!!!!!");
               //break;
               } catch (ParseException ex) {
                    Logger.getLogger(TelaOrcamentos.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
           transacao.commit();
           System.out.println("COMMIT UPDATE");
           sessao.close();
//           vetorNOrcamento.add(newOrcamento.getCodorcamentos());
//           jListOrcamento.setListData(vetorNOrcamento);
           jListOrcamento.setEnabled(true);
           jListOrcamento.updateUI();
           JOptionPane.showMessageDialog(null, "Orçamento atualizado com sucesso");



       }else{
            jButtonAlterar.setText("Atualizar");
            jListOrcamento.setEnabled(false);
            jButtonDeletar.setEnabled(false);
            Campos(true);
            jButtonCopy.setEnabled(true);
            jTextFieldNumOrcamento.setEditable(false);

       }

        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Erro: "+ ex+"\n Causa: "+ ex.getCause(), "Atenção - Atualizando", JOptionPane.INFORMATION_MESSAGE );
        }
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVisualizarActionPerformed

        try {
            Locale locale = new Locale("pt", "BR");
            GregorianCalendar calendar = new GregorianCalendar();
            SimpleDateFormat formatador = new SimpleDateFormat("dd' de 'MMMMM' 'yyyy ", locale);
            String dataHora = formatador.format(calendar.getTime());
            Document document = null;
            javax.swing.table.DefaultTableModel tableResultado = (javax.swing.table.DefaultTableModel) jTableMaterial.getModel();
            int linhas = tableResultado.getRowCount();
            System.out.println("Linhas:"+ linhas);
            //try{
            document = new Document(PageSize.A4, 20, 20, 30, 30);


            File fileCliente = new File("z:\\"+ jTextFieldCliente.getText().replaceAll(" ", "_"));
            File fileSolicitante = new File("z:\\"+ jTextFieldCliente.getText().replaceAll(" ", "_") + "\\"+ jTextFieldSolicitante.getText().replaceAll(" ", "_"));

            if (!fileSolicitante.exists()) {
                fileSolicitante.mkdirs();
                System.out.println("Criando pastas MKDIRS");
            }

//            if (!fileCliente.exists()) {
//                fileCliente.mkdir();
//                System.out.println("Criando pastas MKDIR");
//            }


            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("z:\\" + jTextFieldCliente.getText().replaceAll(" ", "_")+ "\\"  + jTextFieldSolicitante.getText().replaceAll(" ", "_") + "\\"  + jTextFieldNumOrcamento.getText() + "_" + jTextFieldCliente.getText().toUpperCase().replace(" ", "_") + ".pdf"));

            } catch (DocumentException ex) {
                Logger.getLogger(TelaOrcamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TelaOrcamentos.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ///////////////////////////////////////////////////////////////////////cabeçalho/////////////////////////////////////////////////////////////////////////////


            Phrase ph = new Phrase();
            Table t = new Table(1, 1);
            t.setWidth(100);
            float[] widths = {700};
            t.setWidths(widths);
            t.setBorderWidth(0);
            t.setBorderColor(new Color(0, 176, 80));
            t.setAlignment(Element.ALIGN_CENTER);

            Image gif = null;
            try {
                //URL url = this.servletContext.getContext("/").getResource("/imagens/logo.gif");
                URL url = this.getClass().getResource("/images/cabecalhoLN.png");

                if (url != null) {
                    gif = Image.getInstance(url);
                        System.out.println("URL:" + url);
                }
            }catch (IOException ioe) {
                throw new DocumentException(ioe.getMessage());
            }
            if (gif != null) {
                gif.scaleAbsolute(550, 54);
                gif.setAlignment(Image.ALIGN_JUSTIFIED_ALL);
                Chunk ck = new Chunk(gif, 0, 0);
                Cell c1 = new Cell();
                c1.add(ck);
                //c1.add(gif);
                c1.setBorderWidth(0);
                t.addCell(c1);
                    System.out.println("i f header ");
            }else {
                Cell c1 = new Cell();
                c1.add(new Phrase());
                c1.setBorderWidth(0);
                t.addCell(c1);
                    System.out.println("else header");
            }
            // this.CABECALHO
            //Cell c2 = new Cell(new Phrase("teste", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0, 0, 0))));
            //c2.setHorizontalAlignment(Cell.ALIGN_LEFT);
            //c2.setBorderWidth(1);
            //t.addCell(c2);

            //ph.clear();
            ph.add(t);

            HeaderFooter header = new HeaderFooter(ph, false);

            header.setBorder(Rectangle.NO_BORDER);
            document.setHeader(header);


            /////////////////////////////////////////////////////////////////////fim cabeçalho/////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////rodape////////////////////////////////////////////////////////////////////////////////
            //HeaderFooter footer = new HeaderFooter(new Phrase("Dt. Emissão " + new SimpleDateFormat("dd/MM/yyyy").format(new Date())),false);
            Paragraph paragrafoFoo = new Paragraph("" + "Av. Aimará, 113 – Pq. Pirajussara - Embú das Artes/SP \n Fonefax: 4137-5415 | 4245-6987\n E-mail: contato@liraenobre.com.br     CNPJ: 02.793.471/0001-20 " + "                                                                                                                                                                    Página ", new RtfFont("Arial", 7));
            paragrafoFoo.setAlignment(paragrafoFoo.ALIGN_CENTER);
            Phrase fraseFoo = new Phrase();
            fraseFoo.add(paragrafoFoo);
            HeaderFooter footer = new HeaderFooter(fraseFoo, true);
            //header.setAlignment(Element.ALIGN_CENTER);
            document.setFooter(footer);
            /////////////////////////////////////////////////////////////////////fim rodape///////////////////////////////////////////////////////////////////////////////
            document.open();

            document.add(new Paragraph(" "));
            document.add(new Paragraph("São Paulo, " + formatador.format(new Date(getDataTelaToBD(jTextFieldData.getText()))) , new RtfFont("Arial", 12)));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Á", new RtfFont("Arial", 12)));
            document.add(new Paragraph(""));
            document.add(new Paragraph(jTextFieldCliente.getText(), new RtfFont("Arial", 12)));
            document.add(new Paragraph("A/C: " + jTextFieldAC.getText(), new RtfFont("Arial", 12)));
            document.add(new Paragraph("Solicitante: " + jTextFieldSolicitante.getText() + "                                                         N° Pedido / O.S: " + jTextFieldPedidoN.getText(), new RtfFont("Arial", 12)));
            document.add(new Paragraph("Unidade: " + jTextFieldUnidade.getText(), new RtfFont("Arial", 12)));
            document.add(new Paragraph("Local: " + jTextFieldLocal.getText()+"\n", new RtfFont("Arial", 12)));

            document.add(new Paragraph(" "));
            PdfPTable tableRef = new PdfPTable(2);
                int[] widthss = {60, 35};
                tableRef.setWidths(widthss);
                tableRef.setWidthPercentage(100);

                
                PdfPCell ref = new PdfPCell(new Phrase("Referente: "+jTextFieldReferente.getText()+"                       "));
                //PdfPCell NumORcamento = new PdfPCell(new Phrase("Nº Orçamento: "+jTextFieldNumOrcamento.getText(), new Font(Font.NORMAL,12,Font.BOLD)));
                ref.setBackgroundColor(new Color(0,121,76));
                ref.setBorderWidthRight(0);
                tableRef.addCell(ref);

                //PdfPCell ref = new PdfPCell(new Phrase("Referente: "+jTextFieldReferente.getText()+"                       "+ "Nº Orçamento: "+jTextFieldNumOrcamento.getText(), new Font(Font.NORMAL,12,Font.BOLD)));
                //PdfPCell NumORcamento = new PdfPCell(new Phrase("Nº Orçamento: "+jTextFieldNumOrcamento.getText(), new Font(Font.NORMAL,12,Font.BOLD)));
                ref = new PdfPCell(new Phrase("Nº Orçamento: "+jTextFieldNumOrcamento.getText(), new Font(Font.UNDEFINED,12,Font.UNDEFINED)));
                ref.setBackgroundColor(new Color(0,121,76));
                ref.setBorderWidthLeft(0);
                tableRef.addCell(ref);
                //tableRef.setHeaderRows(1);
                //tableRef.addCell(NumORcamento);
                //NumORcamento.setHorizontalAlignment(Element.ALIGN_RIGHT);
                document.add(tableRef);

            //document.add(new Paragraph("REF.: " + jTextFieldReferente.getText(),new RtfFont("Arial",12, RtfFont.BOLD)));

                if(!jTextAreaDescricao.getText().equals("")){
                    document.add(new Paragraph("\t"+ jTextAreaDescricao.getText(), new Font(Font.UNDEFINED,12,Font.BOLD) ));
                }

           
                document.add(new Paragraph(" "));
                PdfPTable tableObs = new PdfPTable(1);
                    //int[] widthss = {10, 13, 70, 15, 15};
                    tableObs.setWidthPercentage(100);
                    tableObs.addCell(new Phrase("Obs: "+jTextFieldObs.getText(),new Font(Font.UNDEFINED,10,Font.BOLD, Color.red)));
                    tableObs.setHeaderRows(0);
                    tableObs.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);


                //document.add(new Paragraph("" + jTextFieldObs.getText()+"\n "));
                //document.add(new Paragraph("Quant.              Unid.                   Descrição do Produto                              Valor Unit.         Valor Total          ", new RtfFont("Arial",12,RtfFont.BOLD)));
            if(!jTextFieldObs.getText().equals("")){
                document.add(tableObs);
                System.out.println("Esconde");
            }

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
                int[] widthsProd = {7, 10, 10, 40, 16, 17};
                table.setWidths(widthsProd);

//		 t.setBorderColor(BaseColor.GRAY);
//		 t.setPadding(4);
//		 t.setSpacing(4);
//		 t.setBorderWidth(1);

                PdfPCell c1 = new PdfPCell(new Phrase("Item", new Font(Font.UNDEFINED,12,Font.BOLD)));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_BOTTOM);
                c1.setBackgroundColor(new Color(0,121,76));
                table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Quant.", new Font(Font.UNDEFINED,12,Font.BOLD)));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_BOTTOM);
                c1.setBackgroundColor(new Color(0,121,76));
                table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Unid.", new Font(Font.UNDEFINED,12,Font.BOLD)));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                c1.setBackgroundColor(new Color(0,121,76));
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Descrição do Produto", new Font(Font.UNDEFINED,12,Font.BOLD)));
		c1.setHorizontalAlignment(c1.ALIGN_CENTER);
                c1.setVerticalAlignment(c1.ALIGN_MIDDLE);
                c1.setBackgroundColor(new Color(0,121,76));

		table.addCell(c1);

                c1 = new PdfPCell(new Phrase("Valor Unit.", new Font(Font.UNDEFINED,12,Font.BOLD)));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                c1.setBackgroundColor(new Color(0,121,76));
		table.addCell(c1);

                c1 = new PdfPCell(new Phrase("Valor Total", new Font(Font.UNDEFINED,12,Font.BOLD)));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                c1.setBackgroundColor(new Color(0,121,76));
		table.addCell(c1);
		table.setHeaderRows(1);

            int x = 0;
            for (; x < linhas; x++) {
               // document.add(new Paragraph((String) tableResultado.getValueAt(x, 1).toString() + "                     " + (String) tableResultado.getValueAt(x, 2)+"                      "+(String)tableResultado.getValueAt(x, 3).toString()+"                                                           "+(String)tableResultado.getValueAt(x, 4)+"                "+ (String)tableResultado.getValueAt(x, 5)));

                Paragraph paraItem = new Paragraph("     "+(String) tableResultado.getValueAt(x, 1).toString());
                Paragraph para = new Paragraph("     "+(String) tableResultado.getValueAt(x, 2).toString());
                paraItem.setAlignment(para.ALIGN_CENTER);
                para.setAlignment(para.ALIGN_CENTER);
                
                c1 = new PdfPCell(new Phrase((String) tableResultado.getValueAt(x, 1).toString()));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_TOP);
                table.addCell(c1);//Item

                c1 = new PdfPCell(new Phrase((String) tableResultado.getValueAt(x, 2).toString()));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_TOP);
                table.addCell(c1);//QUANt.

                c1 = new PdfPCell(new Phrase((String) tableResultado.getValueAt(x, 3).toString()));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(c1);//Unid.

                c1 = new PdfPCell(new Phrase((String) tableResultado.getValueAt(x, 4).toString()));
                c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                c1.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(c1);//Descricao

                c1 = new PdfPCell(new Phrase("R$ "+(String) tableResultado.getValueAt(x, 5).toString()));
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                c1.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(c1);//Valor Unit

                c1 = new PdfPCell(new Phrase("R$ "+(String) tableResultado.getValueAt(x, 6).toString()));
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                c1.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(c1);//Valor Total
		


            }

                document.add(table);


                PdfPTable tableTatoal = new PdfPTable(2);
                tableTatoal.setWidthPercentage(33);
                int[] widthsTotal = {16, 17};
                tableTatoal.setWidths(widthsTotal);
                //tableTatoal.setWidthPercentage(33);

                calculaTable();
                PdfPCell refTotal = new PdfPCell(new Phrase("Total Geral", new Font(Font.UNDEFINED,12,Font.BOLD)));
                refTotal.setBackgroundColor(new Color(0,121,76));
                refTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
                refTotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableTatoal.addCell(refTotal);

                PdfPCell refTotalValor = new PdfPCell(new Phrase("R$ "+moeda(totalGeral), new Font(Font.UNDEFINED,12,Font.BOLD)));
                refTotalValor.setBackgroundColor(new Color(0,121,76));
                refTotalValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
                refTotalValor.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableTatoal.addCell(refTotalValor);
                //tableRef.addCell(NumORcamento);
                tableTatoal.setHorizontalAlignment(Element.ALIGN_RIGHT);
                document.add(tableTatoal);

                Extenso ex = new Extenso(totalGeral);

                document.add(new Paragraph(" ", new RtfFont("Arial",5, RtfFont.BOLD)));
                document.add(new Paragraph("2. Valor total dos serviços", new RtfFont("Arial", 12, RtfFont.BOLD)));
                document.add(new Paragraph("R$ "+moeda(totalGeral)+" ("+ex.toString()+")"));

                document.add(new Paragraph(" ", new RtfFont("Arial",5, RtfFont.BOLD)));
                document.add(new Paragraph("3. Prazo de Entrega", new RtfFont("Arial", 12, RtfFont.BOLD)));
                document.add(new Paragraph(jTextFieldPrazoentrega.getText()));

                document.add(new Paragraph(" ", new RtfFont("Arial", 5, RtfFont.BOLD)));
                document.add(new Paragraph("4. Condições de Pagamento", new RtfFont("Arial", 12, RtfFont.BOLD)));
                document.add(new Paragraph(jTextFieldCondicaopagamento.getText()));

                document.add(new Paragraph(" ", new RtfFont("Arial",5, RtfFont.BOLD)));
                document.add(new Paragraph("5. Validade da Proposta", new RtfFont("Arial", 12, RtfFont.BOLD)));
                document.add(new Paragraph(jTextFieldValidadeproposta.getText()));

                document.add(new Paragraph(" "));

                Paragraph atenc = new Paragraph("Atenciosamente,",new Font(Font.UNDEFINED,10,Font.BOLD, Color.black));
                atenc.setAlignment(Element.ALIGN_CENTER);
                
                document.add(atenc);
                
                Paragraph liraenobre = new Paragraph("Lira & Nobre Artefatos de Madeira.",new Font(Font.UNDEFINED,10,Font.BOLD, new Color(0,121,76)));
                liraenobre.setAlignment(Element.ALIGN_CENTER);
                document.add(liraenobre);
                //document.open();\n          Lira & Nobre Artefatos de Madeira.
            //                //Image image = null;          .setAlignment(Element.ALIGN_CENTER)
            //try {
            //
            //
            //    Image img = Image.getInstance("d:\\java\\cabeçalhoLN.jpg");
            //    img.setAlignment(Element.ALIGN_CENTER);
            //    document.add(img);
            //
            //} catch (IOException e) {
            //}
            //try {
            //
            //
            //    Image img = Image.getInstance("d:\\java\\cabeçalhoLN.jpg");
            //    img.setAlignment(Element.ALIGN_CENTER);
            //    document.add(img);
            //
            //} catch (IOException e) {
            //}
            //        try {
            //
            //
            //    Image img = Image.getInstance("d:\\java\\cabeçalhoLN.jpg");
            //    img.setAlignment(Element.ALIGN_CENTER);
            //    document.add(img);
            //
            //} catch (IOException e) {
            //}
            document.close();
            //}catch(Exception ex){
            //System.out.println("Erro = "+ ex);
            //        document.close();
            //}
            
                Process p;
            try {
                p = Runtime.getRuntime().exec("cmd.exe /C z:\\"+ jTextFieldCliente.getText().replaceAll(" ", "_") + "\\"  + jTextFieldSolicitante.getText().replaceAll(" ", "_") + "\\"  + jTextFieldNumOrcamento.getText() + "_" + jTextFieldCliente.getText().replace(" ", "_") + ".pdf");
                System.out.println("Abrindo Arquivo PDF   z:\\"+ jTextFieldCliente.getText().replaceAll(" ", "_") + "\\"  + jTextFieldSolicitante.getText().replaceAll(" ", "_") + "\\"+ jTextFieldNumOrcamento.getText() + "_" + jTextFieldCliente.getText().replace(" ", "_") + ".pdf");
            } catch (IOException ex1) {
                Logger.getLogger(TelaOrcamentos.class.getName()).log(Level.SEVERE, null, ex1);
            }
                
                //document.close();
            
            } catch (DocumentException ex) {
                Logger.getLogger(TelaOrcamentos.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_jButtonVisualizarActionPerformed


    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        LimpaCampos();
        jTextFieldNumOrcamento.setText("");
        jTextFieldData.setText("");
        System.out.println("Abrindo Tela Orçamentos... - Opened");

       


    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        LimpaCampos();
        Campos(false);
        jTextFieldNumOrcamento.setText("");
        jTextFieldData.setText("");
        jRadioButtonAP.setSelected(false);
        jRadioButtonAG.setSelected(false);
        jRadioButtonM.setSelected(false);
        jButtonSalvar.setEnabled(false);
        jButtonAlterar.setEnabled(false);
        jButtonCopy.setEnabled(false);
        jButtonMaisLinha.setEnabled(false);
        jButtonMenosLinha.setEnabled(false);
        jButtonDeletar.setEnabled(false);
        jButtonVisualizar.setEnabled(false);
        jListOrcamento.setSelectedIndex(-1);
        jTextFieldCliente.requestFocus();
        System.out.println("Fechando Tela Orçamentos... - Closed");

        Transaction transacao = null;
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        try{
            transacao = sessao.beginTransaction();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Erro: "+ ex+"\n Causa: "+ ex.getCause(), "Atenção", JOptionPane.INFORMATION_MESSAGE );
        }

        Query consultaClientes = sessao.createQuery(" from Clientes c order by c.razaosocial, c.codcliente  ");
        List clientesList = consultaClientes.list();
        System.out.println("foram encontradas "+clientesList.size()+" clientes");
        Iterator c =clientesList.iterator();

        jComboBoxClientes.removeAllItems();
        jComboBoxClientes.addItem("SELECIONE O CLIENTE");
            while (c.hasNext()) {
               Clientes cliList = (Clientes) c.next();
               System.out.println(cliList.getRazaosocial());
                //System.out.println("NEXT"+cliList.getCodclientes());

               
               jComboBoxClientes.addItem(cliList.getCodcliente()+" - "+ cliList.getRazaosocial());
               jComboBoxClientes.updateUI();

            }
        transacao.commit();
        sessao.close();

        SimpleDateFormat sfd2 = new SimpleDateFormat("dd/MM/yyyy");
        String data = sfd2.format(new Date());
        jComboBoxDataInicial.setSelectedItem(data);
        jComboBoxDataFinal.setSelectedItem(data);
        jLabelNOrcamentos.setText("Nº Orçamento:");

         vetorNOrcamento.clear();
         jListOrcamento.updateUI();

         if(jButtonNovo.getText().equals("Cancelar")){
           jButtonNovo.setText("Novo");
           jButtonSalvar.setEnabled(false);
        }
      
    }//GEN-LAST:event_formInternalFrameClosed

    private void jButtonCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCopyActionPerformed
        Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.beginTransaction();

            SQLQuery query = sessao.createSQLQuery("select nextval('seq_orcamentos')");
            Object object = query.uniqueResult();
            System.out.println("NEXTVAL NOVO "+( (java.math.BigInteger)object ).longValue());

            jTextFieldNumOrcamento.setText(((java.math.BigInteger)object ).toString());
            jButtonSalvar.setEnabled(true);
            jButtonAlterar.setEnabled(false);
            jButtonDeletar.setEnabled(false);
            jButtonNovo.setEnabled(false);

            if(jButtonAlterar.getText().equals("Atualizar")){
                jButtonAlterar.setText("Alterar");
                //Campos(false);
            }
            jListOrcamento.setEnabled(true);
            jListOrcamento.updateUI();

            sessao.close();
    }//GEN-LAST:event_jButtonCopyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Retorno;
    private javax.swing.ButtonGroup buttonGroupPesquisa;
    private javax.swing.ButtonGroup buttonGroupStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCopy;
    private javax.swing.JButton jButtonDeletar;
    private javax.swing.JButton jButtonMaisLinha;
    private javax.swing.JButton jButtonMenosLinha;
    private javax.swing.JButton jButtonNovo;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JButton jButtonVisualizar;
    private javax.swing.JCheckBox jCheckBoxAG;
    private javax.swing.JCheckBox jCheckBoxAP;
    private javax.swing.JCheckBox jCheckBoxM;
    private javax.swing.JComboBox jComboBoxClientes;
    private javax.swing.JComboBox jComboBoxDataFinal;
    private javax.swing.JComboBox jComboBoxDataInicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelDF;
    private javax.swing.JLabel jLabelDI;
    private javax.swing.JLabel jLabelNOrcamento;
    private javax.swing.JLabel jLabelNOrcamentos;
    private javax.swing.JLabel jLabelTotalGeral;
    private javax.swing.JList jListOrcamento;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelDados;
    private javax.swing.JRadioButton jRadioButtonAG;
    private javax.swing.JRadioButton jRadioButtonAP;
    private javax.swing.JRadioButton jRadioButtonM;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableMaterial;
    private javax.swing.JTextArea jTextAreaDescricao;
    private javax.swing.JTextField jTextFieldAC;
    private javax.swing.JTextField jTextFieldCliente;
    private javax.swing.JTextField jTextFieldCondicaopagamento;
    private javax.swing.JTextField jTextFieldData;
    private javax.swing.JTextField jTextFieldLocal;
    private javax.swing.JTextField jTextFieldNOrcamentoBusca;
    private javax.swing.JTextField jTextFieldNumOrcamento;
    private javax.swing.JTextField jTextFieldObs;
    private javax.swing.JTextField jTextFieldPedidoN;
    private javax.swing.JTextField jTextFieldPrazoentrega;
    private javax.swing.JTextField jTextFieldReferente;
    private javax.swing.JTextField jTextFieldSolicitante;
    private javax.swing.JTextField jTextFieldUnidade;
    private javax.swing.JTextField jTextFieldValidadeproposta;
    // End of variables declaration//GEN-END:variables

}
