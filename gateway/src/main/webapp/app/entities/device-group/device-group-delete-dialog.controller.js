(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('DeviceGroupDeleteController',DeviceGroupDeleteController);

    DeviceGroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'DeviceGroup'];

    function DeviceGroupDeleteController($uibModalInstance, entity, DeviceGroup) {
        var vm = this;
        vm.deviceGroup = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            DeviceGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
