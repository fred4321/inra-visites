/* * Copyright (C) 2014 thurgi*/

angular.module('Nouveau', ['formINRA']).
        controller('NouveauCtrl', ['$scope', 'CONST', 'AgentService',
            function ($scope, CONST, AgentService) {
                $scope.CONST = CONST;
                $scope.edit;
                $scope.validate;
                $scope.error;
                $scope.agent={
                    dateNaissance : new Date(new Date().getFullYear()-35, 0,1),
                    dateEntree : new Date(new Date().getFullYear()-1, 0,1)
                };
                $scope.submit = function () {
                    var form = $scope.validate(); //formulaire correct
                    if (form) {
                        AgentService.add(form, function (result) {
                            if (result.status === 200) {
                                $scope.error('info', 'Enregistrement réussi');
                            } else if (result.status === 207 ) {
                                $scope.error('danger', ' erreur : Numéro de matricule déjà existant ');
                            } else {
                                $scope.error('danger', result.statusText);
                            }
                        }, function (echec) {
                            $scope.error('danger', "Echec du serveur : " + echec.statusText);
                        });
                    }
                };
                $scope.print=function(){
                    window.print();
                };
            }]);