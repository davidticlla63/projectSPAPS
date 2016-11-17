package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.ILabExamenDetalleImagDao;
import com.teds.spaps.interfaces.dao.ILabExamenImagDao;
import com.teds.spaps.interfaces.dao.ILabGrupoExamenImagDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamenDetalleImag;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.LabGrupoExamenImag;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDOrdenImagenologia;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabExamenImagDao extends DataAccessObjectJpa<LabExamenImag>
		implements ILabExamenImagDao {
	private @Inject ILabExamenDetalleImagDao examenDetalleDao;
	private @Inject ILabGrupoExamenImagDao grupoExamenDao;

	public LabExamenImagDao() {
		super(LabExamenImag.class);
	}

	@Override
	public LabExamenImag create(LabExamenImag especialidad) {
		return super.create(especialidad);
	}

	@Override
	public LabExamenImag update(LabExamenImag especialidad) {
		return super.update(especialidad);
	}

	@Override
	public LabExamenImag registrar(LabExamenImag examen,
			List<LabExamenDetalleImag> listExamenDetalles) {
		try {
			beginTransaction();
			examen = create(examen);

			for (LabExamenDetalleImag labExamenDetalle : listExamenDetalles) {
				LabExamenDetalleImag detalle = new LabExamenDetalleImag();
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
				detalle.setUnidaMedida(labExamenDetalle.getUnidaMedida());
				detalle.setFechaRegistro(new Date());
				detalle = examenDetalleDao.create(detalle);

			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "LabExamenImag "
					+ examen.toString());
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
	public LabExamenImag modificar(LabExamenImag examen,
			List<LabExamenDetalleImag> listExamenDetalles) {
		try {
			examen.setListExamenDetalle(new ArrayList<LabExamenDetalleImag>());
			beginTransaction();
			examenDetalleDao.deleteDetail(examen);
			for (LabExamenDetalleImag labExamenDetalle : listExamenDetalles) {
				// examenDetalleDao.delete(labExamenDetalle);
				LabExamenDetalleImag detalle = new LabExamenDetalleImag();
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
				detalle.setUnidaMedida(labExamenDetalle.getUnidaMedida());
				detalle.setFechaRegistro(new Date());
				detalle = examenDetalleDao.create(detalle);
				System.out.println("id : " + labExamenDetalle.getId() + " "
						+ labExamenDetalle.getParametros());

			}

			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "LabExamenImag "
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
	public boolean eliminar(LabExamenImag especialidad) {
		try {
			beginTransaction();
			LabExamenImag bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "LabExamenImag "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabExamenImag obtenerLabExamenImag(Integer id) {
		return findById(id);
	}

	@Override
	public LabExamenImag obtenerLabExamenImag(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<LabExamenImag> obtenerLabExamenImagOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabExamenImag> obtenerLabExamenImagOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabExamenImag> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabExamenImag> obtenerPorCompaniaOrdenadoFechaRegistro(
			Compania compania) {
		return findAllActiveParameter("compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<LabExamenImag> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania) {
		return findAllActiveParameter(50, "compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<LabExamenImag> obtenerPorGrupoExamen(
			LabGrupoExamenImag grupoExamen) {
		return findAllActiveParameter("grupoExamen", grupoExamen.getId());
	}

	@Override
	public List<LabExamenImag> obtenerExamenesPorGrupoExamen(
			LabGrupoExamenImag grupoExamen) {
		return findAllActiveOneParameter("estado", "AC", "grupoExamen",
				grupoExamen.getId());
	}

	@Override
	public List<LabExamenImag> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabExamenImagDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabExamenImag> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ILabExamenImagDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<LabExamenImag> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabExamenImagDao#obtenerAllActivos()
	 */
	@Override
	public List<LabExamenImag> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<EDOrdenImagenologia> obtenerAllStructureOrden(Compania compania) {
		// TODO Auto-generated method stub
		List<EDOrdenImagenologia> listOrdenLaboratorios = new ArrayList<EDOrdenImagenologia>();
		List<LabGrupoExamenImag> listGrupoExamens = grupoExamenDao
				.obtenerAllGrupoExamen(compania);
		int correlativo = 1;
		LabGrupoExamenImag grupoExamen = new LabGrupoExamenImag();
		for (LabGrupoExamenImag labGrupoExamen : listGrupoExamens) {
			if (grupoExamen.getId().intValue() == 0
					|| grupoExamen.getId().intValue() != labGrupoExamen.getId()
							.intValue()) {
				String query = "select em from LabExamenImag em  where em.estado='AC' and em.compania.id="
						+ compania.getId()
						+ "  and em.visualizar=true and em.grupoExamen.id="
						+ labGrupoExamen.getId() + " order by em.id  asc";
				System.out.println(query);
				List<LabExamenImag> listExamens = executeQueryResulList(query);
				listOrdenLaboratorios.add(new EDOrdenImagenologia(correlativo,
						labGrupoExamen, listExamens));
				correlativo++;
			}
			grupoExamen = labGrupoExamen;
		}
		return listOrdenLaboratorios;
	}

	@Override
	public List<LabExamenImag> obtenerPorCompaniaAutoComplete(
			String descripcion, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<LabExamenImag> obtenerPorSucursalAutoComplete(
			String descripcion, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "sucursal",
				sucursal.getId());
	}

}
