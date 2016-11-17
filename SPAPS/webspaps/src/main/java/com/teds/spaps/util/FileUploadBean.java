/**
 * @author david
 */
package com.teds.spaps.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.teds.spaps.interfaces.dao.ILabOrdenDetalleImagDao;
import com.teds.spaps.model.LabOrdenDetalleImag;
import com.teds.spaps.structore.EDFiles;

@ManagedBean(name = "fileUploadBean")
@SessionScoped
public class FileUploadBean extends AbstractDynamicResourceHandler implements
		Serializable {

	/**
     *
     */
	private static final long serialVersionUID = 1L;

	public static List<EDFiles> listArchivos = new ArrayList<EDFiles>();

	public static UploadedFile file;

	// REPOSITORY
	private @Inject ILabOrdenDetalleImagDao detalleImagDao;
	// OBJECT
	private StreamedContent streamedContentimageProducto;

	/**
	 * 
	 */
	public FileUploadBean() {
		// TODO Auto-generated constructor stub
	}

	/* The above code is for file upload using simple mode. */

	// This below code is for file upload with advanced mode.

	public void uploadPhoto(FileUploadEvent e) throws IOException {

		UploadedFile uploadedPhoto = e.getFile();
		String filePath = "c:/temp/fotos/";
		File folder = new File(filePath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		byte[] bytes = null;

		if (null != uploadedPhoto) {
			bytes = uploadedPhoto.getContents();
			String filename = FilenameUtils
					.getName(uploadedPhoto.getFileName());
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(filePath + filename)));
			stream.write(bytes);
			stream.close();
		}

		FacesContext.getCurrentInstance().addMessage(
				"messages",
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Your Photo (File Name " + uploadedPhoto.getFileName()
								+ " with size " + uploadedPhoto.getSize()
								+ ")  Uploaded Successfully", ""));
		listArchivos.add(new EDFiles(uploadedPhoto, "", false));
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName());
		try {
			FacesContext.getCurrentInstance().addMessage(null, msg);
			UploadedFile file = event.getFile();
			InputStream molde = file.getInputstream();
			byte[] image = ImageUtil.toByteArrayUsingJava(molde);
			FacesUtil.setSessionAttribute("imageProducto", image);
			listArchivos.add(new EDFiles(-1 * listArchivos.size(), file, "",
					false, image, file.getFileName(), new Date()));
		} catch (Exception e) {
			FacesUtil.errorMessage("No se pudo subir la imagen.");
		}
	}

	@SuppressWarnings("static-access")
	public void handleFileUploadSingle(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName());
		try {
			FacesContext.getCurrentInstance().addMessage(null, msg);
			UploadedFile file = event.getFile();
			InputStream molde = file.getInputstream();
			byte[] image = ImageUtil.toByteArrayUsingJava(molde);
			FacesUtil.setSessionAttribute("imageProducto", image);
			this.file = file;
		} catch (Exception e) {
			FacesUtil.errorMessage("No se pudo subir la imagen.");
		}
	}
	
	public void removeItem(EDFiles files){
		listArchivos.remove(files);
	}

	public EDFiles probandoDocumentos(int id) {
		for (EDFiles edFiles : listArchivos) {
			if (edFiles.getId() == id && !edFiles.isEnviado())
				return edFiles;
		}
		return null;
	}

	/**
	 * @return the content
	 */
	public StreamedContent getContent() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the HTML. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			String id = context.getExternalContext().getRequestParameterMap()
					.get("param");
			EDFiles documento = probandoDocumentos(Integer.parseInt(id));
			if (documento != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						documento.getImagen()));
			} else {
				return new DefaultStreamedContent();
			}
		}
	}

	@Override
	protected StreamedContent buildStreamedContentImage(FacesContext context,
			Integer idObject) throws Exception {
		InputStream is = null;
		streamedContentimageProducto = null;
		LabOrdenDetalleImag entity = detalleImagDao
				.obtenerLabOrdenDetalleImag(idObject);
		System.out.println("path " + entity.getPathImagen());
		try {
			streamedContentimageProducto = getImageimageProducto();
			if (streamedContentimageProducto == null) {
				if (idObject != 0
						&& (entity.getPathImagen() != null || entity
								.getPathImagen().equals(""))) {
					Path path = Paths.get(entity.getPathImagen());
					System.out.println(entity.getPathImagen());
					byte[] data = Files.readAllBytes(path);
					is = new ByteArrayInputStream(data);
					streamedContentimageProducto = new DefaultStreamedContent(
							new ByteArrayInputStream(
									ImageUtil.toByteArrayUsingJava(is)));
					return streamedContentimageProducto;
				} else {
					String path = FacesContext.getCurrentInstance()
							.getExternalContext().getRealPath("/resources/gfx");
					InputStream inputStream = new FileInputStream(path
							+ File.separator + "icon_product.png");
					streamedContentimageProducto = new DefaultStreamedContent(
							inputStream, "image/png");
				}
			}
		} catch (Exception e) {
			System.out.println("Error obenter Imagen: " + e.getMessage());
		}
		return streamedContentimageProducto;
	}

	/**
	 * @return the listArchivos
	 */
	public List<EDFiles> getListArchivos() {
		return listArchivos;
	}

	/**
	 * @param listArchivos
	 *            the listArchivos to set
	 */
	public void setListArchivos(List<EDFiles> listArchivos) {
		FileUploadBean.listArchivos = listArchivos;
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
		FileUploadBean.file = file;
	}

	public StreamedContent getImageimageProducto() {
		String mimeType = "image/jpg";
		InputStream is = null;
		try {
			byte[] image = (byte[]) FacesUtil
					.getSessionAttribute("imageProducto");
			is = new ByteArrayInputStream(image);
			return new DefaultStreamedContent(new ByteArrayInputStream(
					ImageUtil.toByteArrayUsingJava(is)), mimeType);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getExtensionFile(String filename) {

		int index = filename.lastIndexOf('.');
		if (index == -1) {
			return "";
		} else {
			return filename.substring(index + 1);
		}

	}

	public static String getNameFile(String filename) {
		String name;
		if (filename.contains("/")) {
			name = filename.substring(filename.lastIndexOf('/') + 1,
					filename.lastIndexOf('.'));
		} else {
			name = filename.substring(0, filename.lastIndexOf('.'));
		}

		return name;
	}

}