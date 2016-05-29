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

}
})();
