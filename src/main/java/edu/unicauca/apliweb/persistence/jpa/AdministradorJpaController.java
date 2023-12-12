/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.jpa;

import edu.unicauca.apliweb.persistence.entities.Administrador;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.unicauca.apliweb.persistence.entities.Trabajador;
import edu.unicauca.apliweb.persistence.entities.Compra;
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
public class AdministradorJpaController implements Serializable {

    public AdministradorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Administrador administrador) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (administrador.getCompraList() == null) {
            administrador.setCompraList(new ArrayList<Compra>());
        }
        List<String> illegalOrphanMessages = null;
        Trabajador trabajadorOrphanCheck = administrador.getTrabajador();
        if (trabajadorOrphanCheck != null) {
            Administrador oldAdministradorOfTrabajador = trabajadorOrphanCheck.getAdministrador();
            if (oldAdministradorOfTrabajador != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Trabajador " + trabajadorOrphanCheck + " already has an item of type Administrador whose trabajador column cannot be null. Please make another selection for the trabajador field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trabajador trabajador = administrador.getTrabajador();
            if (trabajador != null) {
                trabajador = em.getReference(trabajador.getClass(), trabajador.getCcUsuario());
                administrador.setTrabajador(trabajador);
            }
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : administrador.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getIdCompra());
                attachedCompraList.add(compraListCompraToAttach);
            }
            administrador.setCompraList(attachedCompraList);
            em.persist(administrador);
            if (trabajador != null) {
                trabajador.setAdministrador(administrador);
                trabajador = em.merge(trabajador);
            }
            for (Compra compraListCompra : administrador.getCompraList()) {
                Administrador oldCcUsuarioOfCompraListCompra = compraListCompra.getCcUsuario();
                compraListCompra.setCcUsuario(administrador);
                compraListCompra = em.merge(compraListCompra);
                if (oldCcUsuarioOfCompraListCompra != null) {
                    oldCcUsuarioOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldCcUsuarioOfCompraListCompra = em.merge(oldCcUsuarioOfCompraListCompra);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdministrador(administrador.getCcUsuario()) != null) {
                throw new PreexistingEntityException("Administrador " + administrador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Administrador administrador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador persistentAdministrador = em.find(Administrador.class, administrador.getCcUsuario());
            Trabajador trabajadorOld = persistentAdministrador.getTrabajador();
            Trabajador trabajadorNew = administrador.getTrabajador();
            List<Compra> compraListOld = persistentAdministrador.getCompraList();
            List<Compra> compraListNew = administrador.getCompraList();
            List<String> illegalOrphanMessages = null;
            if (trabajadorNew != null && !trabajadorNew.equals(trabajadorOld)) {
                Administrador oldAdministradorOfTrabajador = trabajadorNew.getAdministrador();
                if (oldAdministradorOfTrabajador != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Trabajador " + trabajadorNew + " already has an item of type Administrador whose trabajador column cannot be null. Please make another selection for the trabajador field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (trabajadorNew != null) {
                trabajadorNew = em.getReference(trabajadorNew.getClass(), trabajadorNew.getCcUsuario());
                administrador.setTrabajador(trabajadorNew);
            }
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getIdCompra());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            administrador.setCompraList(compraListNew);
            administrador = em.merge(administrador);
            if (trabajadorOld != null && !trabajadorOld.equals(trabajadorNew)) {
                trabajadorOld.setAdministrador(null);
                trabajadorOld = em.merge(trabajadorOld);
            }
            if (trabajadorNew != null && !trabajadorNew.equals(trabajadorOld)) {
                trabajadorNew.setAdministrador(administrador);
                trabajadorNew = em.merge(trabajadorNew);
            }
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    compraListOldCompra.setCcUsuario(null);
                    compraListOldCompra = em.merge(compraListOldCompra);
                }
            }
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    Administrador oldCcUsuarioOfCompraListNewCompra = compraListNewCompra.getCcUsuario();
                    compraListNewCompra.setCcUsuario(administrador);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldCcUsuarioOfCompraListNewCompra != null && !oldCcUsuarioOfCompraListNewCompra.equals(administrador)) {
                        oldCcUsuarioOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldCcUsuarioOfCompraListNewCompra = em.merge(oldCcUsuarioOfCompraListNewCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = administrador.getCcUsuario();
                if (findAdministrador(id) == null) {
                    throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.");
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
            Administrador administrador;
            try {
                administrador = em.getReference(Administrador.class, id);
                administrador.getCcUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.", enfe);
            }
            Trabajador trabajador = administrador.getTrabajador();
            if (trabajador != null) {
                trabajador.setAdministrador(null);
                trabajador = em.merge(trabajador);
            }
            List<Compra> compraList = administrador.getCompraList();
            for (Compra compraListCompra : compraList) {
                compraListCompra.setCcUsuario(null);
                compraListCompra = em.merge(compraListCompra);
            }
            em.remove(administrador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Administrador> findAdministradorEntities() {
        return findAdministradorEntities(true, -1, -1);
    }

    public List<Administrador> findAdministradorEntities(int maxResults, int firstResult) {
        return findAdministradorEntities(false, maxResults, firstResult);
    }

    private List<Administrador> findAdministradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administrador.class));
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

    public Administrador findAdministrador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administrador.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdministradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administrador> rt = cq.from(Administrador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
