package com.teds.spaps.interfaces.dao;

import java.util.Date;
import java.util.List;

import com.teds.spaps.model.Comprobante;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Gestion;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.model.TipoComprobante;
import com.teds.spaps.util.EDAsiento;


/**
 * Interface used to interact with the Comprobante.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IComprobanteDao {
	
	/**
	 * Use only intermediate transactions
	 * @param comprobante
	 * @return
	 */
	Comprobante create(Comprobante comprobante);
	
	/**
	 * Use only intermediate transactions
	 * @param comprobante
	 * @return
	 */
	Comprobante update(Comprobante comprobante);
	
	Comprobante registrar(Comprobante comprobante,List<EDAsiento> list);

	boolean modificar(Comprobante comprobante);

	boolean eliminar(Comprobante comprobante);

	boolean anular(Comprobante comprobante);

	List<Comprobante> obtenerComprobanteOrdenAscPorId();

	List<Comprobante> obtenerComprobanteOrdenDescPorId();

	List<Comprobante> obtenerPorEmpresaGesstion(Empresa empresa,Gestion gestion);
	
	List<Comprobante> obtenerEntreFechasPorCuenta(Date fechaIni,Date fechaFin,PlanCuenta planCuenta,Empresa empresa);
	
	List<Comprobante> obtenerMenoresAFechaPorCuenta(Date fechaIni,PlanCuenta planCuenta,Empresa empresa);

	int obtenerNumeroComprobante(Date date,Empresa empresa,TipoComprobante tc);
	
	Comprobante obtenerPorId(Integer id);


}
