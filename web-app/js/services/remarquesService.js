/* * Copyright (C) 2014 thurgi*/
angular.module('inra').factory('RemarquesService', ['CONST', '$http', 'JsonParse',
    function (CONST, $http, JsonParse) {

        var add = function (url) {
            return function (data, handleSuccess, handleError) {
                var request = $http({
                    method: "post",
                    url: CONST.baseUri + "observation/add" + url,
                    data: data,
                    transformResponse: function (data) {
                        return JsonParse(data);
                    }
                });
                return(request.then(handleSuccess, handleError));
            };
        };

        var getAll = function (url) {
            return function (id
                    , handleSuccess, handleError) {
                var request = $http({
                    method: "get",
                    url: CONST.baseUri + "observation/getAll" + url + id,
                    transformResponse: function (data) {
                        return JsonParse(data);
                    }
                });
                return(request.then(handleSuccess, handleError));
            };
        };

        var remove = function (url) {
            return function (data, handleSuccess, handleError) {
                var request = $http({
                    method: "post",
                    url: CONST.baseUri + "observation/remove" + url ,
                    data : data
                });
                return(request.then(handleSuccess, handleError));
            };
        };

        var update = function () {
            return function (data
                    , handleSuccess, handleError) {
                var request = $http({
                    method: "post",
                    url: CONST.baseUri + "observation/update/",
                    data: data,
                    transformResponse: function (data) {
                        return JsonParse(data);
                    }
                });
                return(request.then(handleSuccess, handleError));
            };
        };


        var service = function(url){
            return {
                add : add(url),
                getAll : getAll(url),
                remove : remove(url),
                update : update()
            };
        };

        return service;

    }]);
