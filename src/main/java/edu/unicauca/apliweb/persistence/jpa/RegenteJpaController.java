/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.jpa;

import edu.unicauca.apliweb.persistence.entities.Regente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class RegenteJpaController implements Serializable {

    public RegenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Regente regente) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Trabajador trabajadorOrphanCheck = regente.getTrabajador();
        if (trabajadorOrphanCheck != null) {
            Regente oldRegenteOfTrabajador = trabajadorOrphanCheck.getRegente();
            if (oldRegenteOfTrabajador != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Trabajador " + trabajadorOrphanCheck + " already has an item of type Regente whose trabajador column cannot be null. Please make another selection for the trabajador field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trabajador trabajador = regente.getTrabajador();
            if (trabajador != null) {
                trabajador = em.getReference(trabajador.getClass(), trabajador.getCcUsuario());
                regente.setTrabajador(trabajador);
            }
            em.persist(regente);
            if (trabajador != null) {
                trabajador.setRegente(regente);
                trabajador = em.merge(trabajador);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegente(regente.getCcUsuario()) != null) {
                throw new PreexistingEntityException("Regente " + regente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Regente regente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regente persistentRegente = em.find(Regente.class, regente.getCcUsuario());
            Trabajador trabajadorOld = persistentRegente.getTrabajador();
            Trabajador trabajadorNew = regente.getTrabajador();
            List<String> illegalOrphanMessages = null;
            if (trabajadorNew != null && !trabajadorNew.equals(trabajadorOld)) {
                Regente oldRegenteOfTrabajador = trabajadorNew.getRegente();
                if (oldRegenteOfTrabajador != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Trabajador " + trabajadorNew + " already has an item of type Regente whose trabajador column cannot be null. Please make another selection for the trabajador field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (trabajadorNew != null) {
                trabajadorNew = em.getReference(trabajadorNew.getClass(), trabajadorNew.getCcUsuario());
                regente.setTrabajador(trabajadorNew);
            }
            regente = em.merge(regente);
            if (trabajadorOld != null && !trabajadorOld.equals(trabajadorNew)) {
                trabajadorOld.setRegente(null);
                trabajadorOld = em.merge(trabajadorOld);
            }
            if (trabajadorNew != null && !trabajadorNew.equals(trabajadorOld)) {
                trabajadorNew.setRegente(regente);
                trabajadorNew = em.merge(trabajadorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = regente.getCcUsuario();
                if (findRegente(id) == null) {
                    throw new NonexistentEntityException("The regente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regente regente;
            try {
                regente = em.getReference(Regente.class, id);
                regente.getCcUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regente with id " + id + " no longer exists.", enfe);
            }
            Trabajador trabajador = regente.getTrabajador();
            if (trabajador != null) {
                trabajador.setRegente(null);
                trabajador = em.merge(trabajador);
            }
            em.remove(regente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Regente> findRegenteEntities() {
        return findRegenteEntities(true, -1, -1);
    }

    public List<Regente> findRegenteEntities(int maxResults, int firstResult) {
        return findRegenteEntities(false, maxResults, firstResult);
    }

    private List<Regente> findRegenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Regente.class));
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

    public Regente findRegente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Regente.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regente> rt = cq.from(Regente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
