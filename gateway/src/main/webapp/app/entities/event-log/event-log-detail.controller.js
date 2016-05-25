(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('EventLogDetailController', EventLogDetailController);

    EventLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'EventLog'];

    function EventLogDetailController($scope, $rootScope, $stateParams, entity, EventLog) {
        var vm = this;
        vm.eventLog = entity;
        
        var unsubscribe = $rootScope.$on('gatewayApp:eventLogUpdate', function(event, result) {
            vm.eventLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
