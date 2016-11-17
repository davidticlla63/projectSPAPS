package com.teds.spaps.interfaces.dao;

import java.util.Date;
import java.util.List;

import com.teds.spaps.model.Comprobante;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.LineaContable;
import com.teds.spaps.model.PlanCuenta;

/**
 * Interface used to interact with the LineaContable.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface ILineaContableDao {
	
	/**
	 * Use only intermediate transactions
	 * @param lineaContable
	 * @return
	 */
	LineaContable create(LineaContable lineaContable);
	
	/**
	 * Use only intermediate transactions
	 * @param lineaContable
	 * @return
	 */
	LineaContable update(LineaContable lineaContable);
	
	
	List<LineaContable> obtenerEntreFechasPorEmpresa(Date fechaIni, Date fechaFin,Empresa empresa);
	
	List<LineaContable> obtenerPorPeriodoYEmpresa(String mes,String gestion,Empresa empresa);
	
	List<LineaContable> obtenerPorComprobanteYCuenta(Comprobante comprobante,PlanCuenta planCuenta);
	
}
