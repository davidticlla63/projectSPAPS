/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.ParamSistemaIndices;

/**
 * @author ANITA
 *
 */
public interface IParamSistemaIndicesDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param paramSistemaIndices
	 * @return
	 */
	ParamSistemaIndices create(ParamSistemaIndices paramSistemaIndices);
	
	void registrar(String usuario,Compania compania);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	ParamSistemaIndices update(ParamSistemaIndices update);
	
	 ParamSistemaIndices increment(ParamSistemaIndices paramSistemaIndices);

	/**
	 * registrar object
	 * 
	 * @param ParamSistemaIndices
	 * @return ParamSistemaIndices
	 */
	ParamSistemaIndices registrar(ParamSistemaIndices paramSistemaIndices);

	/**
	 * modificar object
	 * 
	 * @param ParamSistemaIndices
	 * @return ParamSistemaIndices
	 */
	ParamSistemaIndices modificar(ParamSistemaIndices paramSistemaIndices);

	/**
	 * eliminar object
	 * 
	 * @param paramSistemaIndices
	 * @return
	 */
	boolean eliminar(ParamSistemaIndices paramSistemaIndices);

	/**
	 * 
	 * @return
	 */
	List<ParamSistemaIndices> obtenerTodosActivosEInactivosPorOrdenAsc();

	ParamSistemaIndices obtenerParamSistemaIndices(Integer id);

	List<ParamSistemaIndices> obtenerParamSistemaIndicesOrdenAscPorId();

	List<ParamSistemaIndices> obtenerParamSistemaIndicesOrdenDescPorId();

	List<ParamSistemaIndices> obtenerPorCompania(Compania compania);
	
	ParamSistemaIndices obtenerPorCompania(String key,Compania compania);


}
