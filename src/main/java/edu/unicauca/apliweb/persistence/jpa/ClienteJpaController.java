/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.unicauca.apliweb.persistence.entities.AtencionMedica;
import edu.unicauca.apliweb.persistence.entities.Cliente;
import edu.unicauca.apliweb.persistence.entities.RegistroDevolucion;
import edu.unicauca.apliweb.persistence.jpa.exceptions.IllegalOrphanException;
import edu.unicauca.apliweb.persistence.jpa.exceptions.NonexistentEntityException;
import edu.unicauca.apliweb.persistence.jpa.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, Exception {
        if (cliente.getRegistroDevolucionList() == null) {
            cliente.setRegistroDevolucionList(new ArrayList<RegistroDevolucion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AtencionMedica fechaAtmedica = cliente.getFechaAtmedica();
            if (fechaAtmedica != null) {
                fechaAtmedica = em.getReference(fechaAtmedica.getClass(), fechaAtmedica.getFechaAtmedica());
                cliente.setFechaAtmedica(fechaAtmedica);
            }
            List<RegistroDevolucion> attachedRegistroDevolucionList = new ArrayList<RegistroDevolucion>();
            for (RegistroDevolucion registroDevolucionListRegistroDevolucionToAttach : cliente.getRegistroDevolucionList()) {
                registroDevolucionListRegistroDevolucionToAttach = em.getReference(registroDevolucionListRegistroDevolucionToAttach.getClass(), registroDevolucionListRegistroDevolucionToAttach.getCodDevolucion());
                attachedRegistroDevolucionList.add(registroDevolucionListRegistroDevolucionToAttach);
            }
            cliente.setRegistroDevolucionList(attachedRegistroDevolucionList);
            em.persist(cliente);
            if (fechaAtmedica != null) {
                fechaAtmedica.getClienteList().add(cliente);
                fechaAtmedica = em.merge(fechaAtmedica);
            }
            for (RegistroDevolucion registroDevolucionListRegistroDevolucion : cliente.getRegistroDevolucionList()) {
                Cliente oldCcClienteOfRegistroDevolucionListRegistroDevolucion = registroDevolucionListRegistroDevolucion.getCcCliente();
                registroDevolucionListRegistroDevolucion.setCcCliente(cliente);
                registroDevolucionListRegistroDevolucion = em.merge(registroDevolucionListRegistroDevolucion);
                if (oldCcClienteOfRegistroDevolucionListRegistroDevolucion != null) {
                    oldCcClienteOfRegistroDevolucionListRegistroDevolucion.getRegistroDevolucionList().remove(registroDevolucionListRegistroDevolucion);
                    oldCcClienteOfRegistroDevolucionListRegistroDevolucion = em.merge(oldCcClienteOfRegistroDevolucionListRegistroDevolucion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getCcCliente()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getCcCliente());
            AtencionMedica fechaAtmedicaOld = persistentCliente.getFechaAtmedica();
            AtencionMedica fechaAtmedicaNew = cliente.getFechaAtmedica();
            List<RegistroDevolucion> registroDevolucionListOld = persistentCliente.getRegistroDevolucionList();
            List<RegistroDevolucion> registroDevolucionListNew = cliente.getRegistroDevolucionList();
            List<String> illegalOrphanMessages = null;
            for (RegistroDevolucion registroDevolucionListOldRegistroDevolucion : registroDevolucionListOld) {
                if (!registroDevolucionListNew.contains(registroDevolucionListOldRegistroDevolucion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RegistroDevolucion " + registroDevolucionListOldRegistroDevolucion + " since its ccCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fechaAtmedicaNew != null) {
                fechaAtmedicaNew = em.getReference(fechaAtmedicaNew.getClass(), fechaAtmedicaNew.getFechaAtmedica());
                cliente.setFechaAtmedica(fechaAtmedicaNew);
            }
            List<RegistroDevolucion> attachedRegistroDevolucionListNew = new ArrayList<RegistroDevolucion>();
            for (RegistroDevolucion registroDevolucionListNewRegistroDevolucionToAttach : registroDevolucionListNew) {
                registroDevolucionListNewRegistroDevolucionToAttach = em.getReference(registroDevolucionListNewRegistroDevolucionToAttach.getClass(), registroDevolucionListNewRegistroDevolucionToAttach.getCodDevolucion());
                attachedRegistroDevolucionListNew.add(registroDevolucionListNewRegistroDevolucionToAttach);
            }
            registroDevolucionListNew = attachedRegistroDevolucionListNew;
            cliente.setRegistroDevolucionList(registroDevolucionListNew);
            cliente = em.merge(cliente);
            if (fechaAtmedicaOld != null && !fechaAtmedicaOld.equals(fechaAtmedicaNew)) {
                fechaAtmedicaOld.getClienteList().remove(cliente);
                fechaAtmedicaOld = em.merge(fechaAtmedicaOld);
            }
            if (fechaAtmedicaNew != null && !fechaAtmedicaNew.equals(fechaAtmedicaOld)) {
                fechaAtmedicaNew.getClienteList().add(cliente);
                fechaAtmedicaNew = em.merge(fechaAtmedicaNew);
            }
            for (RegistroDevolucion registroDevolucionListNewRegistroDevolucion : registroDevolucionListNew) {
                if (!registroDevolucionListOld.contains(registroDevolucionListNewRegistroDevolucion)) {
                    Cliente oldCcClienteOfRegistroDevolucionListNewRegistroDevolucion = registroDevolucionListNewRegistroDevolucion.getCcCliente();
                    registroDevolucionListNewRegistroDevolucion.setCcCliente(cliente);
                    registroDevolucionListNewRegistroDevolucion = em.merge(registroDevolucionListNewRegistroDevolucion);
                    if (oldCcClienteOfRegistroDevolucionListNewRegistroDevolucion != null && !oldCcClienteOfRegistroDevolucionListNewRegistroDevolucion.equals(cliente)) {
                        oldCcClienteOfRegistroDevolucionListNewRegistroDevolucion.getRegistroDevolucionList().remove(registroDevolucionListNewRegistroDevolucion);
                        oldCcClienteOfRegistroDevolucionListNewRegistroDevolucion = em.merge(oldCcClienteOfRegistroDevolucionListNewRegistroDevolucion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getCcCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getCcCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RegistroDevolucion> registroDevolucionListOrphanCheck = cliente.getRegistroDevolucionList();
            for (RegistroDevolucion registroDevolucionListOrphanCheckRegistroDevolucion : registroDevolucionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the RegistroDevolucion " + registroDevolucionListOrphanCheckRegistroDevolucion + " in its registroDevolucionList field has a non-nullable ccCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            AtencionMedica fechaAtmedica = cliente.getFechaAtmedica();
            if (fechaAtmedica != null) {
                fechaAtmedica.getClienteList().remove(cliente);
                fechaAtmedica = em.merge(fechaAtmedica);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
