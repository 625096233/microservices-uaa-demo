(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .controller('EventLogDeleteController',EventLogDeleteController);

    EventLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'EventLog'];

    function EventLogDeleteController($uibModalInstance, entity, EventLog) {
        var vm = this;
        vm.eventLog = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            EventLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
