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

            $scope.optionsLastMonths = {
                chart: {
                    type: 'multiBarChart',
                    height: 500,
                    margin : {
                        top: 20,
                        right: 20,
                        bottom: 60,
                        left: 120
                    },
                    x: function(d){ return d.label; },
                    y: function(d){ return d.value; },
                    showValues: true,
                    valueFormat: function(d){
                        return d3.format(',.2f')(d);
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

            HistoricoSaldo.lastMoths(function(data){
                var amounts, profit;

                amounts = [];
                profit = [];

                var lastAmount = data.amount[data.month.length -1];

                for (var i = data.month.length -1 ; i >= 0; i--){
                    amounts.push({label: data.month[i] , value: data.amount[i]});
                    profit.push({label: data.month[i], value: data.amount[i]-lastAmount});
                    lastAmount = data.amount[i];
                }

                $scope.dataLastMonths = [{
                    key: 'Amounts',
                    values: amounts,
                    color: "#1f77b4",
                },{
                    key: 'Benefits',
                    values: profit,
                    color: "#d62728",

                }];

            });

            $scope.optionsAllYears = {
                chart: {
                    type: 'multiBarChart',
                    height: 500,
                    margin : {
                        top: 20,
                        right: 20,
                        bottom: 60,
                        left: 120
                    },
                    x: function(d){ return d.label; },
                    y: function(d){ return d.value; },
                    showValues: true,
                    valueFormat: function(d){
                        return d3.format(',.2f')(d);
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

            HistoricoSaldo.allYears(function(data){

                var amounts, profit;

                amounts = [];
                profit = [];

                var lastAmount = 0;

                for (var i = 1; i < data.years.length-1; i++){
                    amounts.push({label: data.years[i], value: data.importes[i]});
                    profit.push({label: data.years[i], value: data.importes[i]-lastAmount});
                    lastAmount =data.importes[i];
                }

                $scope.dataAllYears = [{
                    key: 'Saldo',
                    values: amounts,
                    color: "#1f77b4",
                },{
                    key: 'Beneficio',
                    values: profit,
                    color: "#d62728",
                }];

            });


		});
