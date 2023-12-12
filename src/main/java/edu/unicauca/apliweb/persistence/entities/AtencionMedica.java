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
@Table(name = "atencion_medica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtencionMedica.findAll", query = "SELECT a FROM AtencionMedica a"),
    @NamedQuery(name = "AtencionMedica.findByFechaAtmedica", query = "SELECT a FROM AtencionMedica a WHERE a.fechaAtmedica = :fechaAtmedica"),
    @NamedQuery(name = "AtencionMedica.findByTipoAtmedica", query = "SELECT a FROM AtencionMedica a WHERE a.tipoAtmedica = :tipoAtmedica")})
public class AtencionMedica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_ATMEDICA")
    @Temporal(TemporalType.DATE)
    private Date fechaAtmedica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TIPO_ATMEDICA")
    private String tipoAtmedica;
    @OneToMany(mappedBy = "fechaAtmedica")
    private List<Cliente> clienteList;
    @JoinColumn(name = "CC_USUARIO", referencedColumnName = "CC_USUARIO")
    @ManyToOne
    private Regente ccUsuario;

    public AtencionMedica() {
    }

    public AtencionMedica(Date fechaAtmedica) {
        this.fechaAtmedica = fechaAtmedica;
    }

    public AtencionMedica(Date fechaAtmedica, String tipoAtmedica) {
        this.fechaAtmedica = fechaAtmedica;
        this.tipoAtmedica = tipoAtmedica;
    }

    public Date getFechaAtmedica() {
        return fechaAtmedica;
    }

    public void setFechaAtmedica(Date fechaAtmedica) {
        this.fechaAtmedica = fechaAtmedica;
    }

    public String getTipoAtmedica() {
        return tipoAtmedica;
    }

    public void setTipoAtmedica(String tipoAtmedica) {
        this.tipoAtmedica = tipoAtmedica;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public Regente getCcUsuario() {
        return ccUsuario;
    }

    public void setCcUsuario(Regente ccUsuario) {
        this.ccUsuario = ccUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fechaAtmedica != null ? fechaAtmedica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtencionMedica)) {
            return false;
        }
        AtencionMedica other = (AtencionMedica) object;
        if ((this.fechaAtmedica == null && other.fechaAtmedica != null) || (this.fechaAtmedica != null && !this.fechaAtmedica.equals(other.fechaAtmedica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.unicauca.apliweb.persistence.entities.AtencionMedica[ fechaAtmedica=" + fechaAtmedica + " ]";
    }
    
}
