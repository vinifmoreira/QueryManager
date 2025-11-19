package br.com.manager.view;

import java.awt.Window;

import java.awt.Dialog;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.vipautomacao.gerador.swing.component.fields.TextField;
import br.com.vipautomacao.gerador.swing.component.panel.PanelTitle;

public class RunnedQueryView extends JDialog {
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private PanelTitle paneltitle;
    protected JPanel panelContent;
    protected TextField<Object> txtClientId;
    private JLabel lbClientId;
    protected JButton btnSearch;
    protected JButton btnDeleteSelected;
    protected JButton btnDeleteAll;
    protected JTable tableQueries;
    protected JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    
    protected RunnedQueryView(Window owner) {
        super(owner);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 900, 600);
        setTitle("Consultar Queries Executadas");
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
            paneltitle = new PanelTitle("Consultar Queries Executadas");
            paneltitle.setBounds(0, 0, 884, 50);
        }
        return paneltitle;
    }

    private JPanel getPanelContent() {
        if (panelContent == null) {
            panelContent = new JPanel();
            panelContent.setBounds(0, 47, 884, 513);
            panelContent.setLayout(null);
            panelContent.add(getLbClientId());
            panelContent.add(getTxtClientId());
            panelContent.add(getBtnSearch());
            panelContent.add(getScrollPane());
            panelContent.add(getBtnDeleteSelected());
            panelContent.add(getBtnDeleteAll());
        }
        return panelContent;
    }

    private JLabel getLbClientId() {
        if (lbClientId == null) {
            lbClientId = new JLabel("Código do Cliente");
            lbClientId.setBounds(21, 11, 200, 20);
        }
        return lbClientId;
    }

    private TextField<Object> getTxtClientId() {
        if (txtClientId == null) {
            txtClientId = new TextField<>("clientId", true);
            txtClientId.setBounds(21, 36, 300, 30);
        }
        return txtClientId;
    }

    private JButton getBtnSearch() {
        if (btnSearch == null) {
            btnSearch = new JButton("Buscar");
            btnSearch.setBounds(340, 36, 100, 30);
        }
        return btnSearch;
    }

    private JButton getBtnDeleteSelected() {
        if (btnDeleteSelected == null) {
            btnDeleteSelected = new JButton("Excluir Selecionadas");
            btnDeleteSelected.setBounds(21, 470, 150, 30);
        }
        return btnDeleteSelected;
    }

    private JButton getBtnDeleteAll() {
        if (btnDeleteAll == null) {
            btnDeleteAll = new JButton("Excluir Todas");
            btnDeleteAll.setBounds(190, 470, 150, 30);
        }
        return btnDeleteAll;
    }

    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane();
            scrollPane.setBounds(21, 80, 840, 380);
            scrollPane.setViewportView(getTableQueries());
        }
        return scrollPane;
    }

    protected JTable getTableQueries() {
        if (tableQueries == null) {
            tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "Selecionar", "Nome da SQL", "Data", "Autor", "ID da Query" }
            ) {
                private static final long serialVersionUID = 1L;
                Class<?>[] columnTypes = new Class[] { Boolean.class, String.class, String.class, String.class, String.class };
                
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }
                
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 0; // Apenas a coluna de seleção é editável
                }
            };
            
            tableQueries = new JTable(tableModel);
            tableQueries.setRowSelectionAllowed(false);
            tableQueries.getColumnModel().getColumn(0).setPreferredWidth(80);
            tableQueries.getColumnModel().getColumn(1).setPreferredWidth(400);
            tableQueries.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableQueries.getColumnModel().getColumn(3).setPreferredWidth(150);
            tableQueries.getColumnModel().getColumn(4).setPreferredWidth(200);
        }
        return tableQueries;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void addRow(Object[] row) {
        tableModel.addRow(row);
    }
}

