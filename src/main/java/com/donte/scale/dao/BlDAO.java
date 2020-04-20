package com.donte.scale.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.donte.scale.dao.util.GenericDAO;
import com.donte.scale.dao.util.JPAUtil;
import com.donte.scale.model.Bl;
import com.donte.scale.model.Viagem;
import com.donte.scale.util.Aplicacao;

public class BlDAO extends GenericDAO<Bl> {

	public BlDAO(Class<Bl> clazz) {
		super(clazz);	
	}
	
	public BlDAO() {
		this(Bl.class);	
	}
	
	public List<Bl> findProdutosDaViagem(Viagem viagem) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Bl> list = new ArrayList<>();
		try{		
			TypedQuery<Bl> query = em.createQuery("from Bl b where b.viagem = :pViagem", Bl.class);
			query.setParameter("pViagem", viagem);
			list = query.getResultList();
		}catch(Exception e){
			Aplicacao.errorMsg(null, e.getMessage());
		}finally{
			em.close();
		}		
		return list;
	}

}
