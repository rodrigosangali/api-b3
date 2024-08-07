package com.sangali.apib3.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1HasGenerator {

    public static String generatorSHA1Hash(String input){
        try {
            // Cria uma instância do MessageDigest para SHA-1
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

            // Calcula o digest da mensagem
            byte[] hashBytes = messageDigest.digest(input.getBytes());

            // Converte o hash para formato hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        }catch (NoSuchAlgorithmException e){
            System.err.println("Algoritmo SHA-1 não encontrado");
            return null;
        }
    }

}
