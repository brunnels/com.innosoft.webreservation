package com.innosoft.webreservation.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.innosoft.webreservation.entity.MstCalendar;
import com.innosoft.webreservation.service.CalendarService;
import com.innosoft.webreservation.service.SecurityService;

/**
 * Calendar CRUD API
 * @author Administrator
 *
 */
@Controller
@RequestMapping("api/calendar")
public class CalendarApi {	
	/**
	 * Calendar service property
	 */
	@Autowired
	private CalendarService calendarService;
	/**
	 * Security service property
	 */
	@Autowired
	private SecurityService securityService;
	/**
	 * Return list of calendar
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<MstCalendar> listCalendar() {
		List<MstCalendar> list = calendarService.listCalendar();
		return list;
	}
	/**
	 * Update Calendar
	 * @param calendar
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<MstCalendar> updateCalendar(@RequestBody MstCalendar calendar) {
		try {
			if(calendar.getCLDR_ID()==0) {
				calendar = (MstCalendar)securityService.stampCreated(calendar);
				MstCalendar newCalendar = calendarService.addCalendar(calendar);
				return new ResponseEntity<MstCalendar>(newCalendar, HttpStatus.OK);
			} else {
				calendar = (MstCalendar)securityService.stampUpdated(calendar);
				MstCalendar editCalendar = calendarService.editCalendar(calendar);
				return new ResponseEntity<MstCalendar>(editCalendar, HttpStatus.OK);
			}
		} catch(Exception e) {
			return new ResponseEntity<MstCalendar>(calendar, HttpStatus.BAD_REQUEST);
		}	
	}	
	/**
	 * Delete calendar
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCalendar(@PathVariable("id") int id) {
		try {
			boolean deleteCalendar = calendarService.deleteCalendar(id);
			if (deleteCalendar==true) {
				return new ResponseEntity<String>(HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}
