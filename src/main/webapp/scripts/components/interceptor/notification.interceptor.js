 'use strict';

angular.module('mmmApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-mmmApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-mmmApp-params')});
                }
                return response;
            }
        };
    });
