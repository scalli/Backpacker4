
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
import org.backpacker4.bean.Appuser;
import org.backpacker4.business.service.AppuserService;
import org.backpacker4.web.listitem.AppuserListItem;

/**
 * Spring MVC controller for 'Appuser' management.
 */
@Controller
public class AppuserRestController {

	@Resource
	private AppuserService appuserService;
	
	@RequestMapping( value="/items/appuser",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<AppuserListItem> findAllAsListItems() {
		List<Appuser> list = appuserService.findAll();
		List<AppuserListItem> items = new LinkedList<AppuserListItem>();
		for ( Appuser appuser : list ) {
			items.add(new AppuserListItem( appuser ) );
		}
		return items;
	}
	
	@RequestMapping( value="/appuser",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Appuser> findAll() {
		return appuserService.findAll();
	}

	@RequestMapping( value="/appuser/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Appuser findOne(@PathVariable("id") Long id) {
		return appuserService.findById(id);
	}
	
	@RequestMapping( value="/appuser",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Appuser create(@RequestBody Appuser appuser) {
		return appuserService.create(appuser);
	}

	@RequestMapping( value="/appuser/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Appuser update(@PathVariable("id") Long id, @RequestBody Appuser appuser) {
		return appuserService.update(appuser);
	}

	@RequestMapping( value="/appuser/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		appuserService.delete(id);
	}
	
}
