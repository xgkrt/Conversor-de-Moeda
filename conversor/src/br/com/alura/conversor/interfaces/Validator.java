package br.com.alura.conversor.interfaces;
import java.util.List;

public class Validator {
    private final List<String> moedasValidas;

    public Validator(List<String> moedas) {
        this.moedasValidas = moedas;
    }

    public boolean isValidCurrency(String moeda) {
        return moedasValidas.contains(moeda.toUpperCase());
    }

}
