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
import edu.unicauca.apliweb.persistence.entities.Trabajador;
import edu.unicauca.apliweb.persistence.entities.RegistroDevolucion;
import java.util.ArrayList;
import java.util.List;
import edu.unicauca.apliweb.persistence.entities.AtencionMedica;
import edu.unicauca.apliweb.persistence.entities.Regente;
import edu.unicauca.apliweb.persistence.jpa.exceptions.IllegalOrphanException;
import edu.unicauca.apliweb.persistence.jpa.exceptions.NonexistentEntityException;
import edu.unicauca.apliweb.persistence.jpa.exceptions.PreexistingEntityException;
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
        if (regente.getRegistroDevolucionList() == null) {
            regente.setRegistroDevolucionList(new ArrayList<RegistroDevolucion>());
        }
        if (regente.getAtencionMedicaList() == null) {
            regente.setAtencionMedicaList(new ArrayList<AtencionMedica>());
        }
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
            List<RegistroDevolucion> attachedRegistroDevolucionList = new ArrayList<RegistroDevolucion>();
            for (RegistroDevolucion registroDevolucionListRegistroDevolucionToAttach : regente.getRegistroDevolucionList()) {
                registroDevolucionListRegistroDevolucionToAttach = em.getReference(registroDevolucionListRegistroDevolucionToAttach.getClass(), registroDevolucionListRegistroDevolucionToAttach.getCodDevolucion());
                attachedRegistroDevolucionList.add(registroDevolucionListRegistroDevolucionToAttach);
            }
            regente.setRegistroDevolucionList(attachedRegistroDevolucionList);
            List<AtencionMedica> attachedAtencionMedicaList = new ArrayList<AtencionMedica>();
            for (AtencionMedica atencionMedicaListAtencionMedicaToAttach : regente.getAtencionMedicaList()) {
                atencionMedicaListAtencionMedicaToAttach = em.getReference(atencionMedicaListAtencionMedicaToAttach.getClass(), atencionMedicaListAtencionMedicaToAttach.getFechaAtmedica());
                attachedAtencionMedicaList.add(atencionMedicaListAtencionMedicaToAttach);
            }
            regente.setAtencionMedicaList(attachedAtencionMedicaList);
            em.persist(regente);
            if (trabajador != null) {
                trabajador.setRegente(regente);
                trabajador = em.merge(trabajador);
            }
            for (RegistroDevolucion registroDevolucionListRegistroDevolucion : regente.getRegistroDevolucionList()) {
                Regente oldCcUsuarioOfRegistroDevolucionListRegistroDevolucion = registroDevolucionListRegistroDevolucion.getCcUsuario();
                registroDevolucionListRegistroDevolucion.setCcUsuario(regente);
                registroDevolucionListRegistroDevolucion = em.merge(registroDevolucionListRegistroDevolucion);
                if (oldCcUsuarioOfRegistroDevolucionListRegistroDevolucion != null) {
                    oldCcUsuarioOfRegistroDevolucionListRegistroDevolucion.getRegistroDevolucionList().remove(registroDevolucionListRegistroDevolucion);
                    oldCcUsuarioOfRegistroDevolucionListRegistroDevolucion = em.merge(oldCcUsuarioOfRegistroDevolucionListRegistroDevolucion);
                }
            }
            for (AtencionMedica atencionMedicaListAtencionMedica : regente.getAtencionMedicaList()) {
                Regente oldCcUsuarioOfAtencionMedicaListAtencionMedica = atencionMedicaListAtencionMedica.getCcUsuario();
                atencionMedicaListAtencionMedica.setCcUsuario(regente);
                atencionMedicaListAtencionMedica = em.merge(atencionMedicaListAtencionMedica);
                if (oldCcUsuarioOfAtencionMedicaListAtencionMedica != null) {
                    oldCcUsuarioOfAtencionMedicaListAtencionMedica.getAtencionMedicaList().remove(atencionMedicaListAtencionMedica);
                    oldCcUsuarioOfAtencionMedicaListAtencionMedica = em.merge(oldCcUsuarioOfAtencionMedicaListAtencionMedica);
                }
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
            List<RegistroDevolucion> registroDevolucionListOld = persistentRegente.getRegistroDevolucionList();
            List<RegistroDevolucion> registroDevolucionListNew = regente.getRegistroDevolucionList();
            List<AtencionMedica> atencionMedicaListOld = persistentRegente.getAtencionMedicaList();
            List<AtencionMedica> atencionMedicaListNew = regente.getAtencionMedicaList();
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
            for (RegistroDevolucion registroDevolucionListOldRegistroDevolucion : registroDevolucionListOld) {
                if (!registroDevolucionListNew.contains(registroDevolucionListOldRegistroDevolucion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RegistroDevolucion " + registroDevolucionListOldRegistroDevolucion + " since its ccUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (trabajadorNew != null) {
                trabajadorNew = em.getReference(trabajadorNew.getClass(), trabajadorNew.getCcUsuario());
                regente.setTrabajador(trabajadorNew);
            }
            List<RegistroDevolucion> attachedRegistroDevolucionListNew = new ArrayList<RegistroDevolucion>();
            for (RegistroDevolucion registroDevolucionListNewRegistroDevolucionToAttach : registroDevolucionListNew) {
                registroDevolucionListNewRegistroDevolucionToAttach = em.getReference(registroDevolucionListNewRegistroDevolucionToAttach.getClass(), registroDevolucionListNewRegistroDevolucionToAttach.getCodDevolucion());
                attachedRegistroDevolucionListNew.add(registroDevolucionListNewRegistroDevolucionToAttach);
            }
            registroDevolucionListNew = attachedRegistroDevolucionListNew;
            regente.setRegistroDevolucionList(registroDevolucionListNew);
            List<AtencionMedica> attachedAtencionMedicaListNew = new ArrayList<AtencionMedica>();
            for (AtencionMedica atencionMedicaListNewAtencionMedicaToAttach : atencionMedicaListNew) {
                atencionMedicaListNewAtencionMedicaToAttach = em.getReference(atencionMedicaListNewAtencionMedicaToAttach.getClass(), atencionMedicaListNewAtencionMedicaToAttach.getFechaAtmedica());
                attachedAtencionMedicaListNew.add(atencionMedicaListNewAtencionMedicaToAttach);
            }
            atencionMedicaListNew = attachedAtencionMedicaListNew;
            regente.setAtencionMedicaList(atencionMedicaListNew);
            regente = em.merge(regente);
            if (trabajadorOld != null && !trabajadorOld.equals(trabajadorNew)) {
                trabajadorOld.setRegente(null);
                trabajadorOld = em.merge(trabajadorOld);
            }
            if (trabajadorNew != null && !trabajadorNew.equals(trabajadorOld)) {
                trabajadorNew.setRegente(regente);
                trabajadorNew = em.merge(trabajadorNew);
            }
            for (RegistroDevolucion registroDevolucionListNewRegistroDevolucion : registroDevolucionListNew) {
                if (!registroDevolucionListOld.contains(registroDevolucionListNewRegistroDevolucion)) {
                    Regente oldCcUsuarioOfRegistroDevolucionListNewRegistroDevolucion = registroDevolucionListNewRegistroDevolucion.getCcUsuario();
                    registroDevolucionListNewRegistroDevolucion.setCcUsuario(regente);
                    registroDevolucionListNewRegistroDevolucion = em.merge(registroDevolucionListNewRegistroDevolucion);
                    if (oldCcUsuarioOfRegistroDevolucionListNewRegistroDevolucion != null && !oldCcUsuarioOfRegistroDevolucionListNewRegistroDevolucion.equals(regente)) {
                        oldCcUsuarioOfRegistroDevolucionListNewRegistroDevolucion.getRegistroDevolucionList().remove(registroDevolucionListNewRegistroDevolucion);
                        oldCcUsuarioOfRegistroDevolucionListNewRegistroDevolucion = em.merge(oldCcUsuarioOfRegistroDevolucionListNewRegistroDevolucion);
                    }
                }
            }
            for (AtencionMedica atencionMedicaListOldAtencionMedica : atencionMedicaListOld) {
                if (!atencionMedicaListNew.contains(atencionMedicaListOldAtencionMedica)) {
                    atencionMedicaListOldAtencionMedica.setCcUsuario(null);
                    atencionMedicaListOldAtencionMedica = em.merge(atencionMedicaListOldAtencionMedica);
                }
            }
            for (AtencionMedica atencionMedicaListNewAtencionMedica : atencionMedicaListNew) {
                if (!atencionMedicaListOld.contains(atencionMedicaListNewAtencionMedica)) {
                    Regente oldCcUsuarioOfAtencionMedicaListNewAtencionMedica = atencionMedicaListNewAtencionMedica.getCcUsuario();
                    atencionMedicaListNewAtencionMedica.setCcUsuario(regente);
                    atencionMedicaListNewAtencionMedica = em.merge(atencionMedicaListNewAtencionMedica);
                    if (oldCcUsuarioOfAtencionMedicaListNewAtencionMedica != null && !oldCcUsuarioOfAtencionMedicaListNewAtencionMedica.equals(regente)) {
                        oldCcUsuarioOfAtencionMedicaListNewAtencionMedica.getAtencionMedicaList().remove(atencionMedicaListNewAtencionMedica);
                        oldCcUsuarioOfAtencionMedicaListNewAtencionMedica = em.merge(oldCcUsuarioOfAtencionMedicaListNewAtencionMedica);
                    }
                }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<RegistroDevolucion> registroDevolucionListOrphanCheck = regente.getRegistroDevolucionList();
            for (RegistroDevolucion registroDevolucionListOrphanCheckRegistroDevolucion : registroDevolucionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Regente (" + regente + ") cannot be destroyed since the RegistroDevolucion " + registroDevolucionListOrphanCheckRegistroDevolucion + " in its registroDevolucionList field has a non-nullable ccUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Trabajador trabajador = regente.getTrabajador();
            if (trabajador != null) {
                trabajador.setRegente(null);
                trabajador = em.merge(trabajador);
            }
            List<AtencionMedica> atencionMedicaList = regente.getAtencionMedicaList();
            for (AtencionMedica atencionMedicaListAtencionMedica : atencionMedicaList) {
                atencionMedicaListAtencionMedica.setCcUsuario(null);
                atencionMedicaListAtencionMedica = em.merge(atencionMedicaListAtencionMedica);
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
