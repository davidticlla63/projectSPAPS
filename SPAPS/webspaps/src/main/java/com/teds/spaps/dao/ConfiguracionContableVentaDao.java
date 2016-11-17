package com.teds.spaps.dao;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IConfiguracionContableVentaDao;
import com.teds.spaps.model.ConfiguracionContableVenta;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.util.FacesUtil;

/*
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class ConfiguracionContableVentaDao extends DataAccessObject<ConfiguracionContableVenta> implements IConfiguracionContableVentaDao {

	
	@Override
	public boolean modificar(ConfiguracionContableVenta configuracionContableVenta) {
		try{
			update(configuracionContableVenta);
			FacesUtil.infoMessage("Modificación Correcta", "");
			return true;
		}catch(Exception e){
			String cause=e.getMessage();
			if (cause.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			}else{
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return false;
		}
	}

	
	@Override
	public ConfiguracionContableVenta obtenerPorEmpresa(Empresa empresa) {
		try{
		String query = "select em from ConfiguracionContableVenta em where em.empresa.id="+empresa.getId();
		return executeQuerySingleResult(query);
		}catch(Exception e){
			return new ConfiguracionContableVenta();
		}
	}


}
