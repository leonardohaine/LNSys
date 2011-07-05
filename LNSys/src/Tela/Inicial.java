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
import com.jgoodies.looks.plastic.theme.DarkStar;
import com.jgoodies.looks.plastic.theme.DesertRed;
import com.jgoodies.looks.plastic.theme.ExperienceGreen;
import java.awt.Color;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    RelatorioHistoricoEntradaSaida relatorioHistoricoEntradaSaida;
    Backup backup;
    public static String DATABASE = null;
    public static String IP = null;
    public static String USUARIO = null;
    public static String SENHA = null;
    private static Properties config = new Properties();
    public static String ini = "/windows/LNSys.ini";
    /** Creates new form Inicial */
    public Inicial() {
        initComponents();
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
            iniciaLogin();
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



            PlasticLookAndFeel.setPlasticTheme(new ExperienceGreen());
            try {
                UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
            } catch (UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }


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
       
        this.setLocationRelativeTo(null);
        //this.setSize(1024, 768);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        jButtonNovoOrcamento = new javax.swing.JButton();
        jButtonClientes = new javax.swing.JButton();
        jButtonMateriais = new javax.swing.JButton();
        jButtonEntradaSaida = new javax.swing.JButton();
        jButtonBackup = new javax.swing.JButton();
        jButtonFornecedores = new javax.swing.JButton();
        jButtonRelatorioHistorico = new javax.swing.JButton();
        jButtonUsuarios = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LIRA & NOBRE ARTEFATOS DE MADEIRA - SOLUÇÕES EM MÓVEIS SOB MEDIDA");

        jDesktopPaneInicial.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPaneInicial.setToolTipText("");

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
        jButtonNovoOrcamento.setBounds(300, 140, 190, 150);
        jDesktopPaneInicial.add(jButtonNovoOrcamento, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
        jButtonClientes.setBounds(300, 320, 190, 150);
        jDesktopPaneInicial.add(jButtonClientes, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
        jButtonMateriais.setBounds(520, 140, 190, 150);
        jDesktopPaneInicial.add(jButtonMateriais, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
        jButtonEntradaSaida.setBounds(300, 490, 190, 150);
        jDesktopPaneInicial.add(jButtonEntradaSaida, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
        jButtonBackup.setBounds(740, 140, 190, 150);
        jDesktopPaneInicial.add(jButtonBackup, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
        jButtonFornecedores.setBounds(740, 320, 190, 150);
        jDesktopPaneInicial.add(jButtonFornecedores, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
        jButtonRelatorioHistorico.setBounds(520, 490, 190, 150);
        jDesktopPaneInicial.add(jButtonRelatorioHistorico, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
        jButtonUsuarios.setBounds(520, 320, 190, 150);
        jDesktopPaneInicial.add(jButtonUsuarios, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
        jButtonSair.setBounds(740, 490, 190, 150);
        jDesktopPaneInicial.add(jButtonSair, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sky Yellow", "Sky Red", "Sky Pink", "Sky Krupp", "Sky Green", "Sky Bluer", "Sky Blue", "Silver", "Light Gray", "Experience Royale", "Experience Green", "Experience Blue ", "Desert Yellow", "Desert Red", "Desert Green", "Desert Bluer", "Desert Blue ", "Dark Star", "Brown Sugar." }));
        jComboBox1.setBounds(70, 60, 200, 20);
        jDesktopPaneInicial.add(jComboBox1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPaneInicial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPaneInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
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
        System.exit(1);
    }//GEN-LAST:event_jButtonSairActionPerformed

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
        System.out.println();
        System.out.println(config.getProperty("DATABASE"));
        System.out.println(config.getProperty("IP"));
        System.out.println(config.getProperty("USUARIO"));
        System.out.println(config.getProperty("SENHA"));
        System.out.println();

            String database = config.getProperty("DATABASE");
            String ip = config.getProperty("IP");
            String usuario = config.getProperty("USUARIO");
            String senha = config.getProperty("SENHA");



            //if(database.equals("null") || database == null || ip.equals("null") || ip == null || usuario.equals("null") || usuario == null || senha.equals("null") || senha == null){
            if(ip == null){
                JOptionPane.showMessageDialog(null,
                   "O arquivo de configuração está em branco ou faltando parâmetros.", "Erro",
                   JOptionPane.ERROR_MESSAGE);
                   System.out.println("BRANCOOOOOO");
                return ok = false;
            }else{
                DATABASE = database;
                IP = ip;
                USUARIO = new String(decoder.decodeBuffer(usuario));
                SENHA = new String(decoder.decodeBuffer(senha));


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

                  if(Login.usuarios.equals("S")){jButtonUsuarios.setVisible(true);
                        }else{jButtonUsuarios.setVisible(false); }

                  if(Login.orcamentos.equals("S")){jButtonNovoOrcamento.setVisible(true);
                        }else{jButtonNovoOrcamento.setVisible(false); }

                  if(Login.entradaSaida.equals("S")){jButtonEntradaSaida.setVisible(true);
                        }else{jButtonEntradaSaida.setVisible(false); }

                  System.out.println("Verificando Usuarios");
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
                new Login().setModal(true);
               // new Login().setVisible(true);

            }
        });
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBackup;
    private javax.swing.JButton jButtonClientes;
    private javax.swing.JButton jButtonEntradaSaida;
    private javax.swing.JButton jButtonFornecedores;
    private javax.swing.JButton jButtonMateriais;
    private javax.swing.JButton jButtonNovoOrcamento;
    private javax.swing.JButton jButtonRelatorioHistorico;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JButton jButtonUsuarios;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JDesktopPane jDesktopPaneInicial;
    // End of variables declaration//GEN-END:variables

}
