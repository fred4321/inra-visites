/* * Copyright (C) 2014 thurgi*/
angular.module('BS', ['ui.bootstrap'])
        .controller('BSCtrl', ['$scope', '$modalInstance', 'items', 'BsService', 'ReferentielService','UserService','RemarquesService',
            function ($scope, $modalInstance, items, BsService, ReferentielService,UserService,RemarquesService) {
                $scope.access = UserService.getProfil() ==='medecin';
                $scope.typeBS = [];
                $scope.typeBsSelected = [];

                $scope.reload = function () {
                    BsService.get(items, function (result) {
                        for (var key in $scope.typeBsSelected) {
                            $scope.typeBsSelected[key] = false;
                        }
                        $scope.bs = result.data;
                        var types = result.data.type.split("_");
                        for (var i in types) {
                            if ($scope.typeBS.indexOf(types[i]) !== -1) {
                                $scope.typeBsSelected[types[i]] = true;
                            }
                        }
                    });
                };

                ReferentielService.getAll(function (result) {
                    for (var i in result.data) {
                        $scope.typeBS.push(result.data[i].valeur);
                    }
                    $scope.reload();
                }, null, {groupe: 'typeBS'});
                //fermeture
                $scope.ok = function () {
                    //enregistrement des observations en cours d'édition
                    var serviceRemarque = RemarquesService('Remarque');
                    for (var i in $scope.bs.remarques){
                        if ($scope.bs.remarques[i].edit){
                            serviceRemarque.update({id: $scope.bs.remarques[i].id, text: $scope.bs.remarques[i].text});
                        }
                    }
                    //parse du type de bs
                    var types = "";
                    for (var key in $scope.typeBsSelected) {
                        types = types + ($scope.typeBsSelected[key] ? "_" + key : "");
                    }
                    types = types.substring(1);
                    BsService.update({id: items, type: types}, function (result) {
                        $modalInstance.close(result.data);
                        //$scope.addError('success', 'Enregistrement réussi');
                    }, function () {
                        $scope.addError('danger', 'Erreur(s) serveur');
                    });
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss();
                };
                // alerts
                $scope.alerts = [];
                $scope.addError = function (t, txt) {
                    $scope.alerts.splice(0, $scope.alerts.length);
                    $scope.alerts.push({type: t, msg: txt});
                };
                $scope.closeAlert = function (index) {
                    $scope.alerts.splice(index, 1);
                };

            }]).
        factory('InstanceBS', ['$modal', function ($modal) {
                return function () {
                    this.params = {
                        templateUrl: 'partials/modal/bs.html',
                        controller: 'BSCtrl',
                        size: 'lg',
                        backdrop: false
                    };
                    this.open = function (action) {
                        var vm = $modal.open(this.params);
                        vm.result.then(function (selectedItem) {
                            action(selectedItem);
                        }, function () {
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