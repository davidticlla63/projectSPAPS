/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;


import java.util.List;

import com.teds.spaps.model.FacturaVenta;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IFacturaVentaDao {
	
	FacturaVenta obtenerPorId(Integer id) ;
	
	List<FacturaVenta> obtenerTodasOrdenAscPorId();

}
