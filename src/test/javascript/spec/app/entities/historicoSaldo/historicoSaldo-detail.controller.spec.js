'use strict';

describe('Controller Tests', function() {

    describe('HistoricoSaldo Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHistoricoSaldo, MockActivo, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHistoricoSaldo = jasmine.createSpy('MockHistoricoSaldo');
            MockActivo = jasmine.createSpy('MockActivo');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HistoricoSaldo': MockHistoricoSaldo,
                'Activo': MockActivo,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("HistoricoSaldoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mmmApp:historicoSaldoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
