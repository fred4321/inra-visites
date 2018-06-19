/* * Copyright (C) 2014 thurgi*/
angular.module('inra').directive('knob', function() {
            return {
                require: 'ngModel',
                scope: {model: '=ngModel'},
                controller: function($scope, $element, $timeout) {
                    var el = $($element);
                    $scope.$watch('model', function(v) {
                        var el = $($element);
                        el.val(v).trigger('change');
                    });
                },
                link: function($scope, $element, $attrs, $ngModel) {
                    var el = $($element);
                    el.val($scope.value).knob(
                            {
                                'change': function(v) {
                                    $scope.$apply(function() {
                                        $ngModel.$setViewValue(v);
                                    });
                                }
                            }
                    );
                }
            };
        });
