/*
 * Created on 28 okt 2015 ( Time 11:00:55 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.backpacker4.web.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//--- Common classes
import org.backpacker4.web.common.AbstractController;
import org.backpacker4.web.common.FormMode;
import org.backpacker4.web.common.Message;
import org.backpacker4.web.common.MessageType;

//--- Entities
import org.backpacker4.bean.UserRoles;
import org.backpacker4.bean.Appuser;

//--- Services 
import org.backpacker4.business.service.UserRolesService;
import org.backpacker4.business.service.AppuserService;

//--- List Items 
import org.backpacker4.web.listitem.AppuserListItem;

/**
 * Spring MVC controller for 'UserRoles' management.
 */
@Controller
@RequestMapping("/userRoles")
public class UserRolesController extends AbstractController {

	//--- Variables names ( to be used in JSP with Expression Language )
	private static final String MAIN_ENTITY_NAME = "userRoles";
	private static final String MAIN_LIST_NAME   = "list";

	//--- JSP pages names ( View name in the MVC model )
	private static final String JSP_FORM   = "userRoles/form";
	private static final String JSP_LIST   = "userRoles/list";

	//--- SAVE ACTION ( in the HTML form )
	private static final String SAVE_ACTION_CREATE   = "/userRoles/create";
	private static final String SAVE_ACTION_UPDATE   = "/userRoles/update";

	//--- Main entity service
	@Resource
    private UserRolesService userRolesService; // Injected by Spring
	//--- Other service(s)
	@Resource
    private AppuserService appuserService; // Injected by Spring

	//--------------------------------------------------------------------------------------
	/**
	 * Default constructor
	 */
	public UserRolesController() {
		super(UserRolesController.class, MAIN_ENTITY_NAME );
		log("UserRolesController created.");
	}

	//--------------------------------------------------------------------------------------
	// Spring MVC model management
	//--------------------------------------------------------------------------------------
	/**
	 * Populates the combo-box "items" for the referenced entity "Appuser"
	 * @param model
	 */
	private void populateListOfAppuserItems(Model model) {
		List<Appuser> list = appuserService.findAll();
		List<AppuserListItem> items = new LinkedList<AppuserListItem>();
		for ( Appuser appuser : list ) {
			items.add(new AppuserListItem( appuser ) );
		}
		model.addAttribute("listOfAppuserItems", items ) ;
	}


	/**
	 * Populates the Spring MVC model with the given entity and eventually other useful data
	 * @param model
	 * @param userRoles
	 */
	private void populateModel(Model model, UserRoles userRoles, FormMode formMode) {
		//--- Main entity
		model.addAttribute(MAIN_ENTITY_NAME, userRoles);
		if ( formMode == FormMode.CREATE ) {
			model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE); 			
			//--- Other data useful in this screen in "create" mode (all fields)
			populateListOfAppuserItems(model);
		}
		else if ( formMode == FormMode.UPDATE ) {
			model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE); 			
			//--- Other data useful in this screen in "update" mode (only non-pk fields)
			populateListOfAppuserItems(model);
		}
	}

	//--------------------------------------------------------------------------------------
	// Request mapping
	//--------------------------------------------------------------------------------------
	/**
	 * Shows a list with all the occurrences of UserRoles found in the database
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping()
	public String list(Model model) {
		log("Action 'list'");
		List<UserRoles> list = userRolesService.findAll();
		model.addAttribute(MAIN_LIST_NAME, list);		
		return JSP_LIST;
	}

	/**
	 * Shows a form page in order to create a new UserRoles
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping("/form")
	public String formForCreate(Model model) {
		log("Action 'formForCreate'");
		//--- Populates the model with a new instance
		UserRoles userRoles = new UserRoles();	
		populateModel( model, userRoles, FormMode.CREATE);
		return JSP_FORM;
	}

	/**
	 * Shows a form page in order to update an existing UserRoles
	 * @param model Spring MVC model
	 * @param iduserRoles  primary key element
	 * @return
	 */
	@RequestMapping(value = "/form/{iduserRoles}")
	public String formForUpdate(Model model, @PathVariable("iduserRoles") Integer iduserRoles ) {
		log("Action 'formForUpdate'");
		//--- Search the entity by its primary key and stores it in the model 
		UserRoles userRoles = userRolesService.findById(iduserRoles);
		populateModel( model, userRoles, FormMode.UPDATE);		
		return JSP_FORM;
	}

	/**
	 * 'CREATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param userRoles  entity to be created
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/create" ) // GET or POST
	public String create(@Valid UserRoles userRoles, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'create'");
		try {
			if (!bindingResult.hasErrors()) {
				UserRoles userRolesCreated = userRolesService.create(userRoles); 
				model.addAttribute(MAIN_ENTITY_NAME, userRolesCreated);

				//---
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				return redirectToForm(httpServletRequest, userRoles.getIduserRoles() );
			} else {
				populateModel( model, userRoles, FormMode.CREATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			log("Action 'create' : Exception - " + e.getMessage() );
			messageHelper.addException(model, "userRoles.error.create", e);
			populateModel( model, userRoles, FormMode.CREATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'UPDATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param userRoles  entity to be updated
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/update" ) // GET or POST
	public String update(@Valid UserRoles userRoles, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'update'");
		try {
			if (!bindingResult.hasErrors()) {
				//--- Perform database operations
				UserRoles userRolesSaved = userRolesService.update(userRoles);
				model.addAttribute(MAIN_ENTITY_NAME, userRolesSaved);
				//--- Set the result message
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				log("Action 'update' : update done - redirect");
				return redirectToForm(httpServletRequest, userRoles.getIduserRoles());
			} else {
				log("Action 'update' : binding errors");
				populateModel( model, userRoles, FormMode.UPDATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			messageHelper.addException(model, "userRoles.error.update", e);
			log("Action 'update' : Exception - " + e.getMessage() );
			populateModel( model, userRoles, FormMode.UPDATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'DELETE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param redirectAttributes
	 * @param iduserRoles  primary key element
	 * @return
	 */
	@RequestMapping(value = "/delete/{iduserRoles}") // GET or POST
	public String delete(RedirectAttributes redirectAttributes, @PathVariable("iduserRoles") Integer iduserRoles) {
		log("Action 'delete'" );
		try {
			userRolesService.delete( iduserRoles );
			//--- Set the result message
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));	
		} catch(Exception e) {
			messageHelper.addException(redirectAttributes, "userRoles.error.delete", e);
		}
		return redirectToList();
	}

}
