import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("********************************************");
            System.out.println("Sea bienvenido/a al Conversor de Moneda =]");
            System.out.println("1) Dólar => Peso argentino");
            System.out.println("2) Peso argentino => Dólar");
            System.out.println("3) Dólar => Real brasileño");
            System.out.println("4) Real brasileño => Dólar");
            System.out.println("5) Dólar => Peso colombiano");
            System.out.println("6) Peso colombiano => Dólar");
            System.out.println("7) Salir");
            System.out.println("Elija una opción válida:");
            System.out.println("********************************************");

            opcion = scanner.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                realizarConversion(opcion);
            }
        } while (opcion != 7);

        scanner.close();
    }

    private static void realizarConversion(int opcion) {
        String baseCurrency = "";
        String targetCurrency = "";

        switch (opcion) {
            case 1:
                baseCurrency = "USD";
                targetCurrency = "ARS";
                break;
            case 2:
                baseCurrency = "ARS";
                targetCurrency = "USD";
                break;
            case 3:
                baseCurrency = "USD";
                targetCurrency = "BRL";
                break;
            case 4:
                baseCurrency = "BRL";
                targetCurrency = "USD";
                break;
            case 5:
                baseCurrency = "USD";
                targetCurrency = "COP";
                break;
            case 6:
                baseCurrency = "COP";
                targetCurrency = "USD";
                break;
        }

        try {
            String apiKey = "66895deb15302415e01413c6";  // Coloca tu API key aquí
            String apiUrl = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", apiKey, baseCurrency);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Imprime la respuesta JSON para depuración
            System.out.println("Respuesta de la API: " + responseBody);

            JsonReader reader = new JsonReader(new StringReader(responseBody));
            reader.setLenient(true); // Ajusta el lector a un modo más tolerante

            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            double conversionRate = jsonObject.getAsJsonObject("conversion_rates").get(targetCurrency).getAsDouble();

            System.out.println("Ingrese la cantidad de " + baseCurrency + " a convertir:");
            Scanner scanner = new Scanner(System.in);
            double cantidad = scanner.nextDouble();

            double resultado = cantidad * conversionRate;
            System.out.println(cantidad + " " + baseCurrency + " son " + resultado + " " + targetCurrency);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
