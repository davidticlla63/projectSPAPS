package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.teds.spaps.interfaces.dao.INivelDao;
import com.teds.spaps.interfaces.dao.IParametroEmpresaDao;
import com.teds.spaps.interfaces.dao.IPlanCuentaDao;
import com.teds.spaps.model.MonedaEmpresa;
import com.teds.spaps.model.Nivel;
import com.teds.spaps.model.ParametroEmpresa;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.session.SessionContable;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.EDPlanCuenta;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.NumberUtil;
import com.teds.spaps.util.PlanCuentaUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "planCuentaController")
@ViewScoped
public class PlanCuentaController implements Serializable {

	private static final long serialVersionUID = -7182746804851774430L;

	//DAO
	private @Inject IPlanCuentaDao planCuentaDao;
	private @Inject IParametroEmpresaDao parametroEmpresaDao;
	private @Inject INivelDao nivelDao;
	private @Inject SessionMain sessionMain; //variable del login
	private @Inject SessionContable sessionContable;

	//OBJECT
	private PlanCuenta planCuenta;
	private PlanCuenta planCuentaPadre;
	private MonedaEmpresa monedaEmpresa;
	private ParametroEmpresa parametroEmpresa;

	//LIST
	private List<MonedaEmpresa> listMonedaEmpresa = new ArrayList<MonedaEmpresa>();
	private List<Integer> listTamanio = new ArrayList<Integer>();
	private List<PlanCuenta> listPlanCuentaGeneral = new ArrayList<PlanCuenta>();
	private List<PlanCuenta> listPlanCuentaCoincidencias = new ArrayList<PlanCuenta>();

	//VAR
	private String filterByCuenta = "";
	private int tamanioDigito;
	private int nivelSeleccionado;
	private String currentPage;
	private String maskCodigoCuenta;

	//treeNode
	private TreeNode root;
	private TreeNode selectedNode;

	//STATE V2
	private boolean crear;
	private boolean registrar;
	private boolean seleccionado;
	private boolean stateExpandingPlanCuenta = true;
	private boolean mostrarCodigoCuenta;
	private boolean mostrarTableBusqueda = true;

	@PostConstruct
	public void init() {
		System.out.println("init plan Cuenta()");
		cargarParametroEmpresa();
		loadDefault();
	}

	public void loadDefault(){
		currentPage = "/pages/contabilidad/plan_cuenta/list.xhtml";
		maskCodigoCuenta = "";

		// V2
		crear = true;
		registrar = false;
		seleccionado = false;
		planCuenta = new PlanCuenta();
		planCuentaPadre = new PlanCuenta();

		stateExpandingPlanCuenta = true;
		mostrarCodigoCuenta = true;
		mostrarTableBusqueda = true;

		tamanioDigito = 1;
		nivelSeleccionado = 0;

		selectedNode = null;
		root = new DefaultTreeNode("Root", null);
		listPlanCuentaGeneral = sessionContable.getListPlanCuenta();
		cargarNodos();
		collapsarORexpandir(root,true);
		stateExpandingPlanCuenta = true;
	}

	private void cargarParametroEmpresa(){
		String codigo = "";
		parametroEmpresa = parametroEmpresaDao.obtenerPorEmpresa(sessionMain.getEmpresaLogin());
		if(parametroEmpresa != null){
			codigo = parametroEmpresa.getCodificacionEstandar();
			int anterior = 0;
			for(int i=0;i<codigo.length();i++){
				String letra = String.valueOf(codigo.charAt(i));
				if(letra.equals(".")){
					String numeroString = codigo.substring(anterior, i);
					int numero = 1;
					for(int j=1 ; j < numeroString.length(); j++){
						numero = numero + 1;
					}
					listTamanio.add(numero);
					anterior = i + 1;
				}
			}
			String numeroString = codigo.substring(anterior, codigo.length());
			int numero = 1;
			for(int j=1 ; j < numeroString.length(); j++){
				numero = numero + 1;
			}
			listTamanio.add(numero);
		}else{
			parametroEmpresa = new ParametroEmpresa();
		}
	}

