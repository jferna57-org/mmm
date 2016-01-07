'use strict';

angular.module('mmmApp')
    .factory('HistoricoSaldoSearch', function ($resource) {
        return $resource('api/_search/historicoSaldos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
