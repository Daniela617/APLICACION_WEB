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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByCcCliente", query = "SELECT c FROM Cliente c WHERE c.ccCliente = :ccCliente"),
    @NamedQuery(name = "Cliente.findByNombreCliente", query = "SELECT c FROM Cliente c WHERE c.nombreCliente = :nombreCliente"),
    @NamedQuery(name = "Cliente.findByDirCliente", query = "SELECT c FROM Cliente c WHERE c.dirCliente = :dirCliente"),
    @NamedQuery(name = "Cliente.findByCorreoCliente", query = "SELECT c FROM Cliente c WHERE c.correoCliente = :correoCliente")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CC_CLIENTE")
    private Integer ccCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "NOMBRE_CLIENTE")
    private String nombreCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "DIR_CLIENTE")
    private String dirCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "CORREO_CLIENTE")
    private String correoCliente;
    @JoinColumn(name = "FECHA_ATMEDICA", referencedColumnName = "FECHA_ATMEDICA")
    @ManyToOne
    private AtencionMedica fechaAtmedica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccCliente")
    private List<RegistroDevolucion> registroDevolucionList;

    public Cliente() {
    }

    public Cliente(Integer ccCliente) {
        this.ccCliente = ccCliente;
    }

    public Cliente(Integer ccCliente, String nombreCliente, String dirCliente, String correoCliente) {
        this.ccCliente = ccCliente;
        this.nombreCliente = nombreCliente;
        this.dirCliente = dirCliente;
        this.correoCliente = correoCliente;
    }

    public Integer getCcCliente() {
        return ccCliente;
    }

    public void setCcCliente(Integer ccCliente) {
        this.ccCliente = ccCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDirCliente() {
        return dirCliente;
    }

    public void setDirCliente(String dirCliente) {
        this.dirCliente = dirCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public AtencionMedica getFechaAtmedica() {
        return fechaAtmedica;
    }

    public void setFechaAtmedica(AtencionMedica fechaAtmedica) {
        this.fechaAtmedica = fechaAtmedica;
    }

    @XmlTransient
    public List<RegistroDevolucion> getRegistroDevolucionList() {
        return registroDevolucionList;
    }

    public void setRegistroDevolucionList(List<RegistroDevolucion> registroDevolucionList) {
        this.registroDevolucionList = registroDevolucionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ccCliente != null ? ccCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.ccCliente == null && other.ccCliente != null) || (this.ccCliente != null && !this.ccCliente.equals(other.ccCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.unicauca.apliweb.persistence.entities.Cliente[ ccCliente=" + ccCliente + " ]";
    }
    
}
