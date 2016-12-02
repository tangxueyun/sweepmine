package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import sweepmine.SweepMineFrame;
import utils.GBC;
import utils.Setting;

/**
 * 统计信息的对话框
 * 
 * @author tang
 * @time 2016-10-5
 */
public class StatisticalInfoDialog extends JDialog
{
	private final int WIDTH = 690;
	private final int HEIGHT = 240;
	
	private JPanel mPanel;
	private ButtonGroup group;
	private JTextArea timeTextArea;
	
	private JRadioButton primaryBtn;
	private JRadioButton mediumBtn;
	private JRadioButton seniorBtn;
	
	private JLabel gameCount;
	private JLabel winCount;
	private JLabel winRate;
	private JLabel straightCount;
	private JLabel failCount;
	private JLabel currentStraightCount;
	
	public static void main(String[] args)
	{
		Setting setting = new Setting();
		StatisticalInfoDialog dialog = new StatisticalInfoDialog(
				new SweepMineFrame());
		dialog.setVisible(true);
	}
	
	public StatisticalInfoDialog(Frame owner)
	{
		super(owner, "扫雷统计信息", true);
		// 设置对话框大小
		setSize(WIDTH, HEIGHT);
		// 设置对话框的位置处于父框架的中间
		setLocation(owner.getX() + (owner.getWidth() - getWidth()) / 2,
				owner.getY() + (owner.getHeight() - getHeight()) / 2);
		// 不可更改对话框大小
		setResizable(false);
		
		mPanel = new JPanel(new GridLayout(1, 3));
		mPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		group = new ButtonGroup();
		
		addRadioButtonPanel();
		
		addBestTimePanel();
		
		addInfoPanel();
		
		primaryBtn.setSelected(true);
		setInfo(Setting.PRIMARY);
		
		add(mPanel, BorderLayout.CENTER);
		addButtons();
	}
	
	/**
	 * 添加单选按钮面板
	 */
	private void addRadioButtonPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		// 初级单选按钮
		primaryBtn = new JRadioButton("初级");
		primaryBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (primaryBtn.isSelected()) setInfo(Setting.PRIMARY);
			}
		});
		panel.add(primaryBtn, new GBC(0, 0).setIpad(150, 0));
		group.add(primaryBtn);
		// 中级单选按钮
		mediumBtn = new JRadioButton("中级");
		mediumBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (mediumBtn.isSelected()) setInfo(Setting.MEDIUM);
			}
		});
		panel.add(mediumBtn, new GBC(0, 1).setIpad(150, 0));
		group.add(mediumBtn);
		// 高级单选按钮
		seniorBtn = new JRadioButton("高级");
		seniorBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (seniorBtn.isSelected()) setInfo(Setting.SENIOR);
			}
		});
		panel.add(seniorBtn, new GBC(0, 2).setIpad(150, 0));
		group.add(seniorBtn);
		
		mPanel.add(panel);
	}
	
	private void addBestTimePanel()
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "最佳时间"));
		timeTextArea = new JTextArea();
		timeTextArea.setOpaque(false);
		timeTextArea.setEditable(false);
		timeTextArea.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		panel.add(timeTextArea);
		mPanel.add(panel);
	}
	
	private void addInfoPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		gameCount = new JLabel();
		panel.add(gameCount, new GBC(0, 0).setWeight(100, 1)
				.setAnchor(GBC.WEST));
		
		winCount = new JLabel();
		panel.add(winCount, new GBC(0, 1).setWeight(100, 1).setAnchor(GBC.WEST));
		
		winRate = new JLabel();
		panel.add(winRate, new GBC(0, 2).setWeight(100, 1).setAnchor(GBC.WEST));
		
		straightCount = new JLabel();
		panel.add(straightCount,
				new GBC(0, 3).setWeight(100, 1).setAnchor(GBC.WEST));
		
		failCount = new JLabel();
		panel.add(failCount, new GBC(0, 4).setWeight(100, 1)
				.setAnchor(GBC.WEST));
		
		currentStraightCount = new JLabel();
		panel.add(currentStraightCount, new GBC(0, 5).setWeight(100, 1)
				.setAnchor(GBC.WEST));
		
		mPanel.add(panel);
	}
	
	private void addButtons()
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 8));
		// 关闭按钮：隐蔽对话框
		JButton closeBtn = new JButton("   关闭(C)   ");
		closeBtn.setMnemonic('C');
		closeBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		panel.add(closeBtn);
		// 重置按钮：清空所有统计信息
		JButton resetBtn = new JButton("   重置(R)   ");
		resetBtn.setMnemonic('R');
		resetBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Setting.clearGradeInfo();
				setInfo(Setting.PRIMARY);
			}
		});
		panel.add(resetBtn);
		
		add(panel, BorderLayout.SOUTH);
	}
	
	private void setInfo(int grade)
	{
		timeTextArea.setText("");
		for (int i = 0; i < 5; i++)
		{
			int time = Setting.getBestTime(grade, i);
			if (time != 0)
			{
				timeTextArea.append(time + "     "
						+ Setting.getBestTimeDate(grade, i) + "\n");
			}
		}
		int gameCounts = Setting.getGameCount(grade);
		gameCount.setText("已玩游戏：" + gameCounts);
		
		int winCounts = Setting.getWinCount(grade);
		winCount.setText("已胜游戏：" + winCounts);
		
		int rate = (int) ((float) winCounts / gameCounts * 100);
		winRate.setText("获胜率：" + rate + "%");
		
		straightCount.setText("最多连胜：" + Setting.getStraightCount(grade));
		
		failCount.setText("最多连败：" + Setting.getFailCount(grade));
		
		currentStraightCount.setText("当前连局："
				+ Setting.getCurrentStraightCount(grade));
	}
}
