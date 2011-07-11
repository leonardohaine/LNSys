/*
 * CadUsuario.java
 *
 * Created on 29 de Outubro de 2009, 01:22
 */

package Tela;


import LN.entity.Usuarios;
import Util.HibernateUtil;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

/**
 *
 * @author  LEONARDU
 */
public class CadUsuarios extends javax.swing.JInternalFrame {

    private static CadUsuarios cadUsuario;
    private Inicial inicial = null;
    Vector vUsuario = new Vector();
    /** Creates new form CadUsuario */
    public CadUsuarios(Inicial instancia) throws PropertyVetoException, SQLException, ClassNotFoundException {
        initComponents();
        
         setFrameIcon(new ImageIcon(this.getClass().getClassLoader().getResource("imagens/IconeLN.png")));
        
        this.setSelected(true);
        this.requestFocus();
        this.setClosable(true);
        this.setIconifiable(false);
        
        falseEnable();

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();


        Query loginUsuario = sessao.createQuery("from Usuarios ");
        //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
        List ListUsuario = loginUsuario.list();
        //List materiais = consultaMat.list();
        //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
        Iterator i = ListUsuario.iterator();

        while (i.hasNext()) {
            Usuarios usuarios = (Usuarios) i.next();
            vUsuario.add(usuarios.getNome());
            jListUsuario.setListData(vUsuario);
            jListUsuario.updateUI();
        }

        transacao.commit();
        sessao.close();

        Dimension centraliza = Toolkit.getDefaultToolkit().getScreenSize();   
        this.setLocation((centraliza.width-this.getSize().width)/2,   
                      (centraliza.height-this.getSize().height)/2);
        
        
       
        
    }

    public static CadUsuarios getInstance(Inicial instancia) throws PropertyVetoException, SQLException, ClassNotFoundException {
        if(cadUsuario == null)
            cadUsuario = new CadUsuarios(instancia);
        return cadUsuario;
    }
    
    public void trueEnable(){
        //jTextFieldUsuario.setEditable(true);
        //jTextFieldCodigo.setEditable(true);
        jPasswordFieldSenha.setEditable(true);
        jCheckBoxClientes.setEnabled(true);
        jCheckBoxOrcamentos.setEnabled(true);
        jCheckBoxProdutos.setEnabled(true);
        jCheckBoxFornecedores.setEnabled(true);
        jCheckBoxUsuarios.setEnabled(true);
        jCheckBoxEntradaSaida.setEnabled(true);
        jCheckBoxRelatorios.setEnabled(true);
        jButtonAlterar.setEnabled(true);
        //jButtonSalvar.setEnabled(true);
        jButtonExcluir.setEnabled(true);
    }
    
    public void falseEnable(){
        jTextFieldCodigo.setEditable(false);
        jTextFieldUsuario.setEditable(false);
        jPasswordFieldSenha.setEditable(false);
        jCheckBoxClientes.setEnabled(false);
        jCheckBoxOrcamentos.setEnabled(false);
        jCheckBoxProdutos.setEnabled(false);
        jCheckBoxFornecedores.setEnabled(false);
        jCheckBoxUsuarios.setEnabled(false);
        jCheckBoxEntradaSaida.setEnabled(false);
        jCheckBoxRelatorios.setEnabled(false);
        jButtonAlterar.setEnabled(false);
        jButtonSalvar.setEnabled(false);
        jButtonExcluir.setEnabled(false);
    }
    
    public void limpaTela(){
        jTextFieldCodigo.setText("");
        jTextFieldUsuario.setText("");
        jPasswordFieldSenha.setText("");
        jCheckBoxClientes.setSelected(false);
        jCheckBoxOrcamentos.setSelected(false);
        jCheckBoxProdutos.setSelected(false);
        jCheckBoxFornecedores.setSelected(false);
        jCheckBoxUsuarios.setSelected(false);
        jCheckBoxEntradaSaida.setSelected(false);
        jCheckBoxRelatorios.setSelected(false);
        jButtonSalvar.setEnabled(false);

        
    }

