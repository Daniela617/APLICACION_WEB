/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.jpa;

import edu.unicauca.apliweb.persistence.entities.AtencionMedica;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.unicauca.apliweb.persistence.entities.Regente;
import edu.unicauca.apliweb.persistence.entities.Cliente;
import edu.unicauca.apliweb.persistence.jpa.exceptions.NonexistentEntityException;
import edu.unicauca.apliweb.persistence.jpa.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class AtencionMedicaJpaController implements Serializable {

    public AtencionMedicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AtencionMedica atencionMedica) throws PreexistingEntityException, Exception {
        if (atencionMedica.getClienteList() == null) {
            atencionMedica.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regente ccUsuario = atencionMedica.getCcUsuario();
            if (ccUsuario != null) {
                ccUsuario = em.getReference(ccUsuario.getClass(), ccUsuario.getCcUsuario());
                atencionMedica.setCcUsuario(ccUsuario);
            }
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : atencionMedica.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getCcCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            atencionMedica.setClienteList(attachedClienteList);
            em.persist(atencionMedica);
            if (ccUsuario != null) {
                ccUsuario.getAtencionMedicaList().add(atencionMedica);
                ccUsuario = em.merge(ccUsuario);
            }
            for (Cliente clienteListCliente : atencionMedica.getClienteList()) {
                AtencionMedica oldFechaAtmedicaOfClienteListCliente = clienteListCliente.getFechaAtmedica();
                clienteListCliente.setFechaAtmedica(atencionMedica);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldFechaAtmedicaOfClienteListCliente != null) {
                    oldFechaAtmedicaOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldFechaAtmedicaOfClienteListCliente = em.merge(oldFechaAtmedicaOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAtencionMedica(atencionMedica.getFechaAtmedica()) != null) {
                throw new PreexistingEntityException("AtencionMedica " + atencionMedica + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AtencionMedica atencionMedica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AtencionMedica persistentAtencionMedica = em.find(AtencionMedica.class, atencionMedica.getFechaAtmedica());
            Regente ccUsuarioOld = persistentAtencionMedica.getCcUsuario();
            Regente ccUsuarioNew = atencionMedica.getCcUsuario();
            List<Cliente> clienteListOld = persistentAtencionMedica.getClienteList();
            List<Cliente> clienteListNew = atencionMedica.getClienteList();
            if (ccUsuarioNew != null) {
                ccUsuarioNew = em.getReference(ccUsuarioNew.getClass(), ccUsuarioNew.getCcUsuario());
                atencionMedica.setCcUsuario(ccUsuarioNew);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getCcCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            atencionMedica.setClienteList(clienteListNew);
            atencionMedica = em.merge(atencionMedica);
            if (ccUsuarioOld != null && !ccUsuarioOld.equals(ccUsuarioNew)) {
                ccUsuarioOld.getAtencionMedicaList().remove(atencionMedica);
                ccUsuarioOld = em.merge(ccUsuarioOld);
            }
            if (ccUsuarioNew != null && !ccUsuarioNew.equals(ccUsuarioOld)) {
                ccUsuarioNew.getAtencionMedicaList().add(atencionMedica);
                ccUsuarioNew = em.merge(ccUsuarioNew);
            }
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setFechaAtmedica(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    AtencionMedica oldFechaAtmedicaOfClienteListNewCliente = clienteListNewCliente.getFechaAtmedica();
                    clienteListNewCliente.setFechaAtmedica(atencionMedica);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldFechaAtmedicaOfClienteListNewCliente != null && !oldFechaAtmedicaOfClienteListNewCliente.equals(atencionMedica)) {
                        oldFechaAtmedicaOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldFechaAtmedicaOfClienteListNewCliente = em.merge(oldFechaAtmedicaOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Date id = atencionMedica.getFechaAtmedica();
                if (findAtencionMedica(id) == null) {
                    throw new NonexistentEntityException("The atencionMedica with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Date id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AtencionMedica atencionMedica;
            try {
                atencionMedica = em.getReference(AtencionMedica.class, id);
                atencionMedica.getFechaAtmedica();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atencionMedica with id " + id + " no longer exists.", enfe);
            }
            Regente ccUsuario = atencionMedica.getCcUsuario();
            if (ccUsuario != null) {
                ccUsuario.getAtencionMedicaList().remove(atencionMedica);
                ccUsuario = em.merge(ccUsuario);
            }
            List<Cliente> clienteList = atencionMedica.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setFechaAtmedica(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(atencionMedica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AtencionMedica> findAtencionMedicaEntities() {
        return findAtencionMedicaEntities(true, -1, -1);
    }

    public List<AtencionMedica> findAtencionMedicaEntities(int maxResults, int firstResult) {
        return findAtencionMedicaEntities(false, maxResults, firstResult);
    }

    private List<AtencionMedica> findAtencionMedicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AtencionMedica.class));
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

    public AtencionMedica findAtencionMedica(Date id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AtencionMedica.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtencionMedicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AtencionMedica> rt = cq.from(AtencionMedica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
