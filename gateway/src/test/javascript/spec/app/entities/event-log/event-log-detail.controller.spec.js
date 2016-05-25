'use strict';

describe('Controller Tests', function() {

    describe('EventLog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEventLog;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEventLog = jasmine.createSpy('MockEventLog');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'EventLog': MockEventLog
            };
            createController = function() {
                $injector.get('$controller')("EventLogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gatewayApp:eventLogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
