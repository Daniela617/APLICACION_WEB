/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "registro_devolucion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroDevolucion.findAll", query = "SELECT r FROM RegistroDevolucion r"),
    @NamedQuery(name = "RegistroDevolucion.findByCodDevolucion", query = "SELECT r FROM RegistroDevolucion r WHERE r.codDevolucion = :codDevolucion"),
    @NamedQuery(name = "RegistroDevolucion.findByFechaDevolucion", query = "SELECT r FROM RegistroDevolucion r WHERE r.fechaDevolucion = :fechaDevolucion")})
public class RegistroDevolucion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_DEVOLUCION")
    private Integer codDevolucion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_DEVOLUCION")
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "MOTIVO_DEVOLUCION")
    private String motivoDevolucion;
    @JoinColumn(name = "CC_CLIENTE", referencedColumnName = "CC_CLIENTE")
    @ManyToOne(optional = false)
    private Cliente ccCliente;
    @JoinColumn(name = "CC_USUARIO", referencedColumnName = "CC_USUARIO")
    @ManyToOne(optional = false)
    private Regente ccUsuario;
    @OneToMany(mappedBy = "codDevolucion")
    private List<Producto> productoList;

    public RegistroDevolucion() {
    }

    public RegistroDevolucion(Integer codDevolucion) {
        this.codDevolucion = codDevolucion;
    }

    public RegistroDevolucion(Integer codDevolucion, Date fechaDevolucion, String motivoDevolucion) {
        this.codDevolucion = codDevolucion;
        this.fechaDevolucion = fechaDevolucion;
        this.motivoDevolucion = motivoDevolucion;
    }

    public Integer getCodDevolucion() {
        return codDevolucion;
    }

    public void setCodDevolucion(Integer codDevolucion) {
        this.codDevolucion = codDevolucion;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getMotivoDevolucion() {
        return motivoDevolucion;
    }

    public void setMotivoDevolucion(String motivoDevolucion) {
        this.motivoDevolucion = motivoDevolucion;
    }

    public Cliente getCcCliente() {
        return ccCliente;
    }

    public void setCcCliente(Cliente ccCliente) {
        this.ccCliente = ccCliente;
    }

    public Regente getCcUsuario() {
        return ccUsuario;
    }

    public void setCcUsuario(Regente ccUsuario) {
        this.ccUsuario = ccUsuario;
    }

    @XmlTransient
    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codDevolucion != null ? codDevolucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroDevolucion)) {
            return false;
        }
        RegistroDevolucion other = (RegistroDevolucion) object;
        if ((this.codDevolucion == null && other.codDevolucion != null) || (this.codDevolucion != null && !this.codDevolucion.equals(other.codDevolucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.unicauca.apliweb.persistence.entities.RegistroDevolucion[ codDevolucion=" + codDevolucion + " ]";
    }
    
}
