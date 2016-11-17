package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IConfiguracionFtpDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.ConfiguracionFtp;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class ConfiguracionFtpDao extends DataAccessObjectJpa<ConfiguracionFtp>
		implements IConfiguracionFtpDao {

	public ConfiguracionFtpDao() {
		super(ConfiguracionFtp.class);
	}

	@Override
	public ConfiguracionFtp create(ConfiguracionFtp especialidad) {
		return super.create(especialidad);
	}

	@Override
	public ConfiguracionFtp update(ConfiguracionFtp especialidad) {
		return super.update(especialidad);
	}

	@Override
	public ConfiguracionFtp registrar(ConfiguracionFtp especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "ConfiguracionFtp "
					+ especialidad.toString());
			return especialidad;
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
	public ConfiguracionFtp modificar(ConfiguracionFtp especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "ConfiguracionFtp "
					+ especialidad.toString());
			return especialidad;
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
	public boolean eliminar(ConfiguracionFtp especialidad) {
		try {
			beginTransaction();
			ConfiguracionFtp bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "ConfiguracionFtp "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public ConfiguracionFtp obtenerConfiguracionFtp(Integer id) {
		return findById(id);
	}

	@Override
	public ConfiguracionFtp obtenerConfiguracionFtp(String nombre) {
		return findByParameter("nombre", nombre);
	}
	
	@Override
	public ConfiguracionFtp obtenerConfiguracionFtpActivo(Compania compania,String nombre) {
		return findByParameterObjectTwo("estado", "compania",nombre,compania.getId());
	}

	@Override
	public List<ConfiguracionFtp> obtenerConfiguracionFtpOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<ConfiguracionFtp> obtenerConfiguracionFtpOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<ConfiguracionFtp> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<ConfiguracionFtp> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IConfiguracionFtpDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<ConfiguracionFtp> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IConfiguracionFtpDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<ConfiguracionFtp> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IConfiguracionFtpDao#obtenerAllActivos()
	 */
	@Override
	public List<ConfiguracionFtp> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<ConfiguracionFtp> obtenerAllGrupoExamen(Compania compania) {
		// TODO Auto-generated method stub
		String query = "select em.grupoExamen from LabExamen em  where em.estado='AC' and em.compania.id="
				+ compania.getId()
				+ "  and em.visualizar=true and em.tipoExamen.descripcion like '%LABORATORIO%' order by em.id  asc";
		System.out.println(query);
		return (List<ConfiguracionFtp>) executeQueryResulList(query);
	}
}
