	function PictureSourceType() {};
	PictureSourceType.PHOTO_LIBRARY = 0;
	PictureSourceType.CAMERA = 1;
	 
	function getPicture(sourceType)
	{
		 var options = { quality: 10 };
		 if (sourceType != undefined) {
			  options["sourceType"] = sourceType;
		 }
		 // if no sourceType specified, the default is CAMERA 
		 navigator.camera.getPictureFromLibrary(getPicture_Success, null, options);
	};
	 
	function getPicture_Success(imageData)
	{
			// alert("getpic success");
			$("#img_src").attr("src","data:image/jpeg;base64," + imageData);
	}
    
/**
 * 
 * @param {Function} successCallback
 * @param {Function} errorCallback
 * @param {Object} options
 */
Camera.prototype.getPictureFromLibrary = function(successCallback, errorCallback, options) {
	PhoneGap.exec("Camera.getPictureFromLibrary", GetFunctionName(successCallback), GetFunctionName(errorCallback), options);
}

PhoneGap.addConstructor(function() {
    if (typeof navigator.camera == "undefined") navigator.camera = new Camera();
});