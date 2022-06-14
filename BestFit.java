package memory_management;

import java.util.Scanner;

public class BestFit {
	static void bestFit(int blockSize[], int m, int processSize[], int n)
	{
		int allocation[] = new int[n];
		for(int i=0;i<allocation.length;i++)
			allocation[i] = -1;
		for(int i=0;i<n;++i)
		{
			int bestIdx = -1;
			for(int j=0;j<m;j++)
			{
				if(blockSize[j] >=processSize[i])
				{
					if(bestIdx == -1)
						bestIdx = j;
					else if(blockSize[bestIdx] > blockSize[j])
						bestIdx = j;
				}
			}
			
			if(bestIdx!=-1)
			{
				allocation[i] = bestIdx;
				blockSize[bestIdx] -= processSize[i];
			}
		}
		System.out.println("\nProcess Number\tProcess Size\tBlock Number");
		for(int i=0;i<n;i++)
		{
			System.out.print("\t"+(i)+"\t\t"+processSize[i]+"\t\t");
			if(allocation[i]!=-1)
				System.out.println(allocation[i]);
			else
				System.out.println("Not Allocated");
			
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number of memory blocks : ");
		int m = sc.nextInt();
		int blockSize[] = new int[m];
		for(int i=0;i<m;i++)
		{
			System.out.print("Enter block "+(i)+" size : ");
			blockSize[i] = sc.nextInt();
		}
		
		System.out.print("Enter number of process blocks : ");
		int n = sc.nextInt();
		int processSize[] = new int[n];
		for(int i=0;i<n;i++)
		{
			System.out.print("Enter block "+(i)+" size : ");
			processSize[i] = sc.nextInt();
		}
		bestFit(blockSize,m,processSize,n);
		sc.close();
		
	}
}