    public int getNextvalUsuarios(){

        int nextVal = 0;
    try{
       Session sessao = HibernateUtil.getSessionFactory().openSession();
       Transaction transacao = sessao.beginTransaction();

        SQLQuery query = sessao.createSQLQuery("select nextval('seq_usuarios')");
           Object object = query.uniqueResult();
           nextVal = ( (java.math.BigInteger)object ).intValue();
           System.out.println("NEXTVAL USUÁRIOS "+nextVal);


        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro no gerador de sequencia: "+ e);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldUsuario = new javax.swing.JTextField();
        jCheckBoxProdutos = new javax.swing.JCheckBox();
        jCheckBoxClientes = new javax.swing.JCheckBox();
        jButtonIncluir = new javax.swing.JButton();
        jButtonSalvar = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jPasswordFieldSenha = new javax.swing.JPasswordField();
        jCheckBoxFornecedores = new javax.swing.JCheckBox();
        jCheckBoxUsuarios = new javax.swing.JCheckBox();
        jCheckBoxEntradaSaida = new javax.swing.JCheckBox();
        jCheckBoxRelatorios = new javax.swing.JCheckBox();
        jCheckBoxOrcamentos = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldCodigo = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jPasswordFieldConfSenha = new javax.swing.JPasswordField();
        jButtonSair = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListUsuario = new javax.swing.JList();

        setTitle("CADASTRO DE USUÁRIOS");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
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

        jLabel1.setText("Usuário:");

        jLabel2.setText("Senha:");

        jCheckBoxProdutos.setText("Produtos");

        jCheckBoxClientes.setText("Clientes");

        jButtonIncluir.setFont(new java.awt.Font("Arial", 1, 12));
        jButtonIncluir.setText("Incluir");
        jButtonIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncluirActionPerformed(evt);
            }
        });

        jButtonSalvar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButtonSalvar.setText("Salvar");
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });

        jButtonAlterar.setFont(new java.awt.Font("Arial", 1, 12));
        jButtonAlterar.setText("Alterar");
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

        jButtonExcluir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButtonExcluir.setText("Excluir");
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jCheckBoxFornecedores.setText("Fornecedores");

        jCheckBoxUsuarios.setText("Usuários");

        jCheckBoxEntradaSaida.setText("Entrada e Saída");

        jCheckBoxRelatorios.setText("Relatórios");

        jCheckBoxOrcamentos.setText("Orçamentos");

        jLabel4.setText("Código:");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel5.setText("Confirma Senha:");

        jButtonSair.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButtonSair.setText("Sair");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonIncluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSair))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jCheckBoxEntradaSaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jCheckBoxUsuarios)
                                            .addComponent(jCheckBoxOrcamentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jCheckBoxClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jPasswordFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCheckBoxFornecedores)
                                            .addComponent(jCheckBoxRelatorios)
                                            .addComponent(jCheckBoxProdutos)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPasswordFieldConfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldUsuario)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPasswordFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jPasswordFieldConfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxProdutos)
                            .addComponent(jCheckBoxClientes))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxFornecedores)
                            .addComponent(jCheckBoxOrcamentos))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxUsuarios)
                            .addComponent(jCheckBoxRelatorios))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxEntradaSaida))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIncluir)
                    .addComponent(jButtonSalvar)
                    .addComponent(jButtonAlterar)
                    .addComponent(jButtonExcluir)
                    .addComponent(jButtonSair)))
        );

        jLabel3.setText("Usuários:");

        jListUsuario.setFont(new java.awt.Font("Arial Black", 0, 12));
        jListUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListUsuarioMouseClicked(evt);
            }
        });
        jListUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jListUsuarioKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jListUsuario);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
           

private void jButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirActionPerformed

        limpaTela();

        jTextFieldCodigo.setText(String.valueOf(getNextvalUsuarios()));
        jTextFieldUsuario.setEditable(false);
        jTextFieldUsuario.setEditable(true);
        jTextFieldUsuario.requestFocus();
        jPasswordFieldSenha.setEditable(true);
        jCheckBoxClientes.setEnabled(true);
        jCheckBoxOrcamentos.setEnabled(true);
        jCheckBoxProdutos.setEnabled(true);
        jCheckBoxFornecedores.setEnabled(true);
        jCheckBoxUsuarios.setEnabled(true);
        jCheckBoxEntradaSaida.setEnabled(true);
        jCheckBoxRelatorios.setEnabled(true);
        jTextFieldUsuario.setEditable(true);
        //jButtonAlterar.setEnabled(true);
        jButtonSalvar.setEnabled(true);
        //jButtonExcluir.setEnabled(true);
}//GEN-LAST:event_jButtonIncluirActionPerformed

