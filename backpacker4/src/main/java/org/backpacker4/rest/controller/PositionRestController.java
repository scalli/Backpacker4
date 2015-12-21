
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
import org.backpacker4.bean.Position;
import org.backpacker4.business.service.PositionService;
import org.backpacker4.web.listitem.PositionListItem;

/**
 * Spring MVC controller for 'Position' management.
 */
@Controller
public class PositionRestController {

	@Resource
	private PositionService positionService;
	
	@RequestMapping( value="/items/position",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<PositionListItem> findAllAsListItems() {
		List<Position> list = positionService.findAll();
		List<PositionListItem> items = new LinkedList<PositionListItem>();
		for ( Position position : list ) {
			items.add(new PositionListItem( position ) );
		}
		return items;
	}
	
	@RequestMapping( value="/position",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Position> findAll() {
		return positionService.findAll();
	}

	@RequestMapping( value="/position/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Position findOne(@PathVariable("id") Long id) {
		return positionService.findById(id);
	}
	
	@RequestMapping( value="/position",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Position create(@RequestBody Position position) {
		return positionService.create(position);
	}

	@RequestMapping( value="/position/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Position update(@PathVariable("id") Long id, @RequestBody Position position) {
		return positionService.update(position);
	}

	@RequestMapping( value="/position/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		positionService.delete(id);
	}
	
}
