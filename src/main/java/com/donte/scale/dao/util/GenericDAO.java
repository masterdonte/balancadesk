package com.donte.scale.dao.util;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.donte.scale.model.EntidadeBase;

public class GenericDAO <T extends EntidadeBase> {

	private Class<T> clazz = null;

	public GenericDAO(Class<T> clazz){
		this.clazz = clazz;
	}

	public T salvar(T t){
		EntityManager em = JPAUtil.getEntityManager();
		try{			
			em.getTransaction().begin();
			if(t.getId() == null){
				em.persist(t);
			}else{
				if(!em.contains(t)){
					if(em.find(clazz, t.getId()) == null){
						throw new Exception("Erro ao atualizar entidade");
					}
				}
				t = em.merge(t);
			}			
			em.getTransaction().commit();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally{
			em.close();
		}
		return t;
	}

	public void remover(Long id){
		EntityManager em = JPAUtil.getEntityManager();
		T t = em.find(clazz, id);
		try{			
			em.getTransaction().begin();			
			em.remove(t);
			em.getTransaction().commit();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally{
			em.close();
		}
	}

	public T consultaPorId(Long id){
		EntityManager em = JPAUtil.getEntityManager();
		T t = null;
		try{			
			t = em.find(clazz, id);
		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally{
			em.close();
		}
		return t;
	}

	public List<?> listaTodos(){
		EntityManager em = JPAUtil.getEntityManager();
		List<?> list = null;
		try{			
			list = em.createQuery("Select t from " + clazz.getSimpleName() + " t").getResultList();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally{
			em.close();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	public List<T> findByExample(final T entity) {
		EntityManager em = JPAUtil.getEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		final Example example = Example.create(entity).excludeZeroes().enableLike();
		List<T> list = null;
		try{			
			list = session.createCriteria(this.clazz).add(example).list();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally{			
			em.close();// session fecha automaticamente ao fechar o em			
		}
		return list;		
	}

	public T findOneByExample(final T example) {
		final List<T> res = findByExample(example);
		if ((res != null) && (res.size() == 1)) {
			return res.get(0);
		} else {
			return null;
		}
	}


}
