package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ILabExamenDetalleDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabExamenDetalle;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabExamenDetalleDao extends DataAccessObjectJpa<LabExamenDetalle>
		implements ILabExamenDetalleDao {

	public LabExamenDetalleDao() {
		super(LabExamenDetalle.class);
	}

	@Override
	public boolean deleteDetail(LabExamen labExamen) {
		return super.removeDetail("labExamen", labExamen.getId());
	}
	
	@Override
	public void delete(LabExamenDetalle labExamen) {
		super.deleteReal(labExamen);
		//return super.removeDetail("labExamen", labExamen.getId());
	}

	@Override
	public LabExamenDetalle create(LabExamenDetalle especialidad) {
		return super.create(especialidad);
	}

	@Override
	public LabExamenDetalle update(LabExamenDetalle especialidad) {
		return super.update(especialidad);
	}

	@Override
	public LabExamenDetalle registrar(LabExamenDetalle especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "LabExamenDetalle "
					+ especialidad.toString());
			return especialidad;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public LabExamenDetalle modificar(LabExamenDetalle especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "LabExamenDetalle "
					+ especialidad.toString());
			return especialidad;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean eliminar(LabExamenDetalle especialidad) {
		try {
			beginTransaction();
			LabExamenDetalle bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "LabExamenDetalle "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabExamenDetalle obtenerLabExamenDetalle(Integer id) {
		return findById(id);
	}

	@Override
	public LabExamenDetalle obtenerLabExamenDetalle(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<LabExamenDetalle> obtenerLabExamenDetalleOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabExamenDetalle> obtenerLabExamenDetalleOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabExamenDetalle> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabExamenDetalle> obtenerPorExamen(LabExamen examen) {
		return findAllActiveParameter("labExamen", examen.getId());
	}

	@Override
	public List<LabExamenDetalle> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabExamenDetalleDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabExamenDetalle> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ILabExamenDetalleDao#obtenerPorSucursal(com
	 * .teds .spaps.model.Sucursal)
	 */
	@Override
	public List<LabExamenDetalle> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabExamenDetalleDao#obtenerAllActivos()
	 */
	@Override
	public List<LabExamenDetalle> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
}
