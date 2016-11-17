package com.teds.spaps.interfaces.dao;

import java.util.Date;
import java.util.List;

import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.TipoCambioUfv;

/**
 * Interface used to interact with the TipoCambioUfv.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface ITipoCambioUfvDao {
	
	/**
	 * Use only intermediate transactions
	 * @param tipoCambioUfv
	 * @return
	 */
	TipoCambioUfv create(TipoCambioUfv tipoCambioUfvUfv);
	
	/**
	 * Use only intermediate transactions
	 * @param tipoCambioUfv
	 * @return
	 */
	TipoCambioUfv update(TipoCambioUfv tipoCambioUfv);


	boolean registrar(List<TipoCambioUfv> listEDFechaTipoCambioUfv,Empresa empresaLogin);
	
	TipoCambioUfv modificar(TipoCambioUfv usuario);
	
	boolean eliminar(TipoCambioUfv usuario);
	
	List<TipoCambioUfv> obtenerOrdenAscPorId();
	
	List<TipoCambioUfv> obtenerOrdenDescPorId();

	List<TipoCambioUfv> obtenerPorEmpresa(Empresa empresa);
	
	TipoCambioUfv obtenerPorEmpresaYFecha(Empresa empresa,Date fecha);
}
