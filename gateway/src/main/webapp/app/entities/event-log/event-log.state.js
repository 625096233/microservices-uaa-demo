(function() {
    'use strict';

    angular
        .module('gatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('event-log', {
            parent: 'entity',
            url: '/event-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EventLogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/event-log/event-logs.html',
                    controller: 'EventLogController',
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
        .state('event-log-detail', {
            parent: 'entity',
            url: '/event-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EventLog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/event-log/event-log-detail.html',
                    controller: 'EventLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EventLog', function($stateParams, EventLog) {
                    return EventLog.get({id : $stateParams.id});
                }]
            }
        })
        .state('event-log.new', {
            parent: 'event-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-log/event-log-dialog.html',
                    controller: 'EventLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                eventId: null,
                                eventType: null,
                                deviceId: null,
                                eventData: null,
                                eventDateTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('event-log', null, { reload: true });
                }, function() {
                    $state.go('event-log');
                });
            }]
        })
        .state('event-log.edit', {
            parent: 'event-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-log/event-log-dialog.html',
                    controller: 'EventLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EventLog', function(EventLog) {
                            return EventLog.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('event-log', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('event-log.delete', {
            parent: 'event-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-log/event-log-delete-dialog.html',
                    controller: 'EventLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EventLog', function(EventLog) {
                            return EventLog.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('event-log', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
