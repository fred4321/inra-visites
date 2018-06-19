/* * Copyright (C) 2014 thurgi*/
angular.module('Import', ['Verification', 'RapportImport']).
        controller('ImportCtrl', ['CONST', '$rootScope', '$scope', '$timeout', 'ImportService', 'tableModel', 'InstanceVerification', 'InstanceRapportImport',
            function (CONST, $rootScope, $scope, $timeout, ImportService, tableModel, InstanceVerification, InstanceRapportImport) {

                var listVerif = [], listRapport = [], timeReload;
                
                CONST.pagination.nbPages.pop();
                CONST.pagination.nbPages.push(1000);

                $scope.verifT = new tableModel(listVerif);
                $scope.verifT.ngTable.$params.filter = $rootScope.userFilter.verification;
                $scope.verifT.totalItems=0;
                $scope.verifT.filter = {
                    max: CONST.pagination.nbPages[0],
                    offset: 0,
                    filter: $rootScope.userFilter.verification,
                    sort: null
                };
                
                $scope.$watch('verifT.filter.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reloadVerif,CONST.reloadTimer);
                    }
                },true);

                $scope.rapportT = new tableModel(listRapport);
                $scope.rapportT.ngTable.$params.filter = $rootScope.userFilter.rapport;
                $scope.rapportT.totalItems=0;
                $scope.rapportT.filter = {
                    max: CONST.pagination.nbPages[0],
                    offset: 0,
                    filter: $rootScope.userFilter.rapport,
                    sort: null
                };
                
                $scope.$watch('rapportT.filter.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reloadRapport,CONST.reloadTimer);
                    }
                },true);

                $scope.verification = new InstanceVerification();
                $scope.openVerification = function (id) {
                    $scope.verification.setItem(id);
                    $scope.verification.open();
                };

                $scope.import = new InstanceRapportImport();
                $scope.openImport = function (id) {
                    $scope.import.setItem(id);
                    $scope.import.open();
                };

                $scope.reloadVerif = function () {
                    
                    listVerif.splice(0, listVerif.length);
                    $scope.verifT.ngTable.count($scope.verifT.filter.max);
                    $scope.verifT.filter.sort = $scope.verifT.ngTable.sorting();
                    
                    
                    ImportService.getAllVerif($scope.verifT.filter, function (result) {
                        if (result.status === 200) {
                            listVerif.splice(0, listVerif.length);
                            $scope.verifT.totalItems = result.data.total;
                            for (var i in result.data.verifs) {
                                listVerif.push(result.data.verifs[i]);
                            }
                        }
                        $scope.verifT.reload();
                    });
                };
                
                $scope.$watch('verifT.ngTable.$params', function (newVal, oldVal) {
                    for(var key in newVal.sorting){
                        if(!(oldVal.sorting && oldVal.sorting[key] && oldVal.sorting[key] === newVal.sorting[key])){
                            $scope.reloadVerif();
                        }
                    }
                }, true);

                $scope.reloadRapport = function () {
                    
                    listRapport.splice(0, listRapport.length);
                    $scope.rapportT.ngTable.count($scope.rapportT.filter.max);
                    $scope.rapportT.filter.sort = $scope.rapportT.ngTable.sorting();
                    
                    
                    ImportService.getAllRapport($scope.rapportT.filter, function (result) {
                        if (result.status === 200) {
                            listRapport.splice(0, listRapport.length);
                            $scope.rapportT.totalItems = result.data.total;
                            for (var i in result.data.rapports) {
                                listRapport.push(result.data.rapports[i]);
                            }
                        }
                        $scope.rapportT.reload();
                    });
                };
                
                $scope.$watch('rapportT.ngTable.$params', function (newVal, oldVal) {
                    for(var key in newVal.sorting){
                        if(!(oldVal.sorting && oldVal.sorting[key] && oldVal.sorting[key] === newVal.sorting[key])){
                            $scope.reloadRapport();
                        }
                    }
                }, true);

                $scope.reloadRapport();
                $scope.reloadVerif();
            }]);