	private  void collapsarORexpandir(TreeNode n, boolean option) {
		if(n.getChildren().size() == 0) {
			n.setSelected(false);
		}
		else {
			for(TreeNode s: n.getChildren()) {
				collapsarORexpandir(s, option);
			}
			n.setExpanded(option);
			n.setSelected(false);
		}
	}

	private List<PlanCuenta> findCuentasRootByLocal(){
		List<PlanCuenta>  listPlanCuentaAux = new ArrayList<PlanCuenta>();
		for(PlanCuenta pc: listPlanCuentaGeneral){
			if(pc.getPlanCuentaPadre()==null){
				listPlanCuentaAux.add(pc);
			}
		}
		return listPlanCuentaAux;
	}

	public void cargarNodos(){
		List<PlanCuenta> listPlanCuentaRoot = findCuentasRootByLocal();
		loadTreeNode(root, listPlanCuentaRoot);
	}

	//-------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * ACTION EVENT NUEVA CUENTA
	 */
	public void actionNuevaCuentaNivel1(){
		//actualizar campos
		planCuenta = new PlanCuenta();
		nivelSeleccionado = 1;
		tamanioDigito = listTamanio.get(nivelSeleccionado-1);
		mostrarCodigoCuenta = true;
		//estados de button
		crear = false;
		registrar = true;
		seleccionado = false;
		maskCodigoCuenta = castMaskCodigoCuenta(planCuentaPadre,nivelSeleccionado,listTamanio);
		maskCodigoCuenta = maskCodigoCuenta.substring(0, maskCodigoCuenta.length()-1);//quitar punto '.'
	}

	/**
	 * ACTION EVENT NUEVA SUBCUENTA
	 */
	public void actionNuevaSubCuenta(){
		if(nivelSeleccionado<listTamanio.size()){//verificar si se puede agregar mas niveles
			//actualizar campos
			planCuenta = new PlanCuenta();
			tamanioDigito = listTamanio.get(nivelSeleccionado);
			mostrarCodigoCuenta = true;
			nivelSeleccionado = nivelSeleccionado +1 ;
			mostrarCodigoCuenta = true;
			//estados de button
			crear = false;
			registrar = true;
			seleccionado = false;
			maskCodigoCuenta = castMaskCodigoCuenta(planCuentaPadre,nivelSeleccionado,listTamanio);
			maskCodigoCuenta = maskCodigoCuenta.substring(0, maskCodigoCuenta.length()-1);//quitar punto '.'
			//verificar si es la ultima cuenta, seleccionar como cuenta auxiliar
			if(nivelSeleccionado == listTamanio.size()){
				planCuenta.setDescripcion("");
				planCuenta.setClase("AUXILIAR");
				monedaEmpresa = new MonedaEmpresa();
				monedaEmpresa = sessionContable.getListMonedaEmpresa().get(0);
				planCuenta.setMonedaEmpresa(monedaEmpresa);
			}
		}else{
			FacesUtil.infoMessage("Validación Cuenta", "No se puede agregar mas cuentas");
			return;
		}
	}

	public void onRowSelectPlanCuentaNode(NodeSelectEvent event){
		String descripcion =((EDPlanCuenta) event.getTreeNode().getData()).getCuenta().toString();
		planCuenta = obtenerPlanCuentaByLocal(descripcion);
		monedaEmpresa = planCuenta.getMonedaEmpresa();
		//settear como plan de cuenta padre
		planCuentaPadre = planCuenta;
		//cargando parametros de codigo y nivel de cuenta
		nivelSeleccionado = planCuenta.getNivel().getNivel() ;
		tamanioDigito = listTamanio.get(nivelSeleccionado-1);
		mostrarCodigoCuenta = false;
		//estados de button
		crear = false;
		registrar = false;
		seleccionado = true;
		//armar mask
		int cont = tamanioDigito;
		String codigoAIngresar = "";
		while(cont>=0){
			codigoAIngresar = codigoAIngresar+"9";
			cont = cont -1;
		}
		maskCodigoCuenta = "";//castMaskCodigoCuenta(planCuentaPadre.getCodigo(),tamanioDigito,listTamanio);
		//maskCodigoCuenta = maskCodigoCuenta.substring(0, maskCodigoCuenta.length()-1);//quitar punto '.'
		//System.out.println("mask: "+maskCodigoCuenta);
		//9.99.999.9999.99999 | 1,2,3,5,7
	}

