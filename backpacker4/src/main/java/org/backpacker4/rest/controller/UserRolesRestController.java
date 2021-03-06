
package org.backpacker4.rest.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.backpacker4.bean.UserRoles;
import org.backpacker4.business.service.UserRolesService;
import org.backpacker4.web.listitem.UserRolesListItem;

/**
 * Spring MVC controller for 'UserRoles' management.
 */
@Controller
public class UserRolesRestController {

	@Resource
	private UserRolesService userRolesService;
	
	@RequestMapping( value="/items/userRoles",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<UserRolesListItem> findAllAsListItems() {
		List<UserRoles> list = userRolesService.findAll();
		List<UserRolesListItem> items = new LinkedList<UserRolesListItem>();
		for ( UserRoles userRoles : list ) {
			items.add(new UserRolesListItem( userRoles ) );
		}
		return items;
	}
	
	@RequestMapping( value="/userRoles",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<UserRoles> findAll() {
		return userRolesService.findAll();
	}

	@RequestMapping( value="/userRoles/{iduserRoles}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public UserRoles findOne(@PathVariable("iduserRoles") Integer iduserRoles) {
		return userRolesService.findById(iduserRoles);
	}
	
	@RequestMapping( value="/userRoles",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public UserRoles create(@RequestBody UserRoles userRoles) {
		return userRolesService.create(userRoles);
	}

	@RequestMapping( value="/userRoles/{iduserRoles}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public UserRoles update(@PathVariable("iduserRoles") Integer iduserRoles, @RequestBody UserRoles userRoles) {
		return userRolesService.update(userRoles);
	}

	@RequestMapping( value="/userRoles/{iduserRoles}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("iduserRoles") Integer iduserRoles) {
		userRolesService.delete(iduserRoles);
	}
	
}
