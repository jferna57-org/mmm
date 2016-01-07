'use strict';

angular.module('mmmApp')
    .controller('ActivoDetailController', function ($scope, $rootScope, $stateParams, entity, Activo, User) {
        $scope.activo = entity;
        $scope.load = function (id) {
            Activo.get({id: id}, function(result) {
                $scope.activo = result;
            });
        };
        var unsubscribe = $rootScope.$on('mmmApp:activoUpdate', function(event, result) {
            $scope.activo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
