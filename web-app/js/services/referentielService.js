/* * Copyright (C) 2014 thurgi*/
angular.module('inra').factory('ReferentielService', ['$q', 'CONST', 'serviceModel', '$http',
    function ($q, CONST, serviceModel, $http) {

        var service = new serviceModel( CONST.baseUri + 'params/');

        service.getGroupeDefer = function (type) {
            var groupe = [];
            var def = $q.defer();
            this.getAll(function (result) {
                for (var i in result.data) {
                    groupe.push(
                            {
                                'id': result.data[i].valeur.toString(),
                                'title': result.data[i].valeur.toString()
                            });
                }
            }, null, {groupe: type});
            def.resolve(groupe);
            return def;
        };

        service.getLibEmploi = service.defaultModel(CONST.baseUri + 'params/getLibEmploi/', "get");
        service.tableauPdf = function (data) {
            $http({
                method: 'POST',
                url: CONST.baseUri + 'pdf/tableau',
                data: data,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            });
        };

        return service;

    }]);
