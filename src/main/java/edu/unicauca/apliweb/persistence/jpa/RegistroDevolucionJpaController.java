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
import edu.unicauca.apliweb.persistence.entities.Cliente;
import edu.unicauca.apliweb.persistence.entities.Regente;
import edu.unicauca.apliweb.persistence.entities.Producto;
import edu.unicauca.apliweb.persistence.entities.RegistroDevolucion;
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
public class RegistroDevolucionJpaController implements Serializable {

    public RegistroDevolucionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroDevolucion registroDevolucion) throws PreexistingEntityException, Exception {
        if (registroDevolucion.getProductoList() == null) {
            registroDevolucion.setProductoList(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente ccCliente = registroDevolucion.getCcCliente();
            if (ccCliente != null) {
                ccCliente = em.getReference(ccCliente.getClass(), ccCliente.getCcCliente());
                registroDevolucion.setCcCliente(ccCliente);
            }
            Regente ccUsuario = registroDevolucion.getCcUsuario();
            if (ccUsuario != null) {
                ccUsuario = em.getReference(ccUsuario.getClass(), ccUsuario.getCcUsuario());
                registroDevolucion.setCcUsuario(ccUsuario);
            }
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : registroDevolucion.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getCodProducto());
                attachedProductoList.add(productoListProductoToAttach);
            }
            registroDevolucion.setProductoList(attachedProductoList);
            em.persist(registroDevolucion);
            if (ccCliente != null) {
                ccCliente.getRegistroDevolucionList().add(registroDevolucion);
                ccCliente = em.merge(ccCliente);
            }
            if (ccUsuario != null) {
                ccUsuario.getRegistroDevolucionList().add(registroDevolucion);
                ccUsuario = em.merge(ccUsuario);
            }
            for (Producto productoListProducto : registroDevolucion.getProductoList()) {
                RegistroDevolucion oldCodDevolucionOfProductoListProducto = productoListProducto.getCodDevolucion();
                productoListProducto.setCodDevolucion(registroDevolucion);
                productoListProducto = em.merge(productoListProducto);
                if (oldCodDevolucionOfProductoListProducto != null) {
                    oldCodDevolucionOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldCodDevolucionOfProductoListProducto = em.merge(oldCodDevolucionOfProductoListProducto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegistroDevolucion(registroDevolucion.getCodDevolucion()) != null) {
                throw new PreexistingEntityException("RegistroDevolucion " + registroDevolucion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegistroDevolucion registroDevolucion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegistroDevolucion persistentRegistroDevolucion = em.find(RegistroDevolucion.class, registroDevolucion.getCodDevolucion());
            Cliente ccClienteOld = persistentRegistroDevolucion.getCcCliente();
            Cliente ccClienteNew = registroDevolucion.getCcCliente();
            Regente ccUsuarioOld = persistentRegistroDevolucion.getCcUsuario();
            Regente ccUsuarioNew = registroDevolucion.getCcUsuario();
            List<Producto> productoListOld = persistentRegistroDevolucion.getProductoList();
            List<Producto> productoListNew = registroDevolucion.getProductoList();
            if (ccClienteNew != null) {
                ccClienteNew = em.getReference(ccClienteNew.getClass(), ccClienteNew.getCcCliente());
                registroDevolucion.setCcCliente(ccClienteNew);
            }
            if (ccUsuarioNew != null) {
                ccUsuarioNew = em.getReference(ccUsuarioNew.getClass(), ccUsuarioNew.getCcUsuario());
                registroDevolucion.setCcUsuario(ccUsuarioNew);
            }
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getCodProducto());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            registroDevolucion.setProductoList(productoListNew);
            registroDevolucion = em.merge(registroDevolucion);
            if (ccClienteOld != null && !ccClienteOld.equals(ccClienteNew)) {
                ccClienteOld.getRegistroDevolucionList().remove(registroDevolucion);
                ccClienteOld = em.merge(ccClienteOld);
            }
            if (ccClienteNew != null && !ccClienteNew.equals(ccClienteOld)) {
                ccClienteNew.getRegistroDevolucionList().add(registroDevolucion);
                ccClienteNew = em.merge(ccClienteNew);
            }
            if (ccUsuarioOld != null && !ccUsuarioOld.equals(ccUsuarioNew)) {
                ccUsuarioOld.getRegistroDevolucionList().remove(registroDevolucion);
                ccUsuarioOld = em.merge(ccUsuarioOld);
            }
            if (ccUsuarioNew != null && !ccUsuarioNew.equals(ccUsuarioOld)) {
                ccUsuarioNew.getRegistroDevolucionList().add(registroDevolucion);
                ccUsuarioNew = em.merge(ccUsuarioNew);
            }
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    productoListOldProducto.setCodDevolucion(null);
                    productoListOldProducto = em.merge(productoListOldProducto);
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    RegistroDevolucion oldCodDevolucionOfProductoListNewProducto = productoListNewProducto.getCodDevolucion();
                    productoListNewProducto.setCodDevolucion(registroDevolucion);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldCodDevolucionOfProductoListNewProducto != null && !oldCodDevolucionOfProductoListNewProducto.equals(registroDevolucion)) {
                        oldCodDevolucionOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldCodDevolucionOfProductoListNewProducto = em.merge(oldCodDevolucionOfProductoListNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = registroDevolucion.getCodDevolucion();
                if (findRegistroDevolucion(id) == null) {
                    throw new NonexistentEntityException("The registroDevolucion with id " + id + " no longer exists.");
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
            RegistroDevolucion registroDevolucion;
            try {
                registroDevolucion = em.getReference(RegistroDevolucion.class, id);
                registroDevolucion.getCodDevolucion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroDevolucion with id " + id + " no longer exists.", enfe);
            }
            Cliente ccCliente = registroDevolucion.getCcCliente();
            if (ccCliente != null) {
                ccCliente.getRegistroDevolucionList().remove(registroDevolucion);
                ccCliente = em.merge(ccCliente);
            }
            Regente ccUsuario = registroDevolucion.getCcUsuario();
            if (ccUsuario != null) {
                ccUsuario.getRegistroDevolucionList().remove(registroDevolucion);
                ccUsuario = em.merge(ccUsuario);
            }
            List<Producto> productoList = registroDevolucion.getProductoList();
            for (Producto productoListProducto : productoList) {
                productoListProducto.setCodDevolucion(null);
                productoListProducto = em.merge(productoListProducto);
            }
            em.remove(registroDevolucion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegistroDevolucion> findRegistroDevolucionEntities() {
        return findRegistroDevolucionEntities(true, -1, -1);
    }

    public List<RegistroDevolucion> findRegistroDevolucionEntities(int maxResults, int firstResult) {
        return findRegistroDevolucionEntities(false, maxResults, firstResult);
    }

    private List<RegistroDevolucion> findRegistroDevolucionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegistroDevolucion.class));
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

    public RegistroDevolucion findRegistroDevolucion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegistroDevolucion.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroDevolucionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegistroDevolucion> rt = cq.from(RegistroDevolucion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
