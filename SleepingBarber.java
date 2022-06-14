import java.util.concurrent.*;
public class SleepingBarber extends Thread{
	public static Semaphore customers = new Semaphore(0);
	public static Semaphore barber = new Semaphore(0);
	public static Semaphore accessSeats = new Semaphore(1);
	public static final int chairs = 5;
	public static int numberOfFreeSeats = chairs;
	public class Customer extends Thread 
	{
		int id;
		boolean notCut = true;
		public Customer (int i)
		{
			id = i;
		}
		public void run()
		{
			while(notCut)
			{
				try
				{
					accessSeats.acquire();
					if(numberOfFreeSeats > 0)
					{
						System.out.println("Customer "+this.id+" just sat down");
						numberOfFreeSeats--;
						customers.release();
						accessSeats.release();
						try
						{
							barber.acquire();
							notCut = false;
							this.get_haircut();
						}
						catch(Exception e)
						{}
					}
					else
					{
						System.out.println("There are no free seats. Customer "+ this.id+" has left the barbershop.");
						accessSeats.release();
						notCut = false;
					}
				}
				catch(Exception e)
				{}
			}
		}
		
		public void get_haircut()
		{
			System.out.println("Customer "+this.id+" is getting his hair cut");
			try
			{
				Thread.sleep(5050);
			}
			catch(Exception e)
			{}
		}
	}

	public class Barber extends Thread{
		public Barber()
		{}
		public void run()
		{
			while(true)
			{
				try
				{
					customers.acquire();
					accessSeats.release();
					numberOfFreeSeats++;
					barber.release();
					accessSeats.release();
					this.cutHair();
				}
				catch(Exception e) {}
			}
		}
		
		public void cutHair()
		{
			System.out.println("The barber is cutting hair");
			try
			{
				Thread.sleep(5000);
			}
			catch(Exception e) {}
		}
	}
	
	public static void main(String args[])
	{
		SleepingBarber barberShop = new SleepingBarber();
		barberShop.start();
	}
	
	public void run()
	{
		Barber giovanni  =  new Barber ();
		giovanni.start();
		
		for (int i=1;i<16;i++)
		{
			Customer aCustomer = new Customer(i);
			aCustomer.start();
			try 
			{
				Thread.sleep(2000);
			}
			catch(Exception e) {}
		}
	}
	
}
