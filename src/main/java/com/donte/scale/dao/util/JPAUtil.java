package com.donte.scale.dao.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("balmssql");
	//private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("balmysql");	
		
	public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
	
	public static void shutdownEntityManagerFactory() {
        entityManagerFactory.close();
    }
}
