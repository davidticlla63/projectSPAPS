package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ITipoCuentaDao;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Gestion;
import com.teds.spaps.model.TipoCuenta;


/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class TipoCuentaDao extends DataAccessObject<TipoCuenta> implements ITipoCuentaDao {

	public TipoCuentaDao(){
		super(TipoCuenta.class);
	}
	
	public List<TipoCuenta> obtenerOrdenAscPorId(){
		return findAscAllOrderedByParameter("id");
	}
	
	public List<TipoCuenta> obtenerOrdenDescPorId(){
		return findDescAllOrderedByParameter("id");
	}
	
	public List<TipoCuenta> obtenerActivosOrdenDescPorId(){
		return findAllByEstadoOrderByDesc("AC");
	}
	
	public List<TipoCuenta> obtenerActivosOrdenAscPorId(){
		return findAllByEstadoOrderByAsc("AC");
	}

	@Override
	public List<TipoCuenta> obtenerPorEmpresaYGesstion(Empresa empresa,
			Gestion gestion) {
		String query = "select em from TipoCuenta em where em.empresa.id="+empresa.getId()+" and em.gestion.id="+gestion.getId();
		return executeQueryResulList(query);
	}
}
