(function() {
  'use strict';

  angular
    .module('gisproject')
    .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
      .state('home', {
        url: '/home',
        templateUrl: 'app/main/main.html',
        controller: 'MainController',
        controllerAs: 'vm'
      })
	  .state('uploadMap', {
        url: '/uploadMap',
        templateUrl: 'app/controller/uploadMap/uploadMap.html',
        controller: 'UploadMapController',
        controllerAs: 'vm'
      })
	  .state('result', {
        url: '/result/:resultLayer',
        templateUrl: 'app/controller/result/result.html',
        controller: 'ResultController',
        controllerAs: 'vm'
      })
	  .state('configuration', {
        url: '/configuration',
        templateUrl: 'app/controller/configuration/configuration.html',
        controller: 'ConfigurationController',
        controllerAs: 'vm'
      });

    $urlRouterProvider.otherwise('/home');
  }

})();
