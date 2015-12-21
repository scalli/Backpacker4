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


/**
 * Spring MVC controller for 'Login' management.
 */
@Controller
public class LoginController {
	
	@Resource
	AppuserService appuserService;

	/**
	 * Redirects to the welcome page
	 * @return
	 */
	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

	  ModelAndView model = new ModelAndView();
	  model.addObject("title", "Spring Security Login Form - Database Authentication");
	  model.addObject("message", "This is default page!");
	  model.setViewName("hello");
	  return model;

	}

	/**
	 * Redirects to the admin homepage
	 * @return
	 */
	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView adminPagina() {

	  ModelAndView model = new ModelAndView();
	  model.addObject("title", "Spring Security Login Form - Database Authentication");
	  model.addObject("message", "This page is for ROLE_ADMIN only!");
	  model.setViewName("admin/home");
	  model.addObject("reverseGeoloactionURL", "https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false");
	  addCurrentUserURL(model);
	  addCurrentUser(model);
	  return model;
	}
	
	/**
	 * Redirects to the user homepage
	 * @return
	 */
	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public ModelAndView userPagina() {

	  ModelAndView model = new ModelAndView();
	  model.addObject("title", "Spring Security Login Form - Database Authentication");
	  model.addObject("message", "This page is for ROLE_USER only!");
	  model.setViewName("user/home");
	  model.addObject("reverseGeoloactionURL", "https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false");
	  addCurrentUserURL(model);
	  addCurrentUser(model);
	  return model;
	}
	
	/**
	 * Redirects to user login page
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView indexPagina() {

	  ModelAndView model = new ModelAndView();
	  model.addObject("title", "Spring Security Login Form - Database Authentication");
	  model.addObject("message", "This page is for ROLE_USER only!");
	  model.setViewName("index");
	  addCurrentUser(model);
	  return model;

	}
	
	/**
	 * Redirects to admin login page
	 * @return
	 */
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

	  ModelAndView model = new ModelAndView();
	  model.addObject("title", "Spring Security Login Form - Database Authentication");
	  model.addObject("message", "This page is for ROLE_ADMIN only!");
	  model.setViewName("admin/home");
	  addCurrentUserURL(model);
	  return model;

	}

	/**
	 * Redirects after attempt to login
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

	  ModelAndView model = new ModelAndView();
	  if (error != null) {
		model.addObject("error", "Invalid username and password!");
	  }

	  if (logout != null) {
		model.addObject("msg", "You've been logged out successfully.");
	  }
	  model.setViewName("login");

	  return model;

	}
	
	//for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

	  ModelAndView model = new ModelAndView();
		
	  //check if user is login
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (!(auth instanceof AnonymousAuthenticationToken)) {
		UserDetails userDetail = (UserDetails) auth.getPrincipal();	
		model.addObject("username", userDetail.getUsername());
	  }
		
	  model.setViewName("403");
	  return model;

	}
	
	@Autowired
    private ServletContext servletContext;
	
//---------------------------------------------------------------------------------------
//Private helper methods	
//---------------------------------------------------------------------------------------

	//add the current user
	private void addCurrentUserURL(ModelAndView model){	
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
	
	//get the current user as an Appuser object
	private Appuser getAppuser(String username){
		List<Appuser> appuser_list = appuserService.findAll();
		for(Appuser user : appuser_list){
			if(user.getUsername().equals(username))
				return user;
		}
		return new Appuser();
		}
		
		//add the current user to the modelandview
		private void addCurrentUser(ModelAndView model){	
			UserDetails userDetails =
			(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addObject("username",userDetails.getUsername());
			System.out.println("appuserid=" + getAppuser(userDetails.getUsername()).getId());;
			model.addObject("appuser",getAppuser(userDetails.getUsername()));
			
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
		
		//add the current user to the model
		private void addCurrentUser(Model model){	
				UserDetails userDetails =
				(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				model.addAttribute("username",userDetails.getUsername());
				System.out.println("appuserid=" + getAppuser(userDetails.getUsername()).getId());;
				model.addAttribute("appuser",getAppuser(userDetails.getUsername()));
				
				String url = "";
				List<Appuser> allAppUsers = appuserService.findAll(); 
				for(Appuser appuser : allAppUsers){
					if (appuser.getUsername().equals(userDetails.getUsername())){
						url = servletContext.getRealPath("/")
								+ appuser.getIdPhoto() + "_THUMB.jpg";
					}
				}
				model.addAttribute("thumburl",url);
			}
		

}