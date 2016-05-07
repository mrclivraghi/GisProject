(function() {
  'use strict';

  angular
    .module('gisproject')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController($scope) {
    var vm = this;

    $scope.defaults= {
            scrollWheelZoom: false
        }
		
    }
})();
