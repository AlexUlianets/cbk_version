'use strict';

var services = angular.module('oplaoApp.services', ['ngResource']);

services.factory('DummyFactory', function ($resource) {
    return $resource('/', {}, {
        query: { method: 'GET', params: {} }
    })
});
