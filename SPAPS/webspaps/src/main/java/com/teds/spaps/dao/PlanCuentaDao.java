package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IPlanCuentaDao;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.util.FacesUtil;


/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class PlanCuentaDao extends DataAccessObject<PlanCuenta> implements IPlanCuentaDao{


	public PlanCuentaDao(){
		super(PlanCuenta.class);
	}

	@Override
	public PlanCuenta registrar(PlanCuenta planCuenta){
		try{
			planCuenta = create(planCuenta);
			FacesUtil.infoMessage("Registro Correcto", "Plan Cuenta "+planCuenta.getCodigoAuxiliar());
			return planCuenta;
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

	@Override
	public PlanCuenta modificar(PlanCuenta planCuenta){
		try{
			update(planCuenta);
			FacesUtil.infoMessage("Modificación Correcta", "Plan Cuenta "+planCuenta.getCodigoAuxiliar());
			return planCuenta;
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

	@Override
	public boolean eliminar(PlanCuenta planCuenta,List<PlanCuenta> listCuentasHijas){
		try{
			planCuenta.setEstado("RM");
			update(planCuenta);
			for(PlanCuenta pch: listCuentasHijas){
				pch.setEstado("RM");
				update(pch);
			}
			FacesUtil.infoMessage("Eliminación Correcta", "Plan Cuenta "+planCuenta.getCodigoAuxiliar());
			return true;
		}catch(Exception e){
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public List<PlanCuenta> obtenerTodosOrdenAscPorId(){
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<PlanCuenta> obtenerOrdenDescPorId(){
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<PlanCuenta> findAll() {
		String query = "select em from PlanCuenta em ";
		return executeQueryResulList(query);
	}

	@Override
	public PlanCuenta obtenerPorCodigo(String codigo){
		try{
			return findByParameter("codigo", codigo);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<PlanCuenta> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return null;
	}

	@Override
	public List<PlanCuenta> obtenerTodosActivosEInactivosPorEmpresa(Empresa empresa) {
		String query = "select em from PlanCuenta em where em.empresa.id="+empresa.getId()+" and (em.estado='AC' or em.estado='IN')";
		return executeQueryResulList(query);
	}

	@Override
	public List<PlanCuenta> obtenerPlanCuentaAuxiliarPorDescripcionConsulta(
			String query, Empresa empresa) {
		String queryAux = "select em from PlanCuenta em where em.descripcion='"+query+"' and em.clase='AUXILIAR' and em.empresa.id="+empresa.getId();
		return executeQueryResulList(queryAux);
	}
}

