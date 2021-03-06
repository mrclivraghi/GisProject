(function() { 

angular
.module("gisproject")
.service("mainService", MainService);
/** @ngInject */
function MainService($http)
{
		this.loadFile= function(file,field){
			var formData = new FormData();
			if (file!=null)
				formData.append('file',file);
			var promise= $http.post("http://127.0.0.1:8080/GisProjectServer/mapFile/load"+field+"/",formData,{
				headers: {'Content-Type': undefined}
			});
			return promise; 
		}
		
		this.findAll= function(){
			var promise= $http.post("http://127.0.0.1:8080/GisProjectServer/mapFile/search",{});
			return promise; 
		}
		
		this.getStats= function(){
			var promise= $http.post("http://127.0.0.1:8080/GisProjectServer/algorithm/statistics");
			return promise; 
		}
		
		this.getGeoJSON= function(layerName){
		
		var promise=$http.get("http://localhost:8081/geoserver/gisProject/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=gisProject:"+layerName+"&maxFeatures=100&outputFormat=application/json");
		return promise;
		
		}
		
		this.getPropertyList= function(layerName){
			var promise= $http.get("http://127.0.0.1:8080/GisProjectServer/configuration?layerName="+layerName);
			return promise; 
		}
}
})();
