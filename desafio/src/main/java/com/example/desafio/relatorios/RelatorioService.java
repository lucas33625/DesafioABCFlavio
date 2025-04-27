package com.example.desafio.relatorios;

import com.example.desafio.dao.AuditoriaDAO;
import com.example.desafio.dto.AuditoriaDTO;
import com.example.desafio.dto.ClienteDTO;
import com.example.desafio.model.Cliente;
import com.example.desafio.model.ClienteAudit;
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

    public byte[] gerarRelatorioClientes(List<ClienteDTO> clientes) throws JRException, IOException {
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

    public byte[] gerarRelatorioDetalheCliente(ClienteDTO cliente) throws JRException, IOException {
        try {
            // Carregar o arquivo JRXML do relatório principal
            InputStream inputStream = getClass().getResourceAsStream("/templates/cliente_detalhe.jrxml");
            if (inputStream == null) {
                throw new FileNotFoundException("Arquivo cliente_detalhe.jrxml não encontrado em /templates/");
            }

            // Compilar sub-relatório em tempo de execução
            InputStream subInputStream = getClass().getResourceAsStream("/templates/cliente_auditorias.jrxml");
            if (subInputStream == null) {
                throw new FileNotFoundException("Arquivo cliente_auditorias.jrxml não encontrado em /templates/");
            }
            JasperReport subreport = JasperCompileManager.compileReport(subInputStream);

            // Obter o diretório para as imagens (opcional, se necessário)
            String reportDir = Paths.get(getClass().getResource("/templates/").toURI()).toAbsolutePath().toString() + File.separator;

            // Buscar auditorias do cliente
            AuditoriaDAO auditoriaDAO = new AuditoriaDAO();
            List<AuditoriaDTO> auditorias = auditoriaDAO.buscarAuditoriasPorCliente(cliente.getId());

            // Validação (debug)
            if (auditorias == null || auditorias.isEmpty()) {
                System.out.println("Nenhuma auditoria encontrada para o cliente.");
            }
            for (AuditoriaDTO audit : auditorias) {
                if (audit.getId() == null || audit.getClienteId() == null) {
                    System.out.println("Erro: id ou clienteId nulos para auditoria " + audit);
                }
            }

            // Criar parâmetros
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_DIR", reportDir);
            parameters.put("AUDITORIAS_DATA_SOURCE", new JRBeanCollectionDataSource(auditorias));
            parameters.put("SUBREPORT", subreport); // Passando o sub-relatório compilado

            // DataSource principal (cliente)
            JRBeanCollectionDataSource clienteDataSource = new JRBeanCollectionDataSource(Collections.singletonList(cliente));

            // Compilar o relatório principal
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Preencher relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, clienteDataSource);

            // Exportar para DOCX
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
            exporter.exportReport();

            return out.toByteArray();

        } catch (URISyntaxException e) {
            throw new IOException("Erro ao resolver caminho do relatório", e);

        } catch (FileNotFoundException e) {
            throw new IOException("Arquivo não encontrado", e);

        } catch (JRException e) {
            throw new IOException("Erro ao gerar relatório", e);

        } catch (Exception e) {
            throw new IOException("Erro inesperado", e);
        }
    }
}
