/* * Copyright (C) 2014 thurgi*/
angular.module('inra').factory('serviceModel', ['$http', 'JsonParse', '$location',
    function ($http, JsonParse, $location) {
        return  function (url) {

            function yadfs(handle) {
                return function (error) {
                    if(error.status === 401){
                        $location.path('/login');
                        return;
                    }
                    if(handle && typeof(handle) === 'function'){
                        handle(error);
                    }
                };
            }

            this.add = function (data, handleSuccess, handleError) {
                var request = $http({
                    method: "post",
                    url: url + "post",
                    data: data,
                    transformResponse: function (data) {
                        return JsonParse(data);
                    }
                });
                return(request.then(handleSuccess, yadfs(handleError)));
            };

            this.addOBJ = function (obj, params, handleSuccess, handleError) {
                var request = $http({
                    method: "post",
                    url: url + "post",
                    data: params,
                    transformResponse: function (data) {
                        return JsonParse(data);
                    }
                });
                request.then(function (result) {
                    handleSuccess(result, obj);
                }, yadfs(handleError));
            };

            this.get = function (id, handleSuccess, handleError) {
                var request = $http({
                    method: "get",
                    url: url + "get/" + id,
                    transformResponse: function (data) {
                        return JsonParse(data);
                    }
                });
                return(request.then(handleSuccess, yadfs(handleError)));
            };

            this.getAll = function (handleSuccess, handleError, params) {
                var request = $http({
                    method: "get",
                    url: url + "getAll",
                    params: params,
                    transformResponse: function (data) {
                        return JsonParse(data);
                    }
                });
                return(request.then(handleSuccess, yadfs(handleError)));
            };

            this.getAllOBJ = function (obj, handleSuccess, handleError) {
                var request = $http({
                    method: "get",
                    url: url + "getAll",
                    transformResponse: function (data) {
                        return JsonParse(data);
                    }
                });
                return(request.then(function (result) { handleSuccess(result, obj);}, yadfs(handleError)));
            };

            this.remove = function (id, handleSuccess, handleError) {
                var request = $http({
                    method: "post",
                    url: url + "delete/" + id,
                    params: {
                        action: "delete"
                    }
                });
                return(request.then(handleSuccess, yadfs(handleError)));
            };

            this.removeOBJ = function (obj, id, handleSuccess, handleError) {
                var request = $http({
                    method: "post",
                    url: url + "delete/" + id,
                    params: {
                        action: "delete"
                    }
                });
                return(request.then(function (result) {handleSuccess(result, obj);}, yadfs(handleError)));
            };

            this.update = function (data, handleSuccess, handleError) {
                var request = $http({
                    method: "post",
                    url: url + "update/",
                    data: data,
                    transformResponse: function (data) {
                        return JsonParse(data);
                    }
                });
                return(request.then(handleSuccess, yadfs(handleError)));
            };

            this.updateOBJ = function (obj, data, handleSuccess, handleError) {
                var request = $http({
                    method: "post",
                    url: url + "update/" + data.id,
                    data: data,
                    transformResponse: function (data) {
                        return JsonParse(data);
                    }
                });
                return(request.then(function (result) {handleSuccess(result, obj);}, yadfs(handleError)));
            };

            this.defaultModelWithData = function (url, methode) {
                return function (data, handleSuccess, handleError) {
                    var request = $http({
                        method: methode,
                        url: url,
                        data: data,
                        transformResponse: function (data) {
                            return JsonParse(data);
                        }
                    });
                    return(request.then(handleSuccess, yadfs(handleError)));
                };
            };

            this.defaultModelWithParam = function (url, methode) {
                return function (data, handleSuccess, handleError) {
                    var request = $http({
                        method: methode,
                        url: url,
                        params: data,
                        transformResponse: function (data) {
                            return JsonParse(data);
                        }
                    });
                    return(request.then(handleSuccess, yadfs(handleError)));
                };
            };

            this.defaultModel = function (url, methode) {
                return function (handleSuccess, handleError) {
                    var request = $http({
                        method: methode,
                        url: url,
                        transformResponse: function (data) {
                            return JsonParse(data);
                        }
                    });
                    return(request.then(handleSuccess, yadfs(handleError)));
                };
            };

        };
    }]);