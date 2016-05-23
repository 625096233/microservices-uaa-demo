'use strict';

describe('Controller Tests', function() {

    describe('DeviceGroup Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDeviceGroup, MockDevice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDeviceGroup = jasmine.createSpy('MockDeviceGroup');
            MockDevice = jasmine.createSpy('MockDevice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DeviceGroup': MockDeviceGroup,
                'Device': MockDevice
            };
            createController = function() {
                $injector.get('$controller')("DeviceGroupDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gatewayApp:deviceGroupUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
