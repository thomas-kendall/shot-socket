var ctrl = function($scope, $location, $routeParams, networkService) {
	var socket = null;
	$scope.gameId = $routeParams.gameId;
	$scope.deviceOrientation = {};
	
	if (!window.DeviceOrientationEvent) {
		$location.url('/orientation-not-supported');
		return;
	}		
	
	var initialize = function() {
		var socketUrl = networkService.getWebsocketUrl('/game-controller');
		socket = new WebSocket(socketUrl);		
		socket.onopen = function() {
			// Join the game
			var message = {
					action: 'join-game',
					gameId: $scope.gameId
				};
			socket.send(JSON.stringify(message));			

			// Listen for orientation events
			window.addEventListener('deviceorientation', onDeviceOrientation, false);					
		};
	};
	
	var onDeviceOrientation = function(eventData) {
		$scope.deviceOrientation.tiltLR = eventData.gamma; // tilt left-to-right (degrees)
		$scope.deviceOrientation.tiltFB = eventData.beta;  // tilt front-to-back (degrees)
		$scope.deviceOrientation.dir = eventData.alpha;    // the compass direction the device is facing (degrees)
		$scope.$apply();
		
		var message = {
			action: 'device-orientation',
			tiltLR: $scope.deviceOrientation.tiltLR,
			tiltFB: $scope.deviceOrientation.tiltFB,
			dir: $scope.deviceOrientation.dir
		};
		socket.send(JSON.stringify(message));		
	};
		
	initialize();
};

var app = angular.module('spa');
app.controller('GamePlayerCtrl', ctrl);
