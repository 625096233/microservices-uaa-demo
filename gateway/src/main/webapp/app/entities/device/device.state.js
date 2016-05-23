(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('device', {
            parent: 'entity',
            url: '/device?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Devices'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/device/devices.html',
                    controller: 'DeviceController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('device-detail', {
            parent: 'entity',
            url: '/device/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Device'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/device/device-detail.html',
                    controller: 'DeviceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Device', function($stateParams, Device) {
                    return Device.get({id : $stateParams.id});
                }]
            }
        })
        .state('device.new', {
            parent: 'device',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device/device-dialog.html',
                    controller: 'DeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                deviceId: null,
                                deviceName: null,
                                deviceDescription: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('device', null, { reload: true });
                }, function() {
                    $state.go('device');
                });
            }]
        })
        .state('device.edit', {
            parent: 'device',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device/device-dialog.html',
                    controller: 'DeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Device', function(Device) {
                            return Device.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('device', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('device.delete', {
            parent: 'device',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device/device-delete-dialog.html',
                    controller: 'DeviceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Device', function(Device) {
                            return Device.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('device', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
