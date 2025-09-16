package br.com.manager.view;

import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.vipautomacao.gerador.swing.component.DialogForm;
import br.com.vipautomacao.gerador.swing.component.fields.TextField;
import br.com.vipautomacao.gerador.swing.component.panel.PanelActions;
import br.com.vipautomacao.gerador.swing.component.panel.PanelTitle;
import br.com.vipautomacao.gerador.text.formatter.TextDocument;

public class QueryManagerView<E> extends DialogForm<E> {
	private JPanel jContentPane = null;
	private PanelTitle paneltitle;
	protected JPanel panelContent;
	protected PanelActions panelActions;
	protected TextField<E> txtCodigo;
	private JLabel lbCodigo;
	protected TextField<E> txtNome;
	private JLabel lbNome;
	protected TextField<E> txtUf;
	private JLabel lbUf;
	protected TextField<E> txtIbge;
	private JLabel lbIbge;
	
	public QueryManagerView(Window owner) {
		super(owner);
		initialize();
	}
	
	private void initialize() {
        setBounds(100, 100, 798, 275);
		setTitle("Controle de SQL");
		setModal(true);
		setResizable(false);
		setLocationRelativeTo(getOwner());
		setContentPane(getJContentPane());
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getPanelTitle());
			jContentPane.add(getPanelContent());
			jContentPane.add(getPanelActions());
		}
		return jContentPane;
	}

	private PanelTitle getPanelTitle() {
		if (paneltitle == null) {
			paneltitle = new PanelTitle("Controle de SQL");
			paneltitle.setBounds(0, 0, 802, 52);
		}
		return paneltitle;
	}

	private JPanel getPanelContent() {
		if (panelContent == null) {
			panelContent = new JPanel();
			panelContent.setBounds(0, 53, 802, 138);
			panelContent.setLayout(null);
			panelContent.add(getTxtCodigo());
			panelContent.add(getLbCodigo());
			panelContent.add(getTxtNome());
			panelContent.add(getLbNome());
			panelContent.add(getTxtUf());
			panelContent.add(getLbUf());
			panelContent.add(getTxtIbge());
			panelContent.add(getLbIbge());
		}
		return panelContent;
	}

	public PanelActions getPanelActions() {
		if (panelActions == null) {
			panelActions = new PanelActions();
			panelActions.setBounds(0, 192, 802, 53);
		}
		return panelActions;
	}
	
	private TextField<E> getTxtCodigo() {
		if (txtCodigo == null) {
			txtCodigo = new TextField<>("codigo");
			txtCodigo.setColumns(10);
			txtCodigo.setBounds(10, 36, 96, 30);
		}
		return txtCodigo;
	}

	private JLabel getLbCodigo() {
		if (lbCodigo == null) {
			lbCodigo = new JLabel("Código:");
			lbCodigo.setBounds(10, 11, 96, 20);
		}
		return lbCodigo;
	}

	private TextField<E> getTxtNome() {
		if (txtNome == null) {
			txtNome = new TextField<>("nome", true);
			txtNome.setBounds(116, 36, 678, 30);
			txtNome.setText(txtNome.getText().toUpperCase());
			txtNome.setDocument(new TextDocument(20));
		}
		return txtNome;
	}

	private JLabel getLbNome() {
		if (lbNome == null) {
			lbNome = new JLabel("Nome:");
			lbNome.setBounds(116, 11, 96, 20);
		}
		return lbNome;
	}

	private TextField<E> getTxtUf() {
		if (txtUf == null) {
			txtUf = new TextField<>("uf", true);
			txtUf.setBounds(10, 101, 96, 30);
			txtUf.setText(txtUf.getText().toUpperCase());
			txtUf.setDocument(new TextDocument(2));
		}
		return txtUf;
	}

	private JLabel getLbUf() {
		if (lbUf == null) {
			lbUf = new JLabel("UF:");
			lbUf.setBounds(10, 76, 96, 20);
		}
		return lbUf;
	}

	private TextField<E> getTxtIbge() {
		if (txtIbge == null) {
			txtIbge = new TextField<>("ibge", true);
			txtIbge.setBounds(116, 101, 96, 30);
			txtIbge.setDocument(new TextDocument(3));
		}
		return txtIbge;
	}

	private JLabel getLbIbge() {
		if (lbIbge == null) {
			lbIbge = new JLabel("IBGE:");
			lbIbge.setBounds(116, 76, 96, 20);
		}
		return lbIbge;
	}
	
}
