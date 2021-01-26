angular.module('Tous', []).
        controller('TousCtrl', ['$rootScope','$scope', '$timeout', 'CONST', 'AgentService', 'InstanceAgent', 'tableModel', 'InstanceVM','ReferentielService','UniteService','userFilter',
            function($rootScope,$scope, $timeout, CONST, AgentService, InstanceAgent, tableModel, InstanceVM,ReferentielService,UniteService,userFilter) {
                $scope.CONST = CONST;
                var data = [], timeReload;
                $scope.agentT = new tableModel(data);
                $scope.pagination = {
                    max:CONST.pagination.nbPages[0],
                    offset:0,
                    filter : $rootScope.userFilter.tous,
                    sort : null,
                    archive : false
                };
                $scope.agentT.ngTable.$params.filter = $rootScope.userFilter.tous;
                $scope.totalItems=0;
                
                $scope.$watch('pagination.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reload,CONST.reloadTimer);
                    }
                },true);

                $scope.agent = new InstanceAgent();
                $scope.openAgent = function(agent) {
                    $scope.agent.setItem(agent);
                    $scope.agent.open();
                };
                
                $scope.vm=new InstanceVM();
                $scope.openVM=function(id){
                    $scope.vm.setItem(id);
                    $scope.vm.open();
                };
                
                $scope.reload = function () {
                    $scope.loading = true;
                    data.splice(0, data.length);
                    $scope.agentT.ngTable.count($scope.pagination.max);
                    $scope.pagination.sort = $scope.agentT.ngTable.sorting();
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
                        //error server
                        $scope.loading = false;
                    },$scope.pagination);
                };
                
                $scope.$watch('agentT.ngTable.$params', function (newVal, oldVal) {
                    for(var key in newVal.sorting){
                        if(!(oldVal.sorting && oldVal.sorting[key] && oldVal.sorting[key] === newVal.sorting[key])){
                            $scope.reload();
                        }
                    }
                }, true);
                
                $scope.getSites=function(){
                    return ReferentielService.getGroupeDefer('site');
                };
                $scope.getPeriodes = function() {
                    return ReferentielService.getGroupeDefer('periodicite');
                };
                $scope.getUnites = function() {
                    return UniteService.getDefer();
                };
                
                $scope.reload();

            }]);