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
import edu.unicauca.apliweb.persistence.entities.Categoria;
import edu.unicauca.apliweb.persistence.entities.RegistroDevolucion;
import edu.unicauca.apliweb.persistence.entities.Proveedor;
import java.util.ArrayList;
import java.util.List;
import edu.unicauca.apliweb.persistence.entities.Compra;
import edu.unicauca.apliweb.persistence.entities.Producto;
import edu.unicauca.apliweb.persistence.jpa.exceptions.IllegalOrphanException;
import edu.unicauca.apliweb.persistence.jpa.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getProveedorList() == null) {
            producto.setProveedorList(new ArrayList<Proveedor>());
        }
        if (producto.getCompraList() == null) {
            producto.setCompraList(new ArrayList<Compra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria codCategoria = producto.getCodCategoria();
            if (codCategoria != null) {
                codCategoria = em.getReference(codCategoria.getClass(), codCategoria.getCodCategoria());
                producto.setCodCategoria(codCategoria);
            }
            RegistroDevolucion codDevolucion = producto.getCodDevolucion();
            if (codDevolucion != null) {
                codDevolucion = em.getReference(codDevolucion.getClass(), codDevolucion.getCodDevolucion());
                producto.setCodDevolucion(codDevolucion);
            }
            List<Proveedor> attachedProveedorList = new ArrayList<Proveedor>();
            for (Proveedor proveedorListProveedorToAttach : producto.getProveedorList()) {
                proveedorListProveedorToAttach = em.getReference(proveedorListProveedorToAttach.getClass(), proveedorListProveedorToAttach.getCodProveedor());
                attachedProveedorList.add(proveedorListProveedorToAttach);
            }
            producto.setProveedorList(attachedProveedorList);
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : producto.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getIdCompra());
                attachedCompraList.add(compraListCompraToAttach);
            }
            producto.setCompraList(attachedCompraList);
            em.persist(producto);
            if (codCategoria != null) {
                codCategoria.getProductoList().add(producto);
                codCategoria = em.merge(codCategoria);
            }
            if (codDevolucion != null) {
                codDevolucion.getProductoList().add(producto);
                codDevolucion = em.merge(codDevolucion);
            }
            for (Proveedor proveedorListProveedor : producto.getProveedorList()) {
                proveedorListProveedor.getProductoList().add(producto);
                proveedorListProveedor = em.merge(proveedorListProveedor);
            }
            for (Compra compraListCompra : producto.getCompraList()) {
                Producto oldCodProductoOfCompraListCompra = compraListCompra.getCodProducto();
                compraListCompra.setCodProducto(producto);
                compraListCompra = em.merge(compraListCompra);
                if (oldCodProductoOfCompraListCompra != null) {
                    oldCodProductoOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldCodProductoOfCompraListCompra = em.merge(oldCodProductoOfCompraListCompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getCodProducto());
            Categoria codCategoriaOld = persistentProducto.getCodCategoria();
            Categoria codCategoriaNew = producto.getCodCategoria();
            RegistroDevolucion codDevolucionOld = persistentProducto.getCodDevolucion();
            RegistroDevolucion codDevolucionNew = producto.getCodDevolucion();
            List<Proveedor> proveedorListOld = persistentProducto.getProveedorList();
            List<Proveedor> proveedorListNew = producto.getProveedorList();
            List<Compra> compraListOld = persistentProducto.getCompraList();
            List<Compra> compraListNew = producto.getCompraList();
            List<String> illegalOrphanMessages = null;
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraListOldCompra + " since its codProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codCategoriaNew != null) {
                codCategoriaNew = em.getReference(codCategoriaNew.getClass(), codCategoriaNew.getCodCategoria());
                producto.setCodCategoria(codCategoriaNew);
            }
            if (codDevolucionNew != null) {
                codDevolucionNew = em.getReference(codDevolucionNew.getClass(), codDevolucionNew.getCodDevolucion());
                producto.setCodDevolucion(codDevolucionNew);
            }
            List<Proveedor> attachedProveedorListNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorListNewProveedorToAttach : proveedorListNew) {
                proveedorListNewProveedorToAttach = em.getReference(proveedorListNewProveedorToAttach.getClass(), proveedorListNewProveedorToAttach.getCodProveedor());
                attachedProveedorListNew.add(proveedorListNewProveedorToAttach);
            }
            proveedorListNew = attachedProveedorListNew;
            producto.setProveedorList(proveedorListNew);
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getIdCompra());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            producto.setCompraList(compraListNew);
            producto = em.merge(producto);
            if (codCategoriaOld != null && !codCategoriaOld.equals(codCategoriaNew)) {
                codCategoriaOld.getProductoList().remove(producto);
                codCategoriaOld = em.merge(codCategoriaOld);
            }
            if (codCategoriaNew != null && !codCategoriaNew.equals(codCategoriaOld)) {
                codCategoriaNew.getProductoList().add(producto);
                codCategoriaNew = em.merge(codCategoriaNew);
            }
            if (codDevolucionOld != null && !codDevolucionOld.equals(codDevolucionNew)) {
                codDevolucionOld.getProductoList().remove(producto);
                codDevolucionOld = em.merge(codDevolucionOld);
            }
            if (codDevolucionNew != null && !codDevolucionNew.equals(codDevolucionOld)) {
                codDevolucionNew.getProductoList().add(producto);
                codDevolucionNew = em.merge(codDevolucionNew);
            }
            for (Proveedor proveedorListOldProveedor : proveedorListOld) {
                if (!proveedorListNew.contains(proveedorListOldProveedor)) {
                    proveedorListOldProveedor.getProductoList().remove(producto);
                    proveedorListOldProveedor = em.merge(proveedorListOldProveedor);
                }
            }
            for (Proveedor proveedorListNewProveedor : proveedorListNew) {
                if (!proveedorListOld.contains(proveedorListNewProveedor)) {
                    proveedorListNewProveedor.getProductoList().add(producto);
                    proveedorListNewProveedor = em.merge(proveedorListNewProveedor);
                }
            }
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    Producto oldCodProductoOfCompraListNewCompra = compraListNewCompra.getCodProducto();
                    compraListNewCompra.setCodProducto(producto);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldCodProductoOfCompraListNewCompra != null && !oldCodProductoOfCompraListNewCompra.equals(producto)) {
                        oldCodProductoOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldCodProductoOfCompraListNewCompra = em.merge(oldCodProductoOfCompraListNewCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getCodProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getCodProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Compra> compraListOrphanCheck = producto.getCompraList();
            for (Compra compraListOrphanCheckCompra : compraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Compra " + compraListOrphanCheckCompra + " in its compraList field has a non-nullable codProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categoria codCategoria = producto.getCodCategoria();
            if (codCategoria != null) {
                codCategoria.getProductoList().remove(producto);
                codCategoria = em.merge(codCategoria);
            }
            RegistroDevolucion codDevolucion = producto.getCodDevolucion();
            if (codDevolucion != null) {
                codDevolucion.getProductoList().remove(producto);
                codDevolucion = em.merge(codDevolucion);
            }
            List<Proveedor> proveedorList = producto.getProveedorList();
            for (Proveedor proveedorListProveedor : proveedorList) {
                proveedorListProveedor.getProductoList().remove(producto);
                proveedorListProveedor = em.merge(proveedorListProveedor);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
