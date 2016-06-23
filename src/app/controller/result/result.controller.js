
(function() {
  'use strict';

  angular
    .module('gisproject')
    .controller('ResultController', ResultController);

  /** @ngInject */
  function ResultController($scope,$rootScope,mainService,$stateParams,$http) {
    var vm = this;
	
	
	vm.defaults={
	//crs: 'EPSG3857',
	minZoom: 16
		//srs: 'EPSG:404000'
		
	};
	
	vm.center={ lat:45.494384, lng:9.142647, zoom: 15};
	
	vm.layers={
		baselayers: {
                        xyz: {
                            name: 'OpenStreetMap (XYZ)',
                            url: 'http://tile.openstreetmap.org/{z}/{x}/{y}.png',
                            type: 'xyz'
                        }
                    },
       overlays: {
                     
                        }
	};
	console.log($stateParams.resultLayer);
	var newLayer= {
							name: $stateParams.resultLayer,
                            type: 'wms',
                            visible: true,
                            url: 'http://localhost:8081/geoserver/gisProject/wms',
                            layerParams: {
                                layers: 'gisProject:'+$stateParams.resultLayer,
                                format: 'image/png',
                                transparent: true
                            }
	};
	vm.layers.overlays[$stateParams.resultLayer]=newLayer;
	if ($rootScope.secondLayer)
		vm.layers.overlays['source map']=$rootScope.secondLayer;
	$rootScope.resultLayer=newLayer;
	/*function addLayer(mapIndex)
	{
		var newLayer;
		
		if (vm.selectedMapFile==null || vm.selectedMapFile==null)
		{
			
			newLayer= {
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
		
		} else
		{
				
			
			};
			
		}
		
		
		if (mapIndex==1)
			vm.layers1.overlays[vm.layerName]=newLayer;
		else
			vm.layers2.overlays[vm.layerName]=newLayer;
	
	}*/
	
	vm.statistics;
	
	function getStats()
	{
		mainService.getStats().then(function successCallback(response) {
			vm.statistics=response.data;
			console.log(vm.statistics);
		},function errorCallback(response) { 
			console.log("error");
			return; 
		});
		
	}
	getStats();
	
	function download()
	{
		return "http://localhost:8081/geoserver/gisProject/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=gisProject:"+$rootScope.resultLayer.name+"&outputFormat=SHAPE-ZIP";
    }
	
	vm.download=download;
	
	}
	
})();
