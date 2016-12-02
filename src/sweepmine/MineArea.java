package sweepmine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.GBC;
import utils.MineAreaLayout;
import utils.Setting;

import Component.GameResultDialog;
import Component.GameTimer;
import Component.MineBlock;
import Component.MineCount;

/**
 * 扫雷区:雷块区域
 * 
 * @author tang
 * @time 2016-10-4
 */
public class MineArea extends JPanel
{
	private MineBlock block;
	private SweepMine game;
	private GameTimer timer;
	private MineCount count;
	private JPanel minePanel;
	private static List<MineBlock> list;
	
	private int rows, cols, mineNum;
	private int digBlockNum;// 被挖开的雷块数
	private boolean isWenhao;
	public boolean isSame;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			Setting setting = new Setting();
			
			public void run()
			{
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
				MineArea area = new MineArea();
				frame.add(area);
				frame.pack();
			}
		});
	}
	
	public MineArea()
	{
		setLayout(new BorderLayout());
		
		getValues();
		
		digBlockNum = 0;
		
		isSame = false;
		
		game = new SweepMine();
		// 初始化列表，用于存放雷块控件
		list = new ArrayList<MineBlock>();
		
		addMineBlock();
		
		addTimerAndCount();
	}
	
	/**
	 * 获取行数、列数和雷数
	 */
	public void getValues()
	{
		isWenhao = Setting.isWenhao();
		rows = Setting.getRows();
		cols = Setting.getCols();
		mineNum = Setting.getMineNum();
	}
	
	/**
	 * 在面板中添加雷块，并给每一个雷块添加点击监听事件
	 */
	private void addMineBlock()
	{
		minePanel = new JPanel();
		// 布局设置为自定义的扫雷区布局
		minePanel.setLayout(new MineAreaLayout(rows, cols));
		// 设置背景颜色
		minePanel.setBackground(new Color(0xbe, 0xd0, 0xde));
		// 添加雷块
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				block = new MineBlock(i, j);
				minePanel.add(block);
				list.add(block);
			}
		}
		// 利用循环，为每一个雷块添加鼠标监听器
		for (MineBlock mb : list)
			mb.addMouseListener(new BlockMouseListener(mb));
		// 将雷块面板添加到窗口的中间位置
		add(minePanel, BorderLayout.CENTER);
	}
	
	private void addTimerAndCount()
	{
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0xbe, 0xd0, 0xde));
		// 添加计时器
		timer = new GameTimer();
		panel.add(timer);
		// 添加地雷计数器
		count = new MineCount();
		panel.add(count);
		add(panel, BorderLayout.SOUTH);
	}
	
	/**
	 * 挖开雷块，即显示雷块下的状态
	 * 
	 * @param mb
	 *            被鼠标点击中的雷块或者是一个3*3范围内的中心雷块
	 */
	private void digBlock(MineBlock mb)
	{
		// 确保雷块不处于插旗状态和挖开状态
		if (mb.getFlag() != MineBlock.FLAG && mb.getFlag() != MineBlock.OPEN)
		{
			// 挖开被点击的雷块
			mb.update();
			digBlockNum++;
			// 雷块的四周都没有雷
			if (mb.getState() == 0)
			{
				int mx = mb.getXNum() - 1;
				int my = mb.getYNum() - 1;
				for (int i = 0; i < 3; i++)
				{
					if (mx >= 0 && mx < rows)
					{
						for (int j = 0; j < 3; j++)
						{
							int n = my + j;
							if (n >= 0 && n < cols) digBlock(list.get(mx * cols
									+ n));
						}
					}
					mx++;
				}
			}
			else if (mb.getState() == -1)
			{
				// 游戏失败
				fail();
			}
		}
	}
	
	/**
	 * 自动挖开雷块<br>
	 * 若一个挖开的雷块四周的旗数等于它显示的雷数，那么自动挖开四周没有挖开并且插着旗的雷块
	 * 
	 * @param mb
	 */
	private void autoDigBlock(MineBlock mb)
	{
		// 雷块不处于挖开状态时，返回
		if (mb.getFlag() != MineBlock.OPEN) return;
		// 获取雷块四周插的旗数
		int flagNum = countFlagNum(mb);
		// 旗数不为0且等于雷块显示的雷数时，才能自动挖开四周没有挖开并且插着旗的的雷块
		if (flagNum != 0 && flagNum == mb.getState())
		{
			int mx = mb.getXNum() - 1;
			int my = mb.getYNum() - 1;
			for (int i = 0; i < 3; i++)
			{
				if (mx >= 0 && mx < rows)
				{
					for (int j = 0; j < 3; j++)
					{
						int n = my + j;
						if (n >= 0 && n < cols && i * j != 1)
						{
							digBlock(list.get(mx * cols + n));
						}
					}
				}
				mx++;
			}
		}
	}
	
	/**
	 * 显示所有雷块的挖开状态
	 */
	private void digAllBlock()
	{
		
	}
	
	/**
	 * 计算一个雷块四周插的旗数
	 * 
	 * @param mb
	 *            雷块
	 * @return 旗数
	 */
	private int countFlagNum(MineBlock mb)
	{
		int flagNum = 0;
		int x = mb.getXNum() - 1;
		int y = mb.getYNum() - 1;
		for (int i = 0; i < 3; i++)
		{
			if (x >= 0 && x < rows)
			{
				for (int j = 0; j < 3; j++)
				{
					int n = j + y;
					if (n >= 0 && n < cols && i * j != 1)
					{
						if (list.get(x * cols + n).getFlag() == MineBlock.FLAG) flagNum++;
					}
				}
			}
			x++;
		}
		return flagNum;
	}
	
	private class BlockMouseListener extends MouseAdapter
	{
		int x, y;
		MineBlock mBlock;
		
		public BlockMouseListener(MineBlock mb)
		{
			mBlock = mb;
			x = mBlock.getXNum();
			y = mBlock.getYNum();
		}
		
		@Override
		public void mousePressed(MouseEvent e)
		{
			// 鼠标左右键触发的事件
			if (e.getModifiersEx() == (InputEvent.BUTTON3_DOWN_MASK + InputEvent.BUTTON1_DOWN_MASK))
			{
				autoDigBlock(mBlock);
			}
			// 鼠标左键触发的事件
			else if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0)
			{
				// 第一次点击扫雷区 ，开始游戏
				if (!Main.isStart) startNewGame(x, y);
				if (isSame) game.setMineArea(list);
				digBlock(mBlock);
			}
			// 鼠标右键触发的事件
			else if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0)
			{
				switch (mBlock.getFlag())
				{
					case MineBlock.NORMAL:
						mBlock.setFlag(MineBlock.FLAG);
						count.reduceMineNum();
						break;
					case MineBlock.FLAG:
						if (!isWenhao) mBlock.setFlag(MineBlock.NORMAL);
						else mBlock.setFlag(MineBlock.WENHAO);
						count.increaseMineNum();
						break;
					case MineBlock.WENHAO:
						mBlock.setFlag(MineBlock.NORMAL);
						break;
					default:
						break;
				}
			}
			// 没触雷的情况下，被挖开的雷块等于总雷块数减去雷数，游戏胜利
			if (digBlockNum == cols * rows - mineNum) win();
		}
	}
	
	/**
	 * 获取计时器的时间
	 * 
	 * @return
	 */
	public int getTime()
	{
		return timer.getTime();
	}
	
	/**
	 * 开始新游戏
	 */
	private void startNewGame(int x, int y)
	{
		// 将状态设置为游戏开始了
		Main.isStart = true;
		// 开始计时
		timer.start();
		// 开始布置地雷
		game.startGame(x, y, rows, cols, mineNum, list);
	}
	
	/**
	 * 游戏胜利
	 */
	private void win()
	{
		isSame = false;
		// 停止计时
		timer.stop();
		// 弹出对话框
		GameResultDialog dialog = new GameResultDialog(new SweepMineFrame(),
				this, "游戏胜利", true, timer.getTime());
		dialog.setVisible(true);
	}
	
	/**
	 * 游戏失败
	 */
	private void fail()
	{
		isSame = false;
		// 触雷，游戏失败，弹出对话框，停止计时
		timer.stop();
		digAllBlock();
		// 弹出游戏失败对话框
		GameResultDialog dialog = new GameResultDialog(new SweepMineFrame(),
				this, "游戏失败", false, timer.getTime());
		dialog.setVisible(true);
	}
	
	/**
	 * 重新布置扫雷区
	 */
	public void updateMineArea()
	{
		digBlockNum = 0;
		// 清除列表中存储的所有雷块对象
		list.clear();
		// 移除所有扫雷区中的控件
		removeAll();
		invalidate();
		// 重新布置扫雷区
		addMineBlock();
		addTimerAndCount();
		validate();
		if (Main.isTest) System.out.println("isStart=" + Main.isStart
				+ ",isSame=" + isSame);
	}
}
