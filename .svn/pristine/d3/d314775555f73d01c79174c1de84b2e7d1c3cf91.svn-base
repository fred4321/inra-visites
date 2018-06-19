/**
 *  Copyright (C) 2014 thurgi
 *  data is table and contain objects
 *  filter is object
 **/
angular.module('inra').filter('table', ['$filter', function ($filter) {
        return function (data, filter) {
            var t = true;
            var key = [];
            //teste que le filtre n'est pas vide
            for (var k in filter) {
                key.push(k);
                if (filter[k] !== "") {
                    t = false;
                }
            }
            if (t && key.length > 0) {
                return data;
            }
            //teste du filtre
            var result = [];
            for (var i in data) {
                t = true;
                for (var key in filter) {
                    //teste pour les date
                    var tDate = false, numeric = false, string = false;
                    if (key.search(/date/i) !== -1) {//si la cle de la valeur contient date
                        var date = $filter('date')(new Date(data[i][key]));//création d'un objet date à partir d'une string
                        tDate = date.search(new RegExp(filter[key], "gi")) !== -1;
                    }else if (typeof data[i][key] === 'number' && data[i][key] % 1 === 0) {//si la donnée à tester est un entier
                        numeric = data[i][key].toString().search(filter[key]) === 0;
                    }else if (typeof data[i][key] === 'string') {
                        string = data[i][key].toLowerCase().search(filter[key].toLowerCase()) === 0;
                    }
                    //teste global
                    t = t && (filter[key] === "" || (data[i][key] && ( string || tDate || numeric )));
                }
                if (t) {
                    result.push(data[i]);
                }
            }
            return result;
        };
    }]);