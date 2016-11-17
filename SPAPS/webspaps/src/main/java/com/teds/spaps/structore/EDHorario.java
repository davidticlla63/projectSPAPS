package com.teds.spaps.structore;

import com.teds.spaps.enums.Dias;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.PersonalEspecialidad;
import com.teds.spaps.model.RrHhTurno;

/**
 * 
 * @author david
 *
 */
public class EDHorario {

	private Integer id;
	private String nombre;
	private Especialidad especialidad;
	private Dias dia;
	private RrHhTurno turno;
	private boolean check;

	// Constructor
	public EDHorario() {
		super();
	}

	public EDHorario(Integer id, String nombre, RrHhTurno turno,
			boolean check) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.turno = turno;
		this.check = check;
	}
	
	public EDHorario(Integer id, String nombre, RrHhTurno turno,Dias dia,
			boolean check) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.turno = turno;
		this.dia=dia;
		this.check = check;
	}
	
	public EDHorario(Integer id, String nombre,Especialidad especialidad, RrHhTurno turno,Dias dia,
			boolean check) {
		super();
		this.especialidad= especialidad;
		this.id = id;
		this.nombre = nombre;
		this.turno = turno;
		this.dia=dia;
		this.check = check;
	}
	
	

	public EDHorario(Integer id, String nombre, Dias turno,
			boolean check) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.dia = turno;
		this.check = check;
	}
	
	public EDHorario(Integer id, String nombre,Especialidad especialidad, Dias turno,
			boolean check) {
		super();
		this.especialidad=especialidad;
		this.id = id;
		this.nombre = nombre;
		this.dia = turno;
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

	public RrHhTurno getTurno() {
		return turno;
	}

	public void setTurno(RrHhTurno turno) {
		this.turno = turno;
	}

	/**
	 * @return the dia
	 */
	public Dias getDia() {
		return dia;
	}

	/**
	 * @param dia the dia to set
	 */
	public void setDia(Dias dia) {
		this.dia = dia;
	}

	/**
	 * @return the especialidad
	 */
	public Especialidad getEspecialidad() {
		return especialidad;
	}

	/**
	 * @param especialidad the especialidad to set
	 */
	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

}
