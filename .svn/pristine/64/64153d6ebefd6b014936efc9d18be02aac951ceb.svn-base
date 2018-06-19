/* * Copyright (C) 2014 thurgi*/
angular.module('dateVM', [])
        .controller('DateVMCtrl', ['$scope', '$modalInstance', 'Calendrier', 'HoraireModel', 'ReferentielService', 'VmService',
            function ($scope, $modalInstance, Calendrier, HoraireModel, ReferentielService, VmService) {
                //fermeture
                $scope.ok = function () {
                    var post = getSelection();
                    if (post.creneaux.length > 0) {
                        if(post.lieu){
                            VmService.add(post, function () {
                                $modalInstance.close();
                            }, function (result) {
                                switch(result.status){
                                    case 406:
                                        $scope.addError('danger', 'Site sans lieu ou lieu inconnu');
                                        break;
                                    case 407:
                                        $scope.addError('danger', 'Site Inconnu');
                                        break;
                                    case 415:
                                        $scope.addError('danger', 'Erreur(s) de paramètres');
                                        break;
                                    default:
                                        $scope.addError('danger', 'Erreur(s) serveur');
                                        break;
                                }
                            });
                        }else{
                            $scope.addError('warning', 'Veuillez choisir un lieu');
                        }
                    } else {
                        $scope.addError('warning', 'Veuillez choisir un créneau');
                    }
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss();
                };

                // alerts
                $scope.alerts = [];
                $scope.addError = function (t, txt) {
                    $scope.alerts.push({type: t, msg: txt});
                    if (t === 'success') {
                        $scope.init();
                    }
                };
                $scope.closeAlert = function (index) {
                    $scope.alerts.splice(index, 1);
                };

                $scope.date = {
                    periode: 'false',
                    value: new Date(),
                    debut: new Date(),
                    fin: new Date()
                };

                $scope.calendrierDate = new Calendrier();
                $scope.calendrierDebut = new Calendrier();
                $scope.calendrierFin = new Calendrier();
                $scope.site = {
                    value: [],
                    selected: {}
                };
                $scope.lieu = {
                    value:[],
                    selected: {}
                };
                ReferentielService.getAll(function (result) {
                    $scope.site.value = result.data;
                    $scope.site.selected = $scope.site.value[0];
                }, null, {groupe: 'site'});
                
                ReferentielService.getAll(function (result) {
                    $scope.lieu.value = result.data;
                    $scope.lieu.selected = $scope.lieu.value[0];
                }, null, {groupe: 'lieuVM'});
                
                ReferentielService.getAll(function (result) {
                    if(result.status === 200){
                        for(var i in result.data){
                            if(result.data[i].info2==="1"){
                                $scope.valeurHoraire = result.data[i].valeur;
                                $scope.valeurDelay = result.data[i].info1;
                                $scope.horaire = new HoraireModel(result.data[i].info1, result.data[i].valeur);
                                return;
                            }
                        }
                    }
                    $scope.horaire = new HoraireModel(20);
                }, null, {groupe: 'créneau VM'});
                
                $scope.delays = [];
                ReferentielService.getAll(function (result) {
                    if(result.status===200){
                        for(var i in result.data){
                            $scope.delays.push(result.data[i].valeur);
                        }
                        return;
                    }
                    $scope.delays=[10,15,20];
                    
                }, null, {groupe: 'délai VM'});

                function getSelection() {
                    return {
                        date: $scope.date,
                        site: $scope.site.selected,
                        lieu: $scope.lieu.selected,
                        creneaux: $scope.horaire.getSelected()
                    };
                }
                
                
                $scope.changeDelay = function(delay){
                    if(delay){
                        $scope.horaire = new HoraireModel(delay, $scope.valeurHoraire);
                    }else{
                        $scope.horaire = new HoraireModel($scope.valeurDelay, $scope.valeurHoraire);
                    }
                };
                
                
            }]).
        factory('InstanceDateVM', ['$modal', function ($modal) {
                return function () {
                    this.params = {
                        templateUrl: 'partials/modal/dateVM.html',
                        controller: 'DateVMCtrl',
                        size: 'sm',
                        backdrop: false
                    };
                    this.open = function (action) {
                        var agent = $modal.open(this.params);
                        agent.result.then(function (selectedItem) {
                            action(selectedItem);
                        }, function () {
                            console.log('dissim agent');
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