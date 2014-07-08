package org.wso2.carbon.bps.stats.conf;

public class PublishingConfigData {

	private boolean isPublishingEnable;
	private String url;
	private String userName;
	private String password;
	
	public boolean isPublishingEnable() {
		return isPublishingEnable;
	}
	public void setPublishingEnable(boolean isPublishingEnable) {
		this.isPublishingEnable = isPublishingEnable;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	

}
