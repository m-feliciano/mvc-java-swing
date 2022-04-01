package services;

import com.google.gson.Gson;
import entities.Address;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddressService {

    private AddressService() {}

    private final static String webService = "http://viacep.com.br/ws/";

    public static Address getAddressFromCEP(String cep) throws Exception {
        String urlExternal = webService + cep + "/json";

        try {
            // Create a neat value object to hold the URL
            URL url = new URL(urlExternal);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            String output = "";
            String line;

            while ((line = br.readLine()) != null) {
                output += line;
            }

            output = output.replace("logradouro", "place");
            output = output.replace("localidade", "local");

            Gson gson = new Gson();
            return gson.fromJson(output, Address.class);

        } catch (Exception e) {
            throw new Exception("ERRO: " + e);
        }
    }
}
