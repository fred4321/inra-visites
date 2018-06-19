/* * Copyright (C) 2014 thurgi*/
angular.module('inra').factory('ListeEditModel', function() {
        return  function(service) {
            this.inputView = false;
            this.data = [];
            this.dataEdit=[];
            this.error=function(){};
            
            this.get = function(){
                service.getAllOBJ(this,function(result,listEdit){
                    if (result.status === 200) {
                        listEdit.data=result.data;
                    }else{
                        console.log('erreur de get msg:' + result.statusText);
                    }
                });
            };

            this.add = function() {
                service.addOBJ(this,this.inputValue, function(result, listEdit) {
                    if (result.status === 200) {
                        listEdit.data.push(result.data);
                        listEdit.inputValue = {};
                    } else {
                        this.error();
                    }
                }, function() {
                    this.error();
                });
            };
            
            this.remove = function(id,i) {
                service.removeOBJ(this,id, function(result,listEdit) {
                    if (result.status === 200) {
                        listEdit.data.splice(i, 1);
                    } else {
                        this.error();
                    }
                });
            };
            this.update = function(i) {
                service.updateOBJ(this,this.data[i], function(result,listEdit) {
                    if (result.status === 200) {
                        listEdit.data[i] = result.data;
                    } else {
                        this.error();
                    }
                });
            };
            this.edit=function(i){
                this.dataEdit[i]=angular.copy(this.data[i]);
            };
            this.cancel=function(i){
                this.data[i]=angular.copy(this.dataEdit[i]);
                this.data[i].edit=false;
            };
            
        };
    });