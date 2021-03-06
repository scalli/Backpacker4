
package org.backpacker4.web.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//--- Common classes
import org.backpacker4.web.common.AbstractController;
import org.backpacker4.web.common.FormMode;
import org.backpacker4.web.common.Message;
import org.backpacker4.web.common.MessageType;

//--- Entities
import org.backpacker4.bean.Appuser;
import org.backpacker4.bean.Position;
import org.backpacker4.bean.UserRoles;
import org.backpacker4.bean.Photo;

//--- Services 
import org.backpacker4.business.service.AppuserService;
import org.backpacker4.business.service.PositionService;
import org.backpacker4.business.service.UserRolesService;
import org.backpacker4.business.service.PhotoService;

//--- List Items 
import org.backpacker4.web.listitem.PositionListItem;
import org.backpacker4.bean.Appuser;
import org.backpacker4.web.listitem.PhotoListItem;

/**
 * Spring MVC controller for 'Registration of new Appuser' management.
 */
@Controller
@RequestMapping("/register")
public class UserRegistrationController extends AbstractController {

	//--- Variables names ( to be used in JSP with Expression Language )
	private static final String MAIN_ENTITY_NAME = "appuser";
	private static final String MAIN_LIST_NAME   = "list";

	//--- JSP pages names ( View name in the MVC model )
	private static final String JSP_FORM   = "register/form";
	private static final String JSP_LIST   = "register/list";

	//--- SAVE ACTION ( in the HTML form )
	private static final String SAVE_ACTION_CREATE   = "/register/create";
	private static final String SAVE_ACTION_UPDATE   = "/register/update";

