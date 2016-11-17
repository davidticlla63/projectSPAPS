package com.teds.spaps.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.ITipoCambioDao;
import com.teds.spaps.interfaces.dao.ITipoCambioUfvDao;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.TipoCambio;
import com.teds.spaps.model.TipoCambioUfv;
import com.teds.spaps.util.DateUtility;
import com.teds.spaps.util.EDTipoCambio;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class TipoCambioDao extends DataAccessObject<TipoCambio> implements ITipoCambioDao {

	private @Inject ITipoCambioUfvDao tipoCambioUfvDao;
	
	public TipoCambioDao(){
		super(TipoCambio.class);
	}

	public boolean registrar(List<EDTipoCambio> listTipoCambio,String usuarioRegistro,Empresa empresaLogin){
		try{
			Date fechaActual = new Date();
			for(EDTipoCambio edtc: listTipoCambio){
				TipoCambio tipoCambio = new TipoCambio();
				tipoCambio.setEmpresa(empresaLogin);
				tipoCambio.setEmpresa(empresaLogin);
				tipoCambio.setEstado("AC");
				tipoCambio.setFecha(edtc.getFecha());
				tipoCambio.setFechaRegistro(fechaActual);
				tipoCambio.setUnidad(edtc.getTipoCambio().getUnidad());
				tipoCambio.setUsuarioRegistro(usuarioRegistro);
				TipoCambioUfv tipoCambioUfv = new TipoCambioUfv();
				tipoCambioUfv.setEmpresa(empresaLogin);
				tipoCambioUfv.setEstado("AC");
				tipoCambioUfv.setFecha(edtc.getFecha());
				tipoCambioUfv.setFechaRegistro(fechaActual);
				tipoCambioUfv.setUnidad(edtc.getTipoCambioUfv().getUnidad());
				tipoCambioUfv.setUsuarioRegistro(usuarioRegistro);
				create(tipoCambio);
				tipoCambioUfvDao.create(tipoCambioUfv);
			}
			FacesUtil.infoMessage("Registro Correcto TipoCambio", " ");
			return true;
		}catch(Exception e){
			String cause=e.getMessage();
			if (cause.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			}else{
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return false;
		}
	}

	public TipoCambio modificar(TipoCambio usuario){
		try{
			update(usuario);
			FacesUtil.infoMessage("Modificación Correcta", "TipoCambio "+usuario.getId());
			return usuario;
		}catch(Exception e){
			String cause=e.getMessage();
			if (cause.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			}else{
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	public boolean eliminar(TipoCambio usuario){
		try{
			update(usuario);
			FacesUtil.infoMessage("Eliminación Correcta", "TipoCambio "+usuario.getId());
			return true;
		}catch(Exception e){
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public List<TipoCambio> obtenerOrdenAscPorId(){
		return findAscAllOrderedByParameter("id");
	}

	public List<TipoCambio> obtenerOrdenDescPorId(){
		return findDescAllOrderedByParameter("id");
	}

	public  List<TipoCambio> obtenerPorEmpresa(Empresa empresa){
		return findAllActivosByParameter("empresa", empresa);
	}

	public TipoCambio obtenerPorEmpresaYFecha(Empresa empresa,Date fecha){
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha); 
			String year = new SimpleDateFormat("yyyy").format(fecha);
			Integer month = Integer.parseInt(new SimpleDateFormat("MM").format(fecha).toString());
			String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			String query = "select em from TipoCambio em where em.empresa.id="+empresa.getId() + " and date_part('year',em.fecha)="+year+" and date_part('month',em.fecha)="+month+" and date_part('day',em.fecha)="+day;
			System.out.println("query: "+query);
			return executeQuerySingleResult(query);
		}catch(Exception e){
			return null;
		}
		//return findAllByParameterDateAndTwoParameter("fecha", fecha, "empresa", empresa);
	}

	public TipoCambio obtenerPorEmpresaDiaAnterior(Empresa empresa){
		try{
			Date date1 = new Date();
			Date fecha = DateUtility.restarDiasFecha(date1,1);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha); 
			String year = new SimpleDateFormat("yyyy").format(fecha);
			Integer month = Integer.parseInt(new SimpleDateFormat("MM").format(fecha).toString());
			String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			String query = "select em from TipoCambio em where em.empresa.id="+empresa.getId() + " and date_part('year',fecha_registro)="+year+" and date_part('month',fecha_registro)="+month+" and date_part('day',fecha_registro)="+day;
			return executeQuerySingleResult(query);
			//return findAllByParameterDateAndTwoParameter("fecha", fecha, "empresa", empresa);
		}catch(Exception e){
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.teds.spaps.interfaces.dao.ITipoCambioDao#obtenerUltimoRegistro(com.teds.spaps.model.Empresa, com.teds.spaps.model.Gestion)
	 */
	@Override
	public TipoCambio obtenerUltimoRegistro(Empresa empresa) {
		try{
			return findLastActiveRecord("empresa.id",empresa.getId());
		}catch(Exception e){
			return null;
		}
	}
}
