package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ILabGrupoExamenDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabGrupoExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabGrupoExamenDao extends DataAccessObjectJpa<LabGrupoExamen>
		implements ILabGrupoExamenDao {

	public LabGrupoExamenDao() {
		super(LabGrupoExamen.class);
	}

	@Override
	public LabGrupoExamen create(LabGrupoExamen especialidad) {
		return super.create(especialidad);
	}

	@Override
	public LabGrupoExamen update(LabGrupoExamen especialidad) {
		return super.update(especialidad);
	}

	@Override
	public LabGrupoExamen registrar(LabGrupoExamen especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "LabGrupoExamen "
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
	public LabGrupoExamen modificar(LabGrupoExamen especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "LabGrupoExamen "
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
	public boolean eliminar(LabGrupoExamen especialidad) {
		try {
			beginTransaction();
			LabGrupoExamen bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "LabGrupoExamen "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabGrupoExamen obtenerLabGrupoExamen(Integer id) {
		return findById(id);
	}

	@Override
	public LabGrupoExamen obtenerLabGrupoExamen(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<LabGrupoExamen> obtenerLabGrupoExamenOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabGrupoExamen> obtenerLabGrupoExamenOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabGrupoExamen> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabGrupoExamen> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabGrupoExamenDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabGrupoExamen> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ILabGrupoExamenDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<LabGrupoExamen> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabGrupoExamenDao#obtenerAllActivos()
	 */
	@Override
	public List<LabGrupoExamen> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
	
	
	@Override
	public List<LabGrupoExamen> obtenerAllGrupoExamen(Compania compania) {
		// TODO Auto-generated method stub
		String query = "select em.grupoExamen from LabExamen em  where em.estado='AC' and em.compania.id="
				+ compania.getId()
				+ "  and em.visualizar=true  order by em.id  asc";
		System.out.println(query);
		return (List<LabGrupoExamen>) executeQueryResulList(query);
	}
}
