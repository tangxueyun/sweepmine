package sweepmine;

import game.OptionDialog;
import utils.Setting;

import javax.swing.*;
import java.awt.*;

/**
 * 扫雷程序的主函数
 *
 * @author tang
 * @time 2016-9-23
 */
public class Main {
    // 判断程序是否进行测试 true测试 false不测试
    public static final boolean isTest = true;
    // 游戏是否开始
    public static boolean isStart = false;

    public static void main(String[] args) {
        Setting setting = new Setting();
        // 第一次开始游戏，必须先选择游戏等级才能开始游戏
        if (setting.getGrade() == 0) {
            OptionDialog dialog = new OptionDialog(new SweepMineFrame(),
                    new MineArea());
            dialog.setVisible(true);
        }
        // 显示窗体
        EventQueue.invokeLater(() -> {
                    JFrame frame = new SweepMineFrame();
                    frame.setVisible(true);
                }
        );
    }
}
