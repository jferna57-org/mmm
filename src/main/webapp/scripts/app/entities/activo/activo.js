'use strict';

angular.module('mmmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('activo', {
                parent: 'entity',
                url: '/activos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'mmmApp.activo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/activo/activos.html',
                        controller: 'ActivoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('activo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('activo.detail', {
                parent: 'entity',
                url: '/activo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'mmmApp.activo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/activo/activo-detail.html',
                        controller: 'ActivoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('activo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Activo', function($stateParams, Activo) {
                        return Activo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('activo.new', {
                parent: 'activo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/activo/activo-dialog.html',
                        controller: 'ActivoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nombre: null,
                                    descripcion: null,
                                    saldo: null,
                                    fecha: null,
                                    activo: null,
                                    notas: null,
                                    fechaAlta: null,
                                    fechaBaja: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('activo', null, { reload: true });
                    }, function() {
                        $state.go('activo');
                    })
                }]
            })
            .state('activo.edit', {
                parent: 'activo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/activo/activo-dialog.html',
                        controller: 'ActivoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Activo', function(Activo) {
                                return Activo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('activo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('activo.delete', {
                parent: 'activo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/activo/activo-delete-dialog.html',
                        controller: 'ActivoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Activo', function(Activo) {
                                return Activo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('activo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
