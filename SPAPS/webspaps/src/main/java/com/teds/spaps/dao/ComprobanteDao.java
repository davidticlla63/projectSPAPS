package com.teds.spaps.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IComprobanteDao;
import com.teds.spaps.interfaces.dao.ILineaContableDao;
import com.teds.spaps.model.Comprobante;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Gestion;
import com.teds.spaps.model.LineaContable;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.model.TipoComprobante;
import com.teds.spaps.util.EDAsiento;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.Time;


/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class ComprobanteDao extends DataAccessObjectJpa<Comprobante> implements IComprobanteDao {

	private @Inject ILineaContableDao lineaContableDao;

	public ComprobanteDao(){
		super(Comprobante.class);
	}

	public Comprobante registrar(Comprobante comprobante,List<EDAsiento> list){
		try{
			beginTransaction();
			comprobante = create(comprobante);
			for(EDAsiento eda: list){
				LineaContable ac = new LineaContable();
				ac.setCentroCosto(null);//eda.getCentroCosto());
				ac.setDebeExtranjero( eda.getDebeExtranjero());//totalDebeExtranjero);
				ac.setDebeNacional(eda.getDebeNacional() );//totalDebeNacional);
				ac.setGlosa(eda.getGlosa());
				ac.setHaberExtranjero( eda.getHaberExtranjero());//totalHaberExtranjero);
				ac.setHaberNacional( eda.getHaberNacional());//totalHaberNacional);
				ac.setPlanCuenta(eda.getCuenta());
				ac.setComprobante(comprobante);
				ac.setUsuarioRegistro(comprobante.getUsuarioRegistro());
				ac.setEstado("AC");
				ac.setFecha(comprobante.getFechaRegistro());
				ac.setFechaRegistro(comprobante.getFechaRegistro());
				lineaContableDao.create(ac);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Comprobante Nª "+comprobante.getCorrelativo());
			return comprobante;
		}catch(Exception e){
			Throwable t = e.getCause();
			t.printStackTrace();
			String cause=t.getMessage();
			
			if (cause.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			}else{
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return null;
		}
	}

	public boolean modificar(Comprobante comprobante){
		try{
			update(comprobante);
			FacesUtil.infoMessage("Modificación Correcta", "Comprobante Nª "+comprobante.getCorrelativo());
			return true;
		}catch(Exception e){
			String cause=e.getMessage();
			if (cause.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			}else{
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return false;
		}
	}

	public boolean eliminar(Comprobante comprobante){
		try{
			comprobante.setEstado("RM");
			update(comprobante);
			FacesUtil.infoMessage("Eliminación Correcta", "Comprobante Nª "+comprobante.getCorrelativo());
			return true;
		}catch(Exception e){
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public boolean anular(Comprobante comprobante){
		try{
			comprobante.setEstado("AN");
			update(comprobante);
			FacesUtil.infoMessage("Anulación Correcta", "Comprobante Nª "+comprobante.getCorrelativo());
			return true;
		}catch(Exception e){
			FacesUtil.errorMessage("Error al Anular");
			rollbackTransaction();
			return false;
		}
	}

	public List<Comprobante> obtenerComprobanteOrdenAscPorId(){
		return findAscAllOrderedByParameter("id");
	}

	public List<Comprobante> obtenerComprobanteOrdenDescPorId(){
		return findDescAllOrderedByParameter("id");
	}

	public List<Comprobante> obtenerPorEmpresaGesstion(Empresa empresa,Gestion gestion){
		return findAllByParameterObjectTwo("empresa", "gestion", empresa, gestion);
	}

	public int obtenerNumeroComprobante(Date date,Empresa empresa,TipoComprobante tc){
		Integer year = Integer.parseInt( new SimpleDateFormat("yyyy").format(date));
		Integer mes = Integer.parseInt(new SimpleDateFormat("MM").format(date));
		String query = "select em from Comprobante em where (em.estado='AC' or em.estado='AN') and em.empresa.id="+empresa.getId()+" and em.tipoComprobante.id="+tc.getId()+" and date_part('month', em.fecha) ="+mes+" and date_part('year', em.fecha) ="+year;
		return (( List<Comprobante>)executeQueryResulList(query)).size() + 1;
	}

	@Override
	public Comprobante obtenerPorId(Integer id) {
		return findById(id);
	}

	@Override
	public List<Comprobante> obtenerEntreFechasPorCuenta(Date fechaIni,
			Date fechaFin,PlanCuenta planCuenta, Empresa empresa) {
		String query = "select em from Comprobante em,LineaContable lc where em.id=lc.comprobante.id and lc.planCuenta.id="+planCuenta.getId()
				+" and em.empresa.id="+empresa.getId()+" and to_number(to_char(em.fechaRegistro ,'YYYYMMDD'), '999999999')>=" 
				+ Time.obtenerFormatoYYYYMMDD(fechaIni)+" and to_number(to_char(em.fechaRegistro ,'YYYYMMDD'), '999999999')<="
				+ Time.obtenerFormatoYYYYMMDD(fechaFin);
		return executeQueryResulList(query);
	}

	@Override
	public List<Comprobante> obtenerMenoresAFechaPorCuenta(Date fechaIni,
			PlanCuenta planCuenta, Empresa empresa) {
		String query = "select em from Comprobante em,LineaContable lc where em.id=lc.comprobante.id and lc.planCuenta.id="+planCuenta.getId()
				+" and em.empresa.id="+empresa.getId()+" and to_number(to_char(em.fechaRegistro ,'YYYYMMDD'), '999999999')<"
				+ Time.obtenerFormatoYYYYMMDD(fechaIni);
				return executeQueryResulList(query);
	}

}
