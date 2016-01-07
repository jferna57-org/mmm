'use strict';

angular.module('mmmApp')
    .controller('HistoricoSaldoController', function ($scope, $state, HistoricoSaldo, HistoricoSaldoSearch, ParseLinks) {

        $scope.historicoSaldos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            HistoricoSaldo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.historicoSaldos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HistoricoSaldoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.historicoSaldos = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.historicoSaldo = {
                saldo: null,
                fecha: null,
                notas: null,
                id: null
            };
        };
    });
