/* * Copyright (C) 2014 thurgi*/
angular.module('Login', []).
        controller('LoginCtrl', ['$rootScope','$scope', 'UserService', '$location',
            function ($rootScope,$scope, UserService, $location) {
                UserService.setIdentifiant();
                
                $scope.errors = [];
                $scope.closeError = function (index) {
                    $scope.errors.splice(index, 1);
                };
                
                $scope.viewPage = false;

                UserService.isLogin(function (result) {
                    if (result.data.identifiant && result.data.profil) {
                        UserService.setIdentifiant(result.data.identifiant, result.data.profil);
                        $location.path($rootScope.path);
                    }else{
                        $scope.viewPage = true;
                    }
                }, function () {
                    console.log("erreur serveur");
                });

                $scope.login = function () {
                    var data = {
                        identifiant: $scope.nom,
                        password: $scope.pass
                    };
                    UserService.login(data, function (result) {
                        if (result.status === 204) {
                            $scope.errors.splice(0, $scope.errors.length);
                            $scope.errors.push({msg: 'Utilisateur inconnu'});
                        } else if (result.status === 203) {
                            $scope.errors.splice(0, $scope.errors.length);
                            $scope.errors.push({msg: 'Mot de passe incorrect'});
                        } else if (result.status === 200) {
                            UserService.setIdentifiant(result.data.identifiant, result.data.profil);
                            $location.path('/accueil');
                        } else {
                            $scope.errors.splice(0, $scope.errors.length);
                            $scope.errors.push({msg: 'Erreur de connexion'});
                        }
                    }, function () {
                        $scope.errors.splice(0, $scope.errors.length);
                        $scope.errors.push({msg: 'Sevice Indisponible. Veuillez essayer ultérieurement'});
                    });
                };

            }]);