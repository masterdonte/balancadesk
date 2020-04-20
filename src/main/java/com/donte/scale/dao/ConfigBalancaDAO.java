package com.donte.scale.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.donte.scale.dao.util.GenericDAO;
import com.donte.scale.dao.util.JPAUtil;
import com.donte.scale.model.ConfigBalanca;

public class ConfigBalancaDAO extends GenericDAO<ConfigBalanca> {
	
	private ConfigBalancaDAO(Class<ConfigBalanca> clazz) {
		super(clazz);	
	}
	
	public ConfigBalancaDAO() {
		this(ConfigBalanca.class);
	}

	public ConfigBalanca findByNomeBalanca(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<ConfigBalanca> query = em.createQuery("select c from ConfigBalanca c where c.balanca = :pNome", ConfigBalanca.class);		
		query.setParameter("pNome", nome);		
		ConfigBalanca config = null;
		try {
			config = query.getSingleResult();
		} catch (NoResultException ex) {
			config = null;
		}finally {
			em.close();
		}
		return config;
	}
	
	public ConfigBalanca findByHost(String host) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<ConfigBalanca> query = em.createQuery("select c from ConfigBalanca c where c.macHost = :pHost", ConfigBalanca.class);		
		query.setParameter("pHost", host);		
		ConfigBalanca config = null;
		try {
			config = query.getSingleResult();
		} catch (NoResultException ex) {
			config = consultaPorId(ConfigBalanca.BALANCA_TESTE);
		}finally {
			em.close();
		}
		return config;
	}

}