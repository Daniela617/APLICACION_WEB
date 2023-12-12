/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "regente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Regente.findAll", query = "SELECT r FROM Regente r"),
    @NamedQuery(name = "Regente.findByCcUsuario", query = "SELECT r FROM Regente r WHERE r.ccUsuario = :ccUsuario"),
    @NamedQuery(name = "Regente.findByEpsRegente", query = "SELECT r FROM Regente r WHERE r.epsRegente = :epsRegente")})
public class Regente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CC_USUARIO")
    private Integer ccUsuario;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "CERTIFICADO_REGENTE")
    private byte[] certificadoRegente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "EPS_REGENTE")
    private String epsRegente;
    @JoinColumn(name = "CC_USUARIO", referencedColumnName = "CC_USUARIO", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Trabajador trabajador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccUsuario")
    private List<RegistroDevolucion> registroDevolucionList;
    @OneToMany(mappedBy = "ccUsuario")
    private List<AtencionMedica> atencionMedicaList;

    public Regente() {
    }

    public Regente(Integer ccUsuario) {
        this.ccUsuario = ccUsuario;
    }

    public Regente(Integer ccUsuario, byte[] certificadoRegente, String epsRegente) {
        this.ccUsuario = ccUsuario;
        this.certificadoRegente = certificadoRegente;
        this.epsRegente = epsRegente;
    }

    public Integer getCcUsuario() {
        return ccUsuario;
    }

    public void setCcUsuario(Integer ccUsuario) {
        this.ccUsuario = ccUsuario;
    }

    public byte[] getCertificadoRegente() {
        return certificadoRegente;
    }

    public void setCertificadoRegente(byte[] certificadoRegente) {
        this.certificadoRegente = certificadoRegente;
    }

    public String getEpsRegente() {
        return epsRegente;
    }

    public void setEpsRegente(String epsRegente) {
        this.epsRegente = epsRegente;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    @XmlTransient
    public List<RegistroDevolucion> getRegistroDevolucionList() {
        return registroDevolucionList;
    }

    public void setRegistroDevolucionList(List<RegistroDevolucion> registroDevolucionList) {
        this.registroDevolucionList = registroDevolucionList;
    }

    @XmlTransient
    public List<AtencionMedica> getAtencionMedicaList() {
        return atencionMedicaList;
    }

    public void setAtencionMedicaList(List<AtencionMedica> atencionMedicaList) {
        this.atencionMedicaList = atencionMedicaList;
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
        if (!(object instanceof Regente)) {
            return false;
        }
        Regente other = (Regente) object;
        if ((this.ccUsuario == null && other.ccUsuario != null) || (this.ccUsuario != null && !this.ccUsuario.equals(other.ccUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.unicauca.apliweb.persistence.entities.Regente[ ccUsuario=" + ccUsuario + " ]";
    }
    
}
