/* * Copyright (C) 2014 thurgi*/
angular.module('Referentiel', []).
        controller('ReferentielCtrl', ['$scope', 'CONST', 'UniteService', 'ReferentielService', 'ListeEditModel', 'UserService',
            function ($scope, CONST, UniteService, ReferentielService, ListeEditModel, UserService) {
                $scope.version = CONST.version;
                $scope.groupes = CONST.groupes;
                $scope.filterGroupe = {groupe:""};
                $scope.reload = function () {
                    ReferentielService.getAll(function (result) {
                        $scope.sites = result.data;
                    }, null, {groupe: 'site'});

                    ReferentielService.getAll(function (result) {
                        $scope.lieux = result.data;
                    }, null, {groupe: 'lieuVM'});
                };

                $scope.reload();

                $scope.parametre = new ListeEditModel(ReferentielService);

                ReferentielService.getAll(function (result) {
                    $scope.parametre.data = result.data;
                });

                UserService.getGu(function (result) {
                    $scope.gus = result.data;
                });

                $scope.unite = new ListeEditModel(UniteService);
                $scope.unite.remove = function (id, i) {
                    UniteService.removeOBJ($scope.unite, id, function (result, listEdit) {
                        if (result.status === 200) {
                            listEdit.data.splice(i, 1);
                        }
                    }, function (result) {
                        if (result.status === 409) {
                            var txt = 'Erreur : des entités sont encore liès avec cette unité, veuillez les aaffécté à une autre unité avant de la supprimer \r\n';
                            if (result.data.agents.length > 0) {
                                txt = txt + 'Agents Liés: \r\n';
                                for (var i in result.data.agents) {
                                    var txt = txt + 'matricule : ' + result.data.agents[i].matricule + ' - id : ' + result.data.agents[i].id + ' - nom : ' + result.data.agents[i].nom + ' ' + result.data.agents[i].prenom + ' \r\n';
                                }
                            }
                            if (result.data.users.length > 0) {
                                txt = txt + 'Utilisateurs Liés: \r\n';
                                for (var i in result.data.users) {
                                    var txt = txt = txt + 'id : ' + result.data.users[i].id + ' - identifiant : ' + result.data.users[i].identifiant + ' - profil : ' +result.data.users[i].profil + ' \r\n';
                                }
                            }
                        }
                        alert(txt);
                    });
                };
                
                UniteService.getAll(function (result) {
                    $scope.unite.data = result.data;
                });

                $scope.selectGroupe = function (param) {
                    param.infoValeur = "";
                    param.infoParam1 = "";
                    param.infoParam2 = "";
                    param.infoParam3 = "";
                    param.infoParam4 = "";
                    switch (param.groupe) {
                        case "site":
                            param.infoParam1 = "Saisissez l'id d'un lieu";
                            break;
                        case "periodicite":
                            param.infoValeur = "x mois/an(s)";
                            break;
                        case "créneau BS":
                            param.infoValeur = "format : 07H08 - 09H05";
                            param.infoParam1 = "nb min entre 2 passages";
                            break;
                        case "créneau VM":
                            param.infoValeur = "format : 07H08 - 09H05 - 14H35 - 16H56";
                            param.infoParam1 = "nb min entre 2 passages";
                            param.infoParam2 = "1 pour valeur par defaut";
                            break;
                        case "délai VM":
                            param.infoValeur = "format : 10 (exprimé en minutes)";
                    }
                };

            }]);