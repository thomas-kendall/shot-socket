var app = angular.module('spa', ['ngRoute']);
app.config(function($routeProvider){
    $routeProvider
        .when('/game', {
            templateUrl: 'app/pages/game/game.html',
            controller: 'GameCtrl'
        })
        .when('/game-player/:gameId', {
            templateUrl: 'app/pages/game-player/game-player.html',
            controller: 'GamePlayerCtrl'
        })
        .when('/orientation-not-supported', {
            templateUrl: 'app/pages/orientation-not-supported/orientation-not-supported.html'
        })
        .otherwise('/game');    
});