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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.backpacker4.bean.Feedback;
import org.backpacker4.bean.FeedbackPhoto;
import org.backpacker4.bean.Position;
import org.backpacker4.bean.Typeinfo;
import org.backpacker4.business.service.AppuserService;
import org.backpacker4.business.service.FeedbackPhotoService;
import org.backpacker4.business.service.FeedbackService;
import org.backpacker4.business.service.PhotoService;
import org.backpacker4.business.service.PositionService;
import org.backpacker4.business.service.TypeinfoService;
import org.backpacker4.business.service.UserRolesService;
import org.backpacker4.web.common.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Spring MVC controller for 'User' management.
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {
	
	//--- JSP pages names ( View name in the MVC model )
			private static final String JSP_SEARCH_FORM   = "search/form";
			private static final String JSP_SEARCH_LIST   = "search/list";

			//--- SAVE ACTION ( in the HTML form )
//			private static final String SAVE_ACTION_CREATE   = "/register/create";
//			private static final String SAVE_ACTION_UPDATE   = "/register/update";

			//--- Main entity service
			@Resource
			private FeedbackPhotoService feedbackPhotoService;
			@Resource
			private TypeinfoService typeinfoService;
			@Resource
			private FeedbackService feedbackService;
			@Resource
		    private AppuserService appuserService; // Injected by Spring
			//--- Other service(s)
			@Resource
		    private PositionService positionService; // Injected by Spring
			@Resource
		    private PhotoService photoService; // Injected by Spring
			@Resource
			private UserRolesService userRolesService; // Injected by Spring

			//--------------------------------------------------------------------------------------
			/**
			 * Default constructor
			 */
			public UserController() {
				super(UserController.class, "USER" );
				log("UserController created.");
			}//end of constructor
			
			
			//--------------------------------------------------------------------------------------
			// Request mapping
			//--------------------------------------------------------------------------------------
			/**
			 * Shows a form in order to a search by criteria
			 * @param model Spring MVC model
			 * @return
			 */
			@RequestMapping("/search/form")
			public String formForSearch(Model model) {
				log("Action 'formForSearch'");
				populateModel(model);
				addCurrentUser(model);
				
				return "user/search/form";
			}
			
			/**
			 * Shows the results of feedbacks by criteria
			 * @param model Spring MVC model
			 * @return
			 */
			@RequestMapping("/search/results")
			public String doSearch(Model model, HttpServletRequest httpServletRequest) {
				log("Action 'doSearch'");
				populateModel(model);

				String country = httpServletRequest.getParameter("country");
				String city = httpServletRequest.getParameter("city");
				String typeinfo = httpServletRequest.getParameter("typeinfo");
				
				//Get the list of all selected Feedback objects
				List<Feedback> selection = getSelectionWithCriteria(country,city,typeinfo);
				
				List<String> feedbackheaders = new ArrayList<String>();
				List<String> feedbacktext = new ArrayList<String>();
				ArrayList<ArrayList<String>> feedbackphotos = new ArrayList<ArrayList<String>>();
				
				for(Feedback f : selection){
					String header = "";
					String text = "";
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String date = format.format(f.getDatefeedback());
					String username = appuserService.findById(f.getIdUser()).getUsername();
					
					header = "On " +  date + ", " + username + " wrote this comment ";
					header += "on a " + getTypeinfoname(f) + " in " + getCity(f) + "," + getCountry(f);
					feedbackheaders.add(header);
					
					feedbacktext.add(f.getComment());
					
					feedbackphotos.add((ArrayList<String>)getFeedbackPhotoIds(f));
				}
				
				model.addAttribute("feedbackheaders",feedbackheaders);
				model.addAttribute("feedbacktext",feedbacktext);
				model.addAttribute("feebackphotos",feedbackphotos);
				
				addCurrentUser(model);

				return "user/search/form";
			}
			
			/**
			 * Shows the list of all users of backpackworld
			 * @param model Spring MVC model
			 * @return
			 */
			@RequestMapping("/list")
			public String showUserList(Model model, HttpServletRequest httpServletRequest) {
				
				System.out.println("Entering user/list");
				
				List<Appuser> appusers = new ArrayList<Appuser>();
				appusers = appuserService.findAll();
				
				model.addAttribute("appusers", appusers);
				addCurrentUser(model);
				
				return "user/list";
			}
			
			/**
			 * Shows the info of a user of Backpackworld
			 * @param model Spring MVC model
			 * @return
			 */
			@RequestMapping(value="/info/{id}", method= RequestMethod.GET)
			public String showUserInfo(Model model, HttpServletRequest httpServletRequest,
					@PathVariable("id") Long id) {
				
				System.out.println("Entering user/info/" + id);
				
				Appuser appuser = appuserService.findById(id);
				List<Feedback> feedbacks = getAllFeedback(appuser);
				List<Position> positions = getAllFeedbackPositions(appuser);
				
				List<String> feedbackheaders = new ArrayList<String>();
				List<String> feedbacktext = new ArrayList<String>();
				ArrayList<ArrayList<String>> feedbackphotos = new ArrayList<ArrayList<String>>();
				
				for(Feedback f : feedbacks){
					String header = "";
					String text = "";
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String date = format.format(f.getDatefeedback());
					String username = appuserService.findById(f.getIdUser()).getUsername();
					
					header = "On " +  date + ", " + username + " wrote this comment ";
					header += "on a " + getTypeinfoname(f) + " in " + getCity(f) + "," + getCountry(f);
					feedbackheaders.add(header);
					
					feedbacktext.add(f.getComment());
					
					feedbackphotos.add((ArrayList<String>)getFeedbackPhotoIds(f));
				}
				
				model.addAttribute("feedbackheaders",feedbackheaders);
				model.addAttribute("feedbacktext",feedbacktext);
				model.addAttribute("feebackphotos",feedbackphotos);
				
				model.addAttribute("appuserInfo", appuser);
				model.addAttribute("feedbacks", feedbacks);
				model.addAttribute("positions", positions);
				
				model.addAttribute("lastposition",getAppuserLastPos(appuser));
				
				model.addAttribute("googleAPIurl","https://maps.googleapis.com/maps/api/js?v=3.11&sensor=false");
				
				addCurrentUser(model);
				
				return "user/info";
			}
			
			/**
			 * Shows the info of all places visited by users
			 * @param model Spring MVC model
			 * @return
			 */
			@RequestMapping(value="/places", method= RequestMethod.GET)
			public String showPlaceInfo(Model model, HttpServletRequest httpServletRequest) {
				
				System.out.println("Entering user/places");
				
				List<Position> positions = new ArrayList<Position>();
				List<Feedback> feedbacks = new ArrayList<Feedback>();
				feedbacks = feedbackService.findAll();
				for(Feedback feedback: feedbacks){
					positions.add(positionService.findById(feedback.getIdPosition()));
				}
				
				int number_of_positions = positions.size();
				int number_of_users = appuserService.findAll().size();
				
				model.addAttribute("positions", positions);
				model.addAttribute("number_of_positions",number_of_positions);
				model.addAttribute("number_of_users",number_of_users);
							
				model.addAttribute("googleAPIurl","https://maps.googleapis.com/maps/api/js?v=3.11&sensor=false");
				
				addCurrentUser(model);
				
				return "user/places";
			}
			
			
			@RequestMapping(value = "/feedback", method = RequestMethod.GET)
			public ModelAndView userFeedbackPagina() {

			  ModelAndView model = new ModelAndView();
			  model.setViewName("user/feedback");
			  addCurrentUser(model);
			  model.addObject("googleAPIurl", "https://maps.googleapis.com/maps/api/js?key=AIzaSyAW6_kB9yFhHlKMU0wZRDrgPdlAzQjpj5c&signed_in=true&callback=initMap");
			  model.addObject("asyncdefer"," async defer");
			  return model;
			}
			
			//--------------------------------------------------------------------------------------
			//Private helper methods
			//--------------------------------------------------------------------------------------
			@Autowired
		    private ServletContext servletContext;
			
			
			private Position getAppuserLastPos(Appuser appuser){
				return positionService.findById(appuser.getIdPosition());
				
			}
			
			//Get all the feedback of user
			private List<Feedback> getAllFeedback(Appuser appuser){
				List<Feedback> feedbacks = feedbackService.findAll();
				List<Feedback> feedbacks_return = new ArrayList<Feedback>();
				for(Feedback feedback : feedbacks){
					if(feedback.getIdUser().equals(appuser.getId())){
						feedbacks_return.add(feedback);
					}
				}
				
				return feedbacks_return;
			}
			
			//Get all the positions on which a user has given feedback
			private List<Position> getAllFeedbackPositions(Appuser appuser){
				
				List<Position> positions = new ArrayList<Position>();
				List<Feedback> feedbacks = feedbackService.findAll();
				for(Feedback feedback : feedbacks){
					if(feedback.getIdUser().equals(appuser.getId())){
						positions.add(positionService.findById(feedback.getIdPosition()));
					}
				}
				
				return positions;
			}
			
			//Returns all the Photo ids of a feedback
			private List<String> getFeedbackPhotoIds(Feedback f){
//				List<Long> photoids = new ArrayList<Long>();
				List<String> photo_urls = new ArrayList<String>();
				List<FeedbackPhoto> fplist = feedbackPhotoService.findAll();
				String url = servletContext.getRealPath("/");
				
				for(FeedbackPhoto fbphoto : fplist){
					if(f.getId() == fbphoto.getIdFeedback()){
//						photoids.add(fbphoto.getIdPhoto());
						photo_urls.add(/*url + */fbphoto.getIdPhoto() + "_FULL.jpg");
						System.out.println("url:" + url + fbphoto.getIdPhoto() + "_FULL.jpg");
					}
				}		
				return photo_urls;
			}
			
			//get the country name from a feedback
			private String getCountry(Feedback f){
				Long positionid = f.getIdPosition();
				Position pos = positionService.findById(positionid);
				return pos.getCountry();
			}
			
			//get the city name from a feedback
			private String getCity(Feedback f){
				Long positionid = f.getIdPosition();
				Position pos = positionService.findById(positionid);
				return pos.getCity();
			}
			
			//get the typeinfo name from of feedback
			private String getTypeinfoname (Feedback f){
				Long typeinfoid = f.getIdTypeinfo();
				Typeinfo ti = typeinfoService.findById(typeinfoid);
				return ti.getDescription();
			}
			
			private List<String> getAllTypeInfos(List<Feedback> feedbacks){
				List<String> typeinfos = new ArrayList<String>();
				for (Feedback f : feedbacks) {
					typeinfos.add(typeinfoService.findById(f.getIdTypeinfo()).getDescription());
				}
				
				removeDuplicates(typeinfos);
				
				return typeinfos;
			}
			
			private List<Position> getAllPositionsWithFeedbackOn(List<Feedback> feedbacks){
				List<Position> positions = new ArrayList<Position>();
				for (Feedback f : feedbacks) {
					positions.add(positionService.findById(f.getIdPosition()));
				}
				
				return positions;
			}
			
			private List<String> getCountries(List<Position> positions){
				
				//Get all countries of those positions
				List<String> listOfCountries = new ArrayList<String>();
				for (Position pos : positions) {
				   String country = pos.getCountry();
//				   System.out.println(country);
				   listOfCountries.add(country);
				   
				   removeDuplicates(listOfCountries);
				}
				
				return listOfCountries;
			}
			
			private List<String> getCities(List<Position> positions){
				List<String> listOfCities = new ArrayList<String>();
				for (Position pos : positions) {
				   String city = pos.getCity();
//				   System.out.println(country);
				   listOfCities.add(city);
				}
				
				removeDuplicates(listOfCities);
				
				return listOfCities;
			}
			
			private List<String> removeDuplicates(List<String> al){
				Set<String> hs = new HashSet<String>();
				hs.addAll(al);
				al.clear();
				al.addAll(hs);
				java.util.Collections.sort(al);
				
				return al;
			}
			
			private List<Feedback> getSelectionWithCriteria(String country, String city, String typeinfo){
				
				boolean countrySelected = true;
				boolean citySelected = true;
				boolean typeinfoSelected = true;
				
				if(country.equals("NONE"))
					countrySelected = false;
				if(city.equals("NONE"))
					citySelected = false;
				if(typeinfo.equals("NONE"))
					typeinfoSelected = false;
				
				System.out.println("country: " + country);
				System.out.println("city: " + city);
				System.out.println("typeinfo: " + typeinfo);
				
				
				List<Typeinfo> selectedTypeinfos = new ArrayList<Typeinfo>();
				List<Typeinfo> typeinfos = new ArrayList<Typeinfo>();
				typeinfos = typeinfoService.findAll();
				if(typeinfoSelected){
					for(Typeinfo ti : typeinfos){
						if(ti.getDescription().equals(typeinfo)){
							selectedTypeinfos.add(ti);
						}
					}
				}
				else {
					selectedTypeinfos = typeinfos;
				}
				
				//get all positions in DB
				List<Position> positions = new ArrayList<Position>();
				positions = positionService.findAll();
				
				//List will contain the positions of the selected country
				List<Position> selectedPositionsCountry = new ArrayList<Position>();
				
				if (countrySelected) {
					for (Position position : positions) {
						if (position.getCountry().equals(country)) {
							selectedPositionsCountry.add(position);
						}
					} 
				}
				else {
					selectedPositionsCountry = positions;
				}
				
				//List will contain the positions of the selected country and city
				List<Position> selectedPositionsCountryCity = new ArrayList<Position>();
				
				if (citySelected) {
					for (Position position :  selectedPositionsCountry) {
						if (position.getCity().equals(city)) {
							selectedPositionsCountryCity.add(position);
						}
					} 
				}
				else {
					selectedPositionsCountryCity = selectedPositionsCountry;
				}
				
						
				//get all feedbacks in DB
				List<Feedback> feedbacks = new ArrayList<Feedback>();
				feedbacks = feedbackService.findAll();
				
				//Will contain all feedbacks with the asked criteria
				List<Feedback> selectedFeedbacksPosition = new ArrayList<Feedback>();
				
				for(Feedback f : feedbacks){
					for(Position p : selectedPositionsCountryCity){
						if(f.getIdPosition() == p.getId())
							selectedFeedbacksPosition.add(f);
					}
				}
				
				//Will contain the final list with all feedbacks that answer the criteria
				List<Feedback> selectedFeedbacksFinal = new ArrayList<Feedback>();
				for(Feedback fb : selectedFeedbacksPosition){
					for(Typeinfo ti : selectedTypeinfos){
						if(fb.getIdTypeinfo() == ti.getId()){
							selectedFeedbacksFinal.add(fb);	
						}
					}
				}
				
				return selectedFeedbacksFinal;
				
			}//end of method getSelectionWithCriteria
			
			private void populateModel(Model model){
				//get all feedbacks in DB
				List<Feedback> feedbacks = new ArrayList<Feedback>();
				feedbacks = feedbackService.findAll();
				
				List<Position> positions = getAllPositionsWithFeedbackOn(feedbacks);
				model.addAttribute("listOfCountries",getCountries(positions));
				model.addAttribute("listOfCities", getCities(positions));
				model.addAttribute("listOfTypeInfos",getAllTypeInfos(feedbacks));
				
//				log("model populated");
			}

			private Appuser getAppuser(String username){
			List<Appuser> appuser_list = appuserService.findAll();
			for(Appuser user : appuser_list){
				if(user.getUsername().equals(username))
					return user;
			}
			return new Appuser();
			}
			
			//add the current user
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
			
			//add the current user
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