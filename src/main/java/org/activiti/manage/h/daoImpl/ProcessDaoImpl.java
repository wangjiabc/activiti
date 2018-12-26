package org.activiti.manage.h.daoImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.manage.factory.FlowFactory;
import org.activiti.manage.tools.MyTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.rmi.server.entity.RoomInfoFlowIdEntity;

@Service("processService")
public class ProcessDaoImpl {

	@Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
	
	@Transactional
    public int save(RoomInfoFlowIdEntity roomInfoFlowIdEntity) throws Exception{

        //得到外面已经管理好事务的session而不是opensession

        Session session=sessionFactory.getCurrentSession();

        Serializable result =session.save(roomInfoFlowIdEntity);
        
        int i=(int) result;
        
        System.out.println(roomInfoFlowIdEntity);
        
        MyTestUtil.print(roomInfoFlowIdEntity);
        
        if(i<1){
        	session.getTransaction().rollback();
        	throw new Exception();
        }
        
        return i;
	}
		
	@Transactional
    public Integer del(String guid) throws Exception{

        Session session=sessionFactory.getCurrentSession();
       
        int i=session.createQuery("delete RoomInfoFlowIdEntity where guid=?").setString(0, guid).executeUpdate();
       
        if(i<1){
        	session.getTransaction().rollback();
        	throw new Exception();
        }
        
        return i;
	}
	
	@Transactional
    public Map selectById(String guid,int result,int limit,int offset){

        Session session=sessionFactory.getCurrentSession();

        List<RoomInfoFlowIdEntity> list=session.createQuery("from RoomInfoFlowIdEntity where guid=? and result=? order by currentDate").setString(0, guid)
        		.setInteger(1, result).setMaxResults(limit).setFirstResult(offset).list();
        
        long total=(long) session.createQuery("select count(*) from RoomInfoFlowIdEntity where guid=? and result=? ").setString(0, guid)
        		.setInteger(1, result).uniqueResult();
        
        Map map=new HashMap<>();
        
        map.put("rows", list);
		map.put("total", total);
        
        return map;
        
	}
	
	@Transactional
	public Map selectByGuid(String guid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		List<RoomInfoFlowIdEntity> list = session
				.createQuery("from RoomInfoFlowIdEntity where guid=?")
				.setString(0, guid).list();

		long total = (long) session.createQuery("select count(*) from RoomInfoFlowIdEntity where guid=?")
				.setString(0, guid).uniqueResult();

		Map map = new HashMap<>();

		map.put("rows", list);
		map.put("total", total);

		return map;
	}
	
	@Transactional
    public Map selectByOpenId(String openId,int result,int limit,int offset){

        Session session=sessionFactory.getCurrentSession();

        List<RoomInfoFlowIdEntity> list=session.createQuery("from RoomInfoFlowIdEntity where openId=? and result=? order by currentDate").setString(0, openId)
        		.setInteger(1, result).setMaxResults(limit).setFirstResult(offset).list();
        
        long total=(long) session.createQuery("select count(*) from RoomInfoFlowIdEntity where openId=? and result=? ").setString(0, openId)
        		.setInteger(1, result).uniqueResult();
        
        Map map=new HashMap<>();
        
        map.put("rows", list);
		map.put("total", total);
        
        return map;
        
	}
	
	@Transactional
    public Long selectCountAfter(String openId,Date afterDate){

        Session session=sessionFactory.getCurrentSession();

        System.out.println("afterDate="+afterDate);
        
        long total=(long) session.createQuery("select count(*) from RoomInfoFlowIdEntity where openId=? and result=1 and currentDate>? ").setString(0, openId)
        		.setTimestamp(1, afterDate).uniqueResult();
        
        System.out.println("total="+total);
        
        return total;
        
	}
	
	@Transactional
	public Map selectAllByState(Integer state, int limit, int offset) {
		Session session = sessionFactory.getCurrentSession();

		List<RoomInfoFlowIdEntity> list = session
				.createQuery("from RoomInfoFlowIdEntity where state=? order by currentDate").setInteger(0, state)
				.setMaxResults(limit).setFirstResult(offset).list();

		long total =  (long) session.createQuery("select count(*) from RoomInfoFlowIdEntity where state=? ")
				.setInteger(0, state).uniqueResult();

		Map map = new HashMap<>();

		map.put("rows", list);
		map.put("total", total);

		return map;
	}
	
	@Transactional
    public Map selectAll(int result,int limit,int offset){

        Session session=sessionFactory.getCurrentSession();

        List<RoomInfoFlowIdEntity> list=session.createQuery("from RoomInfoFlowIdEntity where result=? order by currentDate").setInteger(0, result)
        		.setMaxResults(limit).setFirstResult(offset).list();
        
        long total= (long) session.createQuery("select count(*) from RoomInfoFlowIdEntity where result=? ")
        		.setInteger(0, result).uniqueResult();
        
        Map map=new HashMap<>();
        
        map.put("rows", list);
		map.put("total", total);
        
        return map;
        
	}
	
}
