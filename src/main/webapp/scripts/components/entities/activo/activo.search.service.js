'use strict';

angular.module('mmmApp')
    .factory('ActivoSearch', function ($resource) {
        return $resource('api/_search/activos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
