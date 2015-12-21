
package org.backpacker4.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.backpacker4.bean.Position;
import org.backpacker4.bean.jpa.PositionEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class PositionServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public PositionServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'PositionEntity' to 'Position'
	 * @param positionEntity
	 */
	public Position mapPositionEntityToPosition(PositionEntity positionEntity) {
		if(positionEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Position position = map(positionEntity, Position.class);

		return position;
	}
	
	/**
	 * Mapping from 'Position' to 'PositionEntity'
	 * @param position
	 * @param positionEntity
	 */
	public void mapPositionToPositionEntity(Position position, PositionEntity positionEntity) {
		if(position == null) {
			return;
		}

		//--- Generic mapping 
		map(position, positionEntity);

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