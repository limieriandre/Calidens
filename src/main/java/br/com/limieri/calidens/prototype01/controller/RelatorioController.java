package br.com.limieri.calidens.prototype01.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class RelatorioController {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @RequestMapping("/relatorios")
    private ModelAndView relatorios(){
        ModelAndView mv = new ModelAndView("relatorios");

        return mv;
    }


//    private ModelAndView relatorio1(){
//        ModelAndView mv = new ModelAndView("relatorios");
//
//        return mv;
//    }

    @RequestMapping("/relatorio1")
    public void imprimirRel1(@RequestParam Map<String, Object> parametros, HttpServletResponse response) throws JRException, SQLException, IOException {

        parametros = parametros == null ? parametros = new HashMap<>() : parametros;

        // Pega o arquivo .jasper localizado em resources
        InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/consultas_paciente.jasper");

        // Cria o objeto JaperReport com o Stream do arquivo jasper
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        // Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados, no caso uma conexão ao banco de dados
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource.getConnection());

        // Configura a resposta para o tipo PDF
        response.setContentType("application/pdf");
        // Define que o arquivo pode ser visualizado no navegador e também nome final do arquivo
        // para fazer download do relatório troque 'inline' por 'attachment'
        response.setHeader("Content-Disposition", "inline; filename=consultas.pdf");

        // Faz a exportação do relatório para o HttpServletResponse
        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @RequestMapping("/relatorio2")
    public void imprimirRel2(@RequestParam Map<String, Object> parametros, HttpServletResponse response) throws JRException, SQLException, IOException {

        parametros = parametros == null ? parametros = new HashMap<>() : parametros;

        // Pega o arquivo .jasper localizado em resources
        InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/escolas.jasper");

        // Cria o objeto JaperReport com o Stream do arquivo jasper
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        // Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados, no caso uma conexão ao banco de dados
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource.getConnection());

        // Configura a resposta para o tipo PDF
        response.setContentType("application/pdf");
        // Define que o arquivo pode ser visualizado no navegador e também nome final do arquivo
        // para fazer download do relatório troque 'inline' por 'attachment'
        response.setHeader("Content-Disposition", "inline; filename=escolas.pdf");

        // Faz a exportação do relatório para o HttpServletResponse
        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @RequestMapping("/relatorio3")
    public void imprimirRel3(@RequestParam Map<String, Object> parametros, HttpServletResponse response) throws JRException, SQLException, IOException {

        parametros = parametros == null ? parametros = new HashMap<>() : parametros;

        // Pega o arquivo .jasper localizado em resources
        InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/pacientes_escola.jasper");

        // Cria o objeto JaperReport com o Stream do arquivo jasper
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        // Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados, no caso uma conexão ao banco de dados
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource.getConnection());

        // Configura a resposta para o tipo PDF
        response.setContentType("application/pdf");
        // Define que o arquivo pode ser visualizado no navegador e também nome final do arquivo
        // para fazer download do relatório troque 'inline' por 'attachment'
        response.setHeader("Content-Disposition", "inline; filename=pacientes_escola.pdf");

        // Faz a exportação do relatório para o HttpServletResponse
        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @RequestMapping("/relatorio4")
    public void imprimirRel4(@RequestParam Map<String, Object> parametros, HttpServletResponse response) throws JRException, SQLException, IOException {

        parametros = parametros == null ? parametros = new HashMap<>() : parametros;

        // Pega o arquivo .jasper localizado em resources
        InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/pacientes.jasper");

        // Cria o objeto JaperReport com o Stream do arquivo jasper
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        // Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados, no caso uma conexão ao banco de dados
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource.getConnection());

        // Configura a resposta para o tipo PDF
        response.setContentType("application/pdf");
        // Define que o arquivo pode ser visualizado no navegador e também nome final do arquivo
        // para fazer download do relatório troque 'inline' por 'attachment'
        response.setHeader("Content-Disposition", "inline; filename=pacientes.pdf");

        // Faz a exportação do relatório para o HttpServletResponse
        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }
}
