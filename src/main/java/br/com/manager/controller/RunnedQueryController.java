package br.com.manager.controller;

import java.awt.Window;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import br.com.manager.model.QueryEntity;
import br.com.manager.model.RunnedQueryDisplayDto;
import br.com.manager.model.RunnedQueryInClientEntity;
import br.com.manager.service.QueryService;
import br.com.manager.service.RunnedQueryService;
import br.com.manager.view.RunnedQueryView;

public class RunnedQueryController extends RunnedQueryView {
    private static final long serialVersionUID = 1L;
    private List<RunnedQueryInClientEntity> currentQueries;
    private Map<String, RunnedQueryDisplayDto> displayDataMap;

    public RunnedQueryController(Window owner) {
        super(owner);
        currentQueries = new ArrayList<>();
        displayDataMap = new HashMap<>();
        setActionListeners();
    }

    private void setActionListeners() {
        btnSearch.addActionListener(e -> searchQueries());
        btnDeleteSelected.addActionListener(e -> deleteSelectedQueries());
        btnDeleteAll.addActionListener(e -> deleteAllQueries());
    }

    private void searchQueries() {
        String clientId = txtClientId.getText();
        if (clientId == null || clientId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, informe o código do cliente.", 
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            currentQueries = RunnedQueryService.findByClientId(clientId.trim());
            clearTable();
            displayDataMap.clear();
            
            if (currentQueries.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                        "Nenhuma query executada encontrada para este cliente.", 
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Coletar todos os queryIds únicos
            Set<String> queryIds = new HashSet<>();
            for (RunnedQueryInClientEntity runnedQuery : currentQueries) {
                String queryId = runnedQuery.getQueryId();
                if (queryId != null && !queryId.trim().isEmpty()) {
                    queryIds.add(queryId);
                }
            }

            // Buscar todas as queries de uma vez
            Map<String, QueryEntity> queryMap = new HashMap<>();
            if (!queryIds.isEmpty()) {
                try {
                    List<QueryEntity> queries = QueryService.findByUuidList(queryIds);
                    for (QueryEntity query : queries) {
                        if (query != null && query.getId() != null) {
                            queryMap.put(query.getId(), query);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao buscar queries: " + e.getMessage());
                    // Continua sem os dados das queries
                }
            }

            // Preencher a tabela
            for (RunnedQueryInClientEntity runnedQuery : currentQueries) {
                String queryId = runnedQuery.getQueryId();
                QueryEntity query = queryMap.get(queryId);
                
                String queryName = query != null && query.getQuery() != null 
                        ? (query.getQuery().length() > 100 
                            ? query.getQuery().substring(0, 100) + "..." 
                            : query.getQuery())
                        : "N/A";
                String author = query != null && query.getAuthor() != null ? query.getAuthor() : "N/A";
                String runnedAt = "";
                if (runnedQuery.getRunnedAt() != null) {
                    try {
                        LocalDateTime dateTime = runnedQuery.getRunnedAt();
                        runnedAt = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    } catch (Exception e) {
                        runnedAt = runnedQuery.getRunnedAt().toString();
                    }
                }
                
                RunnedQueryDisplayDto displayDto = new RunnedQueryDisplayDto();
                displayDto.setRunnedQueryId(runnedQuery.getId());
                displayDto.setQueryId(queryId);
                displayDto.setQueryName(queryName);
                displayDto.setAuthor(author);
                displayDto.setRunnedAt(runnedAt);
                
                displayDataMap.put(runnedQuery.getId(), displayDto);
                
                addRow(new Object[] { 
                    false, 
                    queryName, 
                    runnedAt, 
                    author,
                    queryId != null ? queryId : "N/A"
                });
            }

            JOptionPane.showMessageDialog(this, 
                    String.format("Foram encontradas %d query(s) executada(s).", currentQueries.size()), 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                    "Erro ao buscar queries: " + e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedQueries() {
        if (currentQueries.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Não há queries para excluir. Por favor, busque primeiro.", 
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Set<String> selectedIds = new HashSet<>();
        DefaultTableModel model = getTableModel();
        
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean selected = (Boolean) model.getValueAt(i, 0);
            if (selected != null && selected) {
                // O ID da runned query está no displayDataMap, precisamos encontrar pelo índice
                if (i < currentQueries.size()) {
                    RunnedQueryInClientEntity runnedQuery = currentQueries.get(i);
                    if (runnedQuery.getId() != null) {
                        selectedIds.add(runnedQuery.getId());
                    }
                }
            }
        }

        if (selectedIds.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor, selecione pelo menos uma query para excluir.", 
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this, 
                String.format("Tem certeza que deseja excluir %d query(s) selecionada(s)?", selectedIds.size()),
                "Confirmação de Exclusão", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                Boolean success = RunnedQueryService.deleteQueries(selectedIds);
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                            String.format("%d query(s) excluída(s) com sucesso!", selectedIds.size()), 
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    searchQueries(); // Atualiza a lista
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "Erro ao excluir queries. Verifique a conexão com a API.", 
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                        "Erro ao excluir queries: " + e.getMessage(), 
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteAllQueries() {
        String clientId = txtClientId.getText();
        if (clientId == null || clientId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor, informe o código do cliente.", 
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (currentQueries.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Não há queries para excluir. Por favor, busque primeiro.", 
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this, 
                String.format("Tem certeza que deseja excluir TODAS as %d query(s) do cliente %s?", 
                        currentQueries.size(), clientId),
                "Confirmação de Exclusão", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                Boolean success = RunnedQueryService.deleteByClient(clientId.trim());
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                            "Todas as queries foram excluídas com sucesso!", 
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    clearTable();
                    currentQueries.clear();
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "Erro ao excluir queries. Verifique a conexão com a API.", 
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                        "Erro ao excluir queries: " + e.getMessage(), 
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

