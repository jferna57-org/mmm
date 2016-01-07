'use strict';

angular.module('mmmApp')
	.controller('ActivoDeleteController', function($scope, $uibModalInstance, entity, Activo) {

        $scope.activo = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Activo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
