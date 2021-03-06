
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
import org.backpacker4.bean.Photo;
import org.backpacker4.business.service.PhotoService;
import org.backpacker4.web.listitem.PhotoListItem;

/**
 * Spring MVC controller for 'Photo' management.
 */
@Controller
public class PhotoRestController {

	@Resource
	private PhotoService photoService;
	
	@RequestMapping( value="/items/photo",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<PhotoListItem> findAllAsListItems() {
		List<Photo> list = photoService.findAll();
		List<PhotoListItem> items = new LinkedList<PhotoListItem>();
		for ( Photo photo : list ) {
			items.add(new PhotoListItem( photo ) );
		}
		return items;
	}
	
	@RequestMapping( value="/photo",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Photo> findAll() {
		return photoService.findAll();
	}

	@RequestMapping( value="/photo/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Photo findOne(@PathVariable("id") Long id) {
		return photoService.findById(id);
	}
	
	@RequestMapping( value="/photo",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Photo create(@RequestBody Photo photo) {
		return photoService.create(photo);
	}

	@RequestMapping( value="/photo/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Photo update(@PathVariable("id") Long id, @RequestBody Photo photo) {
		return photoService.update(photo);
	}

	@RequestMapping( value="/photo/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		photoService.delete(id);
	}
	
}
