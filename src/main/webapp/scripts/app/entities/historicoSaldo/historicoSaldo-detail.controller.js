'use strict';

angular.module('mmmApp')
    .controller('HistoricoSaldoDetailController', function ($scope, $rootScope, $stateParams, entity, HistoricoSaldo, Activo, User) {
        $scope.historicoSaldo = entity;
        $scope.load = function (id) {
            HistoricoSaldo.get({id: id}, function(result) {
                $scope.historicoSaldo = result;
            });
        };
        var unsubscribe = $rootScope.$on('mmmApp:historicoSaldoUpdate', function(event, result) {
            $scope.historicoSaldo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
