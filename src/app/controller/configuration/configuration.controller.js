(function() {
  'use strict';

  angular
    .module('gisproject')
    .controller('ConfigurationController', ConfigurationController);

  /** @ngInject */
  function ConfigurationController($scope,$log,$rootScope) {
    var vm = this;
	
	vm.associationName;
	
	vm.class1=['building','square','office'];
	vm.class2=['edificio'];
	
	vm.selectionValue=[];
	
	vm.associationList={};
	
	function associate()
	{
		if (vm.associationName==null || vm.associationName==undefined)
			vm.associationName="Default";
		
		vm.associationList[vm.associationName]={};
		vm.associationList[vm.associationName].name=vm.associationName;
		vm.associationList[vm.associationName].classList=[];
		for (var i=0; i<Object.keys(vm.selectionValue).length; i++)
		{
			if (vm.selectionValue[Object.keys(vm.selectionValue)[i]])
			{
				vm.associationList[vm.associationName].classList.push(Object.keys(vm.selectionValue)[i]);
			} 
		}
		
		$rootScope.associationList=vm.associationList;
		
	
	}
	
	
	vm.associate=associate;
	
	
    }
})();
