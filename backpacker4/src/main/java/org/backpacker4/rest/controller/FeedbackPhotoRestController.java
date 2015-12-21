
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
import org.backpacker4.bean.FeedbackPhoto;
import org.backpacker4.business.service.FeedbackPhotoService;
import org.backpacker4.web.listitem.FeedbackPhotoListItem;

/**
 * Spring MVC controller for 'FeedbackPhoto' management.
 */
@Controller
public class FeedbackPhotoRestController {

	@Resource
	private FeedbackPhotoService feedbackPhotoService;
	
	@RequestMapping( value="/items/feedbackPhoto",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<FeedbackPhotoListItem> findAllAsListItems() {
		List<FeedbackPhoto> list = feedbackPhotoService.findAll();
		List<FeedbackPhotoListItem> items = new LinkedList<FeedbackPhotoListItem>();
		for ( FeedbackPhoto feedbackPhoto : list ) {
			items.add(new FeedbackPhotoListItem( feedbackPhoto ) );
		}
		return items;
	}
	
	@RequestMapping( value="/feedbackPhoto",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<FeedbackPhoto> findAll() {
		return feedbackPhotoService.findAll();
	}

	@RequestMapping( value="/feedbackPhoto/{idFeedback}/{idPhoto}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public FeedbackPhoto findOne(@PathVariable("idFeedback") Long idFeedback, @PathVariable("idPhoto") Long idPhoto) {
		return feedbackPhotoService.findById(idFeedback, idPhoto);
	}
	
	@RequestMapping( value="/feedbackPhoto",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public FeedbackPhoto create(@RequestBody FeedbackPhoto feedbackPhoto) {
		return feedbackPhotoService.create(feedbackPhoto);
	}

	@RequestMapping( value="/feedbackPhoto/{idFeedback}/{idPhoto}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public FeedbackPhoto update(@PathVariable("idFeedback") Long idFeedback, @PathVariable("idPhoto") Long idPhoto, @RequestBody FeedbackPhoto feedbackPhoto) {
		return feedbackPhotoService.update(feedbackPhoto);
	}

	@RequestMapping( value="/feedbackPhoto/{idFeedback}/{idPhoto}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("idFeedback") Long idFeedback, @PathVariable("idPhoto") Long idPhoto) {
		feedbackPhotoService.delete(idFeedback, idPhoto);
	}
	
}
