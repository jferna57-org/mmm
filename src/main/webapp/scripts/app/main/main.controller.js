'use strict';

angular.module('mmmApp').controller('MainController',
		function($scope, Principal, Activo) {
			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
			});

			Activo.thisMonth(function(data) {
				
				$scope.saldoThisMonth = data;

			});

		});
