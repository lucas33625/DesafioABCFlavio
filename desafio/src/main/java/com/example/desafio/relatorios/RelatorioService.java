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

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Collections;
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

    public byte[] gerarRelatorioClientes(List<Cliente> clientes) throws JRException, IOException {
        try {
            // Carregar o arquivo JRXML
            InputStream inputStream = getClass().getResourceAsStream("/templates/relatorio_clientes.jrxml");
            if (inputStream == null) {
                throw new FileNotFoundException("Arquivo cliente_detalhe.jrxml não encontrado em /templates/");
            }


            // Compilar o relatório JRXML
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Criar a fonte de dados para o relatório
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(clientes);

            // Obter o diretório das imagens
            String reportDir = Paths.get(getClass().getResource("/templates/").toURI())
                    .toAbsolutePath().toString() + File.separator;

            // Passar o parâmetro REPORT_DIR para o relatório
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_DIR", reportDir);

            // Preencher o relatório com dados e parâmetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Exportar para PDF
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);


            return out.toByteArray();

        } catch (URISyntaxException e) {
            // Exceção para problemas com a URI
            System.err.println("Erro ao resolver caminho do relatório: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Erro ao resolver caminho do relatório", e);

        } catch (FileNotFoundException e) {
            // Caso o arquivo JRXML não seja encontrado
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Arquivo não encontrado", e);

        } catch (JRException e) {
            // Caso ocorra erro na geração do relatório Jasper
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Erro ao gerar relatório", e);

        } catch (Exception e) {
            // Tratamento genérico de exceções
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Erro inesperado", e);
        }
    }



    public byte[] gerarRelatorioDetalheCliente(Cliente cliente) throws JRException, IOException {
        try {
            // Carregar o arquivo JRXML
            InputStream inputStream = getClass().getResourceAsStream("/templates/cliente_detalhe.jrxml");
            if (inputStream == null) {
                throw new FileNotFoundException("Arquivo cliente_detalhe.jrxml não encontrado em /templates/");
            }

            // Compilar o relatório JRXML
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Criar a fonte de dados para o relatório
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(cliente));

            // Obter o diretório das imagens
            String reportDir = Paths.get(getClass().getResource("/templates/").toURI())
                    .toAbsolutePath().toString() + File.separator;

            // Passar o parâmetro REPORT_DIR para o relatório
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_DIR", reportDir);

            // Preencher o relatório com dados e parâmetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Exportar para DOCX
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
            exporter.exportReport();

            return out.toByteArray();

        } catch (URISyntaxException e) {
            // Exceção para problemas com a URI
            System.err.println("Erro ao resolver caminho do relatório: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Erro ao resolver caminho do relatório", e);

        } catch (FileNotFoundException e) {
            // Caso o arquivo JRXML não seja encontrado
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Arquivo não encontrado", e);

        } catch (JRException e) {
            // Caso ocorra erro na geração do relatório Jasper
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Erro ao gerar relatório", e);

        } catch (Exception e) {
            // Tratamento genérico de exceções
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Erro inesperado", e);
        }
    }





}
