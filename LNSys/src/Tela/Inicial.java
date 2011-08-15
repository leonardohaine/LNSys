/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Inicial.java
 *
 * Created on 18/12/2010, 19:22:37
 */

package Tela;


import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.hibernate.cfg.Configuration;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Leo
 */
public class Inicial extends javax.swing.JFrame {


    Inicial instancia = this;
    TelaOrcamentos orcamentos;
    CadClientes cadclientes;
    CadMateriais cadmateriais;
    SaidaMaterial saidaMaterial;
    CadFornecedores cadFornecedores;
    CadUsuarios cadUsuario;
    Saida saida;
    Caixa caixa;
    RelatorioHistoricoEntradaSaida relatorioHistoricoEntradaSaida;
    Backup backup;
    public static String DATABASE = null;
    public static String IP = null;
    public static String USUARIO = null;
    public static String SENHA = null;
    public static String USER = null;
    public static String LOGIN = null;
    private static Properties config = new Properties();
    public static String ini = "/windows/LNSys.ini";
    private LayoutManager layout;
    /** Creates new form Inicial */
    public Inicial() {
        initComponents();

        //layout = new FlowLayout(FlowLayout.CENTER);

                // obter dimensões do pai
        int larguraPai   = this.getWidth();
        int alturaPai    = this.getHeight();
        // obter dimensões do filho
        int larguraFilho = jPanelBotoes.getWidth();
        int alturaFilho  = jPanelBotoes.getWidth();
        // calcular novas coordenadas do filho
        int novoX        = (larguraPai - larguraFilho) / 2;
        int novoY        = (alturaPai - alturaFilho) / 2;
        // centralizar filho
        //jPanelBotoes.setLocation(novoX, novoY);

        try {
            //jButtonBackup.setVisible(false);
            //        jButtonClientes.setVisible(false);
            //        jButtonEntradaSaida.setVisible(false);
            //        jButtonFornecedores.setVisible(false);
            //        jButtonMateriais.setVisible(false);
            //        jButtonNovoOrcamento.setVisible(false);
            //        jButtonRelatorioHistorico.setVisible(false);
            //        jButtonUsuarios.setVisible(false);
            //try {
            jComboBoxSkin.setVisible(true);
            iniciaLogin();

//            jPanelBotoes.setLayout(layout);
//                        Dimension centraliza = Toolkit.getDefaultToolkit().getScreenSize();
//                        jPanelBotoes.setLocation((centraliza.width-instancia.getSize().width)/2,
//                                  (centraliza.height-instancia.getSize().height)/2);
            // testando de novo
            //} catch (InterruptedException ex) {
            //    Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
            //}
            //            Dimension centraliza = Toolkit.getDefaultToolkit().getScreenSize();
            //            jButtonNovoOrcamento.setLocation((centraliza.width-instancia.getSize().width)/2,
            //                      (centraliza.height-instancia.getSize().height)/2);
            // 
        } catch (InterruptedException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        }
            //            Dimension centraliza = Toolkit.getDefaultToolkit().getScreenSize();
            //            jButtonNovoOrcamento.setLocation((centraliza.width-instancia.getSize().width)/2,
            //                      (centraliza.height-instancia.getSize().height)/2);
            // testando de novo
        //} catch (InterruptedException ex) {
        //    Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        //}
//            Dimension centraliza = Toolkit.getDefaultToolkit().getScreenSize();
//            jButtonNovoOrcamento.setLocation((centraliza.width-instancia.getSize().width)/2,
//                      (centraliza.height-instancia.getSize().height)/2);
// testando de novo

            jDesktopPaneInicial.setBackground(Color.black);
//Sky Yellow, Sky Red, Sky Pink, Sky Krupp, Sky Green, Sky Bluer, Sky Blue, Silver,
//Light Gray, Experience Royale, Experience Green, Experience Blue, 
//Desert Yellow, Desert Red, Desert Green, Desert Bluer, Desert Blue, 
//Dark Star e Brown Sugar.

//            PlasticLookAndFeel.setPlasticTheme(new DarkStar());
//            try {
//                UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
//            } catch (UnsupportedLookAndFeelException ex) {
//                ex.printStackTrace();
//            }

        try{
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.basic.BasicLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.mac.MacLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.oyoaha.OyoahaLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        }catch (Exception e){
                   JOptionPane.showMessageDialog(null,
                   "Não foi possível carregar o \"Skin\" padrão. Definindo o padrão original.", "Erro",
                   JOptionPane.ERROR_MESSAGE
                        );
        }

        setIconImage(getToolkit().getImage(this.getClass().getClassLoader().getResource("imagens/IconeLN.png")));
       
        
        //this.setSize(1024, 740);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        //jPanelBotoes.setLocation(novoX, novoY);
        //this.setResizable(false);
        

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPaneInicial = new javax.swing.JDesktopPane();
        jPanelBotoes = new javax.swing.JPanel();
        jButtonEntradaSaida = new javax.swing.JButton();
        jButtonClientes = new javax.swing.JButton();
        jButtonNovoOrcamento = new javax.swing.JButton();
        jButtonRelatorioHistorico = new javax.swing.JButton();
        jButtonUsuarios = new javax.swing.JButton();
        jButtonMateriais = new javax.swing.JButton();
        jButtonBackup = new javax.swing.JButton();
        jButtonFornecedores = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jComboBoxSkin = new javax.swing.JComboBox();
        jButtonCaixa = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LIRA & NOBRE ARTEFATOS DE MADEIRA - SOLUÇÕES EM MÓVEIS SOB MEDIDA");

        jDesktopPaneInicial.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPaneInicial.setToolTipText("");

        jPanelBotoes.setBackground(new java.awt.Color(0, 0, 0));

        jButtonEntradaSaida.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButtonEntradaSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/equipment.png"))); // NOI18N
        jButtonEntradaSaida.setMnemonic('M');
        jButtonEntradaSaida.setText("ENTRADA / SAÍDA");
        jButtonEntradaSaida.setBorderPainted(false);
        jButtonEntradaSaida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonEntradaSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEntradaSaidaActionPerformed(evt);
            }
        });

        jButtonClientes.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButtonClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/users.png"))); // NOI18N
        jButtonClientes.setMnemonic('M');
        jButtonClientes.setText("CLIENTES");
        jButtonClientes.setBorderPainted(false);
        jButtonClientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClientesActionPerformed(evt);
            }
        });

        jButtonNovoOrcamento.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButtonNovoOrcamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Docs.png"))); // NOI18N
        jButtonNovoOrcamento.setMnemonic('N');
        jButtonNovoOrcamento.setText("ORÇAMENTO");
        jButtonNovoOrcamento.setBorderPainted(false);
        jButtonNovoOrcamento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonNovoOrcamento.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButtonNovoOrcamento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonNovoOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoOrcamentoActionPerformed(evt);
            }
        });

        jButtonRelatorioHistorico.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButtonRelatorioHistorico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/report.png"))); // NOI18N
        jButtonRelatorioHistorico.setMnemonic('M');
        jButtonRelatorioHistorico.setText("RELATÓRIO  E/S");
        jButtonRelatorioHistorico.setActionCommand("RELATÓRIO\\n\n HISTÓRICO DE  \n ENTRADA E SAÍDA");
        jButtonRelatorioHistorico.setBorderPainted(false);
        jButtonRelatorioHistorico.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonRelatorioHistorico.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonRelatorioHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRelatorioHistoricoActionPerformed(evt);
            }
        });

        jButtonUsuarios.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButtonUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/people.png"))); // NOI18N
        jButtonUsuarios.setMnemonic('M');
        jButtonUsuarios.setText("USUÁRIOS");
        jButtonUsuarios.setBorderPainted(false);
        jButtonUsuarios.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonUsuarios.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUsuariosActionPerformed(evt);
            }
        });

        jButtonMateriais.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButtonMateriais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/settings96.png"))); // NOI18N
        jButtonMateriais.setMnemonic('C');
        jButtonMateriais.setText("MATERIAIS");
        jButtonMateriais.setBorderPainted(false);
        jButtonMateriais.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonMateriais.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonMateriais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMateriaisActionPerformed(evt);
            }
        });

        jButtonBackup.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButtonBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/data.png"))); // NOI18N
        jButtonBackup.setMnemonic('C');
        jButtonBackup.setText("BACKUP");
        jButtonBackup.setBorderPainted(false);
        jButtonBackup.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonBackup.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackupActionPerformed(evt);
            }
        });

        jButtonFornecedores.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButtonFornecedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fornecedor.png"))); // NOI18N
        jButtonFornecedores.setMnemonic('M');
        jButtonFornecedores.setText("FORNECEDORES");
        jButtonFornecedores.setBorderPainted(false);
        jButtonFornecedores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonFornecedores.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonFornecedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFornecedoresActionPerformed(evt);
            }
        });

        jButtonSair.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButtonSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/exit96.png"))); // NOI18N
        jButtonSair.setMnemonic('M');
        jButtonSair.setText("SAIR");
        jButtonSair.setBorderPainted(false);
        jButtonSair.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSair.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });

        jComboBoxSkin.setMaximumRowCount(30);
        jComboBoxSkin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecionar Skin", "Windows Classic", "Windows", "Motif", "Metal", "Nimbus", "Brown Sugar", "Dark Star", "Desert Blue", "Desert Bluer", "Desert Green", "Desert Red", "Desert Yellow", "Experience Blue ", "Experience Green", "Experience Royale", "Light Gray", "Silver", "Sky Blue", "Sky Bluer", "Sky Green", "Sky Krupp", "Sky Pink", "Sky Red", "Sky Yellow" }));
        jComboBoxSkin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxSkinItemStateChanged(evt);
            }
        });

        jButtonCaixa.setText("CAIXA");
        jButtonCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCaixaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelBotoesLayout = new javax.swing.GroupLayout(jPanelBotoes);
        jPanelBotoes.setLayout(jPanelBotoesLayout);
        jPanelBotoesLayout.setHorizontalGroup(
            jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBotoesLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jComboBoxSkin, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jButtonCaixa))
                    .addGroup(jPanelBotoesLayout.createSequentialGroup()
                        .addGroup(jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonNovoOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonEntradaSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelBotoesLayout.createSequentialGroup()
                                .addGroup(jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonMateriais, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonFornecedores, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelBotoesLayout.createSequentialGroup()
                                .addComponent(jButtonRelatorioHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonSair, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanelBotoesLayout.setVerticalGroup(
            jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBotoesLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonMateriais, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNovoOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelBotoesLayout.createSequentialGroup()
                        .addGroup(jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonRelatorioHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonEntradaSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelBotoesLayout.createSequentialGroup()
                        .addComponent(jButtonFornecedores, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSair, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBotoesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(jComboBoxSkin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanelBotoesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCaixa)
                        .addContainerGap())))
        );

        jPanelBotoes.setBounds(60, 70, 660, 570);
        jDesktopPaneInicial.add(jPanelBotoes, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPaneInicial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPaneInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNovoOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoOrcamentoActionPerformed

        orcamentos = null;

       try {

            orcamentos = TelaOrcamentos.getInstance(instancia);
            if (!orcamentos.isVisible()) {
                orcamentos.setVisible(true);
                jDesktopPaneInicial.add(orcamentos, javax.swing.JLayeredPane.POPUP_LAYER);
                orcamentos.show();
                orcamentos.toFront();
                orcamentos.setSelected(true);
                //telaVendas.setLocation(20, 10);

            }
            orcamentos.setSelected(true);
        } catch (SQLException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButtonNovoOrcamentoActionPerformed

    private void jButtonClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClientesActionPerformed
  cadclientes = null;

    try {

            cadclientes = CadClientes.getInstance(instancia);
            if (!cadclientes.isVisible()) {
                cadclientes.setVisible(true);
                jDesktopPaneInicial.add(cadclientes, javax.swing.JLayeredPane.POPUP_LAYER);
                cadclientes.show();
                cadclientes.toFront();
                cadclientes.setSelected(true);
                //telaVendas.setLocation(20, 10);

            }
            cadclientes.setSelected(true);
        } catch (SQLException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonClientesActionPerformed

    private void jButtonMateriaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMateriaisActionPerformed
        cadmateriais = null;

    try {

            cadmateriais = CadMateriais.getInstance(instancia);
            if (!cadmateriais.isVisible()) {
                cadmateriais.setVisible(true);
                jDesktopPaneInicial.add(cadmateriais, javax.swing.JLayeredPane.POPUP_LAYER);
                cadmateriais.show();
                cadmateriais.toFront();
                cadmateriais.setSelected(true);
                //telaVendas.setLocation(20, 10);

            }
            cadmateriais.setSelected(true);
        } catch (SQLException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonMateriaisActionPerformed

    private void jButtonEntradaSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEntradaSaidaActionPerformed
        saidaMaterial = null;

    try {

            saidaMaterial = SaidaMaterial.getInstance(instancia);
            if (!saidaMaterial.isVisible()) {
                saidaMaterial.setVisible(true);
                jDesktopPaneInicial.add(saidaMaterial, javax.swing.JLayeredPane.POPUP_LAYER);
                saidaMaterial.show();
                saidaMaterial.toFront();
                saidaMaterial.setSelected(true);
                //telaVendas.setLocation(20, 10);

            }
            saidaMaterial.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonEntradaSaidaActionPerformed

    private void jButtonBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackupActionPerformed
        backup = null;

    try {

            backup = Backup.getInstance(instancia);
            if (!backup.isVisible()) {
                backup.setVisible(true);
                jDesktopPaneInicial.add(backup, javax.swing.JLayeredPane.POPUP_LAYER);
                backup.show();
                backup.toFront();
                backup.setSelected(true);
                //telaVendas.setLocation(20, 10);

            }
            backup.setSelected(true);
        } catch (SQLException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonBackupActionPerformed

    private void jButtonFornecedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFornecedoresActionPerformed
         cadFornecedores = null;

    try {

            cadFornecedores = CadFornecedores.getInstance(instancia);
            if (!cadFornecedores.isVisible()) {
                cadFornecedores.setVisible(true);
                jDesktopPaneInicial.add(cadFornecedores, javax.swing.JLayeredPane.POPUP_LAYER);
                cadFornecedores.show();
                cadFornecedores.toFront();
                cadFornecedores.setSelected(true);
                //telaVendas.setLocation(20, 10);

            }
            cadFornecedores.setSelected(true);
        } catch (SQLException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonFornecedoresActionPerformed

    private void jButtonRelatorioHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRelatorioHistoricoActionPerformed


        relatorioHistoricoEntradaSaida = null;

    try {

            relatorioHistoricoEntradaSaida = RelatorioHistoricoEntradaSaida.getInstance(instancia);
            if (!relatorioHistoricoEntradaSaida.isVisible()) {
                relatorioHistoricoEntradaSaida.setVisible(true);
                jDesktopPaneInicial.add(relatorioHistoricoEntradaSaida, javax.swing.JLayeredPane.POPUP_LAYER);
                relatorioHistoricoEntradaSaida.show();
                relatorioHistoricoEntradaSaida.toFront();
                relatorioHistoricoEntradaSaida.setSelected(true);
                //telaVendas.setLocation(20, 10);

            }
            relatorioHistoricoEntradaSaida.setSelected(true);
        } catch (SQLException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonRelatorioHistoricoActionPerformed

    private void jButtonUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUsuariosActionPerformed

        cadUsuario = null;

    try {

            cadUsuario = CadUsuarios.getInstance(instancia);
            if (!cadUsuario.isVisible()) {
                cadUsuario.setVisible(true);
                jDesktopPaneInicial.add(cadUsuario, javax.swing.JLayeredPane.POPUP_LAYER);
                cadUsuario.show();
                cadUsuario.toFront();
                cadUsuario.setSelected(true);
                //telaVendas.setLocation(20, 10);

            }
            cadUsuario.setSelected(true);
        } catch (SQLException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButtonUsuariosActionPerformed

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButtonSairActionPerformed



    private void jComboBoxSkinItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxSkinItemStateChanged
//Sky Yellow, Sky Red, Sky Pink, Sky Krupp, Sky Green, Sky Bluer, Sky Blue, Silver,
//Light Gray, Experience Royale, Experience Green, Experience Blue, 
//Desert Yellow, Desert Red, Desert Green, Desert Bluer, Desert Blue, 
//Dark Star e Brown Sugar.

        if ((evt.getStateChange() == 1)) {
          
            if(jComboBoxSkin.getSelectedItem().equals("Dark Star")){

                PlasticLookAndFeel.setPlasticTheme(new DarkStar());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Brown Sugar")) {
                PlasticLookAndFeel.setPlasticTheme(new BrownSugar());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Desert Blue")) {
                PlasticLookAndFeel.setPlasticTheme(new DesertBlue());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Desert Bluer")) {
                PlasticLookAndFeel.setPlasticTheme(new DesertBluer());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Desert Green")) {
                PlasticLookAndFeel.setPlasticTheme(new DesertGreen());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Desert Red")) {
                PlasticLookAndFeel.setPlasticTheme(new DesertRed());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Desert Yellow")) {
                PlasticLookAndFeel.setPlasticTheme(new DesertYellow());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Experience Blue")) {
                PlasticLookAndFeel.setPlasticTheme(new ExperienceBlue());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Experience Green")) {
                PlasticLookAndFeel.setPlasticTheme(new ExperienceGreen());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Experience Royale")) {
                PlasticLookAndFeel.setPlasticTheme(new ExperienceRoyale());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Light Gray")) {
                PlasticLookAndFeel.setPlasticTheme(new LightGray());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Silver")) {
                PlasticLookAndFeel.setPlasticTheme(new Silver());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Sky Blue")) {
                PlasticLookAndFeel.setPlasticTheme(new SkyBlue());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Sky Bluer")) {
                PlasticLookAndFeel.setPlasticTheme(new SkyBluer());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Sky Green")) {
                PlasticLookAndFeel.setPlasticTheme(new SkyGreen());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Sky Krupp")) {
                PlasticLookAndFeel.setPlasticTheme(new SkyKrupp());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Sky Pink")) {
                PlasticLookAndFeel.setPlasticTheme(new SkyPink());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Sky Red")) {
                PlasticLookAndFeel.setPlasticTheme(new SkyRed());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Sky Yellow")) {
                PlasticLookAndFeel.setPlasticTheme(new SkyYellow());
                try{
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(this);
                    new Inicial().pack();
                }catch (UnsupportedLookAndFeelException ex) {
                    JOptionPane.showMessageDialog(null, "Erro Look And Feel:" + ex);
                }
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Metal")) {
               mudaLookAndFeel(0);
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Nimbus")) {
                mudaLookAndFeel(1);
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Motif")) {
                mudaLookAndFeel(2);
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Windows")) {
                mudaLookAndFeel(3);
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }else if (jComboBoxSkin.getSelectedItem().equals("Windows Classic")) {
                mudaLookAndFeel(4);
                //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
            }
            //JOptionPane.showMessageDialog(null,"Skin Alterado para " + (String) jComboBoxSkin.getSelectedItem());
        }
    }//GEN-LAST:event_jComboBoxSkinItemStateChanged

    private void jButtonCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCaixaActionPerformed

        caixa = null;
        try {
            caixa = Caixa.getInstance(instancia);

            if (!caixa.isVisible()) {
                caixa.setVisible(true);
                jDesktopPaneInicial.add(caixa, javax.swing.JLayeredPane.POPUP_LAYER);
                caixa.show();
                caixa.toFront();
                caixa.setSelected(true);
                //telaVendas.setLocation(20, 10);

            }
            caixa.setSelected(true);
       } catch (PropertyVetoException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonCaixaActionPerformed
private javax.swing.UIManager.LookAndFeelInfo looks[];


    public void mudaLookAndFeel(int index) {
        looks = javax.swing.UIManager.getInstalledLookAndFeels();
        try {
            javax.swing.UIManager.setLookAndFeel( looks[index].getClassName());
            System.out.println("Look "+ looks[index].getName());
            javax.swing.SwingUtilities.updateComponentTreeUI( this );
        } catch (Exception e) {
        }
    }



    public static String unidade = System.getProperty("user.home").substring( 0 ,2 );

    public boolean isNumeric (String codigo) {
    try {
        Long.parseLong (codigo);
        System.out.println(codigo);
        return true;
    } catch (NumberFormatException ex) {
        return false;
    }
}

    public static void criaIni() throws FileNotFoundException, IOException{
       File file = new File(unidade+ini);
        if(!file.exists()){
            System.out.println("NAO TEM TXT ");
            File newFile = new File(unidade+ini);
            newFile.createNewFile();
            System.out.println("CRIANDOOOO ");

        }else{
            System.out.println("JAH TEM TXT LER");
        }
        //return criar;
    }


    public void CheckKey() throws Exception{

        try {

            //System.out.println("METODO LER "+LerKey());
            //if(LerKey()==Registro.keyGeneration()){

            if(LerKey()){
                System.out.println("Configuração Ok.");
            }else{
                System.out.println("Autenticaçao - Falhou.");
                new Registro(new javax.swing.JFrame(), true).setVisible(true);
            }
//            if(Registro.keyGeneration().equals(LerKey())){
//                System.out.println("Autenticaçao - Ok.");
//                jMenuIniciar1.setEnabled(true);
//            }else if(LerKey() == null){
//                System.out.println("Autenticaçao - Falhou.");
//                jMenuIniciar1.setEnabled(false);
//                new Registro(new javax.swing.JFrame(), true).setVisible(true);
//            }
        } catch (FileNotFoundException ex) {
            criaIni();
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Inicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static boolean LerKey() throws FileNotFoundException, IOException, Exception{
        criaIni();
        boolean ok = false;
        BASE64Decoder decoder = new BASE64Decoder();
        config.load(new FileInputStream(unidade+ini));
        System.out.println("Iniciando processo de leitura de configurações: ");
       

        String database = config.getProperty("DATABASE");
        String ip = config.getProperty("IP");
        String usuario = config.getProperty("USUARIO");
        String senha = config.getProperty("SENHA");
        if(!config.containsKey("LOGIN")){


            FileWriter leitor = new FileWriter(unidade+ini, true);
            leitor.write("LOGIN="+ JOptionPane.showInputDialog("Tela de Login", "S"));
            leitor.flush();
            leitor.close();
            System.out.println("ADD Tag LOGIN");
        }
        String login = config.getProperty("LOGIN");

        System.out.println();
        System.out.println(config.getProperty("DATABASE"));
        System.out.println(config.getProperty("IP"));
        System.out.println(config.getProperty("USUARIO"));
        System.out.println(config.getProperty("SENHA"));
        System.out.println(config.getProperty("LOGIN"));
        System.out.println();

            //if(database.equals("null") || database == null || ip.equals("null") || ip == null || usuario.equals("null") || usuario == null || senha.equals("null") || senha == null){
        if(ip == null){
            JOptionPane.showMessageDialog(null,
               "O arquivo de configuração está em branco ou faltando parâmetros.", "Erro",
               JOptionPane.ERROR_MESSAGE);
               
            return ok = false;
        }else{
            DATABASE = database;
            IP = ip;
            USUARIO = new String(decoder.decodeBuffer(usuario));
            SENHA = new String(decoder.decodeBuffer(senha));
            LOGIN = login;

            Configuration configuration = new Configuration();
//                configuration.configure("hibernate.cfg.xml");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://"+IP+"/"+ DATABASE);
            configuration.setProperty("hibernate.connection.username", USUARIO);
            configuration.setProperty("hibernate.connection.password", SENHA);
            configuration.mergeProperties(config);
            configuration.getProperties();

            System.out.println("DATABASE: "+ DATABASE+"\nIP: "+IP+"\nUser: "+ USUARIO+"\nSENHA: "+senha);
            System.out.println("URL: "+ configuration.getProperty("hibernate.connection.url"));
            System.out.println("Finalizando leitura!");
            return  ok = true;
        }

}


    private void iniciaLogin() throws InterruptedException{
    new Thread(){//instancia nova thread já implementando o método run()
        @Override
        public void run() {//sobrescreve o método run()

            while(0 == 0){//while para fazer o loop infinito
                  if(Login.clientes.equals("S")){jButtonClientes.setVisible(true);
                    }else{jButtonClientes.setVisible(false); }

                  if(Login.produtos.equals("S")){jButtonMateriais.setVisible(true);
                        }else{jButtonMateriais.setVisible(false); }

                  if(Login.fornecedores.equals("S")){jButtonFornecedores.setVisible(true);
                        }else{jButtonFornecedores.setVisible(false); }

                  if(Login.relatorio.equals("S")){jButtonRelatorioHistorico.setVisible(true);
                        }else{jButtonRelatorioHistorico.setVisible(false); }

                  if(Login.telaUsuarios.equals("S")){jButtonUsuarios.setVisible(true);
                        }else{jButtonUsuarios.setVisible(false); }

                  if(Login.orcamentos.equals("S")){jButtonNovoOrcamento.setVisible(true);
                        }else{jButtonNovoOrcamento.setVisible(false); }

                  if(Login.entradaSaida.equals("S")){jButtonEntradaSaida.setVisible(true);
                        }else{jButtonEntradaSaida.setVisible(false); }

                  //System.out.println("Verificando Usuarios");
                  try{
                    sleep(1000);
                    //faz a thread entrar em estado de espera por 1000 milissegundos ou 1 segundo
                  }catch (Exception e){}
            }
        
    }

    }.start();//inicia a thread.
    //label.setText(horaString+":"+minString+":"+segundoString);//seta hora atual no label

}

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) throws Exception {

        new Inicial().CheckKey();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicial().setVisible(true);
                if(LOGIN == null ? "S" == null : LOGIN.equals("S")){
                    new Login().setModal(true);
                    new Login().setVisible(true);
                }
            }
        });
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBackup;
    private javax.swing.JButton jButtonCaixa;
    private javax.swing.JButton jButtonClientes;
    private javax.swing.JButton jButtonEntradaSaida;
    private javax.swing.JButton jButtonFornecedores;
    private javax.swing.JButton jButtonMateriais;
    private javax.swing.JButton jButtonNovoOrcamento;
    private javax.swing.JButton jButtonRelatorioHistorico;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JButton jButtonUsuarios;
    private javax.swing.JComboBox jComboBoxSkin;
    private javax.swing.JDesktopPane jDesktopPaneInicial;
    private javax.swing.JPanel jPanelBotoes;
    // End of variables declaration//GEN-END:variables

}
