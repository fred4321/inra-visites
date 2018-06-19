/* * Copyright (C) 2014 thurgi*/

angular.module('Archivage', ['Agent']).
        controller('ArchivageCtrl', ['$rootScope', '$scope', '$timeout', 'CONST', 'AgentService', 'InstanceAgent', 'tableModel', 'ReferentielService', 'UniteService',
            function ($rootScope, $scope, $timeout, CONST, AgentService, InstanceAgent, tableModel, ReferentielService, UniteService) {
                $scope.CONST = CONST;
                $scope.filter = {
                    max:CONST.pagination.nbPages[0],
                    offset:0,
                    filter : $rootScope.userFilter.archivage,
                    sort : null,
                    archivage : true,
                    archive : false
                };
                var data = [], timeReload;
                
                $scope.$watch('filter.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reload,CONST.reloadTimer);
                    }
                },true);
                
                $scope.agentT = new tableModel(data);
                $scope.agentT.ngTable.$params.filter = $rootScope.userFilter.archivage;
                
                $scope.getSites = function () {
                    return ReferentielService.getGroupeDefer('site');
                };
                $scope.getUnites = function () {
                    return UniteService.getDefer();
                };

                $scope.agent = new InstanceAgent();
                $scope.openAgent = function (agent) {
                    $scope.agent.setItem(agent);
                    $scope.agent.open(function (value) {
                        data.splice(0, data.length);
                        var t = AgentService.bouchonGetAll();
                        for (var i in t) {
                            data.push(t[i]);
                        }
                        $scope.agentT.ngTable.reload();
                    });
                };

                $scope.reload = function () {
                    $scope.loading = true;
                    data.splice(0, data.length);
                    $scope.agentT.ngTable.count($scope.filter.max);
                    $scope.filter.sort = $scope.agentT.ngTable.sorting();
                    AgentService.getAll(function (result) {
                        if (result.data) {
                            $scope.totalItems = result.data.total;
                            CONST.pagination.nbPages.pop();
                            CONST.pagination.nbPages.push(result.data.total);
                            for (var i in result.data.agents) {
                                data.push(result.data.agents[i]);
                            }
                            $scope.agentT.reload();
                        }
                        $scope.loading = false;
                    }, function(){
                        $scope.loading = false;
                    },$scope.filter);
                };
                
                $scope.$watch('agentT.ngTable.$params', function (newVal, oldVal) {
                    for(var key in newVal.sorting){
                        if(!(oldVal.sorting && oldVal.sorting[key] && oldVal.sorting[key] === newVal.sorting[key])){
                            $scope.reload();
                        }
                    }
                }, true);
                
                
                $scope.reload();

                $scope.archiverTout = function () {
                    if (confirm('Archiver tous les agents affichés')) {
                        var archivage = [];
                        for (var i in $scope.agentT.ngTable.data) {
                            archivage.push($scope.agentT.ngTable.data[i].id);
                        }
                        AgentService.archivage(archivage, function () {
                            for (var j in $scope.agentT.ngTable.data) {
                                for (var i in data) {
                                    if (data[i].id === $scope.agentT.ngTable.data[j].id) {
                                        data.splice(i, 1);
                                    }
                                }
                            }
                            $scope.agentT.reload();
                        });
                    }
                };

                $scope.archiver = function (agent) {
                    AgentService.archivage([agent.id], function () {
                        for (var i in data) {
                            if (data[i].id === agent.id) {
                                data.splice(i, 1);
                                $scope.agentT.ngTable.reload();
                                return;
                            }
                        }
                    });
                };
            }]);