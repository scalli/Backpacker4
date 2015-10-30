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
import org.backpacker4.bean.Appuser;
import org.backpacker4.bean.Position;
import org.backpacker4.bean.Photo;
import org.backpacker4.test.AppuserFactoryForTest;
import org.backpacker4.test.PositionFactoryForTest;
import org.backpacker4.test.PhotoFactoryForTest;

//--- Services 
import org.backpacker4.business.service.AppuserService;
import org.backpacker4.business.service.PositionService;
import org.backpacker4.business.service.PhotoService;

//--- List Items 
import org.backpacker4.web.listitem.PositionListItem;
import org.backpacker4.web.listitem.PhotoListItem;

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
public class AppuserControllerTest {
	
	@InjectMocks
	private AppuserController appuserController;
	@Mock
	private AppuserService appuserService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private PositionService positionService; // Injected by Spring
	@Mock
	private PhotoService photoService; // Injected by Spring

	private AppuserFactoryForTest appuserFactoryForTest = new AppuserFactoryForTest();
	private PositionFactoryForTest positionFactoryForTest = new PositionFactoryForTest();
	private PhotoFactoryForTest photoFactoryForTest = new PhotoFactoryForTest();

	List<Position> positions = new ArrayList<Position>();
	List<Photo> photos = new ArrayList<Photo>();

	private void givenPopulateModel() {
		Position position1 = positionFactoryForTest.newPosition();
		Position position2 = positionFactoryForTest.newPosition();
		List<Position> positions = new ArrayList<Position>();
		positions.add(position1);
		positions.add(position2);
		when(positionService.findAll()).thenReturn(positions);

		Photo photo1 = photoFactoryForTest.newPhoto();
		Photo photo2 = photoFactoryForTest.newPhoto();
		List<Photo> photos = new ArrayList<Photo>();
		photos.add(photo1);
		photos.add(photo2);
		when(photoService.findAll()).thenReturn(photos);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Appuser> list = new ArrayList<Appuser>();
		when(appuserService.findAll()).thenReturn(list);
		
		// When
		String viewName = appuserController.list(model);
		
		// Then
		assertEquals("appuser/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = appuserController.formForCreate(model);
		
		// Then
		assertEquals("appuser/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Appuser)modelMap.get("appuser")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/appuser/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PhotoListItem> photoListItems = (List<PhotoListItem>) modelMap.get("listOfPhotoItems");
		assertEquals(2, photoListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Appuser appuser = appuserFactoryForTest.newAppuser();
		Long id = appuser.getId();
		when(appuserService.findById(id)).thenReturn(appuser);
		
		// When
		String viewName = appuserController.formForUpdate(model, id);
		
		// Then
		assertEquals("appuser/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(appuser, (Appuser) modelMap.get("appuser"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/appuser/update", modelMap.get("saveAction"));
		
		List<PhotoListItem> photoListItems = (List<PhotoListItem>) modelMap.get("listOfPhotoItems");
		assertEquals(2, photoListItems.size());
		
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Appuser appuser = appuserFactoryForTest.newAppuser();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Appuser appuserCreated = new Appuser();
		when(appuserService.create(appuser)).thenReturn(appuserCreated); 
		
		// When
		String viewName = appuserController.create(appuser, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/appuser/form/"+appuser.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(appuserCreated, (Appuser) modelMap.get("appuser"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Appuser appuser = appuserFactoryForTest.newAppuser();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = appuserController.create(appuser, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("appuser/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(appuser, (Appuser) modelMap.get("appuser"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/appuser/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PhotoListItem> photoListItems = (List<PhotoListItem>) modelMap.get("listOfPhotoItems");
		assertEquals(2, photoListItems.size());
		
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

		Appuser appuser = appuserFactoryForTest.newAppuser();
		
		Exception exception = new RuntimeException("test exception");
		when(appuserService.create(appuser)).thenThrow(exception);
		
		// When
		String viewName = appuserController.create(appuser, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("appuser/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(appuser, (Appuser) modelMap.get("appuser"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/appuser/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "appuser.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PhotoListItem> photoListItems = (List<PhotoListItem>) modelMap.get("listOfPhotoItems");
		assertEquals(2, photoListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Appuser appuser = appuserFactoryForTest.newAppuser();
		Long id = appuser.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Appuser appuserSaved = new Appuser();
		appuserSaved.setId(id);
		when(appuserService.update(appuser)).thenReturn(appuserSaved); 
		
		// When
		String viewName = appuserController.update(appuser, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/appuser/form/"+appuser.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(appuserSaved, (Appuser) modelMap.get("appuser"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Appuser appuser = appuserFactoryForTest.newAppuser();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = appuserController.update(appuser, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("appuser/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(appuser, (Appuser) modelMap.get("appuser"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/appuser/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PhotoListItem> photoListItems = (List<PhotoListItem>) modelMap.get("listOfPhotoItems");
		assertEquals(2, photoListItems.size());
		
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

		Appuser appuser = appuserFactoryForTest.newAppuser();
		
		Exception exception = new RuntimeException("test exception");
		when(appuserService.update(appuser)).thenThrow(exception);
		
		// When
		String viewName = appuserController.update(appuser, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("appuser/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(appuser, (Appuser) modelMap.get("appuser"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/appuser/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "appuser.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<PhotoListItem> photoListItems = (List<PhotoListItem>) modelMap.get("listOfPhotoItems");
		assertEquals(2, photoListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PositionListItem> positionListItems = (List<PositionListItem>) modelMap.get("listOfPositionItems");
		assertEquals(2, positionListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Appuser appuser = appuserFactoryForTest.newAppuser();
		Long id = appuser.getId();
		
		// When
		String viewName = appuserController.delete(redirectAttributes, id);
		
		// Then
		verify(appuserService).delete(id);
		assertEquals("redirect:/appuser", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Appuser appuser = appuserFactoryForTest.newAppuser();
		Long id = appuser.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(appuserService).delete(id);
		
		// When
		String viewName = appuserController.delete(redirectAttributes, id);
		
		// Then
		verify(appuserService).delete(id);
		assertEquals("redirect:/appuser", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "appuser.error.delete", exception);
	}
	
	
}
