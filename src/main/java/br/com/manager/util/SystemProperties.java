package br.com.manager.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemProperties {
	private static final String CONFIG_PATH = "application.properties";
	private static final Properties PROPERTIES = new Properties();

	static {
		try (InputStream inputStream = SystemProperties.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {

			if (inputStream == null) {
				throw new IOException("Arquivo de propriedades não encontrado no classpath: " + CONFIG_PATH);
			}

			PROPERTIES.load(inputStream);
		} catch (IOException e) {
			System.err.println("Erro ao carregar o arquivo de propriedades: " + CONFIG_PATH);
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return PROPERTIES.getProperty(key);
	}

	public static Properties getAll() {
		return PROPERTIES;
	}
}
