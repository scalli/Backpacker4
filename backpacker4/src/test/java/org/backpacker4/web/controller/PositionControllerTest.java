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
import org.backpacker4.bean.Position;
import org.backpacker4.test.PositionFactoryForTest;

//--- Services 
import org.backpacker4.business.service.PositionService;


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
public class PositionControllerTest {
	
	@InjectMocks
	private PositionController positionController;
	@Mock
	private PositionService positionService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private PositionFactoryForTest positionFactoryForTest = new PositionFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Position> list = new ArrayList<Position>();
		when(positionService.findAll()).thenReturn(list);
		
		// When
		String viewName = positionController.list(model);
		
		// Then
		assertEquals("position/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = positionController.formForCreate(model);
		
		// Then
		assertEquals("position/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Position)modelMap.get("position")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/position/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Position position = positionFactoryForTest.newPosition();
		Long id = position.getId();
		when(positionService.findById(id)).thenReturn(position);
		
		// When
		String viewName = positionController.formForUpdate(model, id);
		
		// Then
		assertEquals("position/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(position, (Position) modelMap.get("position"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/position/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Position position = positionFactoryForTest.newPosition();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Position positionCreated = new Position();
		when(positionService.create(position)).thenReturn(positionCreated); 
		
		// When
		String viewName = positionController.create(position, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/position/form/"+position.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(positionCreated, (Position) modelMap.get("position"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Position position = positionFactoryForTest.newPosition();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = positionController.create(position, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("position/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(position, (Position) modelMap.get("position"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/position/create", modelMap.get("saveAction"));
		
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

		Position position = positionFactoryForTest.newPosition();
		
		Exception exception = new RuntimeException("test exception");
		when(positionService.create(position)).thenThrow(exception);
		
		// When
		String viewName = positionController.create(position, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("position/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(position, (Position) modelMap.get("position"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/position/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "position.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Position position = positionFactoryForTest.newPosition();
		Long id = position.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Position positionSaved = new Position();
		positionSaved.setId(id);
		when(positionService.update(position)).thenReturn(positionSaved); 
		
		// When
		String viewName = positionController.update(position, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/position/form/"+position.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(positionSaved, (Position) modelMap.get("position"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Position position = positionFactoryForTest.newPosition();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = positionController.update(position, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("position/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(position, (Position) modelMap.get("position"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/position/update", modelMap.get("saveAction"));
		
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

		Position position = positionFactoryForTest.newPosition();
		
		Exception exception = new RuntimeException("test exception");
		when(positionService.update(position)).thenThrow(exception);
		
		// When
		String viewName = positionController.update(position, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("position/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(position, (Position) modelMap.get("position"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/position/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "position.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Position position = positionFactoryForTest.newPosition();
		Long id = position.getId();
		
		// When
		String viewName = positionController.delete(redirectAttributes, id);
		
		// Then
		verify(positionService).delete(id);
		assertEquals("redirect:/position", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Position position = positionFactoryForTest.newPosition();
		Long id = position.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(positionService).delete(id);
		
		// When
		String viewName = positionController.delete(redirectAttributes, id);
		
		// Then
		verify(positionService).delete(id);
		assertEquals("redirect:/position", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "position.error.delete", exception);
	}
	
	
}
