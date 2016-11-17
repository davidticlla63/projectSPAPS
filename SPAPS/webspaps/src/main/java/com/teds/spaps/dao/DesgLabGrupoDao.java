package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IDesgLabGrupoDao;
import com.teds.spaps.interfaces.dao.IDesgLabGrupoExamenDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgLabGrupo;
import com.teds.spaps.model.DesgLabGrupoExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDDesgOrdenLabExamen;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class DesgLabGrupoDao extends DataAccessObjectJpa<DesgLabGrupo>
		implements IDesgLabGrupoDao {

	private @Inject IDesgLabGrupoExamenDao labGrupoExamenDao;

	public DesgLabGrupoDao() {
		super(DesgLabGrupo.class);
	}

	@Override
	public DesgLabGrupo create(DesgLabGrupo desgLabGrupo) {
		return super.create(desgLabGrupo);
	}

	@Override
	public DesgLabGrupo update(DesgLabGrupo desgLabGrupo) {
		return super.update(desgLabGrupo);
	}

	@Override
	public DesgLabGrupo registrar(DesgLabGrupo desgLabGrupo) {
		try {
			beginTransaction();
			desgLabGrupo = create(desgLabGrupo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DesgLabGrupo "
					+ desgLabGrupo.toString());
			return desgLabGrupo;
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
	public DesgLabGrupo registrar(DesgLabGrupo desgLabGrupo,
			List<DesgLabGrupoExamen> examenes) {
		try {
			beginTransaction();
			desgLabGrupo = create(desgLabGrupo);
			for (DesgLabGrupoExamen desgLabGrupoExamen : examenes) {
				desgLabGrupoExamen.setGrupo(desgLabGrupo);
				desgLabGrupoExamen.setCompania(desgLabGrupo.getCompania());
				desgLabGrupoExamen.setEstado(desgLabGrupo.getEstado());
				desgLabGrupoExamen.setFechaModificacion(desgLabGrupo
						.getFechaModificacion());
				desgLabGrupoExamen.setFechaRegistro(desgLabGrupo
						.getFechaRegistro());
				desgLabGrupoExamen.setSucursal(desgLabGrupo.getSucursal());
				desgLabGrupoExamen.setUsuarioRegistro(desgLabGrupo
						.getUsuarioRegistro());
				labGrupoExamenDao.create(desgLabGrupoExamen);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DesgLabGrupo "
					+ desgLabGrupo.toString());
			return desgLabGrupo;
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
	public DesgLabGrupo modificar(DesgLabGrupo desgLabGrupo) {
		try {
			beginTransaction();
			desgLabGrupo = update(desgLabGrupo);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "DesgLabGrupo "
					+ desgLabGrupo.toString());
			return desgLabGrupo;
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
	public DesgLabGrupo modificar(DesgLabGrupo desgLabGrupo,
			List<DesgLabGrupoExamen> examenesSacar,
			List<DesgLabGrupoExamen> examenesMeter) {
		try {
			beginTransaction();
			desgLabGrupo = update(desgLabGrupo);
			for (DesgLabGrupoExamen desgLabGrupoExamen : examenesSacar) {
				labGrupoExamenDao.delete(desgLabGrupoExamen);
			}
			for (DesgLabGrupoExamen desgLabGrupoExamen : examenesMeter) {
				desgLabGrupoExamen.setId(null);
				desgLabGrupoExamen.setGrupo(desgLabGrupo);
				desgLabGrupoExamen.setCompania(desgLabGrupo.getCompania());
				desgLabGrupoExamen.setEstado(desgLabGrupo.getEstado());
				desgLabGrupoExamen.setFechaModificacion(desgLabGrupo
						.getFechaModificacion());
				desgLabGrupoExamen.setFechaRegistro(desgLabGrupo
						.getFechaRegistro());
				desgLabGrupoExamen.setSucursal(desgLabGrupo.getSucursal());
				desgLabGrupoExamen.setUsuarioRegistro(desgLabGrupo
						.getUsuarioRegistro());
				labGrupoExamenDao.create(desgLabGrupoExamen);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "DesgLabGrupo "
					+ desgLabGrupo.toString());
			return desgLabGrupo;
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
	public boolean eliminar(DesgLabGrupo desgLabGrupo) {
		try {
			beginTransaction();
			DesgLabGrupo bar = update(desgLabGrupo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DesgLabGrupo "
					+ desgLabGrupo.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public boolean eliminar(DesgLabGrupo desgLabGrupo,
			List<DesgLabGrupoExamen> examenes) {
		try {
			beginTransaction();
			desgLabGrupo.setEstado("RM");
			desgLabGrupo = update(desgLabGrupo);
			for (DesgLabGrupoExamen desgLabGrupoExamen : examenes) {
				desgLabGrupoExamen.setEstado(desgLabGrupo.getEstado());
				labGrupoExamenDao.update(desgLabGrupoExamen);
			}
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DesgLabGrupo "
					+ desgLabGrupo.toString());
			return desgLabGrupo != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public DesgLabGrupo obtenerDesgLabGrupo(Integer id) {
		return findById(id);
	}

	@Override
	public DesgLabGrupo obtenerDesgLabGrupo(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public List<DesgLabGrupo> obtenerDesgLabGrupoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<DesgLabGrupo> obtenerDesgLabGrupoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<DesgLabGrupo> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<DesgLabGrupo> obtenerPorCompania(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IDesgLabGrupoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DesgLabGrupo> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IDesgLabGrupoDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<DesgLabGrupo> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IDesgLabGrupoDao#obtenerAllActivos()
	 */
	@Override
	public List<DesgLabGrupo> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<DesgLabGrupo> obtenerAllGrupoExamen(Compania compania) {
		// TODO Auto-generated method stub
		String query = "select em.grupoExamen from DesgLabGrupo em  where em.estado='AC' and em.compania.id="
				+ compania.getId()
				+ "  and em.visualizar=true  order by em.id  asc";
		System.out.println(query);
		return (List<DesgLabGrupo>) executeQueryResulList(query);
	}

	@Override
	public boolean verificarRepetido(DesgLabGrupo desgLabGrupo,
			Compania compania) {
		List<DesgLabGrupo> desgLabGrupos = obtenerPorCompania(compania);
		for (DesgLabGrupo desgLabGrupo2 : desgLabGrupos) {
			if (desgLabGrupo2.getDescripcion().equalsIgnoreCase(
					desgLabGrupo.getDescripcion())
					|| desgLabGrupo2.equals(desgLabGrupo))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(DesgLabGrupo desgLabGrupo,
			Compania compania) {
		List<DesgLabGrupo> desgLabGrupos = obtenerPorCompania(compania);
		for (DesgLabGrupo desgLabGrupo2 : desgLabGrupos) {
			if (desgLabGrupo2.getDescripcion().equalsIgnoreCase(
					desgLabGrupo.getDescripcion())
					&& !desgLabGrupo2.equals(desgLabGrupo))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgLabGrupoDao#delete(com.teds.spaps.
	 * model.DesgLabGrupo)
	 */
	@Override
	public void delete(DesgLabGrupo grupo) {
		super.deleteReal(grupo);
	}

	@Override
	public List<EDDesgOrdenLabExamen> obtenerEDGrupos(Sucursal sucursal) {
		List<EDDesgOrdenLabExamen> resultado = new ArrayList<EDDesgOrdenLabExamen>();
		List<DesgLabGrupo> grupos = obtenerPorSucursal(sucursal);
		for (DesgLabGrupo desgLabGrupo : grupos) {
			List<DesgLabGrupoExamen> examenes = labGrupoExamenDao
					.obtenerPorGrupo(desgLabGrupo);
			EDDesgOrdenLabExamen ed = new EDDesgOrdenLabExamen();
			ed.setGrupoExamen(desgLabGrupo);
			ed.setListaExamen(examenes);
			resultado.add(ed);
		}
		return resultado;
	}
}
