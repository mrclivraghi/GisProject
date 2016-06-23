
(function() {
  'use strict';

  angular
    .module('gisproject')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController($scope,$rootScope,MarkerService,$log,mainService,$state) {
    var vm = this;
	
	vm.showResult=false;
	vm.markerPairList={};
	vm.selectedPair;
	
	vm.markers1={
		
	};
	vm.markers2={
		
	};
	vm.status = {
    isCustomHeaderOpen: false,
    isFirstOpen: false,
    isFirstDisabled: false
  };
	
	vm.customIcon={
            iconUrl: 'assets/images/end_point.png',
             iconSize:     [15, 15], // size of the icon
           };
	
	function selectPair(e)
	{
	var item=Number($(".leaflet-contextmenu-item").first().text().replace("Remove pair #",""));
	$(".leaflet-contextmenu-item").remove();
	console.log("item:"+item);
	/*delete vm.markers1[(item)];
	console.log(vm.markerPairList);
	delete vm.markerPairList[item-1].marker1;
	if (vm.markerPairList[item-1].marker2==null)
		vm.markerPairList.splice(item-1,1);
	console.log(vm.markers1);
	vm.markers1Count--;*/
	vm.selectedPair=vm.markerPairList[item-1];
	console.log(vm.selectedPair);
	vm.status.open = !vm.status.open
	
	}
	
	
	
	function removeMarker1(e)
	{
	var item=Number($(".leaflet-contextmenu-item").text().replace("Remove pair #",""));
	$(".leaflet-contextmenu-item").remove();
	console.log("item:"+item);
	delete vm.markers1[(item)];
	console.log(vm.markerPairList);
	delete vm.markerPairList[item-1].marker1;
	if (vm.markerPairList[item-1].marker2==null)
		vm.markerPairList.splice(item-1,1);
	console.log(vm.markers1);
	vm.markers1Count--;
	}
	
	function removeMarker2(e)
	{
		var item=Number($(".leaflet-contextmenu-item").text().replace("Remove pair #",""));
	$(".leaflet-contextmenu-item").remove();
	console.log("item:"+item);
	delete vm.markers2[(item)];
	delete vm.markerPairList[item-1].marker2;
	if (vm.markerPairList[item-1].marker1==null)
		vm.markerPairList.splice(item-1,1);
	vm.markers2Count--;
	
	}
	
	function searchMarker()
	{
		MarkerService.search().then(function successCallback(response) {
				vm.markerPairList=response.data;
				for (var i =0; i<vm.markerPairList.length; i++)
				{
				console.log(vm.markerPairList[i].marker1);
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
				vm.markers1Count=vm.markerPairList.length;
				vm.markers2Count=vm.markerPairList.length;
		},function errorCallback(response) { 
			console.log("error");
			return; 
		});
	}
	searchMarker();
	
	vm.defaults1={
		minZoom: 16
		
	};
	console.log(vm.defaults1);
	vm.events1= {
            map: {
                enable: ['drag', 'click','contextmenu.select'],
                logic: 'emit'
            }
        };
	
	
	
	vm.wmsUrl;
	vm.layerName;
	vm.layer;
	
	vm.center1={ lat:45.494384, lng:9.142647, zoom: 15};
	vm.center2={ lat:45.494384, lng:9.142647, zoom: 15};
	vm.centerLat;
	vm.centerLng;
	
	vm.layers1={
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
	
		vm.layers2={
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
$scope.$on('leafletDirectiveMap.map1.click', function(event, args){
	vm.addMarker1(args.leafletEvent.latlng.lat,args.leafletEvent.latlng.lng);
});
$scope.$on('leafletDirectiveMap.map2.click', function(event, args){
	vm.addMarker2(args.leafletEvent.latlng.lat,args.leafletEvent.latlng.lng);
});

$scope.$on('leafletDirectiveMap.map1.contextmenu.select', function(event, args){
	console.log(event);
});
   
	
	
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
        }]
		};
		
		if (!vm.markerPairList[vm.markers1Count-1])
		{
			vm.markerPairList.push({});
			vm.markerPairList[vm.markers1Count-1].markerPairId=vm.markers1Count;
		}
		
		vm.markerPairList[vm.markers1Count-1].marker1=newMarker;
		
		console.log(vm.markerPairList);
		vm.markers1[vm.markers1Count]=newMarker;
	}
	
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
	
	function highLightMarker(index)
	{
		vm.markers1[index].focus=true;
		vm.markers2[index].focus=true;
		$log.debug(vm.markers1);
		$log.debug(index);
	}
	
	function sendMarker()
	{
		MarkerService.send(vm.markerPairList).then(function successCallback(response) {
				
				vm.markerPairList=response.data;
				for (var i =0; i<vm.markerPairList.length; i++)
				{
				console.log(vm.markerPairList[i].marker1);
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
        }];
					 vm.markers2[(i+1)].contextmenu=true;
					 vm.markers2[(i+1)].contextmenuWidth=140;
					vm.markers2[(i+1)].contextmenuItems=[{
           text: 'Remove pair #'+(i+1),
		  icon: 'http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/ico/Application.ico',
		  iconCls: 'Test',
		  hideOnSelect: false,
          callback: function() { vm.removeMarker2()}
        }];
				}
				vm.markers1Count=vm.markerPairList.length;
				vm.markers2Count=vm.markerPairList.length;
				
				
},function errorCallback(response) { 
	console.log("error");
	return; 
});
	
	}
	
	
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
			}
		else
		{
			$rootScope.layer2=newLayer.name;
			vm.layers2.overlays[vm.layerName]=newLayer;
		}
	
	}
	
	function center(center)
	{
		center.lat=Number(vm.centerLat);
		center.lng=Number(vm.centerLng);
		$log.debug(center);
	
	}
	
	vm.mapFileList;
	vm.selectedMapFile;
	
	function searchMapFile()
	{
		mainService.findAll().then(function successCallback(response) {
			vm.mapFileList=response.data;
			
		},function errorCallback(response) { 
			$log.error("Si è verificato un errore");
			$log.debug(response);
			return; 
		});
	}
	
	function runAlgorithm()
	{
		MarkerService.send(vm.markerPairList).then(function successCallback(response) {
				console.log(response);
				
				console.log($rootScope.associationList);
				console.log($rootScope.parameters);
				
							MarkerService.runAlgorithm($rootScope.parameters,$rootScope.associationList).then(function successCallback(response) {
								vm.markerPairList=response.data;
				for (var i =0; i<vm.markerPairList.length; i++)
				{
				console.log(vm.markerPairList[i].marker1);
				var test=vm.markerPairList[i].marker1.message;
					vm.markers1[(i+1)]=vm.markerPairList[i].marker1;
					vm.markers2[(i+1)]=vm.markerPairList[i].marker2;
					vm.markers1[(i+1)].focus=false;
					vm.markers1[(i+1)].draggable=true;
					vm.markers2[(i+1)].focus=false;
					
					vm.markers2[(i+1)].draggable=true;
					 vm.markers1[(i+1)].contextmenu=true;

					 vm.markers1[(i+1)].icon=vm.customIcon;
					vm.markers2[(i+1)].icon=vm.customIcon;
					
					 vm.markers1[(i+1)].contextmenuWidth=140;
					 
					vm.markers1[(i+1)].contextmenuItems=[{
          text: 'Remove pair #'+(i+1),
		  icon: 'http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/ico/Application.ico',
		  iconCls: 'Test',
		  hideOnSelect: false,
          callback: function(test) { vm.removeMarker1(test)}
        }];
					 vm.markers2[(i+1)].contextmenu=true;
					 vm.markers2[(i+1)].contextmenuWidth=140;
					vm.markers2[(i+1)].contextmenuItems=[{
           text: 'Remove pair #'+(i+1),
		  icon: 'http://files.softicons.com/download/toolbar-icons/16x16-free-application-icons-by-aha-soft/ico/Application.ico',
		  iconCls: 'Test',
		  hideOnSelect: false,
          callback: function() { vm.removeMarker2((i+1))}
        }];
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
	
	
	
	function getResult(){
		MarkerService.getResult(vm.markerPairList).then(function successCallback(response) {
				$state.go('result',{resultLayer: response.data});
		},function errorCallback(response) { 
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
