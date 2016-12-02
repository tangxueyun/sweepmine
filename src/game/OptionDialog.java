package game;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.security.acl.Owner;

import javafx.scene.control.RadioButton;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.sun.org.apache.bcel.internal.generic.NEW;

import sweepmine.Main;
import sweepmine.MineArea;
import sweepmine.SweepMineFrame;
import utils.GBC;
import utils.Setting;

import Component.NumberTextField;

/**
 * 选项对话框
 * 
 * @author tang
 * @time 2016-10-5
 */
public class OptionDialog extends JDialog
{
	private final int WIDTH = 445;
	private final int HEIGHT = 544;
	
	private JRadioButton primaryBtn;
	private JRadioButton mediumBtn;
	private JRadioButton seniorBtn;
	private JRadioButton customBtn;
	
	private NumberTextField rowsField;
	private NumberTextField colsField;
	private NumberTextField mineNumField;
	
	private JCheckBox flashBox;
	private JCheckBox voiceBox;
	private JCheckBox tipBox;
	private JCheckBox saveBox;
	private JCheckBox exitSaveBox;
	private JCheckBox wenhaoBox;
	
	private ButtonGroup group;
	
	private int rows;
	private int cols;
	private int mineNum;
	private int grade;
	
	private boolean isFlash;
	private boolean isVoice;
	private boolean isTip;
	private boolean isSave;
	private boolean isExitSave;
	private boolean isWenhao;
	
	private MineArea mineArea;
	
	public static void main(String[] args)
	{
		Setting setting = new Setting();
		OptionDialog dialog = new OptionDialog(new SweepMineFrame(),
				new MineArea());
		dialog.setVisible(true);
	}
	
