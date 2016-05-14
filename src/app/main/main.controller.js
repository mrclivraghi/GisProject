(function() {
  'use strict';

  angular
    .module('gisproject')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController($scope,MarkerService,$log) {
    var vm = this;
	vm.markers1Count=0;
	vm.markers2Count=0;
	vm.events1= {
            map: {
                enable: ['drag', 'click'],
                logic: 'emit'
            }
        };
	vm.markers1={
		
	};
	vm.markers2={
		
	};
		
$scope.$on('leafletDirectiveMap.map1.click', function(event, args){
	vm.addMarker1(args.leafletEvent.latlng.lat,args.leafletEvent.latlng.lng);
});
$scope.$on('leafletDirectiveMap.map2.click', function(event, args){
	vm.addMarker2(args.leafletEvent.latlng.lat,args.leafletEvent.latlng.lng);
});
   
	
	
    $scope.defaults= {
            scrollWheelZoom: false
        }
	
	function addMarker1(latitude,longitude)
	{
		vm.markers1Count++;
		vm.markers1["m"+vm.markers1Count]={
			lat:latitude,
			lng:longitude,
			focus: true,
			message: 'Marker #'+vm.markers1Count,
			draggable: false
		};
	}
	
	function addMarker2(latitude,longitude)
	{
		vm.markers2Count++;
		vm.markers2["m"+vm.markers2Count]={
			lat:latitude,
			lng:longitude,
			focus: true,
			message: 'Marker #'+vm.markers2Count,
			draggable: false
		};
	}
	
	function sendMarker()
	{
		MarkerService.send(vm.markers).then(function successCallback(response) {
				console.log(response);
},function errorCallback(response) { 
	console.log("error");
	return; 
});
	
	}
	
	
	
	vm.addMarker1=addMarker1;
	vm.addMarker2=addMarker2;
	vm.sendMarker=sendMarker;
	
	
    }
})();
