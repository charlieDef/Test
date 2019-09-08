package com.materials.server;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

public class EntityManager_Access {

	private static final ThreadLocal<EntityManager_Access> FACTORY_ACCESSOR = new ThreadLocal<EntityManager_Access>();

	private EntityManager entityManager;
	private HttpServletRequest httpServletRequest;

	private EntityManager_Access() {
	}

	public static EntityManager_Access getInstance() {
		if (FACTORY_ACCESSOR.get() == null) {
			FACTORY_ACCESSOR.set(new EntityManager_Access());
		}
		return FACTORY_ACCESSOR.get();
	}

	public static void removeInstance() {
		// synchronization between get() and remove() methods is not required as
		// all method calls occur in the same thread
		FACTORY_ACCESSOR.remove();

	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

}