//////////////////////////////////////////////////////////////////////////////////////
//
//  Copyright 2012 Freshplanet (http://freshplanet.com | opensource@freshplanet.com)
//  
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//  
//    http://www.apache.org/licenses/LICENSE-2.0
//  
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//  
//////////////////////////////////////////////////////////////////////////////////////

package com.freshplanet.ane.AirFacebook;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.facebook.FacebookSdk;
import com.freshplanet.ane.AirFacebook.functions.*;

public class AirFacebookExtensionContext extends FREContext
{
	@Override
	public void dispose()
	{
		AirFacebookExtension.context = null;
	}

	@Override
	public Map<String, FREFunction> getFunctions()
	{
		Map<String, FREFunction> functions = new HashMap<String, FREFunction>();

		// Base API
		functions.put("init", new InitFunction());
		functions.put("getAccessToken", new GetAccessTokenFunction());
		functions.put("getProfile", new GetProfileFunction());
		functions.put("openSessionWithPermissions", new OpenSessionWithPermissionsFunction());
		functions.put("closeSessionAndClearTokenInformation", new CloseSessionAndClearTokenInformationFunction());
		functions.put("requestWithGraphPath", new RequestWithGraphPathFunction());

		// Sharing dialogs
		functions.put("canPresentShareDialog", new CanPresentShareDialogFunction());
		functions.put("shareLinkDialog", new ShareLinkDialogFunction());

		// Invite dialog
		functions.put("canPresentAppInviteDialog", new CanPresentAppInviteDialogFunction());
		functions.put("appInviteDialog", new AppInviteDialogFunction());

		functions.put("openDeferredAppLink", new OpenDeferredAppLinkFunction());

		// FB events
		functions.put("activateApp", new ActivateAppFunction());
		functions.put("deactivateApp", new DeactivateAppFunction());

		// Debug
		functions.put("log", new LogFunction());
		return functions;	
	}
	
	private String _appID;

	public String getAppID() {
		return _appID;
	}

	public void init(String appID)
	{
		if(appID != null) {

			_appID = appID;
			FacebookSdk.setApplicationId(_appID);
		}
		FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
	}

	public void launchRequestThread(String graphPath, Bundle parameters, String httpMethod, String callback)
	{
		new RequestThread(this, graphPath, parameters, httpMethod, callback).start();
	}
}
