var ctrl = function($scope, $location, networkService) {
	var gameId = 0;
	$scope.gameControllerUrl = '';
	$scope.playerConnected = false;
	$scope.deviceOrientation = {};
	$scope.canvasAlpha = null;
	$scope.rectAlpha = null;
	$scope.canvasBeta = null;
	$scope.rectBeta = null;
	$scope.canvasGamma = null;
	$scope.rectGamma = null;
		
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
			drawScene($scope.canvasAlpha, $scope.deviceOrientation.alpha); //    0 to 360
			drawScene($scope.canvasBeta, $scope.deviceOrientation.beta);   // -180 to 180
			drawScene($scope.canvasGamma, $scope.deviceOrientation.gamma * 2); //  -90 to  90
		}
		
		$scope.$apply();
	};
	
	var setupScenes = function() {
		$scope.canvasAlpha = new fabric.Canvas('canvas-alpha');
		$scope.canvasBeta = new fabric.Canvas('canvas-beta');
		$scope.canvasGamma = new fabric.Canvas('canvas-gamma');
	};
	
	var drawScene = function(canvas, angle) {
		var rect = canvas.item(0);
		if(!rect){
			var rectWidth = 4;
			var rectHeight = 50;
			rect = new fabric.Rect({
				left: canvas.width/2 - rectWidth/2,
				top: (canvas.height - rectHeight)/2,
				fill: 'red',
				width: rectWidth,
				height: rectHeight,
			});			
			canvas.add(rect);
		}
		
		rect.set({
			angle: angle
		});
		canvas.renderAll();
	};
	
	setupScenes();
	var socketUrl = networkService.getWebsocketUrl('/game-display');
	var socket = new WebSocket(socketUrl);
	socket.onmessage = socketMessageReceived;	
};

var app = angular.module('spa');
app.controller('GameCtrl', ctrl);
