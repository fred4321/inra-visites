/* * Copyright (C) 2014 thurgi*/
angular.module('EnCourATraiterVM', ['ui.bootstrap', 'ngTable']).
        controller('EnCourATraiterVMCtrl', ['CONST','$rootScope','$scope', '$timeout', 'VmService', 'tableModel', 'ReferentielService', 'AgentService', 'UniteService','UserService',
            function (CONST,$rootScope,$scope, $timeout, VmService, tableModel, ReferentielService, AgentService, UniteService, UserService) {
                $scope.access=( UserService.getProfil() === 'SP' || UserService.getProfil() === 'GU' );
                $scope.filter = {
                    max:CONST.pagination.nbPages[0],
                    offset:0,
                    filter : $rootScope.userFilter.enCourATraiterVM,
                    sort : null,
                    statut: "non affecté"
                };
                var data = [],timeReload;
                $scope.creneauT = new tableModel(data);
                $scope.creneauT.ngTable.$params.filter = $rootScope.userFilter.enCourATraiterVM;
                
                $scope.$watch('filter.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reload,CONST.reloadTimer);
                    }
                },true);

                $scope.getSites = function () {
                    return ReferentielService.getGroupeDefer('site');
                };
                $scope.getUnites = function () {
                    return UniteService.getDefer();
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
                    }, function(){
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

                //liste de choix pour le tableau
                $scope.agents = [];
                $scope.getAgentPrioritiare = function (creneau, i) {
                    AgentService.getPrioritairesVM({unite: creneau.unite}, function (result) {
                        $scope.agents[i] = [];
                        for (var j in result.data) {
                            $scope.agents[i].push(result.data[j]);
                        }
                    });
                };

                ReferentielService.getAll(function (result) {
                    $scope.listeTypeVM = [];
                    for (var i in result.data) {
                        $scope.listeTypeVM[i] = result.data[i];
                    }
                }, null, {groupe: 'natureVM'});

                /**
                 * Affecte toute les lignes pour leur attribué une nature de vm
                 * @param {obj params} value
                 */
                $scope.affectAll = function (value) {
                    for (var i in $scope.creneauT.data) {
                        $scope.creneauT.data[i].natureVM = value;
                    }
                    $scope.creneauT.reload();
                };
                
                $scope.affectAgent = function(agents,id){
                    var i = 0, j = 0, li = agents.length, lj = $scope.listeTypeVM.length;
                    for (i;i<li;i++){
                        if(agents[i].id === id){
                            for(j;j<lj;j++){
                                if($scope.listeTypeVM[j].valeur === agents[i].natureVM){
                                    return $scope.listeTypeVM[j];
                                }
                            }
                            return null;
                        }
                    }
                    return null;
                };
                
                $scope.save = function (creneau,i) {
                    if (creneau.natureVM && creneau.agent) {
                        var post = [{id: creneau.id, agent: creneau.agent.id, natureVM: creneau.natureVM.valeur}];
                        VmService.assignAgentAndNature(post, function () {
                            $scope.agents.splice(i, 1);
                            $scope.creneauT.delete(creneau.id);
                        });
                    } else {
                        creneau.warning = true;
                    }
                };

                $scope.saveAll = function () {
                    if (confirm('Enregistrer l\'ensemble des lignes renseignées')) {
                        var post = [];
                        var idAgent = [];
                        for (var i in $scope.creneauT.ngTable.data) {
                            if ($scope.creneauT.ngTable.data[i].natureVM && $scope.creneauT.ngTable.data[i].agent) {
                                post.push({
                                    id: $scope.creneauT.ngTable.data[i].id,
                                    agent: $scope.creneauT.ngTable.data[i].agent.id,
                                    natureVM: $scope.creneauT.ngTable.data[i].natureVM.valeur
                                });
                                idAgent.push($scope.creneauT.ngTable.data[i].agent.id);
                                if(idAgent.lastIndexOf($scope.creneauT.ngTable.data[i].agent.id)!==idAgent.indexOf($scope.creneauT.ngTable.data[i].agent.id)){
                                    alert('Agent '+$scope.creneauT.ngTable.data[i].agent.nom+' affécté sur deux créneaux');
                                    $scope.creneauT.ngTable.data[i].warning = true;
                                    return;
                                }
                            } 
                        }
                        VmService.assignAgentAndNature(post, function () {
                            $scope.creneauT.deleteGroupe(post);
                        });
                    }
                };

                $scope.delete = function (creneau) {
                    VmService.remove(creneau.id, function () {
                        $scope.creneauT.delete(creneau.id);
                    });
                };
                
                $scope.reload();
            }]);