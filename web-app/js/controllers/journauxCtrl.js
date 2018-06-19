/* * Copyright (C) 2014 thurgi*/
angular.module('Journaux',[]).
        controller('JournauxCtrl', ['CONST','$rootScope', '$scope', '$timeout', 'tableModel','JournauService',
            function(CONST,$rootScope, $scope, $timeout, tableModel,JournauService) {
                $scope.filter = {
                    max: CONST.pagination.nbPages[0],
                    offset: 0,
                    filter: $rootScope.userFilter.journaux,
                    sort: null,
                    prioritaireVM: true,
                    inactif: true
                };
                var data = [], timeReload;
                $scope.journalT = new tableModel(data);
                $scope.journalT.ngTable.$params.filter = $rootScope.userFilter.journaux;
                
                $scope.$watch('filter.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reload,CONST.reloadTimer);
                    }
                },true);
                
                $scope.reload = function () {
                    $scope.loading = true;
                    data.splice(0, data.length);
                    $scope.journalT.ngTable.count($scope.filter.max);
                    $scope.filter.sort = $scope.journalT.ngTable.sorting();
                    JournauService.getAll(function (result){
                        if (result.data) {
                            $scope.totalItems = result.data.total;
                            CONST.pagination.nbPages.pop();
                            CONST.pagination.nbPages.push(result.data.total);
                            for (var i in result.data.journaux) {
                                if(!result.data.journaux[i].unite){
                                    result.data.journaux[i].unite='';
                                }
                                data.push(result.data.journaux[i]);
                            }
                            $scope.journalT.reload();
                        }
                        $scope.loading = false;
                    }, function () {
                        $scope.loading = false;
                    }, $scope.filter);
                };
                
                $scope.$watch('journalT.ngTable.$params', function (newVal, oldVal) {
                    for(var key in newVal.sorting){
                        if(!(oldVal.sorting && oldVal.sorting[key] && oldVal.sorting[key] === newVal.sorting[key])){
                            $scope.reload();
                        }
                    }
                }, true);
                
                $scope.getUnites = function () {
                    return JournauService.getTypeDefer('unite');
                };
                $scope.getTypes = function () {
                    return JournauService.getTypeDefer('type');
                };
                $scope.getUsers = function () {
                    return JournauService.getTypeDefer('user');
                };
                
                $scope.reload();
                
            }]);
		