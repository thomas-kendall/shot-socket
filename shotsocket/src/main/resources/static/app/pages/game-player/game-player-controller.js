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
		$scope.deviceOrientation.alpha = eventData.alpha;    // the compass direction the device is facing (degrees)
		$scope.deviceOrientation.beta = eventData.beta;  // tilt front-to-back (degrees)
		$scope.deviceOrientation.gamma = eventData.gamma;  // tilt left-to-right (degrees)
		$scope.$apply();
		
		var message = {
			action: 'device-orientation',
			alpha: $scope.deviceOrientation.alpha,
			beta: $scope.deviceOrientation.beta,
			gamma: $scope.deviceOrientation.gamma
		};
		socket.send(JSON.stringify(message));		
	};
		
	initialize();
};

var app = angular.module('spa');
app.controller('GamePlayerCtrl', ctrl);
