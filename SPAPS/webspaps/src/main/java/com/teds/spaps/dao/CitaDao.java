package com.teds.spaps.dao;

/**
 * @author david
 */

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ICitaDao;
import com.teds.spaps.model.Cita;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class CitaDao extends DataAccessObjectJpa<Cita> implements ICitaDao {

	public CitaDao() {
		super(Cita.class);
	}

	@Override
	public Cita create(Cita cita) {
		return super.create(cita);
	}

	@Override
	public Cita update(Cita cita) {
		return super.update(cita);
	}

	@Override
	public Cita registrar(Cita cita) {
		try {
			beginTransaction();
			cita = create(cita);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Cita " + cita.toString());
			return cita;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar: "+e.getMessage());
			}
			System.err.println("error en : "+e.getMessage());
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public Cita modificar(Cita cita) {
		try {
			beginTransaction();
			cita = update(cita);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Cita " + cita.toString());
			return cita;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al modificar: "+e.getMessage());
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean eliminar(Cita cita) {
		try {
			beginTransaction();
			cita.setEstado("RM");
			Cita bar = update(cita);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Cita " + cita.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar: "+e.getMessage());
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Cita obtenerCita(Integer id) {
		return findById(id);
	}

	@Override
	public List<Cita> obtenerCitaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Cita> obtenerCitaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	
	@Override
	public List<Cita> obtenerPorEmpresa(Empresa empresa) {
		return findAllActivosByParameter("empresa", empresa.getId());
	}

	@Override
	public List<Cita> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}
	
	
	@Override
	public List<Cita> obtenerPorCitasEntreDosFechas(String usuario,Sucursal sucursal,Date fechaini,Date fechafin) {
		return findActiveForThwoDateForOtherTable("usuarioRegistro", usuario, "sucursal", sucursal.getId(), "fechaRegistro", fechaini, "fechaRegistro", fechafin);
	}
	
	@Override
	public List<Cita> obtenerPorCitasEntreDosFechasPorMedicos(Personal personal,Sucursal sucursal,Date fechaini,Date fechafin) {
		return findActiveForThwoDateForOtherTable("personal", personal.getId(), "sucursal", sucursal.getId(), "fechaRegistro", fechaini, "fechaRegistro", fechafin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ICitaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Cita> obtenerTodosActivosEInactivosPorOrdenAsc() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ICitaDao#obtenerCita(java.lang.String)
	 */
	@Override
	public Cita obtenerCita(String descripcion) {
		// TODO Auto-generated method stub
		return null;
	}

}
