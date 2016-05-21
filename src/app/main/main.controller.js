
(function() {
  'use strict';

  angular
    .module('gisproject')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController($scope,MarkerService,$log) {
    var vm = this;
	
	vm.markerPairList={};
	vm.markers1={
		
	};
	vm.markers2={
		
	};
	
	function searchMarker()
	{
		MarkerService.search().then(function successCallback(response) {
				vm.markerPairList=response.data;
				for (var i =0; i<vm.markerPairList.length; i++)
				{
					vm.markers1["m"+(i+1)]=vm.markerPairList[i].marker1;
					vm.markers2["m"+(i+1)]=vm.markerPairList[i].marker2;
					vm.markers1["m"+(i+1)].focus=false;
					vm.markers1["m"+(i+1)].draggable=true;
					vm.markers2["m"+(i+1)].focus=false;
					vm.markers2["m"+(i+1)].draggable=true;
					
				}
				vm.markers1Count=vm.markerPairList.length;
				vm.markers2Count=vm.markerPairList.length;
		},function errorCallback(response) { 
			console.log("error");
			return; 
		});
	}
	searchMarker();
	
	vm.defaults1={
	//crs: 'EPSG3857',
	minZoom: 16
		//srs: 'EPSG:404000'
		
	};
	console.log(vm.defaults1);
	vm.events1= {
            map: {
                enable: ['drag', 'click'],
                logic: 'emit'
            }
        };
	
	
	
	vm.wmsUrl;
	vm.layerName;
	vm.layer;
	
	vm.center1={ lat:45.494384, lng:9.142647, zoom: 15};
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
                      /*  wms: {
                            name: 'Ecuador adm',
                            type: 'wms',
                            visible: true,
                            url: 'http://ows3.como.polimi.it:8080/geoserver/user18_16/wms',
                            layerParams: {
                                layers: 'user18_16:BOL_adm1',
                                format: 'image/png',
                                transparent: true
                            }
							},*/
							
						/*	wms2: {
                            name: 'DBT_00',
                            type: 'wms',
                            visible: true,
                            url: 'http://localhost:8081/geoserver/gisProject/wms',
                            layerParams: {
                                layers: 'gisProject:DBT_00_4326',
                                format: 'image/png',
                                transparent: true
                            }
							}*/
                        }
	};
	
		
$scope.$on('leafletDirectiveMap.map1.click', function(event, args){
	vm.addMarker1(args.leafletEvent.latlng.lat,args.leafletEvent.latlng.lng);
});
$scope.$on('leafletDirectiveMap.map2.click', function(event, args){
	vm.addMarker2(args.leafletEvent.latlng.lat,args.leafletEvent.latlng.lng);
});
   
	
	
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
		MarkerService.send(vm.markerPairList).then(function successCallback(response) {
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
