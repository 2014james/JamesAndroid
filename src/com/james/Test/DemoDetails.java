package com.james.Test;

/***
 * @author james
 * @Package com.james.Test
 * @Title: DemoDetails.java
 * @Description
 * @date 2015-8-31
 * @version 1.0
 * 
 */
public class DemoDetails {
	public String activityName;
	public int descriptionId;
	public Class<? extends android.app.Activity> activityClass;

	/**
	 * 
	 * @param titleId
	 *            ����
	 * @param descriptionId
	 *            ��������
	 * @param activityClass
	 *            Activity����
	 */
	public DemoDetails(String activityName, int descriptionId,
			Class<? extends android.app.Activity> activityClass) {
		super();
		this.activityName = activityName;
		this.descriptionId = descriptionId;
		this.activityClass = activityClass;
	}
}
