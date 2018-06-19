/* * Copyright (C) 2014 thurgi*/
angular.module('dateBS', [])
        .controller('DateBSCtrl', ['$scope', '$filter', '$modalInstance', 'Calendrier', 'ReferentielService', 'BsService',
            function ($scope, $filter, $modalInstance, Calendrier, ReferentielService, BsService) {
                $scope.date = {
                    periode: 'false',
                    value: new Date(),
                    debut: new Date(),
                    fin: new Date()
                };
                //propriété des alert et methode de fermeture
                $scope.alerts = [];
                $scope.closeAlert = function (index) {
                    $scope.alerts.splice(index, 1);
                };
                //fermeture
                $scope.ok = function (form) {
                    if (form.$valid) {
                        var post = {
                            date: $scope.date,
                            creneau: form.creneau.$modelValue,
                            site: form.site.$modelValue,
                            lieu: form.lieu.$modelValue
                        };
                        BsService.addDate(post, function (result) {
                            if (result.status === 200) {
                                $modalInstance.close(result.status === 200);
                            }
                            $scope.alerts.splice(0, $scope.alerts.length);
                            $scope.alerts.push({type: 'danger', msg: 'Aucun Bilan sanguin en attente sur le site sélectionné'});
                        }, function (result) {
                            $scope.alerts.splice(0, $scope.alerts.length);
                            $scope.alerts.push({type: 'danger', msg: 'Erreur(s) serveur code:'+result.status});
                        });
                    }
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss();
                };

                ReferentielService.getAll(function (result) {
                    $scope.creneaux = result.data;
                }, null, {groupe: 'créneau BS'});

                ReferentielService.getAll(function (result) {
                    $scope.sites = result.data;
                }, null, {groupe: 'site'});
                
                ReferentielService.getAll(function (result) {
                    $scope.lieux = result.data;
                }, null, {groupe: 'lieuBS'});
                
                $scope.calendrierDate = new Calendrier();
                $scope.calendrierDebut = new Calendrier();
                $scope.calendrierFin = new Calendrier();

            }]).
        factory('InstanceDateBS', ['$modal', function ($modal) {
                return function () {
                    this.params = {
                        templateUrl: 'partials/modal/dateBS.html',
                        controller: 'DateBSCtrl',
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