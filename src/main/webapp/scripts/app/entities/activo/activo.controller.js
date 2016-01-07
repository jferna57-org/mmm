'use strict';

angular.module('mmmApp')
    .controller('ActivoController', function ($scope, $state, Activo, ActivoSearch, ParseLinks) {

        $scope.activos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Activo.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.activos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ActivoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.activos = result;
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
            $scope.activo = {
                nombre: null,
                descripcion: null,
                saldo: null,
                fecha: null,
                activo: null,
                notas: null,
                fechaAlta: null,
                fechaBaja: null,
                id: null
            };
        };
    });
