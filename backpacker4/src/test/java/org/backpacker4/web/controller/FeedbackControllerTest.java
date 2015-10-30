package org.backpacker4.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import org.backpacker4.bean.Feedback;
import org.backpacker4.bean.Appuser;
import org.backpacker4.bean.Photo;
import org.backpacker4.bean.Typeinfo;
import org.backpacker4.bean.Position;
import org.backpacker4.test.FeedbackFactoryForTest;
import org.backpacker4.test.AppuserFactoryForTest;
import org.backpacker4.test.PhotoFactoryForTest;
import org.backpacker4.test.TypeinfoFactoryForTest;
import org.backpacker4.test.PositionFactoryForTest;

//--- Services 
import org.backpacker4.business.service.FeedbackService;
import org.backpacker4.business.service.AppuserService;
import org.backpacker4.business.service.PhotoService;
import org.backpacker4.business.service.TypeinfoService;
import org.backpacker4.business.service.PositionService;

//--- List Items 
import org.backpacker4.web.listitem.AppuserListItem;
import org.backpacker4.web.listitem.PhotoListItem;
import org.backpacker4.web.listitem.TypeinfoListItem;
import org.backpacker4.web.listitem.PositionListItem;

import org.backpacker4.web.common.Message;
import org.backpacker4.web.common.MessageHelper;
import org.backpacker4.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackControllerTest {
	
	@InjectMocks
	private FeedbackController feedbackController;
	@Mock
	private FeedbackService feedbackService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private AppuserService appuserService; // Injected by Spring
	@Mock
	private PhotoService photoService; // Injected by Spring
	@Mock
	private TypeinfoService typeinfoService; // Injected by Spring
	@Mock
	private PositionService positionService; // Injected by Spring

	private FeedbackFactoryForTest feedbackFactoryForTest = new FeedbackFactoryForTest();
	private AppuserFactoryForTest appuserFactoryForTest = new AppuserFactoryForTest();
	private PhotoFactoryForTest photoFactoryForTest = new PhotoFactoryForTest();
	private TypeinfoFactoryForTest typeinfoFactoryForTest = new TypeinfoFactoryForTest();
	private PositionFactoryForTest positionFactoryForTest = new PositionFactoryForTest();

	List<Appuser> appusers = new ArrayList<Appuser>();
	List<Photo> photos = new ArrayList<Photo>();
	List<Typeinfo> typeinfos = new ArrayList<Typeinfo>();
	List<Position> positions = new ArrayList<Position>();

	private void givenPopulateModel() {
		Appuser appuser1 = appuserFactoryForTest.newAppuser();
		Appuser appuser2 = appuserFactoryForTest.newAppuser();
		List<Appuser> appusers = new ArrayList<Appuser>();
		appusers.add(appuser1);
		appusers.add(appuser2);
		when(appuserService.findAll()).thenReturn(appusers);

		Photo photo1 = photoFactoryForTest.newPhoto();
		Photo photo2 = photoFactoryForTest.newPhoto();
		List<Photo> photos = new ArrayList<Photo>();
		photos.add(photo1);
		photos.add(photo2);
		when(photoService.findAll()).thenReturn(photos);

		Typeinfo typeinfo1 = typeinfoFactoryForTest.newTypeinfo();
		Typeinfo typeinfo2 = typeinfoFactoryForTest.newTypeinfo();
		List<Typeinfo> typeinfos = new ArrayList<Typeinfo>();
		typeinfos.add(typeinfo1);
		typeinfos.add(typeinfo2);
		when(typeinfoService.findAll()).thenReturn(typeinfos);

		Position position1 = positionFactoryForTest.newPosition();
		Position position2 = positionFactoryForTest.newPosition();
		List<Position> positions = new ArrayList<Position>();
		positions.add(position1);
		positions.add(position2);
		when(positionService.findAll()).thenReturn(positions);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Feedback> list = new ArrayList<Feedback>();
		when(feedbackService.findAll()).thenReturn(list);
		
		// When
		String viewName = feedbackController.list(model);
		
		// Then
		assertEquals("feedback/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = feedbackController.formForCreate(model);
		
		// Then
		assertEquals("feedback/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Feedback)modelMap.get("feedback")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/feedback/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PhotoListItem> photoListItems = (List<PhotoListItem>) modelMap.get("listOfPhotoItems");
		assertEquals(2, photoListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TypeinfoListItem> typeinfoListItems = (List<TypeinfoListItem>) modelMap.get("listOfTypeinfoItems");
		assertEquals(2, typeinfoListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Feedback feedback = feedbackFactoryForTest.newFeedback();
		Long id = feedback.getId();
		when(feedbackService.findById(id)).thenReturn(feedback);
		
		// When
		String viewName = feedbackController.formForUpdate(model, id);
		
		// Then
		assertEquals("feedback/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedback, (Feedback) modelMap.get("feedback"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/feedback/update", modelMap.get("saveAction"));
		
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
		List<TypeinfoListItem> typeinfoListItems = (List<TypeinfoListItem>) modelMap.get("listOfTypeinfoItems");
		assertEquals(2, typeinfoListItems.size());
		
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Feedback feedback = feedbackFactoryForTest.newFeedback();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Feedback feedbackCreated = new Feedback();
		when(feedbackService.create(feedback)).thenReturn(feedbackCreated); 
		
		// When
		String viewName = feedbackController.create(feedback, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/feedback/form/"+feedback.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedbackCreated, (Feedback) modelMap.get("feedback"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Feedback feedback = feedbackFactoryForTest.newFeedback();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = feedbackController.create(feedback, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("feedback/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedback, (Feedback) modelMap.get("feedback"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/feedback/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PhotoListItem> photoListItems = (List<PhotoListItem>) modelMap.get("listOfPhotoItems");
		assertEquals(2, photoListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TypeinfoListItem> typeinfoListItems = (List<TypeinfoListItem>) modelMap.get("listOfTypeinfoItems");
		assertEquals(2, typeinfoListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Feedback feedback = feedbackFactoryForTest.newFeedback();
		
		Exception exception = new RuntimeException("test exception");
		when(feedbackService.create(feedback)).thenThrow(exception);
		
		// When
		String viewName = feedbackController.create(feedback, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("feedback/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedback, (Feedback) modelMap.get("feedback"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/feedback/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "feedback.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PhotoListItem> photoListItems = (List<PhotoListItem>) modelMap.get("listOfPhotoItems");
		assertEquals(2, photoListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TypeinfoListItem> typeinfoListItems = (List<TypeinfoListItem>) modelMap.get("listOfTypeinfoItems");
		assertEquals(2, typeinfoListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Feedback feedback = feedbackFactoryForTest.newFeedback();
		Long id = feedback.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Feedback feedbackSaved = new Feedback();
		feedbackSaved.setId(id);
		when(feedbackService.update(feedback)).thenReturn(feedbackSaved); 
		
		// When
		String viewName = feedbackController.update(feedback, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/feedback/form/"+feedback.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedbackSaved, (Feedback) modelMap.get("feedback"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Feedback feedback = feedbackFactoryForTest.newFeedback();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = feedbackController.update(feedback, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("feedback/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedback, (Feedback) modelMap.get("feedback"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/feedback/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TypeinfoListItem> typeinfoListItems = (List<TypeinfoListItem>) modelMap.get("listOfTypeinfoItems");
		assertEquals(2, typeinfoListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Feedback feedback = feedbackFactoryForTest.newFeedback();
		
		Exception exception = new RuntimeException("test exception");
		when(feedbackService.update(feedback)).thenThrow(exception);
		
		// When
		String viewName = feedbackController.update(feedback, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("feedback/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedback, (Feedback) modelMap.get("feedback"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/feedback/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "feedback.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<AppuserListItem> appuserListItems = (List<AppuserListItem>) modelMap.get("listOfAppuserItems");
		assertEquals(2, appuserListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TypeinfoListItem> typeinfoListItems = (List<TypeinfoListItem>) modelMap.get("listOfTypeinfoItems");
		assertEquals(2, typeinfoListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Feedback feedback = feedbackFactoryForTest.newFeedback();
		Long id = feedback.getId();
		
		// When
		String viewName = feedbackController.delete(redirectAttributes, id);
		
		// Then
		verify(feedbackService).delete(id);
		assertEquals("redirect:/feedback", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Feedback feedback = feedbackFactoryForTest.newFeedback();
		Long id = feedback.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(feedbackService).delete(id);
		
		// When
		String viewName = feedbackController.delete(redirectAttributes, id);
		
		// Then
		verify(feedbackService).delete(id);
		assertEquals("redirect:/feedback", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "feedback.error.delete", exception);
	}
	
	
}
