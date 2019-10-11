package Component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

import utils.Setting;

/**
 * 记雷器
 * 
 * @author tang
 * @time 2016-10-10
 */
public class MineCount extends JComponent
{
	private Image image;
	private int width;
	private int height;
	private int mineNum;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(200, 200);
				frame.setVisible(true);
				
				MineCount count = new MineCount();
				frame.add(count);
				
				count.reduceMineNum();
				count.increaseMineNum();
			}
		});
	}
	
	/**
	 * 构造器
	 */
	public MineCount()
	{
		// Setting setting = new Setting(Setting.LOOK);
		// 获取记雷器的背景图片资源
		if (Setting.getBlockColor() == Setting.GREEN_BLOCK) image = new ImageIcon(
				"/src/image/green/mine_count.png").getImage();
		else image = new ImageIcon("/src/image/blue/mine_count.png").getImage();
		// 资源不为空，则获取图片的宽高，作为首选尺寸
		if (image == null) return;
		width = image.getWidth(this);
		height = image.getHeight(this);
		setPreferredSize(new Dimension(width, height));
		// 初始化雷数
		mineNum = Setting.getMineNum();
	}
	
	/**
	 * 绘制记雷器
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		// 图片资源不为空时，才能绘制
		if (image == null) return;
		Graphics2D g2 = (Graphics2D) g;
		// 绘制图片资源
		g2.drawImage(image, 0, 0, null);
		// 设置字体颜色为白色
		g2.setColor(Color.WHITE);
		// 设置字体风格为加粗，大小为30
		g2.setFont(new Font(null, Font.PLAIN, 30));
		// 绘制雷数
		g2.drawString("" + mineNum, width / 2 - 35, height / 2 + 13);
	}
	
	/**
	 * 雷数加1，重绘记雷器
	 */
	public void increaseMineNum()
	{
		mineNum++;
		repaint();
	}
	
	/**
	 * 雷数减1，重绘记雷器
	 */
	public void reduceMineNum()
	{
		mineNum--;
		repaint();
	}
	
	/**
	 * 获取当前雷数
	 * 
	 * @return
	 */
	public int getMineNum()
	{
		return mineNum;
	}
}
