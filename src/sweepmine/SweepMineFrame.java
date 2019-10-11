package sweepmine;

import game.ChangeSurfaceDialog;
import game.NewGameDialog;
import game.OptionDialog;
import game.StatisticalInfoDialog;
import help.AboutDialog;
import help.HelpFrame;
import utils.Setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 扫雷框架
 *
 * @author tang
 * @time 2016-9-24
 */
public class SweepMineFrame extends JFrame {
    private JMenu gameMenu, helpMenu;
    private JMenuBar menuBar;
    private MineArea area;

    public SweepMineFrame() {
        setTitle("扫雷");
        setIconImage(new ImageIcon("src/image/lei.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置为windows感官
        try {
            UIManager
                    .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);

        menuBar = new JMenuBar();
        addGameMenu();
        addHelpMenu();
        setJMenuBar(menuBar);

        // 在窗口中添加扫雷区
        area = new MineArea();
        add(area, BorderLayout.CENTER);
        pack();

        Setting.getFameSetting(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { // 在退出程序前，将窗口的位置、宽高保存
                Setting.saveFrameSetting(SweepMineFrame.this);
                System.exit(0);
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // 获取屏幕的宽高
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension dimension = toolkit.getScreenSize();
                // 若已经最大化，再双击窗口或点击最大化按钮，窗口恢复首选宽高
                if (e.getComponent().getWidth() == dimension.getWidth()
                        && e.getComponent().getHeight() == dimension
                        .getHeight()) pack();
            }
        });
    }

    /**
     * 添加游戏菜单
     */
    private void addGameMenu() {
        // 游戏菜单
        gameMenu = new JMenu("游戏(G)");
        // 添加快捷键Alt+G
        gameMenu.setMnemonic('G');
        // 添加菜单项
        addNewGameItem();
        addInfoItem();
        addOptionItem();
        addChangeItem();
        addExitItem();

        menuBar.add(gameMenu);
    }

    /**
     * 添加帮助菜单
     */
    private void addHelpMenu() {
        // 帮助
        helpMenu = new JMenu("帮助(H)");
        // 设置快捷键ALt+H
        helpMenu.setMnemonic('H');
        // 添加菜单项
        addCheckHelpItem();
        addAboutItem();
        addMoreItem();

        menuBar.add(helpMenu);
    }

    /**
     * 在游戏菜单中添加新游戏菜单项
     */
    private void addNewGameItem() {
        JMenuItem newGameItem = new JMenuItem("新游戏(N)");
        newGameItem.setAccelerator(KeyStroke.getKeyStroke("F2"));
        newGameItem.addActionListener(e ->
        {
            if (Main.isStart) {
                JDialog dialog = new NewGameDialog(SweepMineFrame.this, area);
                dialog.setVisible(true);
            }
        });
        gameMenu.add(newGameItem);
        gameMenu.addSeparator();
    }

    /**
     * 在游戏菜单中添加统计信息菜单项
     */
    private void addInfoItem() {
        JMenuItem infoItem = new JMenuItem("统计信息(S)");
        infoItem.setAccelerator(KeyStroke.getKeyStroke("F4"));
        infoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new StatisticalInfoDialog(SweepMineFrame.this);
                dialog.setVisible(true);
            }
        });
        gameMenu.add(infoItem);
    }

    /**
     * 在游戏菜单中添加选项菜单项
     */
    private void addOptionItem() {
        JMenuItem optionItem = new JMenuItem("选项(C)");
        optionItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
        optionItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new OptionDialog(SweepMineFrame.this, area);
                dialog.setVisible(true);
            }
        });
        gameMenu.add(optionItem);
    }

    /**
     * 在游戏菜单中添加更改外观菜单项
     */
    private void addChangeItem() {
        JMenuItem changeItem = new JMenuItem("更改外观(A)");
        changeItem.setAccelerator(KeyStroke.getKeyStroke("F7"));
        changeItem.addActionListener(e -> {
            JDialog dialog = new ChangeSurfaceDialog(SweepMineFrame.this,
                    area);
            dialog.setVisible(true);
        });
        gameMenu.add(changeItem);
        gameMenu.addSeparator();
    }

    /**
     * 在游戏菜单中添加退出菜单项
     */
    private void addExitItem() {
        JMenuItem exitItem = new JMenuItem("退出(X)");
        exitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(exitItem);
    }

    /**
     * 在帮助菜单中添加查看帮助菜单项
     */
    private void addCheckHelpItem() {
        JMenuItem checkHelpItem = new JMenuItem("查看帮助(V)");
        checkHelpItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
        checkHelpItem.addActionListener(e ->
                EventQueue.invokeLater(() -> {
                    HelpFrame helpFrame = new HelpFrame();
                    helpFrame.setVisible(true);
                })
        );
        helpMenu.add(checkHelpItem);
        helpMenu.addSeparator();
    }

    /**
     * 在帮助菜单中添加关于扫雷菜单项
     */
    private void addAboutItem() {
        JMenuItem aboutItem = new JMenuItem("关于扫雷(A)");
        aboutItem.addActionListener(e -> {
            JDialog dialog = new AboutDialog(SweepMineFrame.this);
            dialog.setVisible(true);
            if (Main.isTest)
                System.out.println("关于扫雷");
        });
        helpMenu.add(aboutItem);
        helpMenu.addSeparator();
    }

    /**
     * 在帮助菜单中添加更多游戏菜单项
     */
    private void addMoreItem() {
        JMenuItem getMoreItem = new JMenuItem("联机获取更多游戏(M)");

        getMoreItem.addActionListener(e ->
                JOptionPane.showMessageDialog(SweepMineFrame.this,
                        "该功能尚未开通，敬请期待！", "联机获取更多游戏",
                        JOptionPane.INFORMATION_MESSAGE)
        );

        helpMenu.add(getMoreItem);
    }
}