private void jListUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jListUsuarioKeyReleased
    if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN || evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP){
           

           
                jTextFieldUsuario.setText((String) jListUsuario.getSelectedValue());
                String nomeUs = (String) jListUsuario.getSelectedValue();
                
                System.out.println("US " + nomeUs);
                jButtonAlterar.setEnabled(true);
                jButtonExcluir.setEnabled(true);

                Session sessao = HibernateUtil.getSessionFactory().openSession();
                Transaction transacao = sessao.beginTransaction();


                Query loginUsuario = sessao.createQuery("from Usuarios u where u.nome = '"+jTextFieldUsuario.getText()+"' ");
                //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
                List ListUsuario = loginUsuario.list();
                //List materiais = consultaMat.list();
                //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
                Iterator i = ListUsuario.iterator();

        //while (i.hasNext()) {
                Usuarios usuarios = (Usuarios) i.next();

                //if(usuarioVO != null){
                    if(usuarios.getCheckclientes().equals("S")){
                        jCheckBoxClientes.setSelected(true);
                    }else{jCheckBoxClientes.setSelected(false);}

                    if(usuarios.getCheckprodutos().equals("S")){
                        jCheckBoxProdutos.setSelected(true);
                    }else{jCheckBoxProdutos.setSelected(false);}

                    if(usuarios.getCheckfornecedores().equals("S")){
                        jCheckBoxFornecedores.setSelected(true);
                    }else{jCheckBoxFornecedores.setSelected(false);}

                    if(usuarios.getCheckusuarios().equals("S")){
                        jCheckBoxUsuarios.setSelected(true);
                    }else{jCheckBoxUsuarios.setSelected(false);}

                    if(usuarios.getCheckorcamentos().equals("S")){
                        jCheckBoxOrcamentos.setSelected(true);
                    }else{jCheckBoxOrcamentos.setSelected(false);}

                    if(usuarios.getCheckentradasaida().equals("S")){
                            jCheckBoxEntradaSaida.setSelected(true);
                    }else{jCheckBoxEntradaSaida.setSelected(false);}

                    if(usuarios.getCheckrelatorios().equals("S")){
                            jCheckBoxRelatorios.setSelected(true);
                    }else{jCheckBoxRelatorios.setSelected(false);}

                    jTextFieldCodigo.setText(usuarios.getCodusuario());
                    jTextFieldUsuario.setText(usuarios.getNome());
                    jPasswordFieldSenha.setText(usuarios.getSenha());
          
    }
}//GEN-LAST:event_jListUsuarioKeyReleased

