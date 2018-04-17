package ch.gibm.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

abstract class GenericDAO<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Class<T> entityClass;

	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void save(T entity) {
		try {
			EntityManagerHelper.getEntityManager().persist(entity);
		} catch (PersistenceException e) {
			EntityManagerHelper.rollback();
			System.out.println("A Problem occured while saving. Details:");
			e.printStackTrace();
		}
	}

	public void delete(Object id, Class<T> classe) {
		try {
			EntityManager em = EntityManagerHelper.getEntityManager();
			T entityToBeRemoved = em.getReference(classe, id);
			em.remove(entityToBeRemoved);
		} catch (PersistenceException e) {
			System.out.println("A Problem occured while deleting. Details:");
			e.printStackTrace();
		}
	}

	public T update(T entity) {
		T persistedEntity = null;
		try {
			persistedEntity = EntityManagerHelper.getEntityManager().merge(entity);
		} catch (PersistenceException e) {
			System.out.println("A Problem occured while updating. Details:");
			e.printStackTrace();
		}
		return persistedEntity;
	}

	public T find(int entityID) {
		T persistedEntity = null;
		try {
			persistedEntity = EntityManagerHelper.getEntityManager().find(entityClass, entityID);
		} catch (PersistenceException e) {
			System.out.println("A Problem occured while finding. Details:");
			e.printStackTrace();
		}
		return persistedEntity;
	}

	public T findReferenceOnly(int entityID) {
		T persistedEntity = null;
		try {
			persistedEntity = EntityManagerHelper.getEntityManager().getReference(entityClass, entityID);
		} catch (PersistenceException e) {
			System.out.println("A Problem occured while finding Reference. Details:");
			e.printStackTrace();
		}
		return persistedEntity;
	}

	public List<T> findAll() {
		List<T> persistedResultList = null;
		try {
			EntityManager em = EntityManagerHelper.getEntityManager();
			CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(this.entityClass);
			cq.select(cq.from(entityClass));
			persistedResultList = em.createQuery(cq).getResultList();
		} catch (PersistenceException e) {
			System.out.println("A Problem occured while finding ResultList. Details:");
			e.printStackTrace();
		}
		return persistedResultList;
	}

	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;
		EntityManager em = EntityManagerHelper.getEntityManager();

		try {
			TypedQuery<T> query = em.createNamedQuery(namedQuery, this.entityClass);

			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = query.getSingleResult();

		} catch (NoResultException e) {
			System.out.println("No result found for named query: " + namedQuery);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
}
