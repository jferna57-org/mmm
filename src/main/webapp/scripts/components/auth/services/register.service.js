'use strict';

angular.module('mmmApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


