package activiti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.activiti.manage.context.ConnectSession;
import org.activiti.manage.tools.MyTestUtil;
import org.hibernate.Session;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rmi.server.entity.RoomInfoFlowIdEntity;

public class hibernateTest {

	ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	

	public void test() {
		DataSource dataSource = (DataSource) context.getBean(DataSource.class);
		System.out.println(dataSource);
		
		//SessionFactoryImpl sessionFactoryImpl=(SessionFactoryImpl) context.getBean("sessionFactory");
		
		Session session=new ConnectSession().get();

		session.beginTransaction();
		
		RoomInfoFlowIdEntity roomInfoFlowIdEntity=new RoomInfoFlowIdEntity();
		
		roomInfoFlowIdEntity.setDate(new Date());
		
		 Serializable result =session.save(roomInfoFlowIdEntity);
	        
	        Integer i=(Integer) result;
	        
	        System.out.println(roomInfoFlowIdEntity);
	        
	        MyTestUtil.print(roomInfoFlowIdEntity);

	        System.out.println("i="+i);
	        
	        session.getTransaction().commit();
	        
	        //session.getTransaction().rollback();
	        
		List<RoomInfoFlowIdEntity> list=session.createQuery("from RoomInfoFlowIdEntity order by update_time")
        		 .setMaxResults(10).setFirstResult(0).list();
		
		System.out.println("processDaoImpl="+list);
		
		
		
	}
	
}
