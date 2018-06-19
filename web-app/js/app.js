/* 
 * Copyright (C) 2014 thurgi
 */
'use strict';
// Declare app level module which depends on filters, and services
angular.module('inra', [
    'ngRoute',
    'ngAnimate',
    'ui.bootstrap',
    'ui.calendar',
    'ngTable',
    'ngTableExport',
    'angularFileUpload',
    'Configuration',
    'Head',
    'Menu',
    'Login',
    'Acceuil',
    'Convoque',
    'Tous',
    'Agent',
    'BS',
    'ProgrammerVM',
    'EnCourATraiterVM',
    'AttribueVM',
    'EffectueVM',
    'dateVM',
    'dateBS',
    'Nouveau',
    'Archivage',
    'Archive',
    'PrioritaireBS',
    'ProgrammerBS',
    'ConvoquerBS',
    'EffectuerBS',
    'Import',
    'Referentiel',
    'Droit',
    'Journaux',
    'Stat',
    'password'
]).config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/accueil', {templateUrl: 'partials/acceuil.html', controller: 'AcceuilCtrl'});
        $routeProvider.when('/convoque', {templateUrl: 'partials/convoque.html', controller: 'ConvoqueCtrl'});
        $routeProvider.when('/tous', {templateUrl: 'partials/tous.html', controller: 'TousCtrl'});
        $routeProvider.when('/programmerVM', {templateUrl: 'partials/programmerVM.html', controller: 'ProgrammerVMCtrl'});
        $routeProvider.when('/enCourATraiterVM', {templateUrl: 'partials/enCourATraiterVM.html', controller: 'EnCourATraiterVMCtrl'});
        $routeProvider.when('/attribueVM', {templateUrl: 'partials/attribueVM.html', controller: 'AttribueVMCtrl'});
        $routeProvider.when('/effectueVM', {templateUrl: 'partials/effectueVM.html', controller: 'EffectueVMCtrl'});
        $routeProvider.when('/nouveau', {templateUrl: 'partials/nouveau.html', controller: 'NouveauCtrl'});
        $routeProvider.when('/archivage', {templateUrl: 'partials/archivage.html', controller: 'ArchivageCtrl'});
        $routeProvider.when('/archive', {templateUrl: 'partials/archive.html', controller: 'ArchiveCtrl'});
        $routeProvider.when('/prioritaireBS', {templateUrl: 'partials/prioritaireBS.html', controller: 'PrioritaireBSCtrl'});
        $routeProvider.when('/programmerBS', {templateUrl: 'partials/programmerBS.html', controller: 'ProgrammerBSCtrl'});
        $routeProvider.when('/convoquerBS', {templateUrl: 'partials/convoquerBS.html', controller: 'ConvoquerBSCtrl'});
        $routeProvider.when('/effectuerBS', {templateUrl: 'partials/effectuerBS.html', controller: 'EffectuerBSCtrl'});
        $routeProvider.when('/import', {templateUrl: 'partials/import.html', controller: 'ImportCtrl'});
        $routeProvider.when('/referentiel', {templateUrl: 'partials/referentiel.html', controller: 'ReferentielCtrl'});
        $routeProvider.when('/droit', {templateUrl: 'partials/droit.html', controller: 'DroitCtrl'});
        $routeProvider.when('/journaux', {templateUrl: 'partials/journaux.html', controller: 'JournauxCtrl'});
        $routeProvider.when('/login', {templateUrl: 'partials/login.html', controller: 'LoginCtrl'});
        $routeProvider.when('/stat', {templateUrl: 'partials/stat.html', controller: 'StatCtrl'});
        $routeProvider.otherwise({redirectTo: '/accueil'});
    }]).run(['$rootScope', '$location', 'UserService', 'AgentService', 'FilterModel',
    function ($rootScope, $location, UserService, AgentService, FilterModel) {
        $rootScope.path = $location.path();
        if (!UserService.isLoggedIn()) {
            $location.path('/login');
        }
        $rootScope.userFilter = FilterModel;
        //test si les agent doivent passé en prioritaire et les affecte en prioritaire si oui
        AgentService.affectePrioritaire();
    }]);
