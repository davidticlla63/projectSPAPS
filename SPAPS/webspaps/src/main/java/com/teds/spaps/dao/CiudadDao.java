package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ICiudadDao;
import com.teds.spaps.model.Ciudad;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class CiudadDao extends DataAccessObjectJpa<Ciudad>
		implements ICiudadDao {

	public CiudadDao() {
		super(Ciudad.class);
	}

	@Override
	public Ciudad create(Ciudad especialidad) {
		return super.create(especialidad);
	}

	@Override
	public Ciudad update(Ciudad especialidad) {
		return super.update(especialidad);
	}

	@Override
	public Ciudad registrar(Ciudad especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Ciudad "
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
	public Ciudad modificar(Ciudad especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Ciudad "
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
	public boolean eliminar(Ciudad especialidad) {
		try {
			beginTransaction();
			Ciudad bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Ciudad "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Ciudad obtenerCiudad(Integer id) {
		return findById(id);
	}

	@Override
	public Ciudad obtenerCiudad(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<Ciudad> obtenerCiudadOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Ciudad> obtenerCiudadOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Ciudad> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<Ciudad> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ICiudadDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Ciudad> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ICiudadDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<Ciudad> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ICiudadDao#obtenerAllActivos()
	 */
	@Override
	public List<Ciudad> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Ciudad> obtenerAllGrupoExamen(Compania compania) {
		// TODO Auto-generated method stub
		String query = "select em.grupoExamen from LabExamen em  where em.estado='AC' and em.compania.id="
				+ compania.getId()
				+ "  and em.visualizar=true and em.tipoExamen.descripcion like '%LABORATORIO%' order by em.id  asc";
		System.out.println(query);
		return (List<Ciudad>) executeQueryResulList(query);
	}
}
