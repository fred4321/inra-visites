/* * Copyright (C) 2014 thurgi*/
angular.module('inra').factory('VmService', ['CONST', 'serviceModel', '$http', 'JsonParse',
    function (CONST, serviceModel, $http, JsonParse) {

        var url = CONST.baseUri + 'VM/';
        var service = new serviceModel(url);

        service.courriel = function (action, data, handleSuccess, handleError) {
            var request = $http({
                method: "post",
                url: url + action + "/",
                data: data,
                transformResponse: function (data) {
                    return JsonParse(data);
                }
            });
            return(request.then(handleSuccess, handleError));
        };
        
        service.convoquer = service.defaultModelWithData(url + "convoquer/" , "post");
        service.assignUnite = service.defaultModelWithData(url + "assignUnite/" , "post");
        service.assignAgentAndNature = service.defaultModelWithData(url + "assignAgentAndNature/" , "post");
        service.absent = service.defaultModelWithData(url + "absent/" , "post");

        return service;

    }]);
