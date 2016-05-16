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

this.search = function() {

var promise= $http.get("http://127.0.0.1:8080/GisProjectServer/map");
return promise; 
};


}
})();
