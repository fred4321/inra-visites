angular.module('PrioritaireBS', []).
        controller('PrioritaireBSCtrl', ['$rootScope', '$scope', '$timeout', 'CONST', 'InstanceAgent', 'tableModel', 'ReferentielService', 'BsService', 'InstanceBS', 'UniteService', 'UserService',
            function ($rootScope, $scope, $timeout, CONST, InstanceAgent, tableModel, ReferentielService, BsService, InstanceBS, UniteService, UserService) {
                $scope.CONST = CONST;
                $scope.access = (UserService.getProfil() === 'SP' || UserService.getProfil() === 'medecin');
                $scope.filter = {
                    max: CONST.pagination.nbPages[0],
                    offset: 0,
                    filter: $rootScope.userFilter.prioritaireBS,
                    sort: null,
                    statut:'créé'
                };
                var data = [], timeReload;
                $scope.BST = new tableModel(data);
                $scope.BST.ngTable.$params.filter = $rootScope.userFilter.prioritaireBS;
                
                $scope.$watch('filter.filter',function(newVal,oldVal){
                    if(oldVal && !angular.equals(newVal, oldVal)){
                        $timeout.cancel(timeReload);
                        timeReload = $timeout($scope.reload,CONST.reloadTimer);
                    }
                },true);

                $scope.agent = new InstanceAgent();
                $scope.openAgent = function (id) {
                    $scope.agent.setItem(id);
                    $scope.agent.open(function (value) {
                    });
                };

                $scope.bs = new InstanceBS();
                $scope.openBS = function (id) {
                    $scope.bs.setItem(id);
                    $scope.bs.open(function (value) {
                    });
                };

                $scope.reload = function () {
                    $scope.loading = true;
                    data.splice(0, data.length);
                    $scope.BST.ngTable.count($scope.filter.max);
                    $scope.filter.sort = $scope.BST.ngTable.sorting();
                    BsService.getAll(function (result) {
                        if (result.data) {
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

                $scope.getSites = function () {
                    return ReferentielService.getGroupeDefer('site');
                };
                $scope.getUnites = function () {
                    return UniteService.getDefer();
                };

            }]);