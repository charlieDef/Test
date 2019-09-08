package com.materials.client.widgets.menu;

public class MenuLink {

	private String name;
	private String target;
	
	public MenuLink() {
	}
	
	public MenuLink(String name, String target) {
		this.name=name;
		this.target = target;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTarget() {
		return target;
	}
}
