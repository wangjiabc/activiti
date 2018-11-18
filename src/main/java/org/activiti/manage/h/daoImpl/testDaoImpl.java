package org.activiti.manage.h.daoImpl;

import javax.annotation.Resource;
import javax.transaction.TransactionalException;

import org.activiti.manage.model.PersonEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//要注入必要要求当前对象在容器里
@Service("testService")
public class testDaoImpl {

          
	@Resource(name="sessionFactory")
    private SessionFactory mysessionFactory;
	
    @Transactional
    public void save(Integer id){
    	
    	PersonEntity person = new PersonEntity();  
        person.setId(id);  
        person.setName("路飞");  
        //得到外面已经管理好事务的session而不是opensession

        Session mySession=mysessionFactory.getCurrentSession();
        
        //两个数据库手动包含事务
        try{
         mySession.save(person);
        }catch (TransactionalException e) {
			// TODO: handle exception
        	mySession.getTransaction().rollback();
		}
        
        
        
    }

    
    
    @Transactional
    public void save2(Integer id){
    	
	    	//Vacation vacation=new Vacation();
    	PersonEntity personEntity=new PersonEntity();
        //得到外面已经管理好事务的session而不是opensession

        Session mySession=mysessionFactory.getCurrentSession();
        
        //两个数据库手动包含事务
        try{
         mySession.save(personEntity);
        }catch (TransactionalException e) {
			// TODO: handle exception
        	mySession.getTransaction().rollback();
		}
        
        
        
    }

    
}
