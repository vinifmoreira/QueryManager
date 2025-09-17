package br.com.manager.model;

import org.json.JSONObject;

import br.com.vipautomacao.gerador.web.parse.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRequest {
	private Integer id;
	private String query;
	private String author;
	private String version;

	public String toJson() {
		return new JSONObject(new JsonParser<QueryRequest>(QueryRequest.class).toJson(this)).toString();
	}
}
