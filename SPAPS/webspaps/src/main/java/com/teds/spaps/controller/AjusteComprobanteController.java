package com.teds.spaps.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IComprobanteDao;
import com.teds.spaps.interfaces.dao.IConfiguracionContableDao;
import com.teds.spaps.interfaces.dao.IGestionDao;
import com.teds.spaps.interfaces.dao.ILineaContableDao;
import com.teds.spaps.interfaces.dao.IMayorDao;
import com.teds.spaps.interfaces.dao.IMonedaEmpresaDao;
import com.teds.spaps.interfaces.dao.ITipoComprobanteDao;
import com.teds.spaps.model.Comprobante;
import com.teds.spaps.model.ConfiguracionContable;
import com.teds.spaps.model.Gestion;
import com.teds.spaps.model.LineaContable;
import com.teds.spaps.model.Moneda;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.model.TipoCambioUfv;
import com.teds.spaps.model.TipoComprobante;
import com.teds.spaps.session.SessionContable;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.DateUtility;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "ajusteComprobanteController")
@ViewScoped
public class AjusteComprobanteController implements Serializable {

	private static final long serialVersionUID = -4058327498353920610L;

	//DAO
	private @Inject SessionContable sessionContable;
	private @Inject SessionMain sessionMain;
	private @Inject IGestionDao gestionDao;
	private @Inject IComprobanteDao comprobanteDao;
	private @Inject ITipoComprobanteDao tipoComprobanteDao;
	private @Inject IMonedaEmpresaDao monedaEmpresaDao;
	private @Inject IConfiguracionContableDao configuracionContableDao;
	private @Inject ILineaContableDao lineaContableDao;
	private @Inject IMayorDao mayorDao;

	//VAR
	private String periodo;
	private String gestion;

	//LIST
	private List<Gestion> listGestion;

	//OBJECT

	@PostConstruct
	public void initNewComprobante() {
		loadDefault();
	}

	public void loadDefault(){
		
		periodo = "8";
		
		listGestion = gestionDao.obtenerTodosActivosEInactivosPorOrdenAsc();
		gestion = listGestion.size()==0 ? "2016": listGestion.get(0).getGestion();
	}

	// ACTION EVENT



	// PROCESS

