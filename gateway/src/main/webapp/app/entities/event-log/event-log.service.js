(function() {
    'use strict';
    angular
        .module('gatewayApp')
        .factory('EventLog', EventLog);

    EventLog.$inject = ['$resource', 'DateUtils'];

    function EventLog ($resource, DateUtils) {
        var resourceUrl =  'event/' + 'api/event-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.eventDateTime = DateUtils.convertDateTimeFromServer(data.eventDateTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
