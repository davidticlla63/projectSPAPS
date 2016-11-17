package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ITipoComprobanteDao;
import com.teds.spaps.model.TipoComprobante;


/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class TipoComprobanteDao extends DataAccessObject<TipoComprobante> implements ITipoComprobanteDao {

	public TipoComprobanteDao(){
		super(TipoComprobante.class);
	}

	public List<TipoComprobante> obtenerOrdenAscPorId(){
		return findAscAllOrderedByParameter("id");
	}
	
	public List<TipoComprobante> obtenerOrdenDescPorId(){
		return findDescAllOrderedByParameter("id");
	}

	/* (non-Javadoc)
	 * @see com.qallta.tiluchi.interfaces.dao.ITipoComprobanteDao#obtenerPorId(java.lang.Integer)
	 */
	@Override
	public TipoComprobante obtenerPorId(Integer id) {
		return findById(id);
	}
	
}
