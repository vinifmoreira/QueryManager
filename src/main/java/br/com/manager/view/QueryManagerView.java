package br.com.manager.view;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.StringSelection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.vipautomacao.gerador.swing.component.DialogForm;
import br.com.vipautomacao.gerador.swing.component.fields.TextField;
import br.com.vipautomacao.gerador.swing.component.panel.PanelActions;
import br.com.vipautomacao.gerador.swing.component.panel.PanelTitle;

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
    protected JTextField txtCreatedAt;
    private JLabel lbCreatedAt;
    protected TextField<E> txtAuthor;
    private JLabel lbAuthor;
    
    protected QueryManagerView(Window owner) {
        super(owner);
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 842, 300);
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
            paneltitle.setBounds(0, 0, 826, 50);
        }
        return paneltitle;
    }

    private JPanel getPanelContent() {
        if (panelContent == null) {
            panelContent = new JPanel();
            panelContent.setBounds(0, 47, 826, 187);
            panelContent.setLayout(null);
            panelContent.add(getTxtId());
            panelContent.add(getLbId());
            panelContent.add(getTxtQuery());
            panelContent.add(getLbQuery());
            panelContent.add(getLbCreatedAt());
            panelContent.add(getTxtCreatedAt());
            panelContent.add(getPanelActions());
            panelContent.add(getTxtVersion());
            panelContent.add(getLbVersion());
            panelContent.add(getTxtAuthor());
            panelContent.add(getLbAuthor());
        }
        return panelContent;
    }

    public PanelActions getPanelActions() {
        if (panelActions == null) {
            panelActions = new PanelActions();
            panelActions.setBounds(0, 129, 823, 60);
        }
        return panelActions;
    }

    private TextField<E> getTxtId() {
        if (txtId == null) {
            txtId = new TextField<>("id");
        	txtId.setEditable(false);
            txtId.setBounds(21, 36, 292, 30);
            txtId.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    String text = txtId.getText();
                    if (text != null && !text.isEmpty()) {
                        StringSelection selection = new StringSelection(text);
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
                    }
                }
            });
        }
        return txtId;
    }

    private JLabel getLbId() {
        if (lbId == null) {
            lbId = new JLabel("Código");
            lbId.setBounds(21, 11, 292, 20);
        }
        return lbId;
    }

    private TextField<E> getTxtQuery() {
        if (txtQuery == null) {
            txtQuery = new TextField<>("query", true);
            txtQuery.setBounds(21, 88, 637, 30);
        }
        return txtQuery;
    }

    private JLabel getLbQuery() {
        if (lbQuery == null) {
            lbQuery = new JLabel("SQL");
            lbQuery.setBounds(21, 68, 596, 20);
        }
        return lbQuery;
    }

    private TextField<E> getTxtVersion() {
        if (txtVersion == null) {
            txtVersion = new TextField<>("version", true);
            txtVersion.setBounds(355, 36, 216, 30);
        }
        return txtVersion;
    }

    private JLabel getLbVersion() {
        if (lbVersion == null) {
            lbVersion = new JLabel("Versão");
            lbVersion.setBounds(355, 11, 216, 20);
        }
        return lbVersion;
    }
	private JTextField getTxtCreatedAt() {
		if (txtCreatedAt == null) {
			txtCreatedAt = new JTextField();
			txtCreatedAt.setEditable(false);
			txtCreatedAt.setBounds(699, 88, 106, 30);
		}
		return txtCreatedAt;
	}
	private JLabel getLbCreatedAt() {
		if (lbCreatedAt == null) {
			lbCreatedAt = new JLabel("Data de Criação");
			lbCreatedAt.setBounds(699, 68, 106, 20);
		}
		return lbCreatedAt;
	}
	private TextField<E> getTxtAuthor() {
		if (txtAuthor == null) {
			txtAuthor = new TextField<E>("author", true);
			txtAuthor.setBounds(614, 36, 191, 30);
		}
		return txtAuthor;
	}
	private JLabel getLbAuthor() {
		if (lbAuthor == null) {
			lbAuthor = new JLabel("Autor");
			lbAuthor.setBounds(614, 16, 191, 20);
		}
		return lbAuthor;
	}
}