	private String castMaskCodigoCuenta(PlanCuenta codigoCuentaPadre,int nivelNuevaCuenta,List<Integer> listTamanio){
		String cuenta = obtenerCodigoCompletoConSusPadres(codigoCuentaPadre);
		StringBuilder build = new StringBuilder();
		int cont = 0;
		if(nivelNuevaCuenta == 1){
			int contAux = listTamanio.get(0);
			while(contAux > 0){ build.append("9"); contAux = contAux - 1; }
			cont = cont + 1;
			for(Integer i: listTamanio){
				if(cont>=2){
					int contAux2 = 0;
					while(contAux2 < i){ build.append("0"); contAux2 = contAux2 + 1; }
				} cont = cont + 1; build.append(".");
			}
		}else{
			for(Integer i: listTamanio){
				cont = cont + 1;
				if(cont == nivelNuevaCuenta){
					int contAux2 = 0;
					while(contAux2 < i){ build.append("9"); contAux2 = contAux2 + 1; }
				}else{
					String cuentCut = obtenerCuentaPorNivel(cuenta,listTamanio,cont);
					if(cuentCut.isEmpty()){
						int contAux3 = 1;
						while(contAux3 <= i){
							build.append("0"); contAux3 = contAux3 + 1;
						}
					}else{ build.append(cuentCut);}
				} build.append(".");;
			}
		} return build.toString();
	}

	private String obtenerCuentaPorNivel(String cuenta ,List<Integer> list, int nivel){
		StringBuilder build = new StringBuilder();
		int cont = 0,endIndex=0;
		for(Integer i: list){
			endIndex = endIndex + i;//total de caracteres
			cont = cont + 1; //puntero de la list
			if(cont == nivel){
				int beginIndex = endIndex - i;
				if(endIndex <= cuenta.length()){
					String cut = cuenta.substring(beginIndex, endIndex);
					build.append(cut);
				}
			}
		} return build.toString();
	}

	/**
	 * @param planCuentaPadre2
	 * @return
	 */
	private String obtenerCodigoCompletoConSusPadres(PlanCuenta planCuentaPadre2) {
		String data = "";
		PlanCuenta pc = planCuentaPadre2;
		while(pc!=null){
			data = pc.getCodigo()+data;
			pc=pc.getPlanCuentaPadre();
		}
		return data;
	}

	public void onRowSelectPlanCuentaObject(SelectEvent event){
		//actualizar datos
		//settear como plan de cuenta padre
		monedaEmpresa = planCuenta.getMonedaEmpresa();
		planCuentaPadre = planCuenta;
		nivelSeleccionado = planCuenta.getNivel().getNivel();
		tamanioDigito = listTamanio.get(nivelSeleccionado-1);
		mostrarCodigoCuenta = false;
		//estados de button
		crear = false;
		registrar = false;
		seleccionado = true;
		//armar mask
		maskCodigoCuenta = "";
	}

	/**
	 * Se verifica si la cuenta tiene alguna tranasaccion en algun comprobante,
	 * En el caso de que ya exista una transaccion utilizando esa cuenta no se podra eliminar
	 * 
	 */
	public void sePuedeEliminarCuenta(){
		//1 seleccionar cuenta
		//2 obtener gestion
		//3 obtener empresa
		//4 verificar si la cuenta es PADRE (CONTROL) y verificar tambien sus hijos
		//5 Hacer Busqueda en los comprobante , si la cuenta es utilizada
		//
	}

	public void cancelarBusqueda(){
		filterByCuenta = "";
		mostrarTableBusqueda = true;
	}

	public void registrarPlanCuenta(){
		if(nivelSeleccionado==1){
			registrarPlanCuentaNivel1();
		}else{
			registrarSubPlanCuenta();
		}
	}

