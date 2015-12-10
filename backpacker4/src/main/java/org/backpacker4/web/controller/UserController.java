package org.backpacker4.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.backpacker4.bean.Appuser;
import org.backpacker4.business.service.AppuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.backpacker4.bean.Feedback;
import org.backpacker4.bean.FeedbackPhoto;
import org.backpacker4.bean.FileUpload;
import org.backpacker4.bean.Photo;
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
import org.backpacker4.web.common.FormMode;
import org.backpacker4.web.common.Message;
import org.backpacker4.web.common.MessageType;
import org.backpacker4.web.listitem.PhotoListItem;
import org.backpacker4.web.listitem.PositionListItem;
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
			
			
			/**
			 * Populates the Spring MVC model with the given entity and eventually other useful data
			 * @param model
			 * @param appuser
			 */
			private void populateModel(Model model, Appuser appuser, FormMode formMode) {
				//--- Main entity
				model.addAttribute("appuser", appuser);
				if ( formMode == FormMode.CREATE ) {
					model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
					model.addAttribute(SAVE_ACTION, "/user/create"); 			
					//--- Other data useful in this screen in "create" mode (all fields)
					populateListOfPositionItems(model);
					populateListOfPhotoItems(model);
				}
				else if ( formMode == FormMode.UPDATE ) {
					model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
					model.addAttribute(SAVE_ACTION, "/user/update"); 			
					//--- Other data useful in this screen in "update" mode (only non-pk fields)
					populateListOfPhotoItems(model);
					populateListOfPositionItems(model);
				}
			}
			
			
			//--------------------------------------------------------------------------------------
			// Spring MVC model management
			//--------------------------------------------------------------------------------------
			/**
			 * Populates the combo-box "items" for the referenced entity "Position"
			 * @param model
			 */
			private void populateListOfPositionItems(Model model) {
				List<Position> list = positionService.findAll();
				List<PositionListItem> items = new LinkedList<PositionListItem>();
				for ( Position position : list ) {
					items.add(new PositionListItem( position ) );
				}
				model.addAttribute("listOfPositionItems", items ) ;
			}

			/**
			 * Populates the combo-box "items" for the referenced entity "Photo"
			 * @param model
			 */
			private void populateListOfPhotoItems(Model model) {
				List<Photo> list = photoService.findAll();
				List<PhotoListItem> items = new LinkedList<PhotoListItem>();
				for ( Photo photo : list ) {
					items.add(new PhotoListItem( photo ) );
				}
				model.addAttribute("listOfPhotoItems", items ) ;
			}
			
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
			 * Shows a form page in order to update an existing Appuser
			 * @param model Spring MVC model
			 * @param id  primary key element
			 * @return
			 */
			@RequestMapping(value = "/form")
			public String formForUpdate(Model model) {
				log("Action 'formForUpdate'");
				//--- Search current user and store it in the model 
				Appuser appuser = getCurrentUser();
				populateModel( model, appuser, FormMode.UPDATE);
				model.addAttribute("reverseGeoloactionURL", "https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false");
//				addCurrentUser(model);
				return "user/form";
			}
			
			
			/**
			 * 'UPDATE' action processing. <br>
			 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
			 * @param appuser  entity to be updated
			 * @param bindingResult Spring MVC binding result
			 * @param model Spring MVC model
			 * @param redirectAttributes Spring MVC redirect attributes
			 * @param httpServletRequest
			 * @return
			 */
			@RequestMapping(value = "/update" ) // GET or POST
			public String update(@Valid Appuser appuser, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest
					, @RequestParam(value = "image", required = false) MultipartFile image
					) {
				log("Action 'update'");
				try {
					if (!bindingResult.hasErrors()) {
									
						//Update the user
						Long pid = appuserService.findById(appuser.getId()).getIdPosition();
						appuser.setIdPosition(pid);
						
						Long fid = appuserService.findById(appuser.getId()).getIdPhoto();
						appuser.setIdPhoto(fid);

						Appuser appuserCreated = appuserService.update(appuser); 
						
						//Save the position
						Position positionCreated = updatePosition(httpServletRequest, appuserCreated);
						
						//Add the image to Database column photo
						if (!image.isEmpty()) {
							try {
//									validateImage(image);
									System.out.println("image validation (jpg) succeeded...");
							} catch (RuntimeException re) {
							bindingResult.reject(re.getMessage());
							return redirectToForm1(httpServletRequest, appuserCreated.getId() );
							}
							 
							try {
								Photo afbeelding;
								afbeelding = new Photo();
								afbeelding.setId(Long.parseLong(String.valueOf(0)));
								afbeelding.setComment("");
								//dummy value for position
								afbeelding.setIdPosition(Long.parseLong(String.valueOf(1)));
								afbeelding.setDescription(appuserCreated.getUsername());
								afbeelding.setThumbnail("");
								afbeelding.setFullphoto("");
								Calendar cal = Calendar.getInstance();
								afbeelding.setDatetaken(cal.getTime());
								System.out.println("afbeelding created ...");
								
								//Add photo to database
								Photo afbeeldingSaved = photoService.create(afbeelding);
								System.out.println("afbeelding saved ...");
								
								
								afbeeldingSaved.setFullphoto(afbeeldingSaved.getId() + "_FULL");
								afbeeldingSaved.setThumbnail(afbeeldingSaved.getId() + "_THUMB");
												
								
								//Save full image and thumbnail on server
								saveImage( image, afbeeldingSaved);
								
//							    //Add the saved photoID to the appuser
							    appuserCreated.setIdPhoto(afbeeldingSaved.getId());
							    appuserCreated.setIdPosition(positionCreated.getId());
							    appuserService.update(appuserCreated);
							    System.out.println("User completly saved in DB!");
								
							} catch (IOException e) {
							bindingResult.reject(e.getMessage());
							return redirectToForm1(httpServletRequest, appuserCreated.getId() );
							}
							}
						else{
							System.out.println("image is empty, no photo upload...");
						}
						
						model.addAttribute("appuser", appuserCreated);
						
						//---
						messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
						return redirectToForm1(httpServletRequest, appuser.getId());
						
//						//--- Perform database operations
//						Appuser appuserSaved = appuserService.update(appuser);
//						model.addAttribute(MAIN_ENTITY_NAME, appuserSaved);
//						//--- Set the result message
//						messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
//						log("Action 'update' : update done - redirect");
//						return redirectToForm1(httpServletRequest, appuser.getId());
					} else {
						log("Action 'update' : binding errors");
						populateModel( model, appuser, FormMode.UPDATE);
//						return redirectToForm1(httpServletRequest, appuser.getId());
						return "user/form";
					}
				} catch(Exception e) {
					messageHelper.addException(model, "appuser.error.update", e);
					log("Action 'update' : Exception - " + e.getMessage() );
					populateModel( model, appuser, FormMode.UPDATE);
//					return redirectToForm1(httpServletRequest, appuser.getId());
					return "user/form";
				}
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
				
				model.addAttribute("feedbacks", selection);
				
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
			
			 
		    @RequestMapping(value = "feedback1/savefiles", method = RequestMethod.POST)
		    public String saveNewFeedback(
		            @ModelAttribute("uploadForm") FileUpload uploadForm,
		            Model map, HttpServletRequest httpServletRequest) throws IllegalStateException, IOException {
		       
		    	String saveDirectory = servletContext.getRealPath("/");
		        System.out.println("Files will be saved at:" + saveDirectory);
		        
		        //Get parameters from request
		        String typeinfo = httpServletRequest.getParameter("typeinfo");
		        Long typeinfoid = getTypeinfoId(typeinfo);
		        String comment = httpServletRequest.getParameter("comment");
		        
		        System.out.println("typeinfo = " + typeinfo);
		        System.out.println("comment= " + comment);
		        
		      //Save the position
				Position positionCreated = savePosition(httpServletRequest);
				System.out.println("Postion:" + positionCreated.toString());
				
				//Get the date of today
				Calendar cal = Calendar.getInstance();
				Date today = cal.getTime();
				
				//Construct the feedback
				Feedback f = new Feedback();
				f.setId((long)0);
				f.setComment(comment);
				f.setIdTypeinfo(getTypeinfoId(typeinfo));
				f.setIdPosition(positionCreated.getId());
				f.setDatefeedback(today);
				f.setIdUser(getCurrentUser().getId());
				
				//Save the feedback in DB
				f = feedbackService.create(f);
		 
		        //Handle the files (images) to save
				List<MultipartFile> crunchifyFiles = uploadForm.getFiles();
		 
		        List<String> fileNames = new ArrayList<String>();
		 
		        if (null != crunchifyFiles && crunchifyFiles.size() > 0) {
		            for (MultipartFile multipartFile : crunchifyFiles) {
		            	
		                System.out.println(multipartFile.getContentType());
		                if (multipartFile.getSize()>0 && multipartFile.getContentType().equals("image/jpeg")) {
		                	Photo foto = constructPhoto(positionCreated,getCurrentUser(),f);
		                	String fileName = foto.getId() + "_FULL.jpg";
		                	
		                	// Handle file content - multipartFile.getInputStream()
		                	multipartFile
		                            .transferTo(new File(saveDirectory + fileName));
		                    fileNames.add(fileName);
		                }
		            }
		        }
		 
		        map.addAttribute("files", fileNames);
		        return "user/feedback1";
		    }
		    
		    @RequestMapping(value = "feedback1/updatefiles", method = RequestMethod.POST)
		    public String updateFeedback(
		            @ModelAttribute("uploadForm") FileUpload uploadForm,
		            Model map, HttpServletRequest httpServletRequest) throws IllegalStateException, IOException {
		        String saveDirectory = servletContext.getRealPath("/");
		        System.out.println("Files will be saved at:" + saveDirectory);
		        
		        //Get parameters from request
		        String typeinfo = httpServletRequest.getParameter("typeinfo");
		        Long typeinfoid = getTypeinfoId(typeinfo);
		        String comment = httpServletRequest.getParameter("comment");
		        String feedbackString = httpServletRequest.getParameter("feedbackid");
		        Feedback f = feedbackService.findById(Long.parseLong(feedbackString));
		        
		        System.out.println("typeinfo = " + typeinfo);
		        System.out.println("comment= " + comment);
		        
		      //Save the position
				Position position = positionService.findById(f.getIdPosition());
				position = updatePosition(httpServletRequest,position);
				System.out.println("Postion:" + position.toString());
				
				//Get the date of today
				Calendar cal = Calendar.getInstance();
				Date today = cal.getTime();
				
				//Construct the feedback, not the id
				f.setComment(comment);
				f.setIdTypeinfo(getTypeinfoId(typeinfo));
				f.setIdPosition(position.getId());
				f.setDatefeedback(today);
				f.setIdUser(getCurrentUser().getId());
				
				//Save the feedback in DB
				f = feedbackService.save(f);
		 
		        //Handle the files (images) to save
				List<MultipartFile> crunchifyFiles = uploadForm.getFiles();
		 
		        List<String> fileNames = new ArrayList<String>();
		 
		        if (null != crunchifyFiles && crunchifyFiles.size() > 0) {
		            for (MultipartFile multipartFile : crunchifyFiles) {
		            	Photo foto = constructPhoto(position,getCurrentUser(),f);
		                String fileName = foto.getId() + "_FULL.jpg";
		                if (!"".equalsIgnoreCase(fileName)) {
		                    // Handle file content - multipartFile.getInputStream()
		                    multipartFile
		                            .transferTo(new File(saveDirectory + fileName));
		                    fileNames.add(fileName);
		                }
		            }
		        }
		 
		        map.addAttribute("files", fileNames);
		        return "user/feedback1";
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
			
			@RequestMapping(value = "/feedback1", method = RequestMethod.GET)
			public ModelAndView userFeedback1Pagina() {

			  ModelAndView model = new ModelAndView();
			  model.setViewName("user/feedback1");
			  addCurrentUser(model);
			  model.addObject("googleAPIurl", "https://maps.googleapis.com/maps/api/js?key=AIzaSyAW6_kB9yFhHlKMU0wZRDrgPdlAzQjpj5c&signed_in=true&libraries=places&callback=initMap");
			  model.addObject("asyncdefer"," async defer");
			  return model;
			}
			
			@RequestMapping(value = "/feedback1/update", method = RequestMethod.GET)
			public ModelAndView userFeedback1UpdatePagina(HttpServletRequest httpServletRequest) {

			 long feedbackid = Long.parseLong(httpServletRequest.getParameter("feedbackid"));
			 Feedback feedback = feedbackService.findById(feedbackid);
			 Position position = positionService.findById(feedback.getIdPosition());
				
			 ModelAndView model = new ModelAndView();
			  model.setViewName("user/feedback1/update");
			  addCurrentUser(model);
			  model.addObject("googleAPIurl", "https://maps.googleapis.com/maps/api/js?key=AIzaSyAW6_kB9yFhHlKMU0wZRDrgPdlAzQjpj5c&signed_in=true&libraries=places&callback=initMap");
			  model.addObject("asyncdefer"," async defer");
			  model.addObject("feedback",feedback);
			  model.addObject("position",position);
			  return model;
			}
			
			/**
			 * Deletes a feedback with a given id (in request)
			 * @param model Spring MVC model
			 * @return
			 */
			@RequestMapping(value="/feedback/delete", method= RequestMethod.GET)
			public String deleteUserFeedback(Model model, HttpServletRequest httpServletRequest,
					RedirectAttributes redirectAttrs){
				
				String id = httpServletRequest.getParameter("id");
				Long feedbackid = Long.valueOf(id).longValue();
				
				try {
					feedbackService.delete(feedbackid);
					redirectAttrs.addFlashAttribute("deletesucces", "1");
				} catch (Exception e) {
					redirectAttrs.addFlashAttribute("deletesucces", "0");
					e.printStackTrace();
				}
				
				//redirect to the page we came from
				String returnpage = httpServletRequest.getParameter("returnpage");
				
				if(returnpage.equals("user_info")){
					String appuserInfoId = httpServletRequest.getParameter("userid");
//					redirectAttributes.addAttribute("id", appuserInfoId);
//					redirectAttrs.addFlashAttribute("deletesucces", "1");
					return "redirect:/user/info/" + appuserInfoId;
					
				}
				
				if(returnpage.equals("user_search_form")){
					String appuserInfoId = httpServletRequest.getParameter("userid");
//					redirectAttributes.addAttribute("id", appuserInfoId);
//					redirectAttrs.addFlashAttribute("deletesucces", "1");
					return "redirect:/user/search/form";
					
				}
				
				return "";

			}
			
			//--------------------------------------------------------------------------------------
			//Private helper methods
			//--------------------------------------------------------------------------------------
			@Autowired
		    private ServletContext servletContext;
			
			
			private Photo constructPhoto(Position p, Appuser appuser, Feedback feedback){
				Photo afbeelding = new Photo();
				afbeelding.setId(Long.parseLong(String.valueOf(0)));
				afbeelding.setComment("");
				//dummy value for position
				afbeelding.setIdPosition(p.getId());
				afbeelding.setDescription(appuser.getUsername());
				afbeelding.setThumbnail("");
				afbeelding.setFullphoto("");
				Calendar cal = Calendar.getInstance();
				afbeelding.setDatetaken(cal.getTime());
				System.out.println("afbeelding created ...");
				
				//Add photo to database
				Photo afbeeldingSaved = photoService.create(afbeelding);
				System.out.println("afbeelding saved with id..." + afbeeldingSaved.getId());
				
				
				afbeeldingSaved.setFullphoto(afbeeldingSaved.getId() + "_FULL");
				afbeeldingSaved.setThumbnail(afbeeldingSaved.getId() + "_THUMB");
				
				afbeeldingSaved = photoService.update(afbeeldingSaved);
				
				//Save in table Feedback_Photo
				FeedbackPhoto fp = new FeedbackPhoto();
				fp.setIdFeedback(feedback.getId());
				fp.setIdPhoto(afbeeldingSaved.getId());
				feedbackPhotoService.create(fp);
				
				return afbeeldingSaved;
			}
			
			private long getTypeinfoId(String typeinfoname){
				List<Typeinfo> list = typeinfoService.findAll();
				for(Typeinfo i : list){
					if (i.getDescription().equals(typeinfoname)){
						return i.getId();
					}
				}
			return 0;
			}
			
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
			
			private Appuser getCurrentUser(){
				UserDetails userDetails =
				(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				return getAppuser(userDetails.getUsername());
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
			
			private Position savePosition(HttpServletRequest httpServletRequest){
				
				String lat = httpServletRequest.getParameter("latitude");
				String lon = httpServletRequest.getParameter("longitude");
				String city = httpServletRequest.getParameter("city");
				String country = httpServletRequest.getParameter("country");
				System.out.println(city + "  " + country);
				
				BigDecimal bdlat = new BigDecimal(lat);
				BigDecimal bdlon = new BigDecimal(lon);
				
				Position pos = new Position();
				pos.setId((long) 0);
				pos.setLatitude(bdlat);
				pos.setLongitude(bdlon);
				pos.setCountry(country);
				pos.setCity(city);
				
				Position positionCreated = positionService.create(pos);
				return positionCreated;
			}
			
			private Position updatePosition(HttpServletRequest httpServletRequest, Position oldpos){
				
				String lat = httpServletRequest.getParameter("latitude");
				String lon = httpServletRequest.getParameter("longitude");
				String city = httpServletRequest.getParameter("city");
				String country = httpServletRequest.getParameter("country");
				System.out.println(city + "  " + country);
				
				BigDecimal bdlat = new BigDecimal(lat);
				BigDecimal bdlon = new BigDecimal(lon);
				
				Position pos = new Position();
				pos.setId(oldpos.getId());
				pos.setLatitude(bdlat);
				pos.setLongitude(bdlon);
				pos.setCountry(country);
				pos.setCity(city);
				
				Position positionUpdated = positionService.save(pos);
				return positionUpdated;
			}
			
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
			
			private void saveImage(MultipartFile image, Photo afbeelding)
					throws RuntimeException, IOException {
					try {
					File file = new File(servletContext.getRealPath("/") + "/"
					+ afbeelding.getFullphoto() + ".jpg");
					 
					//Save the full photo
					FileUtils.writeByteArrayToFile(file, image.getBytes());
					System.out.println("Go to the location:  " + file.toString()
					+ " on your computer and verify that the image has been stored.");
					
					//Convert the full image to thumbnail
					byte [] byteArr=image.getBytes();
					// convert byte array to BufferedImage
					InputStream in = new ByteArrayInputStream(byteArr);
					BufferedImage buffimg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
					buffimg = ImageIO.read(in);
					Image img = buffimg;
					
					BufferedImage thumbCreated = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
					thumbCreated.createGraphics().drawImage(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH),0,0,null);

					//Save the bufferedimage
					File outputfile = new File(servletContext.getRealPath("/") + "/"
							+ afbeelding.getThumbnail() + ".jpg");
				    ImageIO.write(thumbCreated, "jpg", outputfile);
				    
				    photoService.save(afbeelding);

					} catch (IOException e) {
					throw e;
					}
					}
			
			private void validateImage(MultipartFile image) {
				if (!image.getContentType().equals("image/jpg")) {
				throw new RuntimeException("Only JPG images are accepted");
				}
				}
			
			/**
			 * Returns "redirect:/entityName/form/id1/id2/..." 
			 * @param httpServletRequest
			 * @param idParts
			 * @return
			 */
			private String redirectToForm1(HttpServletRequest httpServletRequest, Object... idParts) {
				return "redirect:" + getFormURL1(httpServletRequest, idParts);
			}
			
			/**
			 * Returns "/entityName/form/id1/id2/..." 
			 * @param httpServletRequest
			 * @param idParts
			 * @return
			 */
			private String getFormURL1(HttpServletRequest httpServletRequest, Object... idParts) {
				return "/" + "user" + "/form/";
//				return "/" + "user" + "/form/" + encodeUrlPathSegments(httpServletRequest, idParts );
			}
			
}