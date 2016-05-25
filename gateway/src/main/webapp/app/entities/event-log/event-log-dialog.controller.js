(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('EventLogDialogController', EventLogDialogController);

    EventLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EventLog'];

    function EventLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EventLog) {
        var vm = this;
        vm.eventLog = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('gatewayApp:eventLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.eventLog.id !== null) {
                EventLog.update(vm.eventLog, onSaveSuccess, onSaveError);
            } else {
                EventLog.save(vm.eventLog, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.eventDateTime = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
