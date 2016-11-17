package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.teds.spaps.interfaces.dao.ISumasSaldosDao;
import com.teds.spaps.model.Comprobante;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.util.DateUtility;
import com.teds.spaps.util.EDSumasSaldos;
import com.teds.spaps.util.Time;


/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class SumasSaldosDao extends DataAccessObject<Comprobante> implements ISumasSaldosDao {


	public SumasSaldosDao(){
		super(Comprobante.class);
	}

	@Override
	public List<EDSumasSaldos> obtenerTodosHastaFecha(Date fecha,Empresa empresa) {
		List<EDSumasSaldos> list = new ArrayList<EDSumasSaldos>();
		String sql = "SELECT  pc.id AS idPlanCuenta,pc.codigo_auxiliar AS codigoPlanCuenta, "
				+" pc.descripcion AS nombrePlanCuenta, "
				+" pc.clase AS clasePlanCuenta, "
				+" pc.id_tipo_cuenta AS idTipoCuenta, "
				+" tc.nombre AS tipoCuenta, "
				+" ( SELECT CASE WHEN SUM(lc.debe_nacional) IS NULL THEN CAST(0.0 AS double precision) "
				+"   WHEN SUM(lc.debe_nacional) IS NOT NULL THEN SUM(lc.debe_nacional) END "
				+"   FROM linea_contable lc "
				+"   WHERE lc.id_plan_cuenta = pc.id) AS debeSumaNacional, "
				+" (SELECT CASE WHEN SUM(lc.haber_nacional) IS NULL THEN CAST(0.0 AS double precision) "
				+"   WHEN SUM(lc.haber_nacional) IS NOT NULL THEN SUM(lc.haber_nacional) END "
				+"   FROM linea_contable lc "
				+"   WHERE lc.id_plan_cuenta = pc.id) AS haberSumaNacional, "
				+"   CAST(0.0 AS double precision) AS deudorSaldoNacional, "
				+"   CAST(0.0 AS double precision) AS acreedorSaldoNacional, "
				+" (SELECT CASE WHEN SUM(lc.debe_extranjero) IS NULL THEN CAST(0.0 AS double precision) "
				+"   WHEN SUM(lc.debe_extranjero) IS NOT NULL THEN SUM(lc.debe_extranjero) END "
				+"   FROM linea_contable lc "
				+"   WHERE lc.id_plan_cuenta = pc.id) AS debeSumaExtranjero, "
				+" (SELECT CASE WHEN SUM(lc.haber_extranjero) IS NULL THEN CAST(0.0 AS double precision) "
				+"   WHEN SUM(lc.haber_extranjero) IS NOT NULL THEN SUM(lc.haber_extranjero) END "
				+"  FROM linea_contable lc "
				+"   WHERE lc.id_plan_cuenta = pc.id) AS haberSumaExtranjero, "
				+"  CAST(0.0 AS double precision) AS deudorSaldoExtranjero, "
				+"   CAST(0.0 AS double precision) AS acreedorSaldoExtranjero "
				+" FROM "
				+" plan_cuenta pc "
				+" INNER JOIN tipo_cuenta tc ON pc.id_tipo_cuenta = tc.id"
				+" WHERE  "
				+" pc.estado ='AC' AND "
				+" pc.id_empresa = "+empresa.getId()
				+" ORDER BY "
				+" pc.codigo_auxiliar ASC; ";
		// Time.obtenerFormatoYYYYMMDD(fecha);
		Query query = getEntityManager().createNativeQuery(sql);
		List<Object[]> rs2 = query.getResultList();
		for (Object[] o : rs2) {
			Integer idPlanCuenta =  (Integer) o[0];
			String codigoPlanCuenta = (String) o[1];
			String nombrePlanCuenta = (String) o[2];
			String clasePlanCuenta = (String) o[3];
			Integer idTipoCuenta = (Integer) o[4];
			String tipoCuenta = (String)  o[5];
			double debeSumaNacional = (double) o[6];
			double haberSumaNacional = (double) o[7];
			double debeSumaExtranjero = (double) o[8];
			double haberSumaExtranjero = (double) o[9];
			EDSumasSaldos ed = new EDSumasSaldos();
			ed.setClasePlanCuenta(clasePlanCuenta);
			ed.setCodigoPlanCuenta(codigoPlanCuenta);
			ed.setDebeSumaExtranjero(debeSumaExtranjero);
			ed.setDebeSumaNacional(debeSumaNacional);
			ed.setHaberSumaExtranjero(haberSumaExtranjero);
			ed.setHaberSumaNacional(haberSumaNacional);
			ed.setIdPlanCuenta(idPlanCuenta);
			ed.setIdTipoCuenta(idTipoCuenta);
			ed.setNombrePlanCuenta(nombrePlanCuenta);
			ed.setTipoCuenta(tipoCuenta);
			if((debeSumaNacional-haberSumaNacional)>(haberSumaNacional-debeSumaNacional)){
				//la resta entre debe - haber = negativo - > entonces va al deudor la resta
				ed.setDeudorSaldoNacional(debeSumaNacional-haberSumaNacional);
				ed.setDeudorSaldoExtranjero(debeSumaExtranjero - haberSumaExtranjero);
				ed.setAcreedorSaldoExtranjero(0);
				ed.setAcreedorSaldoNacional(0);
			}else{
				ed.setDeudorSaldoExtranjero(0);
				ed.setDeudorSaldoNacional(0);
				ed.setAcreedorSaldoExtranjero(Math.abs(debeSumaExtranjero - haberSumaExtranjero));
				ed.setAcreedorSaldoNacional(Math.abs(debeSumaNacional-haberSumaNacional));
			}
			list.add(ed);
		}
		return list;
	}

	@Override
	public List<EDSumasSaldos> obtenerEstadoResultadoEntreFechas(Date fechaIni,Date fechaFin,Empresa empresa) {
		List<EDSumasSaldos> list = new ArrayList<EDSumasSaldos>();
		String sql = "SELECT  pc.id AS idPlanCuenta,pc.codigo_auxiliar AS codigoPlanCuenta, "
				+" pc.descripcion AS nombrePlanCuenta, "
				+" pc.clase AS clasePlanCuenta, "
				+" pc.id_tipo_cuenta AS idTipoCuenta, "
				+" tc.nombre AS tipoCuenta, "
				+" ( SELECT CASE WHEN SUM(lc.debe_nacional) IS NULL THEN CAST(0.0 AS double precision) "
				+"   WHEN SUM(lc.debe_nacional) IS NOT NULL THEN SUM(lc.debe_nacional) END "
				+"   FROM linea_contable lc "
				+"   WHERE lc.id_plan_cuenta = pc.id) AS debeSumaNacional, "
				+" (SELECT CASE WHEN SUM(lc.haber_nacional) IS NULL THEN CAST(0.0 AS double precision) "
				+"   WHEN SUM(lc.haber_nacional) IS NOT NULL THEN SUM(lc.haber_nacional) END "
				+"   FROM linea_contable lc "
				+"   WHERE lc.id_plan_cuenta = pc.id) AS haberSumaNacional, "
				+"   CAST(0.0 AS double precision) AS deudorSaldoNacional, "
				+"   CAST(0.0 AS double precision) AS acreedorSaldoNacional, "
				+" (SELECT CASE WHEN SUM(lc.debe_extranjero) IS NULL THEN CAST(0.0 AS double precision) "
				+"   WHEN SUM(lc.debe_extranjero) IS NOT NULL THEN SUM(lc.debe_extranjero) END "
				+"   FROM linea_contable lc "
				+"   WHERE lc.id_plan_cuenta = pc.id) AS debeSumaExtranjero, "
				+" (SELECT CASE WHEN SUM(lc.haber_extranjero) IS NULL THEN CAST(0.0 AS double precision) "
				+"   WHEN SUM(lc.haber_extranjero) IS NOT NULL THEN SUM(lc.haber_extranjero) END "
				+"  FROM linea_contable lc "
				+"   WHERE lc.id_plan_cuenta = pc.id) AS haberSumaExtranjero, "
				+"  CAST(0.0 AS double precision) AS deudorSaldoExtranjero, "
				+"   CAST(0.0 AS double precision) AS acreedorSaldoExtranjero "
				+" FROM "
				+" plan_cuenta pc "
				+" INNER JOIN tipo_cuenta tc ON pc.id_tipo_cuenta = tc.id"
				+" WHERE  "
				+" pc.estado ='AC' AND "
				+" pc.id_empresa = "+empresa.getId()
				+" ORDER BY "
				+" pc.codigo_auxiliar ASC; ";
		// Time.obtenerFormatoYYYYMMDD(fecha);
		Query query = getEntityManager().createNativeQuery(sql);
		List<Object[]> rs2 = query.getResultList();
		double totalDebeSumaNacional=0;
		double totalHaberSumaNacional = 0;
		double totalDebeSumaExtranjero = 0;
		double totalHaberSumaExtranjero=0;
		double totalSaldoDeudorNacional =0;
		double totalSaldoAccredorNacional =0;
		double totalSaldoDeudorExtranjero =0;
		double totalSaldoAccredorExtranjero =0;
		for (Object[] o : rs2) {
			Integer idPlanCuenta =  (Integer) o[0];
			String codigoPlanCuenta = (String) o[1];
			String nombrePlanCuenta = (String) o[2];
			String clasePlanCuenta = (String) o[3];
			Integer idTipoCuenta = (Integer) o[4];
			String tipoCuenta = (String)  o[5];
			double debeSumaNacional = (double) o[6];
			double haberSumaNacional = (double) o[7];
			double debeSumaExtranjero = (double) o[8];
			double haberSumaExtranjero = (double) o[9];
			EDSumasSaldos ed = new EDSumasSaldos();
			ed.setClasePlanCuenta(clasePlanCuenta);
			ed.setCodigoPlanCuenta(codigoPlanCuenta);
			ed.setDebeSumaExtranjero(debeSumaExtranjero);
			ed.setDebeSumaNacional(debeSumaNacional);
			ed.setHaberSumaExtranjero(haberSumaExtranjero);
			ed.setHaberSumaNacional(haberSumaNacional);
			ed.setIdPlanCuenta(idPlanCuenta);
			ed.setIdTipoCuenta(idTipoCuenta);
			ed.setNombrePlanCuenta(nombrePlanCuenta);
			ed.setTipoCuenta(tipoCuenta);
			if((debeSumaNacional-haberSumaNacional)>(haberSumaNacional-debeSumaNacional)){
				//la resta entre debe - haber = negativo - > entonces va al deudor la resta
				ed.setDeudorSaldoNacional(debeSumaNacional-haberSumaNacional);
				ed.setDeudorSaldoExtranjero(debeSumaExtranjero - haberSumaExtranjero);
				ed.setAcreedorSaldoExtranjero(0);
				ed.setAcreedorSaldoNacional(0);
			}else{
				ed.setDeudorSaldoExtranjero(0);
				ed.setDeudorSaldoNacional(0);
				ed.setAcreedorSaldoExtranjero(Math.abs(debeSumaExtranjero - haberSumaExtranjero));
				ed.setAcreedorSaldoNacional(Math.abs(debeSumaNacional-haberSumaNacional));
			}
			if(ed.getTipoCuenta().equals("INGRESO") || ed.getTipoCuenta().equals("EGRESO")){
				totalDebeSumaNacional= totalDebeSumaNacional + ed.getDebeSumaNacional();
				totalHaberSumaNacional = totalHaberSumaNacional + ed.getHaberSumaNacional();
				totalDebeSumaExtranjero = totalDebeSumaExtranjero + ed.getDebeSumaExtranjero();
				totalHaberSumaExtranjero = totalHaberSumaExtranjero + ed.getHaberSumaExtranjero();
				totalSaldoDeudorNacional = totalSaldoDeudorNacional + ed.getDeudorSaldoNacional();
				totalSaldoAccredorNacional = totalSaldoAccredorNacional + ed.getAcreedorSaldoNacional();
				totalSaldoDeudorExtranjero = totalSaldoDeudorExtranjero + ed.getDeudorSaldoExtranjero();
				totalSaldoAccredorExtranjero = totalSaldoAccredorExtranjero + ed.getAcreedorSaldoExtranjero();
				ed.setDeudorSaldoExtranjero(Math.abs(debeSumaExtranjero - haberSumaExtranjero));
				ed.setDeudorSaldoNacional(Math.abs(debeSumaNacional-haberSumaNacional));
				list.add(ed);
			}
		}
		EDSumasSaldos res = new EDSumasSaldos();
		res.setAcreedorSaldoExtranjero(-1);
		res.setAcreedorSaldoNacional(-1);
		res.setClasePlanCuenta("");
		res.setCodigoPlanCuenta("RESULTADO");
		res.setDebeSumaExtranjero(-1);
		res.setDebeSumaNacional(-1);
		res.setDeudorSaldoExtranjero(-1);
		res.setDeudorSaldoNacional(-1);
		res.setHaberSumaExtranjero(-1);
		res.setHaberSumaNacional(-1);
		res.setIdPlanCuenta(-1);
		res.setIdTipoCuenta(-1);
		if(totalSaldoDeudorNacional>totalSaldoAccredorNacional){
			res.setNombrePlanCuenta("UTILIDAD");
			//deudorSaldoNacional
			//deudorSaldoExtranjero
			res.setDeudorSaldoExtranjero(Math.abs(totalDebeSumaExtranjero - totalHaberSumaExtranjero));
			res.setDeudorSaldoNacional(Math.abs(totalDebeSumaNacional-totalHaberSumaNacional));
		}else{
			res.setNombrePlanCuenta("PERDIDA");
			res.setDeudorSaldoExtranjero(Math.abs(totalDebeSumaExtranjero - totalHaberSumaExtranjero));
			res.setDeudorSaldoNacional(Math.abs(totalDebeSumaNacional-totalHaberSumaNacional));
		}
		res.setTipoCuenta("");
		list.add(res);
		return list;
	}
	
	@Override
	public List<EDSumasSaldos> obtenerBalanceHastaFecha(Date fecha,Empresa empresa) {
		List<EDSumasSaldos> list = new ArrayList<EDSumasSaldos>();
		String sql = "SELECT  pc.id AS idPlanCuenta,pc.codigo_auxiliar AS codigoPlanCuenta, "
				+" pc.descripcion AS nombrePlanCuenta, "
				+" pc.clase AS clasePlanCuenta, "
				+" pc.id_tipo_cuenta AS idTipoCuenta, "
				+" tc.nombre AS tipoCuenta, "
				+" ( SELECT CASE WHEN SUM(lc.debe_nacional) IS NULL THEN CAST(0.0 AS double precision) "
				+"   WHEN SUM(lc.debe_nacional) IS NOT NULL THEN SUM(lc.debe_nacional) END "
				+"   FROM linea_contable lc "
				+"   WHERE lc.id_plan_cuenta = pc.id) AS debeSumaNacional, "
				+" (SELECT CASE WHEN SUM(lc.haber_nacional) IS NULL THEN CAST(0.0 AS double precision) "
				+"   WHEN SUM(lc.haber_nacional) IS NOT NULL THEN SUM(lc.haber_nacional) END "
				+"   FROM linea_contable lc "
				+"   WHERE lc.id_plan_cuenta = pc.id) AS haberSumaNacional, "
				+"   CAST(0.0 AS double precision) AS deudorSaldoNacional, "
				+"   CAST(0.0 AS double precision) AS acreedorSaldoNacional, "
				+" (SELECT CASE WHEN SUM(lc.debe_extranjero) IS NULL THEN CAST(0.0 AS double precision) "
				+"   WHEN SUM(lc.debe_extranjero) IS NOT NULL THEN SUM(lc.debe_extranjero) END "
				+"   FROM linea_contable lc "
				+"   WHERE lc.id_plan_cuenta = pc.id) AS debeSumaExtranjero, "
				+" (SELECT CASE WHEN SUM(lc.haber_extranjero) IS NULL THEN CAST(0.0 AS double precision) "
				+"   WHEN SUM(lc.haber_extranjero) IS NOT NULL THEN SUM(lc.haber_extranjero) END "
				+"  FROM linea_contable lc "
				+"   WHERE lc.id_plan_cuenta = pc.id) AS haberSumaExtranjero, "
				+"  CAST(0.0 AS double precision) AS deudorSaldoExtranjero, "
				+"   CAST(0.0 AS double precision) AS acreedorSaldoExtranjero "
				+" FROM "
				+" plan_cuenta pc "
				+" INNER JOIN tipo_cuenta tc ON pc.id_tipo_cuenta = tc.id"
				+" WHERE  "
				+" pc.estado ='AC' AND "
				+" pc.id_empresa = "+empresa.getId()
				+" ORDER BY "
				+" pc.codigo_auxiliar ASC; ";
		// Time.obtenerFormatoYYYYMMDD(fecha);
		Query query = getEntityManager().createNativeQuery(sql);
		List<Object[]> rs2 = query.getResultList();
		double totalDebeSumaNacional=0;
		double totalHaberSumaNacional = 0;
		double totalDebeSumaExtranjero = 0;
		double totalHaberSumaExtranjero=0;
		double totalSaldoDeudorNacional =0;
		double totalSaldoAccredorNacional =0;
		double totalSaldoDeudorExtranjero =0;
		double totalSaldoAccredorExtranjero =0;
		for (Object[] o : rs2) {
			Integer idPlanCuenta =  (Integer) o[0];
			String codigoPlanCuenta = (String) o[1];
			String nombrePlanCuenta = (String) o[2];
			String clasePlanCuenta = (String) o[3];
			Integer idTipoCuenta = (Integer) o[4];
			String tipoCuenta = (String)  o[5];
			double debeSumaNacional = (double) o[6];
			double haberSumaNacional = (double) o[7];
			double debeSumaExtranjero = (double) o[8];
			double haberSumaExtranjero = (double) o[9];
			EDSumasSaldos ed = new EDSumasSaldos();
			ed.setClasePlanCuenta(clasePlanCuenta);
			ed.setCodigoPlanCuenta(codigoPlanCuenta);
			ed.setDebeSumaExtranjero(debeSumaExtranjero);
			ed.setDebeSumaNacional(debeSumaNacional);
			ed.setHaberSumaExtranjero(haberSumaExtranjero);
			ed.setHaberSumaNacional(haberSumaNacional);
			ed.setIdPlanCuenta(idPlanCuenta);
			ed.setIdTipoCuenta(idTipoCuenta);
			ed.setNombrePlanCuenta(nombrePlanCuenta);
			ed.setTipoCuenta(tipoCuenta);
			if((debeSumaNacional-haberSumaNacional)>(haberSumaNacional-debeSumaNacional)){
				//la resta entre debe - haber = negativo - > entonces va al deudor la resta
				ed.setDeudorSaldoNacional(debeSumaNacional-haberSumaNacional);
				ed.setDeudorSaldoExtranjero(debeSumaExtranjero - haberSumaExtranjero);
				ed.setAcreedorSaldoExtranjero(0);
				ed.setAcreedorSaldoNacional(0);
			}else{
				ed.setDeudorSaldoExtranjero(0);
				ed.setDeudorSaldoNacional(0);
				ed.setAcreedorSaldoExtranjero(Math.abs(debeSumaExtranjero - haberSumaExtranjero));
				ed.setAcreedorSaldoNacional(Math.abs(debeSumaNacional-haberSumaNacional));
			}
			if(ed.getTipoCuenta().equals("ACTIVO") || ed.getTipoCuenta().equals("PASIVO") || ed.getTipoCuenta().equals("PATRIMONIO") ){
				totalDebeSumaNacional= totalDebeSumaNacional + ed.getDebeSumaNacional();
				totalHaberSumaNacional = totalHaberSumaNacional + ed.getHaberSumaNacional();
				totalDebeSumaExtranjero = totalDebeSumaExtranjero + ed.getDebeSumaExtranjero();
				totalHaberSumaExtranjero = totalHaberSumaExtranjero + ed.getHaberSumaExtranjero();
				totalSaldoDeudorNacional = totalSaldoDeudorNacional + ed.getDeudorSaldoNacional();
				totalSaldoAccredorNacional = totalSaldoAccredorNacional + ed.getAcreedorSaldoNacional();
				totalSaldoDeudorExtranjero = totalSaldoDeudorExtranjero + ed.getDeudorSaldoExtranjero();
				totalSaldoAccredorExtranjero = totalSaldoAccredorExtranjero + ed.getAcreedorSaldoExtranjero();
				ed.setDeudorSaldoExtranjero(Math.abs(debeSumaExtranjero - haberSumaExtranjero));
				ed.setDeudorSaldoNacional(Math.abs(debeSumaNacional-haberSumaNacional));
				list.add(ed);
			}
		}
		EDSumasSaldos res = new EDSumasSaldos();
		res.setAcreedorSaldoExtranjero(-1);
		res.setAcreedorSaldoNacional(-1);
		res.setClasePlanCuenta("");
		res.setCodigoPlanCuenta("RESULTADO");
		res.setDebeSumaExtranjero(-1);
		res.setDebeSumaNacional(-1);
		res.setDeudorSaldoExtranjero(-1);
		res.setDeudorSaldoNacional(-1);
		res.setHaberSumaExtranjero(-1);
		res.setHaberSumaNacional(-1);
		res.setIdPlanCuenta(-1);
		res.setIdTipoCuenta(-1);
		if(totalSaldoDeudorNacional>totalSaldoAccredorNacional){
			res.setNombrePlanCuenta("UTILIDAD");
			//deudorSaldoNacional
			//deudorSaldoExtranjero
			res.setDeudorSaldoExtranjero(Math.abs(totalDebeSumaExtranjero - totalHaberSumaExtranjero));
			res.setDeudorSaldoNacional(Math.abs(totalDebeSumaNacional-totalHaberSumaNacional));
		}else{
			res.setNombrePlanCuenta("PERDIDA");
			res.setDeudorSaldoExtranjero(Math.abs(totalDebeSumaExtranjero - totalHaberSumaExtranjero));
			res.setDeudorSaldoNacional(Math.abs(totalDebeSumaNacional-totalHaberSumaNacional));
		}
		res.setTipoCuenta("");
		list.add(res);
		return list;
	}

	public double obtenerSaldoPorFechas(Date fechaIni, Date fechaFin, PlanCuenta planCuenta, Empresa empresa) {
		String idEmpresa = String.valueOf(empresa.getId());
		String idPlanCuenta = String.valueOf(planCuenta.getId());
		String fechaAnterior = Time.obtenerFormatoYYYYMMDD(DateUtility.restarDiasFecha(fechaIni,1));//restar un dia
		String fechaInicio = Time.obtenerFormatoYYYYMMDD(fechaIni);
		String fechaInicial = Time.obtenerFormatoYYYYMMDD(fechaFin);
		String query = "SELECT "
				+ "( CASE WHEN SUM(lc.debe_nacional) - SUM(lc.haber_nacional) is null THEN 0 "
				+ "WHEN SUM(lc.debe_nacional) - SUM(lc.haber_nacional) is not null THEN SUM(lc.debe_nacional) - SUM(lc.haber_nacional) END ) + " 
				+ "(  SELECT CASE WHEN SUM(lc2.debe_nacional) - SUM(lc2.haber_nacional) is null THEN 0 "
				+ "WHEN SUM(lc2.debe_nacional) - SUM(lc2.haber_nacional) is not null THEN SUM(lc2.debe_nacional) - SUM(lc2.haber_nacional) END "
				+ "FROM "
				+ "linea_contable lc2 "
				+ "INNER JOIN plan_cuenta pc2 ON lc2.id_plan_cuenta=pc2.id "
				+ "INNER JOIN comprobante co2 ON lc2.id_comprobante=co2.id "
				+ "INNER JOIN empresa em2 ON co2.id_empresa=em2.id "
				+ "WHERE to_number(to_char(lc2.fecha ,'YYYYMMDD'), '999999999')<="+fechaAnterior+" AND em2.id = "+idEmpresa+" AND pc2.id = "+idPlanCuenta+" ) "
				+ "FROM "
				+ "linea_contable lc "
				+ "INNER JOIN plan_cuenta pc ON lc.id_plan_cuenta=pc.id "
				+ "INNER JOIN comprobante co ON lc.id_comprobante=co.id "
				+ "INNER JOIN empresa em ON co.id_empresa=em.id "
				+ "WHERE "
				+ "to_number(to_char(lc.fecha ,'YYYYMMDD'), '999999999')>="+fechaInicio+" AND "	
				+ "to_number(to_char(lc.fecha ,'YYYYMMDD'), '999999999')<="+fechaInicial+" AND "
				+ "em.id = "+idEmpresa+" AND "
				+ "pc.id = "+idPlanCuenta;
		return executeNativeQuerySingleResultDouble(query).doubleValue();
	}


}
