
package org.backpacker4.rest.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import org.backpacker4.bean.Feedback;
import org.backpacker4.bean.FeedbackReadable;
import org.backpacker4.bean.Position;
import org.backpacker4.bean.Typeinfo;
import org.backpacker4.business.service.AppuserService;
import org.backpacker4.business.service.FeedbackService;
import org.backpacker4.business.service.PositionService;
import org.backpacker4.business.service.TypeinfoService;
import org.backpacker4.web.listitem.FeedbackListItem;
import org.backpacker4.web.listitem.FeedbackReadableListItem;

/**
 * Spring MVC controller for 'FeedbackReadable' management.
 */
@Controller
public class FeedbackReadableRestController {

	@Resource
	private FeedbackService feedbackService;
	@Resource
	private PositionService positionService;
	@Resource
	private TypeinfoService typeinfoService;
	@Resource
	private AppuserService appuserService;
	
	@RequestMapping( value="/items/feedbackreadable",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<FeedbackReadableListItem> findAllAsListItems() {
		List<FeedbackReadableListItem> items =  new LinkedList<FeedbackReadableListItem>();
		List<Feedback> feedbacks = feedbackService.findAll();
	
		for ( Feedback feedback : feedbacks ) {
			
			FeedbackReadable feedbackr = toFeedbackReadable(feedback);
			
			items.add(new FeedbackReadableListItem( feedbackr ) );
		}
		return items;
	}
	
	@RequestMapping( value="/feedbackreadable",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<FeedbackReadable> findAll() {
		
		List<FeedbackReadable> list = new ArrayList<FeedbackReadable>();
		List<Feedback> feedbacks = feedbackService.findAll();
		
		for ( Feedback feedback : feedbacks ) {
			
			FeedbackReadable feedbackr = toFeedbackReadable(feedback);
			
			list.add(feedbackr);
		}
		
		return list;
	}

	@RequestMapping( value="/feedbackreadable/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public FeedbackReadable findOne(@PathVariable("id") Long id) {
		return toFeedbackReadable(feedbackService.findById(id));
	}
	
	@RequestMapping( value="/feedbackreadableselection",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<FeedbackReadable> findByCriteria(HttpServletRequest httpServletRequest){
		List<FeedbackReadable> list = new ArrayList<FeedbackReadable>();
		
		String country = httpServletRequest.getParameter("country");
		String city = httpServletRequest.getParameter("city");
		String typeinfo = httpServletRequest.getParameter("typeinfo");
		
		//Get the list of all selected Feedback objects
		List<Feedback> selection = getSelectionWithCriteria(country,city,typeinfo);
		
		for ( Feedback feedback : selection ) {
			
			FeedbackReadable feedbackr = toFeedbackReadable(feedback);
			
			list.add(feedbackr);
		}
		
		return list;
	}
	
	private FeedbackReadable toFeedbackReadable(Feedback feedback){
		
		Position position = positionService.findById(feedback.getIdPosition());
		Typeinfo typeinfo = typeinfoService.findById(feedback.getIdTypeinfo());
		Appuser appuser = appuserService.findById(feedback.getIdUser());
					
		FeedbackReadable feedbackr = new FeedbackReadable();
		feedbackr.setId(feedback.getId());
		feedbackr.setCity(position.getCity());
		feedbackr.setCountry(position.getCountry());
		feedbackr.setLatitude(position.getLatitude());
		feedbackr.setLongitude(position.getLongitude());
		feedbackr.setComment(feedback.getComment());
		feedbackr.setDatefeedback(feedback.getDatefeedback());
		feedbackr.setTypeinfo(typeinfo.getDescription());
		feedbackr.setUsername(appuser.getUsername());
		
		return feedbackr;
	}
	
	private List<Feedback> getSelectionWithCriteria(String country, String city, String typeinfo){
		
		boolean countrySelected = true;
		boolean citySelected = true;
		boolean typeinfoSelected = true;
		
		if(country == null)
			countrySelected = false;
		if(city == null)
			citySelected = false;
		if(typeinfo == null)
			typeinfoSelected = false;
		
		System.out.println("country: " + country);
		System.out.println("city: " + city);
		System.out.println("typeinfo: " + typeinfo);
		
		
		List<Typeinfo> selectedTypeinfos = new ArrayList<Typeinfo>();
		List<Typeinfo> typeinfos = new ArrayList<Typeinfo>();
		typeinfos = typeinfoService.findAll();
		if(typeinfoSelected){
			for(Typeinfo ti : typeinfos){
				if(ti.getDescription().equals(typeinfo)){
					selectedTypeinfos.add(ti);
				}
			}
		}
		else {
			selectedTypeinfos = typeinfos;
		}
		
		//get all positions in DB
		List<Position> positions = new ArrayList<Position>();
		positions = positionService.findAll();
		
		//List will contain the positions of the selected country
		List<Position> selectedPositionsCountry = new ArrayList<Position>();
		
		if (countrySelected) {
			for (Position position : positions) {
				if (position.getCountry().equals(country)) {
					selectedPositionsCountry.add(position);
				}
			} 
		}
		else {
			selectedPositionsCountry = positions;
		}
		
		//List will contain the positions of the selected country and city
		List<Position> selectedPositionsCountryCity = new ArrayList<Position>();
		
		if (citySelected) {
			for (Position position :  selectedPositionsCountry) {
				if (position.getCity().equals(city)) {
					selectedPositionsCountryCity.add(position);
				}
			} 
		}
		else {
			selectedPositionsCountryCity = selectedPositionsCountry;
		}
		
				
		//get all feedbacks in DB
		List<Feedback> feedbacks = new ArrayList<Feedback>();
		feedbacks = feedbackService.findAll();
		
		//Will contain all feedbacks with the asked criteria
		List<Feedback> selectedFeedbacksPosition = new ArrayList<Feedback>();
		
		for(Feedback f : feedbacks){
			for(Position p : selectedPositionsCountryCity){
				if(f.getIdPosition() == p.getId())
					selectedFeedbacksPosition.add(f);
			}
		}
		
		//Will contain the final list with all feedbacks that answer the criteria
		List<Feedback> selectedFeedbacksFinal = new ArrayList<Feedback>();
		for(Feedback fb : selectedFeedbacksPosition){
			for(Typeinfo ti : selectedTypeinfos){
				if(fb.getIdTypeinfo() == ti.getId()){
					selectedFeedbacksFinal.add(fb);	
				}
			}
		}
		
		return selectedFeedbacksFinal;
		
	}//end of method getSelectionWithCriteria
}
