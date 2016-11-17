package com.teds.spaps.dao;


import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IConfiguracionContableDao;
import com.teds.spaps.model.ConfiguracionContable;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.util.FacesUtil;

/*
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class ConfiguracionContableDao extends DataAccessObject<ConfiguracionContable> implements IConfiguracionContableDao {

	
	@Override
	public boolean modificar(ConfiguracionContable configuracionContable) {
		try{
			update(configuracionContable);
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

	/* (non-Javadoc)
	 * @see com.teds.spaps.interfaces.dao.IConfiguraionContableDao#obtenerPorEmpresac(com.teds.spaps.model.Empresa)
	 */
	@Override
	public ConfiguracionContable obtenerPorEmpresac(Empresa empresa) {
		String query = "select em from ConfiguracionContable em where em.empresa.id="+empresa.getId();
		return executeQuerySingleResult(query);
	}


}
