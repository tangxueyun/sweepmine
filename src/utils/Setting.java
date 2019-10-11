package utils;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * 包名称： utils<br>
 * 类名称：Setting<br>
 * 类描述：修改和保存设置<br>
 *
 * @author tang
 * @version 1.0
 * @time 2016-10-26
 */

public class Setting {
    /******************* 外观 ************************/
    // 蓝色雷块
    public static final int BLUE_BLOCK = 31;
    // 绿色雷块
    public static final int GREEN_BLOCK = 32;
    // 地雷形状
    public static final int MINE = 21;
    // 花朵形状
    public static final int FLOWER = 22;

    /******************** 游戏难度级别 ****************************/
    // 自定义，9~24*9~30,10~668个雷
    public static final int CUSTOM = 4;
    // 初级，9*9,10个雷
    public static final int PRIMARY = 1;
    // 中级，16*16,40个雷
    public static final int MEDIUM = 2;
    // 高级，16*30,99个雷
    public static final int SENIOR = 3;

    public static Preferences frameNode, lookNode, gradeNode, optionNode;

    // 测试
    public static void main(String[] args) {
        Setting setting = new Setting();
        System.out.println(setting.getBlockColor());
        System.out.println("rows=" + setting.getRows());
        System.out.println("cols=" + setting.getCols());
    }

    /**
     * 初始化用户子节点
     */
    public Setting() {
        Preferences root = Preferences.userRoot();
        // 窗口设置节点
        frameNode = root.node("/sweepmine/setting/frame");
        // 外观设置节点
        lookNode = root.node("/sweepmine/setting/look");
        // 等级信息节点
        gradeNode = root.node("/sweepmine/setting/grade");
        // 选项设置节点
        optionNode = root.node("/sweepmine/setting/option");
    }

    /**
     * 获取窗口设置（位置、宽高）
     *
     * @param frame 窗口框架
     */
    public static void getFameSetting(JFrame frame) {
        // 获取屏幕的宽高
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		Dimension dimension = toolkit.getScreenSize();
        // 计算x和y，使得窗口框架始终处于屏幕中央
//		int x = frameNode.getInt("x",
//				(int) (dimension.getWidth() - frame.getWidth()) / 2);
//		int y = frameNode.getInt("y",
//				(int) (dimension.getHeight() - frame.getHeight()) / 2);
        // 从节点中读取窗口框架的宽高，默认值为窗口框架的宽高
        int width = frameNode.getInt("width", frame.getWidth());
        int height = frameNode.getInt("height", frame.getHeight());
        // 设置窗口框架的位置以及大小
        frame.setBounds(0, 0, width, height);
    }

    /**
     * 保存窗体框架的属性设置（大小，位置）
     */
    public static void saveFrameSetting(JFrame frame) {
        frameNode.putInt("x", frame.getX());
        frameNode.putInt("y", frame.getY());
        frameNode.putInt("width", frame.getWidth());
        frameNode.putInt("height", frame.getHeight());
    }

    /**
     * 获取雷块颜色
     *
     * @return 雷块颜色的编号
     */
    public static int getBlockColor() {
        return lookNode.getInt("block_color", BLUE_BLOCK);
    }

    /**
     * 获取地雷样式
     *
     * @return 地雷样式的编号
     */
    public static int getMineStyle() {
        return lookNode.getInt("mine_style", MINE);
    }

    /**
     * 获取外观随机标志
     *
     * @return 外观随机标志 true：随机 false：不随机
     */
    public static boolean isRandom() {
        return lookNode.getBoolean("is_random", false);
    }

    /**
     * 保存外观设置参数
     *
     * @param color  雷块颜色编号
     * @param style  地雷样式编号
     * @param random 外观随机标志
     */
    public static void saveLookSetting(int color, int style, boolean random) {
        lookNode.putInt("block_color", color);
        lookNode.putInt("mine_style", style);
        lookNode.putBoolean("is_random", random);
    }