	public void generarAjustePorInflacion(){
		System.out.println("generarAjustePorInflacion ");
		//Obtener todas las cuentas que tengan inflacion
		List<PlanCuenta> listPlanCuentaSelecionInflacion = obtenerCuentasSeleccionadasAjusteInflacion();
		//--------------------------------
		//Verficar si existen cuentas con Ajuste de Inflacion
		if(listPlanCuentaSelecionInflacion.size()==0){
			FacesUtil.infoMessage("Verificación", "No se encontraron Cuentas con Ajuste de inflación.");
			return;
		}
		//--------------------------------
		//traer cuenta de configuracion para inflacion
		ConfiguracionContable configuracion = configuracionContableDao.obtenerPorEmpresac(sessionMain.getEmpresaLogin());
		System.out.println("configuracion: "+configuracion.getProcesoAjuste());
		PlanCuenta cuentaInflacion = configuracion.getCuentaInflacion();
		//--------------------------------
		//Obtener todas las cuentas que tengan inflacion ( Ya se las trajo)
		//--------------------------------
		for(PlanCuenta planCuenta: listPlanCuentaSelecionInflacion){
			Date fechaActual = new Date();
			double tipoCambio =  sessionMain.getTipoCambioActual().getUnidad();
			System.out.println("tipoCambio: "+tipoCambio);
			TipoComprobante tipoComprobante = obtenerTipoComprobanteTraspaso();
			System.out.println("tipoComprobante: "+tipoComprobante);
			Moneda moneda = obtenerMonedaNacional();
			System.out.println("moneda: "+moneda);

			//1.- Obtener El mes actual
			Date datePeriodoActual = new Date();
			Date datePeriodoAnterior = new Date();
			datePeriodoActual.setMonth(Integer.parseInt(periodo)-1);
			datePeriodoAnterior.setMonth(Integer.parseInt(periodo)-2);
			//2.- Obtener fecha del ultimo dia del mes anterior
			Date fechaUltimoDiaMesAnterior = DateUtility.getFechaUltimoDiaDelMesPorFecha(datePeriodoAnterior);
			System.out.println("fechaUltimoDiaMesAnterior: "+fechaUltimoDiaMesAnterior);
			//3.- Obtener fecha del ultimo dia del mes actual
			Date fechaUltimoDiaMesActual = DateUtility.getFechaUltimoDiaDelMesPorFecha(datePeriodoActual);
			System.out.println("fechaUltimoDiaMesActual: "+fechaUltimoDiaMesActual);

			List<Date> listFechas =  generarListaRangoFechas(fechaUltimoDiaMesAnterior,fechaUltimoDiaMesActual);
			System.out.println("listFechas: "+listFechas.size());

			for(Date fecha: listFechas){
				System.out.println("fecha: "+fecha);
				Date fechaAnterior = DateUtility.restarDiasFecha(fecha, 1);
				//Obtener tipoCambio Ufv de la fecha
				TipoCambioUfv tcUfvAnterior = obtenerTipoCambioUfvPorFecha(fechaAnterior);
				System.out.println(" * fechaAnterior: "+fechaAnterior+" - tcUfvAnterior: "+tcUfvAnterior+" *");
				if(tcUfvAnterior!=null){
					double unidadTcUfvAnt = tcUfvAnterior.getUnidad();
					System.out.println("fecha: "+fecha+" | tcUfvAnterior:"+tcUfvAnterior);
					TipoCambioUfv tcUfvPosterior = obtenerTipoCambioUfvPorFecha(fecha);
					double unidadTcUfvPost = tcUfvPosterior == null ? 0 : tcUfvPosterior.getUnidad();
					System.out.println("fecha: "+fecha+" | tcUfvPosterior:"+tcUfvPosterior);
					//calculos
					double saldoCuenta = mayorDao.obtenerSaldoPorFechas(fecha, fecha, planCuenta, sessionMain.getEmpresaLogin());
					System.out.println("SALDO : "+saldoCuenta);
					double calculoUfv = calcularAjusteInflacionUfv(unidadTcUfvAnt,unidadTcUfvPost,saldoCuenta) ;
					System.out.println("( ("+unidadTcUfvPost+" / "+unidadTcUfvAnt+" ) -1) * "+saldoCuenta+" = "+calculoUfv);

					if(calculoUfv>0){//si el resultado del calculo es mayor a cero , recien crear comprobante
						System.out.println("calculoUfv: "+calculoUfv);
						double calculoPromedioPorCuenta = calculoUfv / listPlanCuentaSelecionInflacion.size();
						System.out.println("calculoPromedioPorCuenta: "+calculoPromedioPorCuenta);
						//Crear comprobante
						Comprobante comprobante = new Comprobante();
						comprobante.setNumero(comprobanteDao.obtenerNumeroComprobante(fecha,sessionMain.getEmpresaLogin(),tipoComprobante));
						comprobante.setCorrelativo(getPatternCorrelativo(comprobante.getNumero(),tipoComprobante));
						comprobante.setCorrelativoTransaccional("0");
						comprobante.setEmpresa(sessionMain.getEmpresaLogin());
						comprobante.setEstado("AC");
						comprobante.setFecha(fechaActual);
						comprobante.setFechaRegistro(fechaActual);
						comprobante.setGestion(sessionMain.getGestionLogin());
						comprobante.setGlosa("Ajuste Por Inflacion");
						//debe y haber iguales
						comprobante.setImporteTotalDebeExtranjero(calculoUfv/tipoCambio);
						comprobante.setImporteTotalDebeNacional(calculoUfv);
						comprobante.setImporteTotalHaberExtranjero(calculoUfv/tipoCambio);
						comprobante.setImporteTotalHaberNacional(calculoUfv);
						//----------
						comprobante.setNombre("Ajuste por Inflacion");
						comprobante.setTipoCambio(tipoCambio);
						comprobante.setTipoComprobante(tipoComprobante);
						comprobante.setTipoMoneda(moneda.getNombre());
						comprobante.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());
						comprobante = comprobanteDao.create(comprobante);
						System.out.println("comprobante: "+comprobante);
						//agregar asiento
						List<LineaContable> listLineaContable = new ArrayList<>();

						LineaContable linea1 = new LineaContable();
						linea1.setComprobante(comprobante);
						linea1.setCentroCosto(null);
						linea1.setDebeExtranjero(0);
						linea1.setDebeNacional(0);
						//seteando al haber
						linea1.setHaberExtranjero(calculoPromedioPorCuenta/tipoCambio);
						linea1.setHaberNacional(calculoPromedioPorCuenta);
						linea1.setEstado("AC");
						linea1.setFecha(fechaActual);
						linea1.setFechaRegistro(fechaActual);
						linea1.setGlosa(comprobante.getGlosa());
						linea1.setPlanCuenta(planCuenta);
						linea1.setUsuarioRegistro(comprobante.getUsuarioRegistro());
						lineaContableDao.create(linea1);
						listLineaContable.add(linea1);

						LineaContable linea2 = new LineaContable();
						linea2.setCentroCosto(null);
						linea2.setComprobante(comprobante);
						//seteando al debe
						linea2.setDebeExtranjero( calculoUfv / tipoCambio);
						linea2.setDebeNacional(calculoUfv);
						linea2.setHaberExtranjero(0);
						linea2.setHaberNacional(0);
						linea2.setEstado("AC");
						linea2.setFecha(fechaActual);
						linea2.setFechaRegistro(fechaActual);
						linea2.setGlosa(comprobante.getGlosa());
						linea2.setPlanCuenta(cuentaInflacion);
						linea2.setUsuarioRegistro(comprobante.getUsuarioRegistro());
						lineaContableDao.create(linea2);
						listLineaContable.add(linea2);
						//settear fecha anterior
						fechaAnterior = fecha;
					}
				}
				//Si el caso que este como comprobante por dia
				if(configuracion.getProcesoAjuste().equals("CD")){

				}else{
					//---------------------------------------------
					//Si el caso este como comprobante por mes
					//obtener el mes actual
				}
			}
		}
	}

	public void generarAjusteAlCapital(){
		System.out.println("generarAjusteAlCapital ");
	}

	public void generarAjusteDeReservaPatrimoniales(){
		System.out.println(" generarAjusteDeReservaPatrimoniales");
	}

	// AUXILIAR METHOD
	
	private TipoCambioUfv obtenerTipoCambioUfvPorFecha(Date fecha){
		return null;
	}
	
	private TipoComprobante obtenerTipoComprobanteTraspaso(){
		return tipoComprobanteDao.obtenerPorId(3);
	}

	private Moneda obtenerMonedaNacional(){
		return monedaEmpresaDao.obtenerNacionalPorEmpresa(sessionMain.getEmpresaLogin()).getMoneda();
	}

	private String getPatternCorrelativo(int comprobante,TipoComprobante tipoComprobante){
		// pather = "1508-000001";
		StringBuilder build = new StringBuilder();
		Date fecha = new Date(); 
		String year = new SimpleDateFormat("yy").format(fecha);
		String mes = new SimpleDateFormat("MM").format(fecha);
		return build.append(tipoComprobante.getSigla()).append(year).append(mes).append("-").append(String.format("%06d", comprobante)).toString();
	}
	
	private double calcularAjusteInflacionUfv(double ufvFinal,double ufvInicial,double saldoCuenta){
		return ((ufvFinal / ufvInicial )-1)*saldoCuenta;
	}
	
	private List<Date> generarListaRangoFechas(Date fechaInicial,Date fechaFinal){
		List<Date> list = new ArrayList<>();
		list.add(fechaInicial);
		// aqui generar calculo de fechas
		list.add(fechaInicial);
		return list;
	}
	
	private List<PlanCuenta> obtenerCuentasSeleccionadasAjusteInflacion(){
		List<PlanCuenta> list = new ArrayList<PlanCuenta>();
		for(PlanCuenta pc: sessionContable.getListPlanCuentaAuxiliar()){
			if(pc.getAjuste().equals("API")){
				list.add(pc);
			}
		}
		return list;
	}

	// GET AND SET

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getGestion() {
		return gestion;
	}

	public void setGestion(String gestion) {
		this.gestion = gestion;
	}

	public List<Gestion> getListGestion() {
		return listGestion;
	}

	public void setListGestion(List<Gestion> listGestion) {
		this.listGestion = listGestion;
	}

}
