(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('DeviceDetailController', DeviceDetailController);

    DeviceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Device', 'DeviceGroup'];

    function DeviceDetailController($scope, $rootScope, $stateParams, entity, Device, DeviceGroup) {
        var vm = this;
        vm.device = entity;
        
        var unsubscribe = $rootScope.$on('gatewayApp:deviceUpdate', function(event, result) {
            vm.device = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
