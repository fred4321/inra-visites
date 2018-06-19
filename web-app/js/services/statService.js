/* * Copyright (C) 2014 thurgi*/
angular.module('inra').factory('StatService', ['CONST', 'serviceModel',
    function (CONST, serviceModel) {

        var service = new serviceModel(CONST.baseUri + 'agent/');

        service.visiteParSite = service.defaultModel(CONST.baseUri + 'stats/visiteParSite/', "get");
        service.visiteParNature = service.defaultModel(CONST.baseUri + 'stats/visiteParNature/', "get");
        service.tableaux = service.defaultModel(CONST.baseUri + 'stats/tableaux/', "get");

        return service;
    }]);
