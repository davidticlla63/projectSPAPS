package com.teds.spaps.reporte;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

@WebServlet("/ReportFacturaVenta")
public class ReportFacturaVenta extends HttpServlet implements Serializable{

	private static final long serialVersionUID = -4265479773517771142L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream servletOutputStream = response.getOutputStream();
		JasperReport jasperReport;
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx
					.lookup(Conexion.datasourse);
			conn = ds.getConnection();

			Integer id = Integer.parseInt(request.getParameter("id"));
			String  nroNit = request.getParameter("nroNit");
			String  ciudad = request.getParameter("ciudad");
			String  pais = request.getParameter("pais");
			String  empresa = request.getParameter("empresa");
			String  qrtext = request.getParameter("qrtext");
			String  leyenda = request.getParameter("leyenda");

			String urlPath = request.getRequestURL().toString();
			urlPath = urlPath.substring(0, urlPath.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

			String rutaReporte = urlPath+"resources/report/venta/factura/reportFactura.jasper";
			String logo = urlPath+"ServletImageLogo?id="+1+"&type=EMPRESA";
			String qr = urlPath+"codeQR?qrtext="+qrtext;

			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("id",id);
			parameters.put("nroNit", nroNit);
			parameters.put("ciudad", ciudad);
			parameters.put("pais", pais);
			parameters.put("logo", logo);
			parameters.put("empresa", empresa);
			parameters.put("qr", qr);
			parameters.put("leyenda", leyenda);

			jasperReport = (JasperReport)JRLoader.loadObject (new URL(rutaReporte));
			JasperPrint jasperPrint2 = JasperFillManager.fillReport(jasperReport, parameters, conn);
			response.setContentType("application/pdf");// text/html  application/pdf   application/html
			JasperExportManager.exportReportToPdfStream(jasperPrint2,servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());			
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
