/* * Copyright (C) 2014 thurgi*/
angular.module('EffectueVM', ['ui.bootstrap', 'VM']).
        controller('EffectueVMCtrl', ['$rootScope','$scope', '$timeout', 'CONST', 'VmService','tableModel','InstanceVM','UserService','InstanceAgent',
            function($rootScope,$scope, $timeout, CONST, VmService,tableModel,InstanceVM,UserService,InstanceAgent) {
                $scope.CONST=CONST;
                $scope.access=( UserService.getProfil() === 'SP' || UserService.getProfil() === 'medecin' );
                $scope.filter = {
                    max:CONST.pagination.nbPages[0],
                    offset:0,
                    filter : $rootScope.userFilter.effectueVM,
                    sort : null,
                    statut: 'effectué-cloturé'
                };
                var data = [], timeReload;
                $scope.creneauT=new tableModel(data);
                $scope.creneauT.ngTable.$params.filter = $rootScope.userFilter.effectueVM;

                $scope.$watch('filter.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reload,CONST.reloadTimer);
                    }
                },true);
                
                $scope.reload = function () {
                    $scope.filter.unite="agent";
                    $scope.loading = true;
                    data.splice(0, data.length);
                    $scope.creneauT.ngTable.count($scope.filter.max);
                    $scope.filter.sort = $scope.creneauT.ngTable.sorting();
                    VmService.getAll(function (result) {
                        if (result.data) {
                            $scope.totalItems = result.data.total;
                            CONST.pagination.nbPages.pop();
                            CONST.pagination.nbPages.push(result.data.total);
                            for (var i in result.data.vms){
                                data.push(result.data.vms[i]);
                            }
                            $scope.creneauT.reload();
                        }
                        $scope.loading = false;
                    }, function(){
                        $scope.loading = false;
                    }, $scope.filter);
                };
                
                $scope.$watch('creneauT.ngTable.$params', function (newVal, oldVal) {
                    for(var key in newVal.sorting){
                        if(!(oldVal.sorting && oldVal.sorting[key] && oldVal.sorting[key] === newVal.sorting[key])){
                            $scope.reload();
                        }
                    }
                }, true);
                
                $scope.reload();
                
                $scope.vm=new InstanceVM();
                $scope.openVM=function(id){
                    $scope.vm.setItem(id);
                    $scope.vm.open(function(value){
                        $scope.reload();
                    });
                };
                
                $scope.agent = new InstanceAgent();
                $scope.openAgent = function (agent) {
                    $scope.agent.setItem(agent);
                    $scope.agent.open(function (value) {
                        $scope.reload();
                    });
                };
                
                $scope.recap = function ( creneau ){
                    creneau.sendMail=true;
                    VmService.courriel("recapitulation",{id:creneau.id}, function () {
                        creneau.sendMail=false;
                    });
                };
            }]);