/* * Copyright (C) 2014 thurgi*/

angular.module('inra').factory('ImportService', ['CONST', 'serviceModel',
    function (CONST, serviceModel) {

        var service = new serviceModel(CONST.baseUri + 'import/');
        service.getAllVerif = service.defaultModelWithParam(CONST.baseUri + 'import/getAllVerification', "get");
        service.getAllRapport = service.defaultModelWithParam(CONST.baseUri + 'import/getAllImport', "get");
        service.getVerif = service.defaultModelWithParam(CONST.baseUri + 'import/getVerification' , "get" );
        service.getRapport = service.defaultModelWithParam(CONST.baseUri + 'import/getImport' , "get" );        

        return service;

    }]);
