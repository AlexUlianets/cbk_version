var app = angular.module('main', ['ui.router', 'oc.lazyLoad']);

// Enter page

app.controller('widgets',['$scope', '$http', '$state','$stateParams', function($scope, $http, $state, $stateParams) {
    $scope.$state = $state;
    $scope.$stateParams = $stateParams;
    $scope.graph = $scope.$state.params.graph;
    $scope.tabClass = $scope.$state.params.tabClass;
    $scope.page=$scope.$state.params.page;
    loadScript();
    }])