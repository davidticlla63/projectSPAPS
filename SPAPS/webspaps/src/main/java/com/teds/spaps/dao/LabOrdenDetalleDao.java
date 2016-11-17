package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ILabOrdenDetalleDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenDetalle;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabOrdenDetalleDao extends DataAccessObjectJpa<LabOrdenDetalle>
		implements ILabOrdenDetalleDao {

	public LabOrdenDetalleDao() {
		super(LabOrdenDetalle.class);
	}

	@Override
	public boolean deleteDetail(LabOrden labExamen) {
		return super.removeDetail("orden", labExamen.getId());
	}

	@Override
	public void delete(LabOrdenDetalle labOrdenDetalle) {
		super.deleteReal(labOrdenDetalle);
		// return super.removeDetail("labExamen", labExamen.getId());
	}

	@Override
	public LabOrdenDetalle create(LabOrdenDetalle especialidad) {
		return super.create(especialidad);
	}

	@Override
	public LabOrdenDetalle update(LabOrdenDetalle especialidad) {
		return super.update(especialidad);
	}

	@Override
	public LabOrdenDetalle registrar(LabOrdenDetalle especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "LabOrdenDetalle "
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
	public LabOrdenDetalle modificar(LabOrdenDetalle especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "LabOrdenDetalle "
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
	public boolean eliminar(LabOrdenDetalle especialidad) {
		try {
			beginTransaction();
			LabOrdenDetalle bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "LabOrdenDetalle "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabOrdenDetalle obtenerLabOrdenDetalle(Integer id) {
		return findById(id);
	}

	@Override
	public LabOrdenDetalle obtenerLabOrdenDetalle(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<LabOrdenDetalle> obtenerLabOrdenDetalleOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabOrdenDetalle> obtenerLabOrdenDetalleOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabOrdenDetalle> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabOrdenDetalle> obtenerPorExamen(LabExamen examen) {
		return findAllActiveParameter("labExamen", examen.getId());
	}

	@Override
	public List<LabOrdenDetalle> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabOrdenDetalleDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabOrdenDetalle> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ILabOrdenDetalleDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<LabOrdenDetalle> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabOrdenDetalleDao#obtenerAllActivos()
	 */
	@Override
	public List<LabOrdenDetalle> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
}
