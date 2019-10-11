package Component;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sweepmine.Main;
import sweepmine.MineArea;
import sweepmine.SweepMineFrame;
import utils.GBC;
import utils.Setting;

/**
 * 游戏结束后显示的对话框
 *
 * @author tang
 * @time 2016-11-14
 */
public class GameResultDialog extends JDialog {

    private JButton exitBtn;
    private JButton gameBtn;

    private JLabel label;
    private int grade;
    private int time;
    private String date;
    private MineArea area;

    // 用于测试的主函数
    public static void main(String[] args) {
        SweepMineFrame frame = new SweepMineFrame();
        MineArea area = new MineArea();
        // 失败对话框
        GameResultDialog fail = new GameResultDialog(frame, area, "游戏失败",
                false, 10);
        fail.setVisible(true);
        // // 成功对话框
        // JDialog win = new GameResultDialog(frame, area, "游戏胜利", true, 10);
        // win.setVisible(true);

    }

    /**
     * 构造器
     *
     * @param owner 父窗体
     * @param title 对话框标题
     * @param isWin 游戏是否胜利 true:胜利 false:失败
     */
    public GameResultDialog(final JFrame owner, final MineArea area,
                            String title, boolean isWin, int time) {
        super(owner, title, true);
        // 不可更改对话框大小
        setResizable(false);

        grade = Setting.getGrade();

        this.area = area;
        this.time = time;

        initButtons();

        if (isWin) {
            setSize(395, 340);
            label = new JLabel("恭喜！您赢了！", JLabel.CENTER);
            if (grade != Setting.CUSTOM) {
                Setting.addWinCount(grade);
                addInfo(Setting.getWinCount(grade));
            }
            addButtonWin();
        } else {
            setSize(478, 285);
            label = new JLabel("不好意思，您输了，下次走运！", JLabel.CENTER);
            if (grade != Setting.CUSTOM) addInfo(Setting.getWinCount(grade));
            addButtonFail();
        }
        // 设置对话框位置处于窗口中间
        setLocation(owner.getX() + (owner.getWidth() - getWidth()) / 2,
                owner.getY() + (owner.getHeight() - getHeight()) / 2);

        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        add(label, BorderLayout.NORTH);

        // 窗口关闭监听器
        addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // 隐藏对话框
                        setVisible(false);
                        // 刷新扫雷区视图
                        area.updateMineArea();
                        Main.isStart = false;
                    }
                }
        );
    }

    /**
     * 初始化按钮
     */
    private void initButtons() {
        // 退出按钮
        exitBtn = new JButton("退出(X)");
        exitBtn.setMnemonic('X');
        //设置按钮点击事件监听器，实现点击按钮退出程序
        exitBtn.addActionListener(e -> System.exit(0));
        // 开始新游戏按钮
        gameBtn = new JButton("再玩一局(P)");
        gameBtn.setMnemonic('P');
        //设置按钮点击事件监听器，实现点击按钮重新开始游戏
        gameBtn.addActionListener(e ->
        {
            setVisible(false);
            Main.isStart = false;
            area.updateMineArea();
        });
    }

    /**
     * 添加游戏信息
     */
    private void addInfo(int winCount) {
        JPanel panel = new JPanel(new GridBagLayout());

        JLabel timeLabel = new JLabel("时间：" + time + "秒");
        panel.add(timeLabel,
                new GBC(0, 0).setInsets(0, 23, 10, 0).setAnchor(GBC.NORTHWEST));

        sortBestTime();
        int bestTime = Setting.getBestTime(grade, 0);
        JLabel bestTimeLabel = new JLabel("最佳时间：" + bestTime + "秒");
        panel.add(bestTimeLabel, new GBC(0, 1).setInsets(5, 23, 5, 0)
                .setAnchor(GBC.WEST));

        JLabel dateLabel = new JLabel("日期：" + date);
        panel.add(dateLabel, new GBC(0, 1).setInsets(5, 0, 5, 0));

        Setting.addGameCount(grade);
        int playNum = Setting.getGameCount(grade);
        JLabel playNumLabel = new JLabel("已玩游戏：" + playNum);
        panel.add(playNumLabel,
                new GBC(0, 2).setWeight(100, 0).setInsets(5, 23, 5, 0)
                        .setAnchor(GBC.WEST));

        JLabel winNum = new JLabel("已胜游戏：" + winCount);
        panel.add(winNum,
                new GBC(0, 3).setInsets(5, 23, 5, 0).setAnchor(GBC.WEST));

        int present = (int) ((float) winCount / playNum * 100);
        JLabel precent = new JLabel("百分比：" + present + "%");
        panel.add(precent, new GBC(0, 3).setInsets(5, 0, 5, 0));

        add(panel, BorderLayout.CENTER);
    }

    /**
     * 游戏胜利，添加三个按钮
     */
    public void addButtonWin() {
        // 按钮面板
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 15, 20, 250));
        panel.add(gameBtn);
        panel.add(exitBtn);
        add(panel, BorderLayout.SOUTH);
    }

    /**
     * 游戏失败，添加三个按钮
     */
    public void addButtonFail() {
        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
        panel.add(exitBtn);

        JButton aginButton = new JButton("重新开始这个游戏(R)");
        aginButton.setMnemonic('R');
        aginButton.addActionListener(e -> {
            setVisible(false);
            area.isSame = true;
            area.updateMineArea();
        });
        panel.add(aginButton);
        panel.add(gameBtn);
        add(panel, BorderLayout.SOUTH);
    }

    /**
     * 排序最佳时间<br>
     * 数据存储在有序树集中，无须再进行排序
     */
    private void sortBestTime() {
        // 当前日期
        date = LocalDate.now().getYear() + "/"
                + LocalDate.now().getMonthValue() + "/"
                + LocalDate.now().getDayOfMonth();
        // 有序树集
        TreeMap<Integer, String> map = new TreeMap<>();
        // 将本次的最佳时间和日期添加到树集中，用于排序
        map.put(time, date);
        // 将存储的5个最佳时间和记录日期添加到树集中
        for (int i = 0; i < 5; i++) {
            int t = Setting.getBestTime(grade, i);
            String d = Setting.getBestTimeDate(grade, i);
            if (t != 0) {
                map.put(Setting.getBestTime(grade, i),
                        Setting.getBestTimeDate(grade, i));
            }
        }
        // 将排序好的前5个最佳时间个日期存储回设置中
        int k = 0;
        for (int key : map.keySet()) {
            if (k < 5 && key != 0) {
                Setting.setBestTime(grade, k, key);
                Setting.setBestTimeDate(grade, k, map.get(key));
            }
            k++;
        }
    }
}
