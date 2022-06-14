import java.util.*;
public class Bankers {
	static int n,m;
	int need[][],max[][], alloc[][], avail[], safeSequence[];
	void initializeValues()
	{
		Scanner sc = new Scanner(System.in);
		alloc = new int[n][m];
		System.out.println("Enter the allocation Matrix : ");
		for(int i=0;i<n;i++)
			for(int j=0;j<m;j++)
				alloc[i][j] = sc.nextInt();
		max = new int[n][m];
		System.out.println("Enter the max matrix : ");
		for(int i=0;i<n;i++)
			for(int j=0;j<m;j++)
				max[i][j] = sc.nextInt();
		avail = new int[m];
		System.out.println("Enter the available matrix : ");
		for(int j=0;j<m;j++)
			avail[j] = sc.nextInt();
		safeSequence = new int[n];
		need = new int[n][m];
		sc.close();
	}
	
	void isSafe()
	{
		int count =0;
		boolean visited[] =new boolean[n];
		for(int i=0;i<n;i++)
			visited[i] = false;
		int work[] = new int[m];
		for(int i=0;i<m;i++)
			work[i] = avail[i];
		while(count<n)
		{
			boolean flag =false;
			for(int i=0;i<n;i++)
			{
				if(visited[i]==false)
				{
					int j;
					for(j=0;j<m;j++)
					{
						if(need[i][j]>work[j])
							break;
					}
					if(j==m)
					{
						safeSequence[count++] = i;
						visited[i] = true;
						flag = true;
						for(j=0;j<m;j++)
							work[j] = work[j] + alloc[i][j];
					}
				}
			}
			if(flag==false)
				break;
		}
		if(count<n)
			System.out.println("The System is unsafe!");
		else
		{
			System.out.println("Following is SAFE sequence : ");
			for(int i=0;i<n;i++)
			{
				System.out.print("P"+safeSequence[i]);
				if(i!=n-1)
					System.out.print("->");
			}
		}
	}
	
	void calculateNeed()
	{
		//need = new int[n][m];
		for(int i=0;i<n;i++)
			for(int j=0;j<m;j++)
				need[i][j] = max[i][j] - alloc[i][j];
	}
	
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the number of processes : ");
		n = sc.nextInt();
		System.out.print("Enter the number of resources : ");
		m = sc.nextInt();
		Bankers b = new Bankers();
		b.initializeValues();
		b.calculateNeed();
		b.isSafe();
		sc.close();
	}
}
