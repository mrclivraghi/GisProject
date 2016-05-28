(function() { 

angular
.module("gisproject")
.service("MarkerService", MarkerService);
/** @ngInject */
function MarkerService($http)
{


this.send = function(markerObject) {
var markerArray = Object.keys(markerObject).map(function (key) {return markerObject[key]});
var promise= $http.post("http://127.0.0.1:8080/GisProjectServer/map",markerArray);
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
	
console.log("data");
console.log(data);

	var promise= $http.post("http://127.0.0.1:8080/GisProjectServer/algorithm",data);
return promise; 

}



}
})();
