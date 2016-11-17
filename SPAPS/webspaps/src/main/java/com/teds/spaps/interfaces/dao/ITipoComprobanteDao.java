package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.TipoComprobante;


/**
 * Interface used to interact with the TipoComprobante.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface ITipoComprobanteDao {
	
	/**
	 * Use only intermediate transactions
	 * @param tipoComprobante
	 * @return
	 */
	TipoComprobante create(TipoComprobante tipoComprobante);
	
	/**
	 * Use only intermediate transactions
	 * @param tipoComprobante
	 * @return
	 */
	TipoComprobante update(TipoComprobante tipoComprobante);
	
	List<TipoComprobante> obtenerOrdenAscPorId();
	
	List<TipoComprobante> obtenerOrdenDescPorId();
	
	TipoComprobante obtenerPorId(Integer id);
	
}
