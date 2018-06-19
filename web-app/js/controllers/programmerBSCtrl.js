/* * Copyright (C) 2014 thurgi*/
angular.module('ProgrammerBS', []).
        controller('ProgrammerBSCtrl', ['$rootScope', '$scope', '$timeout', 'CONST', 'InstanceDateBS', 'BsService', 'tableModel', 'InstanceBS', 'ReferentielService', 'UserService', 'UniteService', 'InstanceAgent',
            function ($rootScope, $scope, $timeout, CONST, InstanceDateBS, BsService, tableModel, InstanceBS, ReferentielService, UserService, UniteService, InstanceAgent) {
                $scope.CONST = CONST;
                $scope.access = (UserService.getProfil() === 'SP' || UserService.getProfil() === 'medecin');
                $scope.sp = UserService.getProfil() === 'SP';
                $scope.medecin = UserService.getProfil() === 'medecin';
                $scope.gu = UserService.getProfil() === 'GU';
                $scope.filter = {
                    max: CONST.pagination.nbPages[0],
                    offset: 0,
                    filter: $rootScope.userFilter.programmerBS,
                    sort: null,
                    statut: "programmé"
                };
                var data = [], timeReload;
                $scope.BST = new tableModel(data);
                $scope.BST.ngTable.$params.filter = $rootScope.userFilter.programmerBS;
                
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

                $scope.email = function (bs) {
                    BsService.convoquer({id: bs.id}, function () {
                        $scope.BST.delete(bs.id);
                    });
                };

                $scope.initialiser = function (id) {
                    
                    BsService.initialiser({id: id}, function (result) {
                        if (result.status === 200) {
                            $scope.BST.delete(id);
                        }
                    });
                };

                $scope.reload = function () {
                    $scope.loading = true;
                    data.splice(0, data.length);
                    $scope.BST.ngTable.count($scope.filter.max);
                    $scope.filter.sort = $scope.BST.ngTable.sorting();
                    BsService.getAll(function (result) {
                        if (result.status === 200 && result.data.bss) {
                            $scope.totalItems = result.data.total;
                            CONST.pagination.nbPages.pop();
                            CONST.pagination.nbPages.push(result.data.total);
                            for (var i in result.data.bss) {
                                data.push(result.data.bss[i]);
                            }
                            $scope.BST.reload();
                        }
                        $scope.loading = false;
                    }, function () {
                        //erreur serveur
                        $scope.loading = false;
                    }, $scope.filter);
                };
                
                $scope.$watch('BST.ngTable.$params', function (newVal, oldVal) {
                    for(var key in newVal.sorting){
                        if(!(oldVal.sorting && oldVal.sorting[key] && oldVal.sorting[key] === newVal.sorting[key])){
                            $scope.reload();
                        }
                    }
                }, true);
                
                $scope.reload();

                $scope.modalDate = new InstanceDateBS();
                $scope.openModalDate = $scope.access ? function () {
                    $scope.modalDate.open(function (value) {
                        $scope.reload();
                    });
                } : false;

                $scope.bs = new InstanceBS();
                $scope.openBS = function (id) {
                    $scope.bs.setItem(id);
                    $scope.bs.open(function (value) {
                    });
                };

                $scope.agent = new InstanceAgent();
                $scope.openAgent = function (agent) {
                    $scope.agent.setItem(agent);
                    $scope.agent.open(function (value) {
                    });
                };
            }]);