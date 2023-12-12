/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.entities;

import java.io.Serializable;
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
    private Integer idCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_COMPRA")
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR_COMPRA")
    private int valorCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CNT_PRODUCTOS")
    private int cntProductos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUBTOTAL_COMPRA")
    private int subtotalCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRECIO_UNITARIO_COMPRA")
    private int precioUnitarioCompra;
    @JoinColumn(name = "CC_USUARIO", referencedColumnName = "CC_USUARIO")
    @ManyToOne
    private Administrador ccUsuario;
    @JoinColumn(name = "COD_PRODUCTO", referencedColumnName = "COD_PRODUCTO")
    @ManyToOne(optional = false)
    private Producto codProducto;

    public Compra() {
    }

    public Compra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Compra(Integer idCompra, Date fechaCompra, int valorCompra, int cntProductos, int subtotalCompra, int precioUnitarioCompra) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.valorCompra = valorCompra;
        this.cntProductos = cntProductos;
        this.subtotalCompra = subtotalCompra;
        this.precioUnitarioCompra = precioUnitarioCompra;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(int valorCompra) {
        this.valorCompra = valorCompra;
    }

    public int getCntProductos() {
        return cntProductos;
    }

    public void setCntProductos(int cntProductos) {
        this.cntProductos = cntProductos;
    }

    public int getSubtotalCompra() {
        return subtotalCompra;
    }

    public void setSubtotalCompra(int subtotalCompra) {
        this.subtotalCompra = subtotalCompra;
    }

    public int getPrecioUnitarioCompra() {
        return precioUnitarioCompra;
    }

    public void setPrecioUnitarioCompra(int precioUnitarioCompra) {
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