private void jListUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListUsuarioMouseClicked
    if(evt.getClickCount() == 1){
           
                jTextFieldUsuario.setText((String) jListUsuario.getSelectedValue());
                String nomeUs = (String) jListUsuario.getSelectedValue();

                Session sessao = HibernateUtil.getSessionFactory().openSession();
                Transaction transacao = sessao.beginTransaction();


                Query loginUsuario = sessao.createQuery("from Usuarios u where u.nome = '"+jTextFieldUsuario.getText().toUpperCase()+"' ");
                //Query consultaMat = sessao.createQuery("from MateriaisOrcamentos as mo where mo.codorcamentos = '"+jListOrcamento.getSelectedValue().toString()+"'");
                List ListUsuario = loginUsuario.list();
                //List materiais = consultaMat.list();
                //System.out.println("foram encontradas "+orcamentos.size()+" orcamentos");
                Iterator i = ListUsuario.iterator();

        while (i.hasNext()) {
                Usuarios usuarios = (Usuarios) i.next();
                System.out.println("US " + nomeUs);
                jButtonAlterar.setEnabled(true);
                jButtonExcluir.setEnabled(true);

                //if(usuarioVO != null){
                    if(usuarios.getCheckclientes().equals("S")){
                        jCheckBoxClientes.setSelected(true);
                    }else{jCheckBoxClientes.setSelected(false);}

                    if(usuarios.getCheckprodutos().equals("S")){
                        jCheckBoxProdutos.setSelected(true);
                    }else{jCheckBoxProdutos.setSelected(false);}

                    if(usuarios.getCheckfornecedores().equals("S")){
                        jCheckBoxFornecedores.setSelected(true);
                    }else{jCheckBoxFornecedores.setSelected(false);}

                    if(usuarios.getCheckusuarios().equals("S")){
                        jCheckBoxUsuarios.setSelected(true);
                    }else{jCheckBoxUsuarios.setSelected(false);}

                    if(usuarios.getCheckorcamentos().equals("S")){
                        jCheckBoxOrcamentos.setSelected(true);
                    }else{jCheckBoxOrcamentos.setSelected(false);}

                    if(usuarios.getCheckentradasaida().equals("S")){
                            jCheckBoxEntradaSaida.setSelected(true);
                    }else{jCheckBoxEntradaSaida.setSelected(false);}

                    if(usuarios.getCheckrelatorios().equals("S")){
                            jCheckBoxRelatorios.setSelected(true);
                    }else{jCheckBoxRelatorios.setSelected(false);}

                    jTextFieldCodigo.setText(usuarios.getCodusuario());
                    jTextFieldUsuario.setText(usuarios.getNome());
                    jPasswordFieldSenha.setText(usuarios.getSenha());

        }
                transacao.commit();
                sessao.close();
          
    }
}//GEN-LAST:event_jListUsuarioMouseClicked

private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
 if(jButtonAlterar.getText().equals("Atualizar")){
            jButtonAlterar.setText("Alterar");

        falseEnable();
        jButtonExcluir.setEnabled(true);
        jButtonAlterar.setEnabled(true);
        String clientes = null;
        String orcamentos = null;
        String produtos = null;
        String fornecedores = null;
        String usuarios = null;
        String entradaSaida = null;
        String relatorios = null;
        String codigo =  jTextFieldCodigo.getText();
        String nomeUsuario = jTextFieldUsuario.getText().toUpperCase();
        String senha = jPasswordFieldSenha.getText();

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();

        if (jCheckBoxClientes.isSelected()) {clientes = "S";} else { clientes = "N";}
        if (jCheckBoxOrcamentos.isSelected()) {orcamentos = "S"; } else {orcamentos = "N";}
        if (jCheckBoxProdutos.isSelected()) { produtos = "S";} else {produtos = "N";}
        if (jCheckBoxFornecedores.isSelected()) {fornecedores = "S";} else { fornecedores = "N";}
        if (jCheckBoxUsuarios.isSelected()) {usuarios = "S";} else { usuarios = "N";}
        if (jCheckBoxEntradaSaida.isSelected()) {entradaSaida = "S";} else { entradaSaida = "N";}
        if (jCheckBoxRelatorios.isSelected()) {relatorios = "S";} else { relatorios = "N";}
        
            //                           Usuarios(String codusuario, String nome, String senha, String checkprodutos, String checkclientes, String checkfornecedores, String checkorcamentos, String checkrelatorios, String checkentradasaida, String checkusuarios)
            Usuarios cUsuarios = new Usuarios(codigo, nomeUsuario, senha, produtos, clientes, fornecedores, orcamentos, relatorios, entradaSaida, usuarios);
            sessao.update(cUsuarios);
            transacao.commit();
            sessao.close();
            JOptionPane.showMessageDialog(null,"Atualizaçao realizada com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE );
        
 }else{
        jButtonAlterar.setText("Atualizar");
        trueEnable();
        
 }

}//GEN-LAST:event_jButtonAlterarActionPerformed

