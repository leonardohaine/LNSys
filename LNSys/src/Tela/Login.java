/*
 * Login.java
 *
 * Created on 12 de Agosto de 2009, 22:13
 */

package Tela;

import LN.entity.Usuarios;
import Util.HibernateUtil;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author  Administrador
 */
public class Login extends javax.swing.JDialog {
    public static String user;
    public static String clientes = "S";
    public static String orcamentos = "S";
    public static String fornecedores = "S";
    public static String relatorio = "S";
    public static String entradaSaida = "S";
    public static String produtos = "S";
    public static String telaUsuarios = "S";
    public static String login = "S";
    /** Creates new form Login */
    public Login(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);

    }
    
    public Login(){
        initComponents();
        this.setLocationRelativeTo(null);
        
        ImageIcon icone = new ImageIcon(this.getClass().getClassLoader().getResource("imagens/IconeLN.png"));
        setIconImage(icone.getImage());
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPaneMsg = new javax.swing.JOptionPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldUsuario = new javax.swing.JTextField();
        jButtonOut = new javax.swing.JButton();
        jButtonOk = new javax.swing.JButton();
        jPasswordFieldSenha = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("LOGIN");
        setModal(true);

        jPanel1.setBackground(new java.awt.Color(0, 121, 76));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("USUÁRIO");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SENHA");

        jTextFieldUsuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextFieldUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldUsuarioFocusLost(evt);
            }
        });
        jTextFieldUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldUsuarioKeyPressed(evt);
            }
        });

        jButtonOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/logout.png"))); // NOI18N
        jButtonOut.setMargin(new java.awt.Insets(2, 1, 2, 1));
        jButtonOut.setPreferredSize(new java.awt.Dimension(65, 40));
        jButtonOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOutActionPerformed(evt);
            }
        });

        jButtonOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/login.png"))); // NOI18N
        jButtonOk.setMargin(new java.awt.Insets(2, 1, 2, 1));
        jButtonOk.setPreferredSize(new java.awt.Dimension(65, 40));
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jPasswordFieldSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordFieldSenhaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordFieldSenhaFocusLost(evt);
            }
        });
        jPasswordFieldSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordFieldSenhaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonOut, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonOk, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(7, 7, 7)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldUsuario)
                            .addComponent(jPasswordFieldSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jTextFieldUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldUsuarioFocusGained
//    jTextFieldUsuario.setBackground(Color.GRAY);
//    jTextFieldUsuario.setForeground(Color.white);
}//GEN-LAST:event_jTextFieldUsuarioFocusGained

private void jTextFieldUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldUsuarioFocusLost
//    jTextFieldUsuario.setBackground(Color.WHITE);
//    jTextFieldUsuario.setForeground(Color.BLACK);
    //jTextFieldUsuario.setForeground(new java.awt.Color(0, 0, 0));
}//GEN-LAST:event_jTextFieldUsuarioFocusLost

private void jButtonOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOutActionPerformed
    System.exit(0);
}//GEN-LAST:event_jButtonOutActionPerformed

private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();

        String nome = jTextFieldUsuario.getText().toUpperCase();
        String senha = jPasswordFieldSenha.getText().toUpperCase();

        Query loginUsuario = sessao.createQuery("from Usuarios as u where u.nome = '"+nome+"' and u.senha = '"+senha+"' ");

        //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
        List ListUsuario = loginUsuario.list();
        //List materiais = consultaMat.list();
        //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
        Iterator i = ListUsuario.iterator();

        System.out.println("List Us: "+ ListUsuario );
            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
            //System.out.println("MAT ->"+ matOrca.getProduto());
        if(!ListUsuario.isEmpty()){
            while(i.hasNext()) {

                Usuarios usuarios = (Usuarios) i.next();

                    System.out.println("login "+ usuarios.getCodusuario()+"-"+ usuarios.getNome());
                    Login.user = usuarios.getCodusuario()+"-"+ usuarios.getNome();
                    Login.telaUsuarios = usuarios.getCheckusuarios();
                    Login.clientes = usuarios.getCheckclientes();
                    Login.fornecedores = usuarios.getCheckfornecedores();
                    Login.produtos = usuarios.getCheckprodutos();
                    Login.relatorio = usuarios.getCheckrelatorios();
                    Login.entradaSaida = usuarios.getCheckentradasaida();
                    Login.orcamentos = usuarios.getCheckorcamentos();
                    Inicial.USER = user;
                    dispose();
            }
        }else{
           JOptionPane.showMessageDialog(this, "Nome ou senha não conferem!!!", "Aviso", JOptionPane.WARNING_MESSAGE);
           return;
        }

}//GEN-LAST:event_jButtonOkActionPerformed

