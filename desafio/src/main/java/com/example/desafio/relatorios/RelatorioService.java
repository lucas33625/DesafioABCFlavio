package com.example.desafio.relatorios;

import com.example.desafio.model.Cliente;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleDocxExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {

//    public void gerarRelatorioClientes(List<?> clientes) throws Exception {
//
//        File pastaRelatorios = new File("relatorios");
//        if (!pastaRelatorios.exists()) {
//            pastaRelatorios.mkdirs();
//        }
//
//        InputStream templateStream = getClass().getResourceAsStream("/templates/relatorio_clientes.jrxml");
//        JasperReport report = JasperCompileManager.compileReport(templateStream);
//
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(clientes);
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("nomeRelatorio", "Relatório de Clientes");
//
//        JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
//
//        JasperExportManager.exportReportToPdfFile(print, "relatorios/relatorio_clientes.pdf");
//
//        JRDocxExporter exporter = new JRDocxExporter();
//        exporter.setExporterInput(new SimpleExporterInput(print));
//        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("relatorios/clientes.docx")));
//
//        exporter.setConfiguration(new SimpleDocxExporterConfiguration());
//        exporter.exportReport();
//    }
//
//    public void gerarRelatorioDetalheCliente(Object cliente) throws Exception {
//
//        File pastaRelatorios = new File("relatorios");
//        if (!pastaRelatorios.exists()) {
//            pastaRelatorios.mkdirs();
//        }
//
//        InputStream templateStream = getClass().getResourceAsStream("/templates/cliente_detalhe.jasper");
//        JasperReport report = (JasperReport) JRLoader.loadObject(templateStream);
//
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(java.util.Arrays.asList(cliente));
//        Map<String, Object> parametros = new HashMap<>();
//        parametros.put("nomeRelatorio", "Detalhe do Cliente");
//
//        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, dataSource);
//
//        File outputFile = new File("relatorios/detalhe_cliente.docx");
//        JRDocxExporter exporter = new JRDocxExporter();
//        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
//        exporter.exportReport();
//    }

    public byte[] gerarRelatorioClientes(List<Cliente> clientes) throws JRException {
        InputStream inputStream = getClass().getResourceAsStream("/templates/relatorio_clientes.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);


        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(clientes);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] gerarRelatorioDetalheCliente(Cliente cliente) throws JRException, IOException {
        InputStream inputStream = getClass().getResourceAsStream("/templates/cliente_detalhe.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);


        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(java.util.Arrays.asList(cliente));
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
        exporter.exportReport();

        return out.toByteArray();
    }


}
