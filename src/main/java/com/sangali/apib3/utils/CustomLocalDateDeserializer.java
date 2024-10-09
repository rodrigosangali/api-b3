package com.sangali.apib3.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String dateStr = jsonParser.getText();
        //"Date": "06/20/2024 as of 06/19/2024",
        // Extrai a primeira parte da string até o primeiro espaço
        String[] parts = dateStr.split(" ");

        if (parts.length > 0) {
            String datePart = parts[0]; // Pega a primeira parte da string
            try {
                // Define o formato da data
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                return LocalDate.parse(datePart, formatter);
            } catch (DateTimeParseException e) {
                throw new IOException("Falha ao desserializar a data: " + dateStr, e);
            }
        }
        throw new IOException("Formato de data inválido: " + dateStr);
    }
}
