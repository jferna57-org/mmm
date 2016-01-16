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

            $scope.optionsLastYear = {
                chart: {
                    type: 'discreteBarChart',
                    height: 500,
                    margin : {
                        top: 20,
                        right: 100,
                        bottom: 60,
                        left: 55
                    },
                    x: function(d){ return d.label; },
                    y: function(d){ return d.value; },
                    showValues: true,
                    valueFormat: function(d){
                        return d3.format('')(d);
                    },
                    transitionDuration: 500,
                    xAxis: {
                        axisLabel: 'Meses'
                    },
                    yAxis: {
                        axisLabel: 'Importe',
                        axisLabelDistance: 50
                    }
                }
            };

            $scope.dataLastYear = [{
                key: "Cumulative Return",
                values: [
                    { "label" : "Ene" , "value" : 100000 },
                    { "label" : "Feb" , "value" : 110000 },
                    { "label" : "Mar" , "value" : 110000 },
                    { "label" : "Abr" , "value" : 110000 },
                    { "label" : "May" , "value" : 110000 },
                    { "label" : "Jun" , "value" : 110000 },
                    { "label" : "Jul" , "value" : 110000 },
                    { "label" : "Ago" , "value" : 110000 },
                    { "label" : "Sep" , "value" : 110000 },
                    { "label" : "Oct" , "value" : 110000 },
                    { "label" : "Nov" , "value" : 110000 },
                    { "label" : "Dic" , "value" : 110000 }
                    ]
                }];

		});
