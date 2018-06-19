/* * Copyright (C) 2014 thurgi*/
angular.module('Droit', []).
        controller('DroitCtrl', ['$scope', 'CONST', 'UserService', 'ListeEditModel', 'UniteService',
            function ($scope, CONST, UserService, ListeEditModel, UniteService) {
                $scope.version = CONST.version;
                $scope.profils = CONST.profils;

                $scope.utilisateurs = new ListeEditModel(UserService);

                UniteService.getAll(function (result) {
                    $scope.unites = [];
                    for (var i in result.data) {
                        $scope.unites.push(result.data[i].nom);
                    }
                });
                
                $scope.reload = function () {
                    UserService.getAll(function (result) {
                        $scope.utilisateurs.data = result.data;
                    });
                };
                
                $scope.reload();
            }]);