/* * Copyright (C) 2014 thurgi*/
angular.module('inra')
        .directive('remarques', ['CONST', 'RemarquesService',
            function (CONST, RemarquesService) {
                return {
                    restrict: 'E',
                    templateUrl: CONST.templates.remarques,
                    scope: {
                        type: '@',
                        ref: '=',
                        data: '=',
                        title: '@',
                        edit: '='
                    },
                    link: function (scope) {
                        scope.service = scope.type ? RemarquesService(scope.type) : null;
                        for (var i in scope.data) {
                            scope.data[i].edit = false;
                        };
                        scope.remove = function (i, id) {
                            if (scope.service) {
                                scope.service.remove({id: scope.ref, cId: id}, function () {
                                    scope.data.splice(i, 1);
                                });
                            } else {
                                scope.data.splice(i, 1);
                            }
                        };
                        scope.update = function (remarque) {
                            if (scope.service) {
                                scope.service.update({id: remarque.id, text: remarque.text}, function (result) {
                                });
                            }

                        };
                        scope.add = function () {
                            if (scope.service) {
                                scope.service.add({id: scope.ref}, function (result) {
                                    scope.data.push({
                                        text: result.data.text,
                                        id: result.data.id,
                                        edit: true
                                    });
                                });
                            } else {
                                scope.data.push({
                                    text: "",
                                    id: "",
                                    edit: true
                                });
                            }
                        };
                    }
                };
            }]);