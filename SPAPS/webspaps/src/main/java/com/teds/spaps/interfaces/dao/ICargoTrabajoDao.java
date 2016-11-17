/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.CargoTrabajo;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.EmpresaTrabajo;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ICargoTrabajoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param cargoTrabajo
	 * @return
	 */
	CargoTrabajo create(CargoTrabajo cargoTrabajo);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	CargoTrabajo update(CargoTrabajo update);

	/**
	 * registrar object
	 * 
	 * @param CargoTrabajo
	 * @return CargoTrabajo
	 */
	CargoTrabajo registrar(CargoTrabajo cargoTrabajo);

	/**
	 * modificar object
	 * 
	 * @param CargoTrabajo
	 * @return CargoTrabajo
	 */
	CargoTrabajo modificar(CargoTrabajo cargoTrabajo);

	/**
	 * eliminar object
	 * 
	 * @param cargoTrabajo
	 * @return
	 */
	boolean eliminar(CargoTrabajo cargoTrabajo);

	/**
	 * 
	 * @return
	 */
	List<CargoTrabajo> obtenerTodosActivosEInactivosPorOrdenAsc();

	CargoTrabajo obtenerCargoTrabajo(Integer id);

	List<CargoTrabajo> obtenerCargoTrabajoOrdenAscPorId();

	List<CargoTrabajo> obtenerCargoTrabajoOrdenDescPorId();

	List<CargoTrabajo> obtenerPorCompania(Compania compania);

	CargoTrabajo obtenerCargoTrabajo(String descripcion);

	List<CargoTrabajo> obtenerPorEmpresa(EmpresaTrabajo empresa);

	List<CargoTrabajo> obtenerPorEmpresa(String nombre, EmpresaTrabajo empresa);

	List<CargoTrabajo> obtenerPorSucursal(Sucursal sucursal);

	List<CargoTrabajo> onComplete(String query);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<CargoTrabajo> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<CargoTrabajo> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

	/**
	 * @param cargoTrabajo
	 * @param compania
	 * @return
	 */
	boolean verificarRepetido(CargoTrabajo cargoTrabajo, Compania compania);

	/**
	 * @param cargoTrabajo
	 * @param compania
	 * @return
	 */
	boolean verificarRepetidoUpdate(CargoTrabajo cargoTrabajo, Compania compania);

	/**
	 * @param cargoTrabajo
	 * @param sucursal
	 * @return
	 */
	boolean verificarRepetido(CargoTrabajo cargoTrabajo, Sucursal sucursal);

	/**
	 * @param cargoTrabajo
	 * @param sucursal
	 * @return
	 */
	boolean verificarRepetidoUpdate(CargoTrabajo cargoTrabajo, Sucursal sucursal);

}
