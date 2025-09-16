package br.com.manager.controller;

import br.com.manager.model.QueryEntity;
import br.com.manager.view.QueryManagerView;

public class QueryManagerController extends QueryManagerView<QueryEntity> {
	private QueryEntity object;
	
	public QueryManagerController() {
		super(null);
	}

}
