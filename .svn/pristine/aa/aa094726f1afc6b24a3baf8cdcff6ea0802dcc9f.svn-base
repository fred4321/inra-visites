/* * Copyright (C) 2014 thurgi*/
angular.module('inra').factory('JournauService', ['CONST', 'serviceModel', '$q',
    function (CONST, serviceModel, $q) {


        var service = new serviceModel(CONST.baseUri + 'journal/');
        service.getTypes = service.defaultModelWithParam(CONST.baseUri + 'journal/getTypes/', "get");

        service.getTypeDefer = function (type) {
            var types = [];
            var def = $q.defer();
            this.getTypes({type: type}, function (result) {
                if (result.status === 200 && result.data) {
                    for (var i in result.data) {
                        if (result.data[i]) {
                            types.push(
                                    {
                                        'id': result.data[i],
                                        'title': result.data[i]
                                    }
                            );
                        }
                    }
                }
            }, null);
            def.resolve(types);
            return def;
        };

        return service;
    }]);
