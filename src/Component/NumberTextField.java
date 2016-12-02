package Component;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import utils.GBC;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 带标签的文本域<br>
 * 1、只接受数字,且只能输入5个<br>
 * 2、获取到焦点时，文本域的文本被全选<br>
 * 3、使用快捷键能快速获取焦点
 * 
 * @author tang
 * @time 2016-10-31
 */
public class NumberTextField extends JPanel
{
	private JTextField textField;
	private JLabel label;
	
	/**
	 * 构造器
	 * 
	 * @param title
	 *            标签控件的标题
	 * @param text
	 *            文本域的文本
	 * @param key
	 *            标签快捷键的字符
	 */
	public NumberTextField(String title, int text, char key)
	{
		// 设置空白边距
		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		// 设置为1行两列的网格布局
		setLayout(new GridLayout(1, 2));
		
		textField = new JTextField(5);
		// 凹陷边距
		textField.setBorder(BorderFactory.createLoweredBevelBorder());
		// 添加聚焦事件监听器
		textField.addFocusListener(new FocusAction());
		// 添加键盘事件监听器
		textField.addKeyListener(new InputAction());
		// 初始化文本
		textField.setText("" + text);
		
		label = new JLabel(title);
		// 为标签添加快捷键
		label.setDisplayedMnemonic(key);
		// 将标签设置为textField的
		label.setLabelFor(textField);
		label.setHorizontalTextPosition(SwingConstants.LEFT);
		add(label);
		add(textField);
	}
	
	/**
	 * 重写聚焦适配器：获取焦点时，文本域内的文本全被选中
	 */
	class FocusAction extends FocusAdapter
	{
		@Override
		public void focusGained(FocusEvent e)
		{
			textField.selectAll();
		}
	}
	
	/**
	 * 重写键盘适配器：只接受5个数字输入，其他输入被屏蔽
	 */
	class InputAction extends KeyAdapter
	{
		@Override
		public void keyTyped(KeyEvent e)
		{
			String str = textField.getText();
			if (e.getKeyChar() < KeyEvent.VK_0
					|| e.getKeyChar() > KeyEvent.VK_9 || str.length() > 4) e
					.consume();
			
		}
	}
	
	/**
	 * 文本域是否可用<br>
	 * 可用：文本域可输入，标签和文本域的文本为白色<br>
	 * 不可用：文本域不可输入，标签和文本域的文本变灰色<br>
	 * 
	 * @param b
	 *            false：不可用 true:可用
	 */
	public void setTextEnable(boolean b)
	{
		// 文本域是否可编辑
		textField.setEditable(b);
		// 文本域是否可获取焦点
		textField.setEnabled(b);
		// 标签是否可获取焦点（变灰色）
		label.setEnabled(b);
		// true:文本域的文本为黑色
		// false:文本域的文本为灰色
		if (b) textField.setForeground(Color.BLACK);
		else textField.setForeground(Color.GRAY);
	}
	
	/**
	 * 设置文本域的文本<br>
	 * 将整型的参数隐形转化为String类型
	 * 
	 * @param text
	 *            文本
	 */
	public void setText(int text)
	{
		textField.setText("" + text);
	}
	
	/**
	 * 获取文本域中的文本<br>
	 * 将String类型的位版本转化为整型
	 * 
	 * @return 整型文本
	 */
	public int getText()
	{
		return Integer.parseInt(textField.getText());
	}
}
