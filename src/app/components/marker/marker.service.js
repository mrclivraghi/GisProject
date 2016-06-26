(function() { 

angular
.module("gisproject")
.service("MarkerService", MarkerService);
/** @ngInject */
function MarkerService($http,$rootScope)
{


this.send = function(markerObject) {
var markerArray = Object.keys(markerObject).map(function (key) {return markerObject[key]});
var promise= $http.post("http://127.0.0.1:8080/GisProjectServer/map?layer1=OSM_00_4326&layer2=DBT_00_4326",markerArray);
return promise; 
};

this.remove = function() {
var promise= $http["delete"]("http://127.0.0.1:8080/GisProjectServer/map");
return promise; 
};


this.search = function() {

var promise= $http.get("http://127.0.0.1:8080/GisProjectServer/map");
return promise; 
};

this.runAlgorithm= function(parameters,associationList){
	var data={};

	if (parameters!=undefined)
		data.parameters=Object.keys(parameters).map(function (key) {return parameters[key]});
	else
		data.parameters=[];
	
	data.parameters=parameters;


	if (associationList!=undefined)
		data.associationList=Object.keys(associationList).map(function (key) {return associationList[key]});
	else
		data.associationList=[];
	
	if ($rootScope.layer1)
		data.layer1=$rootScope.layer1;
	
	if ($rootScope.layer2)
		data.layer1=$rootScope.layer2;
		
	
		
	
	var promise= $http.post("http://127.0.0.1:8080/GisProjectServer/algorithm",data);
	return promise; 

}

this.getResult= function(markerList){
var markerArray = Object.keys(markerList).map(function (key) {return markerList[key]});
var promise= $http.post("http://127.0.0.1:8080/GisProjectServer/algorithm/result",markerArray);
return promise; 

}

}
})();
