package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ILabOrdenDetalleImagDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabOrdenDetalleImag;
import com.teds.spaps.model.LabOrdenImag;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabOrdenDetalleImagDao extends DataAccessObjectJpa<LabOrdenDetalleImag>
		implements ILabOrdenDetalleImagDao {

	public LabOrdenDetalleImagDao() {
		super(LabOrdenDetalleImag.class);
	}

	@Override
	public boolean deleteDetail(LabOrdenImag labExamen) {
		return super.removeDetail("orden", labExamen.getId());
	}
	
	@Override
	public void delete(LabOrdenDetalleImag labOrdenDetalle) {
		super.deleteReal(labOrdenDetalle);
		//return super.removeDetail("labExamen", labExamen.getId());
	}


	@Override
	public LabOrdenDetalleImag create(LabOrdenDetalleImag especialidad) {
		return super.create(especialidad);
	}

	@Override
	public LabOrdenDetalleImag update(LabOrdenDetalleImag especialidad) {
		return super.update(especialidad);
	}

	@Override
	public LabOrdenDetalleImag registrar(LabOrdenDetalleImag especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "LabOrdenDetalleImag "
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
	public LabOrdenDetalleImag modificar(LabOrdenDetalleImag especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "LabOrdenDetalleImag "
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
	public boolean eliminar(LabOrdenDetalleImag especialidad) {
		try {
			beginTransaction();
			LabOrdenDetalleImag bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "LabOrdenDetalleImag "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabOrdenDetalleImag obtenerLabOrdenDetalleImag(Integer id) {
		return findById(id);
	}

	@Override
	public LabOrdenDetalleImag obtenerLabOrdenDetalleImag(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<LabOrdenDetalleImag> obtenerLabOrdenDetalleImagOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabOrdenDetalleImag> obtenerLabOrdenDetalleImagOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabOrdenDetalleImag> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabOrdenDetalleImag> obtenerPorExamen(LabExamen examen) {
		return findAllActiveParameter("labExamen", examen.getId());
	}

	@Override
	public List<LabOrdenDetalleImag> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabOrdenDetalleImagDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabOrdenDetalleImag> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ILabOrdenDetalleImagDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<LabOrdenDetalleImag> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabOrdenDetalleImagDao#obtenerAllActivos()
	 */
	@Override
	public List<LabOrdenDetalleImag> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
}
