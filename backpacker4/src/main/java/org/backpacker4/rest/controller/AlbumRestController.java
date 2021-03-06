
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
import org.backpacker4.bean.Album;
import org.backpacker4.business.service.AlbumService;
import org.backpacker4.web.listitem.AlbumListItem;

/**
 * Spring MVC controller for 'Album' management.
 */
@Controller
public class AlbumRestController {

	@Resource
	private AlbumService albumService;
	
	@RequestMapping( value="/items/album",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<AlbumListItem> findAllAsListItems() {
		List<Album> list = albumService.findAll();
		List<AlbumListItem> items = new LinkedList<AlbumListItem>();
		for ( Album album : list ) {
			items.add(new AlbumListItem( album ) );
		}
		return items;
	}
	
	@RequestMapping( value="/album",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Album> findAll() {
		return albumService.findAll();
	}

	@RequestMapping( value="/album/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Album findOne(@PathVariable("id") Long id) {
		return albumService.findById(id);
	}
	
	@RequestMapping( value="/album",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Album create(@RequestBody Album album) {
		return albumService.create(album);
	}

	@RequestMapping( value="/album/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Album update(@PathVariable("id") Long id, @RequestBody Album album) {
		return albumService.update(album);
	}

	@RequestMapping( value="/album/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		albumService.delete(id);
	}
	
}
