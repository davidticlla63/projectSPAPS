package com.teds.spaps.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.teds.spaps.model.HCLabOrdenDocumentos;
import com.teds.spaps.util.AbstractDynamicResourceHandler;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author David.Ticlla.Felipe
 *
 */

@Named(value = "hcDynamicResourceController")
@SessionScoped
public class HCDynamicResourceController extends AbstractDynamicResourceHandler
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6201271154658373946L;
	// REPOSITORY
	// OBJECT
	private StreamedContent content;

	public static List<HCLabOrdenDocumentos> documentos = new ArrayList<HCLabOrdenDocumentos>();

	@PostConstruct
	public void init() {
		documentos = new ArrayList<HCLabOrdenDocumentos>();
	}

	private byte[] toByteArrayUsingJava(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = is.read();
		while (reads != -1) {
			baos.write(reads);
			reads = is.read();
		}
		return baos.toByteArray();
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName());
		try {
			FacesContext contexto = FacesContext.getCurrentInstance();
			String ruta = getRutaParam(contexto);
			System.out.println("ruta : " + ruta);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			UploadedFile file = event.getFile();
			InputStream molde = file.getInputstream();
			byte[] image = toByteArrayUsingJava(molde);
			FacesUtil.setSessionAttribute("imageProducto", image);
			HCLabOrdenDocumentos documento = new HCLabOrdenDocumentos();
			documento.setArchivo(image);
			documento.setId(1);
			documentos.add(documento);
			HistoriaClinicaDetalleController.documentos.add(documento);
			System.out.println("documento id = " + documento.getId());
			System.out.println("documento archivo = "
					+ documento.getArchivo().toString());
			// listArchivos.add(new EDFiles(file, "", false));
		} catch (Exception e) {
			FacesUtil.errorMessage("No se pudo subir la imagen.");
		}
	}

	public String getRutaParam(FacesContext contexto) {
		Map<String, String> params = contexto.getExternalContext()
				.getRequestParameterMap();
		return params.get("ruta");
	}

	public StreamedContent getImageimageProducto() {
		String mimeType = "image/jpg";
		InputStream is = null;
		try {
			byte[] image = (byte[]) FacesUtil
					.getSessionAttribute("imagePersonal");
			is = new ByteArrayInputStream(image);
			return new DefaultStreamedContent(new ByteArrayInputStream(
					toByteArrayUsingJava(is)), mimeType);
		} catch (Exception e) {
			return null;
		}
	}

	private HCLabOrdenDocumentos obtener(String ruta) {
		for (HCLabOrdenDocumentos hcLabOrdenDocumentos : documentos) {
			if (hcLabOrdenDocumentos.getPathImagen().equalsIgnoreCase(ruta))
				return hcLabOrdenDocumentos;
		}
		return null;
	}

	@Override
	protected StreamedContent buildStreamedContentImage(FacesContext context,
			Integer idObject) throws Exception {
		// InputStream is = null;
		content = null;
		/*
		 * Personal entity = personalDao.obtenerPersonal(idObject); try {
		 * content = getImageimageProducto(); if (content == null) { if
		 * (idObject != 0 && entity.getFotoPerfil() != null) { is = new
		 * ByteArrayInputStream(entity.getFotoPerfil()); content = new
		 * DefaultStreamedContent( new
		 * ByteArrayInputStream(toByteArrayUsingJava(is))); return content; }
		 * else { String path = FacesContext.getCurrentInstance()
		 * .getExternalContext().getRealPath("/resources/gfx"); InputStream
		 * inputStream = new FileInputStream(path + File.separator +
		 * "icon_product.png"); content = new
		 * DefaultStreamedContent(inputStream, "image/png"); } } } catch
		 * (Exception e) { System.out.println("Error obenter Imagen: " +
		 * e.getMessage()); }
		 */
		return content;
	}

	public void setStreamedContentimageProducto(
			StreamedContent streamedContentimageProducto) {
		this.content = streamedContentimageProducto;
	}

	public static List<HCLabOrdenDocumentos> getDocumentos() {
		return documentos;
	}

	public static void setDocumentos(List<HCLabOrdenDocumentos> documentos) {
		HCDynamicResourceController.documentos = documentos;
	}

	public StreamedContent getContent() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the HTML. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			String ruta = context.getExternalContext().getRequestParameterMap()
					.get("param");
			HCLabOrdenDocumentos documento = obtener(ruta);
			if (documento != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						documento.getArchivo()));
			} else {
				return new DefaultStreamedContent();
			}
		}
	}

	public void setContent(StreamedContent content) {
		this.content = content;
	}

}
