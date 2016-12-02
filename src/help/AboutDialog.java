package help;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JTextArea;

/**
 * 关于“扫雷”的对话框
 * 
 * @author tang
 * @time 2016-9-30
 */
public class AboutDialog extends JDialog
{
	public static final String VERSION = "1.0";
	private final int DEFAULT_WIDTH = 457;
	private final int DEFAULT_HEIGHT = 376;
	
	public AboutDialog(Frame owner)
	{
		super(owner, "关于“扫雷”", true);
		// 大小
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		// 不可更改大小
		setResizable(false);
		String aboutSaoleiStr = "名称：扫雷游戏\n作者：唐雪芸\n版本:" + VERSION
				+ "\n说明：该游戏是仿照Windows7系统自带的“扫雷”游戏开发的，仅供娱乐和学习，不能用于商业";
		
		JTextArea textArea = new JTextArea(aboutSaoleiStr);
		
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.setFont(new Font(null, Font.BOLD, 17));
		
		add(textArea);
	}
}
