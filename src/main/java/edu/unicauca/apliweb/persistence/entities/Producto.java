/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByCodProducto", query = "SELECT p FROM Producto p WHERE p.codProducto = :codProducto"),
    @NamedQuery(name = "Producto.findByNombreProducto", query = "SELECT p FROM Producto p WHERE p.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "Producto.findByPrecioPublicoPrd", query = "SELECT p FROM Producto p WHERE p.precioPublicoPrd = :precioPublicoPrd"),
    @NamedQuery(name = "Producto.findByPrecioCompraPrd", query = "SELECT p FROM Producto p WHERE p.precioCompraPrd = :precioCompraPrd"),
    @NamedQuery(name = "Producto.findByFechaVencimientoPrd", query = "SELECT p FROM Producto p WHERE p.fechaVencimientoPrd = :fechaVencimientoPrd"),
    @NamedQuery(name = "Producto.findByProductoCantidad", query = "SELECT p FROM Producto p WHERE p.productoCantidad = :productoCantidad"),
    @NamedQuery(name = "Producto.findByLaboratorio", query = "SELECT p FROM Producto p WHERE p.laboratorio = :laboratorio")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_PRODUCTO")
    private Integer codProducto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "NOMBRE_PRODUCTO")
    private String nombreProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRECIO_PUBLICO_PRD")
    private int precioPublicoPrd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRECIO_COMPRA_PRD")
    private int precioCompraPrd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_VENCIMIENTO_PRD")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimientoPrd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTO_CANTIDAD")
    private short productoCantidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "LABORATORIO")
    private String laboratorio;
    @JoinTable(name = "distribucion", joinColumns = {
        @JoinColumn(name = "COD_PRODUCTO", referencedColumnName = "COD_PRODUCTO")}, inverseJoinColumns = {
        @JoinColumn(name = "COD_PROVEEDOR", referencedColumnName = "COD_PROVEEDOR")})
    @ManyToMany
    private List<Proveedor> proveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProducto")
    private List<Compra> compraList;

    public Producto() {
    }

    public Producto(Integer codProducto) {
        this.codProducto = codProducto;
    }

    public Producto(Integer codProducto, String nombreProducto, int precioPublicoPrd, int precioCompraPrd, Date fechaVencimientoPrd, short productoCantidad, String laboratorio) {
        this.codProducto = codProducto;
        this.nombreProducto = nombreProducto;
        this.precioPublicoPrd = precioPublicoPrd;
        this.precioCompraPrd = precioCompraPrd;
        this.fechaVencimientoPrd = fechaVencimientoPrd;
        this.productoCantidad = productoCantidad;
        this.laboratorio = laboratorio;
    }

    public Integer getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(Integer codProducto) {
        this.codProducto = codProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getPrecioPublicoPrd() {
        return precioPublicoPrd;
    }

    public void setPrecioPublicoPrd(int precioPublicoPrd) {
        this.precioPublicoPrd = precioPublicoPrd;
    }

    public int getPrecioCompraPrd() {
        return precioCompraPrd;
    }

    public void setPrecioCompraPrd(int precioCompraPrd) {
        this.precioCompraPrd = precioCompraPrd;
    }

    public Date getFechaVencimientoPrd() {
        return fechaVencimientoPrd;
    }

    public void setFechaVencimientoPrd(Date fechaVencimientoPrd) {
        this.fechaVencimientoPrd = fechaVencimientoPrd;
    }

    public short getProductoCantidad() {
        return productoCantidad;
    }

    public void setProductoCantidad(short productoCantidad) {
        this.productoCantidad = productoCantidad;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    @XmlTransient
    public List<Proveedor> getProveedorList() {
        return proveedorList;
    }

    public void setProveedorList(List<Proveedor> proveedorList) {
        this.proveedorList = proveedorList;
    }

    @XmlTransient
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codProducto != null ? codProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.codProducto == null && other.codProducto != null) || (this.codProducto != null && !this.codProducto.equals(other.codProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.unicauca.apliweb.persistence.entities.Producto[ codProducto=" + codProducto + " ]";
    }
    
}
