package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IMonedaEmpresaDao;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.MonedaEmpresa;
import com.teds.spaps.util.FacesUtil;


/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class MonedaEmpresaDao extends DataAccessObject<MonedaEmpresa> implements IMonedaEmpresaDao {

	public MonedaEmpresaDao(){
		super(MonedaEmpresa.class);
	}

	public MonedaEmpresa registrar(MonedaEmpresa monedaEmpresa){
		try{
			monedaEmpresa = create(monedaEmpresa);
			FacesUtil.infoMessage("Registro Correcto", "Moneda  "+monedaEmpresa.getId());
			return monedaEmpresa;
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
	
	public boolean registrar(List<MonedaEmpresa> listMonedaEmpresa){
		try{
			for(MonedaEmpresa me : listMonedaEmpresa){
				create(me);
			}
			FacesUtil.infoMessage("Registro Correcto", "Moneda  ");
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

	public boolean modificar(MonedaEmpresa monedaEmpresa){
		try{
			update(monedaEmpresa);
			FacesUtil.infoMessage("Modificación Correcta", "Moneda "+monedaEmpresa.getId());
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

	public boolean eliminar(MonedaEmpresa monedaEmpresa){
		try{
			monedaEmpresa.setEstado("RM");
			modificar(monedaEmpresa);
			FacesUtil.infoMessage("Eliminación Correcta", "Moneda "+monedaEmpresa.getId());
			return true;
		}catch(Exception e){
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public List<MonedaEmpresa> obtenerMonedaEmpresaOrdenAscPorId(){
		return findAscAllOrderedByParameter("id");
	}
	
	public List<MonedaEmpresa> obtenerMonedaEmpresaOrdenDescPorId(){
		return findDescAllOrderedByParameter("id");
	}
	
	public List<MonedaEmpresa> obtenerTodasPorEmpresa(Empresa empresa){
		return findAllActivosByParameter("empresa", empresa);
	}

	@Override
	public MonedaEmpresa obtenerPorId(Integer id) {
		return findById(id);
	}

	@Override
	public MonedaEmpresa obtenerNacionalPorEmpresa(Empresa empresa) {
		String query = "select em from MonedaEmpresa em where em.empresa.id="+empresa.getId()+" and em.tipo='NACIONAL'";
		return executeQuerySingleResult(query);
	}

	@Override
	public MonedaEmpresa obtenerExtranjeraPorEmpresa(Empresa empresa) {
		String query = "select em from MonedaEmpresa em where em.empresa.id="+empresa.getId()+" and em.tipo='EXTRANJERA'";
		return executeQuerySingleResult(query);
	}

	
}
