angular.module('Convoque', []).
        controller('ConvoqueCtrl', ['CONST','$rootScope', '$scope', '$timeout', 'VmService', 'InstanceAgent', 'tableModel', 'ReferentielService', 'UniteService',
            function (CONST,$rootScope, $scope, $timeout, VmService, InstanceAgent, tableModel, ReferentielService, UniteService) {
                $scope.filter = {
                    max:CONST.pagination.nbPages[0],
                    offset:0,
                    filter : $rootScope.userFilter.convoque,
                    sort : null,
                    statut:'affecté-convoqué'
                };
                var data = [], timeReload;
                $scope.vmt = new tableModel(data);
                $scope.vmt.ngTable.$params.filter = $rootScope.userFilter.convoque;
                
                $scope.$watch('filter.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reload,CONST.reloadTimer);
                    }
                },true);
                
                $scope.agent = new InstanceAgent();
                $scope.openAgent = function (agent) {
                    $scope.agent.setItem(agent);
                    $scope.agent.open(function (value) {
                        $scope.reload();
                    });
                };

                $scope.getSites = function () {
                    return ReferentielService.getGroupeDefer('site');
                };
                $scope.getUnites = function () {
                    return UniteService.getDefer();
                };
                $scope.getPeriodes = function () {
                    return ReferentielService.getGroupeDefer('periodicite');
                };
                $scope.getNatures = function () {
                    return ReferentielService.getGroupeDefer('natureVM');
                };

                $scope.reload = function () {
                    $scope.loading = true;
                    data.splice(0, data.length);
                    $scope.vmt.ngTable.count($scope.filter.max);
                    $scope.filter.sort = $scope.vmt.ngTable.sorting();
                    VmService.getAll(function (result) {
                        if (result.data) {
                            $scope.totalItems = result.data.total;
                            CONST.pagination.nbPages.pop();
                            CONST.pagination.nbPages.push(result.data.total);
                            for (var i in result.data.vms) {
                                data.push(result.data.vms[i]);
                            }
                            $scope.vmt.reload();
                        }
                        $scope.loading = false;
                    },function(){
                        $scope.loading = false;
                    },$scope.filter);
                };
                
                $scope.$watch('vmt.ngTable.$params', function (newVal, oldVal) {
                    for(var key in newVal.sorting){
                        if(!(oldVal.sorting && oldVal.sorting[key] && oldVal.sorting[key] === newVal.sorting[key])){
                            $scope.reload();
                        }
                    }
                }, true);
                
                $scope.reload();
            }]);