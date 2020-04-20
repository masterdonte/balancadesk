package com.donte.scale.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.donte.scale.dao.util.GenericDAO;
import com.donte.scale.dao.util.JPAUtil;
import com.donte.scale.model.Viagem;
import com.donte.scale.util.Aplicacao;

public class ViagemDAO extends GenericDAO<Viagem> {
	
	public ViagemDAO(Class<Viagem> clazz) {
		super(clazz);	
	}

	public ViagemDAO() {
		this(Viagem.class);
	}
	
	public List<Viagem> findViagensComNaviosAtracados() {
		EntityManager em = JPAUtil.getEntityManager();
		List<Viagem> list = null;
		try{
			Integer statusConfirmado = new Integer(3);
			TypedQuery<Viagem> query = em.createQuery("from Viagem v where v.statusViagem = :pStatus AND v.dataPartida IS NULL AND v.dataAtracacao IS NOT NULL", Viagem.class);
			query.setParameter("pStatus", statusConfirmado);
			list = query.getResultList();
		}catch(Exception e){
			Aplicacao.errorMsg(null, e.getMessage());
		}finally{
			em.close();
		}		
		return list;
	}
	
}