private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
    if(jListUsuario.getSelectedIndex() == -1){
        
    }

    Session sessao = HibernateUtil.getSessionFactory().openSession();
    Transaction transacao = sessao.beginTransaction();

    String nomeUs = (String) jListUsuario.getSelectedValue();

    Object[] options = { "Sim", "Não" };
    int a = JOptionPane.showOptionDialog(this, "Deseja apagar o usuário "+ nomeUs.toLowerCase() +"?","Atenção",
    JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE, null,
    options, options[0]);

    if(a == JOptionPane.YES_OPTION){

       
            
       Usuarios usuarios = (Usuarios) sessao.load(Usuarios.class,jTextFieldCodigo.getText());
       //System.out.println("DATA "+ new Date("dd/MM/yyyy"));
       sessao.delete(usuarios);
       sessao.flush();
       transacao.commit();
       sessao.close();
       vUsuario.remove(nomeUs);
       jListUsuario.setListData(vUsuario);
       jListUsuario.updateUI();
       limpaTela();
       JOptionPane.showMessageDialog(null,"Usuário deletado com sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE );

    }else{
        return;
    }
}//GEN-LAST:event_jButtonExcluirActionPerformed

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        limpaTela();
}//GEN-LAST:event_formInternalFrameOpened

private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
    if(jTextFieldUsuario.getText().equals("") || jTextFieldUsuario.getText() == null || jPasswordFieldSenha.getText().equals("") || jPasswordFieldSenha.getText() == null){
        JOptionPane.showMessageDialog(null,"Preencha os campos USUÁRIO e SENHA", "Atenção", JOptionPane.INFORMATION_MESSAGE );
        return ;
    }

    String clientes = null;
    String orcamentos = null;
    String produtos = null;
    String fornecedores = null;
    String usuarios = null;
    String entradaSaida = null;
    String relatorios = null;
    String codigo =  jTextFieldCodigo.getText();
    String nomeUsuario = jTextFieldUsuario.getText().toUpperCase();
    String senha = jPasswordFieldSenha.getText();

    Session sessao = HibernateUtil.getSessionFactory().openSession();
    Transaction transacao = sessao.beginTransaction();

    if (jCheckBoxClientes.isSelected()) {clientes = "S";} else { clientes = "N";}
    if (jCheckBoxOrcamentos.isSelected()) {orcamentos = "S"; } else {orcamentos = "N";}
    if (jCheckBoxProdutos.isSelected()) { produtos = "S";} else {produtos = "N";}
    if (jCheckBoxFornecedores.isSelected()) {fornecedores = "S";} else { fornecedores = "N";}
    if (jCheckBoxUsuarios.isSelected()) {usuarios = "S";} else { usuarios = "N";}
    if (jCheckBoxEntradaSaida.isSelected()) {entradaSaida = "S";} else { entradaSaida = "N";}
    if (jCheckBoxRelatorios.isSelected()) {relatorios = "S";} else { relatorios = "N";}

    if(!jPasswordFieldSenha.getText().equals(jPasswordFieldConfSenha.getText())){
       JOptionPane.showMessageDialog(null, "As Senhas Estão diferentes !");
        return ;
    }

    vUsuario.add(nomeUsuario);
    jListUsuario.setListData(vUsuario);
    jListUsuario.updateUI();
    //Usuarios(String codusuario, String nome, String senha, String checkprodutos, String checkclientes, String checkfornecedores, String checkorcamentos, String checkrelatorios, String checkentradasaida, String checkusuarios)
    Usuarios cUsuarios = new Usuarios(codigo, nomeUsuario, senha, produtos, clientes, fornecedores, orcamentos, relatorios, entradaSaida, usuarios);
    sessao.save(cUsuarios);
    transacao.commit();
    sessao.close();
    limpaTela();
    falseEnable();
    JOptionPane.showMessageDialog(null,"Usuário cadastrado com Sucesso!", "Atenção", JOptionPane.INFORMATION_MESSAGE );


}//GEN-LAST:event_jButtonSalvarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonIncluir;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JCheckBox jCheckBoxClientes;
    private javax.swing.JCheckBox jCheckBoxEntradaSaida;
    private javax.swing.JCheckBox jCheckBoxFornecedores;
    private javax.swing.JCheckBox jCheckBoxOrcamentos;
    private javax.swing.JCheckBox jCheckBoxProdutos;
    private javax.swing.JCheckBox jCheckBoxRelatorios;
    private javax.swing.JCheckBox jCheckBoxUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList jListUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordFieldConfSenha;
    private javax.swing.JPasswordField jPasswordFieldSenha;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldCodigo;
    private javax.swing.JTextField jTextFieldUsuario;
    // End of variables declaration//GEN-END:variables

}
