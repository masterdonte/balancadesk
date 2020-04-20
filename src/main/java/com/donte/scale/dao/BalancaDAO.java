package com.donte.scale.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.donte.scale.dao.util.GenericDAO;
import com.donte.scale.dao.util.JPAUtil;
import com.donte.scale.model.Balanca;
import com.donte.scale.model.Veiculo;

public class BalancaDAO extends GenericDAO<Balanca> {

	public BalancaDAO(Class<Balanca> clazz) {
		super(clazz);		
	}
	
	public BalancaDAO() {
		this(Balanca.class);		
	}
	
	public Balanca getRegistroComTaraSemPesoCheio(Veiculo veiculo) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Balanca> query = em.createQuery("select b from Balanca b where b.veiculo = :pVeiculo and b.tara > 0 and b.pesoCheio = 0", Balanca.class);		
		query.setParameter("pVeiculo", veiculo);		
				
		Balanca balanca = null;

		try {
			balanca = query.getSingleResult();
		} catch (NoResultException ex) {
			balanca = null;
		}finally {
			em.close();
		}

		return balanca;
		
	}
	
}