    /**
     * 获取已玩游戏次数
     *
     * @param grade 游戏等级
     * @return 已玩游戏次数
     */
    public static int getGameCount(int grade) {
        return gradeNode.getInt(grade + "game_count", 0);
    }

    /**
     * 已玩游戏次数增加1
     *
     * @param grade 游戏等级
     */
    public static void addGameCount(int grade) {
        int count = gradeNode.getInt(grade + "game_count", 0) + 1;
        gradeNode.putInt(grade + "game_count", count);
    }

    /**
     * 获取已胜游戏次数
     *
     * @param grade 游戏等级
     * @return 已胜游戏次数
     */
    public static int getWinCount(int grade) {
        return gradeNode.getInt(grade + "win_count", 0);
    }

    /**
     * 已胜游戏次数增加1
     *
     * @param grade 游戏等级
     */
    public static void addWinCount(int grade) {
        int count = gradeNode.getInt(grade + "win_count", 0) + 1;
        gradeNode.putInt(grade + "win_count", count);
    }

    /**
     * 获取最多连胜次数
     *
     * @param grade 游戏等级
     * @return 最多连胜次数
     */
    public static int getStraightCount(int grade) {
        return gradeNode.getInt(grade + "straight_count", 0);
    }

    /**
     * 最多连胜次数增加1
     *
     * @param grade 游戏等级
     */
    public static void addStraightCount(int grade) {
        int count = gradeNode.getInt(grade + "straight_count", 0) + 1;
        gradeNode.putInt(grade + "straight_count", count);
    }

    /**
     * 获取最多连败次数
     *
     * @param grade 游戏等级
     * @return 最多连败次数
     */
    public static int getFailCount(int grade) {
        return gradeNode.getInt(grade + "fail_count", 0);
    }

    /**
     * 最多连败次数增加1
     *
     * @param grade
     */
    public static void addFailCount(int grade) {
        int count = gradeNode.getInt(grade + "fail_count", 0) + 1;
        gradeNode.putInt(grade + "fail_count", count);
    }

    /**
     * 获取当前连胜局数
     *
     * @param grade 游戏等级
     * @return 当前连胜局数
     */
    public static int getCurrentStraightCount(int grade) {
        return gradeNode.getInt(grade + "current_straight_count", 0);
    }

    /**
     * 当前连局增加1
     *
     * @param grade 游戏等级
     */
    public static void setCurrentStraightCount(int grade) {
        int count = gradeNode.getInt(grade + "current_straight_count", 0) + 1;
        gradeNode.putInt(grade + "current_straight_count", count);
    }

    /**
     * 获取记录最佳时间的日期（年-月-日）
     *
     * @param grade 游戏等级
     * @param i     某个最佳时间（0<=i<5）
     * @return 记录最佳时间的日期
     */
    public static String getBestTimeDate(int grade, int i) {
        return gradeNode.get(grade + "date" + i, "");
    }

    /**
     * 设置记录最佳时间的日期
     *
     * @param grade 游戏等级
     * @param i     某个最佳时间（0<=i<5）
     * @param date  记录最佳时间的日期
     */
    public static void setBestTimeDate(int grade, int i, String date) {
        gradeNode.put(grade + "date" + i, date);
    }

    /**
     * 获取最佳时间（用时最少）
     *
     * @param grade 游戏等级
     * @param i     某个最佳时间（0<=i<5）
     * @return 游戏最佳时间
     */
    public static int getBestTime(int grade, int i) {
        return gradeNode.getInt(grade + "best_time" + i, 0);
    }

    /**
     * 记录最佳时间<br>
     * 每个等级只记录前五个最佳时间
     *
     * @param grade 游戏等级
     * @param i     某个最佳时间（0<=i<5）
     * @param time  最佳时间
     */
    public static void setBestTime(int grade, int i, int time) {
        gradeNode.putInt(grade + "best_time" + i, time);
    }

