
package org.backpacker4.web.listitem;

import org.backpacker4.bean.AlbumPhoto;
import org.backpacker4.web.common.ListItem;

public class AlbumPhotoListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public AlbumPhotoListItem(AlbumPhoto albumPhoto) {
		super();

		this.value = ""
			 + albumPhoto.getIdAlbum()
			 + "|"  + albumPhoto.getIdPhoto()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = albumPhoto.toString();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return label;
	}

}
