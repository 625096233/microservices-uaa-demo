(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('device-group', {
            parent: 'entity',
            url: '/device-group?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DeviceGroups'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/device-group/device-groups.html',
                    controller: 'DeviceGroupController',
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
        .state('device-group-detail', {
            parent: 'entity',
            url: '/device-group/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DeviceGroup'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/device-group/device-group-detail.html',
                    controller: 'DeviceGroupDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DeviceGroup', function($stateParams, DeviceGroup) {
                    return DeviceGroup.get({id : $stateParams.id});
                }]
            }
        })
        .state('device-group.new', {
            parent: 'device-group',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-group/device-group-dialog.html',
                    controller: 'DeviceGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                groupId: null,
                                groupName: null,
                                groupDescription: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('device-group', null, { reload: true });
                }, function() {
                    $state.go('device-group');
                });
            }]
        })
        .state('device-group.edit', {
            parent: 'device-group',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-group/device-group-dialog.html',
                    controller: 'DeviceGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DeviceGroup', function(DeviceGroup) {
                            return DeviceGroup.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('device-group', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('device-group.delete', {
            parent: 'device-group',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-group/device-group-delete-dialog.html',
                    controller: 'DeviceGroupDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DeviceGroup', function(DeviceGroup) {
                            return DeviceGroup.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('device-group', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
