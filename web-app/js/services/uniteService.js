/* * Copyright (C) 2014 thurgi*/

angular.module('inra').factory('UniteService', ['CONST', 'serviceModel', '$q',
    function (CONST, serviceModel, $q) {

        var service = new serviceModel(CONST.baseUri + 'unite/');

        service.getDefer = function () {
            var unite = [];
            var def = $q.defer();
            this.getAll(function (result) {
                for (var i in result.data) {
                    unite.push(
                            {
                                'id': result.data[i].nom.toString(),
                                'title': result.data[i].nom.toString()
                            });
                }
            }, null);
            def.resolve(unite);
            return def;
        };
        
        return service;
    }]);
