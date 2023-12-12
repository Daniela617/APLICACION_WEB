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
import edu.unicauca.apliweb.persistence.entities.Administrador;
import edu.unicauca.apliweb.persistence.entities.Regente;
import edu.unicauca.apliweb.persistence.entities.Trabajador;
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
public class TrabajadorJpaController implements Serializable {

    public TrabajadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trabajador trabajador) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador administrador = trabajador.getAdministrador();
            if (administrador != null) {
                administrador = em.getReference(administrador.getClass(), administrador.getCcUsuario());
                trabajador.setAdministrador(administrador);
            }
            Regente regente = trabajador.getRegente();
            if (regente != null) {
                regente = em.getReference(regente.getClass(), regente.getCcUsuario());
                trabajador.setRegente(regente);
            }
            em.persist(trabajador);
            if (administrador != null) {
                Trabajador oldTrabajadorOfAdministrador = administrador.getTrabajador();
                if (oldTrabajadorOfAdministrador != null) {
                    oldTrabajadorOfAdministrador.setAdministrador(null);
                    oldTrabajadorOfAdministrador = em.merge(oldTrabajadorOfAdministrador);
                }
                administrador.setTrabajador(trabajador);
                administrador = em.merge(administrador);
            }
            if (regente != null) {
                Trabajador oldTrabajadorOfRegente = regente.getTrabajador();
                if (oldTrabajadorOfRegente != null) {
                    oldTrabajadorOfRegente.setRegente(null);
                    oldTrabajadorOfRegente = em.merge(oldTrabajadorOfRegente);
                }
                regente.setTrabajador(trabajador);
                regente = em.merge(regente);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTrabajador(trabajador.getCcUsuario()) != null) {
                throw new PreexistingEntityException("Trabajador " + trabajador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trabajador trabajador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trabajador persistentTrabajador = em.find(Trabajador.class, trabajador.getCcUsuario());
            Administrador administradorOld = persistentTrabajador.getAdministrador();
            Administrador administradorNew = trabajador.getAdministrador();
            Regente regenteOld = persistentTrabajador.getRegente();
            Regente regenteNew = trabajador.getRegente();
            List<String> illegalOrphanMessages = null;
            if (administradorOld != null && !administradorOld.equals(administradorNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Administrador " + administradorOld + " since its trabajador field is not nullable.");
            }
            if (regenteOld != null && !regenteOld.equals(regenteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Regente " + regenteOld + " since its trabajador field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (administradorNew != null) {
                administradorNew = em.getReference(administradorNew.getClass(), administradorNew.getCcUsuario());
                trabajador.setAdministrador(administradorNew);
            }
            if (regenteNew != null) {
                regenteNew = em.getReference(regenteNew.getClass(), regenteNew.getCcUsuario());
                trabajador.setRegente(regenteNew);
            }
            trabajador = em.merge(trabajador);
            if (administradorNew != null && !administradorNew.equals(administradorOld)) {
                Trabajador oldTrabajadorOfAdministrador = administradorNew.getTrabajador();
                if (oldTrabajadorOfAdministrador != null) {
                    oldTrabajadorOfAdministrador.setAdministrador(null);
                    oldTrabajadorOfAdministrador = em.merge(oldTrabajadorOfAdministrador);
                }
                administradorNew.setTrabajador(trabajador);
                administradorNew = em.merge(administradorNew);
            }
            if (regenteNew != null && !regenteNew.equals(regenteOld)) {
                Trabajador oldTrabajadorOfRegente = regenteNew.getTrabajador();
                if (oldTrabajadorOfRegente != null) {
                    oldTrabajadorOfRegente.setRegente(null);
                    oldTrabajadorOfRegente = em.merge(oldTrabajadorOfRegente);
                }
                regenteNew.setTrabajador(trabajador);
                regenteNew = em.merge(regenteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = trabajador.getCcUsuario();
                if (findTrabajador(id) == null) {
                    throw new NonexistentEntityException("The trabajador with id " + id + " no longer exists.");
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
            Trabajador trabajador;
            try {
                trabajador = em.getReference(Trabajador.class, id);
                trabajador.getCcUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trabajador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Administrador administradorOrphanCheck = trabajador.getAdministrador();
            if (administradorOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trabajador (" + trabajador + ") cannot be destroyed since the Administrador " + administradorOrphanCheck + " in its administrador field has a non-nullable trabajador field.");
            }
            Regente regenteOrphanCheck = trabajador.getRegente();
            if (regenteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trabajador (" + trabajador + ") cannot be destroyed since the Regente " + regenteOrphanCheck + " in its regente field has a non-nullable trabajador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(trabajador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trabajador> findTrabajadorEntities() {
        return findTrabajadorEntities(true, -1, -1);
    }

    public List<Trabajador> findTrabajadorEntities(int maxResults, int firstResult) {
        return findTrabajadorEntities(false, maxResults, firstResult);
    }

    private List<Trabajador> findTrabajadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trabajador.class));
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

    public Trabajador findTrabajador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trabajador.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrabajadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trabajador> rt = cq.from(Trabajador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
