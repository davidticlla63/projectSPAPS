package com.teds.spaps.interfaces.dao;

import java.util.Date;
import java.util.List;

import com.teds.spaps.model.Empresa;
import com.teds.spaps.util.EDSumasSaldos;

/**
 * Interface used to interact with the Comprobante.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface ISumasSaldosDao {

	List<EDSumasSaldos> obtenerTodosHastaFecha(Date fecha,Empresa empresa);
	
	List<EDSumasSaldos> obtenerEstadoResultadoEntreFechas(Date fechaIni,Date fechaFin,Empresa empresa);
	
	List<EDSumasSaldos> obtenerBalanceHastaFecha(Date fecha,Empresa empresa);
}
