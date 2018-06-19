/* * Copyright (C) 2014 thurgi*/
angular.module('Stat', []).
        controller('StatCtrl', ['$scope', 'StatService', 'exportToCSV',
            function ($scope, StatService, exportToCSV) {

                $scope.isString = function (test) {
                    return typeof test === 'string';
                };

                $scope.exportCsv = function (tableau) {
                    var str = "data:text/csv;charset=UTF-8,";
                    for (var i in tableau.titles) {
                        str = str + tableau.titles[i] + '%3b';
                    }
                    str = str + '%0a';
                    for (var i in tableau.rows) {
                        for (var j in tableau.rows[i]) {
                            str = str + tableau.rows[i][j] + '%3b';
                        }
                        str = str + '%0a';
                    }
                    exportToCSV(str, tableau.title);
                };

                $scope.reload = function () {
                    StatService.tableaux(function (result) {
                        if (result.status === 200) {
                            $scope.tableaux = result.data;
                        }
                    });
                };

                $scope.reload();
            }]);
