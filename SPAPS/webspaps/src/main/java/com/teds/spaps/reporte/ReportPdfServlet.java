package com.teds.spaps.reporte;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
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

import com.teds.spaps.util.Conexion;

@WebServlet("/ReportPdfServlet")
public class ReportPdfServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6864764769799543284L;


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

				Map<String, Object> parameters = (Map<String, Object>) request
						.getSession().getAttribute("parameter");
				String path = (String) request.getSession()
						.getAttribute("path");

				System.out.println("Parametros : " + parameters.toString());
				System.out.println("rutaReporte: " + path);
				
				jasperReport = (JasperReport) JRLoader.loadObject(new URL(
						path));

				jasperPrint = JasperFillManager.fillReport(jasperReport,
						parameters, conn);
				
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setContentType("application/pdf");
				response.setCharacterEncoding("UTF-8");

				JasperExportManager.exportReportToPdfStream(jasperPrint,
						servletOutputStream);

				servletOutputStream.flush();
				servletOutputStream.close();

			} catch (Exception e) {
				// display stack trace in the browser
				e.printStackTrace();
				System.out.println("Error al ingresar Reportes: "
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
					request.getSession().setAttribute("parameter", "");
					request.getSession().setAttribute("path", "");
				}
			} catch (Exception e) {
				System.out.println("No se pudo cerrar la conexion, Error: "
						+ e.getMessage());
			}
		}

	}

}
