/* * Copyright (C) 2014 thurgi*/
angular.module('inra').factory('BsService', ['CONST', 'serviceModel',
    function (CONST, serviceModel) {

        var url = CONST.baseUri + 'BS/';

        var service = new serviceModel(url);

        service.effectuer = service.defaultModelWithData(url + "effectuer/", "post");
        service.convoquer = service.defaultModelWithData(url + "convoquer/", "post");
        service.absent = service.defaultModelWithData(url + "absent/", "post");
        service.courriel = service.defaultModelWithData(url + "courriel/", "post");
        service.addDate = service.defaultModelWithData(url + "addDate/", "post");
        service.initialiser = service.defaultModelWithData(url + "initialiser/" , "post");
        service.getCsv = service.defaultModelWithParam( url + "listeExamenCvs", "get");

        return service;
    }]);
