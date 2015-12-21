package org.backpacker4.web.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.backpacker4.bean.Appuser;
import org.backpacker4.bean.Position;
import org.backpacker4.business.service.AppuserService;
import org.backpacker4.business.service.PositionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Spring MVC controller for handling Ajax requests.
 */
@Controller
@RequestMapping("/Ajax")
public class AjaxController {
	
	//--- Main entity service
	@Resource
    private AppuserService appuserService; // Injected by Spring
	@Resource
    private PositionService positionService; // Injected by Spring

	/**
	 * Checks if a given username already exists
	 * @param request  HttpServletRequest
	 * @param String name the username to check
	 * @return
	 */
	@RequestMapping(value = "/CheckUsername", method = RequestMethod.GET)
	@ResponseBody
	public boolean isValidUsername (HttpServletResponse response, 
			@RequestParam(value = "name", required = false) String name) {
		System.out.println("function isValidUsername entered...");
		List<Appuser> allAppusers = appuserService.findAll();
		for (Appuser appuser : allAppusers) {
			if (appuser.getUsername().equals(name))
				return false;
		}
		return true;
	}
	
	/**
	 * Saves a location in the database
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return
	 */
	@RequestMapping(value = "/SaveLocation", method = RequestMethod.GET)
	@ResponseBody
	public boolean saveLocation (HttpServletRequest request, HttpServletResponse response) {
		System.out.println("function SaveLocation entered...");

		try {
			Long appuserid = Long.valueOf(request.getParameter("appuserid")).longValue();
			System.out.println("appuserid:" + appuserid);
			Appuser appuser = appuserService.findById(appuserid);
			Position updated_position = updatePosition(request,appuser);
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}				
	}
	
	//-------------------------------------------------------------------------
	//Private helper methods
	//-------------------------------------------------------------------------
	//Method to do the actual saving of the location in the database
	private Position updatePosition(HttpServletRequest httpServletRequest, Appuser appuser){
			
			String lat = httpServletRequest.getParameter("latitude");
			String lon = httpServletRequest.getParameter("longitude");
			String city = httpServletRequest.getParameter("city");
			String country = httpServletRequest.getParameter("country");
			
			BigDecimal bdlat = new BigDecimal(lat);
			BigDecimal bdlon = new BigDecimal(lon);
			
			Position pos = positionService.findById((appuser.getIdPosition()));
			pos.setLatitude(bdlat);
			pos.setLongitude(bdlon);
			pos.setCountry(country);
			pos.setCity(city);
			
			System.out.println(city + "  " + country);
			
			Position positionUpdated = positionService.update(pos);
			return positionUpdated;
		}
}
