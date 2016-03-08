'use strict';

angular.module('mmmApp')
    .factory('Activo', function ($resource, DateUtils) {
        return $resource('api/activos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'thisMonth': { method: 'GET', isArray: false, url: 'api/saldo-this-month'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.fecha = DateUtils.convertLocaleDateFromServer(data.fecha);
                    data.fechaAlta = DateUtils.convertLocaleDateFromServer(data.fechaAlta);
                    data.fechaBaja = DateUtils.convertLocaleDateFromServer(data.fechaBaja);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.fecha = DateUtils.convertLocaleDateToServer(data.fecha);
                    data.fechaAlta = DateUtils.convertLocaleDateToServer(data.fechaAlta);
                    data.fechaBaja = DateUtils.convertLocaleDateToServer(data.fechaBaja);
                    return angular.toJson(data);
                }
            },
            'historicize': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.fecha = DateUtils.convertLocaleDateToServer(data.fecha);
                    return angular.toJson(data);
                },
                url: 'api/historicize'
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.fecha = DateUtils.convertLocaleDateToServer(data.fecha);
                    data.fechaAlta = DateUtils.convertLocaleDateToServer(data.fechaAlta);
                    data.fechaBaja = DateUtils.convertLocaleDateToServer(data.fechaBaja);
                    return angular.toJson(data);
                }
            }
        });
    });
