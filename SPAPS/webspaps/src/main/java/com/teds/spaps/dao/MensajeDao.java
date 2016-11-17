package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IMensajeDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Mensaje;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Tarea;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class MensajeDao extends DataAccessObjectJpa<Mensaje> implements
		IMensajeDao {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public MensajeDao() {
		super(Mensaje.class);
	}

	@Override
	public Mensaje create(Mensaje mensaje) {
		return super.create(mensaje);
	}

	@Override
	public Mensaje update(Mensaje mensaje) {
		return super.update(mensaje);
	}

	@Override
	public Mensaje registrar(Mensaje Mensaje) {
		try {
			beginTransaction();
			Mensaje = create(Mensaje);
			commitTransaction();
			return Mensaje;
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
	public Mensaje modificar(Mensaje Mensaje) {
		try {
			beginTransaction();
			Mensaje = update(Mensaje);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta", "Mensaje "
						+ Mensaje.getMensaje());
			return Mensaje;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				if (!isDelete())
					FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean eliminar(Mensaje Mensaje) {
		try {
			setDelete(true);
			Mensaje.setEstado("RM");
			beginTransaction();
			Mensaje bar = update(Mensaje);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Grupo Sanguineo "
					+ Mensaje.toString());
			setDelete(false);
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public boolean verificarRepetido(Mensaje mensaje, Compania compania) {
		List<Mensaje> mensajes = obtenerPorCompania(compania);
		for (Mensaje mensaje2 : mensajes) {
			if (mensaje2.getMensaje().equalsIgnoreCase(mensaje.getMensaje())
					|| mensaje2.equals(mensaje))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(Mensaje mensaje, Compania compania) {
		List<Mensaje> mensajes = obtenerPorCompania(compania);
		for (Mensaje mensaje2 : mensajes) {
			if (mensaje2.getMensaje().equalsIgnoreCase(mensaje.getMensaje())
					&& !mensaje2.equals(mensaje))
				return true;
		}
		return false;
	}

	@Override
	public Mensaje obtenerMensaje(Integer id) {
		return findById(id);
	}

	@Override
	public Mensaje obtenerMensaje(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public List<Mensaje> obtenerMensajeOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Mensaje> obtenerMensajeOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Mensaje> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<Mensaje> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<Mensaje> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IMensajeDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Mensaje> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Mensaje> obtenerPorCompaniaAutoComplete(String mensaje,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"mensaje", mensaje, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Mensaje> obtenerPorSucursalAutoComplete(String mensaje,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"mensaje", mensaje, "estado", "AC", "sucursal",
				sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMensajeDao#obtenerPorTarea(com.teds.spaps
	 * .model.Tarea, com.teds.spaps.model.Compania)
	 */
	@Override
	public List<Mensaje> obtenerPorTarea(Tarea tarea, Compania compania) {
		return findAllActivosByParameterTwo("tarea", "compania", tarea.getId(),
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMensajeDao#obtenerPorTarea(com.teds.spaps
	 * .model.Tarea, com.teds.spaps.model.Sucursal)
	 */
	@Override
	public List<Mensaje> obtenerPorTarea(Tarea tarea, Sucursal sucursal) {
		return findAllActivosByParameterTwo("tarea", "sucursal", tarea.getId(),
				sucursal.getId());
	}

}
