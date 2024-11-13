package com.cryptoetl;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.azure.storage.blob.BlobClientBuilder;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CryptoETL {
    private static final Logger logger = LoggerFactory.getLogger(CryptoETL.class); // Logger

    public static void main(String[] args) {
        // Load environment variables
        Dotenv dotenv = Dotenv.load();
        String connectionString = dotenv.get("AZURE_CONNECTION_STRING");
        String containerName = dotenv.get("AZURE_CONTAINER_NAME");
        String apiKey = dotenv.get("COINGECKO_API_KEY");

        String url = "https://api.coingecko.com/api/v3/coins/markets";

        // Get the current date to use in the file name
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String filePath = Paths.get("data", "CryptoReport_" + dateFormat + ".csv").toString();

        HttpResponse<String> response = Unirest.get(url)
                .queryString("vs_currency", "usd")
                .queryString("ids", "bitcoin,ethereum,litecoin")
                .header("accept", "application/json")  // Add headers
                .header("x-cg-demo-api-key", apiKey)
                .asString();

        if (response.getStatus() == 200) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser.parse(response.getBody());

                // Write the CSV headers, including the new column for creation timestamp
                writer.println("Name;Current Price;24h Change;Creation Date");

                // Get the current timestamp for each record
                String creationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                // Loop through each object in the JSONArray
                for (Object obj : jsonArray) {
                    JSONObject crypto = (JSONObject) obj;

                    // Extract the data you're interested in
                    String name = (String) crypto.get("name");

                    // Handle the price, which could be Long or Double
                    Number priceNumber = (Number) crypto.get("current_price");
                    double price = priceNumber.doubleValue(); // Convert to double

                    // Handle the change in the last 24 hours, which could also be Long or Double
                    Number change24hNumber = (Number) crypto.get("price_change_percentage_24h");
                    double change24h = change24hNumber.doubleValue(); // Convert to double

                    // Display the cryptocurrency information
                    System.out.println("Cryptocurrency: " + name);
                    System.out.println("Current Price: $" + price);
                    System.out.println("24h Change: " + change24h + "%");
                    System.out.println("----------------------------");

                    // Write the data to the CSV file, including the creation date
                    writer.println(name + ";" + price + ";" + change24h + ";" + creationDate);
                }

                System.out.println("Data has been successfully saved to the CSV file located at: " + filePath);

                // Call the method to upload the file to Azure Blob Storage
                uploadToAzure(filePath, connectionString, containerName);

            } catch (Exception e) {
                logger.error("Error parsing response or writing CSV", e); // Using logging instead of printStackTrace
            }
        } else {
            System.out.println("Error: Could not get a valid response.");
        }
    }

    // Method to upload the CSV file to Azure Blob Storage
    private static void uploadToAzure(String filePath, String connectionString, String containerName) {
        String blobName = Paths.get(filePath).getFileName().toString();

        try {
            new BlobClientBuilder()
                    .connectionString(connectionString)
                    .containerName(containerName)
                    .blobName(blobName)
                    .buildClient()
                    .uploadFromFile(filePath, true); // 'true' to overwrite if the blob already exists
            System.out.println("File uploaded to Azure Blob Storage successfully.");
        } catch (Exception e) {
            logger.error("Error uploading file to Azure Blob Storage", e);
        }
    }
}
