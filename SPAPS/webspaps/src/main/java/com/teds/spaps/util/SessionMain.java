package com.teds.spaps.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.teds.spaps.interfaces.dao.IPermisoDao;
import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.interfaces.dao.IUsuarioSucursalDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Consulta;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Gestion;
import com.teds.spaps.model.ParametroEmpresa;
import com.teds.spaps.model.Permiso;
import com.teds.spaps.model.Rol;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoCambio;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.model.UsuarioSucursal;

/**
 * Class SessionMain, persistent data session user login.
 * 
 * @author mauriciobejaranorivera
 *
 */

@Named
@SessionScoped
public class SessionMain implements Serializable {

	private static final long serialVersionUID = 1L;

	// DAO
	private @Inject IPermisoDao permisoDao;
	private @Inject ISucursalDao sucursalDao;
	private @Inject IUsuarioSucursalDao usuarioSucursalDao;
	private String url;

	// OBJECT
	private Rol rol;
	private Usuario usuarioLogin;
	private Sucursal sucursalLogin;
	private Compania compania;
	private Consulta consulta;

	// LIST
	private List<Permiso> listPermisos;

	@PostConstruct
	public void init() {
		System.out.println("----- init SessionMain() --------");
		compania = new Compania();
		rol = new Rol();
		usuarioLogin = new Usuario();
		sucursalLogin = new Sucursal();
		listPermisos = new ArrayList<>();
		consulta = new Consulta();
	}

	public Rol getRol() {
		if (rol.getId().equals(0)) {
			// rol.setId(1);// test
			rol = getUsuarioLogin().getRol();
		}
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public Sucursal getSucursalLogin() {
		if (sucursalLogin.getId().equals(0)) {
			List<UsuarioSucursal> listAux = usuarioSucursalDao
					.obtenerTodosPorUsuario(getUsuarioLogin());
			if (listAux.size() > 0) {
				sucursalLogin = listAux.get(0).getSucursal();
			}else{
				//temporal
				sucursalLogin = new Sucursal();
				sucursalLogin.setId(1);
				sucursalLogin.setNombre("CASA MATRIZ");
			}
		}
		System.out.println("Sucursal Session: "+sucursalLogin);
		return sucursalLogin;
	}

	public void setSucursalLogin(Sucursal sucursalLogin) {
		
		this.sucursalLogin = sucursalLogin;
	}

	public Sucursal PruebaSucursal() {		
		return sucursalDao.obtenerSucursal(sucursalLogin.getId());
	}

	public List<Permiso> getListPermisos() {
		if (listPermisos.size() == 0) {
			listPermisos = permisoDao.obtenerPorRol(getRol());
		}
		return listPermisos;
	}

	public void setListPermisos(List<Permiso> listPermisos) {
		this.listPermisos = listPermisos;
	}

	public StreamedContent getImagenUsuario() {
		StreamedContent stream = null;
		InputStream input = null;
		String mimeType = "image/png";
		byte[] image = null;
		try {
			image = (byte[]) FacesUtil.getSessionAttribute("imageUser");
			if (image == null) {
				image = getUsuarioLogin().getFotoPerfil();
				if (image == null) {
					input = ImageUtil.getImageResources("avatar.jpg",
							"image/jpg").getStream();
					image = ImageUtil.toByteArrayUsingJava(input);
					FacesUtil.setSessionAttribute("imageUser", image);
					stream = new DefaultStreamedContent(input, mimeType);
				} else {
					input = new ByteArrayInputStream(image);
					stream = new DefaultStreamedContent(input, mimeType);
				}
			} else {
				input = new ByteArrayInputStream(image);
				stream = new DefaultStreamedContent(input, mimeType);
			}
			return stream;
		} catch (Exception e) {
			return null;
		}
	}

	public StreamedContent getImagenCompania() {
		return null;
	}

	public Compania getCompania() {
		if (compania.getId().equals(0)) {
			List<UsuarioSucursal> listAux = usuarioSucursalDao
					.obtenerTodosPorUsuario(getUsuarioLogin());
			if (listAux.size() > 0) {
				compania = listAux.get(0).getSucursal().getCompania();
			}
		}
		return compania;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	/**
	 * test
	 * @return
	 */
	public Empresa getEmpresaLogin(){
		Empresa empresa = new Empresa();
			empresa.setId(1);//provicional
		return empresa;
	}
	
	/**
	 * test
	 * @return
	 */
	public TipoCambio getTipoCambioActual(){
		return null;
	}
	
	/**
	 * test
	 * @return
	 */
	public Gestion getGestionLogin(){
		Gestion gestion = new Gestion();
		gestion.setId(1);
		gestion.setGestion("2015");
		return gestion;
	}
	
	/**
	 * test
	 * @return
	 */
	public List<Sucursal> getListSucursal(){
		//temporal
		List<Sucursal> list = new ArrayList<>();
		list.add(getSucursalLogin());
		return list;
	}
	
	/**
	 * test
	 * @return
	 */
	public ParametroEmpresa getParametroEmpresa(){
		ParametroEmpresa param = new ParametroEmpresa();
		param.setId(1);//provicional
		param.setNivelMaximo(5);
		param.setCodificacionEstandar("9.99.999.99999.99999999");
		return param;
	}
	
	/**
	 * @return the consulta
	 */
	public Consulta getConsulta() {
		return consulta;
	}

	/**
	 * @param consulta
	 *            the consulta to set
	 */
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
