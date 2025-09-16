package br.com.manager;

import br.com.manager.controller.QueryManagerController;
import br.com.vipautomacao.gerador.swing.util.LookAndFeelUtil;

public class Application {
    public static void main(String[] args) {
        LookAndFeelUtil.startLookAndFeel();
        new QueryManagerController().setVisible(true);
    }
}
