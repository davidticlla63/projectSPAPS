/**
 * @author Rojas
 */
package com.teds.spaps.structore;

import java.util.List;

import com.teds.spaps.model.Mensaje;
import com.teds.spaps.model.Tarea;

/**
 * @author Rojas
 *
 */
public class EDTarea {

	private Tarea tarea;
	private List<Mensaje> mensajes;

	/**
	 * 
	 */
	public EDTarea() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the tarea
	 */
	public Tarea getTarea() {
		return tarea;
	}

	/**
	 * @param tarea
	 *            the tarea to set
	 */
	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	/**
	 * @return the mensajes
	 */
	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	/**
	 * @param mensajes
	 *            the mensajes to set
	 */
	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.tarea.equals((Tarea) obj);
	}

}
