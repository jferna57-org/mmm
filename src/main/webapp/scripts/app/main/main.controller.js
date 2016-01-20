'use strict';

angular.module('mmmApp').controller('MainController',
		function($scope, Principal, Activo, HistoricoSaldo) {
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
                        right: 20,
                        bottom: 60,
                        left: 120
                    },
                    x: function(d){ return d.label; },
                    y: function(d){ return d.value; },
                    showValues: false,
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

            $scope.optionsAllYears = {
                chart: {
                    type: 'discreteBarChart',
                    height: 500,
                    margin : {
                        top: 20,
                        right: 20,
                        bottom: 60,
                        left: 120
                    },
                    x: function(d){ return d.label; },
                    y: function(d){ return d.value; },
                    showValues: false,
                    valueFormat: function(d){
                        return d3.format(',.4f')(d);
                    },
                    transitionDuration: 500,
                    xAxis: {
                        axisLabel: 'Años'
                    },
                    yAxis: {
                        axisLabel: 'Importe',
                        axisLabelDistance: 50
                    }
                }
            };
            /*
            $scope.dataAllYears = [{
                key: "Cumulative Return",
                values: [
                    { "label" : "2009" , "value" : 100000 },
                    { "label" : "2010" , "value" : 110000 },
                    { "label" : "2011" , "value" : 110000 },
                    { "label" : "2012" , "value" : 110000 },
                    { "label" : "2013" , "value" : 110000 },
                    { "label" : "2014" , "value" : 110000 },
                    { "label" : "2015" , "value" : 110000 },
                    { "label" : "2016" , "value" : 110000 }
                ]
            }]; */


            HistoricoSaldo.allYears(function(data){

                var amounts, years;

                amounts = [];
                years = [];

                for (var i = 0; i < data.importes.length; i++){
                    amounts.push({label: data.years[i], value: data.importes[i]});
                }

                $scope.dataAllYears = [{
                    key: 'Años',
                    values: amounts
                }];

            });


		});
