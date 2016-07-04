
(function() {
  'use strict';

  angular
    .module('gisproject')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController($scope,$rootScope,MarkerService,$log,mainService,$state) {
    var vm = this;
	
	vm.showResult=false;
	vm.markerPairList=[];
	vm.selectedPair;
	
	vm.markers1={};
	vm.markers2={};
	vm.markers1Count=0;
	vm.markers2Count=0;
	//status of the accordion menu on the right
	vm.status = {
		isCustomHeaderOpen: false,
		isFirstOpen: false,
		isFirstDisabled: false
	};
	
	// definition of the custom icon
	vm.customIcon={
        iconUrl: 'assets/images/end_point.png',
        iconSize:     [15, 15], 
    };
	//function to show the selected pair
	function selectPair(e)
	{
		var item=Number($(".leaflet-contextmenu-item").first().text().replace("Remove pair #",""));
		$(".leaflet-contextmenu-item").remove();
		vm.selectedPair=vm.markerPairList[item-1];
		vm.status.open = true;
	}
	//remove marker 1
	function removeMarker1(e)
	{
		var item=Number($(".leaflet-contextmenu-item").first().text().replace("Remove pair #",""));
		$(".leaflet-contextmenu-item").remove();
		delete vm.markers1[(item)];
		delete vm.markerPairList[item-1].marker1;
		if (vm.markerPairList[item-1].marker2==null)
			vm.markerPairList.splice(item-1,1);
		vm.markers1Count--;
	}
	//remove marker 2
	function removeMarker2(e)
	{
		var item=Number($(".leaflet-contextmenu-item").first().text().replace("Remove pair #",""));
		$(".leaflet-contextmenu-item").remove();
		delete vm.markers2[(item)];
		delete vm.markerPairList[item-1].marker2;
		if (vm.markerPairList[item-1].marker1==null)
			vm.markerPairList.splice(item-1,1);
		vm.markers2Count--;
	}
	//retrieve marer list from the server and show them
	function searchMarker()
	{
		MarkerService.search().then(function successCallback(response) {
				vm.markerPairList=response.data;
				for (var i =0; i<vm.markerPairList.length; i++)
				{
					manageMarkerPairItem(i);
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
		minZoom: 15
		
	};
	//define the managed events of the maps
	vm.events1= {
            map: {
                enable: ['drag', 'click','contextmenu.select'],
                logic: 'emit'
            }
        };
	
	
	
	vm.wmsUrl;
	vm.layerName;
	vm.layer;
	vm.center1={ lat:45.481488, lng: 9.167520, zoom: 18};
	vm.center2={ lat:45.481488, lng: 9.167520, zoom: 18};
	vm.centerLat;
	vm.centerLng;
	
	//base layers
	vm.layers1={
		baselayers: {
                        xyz: {
                            name: 'OpenStreetMap (XYZ)',
                            url: 'http://tile.openstreetmap.org/{z}/{x}/{y}.png',
                            type: 'xyz'
                        }
                    },
       overlays: {}
	};
	
		vm.layers2={
		baselayers: {
                        xyz: {
                            name: 'OpenStreetMap (XYZ)',
                            url: 'http://tile.openstreetmap.org/{z}/{x}/{y}.png',
                            type: 'xyz'
                        }
                    },
       overlays: {}
	};	

	//custom behaviour on click
	$scope.$on('leafletDirectiveMap.map1.click', function(event, args){
		vm.addMarker1(args.leafletEvent.latlng.lat,args.leafletEvent.latlng.lng);
	});
	
	$scope.$on('leafletDirectiveMap.map2.click', function(event, args){
		vm.addMarker2(args.leafletEvent.latlng.lat,args.leafletEvent.latlng.lng);
	});
	
	//add marker to the first map
	function addMarker1(latitude,longitude)
	{
		vm.markers1Count++;
		
		var newMarker={
			lat:latitude,
			lng:longitude,
			focus: true,
			message: 'pair #'+vm.markers1Count,
			draggable: true,
			icon : vm.customIcon,
			contextmenu: true,
			contextmenuWidth: 140,
			contextmenuItems: [{
				text: 'Remove pair #'+vm.markers1Count,
				icon: 'http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/ico/Application.ico',
				iconCls: 'Test',
				hideOnSelect: false,
				callback: function() { vm.removeMarker1()}
			},{
							text: 'Select pair #'+vm.markers1Count,
							icon: 'http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/ico/Application.ico',
							iconCls: 'Test',
							hideOnSelect: false,
							callback: function() { vm.selectPair()}
						}]
		};
		
		if (!vm.markerPairList[vm.markers1Count-1])
		{
			vm.markerPairList.push({});
			vm.markerPairList[vm.markers1Count-1].markerPairId=vm.markers1Count;
		}
		
		vm.markerPairList[vm.markers1Count-1].marker1=newMarker;
		vm.markers1[vm.markers1Count]=newMarker;
	}
	//add marker to the second map
	function addMarker2(latitude,longitude)
	{
		vm.markers2Count++;
		
		var newMarker={
			lat:latitude,
			lng:longitude,
			focus: true,
			message: 'pair #'+vm.markers2Count,
			draggable: true,
			icon : vm.customIcon,
			contextmenu: true,
			contextmenuWidth: 140,
			contextmenuItems: [{
				text: 'Remove pair #'+vm.markers2Count,
				icon: 'http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/ico/Application.ico',
				iconCls: 'Test',
				hideOnSelect: false,
				callback: function() { vm.removeMarker2()}
			},{
							text: 'Select pair #'+vm.markers2Count,
							icon: 'http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/ico/Application.ico',
							iconCls: 'Test',
							hideOnSelect: false,
							callback: function() { vm.selectPair()}
						}]
		};
		
		
		if (!vm.markerPairList[vm.markers2Count-1])
		{
			vm.markerPairList.push({});
			vm.markerPairList[vm.markers2Count-1].markerPairId=vm.markers2Count;
		}
		
		vm.markerPairList[vm.markers2Count-1].marker2=newMarker;
		vm.markers2[vm.markers2Count]=newMarker;
	}
	
	// function not used after removal of the marker list
	function highLightMarker(index)
	{
		vm.markers1[index].focus=true;
		vm.markers2[index].focus=true;
		$log.debug(vm.markers1);
		$log.debug(index);
	}
	
	//send markers to the server that store them
	function sendMarker()
	{
		MarkerService.send(vm.markerPairList).then(function successCallback(response) {
				
				vm.markerPairList=response.data;
				for (var i =0; i<vm.markerPairList.length; i++)
				{
					manageMarkerPairItem(i);
				}
				vm.markers1Count=vm.markerPairList.length;
				vm.markers2Count=vm.markerPairList.length;
			},function errorCallback(response) { 
				console.log("error");
				return; 
			});
	}
	
	//remove the markers from the map and delete them from database
	function removeMarker()
	{
		MarkerService.remove().then(function successCallback(response) {
				vm.markerPairList=[];
				vm.markers1={};
				vm.markers2={};
				vm.markers1Count=0;
				vm.markers2Count=0;
			},function errorCallback(response) { 
				console.log("error");
				return; 
		});
	}
	
	//add layer
	function addLayer(mapIndex)
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
				newLayer= {
							name: vm.selectedMapFile.name,
                            type: 'wms',
                            visible: true,
                            url: 'http://localhost:8081/geoserver/gisProject/wms',
                            layerParams: {
                                layers: vm.selectedMapFile.work_space+':'+vm.selectedMapFile.name,
                                format: 'image/png',
                                transparent: true
                            }
			
			};
			
		}
		
		
		if (mapIndex==1)
		{
			vm.layers1.overlays[vm.layerName]=newLayer;
			$rootScope.layer1=newLayer.name;
			$rootScope.firstLayer=newLayer;
			}
		else
		{
			$rootScope.layer2=newLayer.name;
			vm.layers2.overlays[vm.layerName]=newLayer;
			$rootScope.secondLayer=newLayer;
		}
	
	}
	//function to center map in a certain point
	function center(center)
	{
		center.lat=Number(vm.centerLat);
		center.lng=Number(vm.centerLng);
		$log.debug(center);
	
	}
	
	vm.mapFileList;
	vm.selectedMapFile;
	
	//search for the saved map
	function searchMapFile()
	{
		mainService.findAll().then(function successCallback(response) {
			vm.mapFileList=response.data;
			// set session saved layers
			if ($rootScope.firstLayer)
				vm.layers1.overlays[vm.layerName]=$rootScope.firstLayer;
			if ($rootScope.secondLayer)
				vm.layers2.overlays[vm.layerName]=$rootScope.secondLayer;
			
		},function errorCallback(response) { 
			$log.error("Si è verificato un errore");
			$log.debug(response);
			return; 
		});
	}
	// run algorithm and obtain homologous points
	function runAlgorithm()
	{
		MarkerService.send(vm.markerPairList).then(function successCallback(response) {
							MarkerService.runAlgorithm($rootScope.parameters,$rootScope.associationList).then(function successCallback(response) {
								vm.markerPairList=response.data;
								for (var i =0; i<vm.markerPairList.length; i++)
								{
									manageMarkerPairItem(i);
								}
				                vm.markers1Count=vm.markerPairList.length;
								vm.markers2Count=vm.markerPairList.length;
								vm.showResult=true;
							},function errorCallback(response) { 
								console.log("error");	
								return; 
							});			
				},function errorCallback(response) { 
							console.log("error");
							return; 
				});
	}
	
	// manage a pair by adding two markers on the two maps
	function manageMarkerPairItem(i)
	{
					if (vm.markerPairList[i].marker1==undefined || vm.markerPairList[i].marker1 == null 
						|| vm.markerPairList[i].marker2==undefined || vm.markerPairList[i].marker2==null)
						return;
					var test=vm.markerPairList[i].marker1.message;
					vm.markers1[(i+1)]=vm.markerPairList[i].marker1;
					vm.markers2[(i+1)]=vm.markerPairList[i].marker2;
					vm.markers1[(i+1)].focus=false;
					vm.markers1[(i+1)].draggable=true;
					vm.markers2[(i+1)].focus=false;
					vm.markers2[(i+1)].draggable=true;
					
					vm.markers1[(i+1)].icon=vm.customIcon;
					vm.markers2[(i+1)].icon=vm.customIcon;
					
					 vm.markers1[(i+1)].contextmenu=true;
					 vm.markers1[(i+1)].contextmenuWidth=140;
					 
					vm.markers1[(i+1)].contextmenuItems=[{
							text: 'Remove pair #'+(i+1),
							icon: 'http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/ico/Application.ico',
							iconCls: 'Test',
							hideOnSelect: false,
							callback: function() { vm.removeMarker1()}
						},{
							text: 'Select pair #'+(i+1),
							icon: 'http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/ico/Application.ico',
							iconCls: 'Test',
							hideOnSelect: false,
							callback: function() { vm.selectPair()}
						}];
					vm.markers2[(i+1)].contextmenu=true;
					vm.markers2[(i+1)].contextmenuWidth=140;
					vm.markers2[(i+1)].contextmenuItems=[{
							text: 'Remove pair #'+(i+1),
							icon: 'http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/ico/Application.ico',
							iconCls: 'Test',
							hideOnSelect: false,
							callback: function() { vm.removeMarker2()}
						},{
							text: 'Select pair #'+(i+1),
							icon: 'http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/ico/Application.ico',
							iconCls: 'Test',
							hideOnSelect: false,
							callback: function() { vm.selectPair()}
						}];
	}
	
	
	
	//obtain result and redirect to the result view
	function getResult(){
		MarkerService.getResult(vm.markerPairList).then(function successCallback(response) {
				$state.go('result',{resultLayer: response.data});
		},function errorCallback(response) { 
		console.log(response);
			console.log("error");
			return; 
		});
	}
	
	
	searchMapFile();
	
	vm.selectPair=selectPair;
	vm.addMarker1=addMarker1;
	vm.addMarker2=addMarker2;
	vm.highLightMarker=highLightMarker;
	vm.sendMarker=sendMarker;
	vm.addLayer=addLayer;
	vm.center=center;
	vm.searchMapFile=searchMapFile;
	vm.removeMarker1=removeMarker1;
	vm.removeMarker2=removeMarker2;
	vm.removeMarker=removeMarker;
	vm.runAlgorithm=runAlgorithm;
	vm.getResult=getResult;
	
    }
})();
