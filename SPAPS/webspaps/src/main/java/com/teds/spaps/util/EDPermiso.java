package com.teds.spaps.util;

import com.teds.spaps.model.MenuAccion;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
public class EDPermiso {

	private Integer id;
	private String nombre;
	private MenuAccion menuAccion;
	private boolean check;

	// Constructor
	public EDPermiso() {
		super();
	}

	public EDPermiso(Integer id, String nombre, MenuAccion menuAccion,
			boolean check) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.menuAccion = menuAccion;
		this.check = check;
	}

	@Override
	public String toString() {
		return nombre;
	}

	// get and set

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public MenuAccion getMenuAccion() {
		return menuAccion;
	}

	public void setMenuAccion(MenuAccion menuAccion) {
		this.menuAccion = menuAccion;
	}

}
