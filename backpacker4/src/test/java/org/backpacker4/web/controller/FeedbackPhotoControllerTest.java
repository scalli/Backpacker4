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
import org.backpacker4.bean.FeedbackPhoto;
import org.backpacker4.test.FeedbackPhotoFactoryForTest;

//--- Services 
import org.backpacker4.business.service.FeedbackPhotoService;


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
public class FeedbackPhotoControllerTest {
	
	@InjectMocks
	private FeedbackPhotoController feedbackPhotoController;
	@Mock
	private FeedbackPhotoService feedbackPhotoService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private FeedbackPhotoFactoryForTest feedbackPhotoFactoryForTest = new FeedbackPhotoFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<FeedbackPhoto> list = new ArrayList<FeedbackPhoto>();
		when(feedbackPhotoService.findAll()).thenReturn(list);
		
		// When
		String viewName = feedbackPhotoController.list(model);
		
		// Then
		assertEquals("feedbackPhoto/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = feedbackPhotoController.formForCreate(model);
		
		// Then
		assertEquals("feedbackPhoto/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((FeedbackPhoto)modelMap.get("feedbackPhoto")).getIdFeedback());
		assertNull(((FeedbackPhoto)modelMap.get("feedbackPhoto")).getIdPhoto());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/feedbackPhoto/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		FeedbackPhoto feedbackPhoto = feedbackPhotoFactoryForTest.newFeedbackPhoto();
		Long idFeedback = feedbackPhoto.getIdFeedback();
		Long idPhoto = feedbackPhoto.getIdPhoto();
		when(feedbackPhotoService.findById(idFeedback, idPhoto)).thenReturn(feedbackPhoto);
		
		// When
		String viewName = feedbackPhotoController.formForUpdate(model, idFeedback, idPhoto);
		
		// Then
		assertEquals("feedbackPhoto/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedbackPhoto, (FeedbackPhoto) modelMap.get("feedbackPhoto"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/feedbackPhoto/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		FeedbackPhoto feedbackPhoto = feedbackPhotoFactoryForTest.newFeedbackPhoto();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		FeedbackPhoto feedbackPhotoCreated = new FeedbackPhoto();
		when(feedbackPhotoService.create(feedbackPhoto)).thenReturn(feedbackPhotoCreated); 
		
		// When
		String viewName = feedbackPhotoController.create(feedbackPhoto, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/feedbackPhoto/form/"+feedbackPhoto.getIdFeedback()+"/"+feedbackPhoto.getIdPhoto(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedbackPhotoCreated, (FeedbackPhoto) modelMap.get("feedbackPhoto"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		FeedbackPhoto feedbackPhoto = feedbackPhotoFactoryForTest.newFeedbackPhoto();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = feedbackPhotoController.create(feedbackPhoto, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("feedbackPhoto/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedbackPhoto, (FeedbackPhoto) modelMap.get("feedbackPhoto"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/feedbackPhoto/create", modelMap.get("saveAction"));
		
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

		FeedbackPhoto feedbackPhoto = feedbackPhotoFactoryForTest.newFeedbackPhoto();
		
		Exception exception = new RuntimeException("test exception");
		when(feedbackPhotoService.create(feedbackPhoto)).thenThrow(exception);
		
		// When
		String viewName = feedbackPhotoController.create(feedbackPhoto, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("feedbackPhoto/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedbackPhoto, (FeedbackPhoto) modelMap.get("feedbackPhoto"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/feedbackPhoto/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "feedbackPhoto.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		FeedbackPhoto feedbackPhoto = feedbackPhotoFactoryForTest.newFeedbackPhoto();
		Long idFeedback = feedbackPhoto.getIdFeedback();
		Long idPhoto = feedbackPhoto.getIdPhoto();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		FeedbackPhoto feedbackPhotoSaved = new FeedbackPhoto();
		feedbackPhotoSaved.setIdFeedback(idFeedback);
		feedbackPhotoSaved.setIdPhoto(idPhoto);
		when(feedbackPhotoService.update(feedbackPhoto)).thenReturn(feedbackPhotoSaved); 
		
		// When
		String viewName = feedbackPhotoController.update(feedbackPhoto, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/feedbackPhoto/form/"+feedbackPhoto.getIdFeedback()+"/"+feedbackPhoto.getIdPhoto(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedbackPhotoSaved, (FeedbackPhoto) modelMap.get("feedbackPhoto"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		FeedbackPhoto feedbackPhoto = feedbackPhotoFactoryForTest.newFeedbackPhoto();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = feedbackPhotoController.update(feedbackPhoto, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("feedbackPhoto/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedbackPhoto, (FeedbackPhoto) modelMap.get("feedbackPhoto"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/feedbackPhoto/update", modelMap.get("saveAction"));
		
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

		FeedbackPhoto feedbackPhoto = feedbackPhotoFactoryForTest.newFeedbackPhoto();
		
		Exception exception = new RuntimeException("test exception");
		when(feedbackPhotoService.update(feedbackPhoto)).thenThrow(exception);
		
		// When
		String viewName = feedbackPhotoController.update(feedbackPhoto, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("feedbackPhoto/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(feedbackPhoto, (FeedbackPhoto) modelMap.get("feedbackPhoto"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/feedbackPhoto/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "feedbackPhoto.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		FeedbackPhoto feedbackPhoto = feedbackPhotoFactoryForTest.newFeedbackPhoto();
		Long idFeedback = feedbackPhoto.getIdFeedback();
		Long idPhoto = feedbackPhoto.getIdPhoto();
		
		// When
		String viewName = feedbackPhotoController.delete(redirectAttributes, idFeedback, idPhoto);
		
		// Then
		verify(feedbackPhotoService).delete(idFeedback, idPhoto);
		assertEquals("redirect:/feedbackPhoto", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		FeedbackPhoto feedbackPhoto = feedbackPhotoFactoryForTest.newFeedbackPhoto();
		Long idFeedback = feedbackPhoto.getIdFeedback();
		Long idPhoto = feedbackPhoto.getIdPhoto();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(feedbackPhotoService).delete(idFeedback, idPhoto);
		
		// When
		String viewName = feedbackPhotoController.delete(redirectAttributes, idFeedback, idPhoto);
		
		// Then
		verify(feedbackPhotoService).delete(idFeedback, idPhoto);
		assertEquals("redirect:/feedbackPhoto", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "feedbackPhoto.error.delete", exception);
	}
	
	
}
