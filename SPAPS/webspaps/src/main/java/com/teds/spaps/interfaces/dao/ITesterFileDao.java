package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.TesterFile;

public interface ITesterFileDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param testerFile
	 * @return
	 */
	TesterFile create(TesterFile testerFile);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	TesterFile update(TesterFile update);

	/**
	 * registrar object
	 * 
	 * @param TesterFile
	 * @return TesterFile
	 */
	TesterFile registrar(TesterFile testerFile);

	/**
	 * modificar object
	 * 
	 * @param TesterFile
	 * @return TesterFile
	 */
	TesterFile modificar(TesterFile testerFile);

	/**
	 * eliminar object
	 * 
	 * @param testerFile
	 * @return
	 */
	boolean eliminar(TesterFile testerFile);

	/**
	 * 
	 * @return
	 */
	List<TesterFile> obtenerTodosActivosEInactivosPorOrdenAsc();

	TesterFile obtenerTesterFile(Integer id);

	List<TesterFile> obtenerTesterFileOrdenAscPorId();

	List<TesterFile> obtenerTesterFileOrdenDescPorId();

	List<TesterFile> obtenerPorCompania(Compania compania);

	List<TesterFile> onComplete(String query);

}
