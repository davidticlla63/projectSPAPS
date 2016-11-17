/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import com.teds.spaps.interfaces.dao.IPlanesSeguroDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.PlanesSeguro;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
public class PlanesSeguroDao extends DataAccessObjectJpa<PlanesSeguro>
		implements IPlanesSeguroDao {

	public PlanesSeguroDao() {
		super(PlanesSeguro.class);
	}

	@Override
	public PlanesSeguro create(PlanesSeguro planesSeguro) {
		return super.create(planesSeguro);
	}

	@Override
	public PlanesSeguro update(PlanesSeguro planesSeguro) {
		return super.update(planesSeguro);
	}

	@Override
	public void delete(PlanesSeguro planesSeguro) {
		super.deleteReal(planesSeguro);
	}

	@Override
	public PlanesSeguro registrar(PlanesSeguro planesSeguro) {
		try {
			beginTransaction();
			planesSeguro = create(planesSeguro);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Planes de Seguro "
					+ planesSeguro.toString());
			return planesSeguro;
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
	public PlanesSeguro modificar(PlanesSeguro planesSeguro) {
		try {
			beginTransaction();
			planesSeguro = update(planesSeguro);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Planes de Seguro "
					+ planesSeguro.toString());
			return planesSeguro;
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
	public boolean eliminar(PlanesSeguro planesSeguro) {
		try {
			planesSeguro.setEstado("RM");
			beginTransaction();
			PlanesSeguro bar = update(planesSeguro);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Planes de Seguro "
					+ planesSeguro.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public boolean deletePS(PlanesSeguro planesSeguro) {
		try {
			planesSeguro.setEstado("RM");
			beginTransaction();
			deleteReal(planesSeguro);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Planes de Seguro "
					+ planesSeguro.toString());
			return true;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public PlanesSeguro obtenerPlanesSeguro(Integer id) {
		return findById(id);
	}

	@Override
	public PlanesSeguro obtenrPlanesSeguro(Integer id, Compania compania) {
		return findByParameterObjectTwo("id", "compania", id, compania.getId());
	}

	@Override
	public PlanesSeguro obtenerPlanesSeguro(String codigo) {
		return findByParameter("codigo", codigo);
	}

	@Override
	public PlanesSeguro obtenerPlanesSeguro(String codigo, Compania compania) {
		return findByParameterObjectTwo("codigo", "compania", codigo,
				compania.getId());
	}

	@Override
	public PlanesSeguro obtenerPlanesSeguroPorDescripcion(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public PlanesSeguro obtenerPlanesSeguroPorDescripcion(String descripcion,
			Compania compania) {
		return findByParameterObjectTwo("descripcion", "compania", descripcion,
				compania.getId());
	}

	@Override
	public List<PlanesSeguro> obtenerPlanesSeguroOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<PlanesSeguro> obtenerPlanesSeguroOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<PlanesSeguro> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<PlanesSeguro> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<PlanesSeguro> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPlanesSeguroDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<PlanesSeguro> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<PlanesSeguro> obtenerPorIndividuo(Paciente paciente) {
		return findAllByParameter("paciente", paciente.getId());
	}

}
