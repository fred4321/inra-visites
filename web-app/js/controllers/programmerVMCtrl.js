/* * Copyright (C) 2014 thurgi*/
angular.module('ProgrammerVM', []).
        controller('ProgrammerVMCtrl', ['$rootScope','$scope', '$timeout', 'CONST', 'InstanceDateVM', 'VmService', 'tableModel', 'ReferentielService','UniteService',
            function ($rootScope,$scope, $timeout, CONST, InstanceDateVM, VmService, tableModel, ReferentielService, UniteService) {
                $scope.CONST = CONST;
                $scope.filter = {
                    max:CONST.pagination.nbPages[0],
                    offset:0,
                    filter : $rootScope.userFilter.programmerVM,
                    sort : null,
                    statut: "initial"
                };
                var data = [], timeReload;
                $scope.creneauT = new tableModel(data);
                $scope.creneauT.ngTable.$params.filter = $rootScope.userFilter.programmerVM;
                $scope.siteDefault = "";
                
                $scope.$watch('filter.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reload,CONST.reloadTimer);
                    }
                },true);
                
                UniteService.getAll(function (result) {
                    $scope.listeUnite = [];
                    for (var i in result.data) {
                        $scope.listeUnite[i] = result.data[i];
                        $scope.listeUnite[i].valeur = $scope.listeUnite[i].nom;
                    }
                });
                
                $scope.getSites = function () {
                    return ReferentielService.getGroupeDefer('site');
                };
                
                $scope.modalDate = new InstanceDateVM();
                $scope.openModalDate = function () {
                    $scope.modalDate.open(function () {
                        $scope.reload();
                    });
                };
                
                $scope.reload = function () {
                    $scope.loading = true;
                    data.splice(0, data.length);
                    $scope.creneauT.ngTable.count($scope.filter.max);
                    $scope.filter.sort = $scope.creneauT.ngTable.sorting();
                    VmService.getAll(function (result) {
                        if (result.data) {
                            $scope.totalItems = result.data.total;
                            CONST.pagination.nbPages.pop();
                            CONST.pagination.nbPages.push(result.data.total);
                            for (var i in result.data.vms) {
                                data.push(result.data.vms[i]);
                            }
                            $scope.creneauT.reload();
                        }
                        $scope.loading = false;
                    },function(){
                        $scope.loading = false;
                    },$scope.filter);
                };
                
                $scope.$watch('creneauT.ngTable.$params', function (newVal, oldVal) {
                    for(var key in newVal.sorting){
                        if(!(oldVal.sorting && oldVal.sorting[key] && oldVal.sorting[key] === newVal.sorting[key])){
                            $scope.reload();
                        }
                    }
                }, true);
                
                $scope.reload();
                
                /**
                 * Enregistrement de l'unité pour le créneau choisi
                 * @param {type} creneau
                 * @param {type} i
                 */
                $scope.save = function (creneau, i) {
                    if (creneau.unite) {
                        var post = [{id: creneau.id, unite: creneau.unite.id}];
                        VmService.assignUnite(post, function () {
                            $scope.creneauT.deleteGroupe(post);
                        });
                    } else {
                        creneau.warning = true;
                    }
                };

                $scope.saveAll = function () {
                    if (confirm('Enregistrer l\'ensemble des lignes renseignées')) {
                        var post = [];
                        for (var i in $scope.creneauT.ngTable.data) {
                            if ($scope.creneauT.ngTable.data[i].unite) {
                                post.push({id: $scope.creneauT.ngTable.data[i].id, unite: $scope.creneauT.ngTable.data[i].unite.id});
                            } else {
                                $scope.creneauT.ngTable.data[i].warning = true;
                            }
                        }
                        VmService.assignUnite(post, function () {
                            $scope.creneauT.deleteGroupe(post);
                        });
                    }
                };

                /**
                 * Suppression d'une VM
                 * @param {type} creneau
                 * @param {type} i
                 */
                $scope.remove = function (creneau, i) {
                    VmService.remove(creneau.id, function () {
                        $scope.creneauT.delete(creneau.id);
                    });
                };
                /**
                 * affecte une valeur par defaut à l'ensemble du tableau
                 * @param {type} value
                 * @returns {undefined}
                 */
                $scope.affectAll = function (value) {
                    for (var i in $scope.creneauT.data) {
                        $scope.creneauT.data[i].unite = value;
                    }
                    $scope.creneauT.ngTable.reload();
                };

            }]);