/* * Copyright (C) 2014 thurgi*/
angular.module('inra').factory('HoraireModel', [
    function() {
        return function(delai, creneauVM) {
            delai = parseInt(delai);
            
            this.horaire = function(h, m) {
                this.heure = h;
                this.minute = m;
                this.selected = false;
            };
            
            if(creneauVM){
                var tab = creneauVM.split(' - ');
                for(var i in tab){
                    tab[i]=tab[i].toUpperCase().split('H');
                    for (var j in tab[i]){
                        tab[i][j]=parseInt(tab[i][j]);
                    }
                }
            }else{
                var tab = [[9,0],[12,0],[14,0],[17,0]];
            }
            var totalAM = (tab[1][0] - tab[0][0])*60 + tab[1][1] - tab[0][1];
            var totalPM = (tab[3][0] - tab[2][0])*60 + tab[3][1] - tab[2][1];
            this.matin = [];
            for(var i = 0 ; i < totalAM ; i=i+delai){
                this.matin.push(new this.horaire(tab[0][0] + Math.floor((i+tab[0][1])/60), (tab[0][1] + i) %60));
            }
            this.am = [];
            for(var i = 0 ; i < totalPM ; i=i+delai){
                this.am.push(new this.horaire(tab[2][0] + Math.floor((i+tab[2][1])/60), (tab[2][1] + i) %60));
            }

            //methode de selection de creneaux horaire
            this.removeMatin=function(i){
                this.matin.splice(i,1);
            };
            this.removeAM=function(i){
                this.am.splice(i,1);
            };
            this.activeMatin = function() {
                for (var i in this.matin) {
                    this.matin[i].selected = !this.matin[i].selected;
                }
            };
            this.activeAM = function() {
                for (var i in this.am) {
                    this.am[i].selected = !this.am[i].selected;
                }
            };
            this.activeDay = function() {
                this.activeMatin();
                this.activeAM();
            };
            //recupération des creneaux horaire selectionner
            this.getSelected = function() {
                var creneaux = [];
                for (var i in this.am) {
                    if (this.am[i].selected) {
                        creneaux.push(this.am[i].heure + 'H' + ( parseInt(this.am[i].minute)<10 ? '0' + this.am[i].minute : this.am[i].minute ) );
                    }
                }
                for (var i in this.matin) {
                    if (this.matin[i].selected) {
                        creneaux.push(this.matin[i].heure + 'H' + ( parseInt(this.matin[i].minute)<10 ? '0' + this.matin[i].minute : this.matin[i].minute ) );
                    }
                }
                return creneaux;
            };
        };
    }]);