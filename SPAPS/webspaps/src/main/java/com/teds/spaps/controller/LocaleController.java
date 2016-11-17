package com.teds.spaps.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.teds.spaps.util.UserLocale;

/**
 * 
 * @author mauriciobejaranorivera
 * 
 */

@Named(value = "localeController")
@SessionScoped
public class LocaleController implements Serializable {

	private static final long serialVersionUID = 6690364174406091933L;
	
	private Locale locale;
    
    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void changeLanguage(String language) throws IOException {

        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    @Produces @UserLocale
    public Locale getLocale() {
        return locale;
    }
    
}
