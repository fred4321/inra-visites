/* * Copyright (C) 2014 thurgi*/

angular.module('Menu').factory('MenuModel', ['$location', function($location) {
        return ({
            view: true,
            selectMenu: function(i) {
                for (var j in this.menus) {
                    this.menus[j].active = false;
                }
                this.menus[i].active = true;
                this.menuActive = this.menus[i];
                this.selectSubMenu(0);
            },
            selectSubMenu: function(i) {
                for (var j in this.menuActive.subMenu) {
                    this.menuActive.subMenu[j].active = false;
                }
                if(this.menuActive.subMenu.length>0){
                    this.menuActive.subMenu[i].active = true;
                }
            },
            changeLocation: function(i) {
                $location.path(this.menuActive.subMenu[i].link);
            },
            //selection du menu en fonction de l'url
            init: function(location) {
                var t = true;
                for (var i in this.menus) {
                    for (var j in this.menus[i].subMenu) {
                        if (this.menus[i].subMenu[j].link === location.substring(1)) {
                            this.selectMenu(i);
                            this.selectSubMenu(j);
                            t = false;
                        }
                    }
                }
                if (t) {
                    this.selectMenu(0);
                    this.selectSubMenu(0);
                }
            },
            menuActive: {},
            menus: [],
            
            defaultMenus: function() {
                return [
                    {
                        title: 'Accueil',
                        active: false,
                        subMenu: [
                            {
                                title: 'Agents à convoquer',
                                link: 'accueil',
                                active: false,
                                view: true
                            }, {
                                title: 'Agents convoqués',
                                link: 'convoque',
                                active: false,
                                view: true
                            }
                        ]
                    }, {
                        title: 'Agents',
                        active: false,
                        subMenu: [
                            {
                                title: 'Tous les Agents',
                                link: 'tous',
                                active: false,
                                view: true
                            }, {
                                title: 'Nouvel Agent',
                                link: 'nouveau',
                                active: false,
                                view: true
                            }, {
                                title: 'En attente d\'archivage',
                                link: 'archivage',
                                active: false,
                                view: true
                            }, {
                                title: 'Agents archivés',
                                link: 'archive',
                                active: false,
                                view: true
                            }
                        ]
                    },
                    {
                        title: 'Planning VM',
                        active: false,
                        subMenu: [
                            {
                                title: 'Visites à programmer',
                                link: 'programmerVM',
                                active: false,
                                view: true
                            }, {
                                title: 'En cours, à traiter par GU/SP',
                                link: 'enCourATraiterVM',
                                active: false,
                                view: true
                            }, {
                                title: 'En cours, attribuées',
                                link: 'attribueVM',
                                active: false,
                                view: true
                            }, {
                                title: 'Effectuées/Validées',
                                link: 'effectueVM',
                                active: false,
                                view: true
                            }
                        ]
                    },
                    {
                        title: 'Bilan sanguin',
                        active: false,
                        subMenu: [
                            {
                                title: 'Agents à convoquer',
                                link: 'prioritaireBS',
                                active: false,
                                view: true
                            }, {
                                title: 'BS à programmer',
                                link: 'programmerBS',
                                active: false,
                                view: true
                            }, {
                                title: 'Agents convoqués',
                                link: 'convoquerBS',
                                active: false,
                                view: true
                            }, {
                                title: 'BS effectués',
                                link: 'effectuerBS',
                                active: false,
                                view: true
                            }
                        ]
                    },
                    {
                        title: 'Outils',
                        active: false,
                        subMenu: [
                            {
                                title: 'Référentiel',
                                link: 'referentiel',
                                active: false,
                                view: true
                            }, {
                                title: 'Droits',
                                link: 'droit',
                                active: false,
                                view: true
                            }, {
                                title: 'Imports',
                                link: 'import',
                                active: false,
                                view: true
                            }, {
                                title: 'Journaux',
                                link: 'journaux',
                                active: false,
                                view: true
                            }, {
                                title: 'Statistiques',
                                link: 'stat',
                                active: false,
                                view: true
                            }
                        ]
                    }
                ];
            }
        });
    }]);