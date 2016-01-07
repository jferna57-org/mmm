'use strict';

angular.module('mmmApp').controller('ActivoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Activo', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Activo, User) {

        $scope.activo = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Activo.get({id : id}, function(result) {
                $scope.activo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('mmmApp:activoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.activo.id != null) {
                Activo.update($scope.activo, onSaveSuccess, onSaveError);
            } else {
                Activo.save($scope.activo, onSaveSuccess, onSaveError);
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
        $scope.datePickerForFechaAlta = {};

        $scope.datePickerForFechaAlta.status = {
            opened: false
        };

        $scope.datePickerForFechaAltaOpen = function($event) {
            $scope.datePickerForFechaAlta.status.opened = true;
        };
        $scope.datePickerForFechaBaja = {};

        $scope.datePickerForFechaBaja.status = {
            opened: false
        };

        $scope.datePickerForFechaBajaOpen = function($event) {
            $scope.datePickerForFechaBaja.status.opened = true;
        };
}]);
