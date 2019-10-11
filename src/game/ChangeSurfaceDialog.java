package game;

import sweepmine.Main;
import sweepmine.MineArea;
import sweepmine.SweepMineFrame;
import utils.GBC;
import utils.Setting;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 更改外观的对话框
 *
 * @author tang
 * @time 2016-10-5
 */
public class ChangeSurfaceDialog extends JDialog {

    // 用于测试的主函数
    public static void main(String[] args) {
        ChangeSurfaceDialog dialog = new ChangeSurfaceDialog(
                new SweepMineFrame(), new MineArea());
        dialog.setVisible(true);
    }

    private final int WIDTH = 595;
    private final int HEIGHT = 475;

    private int blockColor, mineStyle;
    private boolean isRandom;

    private List<ImageIcon> imgList;

    private MineArea mineArea;

    public ChangeSurfaceDialog(Frame owner, MineArea area) {
        super(owner, "更改外观", true);
        // 设置对话框大小
        setSize(WIDTH, HEIGHT);
        // 不可更改对话框大小
        setResizable(false);

        blockColor = Setting.getBlockColor();
        mineStyle = Setting.getMineStyle();
        isRandom = Setting.isRandom();

        mineArea = area;

        imgList = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            imgList.add(new ImageIcon(ChangeSurfaceDialog.class.getResource("/image/" + i + ".png")));

        setLayout(new GridBagLayout());

        add(new JLabel("选择游戏样式"),
                new GBC(0, 0).setAnchor(GBC.WEST).setIpad(10, 10));

        addChooseStylePanel();

        add(new JLabel("选择棋盘"),
                new GBC(0, 2).setAnchor(GBC.WEST).setIpad(10, 10));

        addChooseColorPanel();

        final JCheckBox checkBox = new JCheckBox("随机选择游戏样式和棋盘(R)");
        // 不绘画边框
        checkBox.setBorderPainted(false);
        checkBox.setMnemonic('R');
        checkBox.setSelected(isRandom);
        checkBox.addActionListener(e ->
        {
            if (checkBox.isSelected()) isRandom = true;
            else isRandom = false;
        });
        add(checkBox, new GBC(0, 4).setAnchor(GBC.WEST).setIpad(10, 10));

        addButtonPanel();

    }

    /**
     * 添加选择地雷样式的单选按钮面板
     */
    private void addChooseStylePanel() {
        JPanel stylePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stylePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        stylePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
                Color.GRAY));

        ButtonGroup styleGroup = new ButtonGroup();

        final JRadioButton mineBtn = new JRadioButton(imgList.get(0));
        setButtonStyle(mineBtn);
        styleGroup.add(mineBtn);
        stylePanel.add(mineBtn);

        final JRadioButton flowerBtn = new JRadioButton(imgList.get(2));
        setButtonStyle(flowerBtn);
        styleGroup.add(flowerBtn);
        stylePanel.add(flowerBtn);

        add(stylePanel, new GBC(0, 1).setAnchor(GBC.WEST).setIpad(265, 15));

        mineBtn.addActionListener(e -> {
            mineStyle = Setting.MINE;
            mineBtn.setIcon(imgList.get(1));
            flowerBtn.setIcon(imgList.get(2));
        });

        flowerBtn.addActionListener(e->{
                mineStyle = Setting.FLOWER;
                mineBtn.setIcon(imgList.get(0));
                flowerBtn.setIcon(imgList.get(3));
        });
        if (mineStyle == Setting.MINE) mineBtn.setIcon(imgList.get(1));
        else flowerBtn.setIcon(imgList.get(3));
    }

    /**
     * 添加选择雷块颜色的单选按钮面板
     */
    private void addChooseColorPanel() {
        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        colorPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
                Color.GRAY));

        ButtonGroup colorGroup = new ButtonGroup();

        final JRadioButton blueBtn = new JRadioButton(imgList.get(4));
        setButtonStyle(blueBtn);
        colorGroup.add(blueBtn);
        colorPanel.add(blueBtn);

        final JRadioButton greenBtn = new JRadioButton(imgList.get(6));
        setButtonStyle(greenBtn);
        colorGroup.add(greenBtn);
        colorPanel.add(greenBtn);

        add(colorPanel, new GBC(0, 3).setAnchor(GBC.WEST).setIpad(215, 15));
        blueBtn.addActionListener(e-> {
                blockColor = Setting.BLUE_BLOCK;
                blueBtn.setIcon(imgList.get(5));
                greenBtn.setIcon(imgList.get(6));
        });
        greenBtn.addActionListener(e-> {
                blockColor = Setting.GREEN_BLOCK;
                blueBtn.setIcon(imgList.get(4));
                greenBtn.setIcon(imgList.get(7));
        });
        if (blockColor == Setting.BLUE_BLOCK) blueBtn.setIcon(imgList.get(5));
        else greenBtn.setIcon(imgList.get(7));
    }

    /**
     * 在窗口中添加按钮面板
     */
    private void addButtonPanel() {
        JPanel btnPanel = new JPanel();
        // 设置靠右布局
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        // 确定按钮
        JButton confirmBtn = new JButton("   确定   ");
        confirmBtn.addActionListener(e ->
        {
            if (Main.isTest) System.out.println("blockColor=" + blockColor
                    + ",mineStyle=" + mineStyle + ",isRandom=" + isRandom);
            Setting.saveLookSetting(blockColor, mineStyle, isRandom);
            Main.isStart = false;
            mineArea.updateMineArea();
            setVisible(false);
        });
        btnPanel.add(confirmBtn);
        // 取消按钮
        JButton cancelBtn = new JButton("   取消   ");
        cancelBtn.addActionListener(e->setVisible(false));
        btnPanel.add(cancelBtn);
        add(btnPanel, new GBC(0, 6).setIpad(360, 0).setInsets(10, 0, 0, 0));
    }

    /**
     * 设置单选按钮的样式
     *
     * @param btn 单选按钮对象
     */
    private void setButtonStyle(JRadioButton btn) {
        // 不绘制边框线
        btn.setBorderPainted(false);
        // 将按钮设置为透明
        btn.setContentAreaFilled(false);
        // 设置按钮标题水平居中
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        // 设置按钮标题垂直置底
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
    }
}
