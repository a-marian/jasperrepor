package com.fiserv.poc.report.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fiserv.poc.report.model.Transaction;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JasperReportController {

    private List<Transaction> getTransactions(){
        Transaction transaction1 = Transaction.builder().transactionId("12343").transactionDate(new Date())
            .cardNumber("23423-23423").merchantId("2312123").build();
        Transaction transaction2 = Transaction.builder().transactionId("12343").transactionDate(new Date())
            .cardNumber("23423-23423").merchantId("2312123").build();
        Transaction transaction3 = Transaction.builder().transactionId("12343").transactionDate(new Date())
            .cardNumber("23423-23423").merchantId("2312123").build();
        Transaction transaction4 = Transaction.builder().transactionId("12343").transactionDate(new Date())
            .cardNumber("23423-23423").merchantId("2312123").build();
        Transaction transaction5 = Transaction.builder().transactionId("12343").transactionDate(new Date())
            .cardNumber("23423-23423").merchantId("2312123").build();

        return List.of(transaction1,transaction2,transaction3,transaction4,transaction5);
    }


    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return exportReport(format);
    }


    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\dummyreports";
        List<Transaction> transactions = getTransactions();
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:transaction.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transactions);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Marian");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\transactions.pdf");

        return "report generated in path : " + path;
    }
}


