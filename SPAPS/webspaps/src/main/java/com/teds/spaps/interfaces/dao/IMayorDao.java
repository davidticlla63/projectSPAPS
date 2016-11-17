package com.teds.spaps.interfaces.dao;

import java.util.Date;
import java.util.List;

import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.util.EDLibroMayor;

/**
 * Interface used to interact with the Comprobante.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IMayorDao {

	List<EDLibroMayor> obtenerMenoresAFecha(Date fecha,PlanCuenta planCuenta,Empresa empresa);
	
	double obtenerSaldoPorFechas(Date fechaIni,Date fechaFin,PlanCuenta planCuenta,Empresa empresa);
	
}
