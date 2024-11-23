package br.com.alura.conversor;

import br.com.alura.conversor.connection.ApiImport;
import br.com.alura.conversor.converter.Converter;
import br.com.alura.conversor.interfaces.ConsoleInterface;

public class Main {
    public static void main(String[] args) {
        ApiImport apiService = new ApiImport();
        Converter converter = new Converter();

        ConsoleInterface consoleInterface = new ConsoleInterface(apiService, converter);

        consoleInterface.start();
    }
}
