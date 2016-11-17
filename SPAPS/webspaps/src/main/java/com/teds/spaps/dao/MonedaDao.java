package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IMonedaDao;
import com.teds.spaps.model.Moneda;

/*
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class MonedaDao extends DataAccessObject<Moneda> implements IMonedaDao {

	public MonedaDao(){
		super(Moneda.class);
	}

	/* (non-Javadoc)
	 * @see com.teds.spaps.interfaces.dao.IMonedaDao#obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Moneda> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAllByParameterObjectTwoQueryOr("estado", "estado", "AC",
				"IN");
	}

	
}
