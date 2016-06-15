(function() {
  'use strict';

  angular
    .module('gisproject')
    .controller('ConfigurationController', ConfigurationController);

  /** @ngInject */
  function ConfigurationController($scope,$log,$rootScope,mainService) {
    var vm = this;
	
	vm.associationName;
	
	vm.class1=[];
	vm.class2=[];
	
	vm.selectionValue=[];
	
	vm.associationList={};
	
	vm.featureList1;
	vm.featureList2;
	
	
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
		console.log($rootScope.associationList);
	
	}
	
	function getFeatures(layerName,featureType,numClass)
	{
		mainService.getGeoJSON(layerName).then(function successCallback(response) {
			console.log(numClass);
				for (var i=0; i<response.data.features.length; i++)
				{
					var value=response.data.features[i].properties[featureType];
					if (numClass==1){
						if (vm.class1.indexOf(value)<0 && value!="")
							vm.class1.push(value);
					}
					else{
						if (vm.class2.indexOf(value)<0 && value!="")
							vm.class2.push(value);
					}
				}
		},function errorCallback(response) { 
			console.log("error");
			return; 
		});
	}
	
	function findProperty(layerName,id)
	{
		mainService.getPropertyList(layerName).then(function successCallback(response) {
		if (id==2)
		{
			vm.featureList2=response.data;
			$log.debug(vm.featureList2);
			}
		else
			vm.featureList1=response.data;
		},function errorCallback(response) { 
			console.log("error");
			return; 
		});
		
	}
	
	findProperty($rootScope.layer1,1);
	findProperty($rootScope.layer2,2);
	
	
	//getFeatures("OSM_00_4326_attr","TYPE");
	
	
	vm.associate=associate;
	vm.getFeatures=getFeatures;
	
    }
})();
