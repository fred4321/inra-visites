/* * Copyright (C) 2014 thurgi*/
angular.module('ConvoquerBS', []).
        controller('ConvoquerBSCtrl', ['$rootScope', '$scope', '$timeout', 'CONST', 'BsService', 'tableModel', 'InstanceBS', 'InstanceAgent', 'UserService', 'UniteService', 'exportToCSV',
            function ($rootScope, $scope, $timeout, CONST, BsService, tableModel, InstanceBS, InstanceAgent, UserService, UniteService, exportToCSV) {
                $scope.CONST = CONST;
                $scope.access = (UserService.getProfil() === 'SP' || UserService.getProfil() === 'medecin');
                $scope.gu = UserService.getProfil() === 'GU';
                $scope.filter = {
                    max: CONST.pagination.nbPages[0],
                    offset: 0,
                    filter: $rootScope.userFilter.convoquerBS,
                    sort: null,
                    statut: "convoqué"
                };
                var data = [], timeReload;
                $scope.BST = new tableModel(data);
                $scope.BST.ngTable.$params.filter = $rootScope.userFilter.convoquerBS;
                
                $scope.$watch('filter.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reload,CONST.reloadTimer);
                    }
                },true);
                
                $scope.sites = {
                    data: [],
                    value: 'vide',
                    boutton: false,
                    change: function (v) {
                        $scope.sites.value = v.nom;
                        this.boutton = true;
                    }
                };

                $scope.getUnites = function () {
                    return UniteService.getDefer();
                };

                $scope.bs = new InstanceBS();
                $scope.openBS = function (id) {
                    if ($scope.access) {
                        $scope.bs.setItem(id);
                        $scope.bs.open(function (value) {
                            $scope.reload();
                        });
                    }
                };

                $scope.agent = new InstanceAgent();
                $scope.openAgent = function (id) {
                    $scope.agent.setItem(id);
                    $scope.agent.open(function (value) {
                        $scope.reload();
                    });
                };

                $scope.reload = function () {
                    $scope.loading = true;
                    data.splice(0, data.length);
                    $scope.BST.ngTable.count($scope.filter.max);
                    $scope.filter.sort = $scope.BST.ngTable.sorting();
                    $scope.sites.data.splice(0, $scope.sites.data.length);
                    BsService.getAll(function (result) {
                        if (result.status === 200 && result.data.bss) {
                            $scope.totalItems = result.data.total;
                            CONST.pagination.nbPages.pop();
                            CONST.pagination.nbPages.push(result.data.total);
                            for (var i in result.data.bss) {
                                $scope.BST.data.push(result.data.bss[i]);
                                if ($scope.sites.data.indexOf(result.data.bss[i].site) === -1) {
                                    $scope.sites.data.push({nom: result.data.bss[i].site});
                                }
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

                $scope.enregistrer = function (bs) {
                    BsService.effectuer([{id: bs.id, statut: 'effectué'}], function () {
                        $scope.BST.delete(bs.id);
                    }, function () {
                        //erreur serveur
                    });
                };

                $scope.enregistrerTout = function () {
                    if (confirm('Enregistrer l\'ensemble des lignes renseignées')) {
                        var bs = [];
                        for (var i in $scope.BST.ngTable.data) {
                            bs.push({id: $scope.BST.ngTable.data[i].id, statut: 'effectué'});
                        }
                        BsService.effectuer(bs, function () {
                            $scope.BST.deleteGroupe(bs);
                        }, function () {
                            //en cas d'erreur serveur
                        });
                    }
                };

                $scope.absent = function (bs) {
                    BsService.absent({id: bs.id, statut: 'absent'}, function () {
                        $scope.BST.delete(bs.id);
                    });
                };
                
                $scope.exportCsv = function(site){
                    console.log(site);
                    BsService.getCsv({site:site},function(result){
                        console.log(result);
                        console.log((result.status === 200 && result.data.length>0));
                        if(result.status === 200 && result.data.length>0){
                            var i = 0, l = result.data.length, str = 'data:text/csv;charset=UTF-8,';
                            for (var key in result.data[0]){
                                str = str + key+'%3b';
                            }
                            str = str + '%0a';
                            for (i;i<l;i++){
                                for (var key in result.data[i]){
                                    str = str + result.data[i][key] + '%3b';
                                }
                                str = str + '%0a';
                            }
                            console.log(str);
                            exportToCSV(str, site);
                        }
                        
                    },function(result){
                        console.log(result);
                    });
                };

            }]);