package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ILabOrdenSubDetalleImagDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabOrdenImag;
import com.teds.spaps.model.LabOrdenSubDetalleImag;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabOrdenSubDetalleImagDao extends
		DataAccessObjectJpa<LabOrdenSubDetalleImag> implements
		ILabOrdenSubDetalleImagDao {

	public LabOrdenSubDetalleImagDao() {
		super(LabOrdenSubDetalleImag.class);
	}

	@Override
	public boolean deleteDetail(LabOrdenImag labExamen) {
		return super.removeDetail("orden", labExamen.getId());
	}

	@Override
	public void delete(LabOrdenSubDetalleImag labExamen) {
		super.deleteReal(labExamen);
		// return super.removeDetail("labExamen", labExamen.getId());
	}

	@Override
	public LabOrdenSubDetalleImag create(LabOrdenSubDetalleImag especialidad) {
		return super.create(especialidad);
	}

	@Override
	public LabOrdenSubDetalleImag update(LabOrdenSubDetalleImag especialidad) {
		return super.update(especialidad);
	}

	@Override
	public LabOrdenSubDetalleImag registrar(LabOrdenSubDetalleImag especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"LabOrdenSubDetalleImag " + especialidad.toString());
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
	public LabOrdenSubDetalleImag modificar(LabOrdenSubDetalleImag especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"LabOrdenSubDetalleImag " + especialidad.toString());
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
	public boolean eliminar(LabOrdenSubDetalleImag especialidad) {
		try {
			beginTransaction();
			LabOrdenSubDetalleImag bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"LabOrdenSubDetalleImag " + especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabOrdenSubDetalleImag obtenerLabOrdenSubDetalleImag(Integer id) {
		return findById(id);
	}

	@Override
	public LabOrdenSubDetalleImag obtenerLabOrdenSubDetalleImag(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<LabOrdenSubDetalleImag> obtenerLabOrdenSubDetalleImagOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabOrdenSubDetalleImag> obtenerLabOrdenSubDetalleImagOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabOrdenSubDetalleImag> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabOrdenSubDetalleImag> obtenerPorExamen(LabExamen examen) {
		return findAllActiveParameter("labExamen", examen.getId());
	}

	@Override
	public List<LabOrdenSubDetalleImag> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabOrdenSubDetalleImagDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabOrdenSubDetalleImag> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ILabOrdenSubDetalleImagDao#obtenerPorSucursal
	 * (com.teds .spaps.model.Sucursal)
	 */
	@Override
	public List<LabOrdenSubDetalleImag> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ILabOrdenSubDetalleImagDao#obtenerAllActivos()
	 */
	@Override
	public List<LabOrdenSubDetalleImag> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
}
