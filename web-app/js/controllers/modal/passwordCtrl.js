/* * Copyright (C) 2014 thurgi*/
angular.module('password', [])
        .controller('PasswordCtrl', ['$scope', '$modalInstance', 'UserService',
            function ($scope, $modalInstance, UserService) {

                //propriété des alert et methode de fermeture
                $scope.alerts = [];
                $scope.closeAlert = function (index) {
                    $scope.alerts.splice(index, 1);
                };
                $scope.removeAllAlert = function () {
                    $scope.alerts.splice(0, $scope.alerts.length);
                };
                //fermeture
                $scope.ok = function (hold, new1, new2, form) {
                    if (form.$valid && new1 === new2) {
                        UserService.setPassword({
                            hold: hold,
                            new1: new1,
                            new2: new2
                        }, function (result) {
                            $scope.removeAllAlert();
                            if (result.status) {
                                $scope.alerts.push({type: 'info', msg: 'Mot de passe modifié avec succès'});
                            }
                        }, function (result) {
                            $scope.removeAllAlert();
                            switch (result.status) {
                                case 500:
                                    $scope.alerts.push({type: 'danger', msg: 'Erreur(s) serveur'});
                                    break;
                                case 409:
                                    $scope.alerts.push({type: 'danger', msg: 'Ancien mot de passe incorrect'});
                                    break;
                                case 412:
                                    $scope.alerts.push({type: 'danger', msg: 'Nouveaux mots de passe non identiques'});
                                    break;
                                case 415:
                                    $scope.alerts.push({type: 'danger', msg: 'Champ(s) vide(s)'});
                                    break;
                            }
                        });
                    }
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss();
                };
            }]).
        factory('InstancePassword', ['$modal', function ($modal) {
                return function () {
                    this.params = {
                        templateUrl: 'partials/modal/password.html',
                        controller: 'PasswordCtrl',
                        size: 'sm',
                        backdrop: false
                    };
                    this.open = function (action) {
                        var agent = $modal.open(this.params);
                        agent.result.then(function (selectedItem) {
                            action(selectedItem);
                        }, function () {
                            //console.log('dissim agent');
                        });
                    };
                    this.setItem = function (item) {
                        this.params.resolve = {
                            items: function () {
                                return item;
                            }
                        };
                    };
                };
            }]);