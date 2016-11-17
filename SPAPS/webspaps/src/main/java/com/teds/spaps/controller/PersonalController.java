/**
 * 
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.teds.spaps.enums.Dias;
import com.teds.spaps.enums.Sexo;
import com.teds.spaps.interfaces.dao.ICargoDao;
import com.teds.spaps.interfaces.dao.IEmpresaDao;
import com.teds.spaps.interfaces.dao.IEspecialidadDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.interfaces.dao.IRolDao;
import com.teds.spaps.interfaces.dao.IRrHhTurnoDao;
import com.teds.spaps.model.Cargo;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.PersonalEspecialidad;
import com.teds.spaps.model.Rol;
import com.teds.spaps.model.RrHhDiaTurno;
import com.teds.spaps.model.RrHhTurno;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.model.UsuarioSucursal;
import com.teds.spaps.structore.EDHorario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;
import com.teds.spaps.util.Time;

/**
 * @author david
 *
 */
@ManagedBean(name = "personalController")
@ViewScoped
public class PersonalController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject IPersonalDao personalDao;
	private @Inject SessionMain sessionMain;
	private @Inject IEmpresaDao empresaDao;
	private @Inject ICargoDao iCargoDao;
	private @Inject IEspecialidadDao especialidadDao;
	private @Inject IRolDao rolDao;
	private @Inject IRrHhTurnoDao turnoDao;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;

	/******* OBJECT **********/
	private Personal personal;
	private Usuario usuario;
	private Personal personalSelected;
	private Sucursal sucursal;
	// private Empresa selectedEmpresa;
	private Cargo selectedCargo;
	private Especialidad selectedEspecialidad = new Especialidad();
	private PersonalEspecialidad personalEspecialidadSelected = new PersonalEspecialidad();
	private RrHhTurno turno;

	/******* LIST **********/
	private List<Personal> listaPersonal;
	// private List<Empresa> listaEmpresa = new ArrayList<Empresa>();
	private List<Cargo> listaCargos = new ArrayList<Cargo>();

	private List<RrHhTurno> listTurnos = new ArrayList<RrHhTurno>();
	private List<Especialidad> listaEspecialidad = new ArrayList<Especialidad>();
	private List<PersonalEspecialidad> listaPersonalEspecialidad = new ArrayList<PersonalEspecialidad>();
	private List<Rol> listRol = new ArrayList<Rol>();

	private String[] selectedDias;
	private String[] selectedTurnos;

	// TREE
	private TreeNode root;
	private TreeNode[] selectedNodes;
	private String selectionModeTreeNode;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// verificar password
	private String repeatPassword;

	// URL PAGE
	private String currentPage = "/pages/rr_hh/personal/list.xhtml";

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		// listDias= Dias.values();
		defaultValues();
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;
		usuario = new Usuario();
		personal = new Personal();
		personalSelected = new Personal();
		selectedCargo = new Cargo();
		selectedEspecialidad = new Especialidad();
		listaPersonal = personalDao.obtenerPorSucursal(sucursal);
		personalEspecialidadSelected = new PersonalEspecialidad();

		listaPersonalEspecialidad.clear();
		listRol = rolDao
				.obtenerTodosActivosEInactivosRpoCompaniaPorOrdenAsc(sucursal
						.getCompania());// obtenerTodosActivosEInactivosPorOrdenAsc();

		currentPage = "/pages/rr_hh/personal/list.xhtml";
	}

	public void desactivarItems(PersonalEspecialidad personalEspecialidad) {
		for (PersonalEspecialidad personalEspecialidad2 : listaPersonalEspecialidad) {
			if (!personalEspecialidad2.getEspecialidad().getId()
					.equals(personalEspecialidad.getEspecialidad().getId())) {
				personalEspecialidad2.setEstado(false);
			}
		}
	}

	public boolean verificarEspecialidades() {
		for (PersonalEspecialidad personalEspecialidad2 : listaPersonalEspecialidad) {
			if (personalEspecialidad2.isEstado() == true) {
				return true;
			}
		}
		return false;
	}

	public void onRowDeleteEspecialidad(
			PersonalEspecialidad PersonalEspecialidad) {
		listaPersonalEspecialidad.remove(PersonalEspecialidad);
	}

	private void cargarHorarios() {
		listTurnos = turnoDao.obtenerPorCompania(sucursal.getCompania());
		root = new DefaultTreeNode(
				new EDHorario(0, "", null, null, null, false), null);
		for (Dias dia : personal.getArrayDias()) {
			TreeNode tn1 = new DefaultTreeNode(new EDHorario(0, dia.getLabel(),
					new Especialidad(), dia, false), root);
			tn1.setExpanded(true);
			tn1.setSelected(false);
			for (RrHhTurno turno : listTurnos) {
				TreeNode tn2 = new DefaultTreeNode("1", new EDHorario(0,
						turno.getNombre() + " " + turno.getHoraEntrada()
								+ " - " + turno.getHoraSalida(), turno, dia,
						false), tn1);
				tn2.setExpanded(true);
				tn2.setSelected(false);

			}
		}
	}

	private void cargarHorariosPorPersonal() {
		root = new DefaultTreeNode(
				new EDHorario(0, "", null, null, null, false), null);

		for (Dias dia : personal.getArrayDias()) {
			TreeNode tn1 = new DefaultTreeNode(
					new EDHorario(0, dia.getLabel(),
							personalEspecialidadSelected.getEspecialidad(),
							dia, false), root);
			tn1.setExpanded(true);
			int i = 0;
			for (RrHhTurno turno : listTurnos) {
				TreeNode tn2 = new DefaultTreeNode("1", new EDHorario(0,
						turno.getNombre() + " " + turno.getHoraEntrada()
								+ " - " + turno.getHoraSalida(), turno, dia,
						false), tn1);
				tn2.setExpanded(true);
				tn2.setSelected(tienePermisoPorTurno(turno, dia));
				if (tn2.isSelected()) {
					i++;
				}
			}
			tn1.setSelected(i == listTurnos.size() && listTurnos.size() > 0);
		}

	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (personal.getNombre().trim().isEmpty()
					|| personal.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			if (selectedCargo.getId().intValue() == 0 || selectedCargo == null) {
				FacesUtil.infoMessage("VALIDACION", "Selecione Cargo");
				return;
			}
			if (listaPersonalEspecialidad.size() == 0
					|| verificarEspecialidades() == false) {
				FacesUtil.infoMessage("VALIDACION",
						"Llene Especialidades o Active Una Especialidad");
				return;
			}
			byte[] image = (byte[]) FacesUtil
					.getSessionAttribute("imagePersonal");
			if (image != null) {
				personal.setFotoPerfil(image);
			}
			personal.setSucursal(sucursal);
			personal.setCargo(selectedCargo);
			personal.setFechaRegistro(new Date());
			personal.setFechaModificacion(personal.getFechaRegistro());
			personal.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());
			if (personal.isLogin()) {
				if (personal.getEmail().trim().length() == 0
						|| personal.getEmail() == null) {
					FacesUtil
							.infoMessage("VALIDACION", "Llene Email por favor");
					return;
				}
				if (!usuario.getPassword().equals(repeatPassword)) {
					FacesUtil.errorMessage("Las Contraseñas no coinciden.");
					return;
				}
				usuario.setEmail(personal.getEmail());
				usuario.setNombre(personal.getNombre() + " "
						+ personal.getApellidoPaterno() + " "
						+ personal.getApellidoMaterno());
				usuario.setFechaRegistro(new Date());
				usuario.setTipo("invitado");
				usuario.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				usuario.setEstado("AC");
				usuario.getListUsuarioSucursals().add(
						new UsuarioSucursal(usuario, sucursal, "AC",
								new Date(), usuario.getUsuarioRegistro()));
				personal.setUsuario(usuario);
			} else {
				personal.setUsuario(null);
			}

			Personal r = personalDao.registrar(personal,
					listaPersonalEspecialidad, selectedNodes);
			if (r != null) {
				defaultValues();
			} else {
				return;
			}
		} catch (Exception e) {
			System.out.println("Error en registro de personal: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (personal.getNombre().trim().isEmpty()
					|| personal.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				if (selectedCargo.getId().intValue() == 0
						|| selectedCargo == null) {
					FacesUtil.infoMessage("VALIDACION", "Selecione Cargo");
					return;
				}
				if (listaPersonalEspecialidad.size() == 0
						&& verificarEspecialidades() == false) {
					FacesUtil.infoMessage("VALIDACION",
							"Llene Especialidades o Active Una Especialidad");
					return;
				}
				byte[] image = (byte[]) FacesUtil
						.getSessionAttribute("imagePersonal");
				if (image != null) {
					personal.setFotoPerfil(image);
				}
				personal.setSucursal(sucursal);
				personal.setCargo(selectedCargo);
				// personal.setUsuario(sessionMain.getUsuarioLogin());
				personal.setFechaModificacion(personal.getFechaRegistro());
				personal.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				if (personal.isLogin()) {
					if (personal.getEmail().trim().length() == 0
							|| personal.getEmail() == null) {
						FacesUtil.infoMessage("VALIDACION",
								"Llene Email por favor");
						return;
					}
					if (!usuario.getPassword().equals(repeatPassword)) {
						FacesUtil.errorMessage("Las Contraseñas no coinciden.");
						return;
					}
					usuario.setEmail(personal.getEmail());
					usuario.setNombre(personal.getNombre() + " "
							+ personal.getApellidoPaterno() + " "
							+ personal.getApellidoMaterno());
					usuario.setFechaRegistro(new Date());
					usuario.setTipo("invitado");
					usuario.setUsuarioRegistro(sessionMain.getUsuarioLogin()
							.getLogin());
					usuario.setFechaModificacion(new Date());
					personal.setUsuario(usuario);
				} else {
					personal.setUsuario(null);
				}
				Personal r = personalDao.modificar(personal,
						listaPersonalEspecialidad, selectedNodes);
				if (r != null) {
					FacesUtil.infoMessage("Personal actualizado", r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de personal: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (personalDao.eliminar(personal)) {
				FacesUtil
						.infoMessage("Personal Eliminado", personal.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de personal: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		personal = personalSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		if (personal.getCargo() != null) {
			if (personal.getCargo().getId() > 0)
				selectedCargo = personal.getCargo();
		} else {
			selectedCargo = new Cargo();
		}
		selectedEspecialidad = personal.obtenerEspecialidadActiva();
		listaPersonalEspecialidad = personal.getListEspecialidades();
		if (personal.isLogin()) {
			usuario = personal.getUsuario();
			repeatPassword = usuario.getPassword();
			personal.setEmail(usuario.getEmail());
		}
		currentPage = "/pages/rr_hh/personal/edit.xhtml";
	}

	public void onRowSelectPersonalEspecialidad(SelectEvent event) {
		cargarHorarios();
		// System.out.println("usuario : " + usuario.getNombre());

		selectionModeTreeNode = "checkbox";
		cargarHorariosPorPersonal();
	}

	public void addHorario() {
		personalEspecialidadSelected.getListDiaTurnos().clear();
		for (TreeNode tn : selectedNodes) {
			EDHorario e = (EDHorario) tn.getData();
			if (tn.getType().equals("1")) {
				RrHhTurno turno = e.getTurno();
				Dias dia = e.getDia();
				System.out.println(dia.getLabel() + " " + e.isCheck());

				RrHhDiaTurno horarios = new RrHhDiaTurno();
				horarios.setFechaRegistro(new Date());
				horarios.setDias(dia);
				horarios.setTurno(turno);
				horarios.setPersonalEspecialidad(personalEspecialidadSelected);
				horarios.setUsuarioRegistro(personal.getUsuarioRegistro());
				horarios.setPersonal(personal);
				personalEspecialidadSelected.getListDiaTurnos().add(horarios);

			}
		}
	}

	private boolean tienePermisoPorTurno(RrHhTurno turno, Dias dia) {
		for (RrHhDiaTurno permiso : personalEspecialidadSelected
				.getListDiaTurnos()) {
			if (permiso.getTurno().equals(turno)
					&& permiso.getDias().equals(dia)) {
				return true;
			}
		}
		return false;
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		personal = new Personal();
		personal.setFechaRegistro(new Date());
		listTurnos = turnoDao.obtenerPorCompania(sucursal.getCompania());
		FacesUtil.setSessionAttribute("imagePersonal", null);
		ParamSistemaIndices sistemaIndices = sistemaIndicesDao
				.obtenerPorCompania("PE", sucursal.getCompania());
		personal.setCodigo(sistemaIndices.getPrefijo()
				+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
						sistemaIndices.getCorrelativo()));
		listaPersonalEspecialidad.clear();
		cargarHorarios();
		currentPage = "/pages/rr_hh/personal/edit.xhtml";

	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		personal = new Personal();
		cargarHorarios();
		currentPage = "/pages/rr_hh/personal/list.xhtml";
	}

	// ONCOMPLETETEXT PAIS
	public List<Especialidad> completeEspecialidad(String query) {
		String upperQuery = query.toUpperCase();
		listaEspecialidad = especialidadDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());

		return listaEspecialidad;
	}

	public boolean verificarEspecialidad(Especialidad especialidad) {
		for (PersonalEspecialidad PersonalEspecialidad : listaPersonalEspecialidad) {
			if (PersonalEspecialidad.getEspecialidad().equals(especialidad))
				return true;
		}
		return false;
	}

	public void onRowSelectEspecialidadClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (Especialidad i : listaEspecialidad) {
			if (i.getNombre().equals(nombre)) {
				if (!verificarEspecialidad(i)) {
					PersonalEspecialidad auxiliar = new PersonalEspecialidad();
					auxiliar.setEspecialidad(i);
					auxiliar.setPersonal(personal);
					cargarHorarios();
					if (listaPersonalEspecialidad.size() == 0) {
						auxiliar.setEstado(true);
						listaPersonalEspecialidad.add(auxiliar);
					} else {
						auxiliar.setEstado(false);
						listaPersonalEspecialidad.add(auxiliar);
					}
					correlativoEspecialidades();
				} else {
					FacesUtil.errorMessage("Ya existe la especialidad");
				}
				return;
			}
		}
	}

	private void correlativoEspecialidades() {
		int correl = 1;
		for (PersonalEspecialidad personalEspecialidad : listaPersonalEspecialidad) {
			personalEspecialidad.setCorrelativo(correl);
			correl++;
		}
	}

	// ONCOMPLETETEXT CARGO
	public List<Cargo> completeCargo(String query) {
		String upperQuery = query.toUpperCase();
		listaCargos = iCargoDao.obtenerPorCompaniaAutoComplete(upperQuery,
				sucursal.getCompania());
		return listaCargos;
	}

	public void onRowSelectCargoClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (Cargo i : listaCargos) {
			if (i.getDescripcion().equals(nombre)) {
				selectedCargo = i;
				return;
			}
		}
	}

	public void actionNuevoHorario() {
		try {
			System.out.println("Ingreso a actionNuevoHorario...");

			FacesUtil.updateComponent("formDlg001");
			FacesUtil.showDialog("dlgDialog");

		} catch (Exception e) {
			System.err.println("Error en actionNuevoHorario : "
					+ e.getStackTrace());
		}
	}

	public void selectSexo(ValueChangeEvent changeEvent) {
		Sexo valor = (Sexo) changeEvent.getNewValue();
		personal.setSexo(valor);
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	/**
	 * 
	 */
	public PersonalController() {
	}

	public Personal getPersonal() {
		return personal;
	}

	public Personal getPersonalSelected() {
		return personalSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Personal> getListaPersonal() {
		return listaPersonal;
	}

	public boolean isModificar() {
		return modificar;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public boolean isCrear() {
		return crear;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	public void setPersonalSelected(Personal personalSelected) {
		this.personalSelected = personalSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaPersonal(List<Personal> listaPersonal) {
		this.listaPersonal = listaPersonal;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
	}

	/**
	 * @return the selectedEmpresa
	 */
	// public Empresa getSelectedEmpresa() {
	// return selectedEmpresa;
	// }
	//
	// /**
	// * @param selectedEmpresa
	// * the selectedEmpresa to set
	// */
	// public void setSelectedEmpresa(Empresa selectedEmpresa) {
	// this.selectedEmpresa = selectedEmpresa;
	// }
	//
	// /**
	// * @return the listaEmpresa
	// */
	// public List<Empresa> getListaEmpresa() {
	// return listaEmpresa;
	// }
	//
	// /**
	// * @param listaEmpresa
	// * the listaEmpresa to set
	// */
	// public void setListaEmpresa(List<Empresa> listaEmpresa) {
	// this.listaEmpresa = listaEmpresa;
	// }

	/**
	 * @return the listaCargos
	 */
	public List<Cargo> getListaCargos() {
		return listaCargos;
	}

	/**
	 * @param listaCargos
	 *            the listaCargos to set
	 */
	public void setListaCargos(List<Cargo> listaCargos) {
		this.listaCargos = listaCargos;
	}

	/**
	 * @return the selectedCargo
	 */
	public Cargo getSelectedCargo() {
		return selectedCargo;
	}

	/**
	 * @param selectedCargo
	 *            the selectedCargo to set
	 */
	public void setSelectedCargo(Cargo selectedCargo) {
		this.selectedCargo = selectedCargo;
	}

	/**
	 * @return the selectedEspecialidad
	 */
	public Especialidad getSelectedEspecialidad() {
		return selectedEspecialidad;
	}

	/**
	 * @param selectedEspecialidad
	 *            the selectedEspecialidad to set
	 */
	public void setSelectedEspecialidad(Especialidad selectedEspecialidad) {
		this.selectedEspecialidad = selectedEspecialidad;
	}

	/**
	 * @return the listaEspecialidad
	 */
	public List<Especialidad> getListaEspecialidad() {
		return listaEspecialidad;
	}

	/**
	 * @param listaEspecialidad
	 *            the listaEspecialidad to set
	 */
	public void setListaEspecialidad(List<Especialidad> listaEspecialidad) {
		this.listaEspecialidad = listaEspecialidad;
	}

	public List<PersonalEspecialidad> getListaPersonalEspecialidad() {
		return listaPersonalEspecialidad;
	}

	public void setListaPersonalEspecialidad(
			List<PersonalEspecialidad> listaPersonalEspecialidad) {
		this.listaPersonalEspecialidad = listaPersonalEspecialidad;
	}

	public PersonalEspecialidad getPersonalEspecialidadSelected() {
		return personalEspecialidadSelected;
	}

	public void setPersonalEspecialidadSelected(
			PersonalEspecialidad PersonalEspecialidadSelected) {
		this.personalEspecialidadSelected = PersonalEspecialidadSelected;
	}

	// public List<PersonalEspecialidad> getListaPersonalEspecialidadBackup() {
	// return listaPersonalEspecialidadBackup;
	// }
	//
	// public void setListaPersonalEspecialidadBackup(
	// List<PersonalEspecialidad> listaPersonalEspecialidadBackup) {
	// this.listaPersonalEspecialidadBackup = listaPersonalEspecialidadBackup;
	// }

	/**
	 * @return the currentPage
	 */
	public String getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	//
	// /**
	// * @return the listDias
	// */
	// public List<Dias> getListDias() {
	// return listDias;
	// }
	//
	// /**
	// * @param listDias the listDias to set
	// */
	// public void setListDias(List<Dias> listDias) {
	// this.listDias = listDias;
	// }

	/**
	 * @return the listTurnos
	 */
	public List<RrHhTurno> getListTurnos() {
		return listTurnos;
	}

	/**
	 * @param listTurnos
	 *            the listTurnos to set
	 */
	public void setListTurnos(List<RrHhTurno> listTurnos) {
		this.listTurnos = listTurnos;
	}

	// /**
	// * @return the dia
	// */
	// public Dias getDia() {
	// return dia;
	// }
	//
	// /**
	// * @param dia the dia to set
	// */
	// public void setDia(Dias dia) {
	// this.dia = dia;
	// }

	/**
	 * @return the turno
	 */
	public RrHhTurno getTurno() {
		return turno;
	}

	/**
	 * @param turno
	 *            the turno to set
	 */
	public void setTurno(RrHhTurno turno) {
		this.turno = turno;
	}

	/**
	 * @return the selectedDias
	 */
	public String[] getSelectedDias() {
		return selectedDias;
	}

	/**
	 * @param selectedDias
	 *            the selectedDias to set
	 */
	public void setSelectedDias(String[] selectedDias) {
		this.selectedDias = selectedDias;
	}

	/**
	 * @return the selectedTurnos
	 */
	public String[] getSelectedTurnos() {
		return selectedTurnos;
	}

	/**
	 * @param selectedTurnos
	 *            the selectedTurnos to set
	 */
	public void setSelectedTurnos(String[] selectedTurnos) {
		this.selectedTurnos = selectedTurnos;
	}

	/**
	 * @return the root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	/**
	 * @return the selectedNodes
	 */
	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	/**
	 * @param selectedNodes
	 *            the selectedNodes to set
	 */
	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	/**
	 * @return the selectionModeTreeNode
	 */
	public String getSelectionModeTreeNode() {
		return selectionModeTreeNode;
	}

	/**
	 * @param selectionModeTreeNode
	 *            the selectionModeTreeNode to set
	 */
	public void setSelectionModeTreeNode(String selectionModeTreeNode) {
		this.selectionModeTreeNode = selectionModeTreeNode;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the listRol
	 */
	public List<Rol> getListRol() {
		return listRol;
	}

	/**
	 * @param listRol
	 *            the listRol to set
	 */
	public void setListRol(List<Rol> listRol) {
		this.listRol = listRol;
	}

	/**
	 * @return the repeatPassword
	 */
	public String getRepeatPassword() {
		return repeatPassword;
	}

	/**
	 * @param repeatPassword
	 *            the repeatPassword to set
	 */
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

}
