(function() {
  'use strict';

  angular
    .module('gisproject')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController($scope,MarkerService) {
    var vm = this;
	vm.markersCount=0;
	vm.events= {
            map: {
                enable: ['drag', 'click'],
                logic: 'emit'
            }
        };
	vm.markers={
		
	};
		
$scope.$on('leafletDirectiveMap.click', function(event, args){
	vm.addMarker(args.leafletEvent.latlng.lat,args.leafletEvent.latlng.lng);
});
   
	
	
    $scope.defaults= {
            scrollWheelZoom: false
        }
	
	function addMarker(latitude,longitude)
	{
		vm.markersCount++;
		vm.markers["m"+vm.markersCount]={
			lat:latitude,
			lng:longitude,
			focus: true,
			message: 'Marker #'+vm.markersCount,
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
	
	
	
	vm.addMarker=addMarker;
	vm.sendMarker=sendMarker;
	
	
    }
})();
