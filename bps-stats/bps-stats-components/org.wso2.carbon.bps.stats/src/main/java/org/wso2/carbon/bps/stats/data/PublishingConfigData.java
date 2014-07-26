package org.wso2.carbon.bps.stats.data;

public class PublishingConfigData {

	public boolean publishingEnable;
	public String bamUrl;
	public String bamUserName;
	public String bamPassword;

	public boolean isPublishingEnable() {
		return publishingEnable;
	}

	public void setPublishingEnable(boolean publishingEnable) {
		this.publishingEnable = publishingEnable;
	}

	public String getBamUrl() {
		return bamUrl;
	}

	public void setBamUrl(String bamUrl) {
		this.bamUrl = bamUrl;
	}

	public String getBamUserName() {
		return bamUserName;
	}

	public void setBamUserName(String bamUserName) {
		this.bamUserName = bamUserName;
	}

	public String getBamPassword() {
		return bamPassword;
	}

	public void setBamPassword(String bamPassword) {
		this.bamPassword = bamPassword;
	}

}
