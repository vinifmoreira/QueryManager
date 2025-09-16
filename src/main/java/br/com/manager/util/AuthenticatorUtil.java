package br.com.manager.util;

import java.util.HashMap;

import org.json.JSONObject;

import br.com.vipautomacao.gerador.web.util.HttpRequest;

public class AuthenticatorUtil {
	private static String apiHost;
	private static String token;
	private static Long generatedAt;
	private static Long lifeTime;

	static {
		try {
			apiHost = SystemProperties.get("api.vip.auth");
			lifeTime = Long.valueOf(SystemProperties.get("api.vip.auth.token.lifetime"));
			refreshToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getToken() {
		if (token == null || generatedAt == null || Math.abs(System.currentTimeMillis() - generatedAt) > lifeTime) {
			refreshToken();
		}
		return token;
	}

	public static void refreshToken() {
		try {
			JSONObject requestBody = new JSONObject();
			requestBody.put("username", "desenvolvimento@vipautomacao.com.br");
			requestBody.put("password", "devAutomacao321");
			JSONObject response = new JSONObject(HttpRequest.post(apiHost,
					"/v1/auth?applicationId=8a888fb6-430a-44a3-a35f-9b51965c0076", new HashMap<String, String>() {
						{
							put("Content-Type", "application/json");
						}
					}, requestBody.toString()).getResponse());
			token = response.getString("token");
			generatedAt = System.currentTimeMillis();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
