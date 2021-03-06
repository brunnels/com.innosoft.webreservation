package com.innosoft.webreservation.server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innosoft.webreservation.entity.SysEmail;
import com.innosoft.webreservation.entity.SysSetting;
import com.innosoft.webreservation.entity.TrnReservation;
import com.innosoft.webreservation.service.CustomerMemberService;
import com.innosoft.webreservation.service.EmailService;
import com.innosoft.webreservation.service.ReservationService;
import com.innosoft.webreservation.service.SysSettingService;
/**
 * Notification of reservation service
 */
@Component("notificationReservationService")
public class NotificationReservationService {
	/**
	 * Email service property
	 */
	@Autowired
	private EmailService emailService;
	/**
	 * system setting service property
	 */
	@Autowired
	private SysSettingService sysSettingService;
	/**
	 * Reservation service property
	 */
	@Autowired
	private ReservationService reservationService;
	/**
	 * Customer member service property
	 */
	@Autowired
	private CustomerMemberService customerMemberService;

	/**
	 * process method
	 */
	public void process() {
		try {

			Calendar systemDate = Calendar.getInstance();
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		    String currentTime = timeFormat.format(systemDate.getTime());
		    String currentDate = dateFormat.format(systemDate.getTime());
		    
		    SysSetting setting = sysSettingService.getSetting(sysSettingService.getMaxId());
		    
		    String setTime = setting.getSSET_NOTIFICATION_TIME();
		    String setDate = dateFormat.format(setting.getSSET_NOTIFICATION_DATE());	
		    int setNoOfDays = setting.getSSET_NOTIFICATION_NO_OF_DAYS();
		    
		    if(setTime.equals(currentTime) && !setDate.equals(currentDate))
		    {
		    	//System.out.println("Success Equal");
		    	String emailServe = "";
		    	List<TrnReservation> list = reservationService.notificationReservation(currentDate,setNoOfDays);    	
		    	for (int i = 0; i < list.size(); i++) {

		    		if(list.size() != 0)
		    		{	
		    			//System.out.println("getting item Now Please Wait A while");
		    			emailServe = list.get(i).RESV_MEBR_FK.MEBR_EMAIL_ADDRESS + "," + emailServe;
		    			//System.out.println(emailServe);
		    		}
		    	}
		    	if(emailServe != null){
		    		SysEmail email = new SysEmail();
									
					email.setEMAIL_EMAIL(emailServe);
					email.setEMAIL_MESSAGE("Notification Email");
					email.setEMAIL_SUBJECT("You reservation is near.");
	
					emailService.sendMail(email);	
					
					SysSetting updateSetting = new SysSetting();
					updateSetting.setSSET_ID(1);
					updateSetting.setSSET_NOTIFICATION_DATE(systemDate.getTime());
					updateSetting.setSSET_NOTIFICATION_TIME(setTime);
					
					sysSettingService.addSetting(updateSetting);
		    	}
		  }
		    
		} catch (Exception e) {
			
		}		
	}
}


