/* * Copyright (C) 2014 thurgi*/
angular.module('inra')
        .directive('import', ['CONST', '$http', 'FileUploader',
            function(CONST, $http, FileUploader) {
                return {
                    restrict: 'E',
                    templateUrl: CONST.templates.import,
                    scope: {
                        fileName: '@'
                    },
                    link: function(scope) {
                        scope.alerts = [];
                        scope.statut = 'init';

                        scope.file = new FileUploader({
                            url: 'import/upload?name=' + scope.fileName,
                            alias: scope.fileName,
                            filters: [{
                                    fn: function(item) {
                                        
                                        var reg = new RegExp(scope.fileName + '_[0-9]{8}\.xlsx', 'gi');
                                        if (reg.test(item.name)) {
                                            scope.statut = 'submit';
                                            return true;
                                        }
                                        scope.alerts.push({msg: 'nom de fichier incorrect', type: 'warning'});
                                        return false;
                                    }
                                }]
                        });

                        scope.file.onSuccessItem = function(file, b, statut) {
                            if (statut === 200) {
                                scope.statut = 'uploaded';
                            } else {
                                scope.init();
                                scope.alerts.push({msg: 'Erreur lors de l\'envoie du fichier', type: 'danger'});
                            }
                        };
                        scope.file.onErrorItem = function() {
                            scope.init();
                            scope.alerts.push({msg: 'Erreur du serveur lors de l\'envoie du fichier', type: 'danger'});
                        };

                        scope.submit = function() {
                            scope.statut = 'transfert';
                            scope.file.queue[0].upload();
                        };

                        scope.verification = function() {
                            scope.statut='verification';
                            $http.get('import/verif?name=' + scope.fileName).success(function(result, statut) {
                                if (statut === 200) {
                                    scope.statut='verifier';
                                } else {
                                    scope.init();
                                    scope.alerts.push({msg: 'Erreur lors de la vérification', type: 'warning'});
                                }
                            }).error(function(result, statut) {
                                scope.init();
                                scope.alerts.push({msg: 'Erreur lors de la vérification', type: 'warning'});
                            });
                        };
                        
                        scope.import=function(){
                            scope.statut='importation';
                            $http.get('import/importation?name=' + scope.fileName).success(function(result, statut) {
                                if (statut === 200) {
                                    scope.statut='importer';
                                } else {
                                    scope.init();
                                    scope.alerts.push({msg: 'Erreur lors de l\'import', type: 'warning'});
                                }
                            }).error(function(result, statut) {
                                scope.init();
                                scope.alerts.push({msg: 'Erreur lors de l\'import', type: 'warning'});
                            });
                        };
                        
                        scope.init=function(){
                            scope.statut='init';
                            scope.alerts.splice(0, scope.alerts.length);
                            scope.file.clearQueue();
                        };

                        scope.closeAlert = function(index) {
                            scope.alerts.splice(index, 1);
                        };
                    }
                };
            }]);