package com.donte.scale.dao;

import com.donte.scale.dao.util.GenericDAO;
import com.donte.scale.model.Veiculo;

public class VeiculoDAO extends GenericDAO<Veiculo> {

	public VeiculoDAO(Class<Veiculo> clazz) {
		super(clazz);		
	}
	
	public VeiculoDAO(){
		this(Veiculo.class);
	}

}
