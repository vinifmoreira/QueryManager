package br.com.manager.model;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.vipautomacao.gerador.web.parse.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RunnedQueryInClientEntity {
    private String id;
    private Boolean deleted;
    private String queryId;
    private String clientId;
    private LocalDateTime runnedAt;
    private LocalDateTime deletedAt;
    
    public static RunnedQueryInClientEntity fromJson(String json) {
        try {
            return new JsonParser<RunnedQueryInClientEntity>(RunnedQueryInClientEntity.class).getObject(new JSONObject(json));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static List<RunnedQueryInClientEntity> fromJsonArray(String jsonArray) {
        try {
            return new JsonParser<RunnedQueryInClientEntity>(RunnedQueryInClientEntity.class).getList(new JSONArray(jsonArray));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

