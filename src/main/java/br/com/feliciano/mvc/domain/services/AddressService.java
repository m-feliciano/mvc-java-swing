package br.com.feliciano.mvc.domain.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.mchange.util.AssertException;

import br.com.feliciano.mvc.domain.entities.Address;

public class AddressService {

    private AddressService() {
    	throw new AssertException("This class must not be instantiated.");
    }

    private static final String webService = "http://viacep.com.br/ws/";

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
            throw new RuntimeException("ERRO: " + e);
        }
    }
}
