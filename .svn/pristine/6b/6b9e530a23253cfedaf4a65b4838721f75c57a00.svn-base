/* * Copyright (C) 2014 thurgi*/
angular.module('inra')
        .directive('inraPagination', ['CONST', '$log', function(CONST, $log) {
                return {
                    restrict: 'E',
                    templateUrl: CONST.templates.pagination,
                    scope: {
                        totalItems : '=',
                        changePage : '=',
                        changeItemsPerPages : '=',
                        params : '='
                    },
                    link: function(scope) {
                        scope.nbPage = CONST.pagination.nbPages;
                        scope.maxSize = CONST.pagination.maxSize;
                        scope.params.currentPage = 1;
                    }
                };
            }]);