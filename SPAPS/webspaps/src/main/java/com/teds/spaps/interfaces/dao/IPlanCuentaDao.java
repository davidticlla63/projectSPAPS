package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.PlanCuenta;

/**
 * Interface used to interact with the PlanCuenta.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IPlanCuentaDao {
	
	/**
	 * Use only intermediate transactions
	 * @param planCuenta
	 * @return
	 */
	PlanCuenta create(PlanCuenta planCuenta);
	
	/**
	 * Use only intermediate transactions
	 * @param update
	 * @return
	 */
	PlanCuenta update(PlanCuenta update);
	
	/**
	 * registrar object
	 * @param PlanCuenta
	 * @return PlanCuenta
	 */
	PlanCuenta registrar(PlanCuenta planCuenta) ;
	
	/**
	 * modificar object
	 * @param PlanCuenta
	 * @return PlanCuenta
	 */
	PlanCuenta modificar(PlanCuenta planCuenta) ;
	
	/**
	 * eliminar object
	 * @param planCuenta
	 * @return
	 */
	boolean eliminar(PlanCuenta planCuenta,List<PlanCuenta> listCuentasHijas) ;
	
	/**
	 * 
	 * @return
	 */
	List<PlanCuenta> obtenerTodosActivosEInactivosPorOrdenAsc();
	
	/**
	 * 
	 * @return
	 */
	List<PlanCuenta> obtenerTodosOrdenAscPorId();
	
	/**
	 * 
	 * @return
	 */
	List<PlanCuenta> obtenerOrdenDescPorId();
	
	/**
	 * 
	 * @return
	 */
	List<PlanCuenta> findAll();
	
	/**
	 * 
	 * @param codigo
	 * @return
	 */
	PlanCuenta obtenerPorCodigo(String codigo);
	
	/**
	 * 
	 * @return
	 */
	List<PlanCuenta> obtenerTodosActivosEInactivosPorEmpresa(Empresa empresa);
	
	List<PlanCuenta> obtenerPlanCuentaAuxiliarPorDescripcionConsulta(String query, Empresa empresa);

}
