package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sweepmine.Main;
import sweepmine.MineArea;
import sweepmine.SweepMineFrame;
import utils.Setting;

/**
 * 新游戏对话框
 * 
 * @author tang
 * @time 2016-10-5
 */
public class NewGameDialog extends JDialog
{
	private Color blue;
	private JPanel panel;
	private ButtonGroup group;
	
	private int grade;
	
	public static void main(String[] args)
	{
		NewGameDialog dialog = new NewGameDialog(new SweepMineFrame(),
				new MineArea());
		dialog.setVisible(true);
	}
	
	public NewGameDialog(JFrame owner, final MineArea area)
	{
		super(owner, "新游戏", true);
		
		setSize(370, 260);
		setResizable(false);
		
		blue = new Color(0x4e, 0x63, 0xca);
		
		grade = Setting.getGrade();
		
		JLabel label = new JLabel("<html><h2>游戏正在进行，您希望做什么？</h2></html>");
		label.setForeground(blue);
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
		add(label, BorderLayout.NORTH);
		
		panel = new JPanel(new GridLayout(3, 1));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		group = new ButtonGroup();
		
		addButton("<html><h4>退出并开始新游戏(N)<br></h4>本局将作为失败计入统计信息。</html>", 'N',
				130, new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						setVisible(false);
						Setting.addGameCount(grade);
						area.updateMineArea();
						Main.isStart = false;
					}
				});
		addButton("<html><h4>重新开始这个游戏(R)<br></h4>本局将作为失败计入统计信息。</html>", 'R',
				130, new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						setVisible(false);
						Setting.addGameCount(grade);
						area.updateMineArea();
						area.isSame = true;
						Main.isStart = true;
					}
				});
		
		addButton("<html><h4>继续游戏(K)</h4></html>", 'K', 220,
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						setVisible(false);
					}
				});
		
		add(panel);
	}
	
	private void addButton(String title, char c, int right,
			ActionListener listener)
	{
		// 获取按钮图标资源
		ImageIcon icon = new ImageIcon("src/image/arrow.png");
		if (icon == null) return;
		JButton btn = new JButton(title, icon);
		// 按钮背景透明
		btn.setContentAreaFilled(false);
		// 设置快捷键
		btn.setMnemonic(c);
		// 设置字体颜色
		btn.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, right));
		btn.setForeground(blue);
		btn.addActionListener(listener);
		group.add(btn);
		panel.add(btn);
	}
}
