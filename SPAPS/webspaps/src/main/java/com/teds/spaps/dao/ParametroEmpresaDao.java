package com.teds.spaps.dao;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IParametroEmpresaDao;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.ParametroEmpresa;
import com.teds.spaps.util.FacesUtil;


/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class ParametroEmpresaDao extends DataAccessObject<ParametroEmpresa> implements IParametroEmpresaDao {

	public ParametroEmpresaDao(){
		super(ParametroEmpresa.class);
	}

	public ParametroEmpresa registrar(ParametroEmpresa usuario){
		try{
			usuario = create(usuario);
			return usuario;
		}catch(Exception e){
			String cause=e.getMessage();
			if (cause.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			}else{
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return null;
		}
	}

	public boolean modificar(ParametroEmpresa usuario){
		try{
			update(usuario);
			return true;
		}catch(Exception e){
			String cause=e.getMessage();
			if (cause.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			}else{
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return false;
		}
	}

	public ParametroEmpresa obtenerPorEmpresa(Empresa empresa){
		return findByParameter("empresa", empresa);
	}

}
