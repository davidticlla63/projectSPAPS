package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.ILabExamenDao;
import com.teds.spaps.interfaces.dao.ILabExamenDetalleDao;
import com.teds.spaps.interfaces.dao.ILabGrupoExamenDao;
import com.teds.spaps.interfaces.dao.ILabValoresRefDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabExamenDetalle;
import com.teds.spaps.model.LabGrupoExamen;
import com.teds.spaps.model.LabValoresRef;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDOrdenLaboratorio;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabExamenDao extends DataAccessObjectJpa<LabExamen> implements
		ILabExamenDao {
	private @Inject ILabExamenDetalleDao examenDetalleDao;
	private @Inject ILabGrupoExamenDao grupoExamenDao;
	private @Inject ILabValoresRefDao valoresRefDao;

	public LabExamenDao() {
		super(LabExamen.class);
	}

	@Override
	public LabExamen create(LabExamen especialidad) {
		return super.create(especialidad);
	}

	@Override
	public LabExamen update(LabExamen especialidad) {
		return super.update(especialidad);
	}

	@Override
	public LabExamen registrar(LabExamen examen,
			List<LabExamenDetalle> listExamenDetalles) {
		try {
			beginTransaction();
			examen = create(examen);

			for (LabExamenDetalle labExamenDetalle : listExamenDetalles) {
				LabExamenDetalle detalle = new LabExamenDetalle();
				detalle.setCompania(labExamenDetalle.getCompania());
				detalle.setCorrelativo(labExamenDetalle.getCorrelativo());
				detalle.setDescripcion(detalle.getDescripcion());
				detalle.setEstado(labExamenDetalle.getEstado());
				detalle.setLabel(labExamenDetalle.isLabel());
				detalle.setSucursal(labExamenDetalle.getSucursal());
				detalle.setParametros(labExamenDetalle.getParametros());
				detalle.setValores(labExamenDetalle.isValores());
				detalle.setUsuarioRegistro(labExamenDetalle
						.getUsuarioRegistro());
				detalle.setLabExamen(examen);
				detalle.setTipoValor(labExamenDetalle.getTipoValor());
				detalle.setUnidaMedida(labExamenDetalle.getUnidaMedida());
				detalle.setFechaRegistro(new Date());
				detalle.setTipoParametro(labExamenDetalle.getTipoParametro());
				detalle = examenDetalleDao.create(detalle);
				for (LabValoresRef labValoresRef : labExamenDetalle
						.getListValoresReferencia()) {
					labValoresRef.setExamenDetalle(detalle);
					labValoresRef.setLabExamen(examen);
					labValoresRef.setId(null);
					labValoresRef = valoresRefDao.create(labValoresRef);
				}
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"LabExamen " + examen.toString());
			return examen;
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
	public LabExamen modificar(LabExamen examen,
			List<LabExamenDetalle> listExamenDetalles) {
		try {
			examen.setListExamenDetalle(new ArrayList<LabExamenDetalle>());
			beginTransaction();
			examen = update(examen);
			valoresRefDao.deleteDetail(examen);
			examenDetalleDao.deleteDetail(examen);
			for (LabExamenDetalle labExamenDetalle : listExamenDetalles) {
				// examenDetalleDao.delete(labExamenDetalle);
				LabExamenDetalle detalle = new LabExamenDetalle();
				detalle.setCompania(labExamenDetalle.getCompania());
				detalle.setCorrelativo(labExamenDetalle.getCorrelativo());
				detalle.setDescripcion(labExamenDetalle.getDescripcion());
				detalle.setEstado(labExamenDetalle.getEstado());
				detalle.setLabel(labExamenDetalle.isLabel());
				detalle.setSucursal(labExamenDetalle.getSucursal());
				detalle.setParametros(labExamenDetalle.getParametros());
				detalle.setValores(labExamenDetalle.isValores());
				detalle.setUsuarioRegistro(labExamenDetalle
						.getUsuarioRegistro());
				detalle.setLabExamen(examen);
				detalle.setTipoValor(labExamenDetalle.getTipoValor());
				detalle.setUnidaMedida(labExamenDetalle.getUnidaMedida());
				detalle.setFechaRegistro(new Date());
				detalle.setTipoParametro(labExamenDetalle.getTipoParametro());
				detalle = examenDetalleDao.create(detalle);
//				System.out.println("id : " + labExamenDetalle.getId() + " "
//						+ labExamenDetalle.getParametros());

				for (LabValoresRef labValoresRef : labExamenDetalle
						.getListValoresReferencia()) {
					labValoresRef.setExamenDetalle(detalle);
					labValoresRef.setLabExamen(examen);
					labValoresRef.setId(null);
					labValoresRef = valoresRefDao.create(labValoresRef);
				}
			}

			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "LabExamen "
					+ examen.toString());
			return examen;
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
	public boolean eliminar(LabExamen especialidad) {
		try {
			beginTransaction();
			LabExamen bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "LabExamen "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabExamen obtenerLabExamen(Integer id) {
		return findById(id);
	}

	@Override
	public LabExamen obtenerLabExamen(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<LabExamen> obtenerLabExamenOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabExamen> obtenerLabExamenOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabExamen> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabExamen> obtenerPorCompaniaOrdenadoFechaRegistro(
			Compania compania) {
		return findAllActiveParameter("compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<LabExamen> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania) {
		return findAllActiveParameter(50, "compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<LabExamen> obtenerPorGrupoExamen(LabGrupoExamen grupoExamen) {
		return findAllActiveParameter("grupoExamen", grupoExamen.getId());
	}

	@Override
	public List<LabExamen> obtenerPorCompania(String nombre, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabExamenDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabExamen> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabExamenDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<LabExamen> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabExamenDao#obtenerAllActivos()
	 */
	@Override
	public List<LabExamen> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<EDOrdenLaboratorio> obtenerAllStructureOrden(Compania compania) {
		// TODO Auto-generated method stub
		List<EDOrdenLaboratorio> listOrdenLaboratorios = new ArrayList<EDOrdenLaboratorio>();
		List<LabGrupoExamen> listGrupoExamens = grupoExamenDao
				.obtenerAllGrupoExamen(compania);
		int correlativo = 1;
		LabGrupoExamen grupoExamen = new LabGrupoExamen();
		for (LabGrupoExamen labGrupoExamen : listGrupoExamens) {
			if (grupoExamen.getId().intValue() == 0
					|| grupoExamen.getId().intValue() != labGrupoExamen.getId()
							.intValue()) {
				String query = "select em from LabExamen em  where em.estado='AC' and em.compania.id="
						+ compania.getId()
						+ "  and em.visualizar=true and em.grupoExamen.id="
						+ labGrupoExamen.getId() + " order by em.id  asc";
				System.out.println(query);
				List<LabExamen> listExamens = executeQueryResulList(query);
				listOrdenLaboratorios.add(new EDOrdenLaboratorio(correlativo,
						labGrupoExamen, listExamens));
				correlativo++;
			}
			grupoExamen = labGrupoExamen;
		}
		return listOrdenLaboratorios;
	}

	@Override
	public List<LabExamen> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<LabExamen> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "sucursal",
				sucursal.getId());
	}

}
