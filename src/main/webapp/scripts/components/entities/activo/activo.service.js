'use strict';

angular.module('mmmApp')
    .factory('Activo', function ($resource, DateUtils) {
        return $resource('api/activos/:id', {}, {
            'query': { method: 'GET', isArray: true},
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
