package utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * 简化了GridBagLayout的使用
 * 
 * @author tang
 * @time 2016-11-14
 */
public class GBC extends GridBagConstraints
{
	/**
	 * 带网格位置的GBC构造器
	 * 
	 * @param gridx
	 *            gridx位置
	 * @param gridy
	 *            gridy位置
	 */
	public GBC(int gridx, int gridy)
	{
		this.gridx = gridx;
		this.gridy = gridy;
	}
	
	/**
	 * 带网格位置和网格跨度的构造器
	 * 
	 * @param gridx
	 *            x方向的网格位置
	 * @param gridy
	 *            y方向的网格位置
	 * @param gridwidth
	 *            x方向跨度的网格数
	 * @param gridheight
	 *            y方向跨度的网格数
	 */
	public GBC(int gridx, int gridy, int gridwidth, int gridheight)
	{
		this.gridx = gridx;
		this.gridy = gridy;
		this.gridwidth = gridwidth;
		this.gridheight = gridheight;
	}
	
	/**
	 * 设置对齐方式
	 * 
	 * @param anchor
	 *            对齐方式的值
	 * @return 返回设置后的对齐方式
	 */
	public GBC setAnchor(int anchor)
	{
		this.anchor = anchor;
		return this;
	}
	
	/**
	 * 设置填充方向
	 * 
	 * @param fill
	 *            填充方向
	 * @return 返回设置后的填充方向
	 */
	public GBC setFill(int fill)
	{
		this.fill = fill;
		return this;
	}
	
	/**
	 * 设置单元格的权重
	 * 
	 * @param weightx
	 *            x方向的权重
	 * @param weighty
	 *            y方向的权重
	 * @return
	 */
	public GBC setWeight(double weightx, double weighty)
	{
		this.weightx = weightx;
		this.weighty = weighty;
		return this;
	}
	
	/**
	 * 设置单元格的外边距
	 * 
	 * @param distance
	 *            外边距
	 * @return 返回设置后的外边距
	 */
	public GBC setInsets(int distance)
	{
		this.insets = new Insets(distance, distance, distance, distance);
		return this;
	}
	
	/**
	 * 设置单元格的外边距
	 * 
	 * @param top
	 *            上外边距
	 * @param left
	 *            左外边距
	 * @param bottom
	 *            下外边距
	 * @param right
	 *            右外边距
	 * @return
	 */
	public GBC setInsets(int top, int left, int bottom, int right)
	{
		this.insets = new Insets(top, left, bottom, right);
		return this;
	}
	
	/**
	 * 设置内边距
	 * 
	 * @param ipadx
	 *            x方向的内边距
	 * @param ipady
	 *            y方向的内边距
	 * @return
	 */
	public GBC setIpad(int ipadx, int ipady)
	{
		this.ipadx = ipadx;
		this.ipady = ipady;
		return this;
	}
}
