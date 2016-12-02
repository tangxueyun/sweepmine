package sweepmine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.RowSet;

import Component.MineBlock;

/**
 * 扫雷核心算法
 * 
 * @author tang
 * @time 2016-10-4
 */
public class SweepMine
{
	/******************** 游戏难度级别 ****************************/
	// 自定义，9~24*9~30,10~668个雷
	public static final int CUSTOM = 0;
	// 初级，9*9,10个雷
	public static final int PRIMARY = 1;
	// 中级，16*16,40个雷
	public static final int MEDIUM = 2;
	// 高级，16*30,99个雷
	public static final int SENIOR = 3;
	
	int[][] mineMartrix, martrix;
	int rows;
	int cols;
	List<MineBlock> list;
	
	/**
	 * 开始游戏
	 * 
	 * @param xNum
	 * @param yNum
	 * @param r
	 * @param c
	 * @param mineNum
	 * @param l
	 */
	public void startGame(int xNum, int yNum, int r, int c, int mineNum,
			List<MineBlock> l)
	{
		rows = r;
		cols = c;
		
		System.out.println("rows=" + rows + ",cols=" + cols);
		list = new ArrayList<MineBlock>();
		list = l;
		
		createMineAreaMatrix(xNum, yNum, mineNum);
	}
	
	/**
	 * 生成扫雷区矩阵 （1：地雷 2：没有雷）
	 * 
	 * @param rows
	 *            矩阵的行数
	 * @param cols
	 *            矩阵的类书
	 * @param mineNum
	 *            扫雷区的雷数
	 * @return 生成的矩阵
	 */
	private void createMineAreaMatrix(int xNum, int yNum, int mineNum)
	{
		mineMartrix = new int[rows][cols];
		for (int i = 0; i < mineNum; i++)
		{
			// Math.random()生成一个[0,1)的随机数，确保x,y都在矩阵内
			int x = (int) (Math.random() * rows);
			int y = (int) (Math.random() * cols);
			// mineMartrix[x][y]等于1时，不累计雷数，确保矩阵有mineNum个雷
			// 矩阵中在xNum,yNum处（第一次点击的位置）不等于1，确保第一次点击一定不会触雷
			if (mineMartrix[x][y] == 1 || (x == xNum && y == yNum)) i--;
			// 该位置不等于1时，将矩阵的相应位置设置为1
			else mineMartrix[x][y] = 1;
		}
		setMineArea(list);
	}
	
	/**
	 * 计算一个3*3区域内的总雷数
	 * 
	 * @param x
	 *            3*3区域中心的x位置
	 * @param y
	 *            3*3区域中心的y位置
	 * @return 总雷数
	 */
	private int countMineNum(int x, int y)
	{
		int n = 0;
		if (mineMartrix[x][y] == 1) n = -1;
		else
		{
			// 中间
			if (x != 0 && y != 0 && x != rows - 1 && y != cols - 1)
			{
				for (int i = x - 1; i < x + 2; i++)
				{
					for (int j = y - 1; j < y + 2; j++)
					{
						n += mineMartrix[i][j];
					}
				}
			}
			// 左上角
			else if (x == 0 && y == 0)
			{
				for (int i = x; i < x + 2; i++)
				{
					for (int j = y; j < y + 2; j++)
					{
						n += mineMartrix[i][j];
					}
				}
			}
			// 左下角
			else if (x == rows - 1 && y == 0)
			{
				for (int i = x - 1; i < x + 1; i++)
				{
					for (int j = y; j < y + 2; j++)
					{
						n += mineMartrix[i][j];
					}
				}
			}
			// 右上角
			else if (x == 0 && y == cols - 1)
			{
				for (int i = x; i < x + 2; i++)
				{
					for (int j = y - 1; j < y + 1; j++)
					{
						n += mineMartrix[i][j];
					}
				}
			}
			// 右下角
			else if (x == rows - 1 && y == cols - 1)
			{
				for (int i = x - 1; i < x + 1; i++)
				{
					for (int j = y - 1; j < y + 1; j++)
					{
						n += mineMartrix[i][j];
					}
				}
			}
			// 上边
			else if (x == 0 && y != 0)
			{
				for (int i = x; i < x + 2; i++)
				{
					for (int j = y - 1; j < y + 2; j++)
					{
						n += mineMartrix[i][j];
					}
				}
			}
			// 左边
			else if (y == 0 && x != 0)
			{
				for (int i = x - 1; i < x + 2; i++)
				{
					for (int j = y; j < y + 2; j++)
					{
						n += mineMartrix[i][j];
					}
				}
			}
			// 下边
			else if (x == rows - 1 && y != 0)
			{
				for (int i = x - 1; i < x + 1; i++)
				{
					for (int j = y - 1; j < y + 2; j++)
					{
						n += mineMartrix[i][j];
					}
				}
			}
			// 右边
			else if (x != 0 && y == cols - 1)
			{
				for (int i = x - 1; i < x + 2; i++)
				{
					for (int j = y - 1; j < y + 1; j++)
					{
						n += mineMartrix[i][j];
					}
				}
			}
		}
		return n;
	}
	
	/**
	 * 
	 * @param list
	 */
	public void setMineArea(List<MineBlock> list)
	{
		martrix = new int[rows][cols];
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				martrix[i][j] = countMineNum(i, j);
				list.get(i * cols + j).setState(martrix[i][j]);
			}
		}
		if (Main.isTest)
		{
			System.out.println("设置所有雷块状态");
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < cols; j++)
				{
					System.out.print(" " + martrix[i][j]);
				}
				System.out.println("");
			}
		}
	}
}
