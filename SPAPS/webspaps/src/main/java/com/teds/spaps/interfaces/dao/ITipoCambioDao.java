package com.teds.spaps.interfaces.dao;

import java.util.Date;
import java.util.List;

import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.TipoCambio;
import com.teds.spaps.util.EDTipoCambio;

/**
 * Interface used to interact with the TipoCambio.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface ITipoCambioDao {
	
	/**
	 * Use only intermediate transactions
	 * @param tipoCambio
	 * @return
	 */
	TipoCambio create(TipoCambio tipoCambio);
	
	/**
	 * Use only intermediate transactions
	 * @param tipoCambio
	 * @return
	 */
	TipoCambio update(TipoCambio tipoCambio);


	boolean registrar(List<EDTipoCambio> listEDFechaTipoCambio,String usuarioRegistro,Empresa empresaLogin);
	
	TipoCambio modificar(TipoCambio usuario);
	
	boolean eliminar(TipoCambio usuario);
	
	List<TipoCambio> obtenerOrdenAscPorId();
	
	List<TipoCambio> obtenerOrdenDescPorId();

	List<TipoCambio> obtenerPorEmpresa(Empresa empresa);
	
	TipoCambio obtenerPorEmpresaYFecha(Empresa empresa,Date fecha);
	
	TipoCambio obtenerUltimoRegistro(Empresa empresa);
	
	TipoCambio obtenerPorEmpresaDiaAnterior(Empresa empresa);
}
