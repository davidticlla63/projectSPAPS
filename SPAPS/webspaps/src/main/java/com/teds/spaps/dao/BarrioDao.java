package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IBarrioDao;
import com.teds.spaps.model.Barrio;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class BarrioDao extends DataAccessObjectJpa<Barrio> implements
		IBarrioDao {

	private boolean isDelete;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public BarrioDao() {
		super(Barrio.class);
	}

	@Override
	public Barrio create(Barrio barrio) {
		return super.create(barrio);
	}

	@Override
	public Barrio update(Barrio barrio) {
		return super.update(barrio);
	}

	@Override
	public Barrio registrar(Barrio barrio) {
		try {
			beginTransaction();
			barrio = create(barrio);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Barrio " + barrio.getNombre());
			return barrio;
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
	public Barrio modificar(Barrio barrio) {
		try {
			beginTransaction();
			barrio = update(barrio);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta", "Barrio "
						+ barrio.getNombre());
			return barrio;
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
	public boolean eliminar(Barrio barrio) {
		try {
			setDelete(true);
			barrio.setEstado("RM");
			beginTransaction();
			Barrio bar = update(barrio);
			commitTransaction();
			setDelete(false);
			FacesUtil.infoMessage("Eliminación Correcta",
					"Barrio " + barrio.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public Barrio obtenerBarrio(Integer id) {
		return findById(id);
	}

	@Override
	public List<Barrio> obtenerBarrioOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Barrio> obtenerBarrioOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Barrio> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania);
	}

	@Override
	public boolean verificarRepetido(Barrio barrio, Compania compania) {
		List<Barrio> barrios = obtenerPorCompania(compania);
		for (Barrio barrio2 : barrios) {
			if (barrio2.getNombre().equalsIgnoreCase(barrio.getNombre())
					|| barrio2.equals(barrio))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(Barrio barrio, Compania compania) {
		List<Barrio> barrios = obtenerPorCompania(compania);
		for (Barrio barrio2 : barrios) {
			if (barrio2.getNombre().equalsIgnoreCase(barrio.getNombre()) &&!barrio2.equals(barrio))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IBarrioDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Barrio> obtenerTodosActivosEInactivosPorOrdenAsc() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDocumentoIdentidadDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<Barrio> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("nombre", query);
	}

	@Override
	public List<Barrio> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public List<Barrio> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

}
