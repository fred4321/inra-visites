/* * Copyright (C) 2014 thurgi*/
angular.module('RapportImport', ['ui.bootstrap'])
        .controller('RapportImportCtrl', ['$scope', '$modalInstance', 'ImportService', 'items', 'exportToCSV','$filter',
            function ($scope, $modalInstance, ImportService, items, exportToCSV,$filter) {
                //fermeture
                $scope.ok = function () {
                    $modalInstance.close(getSelection());
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss();
                };

                //accordeon
                $scope.acc = {
                    nouveau: false,
                    archive: false,
                    modif: false,
                    erreur: false,
                    one: false
                };

                $scope.reload = function () {
                    ImportService.getRapport({id: items}, function (result) {
                        $scope.rapport = result.data;
                    });
                };

                $scope.reload();

                $scope.exportCsv = function (resume, nouveau, modifier, prioritaire, erreur) {
                    var str = 'data:text/csv;charset=UTF-8,';
                    var r = $scope.rapport;
                    if (r) {
                        if (resume) {
                            str = str + '"id";"Date";"Type";"Erreurs";"Modifiées";"Nouveaux agents";"Agents prioritaires";%0a';
                            str = str + '"' + r.id + '";"' + $filter('date')(r.date, 'yyyy-MM-dd HH:mm:ss') + '";"' + r.type + '";"' + r.erreur.length + '";"' + r.modifier.length + '";"' + r.nouveau.length + '";"' + r.prioritaire.length + '";%0a';
                        }
                        if (nouveau) {
                            str = resume ? str : str + '"Nouveaux agents : ' + r.nouveau.length + '";%0a';
                            str = str + '"action";"matricule";"prenom";"nom";"ligne";%0a';
                            for (var i in r.nouveau) {
                                str = str + '"NOUVEAU";"' + r.nouveau[i].matricule +'";"'+ r.nouveau[i].prenom +'";"'+ r.nouveau[i].nom +'";"' + r.nouveau[i].row + '";%0a';
                            }
                        }
                        if (modifier) {
                            str = resume ? str : str + '"Modifiées : ' + r.modifier.length + '";%0a';
                            str =  nouveau ? str : str + '"action";"matricule";"prenom";"nom";"ligne";%0a';
                            for (var i in r.modifier) {
                                str = str + '"MODIFICATION";"' + r.modifier[i].matricule +'";"'+ r.modifier[i].prenom +'";"'+ r.modifier[i].nom +'";"'+ r.modifier[i].row + '";%0a';
                            }
                        }
                        if (prioritaire) {
                            str = resume ? str : str + '"Agents prioritaires : ' + r.prioritaire.length + '";%0a';
                            str = str + '"action";"matricule";"prenom";"nom";"nature";%0a';
                            for (var i in r.prioritaire) {
                                
                                str = str + '"PRIORITAIRE";' + r.prioritaire[i].matricule +'";"'+ r.prioritaire[i].prenom +'";"'+ r.prioritaire[i].nom +'";"'+ r.prioritaire[i].info + '";%0a';
                            }
                        }
                        if (erreur) {
                            console.log(r.erreur);
                            str = resume ? str : str + '"Erreur : ' + r.erreur.length + '";%0a';
                            str = str + '"action";"matricule";"ligne";"erreur";%0a';
                            for (var i in r.erreur) {
                                console.log(i,r.erreur[i]);
                                str = str + '"ERREUR";"' + r.erreur[i].matricule +'";"'+ r.erreur[i].row+'";"'+ r.erreur[i].erreur + '";%0a';
                            }
                        }
                    }
                    exportToCSV(str, 'Rapport_Import_'+r.type+'_'+r.date.substring(0,10));
                };

            }]).
        factory('InstanceRapportImport', ['$modal', function ($modal) {
                return function () {
                    this.params = {
                        templateUrl: 'partials/modal/rapportImport.html',
                        controller: 'RapportImportCtrl',
                        size: 'lg',
                        backdrop: false
                    };
                    this.open = function (action) {
                        var vm = $modal.open(this.params);
                        vm.result.then(function (selectedItem) {
                            action(selectedItem);
                        }, function () {
                            //console.log('dissim rapport import');
                        });
                    };
                    this.setItem = function (item) {
                        this.params.resolve = {
                            items: function () {
                                return item;
                            }
                        };
                    };
                };
            }]);