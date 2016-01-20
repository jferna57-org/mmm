'use strict';

angular.module('mmmApp')
    .factory('HistoricoSaldo', function ($resource, DateUtils) {
        return $resource('api/historicoSaldos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'allYears': { method: 'GET', isArray: false, url: 'api/historicoSaldos/allYears'},
            'lastMoths': { method: 'GET', isArray: false, url: 'api/historicoSaldos/lastMonths'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.fecha = DateUtils.convertLocaleDateFromServer(data.fecha);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.fecha = DateUtils.convertLocaleDateToServer(data.fecha);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.fecha = DateUtils.convertLocaleDateToServer(data.fecha);
                    return angular.toJson(data);
                }
            }
        });
    });
