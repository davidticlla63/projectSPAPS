package com.teds.spaps.structore;

import java.util.Date;

import org.primefaces.model.UploadedFile;

/**
 * 
 * @author david
 *
 */
public class EDFiles {

	private Integer id;
	private UploadedFile file;
	private String path;
	private String pathImage;
	private boolean enviado;
	private String nombre;
	private String comentario;
	private byte[] imagen;
	private Date fecha;

	// Constructor
	public EDFiles() {
		super();
	}
	
	
	/**
	 * @param file
	 * @param path
	 * @param enviado
	 */
	public EDFiles(int id,UploadedFile file, String path, boolean enviado,byte[] imagen,String nombre,Date fecha) {
		super();
		this.id=id;
		this.file = file;
		this.path = path;
		this.enviado = enviado;
		this.setImagen(imagen);
		this.nombre=nombre;
		this.fecha=fecha;
	}
	
	/**
	 * @param file
	 * @param path
	 * @param enviado
	 */
	public EDFiles(UploadedFile file, String path, boolean enviado,byte[] imagen) {
		super();
		this.file = file;
		this.path = path;
		this.enviado = enviado;
		this.setImagen(imagen);
	}

	/**
	 * @param file
	 * @param path
	 * @param enviado
	 */
	public EDFiles(UploadedFile file, String path, boolean enviado) {
		super();
		this.file = file;
		this.path = path;
		this.enviado = enviado;
	}

	/**
	 * @param id
	 * @param file
	 * @param path
	 * @param enviado
	 */
	public EDFiles(Integer id, UploadedFile file, String path, boolean enviado) {
		super();
		this.id = id;
		this.file = file;
		this.path = path;
		this.enviado = enviado;
	}

	@Override
	public String toString() {
		return path;
	}
	
	

	// get and set

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EDFiles other = (EDFiles) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the enviado
	 */
	public boolean isEnviado() {
		return enviado;
	}

	/**
	 * @param enviado
	 *            the enviado to set
	 */
	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	

	/**
	 * @return the pathImage
	 */
	public String getPathImage() {
		return pathImage;
	}

	/**
	 * @param pathImage the pathImage to set
	 */
	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

	/**
	 * @return the imagen
	 */
	public byte[] getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}


	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}


	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
