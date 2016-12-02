package help;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 查看帮助的窗口框架
 * 
 * @author tang
 * @time 2016-9-25
 */
public class HelpFrame extends JFrame
{
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 500;
	
	private int y = 0;
	private JPanel panel;
	private GridBagConstraints constraints;
	
	public HelpFrame()
	{
		// 标题
		setTitle("查看帮助");
		// 图标
		setIconImage(new ImageIcon("image/lei.png").getImage());
		// 窗体大小
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		// 字体颜色
		Color blue = new Color(0x4e, 0x63, 0xca);
		// 网格组布局
		GridBagLayout layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		// 左对齐
		constraints.anchor = GridBagConstraints.WEST;
		// 布局边距：上左下右都为5
		constraints.insets = new Insets(5, 5, 5, 5);
		// 横向跨10个格
		constraints.weightx = 10;
		// 水平填充
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		panel = new JPanel(layout);
		
		JScrollPane scrollPane = new JScrollPane(panel);
		
		addLabel(18, blue, "扫雷：玩法\n");
		
		addTextArea("扫雷是一种具有迷惑性的对记忆和推理能力的简单测试，它是长久以来最受欢迎的Windows游戏之一。游戏目标：找出空方块并避免触雷。\n");
		
		addLabel(14, blue, "启动游戏的步骤:");
		
		addTextArea("1. 打开“扫雷”游戏。\n2. 选择一个难度级别：初级、中级或高级。\n3. 若要开始，请单击一个方块。");
		
		addLabel(14, blue, "保存游戏的步骤：");
		
		addTextArea("如果需要稍后完成游戏，可以退出该游戏，然后单击“保存”。下次玩该游戏时，系统会询问您是否继续玩上次的游戏。如果继续，请单击“是”。");
		
		addLabel(14, blue, "更改游戏选项的步骤：");
		
		addTextArea("您可以调整难度级别、打开和关闭动画等。\n1. 打开“扫雷”游戏。\n2. 单击“游戏”菜单，然后单击“选项”。\n3. 进行选择，然后单击“确定”。\n");
		
		addLabel(14, blue, "自定义游戏外观的步骤：");
		
		addTextArea("您可以更改扫雷区颜色，以及是否隐藏地雷或鲜花。\n1. 打开“扫雷”游戏。\n2. 单击“游戏”菜单，然后单击“更改外观”。\n3. 进行选择，然后单击“确定”。\n");
		
		addLabel(16, Color.BLACK, "扫雷：规则和基本要求\n");
		
		addLabel(14, Color.BLACK, "游戏目标");
		
		addTextArea("找出空方块，同时避免触雷。清除扫雷区的速度越快，得分就越高。\n");
		
		addLabel(14, Color.BLACK, "扫雷区");
		
		addTextArea("扫雷有三个标准扫雷区可供选择，各扫雷区的扫雷难度依次递增。\n初级：81个方块、10个雷。\n中级：256个方块、40个雷。\n高级：480个方块、99个雷。\n还可以通过单击“游戏”菜单，然后单击“选项”创建自定义扫雷区。扫雷游戏支持最多有720个方块和668个雷的扫雷区。\n");
		
		addLabel(14, Color.BLACK, "玩法");
		
		addTextArea("扫雷的规则非常简单：\n挖开地雷，游戏即告结束。\n挖开空方块，可以继续玩。\n挖开数字，则表示在其周围的八个方块中共有多少个雷，可以使用该信息推断能够安全单击附近的哪些方块。\n");
		
		addLabel(14, Color.BLACK, "提示与技巧");
		
		addTextArea("标记地雷。若果您认为某个方块可能藏有地雷，请鼠标右键单击它。这会在该方块上做一个旗标。（如果不确定，请再次鼠标右键单击标记为问号。）\n研究图案。如果一行中有三个方块显示为2-3-2，您就会知道该行旁边可能排列这三个雷。如果一方块显示为8，则它周围的每个方块下面都有一个雷。\n浏览未探测的。如果不确定下一个单位位置，可以尝试清除某些未探测的区域。在未标记方块的中间单击比在可能有雷的区域单击要好一些。");
		
		setContentPane(scrollPane);
	}
	
	/**
	 * 添加标签
	 * 
	 * @param size
	 *            字体大小
	 * @param color
	 *            字体颜色
	 * @param text
	 *            文本
	 */
	public void addLabel(int size, Color color, String text)
	{
		JLabel label = new JLabel(text);
		// 样式
		label.setFont(new Font(null, Font.BOLD, size));
		// 字体颜色
		label.setForeground(color);
		// 位置
		constraints.gridx = 0;
		constraints.gridy = y;
		
		panel.add(label, constraints);
		y++;
	}
	
	/**
	 * 添加文本域
	 * 
	 * @param text
	 *            文本域中要显示的文本
	 */
	public void addTextArea(String text)
	{
		JTextArea textArea = new JTextArea(text);
		// 设置样式
		textArea.setFont(new Font(null, Font.PLAIN, 14));
		// 背景设置为透明的
		textArea.setOpaque(false);
		// 内容过长时自动换行
		textArea.setLineWrap(true);
		// 文本不可修改
		textArea.setEditable(false);
		// 位置
		constraints.gridx = 0;
		constraints.gridy = y;
		
		panel.add(textArea, constraints);
		y++;
	}
}
