(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('DeviceGroupDialogController', DeviceGroupDialogController);

    DeviceGroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DeviceGroup', 'Device'];

    function DeviceGroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DeviceGroup, Device) {
        var vm = this;
        vm.deviceGroup = entity;
        vm.devices = Device.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('gatewayApp:deviceGroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.deviceGroup.id !== null) {
                DeviceGroup.update(vm.deviceGroup, onSaveSuccess, onSaveError);
            } else {
                DeviceGroup.save(vm.deviceGroup, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
