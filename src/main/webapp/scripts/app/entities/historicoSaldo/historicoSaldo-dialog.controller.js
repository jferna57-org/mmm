'use strict';

angular.module('mmmApp').controller('HistoricoSaldoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HistoricoSaldo', 'Activo', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, HistoricoSaldo, Activo, User) {

        $scope.historicoSaldo = entity;
        $scope.activos = Activo.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            HistoricoSaldo.get({id : id}, function(result) {
                $scope.historicoSaldo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('mmmApp:historicoSaldoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.historicoSaldo.id != null) {
                HistoricoSaldo.update($scope.historicoSaldo, onSaveSuccess, onSaveError);
            } else {
                HistoricoSaldo.save($scope.historicoSaldo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForFecha = {};

        $scope.datePickerForFecha.status = {
            opened: false
        };

        $scope.datePickerForFechaOpen = function($event) {
            $scope.datePickerForFecha.status.opened = true;
        };
}]);
