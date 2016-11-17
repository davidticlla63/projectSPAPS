package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.teds.spaps.interfaces.dao.ISeguroDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Seguro;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class SeguroDao extends DataAccessObjectJpa<Seguro> implements
		ISeguroDao {

	public SeguroDao() {
		super(Seguro.class);
	}

	@Override
	public Seguro create(Seguro seguro) {
		return super.create(seguro);
	}

	@Override
	public Seguro update(Seguro seguro) {
		return super.update(seguro);
	}

	@Override
	public Seguro registrar(Seguro seguro) {
		try {
			beginTransaction();
			seguro = create(seguro);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Seguro " + seguro.getNombre());
			return seguro;
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
	public Seguro modificar(Seguro seguro) {
		try {
			beginTransaction();
			seguro = update(seguro);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Seguro " + seguro.getNombre());
			return seguro;
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
	public boolean eliminar(Seguro seguro) {
		try {
			seguro.setEstado("RM");
			beginTransaction();
			Seguro bar = update(seguro);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Seguro " + seguro.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Seguro obtenerSeguro(Integer id) {
		return findById(id);
	}

	@Override
	public Seguro obtenerSeguro(Integer id, Compania compania) {
		return findByParameterObjectTwo("id", "compania", id, compania.getId());
	}

	@Override
	public Seguro obtenerSeguro(String codigo) {
		return findByParameter("codigo", codigo);
	}

	@Override
	public Seguro obtenerSeguro(String codigo, Compania compania) {
		return findByParameterObjectTwo("codigo", "compania", codigo,
				compania.getId());
	}

	@Override
	public Seguro obtenerSeguroPorNombre(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public Seguro obtenerSeguroPorDescripcion(String descripcion,
			Compania compania) {
		return findByParameterObjectTwo("descripcion", "compania", descripcion,
				compania.getId());
	}

	@Override
	public List<Seguro> obtenerSeguroOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Seguro> obtenerSeguroOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Seguro> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<Seguro> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	@Override
	public List<Seguro> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<Seguro> obtenerPorCompania(String nombre, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ISeguroDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Seguro> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}
	@Override
	public Seguro DevolverSeguroPorId(Integer id){
		String query = "SELECT NEW com.teds.spaps.model.Seguro(p.id, p.nombre)FROM Seguro AS p where p.id="+id;
		Query q = getEntityManager().createQuery(query);
		System.out.println(q.getSingleResult());
		return (Seguro)q.getSingleResult();
	}
	@Override
	public List<Seguro> devolverSeguroOnCompleteCompania(Compania compania, String nombre){
		String query =  "select NEW com.teds.spaps.model.Seguro(p.id, p.nombre)from Seguro p where upper(translate(p.nombre, 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ nombre + "%'";
		Query q = getEntityManager().createQuery(query);
		return (List<Seguro>)q.getResultList();
	}
}
