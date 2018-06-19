/* * Copyright (C) 2014 thurgi*/
angular.module('AttribueVM', ['ngTable']).
        controller('AttribueVMCtrl', ['$rootScope', '$scope', '$timeout', 'CONST', 'VmService', 'tableModel', 'UserService', 'InstanceVM', 'userFilter', 'InstanceAgent',
            function ($rootScope, $scope, $timeout, CONST, VmService, tableModel, UserService, InstanceVM, userFilter, InstanceAgent) {
                $scope.CONST = CONST;
                $scope.sp = UserService.getProfil() === 'SP';
                $scope.medecin = UserService.getProfil() === 'medecin';
                $scope.filter = {
                    max:CONST.pagination.nbPages[0],
                    offset:0,
                    filter : $rootScope.userFilter.attribueVM,
                    sort : null,
                    statut : 'affecté-convoqué'
                };
                var data = [], timeReload;
                $scope.creneauT = new tableModel(data);
                $scope.creneauT.ngTable.$params.filter = $rootScope.userFilter.attribueVM;
                
                $scope.$watch('filter.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reload,CONST.reloadTimer);
                    }
                },true);
                
                $scope.vm = new InstanceVM();
                $scope.openVm = function (id) {
                    $scope.vm.setItem(id);
                    $scope.vm.open(function (value) {
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
                
                $scope.reload();

                /**
                 * Suppression d'une VM
                 * @param {type} creneau
                 */
                $scope.remove = function (creneau) {
                    VmService.remove(creneau.id, function () {
                        $scope.creneauT.delete(creneau.id);
                    });
                };

                $scope.convoquer = function (creneau) {
                    creneau.sendMail=true;
                    VmService.convoquer({id: creneau.id}, function (result) {
                        if (result.data) {
                            creneau.statut = result.data.statut;
                            creneau.dateConvoc = result.data.dateConvoc;
                        }
                        creneau.sendMail=false;
                    },function(){
                        alert("Impossible d'envoyer la convocation.");
                        creneau.sendMail=false;
                    });
                };

                $scope.absent = function (creneau) {
                    VmService.absent({id: creneau.id, statut: 'absent'}, function () {
                        $scope.creneauT.delete(creneau.id);
                    });
                };

            }]);