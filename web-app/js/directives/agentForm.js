/* * Copyright (C) 2014 thurgi*/
angular.module('formINRA', ['Configuration', 'ngAnimate'])
        .directive('agentForm', ['CONST', 'Calendrier', 'ReferentielService', 'UniteService', 'UserService', 'cleanUpSpecialChars',
            function (CONST, Calendrier, ReferentielService, UniteService, UserService, cleanUpSpecialChars) {
                return {
                    restrict: 'E',
                    templateUrl: CONST.templates.agentForm,
                    scope: {
                        formData: '=',
                        edit: '=',
                        validate: '=',
                        addError: '=',
                        initEdit: '@'
                    },
                    link: function (scope, elem, attrs) {
                        scope.referentiel = {
                            unites: [],
                            gus: [],
                            sites: []
                        };

                        scope.calendrierEntree = new Calendrier();
                        scope.calendrierNaissance = new Calendrier();
                        scope.calendrierSortie = new Calendrier();
                        scope.calendrierDerniereVm = new Calendrier();

                        scope.disable = (scope.initEdit) ? true : false;

                        ReferentielService.getAll(function (result) {
                            scope.periodicite = [];
                            if (scope.formData && scope.formData.periodicite) {
                                scope.periodicite.push(scope.formData.periodicite);
                            }
                            for (var i in result.data) {
                                scope.periodicite.push(result.data[i].valeur);
                            }
                        }, null, {groupe: 'periodicite'});

                        ReferentielService.getAll(function (result) {
                            scope.sites = [];
                            if (scope.formData && scope.formData.site) {
                                scope.sites.push(scope.formData.site);
                            }
                            for (var i in result.data) {
                                scope.sites.push(result.data[i].valeur);
                            }
                            scope.referentiel.sites = result.data;
                        }, null, {groupe: 'site'});

                        ReferentielService.getLibEmploi(function (result) {
                            scope.emplois = result.data;
                            if (scope.emplois[0] === null) {
                                scope.emplois.shift();
                            }
                        });

                        UniteService.getAll(function (result) {
                            scope.unites = [];
                            if (scope.formData && scope.formData.unite) {
                                scope.unites.push(scope.formData.unite);
                            }
                            for (var i in result.data) {
                                scope.unites.push(result.data[i].nom);
                            }
                            scope.referentiel.unites = result.data;
                        }, null);

                        UserService.getGu(function (result) {
                            scope.gus = [];
                            if (scope.formData && scope.formData.defaultGu) {
                                scope.gus.push(scope.formData.defaultGu);
                            }
                            for (var i in result.data) {
                                scope.gus.push(result.data[i].identifiant);
                            }
                            scope.referentiel.gus = result.data;
                        });

                        scope.alerts = [];

                        scope.edit = function () {
                            scope.disable = !scope.disable;
                        };

                        scope.init = function () {
                            for (var key in scope.formData) {
                                scope.formData[key] = '';
                            }
                        };

                        scope.validate = function () {
                            if (scope.agentForm.$valid) {
                                scope.formData.dateNaissance = (typeof scope.formData.dateNaissance === 'string' || scope.formData.dateNaissance instanceof String) ? scope.formData.dateNaissance : scope.calendrierNaissance.formating(scope.formData.dateNaissance);
                                scope.formData.dateEntree = (typeof scope.formData.dateEntree === 'string' || scope.formData.dateEntree instanceof String) ? scope.formData.dateEntree : scope.calendrierEntree.formating(scope.formData.dateEntree);
                                scope.formData.dateSortie = (typeof scope.formData.dateSortie === 'string' || scope.formData.dateSortie instanceof String) ? scope.formData.dateSortie : scope.calendrierSortie.formating(scope.formData.dateSortie);
                                scope.formData.dateDerniereVM = (typeof scope.formData.dateDerniereVM === 'string' || scope.formData.dateDerniereVM instanceof String) ? scope.formData.dateDerniereVM : scope.calendrierDerniereVm.formating(scope.formData.dateDerniereVM);
                                return scope.formData;
                            } else {
                                scope.addError('danger', 'Certains champs de saisis ne sont pas correct.');
                                return false;
                            }
                        };
                        //Ajoute une alert (type(string), texte(string)
                        scope.addError = function (t, txt) {
                            scope.alerts.splice(0, scope.alerts.length);
                            scope.alerts.push({type: t, msg: txt});
                            if (t === 'success' || t === 'info') {
                                scope.init();
                            }
                        };

                        scope.closeAlert = function (index) {
                            scope.alerts.splice(index, 1);
                        };

                        scope.changeUnite = function (unite) {
                            for (var i in scope.referentiel.unites) {
                                if (scope.referentiel.unites[i].nom === unite) {
                                    scope.formData.site = scope.referentiel.unites[i].site;
                                    scope.formData.gu = scope.referentiel.unites[i].defaultGu;
                                    scope.changeGu(scope.referentiel.unites[i].defaultGu);
                                    return;
                                }
                            }
                        };

                        scope.changeGu = function (gu) {
                            for (var i in scope.referentiel.gus) {
                                if (scope.referentiel.gus[i].identifiant === gu) {
                                    scope.formData.agu = scope.referentiel.gus[i].email;
                                    return;
                                }
                            }
                        };

                        function cleanUpSpecialChars(str)
                        {
                            str = str.replace(/[ÀÁÂÃÄÅ]/g, "A");
                            str = str;
                            str = str.replace(/[ÈÉÊË]/g, "E");
                            //.... all the rest
                            return str.replace(/[^a-z0-9]/gi, ''); // final clean up
                        }

                        scope.setEmail = function () {
                            scope.formData.courriel = ((scope.formData.prenom ? scope.formData.prenom : "") + "." + (scope.formData.nom ? scope.formData.nom : "") + "@inra.fr")
                                    .replace(/ /g, '')
                                    .replace(/[ÀÁÂÃÄÅàáâãäå]/g, "a")
                                    .replace(/[ÈÉÊËèéêë]/g, "e")
                                    .replace(/[îïÎÏ]/g, "i")
                                    .replace(/[ôöÔÖ]/g, "o")
                                    .replace(/[ùûüÛÜ]/g, "u")
                                    .replace(/[ç]/g, "c")
                                    .toLowerCase();
                        };

                    }
                };
            }]);