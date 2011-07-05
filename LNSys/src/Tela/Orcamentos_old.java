/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Orcamentos_old.java
 *
 * Created on 18/12/2010, 22:59:59
 */

package Tela;

import Util.CalendarComboBox;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Leo
 */
public class Orcamentos_old extends javax.swing.JInternalFrame {

    private static Orcamentos_old orcamentos;
    private Inicial inicial = null;

    /** Creates new form Orcamentos_old */
    public Orcamentos_old(Inicial instancia) {
        initComponents();

         mostraTabelaVazia();
       // jTableMaterial.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //ImageIcon icone = new ImageIcon(this.getClass().getClassLoader().getResource("imagens/iconeLN.png"));
        //setIconImage(icone.getImage());

        setFrameIcon(new ImageIcon(this.getClass().getClassLoader().getResource("imagens/IconeLN.png")));

        Dimension centraliza = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((centraliza.width-this.getSize().width)/2,
                      (centraliza.height-this.getSize().height)/2);
    }

    public static Orcamentos_old getInstance(Inicial instancia) throws PropertyVetoException, SQLException, ClassNotFoundException {
        if(orcamentos == null)
            orcamentos = new Orcamentos_old(instancia);
        return orcamentos;
    }


    public void montaTabela(){

        System.out.println("Montando Tabela");

       // jTableMaterial.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        //int row = jTableMaterial.getRowCount();
        //centralizado.setBackground(((row%2==0) ? Color.LIGHT_GRAY : Color.WHITE));
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        jTableMaterial.getColumnModel().getColumn(0).setCellRenderer(direita);
        jTableMaterial.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        jTableMaterial.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        jTableMaterial.getColumnModel().getColumn(3).setCellRenderer(direita);
        jTableMaterial.getColumnModel().getColumn(4).setCellRenderer(direita);
        //jTableMaterial.getColumnModel().getColumn(5).setCellRenderer(direita);
        //jTableMaterial.getColumnModel().getColumn(6).setCellRenderer(direita);
        //jTableMaterial.getColumnModel().getColumn(7).setCellRenderer(esquerda);
        //jTableMaterial.getColumnModel().getColumn(8).setCellRenderer(centralizado);
        //jTableMaterial.getColumnModel().getColumn(9).setCellRenderer(centralizado);
        System.out.println("Ajustando colunas");
        jTableMaterial.getColumnModel().getColumn(0).setPreferredWidth(80);//QUANT
        jTableMaterial.getColumnModel().getColumn(0).setMinWidth(80);
        jTableMaterial.getColumnModel().getColumn(1).setPreferredWidth(60);//UNIDADE
        jTableMaterial.getColumnModel().getColumn(1).setMinWidth(60);
        jTableMaterial.getColumnModel().getColumn(2).setPreferredWidth(195);//PROD
        jTableMaterial.getColumnModel().getColumn(2).setMinWidth(192);
        jTableMaterial.getColumnModel().getColumn(3).setPreferredWidth(100);//V UNIT
        jTableMaterial.getColumnModel().getColumn(3).setMinWidth(100);
        jTableMaterial.getColumnModel().getColumn(4).setPreferredWidth(80);//VALOR TOTAL
        jTableMaterial.getColumnModel().getColumn(4).setMinWidth(80);
       // jTableMaterial.getColumnModel().getColumn(5).setPreferredWidth(60);//PROMOCAO
        //jTableResultado.getColumnModel().getColumn(6).setPreferredWidth(60);//DINHEIRO
        //jTableMaterial.getColumnModel().getColumn(6).setPreferredWidth(45);//QTD
        //jTableMaterial.getColumnModel().getColumn(7).setPreferredWidth(70);//LOCAL
        //jTableMaterial.getColumnModel().getColumn(8).setPreferredWidth(0);
        //jTableMaterial.getColumnModel().getColumn(8).setMinWidth(0);
        //jTableMaterial.getColumnModel().getColumn(8).setMaxWidth(0);
        //jTableMaterial.getColumnModel().getColumn(9).setPreferredWidth(0);
        //jTableMaterial.getColumnModel().getColumn(9).setMinWidth(0);
        //jTableMaterial.getColumnModel().getColumn(9).setMaxWidth(0);

    }


