package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.primefaces.model.TreeNode;

import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.interfaces.dao.IPersonalEspecialidadDao;
import com.teds.spaps.interfaces.dao.IRrHhDiaTurnoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.PersonalEspecialidad;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class PersonalDao extends DataAccessObjectJpa<Personal> implements
		IPersonalDao {

	private @Inject IPersonalEspecialidadDao PersonalEspecialidadDao;
	private @Inject IRrHhDiaTurnoDao hhPesonalDiaTurnoDao;

	public PersonalDao() {
		super(Personal.class);
	}

	@Override
	public Personal create(Personal personal) {
		return super.create(personal);
	}

	@Override
	public Personal update(Personal personal) {
		return super.update(personal);
	}
	
	@Override
	public Personal registrar(Personal personal) {
		try {
			beginTransaction();
			personal = create(personal);

			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Personal " + personal.toString());
			return personal;
		} catch (Exception e) {
			String cause = e.getMessage();
			System.out.println(cause);
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
	public Personal registrar(Personal personal,
			List<PersonalEspecialidad> especialidades, TreeNode[] selectedNodes) {
		try {
			beginTransaction();

			for (PersonalEspecialidad PersonalEspecialidad : especialidades) {
				PersonalEspecialidad.setPersonal(personal);
				PersonalEspecialidad.setFechaRegistro(personal
						.getFechaRegistro());
				PersonalEspecialidad.setFechaModificacion(personal
						.getFechaRegistro());
				PersonalEspecialidad.setUsuarioRegistro(personal
						.getUsuarioRegistro());
				personal.getListEspecialidades().add(PersonalEspecialidad);

			}
			personal = create(personal);

			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Personal " + personal.toString());
			return personal;
		} catch (Exception e) {
			String cause = e.getMessage();
			System.out.println(cause);
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
	public Personal modificar(Personal personal,
			List<PersonalEspecialidad> especialidadesMeter,
			TreeNode[] selectedNodes) {
		try {
			beginTransaction();
			personal.setListEspecialidades(new ArrayList<PersonalEspecialidad>());
			if (hhPesonalDiaTurnoDao.deleteDetail(personal)) {	
				System.out.println("Elimino sus detalles Horarios");
				PersonalEspecialidadDao.deleteDetail(personal);
				System.out.println("Elimino sus detalles");
			}else{
				System.out.println("No Elimino sus detalles");
				PersonalEspecialidadDao.deleteDetail(personal);
			}

			for (PersonalEspecialidad PersonalEspecialidad : especialidadesMeter) {
				PersonalEspecialidad.setPersonal(personal);
				PersonalEspecialidad.setFechaRegistro(personal
						.getFechaRegistro());
				PersonalEspecialidad.setFechaModificacion(personal
						.getFechaRegistro());
				PersonalEspecialidad.setUsuarioRegistro(personal
						.getUsuarioRegistro());
				personal.getListEspecialidades().add(PersonalEspecialidad);
				// PersonalEspecialidadDao.delete(PersonalEspecialidad);
			}
			personal = update(personal);

			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Personal "
					+ personal.toString());
			return personal;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual. "+cause);
			} else {
				FacesUtil.errorMessage("Error al modificar "+cause);
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean eliminar(Personal personal) {
		try {
			personal.setEstado("RM");
			beginTransaction();
			personal = update(personal);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Personal "
					+ personal.toString());
			return personal != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Personal obtenerPersonal(Integer id) {
		return findById(id);
	}

	@Override
	public List<Personal> obtenerPersonalOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Personal> obtenerPersonalOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Personal> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActiveParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<Personal> obtenerPorCompania(String nombre, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

	@Override
	public List<Personal> obtenerPorUsuario(Usuario usuario) {
		return findAllActiveByParameter("usuario", usuario.getId());
	}

	@Override
	public Personal obtenerPorUsuario(Usuario usuario, Sucursal sucursal) {
		return findByParameterObjectTwo("usuario", "sucursal", usuario.getId(),
				sucursal.getId());
	}

	@Override
	public Personal obtenerPorUsuario(Usuario usuario, Compania compania) {
		return findByParameterObjectTwo("usuario", "compania", usuario.getId(),
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPersonalDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Personal> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Personal> obtenerPorSucursal(String nombre, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

	@Override
	public List<Personal> obtenerPorSucursalAndEpecialidad(String descripcion,
			Especialidad especialidad, Sucursal sucursal) {
		return findAllAndParameterForNameAutoCompleteThwoTables("nombre",
				descripcion, "estado", "AC", "personal",
				"PersonalEspecialidad", "especialidad", especialidad.getId(),
				"sucursal", sucursal.getId());
	}

	@Override
	public List<Personal> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Personal> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "sucursal",
				sucursal.getId());
	}

	@Override
	public List<Personal> obtenerPorNombreCompania(String nombre,
			String apellidoPaterno, String apellidoMaterno, Compania compania) {
		/*
		 * String query = "select em from Paciente em where em.nombre like '%" +
		 * nombre + "%' or em.apellidoPaterno like '%" + apellidoPaterno +
		 * "%' or em.apellidoMaterno like '%" + apellidoMaterno +
		 * "%' and estado='AC' and em.compania=" + compania.getId();
		 */
		// return
		// findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
		// nombre, "apellidoPaterno", apellidoPaterno, "apellidoMaterno",
		// apellidoMaterno, "estado", "AC", "compania", compania.getId());
		return findAllActiveOtherTableAndParameterForNamesAutoComplete(
				"nombre", nombre, "apellidoPaterno", apellidoPaterno,
				"apellidoMaterno", apellidoMaterno, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Personal> obtenerPorNombreSucursal(String nombre,
			String apellidoPaterno, String apellidoMaterno, Sucursal sucursal) {
		/*
		 * String query = "select em from Paciente em where em.nombre like '%" +
		 * nombre + "%' or em.apellidoPaterno like '%" + apellidoPaterno +
		 * "%' or em.apellidoMaterno like '%" + apellidoMaterno +
		 * "%' and estado='AC' and em.compania=" + compania.getId();
		 */
		// return
		// findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
		// nombre, "apellidoPaterno", apellidoPaterno, "apellidoMaterno",
		// apellidoMaterno, "estado", "AC", "compania", compania.getId());
		return findAllActiveOtherTableAndParameterForNamesAutoComplete(
				"nombre", nombre, "apellidoPaterno", apellidoPaterno,
				"apellidoMaterno", apellidoMaterno, "estado", "AC", "sucursal",
				sucursal.getId());
	}

}
