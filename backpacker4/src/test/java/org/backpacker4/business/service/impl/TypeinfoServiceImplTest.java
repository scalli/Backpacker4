/*
 * Created on 28 okt 2015 ( Time 11:01:17 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.backpacker4.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.backpacker4.bean.Typeinfo;
import org.backpacker4.bean.jpa.TypeinfoEntity;
import java.util.List;
import org.backpacker4.business.service.mapping.TypeinfoServiceMapper;
import org.backpacker4.persistence.services.jpa.TypeinfoPersistenceJPA;
import org.backpacker4.test.TypeinfoFactoryForTest;
import org.backpacker4.test.TypeinfoEntityFactoryForTest;
import org.backpacker4.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of TypeinfoService
 */
@RunWith(MockitoJUnitRunner.class)
public class TypeinfoServiceImplTest {

	@InjectMocks
	private TypeinfoServiceImpl typeinfoService;
	@Mock
	private TypeinfoPersistenceJPA typeinfoPersistenceJPA;
	@Mock
	private TypeinfoServiceMapper typeinfoServiceMapper;
	
	private TypeinfoFactoryForTest typeinfoFactoryForTest = new TypeinfoFactoryForTest();

	private TypeinfoEntityFactoryForTest typeinfoEntityFactoryForTest = new TypeinfoEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Long id = mockValues.nextLong();
		
		TypeinfoEntity typeinfoEntity = typeinfoPersistenceJPA.load(id);
		
		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();
		when(typeinfoServiceMapper.mapTypeinfoEntityToTypeinfo(typeinfoEntity)).thenReturn(typeinfo);

		// When
		Typeinfo typeinfoFound = typeinfoService.findById(id);

		// Then
		assertEquals(typeinfo.getId(),typeinfoFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<TypeinfoEntity> typeinfoEntitys = new ArrayList<TypeinfoEntity>();
		TypeinfoEntity typeinfoEntity1 = typeinfoEntityFactoryForTest.newTypeinfoEntity();
		typeinfoEntitys.add(typeinfoEntity1);
		TypeinfoEntity typeinfoEntity2 = typeinfoEntityFactoryForTest.newTypeinfoEntity();
		typeinfoEntitys.add(typeinfoEntity2);
		when(typeinfoPersistenceJPA.loadAll()).thenReturn(typeinfoEntitys);
		
		Typeinfo typeinfo1 = typeinfoFactoryForTest.newTypeinfo();
		when(typeinfoServiceMapper.mapTypeinfoEntityToTypeinfo(typeinfoEntity1)).thenReturn(typeinfo1);
		Typeinfo typeinfo2 = typeinfoFactoryForTest.newTypeinfo();
		when(typeinfoServiceMapper.mapTypeinfoEntityToTypeinfo(typeinfoEntity2)).thenReturn(typeinfo2);

		// When
		List<Typeinfo> typeinfosFounds = typeinfoService.findAll();

		// Then
		assertTrue(typeinfo1 == typeinfosFounds.get(0));
		assertTrue(typeinfo2 == typeinfosFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();

		TypeinfoEntity typeinfoEntity = typeinfoEntityFactoryForTest.newTypeinfoEntity();
		when(typeinfoPersistenceJPA.load(typeinfo.getId())).thenReturn(null);
		
		typeinfoEntity = new TypeinfoEntity();
		typeinfoServiceMapper.mapTypeinfoToTypeinfoEntity(typeinfo, typeinfoEntity);
		TypeinfoEntity typeinfoEntitySaved = typeinfoPersistenceJPA.save(typeinfoEntity);
		
		Typeinfo typeinfoSaved = typeinfoFactoryForTest.newTypeinfo();
		when(typeinfoServiceMapper.mapTypeinfoEntityToTypeinfo(typeinfoEntitySaved)).thenReturn(typeinfoSaved);

		// When
		Typeinfo typeinfoResult = typeinfoService.create(typeinfo);

		// Then
		assertTrue(typeinfoResult == typeinfoSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();

		TypeinfoEntity typeinfoEntity = typeinfoEntityFactoryForTest.newTypeinfoEntity();
		when(typeinfoPersistenceJPA.load(typeinfo.getId())).thenReturn(typeinfoEntity);

		// When
		Exception exception = null;
		try {
			typeinfoService.create(typeinfo);
		} catch(Exception e) {
			exception = e;
		}

		// Then
		assertTrue(exception instanceof IllegalStateException);
		assertEquals("already.exists", exception.getMessage());
	}

	@Test
	public void update() {
		// Given
		Typeinfo typeinfo = typeinfoFactoryForTest.newTypeinfo();

		TypeinfoEntity typeinfoEntity = typeinfoEntityFactoryForTest.newTypeinfoEntity();
		when(typeinfoPersistenceJPA.load(typeinfo.getId())).thenReturn(typeinfoEntity);
		
		TypeinfoEntity typeinfoEntitySaved = typeinfoEntityFactoryForTest.newTypeinfoEntity();
		when(typeinfoPersistenceJPA.save(typeinfoEntity)).thenReturn(typeinfoEntitySaved);
		
		Typeinfo typeinfoSaved = typeinfoFactoryForTest.newTypeinfo();
		when(typeinfoServiceMapper.mapTypeinfoEntityToTypeinfo(typeinfoEntitySaved)).thenReturn(typeinfoSaved);

		// When
		Typeinfo typeinfoResult = typeinfoService.update(typeinfo);

		// Then
		verify(typeinfoServiceMapper).mapTypeinfoToTypeinfoEntity(typeinfo, typeinfoEntity);
		assertTrue(typeinfoResult == typeinfoSaved);
	}

	@Test
	public void delete() {
		// Given
		Long id = mockValues.nextLong();

		// When
		typeinfoService.delete(id);

		// Then
		verify(typeinfoPersistenceJPA).delete(id);
		
	}

}
