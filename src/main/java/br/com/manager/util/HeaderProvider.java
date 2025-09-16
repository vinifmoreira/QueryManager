package br.com.manager.util;

import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HeaderProvider {

	public static String getToken() {
		return String.format("UDSLongToken %s", AuthenticatorUtil.getToken());
	}

	public static Map<String, String> buildHeaderWithDefaultTokenAndContentType() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("User-Agent", "Application");
		headers.put("Authorization", getToken());
		return headers;
	}

	public static Map<String, String> buildHeaderWithDefaultToken() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", getToken());
		headers.put("User-Agent", "Application");
		return headers;
	}
}
