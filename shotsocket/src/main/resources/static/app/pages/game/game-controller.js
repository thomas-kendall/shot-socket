var ctrl = function($scope, $location, networkService) {
	var gameId = 0;
	$scope.gameControllerUrl = '';
	$scope.playerConnected = false;
	$scope.deviceOrientation = {};
		
	var socketMessageReceived = function(e) {
		var message = JSON.parse(e.data);
		if(message.action === 'new-game'){
			gameId = message.gameId;
			$scope.gameControllerUrl = networkService.getFullUrl("/#/game-player/" + gameId);
			new QRCode(document.getElementById('gameControllerUrlQrCode'), $scope.gameControllerUrl);
		} else if(message.action === 'player-connected'){
			$scope.playerConnected = true;
		} else if(message.action === 'player-disconnected'){
			$scope.playerConnected = false;
		} else if(message.action === 'device-orientation'){
			$scope.deviceOrientation.alpha = message.alpha;
			$scope.deviceOrientation.beta = message.beta;
			$scope.deviceOrientation.gamma = message.gamma;
			drawScene();
		}
		
		$scope.$apply();
	};
	
	var drawScene = function() {
		var canvas = document.getElementById('canvas');
		if(canvas) {
			var ctx = canvas.getContext('2d');
						
			// background
//			ctx.fillStyle = 'rgb(100, 100, 100)';
//			ctx.fillRect(0, 0, canvas.width, canvas.height);
			ctx.clearRect(0, 0, canvas.width, canvas.height);
			
			var h = canvas.height / 2;
			var angle = toRadians($scope.deviceOrientation.alpha);
			var dx = h * Math.cos(angle);
			var dy = h * Math.sin(angle);
			var x = canvas.width / 2 + dx;
			var y = canvas.height / 2 + dy;
			
			// draw a line
			ctx.beginPath();
			ctx.moveTo(canvas.width / 2, canvas.height / 2);
			ctx.lineTo(x, y);
			ctx.closePath();
			ctx.stroke();
		}
	};
	
	var toRadians = function(degrees) {
		return degrees * Math.PI / 180;
	}
	
	var socketUrl = networkService.getWebsocketUrl('/game-display');
	var socket = new WebSocket(socketUrl);
	socket.onmessage = socketMessageReceived;
	
};

var app = angular.module('spa');
app.controller('GameCtrl', ctrl);
