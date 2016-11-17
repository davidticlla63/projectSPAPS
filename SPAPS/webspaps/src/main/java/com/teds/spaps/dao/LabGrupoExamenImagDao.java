package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ILabGrupoExamenImagDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabGrupoExamenImag;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabGrupoExamenImagDao extends DataAccessObjectJpa<LabGrupoExamenImag>
		implements ILabGrupoExamenImagDao {

	public LabGrupoExamenImagDao() {
		super(LabGrupoExamenImag.class);
	}

	@Override
	public LabGrupoExamenImag create(LabGrupoExamenImag especialidad) {
		return super.create(especialidad);
	}

	@Override
	public LabGrupoExamenImag update(LabGrupoExamenImag especialidad) {
		return super.update(especialidad);
	}

	@Override
	public LabGrupoExamenImag registrar(LabGrupoExamenImag especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "LabGrupoExamenImag "
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
	public LabGrupoExamenImag modificar(LabGrupoExamenImag especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "LabGrupoExamenImag "
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
	public boolean eliminar(LabGrupoExamenImag especialidad) {
		try {
			beginTransaction();
			LabGrupoExamenImag bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "LabGrupoExamenImag "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabGrupoExamenImag obtenerLabGrupoExamenImag(Integer id) {
		return findById(id);
	}

	@Override
	public LabGrupoExamenImag obtenerLabGrupoExamenImag(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<LabGrupoExamenImag> obtenerLabGrupoExamenImagOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabGrupoExamenImag> obtenerLabGrupoExamenImagOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabGrupoExamenImag> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabGrupoExamenImag> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabGrupoExamenImagDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabGrupoExamenImag> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ILabGrupoExamenImagDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<LabGrupoExamenImag> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabGrupoExamenImagDao#obtenerAllActivos()
	 */
	@Override
	public List<LabGrupoExamenImag> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
	
	@Override
	public List<LabGrupoExamenImag> obtenerAllGrupoExamen(Compania compania) {
		// TODO Auto-generated method stub
		String query = "select em.grupoExamen from LabExamenImag em  where em.estado='AC' and em.compania.id="
				+ compania.getId()
				+ "  and em.visualizar=true  order by em.id  asc";
		System.out.println(query);
		return (List<LabGrupoExamenImag>) executeQueryResulList(query);
	}
}