	//--- Main entity service
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
	public UserRegistrationController() {
		super(UserRegistrationController.class, MAIN_ENTITY_NAME );
		log("UserRegistrationController created.");
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


	/**
	 * Populates the Spring MVC model with the given entity and eventually other useful data
	 * @param model
	 * @param appuser
	 */
	private void populateModel(Model model, Appuser appuser, FormMode formMode) {
		//--- Main entity
		model.addAttribute(MAIN_ENTITY_NAME, appuser);
		if ( formMode == FormMode.CREATE ) {
			model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE); 			
			//--- Other data useful in this screen in "create" mode (all fields)
			populateListOfPositionItems(model);
			populateListOfPhotoItems(model);
		}
		else if ( formMode == FormMode.UPDATE ) {
			model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE); 			
			//--- Other data useful in this screen in "update" mode (only non-pk fields)
			populateListOfPhotoItems(model);
			populateListOfPositionItems(model);
		}
	}

	//--------------------------------------------------------------------------------------
	// Request mapping
	//--------------------------------------------------------------------------------------
	/**
	 * Shows a list with all the occurrences of Appuser found in the database
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping()
	public String list(Model model) {
		log("Action 'list'");
		List<Appuser> list = appuserService.findAll();
		model.addAttribute(MAIN_LIST_NAME, list);		
		return JSP_LIST;
	}

	/**
	 * Shows a form page in order to create a new Appuser
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping("/form")
	public String formForCreate(Model model) {
		log("Action 'formForCreate'");
		//--- Populates the model with a new instance
		Appuser appuser = new Appuser();	
		populateModel( model, appuser, FormMode.CREATE);
		model.addAttribute("reverseGeoloactionURL", "https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false");
		log("model populated");
		return JSP_FORM;
	}
	
	
	/**
	 * Shows a form page in order to update an existing Appuser
	 * @param model Spring MVC model
	 * @param id  primary key element
	 * @return
	 */
	@RequestMapping(value = "/form/{id}")
	public String formForUpdate(Model model, @PathVariable("id") Long id ) {
		log("Action 'formForUpdate'");
		//--- Search the entity by its primary key and stores it in the model 
		Appuser appuser = appuserService.findById(id);
		populateModel( model, appuser, FormMode.UPDATE);
		model.addAttribute("reverseGeoloactionURL", "https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false");
//		addCurrentUser(model);
		return JSP_FORM;
	}

	/**
	 * 'CREATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param appuser  entity to be created
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/create" ) // GET or POST
	public String create(@Valid Appuser appuser, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest
			, @RequestParam(value = "image", required = false) MultipartFile image
			) {
		log("Action 'create'");
		try {
			if (!bindingResult.hasErrors()) {
//				
				//Save the position
				Position positionCreated = savePosition(httpServletRequest);
				
				//Create the user and update his position
				Appuser appuserCreated = appuserService.create(appuser); 
				appuserCreated.setIdPosition(positionCreated.getId());
			    appuserService.update(appuserCreated);
				
				//Give the created user the ROLE_USER role
				UserRoles userroles = new UserRoles();
				userroles.setIduserRoles(0);
				userroles.setUserid(appuserCreated.getId());
				userroles.setUserrole("ROLE_USER");
				System.out.println("De userroles zijn: " + userroles.toString());
				userRolesService.create(userroles); 
				System.out.println("userroles created");
				
				//Add the image to Database column photo
				if (!image.isEmpty()) {
					System.out.println("image is not empty!");
//					try {
//							validateImage(image);
//							System.out.println("image validation (jpg) succeeded...");
//					} catch (RuntimeException re) {
//					bindingResult.reject(re.getMessage());
//					return redirectToForm1(httpServletRequest, appuserCreated.getId() );
//					}
					 
					try {
						Photo afbeelding = new Photo();
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
						
//					    //Add the saved photoID to the appuser
					    appuserCreated.setIdPhoto(afbeeldingSaved.getId());
					    appuserService.update(appuserCreated);
					    System.out.println("User completly saved in DB!");
						
					} catch (IOException e) {
					bindingResult.reject(e.getMessage());
					return redirectToForm1(httpServletRequest, appuserCreated.getId() );
					}
					}
				
				model.addAttribute(MAIN_ENTITY_NAME, appuserCreated);
				
				//---
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				return "register/succes";
//				return redirectToForm1(httpServletRequest, appuserCreated.getId() );
			} else {
				populateModel( model, appuser, FormMode.CREATE);
				System.out.println("Not all valid fields");
				return JSP_FORM;
			}
		} catch(Exception e) {
			log("Action 'create' : Exception - " + e.getMessage() );
			messageHelper.addException(model, "appuser.error.create", e);
			populateModel( model, appuser, FormMode.CREATE);
			System.out.println("Not all valid fields: exception");
			return JSP_FORM;
		}
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
				Long fid = appuserService.findById(appuser.getId()).getIdPhoto();
				appuser.setIdPhoto(fid);
				appuser.setIdPosition(pid);
				System.out.println("pid:" + pid + "   fid: " + fid);
				Appuser appuserCreated = appuserService.update(appuser); 
				
				//Save the position
				Position positionCreated = updatePosition(httpServletRequest, appuserCreated);
				
				//Give the created user the ROLE_USER role
//				UserRoles userroles = new UserRoles();
//				userroles.setIduserRoles(0);
//				userroles.setUserid(appuserCreated.getId());
//				userroles.setUserrole("ROLE_USER");
//				System.out.println(userroles.toString());
//				userRolesService.create(userroles); 
				
				//Add the image to Database column photo
				if (!image.isEmpty()) {
					try {
							validateImage(image);
							System.out.println("image validation (jpg) succeeded...");
					} catch (RuntimeException re) {
					bindingResult.reject(re.getMessage());
					return redirectToForm1(httpServletRequest, appuserCreated.getId() );
					}
					 
					try {
						Photo afbeelding;
						if(appuserCreated.getIdPhoto() != null)
							afbeelding = photoService.findById(appuserCreated.getIdPhoto());
						else {
							//No image has been uploaded yet
							afbeelding = new Photo();
							afbeelding.setId(Long.parseLong(String.valueOf(0)));
						}
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
						Photo afbeeldingSaved = photoService.update(afbeelding);
						System.out.println("afbeelding saved ...");
						
						
						afbeeldingSaved.setFullphoto(afbeeldingSaved.getId() + "_FULL");
						afbeeldingSaved.setThumbnail(afbeeldingSaved.getId() + "_THUMB");
										
						
						//Save full image and thumbnail on server
						saveImage( image, afbeeldingSaved);
						
//					    //Add the saved photoID to the appuser
//					    appuserCreated.setIdPhoto(afbeeldingSaved.getId());
//					    appuserCreated.setIdPosition(positionCreated.getId());
//					    appuserService.update(appuserCreated);
					    System.out.println("User completly saved in DB!");
						
					} catch (IOException e) {
					bindingResult.reject(e.getMessage());
					return redirectToForm1(httpServletRequest, appuserCreated.getId() );
					}
					}
				
				model.addAttribute(MAIN_ENTITY_NAME, appuserCreated);
				
				//---
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				return redirectToForm1(httpServletRequest, appuser.getId());
				
//				//--- Perform database operations
//				Appuser appuserSaved = appuserService.update(appuser);
//				model.addAttribute(MAIN_ENTITY_NAME, appuserSaved);
//				//--- Set the result message
//				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
//				log("Action 'update' : update done - redirect");
//				return redirectToForm1(httpServletRequest, appuser.getId());
			} else {
				log("Action 'update' : binding errors");
				populateModel( model, appuser, FormMode.UPDATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			messageHelper.addException(model, "appuser.error.update", e);
			log("Action 'update' : Exception - " + e.getMessage() );
			populateModel( model, appuser, FormMode.UPDATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'DELETE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param redirectAttributes
	 * @param id  primary key element
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}") // GET or POST
	public String delete(RedirectAttributes redirectAttributes, @PathVariable("id") Long id) {
		log("Action 'delete'" );
		try {
			appuserService.delete( id );
			//--- Set the result message
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));	
		} catch(Exception e) {
			messageHelper.addException(redirectAttributes, "appuser.error.delete", e);
		}
		return redirectToList();
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
		return "/" + "register" + "/form/" + encodeUrlPathSegments(httpServletRequest, idParts );
	}
	
	private void validateImage(MultipartFile image) {
		if (!image.getContentType().equals("image/jpg")) {
		throw new RuntimeException("Only JPG images are accepted");
		}
		}
	
	@Autowired
    private ServletContext servletContext;
	
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
				//Add current user only if someone is logged in	
				try {
					UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
							.getPrincipal();
					model.addAttribute("username", userDetails.getUsername());
					System.out.println("appuserid=" + getAppuser(userDetails.getUsername()).getId());
					;
					model.addAttribute("appuser", getAppuser(userDetails.getUsername()));
					String url = "";
					List<Appuser> allAppUsers = appuserService.findAll();
					for (Appuser appuser : allAppUsers) {
						if (appuser.getUsername().equals(userDetails.getUsername())) {
							url = servletContext.getRealPath("/") + appuser.getIdPhoto() + "_THUMB.jpg";
						}
					}
					model.addAttribute("thumburl", url);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
}
