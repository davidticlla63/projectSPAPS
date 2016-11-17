package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IDesgServGrupoDao;
import com.teds.spaps.interfaces.dao.IDesgServGrupoServicioDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgServGrupo;
import com.teds.spaps.model.DesgServGrupoServicio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDDesgOrdenServicio;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class DesgServGrupoDao extends DataAccessObjectJpa<DesgServGrupo>
		implements IDesgServGrupoDao {

	private @Inject IDesgServGrupoServicioDao servGrupoServicioDao;

	public DesgServGrupoDao() {
		super(DesgServGrupo.class);
	}

	@Override
	public DesgServGrupo create(DesgServGrupo desgServGrupo) {
		return super.create(desgServGrupo);
	}

	@Override
	public DesgServGrupo update(DesgServGrupo desgServGrupo) {
		return super.update(desgServGrupo);
	}

	@Override
	public DesgServGrupo registrar(DesgServGrupo desgServGrupo) {
		try {
			beginTransaction();
			desgServGrupo = create(desgServGrupo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DesgServGrupo "
					+ desgServGrupo.toString());
			return desgServGrupo;
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
	public DesgServGrupo registrar(DesgServGrupo desgServGrupo,
			List<DesgServGrupoServicio> examenes) {
		try {
			beginTransaction();
			desgServGrupo = create(desgServGrupo);
			for (DesgServGrupoServicio desgServGrupoExamen : examenes) {
				desgServGrupoExamen.setGrupo(desgServGrupo);
				desgServGrupoExamen.setCompania(desgServGrupo.getCompania());
				desgServGrupoExamen.setEstado(desgServGrupo.getEstado());
				desgServGrupoExamen.setFechaModificacion(desgServGrupo
						.getFechaModificacion());
				desgServGrupoExamen.setFechaRegistro(desgServGrupo
						.getFechaRegistro());
				desgServGrupoExamen.setSucursal(desgServGrupo.getSucursal());
				desgServGrupoExamen.setUsuarioRegistro(desgServGrupo
						.getUsuarioRegistro());
				servGrupoServicioDao.create(desgServGrupoExamen);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DesgServGrupo "
					+ desgServGrupo.toString());
			return desgServGrupo;
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
	public DesgServGrupo modificar(DesgServGrupo desgServGrupo) {
		try {
			beginTransaction();
			desgServGrupo = update(desgServGrupo);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "DesgServGrupo "
					+ desgServGrupo.toString());
			return desgServGrupo;
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
	public DesgServGrupo modificar(DesgServGrupo desgServGrupo,
			List<DesgServGrupoServicio> examenesSacar,
			List<DesgServGrupoServicio> examenesMeter) {
		try {
			beginTransaction();
			desgServGrupo = update(desgServGrupo);
			for (DesgServGrupoServicio desgServGrupoExamen : examenesSacar) {
				servGrupoServicioDao.delete(desgServGrupoExamen);
			}
			for (DesgServGrupoServicio desgServGrupoExamen : examenesMeter) {
				desgServGrupoExamen.setId(null);
				desgServGrupoExamen.setGrupo(desgServGrupo);
				desgServGrupoExamen.setCompania(desgServGrupo.getCompania());
				desgServGrupoExamen.setEstado(desgServGrupo.getEstado());
				desgServGrupoExamen.setFechaModificacion(desgServGrupo
						.getFechaModificacion());
				desgServGrupoExamen.setFechaRegistro(desgServGrupo
						.getFechaRegistro());
				desgServGrupoExamen.setSucursal(desgServGrupo.getSucursal());
				desgServGrupoExamen.setUsuarioRegistro(desgServGrupo
						.getUsuarioRegistro());
				servGrupoServicioDao.create(desgServGrupoExamen);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "DesgServGrupo "
					+ desgServGrupo.toString());
			return desgServGrupo;
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
	public boolean eliminar(DesgServGrupo desgServGrupo) {
		try {
			beginTransaction();
			DesgServGrupo bar = update(desgServGrupo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DesgServGrupo "
					+ desgServGrupo.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public boolean eliminar(DesgServGrupo desgServGrupo,
			List<DesgServGrupoServicio> examenes) {
		try {
			beginTransaction();
			desgServGrupo.setEstado("RM");
			desgServGrupo = update(desgServGrupo);
			for (DesgServGrupoServicio desgServGrupoExamen : examenes) {
				desgServGrupoExamen.setEstado(desgServGrupo.getEstado());
				servGrupoServicioDao.update(desgServGrupoExamen);
			}
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DesgServGrupo "
					+ desgServGrupo.toString());
			return desgServGrupo != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public DesgServGrupo obtenerDesgServGrupo(Integer id) {
		return findById(id);
	}

	@Override
	public DesgServGrupo obtenerDesgServGrupo(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public List<DesgServGrupo> obtenerDesgServGrupoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<DesgServGrupo> obtenerDesgServGrupoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<DesgServGrupo> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<DesgServGrupo> obtenerPorCompania(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IDesgServGrupoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DesgServGrupo> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IDesgServGrupoDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<DesgServGrupo> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IDesgServGrupoDao#obtenerAllActivos()
	 */
	@Override
	public List<DesgServGrupo> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<DesgServGrupo> obtenerAllGrupoExamen(Compania compania) {
		// TODO Auto-generated method stub
		String query = "select em.grupoExamen from DesgServGrupo em  where em.estado='AC' and em.compania.id="
				+ compania.getId()
				+ "  and em.visualizar=true  order by em.id  asc";
		System.out.println(query);
		return (List<DesgServGrupo>) executeQueryResulList(query);
	}

	@Override
	public boolean verificarRepetido(DesgServGrupo desgServGrupo,
			Compania compania) {
		List<DesgServGrupo> desgServGrupos = obtenerPorCompania(compania);
		for (DesgServGrupo desgServGrupo2 : desgServGrupos) {
			if (desgServGrupo2.getDescripcion().equalsIgnoreCase(
					desgServGrupo.getDescripcion())
					|| desgServGrupo2.equals(desgServGrupo))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(DesgServGrupo desgServGrupo,
			Compania compania) {
		List<DesgServGrupo> desgServGrupos = obtenerPorCompania(compania);
		for (DesgServGrupo desgServGrupo2 : desgServGrupos) {
			if (desgServGrupo2.getDescripcion().equalsIgnoreCase(
					desgServGrupo.getDescripcion())
					&& !desgServGrupo2.equals(desgServGrupo))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgServGrupoDao#delete(com.teds.spaps.
	 * model.DesgServGrupo)
	 */
	@Override
	public void delete(DesgServGrupo grupo) {
		super.deleteReal(grupo);
	}

	@Override
	public List<EDDesgOrdenServicio> obtenerEDGrupos(Sucursal sucursal) {
		List<EDDesgOrdenServicio> resultado = new ArrayList<EDDesgOrdenServicio>();
		List<DesgServGrupo> grupos = obtenerPorSucursal(sucursal);
		for (DesgServGrupo desgServGrupo : grupos) {
			List<DesgServGrupoServicio> examenes = servGrupoServicioDao
					.obtenerPorGrupo(desgServGrupo);
			System.out.println("lista de servicios por grupo "
					+ desgServGrupo.getDescripcion() + " = " + examenes.size());
			EDDesgOrdenServicio ed = new EDDesgOrdenServicio();
			ed.setGrupoServicio(desgServGrupo);
			ed.setServicios(examenes);
			resultado.add(ed);
		}
		return resultado;
	}
}
