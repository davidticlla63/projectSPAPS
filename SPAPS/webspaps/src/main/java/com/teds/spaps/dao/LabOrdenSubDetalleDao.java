package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ILabOrdenSubDetalleDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenSubDetalle;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabOrdenSubDetalleDao extends
		DataAccessObjectJpa<LabOrdenSubDetalle> implements
		ILabOrdenSubDetalleDao {

	public LabOrdenSubDetalleDao() {
		super(LabOrdenSubDetalle.class);
	}

	@Override
	public boolean deleteDetail(LabOrden labExamen) {
		return super.removeDetail("orden", labExamen.getId());
	}

	@Override
	public void delete(LabOrdenSubDetalle labExamen) {
		super.deleteReal(labExamen);
		// return super.removeDetail("labExamen", labExamen.getId());
	}

	@Override
	public LabOrdenSubDetalle create(LabOrdenSubDetalle especialidad) {
		return super.create(especialidad);
	}

	@Override
	public LabOrdenSubDetalle update(LabOrdenSubDetalle especialidad) {
		return super.update(especialidad);
	}

	@Override
	public LabOrdenSubDetalle registrar(LabOrdenSubDetalle especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "LabOrdenSubDetalle "
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
	public LabOrdenSubDetalle modificar(LabOrdenSubDetalle especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"LabOrdenSubDetalle " + especialidad.toString());
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
	public boolean eliminar(LabOrdenSubDetalle especialidad) {
		try {
			beginTransaction();
			LabOrdenSubDetalle bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "LabOrdenSubDetalle "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabOrdenSubDetalle obtenerLabOrdenSubDetalle(Integer id) {
		return findById(id);
	}

	@Override
	public LabOrdenSubDetalle obtenerLabOrdenSubDetalle(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<LabOrdenSubDetalle> obtenerLabOrdenSubDetalleOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabOrdenSubDetalle> obtenerLabOrdenSubDetalleOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabOrdenSubDetalle> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabOrdenSubDetalle> obtenerPorExamen(LabExamen examen) {
		return findAllActiveParameter("labExamen", examen.getId());
	}

	@Override
	public List<LabOrdenSubDetalle> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabOrdenSubDetalleDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabOrdenSubDetalle> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ILabOrdenSubDetalleDao#obtenerPorSucursal(com
	 * .teds .spaps.model.Sucursal)
	 */
	@Override
	public List<LabOrdenSubDetalle> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabOrdenSubDetalleDao#obtenerAllActivos()
	 */
	@Override
	public List<LabOrdenSubDetalle> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
}
