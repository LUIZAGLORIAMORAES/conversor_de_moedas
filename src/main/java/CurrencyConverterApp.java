import exceptions.ApiException;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class CurrencyConverterApp {
    private static final String[][] options = {
            {"USD", "ARS"}, {"ARS", "USD"},
            {"USD", "BRL"}, {"BRL", "USD"},
            {"USD", "COP"}, {"COP", "USD"},
            {"sair"}
    };

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            try {
                ExchangeRateService exchangeRateService = new ExchangeRateService();
                Scanner scanner = new Scanner(System.in);

                System.out.println("************************************************");
                System.out.println("Seja bem-vindo/a ao Conversor de Moedas");
                menu();
                System.out.println("Escolha uma opção válida: ");
                System.out.println("************************************************");
                int option = scanner.nextInt();

                if (option > 7) {
                    System.out.println("Opção inválida. Por favor, escolha uma opção de 1 a 7.");
                    continue;
                }


                if (option == 7) {
                    exit = true;
                    System.out.println("Obrigado por usar o Conversor de Moedas!");
                    continue;
                }

                scanner.nextLine();
                int newOption = option - 1;
                String currency1 = options[newOption][0];
                String currency2 = options[newOption][1];

                convertCurrency(currency1, currency2, exchangeRateService, scanner);

            } catch (InputMismatchException exception) {
                exception.getStackTrace();
                System.out.println("Opção inválida. Por favor, escolha uma opção de 1 a 7.");
            } catch (ApiException exception) {
                System.out.println(exception.getMessage());
            } catch (Exception exception) {
                System.out.println("Erro inesperado, favor falar com o suporte!");
                exit = true;
            }
        }
    }

    private static void menu() {
        System.out.println("1) Dólar >>> Peso argentino");
        System.out.println("2) Peso argentino >>> Dólar");
        System.out.println("3) Dólar >>> Real brasileiro");
        System.out.println("4) Real brasileiro >>> Dólar");
        System.out.println("5) Dólar >>> Peso colombiano");
        System.out.println("6) Peso colombiano >>> Dólar");
        System.out.println("7) Sair");
    }

    private static void convertCurrency(String currencyFrom, String currencyTo, ExchangeRateService exchangeRateService, Scanner scanner) throws ApiException {
        try {
            System.out.print("Digite o valor em " + currencyFrom + " que deseja converter: ");
            scanner.useLocale(Locale.US);
            double amountFrom = scanner.nextFloat();
            double exchangeRate = exchangeRateService.getExchangeRate(currencyFrom, currencyTo);
            double convertedAmount = amountFrom * exchangeRate;
            System.out.println("Valor " + amountFrom + " (" + currencyFrom + ") corresponde ao valor final de =>>> " + convertedAmount + " (" + currencyTo + ")");
        } catch (IOException e) {
            throw new ApiException("Erro ao obter a taxa de câmbio: " + e.getMessage());
        }
    }
}
