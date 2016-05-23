(function() {
    'use strict';
    angular
        .module('gatewayApp')
        .factory('DeviceGroup', DeviceGroup);

    DeviceGroup.$inject = ['$resource'];

    function DeviceGroup ($resource) {
        var resourceUrl =  'device/' + 'api/device-groups/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
