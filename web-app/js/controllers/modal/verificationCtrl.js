/* * Copyright (C) 2014 thurgi*/
angular.module('Verification', ['ui.bootstrap'])
        .controller('VerificationCtrl', ['$scope', '$modalInstance', 'ImportService', 'items','exportToCSV',
            function ($scope, $modalInstance, ImportService, items,exportToCSV) {

                //fermeture
                $scope.cancel = function () {
                    $modalInstance.dismiss();
                };

                //accordeon
                $scope.acc = {
                    vide: false,
                    invalide: false,
                    champ: false,
                    ligne: false,
                    one: false,
                    BDD: false
                };

                $scope.reload = function () {
                    ImportService.getVerif({id: items}, function (result) {
                        $scope.verif = result.data;
                    });
                };

                $scope.reload();

                $scope.exportCsv = function (resume, champs, lignes, invalides, vides,DBB) {
                    var str = 'data:text/csv;charset=UTF-8,';
                    var v=$scope.verif;
                    if (v) {
                        if (resume) {
                            str = str + '"id";"Date";"Type";"Vides";"Invalides";"Doublons champs";"Doublons lignes";"Conflits avec la base de donnée";"Erreurs lignes";%0a';
                            str = str + '"'+v.id+'";"'+v.date+'";"'+v.type+'";"'+v.vide.length+'";"'+v.invalide.length+'";"'+v.champsLength+'";"'+v.ligneLength+'";"'+v.DBB.length+'";"'+(v.vide.length+v.invalide.length+v.champsLength+v.ligneLength+v.DBB.length)+'";%0a';
                        }
                        if (vides){
                            str = resume ? str : str + '"Vides : '+v.vide.length+'";%0a';
                            for (var i in v.vide){
                                str = str + '"Ligne '+v.vide[i].row+': le champs '+v.vide[i].dataType+' est vide";%0a';
                            }
                        }
                        if (invalides){
                            str = resume ? str : str + '"Invalide : '+v.invalide.length+'";%0a';
                            for (var i in v.invalide){
                                str = str + '"Ligne '+v.invalide[i].row+': le champs '+v.invalide[i].dataType+' est invalide";%0a';
                            }
                        }
                        if (champs){
                            str = resume ? str : str + '"Champs : '+v.champsLength+'";%0a';
                            for (var i in v.champs){
                                str = str + '"'+v.champs[i]+'";%0a';
                            }
                        }
                        if (lignes){
                            str = resume ? str : str + '"Lignes : '+v.ligneLength+'";%0a';
                            for (var i in v.lignes){
                                str = str + '"'+v.lignes[i]+' : Lignes identiques";%0a';
                            }
                        }
                        if (DBB){
                            str = resume ? str : str + '"Conflits avec la base de donnée : '+v.DBB.length+'";%0a';
                            for (var i in v.DBB){
                                str = str + '"ligne '+v.DBB[i].row+' : Champs de référence différent";%0a';
                            }
                        }
                    }
                    exportToCSV(str, 'Rapport_verif_'+v.type+'_'+v.date.substring(0,10));
                };

            }]).
        factory('InstanceVerification', ['$modal', function ($modal) {
                return function () {
                    this.params = {
                        templateUrl: 'partials/modal/verification.html',
                        controller: 'VerificationCtrl',
                        size: 'lg',
                        backdrop: false
                    };
                    this.open = function (action) {
                        var vm = $modal.open(this.params);
                        vm.result.then(function (selectedItem) {
                            action(selectedItem);
                        }, function () {
                            //console.log('dissim verification');
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