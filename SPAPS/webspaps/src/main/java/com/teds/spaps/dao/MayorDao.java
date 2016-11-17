package com.teds.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IMayorDao;
import com.teds.spaps.model.Comprobante;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.util.DateUtility;
import com.teds.spaps.util.EDLibroMayor;
import com.teds.spaps.util.Time;


/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class MayorDao extends DataAccessObject<Comprobante> implements IMayorDao {


	public MayorDao(){
		super(Comprobante.class);
	}

	@Override
	public List<EDLibroMayor> obtenerMenoresAFecha(Date fecha,PlanCuenta planCuenta, Empresa empresa) {
		String query = "select em from Comprobante em,LineaContable lc where em.id=lc.comprobante.id and lc.planCuenta.id="+planCuenta.getId()
				+" and em.empresa.id="+empresa.getId()+" and to_number(to_char(em.fechaRegistro ,'YYYYMMDD'), '999999999')<"
				+ Time.obtenerFormatoYYYYMMDD(fecha);
		List<Comprobante> list = executeQueryResulList(query);
		return null;
	}

	@Override
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
	
	/**
	 * public int correlativoSiguiente(){
		String query = "select count(em) from pago em where (em.estado='AC' or em.estado='IN')";
		return ((BigInteger) executeNativeQuerySingleResult(query)).intValue()+1;	
	}
	 */


}
