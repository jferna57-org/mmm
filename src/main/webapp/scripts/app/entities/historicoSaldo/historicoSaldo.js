'use strict';

angular.module('mmmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('historicoSaldo', {
                parent: 'entity',
                url: '/historicoSaldos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'mmmApp.historicoSaldo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/historicoSaldo/historicoSaldos.html',
                        controller: 'HistoricoSaldoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('historicoSaldo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('historicoSaldo.detail', {
                parent: 'entity',
                url: '/historicoSaldo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'mmmApp.historicoSaldo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/historicoSaldo/historicoSaldo-detail.html',
                        controller: 'HistoricoSaldoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('historicoSaldo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HistoricoSaldo', function($stateParams, HistoricoSaldo) {
                        return HistoricoSaldo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('historicoSaldo.new', {
                parent: 'historicoSaldo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/historicoSaldo/historicoSaldo-dialog.html',
                        controller: 'HistoricoSaldoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    saldo: null,
                                    fecha: null,
                                    notas: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('historicoSaldo', null, { reload: true });
                    }, function() {
                        $state.go('historicoSaldo');
                    })
                }]
            })
            .state('historicoSaldo.edit', {
                parent: 'historicoSaldo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/historicoSaldo/historicoSaldo-dialog.html',
                        controller: 'HistoricoSaldoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HistoricoSaldo', function(HistoricoSaldo) {
                                return HistoricoSaldo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('historicoSaldo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('historicoSaldo.delete', {
                parent: 'historicoSaldo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/historicoSaldo/historicoSaldo-delete-dialog.html',
                        controller: 'HistoricoSaldoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HistoricoSaldo', function(HistoricoSaldo) {
                                return HistoricoSaldo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('historicoSaldo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