	public OptionDialog(Frame owner, MineArea area)
	{
		super(owner, "选项", true);
		// 设置对话框的宽高
		setSize(WIDTH, HEIGHT);
		// 设置对话框的位置始终处于父框架的中间
		setLocation(owner.getX() + (owner.getWidth() - getWidth()) / 2,
				owner.getY() + (owner.getHeight() - getHeight()) / 2);
		// 对话框的大小不能更改
		setResizable(false);
		
		setLayout(new GridBagLayout());
		
		getInitValue();
		
		mineArea = area;
		
		group = new ButtonGroup();
		addGradePanel();
		addOptionPanel();
		addButtons();
		
		init();
		
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				if (grade == 0) System.exit(0);
				else setVisible(false);
			}
		});
	}
	
	/**
	 * 获取初始值
	 */
	private void getInitValue()
	{
		rows = Setting.getRows();
		cols = Setting.getCols();
		mineNum = Setting.getMineNum();
		grade = Setting.getGrade();
		
		isFlash = Setting.isFalsh();
		isVoice = Setting.isVoice();
		isTip = Setting.isTip();
		isSave = Setting.isSave();
		isExitSave = Setting.isExitSave();
		isWenhao = Setting.isWenhao();
	}
	
	/**
	 * 设置各个控件的初始化状态
	 */
	private void init()
	{
		switch (grade)
		{
			case Setting.PRIMARY:
				primaryBtn.setSelected(true);
				setCustonPanelEnabel(false);
				break;
			case Setting.MEDIUM:
				mediumBtn.setSelected(true);
				setCustonPanelEnabel(false);
				break;
			case Setting.SENIOR:
				seniorBtn.setSelected(true);
				setCustonPanelEnabel(false);
				break;
			case Setting.CUSTOM:
				customBtn.setSelected(true);
				setCustonPanelEnabel(true);
				break;
			default:
				setCustonPanelEnabel(false);
				break;
		}
		
		if (isWenhao) wenhaoBox.setSelected(true);
		else wenhaoBox.setSelected(false);
	}
	
	/**
	 * 添加四个难度等级的单选按钮
	 */
	private void addGradePanel()
	{
		JPanel diffcultyPanel = new JPanel();
		diffcultyPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "难度"));
		diffcultyPanel.setLayout(new GridBagLayout());
		
		// 初级单选按钮
		primaryBtn = new JRadioButton(
				"<html>初级(<u>B</u>)<br>10个雷<br>9x9平铺网格</html>");
		setRadioBtnStyle(primaryBtn, 'B');
		diffcultyPanel.add(primaryBtn,
				new GBC(0, 0).setIpad(70, 0).setInsets(10, 0, 0, 0));
		primaryBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (primaryBtn.isSelected())
				{
					grade = Setting.PRIMARY;
					rows = 9;
					cols = 9;
					mineNum = 10;
					setCustonPanelEnabel(false);
				}
			}
		});
		
		// 中级单选按钮
		mediumBtn = new JRadioButton(
				"<html>中级(<u>I</u>)<br>40个雷<br>16x16平铺网格</html>");
		setRadioBtnStyle(mediumBtn, 'I');
		diffcultyPanel.add(mediumBtn,
				new GBC(0, 1).setIpad(68, 0).setInsets(0, 5, 0, 0));
		mediumBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (mediumBtn.isSelected())
				{
					grade = Setting.MEDIUM;
					rows = 16;
					cols = 16;
					mineNum = 40;
					setCustonPanelEnabel(false);
				}
			}
		});
		
		// 高级单选按钮
		seniorBtn = new JRadioButton(
				"<html>高级(<u>V</u>)<br>99个雷<br>16x30平铺网格</html>");
		setRadioBtnStyle(seniorBtn, 'V');
		diffcultyPanel.add(seniorBtn,
				new GBC(0, 2).setIpad(68, 0).setInsets(0, 5, 10, 0));
		seniorBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (seniorBtn.isSelected())
				{
					grade = Setting.SENIOR;
					rows = 16;
					cols = 30;
					mineNum = 99;
					setCustonPanelEnabel(false);
				}
			}
		});
		
		// 自定义单选按钮
		customBtn = new JRadioButton("<html>自定义(<u>U</u>)</html>");
		customBtn.setEnabled(false);
		setRadioBtnStyle(customBtn, 'U');
		diffcultyPanel.add(customBtn, new GBC(1, 0).setIpad(70, 0));
		customBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				grade = Setting.CUSTOM;
				if (customBtn.isSelected()) setCustonPanelEnabel(true);
			}
		});
		
		// 自定义高度
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		rowsField = new NumberTextField(
				"<html>高度(9~24)(<u>H</u>):      <html>", rows, 'H');
		panel.add(rowsField);
		// 自定义宽度
		colsField = new NumberTextField("<html>宽度(9~30)(<u>W</u>):</html>",
				cols, 'W');
		panel.add(colsField);
		// 自定义雷数
		mineNumField = new NumberTextField(
				"<html>雷数(10~668)(<u>M</u>):</html>", mineNum, 'M');
		panel.add(mineNumField);
		
		diffcultyPanel.add(panel, new GBC(1, 1).setInsets(0, 40, 0, 0));
		
		add(diffcultyPanel, new GBC(0, 0));
	}
	
	/**
	 * 添加多个游戏设置复选框
	 */
	private void addOptionPanel()
	{
		// 显示动画的复选框
		flashBox = new JCheckBox("<html>显示动画(<u>D</u>)</html>");
		flashBox.setMnemonic('D');
		flashBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (flashBox.isSelected()) isFlash = true;
				else isFlash = false;
			}
		});
		add(flashBox, new GBC(0, 1).setAnchor(GBC.WEST).setInsets(10, 5, 5, 0));
		// 该模块功能尚未开发，无法使用
		flashBox.setEnabled(false);
		
		// 播放声音的复选框
		voiceBox = new JCheckBox("<html>播放声音(<u>P</u>)</html>");
		voiceBox.setMnemonic('P');
		add(voiceBox, new GBC(0, 2).setAnchor(GBC.WEST).setInsets(5, 5, 5, 0));
		// 该模块功能尚未开发，无法使用
		voiceBox.setEnabled(false);
		
		// 显示提示的复选框
		tipBox = new JCheckBox("<html>显示提示(<u>S</u>)</html>");
		tipBox.setMnemonic('S');
		add(tipBox, new GBC(0, 3).setAnchor(GBC.WEST).setInsets(5, 5, 5, 0));
		// 该模块功能尚未开发，无法使用
		tipBox.setEnabled(false);
		
		// 始终继续保存的游戏的复选框
		saveBox = new JCheckBox("<html>始终继续保存的游戏(<u>C</u>)</html>");
		saveBox.setMnemonic('C');
		add(saveBox, new GBC(0, 4).setAnchor(GBC.WEST).setInsets(5, 5, 5, 0));
		// 该模块功能尚未开发，无法使用
		saveBox.setEnabled(false);
		
		// 退出时始终保存游戏的复选框
		exitSaveBox = new JCheckBox("<html>退出时始终保存游戏(<u>A</u>)</html>");
		exitSaveBox.setMnemonic('A');
		add(exitSaveBox, new GBC(0, 5).setAnchor(GBC.WEST)
				.setInsets(5, 5, 5, 0));
		// 该模块功能尚未开发，无法使用
		exitSaveBox.setEnabled(false);
		
		// 允许问号的复选框
		wenhaoBox = new JCheckBox("<html>允许问号(双击右键)(<u>Q</u>)</html>");
		wenhaoBox.setMnemonic('Q');
		wenhaoBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (wenhaoBox.isSelected()) isWenhao = true;
				else isWenhao = false;
			}
		});
		add(wenhaoBox, new GBC(0, 6).setAnchor(GBC.WEST).setInsets(5, 5, 5, 0));
	}
	
	private void addButtons()
	{
		// 确定按钮：保存信息，关闭对话框
		JButton confirmBtn = new JButton("   确定   ");
		confirmBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (grade == 0)
				{
					JOptionPane.showMessageDialog(new SweepMineFrame(),
							"请选择一个游戏难度", "警告", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if (grade == Setting.CUSTOM)
					{
						rows = rowsField.getText();
						cols = colsField.getText();
						mineNum = mineNumField.getText();
					}
					
					Setting.setGrade(grade, rows, cols, mineNum);
					Setting.saveOptionSetting(isFlash, isVoice, isTip, isSave,
							isExitSave, isWenhao);
					setVisible(false);
					
					Main.isStart = false;
					mineArea.getValues();
					mineArea.updateMineArea();
				}
			}
		});
		add(confirmBtn, new GBC(0, 7).setInsets(20, 100, 0, 0));
		// 取消按钮：关闭对话框
		JButton cancelBtn = new JButton("   取消   ");
		cancelBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (grade == 0)
				{
					JOptionPane.showMessageDialog(new SweepMineFrame(),
							"请选择一个游戏难度", "警告", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					setVisible(false);
				}
			}
		});
		add(cancelBtn, new GBC(0, 7).setAnchor(GBC.EAST)
				.setInsets(20, 0, 0, 15));
	}
	
	/**
	 * 自定义难度的设置面板是否可用<br>
	 * 自定义单选按钮被选中时可用，否则不可用
	 * 
	 * @param b
	 *            true:可用 false:不可用
	 */
	private void setCustonPanelEnabel(boolean b)
	{
		colsField.setTextEnable(b);
		rowsField.setTextEnable(b);
		mineNumField.setTextEnable(b);
	}
	
	/**
	 * 设置单选按钮样式
	 * 
	 * @param jrb
	 *            单选按钮
	 * @param key
	 *            单选按钮快捷键字符
	 */
	private void setRadioBtnStyle(JRadioButton jrb, char key)
	{
		jrb.setFocusPainted(false);
		jrb.setMnemonic(key);
		group.add(jrb);
	}
}
