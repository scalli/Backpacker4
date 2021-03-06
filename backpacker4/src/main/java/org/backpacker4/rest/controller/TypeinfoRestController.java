
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
import org.backpacker4.bean.Typeinfo;
import org.backpacker4.business.service.TypeinfoService;
import org.backpacker4.web.listitem.TypeinfoListItem;

/**
 * Spring MVC controller for 'Typeinfo' management.
 */
@Controller
public class TypeinfoRestController {

	@Resource
	private TypeinfoService typeinfoService;
	
	@RequestMapping( value="/items/typeinfo",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TypeinfoListItem> findAllAsListItems() {
		List<Typeinfo> list = typeinfoService.findAll();
		List<TypeinfoListItem> items = new LinkedList<TypeinfoListItem>();
		for ( Typeinfo typeinfo : list ) {
			items.add(new TypeinfoListItem( typeinfo ) );
		}
		return items;
	}
	
	@RequestMapping( value="/typeinfo",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Typeinfo> findAll() {
		return typeinfoService.findAll();
	}

	@RequestMapping( value="/typeinfo/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Typeinfo findOne(@PathVariable("id") Long id) {
		return typeinfoService.findById(id);
	}
	
	@RequestMapping( value="/typeinfo",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Typeinfo create(@RequestBody Typeinfo typeinfo) {
		return typeinfoService.create(typeinfo);
	}

	@RequestMapping( value="/typeinfo/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Typeinfo update(@PathVariable("id") Long id, @RequestBody Typeinfo typeinfo) {
		return typeinfoService.update(typeinfo);
	}

	@RequestMapping( value="/typeinfo/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		typeinfoService.delete(id);
	}
	
}
