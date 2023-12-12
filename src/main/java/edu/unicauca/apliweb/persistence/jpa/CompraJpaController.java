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
import edu.unicauca.apliweb.persistence.entities.Compra;
import edu.unicauca.apliweb.persistence.entities.Producto;
import edu.unicauca.apliweb.persistence.jpa.exceptions.NonexistentEntityException;
import edu.unicauca.apliweb.persistence.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class CompraJpaController implements Serializable {

    public CompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compra compra) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador ccUsuario = compra.getCcUsuario();
            if (ccUsuario != null) {
                ccUsuario = em.getReference(ccUsuario.getClass(), ccUsuario.getCcUsuario());
                compra.setCcUsuario(ccUsuario);
            }
            Producto codProducto = compra.getCodProducto();
            if (codProducto != null) {
                codProducto = em.getReference(codProducto.getClass(), codProducto.getCodProducto());
                compra.setCodProducto(codProducto);
            }
            em.persist(compra);
            if (ccUsuario != null) {
                ccUsuario.getCompraList().add(compra);
                ccUsuario = em.merge(ccUsuario);
            }
            if (codProducto != null) {
                codProducto.getCompraList().add(compra);
                codProducto = em.merge(codProducto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCompra(compra.getIdCompra()) != null) {
                throw new PreexistingEntityException("Compra " + compra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compra compra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra persistentCompra = em.find(Compra.class, compra.getIdCompra());
            Administrador ccUsuarioOld = persistentCompra.getCcUsuario();
            Administrador ccUsuarioNew = compra.getCcUsuario();
            Producto codProductoOld = persistentCompra.getCodProducto();
            Producto codProductoNew = compra.getCodProducto();
            if (ccUsuarioNew != null) {
                ccUsuarioNew = em.getReference(ccUsuarioNew.getClass(), ccUsuarioNew.getCcUsuario());
                compra.setCcUsuario(ccUsuarioNew);
            }
            if (codProductoNew != null) {
                codProductoNew = em.getReference(codProductoNew.getClass(), codProductoNew.getCodProducto());
                compra.setCodProducto(codProductoNew);
            }
            compra = em.merge(compra);
            if (ccUsuarioOld != null && !ccUsuarioOld.equals(ccUsuarioNew)) {
                ccUsuarioOld.getCompraList().remove(compra);
                ccUsuarioOld = em.merge(ccUsuarioOld);
            }
            if (ccUsuarioNew != null && !ccUsuarioNew.equals(ccUsuarioOld)) {
                ccUsuarioNew.getCompraList().add(compra);
                ccUsuarioNew = em.merge(ccUsuarioNew);
            }
            if (codProductoOld != null && !codProductoOld.equals(codProductoNew)) {
                codProductoOld.getCompraList().remove(compra);
                codProductoOld = em.merge(codProductoOld);
            }
            if (codProductoNew != null && !codProductoNew.equals(codProductoOld)) {
                codProductoNew.getCompraList().add(compra);
                codProductoNew = em.merge(codProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compra.getIdCompra();
                if (findCompra(id) == null) {
                    throw new NonexistentEntityException("The compra with id " + id + " no longer exists.");
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
            Compra compra;
            try {
                compra = em.getReference(Compra.class, id);
                compra.getIdCompra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compra with id " + id + " no longer exists.", enfe);
            }
            Administrador ccUsuario = compra.getCcUsuario();
            if (ccUsuario != null) {
                ccUsuario.getCompraList().remove(compra);
                ccUsuario = em.merge(ccUsuario);
            }
            Producto codProducto = compra.getCodProducto();
            if (codProducto != null) {
                codProducto.getCompraList().remove(compra);
                codProducto = em.merge(codProducto);
            }
            em.remove(compra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compra> findCompraEntities() {
        return findCompraEntities(true, -1, -1);
    }

    public List<Compra> findCompraEntities(int maxResults, int firstResult) {
        return findCompraEntities(false, maxResults, firstResult);
    }

    private List<Compra> findCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compra.class));
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

    public Compra findCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compra.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compra> rt = cq.from(Compra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
