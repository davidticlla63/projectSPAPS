package com.teds.spaps.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.teds.spaps.interfaces.dao.IUsuarioDao;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject IUsuarioDao usuarioDao;
	private @Inject SessionMain sessionMain;
	private Usuario usuario;

	@PostConstruct
	public void init() {
		System.out.println("init - LoginController");
		usuario = new Usuario();
	}

	/**
	 * Verificar login y Password para ingresar al sistema
	 */
	public void login() {
		try {
			if (usuario.getLogin().isEmpty() || usuario.getPassword().isEmpty()) {
				System.out.println("login() -> Usuario o Password sin datos.");
				FacesUtil.errorMessage("Ingresar Usuario y Contraseña.");
				return;
			}
			usuario = usuarioDao.obtenerPorLoginYPassword(usuario.getLogin(),
					usuario.getPassword());
			// validacion
			if (usuario == null) {
				FacesUtil.errorMessage("Revisar Usuario o Contraseña.");
				return;
			}
			// validacion usuario eliminado
			if (usuario.getState().equals("RM")) {
				FacesUtil.infoMessage("Verificar!",
						"Usuario o contraseña incorrecta");
				return;
			}
			// validacion usuario inactivo
			if (usuario.getState().equals("IN")) {
				FacesUtil.infoMessage("Verificar!", "Usuario Inactivo");
				return;
			}
			// usuario ok
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();
			if (request.getUserPrincipal() != null) {
				logout();
			}
			sessionMain.setUsuarioLogin(usuario);
			request.login(usuario.getLogin(), usuario.getPassword());
			if(usuario.getState().equals("PN")){//PENDIENTE
				FacesUtil.redirect("/pages/config/personal/index.xhtml");
				return;
			}
			FacesUtil.redirect(usuario.getPaginaInicio());
		} catch (Exception e) {
			FacesUtil.errorMessage("Usuario o contraseña incorrecta");
		}
	}

	/**
	 * Cerrar Session del Navegador (Sistema)
	 */
	public void logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
			try {
				context.getExternalContext().redirect(
						request.getContextPath() + "/login.xhtml");
			} catch (IOException e) {
			}
		}
	}

	// ----------- Getters and Setters ------------

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
