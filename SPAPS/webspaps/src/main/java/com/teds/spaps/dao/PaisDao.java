package com.teds.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IPaisDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Pais;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class PaisDao extends DataAccessObjectJpa<Pais> implements IPaisDao {

	public PaisDao() {
		super(Pais.class);
	}

	@Override
	public Pais create(Pais pais) {
		return super.create(pais);
	}

	@Override
	public Pais update(Pais pais) {
		return super.update(pais);
	}

	@Override
	public Pais registrar(Pais pais) {
		try {
			beginTransaction();
			pais = create(pais);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Pais " + pais.getNombre());
			return pais;
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
	public Pais modificar(Pais pais) {
		try {
			beginTransaction();
			pais = update(pais);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Pais " + pais.getNombre());
			return pais;
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
	public boolean eliminar(Pais pais) {
		try {
			beginTransaction();
			pais.setEstado("RM");
			pais.setFechaModificacion(new Date());
			Pais bar = update(pais);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Pais " + pais.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Pais obtenerPais(Integer id) {
		return findById(id);
	}

	@Override
	public List<Pais> obtenerPaisOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Pais> obtenerPaisOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Pais> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<Pais> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public List<Pais> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPaisDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Pais> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

}
