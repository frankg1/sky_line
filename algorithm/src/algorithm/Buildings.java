package algorithm;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;

class Item
{
	public int x;
	public int lh;
	public int rh;
	public Item()
	{
		this.rh=this.lh=0;
	}
}

class Queue
{
	public Item items[];//
	public int cnt;
	public Queue()//构造
	{
		cnt=0;
		this.items=new Item[100];
		for(int i=0;i<100;i++)
		{
			this.items[i]=new Item();
		}
		
	}
	public boolean insert(int j,int x,int h)
	{
		//如果在j为插入的x和前面那位的x重合，那么只需要更新即可
		if(j!=0&&this.items[j-1].x==x)
		{
			this.items[j-1].rh=h;
			this.items[j].lh=h;
			this.show();
			return false;
		}
		//如果在第0位插入
		if(j==0)
		{
			for(int i=99;i>j;i--)
			{
				this.items[i].lh=this.items[i-1].lh;
				this.items[i].rh=this.items[i-1].rh;
				this.items[i].x=this.items[i-1].x;
				//System.out.println(""+i+" "+this.items[i].x+" "+this.items[i].rh);
			}
			//System.out.println("j="+j);
			this.items[j].x=x;
			this.items[j].lh=0;
			this.items[j].rh=h;
			this.items[j+1].lh=h;
			cnt++;
			this.show();
			return true;
		}
		for(int i=99;i>j;i--)
		{
			this.items[i].lh=this.items[i-1].lh;
			this.items[i].rh=this.items[i-1].rh;
			this.items[i].x=this.items[i-1].x;
		}
		this.items[j].x=x;
		this.items[j].lh=this.items[j-1].rh;
		this.items[j].rh=h;
		this.items[j+1].lh=h;
		cnt++;
		this.show();
		return true;
	}
	public void show()
	{
		System.out.println("this is show!");
		for(int i=0;i<this.cnt;i++)
		{
			System.out.print("#"+i+" "+this.items[i].lh+" "+this.items[i].x+' '+this.items[i].rh+" ----");
		}
		System.out.println("");
	}
	public void clear()
	{
		for(int i=0;i<this.cnt;i++)
		{
			int hh=this.items[i].rh;
			while(true)
			{
				if(this.items[i+1].rh!=hh||this.items[i+1].x==100)
				{
					break;
				}
				this.deleteItem(i+1);
			}
			if(this.items[i+1].x==100)
			{
				break;
			}
		}
	}
	public void deleteItem(int j)
	{
		for(int i=j;i<99;i++)
		{
			this.items[i]=this.items[i+1];
		}
		this.cnt--;
	}
}
class SItem
{
	public int lx;
	public int rx;
	public int h;
}

class SQueue
{
	public SItem sitems[];
	public int cnt;
	public SQueue() throws IOException
	{
		cnt=0;
		sitems=new SItem[100];
		for(int i=0;i<100;i++)
		{
			this.sitems[i]=new SItem();
		}
		//BufferedInputStream bs=new BufferedInputStream(new FileInputStream("data.txt"));
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("data.txt")));
		String str=br.readLine();
		while(str!=""&&str!=null)
		{
			//先分解出来三个东西
			String  a,b,c;
			int index1,index2;
			index1=str.indexOf(' ');
			index2=str.indexOf(' ',index1+1);
			a=str.substring(0,index1);
			b=str.substring(index1+1,index2);
			c=str.substring(index2+1,str.length());
			//此处分界完毕
			this.sitems[cnt].lx=Integer.parseInt(a);
			this.sitems[cnt].h=Integer.parseInt(b);
			this.sitems[cnt].rx=Integer.parseInt(c);
			cnt++;
			str=br.readLine();
		}
	}
	
	
}

public class Buildings {

	public static void main(String[] args)throws IOException 
	{
		// TODO Auto-generated method stub
		
		//循环加入SQueue中的元素
		int i=0;
		SQueue sq=new SQueue();
		Queue q=new Queue();
		boolean flag=true;
		for(i=0;i<sq.cnt;i++)
		{
			//此时提取出第一个
			SItem si=sq.sitems[i];
			//遍历queue,如果没有元素，那么直接加入queue之中，在合适的位置
			if(q.cnt==0)
			{
				q.items[0].lh=0;
				q.items[0].rh=0;
				q.items[0].x=1;
				q.items[1].lh=0;
				q.items[1].rh=0;
				q.items[1].x=100;
				q.cnt=2;
			}
			int j=0;
			for(j=0;j<q.cnt;j++)//此循环遍历queue
			//如何确定需要再后面增加呢？
			{
				int hh=q.items[j].lh;
				//遇到第一个小于之处，停下来
				if(si.lx<q.items[j].x)
					//表明我和你这个矩形有交集，而且是第一个交集的矩形
				{
					//分别比较该点左右两边的高度
					//先看左边，如果我小于等于左边的，是我被隐没了，直接把我裁剪剩下右半部分继续走循环
					if(si.h<=q.items[j].lh)
					{
						si.lx=q.items[j].x;
						//continue;
					}
					else//如果我高于，那么就要做操作
					{
						//queue队列更新，在j节点之前j-1节点之间插入一个新的节点，si更新继续走循环
						flag=q.insert(j, si.lx, si.h);
						
						if(flag)
							{
								si.lx=q.items[j+1].x;
							}
						else
						{
							si.lx=q.items[j].x;
						}
						//continue;
					}
				}
				if(si.rx-si.lx<0)
				{
					if(flag)
					{
						q.insert(j+1, si.rx, hh);
					}
					else
					{
						q.insert(j, si.rx, hh);
					}
					break;
				}
				//如果没有交集，，就是比你小
				if(si.rx-si.lx==0)
				{
					//如果长度等于0，跳出来即可
					break;
				}
			}
		}
		
		
		q.clear();
		for(int i1=0;i1<20;i1++)
		{
			System.out.println(""+i1+"# x:"+q.items[i1].x+" rh:"+q.items[i1].rh);
		}
	}

}
