package com.sangali.apib3.service;

import com.sangali.apib3.model.LinhaExcel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RealExcelFileService {

    public List<LinhaExcel> lerExcelProventosRecebidos() throws IOException {

        File diretorio = new File("D:\\WorkspaceJava\\api-b3\\proventos\\recebidos");
        File[] arquivos = diretorio.listFiles();

        List<LinhaExcel> linhaExcels = new ArrayList<>();
        // Para cada arquivos excel na pasta
        Arrays.stream(arquivos).forEach(arquivo -> {


            try {
                FileInputStream file = new FileInputStream(arquivo);
                Workbook workbook = WorkbookFactory.create(file);
                Sheet sheet = workbook.getSheetAt(0);

                Iterator<Row> rowIterator = sheet.rowIterator();

                // ignorar o header do excel ou a primeira linha
                rowIterator.next();

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    LinhaExcel linhaExcel = new LinhaExcel();
                    // Caso encontre uma linha em branco pula
                    if (Objects.equals(row.getCell(1).getStringCellValue(), "")) continue;

                    linhaExcel.setDataOperacao(LocalDate.parse(row.getCell(1).getStringCellValue(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    linhaExcel.setTipoMovimentacao(row.getCell(2).getStringCellValue());
                    linhaExcel.setProduto(row.getCell(0).getStringCellValue());
                    linhaExcel.setInstituicao(row.getCell(3).getStringCellValue());
                    try{
                        linhaExcel.setQuantidade(Integer.parseInt(row.getCell(4).getStringCellValue()));
                    }catch (Exception e){
                        System.out.println("ERRO: zerado preco quantidade");
                        System.out.println(e);
                        linhaExcel.setQuantidade(0);
                    }

                    try {
                        linhaExcel.setPrecoUnitario(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
                    }catch (Exception e){
                        System.out.println("ERRO: zerado preco unitario");
                        System.out.println(e);
                        linhaExcel.setPrecoUnitario(BigDecimal.valueOf(0));
                    }

                    linhaExcel.setValorOperacao(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));

                    linhaExcels.add(linhaExcel);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        return linhaExcels;
    }
}
