/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.ConfiguracionFtp;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IConfiguracionFtpDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	ConfiguracionFtp create(ConfiguracionFtp especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	ConfiguracionFtp update(ConfiguracionFtp update);

	/**
	 * registrar object
	 * 
	 * @param ConfiguracionFtp
	 * @return ConfiguracionFtp
	 */
	ConfiguracionFtp registrar(ConfiguracionFtp especialidad);

	/**
	 * modificar object
	 * 
	 * @param ConfiguracionFtp
	 * @return ConfiguracionFtp
	 */
	ConfiguracionFtp modificar(ConfiguracionFtp especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(ConfiguracionFtp especialidad);

	/**
	 * 
	 * @return
	 */
	List<ConfiguracionFtp> obtenerTodosActivosEInactivosPorOrdenAsc();

	ConfiguracionFtp obtenerConfiguracionFtp(Integer id);

	ConfiguracionFtp obtenerConfiguracionFtp(String nombre);
	
	 ConfiguracionFtp obtenerConfiguracionFtpActivo(Compania compania,String nombre) ;

	List<ConfiguracionFtp> obtenerConfiguracionFtpOrdenAscPorId();

	List<ConfiguracionFtp> obtenerConfiguracionFtpOrdenDescPorId();

	List<ConfiguracionFtp> obtenerPorCompania(Compania compania);

	List<ConfiguracionFtp> obtenerPorCompania(String nombre, Compania compania);

	List<ConfiguracionFtp> obtenerPorSucursal(Sucursal sucursal);

	List<ConfiguracionFtp> obtenerAllActivos();
	
	List<ConfiguracionFtp> obtenerAllGrupoExamen(Compania compania) ;

}
