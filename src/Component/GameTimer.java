package Component;

import utils.Setting;

import javax.swing.*;
import java.awt.*;

/**
 * 游戏计时器
 *
 * @author tang
 * @time 2016-10-7
 */
public class GameTimer extends JComponent {
    private Timer timer;
    private Image image;

    private int width;
    private int height;

    private int time;

    /**
     * 主函数，用于测试计时器
     *
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            JFrame frame = new JFrame();
            frame.setSize(200, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            GameTimer timer = new GameTimer();
            frame.add(timer);
            timer.start();
        });
    }

    /**
     * 构造器
     */
    public GameTimer() {
        // 获取计时器背景图片
        if (Setting.getBlockColor() == Setting.GREEN_BLOCK)
            image = new ImageIcon(GameTimer.class.getResource("/image/green/timer.png")).getImage();
        else
            image = new ImageIcon(GameTimer.class.getResource("/image/blue/timer.png")).getImage();
        // 背景图片不为空时，获取图片的宽高
        if (image == null) return;
        width = image.getWidth(this);
        height = image.getHeight(this);
        // 设置首选宽高
        setPreferredSize(new Dimension(width, height));
        // 初始化时间为0
        time = 0;
    }

    /**
     * 绘制计时器
     */
    @Override
    protected void paintComponent(Graphics g) {

        if (image == null) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, null);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font(null, Font.PLAIN, 30));
        g2.drawString("" + time, width / 2, height / 2 + 13);
    }

    /**
     * 开始计时
     */
    public void start() {
        // 每个1秒，时间加1，并重新绘制计时器
        timer = new Timer(1000, e -> {
            time++;
            repaint();
        });
        // 计时开始
        timer.start();
    }

    /**
     * 当计时器不为空或者处于计时状态，则停止计时
     */
    public void stop() {
        if (timer == null || !timer.isRunning()) return;
        timer.stop();
    }

    /**
     * 获取时间
     *
     * @return 当前的时间计时
     */
    public int getTime() {
        return time;
    }
}
