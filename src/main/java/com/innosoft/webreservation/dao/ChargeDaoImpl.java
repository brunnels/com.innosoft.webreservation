package com.innosoft.webreservation.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.innosoft.webreservation.entity.MstCharge;
/**
 *CRUD implementation for charge data object
 */
@Repository
@Transactional
public class ChargeDaoImpl implements ChargeDao {
	/**
	 * Session factory method
	 */
	@Autowired
	private SessionFactory sessionFactory;
	/**
	 * Get Session factory method
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	/**
	 * Set session factory method
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/**
	 * List charge method
	 */
	@SuppressWarnings("unchecked")
	public List<MstCharge> listCharge() {
		Session session = this.sessionFactory.getCurrentSession();
		List<MstCharge> list = session.createQuery("from MstCharge").list();	
		return list;
	}
	/**
	 * Get max id method
	 * @return
	 */
	public int getMaxId()
	{
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(MstCharge.class).setProjection(Projections.max("CHRG_ID"));
	    Integer maxId = (Integer)criteria.uniqueResult();
		if(maxId == null){
			maxId = 0;
		}
		return 	maxId;
	}
	/**
	 * Get max charge number method
	 * @return
	 */
	public String getMaxChrgNum()
	{
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(MstCharge.class).setProjection(Projections.max("CHRG_CHARGE_NO"));
	    String maxNo = (String)criteria.uniqueResult();
		if(maxNo == null){
			maxNo = "0";
		}
		return 	maxNo;
	}
	/**
	 * Add charge method
	 */
	public MstCharge addCharge(MstCharge charge) {
		
		try {
			Session session = this.sessionFactory.openSession();
			Transaction tx = null;	
			StringBuilder sb = new StringBuilder();
			int cnvrtdVl = Integer.parseInt(getMaxChrgNum()) + 1;
			sb.append("");
			sb.append(cnvrtdVl);
			tx = session.beginTransaction();
			MstCharge newCharge = new MstCharge();
			newCharge.setCHRG_ID(getMaxId() + 1);
			newCharge.setCHRG_CHARGE_NO(sb.toString());
			newCharge.setCHRG_CUST_ID(charge.CHRG_CUST_ID);
			newCharge.setCHRG_PRICE(charge.CHRG_PRICE);
			newCharge.setCHRG_APP_DIVISION(charge.CHRG_APP_DIVISION);
			newCharge.setCHRG_APP_START_DATE(charge.CHRG_APP_START_DATE);
			newCharge.setCHRG_APP_END_DATE(charge.CHRG_APP_END_DATE);
			
			newCharge.setCREATED_BY_USER_ID(charge.CREATED_BY_USER_ID);
			newCharge.setCREATED_DATE(charge.CREATED_DATE);
			newCharge.setUPDATED_BY_USER_ID(charge.UPDATED_BY_USER_ID);
			newCharge.setUPDATED_DATE(charge.UPDATED_DATE);
			newCharge.setISDELETED(charge.ISDELETED);
			
			session.save(newCharge);
			tx.commit();
			session.close();
			
			return newCharge;			
		} catch(Exception e) {
			return charge;	
		}
	}
	/**
	 * Edit charge method
	 */
	public MstCharge editCharge(MstCharge charge) {
		try {
			Session session = this.sessionFactory.openSession();
			Transaction tx = null;	
			
			tx = session.beginTransaction();
			MstCharge updateCharge = (MstCharge)session.get(MstCharge.class, charge.CHRG_ID); 
			
			updateCharge.setCHRG_CHARGE_NO(charge.CHRG_CHARGE_NO);
			updateCharge.setCHRG_APP_DIVISION(charge.CHRG_APP_DIVISION);
			updateCharge.setCHRG_APP_END_DATE(charge.CHRG_APP_END_DATE);
			updateCharge.setCHRG_APP_START_DATE(charge.CHRG_APP_START_DATE);
			updateCharge.setCHRG_PRICE(charge.CHRG_PRICE);
			updateCharge.setCHRG_CUST_ID(charge.CHRG_CUST_ID);
			
			updateCharge.setUPDATED_BY_USER_ID(charge.UPDATED_BY_USER_ID);
			updateCharge.setUPDATED_DATE(charge.UPDATED_DATE);
			
			session.update(updateCharge); 
			tx.commit();
			session.close();
			
			return updateCharge;
		} catch (Exception e) 
		{
			return new MstCharge();
		}	
	}
	/**
	 * Delete charge method
	 */
	public boolean deleteCharge(int id) {
	    try {
			Session session = this.sessionFactory.openSession();
			Transaction tx = null;	
			
	    	tx = session.beginTransaction();
	    	MstCharge deleteCharge = (MstCharge)session.get(MstCharge.class, id); 
	    	
	    	session.delete(deleteCharge); 
	    	
	    	tx.commit();
	    	session.close();
	    	
	    	return true;
	    } catch (Exception e) {
	    	return false; 
	    }	
	}	
}
