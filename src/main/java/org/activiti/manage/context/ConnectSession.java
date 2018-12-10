package org.activiti.manage.context;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConnectSession {

private Future<SessionFactoryImpl> future;

	
	public ConnectSession() {
		// TODO Auto-generated constructor stub
		ExecutorService pool=Executors.newCachedThreadPool();
		GetConnect getConnect=new GetConnect();
		future=pool.submit(getConnect);
	}
	
	//创建数据库连接池
	class GetConnect implements Callable<SessionFactoryImpl>{
		@Override
		public SessionFactoryImpl call() throws Exception {
			// TODO Auto-generated method stub
			ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-mybatis.xml");
			
			SessionFactoryImpl sessionFactoryImpl=(SessionFactoryImpl) applicationContext.getBean("sessionFactory");

			return sessionFactoryImpl;
		}
		
	}
	
	public Session getContext(){
		Session session=null;
		SessionFactoryImpl sessionFactoryImpl;
		try {
			sessionFactoryImpl = future.get();
			try{
				session=sessionFactoryImpl.getCurrentSession();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				session=sessionFactoryImpl.openSession();
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return session;
	}
	
	public Session get(){
		return this.getContext();
	}
	
}
