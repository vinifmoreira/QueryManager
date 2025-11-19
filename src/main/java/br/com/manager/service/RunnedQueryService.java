package br.com.manager.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.manager.model.RunnedQueryInClientEntity;
import br.com.manager.util.InfoUtilRequest;
import br.com.vipautomacao.gerador.web.dto.HttpResponseDto;

public class RunnedQueryService {

	public static List<RunnedQueryInClientEntity> findByClientId(String clientId) throws Exception {
		HttpResponseDto response = InfoUtilRequest.get(String.format("/v1/sql/runned/client/%s", clientId));
		if (response.getCode().equals(200)) {
			return RunnedQueryInClientEntity.fromJsonArray(response.getResponse());
		} else if (response.getCode().equals(404)) {
			return new ArrayList<>();
		} else {
			throw new Exception("Erro ao buscar queries executadas: " + response.getResponse());
		}
	}

	public static Boolean deleteByClient(String clientId) {
		HttpResponseDto response = InfoUtilRequest.delete(String.format("/v1/sql/runned/client/%s", clientId), "", "");
		if (response.getCode().equals(204)) {
			return true;
		}
		return false;
	}

	public static Boolean deleteQueries(Set<String> queryUuids) {
		if (queryUuids == null || queryUuids.isEmpty()) {
			return false;
		}
		
		String queryParams = queryUuids.stream()
				.map(uuid -> {
					try {
						return "queries=" + URLEncoder.encode(uuid, "UTF-8");
					} catch (Exception e) {
						return "queries=" + uuid;
					}
				})
				.collect(Collectors.joining("&"));
		
		HttpResponseDto response = InfoUtilRequest.delete("/v1/sql/runned?" + queryParams, "", "");
		if (response.getCode().equals(204)) {
			return true;
		}
		return false;
	}

	public static Boolean deleteQuery(String uuid) {
		HttpResponseDto response = InfoUtilRequest.delete(String.format("/v1/sql/runned/%s", uuid), "", "");
		if (response.getCode().equals(204)) {
			return true;
		}
		return false;
	}
}

