package br.com.manager.controller;

import java.awt.Component;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

import com.towel.swing.table.TableConfig;

import br.com.manager.model.QueryEntity;
import br.com.manager.model.QueryRequest;
import br.com.manager.service.QueryService;
import br.com.manager.view.QueryManagerView;
import br.com.vipautomacao.gerador.swing.component.view.ViewSearchController;
import br.com.vipautomacao.gerador.swing.model.dto.ViewSearchDto;
import br.com.vipautomacao.gerador.swing.util.SwingUtil;
import br.com.vipautomacao.gerador.time.util.DateUtil;

public class QueryManagerController extends QueryManagerView<QueryEntity> {
	private Component[] enable;
	private boolean isRoot = false;

	public QueryManagerController() {
		super(null);
		start(null, true);
	}

	public QueryManagerController(boolean isRoot) {
		super(null);
		start(null, isRoot);
	}

	private void start(QueryEntity object, Boolean isRoot) {
		this.object = object;
		this.isRoot = isRoot;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				initializing();
			}
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				closeEvent();
			}
		});
	}

	public void initializing() {
		enable = Arrays.asList(txtId, txtQuery, txtVersion, txtCreatedAt, txtAuthor).toArray(new Component[0]);

		setActionListener();
		disableFields();
		showObject();
	}

	private void setActionListener() {
		panelActions.getBtnClose().addActionListener(e -> closeEvent());
		panelActions.getBtnNew().addActionListener(e -> newEvent());
		panelActions.getBtnUpdate().addActionListener(e -> updateEvent());
		panelActions.getBtnDelete().addActionListener(e -> deleteEvent());
		panelActions.getBtnSave().addActionListener(e -> saveEvent());
		panelActions.getBtnCancel().addActionListener(e -> cancelEvent());
		panelActions.getBtnSearch().addActionListener(e -> search());
	}
	
	private void closeEvent() {
		if (isRoot) {
			System.exit(0);
		} else {
			dispose();
		}
	}

	private void search() {
		ViewSearchController<QueryEntity> viewSearch = search(getOwner());
		viewSearch.setVisible(true);
		if (viewSearch.getObject() != null) {
			showObject(viewSearch.getObject());
		}
	}

	private void newEvent() {
		object = null;
		SwingUtil.cleanField(enable);
		enableFields();
		enable[1].requestFocus();
	}

	private void updateEvent() {
		enableFields();
		enable[1].requestFocus();
	}

	private void deleteEvent() {
		int confirmation = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este item?",
				"Confirmação de Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (confirmation == JOptionPane.YES_OPTION) {
			QueryService.delete(txtId.getText());

			JOptionPane.showMessageDialog(null, "Item excluído com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void saveEvent() {
		String validate = SwingUtil.validateObject(enable, txtId);
		if (Objects.nonNull(validate)) {
			JOptionPane.showMessageDialog(this, "\nAtenção! Campo(s) Necessário(s):\n\n" + validate + "\n");
			return;
		}
		if (Objects.isNull(object)) {
			object = new QueryEntity();
		}
		SwingUtil.buildObject(object, enable, txtId);
		try {
			if (Objects.isNull(object.getId())) {
				QueryService.create(build(object));
			} else {
				QueryService.update(build(object));
			}
			showObject(object);
			disableFields();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	private QueryRequest build(QueryEntity entity) {
		QueryRequest object = new QueryRequest();
		object.setId(entity.getId() != null ? Integer.valueOf(object.getId()) : null);
		object.setAuthor(object.getAuthor());
		object.setQuery(object.getQuery());
		object.setVersion(object.getVersion());
		return object;
	}

	private void cancelEvent() {
		object = null;
		disableFields();
		showObject();
	}

	private void enableFields() {
		SwingUtil.enable(enable, txtId);
		panelActions.enableFields();
	}

	private void disableFields() {
		SwingUtil.disable(enable);
		panelActions.disableFields();
	}

	private void showObject() {
		if (Objects.nonNull(object)) {
			showObject(object);
		} else {
			try {
				object = QueryService.findLast();
				showObject(object);

			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erro ao consultar a última Query");
			}
		}
	}

	private void showObject(QueryEntity object) {
		this.object = object;
		SwingUtil.showFields(object, enable);
		txtCreatedAt.setText(DateUtil.dateToHyphenYYYYMMDD(DateUtil.getDate(object.getCreatedAt())));
	}

	public static ViewSearchController<QueryEntity> search(Window owner) {
		return search(owner, "");
	}

	public static ViewSearchController<QueryEntity> search(Window owner, String search) {
		List<TableConfig> listTableConfig = new ArrayList<>();
		listTableConfig.add(new TableConfig("id", "Código", 220));
		listTableConfig.add(new TableConfig("query", "SQL", 700));
		listTableConfig.add(new TableConfig("author", "Autor", 70));
		listTableConfig.add(new TableConfig("version", "Versão", 70));
		listTableConfig.add(new TableConfig("createdAt", "Data", 70));

		ViewSearchDto<QueryEntity> dto = new ViewSearchDto<QueryEntity>();
		dto.setClazz(QueryEntity.class);
		dto.setFilters(Arrays.asList("query"));
		dto.setAdvancedFilters(Arrays.asList("id", "query", "author", "version"));
		dto.setListTableConfig(listTableConfig);
		dto.setWindowInsert(new QueryManagerController(false));
		dto.setSearch(search);

		ViewSearchController<QueryEntity> viewSearch = new ViewSearchController<>(owner, dto);
		viewSearch.setAdvancedSearch(true);
		viewSearch.setCustomSearch(QueryService.class);
		viewSearch.setCustomFunctionName("findByTerm");
		viewSearch.setUseFindCount(false);
		viewSearch.setTitle("Pesquisa de SQLs");
		return viewSearch;
	}
}