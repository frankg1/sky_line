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
	public Queue()//����
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
		//�����jΪ�����x��ǰ����λ��x�غϣ���ôֻ��Ҫ���¼���
		if(j!=0&&this.items[j-1].x==x)
		{
			this.items[j-1].rh=h;
			this.items[j].lh=h;
			this.show();
			return false;
		}
		//����ڵ�0λ����
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
			//�ȷֽ������������
			String  a,b,c;
			int index1,index2;
			index1=str.indexOf(' ');
			index2=str.indexOf(' ',index1+1);
			a=str.substring(0,index1);
			b=str.substring(index1+1,index2);
			c=str.substring(index2+1,str.length());
			//�˴��ֽ����
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
		
		//ѭ������SQueue�е�Ԫ��
		int i=0;
		SQueue sq=new SQueue();
		Queue q=new Queue();
		boolean flag=true;
		for(i=0;i<sq.cnt;i++)
		{
			//��ʱ��ȡ����һ��
			SItem si=sq.sitems[i];
			//����queue,���û��Ԫ�أ���ôֱ�Ӽ���queue֮�У��ں��ʵ�λ��
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
			for(j=0;j<q.cnt;j++)//��ѭ������queue
			//���ȷ����Ҫ�ٺ��������أ�
			{
				int hh=q.items[j].lh;
				//������һ��С��֮����ͣ����
				if(si.lx<q.items[j].x)
					//�����Һ�����������н����������ǵ�һ�������ľ���
				{
					//�ֱ�Ƚϸõ��������ߵĸ߶�
					//�ȿ���ߣ������С�ڵ�����ߵģ����ұ���û�ˣ�ֱ�Ӱ��Ҳü�ʣ���Ұ벿�ּ�����ѭ��
					if(si.h<=q.items[j].lh)
					{
						si.lx=q.items[j].x;
						//continue;
					}
					else//����Ҹ��ڣ���ô��Ҫ������
					{
						//queue���и��£���j�ڵ�֮ǰj-1�ڵ�֮�����һ���µĽڵ㣬si���¼�����ѭ��
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
				//���û�н����������Ǳ���С
				if(si.rx-si.lx==0)
				{
					//������ȵ���0������������
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