	private String limpiarCodigoCuenta(String codigoCuenta,int nivel,List<Integer> listTamanio){
		codigoCuenta =codigoCuenta.replace(".", "");
		int cont = 0,count = 0,beginIndex = 0;
		for(Integer i: listTamanio){
			beginIndex = count;
			count = count + i; cont = cont + 1;
			if(cont == nivel){break;}
		}
		codigoCuenta = codigoCuenta.substring(beginIndex,count);
		return codigoCuenta;
	}
	
	public void changeOneMenuMonedaEmpresa(){
		for(MonedaEmpresa me: sessionContable.getListMonedaEmpresa()){
			if(me.getId().equals(monedaEmpresa.getId())){
				monedaEmpresa = me;
				planCuenta.setMonedaEmpresa(monedaEmpresa);
			}
		}
	}

	private String cargarCodigoCuentaConPunto(String cuenta,List<Integer> listTamanio){
		StringBuilder sb = new StringBuilder();
		int ant = 0;
		for(Integer i: listTamanio){
			sb.append(cuenta.substring(ant,(ant+ i.intValue())));
			sb.append(".");
			ant = i.intValue();
		}
		return sb.toString().substring(0,sb.length()-1);
	}
	
	/**
	 * Registro de nueva cuenta nivel 1 en DB
	 */
	private void registrarPlanCuentaNivel1(){
		Nivel nivel = nivelDao.findByNivelEmpresa(nivelSeleccionado ,sessionMain.getEmpresaLogin());
		planCuenta.setCodigo(limpiarCodigoCuenta(planCuenta.getCodigo(),nivel.getNivel(),listTamanio)); //ver la forma que se guardar el codigo de la cuenta(QUITAR ULTIMOS CEROS Y LOS PUNTOS)
		planCuenta.setDescripcion(planCuenta.getDescripcion().toUpperCase());
		planCuenta.setCodigo(PlanCuentaUtil.llenarDelanteConCeroCodificacion(planCuenta.getCodigo(),tamanioDigito));
		planCuenta.setCodigoAuxiliar(cargarCodigoCuentaConPunto(PlanCuentaUtil.llenarDespuesConCeroCodificacion(planCuenta.getCodigo(),tamanioDigito,listTamanio),listTamanio));
		planCuenta.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());
		planCuenta.setFecha(new Date());
		planCuenta.setEstado("AC");
		planCuenta.setEmpresa(sessionMain.getEmpresaLogin());
		planCuenta.setNivel(nivel);
		planCuenta.setPlanCuentaPadre(planCuentaPadre.getId().equals(0)?null:planCuentaPadre);
		PlanCuenta pc = planCuentaDao.registrar(planCuenta);
		if(pc != null){
			//agregar la nueva cuenta a la lista en memoria
			sessionContable.getListPlanCuenta().add(pc);
			loadDefault();
		}
		//nivel , tipoCuenta , monedaEmpresa
	}

	/**
	 * Registro de nueva sub cuenta en DB
	 */
	private void registrarSubPlanCuenta(){
		Nivel nivel = nivelDao.findByNivelEmpresa(nivelSeleccionado ,sessionMain.getEmpresaLogin());
		String newCodigoAuxiliar = planCuenta.getCodigo();
		planCuenta.setCodigo(limpiarCodigoCuenta(planCuenta.getCodigo(),nivel.getNivel(),listTamanio)); //ver la forma que se guardar el codigo de la cuenta(QUITAR ULTIMOS CEROS Y LOS PUNTOS)
		String newCodigo = PlanCuentaUtil.llenarDelanteConCeroCodificacion(planCuenta.getCodigo(),tamanioDigito);

		planCuenta.setDescripcion(planCuenta.getDescripcion().toUpperCase());
		planCuenta.setCodigo(newCodigo);
		planCuenta.setCodigoAuxiliar(newCodigoAuxiliar);
		planCuenta.setEmpresa(sessionMain.getEmpresaLogin());
		planCuenta.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());
		planCuenta.setPlanCuentaPadre(planCuentaPadre);
		planCuenta.setFecha(new Date());
		planCuenta.setEstado("AC");
		planCuenta.setTipoCuenta(planCuentaPadre.getTipoCuenta());
		planCuenta.setNivel(nivel);
		PlanCuenta pc = planCuentaDao.registrar(planCuenta);
		if(pc != null){
			//agregar la nueva cuenta a la lista en memoria
			sessionContable.getListPlanCuenta().add(pc);
			loadDefault();
		}
	}

	/**
	 * Registro de nueva cuenta en DB
	 */
	public void modificarPlanCuenta(){
		boolean sw = false;
		if(sw){
			loadDefault();
		}
	}

	/**
	 * Registro de nueva cuenta en DB
	 */
	public void eliminarPlanCuenta(){
		boolean sw = false;
		if(sw){
			loadDefault();
		}
	}

	//- busqueda de cuentas - 

	private void buscarNodos(String nombre){
		List<PlanCuenta> listPlanCuentaCoincidencias2 = obtenerPlanCuentaCoincidencias(nombre);
		listPlanCuentaCoincidencias = obtenerPlanCuentaUltimoNivel(listPlanCuentaCoincidencias2);
	}

	private List<PlanCuenta> obtenerPlanCuentaCoincidencias(String query){
		List<PlanCuenta> list = new ArrayList<PlanCuenta>();
		for(PlanCuenta pc: listPlanCuentaGeneral){
			if(NumberUtil.isNumeric(query)){
				if(pc.getCodigo().startsWith(query)){ list.add(pc); }
			}else{
				if(pc.getDescripcion().toUpperCase().startsWith(query.toUpperCase())){ list.add(pc); }
			}
		}
		return list;
	}

	private List<PlanCuenta> obtenerPlanCuentaUltimoNivel(List<PlanCuenta> list){
		List<PlanCuenta> listAux = new ArrayList<PlanCuenta>();
		for(PlanCuenta pc: list){
			if(pc.getNivel().getNivel() == parametroEmpresa.getNivelMaximo()){
				listAux.add(pc);
			}
		}
		return listAux;
	}

	public List<PlanCuenta> onCompleteTextCuentaAuxiliar(String query) {
		String upperQuery = query.toUpperCase();
		List<PlanCuenta> results = new ArrayList<PlanCuenta>();
		for(PlanCuenta i : listPlanCuentaGeneral) {
			if(i.getClase().equals("AUXILIAR")){
				if(NumberUtil.isNumeric(query.replace(".", ""))){
					if(i.getCodigoAuxiliar().replace(".", "").startsWith(query.replace(".", ""))){ results.add(i); }
				}else{
					if(i.getDescripcion().toUpperCase().startsWith(upperQuery)){ results.add(i); }
				}
			}
		}         
		return results;
	}

	//-------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * method recursivo que carga el plan de cuenta
	 * @param root
	 * @param listPlanCuenta
	 */
	private void loadTreeNode(TreeNode root, List<PlanCuenta> listPlanCuenta){
		if(listPlanCuenta.size()>0){
			for(PlanCuenta pc : listPlanCuenta){
				String moneda = pc.getMonedaEmpresa()!=null?pc.getMonedaEmpresa().getMoneda().getNombre():"";
				TreeNode tn = new DefaultTreeNode(new EDPlanCuenta(pc.getId() ,pc.getCodigo(), pc.getDescripcion(), pc.getClase(),moneda,pc),root);
				loadTreeNode(tn, obtenerHijas(pc));
			}
		}
	}

	private List<PlanCuenta> obtenerHijas(PlanCuenta padre){
		return obtenerHijasByLocal( padre);
	}

	private List<PlanCuenta> obtenerHijasByLocal(PlanCuenta padre){
		List<PlanCuenta>  listPlanCuentaAux = new ArrayList<PlanCuenta>();
		for(PlanCuenta pc: listPlanCuentaGeneral){
			if(pc.getPlanCuentaPadre()!=null){
				if(pc.getPlanCuentaPadre().equals(padre)){
					listPlanCuentaAux.add(pc);
				}
			}
		}
		return listPlanCuentaAux;
	}

	public void expanding(){
		collapsarORexpandir(root, stateExpandingPlanCuenta);
	}

	public void onRowSelectPlanCuenta(SelectEvent event) {

		//cargando parametros de codigo y nivel de cuenta
		//nivelSeleccionado = selectedPlanCuenta.getNivel().getNivel() ;// cuenta nivel =1  nivel_maximo = 2
		//System.out.println("nivelSeleccionado = "+nivelSeleccionado);

		//verificar que cuando este en la ultima cuenta auxiliar ya no le permita agregar mas cuentas
		if( nivelSeleccionado >= parametroEmpresa.getNivelMaximo()){
			//selectedNode = null;
			selectedNode = new DefaultTreeNode(null, null);
		}else{
			tamanioDigito = listTamanio.get(nivelSeleccionado);
		}
		//--

	}

	// id codigo  descripcion clase tipoCuenta planCuentaPadre moneda tipoRegistro correlativo1 correlativo2 empresa nivel fecha estado usuarioRegistro
	public void agregarSubCuenta(){
		try {
			//System.out.println("Ingreso a agegarSubCuenta cuenta: "+newPlanCuenta.getDescripcion()+" | nombreClase: "+nombreClase);
			//			if(nombreClase.equals("AUXILIAR")){
			//				newPlanCuenta.setMonedaEmpresa(monedaEmpresa);
			//				//newPlanCuenta.setUfv(permitirUfv?"SI":"NO");
			//			}else{
			//				newPlanCuenta.setMonedaEmpresa(null);
			//				newPlanCuenta.setUfv("NO");
			//			}
			//	String newCodigo = PlanCuentaUtil.llenarDelanteConCeroCodificacion(newPlanCuenta.getCodigo(),tamanioDigito);
			//String parteCodigoAuxiliarPadre = obtenerParteCodigoAuxiliar(selectedPlanCuenta.getCodigoAuxiliar(),selectedPlanCuenta.getNivel());
			//	String newCodigoAuxiliar = PlanCuentaUtil.cargarCodificacion(parteCodigoAuxiliarPadre,newPlanCuenta.getCodigo(),tamanioDigito,listTamanio);

			//System.out.println("-----newCodigo:"+newCodigo+" | newCodigoAuxiliar:"+newCodigoAuxiliar+"-----");
			//newPlanCuenta.setDescripcion(newPlanCuenta.getDescripcion().toUpperCase());
			//newPlanCuenta.setCodigo(newCodigo);
			//newPlanCuenta.setCodigoAuxiliar(newCodigoAuxiliar);
			//newPlanCuenta.setClase(nombreClase);
			//newPlanCuenta.setEmpresa(sessionMain.getEmpresaLogin());
			///newPlanCuenta.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());
			//newPlanCuenta.setPlanCuentaPadre(selectedPlanCuenta);
			//newPlanCuenta.setFecha(new Date());
			//newPlanCuenta.setEstado("AC");
			//newPlanCuenta.setTipoCuenta(selectedPlanCuenta.getTipoCuenta());
			//Nivel nivel = nivelDao.findByNivelEmpresa(nivelSeleccionado + 1 ,sessionMain.getEmpresaLogin());
			//newPlanCuenta.setNivel(nivel);
			//PlanCuenta pc = planCuentaDao.registrar(newPlanCuenta);
			//if(pc!=null){
			//agregar la nueva cuenta a la lista en memoria
			//	sessionContable.getListPlanCuenta().add(pc);
			//ocultar dialog 
			//	FacesUtil.hideDialog("dlgGestionCuenta");
			//	loadDefault();
			//}
		} catch (Exception e) {
			FacesUtil.errorMessage("Registro Incorrecto");
		}
	}

	public void registrarCuenta() {
		System.out.println("Ingreso a registrarPlanCuenta: ");
		//		if(nombreClase.equals("AUXILIAR")){
		//			newPlanCuenta.setMonedaEmpresa(monedaEmpresa);
		//			//newPlanCuenta.setUfv(permitirUfv?"SI":"NO");
		//		}else{
		//			newPlanCuenta.setMonedaEmpresa(null);
		//			newPlanCuenta.setUfv("NO");
		//		}
		//verificar que no agregue el mismo codigo
		//newPlanCuenta.setDescripcion(newPlanCuenta.getDescripcion().toUpperCase());
		//	newPlanCuenta.setCodigo(PlanCuentaUtil.llenarDelanteConCeroCodificacion(newPlanCuenta.getCodigo(),tamanioDigito));
		//newPlanCuenta.setCodigoAuxiliar(PlanCuentaUtil.llenarDespuesConCeroCodificacion(newPlanCuenta.getCodigo(),tamanioDigito,listTamanio));
		//newPlanCuenta.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());
		//newPlanCuenta.setClase(nombreClase);
		//newPlanCuenta.setFecha(new Date());
		//newPlanCuenta.setEstado("AC");
		//newPlanCuenta.setEmpresa(sessionMain.getEmpresaLogin());
		//	Nivel nivel = nivelDao.findByNivelEmpresa(nivelSeleccionado+1 ,sessionMain.getEmpresaLogin());
		//newPlanCuenta.setNivel(nivel);
		//newPlanCuenta.setPlanCuentaPadre(null);
		//newPlanCuenta.setTipoCuenta(findTipoCuentaByLocal(newPlanCuenta.getCodigo()));			
		//PlanCuenta pc = planCuentaDao.registrar(newPlanCuenta);
		//if(pc!=null){
		//agregar la nueva cuenta a la lista en memoria
		//	sessionContable.getListPlanCuenta().add(pc);
		//ocultar dialog 
		//	FacesUtil.hideDialog("dlgGestionCuenta");
		//	loadDefault();
		//}
	}

	/**
	 * 
	 * @param codigoAuxiliar
	 * @param nivel
	 * @return
	 */
	private String obtenerParteCodigoAuxiliar(String codigoAuxiliar, Nivel nivel) {
		System.out.println("codigoAuxiliar: "+codigoAuxiliar+" | "+nivel);
		int nroDigitosTotal =PlanCuentaUtil.obtenerTamanioHastaNIvel(nivel.getNivel(),listTamanio);
		System.out.println("nroDigitosTotal: "+nroDigitosTotal);
		return codigoAuxiliar.substring(0, nroDigitosTotal );
	}

	/**
	 * 
	 */
	public void modificarCuenta() {
		//System.out.println("Ingreso a modificarPlanCuenta: " + newPlanCuenta.getId());
		//		if(nombreClase.equals("AUXILIAR")){
		//			newPlanCuenta.setMonedaEmpresa(monedaEmpresa);
		//			//newPlanCuenta.setUfv(permitirUfv?"SI":"NO");
		//		}
		//PlanCuenta pc = planCuentaDao.modificar(newPlanCuenta);
		//if(pc!=null){
		//actualizar la nueva cuenta a la lista en memoria
		//	sessionContable.actualizarCuenta(pc);
		//	loadDefault();
		//}	
	}

	private PlanCuenta obtenerPlanCuentaByLocal(String descripcion){
		for(PlanCuenta pc: listPlanCuentaGeneral){
			if(pc.getDescripcion().equals(descripcion)){
				return pc;
			}
		}
		return null;
	}

	public void onNodeSelect(NodeSelectEvent event) {
		//String descripcion =((EDPlanCuenta) event.getTreeNode().getData()).getCuenta().toString();
		//selectedPlanCuenta = obtenerPlanCuentaByLocal(descripcion);//planCuentaRepository.findByDescripcionAndEmpresa2(descripcion, empresaLogin);
		//cargando parametros de codigo y nivel de cuenta
		//nivelSeleccionado = selectedPlanCuenta.getNivel().getNivel() ;// cuenta nivel =1  nivel_maximo = 2
		tamanioDigito = listTamanio.get(nivelSeleccionado-1);
		//System.out.println("nivelSeleccionado = "+nivelSeleccionado+" | tamanioDigito = "+tamanioDigito);

	}

	public void deleteNode() {
		//System.out.println("deleteNode()");
		EDPlanCuenta aux = (EDPlanCuenta) selectedNode.getData();
		//System.out.println("aux="+aux);
		selectedNode.getChildren().clear();
		selectedNode.getParent().getChildren().remove(selectedNode);
		selectedNode.setParent(null);
		selectedNode = null;
		PlanCuenta pc = new PlanCuenta(); 
		//preguntar:
		//si es una clase de control -> lanzarle un dialogo que se borrara todos sus cuentas asociadas
		//if(aux.getClase().equals("CONTROL")){
		//	FacesUtil.showDialog("dlgEliminarCuentaControl");
		//}else{//AUXILIAR
		//try{
		pc = aux.getPc();
		//obtener todas sus cuentas hijas
		List<PlanCuenta> listCuentasHijas = obtenerHijasByLocal(pc);
		boolean sw = planCuentaDao.eliminar(pc,listCuentasHijas);
		if(sw){
			//eliminar cuenta de memoria(APLICATION SOCPED)
			sessionContable.eliminarCuenta(pc);
			loadDefault();
		}
	}

	public void displaySelectedSingle() {
		if(selectedNode != null) {
			FacesUtil.infoMessage("Selected", selectedNode.getData().toString());
		}
	}

	public void buscarCuentasByFilter(){
		buscarNodos(filterByCuenta);
		mostrarTableBusqueda = false;
	}


	// ------------  get and set -------------------


	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public boolean isStateExpandingPlanCuenta() {
		return stateExpandingPlanCuenta;
	}

	public void setStateExpandingPlanCuenta(boolean stateExpandingPlanCuenta) {
		this.stateExpandingPlanCuenta = stateExpandingPlanCuenta;
	}

	public String getFilterByCuenta() {
		return filterByCuenta;
	}

	public void setFilterByCuenta(String filterByCuenta) {
		this.filterByCuenta = filterByCuenta;
	}

	public List<MonedaEmpresa> getListMonedaEmpresa() {
		return listMonedaEmpresa;
	}

	public void setListMonedaEmpresa(List<MonedaEmpresa> listMonedaEmpresa) {
		this.listMonedaEmpresa = listMonedaEmpresa;
	}

	public ParametroEmpresa getParametroEmpresa() {
		return parametroEmpresa;
	}

	public void setParametroEmpresa(ParametroEmpresa parametroEmpresa) {
		this.parametroEmpresa = parametroEmpresa;
	}

	public int getTamanioDigito() {
		return tamanioDigito;
	}

	public void setTamanioDigito(int tamanioDigito) {
		this.tamanioDigito = tamanioDigito;
	}

	public int getNivelSeleccionado() {
		return nivelSeleccionado;
	}

	public void setNivelSeleccionado(int nivelSeleccionado) {
		this.nivelSeleccionado = nivelSeleccionado;
	}

	public List<PlanCuenta> getListPlanCuentaCoincidencias() {
		return listPlanCuentaCoincidencias;
	}

	public void setListPlanCuentaCoincidencias(
			List<PlanCuenta> listPlanCuentaCoincidencias) {
		this.listPlanCuentaCoincidencias = listPlanCuentaCoincidencias;
	}

	public boolean isMostrarTableBusqueda() {
		return mostrarTableBusqueda;
	}

	public void setMostrarTableBusqueda(boolean mostrarTableBusqueda) {
		this.mostrarTableBusqueda = mostrarTableBusqueda;
	}

	public boolean isMostrarCodigoCuenta() {
		return mostrarCodigoCuenta;
	}

	public void setMostrarCodigoCuenta(boolean mostrarCodigoCuenta) {
		this.mostrarCodigoCuenta = mostrarCodigoCuenta;
	}

	public String getPage() {
		return currentPage;
	}

	public void setPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public boolean isCrear() {
		return crear;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public PlanCuenta getPlanCuenta() {
		return planCuenta;
	}

	public void setPlanCuenta(PlanCuenta planCuenta) {
		this.planCuenta = planCuenta;
	}

	public PlanCuenta getPlanCuentaPadre() {
		return planCuentaPadre;
	}

	public void setPlanCuentaPadre(PlanCuenta planCuentaPadre) {
		this.planCuentaPadre = planCuentaPadre;
	}

	public String getMaskCodigoCuenta() {
		return maskCodigoCuenta;
	}

	public void setMaskCodigoCuenta(String maskCodigoCuenta) {
		this.maskCodigoCuenta = maskCodigoCuenta;
	}

	public MonedaEmpresa getMonedaEmpresa() {
		return monedaEmpresa;
	}

	public void setMonedaEmpresa(MonedaEmpresa monedaEmpresa) {
		this.monedaEmpresa = monedaEmpresa;
	}

}
