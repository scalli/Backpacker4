
package org.backpacker4.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.backpacker4.bean.Typeinfo;
import org.backpacker4.bean.jpa.TypeinfoEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class TypeinfoServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public TypeinfoServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'TypeinfoEntity' to 'Typeinfo'
	 * @param typeinfoEntity
	 */
	public Typeinfo mapTypeinfoEntityToTypeinfo(TypeinfoEntity typeinfoEntity) {
		if(typeinfoEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Typeinfo typeinfo = map(typeinfoEntity, Typeinfo.class);

		return typeinfo;
	}
	
	/**
	 * Mapping from 'Typeinfo' to 'TypeinfoEntity'
	 * @param typeinfo
	 * @param typeinfoEntity
	 */
	public void mapTypeinfoToTypeinfoEntity(Typeinfo typeinfo, TypeinfoEntity typeinfoEntity) {
		if(typeinfo == null) {
			return;
		}

		//--- Generic mapping 
		map(typeinfo, typeinfoEntity);

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}