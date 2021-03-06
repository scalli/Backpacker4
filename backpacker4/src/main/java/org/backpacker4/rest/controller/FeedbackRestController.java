
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
import org.backpacker4.bean.Feedback;
import org.backpacker4.business.service.FeedbackService;
import org.backpacker4.web.listitem.FeedbackListItem;

/**
 * Spring MVC controller for 'Feedback' management.
 */
@Controller
public class FeedbackRestController {

	@Resource
	private FeedbackService feedbackService;
	
	@RequestMapping( value="/items/feedback",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<FeedbackListItem> findAllAsListItems() {
		List<Feedback> list = feedbackService.findAll();
		List<FeedbackListItem> items = new LinkedList<FeedbackListItem>();
		for ( Feedback feedback : list ) {
			items.add(new FeedbackListItem( feedback ) );
		}
		return items;
	}
	
	@RequestMapping( value="/feedback",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Feedback> findAll() {
		return feedbackService.findAll();
	}

	@RequestMapping( value="/feedback/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Feedback findOne(@PathVariable("id") Long id) {
		return feedbackService.findById(id);
	}
	
	@RequestMapping( value="/feedback",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Feedback create(@RequestBody Feedback feedback) {
		return feedbackService.create(feedback);
	}

	@RequestMapping( value="/feedback/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Feedback update(@PathVariable("id") Long id, @RequestBody Feedback feedback) {
		return feedbackService.update(feedback);
	}

	@RequestMapping( value="/feedback/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		feedbackService.delete(id);
	}
	
}
