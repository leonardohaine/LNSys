package LN.entity;
// Generated 03/07/2011 16:02:55 by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * HistoricoEntrada generated by hbm2java
 */
public class HistoricoEntrada  implements java.io.Serializable {


     private int codhistorico;
     private int codmateriais;
     private String descricao;
     private Integer quantidade;
     private Double valor;
     private Date dataentrada;
     private String status;
     private Double total;
     private String usuario;

    public HistoricoEntrada() {
    }

	
    public HistoricoEntrada(int codhistorico, int codmateriais) {
        this.codhistorico = codhistorico;
        this.codmateriais = codmateriais;
    }
    public HistoricoEntrada(int codhistorico, int codmateriais, String descricao, Integer quantidade, Double valor, Date dataentrada, String status, Double total, String usuario) {
       this.codhistorico = codhistorico;
       this.codmateriais = codmateriais;
       this.descricao = descricao;
       this.quantidade = quantidade;
       this.valor = valor;
       this.dataentrada = dataentrada;
       this.status = status;
       this.total = total;
    }
   
    public int getCodhistorico() {
        return this.codhistorico;
    }
    
    public void setCodhistorico(int codhistorico) {
        this.codhistorico = codhistorico;
    }
    public int getCodmateriais() {
        return this.codmateriais;
    }
    
    public void setCodmateriais(int codmateriais) {
        this.codmateriais = codmateriais;
    }
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Integer getQuantidade() {
        return this.quantidade;
    }
    
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    public Double getValor() {
        return this.valor;
    }
    
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public Date getDataentrada() {
        return this.dataentrada;
    }
    
    public void setDataentrada(Date dataentrada) {
        this.dataentrada = dataentrada;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public Double getTotal() {
        return this.total;
    }
    
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }




}


