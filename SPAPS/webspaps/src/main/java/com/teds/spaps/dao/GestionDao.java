package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IGestionDao;
import com.teds.spaps.model.Gestion;

/*
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class GestionDao extends DataAccessObject<Gestion> implements IGestionDao {

	public GestionDao(){
		super(Gestion.class);
	}

	/* (non-Javadoc)
	 * @see com.qallta.tiluchi.interfaces.dao.IGestionDao#modificar(com.qallta.tiluchi.model.Gestion)
	 */
	@Override
	public boolean modificar(Gestion gestion) {
		return false;
	}

	/* Obtener la gestion Actual, Solo puede haber una con el estado AC=Activa
	 * @see com.qallta.tiluchi.interfaces.dao.IGestionDao#obtenerGestion()
	 */
	@Override
	public Gestion obtenerGestion() {
		String query = "select em from Gestion em where em.estado='AC' ";
		return executeQuerySingleResult(query);
	}

	/* (non-Javadoc)
	 * @see com.qallta.tiluchi.interfaces.dao.IGestionDao#obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Gestion> obtenerTodosActivosEInactivosPorOrdenAsc() {
		String query = "select em from Gestion em where em.estado='AC' or em.estado='IN'";
		return executeQueryResulList(query);
	}
}
