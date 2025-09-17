package br.com.manager.controller;

import java.awt.Component;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

import br.com.manager.model.QueryEntity;
import br.com.manager.model.QueryRequest;
import br.com.manager.service.QueryService;
import br.com.manager.view.QueryManagerView;
import br.com.vipautomacao.gerador.swing.component.view.ViewSearchController;
import br.com.vipautomacao.gerador.swing.model.dto.ViewSearchDto;
import br.com.vipautomacao.gerador.swing.util.ComponentFocusUtil;
import br.com.vipautomacao.gerador.swing.util.SwingUtil;

@SuppressWarnings("serial")
public class QueryManagerController extends QueryManagerView<QueryEntity> {
	private Component[] enable;
	public QueryManagerController(QueryEntity object) {
		super(null);
		start(object);
	}

	private void start(QueryEntity object) {
		this.object = object;
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				initializing();
			}
		});
	}

	public void initializing() {
		enable = Arrays.asList(txtId, txtQuery, txtVersion, txtCreatedAt, txtAuthor).toArray(new Component[0]);

		setActionListener();
		setFocus();
		disableFields();
		showObject();
	}

	private void setFocus() {
		ComponentFocusUtil mf = new ComponentFocusUtil(this);
		mf.changeFocus(enable);
	}

	private void setActionListener() {
		panelActions.getBtnClose().addActionListener(e -> dispose());
		panelActions.getBtnNew().addActionListener(e -> newEvent());
		panelActions.getBtnUpdate().addActionListener(e -> updateEvent());
		panelActions.getBtnDelete().addActionListener(e -> deleteEvent());
		panelActions.getBtnSave().addActionListener(e -> saveEvent());
		panelActions.getBtnCancel().addActionListener(e -> cancelEvent());
		panelActions.getBtnSearch().addActionListener(e -> search());
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
				JOptionPane.showMessageDialog(this, "Erro ao consultar o última Query");
			}
		}
	}

	private void showObject(QueryEntity object) {
		this.object = object;
		SwingUtil.showFields(object, enable);
	}

	public static ViewSearchController<QueryEntity> search(Window owner) {
		return search(owner, "");
	}

	public static ViewSearchController<QueryEntity> search(Window owner, String search) {
		List<QueryEntity> listTableConfig = new ArrayList<>();
//        listTableConfig.add(new QueryEntity("codigo", "Código", 100));
//        listTableConfig.add(new QueryEntity("nome", "Nome", 610));
//        listTableConfig.add(new QueryEntity("comissao", "Comissão", 100));

		ViewSearchDto dto = new ViewSearchDto();
		dto.setClazz(QueryEntity.class);
		dto.setFilters(Arrays.asList("nome"));
		dto.setAdvancedFilters(Arrays.asList("codigo", "nome", "comissao"));
		dto.setListTableConfig(listTableConfig);
		dto.setWindowInsert(new QueryManagerController(null));
		dto.setSearch(search);

		ViewSearchController<QueryEntity> viewSearch = new ViewSearchController<>(owner, dto);
		viewSearch.setAdvancedSearch(true);
		viewSearch.setCustomSearch(QueryEntity.class);
		viewSearch.setTitle("Pesquisa de Grupo");
		return viewSearch;
	}
}
