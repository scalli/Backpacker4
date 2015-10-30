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
import org.backpacker4.bean.AlbumPhoto;
import org.backpacker4.test.AlbumPhotoFactoryForTest;

//--- Services 
import org.backpacker4.business.service.AlbumPhotoService;


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
public class AlbumPhotoControllerTest {
	
	@InjectMocks
	private AlbumPhotoController albumPhotoController;
	@Mock
	private AlbumPhotoService albumPhotoService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private AlbumPhotoFactoryForTest albumPhotoFactoryForTest = new AlbumPhotoFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<AlbumPhoto> list = new ArrayList<AlbumPhoto>();
		when(albumPhotoService.findAll()).thenReturn(list);
		
		// When
		String viewName = albumPhotoController.list(model);
		
		// Then
		assertEquals("albumPhoto/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = albumPhotoController.formForCreate(model);
		
		// Then
		assertEquals("albumPhoto/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((AlbumPhoto)modelMap.get("albumPhoto")).getIdAlbum());
		assertNull(((AlbumPhoto)modelMap.get("albumPhoto")).getIdPhoto());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/albumPhoto/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		AlbumPhoto albumPhoto = albumPhotoFactoryForTest.newAlbumPhoto();
		Long idAlbum = albumPhoto.getIdAlbum();
		Long idPhoto = albumPhoto.getIdPhoto();
		when(albumPhotoService.findById(idAlbum, idPhoto)).thenReturn(albumPhoto);
		
		// When
		String viewName = albumPhotoController.formForUpdate(model, idAlbum, idPhoto);
		
		// Then
		assertEquals("albumPhoto/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(albumPhoto, (AlbumPhoto) modelMap.get("albumPhoto"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/albumPhoto/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		AlbumPhoto albumPhoto = albumPhotoFactoryForTest.newAlbumPhoto();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		AlbumPhoto albumPhotoCreated = new AlbumPhoto();
		when(albumPhotoService.create(albumPhoto)).thenReturn(albumPhotoCreated); 
		
		// When
		String viewName = albumPhotoController.create(albumPhoto, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/albumPhoto/form/"+albumPhoto.getIdAlbum()+"/"+albumPhoto.getIdPhoto(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(albumPhotoCreated, (AlbumPhoto) modelMap.get("albumPhoto"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		AlbumPhoto albumPhoto = albumPhotoFactoryForTest.newAlbumPhoto();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = albumPhotoController.create(albumPhoto, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("albumPhoto/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(albumPhoto, (AlbumPhoto) modelMap.get("albumPhoto"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/albumPhoto/create", modelMap.get("saveAction"));
		
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

		AlbumPhoto albumPhoto = albumPhotoFactoryForTest.newAlbumPhoto();
		
		Exception exception = new RuntimeException("test exception");
		when(albumPhotoService.create(albumPhoto)).thenThrow(exception);
		
		// When
		String viewName = albumPhotoController.create(albumPhoto, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("albumPhoto/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(albumPhoto, (AlbumPhoto) modelMap.get("albumPhoto"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/albumPhoto/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "albumPhoto.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		AlbumPhoto albumPhoto = albumPhotoFactoryForTest.newAlbumPhoto();
		Long idAlbum = albumPhoto.getIdAlbum();
		Long idPhoto = albumPhoto.getIdPhoto();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		AlbumPhoto albumPhotoSaved = new AlbumPhoto();
		albumPhotoSaved.setIdAlbum(idAlbum);
		albumPhotoSaved.setIdPhoto(idPhoto);
		when(albumPhotoService.update(albumPhoto)).thenReturn(albumPhotoSaved); 
		
		// When
		String viewName = albumPhotoController.update(albumPhoto, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/albumPhoto/form/"+albumPhoto.getIdAlbum()+"/"+albumPhoto.getIdPhoto(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(albumPhotoSaved, (AlbumPhoto) modelMap.get("albumPhoto"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		AlbumPhoto albumPhoto = albumPhotoFactoryForTest.newAlbumPhoto();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = albumPhotoController.update(albumPhoto, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("albumPhoto/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(albumPhoto, (AlbumPhoto) modelMap.get("albumPhoto"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/albumPhoto/update", modelMap.get("saveAction"));
		
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

		AlbumPhoto albumPhoto = albumPhotoFactoryForTest.newAlbumPhoto();
		
		Exception exception = new RuntimeException("test exception");
		when(albumPhotoService.update(albumPhoto)).thenThrow(exception);
		
		// When
		String viewName = albumPhotoController.update(albumPhoto, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("albumPhoto/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(albumPhoto, (AlbumPhoto) modelMap.get("albumPhoto"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/albumPhoto/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "albumPhoto.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		AlbumPhoto albumPhoto = albumPhotoFactoryForTest.newAlbumPhoto();
		Long idAlbum = albumPhoto.getIdAlbum();
		Long idPhoto = albumPhoto.getIdPhoto();
		
		// When
		String viewName = albumPhotoController.delete(redirectAttributes, idAlbum, idPhoto);
		
		// Then
		verify(albumPhotoService).delete(idAlbum, idPhoto);
		assertEquals("redirect:/albumPhoto", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		AlbumPhoto albumPhoto = albumPhotoFactoryForTest.newAlbumPhoto();
		Long idAlbum = albumPhoto.getIdAlbum();
		Long idPhoto = albumPhoto.getIdPhoto();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(albumPhotoService).delete(idAlbum, idPhoto);
		
		// When
		String viewName = albumPhotoController.delete(redirectAttributes, idAlbum, idPhoto);
		
		// Then
		verify(albumPhotoService).delete(idAlbum, idPhoto);
		assertEquals("redirect:/albumPhoto", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "albumPhoto.error.delete", exception);
	}
	
	
}
