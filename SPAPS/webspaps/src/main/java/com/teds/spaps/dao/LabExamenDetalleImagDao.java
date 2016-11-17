package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ILabExamenDetalleImagDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamenDetalleImag;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabExamenDetalleImagDao extends
		DataAccessObjectJpa<LabExamenDetalleImag> implements
		ILabExamenDetalleImagDao {

	public LabExamenDetalleImagDao() {
		super(LabExamenDetalleImag.class);
	}

	@Override
	public boolean deleteDetail(LabExamenImag labExamen) {
		return super.removeDetail("labExamen", labExamen.getId());
	}

	@Override
	public void delete(LabExamenDetalleImag labExamen) {
		super.deleteReal(labExamen);
		// return super.removeDetail("labExamen", labExamen.getId());
	}

	@Override
	public LabExamenDetalleImag create(LabExamenDetalleImag especialidad) {
		return super.create(especialidad);
	}

	@Override
	public LabExamenDetalleImag update(LabExamenDetalleImag especialidad) {
		return super.update(especialidad);
	}

	@Override
	public LabExamenDetalleImag registrar(LabExamenDetalleImag especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "LabExamenDetalleImag "
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
	public LabExamenDetalleImag modificar(LabExamenDetalleImag especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"LabExamenDetalleImag " + especialidad.toString());
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
	public boolean eliminar(LabExamenDetalleImag especialidad) {
		try {
			beginTransaction();
			LabExamenDetalleImag bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"LabExamenDetalleImag " + especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabExamenDetalleImag obtenerLabExamenDetalleImag(Integer id) {
		return findById(id);
	}

	@Override
	public LabExamenDetalleImag obtenerLabExamenDetalleImag(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<LabExamenDetalleImag> obtenerLabExamenDetalleImagOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabExamenDetalleImag> obtenerLabExamenDetalleImagOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabExamenDetalleImag> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabExamenDetalleImag> obtenerPorExamen(LabExamenImag examen) {
		return findAllActiveParameter("labExamen", examen.getId());
	}

	@Override
	public List<LabExamenDetalleImag> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabExamenDetalleImagDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabExamenDetalleImag> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ILabExamenDetalleImagDao#obtenerPorSucursal(com
	 * .teds .spaps.model.Sucursal)
	 */
	@Override
	public List<LabExamenDetalleImag> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ILabExamenDetalleImagDao#obtenerAllActivos()
	 */
	@Override
	public List<LabExamenDetalleImag> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
}
