/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDesgServGrupoServicioDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgServGrupo;
import com.teds.spaps.model.DesgServGrupoServicio;
import com.teds.spaps.model.Servicio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DesgServGrupoServicioDao extends
		DataAccessObjectJpa<DesgServGrupoServicio> implements
		IDesgServGrupoServicioDao {

	/**
	 * @param typeT
	 */
	public DesgServGrupoServicioDao() {
		super(DesgServGrupoServicio.class);
	}

	@Override
	public DesgServGrupoServicio create(DesgServGrupoServicio detalleAntecedente) {
		return super.create(detalleAntecedente);
	}

	@Override
	public DesgServGrupoServicio update(DesgServGrupoServicio detalleAntecedente) {
		return super.update(detalleAntecedente);
	}

	@Override
	public void delete(DesgServGrupoServicio detalleAntecedente) {
		super.deleteReal(detalleAntecedente);
	}

	@Override
	public DesgServGrupoServicio registrar(
			DesgServGrupoServicio detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = create(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DesgServGrupoServicio "
					+ detalleAntecedente.toString());
			return detalleAntecedente;
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
	public boolean registrar(List<DesgServGrupoServicio> antecedentes) {
		try {
			beginTransaction();
			for (DesgServGrupoServicio detalleAntecedente : antecedentes) {
				create(detalleAntecedente);
			}
			commitTransaction();
			return true;
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
			return false;
		}
	}

	@Override
	public DesgServGrupoServicio modificar(
			DesgServGrupoServicio detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DesgServGrupoServicio " + detalleAntecedente.toString());
			return detalleAntecedente;
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
	public boolean eliminar(DesgServGrupoServicio detalleAntecedente) {
		try {
			detalleAntecedente.setEstado("RM");
			beginTransaction();
			DesgServGrupoServicio bar = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"DesgServGrupoServicio " + detalleAntecedente.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgServGrupoServicioDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DesgServGrupoServicio> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgServGrupoServicioDao#
	 * obtenerDesgServGrupoServicio(java.lang.Integer)
	 */
	@Override
	public DesgServGrupoServicio obtenerDesgServGrupoServicio(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgServGrupoServicioDao#
	 * obtenerDesgServGrupoServicioOrdenAscPorId()
	 */
	@Override
	public List<DesgServGrupoServicio> obtenerDesgServGrupoServicioOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgServGrupoServicioDao#
	 * obtenerDesgServGrupoServicioOrdenDescPorId()
	 */
	@Override
	public List<DesgServGrupoServicio> obtenerDesgServGrupoServicioOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgServGrupoServicioDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DesgServGrupoServicio> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgServGrupoServicioDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DesgServGrupoServicio> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public boolean verificarServicioRegistrado(Servicio servicio,
			Sucursal sucursal) {
		List<DesgServGrupoServicio> examenes = obtenerPorSucursal(sucursal);
		for (DesgServGrupoServicio desgServGrupoServicio : examenes) {
			if (desgServGrupoServicio.getServicio().equals(servicio))
				return true;
		}
		return false;
	}

	@Override
	public List<DesgServGrupoServicio> obtenerPorServicio(Servicio servicio) {
		return findAllActivosByParameter("servicio", servicio.getId());
	}

	@Override
	public List<DesgServGrupoServicio> obtenerPorGrupo(DesgServGrupo grupo) {
		return findAllActivosByParameter("grupo", grupo.getId());
	}

	@Override
	public List<Servicio> obtenerServiciosPorGrupo(DesgServGrupo grupo) {
		List<Servicio> resultado = new ArrayList<Servicio>();
		List<DesgServGrupoServicio> list = obtenerPorGrupo(grupo);
		for (DesgServGrupoServicio desgLabGrupoExamen : list) {
			resultado.add(desgLabGrupoExamen.getServicio());
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgServGrupoServicioDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DesgServGrupoServicio> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
