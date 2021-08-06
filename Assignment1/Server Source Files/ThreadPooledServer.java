

public class ThreadPooledServer {
	public static void main(String[] args) {
		
		if(args.length !=1)
		{
			System.out.println("java DictServer <port>");
			System.exit(0);
		}

		int port = Integer.valueOf(args[0]);
		int numberOfThreads = 3;
		int bufferSize = 100;
		
		ThreadPool pool = new ThreadPool(numberOfThreads, bufferSize, port);
		pool.initialize();	
	}

}
