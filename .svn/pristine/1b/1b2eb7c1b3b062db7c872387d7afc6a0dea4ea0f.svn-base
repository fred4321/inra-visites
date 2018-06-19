/* * Copyright (C) 2014 thurgi*/
angular.module('inra').factory('AgentService', ['CONST', 'serviceModel',
    function (CONST, serviceModel) {

        var service = new serviceModel(CONST.baseUri + 'agent/');
        
        service.archivage = service.defaultModelWithData( CONST.baseUri + 'agent/archiver/' , "post");
        
        service.getPrioritairesVM = service.defaultModelWithParam(CONST.baseUri + 'agent/getAllPrioritaireVM/' , "get" );
        
        service.getAllConvoquerVM = service.defaultModel(CONST.baseUri + 'agent/getAllConvoquerVM/' , "get" );
        
        service.getAllAConvoquer = service.defaultModel(CONST.baseUri + 'agent/getAllAConvoquer/' , "get" );
        
        service.affectePrioritaire = service.defaultModel(CONST.baseUri + 'agent/affectePrioritaire/' , "get" );
        
        service.getAllAConvoquer = service.defaultModel(CONST.baseUri + 'agent/getAllAConvoquer/' , "get" );

        return service;
    }]);
