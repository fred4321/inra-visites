/* * Copyright (C) 2014 thurgi*/
angular.module('inra')
        .directive('action', ['CONST', '$timeout', 'exportToCSV','$location',
            function (CONST, $timeout, exportToCSV, $location) {
                return {
                    restrict: 'E',
                    templateUrl: CONST.templates.action,
                    scope: {
                        //fonction custom
                        printAction: '=',
                        reloadAction: '=',
                        exportAction: '=',
                        //affichage des bouttons (boolean)
                        printView: '@',
                        exportView: '@',
                        reloadView: '@',
                        csvName: '@'

                    },
                    link: function (scope) {
                        //Action par defaut
                        scope.print = {
                            action: (scope.printAction) ? scope.printAction : function () {
                                $timeout(function () {
                                    window.print();
                                });
                            },
                            view: (scope.printView)
                        };

                        scope.reload = {
                            action: (scope.reloadAction) ? scope.reloadAction : function () {
                                location.reload();
                            },
                            view: (scope.reloadView)
                        };

                        //exportToCSV();
                        scope.exportToCSV = function () {
                            scope.exportAction.generate();
                            var str = scope.exportAction.link();
                            var t = new Date();
                            exportToCSV(str, ((scope.csvName?scope.csvName:$location.path().substring(1))+''+t.getFullYear()+(t.getMonth()+1)+t.getDate()));
                        };
                    }
                };
            }]);