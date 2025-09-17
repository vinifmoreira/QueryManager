package br.com.manager.view;

import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.vipautomacao.gerador.swing.component.DialogForm;
import br.com.vipautomacao.gerador.swing.component.fields.TextField;
import br.com.vipautomacao.gerador.swing.component.panel.PanelActions;
import br.com.vipautomacao.gerador.swing.component.panel.PanelTitle;
import br.com.vipautomacao.gerador.text.formatter.MonetaryDocument;
import br.com.vipautomacao.gerador.text.formatter.TextDocument;

public abstract class QueryManagerView<E> extends DialogForm<E> {
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private PanelTitle paneltitle;
    protected JPanel panelContent;
    protected PanelActions panelActions;
    protected TextField<E> txtId;
    private JLabel lbId;
    protected TextField<E> txtQuery;
    private JLabel lbQuery;
    protected TextField<E> txtVersion;
    private JLabel lbVersion;
    protected TextField<E> txtCreatedAt;
    private JLabel lblCreatedAt;
    protected TextField<E> txtAuthor;
    private JLabel lblAuthor;
    
    protected QueryManagerView(Window owner) {
        super(owner);
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 691, 270);
        setTitle("Cadastro de SQLs");
        setModal(true);
        setLocationRelativeTo(getOwner());
        setContentPane(getJContentPane());
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getPaneltitle());
            jContentPane.add(getPanelContent());
        }
        return jContentPane;
    }

    private PanelTitle getPaneltitle() {
        if (paneltitle == null) {
            paneltitle = new PanelTitle("Cadastro de SQLs");
            paneltitle.setBounds(0, 0, 675, 50);
        }
        return paneltitle;
    }

    private JPanel getPanelContent() {
        if (panelContent == null) {
            panelContent = new JPanel();
            panelContent.setBounds(0, 47, 896, 187);
            panelContent.setLayout(null);
            panelContent.add(getTxtId());
            panelContent.add(getLbId());
            panelContent.add(getTxtQuery());
            panelContent.add(getLbQuery());
            panelContent.add(getTxtAuthor());
            panelContent.add(getLbAuthor());
            panelContent.add(getTxtVersion());
            panelContent.add(getLblCreatedAt());
            panelContent.add(getTxtCreatedAt());
            panelContent.add(getLblAuthor());
            panelContent.add(getPanelActions());
        }
        return panelContent;
    }

    public PanelActions getPanelActions() {
        if (panelActions == null) {
            panelActions = new PanelActions();
            panelActions.setBounds(0, 129, 673, 60);
        }
        return panelActions;
    }

    private TextField<E> getTxtId() {
        if (txtId == null) {
            txtId = new TextField<>("id");
            txtId.setBounds(21, 36, 96, 30);
        }
        return txtId;
    }

    private JLabel getLbId() {
        if (lbId == null) {
            lbId = new JLabel("Id:");
            lbId.setBounds(21, 11, 96, 20);
        }
        return lbId;
    }

    private TextField<E> getTxtQuery() {
        if (txtQuery == null) {
            txtQuery = new TextField<>("query", true);
            txtQuery.setBounds(127, 36, 517, 30);
            txtQuery.setText(txtQuery.getText().toUpperCase());
            txtQuery.setDocument(new TextDocument(60));
        }
        return txtQuery;
    }

    private JLabel getLbQuery() {
        if (lbQuery == null) {
            lbQuery = new JLabel("Query:");
            lbQuery.setBounds(127, 11, 517, 20);
        }
        return lbQuery;
    }

    private TextField<E> getTxtAuthor() {
        if (txtVersion == null) {
            txtVersion = new TextField<>("author", true);
            txtVersion.setBounds(452, 88, 96, 30);
            txtVersion.setDocument(new TextDocument(60));
        }
        return txtVersion;
    }

    private JLabel getLbAuthor() {
        if (lbVersion == null) {
            lbVersion = new JLabel("Version:");
            lbVersion.setBounds(452, 63, 96, 30);
        }
        return lbVersion;
    }
	private TextField<E> getTxtVersion() {
		if (txtCreatedAt == null) {
			txtCreatedAt = new TextField<E>("version", true);
			txtCreatedAt.setBounds(558, 88, 86, 30);
		}
		return txtCreatedAt;
	}
	private JLabel getLblCreatedAt() {
		if (lblCreatedAt == null) {
			lblCreatedAt = new JLabel("CreatedAt:");
			lblCreatedAt.setBounds(558, 63, 86, 30);
		}
		return lblCreatedAt;
	}
	private TextField<E> getTxtCreatedAt() {
		if (txtAuthor == null) {
			txtAuthor = new TextField<E>("createdAt", true);
			txtAuthor.setText("");
			txtAuthor.setBounds(21, 88, 421, 30);
		}
		return txtAuthor;
	}
	private JLabel getLblAuthor() {
		if (lblAuthor == null) {
			lblAuthor = new JLabel("Author:");
			lblAuthor.setBounds(21, 63, 96, 30);
		}
		return lblAuthor;
	}
}