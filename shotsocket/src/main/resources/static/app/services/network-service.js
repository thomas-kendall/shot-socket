var service = function($http) {
	var serverHost = '';
	var httpProtocol = '';
	var wsProtocol = '';
	
	var initialize = function() {		
		var loc = window.location;
		serverHost = loc.host;
		httpProtocol = loc.protocol;
		wsProtocol = httpProtocol === 'https:' ? 'wss:' : 'ws:';
		
		if(serverHost.indexOf('localhost') > -1) {
			getServerIpAddress(function(ipAddress){
				serverHost = serverHost.replace('localhost', ipAddress);
			});
		}
	};
	
	var getServerIpAddress = function(onSuccess) {
		$http.get('/ipinfo')
			.then(function(response){
				onSuccess(response.data.ipAddress);
			}
		 );
	};
	
	var getWebsocketUrl = function(path) {
		return wsProtocol + '//' + serverHost + path;
	};
	
	var getFullUrl = function(path) {
		return httpProtocol + "//" + serverHost + path;
	}
	
	initialize();

	return {
		getWebsocketUrl: getWebsocketUrl,
		getFullUrl: getFullUrl
	};
};

var app = angular.module('spa');
app.service('networkService', service);
