package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IDesgImagGrupoDao;
import com.teds.spaps.interfaces.dao.IDesgImagGrupoExamenDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgImagGrupo;
import com.teds.spaps.model.DesgImagGrupoExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDDesgOrdenImagExamen;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class DesgImagGrupoDao extends DataAccessObjectJpa<DesgImagGrupo>
		implements IDesgImagGrupoDao {

	private @Inject IDesgImagGrupoExamenDao imagGrupoExamenDao;

	public DesgImagGrupoDao() {
		super(DesgImagGrupo.class);
	}

	@Override
	public DesgImagGrupo create(DesgImagGrupo desgImagGrupo) {
		return super.create(desgImagGrupo);
	}

	@Override
	public DesgImagGrupo update(DesgImagGrupo desgImagGrupo) {
		return super.update(desgImagGrupo);
	}

	@Override
	public DesgImagGrupo registrar(DesgImagGrupo desgImagGrupo) {
		try {
			beginTransaction();
			desgImagGrupo = create(desgImagGrupo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DesgImagGrupo "
					+ desgImagGrupo.toString());
			return desgImagGrupo;
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
	public DesgImagGrupo registrar(DesgImagGrupo desgImagGrupo,
			List<DesgImagGrupoExamen> examenes) {
		try {
			beginTransaction();
			desgImagGrupo = create(desgImagGrupo);
			for (DesgImagGrupoExamen desgImagGrupoExamen : examenes) {
				desgImagGrupoExamen.setGrupo(desgImagGrupo);
				desgImagGrupoExamen.setCompania(desgImagGrupo.getCompania());
				desgImagGrupoExamen.setEstado(desgImagGrupo.getEstado());
				desgImagGrupoExamen.setFechaModificacion(desgImagGrupo
						.getFechaModificacion());
				desgImagGrupoExamen.setFechaRegistro(desgImagGrupo
						.getFechaRegistro());
				desgImagGrupoExamen.setSucursal(desgImagGrupo.getSucursal());
				desgImagGrupoExamen.setUsuarioRegistro(desgImagGrupo
						.getUsuarioRegistro());
				imagGrupoExamenDao.create(desgImagGrupoExamen);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DesgImagGrupo "
					+ desgImagGrupo.toString());
			return desgImagGrupo;
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
	public DesgImagGrupo modificar(DesgImagGrupo desgImagGrupo) {
		try {
			beginTransaction();
			desgImagGrupo = update(desgImagGrupo);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "DesgImagGrupo "
					+ desgImagGrupo.toString());
			return desgImagGrupo;
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
	public DesgImagGrupo modificar(DesgImagGrupo desgImagGrupo,
			List<DesgImagGrupoExamen> examenesSacar,
			List<DesgImagGrupoExamen> examenesMeter) {
		try {
			beginTransaction();
			desgImagGrupo = update(desgImagGrupo);
			for (DesgImagGrupoExamen desgImagGrupoExamen : examenesSacar) {
				imagGrupoExamenDao.delete(desgImagGrupoExamen);
			}
			for (DesgImagGrupoExamen desgImagGrupoExamen : examenesMeter) {
				desgImagGrupoExamen.setId(null);
				desgImagGrupoExamen.setGrupo(desgImagGrupo);
				desgImagGrupoExamen.setCompania(desgImagGrupo.getCompania());
				desgImagGrupoExamen.setEstado(desgImagGrupo.getEstado());
				desgImagGrupoExamen.setFechaModificacion(desgImagGrupo
						.getFechaModificacion());
				desgImagGrupoExamen.setFechaRegistro(desgImagGrupo
						.getFechaRegistro());
				desgImagGrupoExamen.setSucursal(desgImagGrupo.getSucursal());
				desgImagGrupoExamen.setUsuarioRegistro(desgImagGrupo
						.getUsuarioRegistro());
				imagGrupoExamenDao.create(desgImagGrupoExamen);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "DesgImagGrupo "
					+ desgImagGrupo.toString());
			return desgImagGrupo;
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
	public boolean eliminar(DesgImagGrupo desgImagGrupo) {
		try {
			beginTransaction();
			DesgImagGrupo bar = update(desgImagGrupo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DesgImagGrupo "
					+ desgImagGrupo.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public boolean eliminar(DesgImagGrupo desgImagGrupo,
			List<DesgImagGrupoExamen> examenes) {
		try {
			beginTransaction();
			desgImagGrupo.setEstado("RM");
			desgImagGrupo = update(desgImagGrupo);
			for (DesgImagGrupoExamen desgImagGrupoExamen : examenes) {
				desgImagGrupoExamen.setEstado(desgImagGrupo.getEstado());
				imagGrupoExamenDao.update(desgImagGrupoExamen);
			}
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DesgImagGrupo "
					+ desgImagGrupo.toString());
			return desgImagGrupo != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public DesgImagGrupo obtenerDesgImagGrupo(Integer id) {
		return findById(id);
	}

	@Override
	public DesgImagGrupo obtenerDesgImagGrupo(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public List<DesgImagGrupo> obtenerDesgImagGrupoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<DesgImagGrupo> obtenerDesgImagGrupoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<DesgImagGrupo> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<DesgImagGrupo> obtenerPorCompania(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IDesgImagGrupoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DesgImagGrupo> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IDesgImagGrupoDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<DesgImagGrupo> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IDesgImagGrupoDao#obtenerAllActivos()
	 */
	@Override
	public List<DesgImagGrupo> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<DesgImagGrupo> obtenerAllGrupoExamen(Compania compania) {
		// TODO Auto-generated method stub
		String query = "select em.grupoExamen from DesgImagGrupo em  where em.estado='AC' and em.compania.id="
				+ compania.getId()
				+ "  and em.visualizar=true  order by em.id  asc";
		System.out.println(query);
		return (List<DesgImagGrupo>) executeQueryResulList(query);
	}

	@Override
	public boolean verificarRepetido(DesgImagGrupo desgImagGrupo,
			Compania compania) {
		List<DesgImagGrupo> desgImagGrupos = obtenerPorCompania(compania);
		for (DesgImagGrupo desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getDescripcion().equalsIgnoreCase(
					desgImagGrupo.getDescripcion())
					|| desgImagGrupo2.equals(desgImagGrupo))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(DesgImagGrupo desgImagGrupo,
			Compania compania) {
		List<DesgImagGrupo> desgImagGrupos = obtenerPorCompania(compania);
		for (DesgImagGrupo desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getDescripcion().equalsIgnoreCase(
					desgImagGrupo.getDescripcion())
					&& !desgImagGrupo2.equals(desgImagGrupo))
				return true;
		}
		return false;
	}

	@Override
	public List<EDDesgOrdenImagExamen> obtenerEDGrupos(Sucursal sucursal) {
		List<EDDesgOrdenImagExamen> resultado = new ArrayList<EDDesgOrdenImagExamen>();
		List<DesgImagGrupo> grupos = obtenerPorSucursal(sucursal);
		for (DesgImagGrupo desgLabGrupo : grupos) {
			List<DesgImagGrupoExamen> examenes = imagGrupoExamenDao
					.obtenerPorGrupo(desgLabGrupo);
			EDDesgOrdenImagExamen ed = new EDDesgOrdenImagExamen();
			ed.setGrupoExamen(desgLabGrupo);
			ed.setListaExamen(examenes);
			resultado.add(ed);
		}
		return resultado;
	}

}
