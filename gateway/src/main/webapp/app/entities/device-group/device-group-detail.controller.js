(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('DeviceGroupDetailController', DeviceGroupDetailController);

    DeviceGroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'DeviceGroup', 'Device'];

    function DeviceGroupDetailController($scope, $rootScope, $stateParams, entity, DeviceGroup, Device) {
        var vm = this;
        vm.deviceGroup = entity;
        
        var unsubscribe = $rootScope.$on('gatewayApp:deviceGroupUpdate', function(event, result) {
            vm.deviceGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
