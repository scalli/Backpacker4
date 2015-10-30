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
import org.backpacker4.bean.Photo;
import org.backpacker4.bean.Position;
import org.backpacker4.test.PhotoFactoryForTest;
import org.backpacker4.test.PositionFactoryForTest;

//--- Services 
import org.backpacker4.business.service.PhotoService;
import org.backpacker4.business.service.PositionService;

//--- List Items 
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
public class PhotoControllerTest {
	
	@InjectMocks
	private PhotoController photoController;
	@Mock
	private PhotoService photoService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private PositionService positionService; // Injected by Spring

	private PhotoFactoryForTest photoFactoryForTest = new PhotoFactoryForTest();
	private PositionFactoryForTest positionFactoryForTest = new PositionFactoryForTest();

	List<Position> positions = new ArrayList<Position>();

	private void givenPopulateModel() {
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
		
		List<Photo> list = new ArrayList<Photo>();
		when(photoService.findAll()).thenReturn(list);
		
		// When
		String viewName = photoController.list(model);
		
		// Then
		assertEquals("photo/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = photoController.formForCreate(model);
		
		// Then
		assertEquals("photo/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Photo)modelMap.get("photo")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/photo/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Photo photo = photoFactoryForTest.newPhoto();
		Long id = photo.getId();
		when(photoService.findById(id)).thenReturn(photo);
		
		// When
		String viewName = photoController.formForUpdate(model, id);
		
		// Then
		assertEquals("photo/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(photo, (Photo) modelMap.get("photo"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/photo/update", modelMap.get("saveAction"));
		
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Photo photo = photoFactoryForTest.newPhoto();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Photo photoCreated = new Photo();
		when(photoService.create(photo)).thenReturn(photoCreated); 
		
		// When
		String viewName = photoController.create(photo, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/photo/form/"+photo.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(photoCreated, (Photo) modelMap.get("photo"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Photo photo = photoFactoryForTest.newPhoto();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = photoController.create(photo, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("photo/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(photo, (Photo) modelMap.get("photo"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/photo/create", modelMap.get("saveAction"));
		
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

		Photo photo = photoFactoryForTest.newPhoto();
		
		Exception exception = new RuntimeException("test exception");
		when(photoService.create(photo)).thenThrow(exception);
		
		// When
		String viewName = photoController.create(photo, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("photo/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(photo, (Photo) modelMap.get("photo"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/photo/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "photo.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Photo photo = photoFactoryForTest.newPhoto();
		Long id = photo.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Photo photoSaved = new Photo();
		photoSaved.setId(id);
		when(photoService.update(photo)).thenReturn(photoSaved); 
		
		// When
		String viewName = photoController.update(photo, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/photo/form/"+photo.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(photoSaved, (Photo) modelMap.get("photo"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Photo photo = photoFactoryForTest.newPhoto();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = photoController.update(photo, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("photo/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(photo, (Photo) modelMap.get("photo"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/photo/update", modelMap.get("saveAction"));
		
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

		Photo photo = photoFactoryForTest.newPhoto();
		
		Exception exception = new RuntimeException("test exception");
		when(photoService.update(photo)).thenThrow(exception);
		
		// When
		String viewName = photoController.update(photo, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("photo/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(photo, (Photo) modelMap.get("photo"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/photo/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "photo.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Photo photo = photoFactoryForTest.newPhoto();
		Long id = photo.getId();
		
		// When
		String viewName = photoController.delete(redirectAttributes, id);
		
		// Then
		verify(photoService).delete(id);
		assertEquals("redirect:/photo", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Photo photo = photoFactoryForTest.newPhoto();
		Long id = photo.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(photoService).delete(id);
		
		// When
		String viewName = photoController.delete(redirectAttributes, id);
		
		// Then
		verify(photoService).delete(id);
		assertEquals("redirect:/photo", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "photo.error.delete", exception);
	}
	
	
}
