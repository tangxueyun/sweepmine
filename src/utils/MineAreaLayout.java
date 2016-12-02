package utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * 扫雷区布局
 * 
 * @author tang
 * @time 2016-10-5
 */
public class MineAreaLayout implements LayoutManager
{
	private int rows;
	private int cols;
	private int minWidth;
	private int minHeight;
	private int preferredWidth;
	private int perferredHeight;
	private int marrgin = 30;
	private boolean setSize = false;
	
	public MineAreaLayout(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
	}
	
	public void setSizes(Container parent)
	{
		if (setSize) return;
		Component c = parent.getComponent(0);
		if (c.isVisible())
		{
			minWidth = c.getMinimumSize().width * cols;
			minHeight = c.getMinimumSize().height * rows;
			preferredWidth = c.getPreferredSize().width * cols + 2 * marrgin;
			perferredHeight = c.getPreferredSize().height * rows + marrgin;
		}
		setSize = true;
	}
	
	public void addLayoutComponent(String name, Component comp)
	{
		
	}
	
	public void removeLayoutComponent(Component comp)
	{
		
	}
	
	public Dimension preferredLayoutSize(Container parent)
	{
		setSizes(parent);
		Insets insets = parent.getInsets();
		int width = preferredWidth + insets.left + insets.right;
		int height = perferredHeight + insets.top + insets.bottom;
		
		return new Dimension(width, height);
	}
	
	public Dimension minimumLayoutSize(Container parent)
	{
		setSizes(parent);
		Insets insets = parent.getInsets();
		int width = minWidth + insets.left + insets.right;
		int height = minHeight + insets.top + insets.bottom;
		return new Dimension(width, height);
	}
	
	/**
	 * 摆放容器内的组件
	 */
	public void layoutContainer(Container parent)
	{
		setSizes(parent);
		
		Insets insets = parent.getInsets();
		int containerWidth = parent.getSize().width - insets.left
				- insets.right;
		int containerHeight = parent.getSize().height - insets.top
				- insets.bottom;
		Component c = parent.getComponent(0);
		int left = (containerWidth - (c.getPreferredSize().width * cols)) / 2;
		int top = (containerHeight - (c.getPreferredSize().height * rows)) / 2;
		
		int n = parent.getComponentCount();
		for (int i = 0; i < n; i++)
		{
			Component component = parent.getComponent(i);
			if (component.isVisible())
			{
				int rowNum = i / cols;
				int colNum = i % cols;
				Dimension d = component.getPreferredSize();
				int x = d.width * colNum + left;
				int y = d.height * rowNum + top;
				component.setBounds(x, y, d.width, d.height);
			}
		}
	}
}
