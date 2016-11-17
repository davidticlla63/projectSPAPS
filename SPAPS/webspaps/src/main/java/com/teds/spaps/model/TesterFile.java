package com.teds.spaps.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Barrio
 *
 */
@Entity
@Table(name = "tester_file", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class TesterFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4766247984428476163L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Lob
	@Column(name = "file", nullable = false)
	private byte[] file;

	public TesterFile() {
		super();
		this.id = 0;
		this.nombre = "";
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			if (!(obj instanceof TesterFile)) {
				return false;
			} else {
				if (((TesterFile) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
