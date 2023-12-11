/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "administrador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administrador.findAll", query = "SELECT a FROM Administrador a"),
    @NamedQuery(name = "Administrador.findByCcUsuario", query = "SELECT a FROM Administrador a WHERE a.ccUsuario = :ccUsuario"),
    @NamedQuery(name = "Administrador.findBySueldoAdmin", query = "SELECT a FROM Administrador a WHERE a.sueldoAdmin = :sueldoAdmin"),
    @NamedQuery(name = "Administrador.findByComisionesAdmin", query = "SELECT a FROM Administrador a WHERE a.comisionesAdmin = :comisionesAdmin")})
public class Administrador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CC_USUARIO")
    private Integer ccUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUELDO_ADMIN")
    private long sueldoAdmin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMISIONES_ADMIN")
    private long comisionesAdmin;
    @OneToMany(mappedBy = "ccUsuario")
    private List<Compra> compraList;

    public Administrador() {
    }

    public Administrador(Integer ccUsuario) {
        this.ccUsuario = ccUsuario;
    }

    public Administrador(Integer ccUsuario, long sueldoAdmin, long comisionesAdmin) {
        this.ccUsuario = ccUsuario;
        this.sueldoAdmin = sueldoAdmin;
        this.comisionesAdmin = comisionesAdmin;
    }

    public Integer getCcUsuario() {
        return ccUsuario;
    }

    public void setCcUsuario(Integer ccUsuario) {
        this.ccUsuario = ccUsuario;
    }

    public long getSueldoAdmin() {
        return sueldoAdmin;
    }

    public void setSueldoAdmin(long sueldoAdmin) {
        this.sueldoAdmin = sueldoAdmin;
    }

    public long getComisionesAdmin() {
        return comisionesAdmin;
    }

    public void setComisionesAdmin(long comisionesAdmin) {
        this.comisionesAdmin = comisionesAdmin;
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
        hash += (ccUsuario != null ? ccUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrador)) {
            return false;
        }
        Administrador other = (Administrador) object;
        if ((this.ccUsuario == null && other.ccUsuario != null) || (this.ccUsuario != null && !this.ccUsuario.equals(other.ccUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.unicauca.apliweb.persistence.entities.Administrador[ ccUsuario=" + ccUsuario + " ]";
    }
    
}
