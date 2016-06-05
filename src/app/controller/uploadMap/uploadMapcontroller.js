(function() {
  'use strict';

  angular
    .module('gisproject')
    .controller('UploadMapController', UploadMapController);

  /** @ngInject */
  function UploadMapController($scope,$log,mainService) {
    var vm = this;
	
	vm.map;
		function loadFile(file,field)
		{
			mainService.loadFile(file,field).then(function successCallback(response) {
				$log.debug(response);
				vm.map=response.data;
			},function errorCallback(response) { 
				$log.error("Si è verificato un errore");
				$log.debug(response);
				return; 
			});
		}
		
		
		vm.loadFile=loadFile;
	
    }
})();
