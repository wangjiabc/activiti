package org.activiti.manage.context;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Connect {

	private Future<ClassPathXmlApplicationContext> future;

	
	public Connect() {
		// TODO Auto-generated constructor stub
		ExecutorService pool=Executors.newCachedThreadPool();
		GetConnect getConnect=new GetConnect();
		future=pool.submit(getConnect);
	}
	
	//创建数据库连接池
	class GetConnect implements Callable<ClassPathXmlApplicationContext>{
		@Override
		public ClassPathXmlApplicationContext call() throws Exception {
			// TODO Auto-generated method stub
			ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-sqlservers.xml");
			  return applicationContext;
		}
		
	}
	
	public ClassPathXmlApplicationContext getContext(){
		ClassPathXmlApplicationContext applicationContext=null;
		try {
			applicationContext = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return applicationContext;
	}
	
	public ClassPathXmlApplicationContext get(){
		return this.getContext();
	}
}
