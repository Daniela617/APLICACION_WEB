/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "trabajador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trabajador.findAll", query = "SELECT t FROM Trabajador t"),
    @NamedQuery(name = "Trabajador.findByCcUsuario", query = "SELECT t FROM Trabajador t WHERE t.ccUsuario = :ccUsuario"),
    @NamedQuery(name = "Trabajador.findByNombreUsuario", query = "SELECT t FROM Trabajador t WHERE t.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Trabajador.findByTelUsuario", query = "SELECT t FROM Trabajador t WHERE t.telUsuario = :telUsuario"),
    @NamedQuery(name = "Trabajador.findByLoginUsuario", query = "SELECT t FROM Trabajador t WHERE t.loginUsuario = :loginUsuario"),
    @NamedQuery(name = "Trabajador.findByContraseniaUsuario", query = "SELECT t FROM Trabajador t WHERE t.contraseniaUsuario = :contraseniaUsuario")})
public class Trabajador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CC_USUARIO")
    private Integer ccUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "TEL_USUARIO")
    private String telUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "LOGIN_USUARIO")
    private String loginUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "CONTRASENIA_USUARIO")
    private String contraseniaUsuario;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trabajador")
    private Administrador administrador;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trabajador")
    private Regente regente;

    public Trabajador() {
    }

    public Trabajador(Integer ccUsuario) {
        this.ccUsuario = ccUsuario;
    }

    public Trabajador(Integer ccUsuario, String nombreUsuario, String telUsuario, String loginUsuario, String contraseniaUsuario) {
        this.ccUsuario = ccUsuario;
        this.nombreUsuario = nombreUsuario;
        this.telUsuario = telUsuario;
        this.loginUsuario = loginUsuario;
        this.contraseniaUsuario = contraseniaUsuario;
    }

    public Integer getCcUsuario() {
        return ccUsuario;
    }

    public void setCcUsuario(Integer ccUsuario) {
        this.ccUsuario = ccUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTelUsuario() {
        return telUsuario;
    }

    public void setTelUsuario(String telUsuario) {
        this.telUsuario = telUsuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getContraseniaUsuario() {
        return contraseniaUsuario;
    }

    public void setContraseniaUsuario(String contraseniaUsuario) {
        this.contraseniaUsuario = contraseniaUsuario;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Regente getRegente() {
        return regente;
    }

    public void setRegente(Regente regente) {
        this.regente = regente;
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
        if (!(object instanceof Trabajador)) {
            return false;
        }
        Trabajador other = (Trabajador) object;
        if ((this.ccUsuario == null && other.ccUsuario != null) || (this.ccUsuario != null && !this.ccUsuario.equals(other.ccUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.unicauca.apliweb.persistence.entities.Trabajador[ ccUsuario=" + ccUsuario + " ]";
    }
    
}
