/*
 * Created on 28 okt 2015 ( Time 11:00:53 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.backpacker4.web.listitem;

import org.backpacker4.bean.Album;
import org.backpacker4.web.common.ListItem;

public class AlbumListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public AlbumListItem(Album album) {
		super();

		this.value = ""
			 + album.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = album.toString();
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
