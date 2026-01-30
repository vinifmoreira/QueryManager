package br.com.manager.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

public class SystemProperties {
    private static final String BASE_URL = "https://vipautomacao.com.br/files/";
    private static final String PROD_FILE_NAME = "application.prod.properties";
    private static final String DEV_FILE_NAME = "application.dev.properties";
    private static final Properties properties = new Properties();
    public static Boolean IS_PROD = true;

    public static void init() {
        properties.clear();
        downloadProperties();

        try (FileInputStream inputStream = new FileInputStream(Paths.get(getConfigPath()).toFile())) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo de propriedades: " + getConfigPath());
            e.printStackTrace();
        }
    }

    public static void downloadProperties() {
        try {
            downloadFile(getConfigUrl(), getConfigPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(String url, String filePath) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Application");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.connect();
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Erro ao baixar o arquivo: " + url);
        }
        InputStream inputStream = connection.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();
        connection.disconnect();
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static Properties getAll() {
        return properties;
    }

    public static String getConfigPath() {
        return getFileName();
    }

    public static String getConfigUrl() {
        return BASE_URL + getFileName();
    }

    public static String getFileName() {
        return IS_PROD ? PROD_FILE_NAME : DEV_FILE_NAME;
    }
}
