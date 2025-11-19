package br.com.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RunnedQueryDisplayDto {
    private String runnedQueryId;
    private String queryId;
    private String queryName;
    private String author;
    private String runnedAt;
}

