package br.com.manager.util;

import br.com.vipautomacao.gerador.web.dto.HttpResponseDto;
import br.com.vipautomacao.gerador.web.util.HttpRequest;

public class InfoUtilRequest {
	public static String apiUrl;

	static {
		try {
			String URL = SystemProperties.get("api.vip.infoutil");
			apiUrl = URL;
		} catch (Exception e) {
			System.out.println("Erro ao carregar a propriedade do host api.vip.infoutil");
			e.printStackTrace();
		}
	}

	public static String getUrl() {
		return apiUrl;
	}

	public static HttpResponseDto get(String path) {
		HttpResponseDto retorno = HttpRequest.get(apiUrl, path,
				HeaderProvider.buildHeaderWithDefaultTokenAndContentType());
		if (retorno.getCode() == 401) {
			AuthenticatorUtil.refreshToken();
			retorno = HttpRequest.get(apiUrl, path, HeaderProvider.buildHeaderWithDefaultTokenAndContentType());
		}
		return retorno;
	}

	public static HttpResponseDto post(String endpoint, String prefixo, String data) {
		HttpResponseDto retorno = HttpRequest.post(String.format(apiUrl, prefixo), endpoint,
				HeaderProvider.buildHeaderWithDefaultTokenAndContentType(), data);
		if (retorno.getCode() == 401) {
			AuthenticatorUtil.refreshToken();
			retorno = HttpRequest.post(String.format(apiUrl, prefixo), endpoint,
					HeaderProvider.buildHeaderWithDefaultTokenAndContentType(), data);
		}
		return retorno;
	}
	
	public static HttpResponseDto put(String endpoint, String prefixo, String data) {
		HttpResponseDto retorno = HttpRequest.put(String.format(apiUrl, prefixo), endpoint,
				HeaderProvider.buildHeaderWithDefaultTokenAndContentType(), data);
		if (retorno.getCode() == 401) {
			AuthenticatorUtil.refreshToken();
			retorno = HttpRequest.put(String.format(apiUrl, prefixo), endpoint,
					HeaderProvider.buildHeaderWithDefaultTokenAndContentType(), data);
		}
		return retorno;
	}

	public static HttpResponseDto delete(String endpoint, String prefixo, String data) {
		HttpResponseDto retorno = HttpRequest.remove(String.format(apiUrl, prefixo), endpoint,
				HeaderProvider.buildHeaderWithDefaultTokenAndContentType(), data);
		if (retorno.getCode() == 401) {
			AuthenticatorUtil.refreshToken();
			retorno = HttpRequest.remove(String.format(apiUrl, prefixo), endpoint,
					HeaderProvider.buildHeaderWithDefaultTokenAndContentType(), data);
		}
		return retorno;
	}

}
