package com.teds.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ITipoCambioUfvDao;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.TipoCambioUfv;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class TipoCambioUfvDao extends DataAccessObject<TipoCambioUfv> implements ITipoCambioUfvDao {

	public TipoCambioUfvDao(){
		super(TipoCambioUfv.class);
	}

	public boolean registrar(List<TipoCambioUfv> listTipoCambioUfvUfv,Empresa empresaLogin){
		try{
			for(TipoCambioUfv tcufv: listTipoCambioUfvUfv){
				create(tcufv);
			}
			FacesUtil.infoMessage("Registro Correcto TipoCambioUfv", " ");
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

	public TipoCambioUfv modificar(TipoCambioUfv usuario){
		try{
			update(usuario);
			FacesUtil.infoMessage("Modificación Correcta", "TipoCambioUfv "+usuario.getId());
			return usuario;
		}catch(Exception e){
			String cause=e.getMessage();
			if (cause.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			}else{
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	public boolean eliminar(TipoCambioUfv usuario){
		try{
			update(usuario);
			FacesUtil.infoMessage("Eliminación Correcta", "TipoCambioUfv "+usuario.getId());
			return true;
		}catch(Exception e){
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public List<TipoCambioUfv> obtenerOrdenAscPorId(){
		return null;//findAscAllOrderedByParameter("id");
	}

	public List<TipoCambioUfv> obtenerOrdenDescPorId(){
		return null;//findDescAllOrderedByParameter("id");
	}

	public  List<TipoCambioUfv> obtenerPorEmpresa(Empresa empresa){
		return findAllActivosByParameter("empresa", empresa);
	}

	public TipoCambioUfv obtenerPorEmpresaYFecha(Empresa empresa,Date fecha){
		//return findAllActivosByParameter("empresa", empresa);
		return null;//return findAllByParameterDateAndTwoParameter("fecha", fecha, "empresa", empresa);
	}
}