    private void mostraTabelaVazia(){
           //montaTabela();

            jTableMaterial.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][][] {
                    {null, null, null, null},
        },
                new String [] {
            "QUANTIDADE", "UNIDADE", "PRODUTO", "VALOR UNITÁRIO", "VALOR TOTAL"
        }
        ));
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
        tableResultado.setRowCount(0);
        montaTabela();
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
        jCheckBoxAprovado = new javax.swing.JCheckBox();
        jLabelDI = new javax.swing.JLabel();
        jComboBoxDataInicial = new CalendarComboBox(true);
        jLabelDF = new javax.swing.JLabel();
        jComboBoxDataInicial1 = new CalendarComboBox(true);
        jCheckBoxAguardando = new javax.swing.JCheckBox();
        jLabelNOrcamento = new javax.swing.JLabel();
        jTextFieldNOrcamento = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListOrcamento = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldAC = new javax.swing.JTextField();
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
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDescricao = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableMaterial = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setClosable(true);
        setTitle("ORÇAMENTOS");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar"));

        jCheckBoxAprovado.setText("Aprovado");

        jLabelDI.setText("Data inicial:");

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

        jComboBoxDataInicial1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxDataInicial1KeyPressed(evt);
            }
        });

        jCheckBoxAguardando.setText("Aguardando");

        jLabelNOrcamento.setText("Nº Orçamento:");

        jButton1.setText("OK");
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelDI)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxAprovado))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelDF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxDataInicial1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxAguardando)))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNOrcamento)
                    .addComponent(jTextFieldNOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(217, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelDI)
                        .addComponent(jComboBoxDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBoxAprovado)
                        .addComponent(jLabelNOrcamento))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDF)
                            .addComponent(jComboBoxDataInicial1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBoxAguardando)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(6, 6, 6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jListOrcamento.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jListOrcamento);

        jLabel3.setText("Nº Orçamento:");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel1.setText("Cliente:");

        jLabel2.setText("A/C:");

        jLabel4.setText("Solicitante:");

        jLabel5.setText("Unidade:");

        jLabel6.setText("Nº Pedido:");

        jLabel7.setText("Local:");

        jLabel8.setText("Referente:");

        jLabel9.setText("N° Orçamento:");

        jScrollPane2.setAutoscrolls(true);

        jTextAreaDescricao.setColumns(20);
        jTextAreaDescricao.setRows(5);
        jScrollPane2.setViewportView(jTextAreaDescricao);

        jLabel10.setText("Observações:");

        jTableMaterial.setBackground(new java.awt.Color(51, 153, 0));
        jTableMaterial.setFont(new java.awt.Font("Arial Black", 0, 12));
        jTableMaterial.setForeground(new java.awt.Color(255, 255, 255));
        jTableMaterial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Título 1", "Título 2", "Título 3", "Título 4", "Título 5"
            }
        ));
        jTableMaterial.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableMaterial.setDoubleBuffered(true);
        jTableMaterial.setFillsViewportHeight(true);
        jTableMaterial.setGridColor(new java.awt.Color(255, 255, 255));
        jTableMaterial.setSelectionBackground(new java.awt.Color(0, 0, 0));
        jScrollPane3.setViewportView(jTableMaterial);
        jTableMaterial.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jTableMaterial.getColumnModel().getColumn(0).setResizable(false);
        jTableMaterial.getColumnModel().getColumn(2).setResizable(false);
        jTableMaterial.getColumnModel().getColumn(3).setResizable(false);
        jTableMaterial.getColumnModel().getColumn(4).setResizable(false);
        jTableMaterial.getAccessibleContext().setAccessibleParent(jScrollPane1);

        jButton2.setText("Novo");

        jButton3.setText("Salvar");

        jButton4.setText("Alterar");

        jButton5.setText("Deletar");

        jButton6.setText("Sair");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("+");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("-");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                                .addComponent(jButton6))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldReferente))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldUnidade))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldPedidoN, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldLocal, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldAC, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldSolicitante))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField1)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                        .addGap(57, 57, 57))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldAC, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldPedidoN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldReferente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap(19, Short.MAX_VALUE))
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
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxDataInicialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxDataInicialKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
            System.out.println("ENTER");
            //jButtonVisual.requestFocus();
        }
}//GEN-LAST:event_jComboBoxDataInicialKeyPressed

    private void jComboBoxDataInicial1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxDataInicial1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDataInicial1KeyPressed

    private void jComboBoxDataInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDataInicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDataInicialActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
        tableResultado.addRow(new Object[] {"teste", "teste 1", "teste 2", "teste 3", "teste 4"});

         montaTabela();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
      int[] I = jTableMaterial.getSelectedRows(); // captura as linhas selecionadas

        // pega o DefaultTableModel da tabela
        javax.swing.table.DefaultTableModel table = (javax.swing.table.DefaultTableModel)jTableMaterial.getModel();

        for(int i = (I.length-1); i>=0; --i){
            table.removeRow(I[i]); // remove todas as linhas selecionadas
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       javax.swing.table.DefaultTableModel tableResultado =(javax.swing.table.DefaultTableModel)jTableMaterial.getModel();
        int linhas = tableResultado.getRowCount();
        System.out.println("Linhas:" + linhas);

        Vector dados = new Vector();

        String var1, var2, var3, var4, var5;
        int i =0;
        for(;i < linhas; i++){

        var1 = tableResultado.getValueAt(i, 0).toString();
            //System.out.println("I1 "+i);
        var2 = tableResultado.getValueAt(i, 1).toString();
            //System.out.println("I2 "+i);
        var3 = tableResultado.getValueAt(i, 2).toString();
            //System.out.println("I3 "+i);
        var4 = tableResultado.getValueAt(i, 3).toString();
            //System.out.println("I4 "+i);
        var5 = tableResultado.getValueAt(i, 4).toString();
            System.out.println("I5 "+i);

            System.out.println("SAIDA: \nlinha: "+ (i+1) + "\n"+ var1+ " - "+ var2+ " - "+ var3+ " - "+ var4+ " - "+ var5);


        }

        //dados = tableResultado.getValueAt(1, 3);
        String teste = tableResultado.getValueAt(1, 3).toString();

        String [] str1, str2, str3, str4;
        str1 = dados.toString().split(",", 5);
        //System.out.println("Dados:" + tableResultado.getDataVector());
        //System.out.println("String Teste:"+teste);

    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBoxAguardando;
    private javax.swing.JCheckBox jCheckBoxAprovado;
    private javax.swing.JComboBox jComboBoxDataInicial;
    private javax.swing.JComboBox jComboBoxDataInicial1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JList jListOrcamento;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableMaterial;
    private javax.swing.JTextArea jTextAreaDescricao;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextFieldAC;
    private javax.swing.JTextField jTextFieldCliente;
    private javax.swing.JTextField jTextFieldLocal;
    private javax.swing.JTextField jTextFieldNOrcamento;
    private javax.swing.JTextField jTextFieldPedidoN;
    private javax.swing.JTextField jTextFieldReferente;
    private javax.swing.JTextField jTextFieldSolicitante;
    private javax.swing.JTextField jTextFieldUnidade;
    // End of variables declaration//GEN-END:variables

}
