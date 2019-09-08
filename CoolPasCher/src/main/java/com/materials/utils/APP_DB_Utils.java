package com.materials.utils;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.materials.server.EntityManager_Access;
import com.materials.server.model.TempFile;

public class APP_DB_Utils {

	private static final Logger logger = LoggerFactory.getLogger(APP_DB_Utils.class);

	public static void saveObjectToDatabase(Object object) {
		EntityManager entityManager = EntityManager_Access.getInstance().getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(object);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			logger.error("saveObjectToDatabase[" + object + "]", e);
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void deleteObjectFromDatabase(long id, Class theClass) {
		EntityManager entityManager = EntityManager_Access.getInstance().getEntityManager();

		try {
			Object toDelete = entityManager.find(theClass, id);
			if (toDelete != null) {
				entityManager.getTransaction().begin();
				entityManager.remove(toDelete);
				entityManager.getTransaction().commit();
			}
		} catch (NoResultException exception) {
			logger.error("NoResultException: deleteObjectFromDatabase[" + id + "]", "");
		} catch (Exception e) {
			logger.error("deleteObjectFromDatabase[" + id + "]", e);
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void refreshObject(long id, Class theClass) {
		EntityManager entityManager = EntityManager_Access.getInstance().getEntityManager();
		try {
			Object object = entityManager.find(theClass, id);
			entityManager.refresh(object);
		} catch (NoResultException exception) {
			logger.error("NoResultException: refreshObject[" + id + "]", exception);
			exception.printStackTrace();
		} catch (Exception e) {
			logger.error("refreshObject[" + id + "]", e);
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static <T> T querySingleObject(String theQuery, Map<String, Object> theQueryParams, Class theClass) {
		EntityManager entityManager = EntityManager_Access.getInstance().getEntityManager();
		T result = null;
		try {
			Query query = entityManager.createQuery(theQuery, theClass);
			if (theQueryParams != null) {
				for (String param : theQueryParams.keySet()) {
					query.setParameter(param, theQueryParams.get(param));
				}
			}
			result = (T) query.getSingleResult();
		} catch (NoResultException exception) {
			logger.error("NoResultException: querySingleObject[" + theQuery + "] " + theQueryParams, "");
		} catch (Exception exception) {
			logger.error("Exception: querySingleObject[" + theQuery + "] " + theQueryParams, exception);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T queryListObjects(String theQuery, Map<String, Object> theQueryParams, Class theClass) {
		EntityManager entityManager = EntityManager_Access.getInstance().getEntityManager();
		T results = null;
		try {
			Query query = entityManager.createQuery(theQuery, theClass);
			if (theQueryParams != null) {
				for (String param : theQueryParams.keySet()) {
					query.setParameter(param, theQueryParams.get(param));
				}
			}
			results = (T) query.getResultList();
		} catch (NoResultException exception) {
			exception.printStackTrace();
			logger.error("NoResultException: queryListObjects[" + theQuery + "] " + theQueryParams, "");
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("queryListObjects[" + theQuery + "]", exception);
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	public static <T> T findObject(long id, Class theClass) {
		EntityManager entityManager = EntityManager_Access.getInstance().getEntityManager();
		T result = null;
		try {
			result = (T) entityManager.find(theClass, id);
		} catch (NoResultException exception) {
			logger.error("NoResultException: findObject[" + id + "]", "");
		} catch (Exception exception) {
			logger.error("findObject[" + id + "]", exception);
		}
		return result;
	}

	public static TempFile getTempFile(String id) {
		TempFile tempFile = APP_DB_Utils.querySingleObject("SELECT e FROM TempFile e WHERE e.randomId = '" + id + "'",
				null, TempFile.class);
		if (tempFile != null) {
			return tempFile;
		}

		return null;
	}

	public static <T> T getObjectByAttribute(String attributeName, String attributeValue, Class theClass) {
		Map<String, Object> param = new HashMap<String, Object>(1);
		param.put("rId", attributeValue);
		T ebObject = APP_DB_Utils.querySingleObject(
				"SELECT e FROM " + theClass.getSimpleName() + " e WHERE e." + attributeName + " = :rId", param,
				theClass);
		if (ebObject != null) {
			return ebObject;
		}

		return null;
	}
}
