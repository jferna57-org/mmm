'use strict';

angular.module('mmmApp')
	.controller('HistoricoSaldoDeleteController', function($scope, $uibModalInstance, entity, HistoricoSaldo) {

        $scope.historicoSaldo = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HistoricoSaldo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
