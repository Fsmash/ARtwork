__SDK =
{
	checkVersion : function checkVersionFn(sdkRequirements)
	{
		return __SDK.checkMinSDKVersion(sdkRequirements.minSDKVersion);
	},
    
    checkMinSDKVersion : function checkMinSDKVersionFn(minSDKVersionObject)
    {
		var result = AR.__architectBuildVersion__ >= 21 ? true : false; // AR.__architectBuildVersion__ 21 >= SDK 3.0

		return result;
    }
};
