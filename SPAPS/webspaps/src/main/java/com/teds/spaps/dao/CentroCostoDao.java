package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ICentroCostoDao;
import com.teds.spaps.model.CentroCosto;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class CentroCostoDao extends DataAccessObject<CentroCosto> implements ICentroCostoDao {

	public CentroCostoDao(){
		super(CentroCosto.class);
	}

	public CentroCosto registrar(CentroCosto usuario){
		try{
			usuario = create(usuario);
			FacesUtil.infoMessage("Registro Correcto", "CentroCosto "+usuario.getId());
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

	public boolean modificar(CentroCosto usuario){
		try{
			update(usuario);
			FacesUtil.infoMessage("Modificación Correcta", "CentroCosto "+usuario.getId());
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

	public boolean eliminar(CentroCosto usuario){
		try{
			update(usuario);
			FacesUtil.infoMessage("Eliminación Correcta", "CentroCosto "+usuario.getId());
			return true;
		}catch(Exception e){
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public List<CentroCosto> obtenerOrdenAscPorId(){
		return findAscAllOrderedByParameter("id");
	}
	
	public List<CentroCosto> obtenerOrdenDescPorId(){
		return findDescAllOrderedByParameter("id");
	}
	
	public  List<CentroCosto> obtenerTodosPorEmpresa(Empresa empresa){
		String query = "select em from CentroCosto em where (em.estado='AC' or em.estado='IN') and em.empresa.id="+empresa.getId() ;
		return executeQueryResulList(query);
	}
}