private void jTextFieldUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldUsuarioKeyPressed
     if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
        jPasswordFieldSenha.requestFocus();
    }
}//GEN-LAST:event_jTextFieldUsuarioKeyPressed

private void jPasswordFieldSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldSenhaKeyPressed
    if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){

        jButtonOk.doClick();
        
//        Session sessao = HibernateUtil.getSessionFactory().openSession();
//        Transaction transacao = sessao.beginTransaction();
//
//        String nome = jTextFieldUsuario.getText().toUpperCase();
//        String senha = jPasswordFieldSenha.getText().toUpperCase();
//
//        Query loginUsuario = sessao.createQuery("from Usuarios as u where u.nome = '"+nome+"' and u.senha = '"+senha+"' ");
//
//        //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
//        List ListUsuario = loginUsuario.list();
//        //List materiais = consultaMat.list();
//        //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
//        Iterator i = ListUsuario.iterator();
//
//        System.out.println("List Us: "+ ListUsuario );
//            //MateriaisOrcamentos matOrca = new MateriaisOrcamentos();
//            //System.out.println("MAT ->"+ matOrca.getProduto());
//        if(!ListUsuario.isEmpty()){
//            while(i.hasNext()) {
//
//                Usuarios usuarios = (Usuarios) i.next();
//
//                    System.out.println("login "+ usuarios.getCodusuario()+"-"+ usuarios.getNome());
//                    Login.user = usuarios.getCodusuario()+"-"+ usuarios.getNome();
//                    Login.telaUsuarios = usuarios.getCheckusuarios();
//                    Login.clientes = usuarios.getCheckclientes();
//                    Login.fornecedores = usuarios.getCheckfornecedores();
//                    Login.produtos = usuarios.getCheckprodutos();
//                    Login.relatorio = usuarios.getCheckrelatorios();
//                    Login.entradaSaida = usuarios.getCheckentradasaida();
//                    Login.orcamentos = usuarios.getCheckorcamentos();
//                    Inicial.USER = user;
//                    dispose();
//            }
//        }else{
//           JOptionPane.showMessageDialog(this, "Nome ou senha não conferem!!!", "Aviso", JOptionPane.WARNING_MESSAGE);
//           return;
//        }

    }
}//GEN-LAST:event_jPasswordFieldSenhaKeyPressed

private void jPasswordFieldSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordFieldSenhaFocusGained
//    jPasswordFieldSenha.setBackground(Color.GRAY);
//    jPasswordFieldSenha.setForeground(Color.white);
}//GEN-LAST:event_jPasswordFieldSenhaFocusGained

private void jPasswordFieldSenhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordFieldSenhaFocusLost
//    jPasswordFieldSenha.setBackground(Color.WHITE);
//    jPasswordFieldSenha.setForeground(Color.BLACK);
    //jPasswordFieldSenha.setBackground(new java.awt.Color(255, 255, 255));
    //jPasswordFieldSenha.setForeground(new java.awt.Color(0, 0, 0));
}//GEN-LAST:event_jPasswordFieldSenhaFocusLost
    
    public void limparCampos(){
         jTextFieldUsuario.setText("");
         jPasswordFieldSenha.setText("");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login dialog = new Login(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JOptionPane jOptionPaneMsg;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordFieldSenha;
    private javax.swing.JTextField jTextFieldUsuario;
    // End of variables declaration//GEN-END:variables
    
}
