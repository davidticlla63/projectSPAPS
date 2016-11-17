package com.teds.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ILineaContableDao;
import com.teds.spaps.model.Comprobante;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.LineaContable;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.Time;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class LineaContableDao extends DataAccessObject<LineaContable> implements  ILineaContableDao {

	public LineaContableDao(){
		super(LineaContable.class);
	}

	public LineaContable registrar(LineaContable lineaContable){
		try{
			lineaContable = create(lineaContable);
			FacesUtil.infoMessage("Registro Correcto", "LineaContable "+lineaContable.getId());
			return lineaContable;
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

	public LineaContable modificar(LineaContable lineaContable){
		try{
			update(lineaContable);
			FacesUtil.infoMessage("Modificación Correcta", "lineaContable "+lineaContable.getId());
			return lineaContable;
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

	public boolean eliminar(LineaContable lineaContable){
		try{
			update(lineaContable);
			FacesUtil.infoMessage("Eliminación Correcta", "lineaContable "+lineaContable.getId());
			return true;
		}catch(Exception e){
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public List<LineaContable> obtenerOrdenAscPorId(){
		return findAscAllOrderedByParameter("id");
	}
	
	public List<LineaContable> obtenerOrdenDescPorId(){
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LineaContable> obtenerEntreFechasPorEmpresa(
			Date fechaIni, Date fechaFin, Empresa empresa) {
		String query = "select em from LineaContable em where em.comprobante.empresa.id="+empresa.getId()+" and to_number(to_char(em.fechaRegistro ,'YYYYMMDD'), '999999999')>=" 
				+ Time.obtenerFormatoYYYYMMDD(fechaIni)+" and to_number(to_char(em.fechaRegistro ,'YYYYMMDD'), '999999999')<="
				+ Time.obtenerFormatoYYYYMMDD(fechaFin);
		return executeQueryResulList(query);
	}
	
	@Override
	public List<LineaContable> obtenerPorPeriodoYEmpresa(String mes,String gestion,Empresa empresa){
		String query = "select em from LineaContable em where em.comprobante.empresa.id="+empresa.getId()
					  +" and date_part('month', em.fecha) ="+mes+" and date_part('year', em.fecha) ="+gestion;
		return executeQueryResulList(query);
	}

	@Override
	public List<LineaContable> obtenerPorComprobanteYCuenta(Comprobante comprobante,PlanCuenta planCuenta) {
		String query = "select em from LineaContable em where em.comprobante.id="+comprobante.getId()+" and em.planCuenta.id="+planCuenta.getId();
		return executeQueryResulList(query);
	}
	
//	public List<LineaContable> obtenerPorComprobante(Comprobante comprobante){
//		return findAllActivosByParameter("comprobante", comprobante);
//	}
}
