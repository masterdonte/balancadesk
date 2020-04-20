package com.donte.scale.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.donte.scale.dao.util.GenericDAO;
import com.donte.scale.dao.util.JPAUtil;
import com.donte.scale.model.Turno;

public class TurnoDAO extends GenericDAO<Turno> {
	
	public TurnoDAO(Class<Turno> clazz) {
		super(clazz);	
	}

	public TurnoDAO() {
		this(Turno.class);
	}

	public Turno findTurnoByHora(String horaAtual) {		
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Turno> query = em.createQuery("select t from Turno t WHERE t.id = DBO.FNC_TURNO_BALANCA_ID(:pHora)", Turno.class);	
		query.setParameter("pHora", horaAtual);		
		Turno turno = null;
		try {
			turno = query.getSingleResult();
		} catch (NoResultException ex) {
			turno = null;
		}finally {
			em.close();
		}
		return turno;
	}
	
}

