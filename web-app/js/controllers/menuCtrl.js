var menu = angular.module('Menu', ['ui.bootstrap']).
        controller('MenuCtrl', ['$scope', '$location', 'MenuModel', 'UserService','InstancePassword',
            function ($scope, $location, MenuModel, UserService, InstancePassword) {
                $scope.menu = MenuModel;
                $scope.menu.menus = MenuModel.defaultMenus();
                $scope.password = new InstancePassword();
                
                $scope.openPassword = function () {
                    $scope.password.open();
                };

                $scope.menu.init($location.path());

                var disabledMenu = function (dropList) {
                    for (var k in dropList) {
                        for (var i in $scope.menu.menus) {
                            for (var j in $scope.menu.menus[i].subMenu) {
                                if(dropList[k] === $scope.menu.menus[i].subMenu[j].link){
                                    $scope.menu.menus[i].subMenu.splice(j, 1);
                                }
                            }
                        }
                    }
                };

                $scope.setMenu = function (statut) {
                    if (statut === 'GU') {
                        disabledMenu(['nouveau', 'archivage', 'programmerVM', 'referentiel', 'import', 'droit']);
                    } else if (statut === 'SP') {
                        disabledMenu(['referentiel' , 'droit']);
                    } else if (statut === 'administrateur') {
                        disabledMenu(['nouveau', 'archivage', 'programmerVM']);
                    } else if (statut === 'medecin') {
                        disabledMenu(['nouveau', 'archivage', 'programmerVM', 'referentiel', 'import', 'enCourATraiterVM', 'droit']);
                    }
                    $scope.menu.init($location.path());
                };

                $scope.$watch(UserService.isLoggedIn, function (value, oldValue) {
                    if (!value && oldValue) {
                        //console.log("Disconnect");
                        $scope.user = null;
                        $scope.profil = null;
                        $scope.login = false;
                        $location.path('/login');
                    }
                    if (value) {
                        //console.log("Connect");
                        $scope.user = value.user;
                        $scope.profil = value.profil;
                        $scope.login = true;
                        $scope.setMenu(value.profil);
                    }
                }, true);
                
                $scope.goHome = function(){
                    $location.path('/');
                    $scope.menu.init($location.path());
                };


                $scope.logout = function () {
                    $scope.menu.menus = MenuModel.defaultMenus();
                    UserService.logout(function () {
                        UserService.setIdentifiant();
                    });
                };
            }]);