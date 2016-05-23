(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('DeviceDeleteController',DeviceDeleteController);

    DeviceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Device'];

    function DeviceDeleteController($uibModalInstance, entity, Device) {
        var vm = this;
        vm.device = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Device.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
