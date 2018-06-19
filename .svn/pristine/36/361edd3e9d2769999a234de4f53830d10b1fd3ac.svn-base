/* * Copyright (C) 2014 thurgi*/
angular.module('inra').factory('UserService', ['CONST', 'serviceModel',
    function (CONST, serviceModel) {

        var url = CONST.baseUri + 'user/';
        var profil;
        var identifiant;

        var service = new serviceModel(url);

        service.login = service.defaultModelWithData(url + "login", "post");

        service.logout = service.defaultModel(url + "logout", "post");

        service.isLogin = service.defaultModel(url + "isLogin", "get");

        service.getGu = service.defaultModel(url + "getGu", "get");

        service.setPassword = service.defaultModelWithData(url + "setPassword", "post");

        service.isLoggedIn = function () {
            return (identifiant && profil) ? {user: identifiant, profil: profil} : false;
        };

        service.setIdentifiant = function (aIdentifiant, aProfil) {
            identifiant = aIdentifiant;
            profil = aProfil;
        };

        service.getProfil = function () {
            return profil;
        };

        service.getIdentifiant = function () {
            return identifiant;
        };

        return service;
    }]);
