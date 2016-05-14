(function() {
  'use strict';

  angular
    .module('gisproject')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController($scope,MarkerService,$log) {
    var vm = this;
	
	vm.markerPairList={};
	
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
	
	
	vm.wmsUrl;
	vm.layerName;
	vm.layer;
	
	
	vm.center1={ lat:45, lng:9, zoom: 2};
	vm.center2={ lat:0, lng:0, zoom: 2};
	vm.centerLat;
	vm.centerLng;
	
	vm.layers={
		baselayers: {
                        xyz: {
                            name: 'OpenStreetMap (XYZ)',
                            url: 'http://tile.openstreetmap.org/{z}/{x}/{y}.png',
                            type: 'xyz'
                        }
                    },
       overlays: {
                        wms: {
                            name: 'Ecuador adm',
                            type: 'wms',
                            visible: true,
                            url: 'http://ows3.como.polimi.it:8080/geoserver/user18_16/wms',
                            layerParams: {
                                layers: 'user18_16:BOL_adm1',
                                format: 'image/png',
                                transparent: true
                            }
							}
                        }
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
		
		var newMarker={
			lat:latitude,
			lng:longitude,
			focus: true,
			message: 'Marker #'+vm.markers1Count,
			draggable: true
		};
		
		if (!vm.markerPairList["m"+vm.markers1Count])
		{
			vm.markerPairList["m"+vm.markers1Count]={};
			vm.markerPairList["m"+vm.markers1Count].markerPairId=vm.markers1Count;
		}
		
		vm.markerPairList["m"+vm.markers1Count].marker1=newMarker;
		
		
		vm.markers1["m"+vm.markers1Count]=newMarker;
	}
	
	function addMarker2(latitude,longitude)
	{
		vm.markers2Count++;
		
		var newMarker={
			lat:latitude,
			lng:longitude,
			focus: true,
			message: 'Marker #'+vm.markers2Count,
			draggable: false
		};
		
		
		if (!vm.markerPairList["m"+vm.markers2Count])
		{
			vm.markerPairList["m"+vm.markers2Count]={};
			vm.markerPairList["m"+vm.markers2Count].markerPairId=vm.markers2Count;
		}
		
		vm.markerPairList["m"+vm.markers2Count].marker2=newMarker;
		
		vm.markers2["m"+vm.markers2Count]=newMarker;
	}
	
	function highLightMarker(index)
	{
		vm.markers1["m"+index].focus=true;
		vm.markers2["m"+index].focus=true;
		$log.debug(vm.markers1);
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
	
	function addLayer()
	{
		var newLayer= {
							name: vm.layerName,
                            type: 'wms',
                            visible: true,
                            url: vm.wmsUrl,
                            layerParams: {
                                layers: vm.layer,
                                format: 'image/png',
                                transparent: true
                            }
		
		};
		vm.layers.overlays[vm.layerName]=newLayer;
	
	}
	
	function center(center)
	{
		center.lat=Number(vm.centerLat);
		center.lng=Number(vm.centerLng);
		$log.debug(center);
	
	}
	
	
	
	
	vm.addMarker1=addMarker1;
	vm.addMarker2=addMarker2;
	vm.highLightMarker=highLightMarker;
	vm.sendMarker=sendMarker;
	vm.addLayer=addLayer;
	vm.center=center;
	
    }
})();
