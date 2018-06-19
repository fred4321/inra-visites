/* * Copyright (C) 2014 thurgi*/
angular.module('inra').filter('heureMinute',function(){
    return function(input){
        var posH = input.indexOf('H');
        var posh = input.indexOf('h');
        var pos;
        if(posH !== -1 ){
            pos = posH;
        }else if(posh !== -1){
            pos = posh;
        }else{
            return 0;
        }
        return  parseInt(input.substr(0,pos)) * 60 + parseInt(input.substr(pos+1, input.length));
    };
});