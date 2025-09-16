package br.com.manager.model;

import java.time.LocalDate;
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
public class QueryEntity {
    private String id;
    private Boolean deleted;
    private String query;
    private String author;
    private String version;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;
    
    public static QueryEntity fromJson(String json) {
        try {
            return new JsonParser<QueryEntity>(QueryEntity.class).getObject(new JSONObject(json));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static List<QueryEntity> fromJsonArray(String jsonArray) {
        try {
            return new JsonParser<QueryEntity>(QueryEntity.class).getList(new JSONArray(jsonArray));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
