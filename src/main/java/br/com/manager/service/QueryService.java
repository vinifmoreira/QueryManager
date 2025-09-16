package br.com.manager.service;

import java.util.List;

import br.com.manager.model.QueryEntity;
import br.com.manager.model.QueryRequest;
import br.com.manager.util.InfoUtilRequest;
import br.com.vipautomacao.gerador.web.dto.HttpResponseDto;

public class QueryService {

	public static List<QueryEntity> findAll() throws Exception {
		HttpResponseDto response = InfoUtilRequest.get("/v1/sql/query");
		if (response.getCode().equals(200)) {
			return QueryEntity.fromJsonArray(response.getResponse());
		} else {
			throw new Exception("Queries not found");
		}
	}

	public static QueryEntity findLast() throws Exception {
		HttpResponseDto response = InfoUtilRequest.get("/v1/sql/query/last");
		if (response.getCode().equals(200)) {
			return QueryEntity.fromJson(response.getResponse());
		} else {
			throw new Exception("Query not found");
		}
	}

	public static List<QueryEntity> findByTerm(String term) throws Exception {
		HttpResponseDto response = InfoUtilRequest.get(String.format("/v1/sql/query/search?term=%s", term));
		if (response.getCode().equals(200)) {
			return QueryEntity.fromJsonArray(response.getResponse());
		} else {
			throw new Exception("Query not found");
		}
	}

	public static QueryEntity findById(String id) throws Exception {
		HttpResponseDto response = InfoUtilRequest.get(String.format("/v1/sql/query/%s", id));
		if (response.getCode().equals(200)) {
			return QueryEntity.fromJson(response.getResponse());
		} else {
			throw new Exception("Query not found");
		}
	}
	
	public static QueryEntity create(QueryRequest requestBody) throws Exception {
		HttpResponseDto response = InfoUtilRequest.post("/v1/sql/query", "", requestBody.toJson());
		if (response.getCode().equals(200)) {
			return QueryEntity.fromJson(response.getResponse());
		} else {
			throw new Exception("Fail creating Query");
		}
	}
	
	public static Boolean update(String id, QueryRequest requestBody) {
		HttpResponseDto response = InfoUtilRequest.put(String.format("/v1/sql/query/%s", id), "", requestBody.toJson());
		if (response.getCode().equals(200)) {
			return true;
		}
		return false;
	}
	
	public static Boolean delete(String id) {
		HttpResponseDto response = InfoUtilRequest.delete(String.format("/v1/sql/query/%s", id), "", "");
		if (response.getCode().equals(201)) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(findLast().getQuery());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