    /**
     * 清除所有（低、中、高级)统计信息
     */
    public static void clearGradeInfo() {
        for (int i = PRIMARY; i <= SENIOR; i++) {
            gradeNode.putInt(i + "game_count", 0);
            gradeNode.putInt(i + "win_count", 0);
            gradeNode.putInt(i + "straight_count", 0);
            gradeNode.putInt(i + "fail_count", 0);
            gradeNode.putInt(i + "current_straight_count", 0);
            for (int j = 0; j < 5; j++) {
                gradeNode.putInt(i + "best_time" + j, 0);
                gradeNode.put(i + "date" + j, "");
            }
        }
    }

    /**
     * 设置游戏等级
     *
     * @param grade   游戏等级
     * @param rows    扫雷区行数
     * @param cols    扫雷区列数
     * @param mineNum 扫雷区的雷数
     */
    public static void setGrade(int grade, int rows, int cols, int mineNum) {
        gradeNode.putInt("grade", grade);
        gradeNode.putInt("rows", rows);
        gradeNode.putInt("cols", cols);
        gradeNode.putInt("mine_num", mineNum);
    }

    /**
     * 获取游戏等级
     *
     * @return 游戏等级
     */
    public static int getGrade() {
        return gradeNode.getInt("grade", 0);
    }

    /**
     * 获取扫雷区的行数
     *
     * @return 行数
     */
    public static int getRows() {
        return gradeNode.getInt("rows", 9);
    }

    /**
     * 获取扫雷区的列数
     *
     * @return 列数
     */
    public static int getCols() {
        return gradeNode.getInt("cols", 9);
    }

    /**
     * 获取扫雷区的雷数
     *
     * @return 雷数
     */
    public static int getMineNum() {
        return gradeNode.getInt("mine_num", 0);
    }

    /**
     * 获取开启动画效果的标志
     *
     * @return 开启动画效果的标志 true:开启 false：不开启
     */
    public static boolean isFalsh() {
        return optionNode.getBoolean("is_flash", false);
    }

    /**
     * 获取播放声音的标志
     *
     * @return 播放声音的标志 true：播放 false：不播放
     */
    public static boolean isVoice() {
        return optionNode.getBoolean("is_voice", false);
    }

    /**
     * 获取显示提示的标志
     *
     * @return 显示提示的标志 true：显示提示 false：不显示提示
     */
    public static boolean isTip() {
        return optionNode.getBoolean("is_tip", false);
    }

    /**
     * 获取始终继续保存的游戏的标志
     *
     * @return 始终继续保存的游戏的标志 true：始终继续保存的游戏 false：开始新的游戏
     */
    public static boolean isSave() {
        return optionNode.getBoolean("is_save", false);
    }

    /**
     * 获取始终退出时保存游戏的标志
     *
     * @return 始终退出游戏时保存游戏的标志 true：始终在退出游戏时保存游戏 false：在退出游戏时不保存游戏
     */
    public static boolean isExitSave() {
        return optionNode.getBoolean("is_exit_save", false);
    }

    /**
     * 获取允许问号的标志
     *
     * @return 允许问号的标志 true：允许使用问号标记雷块 false：不允许使用问号标记雷块
     */
    public static boolean isWenhao() {
        return optionNode.getBoolean("is_wenhao", false);
    }

    /**
     * 保存选项设置
     *
     * @param isFlash    显示动画的标志
     * @param isVoice    播放声音的标志
     * @param isTip      显示提示的标志
     * @param isSave     始终继续保存的游戏的标志
     * @param isExitSave 退出时始终保存游戏的标志
     * @param isWenhao   允许问号的标志
     */
    public static void saveOptionSetting(boolean isFlash, boolean isVoice,
                                         boolean isTip, boolean isSave, boolean isExitSave, boolean isWenhao) {
        optionNode.putBoolean("is_flash", isFlash);
        optionNode.putBoolean("is_voice", isVoice);
        optionNode.putBoolean("is_tip", isTip);
        optionNode.putBoolean("is_save", isSave);
        optionNode.putBoolean("is_exit_save", isExitSave);
        optionNode.putBoolean("is_wenhao", isWenhao);
    }
}
