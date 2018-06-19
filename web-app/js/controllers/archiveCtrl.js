/* * Copyright (C) 2014 thurgi*/

angular.module('Archive', []).
        controller('ArchiveCtrl', ['$rootScope','$scope', '$timeout', 'CONST', 'AgentService', 'InstanceAgent', 'tableModel', 'ReferentielService','UniteService',
            function($rootScope,$scope, $timeout, CONST, AgentService, InstanceAgent, tableModel, ReferentielService,UniteService) {
                $scope.CONST = CONST;
                $scope.filter = {
                    max:CONST.pagination.nbPages[0],
                    offset:0,
                    filter : $rootScope.userFilter.archive,
                    sort : null,
                    archive : true
                };
                var data = [], timeReload;
                $scope.agentT = new tableModel(data);
                $scope.agentT.ngTable.$params.filter = $rootScope.userFilter.archive;
                
                $scope.$watch('filter.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reload,CONST.reloadTimer);
                    }
                },true);
                
                $scope.agent = new InstanceAgent();
                $scope.openAgent = function(agent) {
                    $scope.agent.setItem(agent);
                    $scope.agent.open(function(value) {
                        $scope.reload();
                    });
                };
                
                $scope.getSites=function(){
                    return ReferentielService.getGroupeDefer('site');
                };
                $scope.getPeriodes = function() {
                    return ReferentielService.getGroupeDefer('periodicite');
                };
                $scope.getUnites = function() {
                    return UniteService.getDefer();
                };


                $scope.reload = function () {
                    $scope.loading = true;
                    data.splice(0, data.length);
                    $scope.agentT.ngTable.count($scope.filter.max);
                    $scope.filter.sort = $scope.agentT.ngTable.sorting();
                    $scope.agentT.data.splice(0, $scope.agentT.data.length);
                    AgentService.getAll(function (result) {
                        if (result.data) {
                            $scope.totalItems = result.data.total;
                            CONST.pagination.nbPages.pop();
                            CONST.pagination.nbPages.push(result.data.total);
                            for (var i in result.data.agents){
                                data.push(result.data.agents[i]);
                            }
                            $scope.agentT.reload();
                        }
                        $scope.loading = false;
                    },function(){
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
            }]);