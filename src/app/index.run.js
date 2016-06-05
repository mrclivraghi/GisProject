(function() {
  'use strict';

  angular
    .module('gisproject')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log,$rootScope) {

		
	$rootScope.parameters={};
  
    $log.debug('runBlock end');
  }

})();
