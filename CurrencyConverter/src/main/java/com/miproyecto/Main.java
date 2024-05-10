package com.miproyecto;

import java.io.IOException;  // Importa la clase IOException para manejar errores de I/O.
import java.lang.InterruptedException; // Importa la clase InterruptedException para manejar interrupciones.

public class Main {
    public static void main(String[] args) {
        try {
            double rate = CurrencyApi.getExchangeRate("USD", "EUR");
            System.out.println("El tipo de cambio de USD a EUR es: " + rate);
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al recuperar la tasa de cambio: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error en la API: " + e.getMessage());
        }
    }
}
