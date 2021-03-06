
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
import org.backpacker4.bean.AlbumPhoto;
import org.backpacker4.business.service.AlbumPhotoService;
import org.backpacker4.web.listitem.AlbumPhotoListItem;

/**
 * Spring MVC controller for 'AlbumPhoto' management.
 */
@Controller
public class AlbumPhotoRestController {

	@Resource
	private AlbumPhotoService albumPhotoService;
	
	@RequestMapping( value="/items/albumPhoto",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<AlbumPhotoListItem> findAllAsListItems() {
		List<AlbumPhoto> list = albumPhotoService.findAll();
		List<AlbumPhotoListItem> items = new LinkedList<AlbumPhotoListItem>();
		for ( AlbumPhoto albumPhoto : list ) {
			items.add(new AlbumPhotoListItem( albumPhoto ) );
		}
		return items;
	}
	
	@RequestMapping( value="/albumPhoto",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<AlbumPhoto> findAll() {
		return albumPhotoService.findAll();
	}

	@RequestMapping( value="/albumPhoto/{idAlbum}/{idPhoto}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AlbumPhoto findOne(@PathVariable("idAlbum") Long idAlbum, @PathVariable("idPhoto") Long idPhoto) {
		return albumPhotoService.findById(idAlbum, idPhoto);
	}
	
	@RequestMapping( value="/albumPhoto",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AlbumPhoto create(@RequestBody AlbumPhoto albumPhoto) {
		return albumPhotoService.create(albumPhoto);
	}

	@RequestMapping( value="/albumPhoto/{idAlbum}/{idPhoto}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AlbumPhoto update(@PathVariable("idAlbum") Long idAlbum, @PathVariable("idPhoto") Long idPhoto, @RequestBody AlbumPhoto albumPhoto) {
		return albumPhotoService.update(albumPhoto);
	}

	@RequestMapping( value="/albumPhoto/{idAlbum}/{idPhoto}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("idAlbum") Long idAlbum, @PathVariable("idPhoto") Long idPhoto) {
		albumPhotoService.delete(idAlbum, idPhoto);
	}
	
}
