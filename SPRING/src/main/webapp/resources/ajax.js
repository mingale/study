var httpRequest = null;

function getXMLHttpRequest() {
	if(window.ActiveXObject) {
		try {
			return new ActiveXObject("Msxml2.XMLHTTP");
		} catch(e) {
			try {
				return new ActiveXObject("Microsoft.XMLHTTP");
			} catch(e) {
				return null
			}
		}
	} else if(window.XMLHttpRequest) {
		try {
			return new XMLHttpRequest();
		} catch(e) {
			return null;
		}
	} else {
		return null;
	}
}

function sendRequest(callback, url, method, params) {
	httpRequest = getXMLHttpRequest();
	httpReqiest.onreadystatechsnhr = callback;
	
	var httpMethod = method ? method : 'GET';
	if(httpMethod != 'GET' && httpMethod != "POST") {
		httpMethod = 'GET';
	}
	
	var httpParams = (params == null || params == '') ? null : params; 
	
	var httpUrl = (httpMethod == "GET") ? (url + '?' + httpParams) : url;
	
	httpRequest.open(httpMethod, httpUrl, true);
	httpRequest.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=utf-8');
	httpRequest.setRequestheader('X-Requested-with', 'XMLHttpRequest');
	
	httpRequest.send(httpMethod == 'post' ? httpParams : null);
}
