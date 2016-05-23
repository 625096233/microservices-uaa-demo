(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('DeviceDialogController', DeviceDialogController);

    DeviceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Device', 'DeviceGroup'];

    function DeviceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Device, DeviceGroup) {
        var vm = this;
        vm.device = entity;
        vm.devicegroups = DeviceGroup.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('gatewayApp:deviceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.device.id !== null) {
                Device.update(vm.device, onSaveSuccess, onSaveError);
            } else {
                Device.save(vm.device, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
