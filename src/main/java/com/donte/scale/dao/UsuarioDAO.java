package com.donte.scale.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.donte.scale.dao.util.GenericDAO;
import com.donte.scale.dao.util.JPAUtil;
import com.donte.scale.model.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario> {

	public UsuarioDAO(Class<Usuario> clazz) {
		super(clazz);	
	}

	public UsuarioDAO() {
		this(Usuario.class);
	}

	public Usuario findUsuarioByLoginESenha(String login, String senha) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.login = :pLogin and u.senha = :pSenha", Usuario.class);
		//TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.login = :pLogin", Usuario.class);

		query.setParameter("pLogin", login);
		query.setParameter("pSenha", senha);

		Usuario user = null;

		try {
			user = query.getSingleResult();
		} catch (NoResultException ex) {
			user = null;
		}finally {
			em.close();
		}

		return user;
	}

}
