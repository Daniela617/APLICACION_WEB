/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
    @NamedQuery(name = "Compra.findByIdCompra", query = "SELECT c FROM Compra c WHERE c.idCompra = :idCompra"),
    @NamedQuery(name = "Compra.findByFechaCompra", query = "SELECT c FROM Compra c WHERE c.fechaCompra = :fechaCompra"),
    @NamedQuery(name = "Compra.findByValorCompra", query = "SELECT c FROM Compra c WHERE c.valorCompra = :valorCompra"),
    @NamedQuery(name = "Compra.findByCntProductos", query = "SELECT c FROM Compra c WHERE c.cntProductos = :cntProductos"),
    @NamedQuery(name = "Compra.findBySubtotalCompra", query = "SELECT c FROM Compra c WHERE c.subtotalCompra = :subtotalCompra"),
    @NamedQuery(name = "Compra.findByPrecioUnitarioCompra", query = "SELECT c FROM Compra c WHERE c.precioUnitarioCompra = :precioUnitarioCompra")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMPRA")
    private Long idCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_COMPRA")
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR_COMPRA")
    private long valorCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CNT_PRODUCTOS")
    private short cntProductos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUBTOTAL_COMPRA")
    private BigDecimal subtotalCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRECIO_UNITARIO_COMPRA")
    private BigDecimal precioUnitarioCompra;
    @JoinColumn(name = "CC_USUARIO", referencedColumnName = "CC_USUARIO")
    @ManyToOne
    private Administrador ccUsuario;
    @JoinColumn(name = "COD_PRODUCTO", referencedColumnName = "COD_PRODUCTO")
    @ManyToOne(optional = false)
    private Producto codProducto;

    public Compra() {
    }

    public Compra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Compra(Long idCompra, Date fechaCompra, long valorCompra, short cntProductos, BigDecimal subtotalCompra, BigDecimal precioUnitarioCompra) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.valorCompra = valorCompra;
        this.cntProductos = cntProductos;
        this.subtotalCompra = subtotalCompra;
        this.precioUnitarioCompra = precioUnitarioCompra;
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public long getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(long valorCompra) {
        this.valorCompra = valorCompra;
    }

    public short getCntProductos() {
        return cntProductos;
    }

    public void setCntProductos(short cntProductos) {
        this.cntProductos = cntProductos;
    }

    public BigDecimal getSubtotalCompra() {
        return subtotalCompra;
    }

    public void setSubtotalCompra(BigDecimal subtotalCompra) {
        this.subtotalCompra = subtotalCompra;
    }

    public BigDecimal getPrecioUnitarioCompra() {
        return precioUnitarioCompra;
    }

    public void setPrecioUnitarioCompra(BigDecimal precioUnitarioCompra) {
        this.precioUnitarioCompra = precioUnitarioCompra;
    }

    public Administrador getCcUsuario() {
        return ccUsuario;
    }

    public void setCcUsuario(Administrador ccUsuario) {
        this.ccUsuario = ccUsuario;
    }

    public Producto getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(Producto codProducto) {
        this.codProducto = codProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompra != null ? idCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.idCompra == null && other.idCompra != null) || (this.idCompra != null && !this.idCompra.equals(other.idCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.unicauca.apliweb.persistence.entities.Compra[ idCompra=" + idCompra + " ]";
    }
    
}
