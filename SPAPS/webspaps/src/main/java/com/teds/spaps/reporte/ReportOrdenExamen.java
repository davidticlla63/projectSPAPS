package com.teds.spaps.reporte;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.teds.spaps.model.LabOrden;
import com.teds.spaps.util.Conexion;

@WebServlet("/ReportOrdenExamen")
public class ReportOrdenExamen extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6864764769799543284L;

	@Inject
	private EntityManager em;

	@SuppressWarnings("unused")
	@Inject
	private FacesContext facesContext;

	@SuppressWarnings({ "deprecation", "unused", "rawtypes", "unchecked" })
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ServletOutputStream servletOutputStream = response.getOutputStream();

		JasperReport jasperReport;
		JasperPrint jasperPrint;

		Connection conn = null;
		DataSource ds = null;
		Context ctx = null;
		try {

			try {

				ctx = new InitialContext();
				ds = (DataSource) ctx.lookup(Conexion.datasourse);

				conn = ds.getConnection();

				if (conn != null) {
					System.out
							.println("Conexion Exitosa JDBC com.edb.Driver...");
				} else {
					System.out.println("Error Conexion JDBC com.edb.Driver...");
				}

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error al conectar JDBC: " + e.getMessage());
			}

			try {

				String pIdOrden = request.getParameter("pIdOrden");
				// String pPathLogo = request.getParameter("pLogo");
				LabOrden factura = em.find(LabOrden.class,
						new Integer(pIdOrden));

				if (factura != null) {

					System.out.println("Conexion em: " + em.isOpen());

					String realPath = request.getRealPath("/");
					System.out.println("Real Path: " + realPath);

					// load JasperDesign from XML and compile it into
					// JasperReport
					System.out.println("Context getServletContext: "
							+ request.getServletContext().getContextPath());
					System.out.println("Context getServletPath: "
							+ request.getServletPath());
					System.out
							.println("Context getSession().getServletContext(): "
									+ request.getSession().getServletContext()
											.getRealPath("/"));

					String urlPath = request.getRequestURL().toString();
					urlPath = urlPath.substring(0, urlPath.length()
							- request.getRequestURI().length())
							+ request.getContextPath() + "/";
					System.out.println("URL ::::: " + urlPath);

					String urlsubReporte = urlPath
							+ "resources/report/laboratorio/";
					String URL_SERVLET_LOGO = urlPath + "ServletImageLogo?id="
							+ factura.getCompania().getId() + "&type=EMPRESA";

					String URL_SERVLET_IMAGE_ANULADA = "";
					if (factura.getEstado().equals("A")) {

						URL_SERVLET_IMAGE_ANULADA = urlPath
								+ "resources/gfx/anulada.png";
					}

					// String urlQR = urlPath + "codeQR?qrtext=" + qr;

					System.out.println("SUBREPORT_DIR: " + urlsubReporte);
					// create a map of parameters to pass to the report.
					Map parameters = new HashMap();
					parameters.put("ID_ORDEN", new Integer(pIdOrden));
					parameters.put("LOGO", URL_SERVLET_LOGO);
					parameters.put("SUBREPORT_DIR", urlsubReporte);
					// parameters.put("pImageAnulada",
					// URL_SERVLET_IMAGE_ANULADA);
					parameters.put("REPORT_LOCALE", new Locale("en", "US"));

					String rutaReporte = urlPath
							+ "resources/report/laboratorio/reporteOrdenExamen.jasper";

					System.out.println("Parametros : " + parameters.toString());

					System.out.println("rutaReporte: " + rutaReporte);

					jasperReport = (JasperReport) JRLoader.loadObject(new URL(
							rutaReporte));

					jasperPrint = JasperFillManager.fillReport(jasperReport,
							parameters, conn);

					// save report to path
					// JasperExportManager.exportReportToPdfFile(jasperPrint,"C:/etiquetas/Etiqueta+"+pCodigoPre+"-"+pNombreElaborado+".pdf");
					response.setContentType("application/pdf");
					response.setCharacterEncoding("UTF-8");

					JasperExportManager.exportReportToPdfStream(jasperPrint,
							servletOutputStream);

					servletOutputStream.flush();
					servletOutputStream.close();

				} else {
					System.out.println("Factura no encontrada x ID: "
							+ pIdOrden);
				}

			} catch (Exception e) {
				// display stack trace in the browser
				e.printStackTrace();
				System.out.println("Error al ingresar RerpoteVentas: "
						+ e.getMessage());
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				response.setContentType("text/plain");
				response.getOutputStream().print(stringWriter.toString());

			}
		} finally {
			try {
				if (!conn.isClosed()) {
					System.out.println("cerrando conexion...");
					conn.close();
				}
			} catch (Exception e) {
				System.out.println("No se pudo cerrar la conexion, Error: "
						+ e.getMessage());
			}
		}

	}

}
