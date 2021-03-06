package readerwriter;
import java.util.concurrent.Semaphore;
public class ReaderWriter {
	public static final int NUM_OF_READERS = 3;
	public static final int NUM_OF_WRITERS = 2;
	public static void main(String args[])
	{
		RWLock database = new Database();
		Thread[] readerArray = new Thread[NUM_OF_READERS];
		Thread[] writerArray = new Thread[NUM_OF_WRITERS];
		
		for(int i=0;i<NUM_OF_READERS;i++)
		{
			readerArray[i] = new Thread(new Reader(i,database));
			readerArray[i].start();
		}
		for(int i=0;i<NUM_OF_WRITERS;i++)
		{
			writerArray[i] = new Thread(new Writer(i,database));
			writerArray[i].start();
		}
	}
}

interface RWLock
{
	public abstract void acquireReadLock(int readerNum);
	public abstract void acquireWriteLock(int writerNum);
	public abstract void releaseReadLock(int readerNum);
	public abstract void releaseWriteLock(int writerNum);
}

class Database implements RWLock
{
	private int readerCount;
	private Semaphore mutex;
	private Semaphore db;
	
	public Database()
	{
		readerCount = 0;
		mutex = new Semaphore(1);
		db = new Semaphore(1);
	}
	
	public void acquireReadLock(int readerNum)
	{
		try
		{
			mutex.acquire();
		}
		catch(Exception e)
		{}
		
		++readerCount;
		
		if(readerCount==1)
		{
			try
			{
				db.acquire();
			}
			catch(Exception e)
			{}
		}
		
		System.out.println("Reader "+readerNum+" is reading. Reader count = "+readerCount);
		mutex.release();
	}
	
	public void releaseReadLock(int readerNum)
	{
		try
		{
			mutex.acquire();
		}
		catch(Exception e)
		{}
		--readerCount;
		
		if(readerCount == 0)
		{
			db.release();
		}
		
		System.out.println("Reader "+readerNum+" is done reading. Reader count = "+readerCount);
		
		mutex.release();
	}
	
	public void acquireWriteLock(int writerNum)
	{
		try
		{
			db.acquire();
		}
		catch(InterruptedException e)
		{}
		System.out.println("Writer "+writerNum+" is writing.");
	}
	
	public void releaseWriteLock(int writerNum)
	{
		System.out.println("Writer "+writerNum+" is done writing.");
		db.release();
	}
}

class Reader implements Runnable
{
	private RWLock database;
	private int readerNum;
	public Reader(int readerNum, RWLock database)
	{
		this.readerNum = readerNum;
		this.database = database;
	}
	public void run()
	{
		while(true)
		{
			SleepUtilities.nap();
			System.out.println("reader "+readerNum+" wants to read.");
			database.acquireReadLock(readerNum);
			SleepUtilities.nap();
			database.releaseReadLock(readerNum);
		}
	}
}


class Writer implements Runnable
{
	private RWLock database;
	private int writerNum;
	
	public Writer(int w, RWLock d)
	{
		writerNum = w;
		database =d;
	}
	
	public void run()
	{
		while(true)
		{
			SleepUtilities.nap();
			System.out.println("Writer "+writerNum+" wants to write");
			database.acquireReadLock(writerNum);
			
			SleepUtilities.nap();
			database.releaseReadLock(writerNum);
		}
	}
}

class SleepUtilities
{
	public static void nap()
	{
		nap(NAP_TIME);
	}
	public static void nap(int duration)
	{
		int sleeptime = (int)(NAP_TIME*Math.random());
		try
		{
			Thread.sleep(sleeptime*1000);
		}
		catch(Exception e)
		{}
	}
	private static final int NAP_TIME = 5;
}

