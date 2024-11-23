package br.com.alura.conversor.interfaces;

import br.com.alura.conversor.connection.ApiImport;
import br.com.alura.conversor.connection.ResponseRates;
import br.com.alura.conversor.converter.Converter;

import java.util.*;

public class ConsoleInterface {
    private final ApiImport apiService;
    private final Converter converter;
    private ResponseRates responseRates;
    private final Validator validator;
    private String baseCurrency;

    public void moedasDisponiveis() {
        System.out.println("""
                *********************************
                    
                    Moedas disponíveis para conversão:
                    
                    USD - Dólar Americano
                    EUR - Euro
                    GBP - Libra Esterlina
                    BRL - Real Brasileiro
                    ARS - Peso Argentino
                    JPY - Iene Japonês
                    CAD - Dólar Canadense
                    AUD - Dólar Australiano
                    CHF - Franco Suíço
                    CNY - Yuan/Renminbi Chinês
                *********************************
                """);
    }

    public ConsoleInterface(ApiImport apiService, Converter converter) {
        this.apiService = apiService;
        this.converter = converter;

        List<String> moedas = Arrays.asList(
                "USD", "EUR", "GBP", "BRL", "ARS", "JPY", "CAD", "AUD", "CHF", "CNY"
        );
        this.validator = new Validator(moedas);
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        try {
            boolean moedaValida = false;

            while (!moedaValida) {
                moedasDisponiveis();
                System.out.print("Digite a moeda de origem (ex: USD): ");
                baseCurrency = sc.next().toUpperCase();

                if (!validator.isValidCurrency(baseCurrency)) {
                    System.out.println("Moeda não suportada! ");
                    continue;
                }
                moedaValida = true;
                responseRates = apiService.getExchangeRates(baseCurrency);
                System.out.println("Taxas de câmbio obtidas com sucesso!");
            }


            while (true) {
                System.out.println("""
                        \n********************************************
                            MENU DE CONVERSÃO
                        1. Converter moeda.
                        2. Mostrar moedas disponiveis.
                        3. Trocar moeda base.
                        0. Sair
                        ********************************************                            
                        """);
                int opcao = sc.nextInt();

                switch (opcao) {
                    case 0: System.out.println("Encerrando o programa..."); return;
                    case 1: handleConversion(sc); break;
                    case 2: moedasDisponiveis(); break;
                    case 3: trocarMoedaBase(sc); break;
                    default: System.out.println("Opção inválida! Tente novamente.");
                }

            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    private void handleConversion(Scanner scanner) {
        System.out.println("Moeda de origem: " + baseCurrency);

        System.out.print("Digite a moeda de destino (ex: BRL): ");
        String toCurrency = scanner.next().toUpperCase();

        if (!validator.isValidCurrency(toCurrency)) {
            System.out.println("Moeda não suportada! ");
            return;
        }
        System.out.print("Digite o valor a ser convertido: ");
        double amount = scanner.nextDouble();

        try {
            double result = converter.convert(amount, baseCurrency, toCurrency, responseRates.conversion_rates());
            System.out.printf("Valor convertido: %.2f %s%n", result, toCurrency);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void trocarMoedaBase(Scanner scanner) {
        moedasDisponiveis();
        System.out.println("Digite a moeda base: ");
        String novaMoeda = scanner.next().toUpperCase();

        if (!validator.isValidCurrency(baseCurrency)) {
            System.out.println("Moeda não suportada! ");
            return;
        }

        baseCurrency = novaMoeda;
        responseRates = apiService.getExchangeRates(baseCurrency);
        System.out.println("Moeda base alterada para: " + baseCurrency);
    }
}
