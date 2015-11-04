package org.backpacker4.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.backpacker4.bean.Appuser;
import org.backpacker4.business.service.AppuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	
	@Resource
	AppuserService appuserService;
	
	@RequestMapping(value = "/user/feedback", method = RequestMethod.GET)
	public ModelAndView userFeedbackPagina() {

	  ModelAndView model = new ModelAndView();
	  model.setViewName("user/feedback");
	  addCurrentUser(model);
	  model.addObject("googleAPIurl", "https://maps.googleapis.com/maps/api/js?key=AIzaSyAW6_kB9yFhHlKMU0wZRDrgPdlAzQjpj5c&signed_in=true&callback=initMap");
	  model.addObject("asyncdefer"," async defer");
	  return model;
	}
	
	@Autowired
    private ServletContext servletContext;
	
	//add the current user
	private void addCurrentUser(ModelAndView model){	
			UserDetails userDetails =
			(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addObject("username",userDetails.getUsername());
			
			String url = "";
			List<Appuser> allAppUsers = appuserService.findAll(); 
			for(Appuser appuser : allAppUsers){
				if (appuser.getUsername().equals(userDetails.getUsername())){
					url = servletContext.getRealPath("/")
							+ appuser.getIdPhoto() + "_THUMB.jpg";
				}
			}
			model.addObject("thumburl",url);
		}

}