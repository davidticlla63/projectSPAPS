package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IFacturaVentaDao;
import com.teds.spaps.model.FacturaVenta;

@Stateless
public class FacturaVentaDao extends DataAccessObjectJpa<FacturaVenta>
		implements IFacturaVentaDao {

	public FacturaVentaDao() {
		super(FacturaVenta.class);
	}

	
	@Override
	public FacturaVenta obtenerPorId(Integer id) {
		return findById(id);
	}

	@Override
	public List<FacturaVenta> obtenerTodasOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}



}
