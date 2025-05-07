import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        String url_str = "https://v6.exchangerate-api.com/v6/8d4fc774a4d4913fd3ce47f6/latest/USD";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url_str))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(response.body());
        JsonObject jsonobj = root.getAsJsonObject();

        JsonObject rates = jsonobj.getAsJsonObject("conversion_rates");

        Scanner keyboardIn = new Scanner(System.in);
        String coinFrom = "";
        String coinTo = "";
        boolean program = true;
        double totalAmount = 0.0;
        String finalAmount = "0.0";

        DecimalFormat df = new DecimalFormat("#.00");

        Converter converter = new Converter();

        System.out.println("************************************************");
        System.out.println("Bienvenida/o al Conversor de Monedas.\n");

        while (program == true) {
            System.out.println("Seleccione el tipo de conversión que desea realizar: \n");
            System.out.println(
                    "1) Dólar a Peso mexicano\n" +
                            "2) Peso mexicano a Dólar \n" +
                            "3) Euro a Peso mexicano\n" +
                            "4) Peso mexicano a Euro\n" +
                            "5) Dólar a Euro\n" +
                            "6) Euro a Dólar\n" +
                            "7) Salir"
            );
            int optionSelected = keyboardIn.nextInt();

            switch (optionSelected) {
                case 1:
                    // dólar a peso
                    converter.setFrom("USD");
                    converter.setTo("MXN");
                    coinFrom = "dólares";
                    coinTo = "pesos mexicanos";
                    break;
                case 2:
                    // peso a dólar
                    converter.setFrom("MXN");
                    converter.setTo("USD");
                    coinFrom = "pesos mexicanos";
                    coinTo = "dólares";
                    break;
                case 3:
                    // euro a peso
                    converter.setFrom("EUR");
                    converter.setTo("MXN");
                    coinFrom = "euros";
                    coinTo = "pesos mexicanos";
                    break;
                case 4:
                    // peso a euro
                    converter.setFrom("MXN");
                    converter.setTo("EUR");
                    coinFrom = "pesos mexicanos";
                    coinTo = "euros";
                    break;
                case 5:
                    // dólar a euro
                    converter.setFrom("USD");
                    converter.setTo("EUR");
                    coinFrom = "dólares";
                    coinTo = "euros";
                    break;
                case 6:
                    // euro a dólar
                    converter.setFrom("EUR");
                    converter.setTo("USD");
                    coinFrom = "euros";
                    coinTo = "dólares";
                    break;
                case 7:
                    //salir
                    System.out.println("Gracias por usar el conversor ¡Hasta luego!");
                    program = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    continue;
            }

            if(program) {
                System.out.println("Ingrese el valor que desea convertir: ");
                converter.setAmount(keyboardIn.nextDouble());

                totalAmount = converter.convert(converter.getFrom(), converter.getTo(), converter.getAmount(), rates);
                finalAmount = df.format(totalAmount);
                System.out.println("------" + converter.getAmount() + " " + coinFrom + " corresponde a " + finalAmount + " " + coinTo + "------" + "\n");
                System.out.println("************************************************");
            }
        }
    }
}
