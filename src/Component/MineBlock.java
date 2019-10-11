package Component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Transient;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import utils.Setting;

/**
 * 雷块
 * 
 * @author tang
 * @time 2016-10-4
 */
public class MineBlock extends JComponent
{
	// 正常状态的雷块
	public static final int NORMAL = 10;
	public static final int FLAG = 11;// 旗子
	public static final int WENHAO = 12;// 问号
	public static final int OPEN = 13;
	
	private int state;
	private int flag;
	
	private Image image;
	private int preferredWidth;
	private int preferredHeight;
	
	private int xNum, yNum;
	
	private String imageUrl, mineUrl;
	
	/**
	 * 构造器
	 * 
	 * @param xNum
	 *            x方向的编号
	 * @param yNum
	 *            y方向的编号
	 */
	public MineBlock(int xNum, int yNum)
	{
		this.xNum = xNum;
		this.yNum = yNum;

		imageUrl="src/image/";

		if (Setting.getBlockColor() == Setting.GREEN_BLOCK) imageUrl += "green/";
		else imageUrl += "blue/";
		
		if (Setting.getMineStyle() == Setting.MINE) mineUrl = imageUrl
				+ "/mine/";
		else mineUrl = imageUrl + "/flower/";
		
		image = new ImageIcon(imageUrl + "block.png").getImage();
		// 首选尺寸
		preferredWidth = image.getWidth(this);
		preferredHeight = image.getHeight(this);
		setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		// 雷块标志初始为正常
		flag = NORMAL;
		// 添加鼠标事件监听
		addMouseListener(new MouseHandler());
	}
	
	public MineBlock(int i)
	{
		imageUrl="src/image/";

		if (Setting.getBlockColor() == Setting.GREEN_BLOCK) imageUrl += "green/";
		else imageUrl = "blue/";
		
		if (Setting.getMineStyle() == Setting.MINE) mineUrl = imageUrl
				+ "/mine/";
		else mineUrl = imageUrl + "/flower/";
		
		image = new ImageIcon(imageUrl + "block.png").getImage();
		// 首选尺寸
		preferredWidth = image.getWidth(this);
		preferredHeight = image.getHeight(this);
		setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		// 雷块标志初始为正常
		flag = NORMAL;
		// 添加鼠标事件监听
		addMouseListener(new MouseHandler());
	}
	
	/**
	 * 绘制组件
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		if (image == null) return;
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, null);
	}
	
	/**
	 * 获取首选尺寸
	 */
	@Override
	@Transient
	public Dimension getPreferredSize()
	{
		return new Dimension(preferredWidth, preferredHeight);
	}
	
	@Override
	@Transient
	public Dimension getMinimumSize()
	{
		return new Dimension(25, 25);
	}
	
	/**
	 * 内部类：鼠标事件适配器
	 */
	private class MouseHandler extends MouseAdapter
	{
		/**
		 * 雷块未被翻开前，鼠标进入组件，雷块显示为高亮
		 */
		@Override
		public void mouseEntered(MouseEvent e)
		{
			if (flag == NORMAL) image = new ImageIcon(imageUrl
					+ "light_block.png").getImage();
			if (image == null) return;
			repaint();
		}
		
		/**
		 * 雷块未被翻开前，鼠标离开组件时，雷块颜色恢复正常
		 */
		@Override
		public void mouseExited(MouseEvent e)
		{
			if (flag == NORMAL) image = new ImageIcon(imageUrl + "block.png")
					.getImage();
			if (image == null) return;
			repaint();
		}
	}
	
	/**
	 * 获取雷块在X方向上的编号
	 * 
	 * @return
	 */
	public int getXNum()
	{
		return xNum;
	}
	
	/**
	 * 获取雷块在Y方向上的编号
	 * 
	 * @return
	 */
	public int getYNum()
	{
		return yNum;
	}
	
	public int getState()
	{
		return state;
	}
	
	public void setState(int state)
	{
		this.state = state;
	}
	
	public int getFlag()
	{
		return flag;
	}
	
	public void setFlag(int f)
	{
		flag = f;
		switch (flag)
		{
			case FLAG:
				image = new ImageIcon(imageUrl + "flag.png").getImage();
				break;
			case WENHAO:
				image = new ImageIcon(imageUrl + "wenhao.png").getImage();
				break;
			case NORMAL:
				image = new ImageIcon(imageUrl + "block.png").getImage();
				break;
		}
		if (image != null) repaint();
	}
	
	/**
	 * 刷新控件的ui
	 */
	public void update()
	{
		flag = OPEN;
		switch (state)
		{
			case -1:
				image = new ImageIcon(mineUrl + "false.png").getImage();
				break;
			case 0:
				image = new ImageIcon(imageUrl + "blank_block.png").getImage();
				break;
			case 1:
				image = new ImageIcon(imageUrl + "1.png").getImage();
				break;
			case 2:
				image = new ImageIcon(imageUrl + "2.png").getImage();
				break;
			case 3:
				image = new ImageIcon(imageUrl + "3.png").getImage();
				break;
			case 4:
				image = new ImageIcon(imageUrl + "4.png").getImage();
				break;
			case 5:
				image = new ImageIcon(imageUrl + "5.png").getImage();
				break;
			case 6:
				image = new ImageIcon(imageUrl + "6.png").getImage();
				break;
			case 7:
				image = new ImageIcon(imageUrl + "7.png").getImage();
				break;
			case 8:
				image = new ImageIcon(imageUrl + "8.png").getImage();
				break;
		}
		if (image != null) repaint();
	}
}
