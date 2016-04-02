var ctrl = function($scope, $location, $routeParams, networkService) {
	var socket = null;
	$scope.gameId = $routeParams.gameId;
	$scope.deviceOrientation = {};
	$scope.referenceOrientation = null;
	
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
	
	var getOffsetAlpha = function(referenceAlpha, alpha) {
		var offsetAngle = alpha - referenceAlpha;
		if(offsetAngle < 0.0) {
			offsetAngle += 360.0;
		} else if(offsetAngle > 360.0) {
			offsetAngle -= 360.0;
		}
		return offsetAngle;
	};
	
	var getOffsetBeta = function(referenceBeta, beta) {
		var offsetAngle = beta - referenceBeta;
		if(offsetAngle < -180.0) {
			offsetAngle += 360.0;
		} else if(offsetAngle > 180.0) {
			offsetAngle -= 360.0;
		}
		return offsetAngle;
	};
	
	var getOffsetGamma = function(referenceGamma, gamma) {
		var offsetAngle = gamma - referenceGamma;
		if(offsetAngle < -90.0) {
			offsetAngle += 180.0;
		} else if(offsetAngle > 90.0) {
			offsetAngle -= 180.0;
		}
		return offsetAngle;
	};
	
	var onDeviceOrientation = function(eventData) {
		if(!$scope.referenceOrientation) {
			$scope.referenceOrientation = {
				alpha: eventData.alpha,
				beta: eventData.beta,
				gamma: eventData.gamma
			};
		}
		
		$scope.deviceOrientation.alpha = eventData.alpha; // the compass direction the device is facing (degrees)
		$scope.deviceOrientation.beta = eventData.beta;   // tilt front-to-back (degrees)
		$scope.deviceOrientation.gamma = eventData.gamma; // tilt left-to-right (degrees)
		$scope.$apply();
		
		var message = {
			action: 'device-orientation',
			alpha: getOffsetAlpha($scope.referenceOrientation.alpha, $scope.deviceOrientation.alpha),
			beta: getOffsetBeta($scope.referenceOrientation.beta, $scope.deviceOrientation.beta),
			gamma: getOffsetGamma($scope.referenceOrientation.gamma, $scope.deviceOrientation.gamma)
		};
		socket.send(JSON.stringify(message));		
	};
		
	initialize();
};

var app = angular.module('spa');
app.controller('GamePlayerCtrl', ctrl);
